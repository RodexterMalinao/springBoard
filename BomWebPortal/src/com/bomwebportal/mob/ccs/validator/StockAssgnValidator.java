package com.bomwebportal.mob.ccs.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;

public class StockAssgnValidator implements Validator{

	public boolean supports(Class clazz) {
		return clazz.equals(StockAssgnDTO.class);
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public void validate(Object obj, Errors errors) {
		
		StockAssgnDTO dto = (StockAssgnDTO)obj;
		
//		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
//			    "itemCode", "stockItemCode.required");
//		
//		if (dto.getLocation() == null || "NONE".equalsIgnoreCase(dto.getLocation())) {
//			errors.rejectValue("location", "stockLocation.required");
//		}

	}

}
