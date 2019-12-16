package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.DocWaiveReasonDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Method;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Selection;
import com.bomwebportal.lts.dto.form.acq.LtsAcqOtherVoiceServiceFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPersonalInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSupportDocFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSupportDocFormDTO.FormAction;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.LtsConstant.LtsOrderFlag;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;

public class LtsAcqSupportDocController extends SimpleFormController {

	
	private final String viewName = "/lts/acq/ltsacqsupportdoc";
	private final String commandName = "ltsAcqSupportDocCmd";
	
	private LtsOrderDocumentService ltsOrderDocumentService;
	private CodeLkupCacheService suspendReasonLkupCacheService;
	private CodeLkupCacheService ltsDsSuspendReasonLkupCacheService;
	private LtsCommonService ltsCommonService;

	private Locale locale;
	private MessageSource messageSource;
	
	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}
	
	public CodeLkupCacheService getSuspendReasonLkupCacheService() {
		return suspendReasonLkupCacheService;
	}

	public void setSuspendReasonLkupCacheService(
			CodeLkupCacheService suspendReasonLkupCacheService) {
		this.suspendReasonLkupCacheService = suspendReasonLkupCacheService;
	}
	
	


	public CodeLkupCacheService getLtsDsSuspendReasonLkupCacheService() {
		return ltsDsSuspendReasonLkupCacheService;
	}

	public void setLtsDsSuspendReasonLkupCacheService(
			CodeLkupCacheService ltsDsSuspendReasonLkupCacheService) {
		this.ltsDsSuspendReasonLkupCacheService = ltsDsSuspendReasonLkupCacheService;
	}

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public LtsAcqSupportDocController() {
		setCommandClass(LtsAcqSupportDocFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		if (acqOrderCapture == null) {
			LtsSessionHelper.setAcqOrderCapture(request, new AcqOrderCaptureDTO());
    		acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
    	}
		if (acqOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqSupportDocFormDTO form = acqOrderCapture.getLtsAcqSupportDocForm();
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		
		String doctype = acqOrderCapture.getLtsAcqPersonalInfoForm().getDocumentType();
		
		boolean isBrCust = LtsConstant.DOC_TYPE_HKBR.equals(doctype);
		
		if (form == null) {
			form = new LtsAcqSupportDocFormDTO();
		}
		if (form.getCollectMethod() == null) {
			form.setCollectMethod(LtsConstant.COLLECT_METHOD_DIGITAL);	
		}
		if(form.getSignoffMode() == null){
			if(acqOrderCapture.isChannelRetail()
					|| acqOrderCapture.isChannelDirectSales()){
				form.setSignoffMode(LtsConstant.SIGNOFF_MODE_RETAIL);
			}
			if(acqOrderCapture.isChannelCs()
					|| acqOrderCapture.isChannelPremier()){
				form.setSignoffMode(LtsConstant.SIGNOFF_MODE_CALLCENTER);
			}
		}
		if (form.getDistributionMode() == null) {
			if(isBrCust){
				form.setDistributionMode(LtsConstant.DISTRIBUTE_MODE_PAPER);
			}else{
				form.setDistributionMode(LtsConstant.DISTRIBUTE_MODE_ESIGNATURE);
				if(bomSalesUser.getChannelId() != Integer.parseInt(LtsConstant.CHANNEL_ID_RETAIL)){
					form.setSendSms(true);
				}
			}
		}
		
		form.setGeneratedFormList(getGeneratedFormList(acqOrderCapture));
		form.setSupportDocumentList(getSupportDocList(acqOrderCapture, form, bomSalesUser.getUsername(), doctype));
		form.setChannelRetail(acqOrderCapture.isChannelRetail());
		form.setChannelDS(acqOrderCapture.isChannelDirectSales());
		request.setAttribute("isBrCust", isBrCust);
		if(StringUtils.isNotBlank(acqOrderCapture.getSuspendRemark())){
	    	form.setSuspendRemarks(acqOrderCapture.getSuspendRemark());
	    }else{
		    form.setSuspendRemarks(null);
	    }
		
		if("N".equals(ltsCommonService.getLtsOrderFlag(LtsOrderFlag.CHECK_AGE_DISTRIBUTE_PAPER))){
			form.setAllowDistributePaper(true);
		}else{
			String dateOfBirth = acqOrderCapture.getLtsAcqPersonalInfoForm().getDateOfBirth();
			if (StringUtils.isNotEmpty(dateOfBirth)) {
				dateOfBirth = LtsDateFormatHelper.getDateFromDTOFormat(dateOfBirth);
				if(LtsSbOrderHelper.isAgeOver(dateOfBirth)){
					form.setAllowDistributePaper(true);
				}else{
					form.setAllowDistributePaper(false);
				}
			}
		}
		
		return form;
	}
	
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqSupportDocFormDTO form = (LtsAcqSupportDocFormDTO) command;
		String doctype = acqOrderCapture.getLtsAcqPersonalInfoForm().getDocumentType();
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_SUSPEND);
	    if(StringUtils.isNotBlank(form.getSuspendRemarks())){
	    	acqOrderCapture.setSuspendRemark(form.getSuspendRemarks());
		}else{
			acqOrderCapture.setSuspendRemark(null);
		}
	    
	    //to prevent null DN
	    if (acqOrderCapture.getLtsAcqNumSelectionAndPipbForm() == null)
	    {
	    	String numSelView = "ltsacqnumselectionandpipb.html";
	    	ModelAndView mav = new ModelAndView(new RedirectView(numSelView));
			List<String> messageList = new ArrayList<String>();
			messageList.add(messageSource.getMessage("lts.acq.supportDoc.noDNInputOrSelect", new Object[] {}, this.locale));
			mav.addObject("errorMsgList", messageList);
	    	return mav;
	    }
	    
	    if (acqOrderCapture.getLtsAcqNumSelectionAndPipbForm() != null)
	    {
	    	if ((acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getReservedDnList()==null || acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getReservedDnList().size()==0) 
    				&& (acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getSearchMethod()==Method.DN_POOL || acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getSearchMethod()==Method.DN_POOL_THEN_PIPB)) {
	    		String numSelView = "ltsacqnumselectionandpipb.html";
	    		ModelAndView mav = new ModelAndView(new RedirectView(numSelView));
				List<String> messageList = new ArrayList<String>();
				messageList.add(messageSource.getMessage("lts.acq.supportDoc.pleaseSelectDN", new Object[] {}, this.locale));
				mav.addObject("errorMsgList", messageList);
	    		return mav;
	    	}
	    	
	    	if (acqOrderCapture.getLtsAcqNumConfirmationForm() == null)
		    {
		    	String numSelView = "ltsacqnumselectionandpipb.html";
		    	ModelAndView mav = new ModelAndView(new RedirectView(numSelView));
				List<String> messageList = new ArrayList<String>();
				messageList.add(messageSource.getMessage("lts.acq.supportDoc.noDNConfirmed", new Object[] {}, this.locale));
				mav.addObject("errorMsgList", messageList);
		    	return mav;
		    }
	    	else
	    	{
	    		Selection selection = acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection();
	    		if (selection==Selection.USE_NEW_DN || selection==Selection.USE_NEW_DN_AND_PIPB_DN) {
	    			if (LtsConstant.DN_SOURCE_DN_POOL.equals(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDnSource())){
	    				if (acqOrderCapture.getLtsAcqNumConfirmationForm().getNewDn()==null) {
	    			    	String numSelView = "ltsacqnumselectionandpipb.html";
	    			    	ModelAndView mav = new ModelAndView(new RedirectView(numSelView));
	    					List<String> messageList = new ArrayList<String>();
	    					messageList.add(messageSource.getMessage("lts.acq.supportDoc.plsConfirmNewDN", new Object[] {}, this.locale));
	    					mav.addObject("errorMsgList", messageList);
	    			    	return mav;
	    				}
	    			}
	    		}
	    	}
	    	
	    }	    
	    /*
	    
	    
	    private String getServiceNum(CreateServiceType createService, AcqOrderCaptureDTO acqOrderCapture) {
		
		switch (createService) {
		case CREATE_SRV_TYPE_ACQ_EYE:
		case CREATE_SRV_TYPE_ACQ_DEL:
			if(acqOrderCapture.getLtsAcqNumConfirmationForm() != null
					&& acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection()==Selection.USE_PIPB_DN){
				return acqOrderCapture.getLtsAcqNumConfirmationForm().getPipbDn();
			}
			if(acqOrderCapture.getLtsAcqNumConfirmationForm() != null){
				return acqOrderCapture.getLtsAcqNumConfirmationForm().getNewDn();
			}
			return null;
			
		case CREATE_SRV_TYPE_ACQ_PORT_LATER:
			if(acqOrderCapture.getLtsAcqNumConfirmationForm() != null){
				return acqOrderCapture.getLtsAcqNumConfirmationForm().getPipbDn();
			}
			return null;
			
		case CREATE_SRV_TYPE_ACQ_2DEL:
			if (!acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getCreate2ndDel().booleanValue()) {
				return null;
			}
			return StringUtils.leftPad(acqOrderCapture.getLtsAcqOtherVoiceServiceForm().getNew2ndDelDn(), 12, "0");

		default:
			return null;
		}
	}
	*/
	    
	    //	    
	    
	    if (FormAction.CHANGE_DIS_MODE == form.getFormAction()) {
	    	form.setSupportDocumentList(getSupportDocList(acqOrderCapture, form, bomSalesUser.getUsername(), doctype));
	    	ModelAndView mav = new ModelAndView(viewName);
			mav.addObject(commandName, form);
			acqOrderCapture.setLtsAcqSupportDocForm(form);
			try {
				if(acqOrderCapture.isChannelDirectSales()){
					mav.addObject("suspendReasonList", Arrays.asList(ltsDsSuspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
				}else{
					mav.addObject("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
				}
			}
			catch (DAOException e) {
				throw new AppRuntimeException(e);
			}
			return mav;
	    }
	    else if (FormAction.SUSPEND == form.getFormAction()) {
	    	acqOrderCapture.setLtsAcqSupportDocForm(null);	
	    	paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, form.getSuspendReason());	
			paramMap.put(LtsConstant.REQUEST_PARAM_FROM_VIEW, "ltsacqsupportdoc");
	    }
	    else {
	    	
			validate(acqOrderCapture, form, errors);
			
			if (errors.hasFieldErrors()) {
				ModelAndView mav = new ModelAndView(viewName);
				mav.addAllObjects(errors.getModel());
				mav.addObject(commandName, form);
				return mav;
			}
			
	    	acqOrderCapture.setLtsAcqSupportDocForm(form);	
	    }
		return new ModelAndView(new RedirectView(LtsConstant.NEW_ACQ_ORDER_VIEW), paramMap);
	}
	
	private void validate(AcqOrderCaptureDTO acqOrderCapture, LtsAcqSupportDocFormDTO form, BindException errors) {
		
		if (acqOrderCapture.isChannelCs() || acqOrderCapture.isChannelPremier()) {
			if(LtsConstant.DISTRIBUTE_MODE_ESIGNATURE.equals(form.getDistributionMode())) {
				if (!(form.isSendEmail() || form.isSendSms())){
					errors.rejectValue("distributeEmail", "lts.distributeOption.required");
				}
			}
		}
		
	}
	
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		if(acqOrderCapture.isChannelDirectSales()){
			referenceData.put("suspendReasonList", Arrays.asList(ltsDsSuspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		}else{
			referenceData.put("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		}
		return referenceData;
	}
	
	private List<OrderDocDTO> getGeneratedFormList(AcqOrderCaptureDTO acqOrderCapture) {
		List<OrderDocDTO> generatedFormList = new ArrayList<OrderDocDTO>();
		
		String coreServiceAfDocType = null;
		
		if (acqOrderCapture.isEyeOrder()){
			coreServiceAfDocType = LtsConstant.ORDER_DOC_TYPE_EYEX_AF;
		}else{
			coreServiceAfDocType = LtsConstant.ORDER_DOC_TYPE_DEL_AF;
		}
		
		OrderDocDTO coreServiceAF = ltsOrderDocumentService.getOrderDoc(coreServiceAfDocType);
		if (coreServiceAF != null) {
			coreServiceAF.setSelected(true);
			generatedFormList.add(coreServiceAF);
		}
		
		LtsAcqOtherVoiceServiceFormDTO secDelForm = acqOrderCapture.getLtsAcqOtherVoiceServiceForm();
		if (secDelForm.getCreate2ndDel() != null && secDelForm.getCreate2ndDel()) {
			OrderDocDTO secDelAF = ltsOrderDocumentService.getOrderDoc(LtsConstant.ORDER_DOC_TYPE_2ND_DEL_AF);
			if (secDelAF != null) {
				secDelAF.setSelected(true);
//				secDelAF.setMdoInd(LtsConstant.ITEM_MDO_MANDATORY);
				generatedFormList.add(secDelAF);
			}
		}
		
		
		return generatedFormList;
	}
	
	private List<OrderDocDTO> getSupportDocList(AcqOrderCaptureDTO acqOrderCapture, LtsAcqSupportDocFormDTO form, String userId, String doctype) {	
		List<OrderDocDTO> supportDocList = new ArrayList<OrderDocDTO>();
		
		if (acqOrderCapture.isChannelDirectSales()){
			if(isCustSupportDocRequired(acqOrderCapture)){
				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_CUSTOMER_SUPPORT_DOC, userId, form.getDistributionMode());
			}
			else{
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_CUSTOMER_SUPPORT_DOC);
			}
		}
		// For Call Centre Mode
		if (acqOrderCapture.isChannelCs() || (acqOrderCapture.isChannelPremier() && LtsConstant.SIGNOFF_MODE_CALLCENTER.equals(form.getSignoffMode()))) {
			
			// Prepayment Sheet
			if (isPrepaymentSheetRequired(acqOrderCapture.getLtsAcqPaymentMethodFormDTO()) && !LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(acqOrderCapture.getLtsAcqPaymentMethodFormDTO().getPrimaryPaymentMethodDtl().getNewPayMethodType())) {
				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_PREPAYMENT_SHEET, userId, form.getDistributionMode());
			}
			else {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_PREPAYMENT_SHEET);
			}
			
			if (isAutoPayFormRequired(acqOrderCapture.getLtsAcqPaymentMethodFormDTO())) {
				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTOPAY_FORM, userId, form.getDistributionMode());
			}
			else {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTOPAY_FORM);
			}
			
			// NSD
			if (isNsdRequired(acqOrderCapture)) {
				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_NSD, userId, form.getDistributionMode());
			}else {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_NSD);
			}
			
			// Recontract Support Document 
			if (isRecontractSupportDocRequired(acqOrderCapture)) {
				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_SUP_DOC, userId, form.getDistributionMode());
			}else {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_SUP_DOC);
			}
						
			// Recontract AF
