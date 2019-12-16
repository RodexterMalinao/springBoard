package com.bomwebportal.lts.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO.BaCaActionType;

public class LtsAddressRolloutValidator implements Validator {
	
	public boolean supports(Class clazz) {
		return LtsAddressRolloutFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {

		LtsAddressRolloutFormDTO form = (LtsAddressRolloutFormDTO) command;
		
		if (form.isExternalRelocate()) {
			if (StringUtils.isBlank(form.getServiceBoundary()) || StringUtils.isBlank(form.getQuickSearch())) {
				errors.rejectValue("quickSearch", "lts.address.required");	
			}
			else if (StringUtils.isBlank(form.getFloor())) {
				errors.rejectValue("floor", "lts.floor.required");
			}
			if(form.getBaCaAction() == null){
				errors.rejectValue("baCaAction", "lts.secondDelBaCaChange.required");	
			}
			if(form.getBaCaAction() == BaCaActionType.DIFFERENT_FROM_NEW_OLD_IA){
				if (StringUtils.isBlank(form.getBillingAddress())) {
					errors.rejectValue("billingAddress", "lts.billingAddress.required");	
				}else{
					form.setBillingAddress(LtsCommonValidator.removeBaUnwantedBlanks(form.getBillingAddress()));
					if(!LtsCommonValidator.isValidBaFormat(form.getBillingAddress())){
						errors.rejectValue("billingAddress", "lts.invalid.billingAddress");	
					}
				}
			}
		}
		else {
			if (form.isPonSbRequired() && StringUtils.isBlank(form.getPonSbNum())) {
				errors.rejectValue("pobSbNum", "lts.ponSbNum.required");
			}	
		}
		
		
	}

}
