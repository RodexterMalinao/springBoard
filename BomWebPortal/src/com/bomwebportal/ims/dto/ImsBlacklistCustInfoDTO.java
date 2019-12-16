package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class ImsBlacklistCustInfoDTO implements Serializable {

	private String acctNo;
	private String fchReady;
	private Double curOsAmt;
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getFchReady() {
		return fchReady;
	}
	public void setFchReady(String fchReady) {
		this.fchReady = fchReady;
	}
	public Double getCurOsAmt() {
		return curOsAmt;
	}
	public void setCurOsAmt(Double curOsAmt) {
		this.curOsAmt = curOsAmt;
	}
	
	@Override
	public String toString() {
		return "ImsBlOsAmtDTO [acctNo=" + acctNo + ", fchReady=" + fchReady
				+ ", curOsAmt=" + curOsAmt + "]";
	}
	
}