package com.bomwebportal.ims.validator;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.ims.dto.ui.AddressInfoUI;
import com.bomwebportal.ims.service.ImsAddressInfoService;

public class ImsAddressValidator implements Validator{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean supports(Class clazz) {
		return clazz.equals(AddressInfoUI.class);
	}

    private ImsAddressInfoService service;
    
    
	public ImsAddressInfoService getService() {
		return service;
	}


	public void setService(ImsAddressInfoService service) {
		this.service = service;
	}


	/**
	 * main validationr logic
	 */
	public void validate(Object obj, Errors errors){
		
		logger.info("validate is called");
		MessageSource msg = new ClassPathXmlApplicationContext("imsReportConstant.xml");
		
		AddressInfoUI addressInfo = (AddressInfoUI)obj;
		
		if("".equals(addressInfo.getInstallAddress().getFloorNo().trim())){
			errors.rejectValue("installAddress.floorNo", "ims.pcd.addressinfo.msg.016");
		}
		
		if("".equals(addressInfo.getInstallAddress().getQuickSearch().trim())&&"".equals(addressInfo.getInstallAddress().getQuickSearchSB().trim())) {
			errors.rejectValue("installAddress.quickSearch", "ims.pcd.addressinfo.msg.015");
			errors.rejectValue("installAddress.quickSearchSB", "ims.pcd.addressinfo.msg.001");
		}
		
		String sb = addressInfo.getInstallAddress().getSerbdyno();
		String flat = addressInfo.getInstallAddress().getUnitNo().trim();
		String floor = addressInfo.getInstallAddress().getFloorNo().trim();
		String correctSB = service.getCorrectSBbyFlatFloor(sb, floor, flat);
//		sb = "test";
		logger.info("sb:"+sb+" flat:"+flat+" floor:"+floor+" correctSB:"+correctSB);
		String errorMsg = msg.getMessage("ims.pcd.addressinfo.msg.017", null, null, new Locale("en", "US"));
		
		if(!sb.equals(correctSB)){
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "commonErrorArea", null, errorMsg);
//			errors.rejectValue("commonErrorArea", "incorrectSBwithFlatFloor");
		}
	}

}
