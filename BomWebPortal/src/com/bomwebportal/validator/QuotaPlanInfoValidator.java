package com.bomwebportal.validator;


import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.QuotaPlanInfoUI;

public class QuotaPlanInfoValidator implements Validator{

	public boolean supports(Class clazz) {
		
		return clazz.equals(QuotaPlanInfoUI.class);
	}


	public void validate(Object obj, Errors errors) {
		 QuotaPlanInfoUI quotaPlanInfoUI=(QuotaPlanInfoUI)obj;
		 
		 if("Y".equals(quotaPlanInfoUI.getIotPlan())){
			 
		 }else{
		 
			 if (StringUtils.isNotBlank(quotaPlanInfoUI.getAutoTopUpInd())&& quotaPlanInfoUI.getAutoTopUpInd().equalsIgnoreCase("Y")){
				 
				 if (StringUtils.isBlank(quotaPlanInfoUI.getSelectedQuotaPlanOption())){
					 errors.rejectValue("selectedQuotaPlanOption", "dummy",
								"Please select a top-up option.");
				 }
				 
				 if (StringUtils.isBlank(quotaPlanInfoUI.getMaxTopUpTimes())){
					 errors.rejectValue("selectedQuotaPlanOption", "dummy",
								"Please input maximum top up times.");
				 }else if (!StringUtils.isNumeric(quotaPlanInfoUI.getMaxTopUpTimes())){
					 errors.rejectValue("selectedQuotaPlanOption", "dummy",
								"Please input a number for maximum top up times.");
				 }else if (Integer.parseInt(quotaPlanInfoUI.getMaxTopUpTimes())<=0 || Integer.parseInt(quotaPlanInfoUI.getMaxTopUpTimes())>99){
					 errors.rejectValue("selectedQuotaPlanOption", "dummy",
								"Please input maximum top up times between 1 to 99.");
				 }
			 }
			
		}
	}
	
	

}
