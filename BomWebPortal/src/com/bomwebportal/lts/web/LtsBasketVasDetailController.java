package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ExclusiveItemDetailDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO;
import com.bomwebportal.lts.dto.ItemCriteriaDTO.ItemCriteriaBuilder;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsBasketVasDetailFormDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.IddCallPlanProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemActionLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.bom.OfferProfileLtsService;
import com.bomwebportal.lts.service.order.CallPlanLtsService;
import com.bomwebportal.lts.service.order.OfferItemService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;
import com.pccw.springboard.svc.server.dto.CampaignDTO;
import com.pccw.util.CommonUtil;

public class LtsBasketVasDetailController extends SimpleFormController {

	
	private final String nextView = "ltsothervoiceservice.html";
	private final String viewName = "ltsbasketvasdetail";
	private final String commandName = "ltsBasketVasDetailCmd";
	
	protected LtsOfferService ltsOfferService;
	protected OfferItemService offerItemService;
	protected LtsBasketService ltsBasketService;
	protected CustomerProfileLtsService customerProfileLtsService;
	protected CodeLkupCacheService ltsIddCampaignLkupCacheService;
	protected CallPlanLtsService callPlanLtsService;
	protected LtsCommonService ltsCommonService;
	protected OfferProfileLtsService offerProfileLtsService;
	private Locale locale;
	private MessageSource messageSource;
	
	public OfferProfileLtsService getOfferProfileLtsService() {
		return offerProfileLtsService;
	}

	public void setOfferProfileLtsService(
			OfferProfileLtsService offerProfileLtsService) {
		this.offerProfileLtsService = offerProfileLtsService;
	}
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public CallPlanLtsService getCallPlanLtsService() {
		return callPlanLtsService;
	}

	public void setCallPlanLtsService(CallPlanLtsService callPlanLtsService) {
		this.callPlanLtsService = callPlanLtsService;
	}

	public CodeLkupCacheService getLtsIddCampaignLkupCacheService() {
		return ltsIddCampaignLkupCacheService;
	}

	public void setLtsIddCampaignLkupCacheService(
			CodeLkupCacheService ltsIddCampaignLkupCacheService) {
		this.ltsIddCampaignLkupCacheService = ltsIddCampaignLkupCacheService;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}

	public OfferItemService getOfferItemService() {
		return offerItemService;
	}

