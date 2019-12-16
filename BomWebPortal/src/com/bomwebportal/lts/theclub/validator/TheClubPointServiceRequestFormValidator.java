package com.bomwebportal.lts.theclub.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.theclub.dto.LtsTheClubRequestFormDTO;

public class TheClubPointServiceRequestFormValidator implements Validator {

	public boolean supports(Class arg0) {
		
		return LtsTheClubRequestFormDTO.class.equals(arg0);
	}

	public void validate(Object arg0, Errors arg1) {

	}

}
