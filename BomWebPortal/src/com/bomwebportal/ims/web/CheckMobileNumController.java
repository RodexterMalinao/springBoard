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

public class CheckMobileNumController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		JSONArray jsonArray = new JSONArray();
    	
    	String mobileNum = request.getParameter("mobileNum");
    	String SMSNum = request.getParameter("SMS");
    	boolean valid = true;
    	
    	
    	
    	if(mobileNum != null && !("").equals(mobileNum))
    	{
	    	valid = checkPhoneNum(mobileNum);
    	}
    	else if (SMSNum != null && !("").equals(SMSNum))
    	{
	    	valid = checkPhoneNum(SMSNum);
    	}
    	else
    	{
    		valid = false;
    	}
    	
    	jsonArray.add("{\"valid\":" + valid
    					+ "}");

    	return new ModelAndView("ajax_checkmobilenum", "jsonArray", jsonArray);
	}

	private boolean checkPhoneNum(String mobileNum) {
		boolean valid;
		if(mobileNum.length()<3)
		{
			valid = false;
			return false;
		}
		if(mobileNum.substring(0, 1).equals("9") 
				|| mobileNum.substring(0, 1).equals("8")
				|| mobileNum.substring(0, 1).equals("7")
				|| mobileNum.substring(0, 1).equals("6") 
				|| mobileNum.substring(0, 1).equals("5")
				|| mobileNum.substring(0, 1).equals("4"))
		{
			valid = true;
		}else
		{
			valid = false;
		}
		
		if(mobileNum.substring(0, 3).equals("999")){
			valid = false;
		}
		logger.info(mobileNum.length());
		if(mobileNum.length()<8)
		{
			valid = false;
		}
		for(int i=0; i<mobileNum.length(); i++){
		    if(!Character.isDigit(mobileNum.charAt(i)))
		    {
		    	valid = false;
		    }
		}
		return valid;
	}
}
