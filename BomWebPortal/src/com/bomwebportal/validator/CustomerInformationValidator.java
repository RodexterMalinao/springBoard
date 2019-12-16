package com.bomwebportal.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.CustomerInformationDTO;
import com.bomwebportal.util.Utility;

public class CustomerInformationValidator implements Validator {
	
	protected final Log logger = LogFactory.getLog(getClass());

	public boolean supports(Class clazz) {
		return clazz.equals(CustomerInformationDTO.class);
	}

	public void validate(Object obj, Errors errors) {
		CustomerInformationDTO dto = (CustomerInformationDTO) obj;
		
		if ("CLEAR".equalsIgnoreCase(dto.getActionType())
				|| "CREATELTS".equalsIgnoreCase(dto.getActionType())
				|| "CREATE".equalsIgnoreCase(dto.getActionType())
				) {
			return;
		}
		
		// Validation 1: No empty input
		if ("".equals(dto.getIdDocNum().trim()) 
				&& "".equals(dto.getServiceNumber().trim())
				&& "".equals(dto.getLoginIdPrefix().trim())
				&& "NONE".equals(dto.getIdDocType().trim()) 
				&& "NONE".equals(dto.getServiceNumberType().trim())
				&& "NONE".equals(dto.getLoginIdSuffix().trim())) {
			errors.rejectValue("customerInformationError", "searchKey.required");
			return;
		}
		
		// Validation 2: Only one input is allowed
		if (!("".equals(dto.getIdDocNum().trim())) && !("".equals(dto.getServiceNumber().trim())) ||
				!("".equals(dto.getIdDocNum().trim())) && !("".equals(dto.getLoginIdPrefix().trim())) ||
				!("".equals(dto.getServiceNumber().trim())) && !("".equals(dto.getLoginIdPrefix().trim()))) {
			errors.rejectValue("customerInformationError", "searchKey.invalid");
			return;
		}
		
		
		// Validation 3: 
		/*if (!"CLEAR".equalsIgnoreCase(dto.getActionType())
				&& !"CREATELTS".equalsIgnoreCase(dto.getActionType())
				&& !"DSCREATELTS".equalsIgnoreCase(dto.getActionType())
				&& !"CREATE".equalsIgnoreCase(dto.getActionType())
				&& (dto.getIsDS()!=null && dto.getIsDS())
			){
			
		}
		else */if(!"DSCREATELTS".equalsIgnoreCase(dto.getActionType()) && (dto.getIsDS()!=null&& dto.getIsDS())){
			if("NONE".equals(dto.getIdDocType().trim())){
				errors.rejectValue("idDocType", "idDocType.required");
				return;
			}
			if("".equals(dto.getIdDocNum().trim())){
				errors.rejectValue("idDocNum", "idDocNum.required");
				return;
			}	//ims ds 20151007		
		}else{		
			if (!("".equals(dto.getIdDocNum().trim())) && "NONE".equals(dto.getIdDocType().trim())) {
				errors.rejectValue("idDocType", "idDocType.required");
				return;
			} else if ("".equals(dto.getIdDocNum().trim()) && !("NONE".equals(dto.getIdDocType().trim()))) {
				errors.rejectValue("idDocNum", "idDocNum.required");
				return;
			}
		}
		
		if(!"DSCREATELTS".equalsIgnoreCase(dto.getActionType()) && (dto.getIsDS()==null || !dto.getIsDS())){
			
			if (!("".equals(dto.getServiceNumber().trim())) && "NONE".equals(dto.getServiceNumberType().trim())) {
				errors.rejectValue("serviceNumberType", "serviceNumType.required");
				return;
			}else if ("".equals(dto.getServiceNumber().trim()) && !("NONE".equals(dto.getServiceNumberType().trim()))) {
				errors.rejectValue("serviceNumber", "serviceNum.required");
				return;
			}
			
			if (!("".equals(dto.getLoginIdPrefix().trim())) && "NONE".equals(dto.getLoginIdSuffix().trim())) {
				errors.rejectValue("loginIdSuffix", "loginIdSuffix.required");
				return;
			}else if ("".equals(dto.getLoginIdPrefix().trim()) && !("NONE".equals(dto.getLoginIdSuffix().trim()))) {
				errors.rejectValue("loginIdPrefix", "loginIdPrefix.required");
				return;
			}
		}
		
		// Validation 4: HKID & BR & Passport
		if ("HKID".equalsIgnoreCase(dto.getIdDocType().trim())) {
			dto.setIdDocNum(dto.getIdDocNum().trim().toUpperCase());
		}
		
		if ("HKID".equals(dto.getIdDocType().trim()) && !"".equals(dto.getIdDocNum().trim())){
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
		} else if ("Passport".equals(dto.getIdDocType().trim()) && !"".equals(dto.getIdDocNum().trim())) {
			if (dto.getIdDocNum().length() < 6) {
				errors.rejectValue("idDocNum", "nowTV.installation.warn.013");
				return;
			} else {
				if (Utility.validatePassport(dto.getIdDocNum())==false) {
					errors.rejectValue("idDocNum", "nowTV.installation.warn.014");
					return;
				}
			}
		}
		
		// Validation 5: Searching key: MRT format
		if ("MRT".equalsIgnoreCase(dto.getServiceNumberType())) {
			if (dto.getServiceNumber() != null && !dto.getServiceNumber().isEmpty()) {
				// 1. only numeric
				if (!StringUtils.isNumeric(dto.getServiceNumber())) {
					errors.rejectValue("serviceNumber", "custInfoMRTNumeric.invalid");
					return;
				}
				// 2. 8 digits
				if (dto.getServiceNumber().length() != 8) {
					errors.rejectValue("serviceNumber", "custInfoMRTLength.invalid");
					return;
				}
				// 3. Start with 2,3,5,6,8,9
				if (!(StringUtils.startsWith(dto.getServiceNumber(), "3")
						|| StringUtils.startsWith(dto.getServiceNumber(), "5")
						|| StringUtils.startsWith(dto.getServiceNumber(), "6")
						|| StringUtils.startsWith(dto.getServiceNumber(), "8")
						|| StringUtils.startsWith(dto.getServiceNumber(), "9"))) {
					errors.rejectValue("serviceNumber", "custInfoMRTPrefix.invalid");
					return;
				}
			}
		}
	}

}
