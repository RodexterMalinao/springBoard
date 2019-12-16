package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.order.ItemOfferMappingDAO;
import com.bomwebportal.lts.dao.order.OfferItemMappingDAO;
import com.bomwebportal.lts.dto.bom.BomAttbDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OfferItemRelationDTO;
import com.bomwebportal.lts.dto.order.OfferTypeDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemActionLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferActionLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.bom.OfferProfileLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;

public class OfferItemServiceImpl extends ItemOfferMappingServiceImpl implements OfferItemService {

	private final Log logger = LogFactory.getLog(getClass());
	
	private LtsBasketService ltsBasketService;
	private OfferItemMappingDAO offerItemMappingDAO;
	private static Map<String, OfferItemRelationDTO> noTpItem = null;
	private Mapper dozerMapper;
	private CodeLkupCacheService promotionExpireMonthAllowanceLkupCacheService;
	private CodeLkupCacheService deviceCodeLkupCacheService = null;
	private OfferProfileLtsService offerProfileLtsService = null;
	
	
	private static final String[] EXCLUDE_PAID_VAS = {LtsConstant.ITEM_TYPE_IDD};
	
	
	public Map<String, OfferItemRelationDTO> getNoTpItems() {
		
		if (noTpItem != null && noTpItem.size() > 0) {
			return noTpItem;
		}
		OfferItemRelationDTO[] noTpItems = null;
		noTpItem = new TreeMap<String, OfferItemRelationDTO>();
		
		try {
			noTpItems = this.offerItemMappingDAO.getNoTpItems();
		} catch (DAOException e) {
			throw new AppRuntimeException("Fail to get no TP items.", e);
		}
		for (int i=0; noTpItems!=null && i<noTpItems.length; ++i) {
			if (noTpItems[i] != null && StringUtils.isNotBlank(noTpItems[i].getExistItemId())) {
				noTpItem.put(noTpItems[i].getExistItemId(), noTpItems[i]);	
			}
		}
		return noTpItem;
	}
	
	public ItemDetailProfileLtsDTO[] getOfferItemMapping(OfferDetailProfileLtsDTO[] pOfferDtls) {
		
		Set<ItemDetailProfileLtsDTO> itemSet = new HashSet<ItemDetailProfileLtsDTO>();
		List<OfferDetailProfileLtsDTO> offerList = new ArrayList<OfferDetailProfileLtsDTO>(Arrays.asList(pOfferDtls));
		
		if (offerList.size() != 0) {
			itemSet.addAll(this.getServiceItems(offerList));
		}
		if (offerList.size() != 0) {
			itemSet.addAll(this.getVasItems(offerList, itemSet));	
		}
		return itemSet.toArray(new ItemDetailProfileLtsDTO[itemSet.size()]);
	}
	
