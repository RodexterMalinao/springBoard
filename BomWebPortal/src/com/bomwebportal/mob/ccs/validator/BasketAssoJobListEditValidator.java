package com.bomwebportal.mob.ccs.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.BasketAssoJobListDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListCreateUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListService;

public class BasketAssoJobListEditValidator implements Validator {
	private MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService;

	public MobCcsBasketAssoJobListService getMobCcsBasketAssoJobListService() {
		return mobCcsBasketAssoJobListService;
	}

	public void setMobCcsBasketAssoJobListService(
			MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService) {
		this.mobCcsBasketAssoJobListService = mobCcsBasketAssoJobListService;
	}

	
	public boolean supports(Class clazz) {
		return clazz.equals(BasketAssoJobListCreateUI.class);
	}

	public void validate(Object obj, Errors errors) {
		BasketAssoJobListCreateUI form = (BasketAssoJobListCreateUI) obj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rowId", "rowId.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobListDesc", "jobListDesc.required");
		
		BasketAssoJobListDTO dto = mobCcsBasketAssoJobListService.getBasketAssoJobListDTO(form.getRowId());
		
		if (dto == null) {
			errors.rejectValue("rowId", "rowId.required");
		}
	}
}