	public void setOfferItemService(OfferItemService offerItemService) {
		this.offerItemService = offerItemService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public LtsBasketVasDetailController() {
		setCommandClass(LtsBasketVasDetailFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		if (LtsSessionHelper.getOrderCapture(request) == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		String locale = RequestContextUtils.getLocale(request).toString();
		LtsBasketVasDetailFormDTO form = (LtsBasketVasDetailFormDTO) command;
		if (orderCapture == null || form.getFormAction() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		String eligibleDocType = LtsSbOrderHelper.getTargetCustomerDocType(orderCapture);

		switch (form.getFormAction()) {
		case UPDATE: {
			BasketDetailDTO selectedBasketDetail = ltsBasketService.getBasket(
					orderCapture.getLtsBasketSelectionForm()
							.getSelectedBasketId(), LtsConstant.LOCALE_ENG, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
			
			setProfileTpVasItem(form, orderCapture, selectedBasketDetail, locale);
			
			orderCapture.setLtsBasketVasDetailForm(form);
			ModelAndView mav = new ModelAndView(viewName);
			mav.addAllObjects(errors.getModel());
			mav.addObject(commandName, form);
			return mav;
			}
		case FILTER: {
			reloadFfpVasItemList(form, orderCapture, orderCapture.getSelectedBasket(), locale);
			ModelAndView mav = new ModelAndView(viewName);
			mav.addObject(commandName, form);
			mav.addAllObjects(errors.getModel());
			return mav;	
			}
		}
		
		if (form.isOptOut0060e()) {
			List<ItemDetailDTO> e0060OutVasItemList = 
					ltsOfferService.getItemList(LtsSbOrderHelper.getItemCriteriaDefaultBuilder(orderCapture, locale)
							.setItemType(LtsConstant.ITEM_TYPE_0060E_OUT)
							.setEligibleDocType(eligibleDocType)
							.setGetAttrInd(false)
							.build());
					
					
			if (e0060OutVasItemList != null && !e0060OutVasItemList.isEmpty()) {
				for (ItemDetailDTO itemDetail : e0060OutVasItemList) {
					itemDetail.setSelected(true);
				}
				form.setE0060OutVasItemList(e0060OutVasItemList);
			}
		}
		if (form.isOptOutIdd()) {
			List<ItemDetailDTO> iddOutVasItemList = 
					ltsOfferService.getItemList(LtsSbOrderHelper.getItemCriteriaDefaultBuilder(orderCapture, locale)
							.setItemType(LtsConstant.ITEM_TYPE_IDD_OUT)
							.setEligibleDocType(eligibleDocType)
							.setGetAttrInd(false)
							.build());
					
			if (iddOutVasItemList != null && !iddOutVasItemList.isEmpty()) {
				for (ItemDetailDTO itemDetail : iddOutVasItemList) {
					itemDetail.setSelected(true);
				}
				form.setIddOutVasItemList(iddOutVasItemList);
			}
		}
		
		ValidationResultDTO[] validationResults = validateSubmit(request, orderCapture, form);
		
		for (ValidationResultDTO validationResult : validationResults) {
			if (Status.INVAILD == validationResult.getStatus()) {
				ModelAndView mav = new ModelAndView(viewName);
				mav.addObject(commandName, form);
				mav.addObject("errorMsgList", validationResult.getMessageList());
				return mav;
			}	
		}

		orderCapture.setLtsBasketVasDetailForm(form);
		orderCapture.setCreate2ndDelByReserveDnInd(null);
			
		if(LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType())){
			form.setSelectedFfpOnly(isSelectedFfpOnly(form));
		}
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	private void reloadFfpVasItemList(LtsBasketVasDetailFormDTO form, OrderCaptureDTO orderCapture, BasketDetailDTO basketDetailDTO, String locale){

		String eligibleDocType = LtsSbOrderHelper.getTargetCustomerDocType(orderCapture);
		ItemCriteriaDTO defaultCriteria = LtsSbOrderHelper.getItemCriteriaDefaultBuilder(orderCapture, locale)
				.setEligibleDocType(eligibleDocType).build();
		
		List<ItemDetailDTO> ffpVasHotItemList 		= ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_FFP_HOT).setCampaignCd(getIddCampaignCode(orderCapture)).build());
		List<ItemDetailDTO> ffpVasOtherItemList 	= ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_FFP_OTHER).setCampaignCd(getIddCampaignCode(orderCapture)).build());
		
		List<String> bundleVasItemIdList = ltsOfferService.getAllItemSetItemId(form.getBundleVasSetList());
		
		boolean is12Mth = form.isMth12Ffp();
		boolean is18Mth = form.isMth18Ffp();
		boolean is24Mth = form.isMth24Ffp();
		
		List<ItemDetailDTO> filterList = new ArrayList<ItemDetailDTO>();
		for(ItemDetailDTO info : ffpVasHotItemList){
			if(is12Mth && "12".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
			else if(is18Mth && "18".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
			else if(is24Mth && "24".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
			else if(StringUtils.isBlank(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
		}
		form.setFfpHotItemList(ltsOfferService.filterOutItemById(filterList, bundleVasItemIdList));
		
		filterList = new ArrayList<ItemDetailDTO>();
		for(ItemDetailDTO info : ffpVasOtherItemList){
			if(is12Mth && "12".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
			else if(is18Mth && "18".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
			else if(is24Mth && "24".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
			else if(StringUtils.isBlank(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
				filterList.add(info);
			}
		}	
		form.setFfpOtherItemList(ltsOfferService.filterOutItemById(filterList, bundleVasItemIdList));
		
		if (orderCapture.getLtsCustomerInquiryForm().isUpgradeToStaffPlan()) {
			List<ItemDetailDTO> ffpStaffItemList = ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_FFP_STAFF).setCampaignCd(getIddCampaignCode(orderCapture)).build());
			
			filterList = new ArrayList<ItemDetailDTO>();
			for(ItemDetailDTO info : ffpStaffItemList){
				if(is12Mth && "12".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
					filterList.add(info);
				}
				else if(is18Mth && "18".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
					filterList.add(info);
				}
				else if(is24Mth && "24".equals(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
					filterList.add(info);
				}
				else if(StringUtils.isBlank(extractContractMonthFromRecurrentAmt(info.getRecurrentAmtTxt()))){
					filterList.add(info);
				}
			}	
			form.setFfpStaffItemList(ltsOfferService.filterOutItemById(filterList, bundleVasItemIdList));
		}	
	}
	
	private String extractContractMonthFromRecurrentAmt(String pRecurrentAmtTxt){
		if(StringUtils.isBlank(pRecurrentAmtTxt)){
			return "";
		}
		
		String[] result = pRecurrentAmtTxt.split("x");
		
		if(result == null || result.length != 2){
			return "";
		}
		
		return result[1].replaceAll(" ", "").replace("month", "");
	}
	
	private boolean isSelectedFfpOnly(LtsBasketVasDetailFormDTO form){
		if(isAnyItemSelected(form.getHotVasItemList())
				|| isAnyItemSelected(form.getOtherVasItemList())
				|| isAnyItemSetSelected(form.getBundleVasSetList()) ){
			return false;
		}
		
		if(isAnyItemSelected(form.getFfpHotItemList())
				|| isAnyItemSelected(form.getFfpOtherItemList())
				|| isAnyItemSelected(form.getFfpStaffItemList())){
			return true;
		}
		
		return false;
	}

	private boolean isAnyItemSetSelected(List<ItemSetDetailDTO> itemSetList){
		if(itemSetList == null){
			return false;
		}
		for(ItemSetDetailDTO itemSet: itemSetList){
			if(itemSet != null && isAnyItemSelected(Arrays.asList(itemSet.getItemDetails()))){
				return true;
			}
		}
		return false;
	}
	
	private boolean isAnyItemSelected(List<ItemDetailDTO> itemList){
		if(itemList == null){
			return false;
		}
		
		for(ItemDetailDTO item: itemList){
			if(item != null && item.isSelected()){
				return true;
			}
		}
		return false;
	}
	
	
	private ValidationResultDTO[] validateSubmit(HttpServletRequest request, OrderCaptureDTO orderCapture, LtsBasketVasDetailFormDTO form) {
		
		List<ValidationResultDTO> validationResultList = new ArrayList<ValidationResultDTO>();
		validationResultList.add(validateApprovalRemark(orderCapture, form));
		validationResultList.add(validateExclusiveItem(request, orderCapture, form));
		validationResultList.add(validateAttbParam(form));
//		validationResultList.add(validateUpgradeBasketArpuDecrease(orderCapture, form));
		validationResultList.add(validateRenewalArpuDecrease(orderCapture, form, LtsSessionHelper.getBomSalesUser(request)));
//		validationResultList.add(validate2ndContractArpuDecrease(orderCapture, form));
//		validationResultList.add(validateBasketArpuDecrease(orderCapture, form));
		validationResultList.add(validateFfpItem(form));
		validationResultList.add(validateBundle2gNum(orderCapture, form));

		if(orderCapture.getLtsBasketServiceForm() != null
				&& !orderCapture.getLtsBasketServiceForm().isExcludeQuotaCheck()){
			validationResultList.add(validateItemQuota(form));
		}
		
		if(LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType())){
			validationResultList.add(validateStandaloneVasItemSelect(form));			
		}
		
		return validationResultList.toArray(new ValidationResultDTO[validationResultList.size()]);
	}
	
	private ValidationResultDTO validateItemQuota(LtsBasketVasDetailFormDTO form){
		List<String> errorMsgList = new ArrayList<String>();
		errorMsgList.addAll(ltsOfferService.validateQuotaItemSetList(form.getContItemSetList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemSetList(form.getBundleVasSetList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemSetList(form.getSmartWrtySetList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemSetList(form.getTeamVasSetList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getMoovItemList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getContentItemList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getHotVasItemList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getBvasItemList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getE0060VasItemList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getFfpHotItemList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getFfpOtherItemList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getFfpStaffItemList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getHotVasItemList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getIddVasItemList()).getMessageList());
		errorMsgList.addAll(ltsOfferService.validateQuotaItemList(form.getOtherVasItemList()).getMessageList());

		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null); 
		}

		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}
	
	private ValidationResultDTO validateStandaloneVasItemSelect(LtsBasketVasDetailFormDTO form){
		
		List<String> errorMsgList = new ArrayList<String>();
		
		if(isAnyItemSelected(form.getHotVasItemList())
				|| isAnyItemSelected(form.getOtherVasItemList())
				|| isAnyItemSetSelected(form.getBundleVasSetList())
				|| isAnyItemSelected(form.getFfpHotItemList())
				|| isAnyItemSelected(form.getFfpOtherItemList())
				|| isAnyItemSelected(form.getFfpStaffItemList())
				|| isAnyItemSelected(form.getMoovItemList())
				|| isAnyItemSelected(form.getContentItemList())
				|| isAnyItemSetSelected(form.getContItemSetList())){

			return new ValidationResultDTO(Status.VALID, null, null);
		}
		
		errorMsgList.add(messageSource.getMessage("lts.basketVasDetail.noOffer", new Object[] {}, this.locale));
		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}

	private ValidationResultDTO validateBundle2gNum(OrderCaptureDTO orderCapture, LtsBasketVasDetailFormDTO form) {
		
		if (orderCapture.getSelectedBasket() == null || StringUtils.isEmpty(orderCapture.getSelectedBasket().getBundle2G())
				|| LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType())) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
		
		List<String> errorMsgList = new ArrayList<String>();
		
		if (StringUtils.isEmpty(form.getBundle2gNum())) {
			errorMsgList.add(messageSource.getMessage("lts.basketVasDetail.2Gbundle", new Object[] {}, this.locale));
		}
		else {
			if (!(form.getBundle2gNum().length() == 8 || form.getBundle2gNum().length() == 12) 
					|| !ltsCommonService.isMobileNumPrefix(form.getBundle2gNum())) {
				errorMsgList.add(messageSource.getMessage("lts.basketVasDetail.invalid", new Object[] {}, this.locale));
			}
		}
		
		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}
	
	
	private ValidationResultDTO validateFfpItem(LtsBasketVasDetailFormDTO form) {
		
		List<ItemDetailDTO> allFfpItemList = new ArrayList<ItemDetailDTO>();
		if (form.getFfpHotItemList() != null && !form.getFfpHotItemList().isEmpty()) {
			allFfpItemList.addAll(form.getFfpHotItemList());
		}
		if (form.getFfpOtherItemList() != null && !form.getFfpOtherItemList().isEmpty()) {
			allFfpItemList.addAll(form.getFfpOtherItemList());
		}
		if (form.getFfpStaffItemList() != null && !form.getFfpStaffItemList().isEmpty()) {
			allFfpItemList.addAll(form.getFfpStaffItemList());
		}
		
		if (allFfpItemList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
	
		
		List<String> errorMsgList = new ArrayList<String>();
		Map<String, ItemDetailDTO> callPlanCdMap = new HashMap<String, ItemDetailDTO>();
		List<ItemDetailDTO> callPlanGiftList = new ArrayList<ItemDetailDTO>();
		for (ItemDetailDTO ffpItem : allFfpItemList) {
			if (!ffpItem.isSelected()) {
				continue;
			}

			String[] itemCallPlanCds = callPlanLtsService.getCallPlanCd(ffpItem.getItemId());
			if (ArrayUtils.isEmpty(itemCallPlanCds)) {
				continue;
			}
			
			for (String itemCallPlanCd : itemCallPlanCds) {
				if (callPlanCdMap.containsKey(itemCallPlanCd)) {
					errorMsgList.add(messageSource.getMessage("lts.basketVasDetail.errorMsg", new Object[] {callPlanCdMap.get(itemCallPlanCd).getItemDisplayHtml(), ffpItem.getItemDisplayHtml()}, this.locale));
					/*"Cannot select <br /><br /> "
							+ callPlanCdMap.get(itemCallPlanCd).getItemDisplayHtml().split("<br />")[0]
							+ " <br /><br /> and <br /><br /> "
							+ ffpItem.getItemDisplayHtml().split("<br />")[0] + " <br /><br /> together.")*/
					break;
				}
				callPlanCdMap.put(itemCallPlanCd, ffpItem);
				
				IddCallPlanProfileLtsDTO[] callPlanInfo = callPlanLtsService.mapIddCallPlan(new String[] {itemCallPlanCd});
				if (ArrayUtils.isNotEmpty(callPlanInfo)) {
					if (StringUtils.equals("Y", callPlanInfo[0].getGiftInd())){
						if (!callPlanGiftList.isEmpty()) {
							errorMsgList.add(messageSource.getMessage("lts.basketVasDetail.multGiftCd", new Object[] {callPlanGiftList.get(0).getItemDisplayHtml(), ffpItem.getItemDisplayHtml()}, this.locale));
								   /*"Cannot select <br /><br /> "
									+ callPlanGiftList.get(0).getItemDisplayHtml().split("<br />")[0]
									+ " <br /><br /> and <br /><br /> "
									+ ffpItem.getItemDisplayHtml().split("<br />")[0] + " <br /><br /> together. (Multiple gift code))");*/
							break;
						}
						callPlanGiftList.add(ffpItem);
					}
				}
			}
		}

		if (errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
		return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
	}
	
	// ## This logic removed on 3-Nov-2017 as request by marketing (Tam, Judy WY)
	// For upgrade order (except 2nd contract & fault case)
	// - If “Effective Price” column is not blank, system will enable “Drop ARPU checking” and use the value of effective price to count the new TP ARPU.
	// - If it is blank, system will disable the “Drop ARPU checking”.
	private ValidationResultDTO validateUpgradeBasketArpuDecrease(OrderCaptureDTO orderCapture, LtsBasketVasDetailFormDTO form) {
		
		BasketDetailDTO selectedBasket = orderCapture.getSelectedBasket();
		
		if (!LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())
				|| selectedBasket == null
				|| StringUtils.isBlank(selectedBasket.getEffectivePrice())) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
		
		
		double profileArpu =  LtsSbOrderHelper.getProfileArpu(orderCapture);
		double newArpu =  LtsSbOrderHelper.getNewArpu(orderCapture, orderCapture.getLtsBasketServiceForm(), form);
		double profileReduceArpu =  LtsSbOrderHelper.getCancelProfileItemAmt(form);
		
		if ((newArpu - profileReduceArpu) < profileArpu) {
			return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.basketVasDetail.ARPU", new Object[] {}, this.locale)), null);
		}
		return new ValidationResultDTO(Status.VALID, null, null); 
	}
	
	
	private ValidationResultDTO validateBasketArpuDecrease(OrderCaptureDTO orderCapture, LtsBasketVasDetailFormDTO form) {
		
		BasketDetailDTO selectedBasket = orderCapture.getSelectedBasket();
		
		if (selectedBasket == null
				|| ! (LtsConstant.EYE_FAULT_CASE_PROJECT_CD_MAP.containsValue(selectedBasket.getProjectCd()))
				|| LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType()) ) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
		
		
		double profileArpu =  LtsSbOrderHelper.getProfileArpu(orderCapture);
		double newArpu =  LtsSbOrderHelper.getNewArpu(orderCapture, orderCapture.getLtsBasketServiceForm(), form);
		double profileReduceArpu =  LtsSbOrderHelper.getCancelProfileItemAmt(form);
		
		if ((newArpu - profileReduceArpu) < profileArpu) {
			return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.basketVasDetail.ARPU", new Object[] {}, this.locale)), null);
		}
		return new ValidationResultDTO(Status.VALID, null, null); 
	}
	
	
	private ValidationResultDTO validate2ndContractArpuDecrease(OrderCaptureDTO orderCapture, LtsBasketVasDetailFormDTO form) {
		
		BasketDetailDTO selectedBasket = orderCapture.getSelectedBasket();
		
		if (!LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())
				|| selectedBasket == null 
				|| !LtsConstant.BASKET_PROJECT_CD_2ND_CONTRACT.equals(selectedBasket.getProjectCd())) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
		
		double profileArpu =  LtsSbOrderHelper.getProfileArpu(orderCapture);
		double newArpu =  LtsSbOrderHelper.getNewArpu(orderCapture, orderCapture.getLtsBasketServiceForm(), form);
		double profileReduceArpu =  LtsSbOrderHelper.getCancelProfileItemAmt(form);
		
		if ((newArpu - profileReduceArpu) < profileArpu) {
			return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.basketVasDetail.ARPU", new Object[] {}, this.locale)), null);
		}
		return new ValidationResultDTO(Status.VALID, null, null); 
	}
	
	
	private ValidationResultDTO validateRenewalArpuDecrease(OrderCaptureDTO orderCapture, LtsBasketVasDetailFormDTO form, BomSalesUserDTO bomSalesUser) {
		
		if (!LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
				|| LtsConstant.EYE_TYPE_EYE3A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())
				|| LtsConstant.EYE_TYPE_EYE2A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())
				|| LtsConstant.TEAM_AREA_CD_DEL_RETENTION.equalsIgnoreCase(bomSalesUser.getAreaCd())
				|| orderCapture.isChannelPremier()
				|| StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())
				|| LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType())) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
			
		
		double profileArpu =  LtsSbOrderHelper.getProfileArpu(orderCapture);
		double newArpu =  LtsSbOrderHelper.getNewArpu(orderCapture, orderCapture.getLtsBasketServiceForm(), form);
		double profileReduceArpu =  LtsSbOrderHelper.getCancelProfileItemAmt(form);
		
		String toProd = StringUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getExistEyeType()) ? orderCapture.getLtsServiceProfile().getExistEyeType() : "DEL" ;
		double existTpArpu = LtsSbOrderHelper.getRenewalProfileTpArpu(orderCapture.getLtsServiceProfile());
		double newTpApru = LtsSbOrderHelper.getNewTpArpu(orderCapture);
		
		boolean isAllowArpuChange = ltsBasketService.isAllowArpuChange(toProd, String.valueOf(existTpArpu), String.valueOf(newTpApru));
		
		if ((newArpu - profileReduceArpu) < profileArpu && !isAllowArpuChange) {
			return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.basketVasDetail.ARPU", new Object[] {}, this.locale)), null);
		}
		
		return new ValidationResultDTO(Status.VALID, null, null);
	}
	
	private ValidationResultDTO validateApprovalRemark(OrderCaptureDTO orderCapture, LtsBasketVasDetailFormDTO form) {
		
		if (orderCapture.getLtsWqRemarkForm() != null) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}
		
		String errorMsg = "Please input approval remark.";
		
		if (form.getProfileAutoOutVasItemList() != null && !form.getProfileAutoOutVasItemList().isEmpty()) {
			 for (ItemDetailProfileLtsDTO profileItemDetail : form.getProfileAutoOutVasItemList()) {
				 if (profileItemDetail.getItemDetail() != null && 
						 LtsConstant.OFFER_HANDLE_MANUAL_WAIVE.equals(profileItemDetail.getItemDetail().getPenaltyHandling())) {
					 return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(errorMsg), null);
				 }
			 }
		}
		
		if (form.getImsProfileAutoOutItemList() != null && !form.getImsProfileAutoOutItemList().isEmpty()) {
			 for (ItemDetailProfileLtsDTO profileItemDetail : form.getImsProfileAutoOutItemList()) {
				 if (profileItemDetail.getItemDetail() != null && 
						 LtsConstant.OFFER_HANDLE_MANUAL_WAIVE.equals(profileItemDetail.getItemDetail().getPenaltyHandling())) {
					 return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(errorMsg), null);
				 }
			 }
		}
		
