package com.bomwebportal.lts.validator.disconnect;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateBillingInfoFormDTO;
import com.bomwebportal.lts.validator.LtsCommonValidator;

public class LtsTerminateBillingInfoValidator implements Validator {

	public boolean supports(Class clazz) {
		return LtsTerminateBillingInfoFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsTerminateBillingInfoFormDTO form = (LtsTerminateBillingInfoFormDTO)command;
		
		if(!form.isChangeBa()) {
			return;
		}
		
		if (StringUtils.isBlank(form.getNewBillingAddress())) {
			errors.rejectValue("newBillingAddress", "lts.billingAddress.required");	
		}
		else{
			form.setNewBillingAddress(LtsCommonValidator.removeBaUnwantedBlanks(form.getNewBillingAddress()));
			if(!LtsCommonValidator.isValidBaFormat(form.getNewBillingAddress())){
				errors.rejectValue("newBillingAddress", "lts.invalid.billingAddress");	
			}else if(LtsCommonValidator.containsSpecialChar(form.getNewBillingAddress())){
				errors.rejectValue("newBillingAddress", "lts.invalid.billingAddress.specialChar");	
			}
		}
		
		if(StringUtils.isNotBlank(form.getNewBillingName())){
			form.setNewBillingName(LtsCommonValidator.removeTabs(form.getNewBillingName()));
			if(form.getNewBillingName().length() > 40){
				errors.rejectValue("newBillingName", "lts.dis.billingname.toolong");	
			}
			if (StringUtils.equalsIgnoreCase(form.getExistingBillingName(), form.getNewBillingName())) {
				errors.rejectValue("newBillingName", "lts.dis.billingname.same.as.existing");
			}
		}
		else if (LtsTerminateBillingInfoFormDTO.CHANGE_BILLING_NAME_IND_FORCE.equals(form.getChangeBillingNameInd())){
			errors.rejectValue("newBillingName", "lts.billingName.required");
			
		}
		
	}

}
