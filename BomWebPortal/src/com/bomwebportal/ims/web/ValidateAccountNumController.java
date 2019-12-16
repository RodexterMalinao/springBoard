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

import com.bomwebportal.ims.service.ValidateAccountNumService;

public class ValidateAccountNumController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ValidateAccountNumService validateAccountNumService;
	
	public ValidateAccountNumService getValidateAccountNumService() {
		return validateAccountNumService;
	}

	public void setValidateAccountNumService(
			ValidateAccountNumService validateAccountNumService) {
		this.validateAccountNumService = validateAccountNumService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("pregenAcc") != null
				&& !("").equals((String)request.getParameter("pregenAcc"))){
			char theChar;
			boolean valid = true;
			int result = -1;
			String errorText = null;
			JSONArray jsonArray = new JSONArray();
			
			for(int idx = 0; idx < ((String)request.getParameter("pregenAcc")).length(); ++idx){
				theChar = ((String)request.getParameter("pregenAcc")).charAt(idx);
				logger.info("theChar: " + theChar);
				if(!Character.isDigit(theChar)){
					valid = false;
					errorText = "Pregen account should contain numbers only";
					break;
				}
			}
			
			if(valid == true){
				long acctnb = Long.parseLong(request.getParameter("pregenAcc"));
				String srccode = null;
		
				result = validateAccountNumService.validateAccountNum(acctnb, srccode);

				if(result != 0){
					errorText = validateAccountNumService.validateAccountNumError(acctnb, srccode);
				}
			}

			jsonArray.add("{\"result\":" + result
					 	+ ", \"errorText\":\"" + errorText
					 	+ "\"}");
		
			logger.info(jsonArray);
		
			return new ModelAndView("ajax_validateaccountnum", "jsonArray", jsonArray);
		}
		
		JSONArray jsonArray = new JSONArray();
		
		String errorText = "";
		int result = 0;
		
		jsonArray.add("{\"result\":" + result
			 	+ ", \"errorText\":\"" + errorText
			 	+ "\"}");
		
		return new ModelAndView("ajax_validateaccountnum", "jsonArray", jsonArray);
	}	
}
