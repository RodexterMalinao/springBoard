package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.DocWaiveReasonDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.form.LtsCustomerIdentificationFormDTO;
import com.bomwebportal.lts.dto.form.LtsOtherVoiceServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsPaymentFormDTO;
import com.bomwebportal.lts.dto.form.LtsRecontractFormDTO;
import com.bomwebportal.lts.dto.form.LtsSupportDocFormDTO;
import com.bomwebportal.lts.dto.form.LtsSupportDocFormDTO.FormAction;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.LtsConstant.LtsOrderFlag;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;

public class LtsSupportDocController extends SimpleFormController {

	
	private final String viewName = "ltssupportdoc";
	private final String commandName = "ltsSupportDocCmd";
	
	private LtsOrderDocumentService ltsOrderDocumentService;
	private CodeLkupCacheService suspendReasonLkupCacheService;
	private LtsCommonService ltsCommonService;

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

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public LtsSupportDocController() {
		setCommandClass(LtsSupportDocFormDTO.class);
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
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		LtsSupportDocFormDTO form = orderCapture.getLtsSupportDocForm();
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		
		boolean isBrCust = LtsConstant.DOC_TYPE_HKBR.equals(orderCapture.getLtsCustomerIdentificationForm().getDocType());
		
		if (form == null) {
			form = new LtsSupportDocFormDTO();
		}
		if (form.getCollectMethod() == null) {
			form.setCollectMethod(LtsConstant.COLLECT_METHOD_DIGITAL);	
		}
		if(form.getSignoffMode() == null){
			if(orderCapture.isChannelRetail()){
				form.setSignoffMode(LtsConstant.SIGNOFF_MODE_RETAIL);
			}
			if(orderCapture.isChannelCs()
					|| orderCapture.isChannelPremier()){
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
		if(StringUtils.isEmpty(form.getDistributeEmail()) 
				&& orderCapture.getLtsPaymentForm() != null
				&& orderCapture.getLtsPaymentForm().getCsPortalItem() != null 
				&& StringUtils.equals(LtsConstant.ITEM_TYPE_MYHKT_BILL, orderCapture.getLtsPaymentForm().getCsPortalItem().getItemType())
				&& orderCapture.getLtsPaymentForm().getCsPortalItem().isSelected()){
			for(ItemAttbDTO itemAttb : orderCapture.getLtsPaymentForm().getCsPortalItem().getItemAttbs()){
				if (StringUtils.equalsIgnoreCase(LtsConstant.CHANNEL_ATTB_FORMAT_EMAIL, itemAttb.getInputFormat())){
					form.setDistributeEmail(itemAttb.getAttbValue());
					break;
				}
			}
		}
		
		form.setGeneratedFormList(getGeneratedFormList(orderCapture));
		form.setSupportDocumentList(getSupportDocList(orderCapture, form, bomSalesUser.getUsername()));
		request.setAttribute("isBrCust", isBrCust);
		
		if("N".equals(ltsCommonService.getLtsOrderFlag(LtsOrderFlag.CHECK_AGE_DISTRIBUTE_PAPER))){
			form.setAllowDistributePaper(true);
		}else{
			String dateOfBirth = orderCapture.getLtsCustomerIdentificationForm().getDateOfBirth();
			if (StringUtils.isNotEmpty(dateOfBirth)) {
				dateOfBirth = LtsDateFormatHelper.getDateFromDTOFormat(dateOfBirth);
				if(LtsSbOrderHelper.isAgeOverByMonth(dateOfBirth)){
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
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		LtsSupportDocFormDTO form = (LtsSupportDocFormDTO) command;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put(LtsConstant.REQUEST_PARAM_ORDER_ACTION, LtsConstant.ORDER_ACTION_SUSPEND);
	    
	    if (FormAction.CHANGE_DIS_MODE == form.getFormAction()) {
	    	form.setSupportDocumentList(getSupportDocList(orderCapture, form, bomSalesUser.getUsername()));
	    	ModelAndView mav = new ModelAndView(viewName);
			mav.addObject(commandName, form);
			orderCapture.setLtsSupportDocForm(form);
			try {
				mav.addObject("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));	
			}
			catch (DAOException e) {
				throw new AppRuntimeException(e);
			}
			return mav;
	    }
	    else if (FormAction.SUSPEND == form.getFormAction()) {
	    	orderCapture.setLtsSupportDocForm(null);	
	    	paramMap.put(LtsConstant.REQUEST_PARAM_REASON_CD, form.getSuspendReason());	
		    paramMap.put(LtsConstant.REQUEST_PARAM_FROM_VIEW, viewName);
	    }
	    else {
	    	
			validate(orderCapture, form, errors);
			
			if (errors.hasFieldErrors()) {
				ModelAndView mav = new ModelAndView(viewName);
				mav.addAllObjects(errors.getModel());
				mav.addObject(commandName, form);
				return mav;
			}
			
	    	orderCapture.setLtsSupportDocForm(form);	
	    }
		return new ModelAndView(new RedirectView(LtsConstant.UPGRADE_EYE_ORDER_VIEW), paramMap);
	}
	
	private void validate(OrderCaptureDTO orderCapture, LtsSupportDocFormDTO form, BindException errors) {
		
		if (orderCapture.isChannelCs() || orderCapture.isChannelPremier()) {
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
		referenceData.put("suspendReasonList", Arrays.asList(suspendReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		return referenceData;
	}
	
	private List<OrderDocDTO> getGeneratedFormList(OrderCaptureDTO orderCapture) {
		List<OrderDocDTO> generatedFormList = new ArrayList<OrderDocDTO>();

		String coreServiceAfDocType = LtsConstant.ORDER_DOC_TYPE_EYEX_AF;
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
				&& StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			coreServiceAfDocType = LtsConstant.ORDER_DOC_TYPE_DEL_AF;
		}
		
		OrderDocDTO coreServiceAF = ltsOrderDocumentService.getOrderDoc(coreServiceAfDocType);
		if (coreServiceAF != null) {
			coreServiceAF.setSelected(true);
			generatedFormList.add(coreServiceAF);
		}
		
		LtsOtherVoiceServiceFormDTO secDelForm = orderCapture.getLtsOtherVoiceServiceForm();
		if (secDelForm.getCreate2ndDel() != null && secDelForm.getCreate2ndDel()) {
			OrderDocDTO secDelAF = ltsOrderDocumentService.getOrderDoc(LtsConstant.ORDER_DOC_TYPE_2ND_DEL_AF);
			if (secDelAF != null) {
				secDelAF.setSelected(true);
//				secDelAF.setMdoInd(LtsConstant.ITEM_MDO_MANDATORY);
				generatedFormList.add(secDelAF);
			}
		}
		
		//TODO: updateDnProfile
		if (orderCapture.getLtsMiscellaneousForm().isRecontract() && orderCapture.getLtsRecontractForm() != null) {
			OrderDocDTO recontractForm = ltsOrderDocumentService.getOrderDoc(LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM);
			if (recontractForm != null && StringUtils.isEmpty(orderCapture.getLtsRecontractForm().getUpdateDnProfile())) {
				recontractForm.setSelected(true);
//				secDelAF.setMdoInd(LtsConstant.ITEM_MDO_MANDATORY);
				generatedFormList.add(recontractForm);
			}
		}
		
		
		return generatedFormList;
	}
	
	private List<OrderDocDTO> getSupportDocList(OrderCaptureDTO orderCapture, LtsSupportDocFormDTO form, String userId) {
		
		List<OrderDocDTO> supportDocList = null;
		
		if (form != null) {
			supportDocList = form.getSupportDocumentList();
		}
		
		if (supportDocList == null) {
			supportDocList = new ArrayList<OrderDocDTO>();
		}
		
		// For Call Centre Mode
		if (orderCapture.isChannelCs() || (orderCapture.isChannelPremier() && LtsConstant.SIGNOFF_MODE_CALLCENTER.equals(form.getSignoffMode()) )){
			
			// Prepayment Sheet
			if (isPrepaymentSheetRequired(orderCapture.getLtsPaymentForm())) {
				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_PREPAYMENT_SHEET, userId, form.getDistributionMode());
			}
			else {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_PREPAYMENT_SHEET);
			}
			// Recontract AF
			//TODO: check is updateDNProfile -> skip recontract AF
			if (orderCapture.getLtsMiscellaneousForm().isRecontract() && orderCapture.getLtsRecontractForm() != null
				&& StringUtils.isEmpty(orderCapture.getLtsRecontractForm().getUpdateDnProfile())) {
				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED, userId, form.getDistributionMode());
			}
			else {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED);
			}
		}else{
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_PREPAYMENT_SHEET);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED);
		}
		
		setRecontractSupportDoc(orderCapture, form, userId, supportDocList);
		
		// Credit Card Copy
		if (isCreditCardCopyRequired(orderCapture.getLtsPaymentForm())) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_CREDIT_CARD_COPY, userId, form.getDistributionMode());
		}
		else {
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_CREDIT_CARD_COPY);
		}
		
