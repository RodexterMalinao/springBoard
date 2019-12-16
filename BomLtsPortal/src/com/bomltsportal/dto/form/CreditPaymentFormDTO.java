package com.bomltsportal.dto.form;

import java.io.Serializable;

public class CreditPaymentFormDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3505287342256044448L;
	
	public static enum PaymentStatus{
		INITIAL,
		READY,
		FINISH
	}
	
	private String paymentInd;
	
	private String ccNum;
	private String ccExpDate;
	private String failPayment;
	private PaymentStatus payStatus;
	private String ccType;
	private String bomCcType;
	private String queryString;
	private String responseCode;
	
	private String merchantId;
	private String referenceNum;
	private String amt;
	private String locale;
	private String currCode;
	private String opCode;
	private String ePayLinkTxId;
	private String authCode;
	private String acqOrderId;
	
	
	public String getPaymentInd() {
		return paymentInd;
	}
	public void setPaymentInd(String paymentInd) {
		this.paymentInd = paymentInd;
	}
	public String getCcNum() {
		return ccNum;
	}
	public void setCcNum(String ccNum) {
		this.ccNum = ccNum;
	}
	public String getCcExpDate() {
		return ccExpDate;
	}
	public void setCcExpDate(String ccExpDate) {
		this.ccExpDate = ccExpDate;
	}
	public String getFailPayment() {
		return failPayment;
	}
	public void setFailPayment(String failPayment) {
		this.failPayment = failPayment;
	}
	
	public String getCcType() {
		return ccType;
	}
	public void setCcType(String ccType) {
		this.ccType = ccType;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public String getReferenceNum() {
		return referenceNum;
	}
	public String getAmt() {
		return amt;
	}
	public String getLocale() {
		return locale;
	}
	public String getCurrCode() {
		return currCode;
	}
	public String getOpCode() {
		return opCode;
	}
	public String getePayLinkTxId() {
		return ePayLinkTxId;
	}
	public String getAuthCode() {
		return authCode;
	}
	public String getAcqOrderId() {
		return acqOrderId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public void setCurrCode(String currCode) {
		this.currCode = currCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public void setePayLinkTxId(String ePayLinkTxId) {
		this.ePayLinkTxId = ePayLinkTxId;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public void setAcqOrderId(String acqOrderId) {
		this.acqOrderId = acqOrderId;
	}
	public String getBomCcType() {
		return bomCcType;
	}
	public void setBomCcType(String bomCcType) {
		this.bomCcType = bomCcType;
	}
	public PaymentStatus getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(PaymentStatus payStatus) {
		this.payStatus = payStatus;
	}
}
