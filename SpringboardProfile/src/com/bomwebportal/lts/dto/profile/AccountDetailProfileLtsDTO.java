package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class AccountDetailProfileLtsDTO implements Serializable {

	private static final long serialVersionUID = 2173520260396701411L;
	
	private String acctNum = null;
	private String creditStatus = null;
	private String acctStatus= null;
	private String acctChrgType = null;
	private String payMethod = null;
	private String outstandingAmount= null;
	private String billMedia = null;
	private String billFmt = null;
	private String billFreq = null;
	private String billLang = null;
	private boolean autopayStatementInd = false;
	private String serialNum = null;
	private boolean primaryAcctInd = false;
	private String bankCd = null;
	private String branchCd = null;
	private String bankAcctNum = null;
	private String creditCardNum = null;
	private String billPeriod = null;
	private String acctName = null;
	private String emailAddr = null;
	private String billAddr = null;
	private String redemMedia = null;
	private String waivePaperReaCd = null;
	private String waivePaperInd = null;
			
	private CustomerDetailProfileLtsDTO customerProfile = null;

	
	public String getAcctNum() {
		return acctNum;
	}

	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

	public String getCreditStatus() {
		return creditStatus;
	}

	public void setCreditStatus(String creditStatus) {
		this.creditStatus = creditStatus;
	}

	public String getAcctStatus() {
		return acctStatus;
	}

	public void setAcctStatus(String acctStatus) {
		this.acctStatus = acctStatus;
	}

	public String getAcctChrgType() {
		return acctChrgType;
	}

	public void setAcctChrgType(String acctChrgType) {
		this.acctChrgType = acctChrgType;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(String outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public boolean isPrimaryAcctInd() {
		return primaryAcctInd;
	}

	public void setPrimaryAcctInd(boolean primaryAcctInd) {
		this.primaryAcctInd = primaryAcctInd;
	}

	public CustomerDetailProfileLtsDTO getCustomerProfile() {
		return customerProfile;
	}

	public void setCustomerProfile(CustomerDetailProfileLtsDTO customerProfile) {
		this.customerProfile = customerProfile;
	}

	public String getBillMedia() {
		return billMedia;
	}

	public void setBillMedia(String billMedia) {
		this.billMedia = billMedia;
	}

	public String getBillFmt() {
		return billFmt;
	}

	public void setBillFmt(String billFmt) {
		this.billFmt = billFmt;
	}

	public String getBillFreq() {
		return billFreq;
	}

	public void setBillFreq(String billFreq) {
		this.billFreq = billFreq;
	}

	public String getBillLang() {
		return billLang;
	}

	public void setBillLang(String billLang) {
		this.billLang = billLang;
	}

	public boolean isAutopayStatementInd() {
		return autopayStatementInd;
	}

	public void setAutopayStatementInd(boolean autopayStatementInd) {
		this.autopayStatementInd = autopayStatementInd;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getBankCd() {
		return this.bankCd;
	}

	public void setBankCd(String pBankCd) {
		this.bankCd = pBankCd;
	}

	public String getBranchCd() {
		return this.branchCd;
	}

	public void setBranchCd(String pBranchCd) {
		this.branchCd = pBranchCd;
	}

	public String getBankAcctNum() {
		return this.bankAcctNum;
	}

	public void setBankAcctNum(String pBankAcctNum) {
		this.bankAcctNum = pBankAcctNum;
	}

	public String getCreditCardNum() {
		return this.creditCardNum;
	}

	public void setCreditCardNum(String pCreditCardNum) {
		this.creditCardNum = pCreditCardNum;
	}

	public String getBillPeriod() {
		return billPeriod;
	}

	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getBillAddr() {
		return billAddr;
	}

	public void setBillAddr(String billAddr) {
		this.billAddr = billAddr;
	}

	public String getRedemMedia() {
		return redemMedia;
	}

	public void setRedemMedia(String redemMedia) {
		this.redemMedia = redemMedia;
	}

	public String getWaivePaperReaCd() {
		return waivePaperReaCd;
	}

	public void setWaivePaperReaCd(String waivePaperReaCd) {
		this.waivePaperReaCd = waivePaperReaCd;
	}

	public String getWaivePaperInd() {
		return waivePaperInd;
	}

	public void setWaivePaperInd(String waivePaperInd) {
		this.waivePaperInd = waivePaperInd;
	}
}
