package com.bomwebportal.offercode.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.offercode.web.OfferCodeReqController;
import com.bomwebportal.offercode.web.OfferCodeReqController.Form;
import com.bomwebportal.util.Utility;

public class OfferCodeReqFormValidator implements Validator {

	public boolean supports(Class clazz) {
		return OfferCodeReqController.Form.class.isAssignableFrom(clazz);
	}

	public void validate(Object object, Errors errors) {
		Form form = (Form)object;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobileNum", "validator.offercode.mobileNum.required", "Please input mobile number");

		if (errors.hasErrors()) return;
		
		String mrt = form.getMobileNum();
		if ( !(Utility.validatePhoneNum(mrt) && Utility.validateHKMobilePrefix(mrt) )  ) {
			errors.rejectValue("mobileNum", "validator.offercode.mobileNum.invalid", "Not a valid mobile number");
		}
		
		
	}


}