	public String[] generateFreeVasItem(ItemDetailProfileLtsDTO[] pItems, String pDeviceType, String pContractPeriod, String pChannelId, String pOrderType) {
		return generateFreeVasItem(pItems, pDeviceType, pContractPeriod, pChannelId, pOrderType, false);
	}
	
	
	public String[] generateFreeVasItem(ItemDetailProfileLtsDTO[] pItems, String pDeviceType, String pContractPeriod, String pChannelId, String pOrderType, boolean isSubmitDisForm) {
		
		List<ItemDetailProfileLtsDTO> voiceItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		
		for (int i=0; pItems!=null && i<pItems.length; ++i) {
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_SERVICE, pItems[i].getItemType()) || pItems[i].isVoiceItem()) {
				voiceItemList.add(pItems[i]);
			}
		}
		ItemDetailProfileLtsDTO[] voiceItems = voiceItemList.toArray(new ItemDetailProfileLtsDTO[voiceItemList.size()]);
		
		
		if (! (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(pOrderType) 
					&& isSubmitDisForm)) {
			if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(pOrderType)
					&& !isFreeVasExist(pItems)) {
				return null;
			}
			else if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(pOrderType) 
					&& this.isPaidVasExist(voiceItems) && !isFreeVasExist(pItems)) {
				return null;
			}
			else if (this.isPaidVasExist(voiceItems)) {
				return null;
			}	
		}
		
		String[] freeVasItems = null;
		
		
		freeVasItems = this.getFreeVasFromItems(voiceItems, pDeviceType, pContractPeriod, pChannelId, (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(pOrderType) 
				&& isSubmitDisForm));
		
		if (ArrayUtils.isNotEmpty(freeVasItems)) {
			return freeVasItems;
		}
		List<ItemDetailProfileLtsDTO> promotionItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		List<String> freeVasItemIdList = new ArrayList<String>();
		
		for (int i=0; voiceItems!=null && i<voiceItems.length; ++i) {
			if (StringUtils.isNotEmpty(voiceItems[i].getPromotionExpiredMonths())) {
				promotionItemList.add(voiceItems[i]);
			}
		}
		try {
			if (promotionItemList.size() == 0) {
				freeVasItemIdList.addAll(Arrays.asList(this.offerItemMappingDAO.getFreeVasItemIds(pDeviceType, null, pContractPeriod, pChannelId)));
			} else {
				for (int i=0; i<promotionItemList.size(); ++i) {
					if (!this.isExpiredItem(promotionItemList.get(i))) {
						freeVasItemIdList.addAll(Arrays.asList(this.offerItemMappingDAO.getFreeVasItemIds(pDeviceType, promotionItemList.get(i).getPromotionExpiredMonths(), pContractPeriod, pChannelId)));
					}
				}
			}
		} catch (DAOException e) {
			logger.error("Fail to get free VAS items.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to get free VAS items.", e);
		}
		return freeVasItemIdList.toArray(new String[freeVasItemIdList.size()]);
	}
	
	public ItemDetailProfileLtsDTO[] getImsOfferItemMapping(OfferDetailProfileLtsDTO[] pOfferDtls) {
		
		List<OfferItemRelationDTO> offerItemRelationList = new ArrayList<OfferItemRelationDTO>();
		OfferItemRelationDTO offerItemRelation = null;
		
		try {
			for (int i=0; pOfferDtls!=null && i<pOfferDtls.length; ++i) {
				offerItemRelation = this.offerItemMappingDAO.getImsOfferItemMapping(pOfferDtls[i]);
				
				if (offerItemRelation != null) {
					offerItemRelationList.add(offerItemRelation);
				}
			}
		} catch (DAOException e) {
			logger.error("Fail to get free VAS items.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to get free VAS items.", e);
		}
		if (offerItemRelationList.size() == 0) {
			return null;
		}
		offerItemRelationList = this.filterNonEyeImsItems(offerItemRelationList);
		List<ItemDetailProfileLtsDTO> itemList = new ArrayList<ItemDetailProfileLtsDTO>();
		
		for (int i=0; i<offerItemRelationList.size(); ++i) {
			itemList.add(this.creatItemByRelationOfferCode(Arrays.asList(pOfferDtls), offerItemRelationList.get(i), offerItemRelationList.get(i).getItemType()));
		}
		return itemList.toArray(new ItemDetailProfileLtsDTO[itemList.size()]);
	}
	
	private List<OfferItemRelationDTO> filterNonEyeImsItems(List<OfferItemRelationDTO> pOfferRelationList) {
		
		boolean existSpecialMode = false;
		
		for (int i=0; i<pOfferRelationList.size(); ++i) {
			if (StringUtils.equals(LtsConstant.OFFER_TYPE_SPECIAL_MODE, pOfferRelationList.get(i).getItemType())) {
				existSpecialMode = true;
				break;
			}
		}
		List<OfferItemRelationDTO> offerList = new ArrayList<OfferItemRelationDTO>(pOfferRelationList);
		
		if (existSpecialMode) {
			Iterator<OfferItemRelationDTO> it = pOfferRelationList.iterator();
			OfferItemRelationDTO offerItemRelation = null;
			
			while (it.hasNext()) {
				offerItemRelation = it.next();
				
				if (StringUtils.equals(LtsConstant.OFFER_TYPE_MIRROR_PLAN, offerItemRelation.getItemType())
						|| StringUtils.equals(LtsConstant.OFFER_TYPE_EXPIRED_MIRROR_PLAN, offerItemRelation.getItemType())) {
					offerList.remove(offerItemRelation);
				}
			}
		}
		return offerList;
	}
	
	private String[] getFreeVasFromItems(ItemDetailProfileLtsDTO[] pItems, String pDeviceType, String pContractPeriod, String pChannelId, boolean isSubmitDisForm) {
		
		String[] freeVasItemIds = null;
		
		try {
			for (int i=0; pItems!=null && i<pItems.length; ++i) {
				if ((!this.isExpiredItem(pItems[i]) && pItems[i].isFreeVasInd())
						|| isSubmitDisForm) {
					freeVasItemIds = this.offerItemMappingDAO.getFreeVasItemIds(pDeviceType, "999", pContractPeriod, pChannelId);
					
					if (ArrayUtils.isNotEmpty(freeVasItemIds)) {
						pItems[i].setChangeToItemId(freeVasItemIds[0]);
						return freeVasItemIds;
					}
				}
			}		
		} catch (DAOException e) {
			logger.error("Fail to get free VAS items.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to get free VAS items.\n" + e.getMessage(), e);
		}
		return null;
	}
	
	public boolean isFreeVasExist(ItemDetailProfileLtsDTO[] pItems) {
		for (int i=0; i<pItems.length; ++i) {
			if (StringUtils.isEmpty(pItems[i].getPromotionExpiredMonths())) {
				return true;
			}
			else if (Double.parseDouble(pItems[i].getPromotionExpiredMonths()) < Double.parseDouble(((LookupItemDTO[])this.promotionExpireMonthAllowanceLkupCacheService.get(CodeLkupCacheService.WILD_CARDS))[0].getItemKey())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isPaidVasExist(ItemDetailProfileLtsDTO[] pItems) {
		
		boolean isPaidVasExist = false;
		
		for (int i=0; i<pItems.length; ++i) {
			if (StringUtils.isNotEmpty(pItems[i].getPromotionExpiredMonths()) 
					&& Double.parseDouble(pItems[i].getPromotionExpiredMonths()) < Double.parseDouble(((LookupItemDTO[])this.promotionExpireMonthAllowanceLkupCacheService.get(CodeLkupCacheService.WILD_CARDS))[0].getItemKey())) {
				return false;
			}
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_VAS, pItems[i].getItemType())
					&& !LtsSbOrderHelper.isContainString(EXCLUDE_PAID_VAS, pItems[i].getItemNature())
					&& pItems[i].getItemDetail() != null
					&& !StringUtils.equals("0", pItems[i].getItemDetail().getGrossEffPrice())) {
				isPaidVasExist = true;
			}
		}	
		return isPaidVasExist;
	}
	
	private Set<ItemDetailProfileLtsDTO> getVasItems(List<OfferDetailProfileLtsDTO> pOfferList, Set<ItemDetailProfileLtsDTO> pSrvItemSet) {
		
		List<OfferDetailProfileLtsDTO> expiredOfferList = new ArrayList<OfferDetailProfileLtsDTO>();
		
		for (int i=0; i < pOfferList.size(); ++i) {
			if (pOfferList.get(i).getExpiredMonths() > 0) {
				expiredOfferList.add(pOfferList.get(i));
			}
		}
		for (int i=0; i < expiredOfferList.size(); ++i) {
			pOfferList.remove(expiredOfferList.get(i));
		}
		Set<ItemDetailProfileLtsDTO> vasItemSet = new HashSet<ItemDetailProfileLtsDTO>();
		
		if (expiredOfferList.size() > 0) {
			vasItemSet.addAll(this.getVasTpItems(expiredOfferList, LtsConstant.ITEM_LTS_VTP, LtsConstant.ITEM_TYPE_EXPIRED_VAS));
		}
		OfferItemRelationDTO vasOfferItemRelation = new OfferItemRelationDTO();
		ItemDetailProfileLtsDTO itemProfile = null;
		
		try {
			 while(vasOfferItemRelation != null && pOfferList.size() > 0) {
				vasOfferItemRelation = this.offerItemMappingDAO.getVasOfferItemMapping(pOfferList.toArray(new OfferDetailProfileLtsDTO[pOfferList.size()]));
				
				if (vasOfferItemRelation != null) {
					itemProfile = this.creatItemByRelation(pOfferList, vasOfferItemRelation, LtsConstant.ITEM_TYPE_VAS);
					this.removeOfferFromList(pOfferList, itemProfile.getOffers());
					vasItemSet.add(itemProfile);
				}
			}
		} catch (DAOException e) {
			logger.error("Fail to get vas offer item mapping.\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to get vas offer item mapping.", e);
		}
		vasItemSet.addAll(this.getVasTpItems(pOfferList, LtsConstant.ITEM_LTS_VAS, LtsConstant.ITEM_TYPE_VAS));
		
		if (!vasItemSet.isEmpty()) {
			setDuplexVasInd(Lists.newArrayList(vasItemSet));
			setForceRetainInd(Lists.newArrayList(vasItemSet));
		}
		
		return vasItemSet;
	}
	
	private Collection<ItemDetailProfileLtsDTO> getVasTpItems(List<OfferDetailProfileLtsDTO> pTpOfferList, String pItemTypeLkup, String pItemType) {

		OfferItemRelationDTO[] vasTpRelations = null;
		Set<ItemDetailProfileLtsDTO> vasTpItemSet = new HashSet<ItemDetailProfileLtsDTO>();
		try {
			vasTpRelations = this.offerItemMappingDAO.getVasTpOfferItem(pTpOfferList.toArray(new OfferDetailProfileLtsDTO[pTpOfferList.size()]), pItemTypeLkup);
		} catch (DAOException e) {
			throw new AppRuntimeException("Fail to get vas TP offer item mapping.", e);
		}
		for (int i=0; vasTpRelations!=null && i<vasTpRelations.length; ++i) {
			vasTpItemSet.add(this.creatItemByRelation(pTpOfferList, vasTpRelations[i], pItemType));
		}
		return vasTpItemSet;
	}

	protected ItemDetailProfileLtsDTO creatItemByRelation(List<OfferDetailProfileLtsDTO> pOfferList, OfferItemRelationDTO pRelation, String itemType) {
		
		ItemDetailProfileLtsDTO item = super.creatItemByRelation(pOfferList, pRelation, itemType);
		
		if (StringUtils.equals("Y", pRelation.getTermExistCdInd())) {
			OfferDetailProfileLtsDTO dummyOffer = new OfferDetailProfileLtsDTO();
			dummyOffer.setOfferType("DUMMY");
			LookupItemDTO[] deviceLkup = (LookupItemDTO[])this.deviceCodeLkupCacheService.get(CodeLkupCacheService.WILD_CARDS);
			OfferActionLtsDTO action = null;
			
			for (int i=0; deviceLkup!=null && i<deviceLkup.length; ++i) {
				action = new OfferActionLtsDTO();
				action.setAction(LtsConstant.OFFER_ACTION_TERMINATE);
				action.setToProd(((String)deviceLkup[i].getItemValue()).toUpperCase());
				dummyOffer.addOfferAction(action);
			}
			item.addOffer(dummyOffer);
		}
		return item;
	}
	
	private ItemDetailProfileLtsDTO creatItemByRelationOfferCode(List<OfferDetailProfileLtsDTO> pOfferList, OfferItemRelationDTO pRelation, String itemType) {
		
		ItemDetailProfileLtsDTO item = new ItemDetailProfileLtsDTO();
		OfferTypeDTO[] offerTypes = pRelation.getOffers();
		this.dozerMapper.map(pRelation, item);
		item.setItemType(itemType);
		item.setItemNature(pRelation.getItemType());
		item.setTpPremium(pRelation.getTpPremium());
		
		for (int i=0; offerTypes!=null && i<offerTypes.length; ++i) {
			for (int j=0; j<pOfferList.size(); ++j) {
				if (StringUtils.equals(offerTypes[i].getOfferCd(), pOfferList.get(j).getOfferSubCd())) {
					pOfferList.get(j).setOfferType(offerTypes[i].getOfferType());
					
					if (StringUtils.equals(offerTypes[i].getOfferType(), LtsConstant.OFFER_TYPE_TERM_PLAN)
							|| StringUtils.equals(offerTypes[i].getOfferType(), LtsConstant.OFFER_TYPE_MIRROR_PLAN)
							|| StringUtils.equals(offerTypes[i].getOfferType(), LtsConstant.OFFER_TYPE_EXPIRED_MIRROR_PLAN)) {
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
		if (StringUtils.equals("Y", pRelation.getTermExistCdInd())) {
			OfferDetailProfileLtsDTO dummyOffer = new OfferDetailProfileLtsDTO();
			dummyOffer.setOfferType("DUMMY");
			LookupItemDTO[] deviceLkup = (LookupItemDTO[])this.deviceCodeLkupCacheService.get(CodeLkupCacheService.WILD_CARDS);
			OfferActionLtsDTO action = null;
			
			for (int i=0; deviceLkup!=null && i<deviceLkup.length; ++i) {
				action = new OfferActionLtsDTO();
				action.setAction(LtsConstant.OFFER_ACTION_TERMINATE);
				action.setToProd(((String)deviceLkup[i].getItemValue()).toUpperCase());
				dummyOffer.addOfferAction(action);
			}
			item.addOffer(dummyOffer);
		}
		return item;
	}
	
	public String getEyeDeviceType(ServiceDetailLtsDTO pSrvDtlLts) {
		
		if (pSrvDtlLts == null) {
			return null;
		}
		ItemDetailLtsDTO[] items = pSrvDtlLts.getItemDtls();
		String srvBasketId = null;
		
		for (int i=0; items!=null && i<items.length; ++i) {
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_PLAN, items[i].getItemType())) {
				srvBasketId = items[i].getBasketId();
				break;
			}
		}
		return this.ltsBasketService.getBasket(srvBasketId, LtsConstant.LOCALE_ENG, "RP_PROMOT").getType();
	}
	
	public boolean isExpiredItem(ItemDetailProfileLtsDTO pItemProfile) {
		return StringUtils.equals(LtsConstant.ITEM_TYPE_EXPIRED_SERVICE, pItemProfile.getItemType()) 
				|| StringUtils.equals(LtsConstant.ITEM_TYPE_EXPIRED_VAS, pItemProfile.getItemType());
	}
	
	public String[] getOffersByItemId(String pItemId) {
		
		try {
			return this.offerItemMappingDAO.getOffersByItemId(pItemId);
		} catch (DAOException e) {
			logger.error("Fail to getOffersByItemId ItemID: ." + pItemId + "\n" + e.getMessage(), e);
			throw new AppRuntimeException("Fail to getOffersByItemId.", e);
		}
	}
	
	public void updateItemActionByPurchases(ItemDetailProfileLtsDTO[] pItemProfiles, String[] pInItemIds, String pToProd) {
		
		List<String> inOfferIdList = new ArrayList<String>();
		String[] offers = null;
		
		for (int i=0; pInItemIds!=null && i<pInItemIds.length; ++i) {
			offers = this.getOffersByItemId(pInItemIds[i]);
			
			if (ArrayUtils.isNotEmpty(offers)) {
				inOfferIdList.addAll(Arrays.asList(offers));
			}
		}
		if (inOfferIdList.size() == 0) {
			return;
		}
		List<String> inPsefCdList = this.offerProfileLtsService.getPsefCdByOfferId(inOfferIdList);
		
		if (inPsefCdList == null || inPsefCdList.size() == 0) {
			return;
		}
		Set<String> inPsefSet = new HashSet<String>(inPsefCdList);
		OfferDetailProfileLtsDTO[] profileOffers = null;
		ItemActionLtsDTO itemAction = null;
		String[] psefs = null;
		
		for (int i=0; pItemProfiles!=null && i<pItemProfiles.length; ++i) {
			
			if (!StringUtils.equals(LtsConstant.ITEM_TYPE_SERVICE, pItemProfiles[i].getItemType())
					&& !StringUtils.equals(LtsConstant.ITEM_TYPE_VAS, pItemProfiles[i].getItemType())) {
				continue;
			}
			
			itemAction = pItemProfiles[i].getItemActionByToProd(pToProd);
				
			if (itemAction == null ) {
				itemAction = new ItemActionLtsDTO();
				itemAction.setToProd(pToProd.toUpperCase());
				itemAction.setAction(LtsConstant.IO_IND_SPACE);
				pItemProfiles[i].addItemAction(itemAction);
			}
			if (StringUtils.equals(LtsConstant.IO_IND_SPACE, itemAction.getAction())) {
				profileOffers = pItemProfiles[i].getOffers();
				
				for (int k=0; profileOffers!=null && k<profileOffers.length; ++k) {
					psefs = profileOffers[k].getPsefs();
					
					for (int l=0; psefs != null && l<psefs.length; ++l) {
						if (inPsefSet.contains(psefs[l])) {
							itemAction.setAction(LtsConstant.IO_IND_OUT);
							
							if (pItemProfiles[i].getTpExpiredMonths() <= 0) {
								itemAction.setPenaltyHandle(LtsConstant.OFFER_HANDLE_AUTO_WAIVE);	
							} else {
								itemAction.setPenaltyHandle(null);
							}
							break;
						}
					}
				}
			}
		}
	}

	public void setForceRetainInd(List<ItemDetailProfileLtsDTO> profileItemList) {
		if (profileItemList == null || profileItemList.isEmpty()) {
			return;
		}
		
		for (ItemDetailProfileLtsDTO profileItem : profileItemList) {
			OfferDetailProfileLtsDTO[] offerDetailProfiles = profileItem.getOffers();
			if (ArrayUtils.isEmpty(offerDetailProfiles)) {
				continue;
			}
			
			for (OfferDetailProfileLtsDTO offerDetailProfile : offerDetailProfiles) {
				List<BomAttbDTO> bomAttbList = this.offerProfileLtsService.getBomAttbByOfferId(offerDetailProfile.getOfferId(), null);
				if (bomAttbList != null && !bomAttbList.isEmpty())  {
					for (BomAttbDTO bomAttb : bomAttbList) {
						if (LtsConstant.BOM_ATTB_TYPE_FORCE_RETAIN.equalsIgnoreCase(bomAttb.getAttbTypeDesc())) {
							profileItem.setForceRetain(true);
							break;
						}
					}
				}
			}
		}
	}
	
	public void setItemOfferMappingDao(ItemOfferMappingDAO itemOfferMappingDao) {
		super.setItemOfferMappingDao(itemOfferMappingDao);
		this.offerItemMappingDAO = (OfferItemMappingDAO)itemOfferMappingDao;
	}

	public Mapper getDozerMapper() {
		return dozerMapper;
	}

	public void setDozerMapper(Mapper dozerMapper) {
		this.dozerMapper = dozerMapper;
	}

	public CodeLkupCacheService getPromotionExpireMonthAllowanceLkupCacheService() {
		return promotionExpireMonthAllowanceLkupCacheService;
	}

	public void setPromotionExpireMonthAllowanceLkupCacheService(
			CodeLkupCacheService promotionExpireMonthAllowanceLkupCacheService) {
		this.promotionExpireMonthAllowanceLkupCacheService = promotionExpireMonthAllowanceLkupCacheService;
	}

	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}

	public CodeLkupCacheService getDeviceCodeLkupCacheService() {
		return deviceCodeLkupCacheService;
	}

	public void setDeviceCodeLkupCacheService(
			CodeLkupCacheService deviceCodeLkupCacheService) {
		this.deviceCodeLkupCacheService = deviceCodeLkupCacheService;
	}

	public OfferProfileLtsService getOfferProfileLtsService() {
		return offerProfileLtsService;
	}

	public void setOfferProfileLtsService(
			OfferProfileLtsService offerProfileLtsService) {
		this.offerProfileLtsService = offerProfileLtsService;
	}
}
