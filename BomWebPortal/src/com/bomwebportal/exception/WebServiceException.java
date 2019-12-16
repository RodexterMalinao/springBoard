package com.bomwebportal.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebServiceException extends BomWebPortalException{
	
	private static final long serialVersionUID = 8749036605423057897L;
	
	protected final Log logger = LogFactory.getLog(getClass());

	public WebServiceException (){
	}
	
	public WebServiceException (Throwable cause) {
		super(cause);
		logMessage();
	}
	
	public WebServiceException (String customMessage, Throwable cause) {
		super(cause.getMessage(), cause);
		this.customMessage = customMessage;
		this.loggingMessage = cause.getMessage();
		logMessage();
	}
	
	public WebServiceException (String customMessage) {
		super(customMessage);
		this.customMessage = customMessage;
		logMessage();
	}


}
