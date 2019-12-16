package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class AppointmentSubmitDTO implements Serializable{

	/**
	 * 
	 */
	
	private String serialNum;
	private int returnValue;
	private int errorCode;
	private String errorMsg;
	
	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public int getReturnValue() {
		return returnValue;
	}
	
	public void setReturnValue(int returnValue) {
		this.returnValue = returnValue;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
