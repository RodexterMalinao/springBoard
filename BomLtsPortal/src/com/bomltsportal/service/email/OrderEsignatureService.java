package com.bomltsportal.service.email;

import java.io.IOException;
import java.util.Date;

import org.codehaus.groovy.control.CompilationFailedException;

import com.bomltsportal.dto.email.EmailTemplateDTO;
import com.bomltsportal.dto.email.OrdEmailReqDTO;
import com.bomltsportal.dto.email.OrderDTO.EsigEmailLang;
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
		;
		
		EmailReqResult(String message) {
			this.message = message;
		}
		public String getMessage() {
			return message;
		}
		private String message;
	}
	
	String getEmailSubject(EmailTemplateDTO emailTemplateDto, OrdEmailReqDTO ordEmailReqDto, SbOrderDTO sbOrder) throws CompilationFailedException, ClassNotFoundException, IOException;
	String getEmailContent(EmailTemplateDTO emailTemplateDto, OrdEmailReqDTO ordEmailReqDto, SbOrderDTO sbOrder) throws CompilationFailedException, ClassNotFoundException, IOException;
	
	EmailTemplateDTO getEmailTemplateDTO(String templateId, String lob, EsigEmailLang locale);
	
	EmailReqResult createEmailReq(String orderId, String templateId,
			Date requestDate,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String emailLang,
			String createBy);
	

	// same as createEmailReq + update esig_email_addr in bomweb_order
	EmailReqResult resendEmailReq(String orderId, String templateId,
			String filePathName1, String filePathName2, String filePathName3,
			String emailAddr,
			String emailLang,
			String createBy);
}
