package com.bomwebportal.validator;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.service.LoginService;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LoginValidator implements Validator {

	private LoginService service;

	public LoginService getService() {
		return service;
	}

	public void setService(LoginService service) {
		this.service = service;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(BomSalesUserDTO.class);
	}

	public void validate(Object obj, Errors errors) {
		BomSalesUserDTO user = (BomSalesUserDTO) obj;
		if (user == null) {
			errors.rejectValue("username", "username.required");
		} else {
			if (user.getUsername() == null || user.getUsername().trim().length() <= 0) {
				errors.rejectValue("username", "username.required");
			} else {
				if (user.getPassword() == null || user.getPassword().trim().length() <= 0) {
					errors.rejectValue("password", "password.required");
					// not use in next version 20111026
					// } else {
					// if (user.getShopCd() == null ||
					// user.getShopCd().trim().length() <= 0) {
					// errors.rejectValue("shopCd", "shopCd.required");
				} else {
					boolean result = service.validateLogin(user);
					if (!result) {
						errors.rejectValue("username", "dummy", user.getErrMsg());
					}
					if (service.getSalesAssign(user.getUsername()) == null) {
						errors.rejectValue("username", "invalid.username");
					}
				}

			}
		}
	}
}
