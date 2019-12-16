package com.bomwebportal.ims.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.util.Utility;


public class ImsAppointmentValidator implements Validator{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean supports(Class clazz) {
		return clazz.isAssignableFrom(AppointmentUI.class);
	}
	
	/**
	 * main validationr logic
	 */
	public void validate(Object obj, Errors errors){
		logger.info("ImsAppointmentValidator is called");
		
		AppointmentUI app = (AppointmentUI)obj;

		logger.info("getAdditionRemarks():" + app.getAdditionRemarks());
		if(app.getAdditionRemarks()!=null && Utility.imsIsContainCreditCardPattern(app.getAdditionRemarks())){
			errors.rejectValue("additionRemarks", "ims.pcd.imsappointment.msg.028");		
		}
	}
}
