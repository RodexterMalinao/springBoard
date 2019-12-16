package com.bomwebportal.wsclient.exception;

public class WsClientException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7639376474691391291L;
	private String errCode;
	private String errMessage;
	
	public WsClientException(String errCode, String errMessage) {
		super(errMessage);
		this.errCode = errCode;
		this.errMessage = errMessage;
	}

	public WsClientException(String errCode, String errMessage, Throwable e) {
		super(e);
		this.errCode = errCode;
		this.errMessage = errMessage;
	}
	
	public WsClientException(Throwable e) {
		this(e.getMessage(), e);
	}
	
	public WsClientException(String message, Throwable e) {
		super(message, e);
		this.errCode = "-99";
		this.errMessage = message;
	}
	
	@Override
	public String getLocalizedMessage() {
		return errMessage == null ? super.getLocalizedMessage() : errMessage + " (" + errCode + ")";
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	
	
	
}
