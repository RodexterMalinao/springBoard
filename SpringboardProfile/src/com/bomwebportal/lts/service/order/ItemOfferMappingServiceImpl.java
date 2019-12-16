package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.order.ItemOfferMappingDAO;
import com.bomwebportal.lts.dto.order.OfferItemRelationDTO;
import com.bomwebportal.lts.dto.order.OfferTypeDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.util.LtsProfileConstant;
import com.bomwebportal.service.CodeLkupCacheService;

public class ItemOfferMappingServiceImpl implements ItemOfferMappingService {

	private final Log logger = LogFactory.getLog(getClass());
	
	protected ItemOfferMappingDAO itemOfferMappingDao = null;
	protected CodeLkupCacheService tpExpireMonthAllowanceLkupCacheService = null;
	
	
	public Collection<ItemDetailProfileLtsDTO> getServiceItems(List<OfferDetailProfileLtsDTO> pOfferList) {
		
		List<OfferDetailProfileLtsDTO> expiredOfferList = new ArrayList<OfferDetailProfileLtsDTO>();
		
		for (int i=0; i < pOfferList.size(); ++i) {
			if (pOfferList.get(i).getExpiredMonths() > 0) {
				expiredOfferList.add(pOfferList.get(i));
			}
		}
		for (int i=0; i < expiredOfferList.size(); ++i) {
			pOfferList.remove(expiredOfferList.get(i));
		}
		OfferItemRelationDTO actualSrvItemRelation = this.getServiceItemsRelation(pOfferList, false);
		pOfferList.addAll(expiredOfferList);
		List<ItemDetailProfileLtsDTO> srvItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		ItemDetailProfileLtsDTO actualSrvItem = null;
		int tpExpireAllowancePeriod = Integer.parseInt(((LookupItemDTO[])this.tpExpireMonthAllowanceLkupCacheService.get(CodeLkupCacheService.WILD_CARDS))[0].getItemKey());
		
		if (actualSrvItemRelation != null) {
			actualSrvItem = this.creatItemByRelation(pOfferList, actualSrvItemRelation, LtsProfileConstant.ITEM_TYPE_SERVICE);
			srvItemList.add(actualSrvItem);
		}
		OfferItemRelationDTO expiredTpSrvItemRelation = this.getServiceItemsRelation(pOfferList, true);
		ItemDetailProfileLtsDTO expiredSrvItem = null;
		
		if (expiredTpSrvItemRelation != null) {
			expiredSrvItem = this.creatItemByRelation(expiredOfferList, expiredTpSrvItemRelation, LtsProfileConstant.ITEM_TYPE_EXPIRED_SERVICE);
			
			// Set IDD Free Mins as only expired TP code (without service code) may not map actual result.
			for (OfferDetailProfileLtsDTO expiredOffer : expiredOfferList) {
				try {
					String iddFreeMin = itemOfferMappingDao.getIddFreeMin(expiredOffer.getOfferId());
					if (StringUtils.isNotBlank(iddFreeMin)) {
						expiredSrvItem.setIddFreeMin(iddFreeMin);
						break;
					}	
				}
				catch (Exception e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
					throw new AppRuntimeException(e.getMessage());
				}
			}
			
			if (actualSrvItem != null && !StringUtils.equals(expiredSrvItem.getItemId(), actualSrvItem.getItemId())) {
				if (StringUtils.isEmpty(actualSrvItem.getIddFreeMin())) {
					actualSrvItem.setIddFreeMin(expiredSrvItem.getIddFreeMin());
				}
				srvItemList.add(expiredSrvItem);
				
				if (actualSrvItem != null && expiredSrvItem.getTpExpiredMonths() >= 0 && expiredSrvItem.getTpExpiredMonths() < tpExpireAllowancePeriod
						&& expiredSrvItem.isFreeVasInd() && !actualSrvItem.isFreeVasInd()) {
					srvItemList.add(this.createNewStandaloneItem(LtsProfileConstant.ITEM_TYPE_FREE));
				}
			}
		}
		if (actualSrvItem != null) {
			this.removeOfferFromList(pOfferList, actualSrvItem.getOffers());
		}
		setDuplexVasInd(srvItemList);
		
		return srvItemList;
	}

	public void setDuplexVasInd(List<ItemDetailProfileLtsDTO> profileItemList) {
		if (profileItemList == null || profileItemList.isEmpty()) {
			return;
		}
		
		for (ItemDetailProfileLtsDTO profileItem : profileItemList) {
			OfferDetailProfileLtsDTO[] offerDetailProfiles = profileItem.getOffers();
			if (ArrayUtils.isEmpty(offerDetailProfiles)) {
				continue;
			}
			for (OfferDetailProfileLtsDTO offerDetailProfile : offerDetailProfiles) {
				Set<String> psefSet = offerDetailProfile.getPsefSet();
				if (psefSet != null) {
					if (psefSet.contains(LtsProfileConstant.PSEF_CD_DUPLEX)) {
						profileItem.setDuplexItem(true);
						return;
					}
				}
			}
		}
	}
	
