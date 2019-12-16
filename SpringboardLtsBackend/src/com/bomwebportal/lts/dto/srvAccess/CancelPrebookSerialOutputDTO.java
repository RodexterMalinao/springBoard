package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class CancelPrebookSerialOutputDTO implements Serializable {

	private static final long serialVersionUID = 3261199538255958505L;

	private String returnValue = null;
	private String errorCd = null;
	private String errorMsg = null;

	
	public String getReturnValue() {
		return this.returnValue;
	}

	public void setReturnValue(String pReturnValue) {
		this.returnValue = pReturnValue;
	}

	public String getErrorCd() {
		return this.errorCd;
	}

	public void setErrorCd(String pErrorCd) {
		this.errorCd = pErrorCd;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String pErrorMsg) {
		this.errorMsg = pErrorMsg;
	}

	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" returnValue = ");
		sb.append(this.returnValue);
		sb.append(" errorCd = ");
		sb.append(this.errorCd);
		sb.append(" errorMsg = ");
		sb.append(this.errorMsg);	
		return sb.toString();
	}
}
