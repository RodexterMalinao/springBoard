package com.bomwebportal.lts.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsSummaryFormDTO;

public class LtsSummaryValidator implements Validator {

	public boolean supports(Class clazz) {
		return LtsSummaryFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		
		LtsSummaryFormDTO summaryFormDTO = (LtsSummaryFormDTO)command;
		
		if (summaryFormDTO.getButtonPressed() == LtsSummaryFormDTO.BUTTON_SIMPLE_SUSPEND) {
			if (StringUtils.isEmpty(summaryFormDTO.getSuspendReason()) || StringUtils.equals("NONE", summaryFormDTO.getSuspendReason())) {
				errors.rejectValue("suspendReason", "lts.reason.required");
			}
		}
	}

}
