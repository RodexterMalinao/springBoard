package com.bomwebportal.lts.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsRedirectSmsFormDTO;

public class LtsRedirectSmsValidator  implements Validator {
	private static final String USERNAME="s_eyemkt";
	private static final String PASSWORD="eyemkt12";
	
	public boolean supports(Class clazz) {
		return LtsRedirectSmsFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {

		LtsRedirectSmsFormDTO form = (LtsRedirectSmsFormDTO) command;
		if( !USERNAME.equals(form.getUsername()) ){
			errors.rejectValue("username","lts.redirectSms.username.required");
		}
		
		if( !PASSWORD.equals(form.getPassword()) ){
			errors.rejectValue("password","lts.redirectSms.password.required");
		}
		
		if( StringUtils.isBlank(form.getMsg()) ){
			errors.rejectValue("msg","lts.redirectSms.msg.required");
		}
		
		if( StringUtils.isBlank(form.getNumber()) ){
			errors.rejectValue("number","lts.redirectSms.number.required");
		}
	}
	
}
