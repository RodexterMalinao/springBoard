package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class CsPortalTxnDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8403223454186800032L;
	
	private String orderId;
	private String apiTy;
	private String sysId;
	private String reply;
	private String reqObj;
	private String clubReply;
	private String myhktReply;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getApiTy() {
		return apiTy;
	}
	public void setApiTy(String apiTy) {
		this.apiTy = apiTy;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getReqObj() {
		return reqObj;
	}
	public void setReqObj(String reqObj) {
		this.reqObj = reqObj;
	}
	public String getClubReply() {
		return clubReply;
	}
	public void setClubReply(String clubReply) {
		this.clubReply = clubReply;
	}
	public String getMyhktReply() {
		return myhktReply;
	}
	public void setMyhktReply(String myhktReply) {
		this.myhktReply = myhktReply;
	}
	
	
}
