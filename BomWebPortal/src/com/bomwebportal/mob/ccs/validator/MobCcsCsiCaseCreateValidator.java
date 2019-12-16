package com.bomwebportal.mob.ccs.validator;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.CsiCaseDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.util.Utility;

public class MobCcsCsiCaseCreateValidator implements Validator {
	private CodeLkupService codeLkupService;

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public boolean supports(Class clazz) {
		return CsiCaseDTO.class.equals(clazz);
	}

	public void validate(Object obj, Errors errors) {
		CsiCaseDTO form = (CsiCaseDTO) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "falloutReportDateStr", "falloutReportDateStr.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "falloutReportTSlot", "falloutReportTSlot.required");
		
		if (! StringUtils.isBlank(form.getNextCallSchDateStr())){
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nextCallSchDateStr", "nextCallSchDateStr.required");
			if (StringUtils.isBlank(form.getNextCallSchTime())){
				ValidationUtils.rejectIfEmptyOrWhitespace(errors,"nextCallSchTime", "nextCallSchTime.required");
			}else{
			
				if (form.getCaseStatus().equalsIgnoreCase("CS01")){ //Open
					Calendar currentDate = Calendar.getInstance();
					Calendar compareDate = Calendar.getInstance();
					compareDate.setTime(Utility.string2DateWithTime(form.getNextCallSchDateStr() + " " + form.getNextCallSchTime()));
					if (compareDate.before(currentDate)) {
						errors.rejectValue("nextCallSchTime", "nextCallSchDateStr.backdate");
					}
				}
			}
		}
		
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPhone", "contactPhone.required");
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
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resultCd", "result.required");
		if (StringUtils.isNotEmpty(form.getRemark())) {
			if (form.getRemark().length() > 4000) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "remark", "remark.toolong");
			}
		}*/
	}

}
