package com.bomwebportal.ims.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class CheckCardHolderNameController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String cardholdername = request.getParameter("cardholdername");
		String registerName = request.getParameter("registerName");
		
		JSONArray jsonArray = new JSONArray();
		
		boolean same = true;
		
		if(cardholdername != null && !("").equals(cardholdername)){
			if(cardholdername.equals(registerName)){
				same = true;
			}else{
				same = false;
			}
		}

		jsonArray.add("{\"same\":" + same
					  + "}");
		
		logger.info(jsonArray);
		
		return new ModelAndView("ajax_checkcardholdername", "jsonArray", jsonArray);
	}

}
