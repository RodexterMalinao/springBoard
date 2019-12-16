package com.bomwebportal.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dao.EmailTemplateDAO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.EmailTemplateDTO;
import com.bomwebportal.dto.EmailTemplateDTO.PdfPwdInd;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.email.EmailTemplateHandler;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.ImsEmailParamHelperDAO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsOrderDetailService;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.PdfUtil;
import com.bomwebportal.util.Utility;
import com.bomwebportal.util.uENC;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

@Transactional(readOnly = true)
public class OrderEsignatureServiceImpl implements OrderEsignatureService, ApplicationContextAware  {
	protected final Log logger = LogFactory.getLog(getClass());
	
	enum EmailTemplateVariable {
		EMAIL_ADDRESS("emailAddress")
		, CUSTOMER_TITLE("customerTitle")
		, CUSTOMER_NAME("customerName")
		, MSISDN("msisdn")
		, APP_DATE("appDate")
		, CONTACT_PHONE("contactPhone")
		, SALES_NAME("salesName")
		, SHOP_CD("shopCd")
		, BRAND("brand")
		, SHOP_BRAND("shopBrand")
		, MOBILE_SERVICE_AGREEMENT_URL("mobileServiceAgreementUrl")
		, PLAN("plan")	//plan for online MOB ACQ
		, ORDER_ID("orderid")
		;
		private EmailTemplateVariable(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		private String name;
	}
	
	//Ims Email template Variable
	enum ImsEmailTemplateVariable {
		EMAIL_ADDRESS("emailAddress")
		, CUSTOMER_TITLE("customerTitle")
		, CUSTOMER_NAME("customerName")
		, MSISDN("msisdn")
		, CONTACT_PHONE("contactPhone")
		, LOGIN_ID("loginId" )
		, INSTALLATION_DATE("installationDate" )
        , INSTALLATION_STARTTIME("installationStartTime" )
        , INSTALLATION_ENDTIME("installationEndTime" )
        , EMAIL_WEB100_LINK("emailWEB100Link" )
        , CUSTOMER_CHN_NAME("customerChnName" )
        , CUSTOMER_CHN_TITLE("customerChnTitle" )
        , ORDER_ID("orderId")
        , HTTPS("https")
        , HTTPQ("httpq")
        , REQUEST_DATE("requestDate")
		;
		private ImsEmailTemplateVariable(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		private String name;
	}
	
	private ApplicationContext context;
	
	private MailService mailService;
	private OrderService orderService;
	private OrdEmailReqService ordEmailReqService;
	private CustomerProfileService customerProfileService;
	
	// LTS
	private CodeLkupCacheService ltsEmailSenderLkupCacheService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private ResourceBundleMessageSource ltsMessageSource;
	
	private EmailTemplateDAO emailTemplateDAO;
	
	private String senderEmailAddr;
	private String senderEmailName;
	private String senderEmailAddrCsl;
	private String senderEmailNameCsl;
	private String reportServerPath; //dataFilePath
	private String SMSAFPath;
	private String SMSQRPath;
	private String CPQSMSQRPath;

	public String getCPQSMSQRPath() {
		return CPQSMSQRPath;
	}

	public void setCPQSMSQRPath(String cPQSMSQRPath) {
		CPQSMSQRPath = cPQSMSQRPath;
	}

	private ImsEmailParamHelperDAO imsEmailParamHelperDAO; //eSiganture CR by Eric Ng
	private String emailWEB100Link;
	private ImsOrderDetailService imsOrderDetailService;
	private String emailWEB100Link_standalone;
	private String srdAmendUrl;  // SRD Amendment URL
	private String nsdFormUploadUrl;  // NSD Form Upload: (For port-in order) URL
	private MessageSource mobMessageSource;
	
	
	private String senderEmailAddrHKTcsl;
	private String senderEmailAddrcslcsl;
	private String senderEmailAddr1010csl;
	private String senderEmailAddr10101010;
	
	private String senderEmailNameHKTcsl;
	private String senderEmailNamecslcsl;
	private String senderEmailName1010csl;
	private String senderEmailName10101010;
	
	
	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}
	
	public String getSrdAmendUrl() {
		return srdAmendUrl;
	}

	public void setSrdAmendUrl(String srdAmendUrl) {
		this.srdAmendUrl = srdAmendUrl;
	}

	public String getNsdFormUploadUrl() {
		return nsdFormUploadUrl;
	}

	public void setNsdFormUploadUrl(String nsdFormUploadUrl) {
		this.nsdFormUploadUrl = nsdFormUploadUrl;
	}

	public String getEmailWEB100Link_standalone() {
		return emailWEB100Link_standalone;
	}

	public void setEmailWEB100Link_standalone(String emailWEB100Link_standalone) {
		this.emailWEB100Link_standalone = emailWEB100Link_standalone;
	}

	public ImsOrderDetailService getImsOrderDetailService() {
		return imsOrderDetailService;
	}

	public void setImsOrderDetailService(ImsOrderDetailService imsOrderDetailService) {
		this.imsOrderDetailService = imsOrderDetailService;
	}

	public ImsEmailParamHelperDAO getImsEmailParamHelperDAO() {
		return imsEmailParamHelperDAO;
	}

	public void setImsEmailParamHelperDAO(
			ImsEmailParamHelperDAO imsEmailParamHelperDAO) {
		this.imsEmailParamHelperDAO = imsEmailParamHelperDAO;
	}

	public String getEmailWEB100Link() {
		return emailWEB100Link;
	}

	public void setEmailWEB100Link(String emailWEB100Link) {
		this.emailWEB100Link = emailWEB100Link;
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}
	
	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public CodeLkupCacheService getLtsEmailSenderLkupCacheService() {
		return ltsEmailSenderLkupCacheService;
	}

	public void setLtsEmailSenderLkupCacheService(
			CodeLkupCacheService ltsEmailSenderLkupCacheService) {
		this.ltsEmailSenderLkupCacheService = ltsEmailSenderLkupCacheService;
	}

	public ResourceBundleMessageSource getLtsMessageSource() {
		return ltsMessageSource;
	}