//			if (acqOrderCapture.getLtsAcqMiscellaneousForm().isRecontract() && acqOrderCapture.getLtsAcqRecontractForm() != null) {
//			if (OrderCapture.getLtsMiscellaneousForm().isRecontract() && OrderCapture.getLtsRecontractForm() != null) {
//				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED, userId, form.getDistributionMode());
//			}
//			else {
//				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED);
//			}
			
			// Third-Party Authorization Letter
			if (isThirdPartyApplication(acqOrderCapture)) {
				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTHORIZATION_LETTER, userId, form.getDistributionMode());
			}
			else {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTHORIZATION_LETTER);
			}
			
			// Credit Card Copy  3rd Party HKID Authorization Form
			if (isCreditCardCopyRequired(acqOrderCapture.getLtsAcqPaymentMethodFormDTO())) {
				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_CREDIT_CARD_COPY, userId, form.getDistributionMode());
			}
			else {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_CREDIT_CARD_COPY);
			}
			
		}		
		
		//Passport supporting document for all channels
		if (LtsConstant.DOC_TYPE_PASSPORT.equals(doctype)) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_PASS_SUP_DOC, userId, form.getDistributionMode());
		}
		else {
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_PASS_SUP_DOC);
		}
		
//		// Credit Card Copy  3rd Party HKID Authorization Form
//		if (!acqOrderCapture.isChannelDirectSales()) {
//			if (isCreditCardCopyRequired(acqOrderCapture.getLtsAcqPaymentMethodFormDTO())) {
//				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_CREDIT_CARD_COPY, userId, form.getDistributionMode());
//			}
//			else {
//				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_CREDIT_CARD_COPY);
//			}
//		}
		
		// Sales Memo Copy
		if (isSalesMemoCopyRequired(acqOrderCapture.getLtsAcqPaymentMethodFormDTO())) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_SALES_MEMO_COPY, userId, form.getDistributionMode());
		}
		else {
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_SALES_MEMO_COPY);
		}
		
