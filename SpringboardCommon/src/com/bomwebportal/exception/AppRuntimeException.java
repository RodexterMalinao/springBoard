package com.bomwebportal.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 10920323665905565L;

	protected final Log logger = LogFactory.getLog(getClass());
	
	private String loggingMessage;
	private String customMessage;

	public String getCustomMessage() {
		return customMessage;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}

	public String getLoggingMessage() {
		return loggingMessage;
	}

	public void setLoggingMessage(String loggingMessage) {
		this.loggingMessage = loggingMessage;
	}


	public AppRuntimeException (String customMessage, Throwable cause) {
		super(cause.getMessage(), cause);
		this.loggingMessage = cause.getMessage();
		this.customMessage = customMessage;
		logMessage();
	}
	
	public AppRuntimeException (String customMessage) {	
		
		this.customMessage = customMessage;
		logMessage();
	}
	
	public AppRuntimeException (Throwable cause) {	
		this.loggingMessage = cause.getMessage();
		logMessage();
	}
	
	public String toString () {
		return loggingMessage + "\n" + super.toString();
	}

	public void logMessage() {
		logger.error(loggingMessage);
	}

	
}
