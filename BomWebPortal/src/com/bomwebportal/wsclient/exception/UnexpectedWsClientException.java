package com.bomwebportal.wsclient.exception;

public class UnexpectedWsClientException extends WsClientException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public UnexpectedWsClientException(String errCode, String errMessage) {
		super(errCode, errMessage);
	}

	public UnexpectedWsClientException(String errCode, String errMessage,
			Throwable e) {
		super(errCode, errMessage, e);
	}

	public UnexpectedWsClientException(Throwable e) {
		super(e);
	}

	public UnexpectedWsClientException(String message, Throwable e) {
		super(message, e);
	}

}
