package com.bomwebportal.lts.validator.acq;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.acq.LtsAcqBillingAddressFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillingAddressFormDTO.BillingAddrDtl;
import com.bomwebportal.lts.validator.LtsCommonValidator;

public class LtsAcqBillingAddressValidator implements Validator {

	public boolean supports(Class clazz) {
		return LtsAcqBillingAddressFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsAcqBillingAddressFormDTO form = (LtsAcqBillingAddressFormDTO) command;
		
		for(int i = 0; i < form.getBillingAddrDtlList().size(); i++){
			BillingAddrDtl billAddrDtl = form.getBillingAddrDtlList().get(i);
			
			String path = "billingAddrDtlList["+i+"].";
			
			if(StringUtils.isBlank(billAddrDtl.getBillingAddressTextArea())){
				errors.rejectValue(path+"billingAddressTextArea", "lts.billingAddress.required");	
			}else{
				billAddrDtl.setBillingAddressTextArea(LtsCommonValidator.removeBaUnwantedBlanks(billAddrDtl.getBillingAddressTextArea()));
				if(!LtsCommonValidator.isValidBaFormat(billAddrDtl.getBillingAddressTextArea())){
					errors.rejectValue(path+"billingAddressTextArea", "lts.invalid.billingAddress");	
				}else if(LtsCommonValidator.containsSpecialChar(billAddrDtl.getBillingAddressTextArea())){
					errors.rejectValue(path+"billingAddressTextArea", "lts.invalid.billingAddress.specialChar");	
				}
			}
		}

	}

}
