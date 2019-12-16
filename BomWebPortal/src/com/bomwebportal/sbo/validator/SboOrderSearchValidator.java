package com.bomwebportal.sbo.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.sbo.dto.form.SboOrderSearchForm;
import com.bomwebportal.util.Utility;

public class SboOrderSearchValidator implements Validator {
	
	protected final Log logger = LogFactory.getLog(getClass());

	public boolean supports(Class clazz) {
		return clazz.equals(SboOrderSearchForm.class);
	}

	public void validate(Object obj, Errors errors) {
		SboOrderSearchForm dto = (SboOrderSearchForm) obj;
		if ("CLEAR".equalsIgnoreCase(dto.getAction())) {
			return;
		}
		
		// Validation 1: No empty input
		if (StringUtils.isEmpty(dto.getIdDocNum()) 
				&& StringUtils.isEmpty(dto.getServiceNumber())
				&& StringUtils.isEmpty(dto.getLoginIdPrefix())
				&& StringUtils.isEmpty(dto.getIdDocType())
				&& StringUtils.isEmpty(dto.getIdDocNum()) 
				&& StringUtils.isEmpty(dto.getServiceNumberType())
				&& StringUtils.isEmpty(dto.getLoginIdSuffix())
				&& StringUtils.isEmpty(dto.getContactEmail())
				&& StringUtils.isEmpty(dto.getOrderId())
				&& StringUtils.isEmpty(dto.getContactNum())
				) {
			errors.reject("sboSearchKey.required");
			return;
		}
		

		
		if (StringUtils.isNotEmpty(dto.getIdDocNum())
				&& StringUtils.isEmpty(dto.getIdDocType())) {
			errors.rejectValue("idDocType", "idDocType.required");
			return;
		} else if (StringUtils.isEmpty(dto.getIdDocNum())
				&& StringUtils.isNotEmpty(dto.getIdDocType())) {
			errors.rejectValue("idDocNum", "idDocNum.required");
			return;
		} else if (StringUtils.isBlank(dto.getIdDocNum())) {
			if (dto.getIdDocNum()!=null && dto.getIdDocNum().length()>0) {
				errors.rejectValue("idDocNum", "idDocNum.required");
				return;
			}
		}

		if (StringUtils.isNotEmpty(dto.getServiceNumber())
				&& StringUtils.isEmpty(dto.getServiceNumberType())) {
			errors.rejectValue("serviceNumberType", "serviceNumType.required");
			return;
		} else if (StringUtils.isEmpty(dto.getServiceNumber())
				&& StringUtils.isNotEmpty(dto.getServiceNumberType())) {
			errors.rejectValue("serviceNumber", "serviceNum.required");
			return;
		} else if (StringUtils.isBlank(dto.getServiceNumber())) {
			if (dto.getServiceNumber()!=null && dto.getServiceNumber().length()>0) {
				errors.rejectValue("serviceNumber", "serviceNum.required");
				return;
			}
		}
		
		
		
		if (StringUtils.isNotEmpty(dto.getLoginIdPrefix())
				&& StringUtils.isEmpty(dto.getLoginIdSuffix())) {
			errors.rejectValue("loginIdSuffix", "loginIdSuffix.required");
			return;
		}else if (StringUtils.isEmpty(dto.getLoginIdPrefix())
				&& StringUtils.isNotEmpty(dto.getLoginIdSuffix())) {
			errors.rejectValue("loginIdPrefix", "loginIdPrefix.required");
			return;
		} else if (StringUtils.isBlank(dto.getLoginIdPrefix())) {
			if (dto.getLoginIdPrefix()!=null && dto.getLoginIdPrefix().length()>0) {
				errors.rejectValue("loginIdPrefix", "loginIdPrefix.required");
				return;
			}
		}
		
		// Validation 4: HKID & BR
		if ("HKID".equalsIgnoreCase(dto.getIdDocType().trim())) {
			dto.setIdDocNum(dto.getIdDocNum().trim().toUpperCase());
		}
		
		if ("HKID".equals(dto.getIdDocType().trim())
				&& StringUtils.isNotEmpty(dto.getIdDocNum())){
			if (Utility.validateHKID(dto.getIdDocNum().trim())==false){
				errors.rejectValue("idDocNum", "invalid.hkid");	
				return;
			  } else {
					if (Utility.validateHKIDcheckDigit(dto.getIdDocNum().trim())==false){
						errors.rejectValue("idDocNum", "invalid.hkidCheckDigit");
						return;
					}					
			  }
		} else if ("BR".equals(dto.getIdDocType().trim()) && !"".equals(dto.getIdDocNum().trim())){
			if (Utility.validateHKBR(dto.getIdDocNum().trim())==false){
				errors.rejectValue("idDocNum", "invalid.hkbr");	
				return;
			  } else {
					if (Utility.validateHKBRcheckDigit(dto.getIdDocNum().trim())==false){
						errors.rejectValue("idDocNum", "invalid.hkbrCheckDigit");
						return;
					 }				  					
			  }
		}
		
		// Validation 5: Searching key: MRT format
		if ("MRT".equalsIgnoreCase(dto.getServiceNumberType())) {
			if (dto.getServiceNumber() != null && !dto.getServiceNumber().isEmpty()) {
				if (!Utility.validatePhoneNum(dto.getServiceNumber())) {
					errors.rejectValue("serviceNumber", "custInfoMRTNumeric.invalid");
					return;
				}
				if (!Utility.validateHKPhonePreFix(dto.getServiceNumber())) {
					errors.rejectValue("serviceNumber", "custInfoMRTPrefix.invalid");
					return;
				}
			}
		}
		
		/*
		if (StringUtils.isNotEmpty(dto.getOrderId())) {
			dto.setOrderId(dto.getOrderId().toUpperCase());
			if (!(dto.getOrderId().matches("^[A-Z]SB[OS][A-Z][0-9]{6}$") || dto.getOrderId().matches("^[A-Z]SB[OS][A-Z][0-9]{8}$"))) {
				errors.rejectValue("orderId", "sboSearchOrderId.pattern");
			}
		}
		*/
	}

}