		// Sales Memo Copy
		if (isSalesMemoCopyRequired(orderCapture.getLtsPaymentForm())) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_SALES_MEMO_COPY, userId, form.getDistributionMode());
		}
		else {
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_SALES_MEMO_COPY);
		}
		
		// Third-Party Authorization Letter
		if (isThirdPartyApplication(orderCapture)) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTHORIZATION_LETTER, userId, form.getDistributionMode());
		}
		else {
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTHORIZATION_LETTER);
		}
		
		//Third Party Bank auto-pay 
		if(isThirdPartyBankAutoPay(orderCapture.getLtsPaymentForm())){
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTOPAY_FORM, userId, form.getDistributionMode());
		}
		else{
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_THIRD_PARTY_AUTOPAY_FORM);
		}
		
		//Passport supporting document for all channels
		if (LtsConstant.DOC_TYPE_PASSPORT.equals(orderCapture.getLtsCustomerIdentificationForm().getDocType())) {
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_PASS_SUP_DOC, userId, form.getDistributionMode());
		}
		else {
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_PASS_SUP_DOC);
		}
		
		// Paper Signature Only
		assignDocForPaperMode(orderCapture, form, supportDocList, userId);
		
		// TODO: premium item proof
		
		
		setSubcItemOrderDoc(orderCapture, form, userId, supportDocList);
		
		return supportDocList;
	}

	
	private void setRecontractSupportDoc(OrderCaptureDTO orderCapture, LtsSupportDocFormDTO form, String userId, List<OrderDocDTO> supportDocList) {
		
		LtsRecontractFormDTO recontractForm = orderCapture.getLtsRecontractForm();
		
		//TODO: if update DN profile is not matched, required to collect HKID copy
		if (LtsSbOrderHelper.isRecontractOrder(orderCapture)) {
			
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_TRANSFEREE_ID_DOC_COPY, userId, form.getDistributionMode());
			addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_TRANSFERER_ID_DOC_COPY, userId, form.getDistributionMode());
			
			if (LtsConstant.CUST_NAME_NOT_MATCHED.equals(orderCapture.getLtsRecontractForm().getUpdateDnProfile())) {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_TRANSFERER_ID_DOC_COPY);
			}
			
			else if (LtsConstant.CUST_NAME_MATCHED.equals(orderCapture.getLtsRecontractForm().getUpdateDnProfile())){
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_TRANSFERER_ID_DOC_COPY);
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_TRANSFEREE_ID_DOC_COPY);
			}
			
			if (recontractForm.isDeceasedCase()) {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_LAWFUL_POSSESSION_SUPPORT_DOC);
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_TRANSFERER_ID_DOC_COPY);
				
				// Refund to transferer
				if (LtsConstant.DISC_DECEASE_NORMAL.equals(recontractForm.getRefundType())) {
					addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DECEASED_SUP_DOC, userId, form.getDistributionMode());
					addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_OPTIONAL, userId, form.getDistributionMode());
					removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_LETTER_OF_ADMINISTRATION);
					removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_MANDATORY);
				}
				else if (LtsConstant.DISC_DECEASE_SPECIAL.equals(recontractForm.getRefundType())) {
					addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DEATH_CERTIFICATE, userId, form.getDistributionMode());
					addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_MANDATORY, userId, form.getDistributionMode());
					removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DECEASED_SUP_DOC);
					removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_LETTER_OF_ADMINISTRATION);
					removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_OPTIONAL);
				}
				else if (LtsConstant.DISC_DECEASE_INHERIT.equals(recontractForm.getRefundType())) {
					addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_LETTER_OF_ADMINISTRATION, userId, form.getDistributionMode());
					removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DECEASED_SUP_DOC);
					removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DEATH_CERTIFICATE);
					removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_MANDATORY);
					removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_OPTIONAL);
				}
			}
			else {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DECEASED_SUP_DOC);
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_OPTIONAL);
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_MANDATORY);
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_LETTER_OF_ADMINISTRATION);
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DEATH_CERTIFICATE);
				
				//TODO: if recontractMode != 'S', skip recontract authorization letter
				if (!LtsConstant.RECONTRACT_MODE_SINGLE.equals(recontractForm.getRecontractMode())) {
//					removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_LAWFUL_POSSESSION_SUPPORT_DOC);
					removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_AUTHORIZATION_LETTER);
				}
				else {
//					addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_LAWFUL_POSSESSION_SUPPORT_DOC, userId, form.getDistributionMode());
					addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_AUTHORIZATION_LETTER, userId, form.getDistributionMode());
				}
			}
		}
		else {
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DECEASED_SUP_DOC);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_TRANSFEREE_ID_DOC_COPY);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_TRANSFERER_ID_DOC_COPY);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_OPTIONAL);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_IMMEDIATE_FAMILY_MEM_SUP_DOC_MANDATORY);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_LETTER_OF_ADMINISTRATION);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_DEATH_CERTIFICATE);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_LAWFUL_POSSESSION_SUPPORT_DOC);
		}
		
	}
	
	
	private boolean isThirdPartyApplication(OrderCaptureDTO orderCapture) {
		
		LtsCustomerIdentificationFormDTO custIdentifcationForm = orderCapture.getLtsCustomerIdentificationForm();
		if (custIdentifcationForm != null) {
			if (custIdentifcationForm.isSecDelThirdPartyApplication() || custIdentifcationForm.isThirdPartyApplication()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isThirdPartyBankAutoPay(LtsPaymentFormDTO paymentForm){
		
		if(paymentForm != null && paymentForm.getThirdPartyBankAccount()!= null){
			return paymentForm.getThirdPartyBankAccount().booleanValue();
		}
		return false;
	}
	
	
	private boolean isPrepaymentSheetRequired(LtsPaymentFormDTO paymentForm) {
		if (paymentForm.getPrePayItem() != null && 
				(StringUtils.equals(paymentForm.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CASH) || StringUtils.equals(paymentForm.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY))) {
					return true;
			}
			return false;
		}
	
	private boolean isCreditCardCopyRequired(LtsPaymentFormDTO paymentForm) {
		
		if (!StringUtils.equals(paymentForm.getNewPayMethodType(), paymentForm.getExistingPayMethodType()) && 
				StringUtils.equals(paymentForm.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)
				&& paymentForm.getThirdPartyCard() != null && paymentForm.getThirdPartyCard().booleanValue()) {
			return true;
		}
		return false;
	}
	
	private boolean isSalesMemoCopyRequired(LtsPaymentFormDTO paymentForm) {
		if (paymentForm.getPrePayItem() != null && StringUtils.isNotBlank(paymentForm.getSalesMemoNum())) {
			return true;
		}
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
	
	private void assignDocForPaperMode(OrderCaptureDTO orderCapture,
			LtsSupportDocFormDTO form, List<OrderDocDTO> supportDocList,
			String userId) {
		
		String coreServiceAfDocType = LtsConstant.ORDER_DOC_TYPE_EYEX_AF_SIGNED;
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
				&& StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			coreServiceAfDocType = LtsConstant.ORDER_DOC_TYPE_DEL_AF_SIGNED;
		}
		
		
		if (StringUtils.equals(LtsConstant.DISTRIBUTE_MODE_PAPER, form.getDistributionMode())) {
			addOrderDoc(supportDocList, coreServiceAfDocType, userId, form.getDistributionMode());
			
			LtsOtherVoiceServiceFormDTO secDelForm = orderCapture.getLtsOtherVoiceServiceForm();
			if (secDelForm.getCreate2ndDel() != null && secDelForm.getCreate2ndDel()) {
				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_2ND_DEL_AF_SIGNED, userId, form.getDistributionMode());	
			}
			
			//TODO
			if (orderCapture.getLtsMiscellaneousForm().isRecontract() && orderCapture.getLtsRecontractForm() != null
			   && StringUtils.isEmpty(orderCapture.getLtsRecontractForm().getUpdateDnProfile())) {
				addOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED, userId, form.getDistributionMode());
			}
			
		}
		else {
			removeOrderDoc(supportDocList, coreServiceAfDocType);
			removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_2ND_DEL_AF_SIGNED );
			if (orderCapture.isChannelRetail()) {
				removeOrderDoc(supportDocList, LtsConstant.ORDER_DOC_TYPE_RECONTRACT_FORM_SIGNED );	
			}
		}

		
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
	
	private void setSubcItemOrderDoc(OrderCaptureDTO orderCapture, LtsSupportDocFormDTO form, String userId, List<OrderDocDTO> supportDocList)  {
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
	
	private String[] getAllSubcItemId(OrderCaptureDTO orderCapture) {
		
		if (orderCapture.getSbOrder() != null) {
			return null;
		}
		
		List<String> selectedItemIdList = new ArrayList<String>();
		
		if (orderCapture.getLtsBasketServiceForm() != null) {
			addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getPlanItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getBvasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getContentItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getBbRentalItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getCancelChargeItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getInstallItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketServiceForm().getMoovItemList(), selectedItemIdList);
			
			if (orderCapture.getLtsBasketServiceForm().getContItemSetList() != null && !orderCapture.getLtsBasketServiceForm().getContItemSetList().isEmpty()) {
				for(ItemSetDetailDTO itemSetDetail : orderCapture.getLtsBasketServiceForm().getContItemSetList()) {
					if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
						addSelectedItemIds(Lists.newArrayList(itemSetDetail.getItemDetails()), selectedItemIdList);	
					}
				}
			}
		}
		
		if (orderCapture.getLtsBasketVasDetailForm() != null) {
			addSelectedItemIds(orderCapture.getLtsBasketVasDetailForm().getBvasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketVasDetailForm().getHotVasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketVasDetailForm().getOtherVasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketVasDetailForm().getE0060VasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketVasDetailForm().getE0060VasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketVasDetailForm().getIddVasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketVasDetailForm().getIddOutVasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketVasDetailForm().getExistVasItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketVasDetailForm().getOptAccessaryItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketVasDetailForm().getPeFreeItemList(), selectedItemIdList);
			addSelectedItemIds(orderCapture.getLtsBasketVasDetailForm().getPeSocketItemList(), selectedItemIdList);
			
			
//			if (orderCapture.getLtsBasketVasDetailForm().getBundleVasSetList() != null && !orderCapture.getLtsBasketVasDetailForm().getBundleVasSetList().isEmpty()) {
//				for(ItemSetDetailDTO itemSetDetail : orderCapture.getLtsBasketVasDetailForm().getBundleVasSetList()) {
//					if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
//						addSelectedItemIds(Lists.newArrayList(itemSetDetail.getItemDetails()), selectedItemIdList);	
//					}
//				}
//			}
			
			if (orderCapture.getLtsBasketVasDetailForm().getTeamVasSetList() != null && !orderCapture.getLtsBasketVasDetailForm().getTeamVasSetList().isEmpty()) {
				for(ItemSetDetailDTO itemSetDetail : orderCapture.getLtsBasketVasDetailForm().getTeamVasSetList()) {
					if (ArrayUtils.isNotEmpty(itemSetDetail.getItemDetails())) {
						addSelectedItemIds(Lists.newArrayList(itemSetDetail.getItemDetails()), selectedItemIdList);	
					}
				}
			}
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
}
