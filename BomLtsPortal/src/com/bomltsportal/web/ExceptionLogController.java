package com.bomltsportal.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomltsportal.exception.UserTimeoutException;
import com.bomltsportal.service.OnlineSalesLogService;
import com.bomltsportal.util.SessionHelper;

public class ExceptionLogController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	private OnlineSalesLogService onlineSalesLogService;
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("ExceptionLogController handleRequest is called");
		
//		Enumeration en = request.getAttributeNames();
//		while(en.hasMoreElements()){
//			String element = (String) en.nextElement();
//			System.out.println(" - " + element + ": \t" + request.getAttribute(element));
//		}
		
		Exception e = (Exception) request.getAttribute("javax.servlet.error.exception");
		if(e != null){
			e.printStackTrace();

//			if(e.getClass().equals(UserTimeoutException.class)){
//				return SessionHelper.getSessionTimeOutRedirectView();
//			}else{
				String servletPath = (String) request.getAttribute("javax.servlet.error.request_uri");
				String exceptionLog = ExceptionUtils.getFullStackTrace(e);
				String reqId = SessionHelper.getRequestId(request);
				String exceptionLogReqId = onlineSalesLogService.insertOnlineExceptionLog(reqId, servletPath, exceptionLog);
				System.out.println("exceptionLogReqId: " + exceptionLogReqId);
				
//				if(StringUtils.isNotBlank(exceptionLogReqId)){
//					request.setAttribute("exceptionlogreqid", exceptionLogReqId);
//				}
//			}
			
			return SessionHelper.getExceptionView();
		}

		
		return SessionHelper.getSessionTimeOutView();
	}

	public OnlineSalesLogService getOnlineSalesLogService() {
		return onlineSalesLogService;
	}

	public void setOnlineSalesLogService(OnlineSalesLogService onlineSalesLogService) {
		this.onlineSalesLogService = onlineSalesLogService;
	}

}
