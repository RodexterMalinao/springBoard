package com.bomwebportal.sbs.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.sbs.dto.StOrderPosSmDTO;

public class StOrderPosSmValidator implements Validator {

	public boolean supports(Class clazz) {
		return StOrderPosSmDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		StOrderPosSmDTO dto = (StOrderPosSmDTO)obj;
		ValidationUtils.rejectIfEmpty(errors, "smNum", "sbs.smNum.required", "POS SM Num is required");
		ValidationUtils.rejectIfEmpty(errors, "smTypeDesc", "sbs.smTypeDesc.required", "POS SM Type is required");
		//ValidationUtils.rejectIfEmpty(errors, "remark", "sbs.smRemark.required", "POS SM Remark is required");

		if (StringUtils.isNotEmpty(dto.getSmNum()) && dto.getSmNum().length() != 13) {
			errors.rejectValue("smNum", "sbs.smNum.invalid", "POS SM Num should be 13 characters");
		}
	}

}
