package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupCriteriaDTO;
import com.bomwebportal.lts.dto.PrepaymentLkupResultDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO.BillMediaDtl;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPersonalInfoFormDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsPaymentService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.bom.EyeProfileCountService;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.acq.LtsAcqSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsAcqPaymentMethodController extends SimpleFormController{

    protected final Log log = LogFactory.getLog(getClass());
    
	private final String nextView = "ltsacqnumselectionandpipb.html";
	private final String viewName = "ltsacqpaymentmethod";
	private final String nextView2 = "ltsacqpersonalinfo.html";
    
	public LtsAcqPaymentMethodController(){
		this.setFormView("lts/acq/ltsacqpaymentmethod");	
		this.setCommandName("ltspaymentmethod");
		this.setCommandClass(LtsAcqPaymentMethodFormDTO.class);
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	
    private LtsPaymentService ltsPaymentService;
	private CodeLkupCacheService creditCardTypeLkupCacheService;
	private CodeLkupCacheService suspendReasonLkupCacheService;
	private LtsOfferService ltsOfferService;
	private LtsCommonService ltsCommonService;
	private CustomerProfileLtsService customerProfileLtsService;
	private LtsProfileService ltsProfileService;
	private ImsProfileService imsProfileService;
	private CodeLkupCacheService ltsDsSuspendReasonLkupCacheService;
	private CodeLkupCacheService ltsAcqPrepayTenureLkupCacheService;
	private EyeProfileCountService eyeProfileCountService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}else{
			logger.warn("LtsAcqPaymentMethodController custnum: " + orderCaptureDTO.getCustomerDetailProfileLtsDTO().getCustNum() + " userid " + orderCaptureDTO.getLtsAcqSalesInfoForm().getStaffId());
			if(!LtsAcqSbOrderHelper.isDummyCustomer(orderCaptureDTO.getCustomerDetailProfileLtsDTO().getCustNum())) {
				for(AccountDetailProfileLtsDTO acctDtl : orderCaptureDTO.getAccountDetailProfileLtsDTO()){
					logger.warn("LtsAcqPaymentMethodController acctnum: "+ acctDtl.getAcctNum() + " custnum: " + orderCaptureDTO.getCustomerDetailProfileLtsDTO().getCustNum() + " userid " + orderCaptureDTO.getLtsAcqSalesInfoForm().getStaffId());
					if(!StringUtils.equals(acctDtl.getAcctNum().substring(0,8),orderCaptureDTO.getCustomerDetailProfileLtsDTO().getCustNum()) && !LtsAcqSbOrderHelper.isDummyAccount(acctDtl.getAcctNum())){
						// acctNum and custNum not match, clear account and personalInfo, back to personalInfo
						AccountDetailProfileLtsDTO[] newAcctArray = {new AccountDetailProfileLtsDTO()};
						orderCaptureDTO.setAccountDetailProfileLtsDTO(newAcctArray);
						CustomerDetailProfileLtsDTO newCustomerDtl = new CustomerDetailProfileLtsDTO();
						orderCaptureDTO.setCustomerDetailProfileLtsDTO(newCustomerDtl);
						LtsAcqPaymentMethodFormDTO ltsAcqPaymentMethodForm = new LtsAcqPaymentMethodFormDTO();
						ltsAcqPaymentMethodForm.setAcctNotMatch(true);
						orderCaptureDTO.setLtsAcqPaymentMethodFormDTO(ltsAcqPaymentMethodForm);
						orderCaptureDTO.setLtsAcqPersonalInfoForm(null);
						orderCaptureDTO.setLtsAcqAccountSelectionForm(null);	
						orderCaptureDTO.setLtsAcqBillMediaBillLangForm(null);
						orderCaptureDTO.setLtsAcqBillingAddressForm(null);
						orderCaptureDTO.setLtsModemArrangementForm(null);
						return new ModelAndView(new RedirectView(nextView2));
					}
				}
			}
		}
		return super.handleRequestInternal(request, response);
		
	}
	
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqPaymentMethodFormDTO form = orderCaptureDTO.getLtsAcqPaymentMethodFormDTO();		
		
		Locale locale = RequestContextUtils.getLocale(request);
		
		if(form == null){
			form = new LtsAcqPaymentMethodFormDTO();
			List<PaymentMethodDtl> payMtdDtlList = new ArrayList<PaymentMethodDtl>();
			for(AccountDetailProfileLtsDTO acctDtl : orderCaptureDTO.getAccountDetailProfileLtsDTO()){
				PaymentMethodDtl payMtdDtl = form.new PaymentMethodDtl();
				payMtdDtl.setAcctNum(acctDtl.getAcctNum());
				if (acctDtl.getAcctChrgType().contains("I")){
				    payMtdDtl.setAcctChrgTypeI(true);
				}else{
					payMtdDtl.setAcctChrgTypeI(false);
				}
				payMtdDtl.setAcctProfile(acctDtl);
				payMtdDtlList.add(payMtdDtl);				
			}
			form.setPaymentMethodDtlList(payMtdDtlList);
		}
		
		form.setIddDeposit(false);
		setupCustomerInfo(orderCaptureDTO, form);
		setupExistPayMthd(orderCaptureDTO, form);
		setupNewPaymentDtl(orderCaptureDTO, form);
