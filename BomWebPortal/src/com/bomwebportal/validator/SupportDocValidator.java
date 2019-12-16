package com.bomwebportal.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.dto.ui.SupportDocUI;
import com.bomwebportal.util.Utility;

public class SupportDocValidator implements Validator {

	public boolean supports(Class clazz) {
		return SupportDocUI.class.equals(clazz);
	}

	public void validate(Object command, Errors errors) {
		SupportDocUI form = (SupportDocUI) command;
		if (form.getDisMode() == null) {
			errors.rejectValue("distributionMode", null, "Require Distribution Mode");
		} else {
			switch (form.getDisMode()) {
			case E:
				if (!CollectMethod.D.equals(form.getCollectMethod())) {
					errors.rejectValue("collectMethod", null, "Only allow Collect method = Digital when Distribution Mode = Digital Signature");
				}
				//20130917 - E==>no email 
				//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "esigEmailLang", null, "Require Email");
				if (StringUtils.isNotBlank(form.getEsigEmailAddr())) {
					if (!Utility.validateEmailMobSpecific(form.getEsigEmailAddr())) {//change validation function 20130722
						errors.rejectValue("esigEmailAddr", null, "Invalid Email format");
					}
				}
				if (form.getEsigEmailLang() == null) {
					errors.rejectValue("esigEmailLang", null, "Require Language");
				}
				break;
			case P:
				if (!CollectMethod.P.equals(form.getCollectMethod())) {
					errors.rejectValue("collectMethod", null, "Only allow Collect Method = Paper when Distribution Mode = Paper");
				}
				break;
			}
		}
	}

}
