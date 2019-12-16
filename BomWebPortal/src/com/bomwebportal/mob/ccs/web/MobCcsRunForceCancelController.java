package com.bomwebportal.mob.ccs.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.service.MobCcsForceCancelService;

public class MobCcsRunForceCancelController implements Controller {
	protected final Log logger = LogFactory.getLog(getClass());

	private MobCcsForceCancelService mobCcsForceCancelService;

	public MobCcsForceCancelService getMobCcsForceCancelService() {
		return mobCcsForceCancelService;
	}

	public void setMobCcsForceCancelService(
			MobCcsForceCancelService mobCcsForceCancelService) {
		this.mobCcsForceCancelService = mobCcsForceCancelService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("mobccsrunforcecancel");
		modelAndView.addObject("cancelFalloutDate", new Date());
		modelAndView.addObject("cancelFalloutList", mobCcsForceCancelService.forceCancelFalloutOrder());
		modelAndView.addObject("cancelDraftDate", new Date());
		modelAndView.addObject("cancelDraftList", mobCcsForceCancelService.forceCancelDraftOrder());
		modelAndView.addObject("cancelPreorderDate", new Date());
		modelAndView.addObject("cancelPreorderList", mobCcsForceCancelService.forceCancelPreorderOrder());
		return modelAndView;
	}
}
