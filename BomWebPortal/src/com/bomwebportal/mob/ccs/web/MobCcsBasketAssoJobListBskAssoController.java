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

import com.bomwebportal.mob.ccs.dto.BasketAssoJobListBskAssoDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListBskAssoUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListBskAssoService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListService;

public class MobCcsBasketAssoJobListBskAssoController extends SimpleFormController {

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

	public Object formBackingObject(HttpServletRequest request)
		throws ServletException {

		logger.info("MobCcsBasketAssoJobListBskAssoController formBackingObject called");
		
		BasketAssoJobListBskAssoUI form = new BasketAssoJobListBskAssoUI();
		form.setJobList(request.getParameter("jobList"));
		form.setShowEnded("true".equals(request.getParameter("showEnded")));
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsBasketAssoJobListBskAssoController ReferenceData called");

		Map<String, List<String>> referenceData = new HashMap<String, List<String>>();

		referenceData.put("jobListALL", this.mobCcsBasketAssoJobListService.getExistJobListALL());
		referenceData.put("existJobListALL", this.mobCcsBasketAssoJobListBskAssoService.getExistJobListALL());
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception {
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		BasketAssoJobListBskAssoUI form = (BasketAssoJobListBskAssoUI) command;
		
		ModelAndView modelAndView = new ModelAndView("mobccsbasketassojoblistbskasso", model);
		if (StringUtils.isNotBlank(form.getJobList())) {
			List<BasketAssoJobListBskAssoDTO> resultList;
			if (form.isShowEnded()) {
				resultList = this.mobCcsBasketAssoJobListBskAssoService.getBasketAssoJobListBskAssoDTOByJobList(form.getJobList());
			} else {
				resultList = this.mobCcsBasketAssoJobListBskAssoService.getBasketAssoJobListBskAssoInRunningDTOByJobList(form.getJobList());
			}
			modelAndView.addObject("resultList", resultList);
		}
		return modelAndView;
	}
}
