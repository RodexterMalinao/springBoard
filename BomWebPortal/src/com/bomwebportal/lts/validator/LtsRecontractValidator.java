package com.bomwebportal.lts.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsRecontractFormDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.util.Utility;

public class LtsRecontractValidator implements Validator{
	
	LtsCommonService ltsCommonService;
	
	public boolean supports(Class clazz) {
		return LtsRecontractFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsRecontractFormDTO form = (LtsRecontractFormDTO) command;


		if(StringUtils.isEmpty(form.getDocId())){
			errors.rejectValue("docId", "lts.docNum.required");
		}
		
		if(StringUtils.isEmpty(form.getDocType())){
			errors.rejectValue("docType", "lts.invalid.doctype");
		}
		
		if(LtsConstant.DOC_TYPE_HKID.equals(form.getDocType())){
			if (Utility.validateHKID(form.getDocId())==false){
				errors.rejectValue("docId", "invalid.hkidCheckDigit");	
			}else if (Utility.validateHKIDcheckDigit(form.getDocId())==false){
				errors.rejectValue("docId", "invalid.hkidCheckDigit");	
			}
		}else if (LtsConstant.DOC_TYPE_PASSPORT.equals(form.getDocType())){
			if(form.getDocId().length() < 6){
				errors.rejectValue("docId", "lts.invalid.length");
			}
			if(!StringUtils.isAlphanumeric(form.getDocId())){
				errors.rejectValue("docId", "lts.invalid.char");
			}
		}
		else if(LtsConstant.DOC_TYPE_HKBR.equals(form.getDocType())){
			if (!Utility.validateHKBR(form.getDocId())){
				errors.rejectValue("docId", "invalid.hkbr");	
			}else{
				if (!Utility.validateHKBRcheckDigit(form.getDocId()))
				    errors.rejectValue("docId", "invalid.hkbrCheckDigit");	
			}
			if(StringUtils.isEmpty(form.getCompanyName())){
				errors.rejectValue("companyName", "companyName.required");
			}
		}
			
		if(StringUtils.isEmpty(form.getTitle())){
			errors.rejectValue("title", "lts.title.required");
		}
		if(StringUtils.isEmpty(form.getFirstName())){
			errors.rejectValue("firstName", "firstName.required");
		}
		if(StringUtils.isEmpty(form.getLastName())){
			errors.rejectValue("lastName", "lastName.required");
		}
		
		if(StringUtils.isNotEmpty(form.getEmailAddr())){
		 	if(!LtsCommonValidator.isValidEmail(form.getEmailAddr())){
		 		errors.rejectValue("emailAddr", "invalid.emailAddr");
		 	}
		}
		
		if(!StringUtils.isEmpty(form.getMobileNum())){
			if(!StringUtils.isNumeric(form.getMobileNum())){
				errors.rejectValue("mobileNum", "invalid.msisdn");
			}else if(!ltsCommonService.isMobileNumPrefix(form.getMobileNum())){
				errors.rejectValue("mobileNum", "msisdn.required");
			}
		}
		
		if(StringUtils.isEmpty(form.getRecontractMode())){
			errors.rejectValue("recontractMode", "lts.option.required");
		}
		
		if(StringUtils.isEmpty(form.getGlobalCallCardService())
				|| StringUtils.isEmpty(form.getIdd0060FixedFeePlan())
				|| StringUtils.isEmpty(form.getMobileIdd0060Service())){
			errors.rejectValue("globalCallCardService", "lts.option.required");
		}
		
		if (form.isDeceasedCase()) {
			if (StringUtils.isBlank(form.getRelationship())) {
				errors.rejectValue("relationship", "lts.invalid.relationship");
			}
			
			if (StringUtils.isBlank(form.getRefundType())) {
				errors.rejectValue("refundType", "lts.invalid.refundType");
			}
			
			if (StringUtils.isNotBlank(form.getNewBillingAddress())) {
				form.setNewBillingAddress(LtsCommonValidator.removeBaUnwantedBlanks(form.getNewBillingAddress()));
				if(!LtsCommonValidator.isValidBaFormat(form.getNewBillingAddress())){
					errors.rejectValue("newBillingAddress", "lts.invalid.billingAddress");	
				}else if(LtsCommonValidator.containsSpecialChar(form.getNewBillingAddress())){
					errors.rejectValue("newBillingAddress", "lts.invalid.billingAddress.specialChar");	
				}
			}else if (LtsConstant.DISC_DECEASE_SPECIAL.equals(form.getRefundType())
					|| LtsConstant.DISC_DECEASE_INHERIT.equals(form.getRefundType())){
				errors.rejectValue("newBillingAddress", "lts.billingAddress.required");	
			}
			
			if(StringUtils.isNotBlank(form.getNewBillingName())){
				form.setNewBillingName(LtsCommonValidator.removeTabs(form.getNewBillingName()));
				if(form.getNewBillingName().length() > 40){
					errors.rejectValue("newBillingName", "lts.dis.billingname.toolong");	
				}
				if (StringUtils.equalsIgnoreCase(form.getExistingBillingName(), form.getNewBillingName())) {
					errors.rejectValue("newBillingName", "lts.dis.billingname.same.as.existing");
				}
			}
			else if (LtsConstant.DISC_DECEASE_SPECIAL.equals(form.getRefundType())
						|| LtsConstant.DISC_DECEASE_INHERIT.equals(form.getRefundType())){
				errors.rejectValue("newBillingName", "lts.billingName.required");
			}
		}
		
		if(form.isVerifyRequried()){
			if(!form.isOldCustVerified()){
				errors.rejectValue("oldCustVerified", "lts.custIdent.pleaseVerify");
			}
			
			if(!form.isNewCustVerified()){
				errors.rejectValue("newCustVerified", "lts.custIdent.pleaseVerify");
			}
		}
	}

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
}
