package com.bomwebportal.mob.ccs.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.ui.MRTReserveUI;

public class MRTReserveValidator implements Validator{
	
	public boolean supports(Class clazz) {
		return clazz.equals(MRTReserveUI.class);
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public void validate(Object obj, Errors errors) {
		
		logger.debug("MRTReserveValidator is call");
		
		MRTReserveUI dto = (MRTReserveUI)obj;

		if (dto.getMrtPool() != null && "NONE".equalsIgnoreCase(dto.getMrtPool())) {
			errors.rejectValue("mrtPool", "mrtReserve.mrtPool.required");
			logger.debug("MRTReserveValidator - missing MRT Pool");
		}
	}

}