//		// Third-Party Authorization Letter
//		if (!acqOrderCapture.isChannelDirectSales()) {
//			if (isThirdPartyApplication(acqOrderCapture)) {
//				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTHORIZATION_LETTER, userId, form.getDistributionMode());
//			}
//			else {
//				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTHORIZATION_LETTER);
//			}
//		}
		
		if (StringUtils.equals(acqOrderCapture.getSelectedBasket().getStaffPlan(), "Y")) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_STAFF_CARD, userId, form.getDistributionMode());
		}else {
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_STAFF_CARD);
		}
		
		if (containIddDeposit(acqOrderCapture, doctype) && !LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(getIddPaymentMethodType(acqOrderCapture))) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IDD_DEPOSIT, userId, form.getDistributionMode());
		}
		else {
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IDD_DEPOSIT);
		}
		
		if (LtsConstant.DOC_TYPE_HKBR.equals(doctype)) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_BR_SUP_DOC, userId, form.getDistributionMode());
		}
		else {
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_BR_SUP_DOC);
		}

		
		// Paper Signature Only
		
			assignDocForPaperMode(acqOrderCapture, form, supportDocList, userId, doctype);
		
			setSubcItemOrderDoc(acqOrderCapture, form, userId, supportDocList);
		// TODO: premium item proof
		
		return supportDocList;
	}
	
	private boolean isCustSupportDocRequired(AcqOrderCaptureDTO acqOrderCapture){

		if(acqOrderCapture.getLtsAcqPersonalInfoForm() != null && acqOrderCapture.getLtsAcqPersonalInfoForm().isMatchWithBomName()){
			return false;
		}
		return true;
		
	}
	
	private String getIddPaymentMethodType(AcqOrderCaptureDTO acqOrderCapture){
		
		for(PaymentMethodDtl dtl : acqOrderCapture.getLtsAcqPaymentMethodFormDTO().getPaymentMethodDtlList()){
			for(AccountDetailProfileLtsDTO acct : acqOrderCapture.getAccountDetailProfileLtsDTO()){
				if(StringUtils.containsIgnoreCase(acct.getAcctChrgType(), "I")){ 
					if(StringUtils.equals(dtl.getAcctNum(), acct.getAcctNum())){
						return dtl.getNewPayMethodType();
					}
				}
			}
		}
		return null;
	}
	
	private boolean isAutoPayFormRequired(LtsAcqPaymentMethodFormDTO paymentForm) {
		for(PaymentMethodDtl dtl : paymentForm.getPaymentMethodDtlList()){		
			if(dtl.getThirdPartyBankAccount()!=null){	
			    if(dtl.getThirdPartyBankAccount()){
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean isCrfRequired(AcqOrderCaptureDTO acqOrderCapture) {
		if(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection()!=null){
			if(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection().equals(Selection.USE_NEW_DN_AND_PIPB_DN)
					||acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection().equals(Selection.USE_PIPB_DN)){
				return true;
			}
		}
		return false;
	}
	
	private boolean isNsdRequired(AcqOrderCaptureDTO acqOrderCapture) {
		if(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection()!=null){
			if(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection().equals(Selection.USE_NEW_DN_AND_PIPB_DN)
					||acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getNumSelection().equals(Selection.USE_PIPB_DN)){
				return true;
			}
		}
		return false;
	}

	private boolean isRecontractSupportDocRequired(AcqOrderCaptureDTO acqOrderCapture) {
		if(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().isPortFromDiffCust()){
			return true;
		}
		return false;
	}
	
	
	private boolean isThirdPartyApplication(AcqOrderCaptureDTO acqOrderCapture) {
		
		LtsAcqPersonalInfoFormDTO custIdentifcationForm = acqOrderCapture.getLtsAcqPersonalInfoForm();
		if (custIdentifcationForm != null) {
			if (custIdentifcationForm.isThirdParty()) {
				return true;
			}
		}
		return false;
	}
	
	
	private boolean isPrepaymentSheetRequired(LtsAcqPaymentMethodFormDTO paymentForm) {
		return paymentForm.isPrepayItemSelected();
	}
	

	private boolean isCreditCardCopyRequired(LtsAcqPaymentMethodFormDTO paymentForm) {
		for(PaymentMethodDtl payMtdDtl: paymentForm.getPaymentMethodDtlList()){
			if (!StringUtils.equals(payMtdDtl.getNewPayMethodType(), payMtdDtl.getExistingPayMethodType()) && 
					StringUtils.equals(payMtdDtl.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)
					&& payMtdDtl.getThirdPartyCard() != null && payMtdDtl.getThirdPartyCard().booleanValue()) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isSalesMemoCopyRequired(LtsAcqPaymentMethodFormDTO paymentForm) {
		for(PaymentMethodDtl payMtdDtl: paymentForm.getPaymentMethodDtlList()){
			if(payMtdDtl.isPrepayItemSelected() && StringUtils.isNotBlank(payMtdDtl.getSalesMemoNum())){
				return true;
			}
		}
//		if (paymentForm.getPrePayItem() != null && StringUtils.isNotBlank(paymentForm.getSalesMemoNum())) {
//			return true;
//		}
		return false;
	}
	
	private void addOrderDoc(List<OrderDocDTO> orderDocList, String orderDocType, String userId, String distributeMode) {
		OrderDocDTO newOrderDoc = getOrderDoc(orderDocType, userId, distributeMode);
		if (newOrderDoc != null) {
			removeOrderDoc(orderDocList, orderDocType);
			orderDocList.add(newOrderDoc);	
		}
	}
	
	private void removeOrderDoc(List<OrderDocDTO> orderDocList, String orderDocType) {
		
		OrderDocDTO removeOrderDoc = null;
		for (OrderDocDTO orderDoc : orderDocList) {
			if (StringUtils.equals(orderDocType, orderDoc.getDocType())) {
				removeOrderDoc = orderDoc;
			}
		}
		
		if (removeOrderDoc != null) {
			orderDocList.remove(removeOrderDoc);	
		}
	}
	
	private void assignDocForPaperMode(AcqOrderCaptureDTO acqOrderCapture,
			LtsAcqSupportDocFormDTO form, List<OrderDocDTO> supportDocList,
			String userId, String doctype) {
		
		if (StringUtils.equals(LtsConstant.DISTRIBUTE_MODE_PAPER, form.getDistributionMode())) {
			
			String coreServiceAfDocType = null;
			if(!acqOrderCapture.isChannelDirectSales()){
				if (acqOrderCapture.isEyeOrder()){
					coreServiceAfDocType = LtsConstant.ORDER_DOC_TYPE_EYEX_AF_SIGNED;
				}else{
					coreServiceAfDocType = LtsConstant.ORDER_DOC_TYPE_DEL_AF_SIGNED;
				}
				addOrderDoc(supportDocList, coreServiceAfDocType, userId, form.getDistributionMode());
			
				LtsAcqOtherVoiceServiceFormDTO secDelForm = acqOrderCapture.getLtsAcqOtherVoiceServiceForm();
				if (secDelForm != null && secDelForm.getCreate2ndDel()) {
					addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_2ND_DEL_AF_SIGNED, userId, form.getDistributionMode());	
				}
				
				// NSD
				if (isNsdRequired(acqOrderCapture)) {
					addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_NSD, userId, form.getDistributionMode());
				}
				
				// Recontract Support Document 
				if (isRecontractSupportDocRequired(acqOrderCapture)) {
					addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_SUP_DOC, userId, form.getDistributionMode());
				}
			
			
	/*			if (acqOrderCapture.getLtsAcqMiscellaneousForm().isRecontract() && acqOrderCapture.getLtsAcqRecontractForm() != null) {
					addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED, userId, form.getDistributionMode());
				}*/		
				
	/*			if (OrderCapture.getLtsMiscellaneousForm().isRecontract() && OrderCapture.getLtsRecontractForm() != null) {
					addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED, userId, form.getDistributionMode());
				}*/
			}else{
				
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_EYEX_AF_SIGNED);
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DEL_AF_SIGNED );
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_2ND_DEL_AF_SIGNED );
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED );	
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_SUP_DOC);
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_NSD);
				
			}
		}
		else {
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_EYEX_AF_SIGNED);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DEL_AF_SIGNED );
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_2ND_DEL_AF_SIGNED );
			if (acqOrderCapture.isChannelRetail()||acqOrderCapture.isChannelDirectSales()) {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED );	
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_SUP_DOC);
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_NSD);
			}
		}

	}
	
	private boolean containIddDeposit(AcqOrderCaptureDTO acqOrderCapture, String docType){
		if(acqOrderCapture.getLtsAcqPaymentMethodFormDTO().isIddDeposit()){
			return true;
		}
		if(acqOrderCapture.isContainFfpVAS()||acqOrderCapture.isContainIddVAS()){	    	   
			if(LtsConstant.DOC_TYPE_PASSPORT.equals(docType)||
					(LtsConstant.DOC_TYPE_HKID.equals(docType)
						&& (StringUtils.startsWith(acqOrderCapture.getLtsAcqPersonalInfoForm().getDocNum(), "W") || StringUtils.startsWith(acqOrderCapture.getLtsAcqPersonalInfoForm().getDocNum(), "WX")))){				
				return true;
			}			
	    }
		
		return false;
	}
	
	private OrderDocDTO getOrderDoc(String orderDocType, String userId, String distributeMode) {
		OrderDocDTO orderDoc = ltsOrderDocumentService.getOrderDoc(orderDocType);
		
		if (orderDoc == null) {
			return null;
		}
		
		if (orderDoc.getWaiveReasonList() != null && !orderDoc.getWaiveReasonList().isEmpty()) {
			
			List<DocWaiveReasonDTO> filteredWaiveReasonList = new ArrayList<DocWaiveReasonDTO>();
			
			for (DocWaiveReasonDTO docWaiveReason : orderDoc.getWaiveReasonList()) {
				
				if (StringUtils.equals("Y", docWaiveReason.getDefaultWaiveInd())) {
					orderDoc.setWaiveReasonCd(docWaiveReason.getWaiveReasonCd());
					orderDoc.setWaiveReasonBy(userId);
				}
				else if (StringUtils.isNotBlank(docWaiveReason.getDefaultWaiveInd()) && 
						!StringUtils.equals(docWaiveReason.getDefaultWaiveInd(), distributeMode)) {
					continue;
				}
				
				filteredWaiveReasonList.add(docWaiveReason);
			}
			orderDoc.setWaiveReasonList(filteredWaiveReasonList);
		}
		return orderDoc;
	}
	
	private void setSubcItemOrderDoc(AcqOrderCaptureDTO orderCapture, LtsAcqSupportDocFormDTO form, String userId, List<OrderDocDTO> supportDocList)  {
		String[] subcItemIds = null;
		
		if (orderCapture.getSbOrder() != null) {
			subcItemIds = getAllSubcItemId(orderCapture.getSbOrder());
		}
		else {
			subcItemIds = getAllSubcItemId(orderCapture);
		}
		
		List<OrderDocDTO> subcItemDocList = null;
		
		if (ArrayUtils.isNotEmpty(subcItemIds)) {
			subcItemDocList = ltsOrderDocumentService.getOrderDocBySubcItem(subcItemIds);
		}
		
		if (subcItemDocList != null && !subcItemDocList.isEmpty()) {
			if (supportDocList.isEmpty()) {
				supportDocList.addAll(subcItemDocList);
				return;
			}
			for (OrderDocDTO subcItemDoc : subcItemDocList) {
				boolean isContainDoc = false;
				for (OrderDocDTO supportDoc : supportDocList)	 {
					if (StringUtils.equals(subcItemDoc.getDocType(), supportDoc.getDocType())) {
						isContainDoc = true;
					}
				}
				if (!isContainDoc) {
					supportDocList.add(subcItemDoc);	
				}
			}
		}
	}
	
	private String[] getAllSubcItemId(SbOrderDTO sbOrder) {
		ServiceDetailLtsDTO coreService = (ServiceDetailLtsDTO)LtsSbOrderHelper.getLtsService(sbOrder);
		ItemDetailLtsDTO[] subcItemDetails = coreService.getItemDtls();
		if (ArrayUtils.isEmpty(subcItemDetails)) {
			return null;
		}
		
		List<String> selectedItemIdList = new ArrayList<String>();
		
		for (ItemDetailLtsDTO itemDetail : subcItemDetails) {
			selectedItemIdList.add(itemDetail.getItemId());
		}
		
		if (selectedItemIdList.isEmpty()) {
			return null;
		}
		
		return selectedItemIdList.toArray(new String[selectedItemIdList.size()]);
		
	}
	
	private String[] getAllSubcItemId(AcqOrderCaptureDTO orderCapture) {
		
		if (orderCapture.getSbOrder() != null) {
			return null;
		}
		
		List<String> selectedItemIdList = new ArrayList<String>();
		
		if (orderCapture.getLtsAcqBasketServiceForm() != null) {
			addSelectedItemIds(orderCapture.getLtsAcqBasketServiceForm().getPlanItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketServiceForm().getBvasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketServiceForm().getContentItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketServiceForm().getInstallItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketServiceForm().getMoovItemList(), selectedItemIdList);
			
			if (orderCapture.getLtsAcqBasketServiceForm().getContItemSetList() != null && !orderCapture.getLtsAcqBasketServiceForm().getContItemSetList().isEmpty()) {
				for(ItemSetDetailDTO itemSetDetail : orderCapture.getLtsAcqBasketServiceForm().getContItemSetList()) {
					if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
						addSelectedItemIds(Lists.newArrayList(itemSetDetail.getItemDetails()), selectedItemIdList);	
					}
				}
			}
		}
		
		if (orderCapture.getLtsAcqBasketVasDetailForm() != null) {
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getBvasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getHotVasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getOtherVasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getE0060VasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getE0060VasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getIddVasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getIddOutVasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getExistVasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getOptAccessaryItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getPeFreeItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getPeSocketItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getPrewireVasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList(), selectedItemIdList);
			
		}
		
		if (orderCapture.getLtsPremiumSelectionForm() != null) {
			if (orderCapture.getLtsPremiumSelectionForm().getGiftSetList() != null && !orderCapture.getLtsPremiumSelectionForm().getGiftSetList().isEmpty()) {
				for(ItemSetDetailDTO itemSetDetail : orderCapture.getLtsPremiumSelectionForm().getGiftSetList()) {
					if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
						addSelectedItemIds(Lists.newArrayList(itemSetDetail.getItemDetails()), selectedItemIdList);	
					}
				}
			}
			
			if (orderCapture.getLtsPremiumSelectionForm().getPremiumSetList() != null && !orderCapture.getLtsPremiumSelectionForm().getPremiumSetList().isEmpty()) {
				for(ItemSetDetailDTO itemSetDetail : orderCapture.getLtsPremiumSelectionForm().getPremiumSetList()) {
					if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
						addSelectedItemIds(Lists.newArrayList(itemSetDetail.getItemDetails()), selectedItemIdList);	
					}
				}
			}
		}
		
		if (selectedItemIdList.isEmpty()) {
			return null;
		}
		
		return selectedItemIdList.toArray(new String[selectedItemIdList.size()]);
		
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
}
