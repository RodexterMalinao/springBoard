/*
 * Created on May 17, 2012
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.service;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;
import javax.swing.text.DateFormatter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ServletContextAware;

import com.bomwebportal.dto.SbEnvironmentDTO;

public class ApplicationEnvironmentProvider implements ServletContextAware {
	
	private static final String DEFAUL_LABEL = "Local";
	
	protected final Log logger = LogFactory.getLog(getClass());	
	
	public void setServletContext(ServletContext pSc) {
		this.buildEnvProfile(pSc);
	}
	
	private void buildEnvProfile(ServletContext pSc) {
		SbEnvironmentDTO appEnv = SbEnvironmentDTO.getInstance();	
		
    	if (StringUtils.isBlank(appEnv.getApplName())) {
			this.getAppVersion(pSc,appEnv);
		}
    	
    	appEnv.setAppServer(pSc.getServerInfo());
    	appEnv.setApplName(pSc.getServletContextName());
    	appEnv.setAppJavaVersion(System.getProperty("java.vm.version"));
    	
    	logger.info("App Env: "      + appEnv.getAppEnv());
		logger.info("Version: "      + appEnv.getAppBuildVersion());
		logger.info("Build Date: "   + appEnv.getAppBuildDate());    	
    	logger.info("Servier Info: " + appEnv.getAppServer());
    	logger.info("Server Name: "  + appEnv.getApplName());
    	logger.info("Java Version:"  + appEnv.getAppJavaVersion());
    	
    	pSc.setAttribute("SbEnv", appEnv);
	}
		
	private void getAppVersion(ServletContext pSc,SbEnvironmentDTO pAppEnv) {
        try {
        	
        	InputStream inputStream = pSc.getResourceAsStream("/META-INF/MANIFEST.MF");
        	
        	Manifest manifest = new Manifest(inputStream);

			Attributes attributes = manifest.getMainAttributes();
			pAppEnv.setAppBuildVersion(attributes
					.getValue("SB-Build-Version")); 
			pAppEnv.setAppBuildDate(attributes.getValue("SB-Build-Date"));
			inputStream.close();
			
			if (StringUtils.isBlank(pAppEnv.getAppBuildVersion())) {
				pAppEnv.setAppBuildVersion(DEFAUL_LABEL);
			}
			
			
			if (StringUtils.isBlank(pAppEnv.getAppBuildDate())) {
				SimpleDateFormat dateFormatter	 = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");				
				pAppEnv.setAppBuildDate(dateFormatter.format(Calendar.getInstance().getTime()));
			}
						
        	        	
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
		}
	}

	public String getAppEnv() {
		SbEnvironmentDTO appEnv = SbEnvironmentDTO.getInstance();	
		return appEnv.getAppEnv();
	}

	public void setAppEnv(String pAppEnv) {
		SbEnvironmentDTO appEnv = SbEnvironmentDTO.getInstance();
		if (pAppEnv == null) {
			pAppEnv = "DEV";
		}
		if ("PRD".equals(pAppEnv.toUpperCase())) {
			appEnv.setAppEnv("Production");			
		} else if ("DEV".equals(pAppEnv.toUpperCase())) {
			appEnv.setAppEnv("Development");						
		} else {
		  appEnv.setAppEnv(pAppEnv.toUpperCase());
		}
	}

}
