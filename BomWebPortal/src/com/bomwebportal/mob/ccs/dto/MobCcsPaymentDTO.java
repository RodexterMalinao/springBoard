package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

public class MobCcsPaymentDTO implements java.io.Serializable{

	private static final long serialVersionUID = -261947158608436619L;

	public MobCcsPaymentDTO(){
	    this.payMethodKey = "-1";
	}
	
	private String orderId;

	private String payMethodKey;
	
	private String creditCardHolderName;
	
	//M - Cash, C - Credit Card, A-AutoPay
	private String payMethodType;
	
	private String thirdPartyInd;
	
	private String creditCardType;
	
	private String creditCardNum;
	
	private String creditExpiryMonth;
	
	private String creditExpiryYear;
	
	private String creditExpiryDate;
	
	private String creditCardIssueBankCd;
	
	private String creditCardIssueBankName; //add by herbert 20110721 
	
	private String creditCardDocType;
	
	private String creditCardDocNum;
	
	private String ceksSubmit;
	
	private String username;
	
	private String creditCardIssueBank;

	private String bankAcctHolderIdType; //autoPay, add 20110602
	private String bankAcctHolderIdNum; //autoPay, add 20110602
	private String bankCode; //autoPay, add 20110602
	private String branchCode; //autoPay, add 20110602
	private String bankAcctHolderName; //autoPay, add 20110602
	private String autopayUpperLimitAmt; //autoPay, add 20110602
	private String bankAcctNum;	 //autoPay, add 20110602
	private String autopayApplDateStr; //autoPay, add 20110602
	private Date autopayApplDate ; //autoPay , add 20110610
	private String bankName;//20110609 for summary display
	private String branchName;//20110609 for summary display
	private String paymentCombination;
	private String inAdvanceAmount;
	
	public Date getAutopayApplDate() {
		return autopayApplDate;
	}

	public void setAutopayApplDate(Date autopayApplDate) {
		this.autopayApplDate = autopayApplDate;
	}

	public String getBankAcctHolderIdType() {
		return bankAcctHolderIdType;
	}

	public String getBankAcctHolderIdNum() {
		return bankAcctHolderIdNum;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getBankAcctHolderName() {
		return bankAcctHolderName;
	}

	public String getAutopayUpperLimitAmt() {
		return autopayUpperLimitAmt;
	}

	public String getBankAcctNum() {
		return bankAcctNum;
	}

	public String getAutopayApplDateStr() {
		return autopayApplDateStr;
	}

	public void setBankAcctHolderIdType(String bankAcctHolderIdType) {
		this.bankAcctHolderIdType = bankAcctHolderIdType;
	}

	public void setBankAcctHolderIdNum(String bankAcctHolderIdNum) {
		this.bankAcctHolderIdNum = bankAcctHolderIdNum;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setBankAcctHolderName(String bankAcctHolderName) {
		this.bankAcctHolderName = bankAcctHolderName;
	}

	public void setAutopayUpperLimitAmt(String autopayUpperLimitAmt) {
		this.autopayUpperLimitAmt = autopayUpperLimitAmt;
	}

	public void setBankAcctNum(String bankAcctNum) {
		this.bankAcctNum = bankAcctNum;
	}

	public void setAutopayApplDateStr(String autopayApplDateStr) {
		this.autopayApplDateStr = autopayApplDateStr;
	}

	public String getCreditCardIssueBank() {
		return creditCardIssueBank;
	}

	public void setCreditCardIssueBank(String creditCardIssueBank) {
		this.creditCardIssueBank = creditCardIssueBank;
	}

	public String getPayMethodKey() {
		return payMethodKey;
	}

	public void setPayMethodKey(String payMethodKey) {
		this.payMethodKey = payMethodKey;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCreditCardIssueBankCd() {
		return creditCardIssueBankCd;
	}

	public void setCreditCardIssueBankCd(String creditCardIssueBankCd) {
		this.creditCardIssueBankCd = creditCardIssueBankCd;
	}
	
	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	
	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}

	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = creditCardHolderName;
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
	
	public String getCreditExpiryDate() {
		return creditExpiryDate;
	}

	public void setCreditExpiryDate(String creditExpiryDate) {
		this.creditExpiryDate = creditExpiryDate;
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

	public String getCreditCardDocType() {
		return creditCardDocType;
	}

	public void setCreditCardDocType(String creditCardDocType) {
		this.creditCardDocType = creditCardDocType;
	}
	
	public String getCreditCardDocNum() {
		return creditCardDocNum;
	}

	public void setCreditCardDocNum(String creditCardDocNum) {
		this.creditCardDocNum = creditCardDocNum;
	}

	public String getCeksSubmit() {
		return ceksSubmit;
	}

	public void setCeksSubmit(String ceksSubmit) {
		this.ceksSubmit = ceksSubmit;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getCreditCardIssueBankName() {
		return creditCardIssueBankName;
	}

	public void setCreditCardIssueBankName(String creditCardIssueBankName) {
		this.creditCardIssueBankName = creditCardIssueBankName;
	}

	public String getPaymentCombination() {
	    return paymentCombination;
	}

	public void setPaymentCombination(String paymentCombination) {
	    this.paymentCombination = paymentCombination;
	}

	public String getInAdvanceAmount() {
	    return inAdvanceAmount;
	}

	public void setInAdvanceAmount(String inAdvanceAmount) {
	    this.inAdvanceAmount = inAdvanceAmount;
	}
	
}
