package com.bomwebportal.mob.ccs.validator;

import java.util.Date;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.BasketAssoJobListTeamAssoDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListTeamAssoEditUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListTeamAssoService;
import com.bomwebportal.util.Utility;

public class BasketAssoJobListTeamAssoEditValidator implements Validator {
	private MobCcsBasketAssoJobListTeamAssoService mobCcsBasketAssoJobListTeamAssoService;

	public MobCcsBasketAssoJobListTeamAssoService getMobCcsBasketAssoJobListTeamAssoService() {
		return mobCcsBasketAssoJobListTeamAssoService;
	}

	public void setMobCcsBasketAssoJobListTeamAssoService(
			MobCcsBasketAssoJobListTeamAssoService mobCcsBasketAssoJobListTeamAssoService) {
		this.mobCcsBasketAssoJobListTeamAssoService = mobCcsBasketAssoJobListTeamAssoService;
	}

	public boolean supports(Class clazz) {
		return BasketAssoJobListTeamAssoEditUI.class.equals(clazz);
	}

	public void validate(Object command, Errors errors) {
		BasketAssoJobListTeamAssoEditUI form = (BasketAssoJobListTeamAssoEditUI) command;
		BasketAssoJobListTeamAssoDTO dto = mobCcsBasketAssoJobListTeamAssoService.getBasketAssoJobListTeamAssoDTO(form.getRowId());
		Date endDate = Utility.string2Date(form.getEndDate());
		if (endDate == null || endDate.before(dto.getStartDate())) {
			errors.rejectValue("endDate", "endDate.beforeStartDate");
		}
	}
}
