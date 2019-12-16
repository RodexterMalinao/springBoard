package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.mob.ccs.dto.CsiSmsLogDTO;
import com.bomwebportal.mob.ccs.service.MobCcsCsiCaseService;
import com.bomwebportal.mob.ccs.service.MobCcsCsiSmsLogService;
import com.bomwebportal.util.Utility;

public class MobCcsCsiSmsLogCreateController extends SimpleFormController {
	private MobCcsCsiSmsLogService mobCcsCsiSmsLogService;
	private MobCcsCsiCaseService mobCcsCsiCaseService;
	
	public MobCcsCsiSmsLogService getMobCcsCsiSmsLogService() {
		return mobCcsCsiSmsLogService;
	}

	public void setMobCcsCsiSmsLogService(
			MobCcsCsiSmsLogService mobCcsCsiSmsLogService) {
		this.mobCcsCsiSmsLogService = mobCcsCsiSmsLogService;
	}
	
	public MobCcsCsiCaseService getMobCcsCsiCaseService() {
		return mobCcsCsiCaseService;
	}

	public void setMobCcsCsiCaseService(MobCcsCsiCaseService mobCcsCsiCaseService) {
		this.mobCcsCsiCaseService = mobCcsCsiCaseService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		CsiSmsLogDTO form = new CsiSmsLogDTO();
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		String caseNo = ServletRequestUtils.getRequiredStringParameter(request, "caseNo");
		if (StringUtils.isNotBlank(caseNo)) {
			form.setCaseNo(caseNo);
			form.setOrderId(orderId);
		}
		
		Date defaultDateNTime = new Date();
		String dateNTime = Utility.date2String(defaultDateNTime, "dd/MM/yyyy HH:mm");
		form.setSmsRecordDateStr(dateNTime.substring(0, 10));
		form.setSmsRecordTimeHour(dateNTime.substring(11, 13));
		form.setSmsRecordTimeMin(dateNTime.substring(14));
		
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("MobCcsCsiSmsLogCreateController ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		List<String> hourList = new ArrayList<String>();
		List<String> minList = new ArrayList<String>();
		
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				hourList.add("0" + String.valueOf(i));
			} else {
				hourList.add(String.valueOf(i));
			}
		}
		
		for (int i = 0; i < 60; i++) {
			if (i < 10) {
				minList.add("0" + String.valueOf(i));
			} else {
				minList.add(String.valueOf(i));
			}
		}
		
		referenceData.put("hourList", hourList);
		referenceData.put("minList", minList);

		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		CsiSmsLogDTO form = (CsiSmsLogDTO) command;
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccscsicasecreate.html"));
		modelAndView.addObject("orderId", form.getOrderId());
		modelAndView.addObject("caseNo", form.getCaseNo());
		
		Date now = new Date();
		CsiSmsLogDTO dto = new CsiSmsLogDTO();
		dto.setCaseNo(StringUtils.trim(form.getCaseNo()));
		dto.setOrderId(StringUtils.trim(form.getOrderId()));
		dto.setSmsRecordDateStr(StringUtils.trim(form.getSmsRecordDateStr()));
		dto.setSmsRecordTimeHour(StringUtils.trim(form.getSmsRecordTimeHour()));
		dto.setSmsRecordTimeMin(StringUtils.trim(form.getSmsRecordTimeMin()));
		mobCcsCsiSmsLogService.insertCsiSmsLogDTO(dto);
		mobCcsCsiCaseService.updateSmsCount(StringUtils.trim(form.getCaseNo()));
		
		modelAndView.addObject("recordCreated", true);
		
		return modelAndView;
	}
}