//		setupPrepayment(orderCaptureDTO, form, locale);
		setupPrepaymentByLkup(orderCaptureDTO, form, locale);
       
       if(containIdd(orderCaptureDTO)){
    	   form.setIddDeposit(true);
    	   request.setAttribute("iddDeposit", true);
       }     
	
       if (form.getPrimaryPaymentMethodDtl()!=null) {
    	   request.setAttribute("dummyAccount", LtsAcqSbOrderHelper.isDummyAccount(
    			   form.getPrimaryPaymentMethodDtl().getAcctNum()));
       }
       if(StringUtils.isNotBlank(orderCaptureDTO.getSuspendRemark())){
    	   form.setSuspendRemarks(orderCaptureDTO.getSuspendRemark());
       }else{
    	   form.setSuspendRemarks(null);
       }
	   
	   return form;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
//		String nextView = (String)request.getAttribute("nextView");
		logger.info("nextView: "+ nextView);
		
		String currentView = (String)request.getParameter("currentView");
		logger.info("currentView: "+ currentView);

		
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		LtsAcqPaymentMethodFormDTO ltsPaymentFormDTO = (LtsAcqPaymentMethodFormDTO)command;
		
		if(ltsPaymentFormDTO.getSubmitInd().compareTo("SUBMIT") == 0){
			
			if (orderCaptureDTO.isChannelCs()) {
				for (PaymentMethodDtl paymentMethodDtl : ltsPaymentFormDTO.getPaymentMethodDtlList()) {
					if(!StringUtils.equals(paymentMethodDtl.getExistingPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)
							&& StringUtils.equals(paymentMethodDtl.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
						if (paymentMethodDtl.getThirdPartyCard()) {
							paymentMethodDtl.setCardHolderDocType(LtsConstant.DOC_TYPE_PASSPORT);
							paymentMethodDtl.setCardHolderDocNum("NOT_PROVIDED");
						}
						paymentMethodDtl.setCardVerified(false);
					}
				}
			}
			for (PaymentMethodDtl paymentMethodDtl : ltsPaymentFormDTO.getPaymentMethodDtlList()) {
				if(StringUtils.equals(paymentMethodDtl.getExistingPayMethodType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)
						&& !StringUtils.equals(paymentMethodDtl.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {					
					paymentMethodDtl.setTermCd(LtsConstant.PAYMENT_TERM_CD_VERBAL);

				}
			}
			orderCaptureDTO.setLtsAcqPaymentMethodFormDTO(ltsPaymentFormDTO);
			if(StringUtils.isNotBlank(ltsPaymentFormDTO.getSuspendRemarks())){
				orderCaptureDTO.setSuspendRemark(ltsPaymentFormDTO.getSuspendRemarks());
			}else{
				orderCaptureDTO.setSuspendRemark(null);
			}
			List<AccountDetailProfileLtsDTO> acct = new ArrayList<AccountDetailProfileLtsDTO>();
			
			
			for (AccountDetailProfileLtsDTO acctProfile : orderCaptureDTO.getAccountDetailProfileLtsDTO()) {
				/* account will not be created during dragon down time, skip the logic*/
				if(!ltsCommonService.isNowDrgDownTime() && !LtsAcqSbOrderHelper.isDummyCustomer(orderCaptureDTO.getCustomerDetailProfileLtsDTO().getCustNum())) {
					AccountDetailProfileLtsDTO orgAccProfile = customerProfileLtsService.getAccountbyAcctNum(acctProfile.getAcctNum(), LtsConstant.SYSTEM_ID_DRG);
					BillMediaDtl correspondingBillMediaDtl = null;
					for(BillMediaDtl billMediaDtl : orderCaptureDTO.getLtsAcqBillMediaBillLangForm().getBillMediaDtlList()){
						if(StringUtils.equals(billMediaDtl.getAcctNum(), acctProfile.getAcctNum())){
							correspondingBillMediaDtl = billMediaDtl;
							break;
						}
					}
					
					//Billing Frequency Handling
					String newBillMedia = getNewBillingMedia(correspondingBillMediaDtl);
					boolean isBillMediaChanged = !StringUtils.equals(orgAccProfile.getBillMedia(), newBillMedia);
					
					for (PaymentMethodDtl paymentMethodDtl : orderCaptureDTO.getLtsAcqPaymentMethodFormDTO().getPaymentMethodDtlList()) {
						if(StringUtils.equals(paymentMethodDtl.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)){
							if(StringUtils.equals(acctProfile.getAcctNum(), paymentMethodDtl.getAcctNum())){
								acctProfile.setBillFreq(LtsConstant.ACCT_BILL_FREQ_MONTHLY);
								break;
							}
						}
					}
					if((isBillMediaChanged || orderCaptureDTO.getLtsAcqAccountSelectionForm().isNewAccountSelected())
							&& (orderCaptureDTO.isEyeOrder() || StringUtils.equals(newBillMedia, LtsConstant.LTS_BILL_MEDIA_PPS_BILL))){
						acctProfile.setBillFreq(LtsConstant.ACCT_BILL_FREQ_MONTHLY);
					}
					
				}
				
				acct.add(acctProfile);
			}
			
			orderCaptureDTO.setAccountDetailProfileLtsDTO(acct.toArray(new AccountDetailProfileLtsDTO[acct.size()]));
			
			return new ModelAndView(new RedirectView(nextView));
		}else{
			 Map<String, Object> paramMap = new HashMap<String, Object>();
		        paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_SUSPEND);
		        paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, ltsPaymentFormDTO.getSuspendReason());
				paramMap.put(LtsConstant.REQUEST_PARAM_FROM_VIEW, viewName);
		        return new ModelAndView(new RedirectView(LtsConstant.NEW_ACQ_ORDER_VIEW), paramMap);
		}
		
	}
	
	private String getNewBillingMedia(BillMediaDtl billMediaDtl){
		if(billMediaDtl.getPaperBillItem() != null && billMediaDtl.getPaperBillItem().isSelected()){
			return LtsConstant.LTS_BILL_MEDIA_PAPER_BILL;
		}
		if(billMediaDtl.getEmailBillItem() != null && billMediaDtl.getEmailBillItem().isSelected()){
			return LtsConstant.LTS_BILL_MEDIA_PPS_BILL;
		}		
		return null;
		
	}
	
	
	private void clearAutoPayField(PaymentMethodDtl payMtdDtl){
		payMtdDtl.setAutoPayUpperLimit(null);
		payMtdDtl.setThirdPartyBankAccount(null);
		payMtdDtl.setBankAccHolderName(null);
		payMtdDtl.setBankAccHolderDocNum("");
		payMtdDtl.setBankAccHolderDocNum(null);
		payMtdDtl.setBankCode(null);
		payMtdDtl.setBranchCode(null);
		payMtdDtl.setBankAccNum(null);
		payMtdDtl.setAutoPayUpperLimit(null);
	}

	private void clearCreditCardField(PaymentMethodDtl payMtdDtl){
		payMtdDtl.setThirdPartyCard(null);
		payMtdDtl.setCardHolderName(null);
		payMtdDtl.setCardHolderDocNum(null);
		payMtdDtl.setCardHolderDocType("");
		payMtdDtl.setCardNum(null);
		payMtdDtl.setCardType("");
		payMtdDtl.setCardVerified(false);
		payMtdDtl.setExpireMonth(0);
		payMtdDtl.setExpireYear(0);
	}
	
	protected Map<String, List> referenceData(HttpServletRequest request) throws Exception {
		Map<String, List> referenceData = new HashMap<String, List>();
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		List<LookupItemDTO> autopayIssueBankList  = ltsPaymentService.getBankCode();
		Comparator<LookupItemDTO> c = new Comparator<LookupItemDTO>(){
			public int compare(LookupItemDTO o1, LookupItemDTO o2) {
				return Integer.parseInt(o1.getItemKey()) - Integer.parseInt(o2.getItemKey());
			}
		};
		
		Collections.sort(autopayIssueBankList, c);
		referenceData.put("autopayIssueBankList", autopayIssueBankList);
		
		String bankCode = (String) request.getAttribute("bankCode");
		if(!StringUtils.isEmpty(bankCode)){
			List<LookupItemDTO> branchList = ltsPaymentService.getBranchCode(bankCode);
			
			Collections.sort(branchList, c);
			referenceData.put("branchList", branchList);
		}
		if(orderCaptureDTO.isChannelDirectSales()){
			referenceData.put("suspendReasonList", Arrays.asList(ltsDsSuspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		}else{
			referenceData.put("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		}
		referenceData.put("creditCardTypeList", Arrays.asList(creditCardTypeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		
		referenceData.put("yearList", ltsPaymentService.getCcExpireYearList());
		
		return referenceData;
	}

	
	private void setupExistPayMthd(AcqOrderCaptureDTO orderCaptureDTO, LtsAcqPaymentMethodFormDTO ltsPaymentFormDTO){
			List<PaymentMethodDtl> payMtdDtlList = ltsPaymentFormDTO.getPaymentMethodDtlList();
			
			for(PaymentMethodDtl payMtdDtl: payMtdDtlList){
				AccountDetailProfileLtsDTO acctDtl = payMtdDtl.getAcctProfile();
//				if(StringUtils.equals(acctDtl.getAcctNum(), payMtdDtl.getAcctNum())){
//					payMtdDtl.setAcctNum(acctDtl.getAcctNum());
					if (orderCaptureDTO.getLtsAcqAccountSelectionForm() != null && orderCaptureDTO.getLtsAcqAccountSelectionForm().isNewAccount()){
						payMtdDtl.setExistingPayMethodType("M");
						payMtdDtl.setExistingPayMethodDisplay("Cash");
					}else{
					    payMtdDtl.setExistingPayMethodType(acctDtl.getPayMethod());
					    if(StringUtils.equals(acctDtl.getPayMethod(), LtsConstant.PAYMENT_TYPE_CASH)){
						    payMtdDtl.setExistingPayMethodDisplay("Cash");
					    }else if(StringUtils.equals(acctDtl.getPayMethod(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)){
						    payMtdDtl.setExistingPayMethodDisplay("Auto Pay");
					    }else if(StringUtils.equals(acctDtl.getPayMethod(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)){
						    payMtdDtl.setExistingPayMethodDisplay("Credit Card");
						    payMtdDtl.setCardNum(payMtdDtl.getAcctProfile().getCreditCardNum());
					    }
					}
//				}
			}
			
		}
		
	

	private int setupTenure(AcqOrderCaptureDTO orderCaptureDTO, LtsAcqPaymentMethodFormDTO form) {
		CustomerDetailProfileLtsDTO custDetailDTO = orderCaptureDTO.getCustomerDetailProfileLtsDTO();
		LtsAddressRolloutFormDTO addressDTO = orderCaptureDTO.getLtsAddressRolloutForm();
		//LTS
		int ltsTenure= this.customerProfileLtsService.getMaxLtsTenure(custDetailDTO.getParentCustNum(), addressDTO.getFlat(), addressDTO.getFloor(), addressDTO.getServiceBoundary());
		
		//IMS
//		TenureDTO[] imsTenures = this.imsProfileService.getImsTenureByAddress(addressDTO.getFlat(), addressDTO.getFloor(), addressDTO.getServiceBoundary());
//		
//		if (imsTenures == null && StringUtils.isNotEmpty(addressDTO.getFloor()) && StringUtils.isNotEmpty(addressDTO.getFlat())) {
//			imsTenures = this.imsProfileService.getImsTenureByAddress(addressDTO.getFloor() + addressDTO.getFlat(), "", addressDTO.getServiceBoundary());
//		}
//		
		
		List<String[]> addrPatternList = LtsSbOrderHelper.getAddrCombinationPattern(addressDTO.getFlat(), addressDTO.getFloor());

		// Get IMS Tenure
		TenureDTO[] imsTenures = null;
		if (addrPatternList != null && !addrPatternList.isEmpty()) {
			for (String[] addrParrtern : addrPatternList) {
				imsTenures = this.imsProfileService.getImsTenureByAddress(addrParrtern[0], addrParrtern[1], addressDTO.getServiceBoundary());
				if (ArrayUtils.isNotEmpty(imsTenures)) {
					break;
				}
			}
		}
		
		int maxTenure = 0;
		
		if (!ArrayUtils.isEmpty(imsTenures)) {
			for (TenureDTO tenure : imsTenures) {
				//check if imsTenure is under same cust
				String[] custDocDtl = this.imsProfileService.getImsCustDocByParentCust(tenure.getCustNum());
				if(StringUtils.equals(custDocDtl[0], custDetailDTO.getDocType())
						&& StringUtils.equals(custDocDtl[1], custDetailDTO.getDocNum())
						&& tenure.getTenure() > maxTenure){
					maxTenure = tenure.getTenure();
				}
			}
		}
		
//		double ltsTenure = orderCaptureDTO.getLtsServiceProfile().getLtsTenure();
//		double imsTenure = orderCaptureDTO.getLtsServiceProfile().getImsTenure();
		
		form.setLtsTenure(ltsTenure);
		form.setImsTenure(maxTenure);
			
		return ltsTenure > maxTenure ? ltsTenure : maxTenure;
	}
	
	

	private void setupCustomerInfo(AcqOrderCaptureDTO orderCaptureDTO, LtsAcqPaymentMethodFormDTO ltsPaymentFormDTO){
		CustomerDetailProfileLtsDTO custDetailDTO = orderCaptureDTO.getCustomerDetailProfileLtsDTO();
		LtsAcqPersonalInfoFormDTO custInfoForm = orderCaptureDTO.getLtsAcqPersonalInfoForm();
		
		String custFirstName = custDetailDTO == null? custInfoForm.getGivenName() : custDetailDTO.getFirstName();
		String custLastName = custDetailDTO == null? custInfoForm.getFamilyName() : custDetailDTO.getLastName();
		String custDocType = custDetailDTO == null? custInfoForm.getDocumentType() : custDetailDTO.getDocType();
		String custDocNum = custDetailDTO == null? custInfoForm.getDocNum() : custDetailDTO.getDocNum();
		
		String custName = custLastName + " " + custFirstName;
		ltsPaymentFormDTO.setCustName(custName.length() > 30? custName.substring(0, 30): custName);
		ltsPaymentFormDTO.setCustDocType(custDocType);
		ltsPaymentFormDTO.setCustDocNum(custDocNum);
	}


	private void setupNewPaymentDtl(AcqOrderCaptureDTO orderCaptureDTO, LtsAcqPaymentMethodFormDTO form){
		String tenureCode = ltsPaymentService.getTenureCode(setupTenure(orderCaptureDTO, form));
		form.setTenureCode(tenureCode);
		
		
		for(PaymentMethodDtl payMtdDtl : form.getPaymentMethodDtlList()){
			List<String> newPayMethods = ltsPaymentService.getNewPayMethod(tenureCode, payMtdDtl.getExistingPayMethodType());
			payMtdDtl.setAllowCash(newPayMethods.contains(LtsConstant.PAYMENT_TYPE_CASH));
			payMtdDtl.setAllowAutoPay(newPayMethods.contains(LtsConstant.PAYMENT_TYPE_AUTO_PAY));
			payMtdDtl.setAllowCreditCard(newPayMethods.contains(LtsConstant.PAYMENT_TYPE_CREDIT_CARD));
			payMtdDtl.setAllowKeepExistPayMtd(newPayMethods.contains(payMtdDtl.getExistingPayMethodType()));
			
			/*not allow cash if mass del premium basket selected*/
			
			/*
			final String[] NO_CASH_BASKET_CATGS = {LtsConstant.BASKET_CATEGORY_PREMIUM, LtsConstant.BASKET_CATEGORY_REBATE_PREMIUM, LtsConstant.BASKET_CATEGORY_REBATE_COUPON};

			if(StringUtils.equals(orderCaptureDTO.getSelectedBasket().getBasketChannelId(), LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_MASS_DEL)
					&& ArrayUtils.contains(NO_CASH_BASKET_CATGS, orderCaptureDTO.getSelectedBasket().getBasketCatg())){
				payMtdDtl.setAllowCash(false);
			}
			*/
			
			if (StringUtils.equals(payMtdDtl.getExistingPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)){
			    payMtdDtl.setNewPayMethodType(payMtdDtl.getExistingPayMethodType()); // set default select existing PayMethod Type 
			}
			
			if(StringUtils.isBlank(payMtdDtl.getApplicationDate())){
				payMtdDtl.setApplicationDate(LtsAppointmentHelper.getToday());
			}
			if(containIdd(orderCaptureDTO)){
				payMtdDtl.setAllowCash(false);
				payMtdDtl.setAllowAutoPay(false);
			}
			if(StringUtils.isNotBlank(orderCaptureDTO.getSelectedBasket().getPayMtdTypes())){
				if(StringUtils.containsNone(orderCaptureDTO.getSelectedBasket().getPayMtdTypes(), LtsConstant.PAYMENT_TYPE_CASH)){
					payMtdDtl.setAllowCash(false);
				}
				if(StringUtils.containsNone(orderCaptureDTO.getSelectedBasket().getPayMtdTypes(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)){
					payMtdDtl.setAllowAutoPay(false);
				}
				if(StringUtils.containsNone(orderCaptureDTO.getSelectedBasket().getPayMtdTypes(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)){
					payMtdDtl.setAllowCreditCard(false);
				}
			}
			if(orderCaptureDTO.isChannelDirectSales()){
				payMtdDtl.setAllowAwaitPayment(true);
			}else{
				payMtdDtl.setAllowAwaitPayment(false);
			}
			if(payMtdDtl.isAllowCreditCard() && payMtdDtl.getExpireMonth()==0 && payMtdDtl.getExpireYear()==0){
				// set default starting from 4 month
		       Calendar today = new GregorianCalendar();
		       today.add(Calendar.MONTH, 4);
			   int year = today.get(Calendar.YEAR);
			   int month = today.get(Calendar.MONTH);
			   month = month+1; // month 0-11
			   payMtdDtl.setExpireMonth(month);
			   payMtdDtl.setExpireYear(year);
			}
		}
	}
	

	
	private void setupPrepaymentByLkup(AcqOrderCaptureDTO order, LtsAcqPaymentMethodFormDTO form, Locale locale){
		PrepaymentLkupCriteriaDTO prepaymentLkupCriteriaDTO = new PrepaymentLkupCriteriaDTO();
		prepaymentLkupCriteriaDTO.setOrderType(LtsConstant.ORDER_TYPE_SB_ACQUISITION);
		prepaymentLkupCriteriaDTO.setDocType(order.getLtsAcqPersonalInfoForm().getDocumentType());
		prepaymentLkupCriteriaDTO.setTenure(setupTenure(order, form));
		prepaymentLkupCriteriaDTO.setAppDate(DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")));
		prepaymentLkupCriteriaDTO.setChannel(order.isChannelPremier()? "P" : "M");

		//Use HKT Premier ind as Housing Type
		String housingTypePT = order.getAddressRollout().getHktPremier();
		if(StringUtils.isNotBlank(housingTypePT)){
			prepaymentLkupCriteriaDTO.setHouseType(housingTypePT);
		}else{
			prepaymentLkupCriteriaDTO.setHouseType(StringUtils.defaultIfEmpty(order.getAddressRollout().getHousingType(), LtsConstant.HOUSE_TYPE_PUBLIC_HSE));
		}
		
		int addrEyeCount = order.getAddressRollout().getNumOfEyeProfile();
		int custEyeCount = eyeProfileCountService.getEyeProfileCountByCust(
				order.getLtsAcqPersonalInfoForm().getDocumentType(),
				order.getLtsAcqPersonalInfoForm().getDocNum());
		prepaymentLkupCriteriaDTO.setEyeProfileCount(addrEyeCount > custEyeCount? addrEyeCount : custEyeCount);
		
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
			if(order.getLtsAcqBasketServiceForm() != null){
				if(order.getLtsAcqBasketServiceForm().getPlanItemList() != null){
					for(ItemDetailDTO itemDtl: order.getLtsAcqBasketServiceForm().getPlanItemList()){
						psefCodeList.addAll(Arrays.asList(ltsOfferService.getItemPsefCodes(itemDtl.getItemId())));
					}
				}
				if(order.getLtsAcqBasketServiceForm().getContItemSetList() != null){
					for (ItemSetDetailDTO itemSetDetail : order.getLtsAcqBasketServiceForm().getContItemSetList()) {
						for(ItemDetailDTO itemDtl: itemSetDetail.getItemDetails()){
							psefCodeList.addAll(Arrays.asList(ltsOfferService.getItemPsefCodes(itemDtl.getItemId())));
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
			prepaymentLkupCriteriaDTO.setPaymentMethod(paymentMethod);
			List<PrepaymentLkupResultDTO> resultList = ltsPaymentService.getPrepaymentItemIdByLkup(prepaymentLkupCriteriaDTO);
			for(PrepaymentLkupResultDTO result: resultList){
				if(CollectionUtils.containsAny(result.getPsefCdSet(), psefCodeList)
						&& StringUtils.isNotBlank(result.getPrepayItemId())){
					List<ItemDetailDTO> itemList = ltsOfferService.getItem(new String[]{result.getPrepayItemId()}, 
																			LtsConstant.DISPLAY_TYPE_ITEM_SELECT, 
																			locale.toString(), 
																			true);
					if(itemList != null && itemList.size() > 0){
						ItemDetailDTO prePayItem = itemList.get(0);
						
						for(PaymentMethodDtl payMtdDtl : form.getPaymentMethodDtlList()){
							if(payMtdDtl.getAcctProfile().getAcctChrgType() != null && payMtdDtl.getAcctProfile().getAcctChrgType().contains("R")){
								if(LtsConstant.PAYMENT_TYPE_CASH.equals(paymentMethod)){
									payMtdDtl.setCashPrePayItem(prePayItem);
								}else if(LtsConstant.PAYMENT_TYPE_AUTO_PAY.equals(paymentMethod)){
									payMtdDtl.setAutopayPrePayItem(prePayItem);
								}else if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(paymentMethod)){
									payMtdDtl.setCreditCardPrePayItem(prePayItem);
								}
							}
						}
						
					}				
					
					break;
				}
			}
		}

		form.setSalesMemoNumRequired(order.isChannelRetail());
	}
	
	private void setupPrepayment(AcqOrderCaptureDTO order, LtsAcqPaymentMethodFormDTO form, Locale locale){
		String selectedBasketId = order.getLtsAcqBasketSelectionForm().getSelectedBasketId();
		
		List<ItemDetailDTO> basketPrepayItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_PREPAY, locale.toString(), true, order.getBasketChannelId(), null);
		
		String housingType = StringUtils.defaultIfEmpty(order.getAddressRollout().getHousingType(), LtsConstant.HOUSE_TYPE_PUBLIC_HSE);

		String tenureCode = form.getTenureCode();
		
		// set tenureCode = "ACQ" if BYPASS = Y
		if("Y".equalsIgnoreCase((String)ltsAcqPrepayTenureLkupCacheService.get(LtsConstant.CODE_LKUP_ACQ_PREPAY_TENURE_BYPASS)))
		{
			tenureCode = "ACQ";
		}
		//
		
		/*List<String[]> prepayItemIdList = {ITEM ID, PAYMENT TYPE}*/
		List<String[]> prepayItemIdList = ltsPaymentService.getPrePayItem(tenureCode, housingType, order.getSelectedBasket().getType());
		
		for(PaymentMethodDtl payMtdDtl : form.getPaymentMethodDtlList()){
			/*Setup Basket Prepay Item*/
			if(payMtdDtl.getAcctProfile().getAcctChrgType() != null && payMtdDtl.getAcctProfile().getAcctChrgType().contains("R")){
				if(basketPrepayItemList != null && basketPrepayItemList.size() > 0
						&& prepayItemIdList != null && prepayItemIdList.size() > 0){
					for(ItemDetailDTO basketPrepayItemDtl : basketPrepayItemList){
						for(String[] prepayItemId : prepayItemIdList){
							if(basketPrepayItemDtl.getItemId().equals(prepayItemId[0])){
								if(StringUtils.equals(prepayItemId[1], LtsConstant.PAYMENT_TYPE_CASH)){
									payMtdDtl.setCashPrePayItem(basketPrepayItemDtl);
									break;
								}else if(StringUtils.equals(prepayItemId[1], LtsConstant.PAYMENT_TYPE_AUTO_PAY)){
									payMtdDtl.setAutopayPrePayItem(basketPrepayItemDtl);
									break;
								}else if(StringUtils.equals(prepayItemId[1], LtsConstant.PAYMENT_TYPE_CREDIT_CARD)){
									payMtdDtl.setCreditCardPrePayItem(basketPrepayItemDtl);
									break;
								}
							}
						}
					}
				}
				
			}
			
			
			//TODO 
			/* SETUP IDD FFP Prepay Item*/

			/*
			if(payMtdDtl.getAcctProfile().getAcctChrgType() != null && payMtdDtl.getAcctProfile().getAcctChrgType().contains("I")){
				ItemDetailDTO iddDepositItem = getIddDeposit(order, form);
				if(iddDepositItem != null){
					payMtdDtl.setIddDepositItem(iddDepositItem);
				}
			}*/
		}
		
		form.setSalesMemoNumRequired(order.isChannelRetail());
	}
	
/*	private ItemDetailDTO getIddDeposit(AcqOrderCaptureDTO order, LtsAcqPaymentMethodFormDTO form){
		String docType = order.getCustomerDetailProfileLtsDTO() != null? order.getCustomerDetailProfileLtsDTO().getDocType() : order.getLtsAcqPersonalInfoForm().getDocumentType();
		String docNum  = order.getCustomerDetailProfileLtsDTO() != null? order.getCustomerDetailProfileLtsDTO().getDocNum() : order.getLtsAcqPersonalInfoForm().getDocNum();
		
		//TODO
		if(LtsConstant.DOC_TYPE_PASSPORT.equals(docType)
				|| (LtsConstant.DOC_TYPE_HKID.equals(docType)
					&& (StringUtils.startsWith(docNum, "W") || StringUtils.startsWith(docNum, "WX")) )
				){
			
			
		}
		
		
		
		//TODO
		return null;
	}*/
	
	private boolean containIdd(AcqOrderCaptureDTO order){

		boolean SelectedFfpVasHotList = false;
		boolean SelectedFfpVasOtherList = false;
		boolean SelectedFfpVasStaffList = false;
		boolean SelectedIddVasList = false;
		
		String docType = order.getCustomerDetailProfileLtsDTO() != null? order.getCustomerDetailProfileLtsDTO().getDocType() : order.getLtsAcqPersonalInfoForm().getDocumentType();
		String docNum  = order.getCustomerDetailProfileLtsDTO() != null? order.getCustomerDetailProfileLtsDTO().getDocNum() : order.getLtsAcqPersonalInfoForm().getDocNum();
		
		
/*		String selectedBasketId = order.getLtsAcqBasketSelectionForm().getSelectedBasketId();
		List<ItemDetailDTO> basketFfpVasHotItemList = ltsOfferService.getBasketItemList(selectedBasketId, LtsConstant.ITEM_TYPE_FFP_HOT, locale.toString(), true, order.getBasketChannelId());
		ItemDetailDTO basketFfpVasHotItem = (basketFfpVasHotItemList != null && !basketFfpVasHotItemList.isEmpty()) ? basketFfpVasHotItemList.get(0) : null;
		*/
		if (order.getLtsAcqBasketVasDetailForm() != null){
		    if (order.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList() != null && !order.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList().isEmpty()){
			    for (int i = 0; i < order.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList().size(); i++) {
				    if (order.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList().get(i).isSelected()){
					    SelectedFfpVasHotList = true;
				    }
			    }
		    }
		
		    if (order.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList() != null && !order.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList().isEmpty()){
			    for (int i = 0; i < order.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList().size(); i++) {
				    if (order.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList().get(i).isSelected()){
					    SelectedFfpVasOtherList = true;
				    }
			    }
		    }
		
		    if (order.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList() != null && !order.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList().isEmpty()){
			    for (int i = 0; i < order.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList().size(); i++) {
				    if (order.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList().get(i).isSelected()){
					   SelectedFfpVasOtherList = true;
				    }
			    }
		    }
		
		    if (order.getLtsAcqBasketVasDetailForm().getIddVasItemList() != null && !order.getLtsAcqBasketVasDetailForm().getIddVasItemList().isEmpty()){
			    for (int i = 0; i < order.getLtsAcqBasketVasDetailForm().getIddVasItemList().size(); i++) {
				    if (order.getLtsAcqBasketVasDetailForm().getIddVasItemList().get(i).isSelected()){
				    	SelectedIddVasList = true;
				    }
			    }
		    }
		}

		if((SelectedFfpVasHotList||SelectedFfpVasOtherList||SelectedFfpVasStaffList||SelectedIddVasList)||
				(order.isContainFfpVAS()||order.isContainIddVAS())){	    	   
			if(LtsConstant.DOC_TYPE_PASSPORT.equals(docType)
					|| (LtsConstant.DOC_TYPE_HKID.equals(docType)
						&& (StringUtils.startsWith(docNum, "W") || StringUtils.startsWith(docNum, "WX")) )
					){				
				return true;
			}			
	    }
		
		return false;
	}
	
	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}
	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
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

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}
	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}
	public void setCustomerProfileLtsService(CustomerProfileLtsService customerProfileLtsService) {
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


	public CodeLkupCacheService getLtsDsSuspendReasonLkupCacheService() {
		return ltsDsSuspendReasonLkupCacheService;
	}


	public void setLtsDsSuspendReasonLkupCacheService(
			CodeLkupCacheService ltsDsSuspendReasonLkupCacheService) {
		this.ltsDsSuspendReasonLkupCacheService = ltsDsSuspendReasonLkupCacheService;
	}

	public CodeLkupCacheService getLtsAcqPrepayTenureLkupCacheService() {
		return ltsAcqPrepayTenureLkupCacheService;
	}

	public void setLtsAcqPrepayTenureLkupCacheService(
			CodeLkupCacheService ltsAcqPrepayTenureLkupCacheService) {
		this.ltsAcqPrepayTenureLkupCacheService = ltsAcqPrepayTenureLkupCacheService;
	}	
	
    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}


	public EyeProfileCountService getEyeProfileCountService() {
		return eyeProfileCountService;
	}


	public void setEyeProfileCountService(
			EyeProfileCountService eyeProfileCountService) {
		this.eyeProfileCountService = eyeProfileCountService;
	}
	
}

