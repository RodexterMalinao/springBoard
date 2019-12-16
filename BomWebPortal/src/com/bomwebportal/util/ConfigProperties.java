package com.bomwebportal.util;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;

public class ConfigProperties implements ServletContextListener {

	private static BomPropertyPlaceholderConfigurer propertyConfigurer = null;
	
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());	
		propertyConfigurer = (BomPropertyPlaceholderConfigurer)wac.getBean("propertyConfigurer");

	}

	public static String getProperty(String name) {
		if (propertyConfigurer == null) return null;
		try {
			return propertyConfigurer.getMergedProperties().getProperty(name);
		} catch (IOException e) {

		}
		return null;
	}

	public static String getPropertyByEnvironment(String name) {
		if (propertyConfigurer == null) return null;
		try {
			return propertyConfigurer.getPropertyByEnvironment(name);
		} catch (Exception e) {

		}
		return null;
	}
}
