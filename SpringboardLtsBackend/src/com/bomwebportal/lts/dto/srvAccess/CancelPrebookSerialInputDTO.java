package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class CancelPrebookSerialInputDTO implements Serializable {

	private static final long serialVersionUID = 1489117729696185532L;

	private String loginID = null;
	private String serialNum = null;
	
	
	public String getLoginID() {
		return this.loginID;
	}

	public void setLoginID(String pLoginID) {
		this.loginID = pLoginID;
	}

	public String getSerialNum() {
		return this.serialNum;
	}

	public void setSerialNum(String pSerialNum) {
		this.serialNum = pSerialNum;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("loginID = ");
		sb.append(this.loginID);
		sb.append(" serialNum = ");
		sb.append(this.serialNum);		
		return sb.toString();
	}
}
