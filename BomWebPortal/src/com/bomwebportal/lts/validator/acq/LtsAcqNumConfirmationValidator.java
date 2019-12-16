package com.bomwebportal.lts.validator.acq;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.acq.LtsAcqNumConfirmationFormDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.validator.LtsCommonValidator;

public class LtsAcqNumConfirmationValidator implements Validator {

	private LtsCommonService ltsCommonService;
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
	
	public boolean supports(Class clazz) {
		return LtsAcqNumConfirmationFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {
		
		LtsAcqNumConfirmationFormDTO form = (LtsAcqNumConfirmationFormDTO) command;
		
		switch (form.getFormAction()) {
		case SUBMIT:
			if (StringUtils.isNotBlank(form.getSuspendRemarks())){
				if(form.getSuspendRemarks().length() > 100){
					errors.rejectValue("suspendRemarks", "lts.remarks.toolong");
				}
			}
			break;
		case SUSPEND:
			if (StringUtils.isBlank(form.getSuspendReason())
					|| StringUtils.equals(form.getSuspendReason(), "NONE")) {
				errors.rejectValue("suspendReason", "lts.reason.required");
			}
			if (StringUtils.isNotBlank(form.getSuspendRemarks())){
				if(form.getSuspendRemarks().length() > 100){
					errors.rejectValue("suspendRemarks", "lts.remarks.toolong");
				}
			}
			break;
		default:
			break;
		}
	}

}
