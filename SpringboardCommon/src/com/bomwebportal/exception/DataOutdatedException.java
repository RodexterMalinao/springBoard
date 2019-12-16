package com.bomwebportal.exception;

public class DataOutdatedException extends AppRuntimeException {

	private static final long serialVersionUID = 1035004115581543988L;
	
	public DataOutdatedException(String customMessage) {
		super(customMessage);
	}
}
