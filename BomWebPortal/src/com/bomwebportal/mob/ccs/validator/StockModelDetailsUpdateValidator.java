package com.bomwebportal.mob.ccs.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.StockModelDetailsDTO;

public class StockModelDetailsUpdateValidator implements Validator{

	public boolean supports(Class clazz) {
		return clazz.equals(StockModelDetailsDTO.class);
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public void validate(Object obj, Errors errors) {
		
		StockModelDetailsDTO dto = (StockModelDetailsDTO)obj;
		
		if ("UPDATE".equalsIgnoreCase(dto.getActionType())) {
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				    "model", "stockModel.required");

			if (dto.getAssignMode() == null || "NONE".equalsIgnoreCase(dto.getAssignMode())) {
				errors.rejectValue("assignMode", "stockAssignMode.required");
			}
		}

	}

}
