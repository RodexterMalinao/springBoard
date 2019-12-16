package com.bomwebportal.lts.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.SignatureDTO;
import com.bomwebportal.lts.dto.form.LtsDigitalSignatureFormDTO;

public class LtsDigitalSignatureValidator implements Validator {
	
	public boolean supports(Class clazz) {
		return LtsDigitalSignatureFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		LtsDigitalSignatureFormDTO form = (LtsDigitalSignatureFormDTO)command;
		
		List<SignatureDTO> signatureList = form.getSignatureList();
		
		if (signatureList == null || signatureList.isEmpty()) {
			return;
		}
		
		SignatureDTO signature = signatureList.get(form.getSignatureIndex());
		
		if (signature != null && StringUtils.isEmpty(signature.getSignatureString())) {
			errors.rejectValue("signatureList["+ form.getSignatureIndex() +"].signatureString", "lts.esignature.required");
		}
		
	}

}
