package com.bomwebportal.wsclient;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.Stub;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.MSISDNAnonType;
import org.openuri.www.MSISDNDealResultValue;
import org.openuri.www.MSISDNInputValue;
import org.openuri.www.MSISDNOutputValue;
import org.openuri.www.NewMSISDNAnonType;
import org.openuri.www.NewMSISDNInputValue;
import org.openuri.www.NewMSISDNOutputValue;
import org.openuri.www.ReserveMSISDNInputValue;
import org.openuri.www.UpdateMSISDNInputValue;
import org.openuri.www.ValidateDepartmentMSISDNInputValue;
import org.openuri.www.ValidateDepartmentMSISDNResponse;

import weblogic.jws.proxies.CnmService;
import weblogic.jws.proxies.CnmServiceSoap;
import weblogic.jws.proxies.CnmService_Impl;
import weblogic.webservice.context.WebServiceContext;
import weblogic.webservice.context.WebServiceSession;
import weblogic.webservice.core.handler.WSSEClientHandler;
import weblogic.xml.security.UserInfo;
import weblogic.xml.security.wsse.Security;
import weblogic.xml.security.wsse.SecurityElementFactory;

import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.exception.WebServiceException;
import com.bomwebportal.util.BomWebPortalConstant;

public class CnmClient {

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
			CnmClient client = new CnmClient();
			// client.setWsdl("http://10.87.120.118:7581/CnmWsGateway/cnm/ws/CnmService.jws?WSDL");
			client
					.setWsdl("http://10.87.6.78:7581/CnmWsGateway/cnm/ws/CnmService.jws?WSDL");
			MnpDTO mnp = new MnpDTO();

			mnp.setMsisdn("12340051");
			mnp.setShopCd("TP1");
			for (int i = 0; i < 10; i++) {
				client.checkMsisdn(mnp.getMsisdn(), mnp.getShopCd());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MnpDTO checkMsisdn(String msisdn, String shopCd) throws WebServiceException {

		MSISDNInputValue msisdnInputValue = new MSISDNInputValue();
		MSISDNOutputValue msisdnOutputValue = null;

		MnpDTO result = new MnpDTO();

		try {
			logger.info("CnmService wsdl = " + wsdl);

			CnmService cnmService = null;
			CnmServiceSoap cnmServiceSoap = null;
			String cnmWsdl = wsdl;

			if (cnmWsdl != null) {
				cnmService = new CnmService_Impl(cnmWsdl);
			} else {
				logger
						.error("Failure in loading WSDL of CnmService Web Service ");
				throw new WebServiceException(
						"Failure in loading WSDL of CnmService Web Service ");
			}

			WebServiceContext context = cnmService.context();
			WebServiceSession session = context.getSession();

			/*
			 * Registers a handler for the SOAP message traffic.
			 */
			HandlerRegistry registry = cnmService.getHandlerRegistry();
			List list = new ArrayList();
			list.add(new HandlerInfo(WSSEClientHandler.class, null, null));

			registry.setHandlerChain(new QName("CnmService"), list);

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
			SecurityElementFactory factory = SecurityElementFactory
					.getDefaultFactory();
			Security security = factory.createSecurity(null);
			security.addToken(ui);
			session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);

			cnmServiceSoap = fixEndPoint(cnmService.getCnmServiceSoap());

			msisdnInputValue.setMsisdn(msisdn);
			msisdnInputValue.setMsisdnLike(msisdn);
			msisdnInputValue.setDepartmentCode("P" + shopCd);
			msisdnInputValue.setOperCode(BomWebPortalConstant.CNM_OPERCODE);
			msisdnInputValue.setStatus(BomWebPortalConstant.CNM_DEFAULT_STATUS);
			msisdnInputValue.setType(BomWebPortalConstant.CNM_TYPE);
			/*****************************************************/
			msisdnInputValue.setCity(null);
			msisdnInputValue.setLevel(null);
			msisdnInputValue.setRegion(null);
			msisdnInputValue.setReserveID(null);
			/*****************************************************/

			logger.info("MSISDNInputValue MsisdnLike = "
					+ msisdnInputValue.getMsisdnLike());
			logger.info("MSISDNInputValue DepartmentCode = "
					+ msisdnInputValue.getDepartmentCode());

			logger.debug("MSISDNInputValue OperCode = "
					+ msisdnInputValue.getOperCode());
			logger.debug("MSISDNInputValue Status = "
					+ msisdnInputValue.getStatus());
			logger.debug("MSISDNInputValue Type = "
					+ msisdnInputValue.getType());

			msisdnOutputValue = cnmServiceSoap.getMSISDNs(msisdnInputValue);

			logger.info("CNM Web Service is called result = "
					+ msisdnOutputValue.getErrorCode());

			if (msisdnOutputValue != null) {

				logger.info("CNM Web Service result ErrorMsg = "
						+ msisdnOutputValue.getErrorMsg());

				logger.debug("CNM Web Service result ReturnCode = "
						+ msisdnOutputValue.getReturnCode());

				if (msisdnOutputValue.getErrorCode() == BomWebPortalConstant.ERRCODE_SUCCESS) {

					MSISDNAnonType[] msisAnonTypeList = msisdnOutputValue
							.getMSISDN();
					logger.info("msisAnonTypeList: " + msisAnonTypeList.length);
					if (msisAnonTypeList != null && msisAnonTypeList.length > 0) {
						logger.info("msisAnonTypeList length = "
								+ msisAnonTypeList.length);

						MSISDNAnonType msisdnAnonType = msisAnonTypeList[0];

						if (msisdnAnonType != null) {

							logger.info("msisdnAnonType ErrorCode = "
									+ msisdnAnonType.getErrorCode());
							logger.info("msisdnAnonType ErrorMsg = "
									+ msisdnAnonType.getErrorMsg());

							logger.debug("msisdnAnonType ReturnCode = "
									+ msisdnAnonType.getReturnCode());

							if (BomWebPortalConstant.ERRCODE_SUCCESS == msisdnAnonType
									.getErrorCode()) {

								logger.info("msisdnAnonType Msisdn = "
										+ msisdnAnonType.getMsisdn());
								logger.info("msisdnAnonType Status = "
										+ msisdnAnonType.getStatus());

								logger.debug("msisdnAnonType DepartmentCode = "
										+ msisdnAnonType.getDepartmentCode());

								result.setMsisdn(msisdnAnonType.getMsisdn());
								result.setCnmStatus(msisdnAnonType.getStatus());
								result.setShopCd(msisdnAnonType.getDepartmentCode());
								result.setMsisdnLvl(msisdnAnonType.getLevel());
								result.setReserveId(msisdnAnonType.getReserveID());
							}
						}
					}
				}
			}

			return result;

		} catch (Exception e) {
			logger.error("Exception caught in checkMsisdn()", e);
			throw new WebServiceException(e.getMessage(), e);
		}
	}
	
