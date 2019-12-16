package com.bomwebportal.email;

import java.io.Serializable;
import java.util.TreeMap;


public class EmailReqResult implements Serializable {
	
	/**
	 *  
	 */
	private static final long serialVersionUID = 4244061103485552223L;

	// Initial copied on 20140925 from Version 1.13 of
	// Springboard\SBWPR3\BomWebPortal\src\com\bomwebportal\service\OrderEsignatureService.java
	
	public static final Integer SUCCESS = 0;
	public static final Integer FAIL = 1;                                // generic fail
	public static final Integer INVALID_EMAIL_ADDR = 2;                  // given email addr is not in valid pattern
	public static final Integer NOT_ESIGN_ORDER = 3;	                 // orderId is not an esignature order
	public static final Integer INVALID_ENCRYPT_PASSWORD = 4;            // encrypt attachment password invalid pattern
	public static final Integer INVALID_ENCRYPT_PASSWORD_LENGTH = 5;     // encrypt attachment password invalid length
	public static final Integer MAIL_SEND_EXCEPTION = 6;                 // error during sending email
	public static final Integer ATTACHMENT_NOT_FOUND = 7;                // attachment cannot be found
	public static final Integer INVALID_ENCRYPT_PASSWORD_LENGTH_MOB = 8;
	public static final Integer EMPTY_EMAIL_ADDR = 9;
	public static final Integer INVALID_SMS_NO = 10;
	public static final Integer SMS_FAIL = 11;
	
	private TreeMap<Integer, String> emailReqResultMap; 
	private Integer errCd;
	private String message;
	
	
	public EmailReqResult() {
		this.emailReqResultMap = new TreeMap<Integer, String>();
		
		this.emailReqResultMap.put(SUCCESS, "SUCCESS");
		this.emailReqResultMap.put(FAIL, "General error");
		this.emailReqResultMap.put(INVALID_EMAIL_ADDR, "Invalid Email Address");
		this.emailReqResultMap.put(NOT_ESIGN_ORDER, "Order's Distribution Mode is not Digital Signature");
		this.emailReqResultMap.put(INVALID_ENCRYPT_PASSWORD,"Encrypt password is invalid");
		this.emailReqResultMap.put(INVALID_ENCRYPT_PASSWORD_LENGTH,"Encrypt password length is invalid");
		this.emailReqResultMap.put(MAIL_SEND_EXCEPTION,"Send mail exception");
		this.emailReqResultMap.put(ATTACHMENT_NOT_FOUND,"Mail attachment not found");
		this.emailReqResultMap.put(INVALID_ENCRYPT_PASSWORD_LENGTH_MOB,"Id Document number length less than 6 characters");
		this.emailReqResultMap.put(EMPTY_EMAIL_ADDR,"No Email Address provide");
		this.emailReqResultMap.put(INVALID_SMS_NO,"Invalid Mobile number");
		this.emailReqResultMap.put(SMS_FAIL,"Cannot send out SMS.");
	}
	
	
	public TreeMap<Integer, String> getEmailReqResultMap() {
		return this.emailReqResultMap;
	}

	public void setEmailReqResultMap(TreeMap<Integer, String> pEmailReqResultMap) {
		this.emailReqResultMap = pEmailReqResultMap;
	}
	
	public void addEmailReqResult(Integer pErrCd, String pMessage) {
		this.emailReqResultMap.put(pErrCd, pMessage);
	}
	
	public Integer getLastEmailResultKey() {
		return this.emailReqResultMap.lastKey();
	}
	
	public Integer getErrCd() {
		return this.errCd;
	}
	
	public void setErrCd(Integer pErrCd) {
		this.errCd = pErrCd;
		this.setMessage(emailReqResultMap.get(pErrCd));
	}
	
	public String getMessage() {
		return this.message;
	}
	
	private void setMessage(String pMessage) {
		this.message = pMessage;
	}
}
