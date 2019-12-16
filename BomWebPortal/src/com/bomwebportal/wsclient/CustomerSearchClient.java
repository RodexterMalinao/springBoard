package com.bomwebportal.wsclient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.Stub;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustomerSearchResponse;
import org.openuri.www.SearchingKeyDTO;

import weblogic.jws.proxies.CustSearchExpress;
import weblogic.jws.proxies.CustSearchExpressSoap;
import weblogic.jws.proxies.CustSearchExpress_Impl;
import weblogic.webservice.context.WebServiceContext;
import weblogic.webservice.context.WebServiceSession;
import weblogic.webservice.core.handler.WSSEClientHandler;
import weblogic.xml.security.UserInfo;
import weblogic.xml.security.wsse.Security;
import weblogic.xml.security.wsse.SecurityElementFactory;

import com.bomwebportal.exception.WebServiceException;
import com.bomwebportal.util.BomWebPortalConstant;

public class CustomerSearchClient {

	protected final Log logger = LogFactory.getLog(getClass());

	private String wsdl;
	private String username;
	private String password;

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
	
	public static void main(String arg[]) {
		try {
			CustomerSearchClient client = new CustomerSearchClient();

			client.setWsdl("http://10.87.120.118:7381/CustProfileGateway/ws/CustSearchExpress.jws?WSDL");

			SearchingKeyDTO searchKeyDTO = new SearchingKeyDTO();
			searchKeyDTO.setObjectAction(1);
			searchKeyDTO.setIdDocNum("R123456(A)");
			searchKeyDTO.setIdDocType("HKID");
			client.getCustomerSearchResponse(searchKeyDTO);
			System.out.println("Success!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CustomerSearchResponse getCustomerSearchResponse(SearchingKeyDTO searchingKeyDTO) throws WebServiceException {
		
		try {
			
			logger.debug("CustSearchService wsdl = " + wsdl);

			CustSearchExpress express = null;
			CustSearchExpressSoap soap = null;
			String customerSearchWsdl = wsdl;

			if (customerSearchWsdl != null) {
				express = new CustSearchExpress_Impl(customerSearchWsdl);
			} else {
				logger.error("Failure in loading WSDL of Customer Search Web Service ");
				throw new WebServiceException("Failure in loading WSDL of Customer Search Web Service ");
			}

			WebServiceContext context = express.context();
			WebServiceSession session = context.getSession();

			/*
			 * Registers a handler for the SOAP message traffic.
			 */
			HandlerRegistry registry = express.getHandlerRegistry();
			List list = new ArrayList();
			list.add(new HandlerInfo(WSSEClientHandler.class, null, null));

			registry.setHandlerChain(new QName("CustSearchService"), list);

			/*
			 * Set the username and password token for SOAP message sent from
			 * the client, through the proxy, to the web service.
			 */
			UserInfo ui = new UserInfo(username, password);// acnmuser
			//UserInfo ui = new UserInfo("acnmuser", "acnmuser");
			session.setAttribute(WSSEClientHandler.REQUEST_USERINFO, ui);

			/*
			 * Adds the username / password token to the SOAP header.
			 */
			SecurityElementFactory factory = SecurityElementFactory.getDefaultFactory();
			Security security = factory.createSecurity(null);
			security.addToken(ui);
			session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);

			soap = express.getCustSearchExpressSoap();
			
			fixEndPoint(soap);
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String refNum = sdf.format(date);

			CustomerSearchResponse response = soap.customerSearchExpress(refNum, searchingKeyDTO);
			
			if(response.getRejectMessage().equals(BomWebPortalConstant.CUSTSEARCH_SUCCESS_MSG)){
				logger.debug("Cust Search Result - Retrieve Success");
			}else if (response.getRejectMessage().equals(BomWebPortalConstant.CUSTSEARCH_FAIL_MSG)){
				logger.debug("Cust Search Result - No Record found.");
			}else if (response.getRejectMessage().equals(BomWebPortalConstant.CUSTSEARCH_MORETHAN30_MSG)) {
				logger.debug("Cust Search Result - More than 30 Records found.");
			}
			
			// 0: Success
			// 1: Over 30 service lines
			// 2: Application Exception
			// 3: System Exception
			if (response.getErrorSeverity() != null) {
				if ("0".equals(response.getErrorSeverity())) {
					logger.debug("Cust Search Result (error severity) - Retrieve Success");
				} else if ("1".equals(response.getErrorSeverity())) {
					logger.debug("Cust Search Result (error severity) - Over 30 service lines");
				} else if ("2".equals(response.getErrorSeverity())) {
					logger.debug("Cust Search Result (error severity) - Application Exception");
				} else if ("3".equals(response.getErrorSeverity())) {
					logger.debug("Cust Search Result (error severity) - System Exception");
				} 
			}
			
			return response;
			
		} catch (Exception e) {
			logger.error("Exception caught in getCustomerSearchResponse()", e);
			throw new WebServiceException(e.getMessage(), e);
		}
	}
	
	// fix the end point address
	private CustSearchExpressSoap fixEndPoint(CustSearchExpressSoap soap) {
		String addr = wsdl.split("\\?", 2)[0];
		Stub stub = (Stub)soap;
		
		stub._setProperty("javax.xml.rpc.service.endpoint.address", addr);
		return soap;
	}

}
