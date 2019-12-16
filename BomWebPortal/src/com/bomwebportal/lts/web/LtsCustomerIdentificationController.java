package com.bomwebportal.lts.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsCustomerIdentificationFormDTO;
import com.bomwebportal.lts.dto.profile.CustPdpoProfileDTO;
import com.bomwebportal.lts.dto.profile.CustomerContactProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.service.bom.CustPdpoProfileService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.util.Utility;

public class LtsCustomerIdentificationController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	private CodeLkupCacheService relationshipCodeLkupCacheService;
	private CodeLkupCacheService relationshipBrCodeLkupCacheService;
	private CodeLkupCacheService titleLkupCacheService;
	private ServiceProfileLtsService serviceProfileLtsService;
	private CustPdpoProfileService custPdpoProfileService;
	private CustomerProfileLtsService customerProfileLtsService;
	
	private final String nextView = "ltssalesinfo.html";

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		return super.handleRequestInternal(request, response);
	}
	
	private void setProfileContactInfo(OrderCaptureDTO orderCapture, LtsCustomerIdentificationFormDTO form) {
		CustomerDetailProfileLtsDTO targetCustomer = orderCapture.getLtsServiceProfile().getPrimaryCust();
		if (LtsSbOrderHelper.isRecontractOrder(orderCapture)) {
			targetCustomer = orderCapture.getLtsRecontractForm().getCustDetailProfile();
		}
		if (targetCustomer == null) {
			return;
		}
		CustomerContactProfileLtsDTO[] profileCustContacts = customerProfileLtsService.getCustContactInfo(targetCustomer.getCustNum(), LtsConstant.SYSTEM_ID_DRG);
		
		if (ArrayUtils.isNotEmpty(profileCustContacts)) {
			for (CustomerContactProfileLtsDTO profileCustContact : profileCustContacts) {
				if (LtsConstant.CONTACT_MEDIA_TYPE_EMAIL.equals(profileCustContact.getMediaType())) {
					form.setCustomerContactEmail(profileCustContact.getMediaNum());
					form.setCustomerOrgContactEmail(profileCustContact.getMediaNum());
					form.setCustomerOrgContactEmailDate(StringUtils.substring(profileCustContact.getLastUpdDate(), 0, 16));
					// If last update date over 3 months, prompt alert message to update.
					form.setContactEmailOverDue(LtsSbOrderHelper.checkLastUpdDate(profileCustContact.getLastUpdDate()));
				}
				else if (LtsConstant.CONTACT_MEDIA_TYPE_MOBILE.equals(profileCustContact.getMediaType())) {
					form.setCustomerContactMobileNum(profileCustContact.getMediaNum());
					form.setCustomerOrgContactMobileNum(profileCustContact.getMediaNum());
					form.setCustomerOrgContactMobileNumDate(StringUtils.substring(profileCustContact.getLastUpdDate(), 0, 16));
					// If last update date over 3 months, prompt alert message to update.
					form.setContactMobileNumOverDue(LtsSbOrderHelper.checkLastUpdDate(profileCustContact.getLastUpdDate()));
				}
			}
		}
	}
	
	private void setDummyDocument(OrderCaptureDTO orderCapture, LtsCustomerIdentificationFormDTO form) {
		String dummyDocType = null;
		String dummyDocNum = null;
		boolean isDummyDoc = false;
		
		CustomerDetailProfileLtsDTO targetCustomer = orderCapture.getLtsServiceProfile().getPrimaryCust();
		
		if (LtsSbOrderHelper.isRecontractOrder(orderCapture)) {
			dummyDocType = orderCapture.getLtsRecontractForm().getDocType();
			dummyDocNum = orderCapture.getLtsRecontractForm().getDocId();
			targetCustomer = orderCapture.getLtsRecontractForm().getCustDetailProfile();
			form.setVerified(orderCapture.getLtsRecontractForm().isNewCustVerified());
		}
		else {
			dummyDocType = orderCapture.getLtsServiceProfile().getPrimaryCust().getDocType();
			dummyDocNum = orderCapture.getLtsServiceProfile().getPrimaryCust().getDocNum();
			isDummyDoc = orderCapture.getLtsCustomerInquiryForm().getDummyDoc() == null ? false : orderCapture.getLtsCustomerInquiryForm().getDummyDoc();
		}
		
		form.setDummyDocType(dummyDocType);
		form.setDummyId(dummyDocNum);
		
		if(checkDummy(dummyDocType, dummyDocNum, isDummyDoc)){
			form.setDummy(true);
		}
		else{
			form.setDummy(false);
			form.setDocType(dummyDocType);
			form.setId(dummyDocNum);
			if (targetCustomer != null) {
				form.setDateOfBirth(LtsSbOrderHelper.getProfileDateOfBirth(targetCustomer));
				form.setDobBom(LtsSbOrderHelper.getProfileDateOfBirth(targetCustomer));
				if(!form.isVerified()){
					form.setVerified( targetCustomer.isIdVerifyInd());
				}
			}
		}
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		LtsCustomerIdentificationFormDTO form = orderCapture.getLtsCustomerIdentificationForm();
		
		if(form == null) {
			form = new LtsCustomerIdentificationFormDTO();
			setDummyDocument(orderCapture, form);
			setProfileContactInfo(orderCapture, form);
		}
		
		setCustPdpo(orderCapture, form);
		form.setMustVerify(!LtsSessionHelper.isChannelCs(request));
		
		form.setThirdPartyApplication(orderCapture.getLtsCustomerInquiryForm().getThirdPartyApplication());
		request.setAttribute("thirdPartyApplication", orderCapture.getLtsCustomerInquiryForm().getThirdPartyApplication());
		
		//Second Del
		
		if(orderCapture.getLtsOtherVoiceServiceForm().getCreate2ndDel() == null
				|| orderCapture.getLtsOtherVoiceServiceForm().getCreate2ndDel() == false){
			request.setAttribute("createSecDel", false);
			form.setCreateSecDel(false);
		}else{
			request.setAttribute("createSecDel", true);
			form.setCreateSecDel(true);
			if(orderCapture.getLtsOtherVoiceServiceForm() != null){
				if(orderCapture.getSecondDelServiceProfile() != null){
					if(StringUtils.isBlank(form.getSecDelId())){
						String dummyDocType = orderCapture.getSecondDelServiceProfile().getPrimaryCust().getDocType();
						String dummyDocNum = orderCapture.getSecondDelServiceProfile().getPrimaryCust().getDocNum();
						boolean dummyInd = orderCapture.getLtsOtherVoiceServiceForm().getSecondDelDummyDoc() == null? 
								false :orderCapture.getLtsOtherVoiceServiceForm().getSecondDelDummyDoc();
						
						form.setSecDelDummyDocType(dummyDocType);
						form.setSecDelDummyId(dummyDocNum);

						if(checkDummy(dummyDocType, dummyDocNum, dummyInd)){
							
							form.setSecDelDummy(true);
						}else{
							form.setSecDelDocType(dummyDocType);
							form.setSecDelId(dummyDocNum);
							form.setSecDelDummy(false);
						}
					}
					
					/*if(orderCaptureDTO.getLtsOtherVoiceServiceForm().getSecondDelDummyDoc() == null
							|| orderCaptureDTO.getLtsOtherVoiceServiceForm().getSecondDelDummyDoc() != true ){
						ltsCustomerIdentificationDTO.setSecDelDummy(false);
					}else{
						ltsCustomerIdentificationDTO.setSecDelDummy(true);
					}*/
					if(orderCapture.getLtsOtherVoiceServiceForm().getSecondDelThirdPartyAppl() == null
							|| orderCapture.getLtsOtherVoiceServiceForm().getSecondDelThirdPartyAppl() != true ){
						form.setSecDelThirdPartyApplication(false);
						request.setAttribute("secDelThirdPartyApplication", LtsConstant.FALSE_IND);
					}else{
						form.setSecDelThirdPartyApplication(true);
						request.setAttribute("secDelThirdPartyApplication", LtsConstant.TRUE_IND);
					}
				}else{
					if(StringUtils.isBlank(form.getSecDelId())){
						if(!form.isDummy()){
							form.setSecDelDocType(form.getDocType());
							form.setSecDelId(form.getId());
							form.setSecDelDummyDocType(form.getDocType());
							form.setSecDelDummyId(form.getId());
						}
					}
				}
			}
		}

//		orderCapture.setLtsCustomerIdentificationForm(form);
		return form;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
		
		LtsCustomerIdentificationFormDTO ltsCustomerIdentificationDTO = (LtsCustomerIdentificationFormDTO) command;
		OrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getOrderCapture(request); 
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}


		if(LtsConstant.DOC_TYPE_HKBR.equals(ltsCustomerIdentificationDTO.getDocType())
				&& orderCaptureDTO.getLtsOtherVoiceServiceForm() != null
				&& orderCaptureDTO.getLtsOtherVoiceServiceForm().getCreate2ndDel() == true){
			ltsCustomerIdentificationDTO.setSecDelThirdPartyApplication(true);
			ltsCustomerIdentificationDTO.setSecDelApplicantDocType(ltsCustomerIdentificationDTO.getApplicantDocType());
			ltsCustomerIdentificationDTO.setSecDelApplicantFirstName(ltsCustomerIdentificationDTO.getApplicantFirstName());
			ltsCustomerIdentificationDTO.setSecDelApplicantId(ltsCustomerIdentificationDTO.getApplicantId());
			ltsCustomerIdentificationDTO.setSecDelApplicantLastName(ltsCustomerIdentificationDTO.getApplicantLastName());
			ltsCustomerIdentificationDTO.setSecDelApplicantTitle(ltsCustomerIdentificationDTO.getApplicantTitle());
			ltsCustomerIdentificationDTO.setSecDelRelationship(ltsCustomerIdentificationDTO.getRelationship());
			ltsCustomerIdentificationDTO.setSecDelApplicantVerified(ltsCustomerIdentificationDTO.isApplicantVerified());
			ltsCustomerIdentificationDTO.setSecDelContactNum(ltsCustomerIdentificationDTO.getContactNum());
			
		}

		orderCaptureDTO.setLtsCustomerIdentificationForm(ltsCustomerIdentificationDTO);
		
		logger.info("nextView: "+ nextView);
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	protected Map<String, List<LookupItemDTO>> referenceData(HttpServletRequest request) throws Exception {
		Map<String, List<LookupItemDTO>> referenceData = new HashMap<String, List<LookupItemDTO>>();
		referenceData.put("relationshipCodeList", Arrays.asList(relationshipCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("relationshipBrCodeList", Arrays.asList(relationshipBrCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("titleList", Arrays.asList(titleLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		
		return referenceData;
	}
	
	private boolean checkDummy(String docType, String docNum, boolean dummyInd){
		if(!dummyInd){
			if((StringUtils.contains(docNum, '#')
					|| StringUtils.containsIgnoreCase(docNum, "Dum"))){
				return true;
			}else if(StringUtils.equals(docType, LtsConstant.DOC_TYPE_HKID)
					&& (!Utility.validateHKID(docNum) || !Utility.validateHKIDcheckDigit(docNum)) ){
				return true;
			}else if(StringUtils.equals(docType, LtsConstant.DOC_TYPE_PASSPORT)
					&& (Utility.validateHKID(docNum) ||  Utility.validateHKIDcheckDigit(docNum))){
				return true;
			}else if(StringUtils.equals(docType, LtsConstant.DOC_TYPE_HKBR) 
					&& (!Utility.validateHKBR(docNum) || !Utility.validateHKBRcheckDigit(docNum)) ){
					return true;
			}
		}
		return dummyInd;
	}
	
	private void setCustPdpo(OrderCaptureDTO orderCapture, LtsCustomerIdentificationFormDTO form) {
		
		CustomerDetailProfileLtsDTO customerProfile = orderCapture.getLtsServiceProfile().getPrimaryCust();
		if (LtsSbOrderHelper.isRecontractOrder(orderCapture)) {
			customerProfile = orderCapture.getLtsRecontractForm().getCustDetailProfile();
		}
		if (customerProfile == null) {
			return;
		}
		
		CustPdpoProfileDTO[] pdpoDetails = custPdpoProfileService.getCustDataPrivacyInfo(customerProfile.getCustNum(), LtsConstant.SYSTEM_ID_DRG);
		if (ArrayUtils.isNotEmpty(pdpoDetails)) {
			for (CustPdpoProfileDTO pdpoProfile : pdpoDetails) {
				if (LtsConstant.DATA_PRIVACY_BOM_LOB_LTS.equals(pdpoProfile.getLob())) {
					form.setExistLtsPdpoStatus(pdpoProfile.getOptInd());
					return;
				}
			}
		}
	}
	

	public CodeLkupCacheService getRelationshipCodeLkupCacheService() {
		return relationshipCodeLkupCacheService;
	}

	public void setRelationshipCodeLkupCacheService(
			CodeLkupCacheService relationshipCodeLkupCacheService) {
		this.relationshipCodeLkupCacheService = relationshipCodeLkupCacheService;
	}

	public CodeLkupCacheService getTitleLkupCacheService() {
		return titleLkupCacheService;
	}

	public void setTitleLkupCacheService(CodeLkupCacheService titleLkupCacheService) {
		this.titleLkupCacheService = titleLkupCacheService;
	}

	public CodeLkupCacheService getRelationshipBrCodeLkupCacheService() {
		return relationshipBrCodeLkupCacheService;
	}

	public void setRelationshipBrCodeLkupCacheService(
			CodeLkupCacheService relationshipBrCodeLkupCacheService) {
		this.relationshipBrCodeLkupCacheService = relationshipBrCodeLkupCacheService;
	}

	public ServiceProfileLtsService getServiceProfileLtsService() {
		return serviceProfileLtsService;
	}

	public void setServiceProfileLtsService(
			ServiceProfileLtsService serviceProfileLtsService) {
		this.serviceProfileLtsService = serviceProfileLtsService;
	}

	public CustPdpoProfileService getCustPdpoProfileService() {
		return custPdpoProfileService;
	}

	public void setCustPdpoProfileService(
			CustPdpoProfileService custPdpoProfileService) {
		this.custPdpoProfileService = custPdpoProfileService;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	
}

