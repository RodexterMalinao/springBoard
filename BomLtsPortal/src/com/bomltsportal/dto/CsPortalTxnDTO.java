package com.bomltsportal.dto;

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
	
	
}
