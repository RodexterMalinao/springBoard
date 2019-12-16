package com.bomwebportal.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DuplicateOrderException extends BomWebPortalException{
	
	private static final long serialVersionUID = 305887819522493623L;
	protected final Log logger = LogFactory.getLog(getClass());

	public DuplicateOrderException (){
	}
	
	public DuplicateOrderException (Throwable cause) {
		super(cause);
		logMessage();
	}
	
	public DuplicateOrderException (String customMessage, Throwable cause) {
		super(customMessage, cause);
		this.customMessage = customMessage;
		this.loggingMessage = cause.getMessage();
		logMessage();
	}
	
	public DuplicateOrderException (String customMessage) {
		super(customMessage);
		this.customMessage = customMessage;
		logMessage();
	}
}
