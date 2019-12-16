package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class Idd0060ProfileLtsDTO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5143991312988006391L;
	
	private String srvNum;
	private String srvType;
	private String datCode;
	private String action;

	private String acctNum;
	private boolean sameAcct;

	public String getSrvNum() {
		return srvNum;
	}
	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}
	public String getSrvType() {
		return srvType;
	}
	public void setSrvType(String srvType) {
		this.srvType = srvType;
	}
	public String getDatCode() {
		return datCode;
	}
	public void setDatCode(String datCode) {
		this.datCode = datCode;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public boolean isSameAcct() {
		return sameAcct;
	}
	public void setSameAcct(boolean sameAcct) {
		this.sameAcct = sameAcct;
	}
	public String getAcctNum() {
		return acctNum;
	}
	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

}
