package com.bomwebportal.wsclient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;
import javax.xml.rpc.handler.HandlerInfo;
import javax.xml.rpc.handler.HandlerRegistry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustTagDTO;
import org.openuri.www.CustTagEnqBaseDTO;
import org.openuri.www.CustTagEnqResponse;

import weblogic.jws.proxies.CustProfile;
import weblogic.jws.proxies.CustProfileSoap;
import weblogic.jws.proxies.CustProfile_Impl;
import weblogic.webservice.context.WebServiceContext;
import weblogic.webservice.context.WebServiceSession;
import weblogic.webservice.core.handler.WSSEClientHandler;
import weblogic.xml.security.UserInfo;
import weblogic.xml.security.wsse.Security;
import weblogic.xml.security.wsse.SecurityElementFactory;

public class CustProfileClient {
	protected final Log logger = LogFactory.getLog(getClass());
	
	public enum SystemId {
		IMS
		, DRG
		, MOB
		;
	}
	public enum SearchKey {
		SRV // By srvType + srvNum
		//, CUS // By custNum
		, DOC // By idDocType + idDocNum
		//, ACC // By acctNum
		;
	}
	public enum SrvType {
		TEL // for DRG
		;
	}
	public enum IdDocType {
		HKID // HKID
		, PASS // Passport
		, BS // BR
		, CERT // Certicate No
		;
	}
	public enum PremierInd {
		Y 
		, N
		;
	}
	public enum PremierCustType {
		@Deprecated PRM // HKT Premier
		, @Deprecated RM // Project RM
		, P1 // Project HKT Premier
		, P2 // RM, RM (Yacht Project)
		;
	}
	
	private String wsdlDocumentLocation;
	private String username;
	private String password;

	public String getWsdlDocumentLocation() {
		return wsdlDocumentLocation;
	}

