package com.bomwebportal.lts.validator.acq;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO.BaCaActionType;
import com.bomwebportal.lts.validator.LtsCommonValidator;

public class LtsAcqAddressRolloutValidator implements Validator {
	
	public boolean supports(Class clazz) {
		return LtsAddressRolloutFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {

		LtsAddressRolloutFormDTO form = (LtsAddressRolloutFormDTO) command;
		
		if (StringUtils.isBlank(form.getServiceBoundary()) || StringUtils.isBlank(form.getQuickSearch())) {
			errors.rejectValue("quickSearch", "lts.address.required");	
		}
		else if (StringUtils.isBlank(form.getFloor())) {
			errors.rejectValue("floor", "lts.floor.required");
		}
		
	}

}
