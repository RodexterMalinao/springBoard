package com.bomwebportal.mob.validate.dto;

import java.io.Serializable;

public class ErrorDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1981957069029899072L;
	private String field;
	private String errorCd;
	private String errorMsg;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCd() {
		return errorCd;
	}

	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}
	
	
	
}
