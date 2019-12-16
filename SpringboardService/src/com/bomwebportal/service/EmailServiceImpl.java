package com.bomwebportal.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.EmailTemplateDAO;
import com.bomwebportal.dto.EmailTemplateDTO;
import com.bomwebportal.dto.EmailTemplateDTO.EsigEmailLang;
import com.bomwebportal.dto.EmailTemplateDTO.PdfPwdInd;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrdEmailReqDTO.DisMode;
import com.bomwebportal.email.EmailReqResult;
import com.bomwebportal.email.EmailTemplateHandler;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.util.PdfUtil;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javax.mail.internet.InternetAddress;
import javax.mail.MessagingException;
@Transactional(readOnly = true)
public abstract class EmailServiceImpl implements EmailService, ApplicationContextAware {
	protected final Log logger = LogFactory.getLog(getClass());

	private ApplicationContext context;

	private MailService mailService;
	private OrdEmailReqService ordEmailReqService;

	private EmailTemplateDAO emailTemplateDAO;


	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public EmailReqResult createEmailReqAndSendEmail(String pPassword, OrdEmailReqDTO pOrdEmailReqDTO, String pServerPath) {

		EmailReqResult rtnEmailReqResult = new EmailReqResult();

		if (StringUtils.isBlank(pOrdEmailReqDTO.getEmailAddr())) {
			logger.warn("emailAddr is blank");
			rtnEmailReqResult.setErrCd(EmailReqResult.INVALID_EMAIL_ADDR);
			return rtnEmailReqResult;
		}
		if (!Utility.validateEmail(pOrdEmailReqDTO.getEmailAddr())) {
			logger.warn("emailAddr is not valid");
			rtnEmailReqResult.setErrCd(EmailReqResult.INVALID_EMAIL_ADDR);
			return rtnEmailReqResult;
		}

		OrdEmailReqDTO dto = pOrdEmailReqDTO;
		dto.setRequestDate(new Date());
		//insert record in DB
		try {
			int seqNo = this.ordEmailReqService.getNextSeq(pOrdEmailReqDTO.getOrderId(), pOrdEmailReqDTO.getTemplateId(), pOrdEmailReqDTO.getLob());
			dto.setSeqNo(seqNo);
			this.ordEmailReqService.insertOrdEmailReq(dto);
			dto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(dto.getOrderId(), seqNo, dto.getTemplateId());
		} catch (Exception e) {
			logger.warn("Exception in insertOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}

		beforeEmailSent(dto);

		// Send email and get result
		try {
			this.sendOrderEmail(dto, pServerPath, pPassword);
			rtnEmailReqResult.setErrCd(EmailReqResult.SUCCESS);

			dto.setSentDate(new Date());
			dto.setErrMsg(null);
		} catch (Exception e) {
			logger.warn("Exception in sendOrderEmail", e);

			rtnEmailReqResult = this.parseException(dto, e);

			StringBuilder errMsg = new StringBuilder(rtnEmailReqResult.getMessage());
			if (e.getMessage() != null) {
				errMsg.append(" " + e.getMessage());
			}
			dto.setErrMsg(errMsg.length() > 100 ? errMsg.substring(0, 100) : errMsg.toString());
		}
		//update send result in DB
		try {
			this.ordEmailReqService.updateOrdEmailReq(dto);
		} catch (Exception e) {
			logger.warn("Exception in updateOrdEmailReq", e);
			throw new AppRuntimeException(e == null ? "" : e.getMessage());
		}

		afterEmailSent(dto);

		if (logger.isDebugEnabled()) {
			logger.debug("result: " + rtnEmailReqResult);
		}
		return rtnEmailReqResult;
	}

	private void beforeEmailSent(OrdEmailReqDTO dto) {
		EmailTemplateHandler handler = getTemplateHandler(dto.getLob(), dto.getTemplateId());
		if (handler == null)
			return;

		handler.beforeEmailSent(dto);
	}

	private void afterEmailSent(OrdEmailReqDTO dto) {
		EmailTemplateHandler handler = getTemplateHandler(dto.getLob(), dto.getTemplateId());
		if (handler == null)
			return;

		handler.afterEmailSent(dto);
	}

	private void sendOrderEmail(OrdEmailReqDTO pOrdEmailReqdto, String pReportServerPath, String pPassword) throws MessagingException,
			ClassNotFoundException, IOException {
		
		//fill in email content
		//get template
		EmailTemplateDTO emailTemplateDto = this.getEmailTemplateDTO(pOrdEmailReqdto.getTemplateId(), pOrdEmailReqdto.getLob(), pOrdEmailReqdto.getLocale());

		//format content
		String subject = this.getEmailSubject(emailTemplateDto, pOrdEmailReqdto);
		String content = this.getEmailContent(emailTemplateDto, pOrdEmailReqdto);

		String senderEmailAddr = this.getSenderEmailAddress(pOrdEmailReqdto);
		String senderEmailName = this.getSenderEmailName(pOrdEmailReqdto);

		if (logger.isInfoEnabled()) {
			logger.info("sender: " + senderEmailAddr + " " + senderEmailName);
			logger.info("subject: " + subject);
			logger.info("content: " + content);
		}

		this.mailService.sendHtml(new InternetAddress(senderEmailAddr, senderEmailName, "UTF-8"), new InternetAddress(pOrdEmailReqdto.getEmailAddr(), pOrdEmailReqdto.getEmailAddr()), null, null,
				subject, content, this.prepareAttachments(pOrdEmailReqdto, emailTemplateDto, pReportServerPath, pPassword));
	}

	

	private String getEmailSubject(EmailTemplateDTO pEmailTemplateDto, OrdEmailReqDTO pOrdEmailReqDto) {
		return formatTemplate(pEmailTemplateDto.getTemplateSubject(),pOrdEmailReqDto);
	}

	private String getEmailContent(EmailTemplateDTO pEmailTemplateDto, OrdEmailReqDTO pOrdEmailReqDto) {		return formatTemplate(pEmailTemplateDto.getTemplateContent(),pOrdEmailReqDto);
	}
	
	private String formatTemplate(String template,OrdEmailReqDTO pOrdEmailReqDto)
	{
		//format from dto
		
		//format from paramMap
		if (pOrdEmailReqDto.getParamString() != null) {

			String json = pOrdEmailReqDto.getParamString();
			Gson gson = new GsonBuilder().create();
			Type typeOfHashMap = new TypeToken<Map<String, String>>() {
			}.getType();
			Map<String, String> paramMap = gson.fromJson(json, typeOfHashMap);
			for (Entry<String, String> iter : paramMap.entrySet()) {
				template = template.replaceAll(iter.getKey(), iter.getValue());
			}
		}
		return template;
	}

	private Map<String, InputStreamSource> prepareAttachments(OrdEmailReqDTO pOrdEmailReqDto, EmailTemplateDTO pEmailTemplateDto, String pReportServerPath, String pPassword) throws IOException {
		List<File> files = new ArrayList<File>();
		if (StringUtils.isNotBlank(pOrdEmailReqDto.getFilePathName1())) {
			String pathname = pReportServerPath + pOrdEmailReqDto.getStorePath() + pOrdEmailReqDto.getFilePathName1();
			if (logger.isInfoEnabled()) {
				logger.info("filePathName1: " + pathname);
			}
			files.add(new File(pathname));
		}
		if (StringUtils.isNotBlank(pOrdEmailReqDto.getFilePathName2())) {
			String pathname = pReportServerPath + pOrdEmailReqDto.getStorePath() + pOrdEmailReqDto.getFilePathName2();
			if (logger.isInfoEnabled()) {
				logger.info("filePathName2: " + pathname);
			}
			files.add(new File(pathname));
		}
		if (StringUtils.isNotBlank(pOrdEmailReqDto.getFilePathName3())) {
			String pathname = pReportServerPath + pOrdEmailReqDto.getStorePath() + pOrdEmailReqDto.getFilePathName3();
			if (logger.isInfoEnabled()) {
				logger.info("filePathName3: " + pathname);
			}
			files.add(new File(pathname));
		}
		if (this.isEmpty(files)) {
			return Collections.emptyMap();
		}

		Map<String, InputStreamSource> attachments = new LinkedHashMap<String, InputStreamSource>();
		if (this.requiredEncryptFile(pEmailTemplateDto)) {
			final String encryptFilePassword = this.getEncryptFilePassword(pOrdEmailReqDto, pPassword);

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

	private EmailReqResult parseException(OrdEmailReqDTO dto, Exception e) {

		EmailReqResult rtnEmailReqResult = new EmailReqResult();

		if (e instanceof MailSendException) {
			rtnEmailReqResult.setErrCd(EmailReqResult.MAIL_SEND_EXCEPTION);
			return rtnEmailReqResult;
		}

		if (e instanceof FileNotFoundException) {
			rtnEmailReqResult.setErrCd(EmailReqResult.ATTACHMENT_NOT_FOUND);
			return rtnEmailReqResult;
		}

		if (e instanceof InvalidEncryptPasswordLengthException) {
			// mob customized message
			if ("MOB".equals(dto.getLob())) {
				rtnEmailReqResult.setErrCd(EmailReqResult.INVALID_ENCRYPT_PASSWORD_LENGTH_MOB);
				return rtnEmailReqResult;
			}

			rtnEmailReqResult.setErrCd(EmailReqResult.INVALID_ENCRYPT_PASSWORD_LENGTH);
			return rtnEmailReqResult;
		}

		if (e instanceof InvalidEncryptPasswordException) {
			rtnEmailReqResult.setErrCd(EmailReqResult.INVALID_ENCRYPT_PASSWORD);
			return rtnEmailReqResult;
		}

		rtnEmailReqResult.setErrCd(EmailReqResult.FAIL);
		return rtnEmailReqResult;
	}

	// ///////////////////////////////////////////////////////////////////////////////////
	// Handling of Specific email template by defining a corresponding template handlers or plugins .......
	// The handler is a bean of EmailTemplateHandler with name "template.<LOB>.<TEMPLATEID>"
	// ///////////////////////////////////////////////////////////////////////////////////

	public EmailTemplateHandler getTemplateHandler(String lob, String templateId) {
		// bean name = template.MOB.RT001 ...

		String beanName = "template." + lob + "." + templateId;
		if (context.containsBean(beanName)) {
			return (EmailTemplateHandler) context.getBean(beanName);
		}
		return null;
	}

	/*
	 * -------------- Bug in Groovy -------------- When Chinese character immediately after variable e.g. $variable[Chinese char]
	 * 
	 * Groovy throws GroovyRuntimeException and fail to replace content
	 * 
	 * or use ${xxx} to represent variable
	 */
	/*
	 * private String replace(String templateContent, OrdEmailReqDTO dto) throws CompilationFailedException, ClassNotFoundException, IOException {
	 * SimpleTemplateEngine engine = new SimpleTemplateEngine(); Template template = engine.createTemplate(templateContent);
	 * 
	 * EmailTemplateVariable emailTemplateVariable = new EmailTemplateVariable(); TreeMap<Integer, String> emailTemplateVariableMap =
	 * emailTemplateVariable.getEmailTemplateVariableMap(); Map<String, Object> params = new HashMap<String, Object>();
	 * 
	 * Iterator<Map.Entry<Integer, String>> it = emailTemplateVariableMap.entrySet().iterator(); Map.Entry<Integer, String> templateVariableEntry = null;
	 * 
	 * while(it.hasNext()) { templateVariableEntry = it.next(); String templateVariableValue = null;
	 * 
	 * if (EmailTemplateVariable.EMAIL_ADDRESS.equals(templateVariableEntry.getKey())) { templateVariableValue = dto.getEsigEmailAddr(); } else if
	 * (EmailTemplateVariable.CUSTOMER_TITLE.equals(templateVariableEntry.getKey())) { templateVariableValue = dto.getTitle(); } else if
	 * (EmailTemplateVariable.CUSTOMER_NAME.equals(templateVariableEntry.getKey())) { templateVariableValue = dto.getContactName(); }
	 * 
	 * if (templateVariableValue != null) { params.put(templateVariableEntry.getValue(), templateVariableValue); } }
	 * 
	 * fillExtraProperties(params, dto);
	 * 
	 * Writable writable = template.make(params); return writable.toString(); }
	 * 
	 * private void fillExtraProperties(Map<String,Object> map, OrdEmailReqDTO dto) { map.put("context", context); String lob = dto.getLob(); String templateId
	 * = dto.getTemplateId();
	 * 
	 * EmailTemplateHandler handler = getTemplateHandler(lob, templateId); try { if (handler != null) { Map<String,?> extra = handler.getSubjectProperties(dto);
	 * if (extra != null) { map.putAll(extra); } extra = handler.getContentProperties(dto); if (extra != null) { map.putAll(extra); } } } catch (Exception e) {
	 * logger.error("Failed to load extra template data ..."); } }
	 */

	private EmailTemplateDTO getEmailTemplateDTO(String pTemplateId, String pLob, EsigEmailLang pLocale) {
		return this.emailTemplateDAO.getEmailTemplateDTO(pTemplateId, pLob, pLocale);
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

	private boolean requiredEncryptFile(EmailTemplateDTO emailTemplateDto) {
		if (PdfPwdInd.Y.equals(emailTemplateDto.getPdfPwdInd())) {
			return true;
		}
		return false;
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
				} catch (IOException e) {
				}
				fis = null;
			}
		}
		return new ByteArrayResource(fbaos.toByteArray(), file.getName());
	}

	private String getExtension(File file) {
		int index = file.getName().lastIndexOf('.');
		if (index == -1) {
			return null;
		}
		return file.getName().substring(index + 1);
	}

	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	public ApplicationContext getApplicationContext() {
		return this.context;
	}

	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
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
}
