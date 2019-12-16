package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.SbOfferActionLkupDAO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ChannelAttbDTO;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ExclusiveChannelDetailDTO;
import com.bomwebportal.lts.dto.ExclusiveItemDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.LtsQuotaDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.UpgradeEyeInfoDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsPaymentFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.bom.BomOfferProductLkupService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsConstant.NewDeviceRedemTypeAttb;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.util.Utility;
import com.google.common.collect.Lists;

public class LtsOfferServiceImpl extends OfferServiceImpl implements LtsOfferService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected SbOfferActionLkupDAO sbOfferActionLkupDAO;
	protected LtsProfileService ltsProfileService;
	private CodeLkupCacheService mobileNumPrefixLkupCacheService;
	private CodeLkupCacheService allow2L2BCodeLkupCacheService;
	private BomOfferProductLkupService bomOfferProductLkupService;
	private LtsCommonService ltsCommonService;
	private LtsQuotaService ltsQuotaService;
	private CodeLkupCacheService ltsQuotaBypassPsefCacheService;
	
	public BomOfferProductLkupService getBomOfferProductLkupService() {
		return bomOfferProductLkupService;
	}

	public void setBomOfferProductLkupService(
			BomOfferProductLkupService bomOfferProductLkupService) {
		this.bomOfferProductLkupService = bomOfferProductLkupService;
	}

	public SbOfferActionLkupDAO getSbOfferActionLkupDAO() {
		return sbOfferActionLkupDAO;
	}

	public void setSbOfferActionLkupDAO(SbOfferActionLkupDAO sbOfferActionLkupDAO) {
		this.sbOfferActionLkupDAO = sbOfferActionLkupDAO;
	}

	public CodeLkupCacheService getAllow2L2BCodeLkupCacheService() {
		return allow2L2BCodeLkupCacheService;
	}

	public void setAllow2L2BCodeLkupCacheService(
			CodeLkupCacheService allow2l2bCodeLkupCacheService) {
		allow2L2BCodeLkupCacheService = allow2l2bCodeLkupCacheService;
	}

	public CodeLkupCacheService getMobileNumPrefixLkupCacheService() {
		return mobileNumPrefixLkupCacheService;
	}

	public void setMobileNumPrefixLkupCacheService(
			CodeLkupCacheService mobileNumPrefixLkupCacheService) {
		this.mobileNumPrefixLkupCacheService = mobileNumPrefixLkupCacheService;
	}

	public LtsProfileService getLtsProfileService() {
		return ltsProfileService;
	}

	public void setLtsProfileService(LtsProfileService ltsProfileService) {
		this.ltsProfileService = ltsProfileService;
	}
	
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public LtsQuotaService getLtsQuotaService() {
		return ltsQuotaService;
	}

	public void setLtsQuotaService(LtsQuotaService ltsQuotaService) {
		this.ltsQuotaService = ltsQuotaService;
	}

	public CodeLkupCacheService getLtsQuotaBypassPsefCacheService() {
		return ltsQuotaBypassPsefCacheService;
	}

	public void setLtsQuotaBypassPsefCacheService(
			CodeLkupCacheService ltsQuotaBypassPsefCacheService) {
		this.ltsQuotaBypassPsefCacheService = ltsQuotaBypassPsefCacheService;
	}

	public List<String> getItemOfferIdList(String itemId) {
		try {
			return this.offerDetailDao.getItemOfferIdList(itemId);
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getItemOfferIdList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public String getUpperApru(String itemId) {
		try {
			return this.offerDetailDao.getUpperApru(itemId);
		} catch (DAOException de) {
			logger.error("Fail in LtsOdfferService.getUpperApru()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	public List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, 
			boolean pSelectDefault, boolean pGetAttrInd, String pChannelId, String pAreaCode, String pApplDate) {
		
		try {
			
			return this.offerDetailDao.getBasketItemList(pBasketId, pItemType, pLocale, pSelectDefault, pGetAttrInd, pChannelId, pApplDate);
			
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getBasketItemList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, 
			boolean pSelectDefault, boolean pGetAttrInd, String pChannelId, String pApplDate) {
		
		try {
			
			return this.offerDetailDao.getBasketItemList(pBasketId, pItemType, pLocale, pSelectDefault, pGetAttrInd, pChannelId, pApplDate);
			
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getBasketItemList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, 
			boolean pSelectDefault, Double pArpuIncrease, String pChannelId, String pApplDate) {
		
		try {
			
			return this.offerDetailDao.getBasketItemList(pBasketId, pItemType, pLocale, pSelectDefault, pArpuIncrease, pChannelId, pApplDate);
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getBasketItemList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getVasOtherCommitmentPeriodItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pBaseContractPeriod, String pChannelId, String pDisplayType, String pCampaignCd, String pApplDate, String pOsType) {
		try {
			
			return this.offerDetailDao.getVasOtherCommitmentPeriodItemList(pItemType, pLocale, pSelectDefault, pGetAttrInd, pBaseContractPeriod, pChannelId, pDisplayType, pCampaignCd, pApplDate, pOsType);
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getVasOtherCommitmentPeriodItemList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, String pChannelId, String pApplDate) {
		return this.getItemList(pItemType, pLocale, pSelectDefault, false, pChannelId, pApplDate);
	}
	
	public List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pChannelId, String pApplDate) {
		try {
			
			return this.offerDetailDao.getItemList(pItemType, pLocale, pSelectDefault, pGetAttrInd, pChannelId, pApplDate);
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getItemDetailList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pChannelId, String pEligibleDevice, String pApplDate) {
		return getItemList(pItemType, pLocale, pSelectDefault, pGetAttrInd, null, pChannelId, pEligibleDevice, pApplDate);
	}
	
	public List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pBaseContractPeriod, String pChannelId, String pEligibleDevice, String pApplDate) {
		try {
			
			return this.offerDetailDao.getItemList(pItemType, pLocale, pSelectDefault, pGetAttrInd, pBaseContractPeriod, pChannelId, LtsConstant.DISPLAY_TYPE_ITEM_SELECT, null, pEligibleDevice, pApplDate, null);
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getItemList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pBaseContractPeriod, String pChannelId, String pDisplayType, String pCampaignCd, String pApplDate) {
		return getItemList(pItemType, pLocale, pSelectDefault, pGetAttrInd, pBaseContractPeriod, pChannelId, pDisplayType, pCampaignCd, pApplDate, null);
	}
		
	public List<ItemDetailDTO> getItemList(String pItemType, String pLocale, boolean pSelectDefault, boolean pGetAttrInd, String pBaseContractPeriod, String pChannelId, String pDisplayType, String pCampaignCd, String pApplDate, String pEligibleDocType) {
		try {
			
			return this.offerDetailDao.getItemList(pItemType, pLocale, pSelectDefault, pGetAttrInd, pBaseContractPeriod, pChannelId, pDisplayType, pCampaignCd, null, pApplDate, pEligibleDocType);
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getItemList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getItemList(ItemCriteriaDTO itemCriteria){
		try {
			return this.offerDetailDao.getItemList(itemCriteria);
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getItemList(ItemCriteriaDTO itemCriteria)", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getBasketItemList(ItemCriteriaDTO itemCriteria){
		try {
			return this.offerDetailDao.getBasketItemList(itemCriteria);
					
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getBasketItemList(ItemCriteriaDTO itemCriteria)", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getBasketItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, String pChannelId, String pApplDate) {
		try {
			
			return this.offerDetailDao.getBasketItemList(pBasketId, pItemType, pLocale, pSelectDefault, pChannelId, pApplDate);
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getBasketItemList()");
			throw new RuntimeException(de.getCustomMessage());
		} 
	}
	
	public List<ItemDetailDTO> getBasketNowTvItemList(String pBasketId, String pItemType, String pLocale, boolean pSelectDefault, String pChannelId, String pApplDate) {
		try {
			
			return this.offerDetailDao.getBasketNowTvItemList(pBasketId, pItemType, pLocale, pSelectDefault, pChannelId, pApplDate);
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getNowTvItemList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	public List<ItemDetailDTO> getItemWithAttb(String[] pItemId, String pDisplayType, String pLocale, boolean pSelectDefault) {
		return getItemWithAttb(pItemId, pDisplayType, pLocale, pSelectDefault, false);
	}
	
	public List<ItemDetailDTO> getItemWithAttb(String[] pItemId, String pDisplayType, String pLocale, boolean pSelectDefault, boolean pIgnoreEffDate) {
		try {
			
			List<ItemDetailDTO> itemDetailList = offerDetailDao.getItem(pItemId, pDisplayType, pLocale, pSelectDefault, pIgnoreEffDate);
			
			if (itemDetailList == null) {
				return null;
			}
			
			for (ItemDetailDTO itemDetail : itemDetailList) {
				if(LtsConstant.ITEM_TYPE_MYHKT_BILL.equals(itemDetail.getItemType())){
					getItemAttbByItemAttbAssign(itemDetail);
				}else{
					getItemAttb(itemDetail);
				}
			}
			
			return itemDetailList;
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOdfferService.getItem()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemDetailDTO> getItem(String[] pItemId, String pDisplayType, String pLocale, boolean pSelectDefault) {
		try {
			
			return this.offerDetailDao.getItem(pItemId, pDisplayType, pLocale, pSelectDefault);
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOdfferService.getItem()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ChannelGroupDetailDTO> getChannelGroupList(String pFormType, String pDeviceType, String pLocale, boolean pSelectDefault) {

	    List<ChannelGroupDetailDTO> channelGroupList = new ArrayList<ChannelGroupDetailDTO>();
	    List<ChannelDetailDTO> channelList = new ArrayList<ChannelDetailDTO>();
	    List<ChannelAttbDTO> channelAttbs = new ArrayList<ChannelAttbDTO>();
		try{
			channelGroupList = this.offerDetailDao.getChannelGroupList(pFormType, pDeviceType, pLocale);
	    	
			for (int i = 0; channelGroupList != null && i < channelGroupList.size(); i++) {
				channelList = this.offerDetailDao.getGroupChannel(channelGroupList.get(i).getChannelGroupId(), pLocale, pSelectDefault);
				for (int j = 0; channelList !=  null & j < channelList.size();  j++) {
					channelAttbs = this.getChannelAttb(channelList.get(j).getChannelId(), pLocale);
					channelList.get(j).setChannelAttbs(channelAttbs.toArray(new ChannelAttbDTO[channelAttbs.size()]));
				}
				channelGroupList.get(i).setChannelDetails(channelList.toArray(new ChannelDetailDTO[0]));
			}
		return channelGroupList;
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOdfferService.getChannelGroupList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public boolean isItemSelected(List<ItemDetailDTO> itemDetailList, String itemType) {
		if (itemDetailList == null || itemDetailList.isEmpty() ) {
			return false;
		}
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (StringUtils.isNotEmpty(itemType)
					&& !StringUtils.equals(itemType, itemDetail.getItemType())) {
				continue;
			}
			if (itemDetail.isSelected()) {
				return true;
			}
		}
		return false;
	}

	public boolean isItemSetSelected(List<ItemSetDetailDTO> itemSetDetailList, String itemType) {
		if (itemSetDetailList == null || itemSetDetailList.isEmpty() ) {
			return false;
		}
		
		for(ItemSetDetailDTO itemSetDetail : itemSetDetailList){
			if(itemSetDetail.getItemDetails() == null || itemSetDetail.getItemDetails().length == 0){
				continue;
			}
			
			if(isItemSelected(Arrays.asList(itemSetDetail.getItemDetails()), itemType)){
				return true;
			}
		}
		return false;
	}
	
	public Map<String, String> getBundleTvMap() {

	   try {
			 return this.offerDetailDao.getBundleTvMap();
		
		   } catch (DAOException de) {
			 logger.error("Fail in LtsOdfferService.getItem()", de);
			 throw new RuntimeException(de.getCustomMessage());
		   }
	}
		
	public Set<String> getNowTvSet() {
		try {
			return this.offerDetailDao.getNowTvSet();
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getNowTvSet()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public ItemDetailDTO getItemAttb(ItemDetailDTO pItemDetail) {
		pItemDetail.setItemAttbs(this.getItemAttb(pItemDetail.getItemId()));
		return pItemDetail;
	}
	
	
	
	public UpgradeEyeInfoDTO getUpgradeEyeInfo(ServiceDetailProfileLtsDTO serviceProfile, 
			String upgradeDeviceType, boolean isUpgradeToStaffPlan) {
		
		try {
			
			ItemDetailProfileLtsDTO profileTpItem = LtsSbOrderHelper.getProfileServiceTermPlan(serviceProfile);
			
			if (profileTpItem == null) {
				return null;
			}
			
			String conditionEndDate = profileTpItem.getConditionEffEndDate();
			String tpCatg = "N/A";
			String 	existSrvType = StringUtils.defaultIfEmpty(serviceProfile.getExistEyeType(), "DEL");
			boolean isExistStaffPlan = serviceProfile.isExistStaffPlan();
			
			// eye2a second contract with same basket offer 
			if (LtsConstant.EYE_TYPE_EYE2A.equalsIgnoreCase(existSrvType) 
					&& LtsSbOrderHelper.isSecondContract(serviceProfile)) {
				tpCatg = LtsConstant.TP_CATG_RETENTION;
			}
			
			// DEL Premium TP
			if (LtsConstant.DAT_CD_DEL.equals(existSrvType)) {
				ItemDetailDTO itemDetail = this.offerDetailDao.getTpVasItemDetail(profileTpItem.getItemId(), LtsConstant.LOCALE_ENG);
				if (itemDetail != null 
						&& StringUtils.equalsIgnoreCase(itemDetail.getIsPremiumTp(), "Y")
						&& StringUtils.isNotEmpty(conditionEndDate) 
						&& !(LtsDateFormatHelper.getDiffMonth(conditionEndDate) < 0)){
					tpCatg = LtsConstant.TP_CATG_PREMIUM;
				}
			}
			
			return this.offerDetailDao.getUpdateEyeInfo(
					existSrvType, upgradeDeviceType,
					StringUtils.isEmpty(conditionEndDate) ? "0" : String.valueOf(LtsDateFormatHelper
							.getDiffMonth(conditionEndDate)),
					isExistStaffPlan, isUpgradeToStaffPlan, tpCatg);

		} catch (DAOException de) {
			logger.error("Fail in getUpgradeEyeInfo()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public String getExtendContractMonth(String deviceType, ItemDetailProfileLtsDTO[] pItemDetailProfile) {
		ItemDetailDTO itemDetail = null;
		String extendContractMth = "0";
		String conditionEndDate = null;
		try {
			for (int i = 0; pItemDetailProfile != null && i < pItemDetailProfile.length; i++){
				conditionEndDate = pItemDetailProfile[i].getConditionEffEndDate();
				if (pItemDetailProfile[i].getItemType() == LtsConstant.ITEM_TYPE_SERVICE) {
					itemDetail = pItemDetailProfile[i].getItemDetail();
					if (itemDetail != null && StringUtils.equalsIgnoreCase(itemDetail.getIsPremiumTp(), "Y")
							&& StringUtils.isNotEmpty(conditionEndDate)){
						if (!(LtsDateFormatHelper.getDiffMonth(conditionEndDate) < 0)){
							extendContractMth = this.offerDetailDao.getExtendContractMth(deviceType, String.valueOf(LtsDateFormatHelper.getDiffMonth(conditionEndDate)));
						}
					}
				}
			}
		} catch (DAOException de) {
			logger.error("Fail in OfferProfileLtsService.getTermPlanContractMonth()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
		return extendContractMth;
	}
	
	
	public String getTermPlanContractMonth(String deviceType, ItemDetailProfileLtsDTO[] pItemDetailProfile, String pLocale) {
		String contractMth = LtsConstant.CONTRACT_MONTH;
		String conditionEndDate = null;
		ItemDetailDTO itemDetail = null;
		try {
			for (int i = 0; pItemDetailProfile != null && i < pItemDetailProfile.length; i++){
				conditionEndDate = pItemDetailProfile[i].getConditionEffEndDate();
			
				if (pItemDetailProfile[i].getItemType() == LtsConstant.ITEM_TYPE_SERVICE) {
					itemDetail = this.offerDetailDao.getTpVasItemDetail(pItemDetailProfile[i].getItemId(), pLocale);
					if (itemDetail != null && StringUtils.equalsIgnoreCase(itemDetail.getIsPremiumTp(), "Y")
							&& StringUtils.isNotEmpty(conditionEndDate)){
						if (!(LtsDateFormatHelper.getDiffMonth(conditionEndDate) < 0)){
							contractMth = this.offerDetailDao.getContractMth(deviceType, String.valueOf(LtsDateFormatHelper.getDiffMonth(conditionEndDate)));
						}
					}
				}
			}
	
		} catch (DAOException de) {
			logger.error("Fail in OfferProfileLtsService.getTermPlanContractMonth()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
		return contractMth;
	}
	
	public List<ItemDetailDTO> getNowTvMirrItemList(String pItemType, String pLocale, boolean pSelectDefault, String pArpu, String pChannelId){
		try {
			
			return this.offerDetailDao.getNowTvMirrItemList(pItemType, pLocale, pSelectDefault, pArpu, pChannelId);
		
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getItemDetailList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ExclusiveChannelDetailDTO> getExclusiveChannelList(
			List<ChannelGroupDetailDTO> channelGroupList, String locale) {
		try {
			return this.offerDetailDao.getExclusiveChannelList(channelGroupList, locale);
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getExclusiveChannelList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	
	public List<ExclusiveItemDetailDTO> getExclusiveItemDetailList(
			List<ItemDetailDTO> itemDetailListA,
			List<ItemDetailDTO> itemDetailListB, String locale,
			boolean isSelectedItemOnly) {
		try {
			return this.offerDetailDao.getExclusiveItemList(itemDetailListA, itemDetailListB, locale, isSelectedItemOnly);
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getExclusiveItemDetailList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public ValidationResultDTO validateExclusiveItem(List<ItemDetailDTO> itemDetailListA, List<ItemDetailDTO> itemDetailListB, String locale) {
		
		List<ExclusiveItemDetailDTO> exclusiveItemDetailList = getExclusiveItemDetailList(itemDetailListA, itemDetailListB, locale, true);

		if (exclusiveItemDetailList == null || exclusiveItemDetailList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
		
		List<String> errorMsgList = new ArrayList<String>();
		
		for (ExclusiveItemDetailDTO exclusiveItemDetail : exclusiveItemDetailList) {
			
			if (StringUtils.isNotBlank(exclusiveItemDetail.getItemADesc()) && StringUtils.isNotBlank(exclusiveItemDetail.getItemBDesc())) {
				errorMsgList.add("Cannot select <br /><br /> "
						+ exclusiveItemDetail.getItemADesc().split("<br />")[0]
						+ " <br /><br /> and <br /><br /> "
						+ exclusiveItemDetail.getItemBDesc().split("<br />")[0] + " <br /><br /> together.");
			}
			
			
		}
		
		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null); 
		}
		
		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}

	public List<ExclusiveItemDetailDTO> getExclusiveItemSetDetailList(
			List<ItemSetDetailDTO> itemSetDetailListA, List<ItemSetDetailDTO> itemSetDetailListB, 
			String displayType, String locale) {
		try {
			return this.offerDetailDao.getExclusiveItemSetList(itemSetDetailListA, itemSetDetailListB, displayType, locale);
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getExclusiveItemSetDetailList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public ValidationResultDTO validateExclusiveItemSet(List<ItemSetDetailDTO> itemSetDetailListA, 
			List<ItemSetDetailDTO> itemSetDetailListB, String displayType, String locale) {
		
		List<ExclusiveItemDetailDTO> exclusiveItemSetDetailList = 
			getExclusiveItemSetDetailList(itemSetDetailListA, itemSetDetailListB, displayType, locale);

		if (exclusiveItemSetDetailList == null || exclusiveItemSetDetailList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
		
		List<String> errorMsgList = new ArrayList<String>();
		
		for (ExclusiveItemDetailDTO exclusiveItemSetDetail : exclusiveItemSetDetailList) {
			
			if (StringUtils.isNotBlank(exclusiveItemSetDetail.getItemADesc()) 
					&& StringUtils.isNotBlank(exclusiveItemSetDetail.getItemBDesc())) {
				errorMsgList.add("Cannot select <br /><br /> "
						+ exclusiveItemSetDetail.getItemADesc().split("<br />")[0]
						+ " <br /><br /> and <br /><br /> "
						+ exclusiveItemSetDetail.getItemBDesc().split("<br />")[0] + " <br /><br /> together.");
			}
			
			
		}
		
		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null); 
		}
		
		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}
	
	public void validateItemAttbDuplexBNum(ItemAttbDTO itemAttb,  List<String> errorMsgList) {
		
		if (StringUtils.isEmpty(itemAttb.getAttbValue())) {
			errorMsgList.add("Please input service number.");
			return;
		}
		
		if(!Utility.validatePhoneNum(itemAttb.getAttbValue())) {
			errorMsgList.add("Invalid service number format.");
			return;
		}
		
		String serviceNum = StringUtils.leftPad(itemAttb.getAttbValue(), 12, '0');
		String inventStatus = ltsProfileService.getServiceInventoryStatus(serviceNum, LtsConstant.SERVICE_TYPE_TEL);
		
		if (!(StringUtils.equals(LtsConstant.INVENT_STS_RESERVED, inventStatus) || StringUtils.equals(LtsConstant.INVENT_STS_WORKING, inventStatus))) {
			errorMsgList.add("Service number is not working or reserved.");
			return;
		}	
	}
	
	public void validateItemAttbMobileNum(ItemAttbDTO itemAttb, List<String> errorMsgList) {
		
		if (StringUtils.isEmpty(itemAttb.getAttbValue())) {
			errorMsgList.add("Please input mobile number.");
			return;
		}

		if(!Utility.validatePhoneNum(itemAttb.getAttbValue())) {
			errorMsgList.add("Invalid mobile number format.");
			return;
		}
		
		try {
			LookupItemDTO[] mobilePrefix = mobileNumPrefixLkupCacheService.getCodeLkupDAO().getCodeLkup();
			
			boolean validPrefix = false;
			
			for(LookupItemDTO item: mobilePrefix){
				if(StringUtils.startsWith(itemAttb.getAttbValue(), item.getItemKey())){
					validPrefix = true;
				}
			}
			
			if (!validPrefix) {
				errorMsgList.add("Invalid mobile number format.");
				return;
			}
			
		}
		catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public void validateItemAttbMandatory(ItemAttbDTO itemAttb, List<String> errorMsgList) {
		
		if (StringUtils.isEmpty(itemAttb.getAttbValue())) {
			if("SELECT".equals(itemAttb.getInputMethod())){
				errorMsgList.add("Please select " + itemAttb.getAttbDesc() + ".");
			}else{
				errorMsgList.add("Please input " + itemAttb.getAttbDesc() + ".");
			}
			return;
		}
		else {
			validateItemAttbLength(itemAttb, errorMsgList);
		}
		
	}
	
	public void validateItemAttbLength(ItemAttbDTO itemAttb, List<String> errorMsgList) {
		if (StringUtils.isEmpty(itemAttb.getAttbValue())) {
			return;
		}
		else if (itemAttb.getMaxLength() != 0 && itemAttb.getAttbValue().length() > itemAttb.getMaxLength()){
			errorMsgList.add("Invalid " + itemAttb.getAttbDesc() + " format (Max Length : " + itemAttb.getMaxLength() + ")." );
			return;
		}
		else if (itemAttb.getMinLength() != 0 && itemAttb.getAttbValue().length() < itemAttb.getMinLength()) {
			errorMsgList.add("Invalid " + itemAttb.getAttbDesc() + " format (Min Length : " + itemAttb.getMinLength() + ")." );
			return;
		}
	}
	
	public ValidationResultDTO validateItemAttb(List<ItemDetailDTO> itemDetailList) {
		if (itemDetailList == null || itemDetailList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
		
		List<String> errorMsgList = new ArrayList<String>();
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (!itemDetail.isSelected() || ArrayUtils.isEmpty(itemDetail.getItemAttbs())) {
				continue;
			}
			
			for (ItemAttbDTO itemAttb : itemDetail.getItemAttbs()) {
				if (StringUtils.equals(itemAttb.getVisualInd(), "N")){
					continue;
				}

				if (StringUtils.equals(LtsConstant.ITEM_ATTB_FORMAT_TEXT, itemAttb.getInputFormat())) {
					validateItemAttbLength(itemAttb, errorMsgList);
				}
				
				if (StringUtils.equals(LtsConstant.ITEM_ATTB_FORMAT_DUPLEX_B_NUM, itemAttb.getInputFormat())) {
					validateItemAttbDuplexBNum(itemAttb, errorMsgList);
				}
				
				else if (StringUtils.equals(LtsConstant.ITEM_ATTB_FORMAT_MANDATORY, itemAttb.getInputFormat())
						|| StringUtils.equals(LtsConstant.ITEM_ATTB_FORMAT_CONTACT_NAME, itemAttb.getInputFormat())
						|| StringUtils.equals(LtsConstant.ITEM_ATTB_FORMAT_DATE, itemAttb.getInputFormat())) {
					validateItemAttbMandatory(itemAttb, errorMsgList);
				}
				
				else if (StringUtils.equals(LtsConstant.ITEM_ATTB_FORMAT_MOBILE_NUM, itemAttb.getInputFormat())) {
					validateItemAttbMobileNum(itemAttb, errorMsgList);
				}
				
				else if (StringUtils.equals(LtsConstant.ITEM_ATTB_FORMAT_NUMBER, itemAttb.getInputFormat())) {
					validateItemAttbLength(itemAttb, errorMsgList);
					if(!StringUtils.isNumeric(itemAttb.getAttbValue())){
						errorMsgList.add("Invalid " + itemAttb.getAttbDesc() + " format (Numberic)." );
					}
				}
				
				else if (StringUtils.equals(LtsConstant.ITEM_ATTB_FORMAT_CONTACT_NUM, itemAttb.getInputFormat())) {
					validateItemAttbMandatory(itemAttb, errorMsgList);
					validateItemAttbLength(itemAttb, errorMsgList);
					if(!StringUtils.isNumeric(itemAttb.getAttbValue())){
						errorMsgList.add("Invalid " + itemAttb.getAttbDesc() + " format (Numberic)." );
					}
					
					boolean isMobNum = ltsCommonService.isMobileNumPrefix(itemAttb.getAttbValue());
					boolean isFixedLineNum = ltsCommonService.isFixLineNumPrefix(itemAttb.getAttbValue(), true);
					if(!isMobNum && !isFixedLineNum){
						errorMsgList.add("Invalid " + itemAttb.getAttbDesc() + "." );
					}
					
				}
				
				else if (StringUtils.equals(LtsConstant.ITEM_ATTB_FORMAT_REMARK, itemAttb.getInputFormat())) {
					validateItemAttbLength(itemAttb, errorMsgList);
				}
			}
			
		}
		
		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null); 
		}
		
		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
		
	}
	
	public boolean isAllow2L2B(String pDeviceType){
		try {
			List<LookupItemDTO> allow2L2BCodeList = Arrays.asList(allow2L2BCodeLkupCacheService.getCodeLkupDAO().getCodeLkup());
			if (allow2L2BCodeList != null && !allow2L2BCodeList.isEmpty()) {
				for (LookupItemDTO allow2L2BCode : allow2L2BCodeList) {
					if (StringUtils.equalsIgnoreCase(pDeviceType, allow2L2BCode.getItemKey().toString())){
						return true;
					}
				}
			}
			return false;
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.isAllow2L2B()", de);
			throw new AppRuntimeException(de);
		}
	}

	public ItemDetailDTO getItemAttbByItemAttbAssign(ItemDetailDTO pItemDetail) {
		pItemDetail.setItemAttbs(this.getItemAttbByItemAttbAssign((pItemDetail.getItemId())));
		return pItemDetail;
	}

	public List<ItemSetDetailDTO> getItemSetListByType(ItemSetCriteriaDTO itemSetCriteria) {
		try {
			return offerDetailDao.getItemSetListByType(itemSetCriteria);
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getItemSetListByType()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public List<ItemSetDetailDTO> getBasketItemSetList(ItemSetCriteriaDTO itemSetCriteria) {
		try {
			return offerDetailDao.getBasketItemSetList(itemSetCriteria);
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getBasketItemSetList()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public boolean isSelectedPaperBillItem(Object obj) {
		if (obj == null) {
			return false;
		}
		// For SBU & SBR Order
		if (obj instanceof LtsPaymentFormDTO) {
			return isItemSelected(((LtsPaymentFormDTO) obj).getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL)
					|| isItemSelected(((LtsPaymentFormDTO) obj).getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE)
					|| isItemSelected(((LtsPaymentFormDTO) obj).getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE)
					|| isItemSelected(((LtsPaymentFormDTO) obj).getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL_BR);
		}
		
		// For SBA Order
		if (obj instanceof LtsAcqBillMediaBillLangFormDTO) {
			if (((LtsAcqBillMediaBillLangFormDTO) obj).getPrimaryBillMediaDtl() != null && 
					((LtsAcqBillMediaBillLangFormDTO) obj).getPrimaryBillMediaDtl().getPaperBillItem() != null) {
				return ((LtsAcqBillMediaBillLangFormDTO) obj).getPrimaryBillMediaDtl().getPaperBillItem().isSelected();	
			}
		}
		
		return false;
	}
	
	//BOM2018061
	public boolean isRenewOfferWithNewDevice(OrderCaptureDTO orderCapture){
		
		if (!LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
				|| !LtsConstant.EYE_TYPE_EYE3A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			return false;
		}
		
		Set<String> allItemOfferCodeSet = new HashSet<String>();
		
		if (orderCapture.getSbOrder() != null) {
			ServiceDetailLtsDTO coreLtsService = LtsSbOrderHelper.getLtsService(orderCapture.getSbOrder());
			if (coreLtsService == null || ArrayUtils.isNotEmpty(coreLtsService.getItemDtls())) {
				for (ItemDetailLtsDTO itemDetail : coreLtsService.getItemDtls()) {
					String[] itemCodeList = getItemOfferCodes(itemDetail.getItemId());
					if (itemCodeList != null && itemCodeList.length > 0) {
						allItemOfferCodeSet.addAll(Arrays.asList(itemCodeList));
					}
				}
			}
		}else if (orderCapture.getLtsBasketServiceForm() == null 
				|| orderCapture.getLtsBasketServiceForm().getPlanItemList() == null
				|| orderCapture.getLtsBasketServiceForm().getPlanItemList().isEmpty()) {
			return false;
		}
		else {
			addOfferCodeToSetBySelectedItem(orderCapture.getLtsBasketServiceForm().getPlanItemList(), allItemOfferCodeSet);
			addOfferCodeToSetBySelectedItem(orderCapture.getLtsBasketServiceForm().getBvasItemList(), allItemOfferCodeSet);
			addOfferCodeToSetBySelectedItemSet(orderCapture.getLtsBasketServiceForm().getContItemSetList(), allItemOfferCodeSet);	
		}

		if (allItemOfferCodeSet == null || allItemOfferCodeSet.isEmpty()) {
			return false;
		}
		
		for(String offerCode: allItemOfferCodeSet){
			if(offerCode.charAt(2) == '4'){
				return true;
			}
		}
		
		return false;
	}
	
	private void addOfferCodeToSetBySelectedItem(List<ItemDetailDTO> itemDetailList, Set<String> allItemOfferCodeSet) {
		if (itemDetailList == null || itemDetailList.isEmpty()) {
			return;
		}
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (itemDetail.isSelected()) {
				String[] itemCodeList = getItemOfferCodes(itemDetail.getItemId());
				if (itemCodeList != null && itemCodeList.length > 0) {
					allItemOfferCodeSet.addAll(Arrays.asList(itemCodeList));
				}
			}
		}
	}

	private void addOfferCodeToSetBySelectedItemSet(List<ItemSetDetailDTO> itemSetDetailList, Set<String> allItemOfferCodeSet) {
		if (itemSetDetailList == null || itemSetDetailList.isEmpty()) {
			return;
		}
		for (ItemSetDetailDTO itemSetDetail : itemSetDetailList) {
			if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
				addOfferCodeToSetBySelectedItem(Lists.newArrayList(itemSetDetail.getItemDetails()), allItemOfferCodeSet);
			}
		}
	}
	
	public NewDeviceRedemTypeAttb getSelectedNewDeviceItemType(OrderCaptureDTO orderCapture) {
		//new common logic for both upg and renew order
		if (orderCapture.getSbOrder() != null) {
			ServiceDetailLtsDTO coreLtsService = LtsSbOrderHelper.getLtsService(orderCapture.getSbOrder());
			if (coreLtsService == null || ArrayUtils.isNotEmpty(coreLtsService.getItemDtls())) {
				for (ItemDetailLtsDTO itemDetail : coreLtsService.getItemDtls()) {
					if(LtsConstant.ITEM_TYPE_FIELD_SERVICE.equals(itemDetail.getItemType())){
						return NewDeviceRedemTypeAttb.FIELD_SERVICE;
					}
					if(LtsConstant.ITEM_TYPE_SELF_PICKUP.equals(itemDetail.getItemType())){
						return NewDeviceRedemTypeAttb.SELF_PICKUP;
					}
				}
			}
		}else if(orderCapture.getLtsBasketServiceForm() != null){
			
			List<ItemSetDetailDTO> deviceRedemItemSetList = null;
			if(LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())){
				deviceRedemItemSetList = orderCapture.getLtsBasketServiceForm().getDeviceRedemSetList();
			}else if(LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())){
				deviceRedemItemSetList = orderCapture.getLtsBasketServiceForm().getContItemSetList();
			}
			
			if(deviceRedemItemSetList != null){
				for(ItemSetDetailDTO itemSet: deviceRedemItemSetList){
					if(itemSet != null){
						for(ItemDetailDTO itemDetail: itemSet.getItemDetails()){
							if(itemDetail != null && itemDetail.isSelected()){
								if(LtsConstant.ITEM_TYPE_FIELD_SERVICE.equals(itemDetail.getItemType())){
									return NewDeviceRedemTypeAttb.FIELD_SERVICE;
								}
								if(LtsConstant.ITEM_TYPE_SELF_PICKUP.equals(itemDetail.getItemType())){
									return NewDeviceRedemTypeAttb.SELF_PICKUP;
								}
							}
						}
					}
				}
			}
		}

		return NewDeviceRedemTypeAttb.NONE;
	}
	
	public boolean isSelectedNewDeviceFieldService(OrderCaptureDTO orderCapture) {
		
		//new common logic for both upg and renew order
		if (orderCapture.getSbOrder() != null) {
			ServiceDetailLtsDTO coreLtsService = LtsSbOrderHelper.getLtsService(orderCapture.getSbOrder());
			if (coreLtsService == null || ArrayUtils.isNotEmpty(coreLtsService.getItemDtls())) {
				for (ItemDetailLtsDTO itemDetail : coreLtsService.getItemDtls()) {
					if(LtsConstant.ITEM_TYPE_FIELD_SERVICE.equals(itemDetail.getItemType())){
						return true;
					}
					if(LtsConstant.ITEM_TYPE_SELF_PICKUP.equals(itemDetail.getItemType())){
						return false;
					}
				}
			}
		}else if(orderCapture.getLtsBasketServiceForm() != null){
			
			List<ItemSetDetailDTO> deviceRedemItemSetList = null;
			if(LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())){
				deviceRedemItemSetList = orderCapture.getLtsBasketServiceForm().getDeviceRedemSetList();
			}else if(LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())){
				deviceRedemItemSetList = orderCapture.getLtsBasketServiceForm().getContItemSetList();
			}
			
			if(deviceRedemItemSetList != null){
				for(ItemSetDetailDTO itemSet: deviceRedemItemSetList){
					if(itemSet != null){
						for(ItemDetailDTO itemDetail: itemSet.getItemDetails()){
							if(itemDetail != null && itemDetail.isSelected()){
								if(LtsConstant.ITEM_TYPE_FIELD_SERVICE.equals(itemDetail.getItemType())){
									return true;
								}
								if(LtsConstant.ITEM_TYPE_SELF_PICKUP.equals(itemDetail.getItemType())){
									return false;
								}
							}
						}
					}
				}
			}
		}
		
		if(LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())){
			return true;
		}
			
		//original logic for renew order
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			List<String> allItemOfferIdList = new ArrayList<String>();
			
			if (orderCapture.getSbOrder() != null) {
				ServiceDetailLtsDTO coreLtsService = LtsSbOrderHelper.getLtsService(orderCapture.getSbOrder());
				if (coreLtsService == null || ArrayUtils.isNotEmpty(coreLtsService.getItemDtls())) {
					for (ItemDetailLtsDTO itemDetail : coreLtsService.getItemDtls()) {
						List<String> itemIdList = getItemOfferIdList(itemDetail.getItemId());
						if (itemIdList != null && !itemIdList.isEmpty()) {
							allItemOfferIdList.addAll(itemIdList);
						}
					}
				}
			}
			else if (orderCapture.getLtsBasketServiceForm() == null 
					|| orderCapture.getLtsBasketServiceForm().getPlanItemList() == null
					|| orderCapture.getLtsBasketServiceForm().getPlanItemList().isEmpty()) {
				return false;
			}
			else {
				addOfferIdToListBySelectedItem(orderCapture.getLtsBasketServiceForm().getPlanItemList(), allItemOfferIdList);
				addOfferIdToListBySelectedItem(orderCapture.getLtsBasketServiceForm().getBvasItemList(), allItemOfferIdList);
				addOfferIdToListBySelectedItemSet(orderCapture.getLtsBasketServiceForm().getContItemSetList(), allItemOfferIdList);	
			}
			
			if (allItemOfferIdList == null || allItemOfferIdList.isEmpty()) {
				return false;
			}
			
			List<String> renewalDeviceOfferIdList = bomOfferProductLkupService.getRenewalDeviceOfferIdList();
			for (String itemOfferId : allItemOfferIdList) {
				if (renewalDeviceOfferIdList.contains(itemOfferId)) {
					return true;
				}
			}
			
			return false;
		}

		return false;
	}
	
	public boolean isRenewalWithNewDevice(OrderCaptureDTO orderCapture) {
		
		if (!LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			return false;
		}
		
		List<String> allItemOfferIdList = new ArrayList<String>();
		
		if (orderCapture.getSbOrder() != null) {
			ServiceDetailLtsDTO coreLtsService = LtsSbOrderHelper.getLtsService(orderCapture.getSbOrder());
			if (coreLtsService == null || ArrayUtils.isNotEmpty(coreLtsService.getItemDtls())) {
				for (ItemDetailLtsDTO itemDetail : coreLtsService.getItemDtls()) {
					List<String> itemIdList = getItemOfferIdList(itemDetail.getItemId());
					if (itemIdList != null && !itemIdList.isEmpty()) {
						allItemOfferIdList.addAll(itemIdList);
					}
				}
			}
		}
		else if (orderCapture.getLtsBasketServiceForm() == null 
				|| orderCapture.getLtsBasketServiceForm().getPlanItemList() == null
				|| orderCapture.getLtsBasketServiceForm().getPlanItemList().isEmpty()) {
			return false;
		}
		else {
			addOfferIdToListBySelectedItem(orderCapture.getLtsBasketServiceForm().getPlanItemList(), allItemOfferIdList);
			addOfferIdToListBySelectedItem(orderCapture.getLtsBasketServiceForm().getBvasItemList(), allItemOfferIdList);
			addOfferIdToListBySelectedItemSet(orderCapture.getLtsBasketServiceForm().getContItemSetList(), allItemOfferIdList);	
		}
		
		if (allItemOfferIdList == null || allItemOfferIdList.isEmpty()) {
			return false;
		}
		
		List<String> renewalDeviceOfferIdList = bomOfferProductLkupService.getRenewalDeviceOfferIdList();
		for (String itemOfferId : allItemOfferIdList) {
			if (renewalDeviceOfferIdList.contains(itemOfferId)) {
				return true;
			}
		}
		return false;
	}
	
	private void addOfferIdToListBySelectedItemSet(List<ItemSetDetailDTO> itemSetDetailList, List<String> allItemOfferIdList) {
		if (itemSetDetailList == null || itemSetDetailList.isEmpty()) {
			return;
		}
		for (ItemSetDetailDTO itemSetDetail : itemSetDetailList) {
			if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
				addOfferIdToListBySelectedItem(Lists.newArrayList(itemSetDetail.getItemDetails()), allItemOfferIdList);
			}
		}
	}
	
	private void addOfferIdToListBySelectedItem(List<ItemDetailDTO> itemDetailList, List<String> allItemOfferIdList) {
		if (itemDetailList == null || itemDetailList.isEmpty()) {
			return;
		}
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (itemDetail.isSelected()) {
				List<String> itemOfferIdList = getItemOfferIdList(itemDetail.getItemId());
				if (itemOfferIdList != null && !itemOfferIdList.isEmpty()) {
					allItemOfferIdList.addAll(itemOfferIdList);
				}
			}
		}
	}
	

	public List<String> getAllItemSetItemId(List<ItemSetDetailDTO> itemSetList){
		List<String> itemIdList = new ArrayList<String>();
		if(itemSetList != null && !itemSetList.isEmpty()){
			for(ItemSetDetailDTO itemSet : itemSetList){
				for(ItemDetailDTO item : itemSet.getItemDetails()){
					itemIdList.add(item.getItemId());
				}
			}
		}
		return itemIdList;
	}
	

	public List<ItemDetailDTO> filterOutItemById(List<ItemDetailDTO> itemList, List<String> itemIdList){
		if(itemIdList == null || itemIdList.isEmpty()){
			return itemList;
		}
		
		List<ItemDetailDTO> filteredItemIdList = new ArrayList<ItemDetailDTO>();
		for(ItemDetailDTO itemListItem: itemList){
			if(!itemIdList.contains(itemListItem.getItemId())){
				filteredItemIdList.add(itemListItem);
			}
		}
		
		return filteredItemIdList;
	}

	public void copyItemListItemAttbValues(List<ItemDetailDTO> oldItemList, List<ItemDetailDTO> newItemList){
		if(oldItemList == null || oldItemList.size() == 0
				|| newItemList == null || newItemList.size() == 0 ){
			return;
		}
		
		for(ItemDetailDTO newItem : newItemList){
			
			for(ItemDetailDTO oldItem: oldItemList){
				if(StringUtils.equals(newItem.getItemId(), oldItem.getItemId())){
					newItem.setSelected(oldItem.isSelected());
					
					if(newItem.getItemAttbs() == null || newItem.getItemAttbs().length == 0){
						continue;
					}
					copyItemAttbValues(oldItem, newItem);
				}				
			}
		}
	}
	
	public void copyItemAttbValues(ItemDetailDTO oldItem, ItemDetailDTO newItem){
		if(!StringUtils.equals(newItem.getItemId(), oldItem.getItemId())){
			return;
		}
		
		if(oldItem.getItemAttbs() == null || oldItem.getItemAttbs().length == 0
				|| newItem.getItemAttbs() == null || newItem.getItemAttbs().length == 0){
			return;
		}
		
		
		for(ItemAttbDTO newItemAttb: newItem.getItemAttbs()){
			for(ItemAttbDTO oldItemAttb: oldItem.getItemAttbs()){
				if(StringUtils.equals(newItemAttb.getAttbID(), oldItemAttb.getAttbID())){
					newItemAttb.setAttbValue(oldItemAttb.getAttbValue());
					break;
				}
			}
		}
	}
	
	public String getOsTypeChangePenalty(List<String> pOfferCdList) {
		try {
			return this.offerDetailDao.getOsTypeChangePenalty(pOfferCdList);
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getOsTypeChangePenalty()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public String getItemProfileOsTypeChangePenalty(ItemDetailProfileLtsDTO itemProfile){
		
		if(itemProfile != null){
			List<String> profileOfferList = new ArrayList<String>();
			OfferDetailProfileLtsDTO[] profileOffers = itemProfile.getOffers();
			if(ArrayUtils.isNotEmpty(profileOffers)){
				for(OfferDetailProfileLtsDTO profileOffer : profileOffers){
					if(CollectionUtils.isNotEmpty(profileOffer.getPsefSet())){
						profileOfferList.addAll(profileOffer.getPsefSet());
					}
				}
			}
			
			return getOsTypeChangePenalty(profileOfferList);
		}
		
		return null;
	}
	
	public List<String> getOsType(List<String> pOfferCdList, boolean isBasketLevel) {
		try {
			return this.offerDetailDao.getOsType(pOfferCdList, isBasketLevel);
		} catch (DAOException de) {
			logger.error("Fail in LtsOfferService.getOsType()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	public String getItemProfileOsType(ItemDetailProfileLtsDTO itemProfile){
		
		if(itemProfile != null){
			List<String> profileOfferList = new ArrayList<String>();
			OfferDetailProfileLtsDTO[] profileOffers = itemProfile.getOffers();
			if(ArrayUtils.isNotEmpty(profileOffers)){
				for(OfferDetailProfileLtsDTO profileOffer : profileOffers){
					if(CollectionUtils.isNotEmpty(profileOffer.getPsefSet())){
						profileOfferList.addAll(profileOffer.getPsefSet());
					}
				}
			}
			
			List<String> osType = getOsType(profileOfferList, true);
			if(osType != null && osType.size() > 0){
				return osType.get(0);
			}
		}
		
		return null;
	}
	
	public boolean isExcludeQuotaCheckUpgRet(OrderCaptureDTO order){

		if(order.getLtsBasketServiceForm() == null){
			return false;
		}
		
		return isExcludeQuotaCheck(order.getLtsBasketServiceForm().getPlanItemList(), 
				order.getLtsBasketServiceForm().getContItemSetList());
		
	}
	
	// No passby now
	public boolean isExcludeQuotaCheckAcq(AcqOrderCaptureDTO order){
		if(order.getLtsAcqBasketServiceForm() == null){
			return false;
		}
		
		return isExcludeQuotaCheck(order.getLtsAcqBasketServiceForm().getPlanItemList(), 
				order.getLtsAcqBasketServiceForm().getContItemSetList());
		
	}

	public boolean isExcludeQuotaCheck(List<ItemDetailDTO> planItemList, List<ItemSetDetailDTO> contItemSetList){
		List<String> psefCodeList = new ArrayList<String>();
		if(planItemList != null){
			for(ItemDetailDTO itemDtl: planItemList){
				if(itemDtl.isSelected()){
					psefCodeList.addAll(Arrays.asList(getItemPsefCodes(itemDtl.getItemId())));
				}
			}
		}
		if(contItemSetList != null){
			for (ItemSetDetailDTO itemSetDetail : contItemSetList) {
				for(ItemDetailDTO itemDtl: itemSetDetail.getItemDetails()){
					if(itemDtl.isSelected()){
						psefCodeList.addAll(Arrays.asList(getItemPsefCodes(itemDtl.getItemId())));
					}
				}
			}
		}
		if(CollectionUtils.containsAny(psefCodeList, getQuotaBypassPsefCodeList())){
			return true;
		}
		
		return false;
	}
	
	// No passby now
	public List<String> getQuotaBypassPsefCodeList(){
		
		List<String> excludePsefCode = new ArrayList<String>();
		
		try {
			for(LookupItemDTO codeLkup: ltsQuotaBypassPsefCacheService.getCodeLkupDAO().getCodeLkup()){
				excludePsefCode.add(codeLkup.getItemKey());
			}
		} catch (DAOException e) {
			return new ArrayList<String>();
		}
		
		return excludePsefCode;
		
	}

	public ValidationResultDTO validateQuotaItemSetList(List<ItemSetDetailDTO> itemSetList){
		List<String> errorMsgList = new ArrayList<String>();
		
		if(CollectionUtils.isNotEmpty(itemSetList)){
			for(ItemSetDetailDTO itemSet: itemSetList){
				ValidationResultDTO result = validateQuotaItemSet(itemSet);
				errorMsgList.addAll(result.getMessageList());
			}
		}
		
		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, errorMsgList, null); 
		}

		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}
	
	public ValidationResultDTO validateQuotaItemSet(ItemSetDetailDTO itemSet) {
		if(itemSet == null){
			return validateQuotaItemList(null);
		}
		return validateQuotaItemList(Arrays.asList(itemSet.getItemDetails()));
	}

	public ValidationResultDTO validateQuotaItemList(List<ItemDetailDTO> itemList) {
		List<String> errorMsgList = new ArrayList<String>();
		
		if(CollectionUtils.isNotEmpty(itemList)){
			//Group all selected item and its program code 
			Map<String, Integer> programCdCountMap = new HashMap<String, Integer>();
			List<ItemDetailDTO> programItemList = new ArrayList<ItemDetailDTO>();
			for(ItemDetailDTO item: itemList){
				String programCd = item.getProgramCd();
				if(item.isSelected() && StringUtils.isNotBlank(programCd)){
					programItemList.add(item);
					if(programCdCountMap.containsKey(programCd)){
						int codeCount = programCdCountMap.get(programCd);
						programCdCountMap.put(programCd, codeCount++);
					}else{
						programCdCountMap.put(programCd, 1);
					}
				}
			}			
			
			//Validate each item, validate the amount against the quota
			if (programCdCountMap.size() > 0){
				for(ItemDetailDTO item: programItemList){
					String invalidProgramDesc = validateQuotaByProgramCdRtnStr(item.getProgramCd(), programCdCountMap.get(item.getProgramCd()));
					if(invalidProgramDesc != null){
						StringBuilder errorMsgBuilder = new StringBuilder("Selected item ");
						errorMsgBuilder.append(" ("+ invalidProgramDesc + ")");
						errorMsgBuilder.append(" is not available. Please unselect the item.");
						errorMsgList.add(errorMsgBuilder.toString());
					}
					
				}
			}
			
		}
		
		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, errorMsgList, null); 
		}

		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}
	
	public List<String> getOutQuotaItemSetDescList(List<ItemSetDetailDTO> itemSetList) {
		List<String> outQuotaItemSetDescList = new ArrayList<String>();
		
		for(ItemSetDetailDTO itemSet: itemSetList){
			outQuotaItemSetDescList.addAll(getOutQuotaItemSetDesc(itemSet));
		}
		
		return outQuotaItemSetDescList;
	}
	
	public List<String> getOutQuotaItemSetDesc(ItemSetDetailDTO itemSet) {		
		return getOutQuotaItemDescList(Arrays.asList(itemSet.getItemDetails()));
	}
	
	public List<String> getOutQuotaItemDescList(List<ItemDetailDTO> itemList) {
		List<String> outQuotaItemDescList = new ArrayList<String>();
		
		if(CollectionUtils.isNotEmpty(itemList)){
			for(int i = 0; i < itemList.size(); i++){
				if(itemList.get(i).isSelected()){
					String programDesc = validateItemQuotaNRtnStr(itemList.get(i));
					if(programDesc!=null){
						outQuotaItemDescList.add("("+programDesc+")");
					}
				}
			}
		}
		
		return outQuotaItemDescList;
	}
	
	public boolean validateItemQuota(ItemDetailDTO item) {
		if(item == null || item.getProgramCd() == null){
			return true;
		}
		
		String quotaProgramCd = item.getProgramCd();
		LtsQuotaDTO quota = ltsQuotaService.getQuota(quotaProgramCd, null);
		
		if(quota != null){
			if(quota.getQuota() > quota.getQuotaUsed()){
				return true;
			}else{
				return false;
			}
		}
		
		return true;
	}
	
	public String validateItemQuotaNRtnStr(ItemDetailDTO item) {
		if(item == null || item.getProgramCd() == null){
			return null;
		}
		
		String quotaProgramCd = item.getProgramCd();
		LtsQuotaDTO quota = ltsQuotaService.getQuota(quotaProgramCd, null);
		
		if(quota != null){
			if(quota.getQuota() > quota.getQuotaUsed()){
				return null;
			}else{
				return quota.getProgramDesc();
			}
		}
		
		return null;
	}
	
	public boolean validateQuotaByProgramCd(String programCd, int num_of_quota_used) {
		LtsQuotaDTO quota = ltsQuotaService.getQuota(programCd, null);
		
		if(quota != null){
			if(quota.getQuota() >= quota.getQuotaUsed() + num_of_quota_used){
				return true;
			}else{
				return false;
			}
		}
		
		return false;
	}
	
	public String validateQuotaByProgramCdRtnStr(String programCd, int num_of_quota_used) {
		LtsQuotaDTO quota = ltsQuotaService.getQuota(programCd, null);
		
		if(quota != null){
			if(quota.getQuota() >= quota.getQuotaUsed() + num_of_quota_used){
				return null;
			}else{
				return quota.getProgramDesc();
			}
		}
		
		return null;
	}
	
	public void addItemSetListQuotaUsed(List<ItemSetDetailDTO> itemSetList){
		for(ItemSetDetailDTO itemSet: itemSetList){
			addItemSetQuotaUsed(itemSet);
		}
	}
	
	public void addItemSetQuotaUsed(ItemSetDetailDTO itemSet){
		addItemListQuotaUsed(Arrays.asList(itemSet.getItemDetails()));
	}
	
	public void addItemListQuotaUsed(List<ItemDetailDTO> itemList){
		if(CollectionUtils.isNotEmpty(itemList)){
			for(int i = 0; i < itemList.size(); i++){
				addItemQuotaUsed(itemList.get(i));
			}
		}
	}

	public void addBasketQuotaUsed(BasketDetailDTO basketDtl){
		if(basketDtl.getProgramCd() != null){
			String quotaProgramCd = basketDtl.getProgramCd();
			ltsQuotaService.increaseQuotaUsed(quotaProgramCd);
		}
	}
	
	public void addItemQuotaUsed(ItemDetailDTO item){
		if(item.getProgramCd() != null && item.isSelected()){
			String quotaProgramCd = item.getProgramCd();
			ltsQuotaService.increaseQuotaUsed(quotaProgramCd);
		}
	}
	
	public void addQuotaUsedByList(List<LtsQuotaDTO> quotaList){
		if(CollectionUtils.isNotEmpty(quotaList)){
			for(LtsQuotaDTO quota: quotaList){
				ltsQuotaService.increaseQuotaUsed(quota);
			}
		}
	}

	public List<String> getQuotaProgramCdItemSetList(
			List<ItemSetDetailDTO> itemSetList) {
		List<String> programCdList = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(itemSetList)){
			for(ItemSetDetailDTO itemSet: itemSetList){
				List<String> tmpList = getQuotaProgramCdItemSet(itemSet);
				if(CollectionUtils.isNotEmpty(tmpList)){
					programCdList.addAll(tmpList);
				}
			}
		}
		return programCdList;
	}

	public List<String> getQuotaProgramCdItemSet(ItemSetDetailDTO itemSet) {
		List<String> programCdList = new ArrayList<String>();
		if(itemSet != null){
			List<String> tmpList = getQuotaProgramCdItemList(Arrays.asList(itemSet.getItemDetails()));
			if(CollectionUtils.isNotEmpty(tmpList)){
				programCdList.addAll(tmpList);
			}
		}
		return programCdList;
	}

	public List<String> getQuotaProgramCdItemList(List<ItemDetailDTO> itemList) {
		List<String> programCdList = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(itemList)){
			for(ItemDetailDTO item: itemList){
				if(item.isSelected() && StringUtils.isNotBlank(item.getProgramCd())){
					programCdList.add(item.getProgramCd());
				}
			}
		}
		return programCdList;
	}

	public Map<String,LtsQuotaDTO> getQuotaMapByItemSetList(List<ItemSetDetailDTO> itemSetList, Map<String,LtsQuotaDTO> quotaMap) {
		if(CollectionUtils.isNotEmpty(itemSetList)){
			for(ItemSetDetailDTO itemSet: itemSetList){
				quotaMap = getQuotaMapByItemSet(itemSet, quotaMap);
			}
		}
		return quotaMap;
	}
	
	public Map<String,LtsQuotaDTO> getQuotaMapByItemSet(ItemSetDetailDTO itemSet, Map<String,LtsQuotaDTO> quotaMap) {
		if(itemSet != null){
			quotaMap = getQuotaMapByItemList(Arrays.asList(itemSet.getItemDetails()),quotaMap);
		}
		return quotaMap;
	}
	
	public Map<String,LtsQuotaDTO> getQuotaMapByItemList(List<ItemDetailDTO> itemList, Map<String,LtsQuotaDTO> quotaMap) {
		if(CollectionUtils.isNotEmpty(itemList)){
			for(ItemDetailDTO item: itemList){
				if(item.isSelected() && StringUtils.isNotBlank(item.getProgramCd())){
					LtsQuotaDTO tempQuota = null;
					if(quotaMap.containsKey(item.getProgramCd()))
					{
						tempQuota = quotaMap.get(item.getProgramCd());
						tempQuota.setCurrentQuotaUsed(tempQuota.getCurrentQuotaUsed()+1);
						quotaMap.put(item.getProgramCd(), tempQuota);
					}
					else
					{
						tempQuota = ltsQuotaService.getQuota(item.getProgramCd(), null);
						if(tempQuota!=null)
						{
							quotaMap.put(item.getProgramCd(), tempQuota);
						}
					}
				}
			}
		}
		return quotaMap;
	}
	
	public Map<String,LtsQuotaDTO> getQuotaMapByBasket(BasketDetailDTO basketDtl, Map<String,LtsQuotaDTO> quotaMap) {
		if(StringUtils.isNotBlank(basketDtl.getProgramCd())){
			LtsQuotaDTO tempQuota = null;
			if(quotaMap.containsKey(basketDtl.getProgramCd()))
			{
				tempQuota = quotaMap.get(basketDtl.getProgramCd());
				tempQuota.setCurrentQuotaUsed(tempQuota.getCurrentQuotaUsed()+1);
				quotaMap.put(basketDtl.getProgramCd(), tempQuota);
			}
			else
			{
				tempQuota = ltsQuotaService.getQuota(basketDtl.getProgramCd(), null);
				if(tempQuota!=null)
				{
					quotaMap.put(basketDtl.getProgramCd(), tempQuota);
				}
			}
		}
		return quotaMap;
	}
	
	public List<String> getOutQuotaByItemSetListInMap(List<ItemSetDetailDTO> itemSetList, Map<String,LtsQuotaDTO> quotaMap) {
		List<String> outQuotaItemSetDescList = new ArrayList<String>();
		
		for(ItemSetDetailDTO itemSet: itemSetList){
			outQuotaItemSetDescList.addAll(getOutQuotaByItemSetInMap(itemSet,quotaMap));
		}
		
		return outQuotaItemSetDescList;
	}
	
	public List<String> getOutQuotaByItemSetInMap(ItemSetDetailDTO itemSet, Map<String,LtsQuotaDTO> quotaMap) {		
		return getOutQuotaByItemListInMap(Arrays.asList(itemSet.getItemDetails()),quotaMap);
	}
	
	public List<String> getOutQuotaByItemListInMap(List<ItemDetailDTO> itemList, Map<String,LtsQuotaDTO> quotaMap) {
		List<String> outQuotaItemDescList = new ArrayList<String>();
		
		if(CollectionUtils.isNotEmpty(itemList)){
			for(int i = 0; i < itemList.size(); i++){
				LtsQuotaDTO tempQuota = quotaMap.get(itemList.get(i).getProgramCd());
				if(tempQuota!=null)
				{
					if(tempQuota.getQuota()<tempQuota.getQuotaUsed()+tempQuota.getCurrentQuotaUsed()){
						String programDesc = tempQuota.getProgramDesc();
						if(StringUtils.isNotBlank(itemList.get(i).getItemDesc())){
							outQuotaItemDescList.add(itemList.get(i).getItemDesc()+" ("+programDesc+")");
						}else{
							outQuotaItemDescList.add(StringUtils.splitByWholeSeparatorPreserveAllTokens(itemList.get(i).getItemDisplayHtml(), "<br/>")[0]+" ("+programDesc+")");
						}
					}
				}				
			}
		}
		
		return outQuotaItemDescList;
	}
	
	public List<String> getOutQuotaByMap(Map<String,LtsQuotaDTO> quotaMap) {
		List<String> outQuotaItemDescList = new ArrayList<String>();
		
		for(Entry<String, LtsQuotaDTO> entry : quotaMap.entrySet()) {
		    LtsQuotaDTO tempQuota = entry.getValue();
		    if(tempQuota!=null)
			{
				if(tempQuota.getQuota()<tempQuota.getQuotaUsed()+tempQuota.getCurrentQuotaUsed()){
					String programDesc = tempQuota.getProgramDesc();
					outQuotaItemDescList.add(programDesc);
				}
			}
		}
		
		return outQuotaItemDescList;
	}
	
	public String getOutQuotaByBasket(BasketDetailDTO basketDtl, Map<String,LtsQuotaDTO> quotaMap) {
		if(StringUtils.isNotBlank(basketDtl.getProgramCd())){
			LtsQuotaDTO tempQuota = null;
			if(quotaMap.containsKey(basketDtl.getProgramCd()))
			{
				tempQuota = quotaMap.get(basketDtl.getProgramCd());
				if(tempQuota!=null)
				{
					if(tempQuota.getQuota()<tempQuota.getQuotaUsed()+tempQuota.getCurrentQuotaUsed()){
						String programDesc = tempQuota.getProgramDesc();
						return programDesc;	
					}
				}
			}
		}
		return "";
	}
 
}
