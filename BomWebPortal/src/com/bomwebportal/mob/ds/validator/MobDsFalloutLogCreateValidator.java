package com.bomwebportal.mob.ds.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ds.dto.MobDsFalloutLogDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;


public class MobDsFalloutLogCreateValidator implements Validator {

	private CodeLkupService codeLkupService;


	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public boolean supports(Class arg0) {
		// TODO Auto-generated method stub
		return MobDsFalloutLogDTO.class.equals(arg0);
		
	}

	public void validate(Object arg0, Errors errors) {
		// TODO Auto-generated method stub
		MobDsFalloutLogDTO form = (MobDsFalloutLogDTO) arg0;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactName", "contactName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPhone", "contactPhone.required");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactEmail", "contactEmail.required");
		
		if (StringUtils.isBlank(form.getCallTypeCd())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "callTypeCd", "callTypeCd.required");
		} else {
			boolean foundCallType = false;
			List<CodeLkupDTO> callTypeCds = this.codeLkupService.getCodeLkupDTOALL("CALL_TYPE");
			if (callTypeCds != null) {
				for (CodeLkupDTO dto : callTypeCds) {
					if (dto.getCodeId().equals(form.getCallTypeCd())) {
						foundCallType = true;
						break;
					}
				}
			}
			if (!foundCallType) {
				errors.rejectValue("callTypeCd", "callTypeCd.required");
			}
		}
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "result", "result.required");
		if (StringUtils.isNotEmpty(form.getRemark())) {
			if (form.getRemark().length() > 4000) {
				errors.rejectValue("remark", "remark.toolong");
				//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "remark", "remark.toolong");
			}
		}
		
		
	}

}
