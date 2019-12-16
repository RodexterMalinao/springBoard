package com.bomwebportal.wsclient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisProperties;
import org.apache.axis.encoding.SimpleType;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import com.bomwebportal.exception.WebServiceException;
import com.bomwebportal.util.BomWebPortalConstant;
import com.google.gson.GsonBuilder;

import enterprise.cc1.CustomerServices_ServiceLocator;
import enterprise.eai.schemas.header.v1_6.ApplicationHeaderDetailsV10CT;
import enterprise.eai.schemas.header.v1_6.MessageReturnCodeV10CT;
import enterprise.eai.schemas.header.v1_6.MessageReturnDetailsV10CT;
import enterprise.eai.schemas.header.v1_6.MessageReturnErrorMessageTextV10CT;
import enterprise.eai.schemas.header.v1_6.OperatorChannelIdentifierV10CT;
import enterprise.eai.schemas.header.v1_6.OperatorDetailsV10CT;
import enterprise.eai.schemas.header.v1_6.OperatorIdentifierV10CT;
import enterprise.eai.schemas.header.v1_6.holders.ApplicationHeaderDetailsV10CTHolder;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.CheckCustomerCreditReplyDetailsV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.CheckCustomerCreditRequestDetailsV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryConditionDetailsV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryConditionDocIDTextV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryConditionDocTypeTextV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryResultDetailsV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryResultMRT3GCountTextV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryResultMRTOthCountTextV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryResultStatusDescTextV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.QueryResultStatusTextV10CT;
import enterprise.eai.schemas.operations.iboss.CustomerCare.CheckCustomerCredit.holders.CheckCustomerCreditReplyDetailsV10CTHolder;

public class CustCreditCheckClient {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private CustomerServices_ServiceLocator customerServiceLocator;

	public CustomerServices_ServiceLocator getCustomerServiceLocator() {
		return customerServiceLocator;
	}

	public void setCustomerServiceLocator(
			CustomerServices_ServiceLocator customerServiceLocator) {
		this.customerServiceLocator = customerServiceLocator;
	}
	
	
	public CustCreditCheckClient(String wsdlUrl) throws ServiceException {
		AxisProperties.setProperty("axis.socketSecureFactory","com.bomwebportal.wsclient.TrustAllAxisJSSESocketFactory");

		customerServiceLocator = new CustomerServices_ServiceLocator();
		customerServiceLocator.setEndpointAddress("CustomerServicesSOAP", wsdlUrl);
	}
	
	public CustCreditCheckClient(Resource keyStore, String keyStorePassword
			, Resource trustStore, String trustStorePassword
			, String wsdlUrl) throws IOException, ServiceException {
		System.setProperty("javax.net.ssl.keyStore", keyStore.getFile().getAbsolutePath());
		System.setProperty("javax.net.ssl.keyStorePassword", keyStorePassword);
		System.setProperty("javax.net.ssl.trustStore", trustStore.getFile().getAbsolutePath());
		System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
		
		customerServiceLocator = new CustomerServices_ServiceLocator();
		customerServiceLocator.setEndpointAddress("CustomerServicesSOAP", wsdlUrl);
	}

