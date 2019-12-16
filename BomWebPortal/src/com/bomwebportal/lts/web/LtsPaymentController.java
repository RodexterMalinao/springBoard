package com.bomwebportal.lts.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.CsPortalIdInqArqDTO;
import com.bomwebportal.lts.dto.CsPortalTxnDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupCriteriaDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsPaymentFormDTO;
import com.bomwebportal.lts.dto.form.LtsRecontractFormDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.service.CsPortalService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsPaymentService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.bom.EyeProfileCountService;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsConstant.LtsOrderFlag;
import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.PaymentService;
import com.bomwebportal.util.Utility;
import com.google.common.collect.Lists;
import com.google.gson.Gson;

public class LtsPaymentController extends SimpleFormController {

	private final String nextView = "ltsappointment.html";
	private final String viewName = "ltspayment";
	private final String commandName = "ltspayment";

	protected final Log logger = LogFactory.getLog(getClass());

	private PaymentService paymentService;
	private LtsPaymentService ltsPaymentService;
	private CodeLkupCacheService creditCardTypeLkupCacheService;
	private CodeLkupCacheService suspendReasonLkupCacheService;
	private CodeLkupCacheService optOutReasonLkupCacheService;
	private CodeLkupCacheService clubLaunchDateLkupCacheService;
	private CodeLkupCacheService waiveReasonLkupCacheService;
	private LtsOfferService ltsOfferService;
	private CsPortalService csPortalService;
	private LtsCommonService ltsCommonService;
	private CustomerProfileLtsService customerProfileLtsService;
	private LtsProfileService ltsProfileService;
	private ImsProfileService imsProfileService;
	private CodeLkupCacheService erDelHandleCacheService;
	private CodeLkupCacheService erEyeHandleCacheService;
	private CodeLkupCacheService theClubPremiumCacheService;
	private EyeProfileCountService eyeProfileCountService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCaptureDTO = LtsSessionHelper
				.getOrderCapture(request);
		setLocale(RequestContextUtils.getLocale(request));
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);

	}

	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {

		OrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getOrderCapture(request);
		LtsPaymentFormDTO ltsPaymentFormDTO = orderCaptureDTO.getLtsPaymentForm();

		String tenureCode = new String();
		String existPayMethod = new String();
		List<String> newPayMethods = new ArrayList<String>();
		Locale locale = RequestContextUtils.getLocale(request);
		
		if (ltsPaymentFormDTO == null) {
			ltsPaymentFormDTO = new LtsPaymentFormDTO();
			ltsPaymentFormDTO.setSubmitInd("SUBMIT");
		}
		if (StringUtils.isBlank(ltsPaymentFormDTO.getApplicationDate())) {
			ltsPaymentFormDTO
					.setApplicationDate(orderCaptureDTO
							.getLtsMiscellaneousForm().getApplicationDate()
							.split(" ")[0]);
		}

		setupExistPayMthd(orderCaptureDTO, ltsPaymentFormDTO);
		setupCustomerInfo(orderCaptureDTO, ltsPaymentFormDTO);

		// find possible new pay method by tenure code and existing pay method
		tenureCode = ltsPaymentService.getTenureCode(getTenure(orderCaptureDTO));
		existPayMethod = ltsPaymentFormDTO.getExistingPayMethodType();
		request.setAttribute("tenureCode", tenureCode);
		request.setAttribute("existPayMethod", existPayMethod);
		request.setAttribute("isNewCust", StringUtils.isBlank(existPayMethod));

		if (StringUtils.isNotBlank(existPayMethod)) {
			newPayMethods = ltsPaymentService.getNewPayMethod(tenureCode, existPayMethod);

			if (existPayMethod.compareTo(LtsConstant.PAYMENT_TYPE_CASH) == 0) {
				ltsPaymentFormDTO.setExistingPayment("Cash");
			} else {
				if (existPayMethod.compareTo(LtsConstant.PAYMENT_TYPE_AUTO_PAY) == 0) {
					ltsPaymentFormDTO.setExistingPayment("Bank Auto-Pay");
				} else {
					if (existPayMethod.compareTo(LtsConstant.PAYMENT_TYPE_CREDIT_CARD) == 0) {
						ltsPaymentFormDTO.setExistingPayment("Credit Card");
					}
				}
			}
		} else {
			newPayMethods = new ArrayList<String>();
			newPayMethods.add("A");
			newPayMethods.add("C");

		}

		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCaptureDTO.getOrderType()) &&
				StringUtils.isBlank(orderCaptureDTO.getLtsServiceProfile().getExistEyeType())) {
				request.setAttribute("showEBtn", true);
		}else{
			// attributes for form display
			for (int i = 0; i < newPayMethods.size(); i++) {
				request.setAttribute("show" + newPayMethods.get(i) + "Btn", true);
			}

		}
		// if the existing method is included in the (ie. = one of the) new
		// methods
		// do not show the "changeTo" button for it
		if (newPayMethods.contains(existPayMethod)) {
			newPayMethods.remove(existPayMethod);
			if (ltsPaymentFormDTO.getSelectButton() == null) {
				ltsPaymentFormDTO.setSelectButton("");
			}
			request.setAttribute("showKeepExistBtn", true);
			request.setAttribute("show" + existPayMethod + "Btn", false);
		}

		request.setAttribute("bankCode", ltsPaymentFormDTO.getBankCode());
		request.setAttribute("branchCode", ltsPaymentFormDTO.getBranchCode());

		ltsPaymentFormDTO.setSalesMemoNumRequired(!LtsSessionHelper.isChannelCs(request));

		// get Prepayment Items for display
		setupPrepaymentItem(request, orderCaptureDTO, ltsPaymentFormDTO, getLocale());
		//retrievePrepaymentItem(request, orderCaptureDTO, ltsPaymentFormDTO, tenureCode, locale.toString()); //Obsoleted
		

		/* Billing options and MyHKT handling */
		String cspDocType = orderCaptureDTO.getLtsRecontractForm() != null ? orderCaptureDTO
				.getLtsRecontractForm().getDocType() : orderCaptureDTO
				.getLtsServiceProfile().getPrimaryCust().getDocType();
		String cspDocNum = orderCaptureDTO.getLtsRecontractForm() != null ? orderCaptureDTO
				.getLtsRecontractForm().getDocId() : orderCaptureDTO
				.getLtsServiceProfile().getPrimaryCust().getDocNum();

		/*Setup Billing Items*/
		ltsPaymentFormDTO.setRecontractCase(orderCaptureDTO.getLtsMiscellaneousForm() == null? false : orderCaptureDTO.getLtsMiscellaneousForm().isRecontract());
		setupBillingItem(orderCaptureDTO, ltsPaymentFormDTO, locale);
				
				
		boolean isDocValid = checkCspDummyDoc(ltsPaymentFormDTO.getCustDocNum());
		
		//Melody//
		//My HKT and The Club handling
		if(!StringUtils.equals(cspDocType, LtsConstant.DOC_TYPE_HKBR)){
			getCsPortalItem( locale.toString(),  cspDocType,  cspDocNum, ltsPaymentFormDTO, isDocValid, orderCaptureDTO);
		}else{
			ltsPaymentFormDTO.setCsPortalItem(null);
		}


		//ltsPaymentFormDTO.setCsPortalExist(isCspExist);
		ltsPaymentFormDTO.setCardVerifyRequired(!orderCaptureDTO.isChannelCs());
		request.setAttribute("isDocValidCsp", isDocValid);

		if (orderCaptureDTO.getLtsAddressRolloutForm() != null) {

			// ER Charge
			if (orderCaptureDTO.getLtsAddressRolloutForm().isExternalRelocate()
					&& ltsPaymentFormDTO.getErChargeItemList() == null) {
				List<ItemDetailDTO> erChargeItemList = ltsOfferService
						.getItemList(LtsConstant.ITEM_TYPE_ER_CHARGE,
								locale.toString(), true,
								orderCaptureDTO.getBasketChannelId(), orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate());
				if (erChargeItemList != null && !erChargeItemList.isEmpty()
						&& orderCaptureDTO.isChannelPremier()) {
					((ItemDetailDTO) erChargeItemList.get(0))
							.setPenaltyHandling(LtsConstant.PENALTY_ACTION_GENERATE);
				}
				ltsPaymentFormDTO.setErChargeItemList(erChargeItemList);
			}

			/* change BA handling */
			else {
				if (StringUtils.isBlank(ltsPaymentFormDTO.getBillingAddress())
						&& orderCaptureDTO.getLtsServiceProfile() != null
						&& orderCaptureDTO.getLtsServiceProfile().getAddress() != null) {
					ltsPaymentFormDTO.setBillingAddress(orderCaptureDTO
							.getLtsServiceProfile().getAddress()
							.getFullAddress());
				}
				ltsPaymentFormDTO.setAllowChangeBa(true);
			}
		}

		
		setRedemptionMediaDetail(orderCaptureDTO, ltsPaymentFormDTO);
		if(ltsPaymentFormDTO.isRequireRedemPremium() && StringUtils.isEmpty(ltsPaymentFormDTO.getRedemptionMedia())){
			ltsPaymentFormDTO.setRedemptionMedia(LtsConstant.REDEMPTION_MEDIA_SMS);
		}
		
		return ltsPaymentFormDTO;
	}
	
	private void setupPrepaymentItem(HttpServletRequest request, OrderCaptureDTO order, LtsPaymentFormDTO form, Locale locale){
		PrepaymentLkupCriteriaDTO criteria = new PrepaymentLkupCriteriaDTO();
		criteria.setAppDate(order.getLtsMiscellaneousForm().getApplicationDate());
		criteria.setDocType(order.getLtsCustomerIdentificationForm().getDocType());
		criteria.setOrderType(order.getOrderType());
		criteria.setTenure(getTenure(order));
		criteria.setChannel(order.isChannelPremier()? "P" : "M");

		//Use HKT Premier ind as Housing Type
		String housingTypePT = order.getNewAddressRollout().getHktPremier();
		if(StringUtils.isNotBlank(housingTypePT)){
			criteria.setHouseType(housingTypePT);
		}else{
			criteria.setHouseType(order.getNewAddressRollout().getHousingType());
		}

		int addrEyeCount = order.getNewAddressRollout().getNumOfEyeProfile();
		int custEyeCount = eyeProfileCountService.getEyeProfileCountByCust(
				order.getLtsCustomerIdentificationForm().getDocType(), 
				order.getLtsCustomerIdentificationForm().getId());
		
		criteria.setEyeProfileCount(addrEyeCount > custEyeCount? addrEyeCount : custEyeCount);
		
		/*check all psef code of plan item / content set item */
		List<String> psefCodeList = new ArrayList<String>();
		if(order.getSbOrder() != null){
			ServiceDetailLtsDTO ltsSrv = LtsSbOrderHelper.getLtsService(order.getSbOrder());
			for(ItemDetailLtsDTO itemDtl : ltsSrv.getItemDtls()){
				if(LtsConstant.ITEM_TYPE_PLAN.equals(itemDtl.getItemType())
						|| LtsConstant.ITEM_TYPE_CONTENT.equals(itemDtl.getItemType())){
					psefCodeList.addAll(Arrays.asList(ltsOfferService.getItemPsefCodes(itemDtl.getItemId())));
				}
			}
		}else{

			if(order.getLtsBasketServiceForm() != null){
				if(order.getLtsBasketServiceForm().getPlanItemList() != null){
					for(ItemDetailDTO itemDtl: order.getLtsBasketServiceForm().getPlanItemList()){
						if(itemDtl.isSelected()){
							psefCodeList.addAll(Arrays.asList(ltsOfferService.getItemPsefCodes(itemDtl.getItemId())));
						}
					}
				}
				if(order.getLtsBasketServiceForm().getContItemSetList() != null){
					for (ItemSetDetailDTO itemSetDetail : order.getLtsBasketServiceForm().getContItemSetList()) {
						for(ItemDetailDTO itemDtl: itemSetDetail.getItemDetails()){
							if(itemDtl.isSelected()){
								psefCodeList.addAll(Arrays.asList(ltsOfferService.getItemPsefCodes(itemDtl.getItemId())));
							}
						}
					}
				}
			}
		}
		
		if(CollectionUtils.containsAny(psefCodeList, ltsPaymentService.getPrepayExcludePsefCodeList())){
			return;
		}
		
		/*Loop for each payment method*/
		String[] paymentMethods = {
				LtsConstant.PAYMENT_TYPE_CASH, 
				LtsConstant.PAYMENT_TYPE_AUTO_PAY, 
				LtsConstant.PAYMENT_TYPE_CREDIT_CARD};
		
		for(String paymentMethod : paymentMethods){
			criteria.setPaymentMethod(paymentMethod);
			List<PrepaymentLkupResultDTO> resultList = ltsPaymentService.getPrepaymentItemIdByLkup(criteria);
			for(PrepaymentLkupResultDTO result: resultList){
				if(CollectionUtils.containsAny(result.getPsefCdSet(), psefCodeList)
						&& StringUtils.isNotBlank(result.getPrepayItemId())){
					List<ItemDetailDTO> itemList = ltsOfferService.getItem(new String[]{result.getPrepayItemId()}, 
																			LtsConstant.DISPLAY_TYPE_ITEM_SELECT, 
																			locale.toString(), 
																			true);
					if(itemList != null && itemList.size() > 0){
						ItemDetailDTO prePayItem = itemList.get(0);
						

						if(LtsConstant.PAYMENT_TYPE_CASH.equals(paymentMethod)
								&& LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(form.getExistingPayMethodType())
								&& StringUtils.startsWith(form.getExistingCreditCardNum(), LtsConstant.TNG_CARD_PREFIX)){
							form.setPrePayItemE(prePayItem);
						}else if(paymentMethod.equals(form.getExistingPayMethodType())){
							form.setPrePayItemE(prePayItem);
						}
						
						if(LtsConstant.PAYMENT_TYPE_CASH.equals(paymentMethod)){
							form.setPrePayItemM(prePayItem);
						}else if(LtsConstant.PAYMENT_TYPE_AUTO_PAY.equals(paymentMethod)){
							form.setPrePayItemA(prePayItem);
						}else if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(paymentMethod)){
							form.setPrePayItemC(prePayItem);
						}
					}
					break;
				}
			}
		}
		
		form.setPrepaymentLkupCriteria(criteria);
		
	}
	
	
	private void setupBillingItem(OrderCaptureDTO orderCaptureDTO, LtsPaymentFormDTO ltsPaymentFormDTO, Locale locale){

		AccountDetailProfileLtsDTO profilePrimaryAcct = LtsSbOrderHelper.getPrimaryAccount(orderCaptureDTO.getLtsServiceProfile());
		String profileEmail = profilePrimaryAcct != null ? profilePrimaryAcct.getEmailAddr() : null; 

		AccountDetailProfileLtsDTO acct = LtsSbOrderHelper.getPrimaryAccount(orderCaptureDTO.getLtsServiceProfile());
		
		if(LtsSbOrderHelper.isRecontractOrder(orderCaptureDTO)){
			acct = orderCaptureDTO.getLtsRecontractForm().getAccountDetailProfile();
		}
		
		if(LtsSbOrderHelper.isRecontractOrder(orderCaptureDTO)){
			//if recontract form email addr is not null, get it, else get acct profile email addr
			if (StringUtils.isNotBlank(orderCaptureDTO.getLtsRecontractForm().getEmailAddr())) {
				profileEmail = orderCaptureDTO.getLtsRecontractForm().getEmailAddr();
			}
			
			else if (acct != null){
				profileEmail = acct.getEmailAddr();
			}
		}
		
		List<ItemDetailDTO> tempItemList;		
		
		if (acct != null ){
			if (StringUtils.equals( LtsConstant.LTS_BILL_MEDIA_PPS_BILL, acct.getBillMedia())) {

				if(ltsPaymentFormDTO.getEmailBillItem() == null){
					tempItemList = ltsOfferService.getItemList(
							LtsConstant.ITEM_TYPE_EMAIL_BILL, locale.toString(), true,
							orderCaptureDTO.getBasketChannelId(), orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate());
					if (tempItemList != null && tempItemList.size() > 0) {
						ltsPaymentFormDTO.setEmailBillItem(tempItemList.get(0));
						if(StringUtils.isBlank(ltsPaymentFormDTO.getEmailBillAddress())
								&& StringUtils.isNotBlank(profileEmail)){
							ltsPaymentFormDTO.setEmailBillAddress(profileEmail);
						}
					}
				}
			} else {			
				if(ltsPaymentFormDTO.getNoBillItem() == null){
					String toProd = null;
					
					if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCaptureDTO.getOrderType())) {
						toProd = LtsConstant.DEVICE_TYPE_1020.equals(orderCaptureDTO.getLtsDeviceSelectionForm().getSelectedDeviceType()) ? LtsConstant.LTS_PRODUCT_TYPE_EYE_2_A :
							LtsConstant.DEVICE_TYPE_EYE3A.equals(orderCaptureDTO.getLtsDeviceSelectionForm().getSelectedDeviceType()) ? LtsConstant.LTS_PRODUCT_TYPE_EYE_3_A : null;
					}
					else if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCaptureDTO.getOrderType())) {
						toProd = StringUtils.defaultIfEmpty(orderCaptureDTO.getLtsServiceProfile().getExistEyeType(), LtsConstant.LTS_PRODUCT_TYPE_DEL);
					}
					
					if (!LtsConstant.EYE_TYPE_EYE3A.equalsIgnoreCase(toProd)) {
						
						tempItemList = ltsOfferService.getItemList(
								LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE, locale.toString(), true,
								orderCaptureDTO.getBasketChannelId(), orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate());
						
						if (tempItemList != null && tempItemList.size() > 0) {
							ltsPaymentFormDTO.setNoBillItem(tempItemList.get(0));
							if(ltsPaymentFormDTO.isCsPortalExist()){//if (isCspExist) {
								ltsPaymentFormDTO.getNoBillItem().setSelected(true);
							}
						}
					}
				}
	
				// set DOC type ---Max.R.MENG
				ltsPaymentFormDTO.setDocTypeBR(StringUtils.equals(ltsPaymentFormDTO.getCustDocType(), LtsBackendConstant.DOC_TYPE_HKBR)? true : false);
	
	
				if(ltsPaymentFormDTO.getPaperBillItem() == null){
					tempItemList = ltsOfferService.getItemList(
							LtsConstant.ITEM_TYPE_PAPER_BILL, locale.toString(), true,
							orderCaptureDTO.getBasketChannelId(), orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate());
					
					if (tempItemList != null && tempItemList.size() > 0) {
						ltsPaymentFormDTO.setPaperBillItem(tempItemList.get(0));
						// get cust level ind from CCM
						String custNum = orderCaptureDTO.getLtsServiceProfile().getPrimaryCust().getCustNum();
						String accNum = orderCaptureDTO.getLtsServiceProfile().getAccounts()[0].getAcctNum();
						if(ltsPaymentFormDTO.isRecontractCase() 
								&& orderCaptureDTO.getLtsRecontractForm() != null
								&& orderCaptureDTO.getLtsRecontractForm().getCustDetailProfile() != null){
							custNum = orderCaptureDTO.getLtsRecontractForm().getCustDetailProfile().getCustNum();
							accNum = ltsPaymentFormDTO.getRecontractAccountNo();
							ltsPaymentFormDTO.setAllowModifyInd(false);
						}
						
						//If recontract case accNum will be empty if user have not input yet, so leave it blank
						if(StringUtils.isNotBlank(accNum)){
							CustomerDetailProfileLtsDTO custProfile = customerProfileLtsService.getCustByCustNum(custNum, LtsConstant.SYSTEM_ID_DRG);
							AccountDetailProfileLtsDTO acctProfile = customerProfileLtsService.getAccountbyAcctNum(accNum, LtsConstant.SYSTEM_ID_DRG);
							String paperBill = acctProfile.getWaivePaperInd();
		
							if(StringUtils.isBlank(paperBill) || StringUtils.equals(paperBill, "N")){						
								ltsPaymentFormDTO.setAllowModifyInd(true);
							}else{
								ltsPaymentFormDTO.setAllowModifyInd(false);
								if(StringUtils.equals(paperBill, "G")){
									ltsPaymentFormDTO.setPaperBillCharge("Y");	
								}else{
									ltsPaymentFormDTO.setPaperBillCharge(paperBill);
								}
								
								if(acctProfile != null){						   
									ltsPaymentFormDTO.setPaperWaiveReason(acctProfile.getWaivePaperReaCd());
								}						
							}
						}
					}
				}
				
				if(ltsPaymentFormDTO.getEmailBillItem() == null){
					List<ItemDetailDTO> emailBillItemList = ltsOfferService
							.getItemList(LtsConstant.ITEM_TYPE_EMAIL_BILL,
									locale.toString(), true, true,
									orderCaptureDTO.getBasketChannelId(), orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate());
					if (emailBillItemList != null && !emailBillItemList.isEmpty()) {
						ltsPaymentFormDTO.setEmailBillItem(emailBillItemList.get(0));
						if(StringUtils.isBlank(ltsPaymentFormDTO.getEmailBillAddress())
								&& StringUtils.isNotBlank(profileEmail)){
							ltsPaymentFormDTO.setEmailBillAddress(profileEmail);
						}
					}
				}
			}
		}
		
		List<ItemDetailDTO> billItemList = ltsPaymentFormDTO.getBillItemList();
		if (StringUtils.isEmpty(ltsPaymentFormDTO.getSelectedBillItemId())) {
			for (int i = 0; billItemList != null && i < billItemList.size(); i++) {
				if ("D".equals(billItemList.get(i).getMdoInd())) {
					ltsPaymentFormDTO.setSelectedBillItemId(billItemList.get(i)
							.getItemId());
					break;
				}
			}
		}
	}

	private void setRedemptionMediaDetail(OrderCaptureDTO orderCapture, LtsPaymentFormDTO form) {
		
		boolean isRequireRedemPremium = LtsSbOrderHelper.isRequireRedemPremium(orderCapture);
		//TODO BOM2019014
		boolean isSelfPickNewDevice = !ltsOfferService.isSelectedNewDeviceFieldService(orderCapture);
		boolean isIOS = LtsConstant.OS_TYPE_IOS.equals(orderCapture.getSelectedBasket().getOsType());
		
		if(StringUtils.equals(LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS, orderCapture.getOrderSubType())){
			if(isRequireRedemPremium){
				form.setRequireRedemPremium(true);
			}
		}else{
			if(isRequireRedemPremium || isSelfPickNewDevice || isIOS){
				form.setRequireRedemPremium(true);
			}
		}

		//only allow redemption by paper for age > 65
		if("N".equals(ltsCommonService.getLtsOrderFlag(LtsOrderFlag.CHECK_AGE_REDEMPTION_PAPER))){
			form.setAllowRedemPaper(true);
		}else{
			String dateOfBirth = orderCapture.getLtsCustomerIdentificationForm().getDateOfBirth();
			if (StringUtils.isNotEmpty(dateOfBirth)) {
				dateOfBirth = LtsDateFormatHelper.getDateFromDTOFormat(dateOfBirth);
				if(LtsSbOrderHelper.isAgeOverByMonth(dateOfBirth)){
					form.setAllowRedemPaper(true);
				}else{
					form.setAllowRedemPaper(false);
				}
			}
		}
		
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		logger.info("nextView: " + nextView);

		String currentView = (String) request.getParameter("currentView");
		logger.info("currentView: " + currentView);

		OrderCaptureDTO orderCaptureDTO = LtsSessionHelper
				.getOrderCapture(request);
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}

		LtsPaymentFormDTO ltsPaymentFormDTO = (LtsPaymentFormDTO) command;

		if (ltsPaymentFormDTO.getSubmitInd().compareTo("SUBMIT") == 0) {

			ValidationResultDTO[] validationResults = validateSubmit(request,
					orderCaptureDTO, ltsPaymentFormDTO);

			for (ValidationResultDTO validationResult : validationResults) {
				if (Status.INVAILD == validationResult.getStatus()) {
					ModelAndView mav = new ModelAndView(viewName, referenceData(request));
					mav.addObject(commandName, ltsPaymentFormDTO);
					mav.addObject("errorMsgList",
							validationResult.getMessageList());
					return mav;
				}
			}

			if (ltsPaymentFormDTO.getSelectButton().equals(LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {
				ltsPaymentFormDTO.setPrePayItem(ltsPaymentFormDTO.getPrePayItemA());
			} else if (StringUtils.isBlank(ltsPaymentFormDTO.getSelectButton())) {
				ltsPaymentFormDTO.setPrePayItem(ltsPaymentFormDTO.getPrePayItemE());
			} else if (ltsPaymentFormDTO.getSelectButton().equals(LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
				if(StringUtils.startsWith(ltsPaymentFormDTO.getCardNum(), LtsConstant.TNG_CARD_PREFIX)){
					ltsPaymentFormDTO.setPrePayItem(ltsPaymentFormDTO.getPrePayItemM());
				}else{
					ltsPaymentFormDTO.setPrePayItem(ltsPaymentFormDTO.getPrePayItemC());
				}
			} else {
				ltsPaymentFormDTO.setSalesMemoNum(null);
				ltsPaymentFormDTO.setPrePayItem(null);
			}

			if(ltsPaymentFormDTO.getPrePayItem() != null){
				ltsPaymentFormDTO.getPrePayItem().setSelected(true);
			}
			
			List<ItemDetailDTO> billItemList = ltsPaymentFormDTO
					.getBillItemList();
			if (StringUtils.isNotEmpty(ltsPaymentFormDTO
					.getSelectedBillItemId())) {
				for (int i = 0; billItemList != null && i < billItemList.size(); i++) {
					billItemList.get(i).setSelected(
							ltsPaymentFormDTO.getSelectedBillItemId().equals(
									billItemList.get(i).getItemId()));
				}
			}

			if (StringUtils.isEmpty(ltsPaymentFormDTO.getSelectButton())) {
				ltsPaymentFormDTO.setChangeReason("");
				if (orderCaptureDTO.getLtsRecontractForm() != null) {
					if (StringUtils.equals(
							ltsPaymentFormDTO.getExistingPayMethodType(),
							LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {
						clearCreditCardField(ltsPaymentFormDTO);
					} else if (StringUtils.equals(
							ltsPaymentFormDTO.getExistingPayMethodType(),
							LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
						clearAutoPayField(ltsPaymentFormDTO);
					} else {
						clearAutoPayField(ltsPaymentFormDTO);
						clearCreditCardField(ltsPaymentFormDTO);
					}
				} else {
					clearAutoPayField(ltsPaymentFormDTO);
					clearCreditCardField(ltsPaymentFormDTO);
				}

				ltsPaymentFormDTO.setNewPayMethodType(ltsPaymentFormDTO
						.getExistingPayMethodType());
			} else {
				ltsPaymentFormDTO.setNewPayMethodType(ltsPaymentFormDTO
						.getSelectButton());
			}
			
			//Billing Frequency Handling
			if(!ltsPaymentFormDTO.isDocTypeBR()){
				AccountDetailProfileLtsDTO accProfile = LtsSbOrderHelper.getPrimaryAccount(orderCaptureDTO.getLtsServiceProfile());
				if(ltsPaymentFormDTO.isRecontractCase()){
					accProfile = orderCaptureDTO.getLtsRecontractForm().getAccountDetailProfile();
				}
				AccountDetailProfileLtsDTO orgAccProfile = customerProfileLtsService.getAccountbyAcctNum(accProfile.getAcctNum(), LtsConstant.SYSTEM_ID_DRG);
				
				
				String newBillMedia = getNewBillMedia(ltsPaymentFormDTO);
				boolean isBillMediaChanged = !StringUtils.equals(orgAccProfile.getBillMedia(), newBillMedia);
				
				if(LtsConstant.PAYMENT_TYPE_AUTO_PAY.equals(ltsPaymentFormDTO.getNewPayMethodType())){
					accProfile.setBillFreq(LtsConstant.ACCT_BILL_FREQ_MONTHLY);
				}else if(isBillMediaChanged){
					if(!LtsConstant.DEVICE_TYPE_DEL.equals(orderCaptureDTO.getLtsDeviceSelectionForm().getSelectedDeviceType())
							|| LtsConstant.LTS_BILL_MEDIA_PPS_BILL.equals(newBillMedia)){
						accProfile.setBillFreq(LtsConstant.ACCT_BILL_FREQ_MONTHLY);
					}
				}
			}

			
			Locale locale = RequestContextUtils.getLocale(request);
			if(ltsPaymentFormDTO.isAllowModifyInd()){
				
				//##Paper Bill Handling
				if(ltsPaymentFormDTO.isDocTypeBR()){
					List<ItemDetailDTO> paperBRItemList  = ltsOfferService.getItemList(
							LtsConstant.ITEM_TYPE_PAPER_BILL_BR, locale.toString(), true,
							orderCaptureDTO.getBasketChannelId(), orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate());
					        ltsPaymentFormDTO.setPaperBillItem(paperBRItemList != null? paperBRItemList.get(0) : null);
				}else{
					if(StringUtils.equals(ltsPaymentFormDTO.getPaperBillCharge(), "Y")){
						List<ItemDetailDTO> paperGenerateItemList  = ltsOfferService.getItemList(
								LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE, locale.toString(), true,
								orderCaptureDTO.getBasketChannelId(), orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate());
								ltsPaymentFormDTO.setPaperBillItem(paperGenerateItemList != null? paperGenerateItemList.get(0) : null);
					}
					if(StringUtils.equals(ltsPaymentFormDTO.getPaperBillCharge(), "W")){
						List<ItemDetailDTO> paperWaiveItemList  = ltsOfferService.getItemList(
								LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE, locale.toString(), true,
								orderCaptureDTO.getBasketChannelId(), orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate());
								ltsPaymentFormDTO.setPaperBillItem(paperWaiveItemList != null ? paperWaiveItemList.get(0) : null);
					}
					if(StringUtils.isEmpty(ltsPaymentFormDTO.getPaperBillCharge()) && !ltsPaymentFormDTO.isRecontractCase()){
						List<ItemDetailDTO> paperUnchangeItemList  = ltsOfferService.getItemList(
								LtsConstant.ITEM_TYPE_PAPER_BILL, locale.toString(), true,
								orderCaptureDTO.getBasketChannelId(), orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate());
								ltsPaymentFormDTO.setPaperBillItem(paperUnchangeItemList != null ? paperUnchangeItemList.get(0) : null);
					}
				}
								
				if(StringUtils.equals(ltsPaymentFormDTO.getPaperBillCharge(), "Y") 
						|| StringUtils.isBlank(ltsPaymentFormDTO.getPaperBillCharge())
						|| StringUtils.equals(ltsPaymentFormDTO.getPaperBillCharge(), "NONE")){
					ltsPaymentFormDTO.setPaperWaiveReason(null);
				}
				
			}else{
					if(StringUtils.equals(ltsPaymentFormDTO.getPaperBillCharge(), "Y")){
						List<ItemDetailDTO> paperGenerateItemList  = ltsOfferService.getItemList(
								LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE, locale.toString(), true,
								orderCaptureDTO.getBasketChannelId(), orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate());
								ltsPaymentFormDTO.setPaperBillItem(paperGenerateItemList != null? paperGenerateItemList.get(0) : null);
					}
					if(StringUtils.equals(ltsPaymentFormDTO.getPaperBillCharge(), "W")){
						List<ItemDetailDTO> paperWaiveItemList  = ltsOfferService.getItemList(
								LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE, locale.toString(), true,
								orderCaptureDTO.getBasketChannelId(), orderCaptureDTO.getLtsMiscellaneousForm().getApplicationDate());
								ltsPaymentFormDTO.setPaperBillItem(paperWaiveItemList != null ? paperWaiveItemList.get(0) : null);
					}	
			}
			
			if(ltsPaymentFormDTO.getEmailBillItem()!= null){
				if(ltsPaymentFormDTO.getEmailBillItem().isSelected()){
					ltsPaymentFormDTO.setPaperBillItem(null);
				}else{
					ltsPaymentFormDTO.setEmailBillItem(null);
				}
			}
			orderCaptureDTO.setLtsPaymentForm(ltsPaymentFormDTO);

			return new ModelAndView(new RedirectView(nextView));
		} else {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_SUSPEND);
			paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, ltsPaymentFormDTO.getSuspendReason());
			paramMap.put(LtsConstant.REQUEST_PARAM_FROM_VIEW, viewName);
			return new ModelAndView(new RedirectView("ltsupgradeeyeorder.html"), paramMap);
		}

	}
	
	private String getNewBillMedia(LtsPaymentFormDTO form){
		if(form.getPaperBillItem() != null && form.getPaperBillItem().isSelected()){
			return LtsConstant.LTS_BILL_MEDIA_PAPER_BILL;
		}
		if(form.getEmailBillItem() != null && form.getEmailBillItem().isSelected()){
			return LtsConstant.LTS_BILL_MEDIA_PPS_BILL;
		}
		if(form.getNoBillItem() != null && form.getNoBillItem().isSelected()){
			return LtsConstant.LTS_BILL_MEDIA_DEVICE_BILL;
		}
		return null;
	}
	
	private ValidationResultDTO validateTheClubCsPortal(OrderCaptureDTO orderCapture, LtsPaymentFormDTO form) {
		
		if (isSelectedTheClubPremium(orderCapture) && !checkCspDummyDoc(form.getCustDocNum())) {  
			return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.ltspayment.cannotRegTheClub", new Object[] {}, this.locale)), null);
		}
		
		if (form.getCsPortalItem() == null || !form.getCsPortalItem().isSelected()) {  
			return new ValidationResultDTO(Status.VALID, null, null);
		}
		
		List<String> errorMsgList = new ArrayList<String>();
		String hktLoginId = null;
		String clubLoginId = null;
		
		//set The Club login and mobile to be same as HKT for case that register both portal
		if(StringUtils.equals(form.getCsPortalItem().getItemType(),LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL)){
			form.setClubEmail(form.getCspEmail());
			form.setClubMobile(form.getCspMobile());
			clubLoginId = form.getClubEmail();
			hktLoginId = form.getCspEmail();
		}
		else if (LtsConstant.ITEM_TYPE_THE_CLUB_BILL.equals(form.getCsPortalItem().getItemType())) {
			clubLoginId = form.getClubEmail();	
		}
		else if (LtsConstant.ITEM_TYPE_MYHKT_BILL.equals(form.getCsPortalItem().getItemType())) {
			hktLoginId = form.getCspEmail();	
		}
		
		validateCspInfo(form ,errorMsgList );
				
		String cspDocType = orderCapture.getLtsRecontractForm() != null ? 
					orderCapture.getLtsRecontractForm().getDocType()	: orderCapture.getLtsServiceProfile().getPrimaryCust().getDocType();
		String cspDocNum = orderCapture.getLtsRecontractForm() != null ? 
					orderCapture.getLtsRecontractForm().getDocId()	: orderCapture.getLtsServiceProfile().getPrimaryCust().getDocNum();

		CsPortalIdInqArqDTO cspRes = new CsPortalIdInqArqDTO();
					
		try {
			
			cspRes = (CsPortalIdInqArqDTO )csPortalService.checkCsIdForTheClubAndHkt(cspDocType, cspDocNum, hktLoginId, clubLoginId, null);
			
			if(cspRes != null){
				logger.debug("checkCsIdForTheClubAndHkt :: DocType:" + cspDocType + " DocNum:" + cspDocNum + "oCorrMyHktLi:" + cspRes.getoCorrMyHktLi() + "oCorrClubLi:" + cspRes.getoCorrClubLi());
				form.setCsldFailInd(csPortalService.isCsldReplyFail(cspRes.getReply()));
			} else{
				logger.debug("checkCsIdForTheClubAndHkt :: DocType:" + cspDocType + " DocNum:" + cspDocNum + "is NULL." );
			}
			
			if(cspRes.isHktLiInUse()&& !StringUtils.contains(hktLoginId, LtsConstant.CSP_DUMMY_EMAIL_DOMAIN)){
				errorMsgList.add(messageSource.getMessage("lts.ltspayment.alrdyRegMyHKT", new Object[] {}, this.locale));
			}
			if(cspRes.isClubLiInUse() && !StringUtils.contains(clubLoginId, LtsConstant.CSP_DUMMY_EMAIL_DOMAIN)){
				errorMsgList.add(messageSource.getMessage("lts.ltspayment.alrdyRegTheClub", new Object[] {}, this.locale));
			}
			
		} catch (Exception e) {
			logger.error("Failed to call checkCsIdForTheClubAndHkt: DocType:" +cspDocType +",DocNum:" +cspDocNum +",hktLogin:" +hktLoginId +",clublogin:" +clubLoginId);
			logger.error(ExceptionUtils.getFullStackTrace(e));
			form.setCsldFailInd(true);
			///throw new AppRuntimeException(e) ;
		}
		
		Gson gson = new Gson();		   		 
		// save records into W_ONLINE_CSPORTAL_TXN
        CsPortalTxnDTO csPortalTxn = new CsPortalTxnDTO();
		csPortalTxn.setApiTy(cspRes.getApiTy());
		csPortalTxn.setReply(cspRes.getReply());
		csPortalTxn.setReqObj(gson.toJson(cspRes));
		csPortalTxn.setSysId(cspRes.getSysId());
		csPortalTxn.setClubReply(cspRes.getoClubLiStatus());
		csPortalTxn.setMyhktReply(cspRes.getoMyHktLiStatus());
		
		form.setCspVaildateRsp(csPortalTxn);
   	 
		
		if (!errorMsgList.isEmpty()) {
			return new ValidationResultDTO(Status.INVAILD, errorMsgList, null);
		}
		
		return new ValidationResultDTO(Status.VALID, null, null);
	}
	
	private void validateCspInfo(LtsPaymentFormDTO pForm, List<String> errorMsgList ){
		if(pForm == null || pForm.getCsPortalItem() == null)
		{return;}
		
		if(!StringUtils.isEmpty(pForm.getCspMobile())){
			if(!ltsCommonService.isMobileNumPrefix(pForm.getCspMobile()) && !pForm.isCsPortalExist() 
					&& !StringUtils.equals(pForm.getCspMobile(),LtsConstant.CSP_DUMMY_MOBILE_NUM)){
				errorMsgList.add(messageSource.getMessage("lts.ltspayment.invalidMobNum", new Object[] {}, this.locale));
			}
		}else {
			if(!pForm.isCsPortalExist()){
				errorMsgList.add(messageSource.getMessage("lts.ltspayment.mobNumReq", new Object[] {}, this.locale));
			}
		}
		
		if(!StringUtils.isEmpty(pForm.getClubMobile())){
			if(!ltsCommonService.isMobileNumPrefix(pForm.getClubMobile()) && !pForm.isClubMembExist()
					&& !StringUtils.equals(pForm.getClubMobile(),LtsConstant.CSP_DUMMY_MOBILE_NUM)){
				errorMsgList.add(messageSource.getMessage("lts.ltspayment.invalidMobNum", new Object[] {}, this.locale));
			}
		}else {
			if(StringUtils.equals(pForm.getCsPortalItem().getItemType(),LtsConstant.ITEM_TYPE_THE_CLUB_BILL)){
				errorMsgList.add(messageSource.getMessage("lts.ltspayment.mobNumReq", new Object[] {}, this.locale));
			}
		}
		
		if(!StringUtils.isEmpty(pForm.getCspEmail())){
			if(!Utility.validateEmail(pForm.getCspEmail()) && !pForm.isCsPortalExist()){
				errorMsgList.add(messageSource.getMessage("lts.ltspayment.validPattern", new Object[] {}, this.locale));
			}
		}else {
			if(!pForm.isCsPortalExist()){
				errorMsgList.add(messageSource.getMessage("lts.ltspayment.emailAddrReq", new Object[] {}, this.locale));
			}
		}
		
		if(!StringUtils.isEmpty(pForm.getClubEmail())){
			if(!Utility.validateEmail(pForm.getClubEmail()) && !pForm.isClubMembExist()){
				errorMsgList.add(messageSource.getMessage("lts.ltspayment.validPattern", new Object[] {}, this.locale));
			}
		}else {
			if(StringUtils.equals(pForm.getCsPortalItem().getItemType(),LtsConstant.ITEM_TYPE_THE_CLUB_BILL)){
				errorMsgList.add(messageSource.getMessage("lts.ltspayment.emailAddrReq", new Object[] {}, this.locale));
			}
		}
}

	private ValidationResultDTO[] validateSubmit(HttpServletRequest request,
			OrderCaptureDTO orderCapture, LtsPaymentFormDTO form) {

		List<ValidationResultDTO> validationResultList = new ArrayList<ValidationResultDTO>();
		validationResultList.add(validateApprovalRemark(orderCapture, form));
		validationResultList.add(validateTheClubCsPortal(orderCapture, form));
		validationResultList.add(validatePaperBillWaiveReason(orderCapture, form));
		return validationResultList
				.toArray(new ValidationResultDTO[validationResultList.size()]);
	}

	private ValidationResultDTO validatePaperBillWaiveReason(OrderCaptureDTO orderCapture, LtsPaymentFormDTO form) {
		if (! ( ltsOfferService.isSelectedPaperBillItem(form) 
					&& LtsConstant.PAPER_BILL_CHARGE_WAIVE.equals(form.getPaperBillCharge())
					&& LtsConstant.PAPER_BILL_WAIVE_REASON_AGE_OVER_65.equals(form.getPaperWaiveReason()))) {
			return new ValidationResultDTO(Status.VALID, null, null); 
		}
		
		/* if paper bill ind exist (either waive/charge), skip validation */
		if (!form.isAllowModifyInd()){
			return new ValidationResultDTO(Status.VALID, null, null); 
		}
		
		String dateOfBirth = orderCapture.getLtsCustomerIdentificationForm().getDateOfBirth();
		if (StringUtils.isEmpty(dateOfBirth)) {
			return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.ltspayment.dobEmpty", new Object[] {}, this.locale)), null); 
		}
		
		final String PAPER_BILL_EFFECTIVE_DATE = "01/09/2016";
		String compareDate = 
			LtsDateFormatHelper.isDateOverdue(PAPER_BILL_EFFECTIVE_DATE, "dd/MM/yyyy") ? LtsDateFormatHelper.getSysDate("dd/MM/yyyy") 
					: PAPER_BILL_EFFECTIVE_DATE; 
		
		if (!LtsSbOrderHelper.isAgeOver(dateOfBirth, compareDate)) {
			return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.ltspayment.ageNotOver65", new Object[] {}, this.locale)), null); 
		}
		return new ValidationResultDTO(Status.VALID, null, null);
	}
	
	private ValidationResultDTO validateApprovalRemark(
			OrderCaptureDTO orderCapture, LtsPaymentFormDTO form) {

		if (orderCapture.getLtsWqRemarkForm() != null) {
			return new ValidationResultDTO(Status.VALID, null, null);
		}

		String errorMsg = messageSource.getMessage("lts.ltspayment.inputAppvRmk", new Object[] {}, this.locale);

		if (form.getErChargeItemList() != null
				&& !form.getErChargeItemList().isEmpty()) {
			for (ItemDetailDTO itemDetail : form.getErChargeItemList()) {
				if ("PA".equals(itemDetail.getPenaltyHandling())) {
					return new ValidationResultDTO(Status.INVAILD,
							Lists.newArrayList(errorMsg), null);
				}
			}
		}

		return new ValidationResultDTO(Status.VALID, null, null);
	}

	private void clearAutoPayField(LtsPaymentFormDTO ltsPaymentFormDTO) {
		ltsPaymentFormDTO.setAutoPayUpperLimit(null);
		ltsPaymentFormDTO.setThirdPartyBankAccount(null);
		ltsPaymentFormDTO.setBankAccHolderName(null);
		ltsPaymentFormDTO.setBankAccHolderDocNum("");
		ltsPaymentFormDTO.setBankAccHolderDocNum(null);
		ltsPaymentFormDTO.setBankCode(null);
		ltsPaymentFormDTO.setBranchCode(null);
		ltsPaymentFormDTO.setBankAccNum(null);
		ltsPaymentFormDTO.setAutoPayUpperLimit(null);
	}

	private void clearCreditCardField(LtsPaymentFormDTO ltsPaymentFormDTO) {
		ltsPaymentFormDTO.setThirdPartyCard(null);
		ltsPaymentFormDTO.setCardHolderName(null);
		ltsPaymentFormDTO.setCardHolderDocNum(null);
		ltsPaymentFormDTO.setCardHolderDocType("");
		ltsPaymentFormDTO.setCardNum(null);
		ltsPaymentFormDTO.setCardType("");
		ltsPaymentFormDTO.setCardVerified(false);
		ltsPaymentFormDTO.setExpireMonth(0);
		ltsPaymentFormDTO.setExpireYear(0);
	}

	protected Map<String, List> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, List> referenceData = new HashMap<String, List>();

		List<LookupItemDTO> autopayIssueBankList = ltsPaymentService
				.getBankCode();
		Comparator<LookupItemDTO> c = new Comparator<LookupItemDTO>() {
			public int compare(LookupItemDTO o1, LookupItemDTO o2) {
				return Integer.parseInt(o1.getItemKey())
						- Integer.parseInt(o2.getItemKey());
			}
		};

		Collections.sort(autopayIssueBankList, c);
		referenceData.put("autopayIssueBankList", autopayIssueBankList);

		String bankCode = (String) request.getAttribute("bankCode");
		if (!StringUtils.isEmpty(bankCode)) {
			List<LookupItemDTO> branchList = ltsPaymentService
					.getBranchCode(bankCode);

			Collections.sort(branchList, c);
			referenceData.put("branchList", branchList);
		}
		
		referenceData.put("erDelChargeHandleList", Arrays.asList(erDelHandleCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("erEyeChargeHandleList", sortErChargeHandleList(Arrays.asList(erEyeHandleCacheService.getCodeLkupDAO().getCodeLkup())));
		referenceData.put("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("creditCardTypeList", Arrays.asList(creditCardTypeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("optOutReasonList", Arrays.asList(optOutReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("paperWaiveReasonList", Arrays.asList(waiveReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("yearList", ltsPaymentService.getCcExpireYearList());

		return referenceData;
	}

	private List<LookupItemDTO> sortErChargeHandleList(List<LookupItemDTO> erChargeHandleList) {
		if (erChargeHandleList == null || erChargeHandleList.isEmpty()) {
			return null;
		}
		
		List<LookupItemDTO> sortedList = new ArrayList<LookupItemDTO>();
		for (LookupItemDTO lookupItem : erChargeHandleList) {
			if (LtsConstant.ER_CHARGE_MEET_REQUIRE_WAIVE.equals(lookupItem.getItemKey())) {
				sortedList.add(lookupItem);
			}
		}
		for (LookupItemDTO lookupItem : erChargeHandleList) {
			if (LtsConstant.ER_CHARGE_1ST_TIME_WAIVE.equals(lookupItem.getItemKey())) {
				sortedList.add(lookupItem);
			}
		}
		for (LookupItemDTO lookupItem : erChargeHandleList) {
			if (LtsConstant.ER_CHARGE_PUBLIC_HSE_WAIVE.equals(lookupItem.getItemKey())) {
				sortedList.add(lookupItem);
			}
		}
		for (LookupItemDTO lookupItem : erChargeHandleList) {
			if (LtsConstant.ER_CHARGE_MARKETING_APPROVE_WAIVE.equals(lookupItem.getItemKey())) {
				sortedList.add(lookupItem);
			}
		}
		for (LookupItemDTO lookupItem : erChargeHandleList) {
			if (LtsConstant.ER_CHARGE_NO_CHARGE_2ND_LINE.equals(lookupItem.getItemKey())) {
				sortedList.add(lookupItem);
			}
		}
		
		for (LookupItemDTO lookupItem : erChargeHandleList) {
			if (sortedList.contains(lookupItem)) {
				continue;
			}
			sortedList.add(lookupItem);
		}
		return sortedList;
	}
	
	private void setupExistPayMthd(OrderCaptureDTO orderCaptureDTO, LtsPaymentFormDTO ltsPaymentFormDTO) {
		if (orderCaptureDTO.getLtsRecontractForm() != null) {
			AccountDetailProfileLtsDTO acctDetail = orderCaptureDTO.getLtsRecontractForm().getAccountDetailProfile();
			if (acctDetail != null) {
				ltsPaymentFormDTO.setExistingPayMethodType(acctDetail.getPayMethod());
				ltsPaymentFormDTO.setExistBillAccNum(acctDetail.getAcctNum());
				ltsPaymentFormDTO.setExistingCreditCardNum(acctDetail.getCreditCardNum());
				if (StringUtils.isBlank(ltsPaymentFormDTO.getRecontractAccountNo())) {
					ltsPaymentFormDTO.setRecontractAccountNo(acctDetail.getAcctNum());
				}
			}
		} else {
			AccountDetailProfileLtsDTO[] profileDTO = orderCaptureDTO.getLtsServiceProfile().getAccounts();
			if (profileDTO != null) {
				for (AccountDetailProfileLtsDTO accProfile : profileDTO) {
					if (accProfile.isPrimaryAcctInd()) {
						ltsPaymentFormDTO.setExistingPayMethodType(accProfile.getPayMethod());
						ltsPaymentFormDTO.setExistBillAccNum(accProfile.getAcctNum());
						ltsPaymentFormDTO.setExistingCreditCardNum(accProfile.getCreditCardNum());
						break;
					}
				}
			}
		}

	}

	private int getTenure(OrderCaptureDTO orderCaptureDTO) {

		int ltsTenure = orderCaptureDTO.getLtsServiceProfile().getLtsTenure();
		int imsTenure = orderCaptureDTO.getLtsServiceProfile().getImsTenure();

		return ltsTenure > imsTenure ? ltsTenure : imsTenure;
	}

	private void setupCustomerInfo(OrderCaptureDTO orderCaptureDTO,
			LtsPaymentFormDTO ltsPaymentFormDTO) {
		String custName = null;
		String custDocType = null;
		String custDocNum = null;
		if (orderCaptureDTO.getLtsRecontractForm() != null) {
			LtsRecontractFormDTO recontract = orderCaptureDTO
					.getLtsRecontractForm();
			if (StringUtils.isNotBlank(recontract.getCompanyName())) {
				custName = recontract.getCompanyName();
			} else {
				custName = recontract.getLastName() + " " + recontract.getFirstName();
			}
			custDocType = recontract.getDocType();
			custDocNum = recontract.getDocId();
		} else {
			custName = orderCaptureDTO.getLtsServiceProfile().getPrimaryCust()
					.getLastName()
					+ " "
					+ orderCaptureDTO.getLtsServiceProfile().getPrimaryCust()
							.getFirstName();
			custDocType = orderCaptureDTO.getLtsCustomerIdentificationForm()
					.getDocType();
			custDocNum = orderCaptureDTO.getLtsCustomerIdentificationForm()
					.getId();
		}
		ltsPaymentFormDTO.setCustName(custName.length() > 30? custName.substring(0, 30): custName);
		ltsPaymentFormDTO.setCustDocType(custDocType);
		ltsPaymentFormDTO.setCustDocNum(custDocNum);
	}

	public boolean checkCspDummyDoc(String docId) {
		for (String dummyString : LtsConstant.CSP_DUMMY_DOC_STRINGS) {
			if (StringUtils.containsIgnoreCase(docId, dummyString)) {
				
				return false;
			}
		}
		
		return isSixDigits(docId);
	}
	
    public boolean isSixDigits(String pDocNum){
			
		   int i;
		   int totalNum = 0;
		   String[] docNumAry = pDocNum.split("");
		   
		   List<String> digitList = new ArrayList<String>();
		   
		   for(i=0; i<10; i++){
			   digitList.add(Integer.toString(i));
		   }
		   
		   for(i=0; i< docNumAry.length; i++){
			   if(digitList.contains(docNumAry[i])){
				   totalNum++;
			   }
		   }
		   
		   if(totalNum >= 6){
		     return true;
		   }
		   
	       return false;  
	}
	
    
    private boolean isSelectedTheClubPremium(OrderCaptureDTO orderCapture) {
    	SbOrderDTO sbOrder = orderCapture.getSbOrder();
    	
    	if (sbOrder != null) {
    		ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
    		if (ltsService != null && ArrayUtils.isNotEmpty(ltsService.getItemDtls())) {
    			for (ItemDetailLtsDTO subcItem : ltsService.getItemDtls()) {
    				if (LtsConstant.ITEM_TYPE_PREMIUM.equals(subcItem.getItemType())) {
    					String theClubPoint = (String)theClubPremiumCacheService.get(subcItem.getItemId());
    					if (StringUtils.isNotBlank(theClubPoint)) {
    						return true;
    					}
    				}
    			}
    		}
    	}
    	
    	if (orderCapture.getLtsPremiumSelectionForm() != null) {
    		if (orderCapture.getLtsPremiumSelectionForm().getPremiumSetList() != null 
    				&& !orderCapture.getLtsPremiumSelectionForm().getPremiumSetList().isEmpty()) {
    			for (ItemSetDetailDTO premiumSet : orderCapture.getLtsPremiumSelectionForm().getPremiumSetList()) {
    				if (ArrayUtils.isNotEmpty(premiumSet.getItemDetails())) {
    					for (ItemDetailDTO premium : premiumSet.getItemDetails()) {
    						if (premium.isSelected()) {
    							String theClubPoint = (String)theClubPremiumCacheService.get(premium.getItemId());
    	    					if (StringUtils.isNotBlank(theClubPoint)) {
    	    						return true;
    	    					}		
    						}
    					}
    				}
    			}
    		}
    	}
    	
    	return false;
    }
    
	private void getCsPortalItem(String locale, String cspDocType, String cspDocNum, LtsPaymentFormDTO ltsPaymentFormDTO, boolean isDocValid, OrderCaptureDTO orderCapture)
	throws Exception
	{
		
		/* MELODY */
		boolean isHKT = false;
		boolean isClub = false;
		String clubEmail = null;
		String hktEmail = null;
		String custName = null;
		
		//enable check box for orders before forced club registration launch 
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTimeFormatter fmt2 = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTime clubLaunchDate = fmt.parseDateTime((String)clubLaunchDateLkupCacheService.get("DATE"));
		DateTime appDate = fmt2.parseDateTime(StringUtils.substring(orderCapture.getLtsMiscellaneousForm().getApplicationDate(),0,10));
		
		ltsPaymentFormDTO.setCspExclude(appDate.isBefore(clubLaunchDate));

        
		if(orderCapture.getLtsRecontractForm() != null){
			custName = StringUtils.substring(StringUtils.deleteWhitespace(StringUtils.lowerCase(StringUtils.replace(orderCapture.getLtsRecontractForm().getLastName(),",", "")
																			+ StringUtils.replace(orderCapture.getLtsRecontractForm().getFirstName(),",", ""))),0,29); 			
		}else{
			custName = StringUtils.substring(StringUtils.deleteWhitespace(StringUtils.lowerCase(StringUtils.replace(orderCapture.getLtsServiceProfile().getPrimaryCust().getLastName(),",", "")
																			+ StringUtils.replace(orderCapture.getLtsServiceProfile().getPrimaryCust().getFirstName(),",", ""))),0,29);
		}
	
//		boolean hktEmpty = true;
//		boolean clubEmpty = true; 
		
		   CsPortalIdInqArqDTO  cspResponse = checkCsIdForTheClubAndHkt(cspDocType, cspDocNum, "", "", "",ltsPaymentFormDTO);
   
		   	 ltsPaymentFormDTO.setPhantomAcct(StringUtils.equals(cspResponse.getoPhantomAcc(),"Y"));
		  	     
		   	 if(cspResponse.isCustAldyMyHkt() && !StringUtils.isEmpty(cspResponse.getoCorrMyHktLi())){
	    		isHKT = true;
	    		hktEmail = cspResponse.getoCorrMyHktLi();
	    	 }
		   	 if(cspResponse.isCustAldyTheClub() && !StringUtils.isEmpty(cspResponse.getoCorrClubLi())){
	    		isClub = true;
	    		clubEmail = cspResponse.getoCorrClubLi();
	    	 }
		   	 
		   	// To create dummy email address
		   	// customer first name + last name should with maximum length of 30 characters
		   	 String dummyEmail = custName + "@theclub.com.hk";
		   	 
		   	 //check if dummy email is in use
		   	 cspResponse= checkCsIdForTheClubAndHkt(cspDocType	, cspDocNum, dummyEmail, dummyEmail, "", ltsPaymentFormDTO);
	   			   
	         if(StringUtils.equals(cspResponse.getoClubLiStatus(), LtsCsPortalBackendConstant.CSP_REPLY_LOGIN_ID_IN_USE) ||
	        		 StringUtils.equals(cspResponse.getoMyHktLiStatus(), LtsCsPortalBackendConstant.CSP_REPLY_LOGIN_ID_IN_USE)){
		   		  //if dummy email is in use, generate another dummy email
	        	    Date date = new Date();
		   		    SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			   
		   		    dummyEmail = custName +	sdf.format(date) + "@theclub.com.hk";
		   }
		   ltsPaymentFormDTO.setDummyClubEmail(dummyEmail);

		   
		   
		   if(isClub && StringUtils.isEmpty(ltsPaymentFormDTO.getClubEmail())){
		    ltsPaymentFormDTO.setClubEmail(clubEmail);
		   }
		   if(isHKT && StringUtils.isEmpty(ltsPaymentFormDTO.getCspEmail())){
 			ltsPaymentFormDTO.setCspEmail(hktEmail);
		   }

		   
      List<ItemDetailDTO> tempItemList;

		/* obtain CSP item */
		ItemDetailDTO cspItem = null;
		ItemDetailDTO viewBillItem = null;
//		if (!isCspExist || !isDocValid) {
		
		
      if ( (!isHKT && isClub) || !isDocValid) {
    	
    	  // reg HKT + view bill
			tempItemList = ltsPaymentService.getItemDetailByType(
					LtsConstant.ITEM_TYPE_MYHKT_BILL,  
					LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale.toString());
			if (tempItemList != null && tempItemList.size() > 0) {
				cspItem = ltsOfferService
						.getItemAttbByItemAttbAssign((tempItemList.get(0)));
			}
      } else if (isHKT && !isClub){
		
			//reg club 
			tempItemList = ltsPaymentService.getItemDetailByType(
					LtsConstant.ITEM_TYPE_THE_CLUB_BILL,
					LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale.toString());
			if (tempItemList != null && tempItemList.size() > 0) {
				cspItem = ltsOfferService
						.getItemAttbByItemAttbAssign((tempItemList.get(0)));
			}
			
			//view bill item
			tempItemList = ltsPaymentService.getItemDetailByType(
					LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL,
					LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale.toString());
			if (tempItemList != null && tempItemList.size() > 0) {
				viewBillItem = ltsOfferService
						.getItemAttbByItemAttbAssign((tempItemList.get(0)));
			}
		} else if (!isHKT && ! isClub){

			// reg both
			tempItemList = ltsPaymentService.getItemDetailByType(
					LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL,
					LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale.toString());
			if (tempItemList != null && tempItemList.size() > 0) {
				cspItem = ltsOfferService
						.getItemAttbByItemAttbAssign((tempItemList.get(0)));
			}
		} else if ((isHKT && isClub)){
			//no need reg / only view bill item
			tempItemList = ltsPaymentService.getItemDetailByType(
					LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL,
					LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale.toString());
			if (tempItemList != null && tempItemList.size() > 0) {
				viewBillItem = ltsOfferService
						.getItemAttbByItemAttbAssign((tempItemList.get(0)));
			}
			ltsPaymentFormDTO.setCsPortalItem(cspItem);
		}

		/* replace with new one if not same as existing one */
		if (cspItem != null && (ltsPaymentFormDTO.getCsPortalItem() == null
				|| !StringUtils.equals(cspItem.getItemId(),
						ltsPaymentFormDTO.getCsPortalItem().getItemId()))) {
			ltsPaymentFormDTO.setCsPortalItem(cspItem);
		}
		
		if (viewBillItem != null &&(ltsPaymentFormDTO.getViewBillItem() == null
				|| !StringUtils.equals(viewBillItem.getItemId(),
						ltsPaymentFormDTO.getViewBillItem().getItemId()))) {
				ltsPaymentFormDTO.setViewBillItem(viewBillItem);
		}
		

		if (!isDocValid && ltsPaymentFormDTO.getCsPortalItem() != null) {
			ltsPaymentFormDTO.getCsPortalItem().setSelected(false);
		}else{
			if(ltsPaymentFormDTO.getCsPortalItem()!=null){
				ltsPaymentFormDTO.getCsPortalItem().setSelected(true);
			}
			if(ltsPaymentFormDTO.getViewBillItem() != null){
				ltsPaymentFormDTO.getViewBillItem().setSelected(true);
			}
		}
		
		boolean isForceSelectTheClub = isSelectedTheClubPremium(orderCapture);
		if (isForceSelectTheClub && !isClub) {
			ltsPaymentFormDTO.getCsPortalItem().setSelected(true);
			ltsPaymentFormDTO.getCsPortalItem().setMdoInd(LtsConstant.ITEM_MDO_MANDATORY);
		}else if(cspItem != null){
			ltsPaymentFormDTO.getCsPortalItem().setMdoInd(cspItem.getMdoInd());
		}
		
		ltsPaymentFormDTO.setCsPortalExist(isHKT);
		ltsPaymentFormDTO.setClubMembExist(isClub);
		ltsPaymentFormDTO.setOptOut("N");
		ltsPaymentFormDTO.setEnableCsp(((!isHKT && isClub) || ltsPaymentFormDTO.isCspExclude()) && isDocValid);
	}
	 
	private CsPortalIdInqArqDTO checkCsIdForTheClubAndHkt(String cspDocType, String cspDocNum, String hktEmail, String clubEmail, String user,LtsPaymentFormDTO ltsPaymentFormDTO){
		
		CsPortalIdInqArqDTO cspResponse = null;
		
		try{
			cspResponse = (CsPortalIdInqArqDTO)csPortalService.checkCsIdForTheClubAndHkt(cspDocType, cspDocNum, hktEmail, clubEmail, user);
					
		}catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			if(!StringUtils.isEmpty(cspDocNum)){
				ltsPaymentFormDTO.setCsldFailInd(csPortalService.isCsldReplyFail(cspResponse.getReply()));
		     }
		}	
		
		if(cspResponse != null){
			logger.debug("checkCsIdForTheClubAndHkt :: " + "oCorrMyHktLi:" + cspResponse.getoCorrMyHktLi() + "oCorrClubLi:" + cspResponse.getoCorrClubLi());
		}else{
			logger.debug("checkCsIdForTheClubAndHkt :: is NULL." );
		}
		
		return cspResponse;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public PaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public LtsPaymentService getLtsPaymentService() {
		return ltsPaymentService;
	}

	public void setLtsPaymentService(LtsPaymentService ltsPaymentService) {
		this.ltsPaymentService = ltsPaymentService;
	}

	public CodeLkupCacheService getCreditCardTypeLkupCacheService() {
		return creditCardTypeLkupCacheService;
	}

	public void setCreditCardTypeLkupCacheService(
			CodeLkupCacheService creditCardTypeLkupCacheService) {
		this.creditCardTypeLkupCacheService = creditCardTypeLkupCacheService;
	}

	public CodeLkupCacheService getSuspendReasonLkupCacheService() {
		return suspendReasonLkupCacheService;
	}

	public void setSuspendReasonLkupCacheService(
			CodeLkupCacheService suspendReasonLkupCacheService) {
		this.suspendReasonLkupCacheService = suspendReasonLkupCacheService;
	}

	public CodeLkupCacheService getOptOutReasonLkupCacheService() {
		return optOutReasonLkupCacheService;
	}

	public void setOptOutReasonLkupCacheService(
			CodeLkupCacheService optOutReasonLkupCacheService) {
		this.optOutReasonLkupCacheService = optOutReasonLkupCacheService;
	}

	public CsPortalService getCsPortalService() {
		return csPortalService;
	}

	public void setCsPortalService(CsPortalService csPortalService) {
		this.csPortalService = csPortalService;
	}

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}

	public LtsProfileService getLtsProfileService() {
		return ltsProfileService;
	}

	public void setLtsProfileService(LtsProfileService ltsProfileService) {
		this.ltsProfileService = ltsProfileService;
	}
	
	public CodeLkupCacheService getErDelHandleCacheService() {
		return erDelHandleCacheService;
	}

	public void setErDelHandleCacheService(
			CodeLkupCacheService erDelHandleCacheService) {
		this.erDelHandleCacheService = erDelHandleCacheService;
	}

	public CodeLkupCacheService getErEyeHandleCacheService() {
		return erEyeHandleCacheService;
	}

	public void setErEyeHandleCacheService(
			CodeLkupCacheService erEyeHandleCacheService) {
		this.erEyeHandleCacheService = erEyeHandleCacheService;
	}

	public CodeLkupCacheService getTheClubPremiumCacheService() {
		return theClubPremiumCacheService;
	}

	public void setTheClubPremiumCacheService(
			CodeLkupCacheService theClubPremiumCacheService) {
		this.theClubPremiumCacheService = theClubPremiumCacheService;
	}

	public CodeLkupCacheService getClubLaunchDateLkupCacheService() {
		return clubLaunchDateLkupCacheService;
	}

	public void setClubLaunchDateLkupCacheService(
			CodeLkupCacheService clubLaunchDateLkupCacheService) {
		this.clubLaunchDateLkupCacheService = clubLaunchDateLkupCacheService;
	}

	public CodeLkupCacheService getWaiveReasonLkupCacheService() {
		return waiveReasonLkupCacheService;
	}

	public void setWaiveReasonLkupCacheService(
			CodeLkupCacheService waiveReasonLkupCacheService) {
		this.waiveReasonLkupCacheService = waiveReasonLkupCacheService;
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

	public EyeProfileCountService getEyeProfileCountService() {
		return eyeProfileCountService;
	}

	public void setEyeProfileCountService(EyeProfileCountService eyeProfileCountService) {
		this.eyeProfileCountService = eyeProfileCountService;
	}

}
