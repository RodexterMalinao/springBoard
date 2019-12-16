package com.bomwebportal.lts.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsCustomerInquiryFormDTO;


public class LtsCustomerInquiryValidator implements Validator {

	public boolean supports(Class clazz) {
		return LtsCustomerInquiryFormDTO.class.isAssignableFrom(clazz);
	}
	
	
	public void validate(Object command, Errors errors) {
		LtsCustomerInquiryFormDTO form = (LtsCustomerInquiryFormDTO) command;
		
		switch (form.getFormAction()) {
		case SEARCH_PROFILE:
			if (StringUtils.isBlank(form.getServiceNum()) || StringUtils.isBlank(form.getDocNum())) {
				errors.rejectValue("docNum", "lts.idDocNumOrServiceNum.required");
			}	
//			else if (StringUtils.isNotBlank(form.getServiceNum()) && !StringUtils.isNumeric(form.getServiceNum())) {
//				errors.rejectValue("serviceNum", "lts.serviceNum.invalid");
//			}
//			else if (StringUtils.isNotBlank(form.getDocNum()) && form.getDocNum().trim().length() > 30) {
//				errors.rejectValue("docNum", "lts.docNum.invalid");
//			}
//			else if ("HKID".equals(form.getDocType()) && !Utility.validateHKID(form.getDocNum())) {
//				errors.rejectValue("docNum", "lts.docNum.invalid");	
//			}
//			else if ("HKID".equals(form.getDocType()) && !Utility.validateHKIDcheckDigit(form.getDocNum())){
//				errors.rejectValue("docNum", "lts.docNum.invalid");	
//			}					
			
			if (form.getDummyDoc() == null) {
				errors.rejectValue("dummyDoc", "lts.dummyDoc.required");
			}
			if (form.getThirdPartyApplication() == null) {
				errors.rejectValue("thirdPartyApplication", "lts.thirdPartyApplication.required");
			}
			break;
		default:
			break;
		}

	}

}