	@Deprecated
	public QueryResultDetailsV10CT checkCustomerCredit(String docType, String docID) throws ServiceException, RemoteException, MalformedURLException {
		CheckCustomerCreditRequestDetailsV10CT rReq = new CheckCustomerCreditRequestDetailsV10CT();

		QueryConditionDetailsV10CT rQC = new QueryConditionDetailsV10CT(
				new QueryConditionDocIDTextV10CT(docID),
				new QueryConditionDocTypeTextV10CT(docType));

		rReq.setQueryCondition(rQC);

		ApplicationHeaderDetailsV10CT rHeader = new ApplicationHeaderDetailsV10CT();

		OperatorDetailsV10CT rOpr = new OperatorDetailsV10CT();

		rOpr.setIdentifier(new OperatorIdentifierV10CT(BomWebPortalConstant.Identifier));
		rOpr.setChannel(new OperatorChannelIdentifierV10CT(BomWebPortalConstant.Channel));

		rHeader.setOperatorInfo(rOpr);

		CheckCustomerCreditReplyDetailsV10CTHolder rReply;
		rReply = new CheckCustomerCreditReplyDetailsV10CTHolder();

		ApplicationHeaderDetailsV10CTHolder rAppHeaderReply;
		rAppHeaderReply = new ApplicationHeaderDetailsV10CTHolder();

		customerServiceLocator.getCustomerServicesSOAP().checkCustomerCredit(rReq, rHeader, rReply, rAppHeaderReply);

		CheckCustomerCreditReplyDetailsV10CT creditReplyDetails = rReply.value;
		return creditReplyDetails == null ? null : creditReplyDetails.getQueryResult();
	}
	
	
	
	
	/*****************************************************************************/
	
	
	public CreditCheckResult checkCustomer(String docType, String docID) throws WebServiceException {
		QueryResultDetailsV10CT result = null;
		MessageReturnDetailsV10CT msgRtn = null;
		
		String errCode = null;
		String errMessage = null;
		try {
			//result = this.checkCustomerCredit(docType, docID);
			
			CheckCustomerCreditRequestDetailsV10CT rReq = new CheckCustomerCreditRequestDetailsV10CT();

			QueryConditionDetailsV10CT rQC = new QueryConditionDetailsV10CT(
					new QueryConditionDocIDTextV10CT(docID),
					new QueryConditionDocTypeTextV10CT(docType));

			rReq.setQueryCondition(rQC);

			ApplicationHeaderDetailsV10CT rHeader = new ApplicationHeaderDetailsV10CT();

			OperatorDetailsV10CT rOpr = new OperatorDetailsV10CT();

			rOpr.setIdentifier(new OperatorIdentifierV10CT(BomWebPortalConstant.Identifier));
			rOpr.setChannel(new OperatorChannelIdentifierV10CT(BomWebPortalConstant.Channel));

			rHeader.setOperatorInfo(rOpr);

			CheckCustomerCreditReplyDetailsV10CTHolder rReply;
			rReply = new CheckCustomerCreditReplyDetailsV10CTHolder();

			ApplicationHeaderDetailsV10CTHolder rAppHeaderReply;
			rAppHeaderReply = new ApplicationHeaderDetailsV10CTHolder();
			
			customerServiceLocator.getCustomerServicesSOAP().checkCustomerCredit(rReq, rHeader, rReply, rAppHeaderReply);

			msgRtn = rAppHeaderReply.value != null ? rAppHeaderReply.value.getMessageReturn() : null;
			
			result = rReply.value != null ? rReply.value.getQueryResult() : null;
			
		
		} catch (Exception e) {
			logger.error("Exception caught in checkCustomerCredit()", e);
			throw new WebServiceException("Unexpected error while checking customer credit", e);
		}
		
		if (msgRtn != null) {
			MessageReturnCodeV10CT _code = msgRtn.getCode();
			MessageReturnErrorMessageTextV10CT _errMsg = msgRtn.getErrorMessage();
			if (_code != null) {
				errCode = _code.toString();
			}
			
			if (_errMsg != null) {
				errMessage = _errMsg.toString();
			}
		}
		/*
		QueryResultStatusTextV10CT creditStatus = null;
		QueryResultStatusDescTextV10CT creditStatusDesc = null;
		QueryResultMRT3GCountTextV10CT mrt3GCount = null;
		QueryResultMRTOthCountTextV10CT mrtOthCount = null;
		*/
		
		String creditStatus = null;
		String creditStatusDesc = null;
		int mrt3GCount = 0;
		int mrtOthCount = 0;
		if (result != null) {
			creditStatus = toString(result.getStatus());
			creditStatusDesc = toString(result.getStatusDesc());
			mrt3GCount = toInt(result.getMRT3GCount());
			mrtOthCount = toInt(result.getMRTOthCount());

			
			logger.info("Cust credit status@checkCustomerCredit: "+ creditStatus);
			logger.info("Cust credit statusDesc@checkCustomerCredit: "+ creditStatusDesc);
			logger.info("Cust credit mrt3GCount@checkCustomerCredit: "+ mrt3GCount);

		}

		if ("0".equals(errCode) && creditStatus != null && creditStatusDesc != null) {
			
			String desc = creditStatusDesc;
			
			int colon = desc.indexOf(':', desc.indexOf(':')+1);
			if (colon >= 0 && colon+1 < desc.length()) {
				desc = desc.substring(colon+1);
			}
			
			CreditCheckResult ccr = null;
			ccr = new CreditCheckResult(creditStatus, desc, creditStatusDesc);
			ccr.mrt3GCount = mrt3GCount;
			ccr.mrtOthCount = mrtOthCount;

			
			return ccr;
		}/*else if (!StringUtils.isEmpty(errCode) && !StringUtils.isEmpty(errMessage)) {
			return new CreditCheckResult(errCode, "99", errMessage, errMessage);

		} else {
			return new CreditCheckResult(errCode, "99", "Unknown error while checking customer credit", "Unknown error while checking customer credit");

		}*/
		else {
			
			throw new WebServiceException("Unexpected error - " + errMessage);
		}
		
		
	}
	
	public static class CreditCheckResult {
		
		private String errCode;
		private String status;
		private String description;
		
		private String systemMessage;
		
		private int mrt3GCount;
		private int mrtOthCount;
	
		
		public CreditCheckResult(String status, String description,
				String systemMessage) {
			this.status = status;
			this.description = description;
			this.systemMessage = systemMessage;
			this.errCode = "0";
		}
		
		public CreditCheckResult(String errCode, String status, String description,
				String systemMessage) {
			this.status = status;
			this.description = description;
			this.systemMessage = systemMessage;
			this.errCode = errCode;
		}

		public String getErrCode() {
			return errCode;
		}
		
		public String getStatus() {
			return status;
		}

		public String getDescription() {
			return description;
		}

		public String getSystemMessage() {
			return systemMessage;
		}

		
		public int getMrt3GCount() {
			return mrt3GCount;
		}

		public int getMrtOthCount() {
			return mrtOthCount;
		}

		public boolean isPass() {
			return "0".equals(errCode)
					&& ("0".equals(status) || "4".equals(status));
		}
		
	}
	
	
	private String toString(SimpleType st) {
		return st == null ? null : st.toString();
		
	}
	
	private int toInt(SimpleType st) {
		String s = toString(st);
		try {
			return StringUtils.isBlank(s) ? 0 : Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	
}
