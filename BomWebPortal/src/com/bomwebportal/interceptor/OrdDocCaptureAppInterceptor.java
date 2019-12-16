package com.bomwebportal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bomwebportal.service.LoginService;

public class OrdDocCaptureAppInterceptor extends HandlerInterceptorAdapter {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private LoginService service;
	private String singleLogin;

	public LoginService getService() {
		return service;
	}

	public void setService(LoginService service) {
		this.service = service;
	}

	public void setSingleLogin(String singleLogin) {
		this.singleLogin = singleLogin;
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("BOMWEB-AUTH-TOKEN");

		logger.debug("token="+token);
		

		
		if (!StringUtils.isEmpty(token)) {
			String auth[] = token.split(":", 2);
			if (auth.length >= 2) {
				String username = auth[0];
				String sessionId = auth[1];
				
				logger.debug("username="+username);
				logger.debug("sessionId="+sessionId);
				
				if (! "Y".equalsIgnoreCase(singleLogin) ) {
					logger.debug("Single login disabled");
					return true;
				}

				String dbSessionId = service.getDbRecordSessionId(username);
				
				if (sessionId.equals(dbSessionId)) {
					logger.debug("authenticated ok");
					return true;
				}
				
			}
		}

		logger.debug("Unauthorized access");
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized access");
		return false;

	}
}
