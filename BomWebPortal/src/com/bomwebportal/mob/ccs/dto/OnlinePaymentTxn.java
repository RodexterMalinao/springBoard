package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OnlinePaymentTxn  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8347434157570866894L;
	private String orderId;
	private String appId;
	private String status;
	private String referenceNo;
	private String retCode;
	private BigDecimal payAmount;
	private String cardPan;
	private String expDate;
	private String retParm;
	private String createBy;
	private String lastUpdBy;
	private String send;
	
	private String rowId;
	
	private String epaylinkTxnId;
	private String authCode;
	
	
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public BigDecimal getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}
	public String getCardPan() {
		return cardPan;
	}
	public void setCardPan(String cardPan) {
		this.cardPan = cardPan;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getRetParm() {
		return retParm;
	}
	public void setRetParm(String retParm) {
		this.retParm = retParm;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public String getSend() {
		return send;
	}
	public void setSend(String send) {
		this.send = send;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getEpaylinkTxnId() {
		return epaylinkTxnId;
	}
	public void setEpaylinkTxnId(String epaylinkTxnId) {
		this.epaylinkTxnId = epaylinkTxnId;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	

}