	private OfferItemRelationDTO getServiceItemsRelation(List<OfferDetailProfileLtsDTO> pOfferList, boolean pExpiredTp) {
		
		if (pOfferList == null || pOfferList.isEmpty()) {
			return null;
		}
		
		OfferItemRelationDTO termPlanOfferItemRelation = null;
		
		try {
			termPlanOfferItemRelation = this.itemOfferMappingDao.getTeamPlanOfferItemMapping(pOfferList.toArray(new OfferDetailProfileLtsDTO[pOfferList.size()]), pExpiredTp);
			
			if (termPlanOfferItemRelation == null) {
				termPlanOfferItemRelation = this.itemOfferMappingDao.getServiceOfferItemMapping(pOfferList.toArray(new OfferDetailProfileLtsDTO[pOfferList.size()]), pExpiredTp);
			}
		} catch (DAOException e) {
			logger.error("Fail to get service offer item mapping.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to get service offer item mapping.", e);
		}
		return termPlanOfferItemRelation;
	}
	
	protected ItemDetailProfileLtsDTO creatItemByRelation(List<OfferDetailProfileLtsDTO> pOfferList, OfferItemRelationDTO pRelation, String itemType) {
		
		ItemDetailProfileLtsDTO item = new ItemDetailProfileLtsDTO();
		item.setItemId(pRelation.getExistItemId());
		item.setTermExistCdInd(pRelation.getTermExistCdInd());
		item.setIddFreeMin(pRelation.getIddFreeMin());
		item.setChangeToItemId(pRelation.getChangeToItemId());
		item.setFreeVasInd(pRelation.isFreeVasInd());
		item.setItemType(itemType);
		item.setItemNature(pRelation.getItemType());
		item.setTpPremium(pRelation.getTpPremium());
		OfferTypeDTO[] offerTypes = pRelation.getOffers();
		
		for (int i=0; offerTypes!=null && i<offerTypes.length; ++i) {
			for (int j=0; j<pOfferList.size(); ++j) {
				if (StringUtils.equals(offerTypes[i].getOfferId(), pOfferList.get(j).getOfferId())) {
					pOfferList.get(j).setOfferType(offerTypes[i].getOfferType());
					
					if (StringUtils.equals(offerTypes[i].getOfferType(), LtsProfileConstant.OFFER_TYPE_TERM_PLAN)
							|| StringUtils.equals(offerTypes[i].getOfferType(), LtsProfileConstant.OFFER_TYPE_MIRROR_PLAN)
							|| StringUtils.equals(offerTypes[i].getOfferType(), LtsProfileConstant.OFFER_TYPE_EXPIRED_MIRROR_PLAN)) {
						item.setConditionEffStartDate(pOfferList.get(j).getConditionEffStartDate());
						item.setConditionEffEndDate(pOfferList.get(j).getConditionEffEndDate());
						item.setTpExpiredMonths(pOfferList.get(j).getExpiredMonths());
					}
					if (StringUtils.isNotEmpty(pOfferList.get(j).getPromotionStartDate())) {
						item.setPromotionStartDate(pOfferList.get(j).getPromotionStartDate());
						item.setPromotionEndDate(pOfferList.get(j).getPromotionEndDate());
						item.setPaidVas(Double.parseDouble(pOfferList.get(j).getPromotionExpiredMonths()) > 0);
						item.setPromotionExpiredMonths(pOfferList.get(j).getPromotionExpiredMonths());
					}
					item.addOffer(pOfferList.get(j));
				}
			}
		}
		return item;
	}
	
	private ItemDetailProfileLtsDTO createNewStandaloneItem(String pItemType) {
		
		ItemDetailProfileLtsDTO item = new ItemDetailProfileLtsDTO();
		item.setFreeVasInd(true);
		item.setItemType(pItemType);
		return item;
	}
	
	protected void removeOfferFromList(List<OfferDetailProfileLtsDTO> pOfferList, OfferDetailProfileLtsDTO[] pOffers) {
		
		for (int i=0; pOffers!=null && i<pOffers.length; ++i) {
			pOfferList.remove(pOffers[i]);
		}
	}

	public ItemOfferMappingDAO getItemOfferMappingDao() {
		return itemOfferMappingDao;
	}

	public void setItemOfferMappingDao(ItemOfferMappingDAO itemOfferMappingDao) {
		this.itemOfferMappingDao = itemOfferMappingDao;
	}

	public CodeLkupCacheService getTpExpireMonthAllowanceLkupCacheService() {
		return tpExpireMonthAllowanceLkupCacheService;
	}

	public void setTpExpireMonthAllowanceLkupCacheService(
			CodeLkupCacheService tpExpireMonthAllowanceLkupCacheService) {
		this.tpExpireMonthAllowanceLkupCacheService = tpExpireMonthAllowanceLkupCacheService;
	}
}
