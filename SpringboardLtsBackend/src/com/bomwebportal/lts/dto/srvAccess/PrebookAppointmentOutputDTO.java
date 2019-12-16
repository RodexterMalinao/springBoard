package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class PrebookAppointmentOutputDTO implements Serializable {

	private static final long serialVersionUID = -4930629411392539840L;
	
	private String serialNum = null;
	private String returnValue = null;
	private String errorCd = null;
	private String errorMsg = null;

	
	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getErrorCd() {
		return errorCd;
	}

	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("serialNum = ");
		sb.append(this.serialNum);
		sb.append(" returnValue = ");
		sb.append(this.returnValue);
		sb.append(" errorCd = ");
		sb.append(this.errorCd);
		sb.append(" errorMsg = ");
		sb.append(this.errorMsg);		
		return sb.toString();
	}
}