	public boolean updateMSISDN(String msisdn, int originalStatus,
			int newStatus, String operatorCode, String reserveID, String type,
			String operDate) throws Exception {
		try {

			

			CnmService cnmService = null;
			CnmServiceSoap cnmServiceSoap = null;
			/*************************************/
			String cnmWsdl = wsdl;
			/*************************************/
			//String cnmWsdl = "http://10.87.120.118:7581/CnmWsGateway/cnm/ws/CnmService.jws?WSDL";
			logger.info("CnmService wsdl = " + wsdl);
			if (cnmWsdl != null) {
				cnmService = new CnmService_Impl(cnmWsdl);
			} else {
				logger
						.error("Failure in loading WSDL of CnmService Web Service ");
				throw new Exception(
						"Failure in loading WSDL of CnmService Web Service ");
			}

			WebServiceContext context = cnmService.context();
			WebServiceSession session = context.getSession();

			// * Registers a handler for the SOAP message traffic.

			HandlerRegistry registry = cnmService.getHandlerRegistry();
			List list = new ArrayList();
			list.add(new HandlerInfo(WSSEClientHandler.class, null, null));

			registry.setHandlerChain(new QName("CnmService"), list);

			// * Set the username and password token for SOAP message sent from
			// the client, through
			// * the proxy, to the web service.

			
			//UserInfo ui = new UserInfo("acnmuser", "acnmuser");// acnmuser
			/*************************************/
			UserInfo ui = new UserInfo(username, password);// acnmuser
			/*************************************/
			session.setAttribute(WSSEClientHandler.REQUEST_USERINFO, ui);

			// * Adds the username / password token to the SOAP header.

			SecurityElementFactory factory = SecurityElementFactory
					.getDefaultFactory();
			Security security = factory.createSecurity(null);
			security.addToken(ui);
			session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);

			cnmServiceSoap = fixEndPoint(cnmService.getCnmServiceSoap());

			UpdateMSISDNInputValue inputValue = new UpdateMSISDNInputValue();
			inputValue.setMsisdn(msisdn);
			inputValue.setOriginalStatus(originalStatus);
			inputValue.setNewStatus(newStatus);
			inputValue.setOperCode(operatorCode);
			inputValue.setReserveID(reserveID);
			inputValue.setType(type);
			inputValue.setOperDate(operDate);
			MSISDNDealResultValue resultValue = cnmServiceSoap
					.updateMSISDN(inputValue);
			String errCodeMsg = (resultValue.getErrorCode() + ":" + resultValue
					.getErrorMsg());

			if (resultValue != null) {
				return resultValue.getIsSuccess();
			} else
				return false;

		} catch (RemoteException e) {
			e.printStackTrace();
			throw new Exception("Exception in CnmClient.updateMSISDN(): "
					+ e.getMessage(), e);
		}
	}
	
	// fix the end point address
	private CnmServiceSoap fixEndPoint(CnmServiceSoap soap) {
		String addr = wsdl.split("\\?", 2)[0];
		Stub stub = (Stub)soap;
		
		stub._setProperty("javax.xml.rpc.service.endpoint.address", addr);
		return soap;
	}
	
	//20150123
	public NewMSISDNAnonType[] getNewMsisdn(String msisdn, int status, String shopCd, String msisdnlvl,String numType, int returnQty) throws WebServiceException {

		NewMSISDNInputValue newMsisdnInputValue = new NewMSISDNInputValue();
		NewMSISDNOutputValue newMsisdnOutputValue = new NewMSISDNOutputValue();
		
		MnpDTO result = new MnpDTO();

		try {
			logger.info("CnmService wsdl = " + wsdl);

			CnmService cnmService = null;
			CnmServiceSoap cnmServiceSoap = null;
			String cnmWsdl = wsdl;

			if (cnmWsdl != null) {
				cnmService = new CnmService_Impl(cnmWsdl);
			} else {
				logger
						.error("Failure in loading WSDL of CnmService Web Service ");
				throw new WebServiceException(
						"Failure in loading WSDL of CnmService Web Service ");
			}

			WebServiceContext context = cnmService.context();
			WebServiceSession session = context.getSession();

			/*
			 * Registers a handler for the SOAP message traffic.
			 */
			HandlerRegistry registry = cnmService.getHandlerRegistry();
			List list = new ArrayList();
			list.add(new HandlerInfo(WSSEClientHandler.class, null, null));

			registry.setHandlerChain(new QName("CnmService"), list);

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
			SecurityElementFactory factory = SecurityElementFactory
					.getDefaultFactory();
			Security security = factory.createSecurity(null);
			security.addToken(ui);
			session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);

			cnmServiceSoap = fixEndPoint(cnmService.getCnmServiceSoap());

			newMsisdnInputValue.setMsisdn(msisdn);
			newMsisdnInputValue.setMsisdnLike(msisdn);
			newMsisdnInputValue.setDepartmentCode("P" + shopCd);
			newMsisdnInputValue.setOperCode(BomWebPortalConstant.CNM_OPERCODE);
			newMsisdnInputValue.setStatus(status);
			newMsisdnInputValue.setType(BomWebPortalConstant.CNM_TYPE);
			/*****************************************************/
			newMsisdnInputValue.setCity(null);
			newMsisdnInputValue.setLevel(msisdnlvl);
			newMsisdnInputValue.setRegion(null);
			newMsisdnInputValue.setReserveID(null);
			newMsisdnInputValue.setNumType(numType);
			newMsisdnInputValue.setReturnQty(returnQty);
			/*****************************************************/

			logger.info("NewMSISDNInputValue Msisdn = "+ newMsisdnInputValue.getMsisdn());
			logger.info("NewMSISDNInputValue MsisdnLike = "+ newMsisdnInputValue.getMsisdnLike());
			logger.info("NewMSISDNInputValue DepartmentCode = "+ newMsisdnInputValue.getDepartmentCode());
			logger.debug("NewMSISDNInputValue OperCode = "+ newMsisdnInputValue.getOperCode());
			logger.debug("NewMSISDNInputValue Status = "+ newMsisdnInputValue.getStatus());
			logger.debug("NewMSISDNInputValue Type = "+ newMsisdnInputValue.getType());
			logger.debug("NewMSISDNInputValue Level = "+ newMsisdnInputValue.getLevel());			
			logger.debug("NewMSISDNInputValue NumType = "+ newMsisdnInputValue.getNumType());		
			logger.debug("NewMSISDNInputValue ReturnQty = "+ newMsisdnInputValue.getReturnQty());
			
			//msisdnOutputValue = cnmServiceSoap.getMSISDNs(msisdnInputValue);
			newMsisdnOutputValue = cnmServiceSoap.getNewMSISDNs(newMsisdnInputValue);
			logger.debug("newMsisdnOutputValue ErrorMsg = "
					+ newMsisdnOutputValue.getErrorMsg());
			
			
			logger.info("CNM Web Service is called result = "
					+ newMsisdnOutputValue.getErrorCode());
			NewMSISDNAnonType[] newNsisAnonTypeList =null; 
			if (newMsisdnOutputValue != null) {

				logger.info("CNM Web Service result ErrorMsg = "
						+ newMsisdnOutputValue.getErrorMsg());

				logger.debug("CNM Web Service result ReturnCode = "
						+ newMsisdnOutputValue.getReturnCode());

				if (newMsisdnOutputValue.getErrorCode() == BomWebPortalConstant.ERRCODE_SUCCESS) {

					newNsisAnonTypeList = newMsisdnOutputValue.getMSISDN();
					
					return newNsisAnonTypeList;
				}
			}

			return newNsisAnonTypeList;

		} catch (Exception e) {
			logger.error("Exception caught in getNewMsisdn()", e);
			throw new WebServiceException(e.getMessage(), e);
		}
	}
	
	
	public NewMSISDNAnonType[] getNew1C2NMsisdn(String msisdn, int status, String shopCd, String msisdnlvl, String cityCd, String numType, int returnQty) throws WebServiceException {

			NewMSISDNInputValue newMsisdnInputValue = new NewMSISDNInputValue();
			NewMSISDNOutputValue newMsisdnOutputValue = new NewMSISDNOutputValue();
			
			MnpDTO result = new MnpDTO();

			try {
				logger.info("CnmService wsdl = " + wsdl);

				CnmService cnmService = null;
				CnmServiceSoap cnmServiceSoap = null;
				String cnmWsdl = wsdl;

				if (cnmWsdl != null) {
					cnmService = new CnmService_Impl(cnmWsdl);
				} else {
					logger
							.error("Failure in loading WSDL of CnmService Web Service ");
					throw new WebServiceException(
							"Failure in loading WSDL of CnmService Web Service ");
				}

				WebServiceContext context = cnmService.context();
				WebServiceSession session = context.getSession();

				/*
				 * Registers a handler for the SOAP message traffic.
				 */
				HandlerRegistry registry = cnmService.getHandlerRegistry();
				List list = new ArrayList();
				list.add(new HandlerInfo(WSSEClientHandler.class, null, null));

				registry.setHandlerChain(new QName("CnmService"), list);

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
				SecurityElementFactory factory = SecurityElementFactory
						.getDefaultFactory();
				Security security = factory.createSecurity(null);
				security.addToken(ui);
				session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);

				cnmServiceSoap = fixEndPoint(cnmService.getCnmServiceSoap());

				newMsisdnInputValue.setMsisdn(msisdn);
				newMsisdnInputValue.setMsisdnLike(msisdn);
				newMsisdnInputValue.setDepartmentCode("P" + shopCd);
				newMsisdnInputValue.setOperCode(BomWebPortalConstant.CNM_OPERCODE);
				newMsisdnInputValue.setStatus(status);
				if ("C".equalsIgnoreCase(numType)){
					newMsisdnInputValue.setType("CCU1C2N");
				} else {
					newMsisdnInputValue.setType("UNICOM1C2N");
				}
				/*****************************************************/
				newMsisdnInputValue.setCity(cityCd);
				newMsisdnInputValue.setLevel(msisdnlvl);
				newMsisdnInputValue.setRegion(null);
				newMsisdnInputValue.setReserveID(null);
				newMsisdnInputValue.setNumType(numType);
				newMsisdnInputValue.setReturnQty(returnQty);
				/*****************************************************/

				logger.info("NewMSISDNInputValue Msisdn = "+ newMsisdnInputValue.getMsisdn());
				logger.info("NewMSISDNInputValue MsisdnLike = "+ newMsisdnInputValue.getMsisdnLike());
				logger.info("NewMSISDNInputValue DepartmentCode = "+ newMsisdnInputValue.getDepartmentCode());
				logger.debug("NewMSISDNInputValue OperCode = "+ newMsisdnInputValue.getOperCode());
				logger.debug("NewMSISDNInputValue Status = "+ newMsisdnInputValue.getStatus());
				logger.debug("NewMSISDNInputValue Type = "+ newMsisdnInputValue.getType());
				logger.debug("NewMSISDNInputValue City = "+ newMsisdnInputValue.getCity());	
				logger.debug("NewMSISDNInputValue Level = "+ newMsisdnInputValue.getLevel());			
				logger.debug("NewMSISDNInputValue NumType = "+ newMsisdnInputValue.getNumType());		
				logger.debug("NewMSISDNInputValue ReturnQty = "+ newMsisdnInputValue.getReturnQty());
				
				//msisdnOutputValue = cnmServiceSoap.getMSISDNs(msisdnInputValue);
				newMsisdnOutputValue = cnmServiceSoap.getNewMSISDNs(newMsisdnInputValue);
				logger.debug("newMsisdnOutputValue ErrorMsg = "
						+ newMsisdnOutputValue.getErrorMsg());
				
				
				logger.info("CNM Web Service is called result = "
						+ newMsisdnOutputValue.getErrorCode());
				NewMSISDNAnonType[] newNsisAnonTypeList =null; 
				if (newMsisdnOutputValue != null) {

					logger.info("CNM Web Service result ErrorMsg = "
							+ newMsisdnOutputValue.getErrorMsg());

					logger.debug("CNM Web Service result ReturnCode = "
							+ newMsisdnOutputValue.getReturnCode());

					if (newMsisdnOutputValue.getErrorCode() == BomWebPortalConstant.ERRCODE_SUCCESS) {

						newNsisAnonTypeList = newMsisdnOutputValue.getMSISDN();
						
						return newNsisAnonTypeList;
					}
				}

				return newNsisAnonTypeList;

			} catch (Exception e) {
				logger.error("Exception caught in getNewMsisdn()", e);
				throw new WebServiceException(e.getMessage(), e);
			}
		}
	
	public NewMSISDNAnonType[] getNewSsMsisdn(String msisdn, int status, String shopCd, String msisdnlvl, String numType, int returnQty) throws WebServiceException {

		NewMSISDNInputValue newMsisdnInputValue = new NewMSISDNInputValue();
		NewMSISDNOutputValue newMsisdnOutputValue = new NewMSISDNOutputValue();
		
		MnpDTO result = new MnpDTO();

		try {
			logger.info("CnmService wsdl = " + wsdl);

			CnmService cnmService = null;
			CnmServiceSoap cnmServiceSoap = null;
			String cnmWsdl = wsdl;

			if (cnmWsdl != null) {
				cnmService = new CnmService_Impl(cnmWsdl);
			} else {
				logger
						.error("Failure in loading WSDL of CnmService Web Service ");
				throw new WebServiceException(
						"Failure in loading WSDL of CnmService Web Service ");
			}

			WebServiceContext context = cnmService.context();
			WebServiceSession session = context.getSession();

			/*
			 * Registers a handler for the SOAP message traffic.
			 */
			HandlerRegistry registry = cnmService.getHandlerRegistry();
			List list = new ArrayList();
			list.add(new HandlerInfo(WSSEClientHandler.class, null, null));

			registry.setHandlerChain(new QName("CnmService"), list);

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
			SecurityElementFactory factory = SecurityElementFactory
					.getDefaultFactory();
			Security security = factory.createSecurity(null);
			security.addToken(ui);
			session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);

			cnmServiceSoap = fixEndPoint(cnmService.getCnmServiceSoap());

			newMsisdnInputValue.setMsisdn(msisdn);
			newMsisdnInputValue.setMsisdnLike(msisdn);
			newMsisdnInputValue.setDepartmentCode("P" + shopCd);
			newMsisdnInputValue.setOperCode(BomWebPortalConstant.CNM_OPERCODE);
			newMsisdnInputValue.setStatus(status);
			
			newMsisdnInputValue.setType("EPROSEC");
			
			/*****************************************************/
			newMsisdnInputValue.setCity(null);
			newMsisdnInputValue.setLevel(msisdnlvl);
			newMsisdnInputValue.setRegion(null);
			newMsisdnInputValue.setReserveID(null);
			newMsisdnInputValue.setNumType(numType);
			newMsisdnInputValue.setReturnQty(returnQty);
			/*****************************************************/

			logger.info("NewMSISDNInputValue Msisdn = "+ newMsisdnInputValue.getMsisdn());
			logger.info("NewMSISDNInputValue MsisdnLike = "+ newMsisdnInputValue.getMsisdnLike());
			logger.info("NewMSISDNInputValue DepartmentCode = "+ newMsisdnInputValue.getDepartmentCode());
			logger.debug("NewMSISDNInputValue OperCode = "+ newMsisdnInputValue.getOperCode());
			logger.debug("NewMSISDNInputValue Status = "+ newMsisdnInputValue.getStatus());
			logger.debug("NewMSISDNInputValue Type = "+ newMsisdnInputValue.getType());
			logger.debug("NewMSISDNInputValue City = "+ newMsisdnInputValue.getCity());	
			logger.debug("NewMSISDNInputValue Level = "+ newMsisdnInputValue.getLevel());			
			logger.debug("NewMSISDNInputValue NumType = "+ newMsisdnInputValue.getNumType());		
			logger.debug("NewMSISDNInputValue ReturnQty = "+ newMsisdnInputValue.getReturnQty());
			
			//msisdnOutputValue = cnmServiceSoap.getMSISDNs(msisdnInputValue);
			newMsisdnOutputValue = cnmServiceSoap.getNewMSISDNs(newMsisdnInputValue);
			logger.debug("newMsisdnOutputValue ErrorMsg = "
					+ newMsisdnOutputValue.getErrorMsg());
			
			
			logger.info("CNM Web Service is called result = "
					+ newMsisdnOutputValue.getErrorCode());
			NewMSISDNAnonType[] newNsisAnonTypeList =null; 
			if (newMsisdnOutputValue != null) {

				logger.info("CNM Web Service result ErrorMsg = "
						+ newMsisdnOutputValue.getErrorMsg());

				logger.debug("CNM Web Service result ReturnCode = "
						+ newMsisdnOutputValue.getReturnCode());

				if (newMsisdnOutputValue.getErrorCode() == BomWebPortalConstant.ERRCODE_SUCCESS) {

					newNsisAnonTypeList = newMsisdnOutputValue.getMSISDN();
					
					return newNsisAnonTypeList;
				}
			}

			return newNsisAnonTypeList;

		} catch (Exception e) {
			logger.error("Exception caught in getNewMsisdn()", e);
			throw new WebServiceException(e.getMessage(), e);
		}
	}
	
	
	//20150123
	public MnpDTO checkCentralNumPoolMsisdn(String msisdn, String shopCd, String numType) throws WebServiceException {

		
			NewMSISDNInputValue newMsisdnInputValue = new NewMSISDNInputValue();
			NewMSISDNOutputValue newMsisdnOutputValue = new NewMSISDNOutputValue();
			
			MnpDTO result = new MnpDTO();

			try {
				logger.info("CnmService wsdl = " + wsdl);

				CnmService cnmService = null;
				CnmServiceSoap cnmServiceSoap = null;
				String cnmWsdl = wsdl;

				if (cnmWsdl != null) {
					cnmService = new CnmService_Impl(cnmWsdl);
				} else {
					logger
							.error("Failure in loading WSDL of CnmService Web Service ");
					throw new WebServiceException(
							"Failure in loading WSDL of CnmService Web Service ");
				}

				WebServiceContext context = cnmService.context();
				WebServiceSession session = context.getSession();

				/*
				 * Registers a handler for the SOAP message traffic.
				 */
				HandlerRegistry registry = cnmService.getHandlerRegistry();
				List list = new ArrayList();
				list.add(new HandlerInfo(WSSEClientHandler.class, null, null));

				registry.setHandlerChain(new QName("CnmService"), list);

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
				SecurityElementFactory factory = SecurityElementFactory
						.getDefaultFactory();
				Security security = factory.createSecurity(null);
				security.addToken(ui);
				session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);

				cnmServiceSoap = fixEndPoint(cnmService.getCnmServiceSoap());

				newMsisdnInputValue.setMsisdn(msisdn);
				newMsisdnInputValue.setMsisdnLike(msisdn);
				newMsisdnInputValue.setDepartmentCode("P" + shopCd);
				newMsisdnInputValue.setOperCode(BomWebPortalConstant.CNM_OPERCODE);
				newMsisdnInputValue.setStatus(BomWebPortalConstant.CNM_DEFAULT_STATUS);//BomWebPortalConstant.CNM_STATUS_NORMAL
				newMsisdnInputValue.setType(BomWebPortalConstant.CNM_TYPE);
				/*****************************************************/
				newMsisdnInputValue.setCity(null);
				newMsisdnInputValue.setLevel(null);
				newMsisdnInputValue.setRegion(null);
				newMsisdnInputValue.setReserveID(null);
				newMsisdnInputValue.setNumType(numType);
				newMsisdnInputValue.setReturnQty(-1);
				/*****************************************************/

				logger.info("newMSISDNInputValue Msisdn = "+ newMsisdnInputValue.getMsisdn());
				logger.info("newMSISDNInputValue MsisdnLike = "+ newMsisdnInputValue.getMsisdnLike());
				logger.info("newMSISDNInputValue DepartmentCode = "+ newMsisdnInputValue.getDepartmentCode());
				logger.debug("newMSISDNInputValue OperCode = "+ newMsisdnInputValue.getOperCode());
				logger.debug("newMSISDNInputValue Status = "+ newMsisdnInputValue.getStatus());
				logger.debug("newMSISDNInputValue Type = "+ newMsisdnInputValue.getType());
				logger.debug("newMSISDNInputValue Level = "+ newMsisdnInputValue.getLevel());		
				logger.debug("newMSISDNInputValue NumType = "+ newMsisdnInputValue.getNumType());			
				logger.debug("newMSISDNInputValue ReturnQty = "+ newMsisdnInputValue.getReturnQty());
				
				newMsisdnOutputValue = cnmServiceSoap.getNewMSISDNs(newMsisdnInputValue);
				
				logger.debug("newMsisdnOutputValue ErrorMsg = "+ newMsisdnOutputValue.getErrorMsg());		
				logger.info("CNM Web Service is called result = "+ newMsisdnOutputValue.getErrorCode());
				 
				if (newMsisdnOutputValue != null) {

					logger.info("CNM Web Service result ErrorMsg = "
							+ newMsisdnOutputValue.getErrorMsg());

					logger.debug("CNM Web Service result ReturnCode = "
							+ newMsisdnOutputValue.getReturnCode());

					if (newMsisdnOutputValue.getErrorCode() == BomWebPortalConstant.ERRCODE_SUCCESS) {

						NewMSISDNAnonType[] newMsisAnonTypeList = newMsisdnOutputValue.getMSISDN();
						logger.info("newMsisAnonTypeList: " + newMsisAnonTypeList.length);
						if (newMsisAnonTypeList != null && newMsisAnonTypeList.length > 0) {
							logger.info("newMsisAnonTypeList length = "
									+ newMsisAnonTypeList.length);

							NewMSISDNAnonType newMSISDNAnonType = newMsisAnonTypeList[0];

							if (newMSISDNAnonType != null) {

								logger.info("newMSISDNAnonType ErrorCode = "+ newMSISDNAnonType.getErrorCode());
								logger.info("newMSISDNAnonType ErrorMsg = "+ newMSISDNAnonType.getErrorMsg());

								logger.debug("newMSISDNAnonType ReturnCode = "
										+ newMSISDNAnonType.getReturnCode());

								if (BomWebPortalConstant.ERRCODE_SUCCESS == newMSISDNAnonType
										.getErrorCode()) {

									logger.info("newMSISDNAnonType Msisdn = "+ newMSISDNAnonType.getMsisdn());
									logger.info("newMSISDNAnonType Status = "+ newMSISDNAnonType.getStatus());
									logger.debug("newMSISDNAnonType DepartmentCode = "+ newMSISDNAnonType.getDepartmentCode());

									result.setMsisdn(newMSISDNAnonType.getMsisdn());
									result.setCnmStatus(newMSISDNAnonType.getStatus());
									result.setShopCd(newMSISDNAnonType.getDepartmentCode());
									result.setMsisdnLvl(newMSISDNAnonType.getLevel());
									result.setReserveId(newMSISDNAnonType.getReserveID());
									result.setNumType(newMSISDNAnonType.getNumType());
								}
							}
						}
					}
				}
				return result;

			} catch (Exception e) {
				logger.error("Exception caught in checkCentralNumPoolMsisdn()", e);
				throw new WebServiceException(e.getMessage(), e);
			}
		}
		
	
	
	public boolean reserveCentralNumPoolMsisdn(String msisdn, String actionType, String shopCd) throws Exception {
		try {

			CnmService cnmService = null;
			CnmServiceSoap cnmServiceSoap = null;
			/*************************************/
			String cnmWsdl = wsdl;
			/*************************************/
			//String cnmWsdl = "http://10.87.120.118:7581/CnmWsGateway/cnm/ws/CnmService.jws?WSDL";
			logger.info("CnmService wsdl = " + wsdl);
			if (cnmWsdl != null) {
				cnmService = new CnmService_Impl(cnmWsdl);
			} else {
				logger
						.error("Failure in loading WSDL of CnmService Web Service ");
				throw new Exception(
						"Failure in loading WSDL of CnmService Web Service ");
			}

			WebServiceContext context = cnmService.context();
			WebServiceSession session = context.getSession();

			// * Registers a handler for the SOAP message traffic.

			HandlerRegistry registry = cnmService.getHandlerRegistry();
			List list = new ArrayList();
			list.add(new HandlerInfo(WSSEClientHandler.class, null, null));

			registry.setHandlerChain(new QName("CnmService"), list);

			// * Set the username and password token for SOAP message sent from
			// the client, through
			// * the proxy, to the web service.

			
			//UserInfo ui = new UserInfo("acnmuser", "acnmuser");// acnmuser
			/*************************************/
			UserInfo ui = new UserInfo(username, password);// acnmuser
			/*************************************/
			session.setAttribute(WSSEClientHandler.REQUEST_USERINFO, ui);

			// * Adds the username / password token to the SOAP header.

			SecurityElementFactory factory = SecurityElementFactory
					.getDefaultFactory();
			Security security = factory.createSecurity(null);
			security.addToken(ui);
			session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);

			cnmServiceSoap = fixEndPoint(cnmService.getCnmServiceSoap());

			ReserveMSISDNInputValue inputValue = new ReserveMSISDNInputValue();
			inputValue.setMsisdn(msisdn);
			inputValue.setType(BomWebPortalConstant.CNM_TYPE);
			inputValue.setDepartmentCode("P" + shopCd);
			inputValue.setActionType(actionType);
			inputValue.setOperCode(BomWebPortalConstant.CNM_OPERCODE);
			
			

			logger.info("MSISDNInputValue DepartmentCode = "+ inputValue.getDepartmentCode());

			logger.debug("MSISDNInputValue OperCode = "+ inputValue.getOperCode());
		
			logger.debug("MSISDNInputValue Type = "+ inputValue.getType());

			
			MSISDNDealResultValue resultValue = cnmServiceSoap.reserveMSISDN(inputValue);
			String errCodeMsg = (resultValue.getErrorCode() + ":" + resultValue
					.getErrorMsg());

			logger.info("errCodeMsg = " + errCodeMsg);
			
			if (resultValue != null) {
				return resultValue.getIsSuccess();
			} else
				return false;

		} catch (RemoteException e) {
			e.printStackTrace();
			throw new Exception("Exception in CnmClient.reserveCentralNumPoolMsisdn(): "
					+ e.getMessage(), e);
		}
	}
	
	/************************************* MIP CNM WS CLIENT *********************************************************/
	/*public MnpDTO checkMsisdnMIP(String msisdn) throws WebServiceException {
		//DS order
		//hard-coded shopCd
		MSISDNInputValue msisdnInputValue = new MSISDNInputValue();
		MSISDNOutputValue msisdnOutputValue = null;

		MnpDTO result = new MnpDTO();

		try {
			logger.info("CnmService wsdl = " + wsdl);

			CnmService cnmService = null;
			CnmServiceSoap cnmServiceSoap = null;
			String cnmWsdl = wsdl;

			if (cnmWsdl != null) {
				cnmService = new CnmService_Impl(cnmWsdl);
			} else {
				logger
						.error("Failure in loading WSDL of CnmService Web Service ");
				throw new WebServiceException(
						"Failure in loading WSDL of CnmService Web Service ");
			}

			WebServiceContext context = cnmService.context();
			WebServiceSession session = context.getSession();

			
			 * Registers a handler for the SOAP message traffic.
			 
			HandlerRegistry registry = cnmService.getHandlerRegistry();
			List list = new ArrayList();
			list.add(new HandlerInfo(WSSEClientHandler.class, null, null));

			registry.setHandlerChain(new QName("CnmService"), list);

			
			 * Set the username and password token for SOAP message sent from
			 * the client, through the proxy, to the web service.
			 
			UserInfo ui = new UserInfo(username, password);// acnmuser
			//UserInfo ui = new UserInfo("acnmuser", "acnmuser");
			session.setAttribute(WSSEClientHandler.REQUEST_USERINFO, ui);

			
			 * Adds the username / password token to the SOAP header.
			 
			SecurityElementFactory factory = SecurityElementFactory
					.getDefaultFactory();
			Security security = factory.createSecurity(null);
			security.addToken(ui);
			session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);

			cnmServiceSoap = fixEndPoint(cnmService.getCnmServiceSoap());

			msisdnInputValue.setMsisdn(msisdn);
			msisdnInputValue.setMsisdnLike(msisdn);
			//msisdnInputValue.setDepartmentCode("");//hard-coded
			msisdnInputValue.setOperCode(BomWebPortalConstant.CNM_OPERCODE);
			msisdnInputValue.setStatus(BomWebPortalConstant.CNM_DEFAULT_STATUS);
			msisdnInputValue.setType(BomWebPortalConstant.CNM_TYPE);
			*//*****************************************************//*
			msisdnInputValue.setCity(null);
			msisdnInputValue.setLevel(null);
			msisdnInputValue.setRegion(null);
			msisdnInputValue.setReserveID(null);
			*//*****************************************************//*

			logger.info("MSISDNInputValue MsisdnLike = "
					+ msisdnInputValue.getMsisdnLike());
			logger.info("MSISDNInputValue DepartmentCode = "
					+ msisdnInputValue.getDepartmentCode());

			logger.debug("MSISDNInputValue OperCode = "
					+ msisdnInputValue.getOperCode());
			logger.debug("MSISDNInputValue Status = "
					+ msisdnInputValue.getStatus());
			logger.debug("MSISDNInputValue Type = "
					+ msisdnInputValue.getType());

			msisdnOutputValue = cnmServiceSoap.getMSISDNs(msisdnInputValue);

			logger.info("CNM Web Service is called result = "
					+ msisdnOutputValue.getErrorCode());

			if (msisdnOutputValue != null) {

				logger.info("CNM Web Service result ErrorMsg = "
						+ msisdnOutputValue.getErrorMsg());

				logger.debug("CNM Web Service result ReturnCode = "
						+ msisdnOutputValue.getReturnCode());

				if (msisdnOutputValue.getErrorCode() == BomWebPortalConstant.ERRCODE_SUCCESS) {

					MSISDNAnonType[] msisAnonTypeList = msisdnOutputValue
							.getMSISDN();
					logger.info("msisAnonTypeList: " + msisAnonTypeList.length);
					if (msisAnonTypeList != null && msisAnonTypeList.length > 0) {
						logger.info("msisAnonTypeList length = "
								+ msisAnonTypeList.length);

						MSISDNAnonType msisdnAnonType = msisAnonTypeList[0];

						if (msisdnAnonType != null) {

							logger.info("msisdnAnonType ErrorCode = "
									+ msisdnAnonType.getErrorCode());
							logger.info("msisdnAnonType ErrorMsg = "
									+ msisdnAnonType.getErrorMsg());

							logger.debug("msisdnAnonType ReturnCode = "
									+ msisdnAnonType.getReturnCode());

							if (BomWebPortalConstant.ERRCODE_SUCCESS == msisdnAnonType
									.getErrorCode()) {

								logger.info("msisdnAnonType Msisdn = "
										+ msisdnAnonType.getMsisdn());
								logger.info("msisdnAnonType Status = "
										+ msisdnAnonType.getStatus());

								logger.debug("msisdnAnonType DepartmentCode = "
										+ msisdnAnonType.getDepartmentCode());

								result.setMsisdn(msisdnAnonType.getMsisdn());
								result.setCnmStatus(msisdnAnonType.getStatus());
								result.setShopCd(msisdnAnonType.getDepartmentCode());
								result.setMsisdnLvl(msisdnAnonType.getLevel());
								result.setReserveId(msisdnAnonType.getReserveID());
							}
						}
					}
				}
			}

			return result;

		} catch (Exception e) {
			logger.error("Exception caught in checkMsisdn()", e);
			throw new WebServiceException(e.getMessage(), e);
		}
	}
	*/
	public MnpDTO checkCentralNumPoolMsisdnMIP(String msisdn) throws WebServiceException {
		//Retail Order
		//hard-coded numType and shopCd
		NewMSISDNInputValue newMsisdnInputValue = new NewMSISDNInputValue();
		NewMSISDNOutputValue newMsisdnOutputValue = new NewMSISDNOutputValue();
		
		MnpDTO result = new MnpDTO();

		try {
			logger.info("CnmService wsdl = " + wsdl);

			CnmService cnmService = null;
			CnmServiceSoap cnmServiceSoap = null;
			String cnmWsdl = wsdl;

			if (cnmWsdl != null) {
				cnmService = new CnmService_Impl(cnmWsdl);
			} else {
				logger
						.error("Failure in loading WSDL of CnmService Web Service ");
				throw new WebServiceException(
						"Failure in loading WSDL of CnmService Web Service ");
			}

			WebServiceContext context = cnmService.context();
			WebServiceSession session = context.getSession();

			/*
			 * Registers a handler for the SOAP message traffic.
			 */
			HandlerRegistry registry = cnmService.getHandlerRegistry();
			List list = new ArrayList();
			list.add(new HandlerInfo(WSSEClientHandler.class, null, null));

			registry.setHandlerChain(new QName("CnmService"), list);

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
			SecurityElementFactory factory = SecurityElementFactory
					.getDefaultFactory();
			Security security = factory.createSecurity(null);
			security.addToken(ui);
			session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);

			cnmServiceSoap = fixEndPoint(cnmService.getCnmServiceSoap());

			newMsisdnInputValue.setMsisdn(msisdn);
			newMsisdnInputValue.setMsisdnLike(msisdn);
			//newMsisdnInputValue.setDepartmentCode("");//hard-coded
			newMsisdnInputValue.setOperCode(BomWebPortalConstant.CNM_OPERCODE);
			newMsisdnInputValue.setStatus(BomWebPortalConstant.CNM_DEFAULT_STATUS);//BomWebPortalConstant.CNM_STATUS_NORMAL
			newMsisdnInputValue.setType(BomWebPortalConstant.CNM_TYPE);
			/*****************************************************/
			newMsisdnInputValue.setCity(null);
			newMsisdnInputValue.setLevel(null);
			newMsisdnInputValue.setRegion(null);
			newMsisdnInputValue.setReserveID(null);
			newMsisdnInputValue.setNumType("B");//hard-coded
			newMsisdnInputValue.setReturnQty(-1);
			/*****************************************************/

			logger.info("newMSISDNInputValue Msisdn = "+ newMsisdnInputValue.getMsisdn());
			logger.info("newMSISDNInputValue MsisdnLike = "+ newMsisdnInputValue.getMsisdnLike());
			logger.info("newMSISDNInputValue DepartmentCode = "+ newMsisdnInputValue.getDepartmentCode());
			logger.debug("newMSISDNInputValue OperCode = "+ newMsisdnInputValue.getOperCode());
			logger.debug("newMSISDNInputValue Status = "+ newMsisdnInputValue.getStatus());
			logger.debug("newMSISDNInputValue Type = "+ newMsisdnInputValue.getType());
			logger.debug("newMSISDNInputValue Level = "+ newMsisdnInputValue.getLevel());		
			logger.debug("newMSISDNInputValue NumType = "+ newMsisdnInputValue.getNumType());			
			logger.debug("newMSISDNInputValue ReturnQty = "+ newMsisdnInputValue.getReturnQty());
			
			newMsisdnOutputValue = cnmServiceSoap.getNewMSISDNs(newMsisdnInputValue);
			
			logger.debug("newMsisdnOutputValue ErrorMsg = "+ newMsisdnOutputValue.getErrorMsg());		
			logger.info("CNM Web Service is called result = "+ newMsisdnOutputValue.getErrorCode());
			 
			if (newMsisdnOutputValue != null) {

				logger.info("CNM Web Service result ErrorMsg = "
						+ newMsisdnOutputValue.getErrorMsg());

				logger.debug("CNM Web Service result ReturnCode = "
						+ newMsisdnOutputValue.getReturnCode());

				if (newMsisdnOutputValue.getErrorCode() == BomWebPortalConstant.ERRCODE_SUCCESS) {

					NewMSISDNAnonType[] newMsisAnonTypeList = newMsisdnOutputValue.getMSISDN();
					logger.info("newMsisAnonTypeList: " + newMsisAnonTypeList.length);
					if (newMsisAnonTypeList != null && newMsisAnonTypeList.length > 0) {
						logger.info("newMsisAnonTypeList length = "
								+ newMsisAnonTypeList.length);

						NewMSISDNAnonType newMSISDNAnonType = newMsisAnonTypeList[0];

						if (newMSISDNAnonType != null) {

							logger.info("newMSISDNAnonType ErrorCode = "+ newMSISDNAnonType.getErrorCode());
							logger.info("newMSISDNAnonType ErrorMsg = "+ newMSISDNAnonType.getErrorMsg());

							logger.debug("newMSISDNAnonType ReturnCode = "
									+ newMSISDNAnonType.getReturnCode());

							if (BomWebPortalConstant.ERRCODE_SUCCESS == newMSISDNAnonType
									.getErrorCode()) {

								logger.info("newMSISDNAnonType Msisdn = "+ newMSISDNAnonType.getMsisdn());
								logger.info("newMSISDNAnonType Status = "+ newMSISDNAnonType.getStatus());
								logger.debug("newMSISDNAnonType DepartmentCode = "+ newMSISDNAnonType.getDepartmentCode());

								result.setMsisdn(newMSISDNAnonType.getMsisdn());
								result.setCnmStatus(newMSISDNAnonType.getStatus());
								result.setShopCd(newMSISDNAnonType.getDepartmentCode());
								result.setMsisdnLvl(newMSISDNAnonType.getLevel());
								result.setReserveId(newMSISDNAnonType.getReserveID());
								result.setNumType(newMSISDNAnonType.getNumType());
							}
						}
					}
				}
			}
			return result;

		} catch (Exception e) {
			logger.error("Exception caught in checkCentralNumPoolMsisdn()", e);
			throw new WebServiceException(e.getMessage(), e);
		}
	}
	
	

}
