package com.bomwebportal.service;

import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;

import org.codehaus.groovy.control.CompilationFailedException;
import org.springframework.mail.MailException;

import com.bomwebportal.dto.EmailTemplateDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface OrderEsignatureService {
	enum EmailReqResult {
		SUCCESS("SUCCESS")
		, FAIL("General error")                                                 // generic fail
		, INVALID_EMAIL_ADDR("Invalid Email Address")                           // given email addr is not in valid pattern
		, NOT_ESIGN_ORDER("Order's Distribution Mode is not Digital Signature") // orderId is not an esignature order
		, INVALID_ENCRYPT_PASSWORD("Encrypt password is invalid")               // encrypt attachment password invalid pattern
		, INVALID_ENCRYPT_PASSWORD_LENGTH("Encrypt password length is invalid") // encrypt attachment password invalid length
		, MAIL_SEND_EXCEPTION("Send mail exception")                            // error during sending email
		, ATTACHMENT_NOT_FOUND("Mail attachment not found")                     // attachment cannot be found
		// for mob only
		, INVALID_ENCRYPT_PASSWORD_LENGTH_MOB("Id Document number length less than 6 characters")
		, EMPTY_EMAIL_ADDR("No Email Address provide")
		// for CC ims
		, INVALID_SMS_NO("Invalid Mobile number")
		, SMS_FAIL("Cannot send out SMS.")
		;
		
		EmailReqResult(String message) {
			this.message = message;
		}
		public String getMessage() {
			return message;
		}
		public void setSeqno(int seqno) {
			this.seqno = seqno;
		}
		public int getSeqno() {
			return seqno;
		}
		private String message;
		private int seqno;
	}
	
	String getEmailSubject(EmailTemplateDTO emailTemplateDto, OrdEmailReqDTO ordEmailReqDto, SbOrderDTO sbOrder) throws CompilationFailedException, ClassNotFoundException, IOException;
	String getEmailContent(EmailTemplateDTO emailTemplateDto, OrdEmailReqDTO ordEmailReqDto, SbOrderDTO sbOrder) throws CompilationFailedException, ClassNotFoundException, IOException;
	
	String formatSubjectLts(String templateSubject, SbOrderDTO sbOrder);
	String formatContentLts(String templateContent, SbOrderDTO sbOrder, String templateId);
	
	String getSenderEmailName(String lob, EsigEmailLang locale, String templateId, String brand, String orderBrand, String orderType);
	
	
	EmailTemplateDTO getEmailTemplateDTO(String templateId, String lob, EsigEmailLang locale);
	void sendOrderEmail(OrdEmailReqDTO dto) throws MailException, MessagingException, CompilationFailedException, ClassNotFoundException, IOException ;//celia ims 20141210
	
	EmailReqResult createEmailReq(String orderId, String templateId,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy);
	
	OrdEmailReqDTO createSmsReqRecord(String orderId, String templateId,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String smsNo,
			int seqNo,
			String createBy, long printRequestId);
	
	EmailReqResult updateOrdEmailReq(String orderId, int seqNo, String templateId);
	
	// same as createEmailReq + update esig_email_addr in bomweb_order
	EmailReqResult resendEmailReq(String orderId, String templateId,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy);
	
	EmailReqResult resendEmailReqLts(String orderId, String templateId, int seqNo,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy, long printReqId);
	
	EmailReqResult resendEmailReqIMS(String orderId, String templateId,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy);
	
	public EmailReqResult createEmailReqIMS(String orderId, String templateId,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String createBy);
}
