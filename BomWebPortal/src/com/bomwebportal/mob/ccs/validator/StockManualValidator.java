package com.bomwebportal.mob.ccs.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.ui.StockManualUI;

public class StockManualValidator implements Validator{

	public boolean supports(Class clazz) {
		return clazz.equals(StockManualUI.class);
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public void validate(Object obj, Errors errors) {
		
		StockManualUI dto = (StockManualUI)obj;
		
	}

}