	public void setLtsMessageSource(ResourceBundleMessageSource ltsMessageSource) {
		this.ltsMessageSource = ltsMessageSource;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
	}

	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}

	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}

	public EmailTemplateDAO getEmailTemplateDAO() {
		return emailTemplateDAO;
	}

	public void setEmailTemplateDAO(EmailTemplateDAO emailTemplateDAO) {
		this.emailTemplateDAO = emailTemplateDAO;
	}

	public String getSenderEmailAddr() {
		return senderEmailAddr;
	}

	public void setSenderEmailAddr(String senderEmailAddr) {
		this.senderEmailAddr = senderEmailAddr;
	}
	
	public String getSenderEmailName() {
		return senderEmailName;
	}

	public void setSenderEmailName(String senderEmailName) {
		this.senderEmailName = senderEmailName;
	}

	public String getSenderEmailAddrCsl() {
		return senderEmailAddrCsl;
	}

	public void setSenderEmailAddrCsl(String senderEmailAddrCsl) {
		this.senderEmailAddrCsl = senderEmailAddrCsl;
	}

	public String getSenderEmailNameCsl() {
		return senderEmailNameCsl;
	}

	public void setSenderEmailNameCsl(String senderEmailNameCsl) {
		this.senderEmailNameCsl = senderEmailNameCsl;
	}

	public String getReportServerPath() {
		return reportServerPath;
	}

	public void setReportServerPath(String reportServerPath) {
		this.reportServerPath = reportServerPath;
	}
	
	public void setSMSAFPath(String sMSAFPath) {
		SMSAFPath = sMSAFPath;
	}

	public String getSMSAFPath() {
		return SMSAFPath;
	}
	
	public void setSMSQRPath(String sMSQRPath) {
		SMSQRPath = sMSQRPath;
	}

	public String getSMSQRPath() {
		return SMSQRPath;
	}
	
	public MessageSource getMobMessageSource() {
		return mobMessageSource;
	}

	public void setMobMessageSource(MessageSource mobMessageSource) {
		this.mobMessageSource = mobMessageSource;
	}

	
	public String getSenderEmailAddrHKTcsl() {
		return senderEmailAddrHKTcsl;
	}

	public void setSenderEmailAddrHKTcsl(String senderEmailAddrHKTcsl) {
		this.senderEmailAddrHKTcsl = senderEmailAddrHKTcsl;
	}

	public String getSenderEmailAddrcslcsl() {
		return senderEmailAddrcslcsl;
	}

	public void setSenderEmailAddrcslcsl(String senderEmailAddrcslcsl) {
		this.senderEmailAddrcslcsl = senderEmailAddrcslcsl;
	}

	public String getSenderEmailAddr1010csl() {
		return senderEmailAddr1010csl;
	}

	public void setSenderEmailAddr1010csl(String senderEmailAddr1010csl) {
		this.senderEmailAddr1010csl = senderEmailAddr1010csl;
	}

	public String getSenderEmailAddr10101010() {
		return senderEmailAddr10101010;
	}

	public void setSenderEmailAddr10101010(String senderEmailAddr10101010) {
		this.senderEmailAddr10101010 = senderEmailAddr10101010;
	}

	
	public String getSenderEmailNameHKTcsl() {
		return senderEmailNameHKTcsl;
	}

	public void setSenderEmailNameHKTcsl(String senderEmailNameHKTcsl) {
		this.senderEmailNameHKTcsl = senderEmailNameHKTcsl;
	}

	public String getSenderEmailNamecslcsl() {
		return senderEmailNamecslcsl;
	}

	public void setSenderEmailNamecslcsl(String senderEmailNamecslcsl) {
		this.senderEmailNamecslcsl = senderEmailNamecslcsl;
	}

	public String getSenderEmailName1010csl() {
		return senderEmailName1010csl;
	}

	public void setSenderEmailName1010csl(String senderEmailName1010csl) {
		this.senderEmailName1010csl = senderEmailName1010csl;
	}

	public String getSenderEmailName10101010() {
		return senderEmailName10101010;
	}

	public void setSenderEmailName10101010(String senderEmailName10101010) {
		this.senderEmailName10101010 = senderEmailName10101010;
	}

	public String getEmailSubject(EmailTemplateDTO emailTemplateDto, OrdEmailReqDTO ordEmailReqDto, SbOrderDTO sbOrder) throws CompilationFailedException, ClassNotFoundException, IOException {
		if (StringUtils.equals(LtsConstant.LOB_LTS, emailTemplateDto.getLob())) {
			return formatSubjectLts(emailTemplateDto.getTemplateSubject(), sbOrder);
		}
		if (ordEmailReqDto.getLob().equals("IMS"))
			return formatContentIms(emailTemplateDto.getTemplateSubject(), ordEmailReqDto);

		return this.replace(emailTemplateDto.getTemplateSubject(), ordEmailReqDto);
	}
	
	public String getEmailContent(EmailTemplateDTO emailTemplateDto, OrdEmailReqDTO ordEmailReqDto, SbOrderDTO sbOrder) throws CompilationFailedException, ClassNotFoundException, IOException {
		if (StringUtils.equals(LtsConstant.LOB_LTS, emailTemplateDto.getLob())) {
			return formatContentLts(emailTemplateDto.getTemplateContent(), sbOrder, emailTemplateDto.getTemplateId(), ordEmailReqDto);
		}

		if (ordEmailReqDto.getLob().equals("IMS"))
			return formatContentIms(emailTemplateDto.getTemplateContent(), ordEmailReqDto);

		return this.replace(emailTemplateDto.getTemplateContent(), ordEmailReqDto);
	}

	public EmailTemplateDTO getEmailTemplateDTO(String templateId, String lob,
			EsigEmailLang locale) {
		return this.emailTemplateDAO.getEmailTemplateDTO(templateId, lob, locale);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult createEmailReqAndSendEmail(String orderId, String templateId,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy) {
		logger.debug("createEmailReqAndSendEmail:"+orderId+" templateId:"+templateId);
		if (StringUtils.isBlank(emailAddr)) {
			logger.warn("emailAddr is blank");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		if (!Utility.validateEmail(emailAddr)) {
			logger.warn("emailAddr is not valid");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		OrderDTO orderDto = this.orderService.getOrder(orderId);
		SubscriberDTO subscriberDTO  = new SubscriberDTO();

		if ("MOB".equals(orderDto.getOrderSumLob())){
		subscriberDTO = this.orderService.getBomWebSub(orderId);
		}		
		
		
		
		if (orderDto.isRetail()){//only retail check DisMode =E
			
			if (!DisMode.E.equals(orderDto.getDisMode()) && !DisMode.I.equals(orderDto.getDisMode())
					&& !"RT034".equalsIgnoreCase(templateId) && !"RT035".equalsIgnoreCase(templateId)) {
				logger.warn("orderId: " + orderId + ", disMode: " + orderDto.getDisMode() + " not allowed to send email");
				return EmailReqResult.NOT_ESIGN_ORDER;
			}
			
		}
		
		Date now = new Date();
		String pLang = "";
		OrdEmailReqDTO dto = new OrdEmailReqDTO();
		dto.setOrderId(orderId);
		dto.setTemplateId(templateId);
		dto.setRequestDate(requestDate);
		dto.setFilePathName1(filePathName1);
		dto.setFilePathName2(filePathName2);
		dto.setFilePathName3(filePathName3);
		dto.setCreateBy(createBy);
		dto.setCreateDate(now);
		dto.setLastUpdBy(createBy);
		dto.setLastUpdDate(now);
		dto.setEmailAddr(emailAddr);
		//dto.setLocale(locale);
		// get lob, esignLang from orderDto
		dto.setLob(orderDto.getOrderSumLob());
		if (orderDto.getEsigEmailLang()==null){
			if ("00".equals(subscriberDTO.getSmsLang())){
				dto.setLocale(EsigEmailLang.CHN);
				pLang = "CHN";
			}else{
				dto.setLocale(EsigEmailLang.ENG);
				pLang = "ENG";
			}
		}else{
		dto.setLocale(orderDto.getEsigEmailLang());
		pLang=dto.getLocale().toString();
		}
		if ("NTV-A".equals(orderDto.getOrderType())) {
			dto.setMethod(DisMode.E);
			try {
				if (!imsEmailParamHelperDAO.isDSNTV(templateId)) {
					dto.setMethod(DisMode.I);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			dto.setMethod(DisMode.E);
		}
		
		OrdEmailReqDTO insertedDto;
		int seqNo;
		try {
			if(templateId.contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)){
				seqNo = this.ordEmailReqService.getNextAmendNoteSeqNoIMS(orderId);
			}//celia ims 20141125	
			else
			seqNo = this.ordEmailReqService.getNextSeq(orderId,templateId);
			dto.setSeqNo(seqNo);
			this.ordEmailReqService.insertOrdEmailReq(dto);
			if (orderId.substring(0, 1).equals("C")){
				insertedDto = this.ordEmailReqService.getCareCashOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId,pLang);
			}else{
				insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId);
			}
		} catch (Exception e) {
			logger.warn("Exception in insertOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		beforeEmailSent(insertedDto);
		EmailReqResult result;
		if(templateId.contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)){	
			result = EmailReqResult.SUCCESS;
		}else{
		try {
			this.sendOrderEmail(insertedDto);
			result = EmailReqResult.SUCCESS;
			insertedDto.setSentDate(new Date());
			insertedDto.setErrMsg(null);
		} catch (Exception e) {
			logger.warn("Exception in sendOrderEmail", e);
			
			result = this.parseException(insertedDto, e);
			
			StringBuilder errMsg = new StringBuilder(result.getMessage()); 
			if (e.getMessage() != null) {
				errMsg.append(" " + e.getMessage());
			}
			insertedDto.setErrMsg(errMsg.length() > 200 ? errMsg.substring(0, 200) : errMsg.toString());
		}
		try {
			this.ordEmailReqService.updateOrdEmailReq(insertedDto);
		} catch (Exception e) {
			logger.warn("Exception in updateOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		
		afterEmailSent(insertedDto);
		
		if (logger.isDebugEnabled()) {
			logger.debug("result: " + result);
		}
		}
		return result;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult createEmailReqAndSendEmailLts(String orderId, String templateId, int seqNo,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy, Long printReqId) {
		if (StringUtils.isBlank(emailAddr)) {
			logger.warn("emailAddr is blank");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		if (!Utility.validateEmail(emailAddr)) {
			logger.warn("emailAddr is not valid");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		OrderDTO orderDto = this.orderService.getOrder(orderId);
		
		
		
		if (orderDto.isRetail()){//only retail check DisMode =E
			
			if (!DisMode.E.equals(orderDto.getDisMode()) && !DisMode.I.equals(orderDto.getDisMode())) {
				logger.warn("orderId: " + orderId + ", disMode: " + orderDto.getDisMode() + " not allowed to send email");
				return EmailReqResult.NOT_ESIGN_ORDER;
			}
			
		}
		
		Date now = new Date();
		
		OrdEmailReqDTO dto = new OrdEmailReqDTO();
		dto.setOrderId(orderId);
		dto.setTemplateId(templateId);
		dto.setRequestDate(requestDate);
		dto.setFilePathName1(filePathName1);
		dto.setFilePathName2(filePathName2);
		dto.setFilePathName3(filePathName3);
		dto.setCreateBy(createBy);
		dto.setCreateDate(now);
		dto.setLastUpdBy(createBy);
		dto.setLastUpdDate(now);
		dto.setEmailAddr(emailAddr);
		//dto.setLocale(locale);
		// get lob, esignLang from orderDto
		dto.setLob(orderDto.getOrderSumLob());
		dto.setLocale(orderDto.getEsigEmailLang());
		dto.setMethod(DisMode.E);
		dto.setPrintReqId(printReqId);
		dto.setSeqNo(seqNo);
		
		OrdEmailReqDTO insertedDto;
		
		try {
//			if(templateId.contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)){
//				seqNo = this.ordEmailReqService.getNextAmendNoteSeqNoIMS(orderId);
//			}//celia ims 20141125	
//			else
//			seqNo = this.ordEmailReqService.getNextSeq(orderId,templateId);
//			dto.setSeqNo(seqNo);
			this.ordEmailReqService.insertOrdEmailReq(dto);
			insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId);
		} catch (Exception e) {
			logger.warn("Exception in insertOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		beforeEmailSent(insertedDto);
		EmailReqResult result;
		if(templateId.contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)){	
			result = EmailReqResult.SUCCESS;
		}else{
		try {
			this.sendOrderEmail(insertedDto);
			result = EmailReqResult.SUCCESS;
			insertedDto.setSentDate(new Date());
			insertedDto.setErrMsg(null);
		} catch (Exception e) {
			logger.warn("Exception in sendOrderEmail", e);
			
			result = this.parseException(insertedDto, e);
			
			StringBuilder errMsg = new StringBuilder(result.getMessage()); 
			if (e.getMessage() != null) {
				errMsg.append(" " + e.getMessage());
			}
			insertedDto.setErrMsg(errMsg.length() > 200 ? errMsg.substring(0, 200) : errMsg.toString());
		}
		try {
			this.ordEmailReqService.updateOrdEmailReq(insertedDto);
		} catch (Exception e) {
			logger.warn("Exception in updateOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		
		afterEmailSent(insertedDto);
		
		if (logger.isDebugEnabled()) {
			logger.debug("result: " + result);
		}
		}
		return result;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult createEmailReqAndSendEmailIMS(String orderId, String templateId,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy) {
		if (StringUtils.isBlank(emailAddr)) {
			logger.warn("emailAddr is blank");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		if (!Utility.validateEmail(emailAddr)) {
			logger.warn("emailAddr is not valid");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		OrderDTO orderDto = this.orderService.getOrder(orderId);
		
		
		
			
//		if (!DisMode.E.equals(orderDto.getDisMode()) && !DisMode.I.equals(orderDto.getDisMode())) 
//		{
//			logger.warn("orderId: " + orderId + ", disMode: " + orderDto.getDisMode() + " not allowed to send email");
//			return EmailReqResult.NOT_ESIGN_ORDER;
//		}
		
		Date now = new Date();
		
		OrdEmailReqDTO dto = new OrdEmailReqDTO();
		dto.setOrderId(orderId);
		dto.setTemplateId(templateId);
		dto.setRequestDate(requestDate);
		dto.setFilePathName1(filePathName1);
		dto.setFilePathName2(filePathName2);
		dto.setFilePathName3(filePathName3);
		dto.setCreateBy(createBy);
		dto.setCreateDate(now);
		dto.setLastUpdBy(createBy);
		dto.setLastUpdDate(now);
		dto.setEmailAddr(emailAddr);
		//dto.setLocale(locale);
		// get lob, esignLang from orderDto
		dto.setLob(orderDto.getOrderSumLob());
		dto.setLocale(orderDto.getEsigEmailLang());
		dto.setMethod(DisMode.I);
		try {
			if (!templateId.contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)&&(imsEmailParamHelperDAO.isDSPCD(templateId)||imsEmailParamHelperDAO.isDSNTV(templateId))){
				dto.setMethod(DisMode.E);     //Celia 20141126
			}
			if (("RT034".equalsIgnoreCase(templateId)||"RT035".equalsIgnoreCase(templateId))&&(orderDto.getShopCode()!=null&&orderDto.getShopCode().contains("DS"))){
				dto.setMethod(DisMode.E);     //set method to E for DS carecash
			}
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		OrdEmailReqDTO insertedDto;
		try {
			int seqNo = 0;
			if(templateId.contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)){
				seqNo = this.ordEmailReqService.getNextAmendNoteSeqNoIMS(orderId);
			}//celia ims 20141125	
			else if("RT035".equalsIgnoreCase(templateId)){
				seqNo = 1; //always 1 for email to i-guard admin (RT035)
			}
			else
				seqNo = this.ordEmailReqService.getNextSeqIMS(orderId);
			dto.setSeqNo(seqNo);
			this.ordEmailReqService.insertOrdEmailReq(dto);
			insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId);
		} catch (Exception e) {
			logger.warn("Exception in insertOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		EmailReqResult result;
		if(templateId.contains(ImsConstants.IMS_AMENTMENT_NOTIFICATION)){	
			result = EmailReqResult.SUCCESS;
		}else{
		try {
			this.sendOrderEmail(insertedDto);
			result = EmailReqResult.SUCCESS;

			insertedDto.setSentDate(new Date());
			insertedDto.setErrMsg(null);
		} catch (Exception e) {
			logger.warn("Exception in sendOrderEmail", e);
			
			result = this.parseException(insertedDto, e);
			
			StringBuilder errMsg = new StringBuilder(result.getMessage()); 
			if (e.getMessage() != null) {
				errMsg.append(" " + e.getMessage());
			}
			insertedDto.setErrMsg(errMsg.length() > 200 ? errMsg.substring(0, 200) : errMsg.toString());
		}
		try {
			this.ordEmailReqService.updateOrdEmailReq(insertedDto);
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
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult resendEmailReq(String orderId, String templateId,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy) {
		if (StringUtils.isBlank(emailAddr)) {
			logger.warn("emailAddr is blank");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		if (!Utility.validateEmail(emailAddr)) {
			logger.warn("emailAddr is not valid");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		this.orderService.updateEsignEmailAddr(orderId, emailAddr, createBy);
		return this.createEmailReq(orderId
				, templateId, new Date()
				, filePathName1, filePathName2, filePathName3
				, emailAddr, createBy);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult resendEmailReqIMS(String orderId, String templateId,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy) {
		if (StringUtils.isBlank(emailAddr)) {
			logger.warn("emailAddr is blank");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		if (!Utility.validateEmail(emailAddr)) {
			logger.warn("emailAddr is not valid");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		this.orderService.updateEsignEmailAddr(orderId, emailAddr, createBy);
		return this.createEmailReqIMS(orderId
				, templateId, new Date()
				, filePathName1, filePathName2, filePathName3
				, emailAddr, createBy);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult resendEmailReqLts(String orderId, String templateId, int seqNo,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy, long printReqId) {
		if (StringUtils.isBlank(emailAddr)) {
			logger.warn("emailAddr is blank");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		if (!Utility.validateEmail(emailAddr)) {
			logger.warn("emailAddr is not valid");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		this.orderService.updateEsignEmailAddr(orderId, emailAddr, createBy);
		return this.createEmailReqLts(orderId
				, templateId, seqNo, new Date()
				, filePathName1, filePathName2, filePathName3
				, emailAddr, createBy, printReqId);
	}
	

	private String getSenderEmailAddress(String lob, String templateId, String shopBrand, String orderBrand) {
		if (StringUtils.equals(lob, LtsConstant.LOB_LTS)) {
			if (LtsConstant.EMAIL_TEMPLATE_IGUARD_CUST_EMAIL.equals(templateId)
					|| LtsConstant.EMAIL_TEMPLATE_IGUARD_ADMIN_EMAIL.equals(templateId)) {
				return (String)ltsEmailSenderLkupCacheService.get(LtsConstant.CODE_LKUP_EMAIL_SENDER_IGUARD_ADDRESS);
			}
			return (String)ltsEmailSenderLkupCacheService.get(LtsConstant.CODE_LKUP_EMAIL_SENDER_ADDRESS);
		}
		
		if (lob.equals("IMS"))
			try {
				return this.imsEmailParamHelperDAO.getSenderEmailAddress();
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				logger.error("Exception thrown in getting sender email address ",e);
				e.printStackTrace();
			}
		
		if (StringUtils.equalsIgnoreCase(lob, "MOB")) {
			String emailSenderAddress = getEmailSender(templateId, "SENDER_ADDRESS");
			if (StringUtils.isNotBlank(emailSenderAddress)) {
				return emailSenderAddress;
			}
		}
			
			if ("1".equals(orderBrand)){ //csl orderBrand
				if ("HKT".equalsIgnoreCase(shopBrand)) {
					logger.info("sendEmailAddrConfig used: "+senderEmailAddrHKTcsl);
					return this.senderEmailAddrHKTcsl;
				}else if ("1010".equalsIgnoreCase(shopBrand)) {
					logger.info("sendEmailAddrConfig used: "+senderEmailAddr1010csl);
					return this.senderEmailAddr1010csl;
				}else if ("csl".equalsIgnoreCase(shopBrand) || shopBrand == null) {
					logger.info("sendEmailAddrConfig used: "+senderEmailAddrcslcsl);
					return this.senderEmailAddrcslcsl;
				}
			}else if ("0".equals(orderBrand)){ //1010 orderBrand
				logger.info("sendEmailAddrConfig used: "+senderEmailAddr10101010);
				return this.senderEmailAddr10101010;
			}
			
			/*if ("csl".equalsIgnoreCase(shopBrand)) {
				return this.senderEmailAddrCsl;
			}*/
			logger.info("sendEmailAddrConfig used: "+senderEmailAddr);
			return this.senderEmailAddr;
		}
	
	public String getSenderEmailName(String lob, EsigEmailLang locale, String templateId, String shopBrand, 
			String orderBrand, String orderType) {
		if (StringUtils.equals(lob, LtsConstant.LOB_LTS)) {
			if (LtsConstant.EMAIL_TEMPLATE_SIGNOFF_CALL_CENTER.equals(templateId) //EYEX006
					|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_CALL_CENTER.equals(templateId) //EYEX006
					|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_CALL_CENTER.equals(templateId) //DEL008
					|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_CALL_CENTER.equals(templateId)) { //VASE01C
				if("Retail".equalsIgnoreCase(shopBrand)) { //Retail and Call center mode share same template id (EYEX006)
					return (String) ltsEmailSenderLkupCacheService.get(
							EsigEmailLang.ENG == locale ? LtsConstant.CODE_LKUP_EMAIL_SENDER_RS_NAME_ENG
									: LtsConstant.CODE_LKUP_EMAIL_SENDER_RS_NAME_CHN);
				}
				return (String) ltsEmailSenderLkupCacheService.get(LtsConstant.CODE_LKUP_EMAIL_SENDER_CC_NAME);
			}
			else if (LtsConstant.EMAIL_TEMPLATE_SIGNOFF_PREMIER.equals(templateId)
						|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_RENEWAL_PREMIER.equals(templateId)
						|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_RENEWAL_PREMIER_DEL.equals(templateId)
						|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_PREMIER.equals(templateId)) {
				return (String) ltsEmailSenderLkupCacheService.get(LtsConstant.CODE_LKUP_EMAIL_SENDER_PT_NAME);
			}
			else if (LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_PREMIER.equals(templateId)
					|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_PREMIER.equals(templateId)) {
				return (String) ltsEmailSenderLkupCacheService.get(LtsConstant.CODE_LKUP_EMAIL_SENDER_PT_NAME);
			}
			else if (LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_DIRECT_SALES.equals(templateId)
					|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_DIRECT_SALES.equals(templateId)
					|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_DIRECT_SALES_WITH_PREPAYMENT.equals(templateId)
					|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_DIRECT_SALES_WITH_PREPAYMENT.equals(templateId)) {
				return (String) ltsEmailSenderLkupCacheService.get(
						EsigEmailLang.ENG == locale ? LtsConstant.CODE_LKUP_EMAIL_SENDER_DS_NAME_ENG
								: LtsConstant.CODE_LKUP_EMAIL_SENDER_DS_NAME_CHN);
			}
			else if (LtsConstant.EMAIL_TEMPLATE_IGUARD_CUST_EMAIL.equals(templateId)
					|| LtsConstant.EMAIL_TEMPLATE_IGUARD_ADMIN_EMAIL.equals(templateId)) {
				return (String) ltsEmailSenderLkupCacheService.get(
						EsigEmailLang.ENG == locale ? LtsConstant.CODE_LKUP_EMAIL_SENDER_IGUARD_NAME_ENG
								: LtsConstant.CODE_LKUP_EMAIL_SENDER_IGUARD_NAME_CHN);
			}			
			return (String) ltsEmailSenderLkupCacheService
					.get(EsigEmailLang.ENG == locale ? LtsConstant.CODE_LKUP_EMAIL_SENDER_NAME_ENG
							: LtsConstant.CODE_LKUP_EMAIL_SENDER_NAME_CHN);
		}
		
		if (lob.equals("IMS"))
			try {
				return this.imsEmailParamHelperDAO.getSenderEmailName(locale,templateId, orderType);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				logger.error("Exception thrown in getting sender email name ",e);
				e.printStackTrace();
			}
		
		if (StringUtils.equalsIgnoreCase(lob, "MOB")) {
			String emailSenderName = getEmailSender(templateId, "SENDER_NAME");
			if (StringUtils.isNotBlank(emailSenderName)) {
				return emailSenderName;
			}
		}

			if ("1".equals(orderBrand)){ //csl orderBrand
				if ("HKT".equalsIgnoreCase(shopBrand)) {
					logger.info("sendEmailAddrConfig used: "+senderEmailNameHKTcsl);
					return this.senderEmailNameHKTcsl;
				}else if ("1010".equalsIgnoreCase(shopBrand)) {
					logger.info("sendEmailAddrConfig used: "+senderEmailName1010csl);
					return this.senderEmailName1010csl;
				}else if ("csl".equalsIgnoreCase(shopBrand) || shopBrand == null) {
					logger.info("sendEmailAddrConfig used: "+senderEmailNamecslcsl);
					return this.senderEmailNamecslcsl;
				}
			}else if ("0".equals(orderBrand)){ //1010 orderBrand
				logger.info("sendEmailAddrConfig used: "+senderEmailName10101010);
				return this.senderEmailName10101010;
			}
			
			
			/*if ("csl".equalsIgnoreCase(shopBrand)) {
				return this.senderEmailNameCsl;
			}*/
			logger.info("sendEmailAddrConfig used: "+senderEmailName);
		return this.senderEmailName;
	}
	
	public void sendOrderEmail(OrdEmailReqDTO dto) throws MailException, MessagingException, CompilationFailedException, ClassNotFoundException, IOException {
		EmailTemplateDTO emailTemplateDto = this.getEmailTemplateDTO(dto.getTemplateId(), dto.getLob(), dto.getLocale());

		SbOrderDTO ltsSbOrder = null;
		String orderBrand = null;
		String shopBrand = dto.getBrand();
		OrderDTO orderDto = null;
		
		if (StringUtils.equals(LtsConstant.LOB_LTS, dto.getLob())) {
			ltsSbOrder = orderRetrieveLtsService.retrieveSbOrder(dto.getOrderId(), true);	
			orderDto = this.orderService.getOrder(dto.getOrderId());
			if(orderDto.isRetail())
			{
				shopBrand = "Retail"; // use shopBrand to store "Retail"
			}
			else
			{
				shopBrand = "";
			}		}
		
		if (StringUtils.equals("MOB", dto.getLob())) {
			orderBrand = orderService.getOrderBrand(dto.getOrderId());
			if ("0".equals(orderBrand)){
				dto.setOrderBrand("1010");
			}else{
				dto.setOrderBrand("csl");
			}
			
			/*CustomerProfileDTO customerInfoDto = customerProfileService.getCustomerProfile(dto.getOrderId());
			if (customerInfoDto!=null){
				if ("1".equals(customerInfoDto.getBrand())){
					orderBrand = customerInfoDto.getBrand();
					dto.setOrderBrand("csl");
				}else if  ("0".equals(customerInfoDto.getBrand())){
					orderBrand = customerInfoDto.getBrand();
					dto.setOrderBrand("1010");
				}
			
			}*/
		}
		
		String subject = this.getEmailSubject(emailTemplateDto, dto, ltsSbOrder);
		String content = this.getEmailContent(emailTemplateDto, dto, ltsSbOrder);
		
		String senderEmailAddr = getSenderEmailAddress(dto.getLob(), dto.getTemplateId(), shopBrand, orderBrand);
		String senderEmailName = getSenderEmailName(dto.getLob(),dto.getLocale(), dto.getTemplateId(), shopBrand, orderBrand, dto.getOrderType());
		
		String YYYYMM = orderService.getSignOffDate(dto.getOrderId());
		
		if (logger.isInfoEnabled()) {
			logger.info("sender: " + senderEmailAddr + " " + senderEmailName);
			logger.info("subject: " + subject);
			logger.info("content: " + content);
		}
		List<File> attachments = new ArrayList<File>();
		if (StringUtils.isNotBlank(dto.getFilePathName1())) {
			String pathname="";
			if (StringUtils.equals("IMS", dto.getLob())) {
				pathname = reportServerPath + "ims" + File.separator + YYYYMM + File.separator + dto.getOrderId() + File.separator + dto.getFilePathName1();
				File f = new File(pathname);
				if(!f.exists()) { 
					logger.info("new filePath not exists:" + pathname);			
					pathname = reportServerPath + dto.getOrderId() + File.separator + dto.getFilePathName1();	
					f = new File(pathname);
					if(!f.exists()) { 
						logger.info("old filePath not exists:" + pathname);							
					}else{
						logger.info("old filePath exists:" + pathname);								
					}
				}else{
					logger.info("new filePath exists:" + pathname);					
				}
			}else{
				pathname = reportServerPath + File.separator + dto.getOrderId() + File.separator + dto.getFilePathName1();				
			}
			if (logger.isInfoEnabled()) {
				logger.info("filePathName1: " + pathname);
			}
			attachments.add(new File(pathname));
		}
		if (StringUtils.isNotBlank(dto.getFilePathName2())) {
			String pathname = reportServerPath + File.separator + dto.getOrderId() + File.separator + dto.getFilePathName2();
			if (logger.isInfoEnabled()) {
				logger.info("filePathName2: " + pathname);
			}
			attachments.add(new File(pathname));
		}
		if (StringUtils.isNotBlank(dto.getFilePathName3())) {
			String pathname = reportServerPath + File.separator + dto.getOrderId() + File.separator + dto.getFilePathName3();
			if (logger.isInfoEnabled()) {
				logger.info("filePathName3: " + pathname);
			}
			attachments.add(new File(pathname));
		}
		this.mailService.sendHtml(new InternetAddress(senderEmailAddr, senderEmailName, "UTF-8")
		, new InternetAddress(dto.getEmailAddr(), dto.getEmailAddr())
		, null
		, null
		, subject, content, this.prepareAttachments(dto, emailTemplateDto, ltsSbOrder));
	}
	
	/*
	 * --------------
	 * Bug in Groovy
	 * --------------
	 * When Chinese character immediately after variable
	 *    e.g.  $variable[Chinese char]
	 *    
	 * Groovy throws GroovyRuntimeException and fail to replace content
	 * 
	 * or use ${xxx} to represent variable
	 */
	private String replace(String templateContent, OrdEmailReqDTO dto) throws CompilationFailedException, ClassNotFoundException, IOException {
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		Template template = engine.createTemplate(templateContent);
	
		Map<String, Object> params = new HashMap<String, Object>();
		for (EmailTemplateVariable var : EmailTemplateVariable.values()) {
			String value = null;
			switch (var) {
			case EMAIL_ADDRESS:
				value = dto.getEsigEmailAddr();
				break;
			case CUSTOMER_TITLE:
				value = dto.getTitle();
				break;
			case CUSTOMER_NAME:
				value = dto.getContactName();
				break;
			case MSISDN:
				value = dto.getMsisdn();
				break;
			case APP_DATE:
				value = dto.getAppDate() == null ? null : Utility.date2String(dto.getAppDate(), BomWebPortalConstant.DATE_FORMAT);
				break;
			case CONTACT_PHONE:
				value = dto.getContactPhone();
				break;
			case SALES_NAME:
				value = dto.getSalesName();
				break;
			case SHOP_CD:
				value = dto.getShopCd();
				break;
			case BRAND:
				value = dto.getBrand();
				if ("MOB".equalsIgnoreCase(dto.getLob()) && dto.getBrand() == null) {
					params.put(var.getName(), "");
				}
				break;
			case SHOP_BRAND:
				//value = dto.getBrand();
				try{
				
					Locale locale = Locale.ENGLISH;
					if (EsigEmailLang.CHN == dto.getLocale()){
						locale = Locale.TRADITIONAL_CHINESE;
					}
					
					if ("MOB".equalsIgnoreCase(dto.getLob()) && dto.getBrand() == null) {
						
						value = mobMessageSource.getMessage("mob."+dto.getTemplateId()+".shopBrand.csl", null, locale);
						
					}else if ("MOB".equalsIgnoreCase(dto.getLob()) && dto.getBrand() != null) {
													
						value = mobMessageSource.getMessage("mob."+dto.getTemplateId()+".shopBrand."+dto.getBrand(), null, locale);
						
					}
				
				} catch (Exception ex){
					value = null;
					params.put(var.getName(), "");
				}
					
				break;
			case MOBILE_SERVICE_AGREEMENT_URL:
				//value = dto.getBrand();
				try{
					
					Locale locale = Locale.ENGLISH;
					if (EsigEmailLang.CHN == dto.getLocale()){
						locale = Locale.TRADITIONAL_CHINESE;
					}
					
					if ("MOB".equalsIgnoreCase(dto.getLob()) && dto.getOrderBrand() == null) {
						
						value = mobMessageSource.getMessage("mob."+dto.getTemplateId()+".mobileServiceAgreementUrl.csl", null, locale);
						
					}else if ("MOB".equalsIgnoreCase(dto.getLob()) && dto.getOrderBrand() != null) {
													
						value = mobMessageSource.getMessage("mob."+dto.getTemplateId()+".mobileServiceAgreementUrl."+dto.getOrderBrand(), null, locale);
						
					}
				
				} catch (Exception ex){
					value = null;
					params.put(var.getName(), "");
				}
				
				
				break;
			case PLAN:
				try{
					Locale locale = Locale.ENGLISH;
					if (EsigEmailLang.CHN == dto.getLocale()){
						locale = Locale.TRADITIONAL_CHINESE;
					}
					
					value = "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" style=\"text-align: left;\">";
					value += "<tbody>";
					
					List<BasketDTO> temp = new ArrayList<BasketDTO>();
					//hs
					temp = ordEmailReqService.getOnlineHSDetails(dto.getOrderId(),"zh".equalsIgnoreCase(locale.getLanguage()) ? "zh_HK" : "en");
					if (!CollectionUtils.isEmpty(temp)){
						value += "<tr><th colspan=\"2\" style=\"text-align: left;\">" + mobMessageSource.getMessage("mob."+dto.getTemplateId()+".device",null,locale) + "</th>";
						value += "<th style=\"text-align: right;\">" + mobMessageSource.getMessage("mob."+dto.getTemplateId()+".hs_price",null,locale) + "</th>";
						value += "<th style=\"text-align: right;\">" + mobMessageSource.getMessage("mob."+dto.getTemplateId()+".upfront",null,locale) + "</th></tr>";
								
						value += "<tr><td colspan=\"2\">";
						value += temp.get(0).getBrandDesc() + " " + temp.get(0).getModelDesc() + " " + temp.get(0).getColorDesc();
						value += "</td><td style=\"text-align: right;\">";
						value += NumberFormat.getCurrencyInstance().format(Double.parseDouble(temp.get(0).getRecurrentAmt()));
						value += "</td><td style=\"text-align: right;\">";
						value += NumberFormat.getCurrencyInstance().format(Double.parseDouble(temp.get(0).getUpfrontAmt()));
						value += "</td></tr>";
					}
					
					//service plan
					value += "<tr><th colspan=\"2\" style=\"text-align: left;\">" + mobMessageSource.getMessage("mob."+dto.getTemplateId()+".srv_plan",null,locale) + "</th>";
					value += "<th style=\"text-align: right;\">" + mobMessageSource.getMessage("mob."+dto.getTemplateId()+".table.header.monthly_fee",null,locale) + "</th>";
					value += "<th style=\"text-align: right;\">" + mobMessageSource.getMessage("mob."+dto.getTemplateId()+".upfront_first_payment",null,locale) + "</th></tr>";
					
					//rate plan desc, amt
					temp = ordEmailReqService.getOnlinePlanDetails(dto.getOrderId(),"RP","zh".equalsIgnoreCase(locale.getLanguage()) ? "zh_HK" : "en",null);
					if (!CollectionUtils.isEmpty(temp)) {
						for (BasketDTO ratePlan : temp) {
							value += "<tr><td colspan=\"2\">";
							value += ratePlan.getDescription();
							value += "</td><td style=\"text-align: right;\">";
							value += NumberFormat.getCurrencyInstance().format(Double.parseDouble(ratePlan.getRecurrentAmt()));
							value += "</td><td style=\"text-align: right;\">";
							value += NumberFormat.getCurrencyInstance().format(Double.parseDouble(ratePlan.getUpfrontAmt()));
							value += "</td></tr>";
						}
					}
					//bfee desc, amt
					temp = ordEmailReqService.getOnlinePlanDetails(dto.getOrderId(),"BFEE","zh".equalsIgnoreCase(locale.getLanguage()) ? "zh_HK" : "en",null);
					if (!CollectionUtils.isEmpty(temp)) {
						for (BasketDTO bFee : temp) {
							value += "<tr><td colspan=\"2\">";
							value += bFee.getDescription();
							value += "</td><td style=\"text-align: right;\">";
							value += NumberFormat.getCurrencyInstance().format(Double.parseDouble(bFee.getRecurrentAmt()));
							value += "</td><td style=\"text-align: right;\">";
							value += NumberFormat.getCurrencyInstance().format(Double.parseDouble(bFee.getUpfrontAmt()));
							value += "</td></tr>";
						}
					}
					
					//vas desc, amt
					temp = ordEmailReqService.getOnlinePlanDetails(dto.getOrderId(),"VAS","zh".equalsIgnoreCase(locale.getLanguage()) ? "zh_HK" : "en",null);
					if (temp.size()>0) {
						value += "<tr><th colspan=\"2\" style=\"text-align: left;\">" + mobMessageSource.getMessage("mob."+dto.getTemplateId()+".value_added_services",null,locale) + "</th>";
						value += "<th style=\"text-align: right;\">" + mobMessageSource.getMessage("mob."+dto.getTemplateId()+".table.header.monthly_fee",null,locale) + "</th>";
						value += "<th style=\"text-align: right;\">" + mobMessageSource.getMessage("mob."+dto.getTemplateId()+".upfront_first_payment",null,locale) + "</th></tr>";
						for (BasketDTO vas : temp) {
							value += "<tr><td colspan=\"2\">";
							value += vas.getDescription();
							value += "</td><td style=\"text-align: right;\">";
							value += NumberFormat.getCurrencyInstance().format(Double.parseDouble(vas.getRecurrentAmt()));
							value += "</td><td style=\"text-align: right;\">";
							value += NumberFormat.getCurrencyInstance().format(Double.parseDouble(vas.getUpfrontAmt()));
							value += "</td></tr>";
						}
					}
					
					//gift desc, amt
					temp = ordEmailReqService.getOnlinePlanDetails(dto.getOrderId(),"VAS","zh".equalsIgnoreCase(locale.getLanguage()) ? "zh_HK" : "en", "FG_CATEGORY");
					if (temp.size()>0) {
						value += "<tr><th colspan=\"2\" style=\"text-align: left;\">" + mobMessageSource.getMessage("mob."+dto.getTemplateId()+".table.header.free_gif",null,locale) + "</th>";
						value += "<th style=\"text-align: right;\">" +  mobMessageSource.getMessage("mob."+dto.getTemplateId()+".table.header.monthly_fee",null,locale) + "</th>";
						value += "<th style=\"text-align: right;\">" + mobMessageSource.getMessage("mob."+dto.getTemplateId()+".amount",null,locale) + "</th></tr>";
						for (BasketDTO free : temp) {
							value += "<tr><td colspan=\"2\">";
							value += free.getDescription();
							value += "</td><td style=\"text-align: right;\">";
							value += NumberFormat.getCurrencyInstance().format(Double.parseDouble(free.getRecurrentAmt()));
							value += "</td><td style=\"text-align: right;\">";
							value += NumberFormat.getCurrencyInstance().format(Double.parseDouble(free.getUpfrontAmt()));
							value += "</td></tr>";
						}
					}
					value += "</tbody></table>";
				} catch (Exception ex){
					value = null;
					params.put(var.getName(), "");
				}
				break;
			case ORDER_ID:
				value = dto.getOrderId();
				break;
			}
			if (value != null) {
				params.put(var.getName(), value);
			}
		}
		
		fillExtraProperties(params, dto);
		
		Writable writable = template.make(params);
		return writable.toString();
	}
	
	public String formatContentLts(String templateContent, SbOrderDTO sbOrder, String templateId){
		return formatContentLts(templateContent, sbOrder, templateId, null);
	}
	
	public String formatContentLts(String templateContent, SbOrderDTO sbOrder, String templateId, OrdEmailReqDTO ordEmailReqDto) {
		ServiceDetailDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
		
		String title = StringUtils.isBlank(ltsService.getAccount().getCustomer().getTitle())? " " : ltsService.getAccount().getCustomer().getTitle();
		String firstName = ltsService.getAccount().getCustomer().getFirstName();
		String lastName = ltsService.getAccount().getCustomer().getLastName();
		String salesContactNum = sbOrder.getSalesContactNum();
		String key = uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, sbOrder.getOrderId());
		String installDate = LtsDateFormatHelper.getDateFromDateTimeSlot(ltsService.getAppointmentDtl().getAppntStartTime());
		String installTime = LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(ltsService.getAppointmentDtl().getAppntStartTime(), ltsService.getAppointmentDtl().getAppntEndTime()));
		String locale = "ENG".equals(sbOrder.getLangPref()) ? LtsConstant.LOCALE_ENG : LtsConstant.LOCALE_CHI;
		
		/* Display SRD as TBC for eye resources shortage case*/
		if(LtsSbHelper.isEyeResourceShortage(sbOrder)
				&& (LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(sbOrder.getOrderType())
						|| LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())) ){
			String tbcMessage = null;
			if(StringUtils.equals(sbOrder.getLangPref(), "ENG")){
				tbcMessage = ltsMessageSource.getMessage("lts.rpt.tbc", null, null);
			}else{
				tbcMessage = ltsMessageSource.getMessage("lts.rpt.tbc.chi", null, null);
			}
			if(StringUtils.isNotBlank(tbcMessage)){
				installDate = tbcMessage;
				installTime = tbcMessage;
			}
		}
		
		if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES.equals(templateId) 
				|| LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_PORT_IN_ORDER.equals(templateId)
				|| LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_AMEND.equals(templateId) 
				|| LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_WQ.equals(templateId)) {	// Email Templates for EYE Service
				ServiceDetailDTO imsServiceDetail = LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls());	
				if(imsServiceDetail != null && StringUtils.equals(LtsBackendConstant.TECHNOLOGY_PON, ((ServiceDetailOtherLtsDTO)imsServiceDetail).getAssgnTechnology())){
					installTime = LtsDateFormatHelper.convertToPonTime(installTime);
				}
			}		
		
		if (LtsConstant.EMAIL_TEMPLATE_SIGNOFF.equals(templateId)) {
			return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, salesContactNum});
		}
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES.equals(templateId)){
			String srvUrl = this.srdAmendUrl + key;
			
			if(LtsBackendConstant.LOCALE_ENG.equals(locale)){
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, LtsBackendConstant.EMAIL_CONTENT_HEADER_EYE, LtsBackendConstant.EMAIL_CONTENT_PRIVACY_STATEMENT_EYE, installDate, installTime, srvUrl});
			}
			else{
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime, srvUrl});
			}
		}
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_DEL.equals(templateId)){
			String srvUrl = this.srdAmendUrl + key;
			if(LtsBackendConstant.LOCALE_ENG.equals(locale)){
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, LtsBackendConstant.EMAIL_CONTENT_HEADER_DEL, LtsBackendConstant.EMAIL_CONTENT_PRIVACY_STATEMENT_DEL, installDate, installTime, srvUrl});
			}
			else{
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime, srvUrl});
			}			
		}
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_PORT_IN_ORDER.equals(templateId)){
			String portInKey = uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, sbOrder.getOrderId() + "-EYE");
			String portInUrl = this.nsdFormUploadUrl + portInKey + "&amp;lang=" + locale;
			if(LtsBackendConstant.LOCALE_ENG.equals(locale)){
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, LtsBackendConstant.EMAIL_CONTENT_HEADER_EYE, LtsBackendConstant.EMAIL_CONTENT_PRIVACY_STATEMENT_EYE, installDate, installTime, portInUrl});
			}
			else{
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime, portInUrl});
			}
		}		
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_PORT_IN_ORDER_DEL.equals(templateId)){
			String portInKey = uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, sbOrder.getOrderId() + "-DEL");
			String portInUrl = this.nsdFormUploadUrl + portInKey + "&amp;lang=" + locale;
			if(LtsBackendConstant.LOCALE_ENG.equals(locale)){
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, LtsBackendConstant.EMAIL_CONTENT_HEADER_DEL, LtsBackendConstant.EMAIL_CONTENT_PRIVACY_STATEMENT_DEL, installDate, installTime, portInUrl});
			}
			else{
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime, portInUrl});
			}
		}
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_AMEND.equals(templateId)){
			return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime});
		}		
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_AMEND_DEL.equals(templateId)){
			return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime});
		}		
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_WQ.equals(templateId)){
			if(LtsBackendConstant.LOCALE_ENG.equals(locale)){
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, LtsBackendConstant.EMAIL_CONTENT_HEADER_DEL, LtsBackendConstant.EMAIL_CONTENT_PRIVACY_STATEMENT_DEL, installDate, installTime});
			}
			else{
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime});
			}			
		}		
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_WQ_DEL.equals(templateId)){
			if(LtsBackendConstant.LOCALE_ENG.equals(locale)){
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, LtsBackendConstant.EMAIL_CONTENT_HEADER_DEL, LtsBackendConstant.EMAIL_CONTENT_PRIVACY_STATEMENT_DEL, installDate, installTime});
			}
			else{
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime});
			}			
		}
		else if (LtsConstant.EMAIL_TEMPLATE_SIGNOFF_CALL_CENTER.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_PREMIER.equals(templateId)) {
			
			templateContent = MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime, this.srdAmendUrl + key});
			templateContent = StringUtils.replace(templateContent, "{3}", installDate);
			templateContent = StringUtils.replace(templateContent, "{4}", installTime);
			templateContent = StringUtils.replace(templateContent, "{5}", this.srdAmendUrl + key);
		}
		else if (LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_RETAIL.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_CALL_CENTER.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_PREMIER.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_DIRECT_SALES.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_ACQUISITION_DIRECT_SALES_WITH_PREPAYMENT.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_RETAIL.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_CALL_CENTER.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_PREMIER.equals(templateId)				
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_DIRECT_SALES.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_DEL_ACQUISITION_DIRECT_SALES_WITH_PREPAYMENT.equals(templateId)) {			
			templateContent = MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime, this.srdAmendUrl + key});
			templateContent = StringUtils.replace(templateContent, "{3}", installDate);
			templateContent = StringUtils.replace(templateContent, "{4}", installTime);
			templateContent = StringUtils.replace(templateContent, "{5}", this.srdAmendUrl + key);
		}
		else if (LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_RENEWAL.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_EYE_RENEWAL_PREMIER.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_RENEWAL_DEL.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_RENEWAL_PREMIER_DEL.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_MASS.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_CALL_CENTER.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_RETAIL.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_SIGNOFF_STANDALONE_VAS_PREMIER.equals(templateId)){
			templateContent = MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate});
			templateContent = StringUtils.replace(templateContent, "[DATE]", installDate);			
		}else if (Arrays.asList(LtsConstant.LTS_AMEND_NOTICE_TEMPLATE_IDS).contains(templateId)){
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String reqDate = df.format(ordEmailReqDto.getRequestDate());
			templateContent = MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, reqDate, salesContactNum});
		}
		return templateContent;
	}
	
	public String formatSubjectLts(String templateSubject, SbOrderDTO sbOrder) {
		ServiceDetailDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
		String srvNum = ltsService.getSrvNum(); 
		return MessageFormat.format(templateSubject, new Object[] { srvNum });
	}
	
	//eSignature CR by Eric Ng
	private String formatContentIms(String templateContent,OrdEmailReqDTO dto)throws CompilationFailedException, ClassNotFoundException, IOException {
		
		
		String order_id = dto.getOrderId();
		OrderImsUI imsOrder = imsOrderDetailService.getOrder(order_id);
		
		String[] installation = imsOrder.getInstallationDisplayTimeString();
		String installationDate = installation[0];
		String installationStartTime = installation[1];
		String installationEndTime = installation[2];
		String login_id = imsOrder.getLoginId();
		String IdDocType = imsOrder.getCustomer().getIdDocType();
		
		logger.info("AppntStartDateDisplay: " +  imsOrder.getAppointment().getAppntStartDateDisplay() + " AppntEndDateDisplay: " +  imsOrder.getAppointment().getAppntEndDateDisplay());
		logger.info("installationStartTime: " + installationStartTime + " installationEndTime: " + installationEndTime);
			
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yyyyMMformatter =  new SimpleDateFormat("yyyyMM");
		
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		Template template = engine.createTemplate(templateContent);
	
		Map<String, String> params = new HashMap<String, String>();
		for (ImsEmailTemplateVariable var : ImsEmailTemplateVariable.values()) {
			String value = null;
			switch (var) {
			case EMAIL_ADDRESS:
				value = dto.getEsigEmailAddr();
				break;
			case CUSTOMER_TITLE:
				if ("BS".equals(IdDocType))
				{
					value = "";
				}
				else
					value = dto.getTitle();
				break;
			case CUSTOMER_NAME:
				if ("BS".equals(IdDocType))
				{
					value = imsOrder.getCustomer().getCompanyName();
				}
				else
					value = dto.getContactName();
				break;
			case MSISDN:
				//value = dto.getMsisdn();
				value =  login_id; // Ad-hoc use msisdn to represent login_id
				break;
//			case APP_DATE:
//				value = dto.getAppDate() == null ? null : Utility.date2String(dto.getAppDate(), BomWebPortalConstant.DATE_FORMAT);
//				break;
			case CONTACT_PHONE:
				value = dto.getContactPhone();
				break;
			case LOGIN_ID:
				value =  login_id; 
				break;
			case INSTALLATION_DATE:
				value = installationDate;
				break;
			case INSTALLATION_STARTTIME:
				value = installationStartTime;
				break;
			case INSTALLATION_ENDTIME:
				value = installationEndTime;
				break;
			case EMAIL_WEB100_LINK:
				
				if(imsOrder.getImsOrderType()!= null && imsOrder.getImsOrderType().equals("TV_I"))
					value = emailWEB100Link_standalone;
				else
					value = emailWEB100Link;
					
				value += "?lang="
				+ (dto.getLocale() == EsigEmailLang.CHN ? "zh" : "en")
				+ "&order_id=" +uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, dto.getOrderId()) ;
				
				logger.info("SRD link: " + value);
				
				break;
			case CUSTOMER_CHN_NAME:
				value = dto.getContactName();
				break;
			case CUSTOMER_CHN_TITLE:
				value = dto.getTitle();
				break;
			case ORDER_ID:
				value = dto.getOrderId();
				break;
//			case SALES_NAME:
//				value = dto.getSalesName();
//				break;
//			case SHOP_CD:
//				value = dto.getShopCd();
//				break;
			case HTTPS:
				if(imsOrder.isOrderTypeNowRet()){
					value=imsOrder.getShortenUrlAf();
				} else if ("NTV-A".equals(dto.getOrderType())) {
					String YYYYMM = imsOrder.getSignOffDate()!=null?yyyyMMformatter.format(imsOrder.getSignOffDate()):"";
					if (imsOrder.getShortenUrlAf()==null || "".equals(imsOrder.getShortenUrlAf())) {
						value = SMSAFPath + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, dto.getOrderId()) + "&SignOffDate=" + YYYYMM + " ";
					} else {
						value=imsOrder.getShortenUrlAf();
					}
				} else{
					value = SMSAFPath + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, dto.getOrderId()) + " ";
				}
				break;
			case HTTPQ:
				if(dto.getOrderType()!=null && ( 
						dto.getOrderType().equalsIgnoreCase("NTVAO") || 
						dto.getOrderType().equalsIgnoreCase("NTVRE")|| 
						dto.getOrderType().equalsIgnoreCase("NTVUS") )) {
					value = CPQSMSQRPath + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, dto.getOrderId()) + " ";
				} else if ("NTV-A".equals(dto.getOrderType())) {
					String YYYYMM = imsOrder.getSignOffDate()!=null?yyyyMMformatter.format(imsOrder.getSignOffDate()):"";
					if (imsOrder.getShortenUrlQr()==null || "".equals(imsOrder.getShortenUrlQr())) {
						value = CPQSMSQRPath + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, dto.getOrderId()) + "&SignOffDate=" + YYYYMM + " ";
					} else {
						value = imsOrder.getShortenUrlQr();
					}
				} else 
					value = SMSQRPath + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, dto.getOrderId()) + " ";
				break;
			case REQUEST_DATE:
				value = formatter.format(dto.getRequestDate());
				break;
			}
		
			if (value != null) {
				params.put(var.getName(), value);
			}
		}
		Writable writable = template.make(params);
		return writable.toString();
	}
	
	private Map<String, InputStreamSource> prepareAttachments(OrdEmailReqDTO ordEmailReqDto, EmailTemplateDTO emailTemplateDto, SbOrderDTO ltsSbOrder) throws IOException {
		
		String YYYYMM = orderService.getSignOffDate(ordEmailReqDto.getOrderId());
		List<File> files = new ArrayList<File>();
		if (StringUtils.isNotBlank(ordEmailReqDto.getFilePathName1())) {
			String pathname = reportServerPath + File.separator + ordEmailReqDto.getOrderId() + File.separator + ordEmailReqDto.getFilePathName1();
			if("IMS".equals(ordEmailReqDto.getLob())){
				pathname = reportServerPath + "ims" + File.separator + YYYYMM + File.separator + ordEmailReqDto.getOrderId() + File.separator + ordEmailReqDto.getFilePathName1();
				File f = new File(pathname);
				if(!f.exists()) { 
					logger.info("new filePath not exists:" + pathname);			
					pathname = reportServerPath + File.separator + ordEmailReqDto.getOrderId() + File.separator + ordEmailReqDto.getFilePathName1();	
					f = new File(pathname);
					if(!f.exists()) { 
						logger.info("old filePath not exists:" + pathname);							
					}else{
						logger.info("old filePath exists:" + pathname);								
					}
				}else{
					logger.info("new filePath exists:" + pathname);					
				}
			}
			if (logger.isInfoEnabled()) {
				logger.info("filePathName1: " + pathname);
				//logger.info("filePathNametest: " + pathnametest);
			}
			files.add(new File(pathname));
		}
		if (StringUtils.isNotBlank(ordEmailReqDto.getFilePathName2())) {
			String pathname = reportServerPath + File.separator + ordEmailReqDto.getOrderId() + File.separator + ordEmailReqDto.getFilePathName2();
			if (logger.isInfoEnabled()) {
				logger.info("filePathName2: " + pathname);
			}
			files.add(new File(pathname));
		}
		if (StringUtils.isNotBlank(ordEmailReqDto.getFilePathName3())) {
			String pathname = reportServerPath + File.separator + ordEmailReqDto.getOrderId() + File.separator + ordEmailReqDto.getFilePathName3();
			if (logger.isInfoEnabled()) {
				logger.info("filePathName3: " + pathname);
			}
			files.add(new File(pathname));
		}
		if (this.isEmpty(files)) {
			return Collections.emptyMap();
		}
		
		Map<String, InputStreamSource> attachments = new LinkedHashMap<String, InputStreamSource>();
		if (requiredEncryptFile(emailTemplateDto)) {
			final String encryptFilePassword = this.getEncryptFilePassword(ordEmailReqDto, ltsSbOrder);
			for (File file : files) {
				attachments.put(file.getName(), this.encryptFile(file, encryptFilePassword));
			}
		} else {
			for (File file : files) {
				attachments.put(file.getName(), this.streamFile(file));
			}
		}
		return attachments;
	}
	
	private InputStreamSource streamFile(File file) throws IOException {
		if (!file.exists()) {
			throw new FileNotFoundException("File not found in " + file.getAbsolutePath());
		}
		
		return new FileSystemResource(file);
	}
	
	private InputStreamSource encryptFile(File file, String password) throws IOException {
		if (!file.exists()) {
			throw new FileNotFoundException("File not found in " + file.getAbsolutePath());
		}
		
		if ("pdf".equalsIgnoreCase(getExtension(file))) {
			return encryptPdfFile(file, password);
		}
		
		return new FileSystemResource(file);
	}

	private InputStreamSource encryptPdfFile(File file, String password) throws IOException {
		InputStream fis = null;
		ByteArrayOutputStream fbaos = new ByteArrayOutputStream();
		try {
			fis = new FileInputStream(file);
			PdfUtil.concatPDFs(Arrays.asList(fis), fbaos, false, false, null, PdfUtil.ALLOW_SCREENREADERS | PdfUtil.ALLOW_PRINTING, password, password);
			fis.close();
			fis = null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {}
				fis = null;
			}
		}
		return new ByteArrayResource(fbaos.toByteArray(), file.getName());
	}

	
	private boolean requiredEncryptFile(EmailTemplateDTO emailTemplateDto) {
		if (PdfPwdInd.Y.equals(emailTemplateDto.getPdfPwdInd())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Retrieve password with length = 6, if fail to retrieve password, throws InvalidEncryptPasswordException
	 */
	private String getEncryptFilePassword(OrdEmailReqDTO dto, SbOrderDTO ltsSbOrder) {
		String password = null;
		if ("MOB".equals(dto.getLob())) {
			CustomerProfileDTO customerProfileDto = null;
			if ("COS".equalsIgnoreCase(dto.getOrderType()) || "CSIM".equalsIgnoreCase(dto.getOrderType()) || "BRM".equals(dto.getOrderType()) || "TOO1".equals(dto.getOrderType())|| "CPM".equalsIgnoreCase(dto.getOrderType())) {
				customerProfileDto = this.customerProfileService.getCosCustomerProfile(dto.getOrderId());
			} else {
				customerProfileDto = this.customerProfileService.getCustomerProfile(dto.getOrderId());
			}
			
			password = customerProfileDto.getIdDocNum();
		} else if (StringUtils.equals(LtsConstant.LOB_LTS, dto.getLob())) {
			ServiceDetailDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(ltsSbOrder);
			if (ltsServiceDetail.getRecontract() != null && StringUtils.equals("Y", ltsServiceDetail.getRecontractInd())) {
				password = ltsServiceDetail.getRecontract().getIdDocNum();
			
			}
			else {
				password = ltsServiceDetail.getActualDocId();	
			}
		} else if ("IMS".equals(dto.getLob())){
			try {
				password = imsEmailParamHelperDAO.getCustomerIDNum(dto.getOrderId());
			} catch (DAOException e) {
				logger.error("Exception thrown in getting customer id number ", e);
				//e.printStackTrace();
			}
		}
		
		if (StringUtils.isBlank(password) || password.length() < 6) {
			logger.warn("password length: " + (password == null ? -1 : password.length()));
			throw new InvalidEncryptPasswordLengthException();
		}
		password = password.substring(0, 6).toUpperCase();
		final String passwordPattern = "^[A-Z0-9]{6}$";
		if (!password.matches(passwordPattern)) {
			logger.warn("password: " + password + " not match with pattern: " + passwordPattern);
			throw new InvalidEncryptPasswordException();
		}
		return password;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	private String getExtension(File file) {
		int index = file.getName().lastIndexOf('.');
		if (index == -1) {
			return null;
		}
		return file.getName().substring(index + 1);
	}
	
	private EmailReqResult parseException(OrdEmailReqDTO dto, Exception e) {
		if (e instanceof MailSendException) {
			return EmailReqResult.MAIL_SEND_EXCEPTION;
		}
		if (e instanceof FileNotFoundException) {
			return EmailReqResult.ATTACHMENT_NOT_FOUND;
		}
		if (e instanceof InvalidEncryptPasswordLengthException) {
			// mob customized message
			if ("MOB".equals(dto.getLob())) {
				return EmailReqResult.INVALID_ENCRYPT_PASSWORD_LENGTH_MOB;
			}
			return EmailReqResult.INVALID_ENCRYPT_PASSWORD_LENGTH;
		}
		if (e instanceof InvalidEncryptPasswordException) {
			return EmailReqResult.INVALID_ENCRYPT_PASSWORD;
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
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult createNoEmailReqRecord(String orderId, String templateId,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy) {

		OrderDTO orderDto = this.orderService.getOrder(orderId);
		if (orderDto.isRetail()){//only retail check DisMode =E
			if (!DisMode.E.equals(orderDto.getDisMode()) ) {
				logger.warn("orderId: " + orderId + ", disMode: " + orderDto.getDisMode() + " not allowed to send email");
				return EmailReqResult.NOT_ESIGN_ORDER;
			}
		}
		
		Date now = new Date();
		OrdEmailReqDTO dto = new OrdEmailReqDTO();
		dto.setOrderId(orderId);
		dto.setTemplateId(templateId);
		dto.setRequestDate(requestDate);
		dto.setFilePathName1(filePathName1);
		dto.setFilePathName2(filePathName2);
		dto.setFilePathName3(filePathName3);
		dto.setCreateBy(createBy);
		dto.setCreateDate(now);
		dto.setLastUpdBy(createBy);
		dto.setLastUpdDate(now);
		dto.setEmailAddr(emailAddr);
		//dto.setLocale(locale);
		// get lob, esignLang from orderDto
		dto.setLob(orderDto.getOrderSumLob());
		dto.setLocale(orderDto.getEsigEmailLang());
		dto.setMethod(DisMode.E);
		
		dto.setErrMsg("No Email Address provide");
		
		//OrdEmailReqDTO insertedDto;
		try {
			int seqNo = this.ordEmailReqService.getNextSeq(orderId,templateId);
			dto.setSeqNo(seqNo);
			this.ordEmailReqService.insertOrdEmailReq(dto);
			//insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId);
		} catch (Exception e) {
			logger.warn("Exception in insertOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}

		return EmailReqResult.EMPTY_EMAIL_ADDR;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult createNoEmailReqRecordLts(String orderId, String templateId, int seqNo,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy, long printReqId) {

		OrderDTO orderDto = this.orderService.getOrder(orderId);
		if (orderDto.isRetail()){//only retail check DisMode =E
			if (!DisMode.E.equals(orderDto.getDisMode()) ) {
				logger.warn("orderId: " + orderId + ", disMode: " + orderDto.getDisMode() + " not allowed to send email");
				return EmailReqResult.NOT_ESIGN_ORDER;
			}
		}
		
		Date now = new Date();
		OrdEmailReqDTO dto = new OrdEmailReqDTO();
		dto.setOrderId(orderId);
		dto.setTemplateId(templateId);
		dto.setRequestDate(requestDate);
		dto.setFilePathName1(filePathName1);
		dto.setFilePathName2(filePathName2);
		dto.setFilePathName3(filePathName3);
		dto.setCreateBy(createBy);
		dto.setCreateDate(now);
		dto.setLastUpdBy(createBy);
		dto.setLastUpdDate(now);
		dto.setEmailAddr(emailAddr);
		dto.setSeqNo(seqNo);
		//dto.setLocale(locale);
		// get lob, esignLang from orderDto
		dto.setLob(orderDto.getOrderSumLob());
		dto.setLocale(orderDto.getEsigEmailLang());
		dto.setMethod(DisMode.E);
		
		dto.setErrMsg("No Email Address provide");
		
		dto.setPrintReqId(printReqId);
		
		//OrdEmailReqDTO insertedDto;
//		try {
//			int seqNo = this.ordEmailReqService.getNextSeq(orderId,templateId);
//			dto.setSeqNo(seqNo);
//			this.ordEmailReqService.insertOrdEmailReq(dto);
//			//insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId);
//		} catch (Exception e) {
//			logger.warn("Exception in insertOrdEmailReq", e);
//			throw new AppRuntimeException(e == null ? "" : e.getMessage());
//		}

		return EmailReqResult.EMPTY_EMAIL_ADDR;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult createNoEmailReqRecordIMS(String orderId, String templateId,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy) {

		OrderDTO orderDto = this.orderService.getOrder(orderId);
		if (orderDto.isRetail()){//only retail check DisMode =E
			if (!DisMode.E.equals(orderDto.getDisMode()) ) {
				logger.warn("orderId: " + orderId + ", disMode: " + orderDto.getDisMode() + " not allowed to send email");
				return EmailReqResult.NOT_ESIGN_ORDER;
			}
		}
		
		Date now = new Date();
		OrdEmailReqDTO dto = new OrdEmailReqDTO();
		dto.setOrderId(orderId);
		dto.setTemplateId(templateId);
		dto.setRequestDate(requestDate);
		dto.setFilePathName1(filePathName1);
		dto.setFilePathName2(filePathName2);
		dto.setFilePathName3(filePathName3);
		dto.setCreateBy(createBy);
		dto.setCreateDate(now);
		dto.setLastUpdBy(createBy);
		dto.setLastUpdDate(now);
		dto.setEmailAddr(emailAddr);
		//dto.setLocale(locale);
		// get lob, esignLang from orderDto
		dto.setLob(orderDto.getOrderSumLob());
		dto.setLocale(orderDto.getEsigEmailLang());
		dto.setMethod(DisMode.I);
		dto.setErrMsg("No Email Address provide");
		
		//OrdEmailReqDTO insertedDto;
		try {
			int seqNo = this.ordEmailReqService.getNextSeqIMS(orderId);
			dto.setSeqNo(seqNo);
			this.ordEmailReqService.insertOrdEmailReq(dto);
			//insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId);
		} catch (Exception e) {
			logger.warn("Exception in insertOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}

		return EmailReqResult.EMPTY_EMAIL_ADDR;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult createEmailReq(String orderId, String templateId,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy){
		if (StringUtils.isEmpty(emailAddr)){
			return this.createNoEmailReqRecord(orderId, templateId, requestDate, filePathName1, filePathName2, filePathName3, emailAddr, createBy);
		}else{
			return this.createEmailReqAndSendEmail(orderId, templateId, requestDate, filePathName1, filePathName2, filePathName3, emailAddr, createBy);
		}
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult createEmailReqIMS(String orderId, String templateId,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy){
		if (StringUtils.isEmpty(emailAddr)){
			return this.createNoEmailReqRecordIMS(orderId, templateId, requestDate, filePathName1, filePathName2, filePathName3, emailAddr, createBy);
		}else{
			return this.createEmailReqAndSendEmailIMS(orderId, templateId, requestDate, filePathName1, filePathName2, filePathName3, emailAddr, createBy);
		}
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult createEmailReqLts(String orderId, String templateId, int seqNo,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy, long printReqId){
		if (StringUtils.isEmpty(emailAddr)){
			return this.createNoEmailReqRecordLts(orderId, templateId, seqNo, requestDate, filePathName1, filePathName2, filePathName3, emailAddr, createBy, printReqId);
		}else{
			return this.createEmailReqAndSendEmailLts(orderId, templateId, seqNo, requestDate, filePathName1, filePathName2, filePathName3, emailAddr, createBy, printReqId);
		}
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public OrdEmailReqDTO createSmsReqRecord(String orderId, String templateId,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String smsNo,
			int seqNo,
			String createBy, long printRequestId) {

		OrderDTO orderDto = this.orderService.getOrder(orderId);
		
		Date now = new Date();
		OrdEmailReqDTO dto = new OrdEmailReqDTO();
		dto.setOrderId(orderId);
		dto.setTemplateId(templateId);
		dto.setRequestDate(requestDate);
		dto.setFilePathName1(filePathName1);
		dto.setFilePathName2(filePathName2);
		dto.setFilePathName3(filePathName3);
		dto.setCreateBy(createBy);
		dto.setCreateDate(now);
		dto.setLastUpdBy(createBy);
		dto.setLastUpdDate(now);
//		dto.setEmailAddr();
		//dto.setLocale(locale);
		// get lob, esignLang from orderDto
		dto.setLob(orderDto.getOrderSumLob());
		dto.setLocale(orderDto.getEsigEmailLang());
		dto.setSMSno(smsNo);
		dto.setMethod(DisMode.S);
		dto.setPrintReqId(printRequestId);
		dto.setSeqNo(seqNo);
		//OrdEmailReqDTO insertedDto;
		try {
//			int seqNo = this.ordEmailReqService.getNextSeq(orderId,templateId);
//			dto.setSeqNo(seqNo);
			this.ordEmailReqService.insertOrdSMSReq(dto);
			//insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId);
		} catch (Exception e) {
			logger.warn("Exception in insertOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}

		return dto;
	}
	
	
	public EmailReqResult updateOrdEmailReq(String orderId, int seqNo, String templateId)  {
		
		OrdEmailReqDTO insertedDto;
		try {
			insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId);
		} catch (Exception e) {
			logger.warn("Exception in insertOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}
		
		try {
			insertedDto.setSentDate(new Date());
			insertedDto.setErrMsg(null);
			this.ordEmailReqService.updateOrdEmailReq(insertedDto);
		} catch (Exception e) {
			logger.warn("Exception in updateOrdEmailReq", e);
			return EmailReqResult.FAIL;
		}
		return EmailReqResult.SUCCESS;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////
	// Handling of Specific email template by defining a corresponding template handlers or plugins .......
	// The handler is a bean of EmailTemplateHandler with name  "template.<LOB>.<TEMPLATEID>"
	/////////////////////////////////////////////////////////////////////////////////////
	
	private EmailTemplateHandler getTemplateHandler(String lob, String templateId) {
		// bean name = template.MOB.RT001  ...

		String beanName = "template."+lob+"." + templateId;
		if (context.containsBean(beanName)) {
			return  (EmailTemplateHandler)context.getBean(beanName);
		}
		return null;
	}
	
	private void beforeEmailSent(OrdEmailReqDTO dto) {
		EmailTemplateHandler handler = getTemplateHandler(dto.getLob(), dto.getTemplateId());
		if (handler == null) return;
		
		handler.beforeEmailSent(dto);
	}
	
	private void fillExtraProperties(Map<String,Object> map, OrdEmailReqDTO dto) {
		map.put("context", context);
		String lob = dto.getLob();
		String templateId = dto.getTemplateId();
		
		EmailTemplateHandler handler = getTemplateHandler(lob, templateId);
		try {
			if (handler != null) {
				Map<String,?> extra = handler.getSubjectProperties(dto);
				if (extra != null) {
					map.putAll(extra);
				}
				extra = handler.getContentProperties(dto);
				if (extra != null) {
					map.putAll(extra);
				}
			}
		} catch (Exception e) {
			logger.error("Failed to load extra template data ...");
		}
	}
	
	private void afterEmailSent(OrdEmailReqDTO dto) {
		EmailTemplateHandler handler = getTemplateHandler(dto.getLob(), dto.getTemplateId());
		if (handler == null) return;
		
		handler.afterEmailSent(dto);
	}
	
	/**
	 * BOMWEB_CODE_LKUP.CODE_TYPE = "EMAIL_SENDER_NAME_ADDR"
	 * BOMWEB_CODE_LKUP.CODE_ID = (email template id)
	 * BOMWEB_CODE_LKUP.CODE_DESC = ("SENDER_NAME" <SENDER_ADDRESS>)
	 * @param templateId
	 * @param type ("SENDER_NAME" | "SENDER_ADDRESS")
	 * @return
	 */
	private String getEmailSender(String templateId, String type) {
		
		List<CodeLkupDTO> emailSenderNameAddressList = 
				LookupTableBean.getInstance().getCodeLkupList().get("EMAIL_SENDER_NAME_ADDR");
		
		if (!CollectionUtils.isEmpty(emailSenderNameAddressList)) {
			for (CodeLkupDTO emailSenderNameAddressDTO : emailSenderNameAddressList) {
				if (StringUtils.equalsIgnoreCase(templateId, emailSenderNameAddressDTO.getCodeId())) {
					String emailSenderNameAddress = emailSenderNameAddressDTO.getCodeDesc();
					Pattern pattern = null;
					if (StringUtils.equalsIgnoreCase("SENDER_NAME", type)) {
						pattern = Pattern.compile("\"(.*?)\"");
					} else {
						pattern = Pattern.compile("<(.*?)>");
					}
					Matcher matcher = pattern.matcher(emailSenderNameAddress);
					if (matcher.find()) {
						String result = matcher.group(1);
						if (StringUtils.isNotBlank(result)) {
							return result;
						}
					}
				}
			}
		}
		return "";
	}
}
