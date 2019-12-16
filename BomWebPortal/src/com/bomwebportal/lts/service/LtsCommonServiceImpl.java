package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.validation.Errors;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.LtsTeamFunctionDAO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbBaseDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.LtsTeamFunctionDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsBasketVasDetailFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ServiceProfileInventoryDTO;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsDrgService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsConstant.LtsOrderFlag;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.util.Utility;

public class LtsCommonServiceImpl implements LtsCommonService {

	private CodeLkupCacheService fixLineNumPrefixLkupCacheService;
	private CodeLkupCacheService mobileNumPrefixLkupCacheService;
	private LtsTeamFunctionDAO ltsTeamFunctionDAO;
	private CodeLkupCacheService ltsDrgDownTimeLkupCacheService;
	private CodeLkupCacheService ltsFieldVisitParmCacheService;
	private CodeLkupCacheService ltsOrderFlagCacheService;
	private ServiceProfileLtsDrgService serviceProfileLtsDrgService;
	
	public LtsTeamFunctionDAO getLtsTeamFunctionDAO() {
		return ltsTeamFunctionDAO;
	}

	public void setLtsTeamFunctionDAO(LtsTeamFunctionDAO ltsTeamFunctionDAO) {
		this.ltsTeamFunctionDAO = ltsTeamFunctionDAO;
	}

	public CodeLkupCacheService getFixLineNumPrefixLkupCacheService() {
		return fixLineNumPrefixLkupCacheService;
	}

	public void setFixLineNumPrefixLkupCacheService(
			CodeLkupCacheService fixLineNumPrefixLkupCacheService) {
		this.fixLineNumPrefixLkupCacheService = fixLineNumPrefixLkupCacheService;
	}

	public CodeLkupCacheService getMobileNumPrefixLkupCacheService() {
		return mobileNumPrefixLkupCacheService;
	}

	public void setMobileNumPrefixLkupCacheService(
			CodeLkupCacheService mobileNumPrefixLkupCacheService) {
		this.mobileNumPrefixLkupCacheService = mobileNumPrefixLkupCacheService;
	}

	public CodeLkupCacheService getLtsDrgDownTimeLkupCacheService() {
		return ltsDrgDownTimeLkupCacheService;
	}

	public void setLtsDrgDownTimeLkupCacheService(
			CodeLkupCacheService ltsDrgDownTimeLkupCacheService) {
		this.ltsDrgDownTimeLkupCacheService = ltsDrgDownTimeLkupCacheService;
	}
	
	public CodeLkupCacheService getLtsFieldVisitParmCacheService() {
		return ltsFieldVisitParmCacheService;
	}

	public void setLtsFieldVisitParmCacheService(
			CodeLkupCacheService ltsFieldVisitParmCacheService) {
		this.ltsFieldVisitParmCacheService = ltsFieldVisitParmCacheService;
	}

	public ServiceProfileLtsDrgService getServiceProfileLtsDrgService() {
		return serviceProfileLtsDrgService;
	}

	public void setServiceProfileLtsDrgService(
			ServiceProfileLtsDrgService serviceProfileLtsDrgService) {
		this.serviceProfileLtsDrgService = serviceProfileLtsDrgService;
	}

	public CodeLkupCacheService getLtsOrderFlagCacheService() {
		return ltsOrderFlagCacheService;
	}

	public void setLtsOrderFlagCacheService(CodeLkupCacheService ltsOrderFlagCacheService) {
		this.ltsOrderFlagCacheService = ltsOrderFlagCacheService;
	}

	public boolean isMobileNumPrefix(String pNumber) {
		try {
			LookupItemDTO[] mobilePrefix = mobileNumPrefixLkupCacheService.getCodeLkupDAO().getCodeLkup();
			return isNumPrefix(pNumber, mobilePrefix, false);
		} catch (DAOException daoe) {
			daoe.printStackTrace();
		}
		return false;
	}
	
