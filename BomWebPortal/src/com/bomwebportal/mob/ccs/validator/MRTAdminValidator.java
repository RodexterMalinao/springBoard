package com.bomwebportal.mob.ccs.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.ui.MRTAdminDetailUI;

public class MRTAdminValidator implements Validator {

	public boolean supports(Class clazz) {
		return clazz.equals(MRTAdminDetailUI.class);
	}

	public void validate(Object obj, Errors errors) {
		MRTAdminDetailUI form = (MRTAdminDetailUI) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rowId", "rowId.required");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "msisdnlvl", "mrtAdminMsisdnlvl.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "msisdn", "mrtAdminMsisdn.required");
		
		if (StringUtils.isNotBlank(form.getMsisdn())) {
			if (form.getMsisdn().startsWith("0")) {
				errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.leadingzero");
			}
			if (!StringUtils.isNumeric(form.getMsisdn())) {
				errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.numeric");
			}
			if (form.getMsisdn().length() != 8) {
				errors.rejectValue("msisdn", "mrtAdminMsisdn.pattern.pccw3g");
			}
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "reserveId", "reserveId.required");
	}

}
