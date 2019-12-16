package com.bomwebportal.wsclient;

import javax.xml.rpc.Stub;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.DataLogDTO;

import weblogic.jws.proxies.CustSearchExpress;
import weblogic.jws.proxies.CustSearchExpressSoap;
import weblogic.jws.proxies.CustSearchExpress_Impl;
import weblogic.webservice.context.WebServiceSession;
import weblogic.webservice.core.handler.WSSEClientHandler;
import weblogic.xml.security.UserInfo;
import weblogic.xml.security.wsse.Security;
import weblogic.xml.security.wsse.SecurityElementFactory;

public class LoggingWsClient {

	private final Log logger = LogFactory.getLog(getClass());

	private String wsdl;
	private String username;
	private String password;

	
	public void logAction(String pUser, String pFunction, String pKeyValue, String pKeyType) throws Exception{

		if (StringUtils.isEmpty(wsdl)) {
			return;
		}
		
		DataLogDTO logData = new DataLogDTO();
		logData.setPFuncName(pFunction);
		logData.setPKeyType(pKeyType);
		logData.setPKeyValue(pKeyValue);
		logData.setPUserID(pUser);
		logData.setObjectAction(0);
		
		try {
			CustSearchExpress express = new CustSearchExpress_Impl(this.wsdl);
			WebServiceSession session = express.context().getSession();
			UserInfo ui = new UserInfo(this.username, this.password);
			SecurityElementFactory factory = SecurityElementFactory.getDefaultFactory();
			session.setAttribute(WSSEClientHandler.REQUEST_USERINFO, ui);			
			Security security = factory.createSecurity(null);
			security.addToken(ui);
			session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);
			fixEndPoint(express.getCustSearchExpressSoap()).insDataLog(logData);
		} catch (Exception e) {
			logger.error("Exception caught in logAction()\n" + e.getMessage(), e);
			throw e;
		}
	}
	
	public String getWsdl() {
		return wsdl;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	// fix the end point address
	private CustSearchExpressSoap fixEndPoint(CustSearchExpressSoap soap) {
		String addr = wsdl.split("\\?", 2)[0];
		Stub stub = (Stub)soap;
		
		stub._setProperty("javax.xml.rpc.service.endpoint.address", addr);
		return soap;
	}
}
