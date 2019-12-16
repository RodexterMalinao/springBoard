package com.bomwebportal.lts.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ExclusiveChannelDetailDTO;
import com.bomwebportal.lts.dto.ExclusiveItemDetailDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.LtsQuotaDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.UpgradeEyeInfoDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.util.LtsConstant.NewDeviceRedemTypeAttb;

public interface LtsOfferService extends OfferService {

	List<String> getItemOfferIdList(String itemId);
	
	String getUpperApru(String itemId);
	
	List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, String pChannelId, String pApplDate);
	
	List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pChannelId, String pApplDate);
	
	List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pChannelId, String pEligibleDevice, String pApplDate);
	
	List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, String pChannelId, String pApplDate);
	
	List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pChannelId, String pApplDate);

	List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pChannelId, String pAreaCode, String pApplDate);
	
	List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, Double pArpuIncrease, String pChannelId, String pApplDate);
	
	List<ItemDetailDTO> getBasketItemList(ItemCriteriaDTO itemCriteria);
	
	List<ItemDetailDTO> getBasketNowTvItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, String pChannelId, String pApplDate);
	
	List<ItemDetailDTO> getItem(String[] pItemId, String pDisplayType, String pLocale, boolean pSelectDefault);

	List<ItemDetailDTO> getItemWithAttb(String[] pItemId, String pDisplayType, String pLocale, boolean pSelectDefault); 
	List<ItemDetailDTO> getItemWithAttb(String[] pItemId, String pDisplayType, String pLocale, boolean pSelectDefault, boolean pIgnoreEffDate); 
	
	List<ChannelGroupDetailDTO> getChannelGroupList(String pFormType, String pDeviceType, String pLocale, boolean pSelectDefault);
	
	Set<String> getNowTvSet();
	
	Map<String, String> getBundleTvMap();
	
	boolean isItemSelected(List<ItemDetailDTO> itemDetailList, String itemType);
	
	public boolean isItemSetSelected(List<ItemSetDetailDTO> itemSetDetailList, String itemType);
	
	ItemDetailDTO getItemAttb(ItemDetailDTO pItemDetail);
	
	UpgradeEyeInfoDTO getUpgradeEyeInfo(ServiceDetailProfileLtsDTO serviceProfile, String upgradeDeviceType, boolean isUpgradeToStaffPlan);
	
	String getTermPlanContractMonth(String deviceType, ItemDetailProfileLtsDTO[] pItemDetailProfile, String pLocale);
	
	String getExtendContractMonth(String deviceType, ItemDetailProfileLtsDTO[] pItemDetailProfile);
	
	List<ItemDetailDTO> getNowTvMirrItemList(String pItemType, String pLocale, boolean pSelectDefault, String pArpu, String pChannelId);
	
	List<ExclusiveChannelDetailDTO> getExclusiveChannelList(List<ChannelGroupDetailDTO> channelGroupList, String locale);
	
	List<ExclusiveItemDetailDTO> getExclusiveItemDetailList(List<ItemDetailDTO> itemDetailListA, List<ItemDetailDTO> itemDetailListB, String locale, boolean isSelectedItemOnly);
	
	ValidationResultDTO validateExclusiveItem(List<ItemDetailDTO> itemDetailListA, List<ItemDetailDTO> itemDetailListB, String locale);

	List<ExclusiveItemDetailDTO> getExclusiveItemSetDetailList(List<ItemSetDetailDTO> itemSetDetailListA, List<ItemSetDetailDTO> itemSetDetailListB, String displayType, String locale);
	
	ValidationResultDTO validateExclusiveItemSet(List<ItemSetDetailDTO> itemSetDetailListA, List<ItemSetDetailDTO> itemSetDetailListB, String displayType, String locale);
	
	ValidationResultDTO validateItemAttb(List<ItemDetailDTO> itemDetailList);
	
	boolean isAllow2L2B(String pDeviceType);
	
	ItemDetailDTO getItemAttbByItemAttbAssign(ItemDetailDTO pItemDetail);
	
	List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pBaseContractPeriod, String pChannelId, String pDisplayType, String pCampaignCd, String pApplDate);
	
	List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pBaseContractPeriod, String pChannelId, String pDisplayType, String pCampaignCd, String pApplDate, String pEligibleDocType);
	
	List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pBaseContractPeriod, String pChannelId, String pEligibleDevice, String pApplDate);
	
	List<ItemDetailDTO> getItemList(ItemCriteriaDTO itemCriteria);
	
	List<ItemDetailDTO> getVasOtherCommitmentPeriodItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pBaseContractPeriod, String pChannelId, String pDisplayType, String pCampaignCd, String pApplDate, String pOsType);

	List<ItemSetDetailDTO> getItemSetListByType(ItemSetCriteriaDTO itemSetCriteria);
	
	List<ItemSetDetailDTO> getBasketItemSetList(ItemSetCriteriaDTO itemSetCriteria);
	
	boolean isSelectedPaperBillItem(Object obj);
	
	NewDeviceRedemTypeAttb getSelectedNewDeviceItemType(OrderCaptureDTO orderCapture);
	
	boolean isSelectedNewDeviceFieldService(OrderCaptureDTO orderCapture);
	
	boolean isRenewalWithNewDevice(OrderCaptureDTO orderCapture);
	
	boolean isRenewOfferWithNewDevice(OrderCaptureDTO orderCapture);
	
	List<String> getAllItemSetItemId(List<ItemSetDetailDTO> itemSetList);
	
	List<ItemDetailDTO> filterOutItemById(List<ItemDetailDTO> itemList, List<String> itemIdList);
	
	void copyItemListItemAttbValues(List<ItemDetailDTO> oldItemList, List<ItemDetailDTO> newItemList);
	
	void copyItemAttbValues(ItemDetailDTO oldItem, ItemDetailDTO newItem);

	String getOsTypeChangePenalty(List<String> pOfferCdList);
	
	String getItemProfileOsTypeChangePenalty(ItemDetailProfileLtsDTO itemProfile);
	
	List<String> getOsType(List<String> pOfferCdList, boolean isBasketLevel);
	
	String getItemProfileOsType(ItemDetailProfileLtsDTO itemProfile);
	
	boolean isExcludeQuotaCheckUpgRet(OrderCaptureDTO order);

	boolean isExcludeQuotaCheckAcq(AcqOrderCaptureDTO order);
	
	boolean isExcludeQuotaCheck(List<ItemDetailDTO> planItemList, List<ItemSetDetailDTO> contItemSetList);
	
	List<String> getQuotaBypassPsefCodeList();

	ValidationResultDTO validateQuotaItemSetList(List<ItemSetDetailDTO> itemSetList);
	
	ValidationResultDTO validateQuotaItemSet(ItemSetDetailDTO itemSet);
	
	ValidationResultDTO validateQuotaItemList(List<ItemDetailDTO> itemList);
	
	List<String> getOutQuotaItemSetDescList(List<ItemSetDetailDTO> itemSetList);
	
	List<String> getOutQuotaItemSetDesc(ItemSetDetailDTO itemSet);
	
	List<String> getOutQuotaItemDescList(List<ItemDetailDTO> itemList);
	
	boolean validateItemQuota(ItemDetailDTO item);
	
	String validateItemQuotaNRtnStr(ItemDetailDTO item);
	
	boolean validateQuotaByProgramCd(String programCd, int num_of_quota_used);
	
	String validateQuotaByProgramCdRtnStr(String programCd, int num_of_quota_used);
	
	void addItemSetListQuotaUsed(List<ItemSetDetailDTO> itemSetList);
	
	void addItemSetQuotaUsed(ItemSetDetailDTO itemSet);
	
	void addItemListQuotaUsed(List<ItemDetailDTO> itemList);
	
	void addBasketQuotaUsed(BasketDetailDTO basketDtl);
	
	void addItemQuotaUsed(ItemDetailDTO item);
	
	void addQuotaUsedByList(List<LtsQuotaDTO> quotaList);

	List<String> getQuotaProgramCdItemSetList(List<ItemSetDetailDTO> itemSetList);
	
	List<String> getQuotaProgramCdItemSet(ItemSetDetailDTO itemSet);
	
	List<String> getQuotaProgramCdItemList(List<ItemDetailDTO> itemList);
	
	Map<String,LtsQuotaDTO> getQuotaMapByItemSetList(List<ItemSetDetailDTO> itemSetList, Map<String,LtsQuotaDTO> quotaMap);
	
	Map<String,LtsQuotaDTO> getQuotaMapByItemSet(ItemSetDetailDTO itemSet, Map<String,LtsQuotaDTO> quotaMap);
	
	Map<String,LtsQuotaDTO> getQuotaMapByItemList(List<ItemDetailDTO> itemList, Map<String,LtsQuotaDTO> quotaMap);
	
	Map<String,LtsQuotaDTO> getQuotaMapByBasket(BasketDetailDTO basketDtl, Map<String,LtsQuotaDTO> quotaMap);
	
	List<String> getOutQuotaByItemSetListInMap(List<ItemSetDetailDTO> itemSetList, Map<String,LtsQuotaDTO> quotaMap);
	
	List<String> getOutQuotaByItemSetInMap(ItemSetDetailDTO itemSet, Map<String,LtsQuotaDTO> quotaMap);
	
	List<String> getOutQuotaByItemListInMap(List<ItemDetailDTO> itemList, Map<String,LtsQuotaDTO> quotaMap);

	List<String> getOutQuotaByMap(Map<String,LtsQuotaDTO> quotaMap);
	
	String getOutQuotaByBasket(BasketDetailDTO basketDtl, Map<String,LtsQuotaDTO> quotaMap);
	
}
