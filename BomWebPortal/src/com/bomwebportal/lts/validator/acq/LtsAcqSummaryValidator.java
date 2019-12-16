package com.bomwebportal.lts.validator.acq;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.acq.LtsAcqSummaryFormDTO;

public class LtsAcqSummaryValidator implements Validator {

	public boolean supports(Class clazz) {
		return LtsAcqSummaryFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		
		LtsAcqSummaryFormDTO summaryFormDTO = (LtsAcqSummaryFormDTO)command;
		
		if (summaryFormDTO.getButtonPressed() == LtsAcqSummaryFormDTO.BUTTON_SIMPLE_SUSPEND) {
			if (StringUtils.isEmpty(summaryFormDTO.getSuspendReason()) || StringUtils.equals("NONE", summaryFormDTO.getSuspendReason())) {
				errors.rejectValue("suspendReason", "lts.reason.required");
			}
		}
		
		if (summaryFormDTO.getQcCallTimePeriod() != null && summaryFormDTO.getQcCallTimePeriod().length() > 200){
			errors.rejectValue("qcCallTimePeriod", "lts.remarks.toolong");
		}
		if (StringUtils.isNotBlank(summaryFormDTO.getSuspendRemarks())){
			if(summaryFormDTO.getSuspendRemarks().length() > 100){
				errors.rejectValue("suspendRemarks", "lts.remarks.toolong");
			}
		}
		
	}

}
