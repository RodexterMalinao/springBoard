package com.bomwebportal.mob.ccs.validator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.BasketAssoJobListDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListTeamAssoDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListTeamAssoEditUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListTeamAssoService;
import com.bomwebportal.util.Utility;

public class BasketAssoJobListTeamAssoCreateValidator implements Validator {
	private MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService;
	private MobCcsBasketAssoJobListTeamAssoService mobCcsBasketAssoJobListTeamAssoService;

	public MobCcsBasketAssoJobListService getMobCcsBasketAssoJobListService() {
		return mobCcsBasketAssoJobListService;
	}

	public void setMobCcsBasketAssoJobListService(
			MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService) {
		this.mobCcsBasketAssoJobListService = mobCcsBasketAssoJobListService;
	}
	
	public MobCcsBasketAssoJobListTeamAssoService getMobCcsBasketAssoJobListTeamAssoService() {
		return mobCcsBasketAssoJobListTeamAssoService;
	}

	public void setMobCcsBasketAssoJobListTeamAssoService(
			MobCcsBasketAssoJobListTeamAssoService mobCcsBasketAssoJobListTeamAssoService) {
		this.mobCcsBasketAssoJobListTeamAssoService = mobCcsBasketAssoJobListTeamAssoService;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(BasketAssoJobListTeamAssoEditUI.class);
	}

	public void validate(Object obj, Errors errors) {
		BasketAssoJobListTeamAssoEditUI form = (BasketAssoJobListTeamAssoEditUI) obj;
		
		if (this.isFormCompleted(form)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "jobList", "jobList.required");
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "channelCd", "channelCd.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "centreCd", "centreCd.required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "teamCd", "teamCd.required");
			
			Date now = getToday();
			Date startDate = Utility.string2Date(form.getStartDate());
			Date endDate = Utility.string2Date(form.getEndDate());
			if (startDate == null || startDate.before(now)) {
				errors.rejectValue("startDate", "startDate.required");
			} else if (endDate != null && endDate.before(startDate)) {
				errors.rejectValue("endDate", "endDate.beforeStartDate");
			}
			
			if (!errors.hasErrors()) {
				BasketAssoJobListDTO jobListDto = this.mobCcsBasketAssoJobListService.getBasketAssoJobListDTOByJobList(form.getJobList());
				if (jobListDto == null) {
					errors.rejectValue("jobList", "jobList.required");
				} else {
					BasketAssoJobListTeamAssoDTO jobListTeamAssoDto = new BasketAssoJobListTeamAssoDTO();
					jobListTeamAssoDto.setJobList(form.getJobList());
					jobListTeamAssoDto.setChannelCd(jobListDto.getChannelCd());
					jobListTeamAssoDto.setCentreCd(form.getCentreCd());
					jobListTeamAssoDto.setTeamCd(form.getTeamCd());
					jobListTeamAssoDto.setStartDate(startDate);
					jobListTeamAssoDto.setEndDate(endDate);
					List<BasketAssoJobListTeamAssoDTO> dupRecords = this.mobCcsBasketAssoJobListTeamAssoService.getBasketAssoJobListTeamAssoDTOBySearch(jobListTeamAssoDto);
					List<BasketAssoJobListTeamAssoDTO> inRangeRecords = this.mobCcsBasketAssoJobListTeamAssoService.getBasketAssoJobListTeamAssoDTOByRange(form.getJobList(), jobListDto.getChannelCd(), form.getCentreCd(), form.getTeamCd(), startDate, endDate);
					if (!this.isEmpty(dupRecords) || !this.isEmpty(inRangeRecords)) {
						errors.rejectValue("overlapRecords", "dummy", "overlapRecords.found");
						List<BasketAssoJobListTeamAssoDTO> overlapRecords = new ArrayList<BasketAssoJobListTeamAssoDTO>();
						if (!this.isEmpty(dupRecords)) {
							overlapRecords.addAll(dupRecords);
						}
						if (!this.isEmpty(inRangeRecords)) {
							for (BasketAssoJobListTeamAssoDTO taDto : inRangeRecords) {
								boolean exists = false;
								for (BasketAssoJobListTeamAssoDTO or : overlapRecords) {
									if (or.getRowId().equals(taDto.getRowId())) {
										exists = true;
										break;
									}
								}
								if (!exists) {
									overlapRecords.add(taDto);
								}
							}
						}
						Collections.sort(overlapRecords, new Comparator<BasketAssoJobListTeamAssoDTO>() {
							// sort by largest startDate, largest endDate first
							public int compare(BasketAssoJobListTeamAssoDTO o1,
									BasketAssoJobListTeamAssoDTO o2) {
								if (o1.getStartDate() == null) {
									return 1; 
								}
								if (o2.getStartDate() == null) {
									return -1;
								}
								int compare = o2.getStartDate().compareTo(o1.getStartDate());
								if (compare == 0) {
									if (o1.getEndDate() == null) {
										return 1;
									}
									if (o2.getEndDate() == null) {
										return -1;
									}
									return o2.getEndDate().compareTo(o1.getEndDate());
								} else {
									return compare;
								}
							}
						});
						form.setOverlapRecords(overlapRecords);
					}
				}
			}
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
	
	private boolean isFormCompleted(BasketAssoJobListTeamAssoEditUI form) {
		return StringUtils.isNotBlank(form.getJobList()) && StringUtils.isNotBlank(form.getCentreCd()) && StringUtils.isNotBlank(form.getTeamCd());
	}
}
