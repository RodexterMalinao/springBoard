package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.DeviceDetailDTO;
import com.bomwebportal.lts.dto.ExclusiveItemDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO.ItemCriteriaBuilder;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.ModemAssignDTO;
import com.bomwebportal.lts.dto.ModemTechnologyAissgnDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.UpgradeEyeInfoDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsBasketServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ServiceProfileInventoryDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsDeviceService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.ModemAssignService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsDrgService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;

public class LtsBasketServiceController extends SimpleFormController {

	
	private final String nextView = "ltsnowtvservice.html";
	private final String viewName = "ltsbasketservice";
	private final String commandName = "ltsBasketServiceCmd";
	
	protected ModemAssignService modemAssignService;
	protected LtsDeviceService ltsDeviceService;
	protected LtsOfferService ltsOfferService;
	protected CodeLkupCacheService installFeeArpuLkupCacheService;
	protected CodeLkupCacheService ltsFieldVisitParmCacheService;
	protected LtsCommonService ltsCommonService;

	private ServiceProfileLtsDrgService serviceProfileLtsDrgService;
	
	public ModemAssignService getModemAssignService() {
		return modemAssignService;
	}

	public void setModemAssignService(ModemAssignService modemAssignService) {
		this.modemAssignService = modemAssignService;
	}

	public CodeLkupCacheService getInstallFeeArpuLkupCacheService() {
		return installFeeArpuLkupCacheService;
	}

	public void setInstallFeeArpuLkupCacheService(
			CodeLkupCacheService installFeeArpuLkupCacheService) {
		this.installFeeArpuLkupCacheService = installFeeArpuLkupCacheService;
	}

	public LtsDeviceService getLtsDeviceService() {
		return ltsDeviceService;
	}

	public void setLtsDeviceService(LtsDeviceService ltsDeviceService) {
		this.ltsDeviceService = ltsDeviceService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
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

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public LtsBasketServiceController() {
		setCommandClass(LtsBasketServiceFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		if(LtsSbOrderHelper.isStandaloneVasOrder(orderCapture)){
			LtsBasketServiceFormDTO form = new LtsBasketServiceFormDTO();
			String locale = RequestContextUtils.getLocale(request).toString();
			String selectedBasketId = orderCapture.getLtsBasketSelectionForm().getSelectedBasketId();
			ItemCriteriaDTO defaultCriteria = LtsSbOrderHelper.getItemCriteriaDefaultBuilder(orderCapture, locale).setBasketId(selectedBasketId).build();
			List<ItemDetailDTO> planItemList = ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_PLAN).build());
			form.setPlanItemList(planItemList);
			orderCapture.setLtsBasketServiceForm(form);
		}
		
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String locale = RequestContextUtils.getLocale(request).toString();
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		LtsBasketServiceFormDTO form = (LtsBasketServiceFormDTO)command;
		
		form.setExcludeQuotaCheck(ltsOfferService.isExcludeQuotaCheck(form.getPlanItemList(), form.getContItemSetList()));

		ValidationResultDTO[] validationResults = validateSubmit(request, orderCapture, form);
		
		for (ValidationResultDTO validationResult : validationResults) {
			if (Status.INVAILD == validationResult.getStatus()) {
				ModelAndView mav = new ModelAndView(viewName);
				mav.addObject(commandName, form);
				mav.addObject("errorMsgList", validationResult.getMessageList());
				
				String selectDeviceType = orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType();
				DeviceDetailDTO selectedDevice = ltsDeviceService.getEyeDevice(selectDeviceType, RequestContextUtils.getLocale(request).toString());
				mav.addObject("selectedDevice", selectedDevice);
				return mav;
			}	
		}
		
		handleBbRental(form);
		addInstallationFeeItemList(orderCapture, form, locale);
		setPlanItemPassword(form);
		orderCapture.setLtsBasketServiceForm(form);
		orderCapture.setLtsBasketVasDetailForm(null);
		handleAutoUpgradeVDSL(orderCapture);
		return new ModelAndView(new RedirectView(nextView));
	}
	
