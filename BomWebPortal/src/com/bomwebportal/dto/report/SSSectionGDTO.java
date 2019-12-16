package com.bomwebportal.dto.report;

import java.io.InputStream;

public class SSSectionGDTO {
	
	private String autopayUpperLimitAmt = "";
	private String bankAcctHolderName = "";
	private String bankAcctNum = "";
	private String bankCode = "";
	private String branchCode = "";
	private String creditCardHolderName = "";
	private String creditCardNum = "";
	private String creditCardType = "";
	private String creditExpiryMonth = "";
	private String creditExpiryYear = "";
	private InputStream custSignatureAutoPay = null;
	private String payMethodType = "";
	private String thirdPartyInd = "";
	
	public String getAutopayUpperLimitAmt() {
		return autopayUpperLimitAmt;
	}
	public void setAutopayUpperLimitAmt(String autopayUpperLimitAmt) {
		this.autopayUpperLimitAmt = autopayUpperLimitAmt;
	}
	public String getBankAcctHolderName() {
		return bankAcctHolderName;
	}
	public void setBankAcctHolderName(String bankAcctHolderName) {
		this.bankAcctHolderName = bankAcctHolderName;
	}
	public String getBankAcctNum() {
		return bankAcctNum;
	}
	public void setBankAcctNum(String bankAcctNum) {
		this.bankAcctNum = bankAcctNum;
	}
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
	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}
	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = creditCardHolderName;
	}
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public String getCreditCardType() {
		return creditCardType;
	}
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	public String getCreditExpiryMonth() {
		return creditExpiryMonth;
	}
	public void setCreditExpiryMonth(String creditExpiryMonth) {
		this.creditExpiryMonth = creditExpiryMonth;
	}
	public String getCreditExpiryYear() {
		return creditExpiryYear;
	}
	public void setCreditExpiryYear(String creditExpiryYear) {
		this.creditExpiryYear = creditExpiryYear;
	}
	public InputStream getCustSignatureAutoPay() {
		return custSignatureAutoPay;
	}
	public void setCustSignatureAutoPay(InputStream custSignatureAutoPay) {
		this.custSignatureAutoPay = custSignatureAutoPay;
	}
	public String getPayMethodType() {
		return payMethodType;
	}
	public void setPayMethodType(String payMethodType) {
		this.payMethodType = payMethodType;
	}
	public String getThirdPartyInd() {
		return thirdPartyInd;
	}
	public void setThirdPartyInd(String thirdPartyInd) {
		this.thirdPartyInd = thirdPartyInd;
	}

}
