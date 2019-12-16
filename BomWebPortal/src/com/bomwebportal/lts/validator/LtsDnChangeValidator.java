package com.bomwebportal.lts.validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.lts.dto.form.LtsDnChangeFormDTO;
import com.bomwebportal.lts.dto.form.LtsDnChangeFormDTO.Action;
import com.bomwebportal.lts.dto.form.LtsDnChangeFormDTO.Selection;
import com.bomwebportal.lts.dto.form.LtsDnChangeFormDTO.Method;



public class LtsDnChangeValidator implements Validator {

	public boolean supports(Class clazz) {
		return LtsDnChangeFormDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object command, Errors errors) {

		LtsDnChangeFormDTO form = (LtsDnChangeFormDTO) command;
		Selection selection = form.getNumSelection();
		Action action = form.getFormAction();
		
		switch (selection) {
		case USE_NEW_DN:			
			switch (action) {
			case SEARCH_BY_FIRST_8_DIGITS:
				if (StringUtils.isBlank(form.getFirst5To8Digits())||form.getFirst5To8Digits().length()<5) {
					errors.rejectValue("searchErrMsg", "lts.acq.first8digits.required");	
				}
			break;			
			case SEARCH_BY_LAST_3_DIGITS:
				if (StringUtils.isBlank(form.getLast1To3Digits())) {
					errors.rejectValue("searchErrMsg", "lts.acq.last3digits.required");	
				}
			break;
			case LOCK_NUMBER:
				if (form.getNumSelectionStringList()!=null && form.getNumSelectionStringList().size()>0 
						&& form.getNumSelectionList().size()==0) {					
					errors.rejectValue("lockNumErrMsg", "lts.acq.lockNum.invalid");
				} else if ((form.getNumSelectionStringList()!=null && form.getNumSelectionStringList().size()>3) 
						|| (form.getNumSelectionStringList()!=null && form.getReservedDnList()!=null
								&& (form.getNumSelectionStringList().size()+form.getReservedDnList().size())>3)) {
					errors.rejectValue("lockNumErrMsg", "lts.acq.lockNum.error");	
				}
			break;
			case SUBMIT:
				if (form.getSearchMethod()!=Method.RESERVED_DN && !form.isDuplexProfile()
						&& (form.getReservedDnList()==null || form.getReservedDnList().size()==0)) {
					errors.rejectValue("lockNumErrMsg", "lts.acq.lockNum.required");	
				}
								
			break;
	        }		
		break;	
		case KEEP_EXIST_DN:
			if(!form.isDuplexProfile()){
				errors.rejectValue("keepDNErrMsg", "lts.acq.lockNum.required");
			}
			break;
		}
	}

}
