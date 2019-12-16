package com.bomwebportal.mob.ccs.validator;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.BasketAssoJobListBskAssoDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListBskAssoEditUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListBskAssoService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListService;
import com.bomwebportal.util.Utility;

public class BasketAssoJobListBskAssoEndValidator implements Validator {
	private MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService;
	private MobCcsBasketAssoJobListBskAssoService mobCcsBasketAssoJobListBskAssoService;
	
	public MobCcsBasketAssoJobListService getMobCcsBasketAssoJobListService() {
		return mobCcsBasketAssoJobListService;
	}

	public void setMobCcsBasketAssoJobListService(
			MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService) {
		this.mobCcsBasketAssoJobListService = mobCcsBasketAssoJobListService;
	}

	public MobCcsBasketAssoJobListBskAssoService getMobCcsBasketAssoJobListBskAssoService() {
		return mobCcsBasketAssoJobListBskAssoService;
	}

	public void setMobCcsBasketAssoJobListBskAssoService(
			MobCcsBasketAssoJobListBskAssoService mobCcsBasketAssoJobListBskAssoService) {
		this.mobCcsBasketAssoJobListBskAssoService = mobCcsBasketAssoJobListBskAssoService;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(BasketAssoJobListBskAssoEditUI.class);
	}

	public void validate(Object obj, Errors errors) {
		BasketAssoJobListBskAssoEditUI form = (BasketAssoJobListBskAssoEditUI) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobList", "jobList.required");
		if (StringUtils.isNotBlank(form.getJobList())) {
			BasketAssoJobListDTO dto = this.mobCcsBasketAssoJobListService.getBasketAssoJobListDTOByJobList(form.getJobList());
			if (dto == null) {
				errors.rejectValue("jobList", "jobList.required");
			}
		}

		if (isEmpty(form.getRowIds())) {
			errors.rejectValue("rowIds", "rowId.required");
		} else {
			Date endDate = Utility.string2Date(form.getEndDate());
			if (endDate == null) {
				errors.rejectValue("endDate", "endDate.required");
			} else {
				if (!isEmpty(form.getRowIds())) {
					for (String rowId : form.getRowIds()) {
						BasketAssoJobListBskAssoDTO dto = mobCcsBasketAssoJobListBskAssoService.getBasketAssoJobListBskAssoDTO(rowId);
						if (dto == null || endDate.before(dto.getStartDate())) {
							errors.rejectValue("endDate", "endDate.beforeStartDate");
						}
					}
				}
			}
		}
	}
	
	private <T> boolean isEmpty(T[] array) {
		return array == null || array.length == 0;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
