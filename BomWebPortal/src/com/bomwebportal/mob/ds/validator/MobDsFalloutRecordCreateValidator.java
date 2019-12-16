package com.bomwebportal.mob.ds.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ds.dto.MobDsFalloutRecordDTO;

public class MobDsFalloutRecordCreateValidator implements Validator{

	public boolean supports(Class arg0) {
		// TODO Auto-generated method stub
		return MobDsFalloutRecordDTO.class.equals(arg0);
	}

	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		
		MobDsFalloutRecordDTO form = (MobDsFalloutRecordDTO) arg0;
		
		if (StringUtils.isNotEmpty(form.getRemark())) {
			if (form.getRemark().length() > 4000) {
				errors.rejectValue("remark", "remark.toolong");
				//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "remark", "remark.toolong");
			}
		}
	}

}
