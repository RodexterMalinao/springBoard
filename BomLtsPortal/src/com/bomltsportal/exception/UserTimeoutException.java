package com.bomltsportal.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.BomWebPortalException;

public class UserTimeoutException extends BomWebPortalException{
	
	private static final long serialVersionUID = -2281172910492425602L;
	
	protected final Log logger = LogFactory.getLog(getClass());

	public UserTimeoutException (){
	}
	
	public UserTimeoutException (Throwable cause) {
		super(cause);
		logMessage();
	}
	
	public UserTimeoutException (String customMessage, Throwable cause) {
		super(customMessage, cause);
		this.customMessage = customMessage;
		this.loggingMessage = cause.getMessage();
		logMessage();
	}
	
	public UserTimeoutException (String customMessage) {
		super(customMessage);
		this.customMessage = customMessage;
		logMessage();
	}
}