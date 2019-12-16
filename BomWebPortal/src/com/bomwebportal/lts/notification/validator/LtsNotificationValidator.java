package com.bomwebportal.lts.notification.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.NotificationFormDTO;

public class LtsNotificationValidator  implements Validator {
	
	public boolean supports(Class clazz) {
		return NotificationFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {

		NotificationFormDTO form = (NotificationFormDTO) command;
		if(!form.isRunJob() && form.getJobName() == null && form.getMsgId() == null){
			errors.reject("lts.resendAf.resendEmail.required");
		}
	}
	
}