	private ValidationResultDTO[] validateSubmit(HttpServletRequest request, OrderCaptureDTO orderCapture, LtsBasketServiceFormDTO form) {
			
		List<ValidationResultDTO> validationResultList = new ArrayList<ValidationResultDTO>();

		validationResultList.add(validateExclusiveItem(request, form));
		validationResultList.add(validateItemQuota(form));
		validationResultList.add(ltsOfferService.validateItemAttb(form.getPlanItemList()));
		
		return validationResultList.toArray(new ValidationResultDTO[validationResultList.size()]);
	}
	
	private ValidationResultDTO validateItemQuota(LtsBasketServiceFormDTO form){
		List<String> errorMsgList = new ArrayList<String>();
		errorMsgList.addAll(ltsOfferService.validateQuotaItemSetList(form.getContItemSetList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getMoovItemList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getContentItemList()).getMessageList());
		

		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null); 
		}

		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}
	
	
	private void handleAutoUpgradeVDSL(OrderCaptureDTO orderCapture) {
		// Oct 2016. Auto change technology from ADSL to VDSL.
		
		if (!LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			return;
		}

		FsaDetailDTO selectedFsa = LtsSbOrderHelper.getSelectedFsa(orderCapture.getLtsModemArrangementForm());
		
		if (selectedFsa == null || selectedFsa.getFsaProfile() == null) {
			return;
		}
		
		boolean isRenewalWithNewDevice = ltsOfferService.isRenewalWithNewDevice(orderCapture); 
		
		// For renewal with new device, skip auto upgrade if pending order exist on target FSA. 		
		if (isRenewalWithNewDevice && StringUtils.isEmpty(selectedFsa.getPendingOcid())) {
			modemAssignService.autoUpgradeVDSL(selectedFsa.getFsaProfile(), 
					orderCapture.getNewAddressRollout(), orderCapture.getModemTechnologyAissgn(), selectedFsa.getBandwidth());
		}
		else {
			ModemAssignDTO modemAssign = modemAssignService.createRenewalModemAssign(orderCapture.getNewAddressRollout(), 
					selectedFsa.getFsaProfile(), selectedFsa.getFsaProfile().getExistingSrv());
			
			ModemTechnologyAissgnDTO returnObj = new ModemTechnologyAissgnDTO(); 
			try {
				BeanUtils.copyProperties(returnObj, modemAssign);
			}
			catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
				throw new AppRuntimeException(e);	
			}
			orderCapture.setModemTechnologyAissgn(returnObj);
		}
		
	}
	
	
	private void setPlanItemPassword(LtsBasketServiceFormDTO form) {
		String defaultPassword = null;
		// Get Plan package password attribute (7FOM)
		List<ItemDetailDTO> planItemList = form.getPlanItemList();
		if (planItemList != null && !planItemList.isEmpty()) {
			for (ItemDetailDTO planItem : planItemList) {
				if (ArrayUtils.isNotEmpty(planItem.getItemAttbs())) {
					for (ItemAttbDTO itemAttb : planItem.getItemAttbs()) {
						if (StringUtils.containsIgnoreCase("Password", itemAttb.getAttbDesc())
								&& StringUtils.isBlank(itemAttb.getAttbValue())) {
							if (StringUtils.isEmpty(defaultPassword)) {
								defaultPassword = LtsSbOrderHelper.generateDefaultPassword(itemAttb.getMaxLength());
							}
							itemAttb.setAttbValue(defaultPassword);
						}
					}
				}
			}
		}
	}
	
	
	private void handleBbRental(LtsBasketServiceFormDTO form) {
		
		if (form.getBbRentalItemList() == null || form.getBbRentalItemList().isEmpty()) {
			return;
		}
		
		boolean bbRental = true;
		
		if (ltsOfferService.isItemSelected(form.getContentItemList(), LtsConstant.ITEM_TYPE_CONTENT)
				|| ltsOfferService.isItemSelected(form.getMoovItemList(), LtsConstant.ITEM_TYPE_MOOV)) {
			bbRental = false;
		}
		
		for (ItemDetailDTO itemDetail : form.getBbRentalItemList()) {
			itemDetail.setSelected(bbRental);
		}
		
	}
	
	
	private void filterPlanExclusiveItem(LtsBasketServiceFormDTO form, String locale) {
		
		
		List<ItemDetailDTO> validateItemDetailList = new ArrayList<ItemDetailDTO>();
		if (form.getMoovItemList() != null && !form.getMoovItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getMoovItemList());
		}
		
		if (form.getContentItemList() != null && !form.getContentItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getContentItemList());
		}
		
		if (form.getContItemSetList() != null && !form.getContItemSetList().isEmpty()) {
			for (ItemSetDetailDTO itemSetDetail : form.getContItemSetList()) {
				if (ArrayUtils.isEmpty(itemSetDetail.getItemDetails())) {
					continue;
				}
				validateItemDetailList.addAll((List<ItemDetailDTO>)Arrays.asList(itemSetDetail.getItemDetails()));
			}
		}
		
		List<ExclusiveItemDetailDTO> exclusiveItemDetailList = ltsOfferService.getExclusiveItemDetailList(form.getPlanItemList(), validateItemDetailList, locale, false);
		
		if (exclusiveItemDetailList == null || exclusiveItemDetailList.isEmpty()) {
			return;
		}
		
		if (form.getMoovItemList() != null && !form.getMoovItemList().isEmpty()) {
			form.setMoovItemList(filterExclusiveItem(form.getMoovItemList(), exclusiveItemDetailList));
		}
		
		if (form.getContentItemList() != null && !form.getContentItemList().isEmpty()) {
			form.setContentItemList(filterExclusiveItem(form.getContentItemList(), exclusiveItemDetailList));
		}
		
		if (form.getContItemSetList() != null && !form.getContItemSetList().isEmpty()) {
			for (ItemSetDetailDTO itemSetDetail : form.getContItemSetList()) {
				if (ArrayUtils.isEmpty(itemSetDetail.getItemDetails())) {
					continue;
				}
				List<ItemDetailDTO> filterExclusiveItemList = filterExclusiveItem((List<ItemDetailDTO>)Arrays.asList(itemSetDetail.getItemDetails()), exclusiveItemDetailList);
				if (filterExclusiveItemList != null && !filterExclusiveItemList.isEmpty()) {
					itemSetDetail.setItemDetails(filterExclusiveItemList.toArray(new ItemDetailDTO[filterExclusiveItemList.size()]));	
				}
			}
		}
	}
	
	private List<ItemDetailDTO> filterExclusiveItem(List<ItemDetailDTO> itemDetailList, List<ExclusiveItemDetailDTO> exclusiveItemDetailList ) {
		
		Set<ItemDetailDTO> filteredItemSet = new HashSet<ItemDetailDTO>();
		for(ItemDetailDTO itemDetail : itemDetailList) {
			for (ExclusiveItemDetailDTO exclusiveItemDetail : exclusiveItemDetailList) {
				if (StringUtils.equals(exclusiveItemDetail.getItemAId(), itemDetail.getItemId())
						|| StringUtils.equals(exclusiveItemDetail.getItemBId(), itemDetail.getItemId())) {
					continue;
				}
				filteredItemSet.add(itemDetail);
			}
		}
		if (filteredItemSet.isEmpty()) {
			return null;
		}
		return Arrays.asList(filteredItemSet.toArray(new ItemDetailDTO[filteredItemSet.size()]));
	}
	
	private ValidationResultDTO validateExclusiveItem(HttpServletRequest request, LtsBasketServiceFormDTO form) {
		
		List<ItemDetailDTO> validateItemDetailList = new ArrayList<ItemDetailDTO>();
		
		if (form.getPlanItemList() != null && !form.getPlanItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getPlanItemList());
		}
		
		if (form.getMoovItemList() != null && !form.getMoovItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getMoovItemList());
		}
		
		if (form.getContentItemList() != null && !form.getContentItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getContentItemList());
		}
		
		if (form.getContItemSetList() != null && !form.getContItemSetList().isEmpty()) {
			for (ItemSetDetailDTO itemSetDetail : form.getContItemSetList()) {
				if (ArrayUtils.isEmpty(itemSetDetail.getItemDetails())) {
					continue;
				}
				validateItemDetailList.addAll((List<ItemDetailDTO>)Arrays.asList(itemSetDetail.getItemDetails()));
			}
		}
		
		return ltsOfferService.validateExclusiveItem(validateItemDetailList, validateItemDetailList, RequestContextUtils.getLocale(request).toString());

	}
	
	private boolean isProfileRebateTermPlan(OrderCaptureDTO orderCapture) {
		ItemDetailProfileLtsDTO[] profileItems = orderCapture.getLtsServiceProfile().getItemDetails();
		
		if (ArrayUtils.isNotEmpty(profileItems)) {
			for (ItemDetailProfileLtsDTO profileItem : profileItems) {
				if (StringUtils.equals(LtsConstant.PROFILE_ITEM_TYPE_SERVICE, profileItem.getItemType())
						&& StringUtils.contains(LtsConstant.TP_CATG_REBATE, profileItem.getItemDetail().getTpCatg())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void addInstallationFeeItemList(OrderCaptureDTO orderCapture, LtsBasketServiceFormDTO form, String locale) {
		
		String selectedBasketId = orderCapture.getLtsBasketSelectionForm().getSelectedBasketId();
//		String applicationDate = orderCapture.getLtsMiscellaneousForm().getApplicationDate();
		
		ItemCriteriaDTO defaultCriteria = LtsSbOrderHelper.getItemCriteriaDefaultBuilder(orderCapture, locale).setBasketId(selectedBasketId).build();
		List<ItemDetailDTO> installItemList = ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultCriteria).setGetAttrInd(false).setItemType(LtsConstant.ITEM_TYPE_INSTALL).build());
		List<ItemDetailDTO> installWaiveItemList = ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultCriteria).setGetAttrInd(false).setItemType(LtsConstant.ITEM_TYPE_INSTALL_WAIVE).build());
		
//		List<ItemDetailDTO> installItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_INSTALL, locale, true, orderCapture.getBasketChannelId(), applicationDate);
//		List<ItemDetailDTO> installWaiveItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_INSTALL_WAIVE, locale, true, orderCapture.getBasketChannelId(), applicationDate);
		
		if (installItemList == null || installItemList.isEmpty()) {
			form.setInstallItemList(installWaiveItemList);
			return;
		}
		
		boolean waiveInstallationFee = false;
		if (isProfileRebateTermPlan(orderCapture)) {
			waiveInstallationFee = true;
		}
	          
		if (ltsOfferService.isItemSelected(form.getContentItemList(), LtsConstant.ITEM_TYPE_CONTENT)
				|| ltsOfferService.isItemSelected(form.getMoovItemList(), LtsConstant.ITEM_TYPE_MOOV)) {
			waiveInstallationFee = true;
		}
		
		Object installFeeArpu = installFeeArpuLkupCacheService.get(LtsConstant.CODE_LKUP_INSTALL_FEE_ARPU);
		if (installFeeArpu != null && StringUtils.isNotEmpty((String)installFeeArpu)) {
			double profileTpArpu = LtsSbOrderHelper.getProfileTpArpu(orderCapture.getLtsServiceProfile(),orderCapture.getOrderType());
			if (profileTpArpu < Double.parseDouble((String)installFeeArpu)) {
				waiveInstallationFee = true;
			}
		}
		
		form.setInstallItemList(waiveInstallationFee ? installWaiveItemList : installItemList);
	}
	
	private void addBbRentalItemList(OrderCaptureDTO orderCapture, LtsBasketServiceFormDTO form, String locale) {
		
		String selectedBasketId = orderCapture.getLtsBasketSelectionForm().getSelectedBasketId();
//		String applicationDate = orderCapture.getLtsMiscellaneousForm().getApplicationDate();
		
		List<ItemDetailDTO> bbRentalItemList = ltsOfferService.getBasketItemList(
				LtsSbOrderHelper.getItemCriteriaDefaultBuilder(orderCapture, locale)
				.setBasketId(selectedBasketId)
				.setGetAttrInd(false)
				.setItemType(LtsConstant.ITEM_TYPE_BB_RENTAL)
				.build());
		
		
//		List<ItemDetailDTO> bbRentalItemList = ltsOfferService
//			.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_BB_RENTAL, locale, true, orderCapture.getBasketChannelId(), applicationDate);
		
		if (bbRentalItemList != null && !bbRentalItemList.isEmpty() && isGenerateBbRental(orderCapture)) {
			form.setBbRentalItemList(bbRentalItemList);
		}
	}
	
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		String locale = RequestContextUtils.getLocale(request).toString();
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		String selectedBasketId = orderCapture.getLtsBasketSelectionForm().getSelectedBasketId();
		LtsBasketServiceFormDTO form = new LtsBasketServiceFormDTO();
