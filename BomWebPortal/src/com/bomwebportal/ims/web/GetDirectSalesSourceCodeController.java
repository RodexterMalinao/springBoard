package com.bomwebportal.ims.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


import com.bomwebportal.ims.service.ImsPaymentService;



public class GetDirectSalesSourceCodeController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsPaymentService imsPaymentService;



	public ImsPaymentService getImsPaymentService() {
		return imsPaymentService;
	}

	public void setImsPaymentService(ImsPaymentService imsPaymentService) {
		this.imsPaymentService = imsPaymentService;
	}
	

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String sourceCode= null;
		String staffId = request.getParameter("staffid");
		
		sourceCode = imsPaymentService.getDeflaultSourceCode(staffId);
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sourceCode", sourceCode);
		
		return new ModelAndView("ajax_getdirectsalessourcecode",jsonObject);
	}
}
