package com.bomwebportal.ims.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.GetSourceCodeService;
import com.bomwebportal.ims.service.ImsPaymentService;

public class GetCCNewSourceCdAppMthController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private GetSourceCodeService getSourceCodeService;
	
	private ImsPaymentService imsPaymentService;
	
	public GetSourceCodeService getGetSourceCodeService() {
		return getSourceCodeService;
	}

	public void setGetSourceCodeService(GetSourceCodeService getSourceCodeService) {
		this.getSourceCodeService = getSourceCodeService;
	}

	public ImsPaymentService getImsPaymentService() {
		return imsPaymentService;
	}

	public void setImsPaymentService(ImsPaymentService imsPaymentService) {
		this.imsPaymentService = imsPaymentService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String staffNum = (String) request.getParameter("staffNum");
		logger.info("staffName:" + staffNum);
		String sourceCd = imsPaymentService.getDeflaultSourceCode(staffNum);
		String appMethod = getSourceCodeService.getDeflaultAppMethod(sourceCd);
		
		
		//if sourceCd is not null and appMethod is null, 
			//then  
		if(sourceCd != null && !("").equalsIgnoreCase(sourceCd)){
			if (appMethod == null || ("").equalsIgnoreCase(appMethod)){
				appMethod = getSourceCodeService.getDeflaultAppMethodRetry(sourceCd);
			}			
		}
		System.out.println("sourceCd:"+sourceCd);
		System.out.println("appMethod:"+appMethod);
		JSONArray jsonArray = new JSONArray();
		jsonArray.add("{\"newSourceCd\":\"" + sourceCd
				+ "\",\"newAppMethod\":\""	+appMethod
				+ "\"}");
		
		return new ModelAndView("ajax_getsourcecode", "jsonArray", jsonArray);
	}
}
