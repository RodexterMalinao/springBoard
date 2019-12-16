package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.OfferDetailDAO;
import com.bomwebportal.lts.dao.PremiumSetDetailDAO;
import com.bomwebportal.lts.dto.BasketPremiumDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetAttbDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.util.LtsConstant;

public class PremiumItemSetDetailServiceImpl implements PremiumItemSetDetailService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private PremiumSetDetailDAO premiumSetDetailDao;
	private OfferDetailDAO offerDetailDao;
	
	public PremiumSetDetailDAO getPremiumSetDetailDao() {
		return premiumSetDetailDao;
	}

	public void setPremiumSetDetailDao(PremiumSetDetailDAO premiumSetDetailDao) {
		this.premiumSetDetailDao = premiumSetDetailDao;
	}
	
	public OfferDetailDAO getOfferDetailDao() {
		return offerDetailDao;
	}

	public void setOfferDetailDao(OfferDetailDAO offerDetailDao) {
		this.offerDetailDao = offerDetailDao;
	}

	public ItemSetDetailDTO getGiftItemSet(String pGiftCode, String locale, String channelId, String applDate, String basketId, String teamCd, String srvBdry) {
		try {
			return this.offerDetailDao.getGiftItemSetDetail(pGiftCode, locale, channelId, LtsConstant.DISPLAY_TYPE_ITEM_SELECT, applDate, basketId, teamCd, srvBdry);
		} catch (DAOException de) {
			logger.error("Fail in LtsOdfferService.getGiftItemSet()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
		
	}
	

	public List<ItemSetDetailDTO> getPcdBundleGiftItemSet(String pPcdSbid,
			String locale, String channelId, String applDate, String basketId, String teamCd, String srvBdry) {
		
		try {
			return this.offerDetailDao.getPcdBundleGiftItemSetDetail(pPcdSbid, locale, channelId, applDate, basketId, teamCd, srvBdry);
		} catch (DAOException de) {
			logger.error("Fail in LtsOdfferService.getPcdBundleGiftItemSet()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
		
	}
	
	public List<ItemSetDetailDTO> getBasketItemSet(ItemSetCriteriaDTO itemSetCriteria) {
		try {
			
			List<ItemSetDetailDTO> basketItemSetList = new ArrayList<ItemSetDetailDTO>();
			
			List<String> basketItemSetIdList = this.offerDetailDao.getBasketItemSetId(itemSetCriteria.getBasketId(), itemSetCriteria.getRelatedItemId());
			
			if (basketItemSetIdList == null || basketItemSetIdList.isEmpty()) {
				return null;
			}
			
			for (String basketItemSetId : basketItemSetIdList) {
				itemSetCriteria.setItemSetId(basketItemSetId);
				ItemSetDetailDTO itemSetDetail = getItemSetDetailDTO(itemSetCriteria);
				if (itemSetDetail != null) {
					basketItemSetList.add(itemSetDetail);
				}
			}
			return basketItemSetList;

		} catch (DAOException de) {
			logger.error("Fail in LtsOdfferService.getBasketItemSet()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public ItemSetDetailDTO getItemSetDetailDTO(ItemSetCriteriaDTO itemSetCriteria) {
		try {
			
			return this.offerDetailDao.getItemSetDetail(itemSetCriteria);
			
		} catch (DAOException de) {
			logger.error("Fail in LtsOdfferService.getBasketContentList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	
	public List<ItemSetDetailDTO> getPremiumSetList(ItemSetCriteriaDTO itemSetCriteria) {
		
		try {
			List<ItemSetDetailDTO> defaultItemSetList = this.premiumSetDetailDao.getDefaultItemSetList(itemSetCriteria);
			
			if (defaultItemSetList == null || defaultItemSetList.isEmpty()) {
				return null;
			}

			List<ItemSetDetailDTO> rtnItemSetList = new ArrayList<ItemSetDetailDTO>();
			
			// Loop Default Premium set and add back those items and attb
			for (ItemSetDetailDTO itemSetDetail : defaultItemSetList) {
				
				List<ItemDetailDTO> itemList = this.offerDetailDao.getSetItemList(itemSetDetail.getItemSetId(), itemSetCriteria.getLocale(), itemSetCriteria.isSelectDefault(), itemSetCriteria.getChannelId(), itemSetCriteria.getApplnDate(), itemSetCriteria.getEligibleDocType());
				if (itemList != null && !itemList.isEmpty()) {
					itemSetDetail.setItemDetails(itemList.toArray(new ItemDetailDTO[0]));
					rtnItemSetList.add(itemSetDetail);
				}

				List<ItemSetAttbDTO> attbList = this.offerDetailDao.getSetAttbList(itemSetDetail.getItemSetId());
				if (attbList != null && !attbList.isEmpty()) {
					itemSetDetail.setItemSetAttbs(attbList.toArray(new ItemSetAttbDTO[0]));
				}
			}
			return rtnItemSetList;
		}
		catch (DAOException de) {
			logger.error("Fail in PremiumItemSetDetailService.getPremiumSetList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	private void revisePremiumSetQty(ItemSetCriteriaDTO itemSetCriteria, ItemSetDetailDTO premiumSet) {
		
		if (LtsConstant.PREMIUM_ELIGIBLE_TYPE_PLAN.equals(itemSetCriteria.getEligibleItemType())
				&& LtsConstant.ORDER_TYPE_SB_RETENTION.equals(itemSetCriteria.getOrderType())) {
			
			// assigned Qty from w_arpu_change_lkup
			if (itemSetCriteria.getAssignedQty() != null) {
				premiumSet.setMaxQty(itemSetCriteria.getAssignedQty().intValue());
				premiumSet.setMinQty(itemSetCriteria.getAssignedQty().intValue());
			}
			else {
				String extraQty = getExtraPremiumQty(itemSetCriteria.getToProd(), 
						String.valueOf(itemSetCriteria.getNewArpu()), String.valueOf(itemSetCriteria.getNewArpu() - itemSetCriteria.getProfileArpu()));
				if (StringUtils.isNotBlank(extraQty)) {
					premiumSet.setMaxQty(premiumSet.getMaxQty() + Integer.parseInt(extraQty));
					premiumSet.setMinQty(premiumSet.getMaxQty());
				}
				
				// For Submitted D Form + ARPU decrease + new TP > $88 
				if (premiumSet.getMaxQty() <= 0 && itemSetCriteria.isAtLeastHave1Premium()) {
					premiumSet.setMaxQty(1);
					premiumSet.setMinQty(1);
				}	
			}
		}
	}
	
	public List<ItemSetDetailDTO> getBasketPremiumSetList(ItemSetCriteriaDTO itemSetCriteria) {
		
		List<ItemSetDetailDTO> fullItemSetList = new ArrayList<ItemSetDetailDTO>();
		
		
		try {
			if (itemSetCriteria.getEligibleItemTypeList() != null
					&& !itemSetCriteria.getEligibleItemTypeList().isEmpty()) {
				
				for (String eligibleItemType : itemSetCriteria.getEligibleItemTypeList()) {
					itemSetCriteria.setEligibleItemType(eligibleItemType);
					List<ItemSetDetailDTO> basketItemSetList = offerDetailDao.getBasketItemSetList(itemSetCriteria);
					
					if (basketItemSetList != null && !basketItemSetList.isEmpty()) {
						revisePremiumSetQty(itemSetCriteria, basketItemSetList.get(0));
						if (basketItemSetList.get(0).getMaxQty() > 0) {
							fullItemSetList.addAll(basketItemSetList);	
						}
					}
				}
			}
		} catch (DAOException de) {
			logger.error("Fail in PremiumItemSetDetailService.getBasketPremiunSetList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
		return fullItemSetList;
	}
	
	public List<ItemSetDetailDTO> getAllPremiumSetList(ItemSetCriteriaDTO itemSetCriteria) {
		List<ItemSetDetailDTO> fullItemSetList = new ArrayList<ItemSetDetailDTO>();
		
		// Basket Item Set 
		List<ItemSetDetailDTO> basketPremiumSetList = getBasketPremiumSetList(itemSetCriteria);

		if (basketPremiumSetList != null && !basketPremiumSetList.isEmpty()) {
			fullItemSetList.addAll(basketPremiumSetList);
		}
		
		// Add default item set list (default ind = Y)- Membership
		String defaultInd = itemSetCriteria.getDefaultInd();
		itemSetCriteria.setDefaultInd("Y");
		List<ItemSetDetailDTO> defaultItemSetTmpList = getPremiumSetList(itemSetCriteria);
		if (defaultItemSetTmpList != null && !defaultItemSetTmpList.isEmpty()) {
			fullItemSetList.addAll(defaultItemSetTmpList);
		}
		itemSetCriteria.setDefaultInd(defaultInd);
		
		// Specific Standalone Item Set
		if (ArrayUtils.isNotEmpty(itemSetCriteria.getStandaloneItemSetTypes())) {
			String itemSetType = itemSetCriteria.getItemSetType();
			for (String standaloneItemSetType : itemSetCriteria.getStandaloneItemSetTypes()) {
				itemSetCriteria.setItemSetType(standaloneItemSetType);
				List<ItemSetDetailDTO> standaloneItemSetTmpList = getPremiumSetList(itemSetCriteria);
				if (standaloneItemSetTmpList != null && !standaloneItemSetTmpList.isEmpty()) {
					fullItemSetList.addAll(standaloneItemSetTmpList);	
				}
			}
			itemSetCriteria.setItemSetType(itemSetType);
		}

		if (fullItemSetList.isEmpty()) {
			return null;
		}
		
		return fullItemSetList;
		
	}
	
	public List<BasketPremiumDTO> getBasketPremiumList(String[] pBasketId, String pLocale){
		try {
			return this.premiumSetDetailDao.getBasketPremiumList(pBasketId, pLocale);	

		} catch (DAOException de) {
			logger.error("Fail in PremiumItemSetDetailService.getBasketPremiunList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public String getExtraPremiumQty(String toProd, String newApru, String apruDifferent) {
		try {
			return this.premiumSetDetailDao.getExtraPremiumQty(toProd, newApru, apruDifferent);

		} catch (DAOException de) {
			logger.error("Fail in PremiumItemSetDetailService.getExtraPremiumQty()", de);
			throw new RuntimeException(de.getCustomMessage());
		}		
	}

	public String getArpuChangePremiumQty(String toProd, String existArpu, String newApru) {
		try {
			return this.premiumSetDetailDao.getArpuChangePremiumQty(toProd, existArpu, newApru);

		} catch (DAOException de) {
			logger.error("Fail in PremiumItemSetDetailService.getArpuChangePremiumQty()", de);
			throw new RuntimeException(de.getCustomMessage());
		}		
	}
	
}