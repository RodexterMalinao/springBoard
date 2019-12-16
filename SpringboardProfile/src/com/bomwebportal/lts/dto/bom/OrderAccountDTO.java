package com.bomwebportal.lts.dto.bom;

import java.io.Serializable;

public class OrderAccountDTO implements Serializable {

	private static final long serialVersionUID = 1466219132854751299L;

	private String acctNum = null;
	private String chrgCatgCd = null;
	private String custNum = null;
	private String acctType = null;

	
	public String getAcctNum() {
		return acctNum;
	}

	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

	public String getChrgCatgCd() {
		return chrgCatgCd;
	}

	public void setChrgCatgCd(String chrgCatgCd) {
		this.chrgCatgCd = chrgCatgCd;
	}

	public String getCustNum() {
		return custNum;
	}

	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
}
