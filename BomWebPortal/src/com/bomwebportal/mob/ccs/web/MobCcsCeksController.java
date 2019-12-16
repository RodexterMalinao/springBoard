package com.bomwebportal.mob.ccs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.service.MobCcsCeksService;

public class MobCcsCeksController implements Controller {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobCcsCeksService mobCcsCeksService;

	public MobCcsCeksService getMobCcsCeksService() {
		return mobCcsCeksService;
	}

	public void setMobCcsCeksService(MobCcsCeksService mobCcsCeksService) {
		this.mobCcsCeksService = mobCcsCeksService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (logger.isInfoEnabled()) logger.info("start init Ceks");
		
		BomSalesUserDTO salesUser = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		if (logger.isInfoEnabled()) logger.info("sales name:" + salesUser.getUsername());
		
		String ceksUrl = mobCcsCeksService.initCeks(salesUser.getUsername(), request);
		if (logger.isInfoEnabled()) logger.info("ceks url:" + ceksUrl);
		
		request.setAttribute("ceksurl", ceksUrl);
		
		return new ModelAndView("mobccsceks");
	}
}
