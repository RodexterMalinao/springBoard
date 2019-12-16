package com.bomltsportal.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomltsportal.dto.form.ApplicantInfoFormDTO;

public class ApplicantInfoValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return ApplicantInfoFormDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object command, Errors errors) {
		ApplicantInfoFormDTO form = (ApplicantInfoFormDTO) command;
	}

}
