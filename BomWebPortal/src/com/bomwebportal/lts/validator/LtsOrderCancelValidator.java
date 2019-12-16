package com.bomwebportal.lts.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsOrderCancelFormDTO;

public class LtsOrderCancelValidator implements Validator {

	public boolean supports(Class clazz) {
		return LtsOrderCancelFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsOrderCancelFormDTO form = (LtsOrderCancelFormDTO) command;
		
		if(StringUtils.isEmpty(form.getCancelReason())){
			errors.rejectValue("cancelReason", "lts.reason.required");
		}
		if(StringUtils.isEmpty(form.getCancelRemark())){
			errors.rejectValue("cancelRemark", "lts.cancelremark.required");
		}
		
	}


}
