package com.bomwebportal.lts.validator.disconnect;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateCustomerInquiryFormDTO;

public class LtsTerminateCustomerInquiryValidator implements Validator {

	public boolean supports(Class clazz) {
		return LtsTerminateCustomerInquiryFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsTerminateCustomerInquiryFormDTO form = (LtsTerminateCustomerInquiryFormDTO) command;
		
		switch (form.getFormAction()) {
		case SUBMIT:
			if (StringUtils.isNotBlank(form.getServiceNum()) && !StringUtils.isNumeric(form.getServiceNum().trim())) {
				errors.rejectValue("serviceNum", "lts.serviceNum.invalid");
			}
			break;
		default:
			break;
		}
	}

}
