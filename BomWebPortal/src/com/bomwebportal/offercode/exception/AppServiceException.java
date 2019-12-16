package com.bomwebportal.offercode.exception;

public class AppServiceException extends Exception {

	public final static String DEFAULT_ERR_CODE = "99999";
	
	private String code = DEFAULT_ERR_CODE;
	
	public AppServiceException() {
		this.code = DEFAULT_ERR_CODE;
	}

	public AppServiceException(String message) {
		super(message);
		this.code = DEFAULT_ERR_CODE;
	}
	
	public AppServiceException(String code, String message) {
		super(message);
		this.code = code;
	}

	public AppServiceException(Throwable cause) {
		super(cause);
		this.code = DEFAULT_ERR_CODE;
	}

	public AppServiceException(String message, Throwable cause) {
		super(message, cause);
		this.code = DEFAULT_ERR_CODE;
	}
	

	public AppServiceException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	

	public String getCode() {
		return this.code;
	}

}
