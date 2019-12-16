package com.bomltsportal.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bomltsportal.service.DnPoolService;

public class SessionTimeoutListener implements HttpSessionListener {
	
	protected final Log logger = LogFactory.getLog(getClass());

	protected DnPoolService dnPoolService;
	
	public DnPoolService getDnPoolService() {
		return dnPoolService;
	}

	public void setDnPoolService(DnPoolService dnPoolService) {
		this.dnPoolService = dnPoolService;
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		String sessionId = event.getSession().getId();
		
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(event.getSession().getServletContext());
		dnPoolService = (DnPoolService) ctx.getBean("dnPoolService");
		dnPoolService.releaseDnStatus(sessionId);
	}

}
