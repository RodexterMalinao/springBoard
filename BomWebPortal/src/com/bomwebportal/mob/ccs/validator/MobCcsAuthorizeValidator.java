package com.bomwebportal.mob.ccs.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.ui.AuthorizeUI;
import com.bomwebportal.service.LoginService;

public class MobCcsAuthorizeValidator implements Validator {
	private LoginService loginService;

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public boolean supports(Class clazz) {
		return AuthorizeUI.class.equals(clazz);
	}

	public void validate(Object command, Errors errors) {
		AuthorizeUI form = (AuthorizeUI) command;
		
		if ("Y".equals(form.getByPassAuthInd())){
			return;
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "action", "dummy", "Action is required");
		
		if (!errors.hasErrors()) {
			BomSalesUserDTO user = new BomSalesUserDTO();
			user.setUsername(form.getUsername());
			user.setPassword(form.getPassword());
			if (!this.loginService.validateLogin(user)) {
				errors.rejectValue("username", "dummy", user.getErrMsg());
			}
		}
		
	}

}
