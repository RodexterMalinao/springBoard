/**
 * 
 */
package com.bomwebportal.validator;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.ApprovalLoginFormDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.service.LoginService;

// @author MAX.R.MENG LTS
public class ApprovalLoginValidator implements Validator {

	private LoginService service;

	public LoginService getService() {
		return service;
	}

	public void setService(LoginService service) {
		this.service = service;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(ApprovalLoginFormDTO.class);
	}

	public void validate(Object obj, Errors errors) {
		ApprovalLoginFormDTO user = (ApprovalLoginFormDTO) obj;
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
					BomSalesUserDTO userValidate = new BomSalesUserDTO();
					userValidate.setUsername(user.getUsername());
					userValidate.setPassword(user.getPassword());
					boolean result = service.validateLogin(userValidate);
					if (!result) {
						errors.rejectValue("username", "invalid.username.error");
					}
					if (service.getSalesAssign(user.getUsername()) == null) {
						errors.rejectValue("username", "invalid.username");
					}else if(!StringUtils.equals(service.getSalesAssign(user.getUsername()).getCategory(), "MANAGER")
							&& !StringUtils.equals(service.getSalesAssign(user.getUsername()).getCategory(), "SALES MANAGER")){
						errors.rejectValue("approvalInd", "authentication.failed");
					}
				}

			}
		}
	}
}

