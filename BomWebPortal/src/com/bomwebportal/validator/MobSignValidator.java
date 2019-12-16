package com.bomwebportal.validator;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.SignoffDTO;

public class MobSignValidator implements Validator{

	public boolean supports(Class clazz) {
		return clazz.equals(SignoffDTO.class);
	}

	public void validate(Object obj, Errors errors) {
		SignoffDTO signoffDTO=(SignoffDTO)obj;
		if (4==signoffDTO.getMode()&&signoffDTO.isiGuardUadInd()&&!(signoffDTO.isiGuard1() && signoffDTO.isiGuard2())){
			errors.rejectValue("signatureString", "dummy", "Select the first two i-GUARD Phone & Tablet Accidental Damage Repair Plan checkboxes");
		}
		
		if (StringUtils.isBlank(signoffDTO.getSignatureString()) || "[]".equalsIgnoreCase(signoffDTO.getSignatureString())) {
			errors.rejectValue("signatureString", "dummy", "Please sign below.");
		}
		
		if (6==signoffDTO.getMode()&&!(signoffDTO.isTravel1())){
			errors.rejectValue("signatureString", "dummy", "Select Travel Insurance checkbox");
		}
		
		if (7==signoffDTO.getMode()&&!(signoffDTO.isHelperCare1())){
			errors.rejectValue("signatureString", "dummy", "Select HKT Care 2-year Helper Insurance Coupon checkbox");
		}
		
		if (8==signoffDTO.getMode()&&!(signoffDTO.isProjectEagle())){
			errors.rejectValue("signatureString", "dummy", "Select Restart Service checkbox");
		}

	}

}
