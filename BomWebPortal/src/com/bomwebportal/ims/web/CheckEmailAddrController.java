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

import com.bomwebportal.util.Utility;

public class CheckEmailAddrController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		JSONArray jsonArray = new JSONArray();
    	
    	String emailAddr = request.getParameter("emailAddr");
    	boolean valid = true;
    	
    	if(emailAddr != null && !("").equals(emailAddr)){
    		valid = Utility.validateEmail(emailAddr);
    	}
    	
    	jsonArray.add("{\"valid\":" + valid
    					+ "}");

    	return new ModelAndView("ajax_checkemailaddr", "jsonArray", jsonArray);
	}
}
