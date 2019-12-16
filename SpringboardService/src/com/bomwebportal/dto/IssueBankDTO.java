package com.bomwebportal.dto;

import java.io.Serializable;

public class IssueBankDTO implements Serializable {

	private static final long serialVersionUID = -6102802747357199329L;

	private String bankCd;
	
	private String bankName;

	public String getBankCd() {
		return bankCd;
	}

	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}
