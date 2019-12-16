package com.bomwebportal.lts.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsCustomerIdentificationFormDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.util.Utility;

public class LtsCustomerIdentificationValidator implements Validator {

	private LtsCommonService ltsCommonService;
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}


	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}


	public boolean supports(Class clazz) {
		return LtsCustomerIdentificationFormDTO.class.isAssignableFrom(clazz);
	}
	
	
	public void validate(Object command, Errors errors) {
		LtsCustomerIdentificationFormDTO cusIdentification = (LtsCustomerIdentificationFormDTO) command;
		if (StringUtils.isBlank(cusIdentification.getId())) {
			errors.rejectValue("id", "lts.iddocnum.required");
		}else{
			validateDocIdValue(
					cusIdentification.getDocType(),
					cusIdentification.getId(),
					errors, "id");
			if(cusIdentification.isDummy() && StringUtils.equals(cusIdentification.getId(), cusIdentification.getDummyId())
					&& StringUtils.equals(cusIdentification.getDocType(), cusIdentification.getDummyDocType())){
				errors.rejectValue("id", "lts.invaild.dummyId");
			}
		}
		if (cusIdentification.isMustVerify() && !cusIdentification.isVerified()){
			errors.rejectValue("verified", "lts.verification.required");	
		}
		//thirdParty
		if (cusIdentification.isThirdPartyApplication()){
			if (StringUtils.isBlank(cusIdentification.getApplicantTitle())) {
				errors.rejectValue("applicantTitle", "lts.title.required");
			}
			if (StringUtils.isBlank(cusIdentification.getApplicantFirstName())
					|| StringUtils.isBlank(cusIdentification.getApplicantLastName())) {
				errors.rejectValue("applicantLastName", "lts.applicantName.required");
			}
			if (StringUtils.isBlank(cusIdentification.getApplicantId())) {
				if(cusIdentification.isMustVerify()){
					errors.rejectValue("applicantId", "lts.iddocnum.required");
				}
			}else{
				validateDocIdValue(
						cusIdentification.getApplicantDocType(),
						cusIdentification.getApplicantId(),
						errors, "applicantId");
				if(StringUtils.equals(cusIdentification.getApplicantDocType(), cusIdentification.getDocType())
						&& StringUtils.equals(cusIdentification.getApplicantId(), cusIdentification.getId())){
					errors.rejectValue("applicantId", "lts.invaild.thirdpartyinfo");
				}
			}
			if (cusIdentification.isMustVerify() && !cusIdentification.isApplicantVerified()){
				errors.rejectValue("applicantVerified", "lts.verification.required");
			}
			if (StringUtils.isBlank(cusIdentification.getRelationship())) {
				errors.rejectValue("relationship", "lts.invalid.relationship");
			}
			String contact = cusIdentification.getContactNum();
			ltsCommonService.validateContactValue(contact, errors, "contactNum");
		}
		//secDel
		if(cusIdentification.isCreateSecDel()){
			if (StringUtils.isBlank(cusIdentification.getSecDelId())) {
				if(cusIdentification.isMustVerify()){
					errors.rejectValue("secDelId", "lts.iddocnum.required");
				}
			}else{
				validateDocIdValue(
						cusIdentification.getSecDelDocType(),
						cusIdentification.getSecDelId(),
						errors, "secDelId");
				if(cusIdentification.isSecDelDummy() && StringUtils.equals(cusIdentification.getSecDelId(), cusIdentification.getSecDelDummyId())){
					errors.rejectValue("secDelId", "lts.invaild.dummyId");
				}
			}
			if (cusIdentification.isMustVerify() && !cusIdentification.isSecDelVerified()){
				errors.rejectValue("secDelVerified", "lts.verification.required");	
			}
		}
		//secDel thirdParty
		if (cusIdentification.isSecDelThirdPartyApplication()){
			if (StringUtils.isBlank(cusIdentification.getSecDelApplicantTitle())) {
				errors.rejectValue("secDelApplicantTitle", "lts.title.required");
			}
			if (StringUtils.isBlank(cusIdentification.getSecDelApplicantFirstName())
					|| StringUtils.isBlank(cusIdentification.getSecDelApplicantLastName())) {
				errors.rejectValue("secDelApplicantLastName", "lts.applicantName.required");
			}
			if (StringUtils.isBlank(cusIdentification.getSecDelApplicantId())) {
				if(cusIdentification.isMustVerify()){
					errors.rejectValue("secDelApplicantId", "lts.iddocnum.required");
				}
			}else{
				validateDocIdValue(
						cusIdentification.getSecDelApplicantDocType(),
						cusIdentification.getSecDelApplicantId(),
						errors, "secDelApplicantId");
				if(StringUtils.equals(cusIdentification.getSecDelApplicantDocType(), cusIdentification.getSecDelDocType())
						&& StringUtils.equals(cusIdentification.getSecDelApplicantId(), cusIdentification.getSecDelId())){
					errors.rejectValue("secDelApplicantId", "lts.invaild.thirdpartyinfo");
				}
			}
			if (cusIdentification.isMustVerify() && !cusIdentification.isSecDelApplicantVerified()){
				errors.rejectValue("secDelApplicantVerified", "lts.verification.required");
			}
			if (StringUtils.isBlank(cusIdentification.getSecDelRelationship())) {
				errors.rejectValue("secDelRelationship", "lts.invalid.relationship");
			}
			String contact = cusIdentification.getSecDelContactNum();
			ltsCommonService.validateContactValue(contact, errors, "secDelContactNum");
		}
		
		if (StringUtils.isBlank(cusIdentification.getExistLtsPdpoStatus())
				|| LtsConstant.DATA_PRIVACY_OPT_IND_OOA_CD.equals(cusIdentification.getExistLtsPdpoStatus())) {
			if (StringUtils.isBlank(cusIdentification.getNewLtsPdpoStatus())) {
				errors.rejectValue("newLtsPdpoStatus", "lts.optInOutInd.required");
			}
			
			if (LtsConstant.DATA_PRIVACY_OPT_IND_OOP_CD.equals(cusIdentification.getNewLtsPdpoStatus())) {
				if (!cusIdentification.isLtsPdpoBillInsert()
						&& !cusIdentification.isLtsPdpoBm()
						&& !cusIdentification.isLtsPdpoCdOutdial()
						&& !cusIdentification.isLtsPdpoDirectMailing()
						&& !cusIdentification.isLtsPdpoEmail()
						&& !cusIdentification.isLtsPdpoOptOutAll()
						&& !cusIdentification.isLtsPdpoOutbound()
						&& !cusIdentification.isLtsPdpoSms()
						&& !cusIdentification.isLtsPdpoSmsEye()
						&& !cusIdentification.isLtsPdpoWebBill()
						) {
					errors.rejectValue("newLtsPdpoStatus", "lts.optOutService.required");	
				}
			}
		}
		
		
		String contactFixLineNum = StringUtils.remove(cusIdentification.getCustomerContactFixLineNum(), " ");
		String contactMobileNum = StringUtils.remove(cusIdentification.getCustomerContactMobileNum(), " ");
		String contactEmail = StringUtils.remove(cusIdentification.getCustomerContactEmail(), " ");

		if (StringUtils.isNotEmpty(contactMobileNum)){
			if (!ltsCommonService.isMobileNumPrefix(contactMobileNum)) {
				errors.rejectValue("customerContactMobileNum", "lts.invalid.mobilenum");
			}
		}
		if (StringUtils.isNotEmpty(contactFixLineNum)){
			if(!ltsCommonService.isFixLineNumPrefix(contactFixLineNum, true)) {
				errors.rejectValue("customerContactFixLineNum", "lts.invalid.fixlinenum");
			}
		}
		if (StringUtils.isNotEmpty(contactEmail)){
			if (!LtsCommonValidator.isValidEmail(contactEmail)) {
				errors.rejectValue("customerContactEmail", "lts.distributeEmail.invalid");
			}	
		}
		
		
		cusIdentification.setCustomerContactFixLineNum(contactFixLineNum);
		cusIdentification.setCustomerContactMobileNum(contactMobileNum);
		cusIdentification.setCustomerContactEmail(contactEmail);
	}
	
	private void validateDocIdValue(String docType, String docId, Errors errors, String fieldName){		
		if (LtsConstant.DOC_TYPE_HKID.equals(docType)){
			if (!Utility.validateHKID(docId)){
				errors.rejectValue(fieldName, "invalid.hkid");	
			}else if (!Utility.validateHKIDcheckDigit(docId)){
				errors.rejectValue(fieldName, "invalid.hkidCheckDigit");	
			}
		}else if (LtsConstant.DOC_TYPE_PASSPORT.equals(docType)){
			if(docId.length() < 6){
				errors.rejectValue(fieldName, "lts.invalid.length");
			}
//			if(!StringUtils.isAlphanumeric(docId)){
//				errors.rejectValue(fieldName, "lts.invalid.char");
//			}
			if(!Pattern.matches(LtsConstant.PASSPORT_REGEX, docId)){
				errors.rejectValue(fieldName, "lts.invalid.char");
			}
		}else if(LtsConstant.DOC_TYPE_HKBR.equals(docType)){
			if (!Utility.validateHKBR(docId)){
				errors.rejectValue(fieldName, "invalid.hkbr");	
			}else if (!Utility.validateHKBRcheckDigit(docId)){
				errors.rejectValue(fieldName, "invalid.hkbrCheckDigit");	
			}
		}else if(StringUtils.isEmpty(docType)){
			errors.rejectValue(fieldName, "lts.invalid.doctype");
		}
	}
}
