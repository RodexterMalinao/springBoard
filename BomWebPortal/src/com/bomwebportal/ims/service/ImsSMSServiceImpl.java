package com.bomwebportal.ims.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.EmailTemplateDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.ImsEmailParamHelperDAO;
import com.bomwebportal.ims.dao.ImsOrderDAO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.service.ConstantLkupService;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.bomwebportal.util.uENC;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pccw.netvigator.NetvigatorSMSInterfaceServiceLocator;

//steven created this java file, plz find steven if u find anything wrong
@Transactional(readOnly = true)
public class ImsSMSServiceImpl implements ImsSMSService {
	
	private Gson gson = new Gson();
	private String SMSAFPath;
	private String SMSQRPath;
	//private String GoogleShortUrlKey;
	private String firebaseAPIurl;
	private String firebaseAPIapiKey;
	private String firebaseAPIdomain;
	private String environment;
	
	public static final String NTV_SMS_SUCESS_STR = "result=true"; 
	public static final String NTV_SMS_USER = "s_nowtvan";
	public static final String NTV_SMS_PW = "nowtvan";
	public static final String NTV_SMS_TYPE = "1";
	protected final Log logger = LogFactory.getLog(getClass());
	private String endpoint;
	private ConstantLkupService lkupService;
	private OrderService orderService;
	private ImsOrderDAO imsOrderDao;
	private ImsEmailParamHelperDAO imsEmailParamHelperDAO; //ims celia 20141126
	public ImsEmailParamHelperDAO getImsEmailParamHelperDAO() {
		return imsEmailParamHelperDAO;
	}

	public void setImsEmailParamHelperDAO(
			ImsEmailParamHelperDAO imsEmailParamHelperDAO) {
		this.imsEmailParamHelperDAO = imsEmailParamHelperDAO;
	}

	public ImsOrderDAO getImsOrderDao() {
		return imsOrderDao;
	}

	public void setImsOrderDao(ImsOrderDAO imsOrderDao) {
		this.imsOrderDao = imsOrderDao;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	private OrdEmailReqService ordEmailReqService;
	private OrderEsignatureService orderEsignatureService;

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
	}

	public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}

	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}

	public ConstantLkupService getLkupService() {
		return lkupService;
	}

	public void setLkupService(ConstantLkupService lkupService) {
		this.lkupService = lkupService;
	}

