package com.bomwebportal.lts.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.ims.service.ImsCeksService;

public class LtsCeksController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	private ImsCeksService ceksService;
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("start init Ceks");
		System.out.println("start init Ceks");
		
		BomSalesUserDTO salesUser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		logger.info("sales name:"+salesUser.getUsername());
		System.out.println("sales name:"+salesUser.getUsername());
		String ceksUrl = ceksService.initCeks(salesUser.getUsername());
		logger.info("ceks url:"+ceksUrl);
		System.out.println("ceks url:"+ceksUrl);
		request.getSession().setAttribute("ceksurl", ceksUrl);
		
		return new ModelAndView("ltsceks");
	}

	public ImsCeksService getCeksService() {
		return ceksService;
	}

	public void setCeksService(ImsCeksService ceksService) {
		this.ceksService = ceksService;
	}

}