//		String applicationDate = orderCapture.getLtsMiscellaneousForm().getApplicationDate();
		
		ItemCriteriaDTO defaultCriteria = LtsSbOrderHelper.getItemCriteriaDefaultBuilder(orderCapture, locale).setBasketId(selectedBasketId).build();
		
		List<ItemDetailDTO> planItemList = ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_PLAN).build());
		List<ItemDetailDTO> bvasItemList = ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_BVAS).build());
		List<ItemDetailDTO> moovItemList = ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultCriteria).setGetAttrInd(false).setItemType(LtsConstant.ITEM_TYPE_MOOV).build());
		List<ItemDetailDTO> contentItemList = ltsOfferService.getBasketItemList(new ItemCriteriaBuilder(defaultCriteria).setGetAttrInd(false).setItemType(LtsConstant.ITEM_TYPE_CONTENT).build());
				
		ItemSetCriteriaDTO criteria = LtsSbOrderHelper.getItemSetCriteria(orderCapture, bomSalesUser, LtsConstant.ITEM_SET_TYPE_CONT_SET, locale);
		criteria.setOsType(orderCapture.getSelectedBasket().getOsType());
		List<ItemSetDetailDTO> contItemSetList = ltsOfferService.getBasketItemSetList(criteria);
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType()) 
						&& StringUtils.isNotBlank(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			if(StringUtils.isNotBlank(orderCapture.getLtsServiceProfile().getBundleTvCredit())){
				filterRepackTvItem(contItemSetList,orderCapture);
			}
			//else if (isSeparateNowTvService(orderCapture) || isSubscribedNowTvService(orderCapture)) {
			else {
				filterBundleNowTvItem(contItemSetList, orderCapture);	
			}
		}
		
		form.setPlanItemList(planItemList);
		form.setBvasItemList(bvasItemList);
		form.setMoovItemList(moovItemList);
		form.setContentItemList(contentItemList);
		
		if (contItemSetList != null && !contItemSetList.isEmpty() && ArrayUtils.isNotEmpty(contItemSetList.get(0).getItemDetails())) {
			form.setContItemSetList(contItemSetList);	
		}
		filterPlanExclusiveItem(form, locale);
		
		addBbRentalItemList(orderCapture, form, locale);
		addInstallationFeeItemList(orderCapture, form, locale);
		
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())){
			addUpgradeInfoItem(orderCapture, form, locale);	

			//TODO BOM2019014
			ItemSetCriteriaDTO deviceRedemSetCriteria = LtsSbOrderHelper.getItemSetCriteria(orderCapture, bomSalesUser, LtsConstant.ITEM_SET_TYPE_DEVICE_REDEMPTION_SET, locale);
			List<ItemSetDetailDTO> deviceRedemSetList = ltsOfferService.getBasketItemSetList(deviceRedemSetCriteria);
			
			String mandatoryItemType = null;
			if (ltsCommonService.recheckForceFieldService(orderCapture)){
				mandatoryItemType = LtsConstant.ITEM_TYPE_FIELD_SERVICE;
			}else {
				form.setAllowSelfPickup(true);
				if(LtsConstant.OS_TYPE_IOS.equals(orderCapture.getSelectedBasket().getOsType())){
					mandatoryItemType = LtsConstant.ITEM_TYPE_SELF_PICKUP;
				}
			}
			if(mandatoryItemType != null){
				for(int i = 0; i < deviceRedemSetList.size(); i++){
					
					List<ItemDetailDTO> redemItemList = Arrays.asList(deviceRedemSetList.get(i).getItemDetails());
					List<ItemDetailDTO> filteredRedemItemList = new ArrayList<ItemDetailDTO>();
					for(int j = 0; j < redemItemList.size(); j++){
						ItemDetailDTO redemItem = redemItemList.get(j);
						if(StringUtils.equals(mandatoryItemType, redemItem.getItemType()) ){
							redemItem.setMdoInd(LtsConstant.ITEM_MDO_MANDATORY);
							redemItem.setSelected(true);
							filteredRedemItemList.add(redemItem);
						}
					}
					
					deviceRedemSetList.get(i).setItemDetails(filteredRedemItemList.toArray(new ItemDetailDTO[0]));
				}
			}
			form.setDeviceRedemSetList(deviceRedemSetList);
		}
		
		return form;
	}

	private boolean isSeparateNowTvService(OrderCaptureDTO orderCapture) {
		
		if (StringUtils.isNotBlank(orderCapture.getLtsServiceProfile().getBundleTvCredit())) {
			return false;
		}
		
		Set<String> nowTvSet = ltsOfferService.getNowTvSet();
		
		OfferDetailProfileLtsDTO[] offerProfile = orderCapture.getLtsServiceProfile().getOfferProfiles();
		for(int i = 0; i < offerProfile.length; i++){
			if(offerProfile[i].getPsefSet() != null){
				for (String psef : offerProfile[i].getPsefSet() ) {
				if(nowTvSet.contains(psef)){
					return true;
				}
			  }
		    }
		}
		return false;
	}
	
	private void addUpgradeInfoItem(OrderCaptureDTO orderCapture, LtsBasketServiceFormDTO form, String locale) {
		
		ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getLtsServiceProfile();
		UpgradeEyeInfoDTO upgradeEyeInfo = this.ltsOfferService
				.getUpgradeEyeInfo(serviceProfile, orderCapture.getLtsDeviceSelectionForm()
						.getSelectedDeviceType(),
						orderCapture.getLtsCustomerInquiryForm().isUpgradeToStaffPlan());
		
		orderCapture.setUpgradeEyeInfo(upgradeEyeInfo);
		
		if (upgradeEyeInfo != null && !isWaiveUpgradeCharge(orderCapture)) {
			if (StringUtils.isNotBlank(upgradeEyeInfo.getAdminChargeItemId())
					&& !isErWaiveAdminCharge(orderCapture)) {
				List<ItemDetailDTO> adminChargeItemList = ltsOfferService.getItem(new String[] { upgradeEyeInfo.getAdminChargeItemId() }, 
						LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale, true);
				form.setAdminChargeItemList(adminChargeItemList);
			}
			if (StringUtils.isNotBlank(upgradeEyeInfo.getCancelChargeItemId())) {
				List<ItemDetailDTO> cancelChargeItemList = ltsOfferService.getItem(new String[] { upgradeEyeInfo.getCancelChargeItemId() },
						LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale, true);
				form.setCancelChargeItemList(cancelChargeItemList);
			}
		}
	}
	
	private boolean isWaiveUpgradeCharge(OrderCaptureDTO orderCapture) {
		BasketDetailDTO selectedBasket = orderCapture.getSelectedBasket();
		if (LtsConstant.EYE_FAULT_CASE_PROJECT_CD_MAP.containsValue(selectedBasket.getProjectCd())) {
			return true;
		}
		return false;
	}
	
	private boolean isErWaiveAdminCharge(OrderCaptureDTO orderCapture) {
		LtsAddressRolloutFormDTO addrRolloutForm = orderCapture.getLtsAddressRolloutForm();
		if (addrRolloutForm == null || !addrRolloutForm.isExternalRelocate()) {
			return false;
		}
		
		if (LtsConstant.LTS_PRODUCT_TYPE_EYE_2_A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())
				&& LtsConstant.DEVICE_TYPE_EYE3A.equals(orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType())) {
			return LtsSbOrderHelper.isSecondContract(orderCapture.getLtsServiceProfile());	
		}
		
		return false;
	}
	
	
	private boolean isAllow2L2BMirror(OrderCaptureDTO orderCapture) { 
		
		String deviceType = orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType();
		String modemArrangment = orderCapture.getModemTechnologyAissgn().getModemArrangment();
		
		if (!ltsOfferService.isAllow2L2B(deviceType)) {
			return false;
		}
		
		if (StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B, modemArrangment)){
			return true;
		}
		// Special case 1L1B PCD -> PCD + TV & EYE
		else if (StringUtils.equals(LtsConstant.MODEM_TYPE_1L1B, modemArrangment)
				&& (StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_PCD_SDTV,orderCapture.getModemTechnologyAissgn().getNewImsService())
						|| StringUtils.equals(LtsConstant.SRV_ACTION_TYPE_CD_PCD_HDTV,orderCapture.getModemTechnologyAissgn().getNewImsService()))){
			return true;
		}
		
		return false;
			
	}
	
	
	private boolean isGenerateBbRental(OrderCaptureDTO orderCapture) {
		if (StringUtils.equals(LtsConstant.HOUSE_TYPE_PUBLIC_HSE, orderCapture.getNewAddressRollout().getHousingType())) {
			return false;
		}
		
		if (orderCapture.getLtsModemArrangementForm().getModemType() == ModemType.STANDALONE) {
			if (orderCapture.getLtsModemArrangementForm().getExistingFsaList() == null
					|| orderCapture.getLtsModemArrangementForm().getExistingFsaList().isEmpty()) {
				return true;
			}
			else if (!isAllFsaNotAllowToShare(orderCapture.getLtsModemArrangementForm().getExistingFsaList())){
				return true;
			}
		}
		return false;
	}
	
	private boolean isAllFsaNotAllowToShare(List<FsaDetailDTO> fsaDetailList) {
		
		if (fsaDetailList == null || fsaDetailList.isEmpty()) {
			return false;
		}
		
		for (FsaDetailDTO fsaDetail : fsaDetailList) {
			if (!fsaDetail.isNotAllowToShare()) {
				return false;
			}
		}
		return true;
	}
	
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		String locale = RequestContextUtils.getLocale(request).toString();
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		String selectDeviceType = orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType();
		DeviceDetailDTO selectedDevice = ltsDeviceService.getEyeDevice(selectDeviceType, locale);
		Map<String, Object> refereneceData = new HashMap<String, Object>();
		refereneceData.put("selectedDevice", selectedDevice);
		return refereneceData;
	}
	
	private void filterBundleNowTvItem(List<ItemSetDetailDTO> contItemSetList, OrderCaptureDTO orderCapture ) {
		if (contItemSetList == null || contItemSetList.isEmpty() ) {
			return;
		}
		
		List<ItemDetailDTO> itemDetailList = null;
		
		for (ItemSetDetailDTO contItemSetDetail : contItemSetList) {
			itemDetailList = new ArrayList<ItemDetailDTO>();
			if (ArrayUtils.isNotEmpty(contItemSetDetail.getItemDetails())) {
				for (ItemDetailDTO itemDetail : contItemSetDetail.getItemDetails()) {
					if (!StringUtils.equals(itemDetail.getItemType(), LtsConstant.ITEM_TYPE_NOWTV_PAY) 
							&& !StringUtils.equals(itemDetail.getItemType(), LtsConstant.ITEM_TYPE_NOWTV_SPEC)) {
						itemDetailList.add(itemDetail);
					}
				}
			}
			contItemSetDetail.setItemDetails(itemDetailList.toArray(new ItemDetailDTO[itemDetailList.size()]));
		}
		
	}
	
	
	private void filterRepackTvItem(List<ItemSetDetailDTO> contItemSetList, OrderCaptureDTO orderCapture) {
		
		if (contItemSetList == null || contItemSetList.isEmpty()) {
			return;
		}
		
		List<ItemDetailDTO> itemDetailList = null;
		
		for (ItemSetDetailDTO contItemSetDetail : contItemSetList) {
			itemDetailList = new ArrayList<ItemDetailDTO>();
			if (ArrayUtils.isNotEmpty(contItemSetDetail.getItemDetails())) {
				for (ItemDetailDTO itemDetail : contItemSetDetail.getItemDetails()) {
					itemDetailList.add(itemDetail);
					if (itemDetail.getCredit() != null 
							&& !itemDetail.getCredit().equals(orderCapture.getLtsServiceProfile().getBundleTvCredit())) {
						itemDetailList.remove(itemDetail);
					}
				}
			}
			contItemSetDetail.setItemDetails(itemDetailList.toArray(new ItemDetailDTO[itemDetailList.size()]));
		}
		
	}
	
	private boolean isSubscribedNowTvService(OrderCaptureDTO orderCapture) {
		return LtsSbOrderHelper.getNowTvServiceProfile(orderCapture) != null
				|| LtsSbOrderHelper.isNewNowTvService(orderCapture);
	}
	
}
