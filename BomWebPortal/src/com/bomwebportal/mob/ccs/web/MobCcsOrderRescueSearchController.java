package com.bomwebportal.mob.ccs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.mob.ccs.dto.MobCcsOrderRescueDTO;
import com.bomwebportal.mob.ccs.dto.ui.OrderRescueSearchUI;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRescueService;

public class MobCcsOrderRescueSearchController extends SimpleFormController {
	private MobCcsOrderRescueService mobCcsOrderRescueService;
	
	public MobCcsOrderRescueService getMobCcsOrderRescueService() {
		return mobCcsOrderRescueService;
	}
	
	public void setMobCcsOrderRescueService(
			MobCcsOrderRescueService mobCcsOrderRescueService) {
		this.mobCcsOrderRescueService = mobCcsOrderRescueService;
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		logger.info("MobCcsOrderRescueSearchController formBackingObject called");
		
		OrderRescueSearchUI form = new OrderRescueSearchUI();
		form.setOrderId(StringUtils.trim(request.getParameter("orderId")));
		form.setIncidentNo(StringUtils.trim(request.getParameter("incidentNo")));
		form.setMsisdn(StringUtils.trim(request.getParameter("msisdn")));
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("MobCcsOrderRescueSearchController ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		String orderId = StringUtils.trim(request.getParameter("orderId"));
		String incidentNo = StringUtils.trim(request.getParameter("incidentNo"));
		String msisdn = StringUtils.trim(request.getParameter("msisdn"));
		if (StringUtils.isNotBlank(orderId) || StringUtils.isNotBlank(incidentNo) || StringUtils.isNotBlank(msisdn)) {
			List<MobCcsOrderRescueDTO> resultList = this.mobCcsOrderRescueService.getMobCcsOrderRescueDTOBySearch(orderId, incidentNo, msisdn);
			referenceData.put("resultList", resultList);
		}
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		OrderRescueSearchUI form = (OrderRescueSearchUI) command;
		form.setOrderId(StringUtils.trim(form.getOrderId()));
		form.setIncidentNo(StringUtils.trim(form.getIncidentNo()));
		form.setMsisdn(StringUtils.trim(form.getMsisdn()));
	
		ModelAndView modelAndView = new ModelAndView("mobccsorderrescuesearch", model);
		
		if (StringUtils.isNotBlank(form.getOrderId()) || StringUtils.isNotBlank(form.getIncidentNo()) || StringUtils.isNotBlank(form.getMsisdn())) {
			List<MobCcsOrderRescueDTO> resultList = this.mobCcsOrderRescueService.getMobCcsOrderRescueDTOBySearch(form.getOrderId(), form.getIncidentNo(), form.getMsisdn());
			modelAndView.addObject("resultList", resultList);
		}
		
		return modelAndView;
	}
}
