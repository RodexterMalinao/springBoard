package com.bomwebportal.ims.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.ims.dto.CustInfoDTO;

public class CustomerValidator implements Validator{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean supports(Class clazz) {
		return clazz.equals(CustInfoDTO.class);
	}
	
	/**
	 * main validationr logic
	 */
	public void validate(Object obj, Errors errors){
		
		logger.info("validate is called");
		
		CustInfoDTO cust = (CustInfoDTO)obj;
		if(cust.getCustNo()==null || cust.getCustNo().trim().length() == 0){
			errors.rejectValue("custNo", "cust.error.missing");
		}
	}

}
