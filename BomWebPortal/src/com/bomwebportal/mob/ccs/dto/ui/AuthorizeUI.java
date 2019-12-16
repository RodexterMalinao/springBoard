package com.bomwebportal.mob.ccs.dto.ui;

public class AuthorizeUI {
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getCategoryLogin() {
		return categoryLogin;
	}
	public void setCategoryLogin(String categoryLogin) {
		this.categoryLogin = categoryLogin;
	}
	
	public String getReasonCd() {
		return reasonCd;
	}
	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReasonType() {
		return reasonType;
	}
	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

	public String getReasonRemarkInputInd() {
		return reasonRemarkInputInd;
	}
	public void setReasonRemarkInputInd(String reasonRemarkInputInd) {
		this.reasonRemarkInputInd = reasonRemarkInputInd;
	}
	public String getByPassAuthInd() {
		return byPassAuthInd;
	}
	public void setByPassAuthInd(String byPassAuthInd) {
		this.byPassAuthInd = byPassAuthInd;
	}

	
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}


	private String username;
	private String password;
	private String orderId;
	private String action;
	private String categoryLogin;
	
	private String reasonCd;
	private String remark;
	private String reasonType;
	
	private String reasonRemarkInputInd;
	private String byPassAuthInd;
	
	private String basketId;
	
}
