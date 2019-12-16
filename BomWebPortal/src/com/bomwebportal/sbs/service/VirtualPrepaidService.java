package com.bomwebportal.sbs.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.sbs.dao.OnlineApiTxnDAO;
import com.bomwebportal.sbs.dto.VirtualPrepaidResult;
import com.bomwebportal.service.MailService;
import com.bomwebportal.util.ServerUtils;
import com.google.gson.Gson;

public class VirtualPrepaidService  {
	public final static String API_TYPE = "PP_VKK_ACT";
	public final static String SYS_ID = "ASD";
	
	protected final Log logger = LogFactory.getLog(getClass());

	private String failureEmailFrom;
	private String failureEmailTo[];
	private String failureEmailSubject;
	
	private String url;// = "http://localhost:8080/MobOs/rs/fakevkk/api/VirtualPrepaid/action.do";
	
	@Autowired
	private OnlineApiTxnDAO onlineApiTxnDAO;
	@Autowired
	private SbsOrderService sbsOrderService;
	
	@Autowired
	private MailService mailService;
	
	public String getFailureEmailFrom() {
		return failureEmailFrom;
	}


	public void setFailureEmailFrom(String failureEmailFrom) {
		this.failureEmailFrom = failureEmailFrom;
	}


	public String[] getFailureEmailTo() {
		return failureEmailTo;
	}


	public void setFailureEmailTo(String[] failureEmailTo) {
		this.failureEmailTo = failureEmailTo;
	}


	public String getFailureEmailSubject() {
		return failureEmailSubject;
	}


	public void setFailureEmailSubject(String failureEmailSubject) {
		this.failureEmailSubject = failureEmailSubject;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public VirtualPrepaidResult updateEmail(String orderId, String mrt, String email, String updateBy) {
		URLConnection conn;
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("type", "1");
		param.put("orderID", orderId);
		param.put("mrt", mrt);
		param.put("emailAddr", email);	

		try {
			sbsOrderService.insertOrderRemark(orderId, updateBy + " UPDATE VKK SERVICE EMAIL", updateBy);

			
			String geturl = url+"?type=1";
			if (orderId != null)  {
				geturl+= "&orderID="+orderId;
			}
			if (mrt != null)  {
				geturl+= "&mrt="+mrt;
			}
			if (email != null)  {
				geturl+= "&emailAddr="+email;
			}
			conn = ServerUtils.getDummySSLConnection(geturl);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));

			JAXBContext jc = JAXBContext.newInstance(VirtualPrepaidResult.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			VirtualPrepaidResult result = (VirtualPrepaidResult)unmarshaller.unmarshal(br);

			logApiTxn(orderId, API_TYPE, SYS_ID, param, result, updateBy);
			if (! "0".equals(result.getErrorcode()) ) {
				emailErrorForUpdate(orderId, result.getErrorcode(), result.getDescription());
			}
			return result;
		} catch (Exception e) {
			logger.error("Failed to invoke VKK service", e);
			logApiTxn(orderId, API_TYPE, SYS_ID, param, e, updateBy);
			emailErrorForUpdate(orderId, "exception", ExceptionUtils.getStackTrace(e));
			throw new AppRuntimeException(e);
		} 
	}


	public VirtualPrepaidResult activate(String orderId, String mrt, String email, String updateBy) {
		URLConnection conn;
		
		HashMap<String,String> param = new HashMap<String,String>();
		param.put("type", "0");
		param.put("orderID", orderId);
		param.put("mrt", mrt);
		param.put("emailAddr", email);
		
		try {
			sbsOrderService.insertOrderRemark(orderId, updateBy + " ACTIVATE VKK SERVICE", updateBy);
		
			String geturl = url+"?type=0";
			if (orderId != null)  {
				geturl+= "&orderID="+orderId;
			}
			if (mrt != null)  {
				geturl+= "&mrt="+mrt;
			}
			if (email != null)  {
				geturl+= "&emailAddr="+email;
			}
			conn = ServerUtils.getDummySSLConnection(geturl);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));

			JAXBContext jc = JAXBContext.newInstance(VirtualPrepaidResult.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			VirtualPrepaidResult result = (VirtualPrepaidResult)unmarshaller.unmarshal(br);
			
			logApiTxn(orderId, API_TYPE, SYS_ID, param, result, updateBy);

			return result;
		} catch (Exception e) {
			logger.error("Failed to invoke VKK service", e);
			logApiTxn(orderId, API_TYPE, SYS_ID, param, e, updateBy);
			throw new AppRuntimeException(e);
		} 
	}
	
	
	void logApiTxn(String orderId, String apiType, String sysId, Object reqObj, VirtualPrepaidResult result, String updateBy) {

		String sreqObj = new Gson().toJson(reqObj);
		logApiTxn(orderId, apiType, sysId, result.getErrorcode(), result.getDescription(), sreqObj, updateBy);
		
	}
	
	void logApiTxn(String orderId, String apiType, String sysId, Object reqObj, Throwable ex, String updateBy) {

		String sreqObj = new Gson().toJson(reqObj);
		logApiTxn(orderId, apiType, sysId, "", ex.getMessage(), sreqObj, updateBy);
		
	}
	
	
	void logApiTxn(String orderId,
			String apiType,
			String sysId,
			String resultCode,
			String resultDesc,
			String reqObj,
			String updateBy) {
		try {
			
			onlineApiTxnDAO.insertTxn(orderId, apiType, sysId, resultCode, resultDesc, reqObj, updateBy);
			
		} catch (Exception e) {
			//throw new AppRuntimeException(e);
			logger.error("Failed to insert api txn log", e);
		}
		
		
	}
	
	void emailErrorForUpdate(String orderId, String code, String message) {

		String subject = failureEmailSubject + " - " + orderId;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.println("Update to Virtual King king service has failed");
		pw.println();
		pw.println("Order Id : " + orderId);
		pw.println();
		pw.println("Error Code : " + code);
		pw.println();
		pw.println("Message : " + message);
		
		try {
			InternetAddress[] toAddrs = new InternetAddress[failureEmailTo.length];
			for (int i=0; i < failureEmailTo.length; i++) {
				toAddrs[i] = new InternetAddress(failureEmailTo[i]);
			}
			mailService.send(
					new InternetAddress(failureEmailFrom), toAddrs,
					(InternetAddress[])null, (InternetAddress[])null, 
					subject,
					sw.getBuffer().toString(), (List)null);
			//MailUtil.sendMail(failureEmailFrom, failureEmailTo, null, null, subject, sw.toString(), MailUtil.MAIL_TYPE_TEXT, (File[])null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
