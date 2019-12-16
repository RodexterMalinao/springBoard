package com.bomwebportal.ims.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.ims.service.ImsCeksService;

public class ImsCeksController implements Controller{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsCeksService ceksService;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("start init Ceks");
		
		BomSalesUserDTO salesUser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		logger.info("sales name:"+salesUser.getUsername());
		String ceksUrl = ceksService.initCeks(salesUser.getUsername());
		logger.info("ceks url:"+ceksUrl);
		request.setAttribute("ceksurl", ceksUrl);
		
		return new ModelAndView("imsceks");
	}

	public ImsCeksService getCeksService() {
		return ceksService;
	}

	public void setCeksService(ImsCeksService ceksService) {
		this.ceksService = ceksService;
	}	

}
