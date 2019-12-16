package com.bomwebportal.eagle.dto;

import java.io.Serializable;

public class EagleErrorResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5989320305310393304L;
	
	private String errCode = "0";
	private String errMsg;
	
	public EagleErrorResponse() {
		this.errCode = "0";
	}
	
	public EagleErrorResponse(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}
