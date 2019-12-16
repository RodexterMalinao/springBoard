package com.bomwebportal.lts.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsMiscellaneousFormDTO;

public class LtsMiscellaneousValidator implements Validator{

	public boolean supports(Class clazz) {
		return LtsMiscellaneousFormDTO.class.isAssignableFrom(clazz);
	}
	
	public void validate(Object command, Errors errors) {
		
		LtsMiscellaneousFormDTO form = (LtsMiscellaneousFormDTO)command;
		
		
		if (form.isBackDateOrder() && form.isRecontract()) {
			errors.rejectValue("backDate", "lts.backDate.recontract.not.allow");
			errors.rejectValue("recontract", "lts.backDate.recontract.not.allow");
		}
		
		if(form.isBackDateOrder() && form.isDnChange()){
			errors.rejectValue("dnChange", "lts.backDate.dnchange.not.allow");
		}
		
		if (form.isBackDateOrder()) {
			if (StringUtils.isEmpty(form.getBackDate())) {
				errors.rejectValue("backDate", "lts.backDate.required");
			}
		}
		
		if (form.isSubmitDisForm()) {
			if (StringUtils.isEmpty(form.getDisFormSerial())
					&& StringUtils.isEmpty(form.getWaiveDisFormReason())) {
				errors.rejectValue("disFormSerial", "lts.dis.dFormInfo.required");
			}
			
			if (StringUtils.isNotEmpty(form.getDisFormSerial())
					&& StringUtils.isNotEmpty(form.getWaiveDisFormReason())) {
				errors.rejectValue("disFormSerial", "lts.dis.dFormInfo.invalid");
			}
		}
	}
	
}