	public boolean isFixLineNumPrefix(String pNumber, boolean pCheckLtsPrefix) {
		try {
			LookupItemDTO[] fixlinePrefixArray = fixLineNumPrefixLkupCacheService.getCodeLkupDAO().getCodeLkup();
			return isNumPrefix(pNumber, fixlinePrefixArray, pCheckLtsPrefix);
		}catch (DAOException daoe) {
			daoe.printStackTrace();
		}
		return false;
	}

	public boolean isFixLineNumPrefix(String pNumber) {
		return isFixLineNumPrefix(pNumber, false);
	}
	
	private boolean isNumPrefix(String pNumber, LookupItemDTO[] pPrefixItemArray, boolean pCheckLtsPrefix){
		if (StringUtils.isBlank(pNumber) || StringUtils.length(pNumber) != 8) {
			return false;
		}else{ 
			for(LookupItemDTO item: pPrefixItemArray){
				if(StringUtils.startsWith(pNumber, item.getItemKey())){
					return true;
				}
			}
			if(pCheckLtsPrefix){
				if(StringUtils.startsWith(pNumber, "8")){
					return true;
				}
			}
		}
		return false;
	}
	
	public void validateAttbValue(ItemAttbBaseDTO attb, List<String> errorMsgList){
		String attbValue = attb.getAttbValue();
		if (StringUtils.equalsIgnoreCase(LtsConstant.CHANNEL_ATTB_FORMAT_MOBILE_NUM, attb.getInputFormat()) && !StringUtils.equals(attbValue, LtsCsPortalBackendConstant.CSP_DUMMY_NUM)){
			if(attbValue != null && !("").equals(attbValue)){
				if (!this.isMobileNumPrefix(attbValue)){
					errorMsgList.add("Invalid mobile No. Please update again.");
				}
			} else {
				errorMsgList.add("Mobile No. is required.");
			}
		} else if (StringUtils.equalsIgnoreCase(LtsConstant.CHANNEL_ATTB_FORMAT_EMAIL, attb.getInputFormat())){
			if(attbValue != null && !("").equals(attbValue)){
				if (!Utility.validateEmail(attbValue)){
					errorMsgList.add("Email address must be valid patten(xxx@yyy.zzz).");
				}
	    	} else {
	    		errorMsgList.add("Email Address is required.");
	    	}
		}
	}
	
	public String getAttbValueByInputFormat(ItemAttbBaseDTO[] attbArray, String attbType){
		if(attbArray == null || attbArray.length ==0 || attbType == null){
			return null;
		}
		
		for(ItemAttbBaseDTO attb: attbArray){
			if(attbType.equals(attb.getInputFormat())){
				return attb.getAttbValue();
			}
		}
		
		return null;
	}
	
	public String getLkupKeyByDesc(LookupItemDTO[] lkupItems, String desc){
		for(LookupItemDTO lkupItem: lkupItems){
			if(StringUtils.equals((String)(lkupItem.getItemValue()), desc)){
				return lkupItem.getItemKey();
			}
		}
		return null;
	}
	
