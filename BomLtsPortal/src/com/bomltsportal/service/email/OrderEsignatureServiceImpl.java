package com.bomltsportal.service.email;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.bomltsportal.dao.email.EmailTemplateDAO;
import com.bomltsportal.dto.email.EmailTemplateDTO;
import com.bomltsportal.dto.email.EmailTemplateDTO.PdfPwdInd;
import com.bomltsportal.dto.email.OrdEmailReqDTO;
import com.bomltsportal.dto.email.OrderDTO;
import com.bomltsportal.dto.email.OrderDTO.DisMode;
import com.bomltsportal.dto.email.OrderDTO.EsigEmailLang;
import com.bomltsportal.service.AmendmentService;
import com.bomltsportal.service.email.OrdEmailReqService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.LtsDateFormatHelper;
import com.bomltsportal.util.SbOrderHelper;
import com.bomltsportal.util.uENC;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.util.PdfUtil;

@Transactional(readOnly = true)
public class OrderEsignatureServiceImpl implements OrderEsignatureService {
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
		;
		private EmailTemplateVariable(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		private String name;
	}
	
	private MailService mailService;
	private OrderService orderService;
	private OrdEmailReqService ordEmailReqService;
	private AmendmentService amendmentService;
	
	// LTS
	private CodeLkupCacheService ltsEmailSenderLkupCacheService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	
	private EmailTemplateDAO emailTemplateDAO;
	
	private String senderEmailAddr;
	private String senderEmailName;
	private String emailServerPath; //dataFilePath
	private String srdAmendUrl;  // SRD Amendment URL
	private String nsdFormUploadUrl;  // NSD Form Upload: (For port-in order) URL
	
	public AmendmentService getAmendmentService() {
		return amendmentService;
	}

