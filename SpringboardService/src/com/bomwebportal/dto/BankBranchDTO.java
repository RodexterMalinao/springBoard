package com.bomwebportal.dto;

import java.io.Serializable;

public class BankBranchDTO implements Serializable {

	//SELECT BANK_CODE, BRANCH_CODE, BRANCH_NAME FROM W_BANKBRANCHLKUP;	BankBranchDTO
	
	private static final long serialVersionUID = -672131444458078974L;
	private String bankCode;// W_BANKBRANCHLKUP.BANK_CODE
	private String bankName; // add 20110607
	private String branchCode; // W_BANKBRANCHLKUP.BRANCH_CODE
	private String branchName;// W_BANKBRANCHLKUP.BRANCH_NAME
	
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankName() {
		return bankName;
	}
	
	
}