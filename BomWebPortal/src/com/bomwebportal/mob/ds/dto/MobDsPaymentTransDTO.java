package com.bomwebportal.mob.ds.dto;

import java.io.Serializable;
import java.util.Date;

public class MobDsPaymentTransDTO implements Serializable {

	private static final long serialVersionUID = 4463412716009580531L;
	private String orderId;
	private String msisdn;
	private String paymentType;
	private String transDate;
	private String transStatus;
	private double paymentAmount;
	private String storeCd;
	private String paymentStoreCd;
	private String paymentItemCd;
	private String invoiceNo;
	private String remark;
	
	private String ccNum;
	private String ccIssueBank;
	private int ccInstSchedule;
	private String ccType;
	private String thirdPartyInd;
	private String ccExpiryMonth;
	private String ccExpiryYear;
	private String ccHolderName;
	private String ccHolderIdType;
	private String ccHolderIdNum;
	private String ccIssueBankName;
	private String approveCode;
	
	private Date createDate;
	private String createBy;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getStoreCd() {
		return storeCd;
	}
	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}
	public String getPaymentStoreCd() {
		return paymentStoreCd;
	}
	public void setPaymentStoreCd(String paymentStoreCd) {
		this.paymentStoreCd = paymentStoreCd;
	}
	public String getPaymentItemCd() {
		return paymentItemCd;
	}
	public void setPaymentItemCd(String paymentItemCd) {
		this.paymentItemCd = paymentItemCd;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCcNum() {
		return ccNum;
	}
	public void setCcNum(String ccNum) {
		this.ccNum = ccNum;
	}
	public String getCcIssueBank() {
		return ccIssueBank;
	}
	public void setCcIssueBank(String ccIssueBank) {
		this.ccIssueBank = ccIssueBank;
	}
	public int getCcInstSchedule() {
		return ccInstSchedule;
	}
	public void setCcInstSchedule(int ccInstSchedule) {
		this.ccInstSchedule = ccInstSchedule;
	}
	public String getCcType() {
		return ccType;
	}
	public void setCcType(String ccType) {
		this.ccType = ccType;
	}
	public String getThirdPartyInd() {
		return thirdPartyInd;
	}
	public void setThirdPartyInd(String thirdPartyInd) {
		this.thirdPartyInd = thirdPartyInd;
	}
	public String getCcExpiryMonth() {
		return ccExpiryMonth;
	}
	public void setCcExpiryMonth(String ccExpiryMonth) {
		this.ccExpiryMonth = ccExpiryMonth;
	}
	public String getCcExpiryYear() {
		return ccExpiryYear;
	}
	public void setCcExpiryYear(String ccExpiryYear) {
		this.ccExpiryYear = ccExpiryYear;
	}
	public String getCcHolderName() {
		return ccHolderName;
	}
	public void setCcHolderName(String ccHolderName) {
		this.ccHolderName = ccHolderName;
	}
	public String getCcHolderIdType() {
		return ccHolderIdType;
	}
	public void setCcHolderIdType(String ccHolderIdType) {
		this.ccHolderIdType = ccHolderIdType;
	}
	public String getCcHolderIdNum() {
		return ccHolderIdNum;
	}
	public void setCcHolderIdNum(String ccHolderIdNum) {
		this.ccHolderIdNum = ccHolderIdNum;
	}
	public String getCcIssueBankName() {
		return ccIssueBankName;
	}
	public void setCcIssueBankName(String ccIssueBankName) {
		this.ccIssueBankName = ccIssueBankName;
	}
	public String getApproveCode() {
		return approveCode;
	}
	public void setApproveCode(String approveCode) {
		this.approveCode = approveCode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
}
