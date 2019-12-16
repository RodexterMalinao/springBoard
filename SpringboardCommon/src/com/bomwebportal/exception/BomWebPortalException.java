package com.bomwebportal.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BomWebPortalException extends Exception implements BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5417344473994159902L;
	/**
	 * 
	 */
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected BomWebPortalException () {
		super();
	}
	
	protected BomWebPortalException (String message) {
		super(message);
	}

	protected BomWebPortalException (Throwable cause) {
		super(cause);
	}

	protected BomWebPortalException (String customMessage, Throwable cause) {
		super(cause.getMessage(), cause);
		this.customMessage = customMessage;
		this.loggingMessage = cause.getMessage();
	}
	
	protected String loggingMessage;
	protected String customMessage;

	public String getLoggingMessage() {
		return loggingMessage;
	}

	public void setLoggingMessage(String loggingMessage) {
		this.loggingMessage = loggingMessage;
	}

	public String getCustomMessage() {
		return customMessage;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}

	public void logMessage() {
		logger.error(this.getClass().getName() + " being thrown" + "\n" + 		
				"Custom Message = [" + customMessage + "]" + "\n" + 
				"System Message = [" + loggingMessage + "]");
	}
	
	
}
