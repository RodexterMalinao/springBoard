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

import com.bomwebportal.ims.service.ValidateHKIDService;
import com.bomwebportal.util.Utility;

public class ValidateHKIDController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ValidateHKIDService validateHKIDService;

	public ValidateHKIDService getValidateHKIDService() {
		return validateHKIDService;
	}

	public void setValidateHKIDService(ValidateHKIDService validateHKIDService) {
		this.validateHKIDService = validateHKIDService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		JSONArray jsonArray = new JSONArray();
    	
    	String docType = request.getParameter("docType");
    	String idDocNum = request.getParameter("idDocNum");
    	boolean isValid = true;
    	String errorText = null;
    	
    	if((docType != null && !("").equals(docType)) && (idDocNum != null && !("").equals(idDocNum))){
    		if(docType.equals("HKID") || docType.equals("hkid")){
    			isValid = validateHKIDService.validateHKID(idDocNum);
    			if(isValid == false){
        			//errorText = validateHKIDService.validateHKIDError(idDocNum);
        			errorText = "Invalid HKID";
        		}
    		}else if((docType.equals("BS") || docType.equals("bs"))){
    			logger.info("Validate HKBR");
    			isValid = Utility.validateHKBR(idDocNum);
    			if(isValid == true){
    				isValid = Utility.validateHKBRcheckDigit(idDocNum);
    			}
    			if(isValid == false){
        			//errorText = validateHKIDService.validateHKIDError(idDocNum);
        			errorText = "Invalid BR number";
        		}
    		}
    		
    		jsonArray.add("{\"isValid\":" + isValid
					+ ",\"errorText\":\""	+ errorText
					+ "\"}");
    	}
		return new ModelAndView("ajax_validatehkid", "jsonArray", jsonArray);
	}
}
