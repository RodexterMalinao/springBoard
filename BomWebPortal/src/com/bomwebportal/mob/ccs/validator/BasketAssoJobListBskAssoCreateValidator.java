package com.bomwebportal.mob.ccs.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.BasketAssoJobListDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListBskAssoEditUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListService;
import com.bomwebportal.util.Utility;

public class BasketAssoJobListBskAssoCreateValidator implements Validator {
	private MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService;

	public MobCcsBasketAssoJobListService getMobCcsBasketAssoJobListService() {
		return mobCcsBasketAssoJobListService;
	}

	public void setMobCcsBasketAssoJobListService(
			MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService) {
		this.mobCcsBasketAssoJobListService = mobCcsBasketAssoJobListService;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(BasketAssoJobListBskAssoEditUI.class);
	}

	public void validate(Object obj, Errors errors) {
		BasketAssoJobListBskAssoEditUI form = (BasketAssoJobListBskAssoEditUI) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobList", "jobList.required");
		if (StringUtils.isNotBlank(form.getJobList())) {
			BasketAssoJobListDTO jobListDto = this.mobCcsBasketAssoJobListService.getBasketAssoJobListDTOByJobList(form.getJobList());
			if (jobListDto == null) {
				errors.rejectValue("jobList", "jobList.required");
			}
		}
				
		if (form.getBasketIds() == null || form.getBasketIds().length == 0) {
			errors.rejectValue("basketIds", "basketId.required");
		}
		
		Date now = getToday();
		Date startDate = Utility.string2Date(form.getStartDate());
		Date endDate = Utility.string2Date(form.getEndDate());
		if (startDate == null || startDate.before(now)) {
			errors.rejectValue("startDate", "startDate.required");
		} else if (endDate != null && endDate.before(startDate)) {
			errors.rejectValue("endDate", "endDate.beforeStartDate");
		}
	}
	
	private Date getToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