	public void setAmendmentService(AmendmentService amendmentService) {
		this.amendmentService = amendmentService;
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

	public String getEmailServerPath() {
		return emailServerPath;
	}

	public void setEmailServerPath(String emailServerPath) {
		this.emailServerPath = emailServerPath;
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

	public String getEmailSubject(EmailTemplateDTO emailTemplateDto, OrdEmailReqDTO ordEmailReqDto, SbOrderDTO sbOrder) throws CompilationFailedException, ClassNotFoundException, IOException {
		if (StringUtils.equals(LtsBackendConstant.LOB_LTS, emailTemplateDto.getLob())) {
			return formatSubjectLts(emailTemplateDto.getTemplateSubject(), sbOrder);
		}
		return this.replace(emailTemplateDto.getTemplateSubject(), ordEmailReqDto);
	}
	
	public String getEmailContent(EmailTemplateDTO emailTemplateDto, OrdEmailReqDTO ordEmailReqDto, SbOrderDTO sbOrder) throws CompilationFailedException, ClassNotFoundException, IOException {
		if (StringUtils.equals(LtsBackendConstant.LOB_LTS, emailTemplateDto.getLob())) {
			return formatContentLts(emailTemplateDto.getTemplateContent(), sbOrder, emailTemplateDto.getTemplateId());
		}
		return this.replace(emailTemplateDto.getTemplateContent(), ordEmailReqDto);
	}

	public EmailTemplateDTO getEmailTemplateDTO(String templateId, String lob,
			EsigEmailLang locale) {
		return this.emailTemplateDAO.getEmailTemplateDTO(templateId, lob, locale);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult createEmailReq(String orderId, String templateId,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String emailLang,
			String createBy) {
		if (StringUtils.isBlank(emailAddr)) {
			logger.warn("emailAddr is blank");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		if (!SbOrderHelper.validateEmail(emailAddr)) {
			logger.warn("emailAddr is not valid");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		OrderDTO orderDto = this.orderService.getOrder(orderId);
		if (!DisMode.E.equals(orderDto.getDisMode())) {
			logger.warn("orderId: " + orderId + ", disMode: " + orderDto.getDisMode() + " not allowed to send email");
			return EmailReqResult.NOT_ESIGN_ORDER;
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
		dto.setLocale(EsigEmailLang.valueOf(emailLang));
		
		OrdEmailReqDTO insertedDto;
		try {
			int seqNo = this.ordEmailReqService.getNextSeq(orderId,templateId);
			dto.setSeqNo(seqNo);
			this.ordEmailReqService.insertOrdEmailReq(dto);
			insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(orderId, seqNo, templateId);
		} catch (Exception e) {
			logger.warn("Exception in insertOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}

		EmailReqResult result;
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
			insertedDto.setErrMsg(errMsg.length() > 100 ? errMsg.substring(0, 100) : errMsg.toString());
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
		return result;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult resendEmailReq(String orderId, String templateId,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String emailLang,
			String createBy) {
		if (StringUtils.isBlank(emailAddr)) {
			logger.warn("emailAddr is blank");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		if (!SbOrderHelper.validateEmail(emailAddr)) {
			logger.warn("emailAddr is not valid");
			return EmailReqResult.INVALID_EMAIL_ADDR;
		}
		this.orderService.updateEsignEmailAddr(orderId, emailAddr, createBy);
		return this.createEmailReq(orderId
				, templateId, new Date()
				, filePathName1, filePathName2, filePathName3
				, emailAddr, emailLang, createBy);
	}
	

	private String getSenderEmailAddress(String lob) {
		if (StringUtils.equals(lob, LtsBackendConstant.LOB_LTS)) {
			return (String)ltsEmailSenderLkupCacheService.get(BomLtsConstant.CODE_LKUP_EMAIL_SENDER_ADDRESS);
		}
		
		return this.senderEmailAddr;
	}
	
	private String getSenderEmailName(String lob, EsigEmailLang locale, String templateId) {
		if (StringUtils.equals(lob, LtsBackendConstant.LOB_LTS)) {
			return (String) ltsEmailSenderLkupCacheService
					.get(EsigEmailLang.ENG == locale ? BomLtsConstant.CODE_LKUP_EMAIL_SENDER_NAME_ENG
							: BomLtsConstant.CODE_LKUP_EMAIL_SENDER_NAME_CHN);
		}
		
		return this.senderEmailName;
	}
	
	private void sendOrderEmail(OrdEmailReqDTO dto) throws MailException, MessagingException, CompilationFailedException, ClassNotFoundException, IOException {
		EmailTemplateDTO emailTemplateDto = this.getEmailTemplateDTO(dto.getTemplateId(), dto.getLob(), dto.getLocale());

		SbOrderDTO ltsSbOrder = null;
		
		
		if (StringUtils.equals(LtsBackendConstant.LOB_LTS, dto.getLob())) {
			ltsSbOrder = orderRetrieveLtsService.retrieveSbOrder(dto.getOrderId(), true);	
		}
		
		String subject = this.getEmailSubject(emailTemplateDto, dto, ltsSbOrder);
		String content = this.getEmailContent(emailTemplateDto, dto, ltsSbOrder);
		
		String senderEmailAddr = getSenderEmailAddress(dto.getLob());
		String senderEmailName = getSenderEmailName(dto.getLob(),dto.getLocale(), dto.getTemplateId());
		
		if (logger.isInfoEnabled()) {
			logger.info("sender: " + senderEmailAddr + " " + senderEmailName);
			logger.info("subject: " + subject);
			logger.info("content: " + content);
		}
		List<File> attachments = new ArrayList<File>();
		if (StringUtils.isNotBlank(dto.getFilePathName1())) {
			String pathname = emailServerPath + File.separator + dto.getOrderId() + File.separator + dto.getFilePathName1();
			if (logger.isInfoEnabled()) {
				logger.info("filePathName1: " + pathname);
			}
			attachments.add(new File(pathname));
		}
		if (StringUtils.isNotBlank(dto.getFilePathName2())) {
			String pathname = emailServerPath + File.separator + dto.getOrderId() + File.separator + dto.getFilePathName2();
			if (logger.isInfoEnabled()) {
				logger.info("filePathName2: " + pathname);
			}
			attachments.add(new File(pathname));
		}
		if (StringUtils.isNotBlank(dto.getFilePathName3())) {
			String pathname = emailServerPath + File.separator + dto.getOrderId() + File.separator + dto.getFilePathName3();
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
	
		Map<String, String> params = new HashMap<String, String>();
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
				value = dto.getAppDate() == null ? null : LtsDateFormatHelper.date2String(dto.getAppDate(), BomLtsConstant.DATE_FORMAT);
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
			}
			if (value != null) {
				params.put(var.getName(), value);
			}
		}
		Writable writable = template.make(params);
		return writable.toString();
	}
	
	private String formatContentLts(String templateContent, SbOrderDTO sbOrder, String pTemplateId) {
		ServiceDetailDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
		
		String title = ltsService.getAccount().getCustomer().getTitle();
		String firstName = ltsService.getAccount().getCustomer().getFirstName();
		String lastName = ltsService.getAccount().getCustomer().getLastName();
		String key = uENC.encAES(BomLtsConstant.URL_PARM_ENC_KEY, sbOrder.getOrderId());
		String installDate = LtsDateFormatHelper.getDateFromDateTimeSlot(ltsService.getAppointmentDtl().getAppntStartTime());
		String installTime = LtsDateFormatHelper.convertToSBTimeSlot(LtsDateFormatHelper.getFromToTimeFormat(ltsService.getAppointmentDtl().getAppntStartTime(), ltsService.getAppointmentDtl().getAppntEndTime()));
		String locale = StringUtils.equals(BomLtsConstant.EMAIL_ESIG_LANG_ENG, sbOrder.getLangPref()) ? LtsBackendConstant.LOCALE_ENG : LtsBackendConstant.LOCALE_CHI;
		
		if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES.equals(pTemplateId) || LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_PORT_IN_ORDER.equals(pTemplateId)
			|| LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_AMEND.equals(pTemplateId) || LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_WQ.equals(pTemplateId)){	// Email Templates for EYE Service
			ServiceDetailDTO imsServiceDetail = LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls());	
			if(imsServiceDetail != null && StringUtils.equals(BomLtsConstant.TECHNOLOGY_PON, ((ServiceDetailOtherLtsDTO)imsServiceDetail).getAssgnTechnology())){
				installTime = LtsDateFormatHelper.convertToPonTimeSlot(installTime);
			}
		}
		
		if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES.equals(pTemplateId)){
			String srvUrl = this.srdAmendUrl + key;
			if(locale.equals(LtsBackendConstant.LOCALE_ENG)){
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, BomLtsConstant.EMAIL_CONTENT_HEADER_EYE, BomLtsConstant.EMAIL_CONTENT_PRIVACY_STATEMENT_EYE, installDate, installTime, srvUrl});
			}
			else{
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime, srvUrl});
			}
		}
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_DEL.equals(pTemplateId)){
			String srvUrl = this.srdAmendUrl + key;
			if(locale.equals(LtsBackendConstant.LOCALE_ENG)){
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, BomLtsConstant.EMAIL_CONTENT_HEADER_DEL, BomLtsConstant.EMAIL_CONTENT_PRIVACY_STATEMENT_DEL, installDate, installTime, srvUrl});
			}
			else{
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime, srvUrl});
			}			
		}
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_PORT_IN_ORDER.equals(pTemplateId)){
			String portInKey = uENC.encAES(BomLtsConstant.URL_PARM_ENC_KEY, sbOrder.getOrderId() + "-EYE");
			String portInUrl = this.nsdFormUploadUrl + portInKey + "&amp;lang=" + locale;
			if(locale.equals(LtsBackendConstant.LOCALE_ENG)){
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, BomLtsConstant.EMAIL_CONTENT_HEADER_EYE, BomLtsConstant.EMAIL_CONTENT_PRIVACY_STATEMENT_EYE, installDate, installTime, portInUrl});
			}
			else{
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime, portInUrl});
			}
		}		
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_PORT_IN_ORDER_DEL.equals(pTemplateId)){
			String portInKey = uENC.encAES(BomLtsConstant.URL_PARM_ENC_KEY, sbOrder.getOrderId() + "-DEL");
			String portInUrl = this.nsdFormUploadUrl + portInKey + "&amp;lang=" + locale;
			if(locale.equals(LtsBackendConstant.LOCALE_ENG)){
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, BomLtsConstant.EMAIL_CONTENT_HEADER_DEL, BomLtsConstant.EMAIL_CONTENT_PRIVACY_STATEMENT_DEL, installDate, installTime, portInUrl});
			}
			else{
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime, portInUrl});
			}
		}
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_AMEND.equals(pTemplateId)){
			String[] amendTimePeriod = this.amendmentService.getLastestAmendApptTime(ltsService);
			String amendInstallDate = LtsDateFormatHelper.getDateFromDateTimeSlot(amendTimePeriod[0]);
			String amendInstallTime = LtsDateFormatHelper.convertToSBTimeSlot(LtsDateFormatHelper.getFromToTimeFormat(amendTimePeriod[0], amendTimePeriod[1]));
			return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, amendInstallDate, amendInstallTime});
		}		
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_AMEND_DEL.equals(pTemplateId)){
			String[] amendTimePeriod = this.amendmentService.getLastestAmendApptTime(ltsService);
			String amendInstallDate = LtsDateFormatHelper.getDateFromDateTimeSlot(amendTimePeriod[0]);
			String amendInstallTime = LtsDateFormatHelper.convertToSBTimeSlot(LtsDateFormatHelper.getFromToTimeFormat(amendTimePeriod[0], amendTimePeriod[1]));
			return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, amendInstallDate, amendInstallTime});
		}		
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_WQ.equals(pTemplateId)){
			if(locale.equals(LtsBackendConstant.LOCALE_ENG)){
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, BomLtsConstant.EMAIL_CONTENT_HEADER_DEL, BomLtsConstant.EMAIL_CONTENT_PRIVACY_STATEMENT_DEL, installDate, installTime});
			}
			else{
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime});
			}			
		}		
		else if(LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_WQ_DEL.equals(pTemplateId)){
			if(locale.equals(LtsBackendConstant.LOCALE_ENG)){
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, BomLtsConstant.EMAIL_CONTENT_HEADER_DEL, BomLtsConstant.EMAIL_CONTENT_PRIVACY_STATEMENT_DEL, installDate, installTime});
			}
			else{
				return MessageFormat.format(templateContent, new Object[]{title, firstName, lastName, installDate, installTime});
			}			
		}			
		else{
			return null;
		}
	}
	
	private String formatSubjectLts(String templateSubject, SbOrderDTO sbOrder) {
		//ServiceDetailDTO upgradeService = SbOrderHelper.getUpgradeService(sbOrder);
		return MessageFormat.format(templateSubject, new Object[] {});
	}
	
	private Map<String, InputStreamSource> prepareAttachments(OrdEmailReqDTO ordEmailReqDto, EmailTemplateDTO emailTemplateDto, SbOrderDTO ltsSbOrder) throws IOException {
		List<File> files = new ArrayList<File>();
		if (StringUtils.isNotBlank(ordEmailReqDto.getFilePathName1())) {
			String pathname = emailServerPath + File.separator + ordEmailReqDto.getOrderId() + File.separator + ordEmailReqDto.getFilePathName1();
			if (logger.isInfoEnabled()) {
				logger.info("filePathName1: " + pathname);
			}
			files.add(new File(pathname));
		}
		if (StringUtils.isNotBlank(ordEmailReqDto.getFilePathName2())) {
			String pathname = emailServerPath + File.separator + ordEmailReqDto.getOrderId() + File.separator + ordEmailReqDto.getFilePathName2();
			if (logger.isInfoEnabled()) {
				logger.info("filePathName2: " + pathname);
			}
			files.add(new File(pathname));
		}
		if (StringUtils.isNotBlank(ordEmailReqDto.getFilePathName3())) {
			String pathname = emailServerPath + File.separator + ordEmailReqDto.getOrderId() + File.separator + ordEmailReqDto.getFilePathName3();
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

		if (StringUtils.equals(LtsBackendConstant.LOB_LTS, dto.getLob())) {
			ServiceDetailDTO upgradeServiceDetail = LtsSbHelper.getLtsService(ltsSbOrder);
			password = upgradeServiceDetail.getActualDocId();
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
}