		return new ValidationResultDTO(Status.VALID, null, null);
	}
	
	
	
	private ValidationResultDTO validateAttbParam(LtsBasketVasDetailFormDTO form) {
		
		List<ItemDetailDTO> allItemDetailList = new ArrayList<ItemDetailDTO>();
		
		if (form.getHotVasItemList() != null && !form.getHotVasItemList().isEmpty()) {
			allItemDetailList.addAll(form.getHotVasItemList());
		}
		
		if (form.getOtherVasItemList() != null && !form.getOtherVasItemList().isEmpty()) {
			allItemDetailList.addAll(form.getOtherVasItemList());
		}
		
		return ltsOfferService.validateItemAttb(allItemDetailList); 
		
	}
	
	
	private List<ItemDetailDTO> getAllValidateExclusiveItem(OrderCaptureDTO orderCapture,
			LtsBasketVasDetailFormDTO form) {
		
		List<ItemDetailDTO> validateItemDetailList = new ArrayList<ItemDetailDTO>();

		if (form.getHotVasItemList() != null && !form.getHotVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getHotVasItemList());
		}
		if (form.getOtherVasItemList() != null && !form.getOtherVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getOtherVasItemList());
		}
		if (form.getE0060OutVasItemList() != null && !form.getE0060OutVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getE0060OutVasItemList());
		}
		if (form.getE0060VasItemList() != null && !form.getE0060VasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getE0060VasItemList());
		}
		if (form.getIddOutVasItemList() != null && !form.getIddOutVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getIddOutVasItemList());
		}
		if (form.getIddVasItemList() != null && !form.getIddVasItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getIddVasItemList());
		}
		if (form.getPeFreeItemList() != null && !form.getPeFreeItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getPeFreeItemList());
		}
		if (form.getPeSocketItemList() != null && !form.getPeSocketItemList().isEmpty()) {
			validateItemDetailList.addAll(form.getPeSocketItemList());
		}
		if (form.getTeamVasSetList() != null && !form.getTeamVasSetList().isEmpty()) {
			for (ItemSetDetailDTO teamVasItemSet : form.getTeamVasSetList()) {
				if (ArrayUtils.isNotEmpty(teamVasItemSet.getItemDetails())) {
					validateItemDetailList.addAll(Lists.newArrayList(teamVasItemSet.getItemDetails()));	
				}
			}
		}
		if (form.getBundleVasSetList() != null && !form.getBundleVasSetList().isEmpty()) {
			for (ItemSetDetailDTO bundleVasItemSet : form.getBundleVasSetList()) {
				if (ArrayUtils.isNotEmpty(bundleVasItemSet.getItemDetails())) {
					validateItemDetailList.addAll(Lists.newArrayList(bundleVasItemSet.getItemDetails()));	
				}
			}
		}
		
		addProfileItemToItemList(form.getProfileAutoChangeTpItemList(), validateItemDetailList);
		addProfileItemToItemList(form.getProfileAutoOutVasItemList(), validateItemDetailList);
		
		return validateItemDetailList;
	}
	
	
	private ValidationResultDTO validateExclusiveItem(
			HttpServletRequest request, OrderCaptureDTO orderCapture,
			LtsBasketVasDetailFormDTO form) {
				
		List<ItemDetailDTO> validateExclusiveList = getAllValidateExclusiveItem(orderCapture, form); 
		 return ltsOfferService.validateExclusiveItem(validateExclusiveList, validateExclusiveList,
				RequestContextUtils.getLocale(request).toString());
	}
	
	
	private List<ItemDetailDTO> getProfileOrBasketDefaultItemList(OrderCaptureDTO orderCapture, LtsBasketVasDetailFormDTO form) {
		List<ItemDetailDTO> resultList = new ArrayList<ItemDetailDTO>();
		
		if (orderCapture.getLtsBasketServiceForm().getBvasItemList() != null && 
				!orderCapture.getLtsBasketServiceForm().getBvasItemList().isEmpty()) {
			resultList.addAll(orderCapture.getLtsBasketServiceForm().getBvasItemList());
		}
		if (orderCapture.getLtsBasketServiceForm() != null 
				&& orderCapture.getLtsBasketServiceForm().getPlanItemList() != null
				&& !orderCapture.getLtsBasketServiceForm().getPlanItemList().isEmpty()) {
			resultList.addAll(orderCapture.getLtsBasketServiceForm().getPlanItemList());
		}
		addProfileItemToItemList(form.getProfileExistingTpItemList(), resultList);
		addProfileItemToItemList(form.getProfileExistingVasItemList(), resultList);
		
		return resultList;
	}
	
	private void filterIddExclusiveItem(HttpServletRequest request, OrderCaptureDTO orderCapture, LtsBasketVasDetailFormDTO form) {
		List<ItemDetailDTO> itemDetailListA = new ArrayList<ItemDetailDTO>();
		List<ItemDetailDTO> itemDetailListB = new ArrayList<ItemDetailDTO>();
		
		if (ArrayUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getItemDetails())) {
			for (ItemDetailProfileLtsDTO profileItem : orderCapture.getLtsServiceProfile().getItemDetails()) {
				if ((LtsConstant.ITEM_TYPE_SERVICE.equals(profileItem.getItemType())
						|| LtsConstant.ITEM_TYPE_VAS.equals(profileItem.getItemType()))
							&& profileItem.getItemDetail() != null) {
					itemDetailListA.add(profileItem.getItemDetail());
				}
			}
		}
		
		if (form.getIddVasItemList() != null && !form.getIddVasItemList().isEmpty()) {
			itemDetailListB.addAll(form.getIddVasItemList());
		}
		if (form.getIddOutVasItemList() != null && !form.getIddOutVasItemList().isEmpty()) {
			itemDetailListB.addAll(form.getIddVasItemList());
		}
		
		List<ExclusiveItemDetailDTO> exclusiveItemDetailList = ltsOfferService
		.getExclusiveItemDetailList(itemDetailListA, itemDetailListB,
				RequestContextUtils.getLocale(request).toString(), false);
		
		if (exclusiveItemDetailList == null || exclusiveItemDetailList.isEmpty()) {
			return;
		}
		
		for (ExclusiveItemDetailDTO exclusiveItemDetail : exclusiveItemDetailList) {
			form.setIddVasItemList(filterExclusiveItem(form.getIddVasItemList(), exclusiveItemDetail));
			form.setIddOutVasItemList(filterExclusiveItem(form.getIddOutVasItemList(), exclusiveItemDetail));
		}
	}
	
	
	private void removeExclusiveItem (HttpServletRequest request, OrderCaptureDTO orderCapture, LtsBasketVasDetailFormDTO form) {
		
		List<ItemDetailDTO> itemDetailListA = getProfileOrBasketDefaultItemList(orderCapture, form);
		List<ItemDetailDTO> itemDetailListB = getAllValidateExclusiveItem(orderCapture, form);

		List<ExclusiveItemDetailDTO> exclusiveItemDetailList = ltsOfferService
				.getExclusiveItemDetailList(itemDetailListA, itemDetailListB,
						RequestContextUtils.getLocale(request).toString(), false);
		
		if (exclusiveItemDetailList == null || exclusiveItemDetailList.isEmpty()) {
			return;
		}
		
		for (ExclusiveItemDetailDTO exclusiveItemDetail : exclusiveItemDetailList) {
			
			form.setHotVasItemList(filterExclusiveItem(form.getHotVasItemList(), exclusiveItemDetail));
			form.setOtherVasItemList(filterExclusiveItem(form.getOtherVasItemList(), exclusiveItemDetail));
			form.setIddVasItemList(filterExclusiveItem(form.getIddVasItemList(), exclusiveItemDetail));
			form.setIddOutVasItemList(filterExclusiveItem(form.getIddOutVasItemList(), exclusiveItemDetail));
			form.setE0060VasItemList(filterExclusiveItem(form.getE0060VasItemList(), exclusiveItemDetail));
			form.setE0060OutVasItemList(filterExclusiveItem(form.getE0060OutVasItemList(), exclusiveItemDetail));
			form.setProfileAutoChangeTpItemList(filterExclusiveProfileItem(form.getProfileAutoChangeTpItemList(), exclusiveItemDetail));
			
			if (form.getTeamVasSetList() != null && !form.getTeamVasSetList().isEmpty()) {
				for(ItemSetDetailDTO teamVasSet : form.getTeamVasSetList()) {
					if (ArrayUtils.isNotEmpty(teamVasSet.getItemDetails())) {
						List<ItemDetailDTO> teamVasList = Arrays.asList(teamVasSet.getItemDetails());
						List<ItemDetailDTO> filteredList = filterExclusiveItem(teamVasList, exclusiveItemDetail);
						teamVasSet.setItemDetails(filteredList.toArray(new ItemDetailDTO[filteredList.size()]));
					}
				}
			}
			if (form.getBundleVasSetList()!= null && !form.getBundleVasSetList().isEmpty()) {
				for(ItemSetDetailDTO bundleVasSet : form.getBundleVasSetList()) {
					if (ArrayUtils.isNotEmpty(bundleVasSet.getItemDetails())) {
						List<ItemDetailDTO> bundleVasList = Arrays.asList(bundleVasSet.getItemDetails());
						List<ItemDetailDTO> filteredList = filterExclusiveItem(bundleVasList, exclusiveItemDetail);
						bundleVasSet.setItemDetails(filteredList.toArray(new ItemDetailDTO[filteredList.size()]));
					}
				}
			}
			
		}
		
	}

	
	
	private List<ItemDetailProfileLtsDTO> filterExclusiveProfileItem(
			List<ItemDetailProfileLtsDTO> profileItemDetailList,
			ExclusiveItemDetailDTO exclusiveItemDetail) {

		
		if (profileItemDetailList == null || profileItemDetailList.isEmpty()) {
			return null;
		}
		
		List<ItemDetailProfileLtsDTO> newProfileItemDetailList = new ArrayList<ItemDetailProfileLtsDTO>();
		
		for (ItemDetailProfileLtsDTO profileItemDetail : profileItemDetailList) {
			
			if (profileItemDetail.getItemDetail() == null	
					|| StringUtils.equals(exclusiveItemDetail.getItemAId(), profileItemDetail.getItemDetail().getItemId())
					|| StringUtils.equals(exclusiveItemDetail.getItemBId(), profileItemDetail.getItemDetail().getItemId())) {
				continue;
			}
			newProfileItemDetailList.add(profileItemDetail);
		}
		return newProfileItemDetailList;
		
	}
	
	private List<ItemDetailDTO> filterExclusiveItem(List<ItemDetailDTO> itemDetailList, ExclusiveItemDetailDTO exclusiveItemDetail) {
		
		if (itemDetailList == null || itemDetailList.isEmpty()) {
			return null;
		}
		
		List<ItemDetailDTO> newItemDetailList = new ArrayList<ItemDetailDTO>();
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (StringUtils.equals(exclusiveItemDetail.getItemAId(), itemDetail.getItemId()) ||
					StringUtils.equals(exclusiveItemDetail.getItemBId(), itemDetail.getItemId())) {
				continue;
			}
			newItemDetailList.add(itemDetail);
		}
		return newItemDetailList;
	}
	
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		String locale = RequestContextUtils.getLocale(request).toString();
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		String selectedBasketId = orderCapture.getLtsBasketSelectionForm().getSelectedBasketId();
		BasketDetailDTO selectedBasketDetail = orderCapture.getSelectedBasket();
		LtsBasketVasDetailFormDTO form = orderCapture.getLtsBasketVasDetailForm();
		
		if (form == null) {
			form = new LtsBasketVasDetailFormDTO();
			if(LtsSbOrderHelper.isStandaloneVasOrder(orderCapture)){
				initStandaloneVasOrderForm(form, orderCapture, selectedBasketId, selectedBasketDetail, locale, bomSalesUser);
			}else{
				initForm(form, orderCapture, selectedBasketId, selectedBasketDetail, locale, bomSalesUser);
				setFreeVasItem(form, orderCapture, selectedBasketDetail, locale, bomSalesUser);
			}

			if(CollectionUtils.isNotEmpty(form.getFfpHotItemList())
					|| CollectionUtils.isNotEmpty(form.getFfpOtherItemList())
					|| CollectionUtils.isNotEmpty(form.getFfpStaffItemList())){
				form.setHasFfp(true);
			}
			form.setMth12Ffp(true);
			form.setMth18Ffp(true);
			form.setMth24Ffp(true);
		}
		filterIddExclusiveItem(request, orderCapture, form);
		
		setProfileTpVasItem(form, orderCapture, selectedBasketDetail, locale);
		setFsaProfileItem(form, orderCapture, selectedBasketDetail);
		removeExclusiveItem(request, orderCapture, form);
		filterBasketExclusiveItemByPsef(orderCapture, form);
		
		//BOM2018021 Do not check force retain for recontract order 
		if(!LtsSbOrderHelper.isRecontractOrder(orderCapture)){
			filterForceRetainExclusiveItemByPsef(form);
		}
		
		return form;
	}
	
	
	private String[] getAllSelectedItemIds(OrderCaptureDTO orderCapture, LtsBasketVasDetailFormDTO form) {
		List<String> selectedItemIdList = new ArrayList<String>();
		
		// BasketServiceForm
		addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getPlanItemList(), selectedItemIdList);
		addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getAdminChargeItemList(), selectedItemIdList);
		addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getBbRentalItemList(), selectedItemIdList);
		addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getCancelChargeItemList(), selectedItemIdList);
		addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getContentItemList(), selectedItemIdList);
		addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getInstallItemList(), selectedItemIdList);
		addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getMoovItemList(), selectedItemIdList);
		
		if (orderCapture.getLtsBasketServiceForm().getContItemSetList() != null && !orderCapture.getLtsBasketServiceForm().getContItemSetList().isEmpty()) {
			for(ItemSetDetailDTO itemSetDetail : orderCapture.getLtsBasketServiceForm().getContItemSetList()) {
				if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
					addSelectedItemIds(Lists.newArrayList(itemSetDetail.getItemDetails()), selectedItemIdList);	
				}
			}
		}
		
		// NowtvServiceForm

		if (orderCapture.getLtsNowTvServiceForm() != null) {
			addSelectedItemIds(orderCapture.getLtsNowTvServiceForm().getNowTvCeleItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsNowTvServiceForm().getNowTvDeviceItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsNowTvServiceForm().getNowTvEmailItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsNowTvServiceForm().getNowTvFreeItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsNowTvServiceForm().getNowTvMirrorItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsNowTvServiceForm().getNowTvPayItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsNowTvServiceForm().getNowTvPrintItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsNowTvServiceForm().getNowTvSpecItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsNowTvServiceForm().getNowTvSportItemList(), selectedItemIdList);	
		}

		addSelectedItemIds(form.getBvasItemList(), selectedItemIdList);
		addSelectedItemIds(form.getHotVasItemList(), selectedItemIdList);
		addSelectedItemIds(form.getOtherVasItemList(), selectedItemIdList);
		addSelectedItemIds(form.getE0060VasItemList(), selectedItemIdList);
		addSelectedItemIds(form.getE0060VasItemList(), selectedItemIdList);
		addSelectedItemIds(form.getIddVasItemList(), selectedItemIdList);
		addSelectedItemIds(form.getIddOutVasItemList(), selectedItemIdList);
		addSelectedItemIds(form.getExistVasItemList(), selectedItemIdList);
		addSelectedItemIds(form.getOptAccessaryItemList(), selectedItemIdList);
		addSelectedItemIds(form.getPeFreeItemList(), selectedItemIdList);
		addSelectedItemIds(form.getPeSocketItemList(), selectedItemIdList);
		
		
		if (form.getBundleVasSetList() != null && !form.getBundleVasSetList().isEmpty()) {
			for(ItemSetDetailDTO itemSetDetail : form.getBundleVasSetList()) {
				if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
					addSelectedItemIds(Lists.newArrayList(itemSetDetail.getItemDetails()), selectedItemIdList);	
				}
			}
		}
		
		
		if (form.getTeamVasSetList() != null && !form.getTeamVasSetList().isEmpty()) {
			for(ItemSetDetailDTO itemSetDetail : form.getTeamVasSetList()) {
				if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
					addSelectedItemIds(Lists.newArrayList(itemSetDetail.getItemDetails()), selectedItemIdList);	
				}
			}
		}
		
		if (form.getProfileAutoChangeTpItemList() != null && !form.getProfileAutoChangeTpItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO freeVasItem : form.getProfileAutoChangeTpItemList()){
				if (freeVasItem.getItemDetail() != null 
						&& StringUtils.isNotBlank(freeVasItem.getItemDetail().getItemId()) 
						&& freeVasItem.getItemDetail().isSelected()) {
					selectedItemIdList.add(freeVasItem.getItemDetail().getItemId());
				}
			}
		}

		if (!selectedItemIdList.isEmpty()) {
			return selectedItemIdList.toArray(new String[selectedItemIdList.size()]);
		}
		return null;
	}
	
	private void addSelectedItemIds(List<ItemDetailDTO> itemDetailList, List<String> selectedItemIdList) {
		
		if (itemDetailList == null || itemDetailList.isEmpty()) {
			return;
		}
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (itemDetail.isSelected()) {
				selectedItemIdList.add(itemDetail.getItemId());
			}
		}
	}

	private List<String> getProfilePsefCdList(ServiceDetailProfileLtsDTO serviceProfile) {
		ItemDetailProfileLtsDTO[] profileItems = serviceProfile.getItemDetails();
		if (ArrayUtils.isEmpty(profileItems)) {
			return null;
		}
		List<String> profilePsefCdList = new ArrayList<String>();
		for (ItemDetailProfileLtsDTO profileItem : profileItems) {
			OfferDetailProfileLtsDTO[] profileOffers = profileItem.getOffers();
			if (ArrayUtils.isEmpty(profileOffers)) {
				continue;
			}
			for (OfferDetailProfileLtsDTO profileOffer : profileOffers) {
				if (profileOffer.getPsefSet() != null && !profileOffer.getPsefSet().isEmpty()) {
					profilePsefCdList.addAll(profileOffer.getPsefSet());
				}
			}
		}
		return profilePsefCdList;
	}

	private List<String> getForceRetainOfferPsefCd(List<ItemDetailProfileLtsDTO> profileItemList) {
		
		List<String> psefList = new ArrayList<String>();
		if (profileItemList == null || profileItemList.isEmpty()) {
			return psefList;
		}
		
		for (ItemDetailProfileLtsDTO profileItem : profileItemList) {
			if (!profileItem.isForceRetain()) {
				continue;
			}
			if (ArrayUtils.isNotEmpty(profileItem.getOffers())) {
				for (OfferDetailProfileLtsDTO offerDetail : profileItem.getOffers()) {
					if (ArrayUtils.isNotEmpty(offerDetail.getPsefs())) {
						psefList.addAll(Lists.newArrayList(offerDetail.getPsefs()));
					}
					
				}
			}
		}
		return psefList;
	}
	
	private List<String> getItemPsefCd(List<ItemDetailDTO> itemList) {
		
		List<String> psefList = new ArrayList<String>();
		if (itemList == null || itemList.isEmpty()) {
			return psefList;
		}
		
		for (ItemDetailDTO item : itemList) {
			if (!item.isSelected()) {
				continue;
			}
			String[] offerIds = offerItemService.getOffersByItemId(item.getItemId());	
			if (ArrayUtils.isEmpty(offerIds)) {
				continue;
			}
			List<String> psefCdList = offerProfileLtsService.getPsefCdByOfferId(Lists.newArrayList(offerIds));
			if (psefCdList == null || psefCdList.isEmpty()) {
				continue; 
			}
			psefList.addAll(psefCdList);
		}
		return psefList;
	}
	
	private void filterBasketExclusiveItemByPsef(OrderCaptureDTO orderCapture, LtsBasketVasDetailFormDTO form) {
		List<String> basketOfferPsefList = new ArrayList<String>();
		if (orderCapture.getLtsBasketServiceForm() != null && orderCapture.getLtsBasketServiceForm().getPlanItemList() != null) {
			basketOfferPsefList.addAll(getItemPsefCd(orderCapture.getLtsBasketServiceForm().getPlanItemList()));	
		}
		if (orderCapture.getLtsBasketServiceForm() != null && orderCapture.getLtsBasketServiceForm().getPlanItemList() != null) {
			basketOfferPsefList.addAll(getItemPsefCd(orderCapture.getLtsBasketServiceForm().getBvasItemList()));
		}
		if (basketOfferPsefList.isEmpty()) {
			return;
		}
		
		form.setHotVasItemList(filterItemDetailListByPsef(form.getHotVasItemList(), basketOfferPsefList));
		form.setOtherVasItemList(filterItemDetailListByPsef(form.getOtherVasItemList(), basketOfferPsefList));
		filterItemSetDetailListByPsef(form.getTeamVasSetList(), basketOfferPsefList);
		filterItemSetDetailListByPsef(form.getBundleVasSetList(), basketOfferPsefList);
	}
	
	private void filterForceRetainExclusiveItemByPsef(LtsBasketVasDetailFormDTO form) {
		
		List<String> forceRetainOfferPsefList = new ArrayList<String>();
		if (form.getProfileExistingVasItemList() != null) {
			forceRetainOfferPsefList.addAll(getForceRetainOfferPsefCd(form.getProfileExistingVasItemList()));
		}
		if (form.getProfileAutoOutVasItemList() != null) {
			forceRetainOfferPsefList.addAll(getForceRetainOfferPsefCd(form.getProfileAutoOutVasItemList()));
		}
		if (forceRetainOfferPsefList.isEmpty()) {
			return;
		}
		form.setHotVasItemList(filterItemDetailListByPsef(form.getHotVasItemList(), forceRetainOfferPsefList));
		form.setOtherVasItemList(filterItemDetailListByPsef(form.getOtherVasItemList(), forceRetainOfferPsefList));
		filterItemSetDetailListByPsef(form.getTeamVasSetList(), forceRetainOfferPsefList);
		filterItemSetDetailListByPsef(form.getBundleVasSetList(), forceRetainOfferPsefList);
	}
	
	private void filterItemSetDetailListByPsef(List<ItemSetDetailDTO> itemSetDetailList, List<String> psefCdList) {
		if (itemSetDetailList != null && !itemSetDetailList.isEmpty()) {
			for(ItemSetDetailDTO teamVasSet : itemSetDetailList) {
				if (ArrayUtils.isNotEmpty(teamVasSet.getItemDetails())) {
					List<ItemDetailDTO> filteredItemDetailList = filterItemDetailListByPsef(Lists.newArrayList(teamVasSet.getItemDetails()), psefCdList);
					teamVasSet.setItemDetails(filteredItemDetailList.toArray(new ItemDetailDTO[filteredItemDetailList.size()]));
				}
			}
		}
	}
	
	private List<ItemDetailDTO> filterItemDetailListByPsef(List<ItemDetailDTO> itemDetailList, List<String> psefCdList) {
		
		if (itemDetailList == null || itemDetailList.isEmpty() 
				|| psefCdList == null || psefCdList.isEmpty()) {
			return itemDetailList;
		}
		
		List<ItemDetailDTO> filteredList = new ArrayList<ItemDetailDTO>();
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			filteredList.add(itemDetail);
			
			String[] offerIds = offerItemService.getOffersByItemId(itemDetail.getItemId());	
			if (ArrayUtils.isEmpty(offerIds)) {
				continue;
			}

			List<String> offerPsefCdList = offerProfileLtsService.getPsefCdByOfferId(Lists.newArrayList(offerIds));
			if (psefCdList == null || psefCdList.isEmpty()) {
				continue; 
			}
			
			for (String psefCd : offerPsefCdList) {
				if (psefCdList.contains(psefCd)) {
					filteredList.remove(itemDetail);
					break;
				}
			}
		}
		return filteredList;
	}
	
	private void initStandaloneVasOrderForm(LtsBasketVasDetailFormDTO form,
			OrderCaptureDTO orderCapture, String selectedBasketId,
			BasketDetailDTO selectedBasketDetail, String locale,BomSalesUserDTO bomSalesUser) {

		String deviceType = orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType();
		String applicationDate = orderCapture.getLtsMiscellaneousForm().getApplicationDate();
		String eligibleDocType = LtsSbOrderHelper.getTargetCustomerDocType(orderCapture);

		ItemSetCriteriaDTO bundleVasSetCriteria = LtsSbOrderHelper.getItemSetCriteria(orderCapture, bomSalesUser, LtsConstant.ITEM_SET_TYPE_BUNDLE_VAS, locale);
		bundleVasSetCriteria.setProfileArpu(LtsSbOrderHelper.getProfileArpu(orderCapture));
		List<ItemSetDetailDTO> bundleVasItemList = ltsOfferService.getBasketItemSetList(bundleVasSetCriteria);		
		form.setBundleVasSetList(bundleVasItemList);
		List<String> bundleVasItemIdList = ltsOfferService.getAllItemSetItemId(bundleVasItemList);
		
		ItemCriteriaDTO defaultCriteria = LtsSbOrderHelper.getItemCriteriaDefaultBuilder(orderCapture, locale)
				.setEligibleDocType(eligibleDocType).build();
		
		List<ItemDetailDTO> hotVasItemList 		= ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_VAS_HOT).setEligibleDevice(deviceType).build());
		List<ItemDetailDTO> otherVasItemList 	= ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_VAS_OTHER).setEligibleDevice(deviceType).build());
		List<ItemDetailDTO> ffpHotItemList 		= ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_FFP_HOT).setCampaignCd(getIddCampaignCode(orderCapture)).build());
		List<ItemDetailDTO> ffpOtherItemList 	= ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_FFP_OTHER).setCampaignCd(getIddCampaignCode(orderCapture)).build());
		form.setHotVasItemList(ltsOfferService.filterOutItemById(hotVasItemList, bundleVasItemIdList));
		form.setOtherVasItemList(ltsOfferService.filterOutItemById(otherVasItemList, bundleVasItemIdList));
		form.setFfpHotItemList(ltsOfferService.filterOutItemById(ffpHotItemList, bundleVasItemIdList));
		form.setFfpOtherItemList(ltsOfferService.filterOutItemById(ffpOtherItemList, bundleVasItemIdList));
		
		if(StringUtils.equals(deviceType, LtsConstant.DEVICE_TYPE_EYE3A)){
			List<ItemDetailDTO> moovItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_MOOV, locale, true, orderCapture.getBasketChannelId(), applicationDate);
			List<ItemDetailDTO> contentItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_CONTENT, locale, true, orderCapture.getBasketChannelId(), applicationDate);
			List<ItemSetDetailDTO> contItemSetList = ltsOfferService.getBasketItemSetList(LtsSbOrderHelper.getItemSetCriteria(orderCapture, bomSalesUser, LtsConstant.ITEM_SET_TYPE_CONT_SET, locale));

			form.setMoovItemList(moovItemList);
			form.setContentItemList(contentItemList);
			
			if (contItemSetList != null && !contItemSetList.isEmpty() && ArrayUtils.isNotEmpty(contItemSetList.get(0).getItemDetails())) {
				form.setContItemSetList(contItemSetList);	
			}
		}
		
		List<String> profilePsefCdList = getProfilePsefCdList(orderCapture.getLtsServiceProfile());
		
		form.setHotVasItemList(filterItemDetailListByPsef(form.getHotVasItemList(), profilePsefCdList));
		form.setOtherVasItemList(filterItemDetailListByPsef(form.getOtherVasItemList(), profilePsefCdList));
		form.setMoovItemList(filterItemDetailListByPsef(form.getMoovItemList(), profilePsefCdList));
		form.setContentItemList(filterItemDetailListByPsef(form.getContentItemList(), profilePsefCdList));
		
		if (form.getBundleVasSetList() != null && !form.getBundleVasSetList().isEmpty()) {
			for(ItemSetDetailDTO itemSetDetail : form.getBundleVasSetList()) {
				if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
					List<ItemDetailDTO> itemDetailList = filterItemDetailListByPsef(Lists.newArrayList(itemSetDetail.getItemDetails()), profilePsefCdList);
					if (itemDetailList != null) {
						itemSetDetail.setItemDetails(itemDetailList.toArray(new ItemDetailDTO[itemDetailList.size()]));
					}
						
				}
			}
		}
		
		if (form.getContItemSetList() != null && !form.getContItemSetList().isEmpty()) {
			for(ItemSetDetailDTO itemSetDetail : form.getContItemSetList()) {
				if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
					List<ItemDetailDTO> itemDetailList = filterItemDetailListByPsef(Lists.newArrayList(itemSetDetail.getItemDetails()), profilePsefCdList);
					if (itemDetailList != null) {
						itemSetDetail.setItemDetails(itemDetailList.toArray(new ItemDetailDTO[itemDetailList.size()]));
					}	
				}
			}
		}
	}
	
	
	
	private void initForm(LtsBasketVasDetailFormDTO form,
			OrderCaptureDTO orderCapture, String selectedBasketId,
			BasketDetailDTO selectedBasketDetail, String locale,BomSalesUserDTO bomSalesUser) {
		
		String baseContractPeriod = selectedBasketDetail.getContractPeriod();
		if (StringUtils.isNotEmpty(selectedBasketDetail.getExtendContractPeriod())) {
			baseContractPeriod = String.valueOf(Integer.parseInt(baseContractPeriod) - Integer.parseInt(selectedBasketDetail.getExtendContractPeriod()));
		}
		
		String applicationDate = orderCapture.getLtsMiscellaneousForm().getApplicationDate();
		String eligibleDocType = LtsSbOrderHelper.getTargetCustomerDocType(orderCapture);
		String deviceType = null;
		if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())){
			if (LtsConstant.DEVICE_TYPE_EYE3A.equals(orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType())) {
				deviceType = LtsConstant.EYE_TYPE_EYE3A;
			}	
		}
		else {
			deviceType = StringUtils.defaultIfEmpty(orderCapture.getLtsServiceProfile().getExistEyeType(), "DEL");	
		}
		
		ItemCriteriaDTO defaultCriteria = LtsSbOrderHelper.getItemCriteriaDefaultBuilder(orderCapture, locale)
				.setEligibleDevice(deviceType)
				.setEligibleDocType(eligibleDocType).build();
		
		List<ItemDetailDTO> existVasItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_EXIST_VAS, locale, true, orderCapture.getBasketChannelId(), applicationDate);
		if (existVasItemList == null || existVasItemList.isEmpty()) {
			existVasItemList = ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_EXIST_VAS).setGetAttrInd(false).build());
		}
		
		List<ItemDetailDTO> hotVasItemList 		= ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_VAS_HOT).setBaseContractPeriod(baseContractPeriod).build());
		List<ItemDetailDTO> otherVasItemList 	= ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_VAS_OTHER).build());
		List<ItemDetailDTO> iddVasItemList 		= ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_IDD).setGetAttrInd(false).build());
		List<ItemDetailDTO> e0060VasItemList 	= ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_0060E).setGetAttrInd(false).build());
		
		List<ItemDetailDTO> peFreeVasItemList 	= ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_PE_FREE, locale, true, orderCapture.getBasketChannelId(), applicationDate);
		List<ItemDetailDTO> peSocketVasItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_PE_SOCKET, locale, true, orderCapture.getBasketChannelId(), applicationDate);
		List<ItemDetailDTO> optAccessaryItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_OPT_ACC, locale, true, orderCapture.getBasketChannelId(), applicationDate);
		List<ItemDetailDTO> bvasItemList = orderCapture.getLtsBasketServiceForm().getBvasItemList();
		List<ItemSetDetailDTO> smartWrtyItemList = ltsOfferService
				.getBasketItemSetList(LtsSbOrderHelper.getItemSetCriteria(orderCapture,
						bomSalesUser, LtsConstant.ITEM_SET_TYPE_SMART_WARRANTY, locale));
		form.setSmartWrtySetList(smartWrtyItemList);
		

		ItemSetCriteriaDTO bundleVasSetCriteria = LtsSbOrderHelper.getItemSetCriteria(orderCapture, bomSalesUser, LtsConstant.ITEM_SET_TYPE_BUNDLE_VAS, locale);
		bundleVasSetCriteria.setProfileArpu(LtsSbOrderHelper.getProfileArpu(orderCapture));
		List<ItemSetDetailDTO> bundleVasItemList = ltsOfferService.getBasketItemSetList(bundleVasSetCriteria);
		form.setBundleVasSetList(bundleVasItemList);
		
		List<String> bundleVasItemIdList = ltsOfferService.getAllItemSetItemId(bundleVasItemList);
		
		 // Back date order cannot subc FFP due to SRD = (effective start date)issue.
		if (!orderCapture.getLtsMiscellaneousForm().isBackDateOrder()) {
			String iddCampaignCd = getIddCampaignCode(orderCapture);
			List<ItemDetailDTO> ffpHotItemList 		= ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_FFP_HOT).setCampaignCd(iddCampaignCd).build());
			List<ItemDetailDTO> ffpOtherItemList 	= ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_FFP_OTHER).setCampaignCd(iddCampaignCd).build());
			form.setFfpHotItemList(ltsOfferService.filterOutItemById(ffpHotItemList, bundleVasItemIdList));
			form.setFfpOtherItemList(ltsOfferService.filterOutItemById(ffpOtherItemList, bundleVasItemIdList));
			if (orderCapture.getLtsCustomerInquiryForm().isUpgradeToStaffPlan()) {
				List<ItemDetailDTO> ffpStaffItemList = ltsOfferService.getItemList(new ItemCriteriaBuilder(defaultCriteria).setItemType(LtsConstant.ITEM_TYPE_FFP_STAFF).setCampaignCd(iddCampaignCd).build());
				form.setFfpStaffItemList(ltsOfferService.filterOutItemById(ffpStaffItemList, bundleVasItemIdList));
			}	
		}

		form.setExistVasItemList(existVasItemList);
		form.setHotVasItemList(ltsOfferService.filterOutItemById(hotVasItemList, bundleVasItemIdList));
		form.setOtherVasItemList(ltsOfferService.filterOutItemById(otherVasItemList, bundleVasItemIdList));
		form.setPeFreeItemList(peFreeVasItemList);
		form.setPeSocketItemList(peSocketVasItemList);
		form.setIddVasItemList(iddVasItemList);
		form.setE0060VasItemList(e0060VasItemList);
		form.setOptAccessaryItemList(optAccessaryItemList);
		form.setBvasItemList(bvasItemList);

	}
	
	private String getIddCampaignCode(OrderCaptureDTO orderCapture) {
		
		if (orderCapture.getLtsServiceProfile() == null || orderCapture.isChannelPremier()) {
			return null;
		}
		CustomerDetailProfileLtsDTO profileCustomer = orderCapture.getLtsServiceProfile().getPrimaryCust();
		
		List<CampaignDTO> iddCampaignList = customerProfileLtsService.getCampaign(LtsConstant.SYSTEM_ID_DRG, profileCustomer.getCustNum(), null, LtsConstant.CAMPAIGN_LOB_IDD);
		
		if (iddCampaignList == null || iddCampaignList.isEmpty()) {
			return "Z";
		}
		return StringUtils.defaultIfEmpty((String)ltsIddCampaignLkupCacheService.get((iddCampaignList.get(0)).getValuePropId()), "Z");
	}
	
	
	private void addProfileItemToItemList(List<ItemDetailProfileLtsDTO> profileItemDetailList, List<ItemDetailDTO> itemDetailList) {
		
		if (profileItemDetailList == null || profileItemDetailList.isEmpty()) {
			return;
		}
		
		for (ItemDetailProfileLtsDTO profileItemDetail : profileItemDetailList) {
			if (profileItemDetail.getItemDetail() != null) {
				itemDetailList.add(profileItemDetail.getItemDetail());
			}
		}
	}
	
	private void setFsaProfileItem(LtsBasketVasDetailFormDTO form, OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket) {
		
		if (orderCapture.getLtsServiceProfile().getEyeFsaProfile() == null
				|| ArrayUtils.isEmpty(orderCapture.getLtsServiceProfile().getEyeFsaProfile().getItems())) {
			return;
		}
		
		List<ItemDetailProfileLtsDTO> imsProfileAutoOutItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		List<ItemDetailProfileLtsDTO> imsProfileExistingItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		List<ItemDetailProfileLtsDTO> imsProfileIngoreItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		try {
			for (ItemDetailProfileLtsDTO fsaProfileItem : orderCapture.getLtsServiceProfile().getEyeFsaProfile().getItems()) {
				ItemDetailProfileLtsDTO clonedFsaProfileItem = (ItemDetailProfileLtsDTO)CommonUtil.cloneNestedSerializableObject(fsaProfileItem);
				
				if (clonedFsaProfileItem.getItemDetail() == null) {
					imsProfileIngoreItemList.add(clonedFsaProfileItem);
					continue;
				}
				
				ItemActionLtsDTO itemAction = getItemAction(clonedFsaProfileItem.getItemActions(), orderCapture);
				
				if (itemAction == null) {
					imsProfileExistingItemList.add(clonedFsaProfileItem);
					continue;
				}
				
//				fsaProfileItem.getItemDetail().setpe
				if (LtsConstant.IO_IND_OUT.equals(itemAction.getAction())) {
					imsProfileAutoOutItemList.add(clonedFsaProfileItem);
				}	
				else {
					imsProfileExistingItemList.add(clonedFsaProfileItem);
				}
				
				if (clonedFsaProfileItem.getItemDetail() != null) {
					clonedFsaProfileItem.getItemDetail().setPenaltyHandling(itemAction.getPenaltyHandle());
				}
			}
		}
		catch (Exception e) {
			throw new AppRuntimeException(e.getMessage());
		}
		
		form.setImsProfileAutoOutItemList(imsProfileAutoOutItemList);
		form.setImsProfileExistingItemList(imsProfileExistingItemList);
		form.setImsProfileIngoreItemList(imsProfileIngoreItemList);
	}
	
	private void setFreeVasItem(LtsBasketVasDetailFormDTO form, OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket, String locale, BomSalesUserDTO bomSalesUser)  throws Exception {
		
		
		// Premier Team Free VAS
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
				&& LtsConstant.CHANNEL_ID_PREMIER.equals(String.valueOf(bomSalesUser.getChannelId()))) {
			List<ItemSetDetailDTO> teamVasSetList = ltsOfferService
					.getBasketItemSetList(LtsSbOrderHelper.getItemSetCriteria(orderCapture,
							bomSalesUser, LtsConstant.ITEM_SET_TYPE_PREMIER_TEAM_VAS, locale));
			form.setTeamVasSetList(teamVasSetList);
			return;
		}
		
		// DEL Renewal Free VAS
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
				&& StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())
				&& LtsConstant.BASKET_CATEGORY_PREMIUM.equals(selectedBasket.getBasketCatg())) {
			if (orderCapture.getLtsMiscellaneousForm().isSubmitDisForm()
					|| !offerItemService.isPaidVasExist(orderCapture.getLtsServiceProfile().getItemDetails())) {
				List<ItemSetDetailDTO> teamVasSetList = 
					ltsOfferService
					.getBasketItemSetList(LtsSbOrderHelper.getItemSetCriteria(orderCapture,
							bomSalesUser, LtsConstant.ITEM_SET_TYPE_TEAM_VAS, locale));
				form.setTeamVasSetList(teamVasSetList);
				return;
			}
			return;
		}
		// UPGRADE / EYE Renewal Free Vas
		setProfileFreeVasItem(form, orderCapture, selectedBasket, locale);
	}
	
	
	private void setProfileFreeVasItem(LtsBasketVasDetailFormDTO form, OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket, String locale)  throws Exception {
		ItemDetailProfileLtsDTO[] profileItems = orderCapture.getLtsServiceProfile().getItemDetails();
		ItemDetailProfileLtsDTO[] clonedProfileItems = new ItemDetailProfileLtsDTO[profileItems.length];

		for (int i = 0; i < profileItems.length; ++i) {
			clonedProfileItems[i] = (ItemDetailProfileLtsDTO) CommonUtil
					.cloneNestedSerializableObject(profileItems[i]);
		}
		
		if (ArrayUtils.isEmpty(clonedProfileItems)) {
			return;
		}
		
		List<ItemDetailProfileLtsDTO> profileAutoChangeTpItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		String[] freeVasItemIds = offerItemService.generateFreeVasItem(clonedProfileItems, selectedBasket.getType(), 
				selectedBasket.getActualContractPeriod(), orderCapture.getBasketChannelId(), 
				orderCapture.getOrderType(), orderCapture.getLtsMiscellaneousForm().isSubmitDisForm());
		
		if (ArrayUtils.isNotEmpty(freeVasItemIds)) {
			List<ItemDetailDTO> freeVasItemList = ltsOfferService.getItem(freeVasItemIds, LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale, true);
			if (freeVasItemList != null && !freeVasItemList.isEmpty()) {
				for (ItemDetailDTO freeVasItem : freeVasItemList) {
					freeVasItem.setSelected(true);
					ItemDetailProfileLtsDTO itemDetailProfileLts = new ItemDetailProfileLtsDTO();
					itemDetailProfileLts.setItemDetail(freeVasItem);
					profileAutoChangeTpItemList.add(itemDetailProfileLts);
				}
				form.setProfileAutoChangeTpItemList(profileAutoChangeTpItemList);
			}
		}
	
	}
	
	private void setProfileTpVasItem(LtsBasketVasDetailFormDTO form,
			OrderCaptureDTO orderCapture,
			BasketDetailDTO selectedBasket, String locale) throws Exception {
		
		ItemDetailProfileLtsDTO[] profileItems = orderCapture.getLtsServiceProfile().getItemDetails();
		ItemDetailProfileLtsDTO[] clonedProfileItems = new ItemDetailProfileLtsDTO[profileItems.length];
		
		for (int i=0; i<profileItems.length; ++i) {
			clonedProfileItems[i] = (ItemDetailProfileLtsDTO)CommonUtil.cloneNestedSerializableObject(profileItems[i]);
		}			
		if (ArrayUtils.isEmpty(clonedProfileItems)) {
			return;
		}
		
		List<ItemDetailProfileLtsDTO> profileAutoOutTpItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		List<ItemDetailProfileLtsDTO> profileAutoOutVasItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		List<ItemDetailProfileLtsDTO> profileExistingTpItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		List<ItemDetailProfileLtsDTO> profileExistingVasItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		List<ItemDetailProfileLtsDTO> profileExpiredItemList = new ArrayList<ItemDetailProfileLtsDTO>();
		
		try {
			
			// Auto change to another item
			String toProd = null;
			
			
			if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())) {
				toProd = LtsConstant.DEVICE_TYPE_1020.equals(orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType()) ? LtsConstant.LTS_PRODUCT_TYPE_EYE_2_A :
					LtsConstant.DEVICE_TYPE_EYE3A.equals(orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType()) ? LtsConstant.LTS_PRODUCT_TYPE_EYE_3_A : null;
			}
			else if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
				toProd = StringUtils.defaultIfEmpty(orderCapture.getLtsServiceProfile().getExistEyeType(), LtsConstant.LTS_PRODUCT_TYPE_DEL);
			}
			
			String[] selectedItemIds = getAllSelectedItemIds(orderCapture, form);
			offerItemService.updateItemActionByPurchases(clonedProfileItems, selectedItemIds, toProd);	
			
			for (ItemDetailProfileLtsDTO clonedProfileItem : clonedProfileItems) {
				
				// Auto out expired term plan
				if (StringUtils.equals(clonedProfileItem.getItemType(), LtsConstant.ITEM_TYPE_EXPIRED_SERVICE)) {
					profileExpiredItemList.add(clonedProfileItem);
					continue;
				}
				
				// Auto out expired VAS
				if (StringUtils.equals(clonedProfileItem.getItemType(), LtsConstant.ITEM_TYPE_EXPIRED_VAS)) {
					profileExpiredItemList.add(clonedProfileItem);
					continue;
				}
				
				// Renewal auto out all line level and retain all VAS level 
				if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
						&& !LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType())) {
					if (LtsConstant.PROFILE_ITEM_TYPE_SERVICE.equals(clonedProfileItem.getItemType())) {
						if (clonedProfileItem.getTpExpiredMonths() <= 0
								&& StringUtils.isNotBlank(clonedProfileItem.getConditionEffStartDate())) {
							clonedProfileItem.getItemDetail().setPenaltyHandling(LtsConstant.PENALTY_ACTION_AUTO_WAIVE);	
						}
						profileAutoOutTpItemList.add(clonedProfileItem);
						continue;
					}
				}
				
				// Auto out system not support term plan / VAS
				if (ArrayUtils.isNotEmpty(clonedProfileItem.getItemActions())
						&& !LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType()))	 {
					ItemActionLtsDTO itemAction = getItemAction(clonedProfileItem.getItemActions(), orderCapture);

					if (itemAction != null) {
						if (clonedProfileItem.getItemDetail() != null) {
							clonedProfileItem.getItemDetail().setPenaltyHandling(itemAction.getPenaltyHandle());
						}
						if (LtsConstant.IO_IND_OUT.equals(itemAction.getAction())) {
							if (LtsConstant.PROFILE_ITEM_TYPE_SERVICE.equals(clonedProfileItem.getItemType())) {
								profileAutoOutTpItemList.add(clonedProfileItem);
							}
							else if (LtsConstant.PROFILE_ITEM_TYPE_VAS.equals(clonedProfileItem.getItemType())) {
								profileAutoOutVasItemList.add(clonedProfileItem);
							}
							continue;
						}
					}
				}
				
				//Auto out VAS that are not supported in new OS Type for eye renew order
				if(orderCapture.getLtsServiceProfile().getExistEyeType() != null
						&& LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
						&& !LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType())){

					if (LtsConstant.PROFILE_ITEM_TYPE_VAS.equals(clonedProfileItem.getItemType())) {
						String profileItemOsType = ltsOfferService.getItemProfileOsType(clonedProfileItem);
						
						if(StringUtils.isNotEmpty(profileItemOsType)
								&& !StringUtils.equals(profileItemOsType, StringUtils.defaultIfEmpty(selectedBasket.getOsType(), LtsConstant.OS_TYPE_AND))){
							
							//check penalty handling if Auto Out due to OS Type change
							String penalty = ltsOfferService.getItemProfileOsTypeChangePenalty(clonedProfileItem);
							if(StringUtils.isNotBlank(penalty)){
								clonedProfileItem.getItemDetail().setPenaltyHandling(penalty);
							}
							
							profileAutoOutVasItemList.add(clonedProfileItem);
							continue;
						}
					}
					
				}
				
				
				// Carry forward existing term plan / VAS to new service.
				if (LtsConstant.PROFILE_ITEM_TYPE_SERVICE.equals(clonedProfileItem.getItemType())) {
					profileExistingTpItemList.add(clonedProfileItem);
				}
				else if (LtsConstant.PROFILE_ITEM_TYPE_VAS.equals(clonedProfileItem.getItemType())) {
					//BOM2018021 auto out if recontract and is force retain
					if(LtsSbOrderHelper.isRecontractOrder(orderCapture) && clonedProfileItem.isForceRetain()){
						if(orderCapture.getLtsRecontractForm().isDeceasedCase()){
							clonedProfileItem.setForceRetain(false);
							clonedProfileItem.getItemDetail().setPenaltyHandling(LtsConstant.PENALTY_ACTION_AUTO_WAIVE);
						}
						profileAutoOutVasItemList.add(clonedProfileItem);
					}else{
						profileExistingVasItemList.add(clonedProfileItem);
					}
				}
			}				
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
		
		
		
		waivePenaltyByBasketProject(selectedBasket, profileAutoOutTpItemList);
		waivePenaltyByBasketProject(selectedBasket, profileAutoOutVasItemList);
		
		form.setProfileAutoOutTpItemList(profileAutoOutTpItemList);
		form.setProfileExistingTpItemList(profileExistingTpItemList);
		form.setProfileAutoOutVasItemList(profileAutoOutVasItemList);
		form.setProfileExistingVasItemList(profileExistingVasItemList);
		form.setProfileExpiredItemList(profileExpiredItemList);
	}
	
	private void waivePenaltyByBasketProject(BasketDetailDTO selectedBasket, List<ItemDetailProfileLtsDTO> profileItemList) {
		if (profileItemList == null || profileItemList.isEmpty()) {
			return;
		}
		if (LtsConstant.EYE_FAULT_CASE_PROJECT_CD_MAP.containsValue(selectedBasket.getProjectCd())) {
			for (ItemDetailProfileLtsDTO profileItem : profileItemList) {
				if (StringUtils.isNotBlank(profileItem.getItemDetail().getPenaltyHandling())) {
					profileItem.getItemDetail().setPenaltyHandling(LtsConstant.PENALTY_ACTION_AUTO_WAIVE);
				}
			}
		}
		
	}
	
	private ItemActionLtsDTO getItemAction(ItemActionLtsDTO[] itemActions, OrderCaptureDTO orderCapture) {
		
		if (ArrayUtils.isEmpty(itemActions)) {
			return null;
		}
		
		for (ItemActionLtsDTO itemAction : itemActions) {
			
			if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())) {
				if (LtsConstant.DEVICE_TYPE_1020.equals(orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType())
						&& LtsConstant.LTS_PRODUCT_TYPE_EYE_2_A.equals(itemAction.getToProd())) {
					return itemAction;
				}
				if (LtsConstant.DEVICE_TYPE_EYE3A.equals(orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType())
						&& LtsConstant.LTS_PRODUCT_TYPE_EYE_3_A.equals(itemAction.getToProd())) {
					return itemAction;
				}	
			}
			
			if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
				if (StringUtils.equalsIgnoreCase(StringUtils.defaultIfEmpty(
						orderCapture.getLtsServiceProfile().getExistEyeType(),
						LtsConstant.LTS_PRODUCT_TYPE_DEL), itemAction
						.getToProd())) {
					return itemAction;
				}
			}
		}
		return null;
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		Map<String, String> penaltyMap = new TreeMap<String, String>();
		penaltyMap.put(LtsConstant.OFFER_HANDLE_AUTO_GENERATE, "Generate");
		
		if (orderCapture != null && orderCapture.isChannelPremier()) {
			penaltyMap.put(LtsConstant.OFFER_HANDLE_MANUAL_WAIVE, "Waive");	
		}
		
		referenceData.put("penaltyMap", penaltyMap);
		

		if(!ltsOfferService.isSelectedNewDeviceFieldService(orderCapture)){
			referenceData.put("notAllowPeSocket", true);
		}
		
		return referenceData;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
}