	public LtsTeamFunctionDTO getTeamFunction(String channelCd, String teamCd) {
		try {
			return ltsTeamFunctionDAO.getTeamFunction(channelCd, teamCd);
		}
		catch (Exception e) {
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
	public void validateContactValue(String contact, Errors errors, String fieldName){
		if (StringUtils.isBlank(contact)) {
			errors.rejectValue(fieldName, "lts.contactPhone.required");
		}else{
			if (!Utility.validatePhoneNum(contact)) {
				errors.rejectValue(fieldName, "lts.invalid.contactPhone");
			}else{
				try {
					if (StringUtils.isBlank(contact)) {
						errors.rejectValue(fieldName, "lts.contactPhone.required");
					}else if (!Utility.validatePhoneNum(contact)) {
						errors.rejectValue(fieldName, "lts.invalid.contactPhone");
					}else{ 
						LookupItemDTO[] fixLinePrefex = fixLineNumPrefixLkupCacheService.getCodeLkupDAO().getCodeLkup();
						LookupItemDTO[] mobilePrefex = mobileNumPrefixLkupCacheService.getCodeLkupDAO().getCodeLkup();
						boolean flag = false; 
						for(LookupItemDTO item: mobilePrefex){
							if(StringUtils.startsWith(contact, item.getItemKey())){
								flag = true;
							}
						}
						for(LookupItemDTO item: fixLinePrefex){
							if(StringUtils.startsWith(contact, item.getItemKey())){
								flag = true;
							}
						}
						if(StringUtils.startsWith(contact, "8")){
							flag = true;
						}
						if(!flag){
							errors.rejectValue(fieldName, "lts.invalid.contactPhone");
						}
					}
				} catch (DAOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	
	public void validateSmsNum(String smsNum, Errors errors, String fieldName){
		
		if (StringUtils.isBlank(smsNum)) {
			errors.rejectValue(fieldName, "lts.distributeSms.required");
		}
		else{
			if (!Utility.validatePhoneNum(smsNum)) {
				errors.rejectValue(fieldName, "lts.distributeSms.invalid");
			}
			else{
				try {
					LookupItemDTO[] mobilePrefex = mobileNumPrefixLkupCacheService.getCodeLkupDAO().getCodeLkup();
					boolean flag = false; 
					for(LookupItemDTO item: mobilePrefex){
						if(StringUtils.startsWith(smsNum, item.getItemKey())){
							flag = true;
						}
					}
					if(StringUtils.startsWith(smsNum, "8")){
						flag = true;
					}
					if(!flag){
						errors.rejectValue(fieldName, "lts.distributeSms.invalid");
					}
				} catch (DAOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	public boolean isNowDrgDownTime(){
		String startTimeString = (String)ltsDrgDownTimeLkupCacheService.get("START_TIME");
		String endTimeString = (String)ltsDrgDownTimeLkupCacheService.get("END_TIME");
		
		if(StringUtils.isBlank(startTimeString)||StringUtils.isBlank(endTimeString)){
			return false;
		}
		
		LocalTime startTime =LocalTime.parse(startTimeString, DateTimeFormat.forPattern("HH:mm"));
		LocalTime endTime = LocalTime.parse(endTimeString, DateTimeFormat.forPattern("HH:mm"));
		LocalTime nowTime = LocalTime.now();
		
		//start end on different day
		if(startTime.isAfter(endTime)){
			if(nowTime.isAfter(startTime) || nowTime.isBefore(endTime)){
				return true;
			}
		}else{
			if(nowTime.isAfter(startTime) && nowTime.isBefore(endTime)){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isShowDrgDownTimeMsg(){
		String startTimeString = (String)ltsDrgDownTimeLkupCacheService.get("START_TIME");
		String showMsgTimeString = (String)ltsDrgDownTimeLkupCacheService.get("SHOW_MSG");
		String endTimeString = (String)ltsDrgDownTimeLkupCacheService.get("END_TIME");

		if(StringUtils.isBlank(startTimeString)||StringUtils.isBlank(endTimeString)){
			return false;
		}
		
		if(StringUtils.isBlank(showMsgTimeString)){
			showMsgTimeString = startTimeString;
		}

		LocalTime showMsgTime =LocalTime.parse(showMsgTimeString, DateTimeFormat.forPattern("HH:mm"));
		LocalTime endTime = LocalTime.parse(endTimeString, DateTimeFormat.forPattern("HH:mm"));
		LocalTime nowTime = LocalTime.now();
		
		//start end on different day
		if(showMsgTime.isAfter(endTime)){
			if(nowTime.isAfter(showMsgTime) || nowTime.isBefore(endTime)){
				return true;
			}
		}else{
			if(nowTime.isAfter(showMsgTime) && nowTime.isBefore(endTime)){
				return true;
			}
		}
		
		return false;
	}
	
	public List<ItemDetailDTO> getAcqBasketItem(AcqOrderCaptureDTO acqOrderCapture){
		
		List<ItemDetailDTO> itemDetailList = new ArrayList<ItemDetailDTO>();
		
		if (acqOrderCapture.getLtsAcqBasketServiceForm() != null) {
            addItemListDtl(acqOrderCapture.getLtsAcqBasketServiceForm().getInstallItemList(),itemDetailList);
            addItemListDtl(acqOrderCapture.getLtsAcqBasketServiceForm().getBvasItemList(),itemDetailList);
            addItemListDtl(acqOrderCapture.getLtsAcqBasketServiceForm().getPlanItemList(),itemDetailList);
            addItemListDtl(acqOrderCapture.getLtsAcqBasketServiceForm().getContentItemList(),itemDetailList);
            addItemListDtl(acqOrderCapture.getLtsAcqBasketServiceForm().getMoovItemList(),itemDetailList);
            if (acqOrderCapture.getLtsAcqBasketServiceForm().getContItemSetList() != null && !acqOrderCapture.getLtsAcqBasketServiceForm().getContItemSetList().isEmpty()){
            for(ItemSetDetailDTO itemSetDetail:acqOrderCapture.getLtsAcqBasketServiceForm().getContItemSetList()){
				addItemListDtl(Arrays.asList(itemSetDetail.getItemDetails()),itemDetailList);
			}
            }
		}
		
		if (acqOrderCapture.getLtsAcqBasketVasDetailForm() != null) {			
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getE0060VasItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getE0060OutVasItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getExistVasItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getHotVasItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getIddOutVasItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getIddVasItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getOtherVasItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getPeFreeItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getPeSocketItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getOptAccessaryItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getPrewireVasItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getPreinstallVasItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getBvasItemList(),itemDetailList);
			addItemListDtl(acqOrderCapture.getLtsAcqBasketVasDetailForm().getOtherVasComtPeriodItemList(),itemDetailList);
			if (acqOrderCapture.getLtsAcqBasketVasDetailForm().getSmartWrtySetList() != null && !acqOrderCapture.getLtsAcqBasketVasDetailForm().getSmartWrtySetList().isEmpty()){
				for(ItemSetDetailDTO itemSetDetail:acqOrderCapture.getLtsAcqBasketVasDetailForm().getSmartWrtySetList()){
					addItemListDtl(Arrays.asList(itemSetDetail.getItemDetails()),itemDetailList);
				}
			}
			if (acqOrderCapture.getLtsAcqBasketVasDetailForm().getBundleVasSetList() != null && !acqOrderCapture.getLtsAcqBasketVasDetailForm().getBundleVasSetList().isEmpty()){
				for(ItemSetDetailDTO itemSetDetail:acqOrderCapture.getLtsAcqBasketVasDetailForm().getBundleVasSetList()){
					addItemListDtl(Arrays.asList(itemSetDetail.getItemDetails()),itemDetailList);
				}
			}
		}
		
		if (acqOrderCapture.getLtsPremiumSelectionForm() != null) {
			if (acqOrderCapture.getLtsPremiumSelectionForm().getPremiumSetList() != null && !acqOrderCapture.getLtsPremiumSelectionForm().getPremiumSetList().isEmpty()){
				for(ItemSetDetailDTO itemSetDetail:acqOrderCapture.getLtsPremiumSelectionForm().getPremiumSetList()){
					addItemListDtl(Arrays.asList(itemSetDetail.getItemDetails()),itemDetailList);
				}
			}
		}
		
		return itemDetailList;
	}
	
	public void addItemListDtl(List<ItemDetailDTO> formItemDetail, List<ItemDetailDTO> allItemList){
		if (formItemDetail == null || formItemDetail.isEmpty()) {
			return;
		}
		
		for (ItemDetailDTO itemDetail : formItemDetail) {
			if (!itemDetail.isSelected()) {
				continue;
			}
			allItemList.add(itemDetail);
		}

	}

	public boolean recheckForceFieldService(OrderCaptureDTO order){
		
		if(order.getSbOrder() != null){
			ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(order.getSbOrder());
			if(ltsService != null && ArrayUtils.isNotEmpty(ltsService.getItemDtls())){
				for(ItemDetailLtsDTO item: ltsService.getItemDtls()){
					if(LtsConstant.ITEM_TYPE_PE_SOCKET.equals(item.getItemType())){
						return true;
					}
				}
			}
			
			if(LtsSbOrderHelper.get2ndDelService(order.getSbOrder()) != null){
				return true;
			}
			
			return !order.getLtsBasketServiceForm().isAllowSelfPickup();
		}
		
		if(!isAllowSelectSelfPickupDevice(order)){
			return true;
		} 
		
		if(isChangePE(order)){
			return true;
		}
		
		if(order.getLtsAddressRolloutForm().isExternalRelocate()
				|| order.getLtsModemArrangementForm().isExistingFsaER()
				|| order.getLtsModemArrangementForm().isLostModem()){
			return true;
		}
		
		FsaDetailDTO selectedFsa = LtsSbOrderHelper.getSelectedFsa(order.getLtsModemArrangementForm());
		if(StringUtils.isNotBlank(selectedFsa.getUpgradeBandwidth())
				|| selectedFsa.getNewService() != null){
			return true;
		}
		
		if(LtsSbOrderHelper.isEyeProfile(order)){

			if(StringUtils.equals(LtsConstant.ROUTER_OPTION_SHARE_RENTAL_ROUTER,order.getLtsModemArrangementForm().getRentalRouterInd())){
				return false;
			}
			
			if(validateFsaOfferForceFS(selectedFsa.getFsaProfile())){
				return true;
			}			
			
			FsaServiceDetailOutputDTO eyeFsaProfile = order.getLtsServiceProfile().getEyeFsaProfile();
			if(eyeFsaProfile != null
					&& !StringUtils.equals(eyeFsaProfile.getFsa(), selectedFsa.getFsaProfile().getFsa())){
				return true;
			}
			
		}else{
			ModemType modemType = order.getLtsModemArrangementForm().getModemType();
			if(modemType == ModemType.STANDALONE
					|| modemType == ModemType.SHARE_PCD
					|| modemType == ModemType.SHARE_OTHER_FSA
					|| modemType == ModemType.SHARE_BUNDLE
					|| modemType == ModemType.SHARE_TV){
				return true;
			}
			if(modemType == ModemType.SHARE_EX_FSA
					&& order.getLtsModemArrangementForm().isExistingFsaER()){
				return true;
			}
		}
		
		
		return false;
	}
	
	public boolean validateFsaOfferForceFS(FsaServiceDetailOutputDTO fsaProfile){
		List<String> checkFsaOfferCdList = new ArrayList<String>();
		String valueString = (String) (ltsFieldVisitParmCacheService.get(LtsConstant.FS_PARM_FORCE_FIELD_SERVICE_WG_VAS));
		checkFsaOfferCdList.addAll(Arrays.asList(StringUtils.split(StringUtils.deleteWhitespace(valueString), ",")));
		
		for(OfferDetailProfileLtsDTO fsaOffer : fsaProfile.getOffers()){
			if(checkFsaOfferCdList.contains(fsaOffer.getOfferSubCd())){
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isChangePE(OrderCaptureDTO orderCapture){
		boolean isProfilePE = false;
		boolean isBasketPE = "Y".equals(orderCapture.getSelectedBasket().getPeInd());
		
		//DEL profile must be PE
		if(orderCapture.getLtsServiceProfile().getExistEyeType() == null){
			isProfilePE = true;
		}else{
			isProfilePE = LtsSbOrderHelper.isEyeProfileParalleExtension(orderCapture.getLtsServiceProfile());
		}
		
		if(isProfilePE != isBasketPE){
			return true;
		}
		
		return false;
	}
	
	protected boolean isAllowSelectSelfPickupDevice(OrderCaptureDTO orderCapture){
		
		if (!LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())){
			return false;
		}
		
		if(!StringUtils.equals(
				LtsConstant.DEVICE_TYPE_EYE3A, 
				orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType())){
			return false;
		}
		
		if(orderCapture.getLtsBasketVasDetailForm() != null){
			LtsBasketVasDetailFormDTO vasForm = orderCapture.getLtsBasketVasDetailForm();
			for(int i = 0; vasForm.getPeSocketItemList() != null && i < vasForm.getPeSocketItemList().size(); i++){
				if(vasForm.getPeSocketItemList().get(i).isSelected()){
					return false;
				}
			}
		}
		
		if(orderCapture.getLtsOtherVoiceServiceForm() != null
				&& orderCapture.getLtsOtherVoiceServiceForm().getCreate2ndDel() == true){
			return false;
		}
		
		boolean delNgnFlag = false; //not del profile or del and ngn
		boolean fsaValidFalg = false;
		
		//Check DEL profile for NGN network
		if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			String srvNum = orderCapture.getLtsServiceProfile().getSrvNum();
			ServiceProfileInventoryDTO srvInventory = 
					this.serviceProfileLtsDrgService.getServiceInventory(srvNum, LtsConstant.SERVICE_TYPE_TEL);
			if(srvInventory != null){
				if(LtsConstant.NETWORK_IND_NGN.equals(srvInventory.getNetworkInd())){
					delNgnFlag = true;
				}
			}
		}else{
			delNgnFlag = true;
		}
		
		//check selected fsa
		if(orderCapture.getLtsModemArrangementForm() != null){
			fsaValidFalg = validateFsaOfferAllowSelfPickup(orderCapture);
		}
		
		if(delNgnFlag && fsaValidFalg){
			return true;
		}
		
		return false;
	}
	
	public boolean validateFsaOfferAllowSelfPickup(OrderCaptureDTO order){
		boolean isFsaSubWifi0 = false;
		boolean isFsaSubBrmWifi = false;
		boolean isFsaShareHnRentalRouter = false;
		boolean isExistEyeNoSwitchFsa = false;
		boolean isLineChanage = false;
		
		FsaDetailDTO selectedFsa = LtsSbOrderHelper.getSelectedFsa(order.getLtsModemArrangementForm());
		
		if(selectedFsa != null){
			
			List<String> checkFsaOfferCdList = new ArrayList<String>();
			String valueString = (String) (ltsFieldVisitParmCacheService.get(LtsConstant.FS_PARM_ALLOW_SELF_PICKUP_WG_VAS));
			checkFsaOfferCdList.addAll(Arrays.asList(StringUtils.split(StringUtils.deleteWhitespace(valueString), ",")));
			
			isFsaSubBrmWifi = selectedFsa.isBrmWifiInd();
			isFsaShareHnRentalRouter = 
					(selectedFsa.isMeshRouterGrp() || ArrayUtils.contains(LtsConstant.ROUTER_RENTAL_ROUTER_GROUP_CODES, selectedFsa.getRouterGrp()));
			if(isFsaShareHnRentalRouter){
				isFsaShareHnRentalRouter = StringUtils.equals(LtsConstant.ROUTER_OPTION_SHARE_RENTAL_ROUTER,order.getLtsModemArrangementForm().getRentalRouterInd());
			}
			
			for(OfferDetailProfileLtsDTO fsaOffer : selectedFsa.getFsaProfile().getOffers()){
				if(checkFsaOfferCdList.contains(fsaOffer.getOfferSubCd())){
					isFsaSubWifi0 = true;
					break;
				}
			}

			FsaServiceDetailOutputDTO eyeFsaProfile = order.getLtsServiceProfile().getEyeFsaProfile();
			if(eyeFsaProfile != null
					&& StringUtils.equals(eyeFsaProfile.getSrvType(), selectedFsa.getFsaProfile().getSrvType())){
				isExistEyeNoSwitchFsa = true;
			}
			
			if(!StringUtils.equals(selectedFsa.getTechnology(), order.getModemTechnologyAissgn().getTechnology())){
				isLineChanage = true;
			}
			
			if(!isLineChanage
					&& (isFsaSubWifi0 || isFsaSubBrmWifi || isFsaShareHnRentalRouter || isExistEyeNoSwitchFsa) ){
				return true;
			}
		}
		
		return false;
		
	}

	public String getLtsOrderFlag(LtsOrderFlag flag){
		return (String) (ltsOrderFlagCacheService.get(flag.name()));
	}

	public String getLtsOrderFlag(String flagName){
		return (String) (ltsOrderFlagCacheService.get(flagName));
	}

}
