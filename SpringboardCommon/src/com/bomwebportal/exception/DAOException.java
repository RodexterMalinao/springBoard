package com.bomwebportal.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DAOException extends BomWebPortalException{

	private static final long serialVersionUID = -2409592164028127354L;
	
	protected final Log logger = LogFactory.getLog(getClass());

	public DAOException (){
	}
	
	public DAOException (Throwable cause) {
		super(cause);
		logMessage();
	}
	
	public DAOException (String customMessage, Throwable cause) {
		this.customMessage = customMessage;
		this.loggingMessage = cause.getMessage();
		logMessage();
	}
	
	public DAOException (String customMessage) {
		this.customMessage = customMessage;
		logMessage();
	}


}