	public void setWsdlDocumentLocation(String wsdlDocumentLocation) {
		this.wsdlDocumentLocation = wsdlDocumentLocation;
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

	private CustProfile initCustProfile() throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("wsdlDocumentLocation: " + wsdlDocumentLocation +
					", username: " + username + 
					", password: " + password);
		}
		CustProfile custProfile = new CustProfile_Impl(this.wsdlDocumentLocation);
		setSecurity(custProfile);
		return custProfile;
	}
	
	public CustTagDTO getCustomerTagByMrt(String mobNum) throws IOException, ServiceException {
		return this.getResponse(this.getRequestBySrvNum(SystemId.MOB, null, mobNum));
	}
	
	public CustTagDTO getCustomerTagByTel(String dn) throws IOException, ServiceException {
		return this.getResponse(this.getRequestBySrvNum(SystemId.DRG, SrvType.TEL, dn));
	}
	
	public CustTagDTO getCustomerTagByLogin(String bsn) throws IOException, ServiceException {
		return this.getResponse(this.getRequestBySrvNum(SystemId.IMS, null, bsn));
	}

	public CustTagDTO getCustomerTagByIdDoc(SystemId systemId, IdDocType idDocType, String idDocNum) throws IOException, ServiceException {
		return this.getResponse(this.getRequestByIdDoc(systemId, idDocType, idDocNum));
	}
	
	private void setSecurity(CustProfile custProfile) {
		WebServiceContext context = custProfile.context();
		WebServiceSession session = context.getSession();
		
		// Registers a handler for the SOAP message traffic.
		HandlerRegistry registry = custProfile.getHandlerRegistry();
		List<HandlerInfo> list = new ArrayList<HandlerInfo>();
		list.add(new HandlerInfo(WSSEClientHandler.class, null, null));
		registry.setHandlerChain(new QName("CustProfile"), list);
		
		UserInfo userInfo = new UserInfo(username, password);
		session.setAttribute(WSSEClientHandler.REQUEST_USERINFO, userInfo);
		SecurityElementFactory factory = SecurityElementFactory.getDefaultFactory();
		Security security = factory.createSecurity(null);
		security.addToken(userInfo);       
		session.setAttribute(WSSEClientHandler.REQUEST_SECURITY, security);
	}
	
	private CustTagDTO getResponse(CustTagEnqBaseDTO request) throws ServiceException, IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("searchKey: " + request.getSearchKey() + 
					", systemId: " + request.getSystemId() + 
					", srvType: " + request.getSrvType() + 
					", srvNum: " + request.getSrvNum() +
					", idDocType: " + request.getIdDocType() +
					", idDocNum: " + request.getIdDocNum());
		}
		CustProfile custProfile = this.initCustProfile();
		CustProfileSoap soap = fixEndPoint(custProfile.getCustProfileSoap());
		CustTagEnqResponse response = soap.getPremierCustEnquiry(request);
		if (response == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("response is null");
			}
			throw new RemoteException("response is null");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("CustTagEnqResponse rejectMessage: " + response.getRejectMessage() + ", errorSeverity: " + response.getErrorSeverity());
		}
		if (!"0".equals(response.getErrorSeverity())) {
			throw new RemoteException(response.getRejectMessage() + "(" + response.getErrorSeverity() + ")");
		}
		CustTagDTO custTagDto = response.getCustTagDTO();
		if (custTagDto == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("custTagDto is null");
			}
			throw new RemoteException("custTagDto is null");
		}
		return custTagDto;
	}
	
	private CustTagEnqBaseDTO getRequestBySrvNum(SystemId systemId, SrvType srvType, String srvNum) {
		CustTagEnqBaseDTO request = new CustTagEnqBaseDTO();
		request.setSystemId(systemId.toString());
		request.setSearchKey(SearchKey.SRV.toString());
		request.setSrvType(srvType.toString());
		request.setSrvNum(StringUtils.trim(srvNum));
		request.setCustNum(null);
		request.setAcctNum(null);
		request.setIdDocType(null);
		request.setIdDocNum(null);
		request.setSrvBdryNum(null);
		return request;
	}
	
	private CustTagEnqBaseDTO getRequestByIdDoc(SystemId systemId, IdDocType idDocType, String idDocNum) {
		CustTagEnqBaseDTO request = new CustTagEnqBaseDTO();
		request.setSystemId(systemId.toString());
		request.setSearchKey(SearchKey.DOC.toString());
		request.setSrvType(null);
		request.setSrvNum(null);
		request.setCustNum(null);
		request.setAcctNum(null);
		request.setIdDocType(idDocType.toString());
		request.setIdDocNum(StringUtils.trim(idDocNum));
		request.setSrvBdryNum(null);
		return request;
	}
	
	
	// fix the end point address
	private CustProfileSoap fixEndPoint(CustProfileSoap soap) {
		String addr = wsdlDocumentLocation.split("\\?", 2)[0];
		Stub stub = (Stub)soap;
		
		stub._setProperty("javax.xml.rpc.service.endpoint.address", addr);
		return soap;
	}
	
	public static final void main(String[] args) throws Exception {
		String wsdlDocumentLocation;
		String username;
		String password;
		
		// sit
		// wsdlDocumentLocation = "http://pc01202845a:7001/CustProfileGateway/ws/CustProfile.jws?WSDL=";
		// username = "pebilluser";
		// password = "ebilluser";
		
		// uat
		// wsdlDocumentLocation = "http://xhkars63:7381/CustProfileGateway/ws/CustProfile.jws?WSDL=";
		// username = "aebilluser";
		// password = "ebilluser";
		
		// prd
		wsdlDocumentLocation = "http://bomeaiprd:7081/CustProfileGateway/ws/CustProfile.jws?WSDL=";
		username = "pebilluser";
		password = "ebilluser";
		
		CustProfileClient client = new CustProfileClient();
		client.setWsdlDocumentLocation(wsdlDocumentLocation);
		client.setUsername(username);
		client.setPassword(password);
		CustTagDTO custTagDTO = client.getCustomerTagByIdDoc(SystemId.MOB, IdDocType.HKID, "Z648276(8)");
		StringBuilder sb = new StringBuilder();
		sb.append(custTagDTO.getPremierInd());
		sb.append(", " + custTagDTO.getBomCustNum());
		sb.append(", " + custTagDTO.getPremierCustType());
		sb.append(", " + custTagDTO.getPremierCustDesc());
		sb.append(", " + custTagDTO.getPremierAddrType());
		sb.append(", " + custTagDTO.getPremierAddrDesc());
		System.out.println(sb);
	}
}
