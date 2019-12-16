package com.bomltsportal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.service.AvailablePeriodService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;

public class AvailableServiceInterceptor extends HandlerInterceptorAdapter {
protected final Log logger = LogFactory.getLog(getClass());
	
	private AvailablePeriodService availablePeriodService;

	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)throws Exception{	

		String reqPage = request.getServletPath().replace(".html", "").replace("/", "");
		Boolean isVtMode = (Boolean)SessionHelper.getSessionOption(request, BomLtsConstant.SESSION_OPTION_VT);
		
		
		if(BooleanUtils.isTrue(isVtMode) 
				|| StringUtils.equals(reqPage, BomLtsConstant.URL_VIEW_MSG)
				|| StringUtils.equals(reqPage, "registration")){
			return true;
		}
		
		String lang = null;
		try{
			OrderCaptureDTO order = SessionHelper.getOrderCapture(request);
			lang = order.getLang();
		}catch(Exception e){
			
		}
		if (this.availablePeriodService.IsServiceInMaintenance()){
			logger.info("Service is in maintenance.");
			String msg = null;
			if(StringUtils.isNotBlank(lang)){
				msg = availablePeriodService.getMaintenanceDetail(lang);
			}else{
				StringBuilder sb = new StringBuilder();
				String engMsg = availablePeriodService.getMaintenanceDetail(BomLtsConstant.LOCALE_ENG);
				String chiMsg = availablePeriodService.getMaintenanceDetail(BomLtsConstant.LOCALE_CHI);
				
				if(StringUtils.isNotBlank(engMsg)){
					sb.append(engMsg);
					sb.append("<br/>");
				}
				if(StringUtils.isNotBlank(chiMsg)){
					sb.append(chiMsg);
				}
				msg = sb.toString();
			}
			
			if(StringUtils.isNotBlank(msg)){
				response.sendRedirect("message.html?"+BomLtsConstant.URL_PARM_MSG+"="+msg);
			}else{
				response.sendRedirect("message.html?"+BomLtsConstant.URL_PARM_MSG_CODE+"="+BomLtsConstant.URL_PARM_MSG_CODE_MAINTEINANCE);
				
			}
			return false;
		}
		
		
		
		return true;
	}
	
	
	public AvailablePeriodService getAvailablePeriodService() {
		return availablePeriodService;
	}

	public void setAvailablePeriodService(AvailablePeriodService availablePeriodService) {
		this.availablePeriodService = availablePeriodService;
	}	
	
}
