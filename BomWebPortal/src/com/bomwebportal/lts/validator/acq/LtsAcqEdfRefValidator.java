package com.bomwebportal.lts.validator.acq;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsEdfRefFormDTO;

public class LtsAcqEdfRefValidator implements Validator{

	public boolean supports(Class clazz) {
		return LtsEdfRefFormDTO.class.isAssignableFrom(clazz);
	}


	public void validate(Object command, Errors errors) {
		
		LtsEdfRefFormDTO form = (LtsEdfRefFormDTO)command;
		if (StringUtils.isBlank(form.getEdfRef()) || StringUtils.length(form.getEdfRef()) < 16 || StringUtils.length(form.getEdfRef()) > 20) {
			errors.rejectValue("edfRef", "lts.pcdEdfRef.required");
		}
		
	}

}