//	private ResourceBundleMessageSource messageSource;
//	
//	public void setMessageSource(ResourceBundleMessageSource messageSource) {
//	this.messageSource = messageSource;
//}
//
//public ResourceBundleMessageSource getMessageSource() {
//	return messageSource;
//}
	
	private String nTVSmsPath;
	public String getnTVSmsPath() {
		return nTVSmsPath;
	}

	public void setnTVSmsPath(String nTVSmsPath) {
		this.nTVSmsPath = nTVSmsPath;
	}

	public EmailReqResult createSMSReq(String orderId, Date requestDate, String SMSno, String username, String templateId){
		return this.createSMSReq(orderId, requestDate, SMSno, username, templateId, 0);
	}
	
	public void addSendNowRetSmsRecord(String orderId, String templateId, String SMSno, String createBy, EmailReqResult result, int _seq)
	{
		OrderDTO orderDto = this.orderService.getOrder(orderId);		
		String filePathName1 = orderId + "_AF.pdf";
		String filePathName2 = null;
		String filePathName3 = null;		
		if (templateId.contains("NRSUA")){
			filePathName1  = null;
		}		
		Date now = new Date();
		OrdEmailReqDTO dto = new OrdEmailReqDTO();
		dto.setOrderId(orderId);
		dto.setTemplateId(templateId);
		dto.setRequestDate(new Date());
		dto.setFilePathName1(filePathName1);
		dto.setFilePathName2(filePathName2);
		dto.setFilePathName3(filePathName3);
		dto.setCreateBy( createBy);
		dto.setCreateDate(now);
		dto.setLastUpdBy( createBy);
		dto.setLastUpdDate(now);
		dto.setSMSno(SMSno);
		dto.setMethod(orderDto.getDisMode());
		dto.setLob(orderDto.getOrderSumLob());
		dto.setLocale(orderDto.getEsigEmailLang());
		if(EmailReqResult.SUCCESS==result){
			dto.setSentDate(new Date());
		}else{
			dto.setErrMsg(EmailReqResult.FAIL.toString());
		}
		OrdEmailReqDTO insertedDto;
		try {
			int seqNo=0;
			if (_seq != 0){
				seqNo = _seq;
			}else if (templateId.contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)){
				seqNo = this.ordEmailReqService.getNextAmendNoteSeqNoIMS(orderId);
			}else{
				seqNo = this.ordEmailReqService.getNextSeqIMS(orderId);
			}
			dto.setSeqNo(seqNo);
			this.ordEmailReqService.insertOrdSMSReq(dto);
			insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId);
		} catch (Exception e) {
			logger.warn("Exception in insertOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
//		insertedDto.setErrMsg(result.toString());
		try {
			this.ordEmailReqService.updateOrdSMSReq(insertedDto);
		} catch (Exception e) {
			logger.warn("Exception in updateOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	public void updateSendNowRetSmsRecord(OrdEmailReqDTO insertedDto, EmailReqResult result)
	{
		try {
			this.ordEmailReqService.updateOrdSMSReq(insertedDto);
		} catch (Exception e) {
			logger.warn("Exception in updateOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
	}
	
	public EmailReqResult createSMSReq(String orderId, Date requestDate, String SMSno, String username, String templateId, int _seq)
	{
		OrderDTO orderDto = this.orderService.getOrder(orderId);
		
		//String PT = "Y";
		
		//String templateId = "Y".equals(PT)?"RT014":"RT014";
		
		String filePathName1 = orderId + "_AF.pdf";
		String filePathName2 = null;
		String filePathName3 = null;
		
		if (templateId.contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)){
			filePathName1  = null;
		}//Celia ims 20141124
		
		
		Date now = new Date();
		OrdEmailReqDTO dto = new OrdEmailReqDTO();
		dto.setOrderId(orderId);
		dto.setTemplateId(templateId);
		dto.setRequestDate(requestDate);
		dto.setFilePathName1(filePathName1);
		dto.setFilePathName2(filePathName2);
		dto.setFilePathName3(filePathName3);
		dto.setCreateBy(username);
		dto.setCreateDate(now);
		dto.setLastUpdBy(username);
		dto.setLastUpdDate(now);
		dto.setSMSno(SMSno);
		dto.setMethod(orderDto.getDisMode());
		dto.setLob(orderDto.getOrderSumLob());
		dto.setLocale(orderDto.getEsigEmailLang());
		
		OrdEmailReqDTO insertedDto;
		try {
			int seqNo=0;
			if (_seq != 0){
				seqNo = _seq;
			}else if (templateId.contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)){
				seqNo = this.ordEmailReqService.getNextAmendNoteSeqNoIMS(orderId);
			}else{
				seqNo = this.ordEmailReqService.getNextSeqIMS(orderId);
			}
			dto.setSeqNo(seqNo);
			this.ordEmailReqService.insertOrdSMSReq(dto);
			insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId);
		} catch (Exception e) {
			logger.warn("Exception in insertOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		EmailReqResult result;
		if(templateId.contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)){
			result = EmailReqResult.SUCCESS;
		}else{		
			String api_code = ImsConstants.IMS_API_CODE;			
		try {
			if (imsEmailParamHelperDAO.isDSPCD(templateId)){
				api_code = ImsConstants.IMS_DS_PCD_API_CODE;
			}
			else if (imsEmailParamHelperDAO.isDSNTV(templateId)){
				api_code = ImsConstants.IMS_DS_NTV_API_CODE;
			}//ims ds celia 20141126  
			
			if (!StringUtils.isEmpty(imsEmailParamHelperDAO.isNTV(templateId))) {
				logger.debug("SMSno NTV: " + SMSno);
				logger.debug("insertedDto  NTV: " + gson.toJson(insertedDto));
				logger.debug("api_code  NTV: " + api_code);
				result = EmailReqResult.SUCCESS;
				this.sendNTVSMS(insertedDto, result, SMSno);
				insertedDto.setSentDate(new Date());
			} else {
				logger.debug("SMSno: " + SMSno);
				logger.debug("insertedDto: " + gson.toJson(insertedDto));
				logger.debug("api_code: " + api_code);
				this.sendSMSMessage(SMSno, insertedDto, api_code);
				result = EmailReqResult.SUCCESS;
				insertedDto.setSentDate(new Date());
				insertedDto.setErrMsg(null);
			}
			
//			insertedDto.setSentDate(new Date());
//			insertedDto.setErrMsg(null);
		} catch (Exception e) {
			logger.warn("Exception in sendOrderEmail", e);
			
			result = this.parseException(insertedDto, e);
			
			StringBuilder errMsg = new StringBuilder(result.getMessage()); 
			if (e.getMessage() != null) {
				errMsg.append(" " + e.getMessage());
			}
			insertedDto.setErrMsg(errMsg.length() > 100 ? errMsg.substring(0, 100) : errMsg.toString());
		}
		
		try {
			this.ordEmailReqService.updateOrdSMSReq(insertedDto);
		} catch (Exception e) {
			logger.warn("Exception in updateOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("result: " + result);
		}
		}
		return result;
	}
	
	public void sendSMSMessage(String mobileno, OrdEmailReqDTO dto, String aPIcode) throws ServiceException {
		// TODO Auto-generated method stub
		try{
			String ref =(dto.getOrderId().replaceAll("\\D", ""));
			EmailTemplateDTO emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(dto.getTemplateId(), dto.getLob(), dto.getLocale());
			logger.debug("emailTemplateDto" + gson.toJson(emailTemplateDto));
			logger.info("send SMS Message");
			 
			NetvigatorSMSInterfaceServiceLocator ws = new NetvigatorSMSInterfaceServiceLocator();
			logger.debug("endpoint = " + endpoint);
			ws.setNetvigatorSMSEndpointAddress(endpoint);
									
			String v1 = "852"+mobileno;
			
			SbOrderDTO ltsSbOrder = null;
			String content = this.orderEsignatureService.getEmailContent(emailTemplateDto, dto, ltsSbOrder);
			logger.info("Content Just Get:" + content);
			
			content = this.deleteHTMLtag(content);
			
			logger.info("Content Check Before Send:" + content);
			
			String v2 = content;
			String v3 = ref;
			String v4 = aPIcode;
			
			
			logger.info("v1 mobile number::"+v1);
			logger.info("v2 message:"+v2);
			logger.info("v3 orderIdRefNum:"+v3);
			logger.info("v4 API code:"+v4);
			String SmsOrNot = lkupService.getSendSMSorNot();
			logger.info("SmsOrNot:"+SmsOrNot);
			logger.debug("SmsOrNot:"+SmsOrNot);
			if(("Y").equals(SmsOrNot)){
				String result = ws.getNetvigatorSMS().sendSMS(v1, v2, v3, v4);
				logger.debug("result:"+result);
			}else{
				logger.info("No need to send sms");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	private EmailReqResult parseException(OrdEmailReqDTO dto, Exception e) {
				
		if (e instanceof ServiceException) {
			return EmailReqResult.SMS_FAIL;
		}
		return EmailReqResult.FAIL;
	}
	
	private class InvalidEncryptPasswordLengthException extends InvalidEncryptPasswordException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InvalidEncryptPasswordLengthException() {
			super();
		}
	}
	
	private class InvalidEncryptPasswordException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public InvalidEncryptPasswordException() {
			super();
		}
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getEndpoint() {
		return endpoint;
	}
	public EmailReqResult resendSMSReq(String orderId, String templateId, String SMSno, String createBy){
		return resendSMSReq(orderId, templateId,SMSno, createBy, 0);
	}
	public EmailReqResult resendSMSReq(String orderId, String templateId, String SMSno, String createBy, int _seq) {
		if (StringUtils.isBlank(SMSno)) {
			logger.warn("SMSno is blank");
			return EmailReqResult.INVALID_SMS_NO;
		}
		if (!Utility.validatePhoneNum(SMSno)) {
			logger.warn("SMSno is not valid");
			return EmailReqResult.INVALID_SMS_NO;
		}
		
		this.orderService.updateSMSNo(orderId, SMSno, createBy);
		if (_seq==0)
		    return this.createSMSReq(orderId, new Date(), SMSno, createBy, templateId);
		else
			return this.createSMSReq(orderId, new Date(), SMSno, createBy, templateId, _seq);
	}

	public String deleteHTMLtag(String content)
	{
		List<String> reg = new ArrayList<String>();
		reg.add("<p>");
		reg.add("</p>");
		
		for(String i:reg)
		{
			content = content.replaceAll(i, "");
		}
		return content;
	}
	
	public String sendNTVSMSfunction(String URL, String smsNo, String content) {
		String status = "";
		
		try {
			String uriText = URL+smsNo+nowRetSmsMsgParm+URLEncoder.encode(content, "BIG5");
			logger.info("uriText:"+uriText);
			logger.debug("wget -o abc.test '"+uriText+"'");
			URL url = new URL(uriText);			    			  			   
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(nowRetSmsProxyServer, 8080));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);			    			    			     

		    connection.setUseCaches(false);
		    connection.setDoOutput(true);

		    //Send request
		    DataOutputStream wr = new DataOutputStream (connection.getOutputStream());			   
		    wr.close();
		    
		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+;
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    status = response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			status = e.getMessage();
		}
		
		return status;
	}

    public void sendNTVSMS(OrdEmailReqDTO dto, EmailReqResult result, String smsNo) throws ServiceException{
    	String SmsOrNot = lkupService.getSendNowRetSmsOrNot();
    	String returnMessage = "";
    	
    	result = EmailReqResult.SUCCESS;
    	try{
	    	EmailTemplateDTO emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(dto.getTemplateId(), dto.getLob(), dto.getLocale());
			logger.info("send SMS Message");
			
			SbOrderDTO ltsSbOrder = null;
			String content = this.orderEsignatureService.getEmailContent(emailTemplateDto, dto, ltsSbOrder);
	    	
			logger.info("Content Just Get:" + content);
			content = this.deleteHTMLtag(content);
			logger.info("Content Check Before Send:" + content);		

	    	if("Y".equals(SmsOrNot)){
	    		returnMessage = sendNTVSMSfunction(nowRetSmsUrl, smsNo, content);
	    		logger.debug("nowRetSmsUrl SMS responseString: "+returnMessage);
	    		if (StringUtils.isBlank(returnMessage) || returnMessage.indexOf("<status>OK</status>")<=1) {
	    			returnMessage = sendNTVSMSfunction(nowRetSmsUrl2, smsNo, content);
		    		logger.debug("nowRetSmsUrl2 SMS responseString: "+returnMessage);
	    		}
			}
		} catch (Exception e) {
			result =  EmailReqResult.FAIL;
			e.printStackTrace();
			returnMessage = e.getMessage();
		}
		String errMsg = "";
		if (StringUtils.isBlank(returnMessage)) {
			result =  EmailReqResult.FAIL;
			dto.setErrMsg("result is null");
		} else if (!StringUtils.isBlank(returnMessage) && returnMessage.indexOf("<status>OK</status>")<=1) {
			result =  EmailReqResult.FAIL;
			errMsg = returnMessage;
			if (returnMessage.indexOf("<reason>")>-1) {
				errMsg = returnMessage.substring(returnMessage.indexOf("<reason>")+8,returnMessage.indexOf("</reason>"));
			}
			errMsg = errMsg.length()>98?errMsg.substring(0,98):errMsg;
			dto.setErrMsg(errMsg);
		}
		
    }
	

	public String sendNowRetSms(String number, String templateId, String order_id, String locale, Date signOffDate, String afShortUrl){		
		try{
			String msg = imsEmailParamHelperDAO.getNowRetSmsMsg(templateId, order_id, locale);
			String SmsOrNot = lkupService.getSendNowRetSmsOrNot();
	        DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
	        
	        logger.debug(dateFormat.format(signOffDate));
			
//			String afUrl = emailWEB100Link+"?lang="+genAfUrlLocale+"&order_id=" +new uENC().encAES(BomWebPortalConstant.CEKS_ENC_KEY, order_id) ;
//			String afUrl= SMSAFPath + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, order_id)+"&SignOffDate="+dateFormat.format(signOffDate) + " ";
	        logger.debug("afShortUrl b4: " + afShortUrl);
	        String longUrl =SMSAFPath + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, order_id)+"&SignOffDate="+dateFormat.format(signOffDate);
	        if("Not Exist".equals(afShortUrl)){
	        	afShortUrl = shortenUrl(longUrl);
	        }
	        if(afShortUrl==null || "".equals(afShortUrl)){//try 1/2 more if null
				afShortUrl = shortenUrl(longUrl);
			}
	        if(afShortUrl==null || "".equals(afShortUrl)){//try 2/2 more if null
				afShortUrl = shortenUrl(longUrl);
			}
	        if(afShortUrl==null || "".equals(afShortUrl)){
	        	afShortUrl =longUrl;
	        }else{
	        	this.updateShortenURL(order_id, afShortUrl);
	        }
			logger.info("afShortUrl :"+afShortUrl);
			msg = msg.replace("${https}", afShortUrl);
			logger.info("msg:"+msg+" getSendNowRetSmsOrNot:"+SmsOrNot +" nowRetSmsProxyServer:"+nowRetSmsProxyServer);
			
			
			if("Y".equals(SmsOrNot)){
//				String uriText = "https://hgw.hkcsl.com/gateway/gateway.jsp?application=LabNOWTV&mrt=852"+number+"&msg_text="+URLEncoder.encode(msg, "BIG5");
				String uriText = nowRetSmsUrl+number+nowRetSmsMsgParm+URLEncoder.encode(msg, "BIG5");
				logger.info("uriText:"+uriText);
				logger.debug("wget -o abc.test '"+uriText+"'");
				URL url = new URL(uriText);			    			  			   
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(nowRetSmsProxyServer, 8080));
				HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);			    			    			     

			    connection.setUseCaches(false);
			    connection.setDoOutput(true);

			    //Send request
			    DataOutputStream wr = new DataOutputStream (connection.getOutputStream());			   
			    wr.close();

			    //Get Response  
			    InputStream is = connection.getInputStream();
			    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			    String line;
			    while ((line = rd.readLine()) != null) {
			      response.append(line);
			      response.append('\r');
			    }
			    rd.close();
			    return response.toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "Error";
	}
	/*
	public static void main(String[] arg){
		
		ImsSMSServiceImpl testsrv = new ImsSMSServiceImpl();
		try{
			testsrv.setNowRetSmsProxyServer("proxy.pccw.com");
			testsrv.setGoogleShortUrlKey("AIzaSyCwwRFSUGyU6ndnySaV6nGrfV0J21SsHeo");
			String longUrl= "https://shopuat.hkt.com/ImsAfReport/af.pdf?sbid=5ced5d11a832095850c7d0b488bc8059&SignOffDate=201610";
			System.out.println(testsrv.shortenUrl(longUrl));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			   
			   System.setProperty("https.proxyHost", "proxy.pccw.com");
			      System.setProperty("https.proxyPort", "8080");
			      Gson gson = new Gson();
			   
			   URL url = new URL("https://www.googleapis.com/urlshortener/v1/url?key=AIzaSyCwwRFSUGyU6ndnySaV6nGrfV0J21SsHeo");
			    
			         Map<String,Object> params = new HashMap<String, Object>();
			         //params.put("key", "AIzaSyCwwRFSUGyU6ndnySaV6nGrfV0J21SsHeo");
			         params.put("longUrl", "https://shop.hkt.com/ImsAfReport/af.pdf?sbid=7a3e9e25c97a8d2f555aeccae41b6fbb");                 
			        
			         byte[] postDataBytes = gson.toJson(params).toString().getBytes("UTF-8");
			 
			         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			         conn.setRequestMethod("POST");
			         conn.setRequestProperty("Content-Type", "application/json");
			         conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			         conn.setDoOutput(true);
			         conn.getOutputStream().write(postDataBytes);
			 
			         Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			 
			         for (int c; (c = in.read()) >= 0;)
			             System.out.print((char)c);
			   
			  }catch(Exception e){
			   e.printStackTrace();
			  }
			   

		
		
		
	}*/
	
	public String shortenUrl(String longUrl){
		if("dev".equals(environment)){
			return "";
		}
		String shortenUrl = "";
		
		try{
			Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		    logger.info("longUrl: " + longUrl);
		    firebaseReqObj params = new firebaseReqObj(firebaseAPIdomain, longUrl, "UNGUESSABLE");
		    String JSON_STRING = gson.toJson(params);
		    StringRequestEntity requestEntity = new StringRequestEntity(JSON_STRING, "application/json", "UTF-8");
		    
		    HttpClient client = new HttpClient();
		    HostConfiguration config = client.getHostConfiguration();
		    config.setProxy(nowRetSmsProxyServer, 8080);
		    
		    PostMethod post = new PostMethod(firebaseAPIurl + "?key=" + firebaseAPIapiKey);
		    post.setRequestHeader("Content-Type", "application/json");
		    post.setRequestEntity(requestEntity);
		    int status = client.executeMethod(post);

		    if (status == 200 || status == 201) {
		    	String res = post.getResponseBodyAsString();
		    	firebaseRespObj wrapper = gson.fromJson(res, firebaseRespObj.class);
		    	shortenUrl = wrapper.shortLink;
		    } else {
		    	logger.error("shortenUrl API Call Failed");
		    }
		    post.releaseConnection();
		}catch(Exception e){
			logger.error("shortenUrl API Call Failed", e);
			return null;
		}
		return shortenUrl;
	}
	
	/**
	 * Wrapper class for JSON Request Body
	 * @author 81102071 Jamie Medrano
	 */
	private class firebaseReqObj {
		DynamicLinkInfo dynamicLinkInfo;
		Suffix suffix;
		
		firebaseReqObj(String domain, String url, String option) {
			this.dynamicLinkInfo = new DynamicLinkInfo(domain, url);
			this.suffix = new Suffix(option);
		}
		
		class DynamicLinkInfo {
			String domainUriPrefix;
			String link;
			DynamicLinkInfo(String domain, String url) {
				this.domainUriPrefix = domain;
				this.link = url;
			}
		}
		
		class Suffix {
			String option;
			Suffix(String option) {
				this.option = option;
			}
		}
	}
	
	/**
	 * Wrapper class for JSON Response Body
	 * @author 81102071 Jamie Medrano
	 */
	private class firebaseRespObj {
		String shortLink = "";
		String previewLink;
	}
	
	public void updateShortenURL(String order_id, String shortenURL) {
		try {
			imsOrderDao.updateShortenURL(order_id,shortenURL);	
		}catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateShortenQrCodeURL(String order_id, String shortenURL) {
		try {
			imsOrderDao.updateShortenURLQrCode(order_id,shortenURL);	
		}catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getShortenUrl(String order_id){
		logger.info("getShortenUrl:"+order_id);
		String afShortUrl = null;
		try{
			afShortUrl = imsOrderDao.getShortenUrl(order_id);
			if("Not Exist".equals(afShortUrl)){
		        DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		        List<com.bomwebportal.ims.dto.OrderDTO> a = imsOrderDao.getBomWebOrder(order_id);
		        if(a!=null&&a.size()>0){
		        	Date day=a.get(0).getSignOffDate();
		        	if(day==null){
		        		day=a.get(0).getAppDate();
		        	}
		        	String dateSting ="";
		        	try{
		        		dateSting = dateFormat.format(day);
		        	}catch (Exception e){
		        		dateSting = dateFormat.format(new Date());
		        	}
			        String longUrl =SMSAFPath + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, order_id)+"&SignOffDate="+ dateSting;
			        if("Not Exist".equals(afShortUrl)){
			        	afShortUrl = shortenUrl(longUrl);
			        }
			        for(int i=0;i<20;i++){
						if(afShortUrl==null || "".equals(afShortUrl)||
								(afShortUrl!=null&&afShortUrl.length()>55)){//try more if null
							afShortUrl = shortenUrl(longUrl);
							logger.debug("i: " + i );
							if(afShortUrl!=null&&afShortUrl.length()>55){
		                        logger.debug("afShortUrl.length(): " + afShortUrl.length());
		                        logger.debug("afShortUrl: " + afShortUrl);
							}else{
								logger.debug("Cannot shorten URL.");
								logger.debug("afShortUrl: " + afShortUrl);
							}
						}
			        }
					if(afShortUrl==null || "".equals(afShortUrl)){
			        	afShortUrl =longUrl;
			        }else{
			        	this.updateShortenURL(order_id, afShortUrl);
			        }
		        }
			}
		}catch (DAOException de) {
			logger.error("Exception caught in getShortenUrl()", de);
			throw new AppRuntimeException(de);
		}	
		return afShortUrl;
	}
	
	//martin, 20170125, BOM2016144
	public String getShortenUrlQrCode(String order_id){
		logger.info("getShortenUrl");
		String qrShortUrl = null;
		try{
			qrShortUrl = imsOrderDao.getShortenUrlQrCode(order_id);
			if("Not Exist".equals(qrShortUrl)){
		        DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		        List<com.bomwebportal.ims.dto.OrderDTO> a = imsOrderDao.getBomWebOrder(order_id);
		        if(a!=null&&a.size()>0){
		        	Date day=a.get(0).getSignOffDate();
		        	if(day==null){
		        		day=a.get(0).getAppDate();
		        	}
		        	String dateSting ="";
		        	try{
		        		dateSting = dateFormat.format(day);
		        	}catch (Exception e){
		        		dateSting = dateFormat.format(new Date());
		        	}
			        String longUrl = SMSQRPath + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, order_id)+"&SignOffDate="+ dateSting;
			        if("Not Exist".equals(qrShortUrl)){
			        	qrShortUrl = shortenUrl(longUrl);
			        }
			        for(int i=0;i<20;i++){
						if(qrShortUrl==null || "".equals(qrShortUrl)||
								(qrShortUrl!=null&&qrShortUrl.length()>60)){//try more if null
							qrShortUrl = shortenUrl(longUrl);
							logger.debug("i: " + i );
							if(qrShortUrl!=null&&qrShortUrl.length()>60){
		                        logger.debug("qrShortUrl.length(): " + qrShortUrl.length());
		                        logger.debug("qrShortUrl: " + qrShortUrl);
							}else{
								logger.debug("Cannot shorten URL.");
								logger.debug("qrShortUrl: " + qrShortUrl);
							}
						}
			        }
					if(qrShortUrl==null || "".equals(qrShortUrl)){
			        	qrShortUrl = longUrl;
			        }else{
			        	this.updateShortenQrCodeURL(order_id, qrShortUrl);
			        }
		        }
			}
		}catch (DAOException de) {
			logger.error("Exception caught in getShortenUrlQrCode()", de);
			throw new AppRuntimeException(de);
		}	
		return qrShortUrl;
	}

	private String nowRetSmsUrl;
	private String nowRetSmsUrl2; // martin, 20170227
	private String nowRetSmsMsgParm;
	private String nowRetSmsProxyServer;
	public String getNowRetSmsUrl() {
		return nowRetSmsUrl;
	}

	public void setNowRetSmsUrl(String nowRetSmsUrl) {
		logger.info("setNowRetSmsUrl");
		this.nowRetSmsUrl = nowRetSmsUrl;
	}

	public String getNowRetSmsUrl2() {
		return nowRetSmsUrl2;
	}

	public void setNowRetSmsUrl2(String nowRetSmsUrl2) {
		this.nowRetSmsUrl2 = nowRetSmsUrl2;
	}

	public String getNowRetSmsMsgParm() {
		return nowRetSmsMsgParm;
	}

	public void setNowRetSmsMsgParm(String nowRetSmsMsgParm) {
		logger.info("setNowRetSmsMsgParm");
		this.nowRetSmsMsgParm = nowRetSmsMsgParm;
	}

	public String getNowRetSmsProxyServer() {
		return nowRetSmsProxyServer;
	}

	public void setNowRetSmsProxyServer(String nowRetSmsProxyServer) {
		logger.info("setNowRetSmsProxyServer");
		this.nowRetSmsProxyServer = nowRetSmsProxyServer;
	}

	public String getSMSAFPath() {
		return SMSAFPath;
	}

	public void setSMSAFPath(String sMSAFPath) {
		SMSAFPath = sMSAFPath;
	}

	/*public String getGoogleShortUrlKey() {
		return GoogleShortUrlKey;
	}

	public void setGoogleShortUrlKey(String googleShortUrlKey) {
		logger.info("setGoogleShortUrlKey");
		GoogleShortUrlKey = googleShortUrlKey;
	}*/

	public void setEnvironment(String environment) {
		logger.info("ImsSMSServiceImpl setEnvironment:"+environment);
		this.environment = environment;
	}

	public String getEnvironment() {
		return environment;
	}

	public String getSMSQRPath() {
		return SMSQRPath;
	}

	public void setSMSQRPath(String sMSQRPath) {
		SMSQRPath = sMSQRPath;
	}

	public String getFirebaseAPIurl() {
		return firebaseAPIurl;
	}

	public void setFirebaseAPIurl(String firebaseAPIurl) {
		this.firebaseAPIurl = firebaseAPIurl;
	}

	public String getFirebaseAPIapiKey() {
		return firebaseAPIapiKey;
	}

	public void setFirebaseAPIapiKey(String firebaseAPIapiKey) {
		this.firebaseAPIapiKey = firebaseAPIapiKey;
	}

	public String getFirebaseAPIdomain() {
		return firebaseAPIdomain;
	}

	public void setFirebaseAPIdomain(String firebaseAPIdomain) {
		this.firebaseAPIdomain = firebaseAPIdomain;
	}

	
	
}
