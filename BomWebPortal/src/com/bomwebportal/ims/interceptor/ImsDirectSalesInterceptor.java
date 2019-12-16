package com.bomwebportal.ims.interceptor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.ImsMultiTabException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;

public class ImsDirectSalesInterceptor extends HandlerInterceptorAdapter{
	
	protected final Log logger = LogFactory.getLog(getClass());
	public static final String IMS_UID = "IMS_UID";
	
	private String appEnv;
    
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{								
		//steven added, requested by raymond - DS mobile number in header 20141113 start
		if(!"dev".equals(appEnv)&&Utility.getDeviceType(request)==null){
//	    	logger.info("ImsCommonInterceptor preHandle is called, url:"+request.getRequestURL());
			if(request.getScheme().equals("http") && !request.isSecure() &&
					request.getRequestURL().indexOf("localhost")==-1 &&
					request.getRequestURL().toString().contains("welcome.html")){//not https and not local
				//redirect to https
				logger.info("not https, not local, not mobile device and welcome page");
		    	logger.info("Redirect to https:"+request.getRequestURL().toString().replace("http://", "https://")+"?"+request.getQueryString());
		    	if(request.getQueryString()!=null&&!request.getQueryString().isEmpty()){
		    		response.sendRedirect(request.getRequestURL().toString().replace("http://", "https://")+"?"+request.getQueryString());
		    	}else{
		    		response.sendRedirect(request.getRequestURL().toString().replace("http://", "https://"));
		    	}
				
			    return false;
			}
		}
		if(Utility.getDeviceType(request)!=null){
			logger.info("mobile device"+Utility.getDeviceType(request));
		}
		//steven added, requested by raymond - DS mobile number in header 20141113 end
		
		return true;
		
	}

	public void setAppEnv(String appEnv) {
		this.appEnv = appEnv;
	}

	public String getAppEnv() {
		return appEnv;
	}
		
}
