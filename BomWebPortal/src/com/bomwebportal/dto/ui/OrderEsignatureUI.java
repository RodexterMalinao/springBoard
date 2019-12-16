package com.bomwebportal.dto.ui;

public class OrderEsignatureUI {
	public static enum Action {
		SEARCH
		, SEARCH_SUBMIT
		, PREVIEW
		, SEND
	}
	;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getShopCd() {
		return shopCd;
	}

	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRequestDateStr() {
		return requestDateStr;
	}

	public void setRequestDateStr(String requestDateStr) {
		this.requestDateStr = requestDateStr;
	}

	public String getOrigEmailAddr() {
		return origEmailAddr;
	}

	public void setOrigEmailAddr(String origEmailAddr) {
		this.origEmailAddr = origEmailAddr;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public boolean isEmailSendConfirm() {
		return emailSendConfirm;
	}

	public void setEmailSendConfirm(boolean emailSendConfirm) {
		this.emailSendConfirm = emailSendConfirm;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderType() {
		return orderType;
	}
	public void setReset(String reset) {
		this.reset = reset;
	}

	public String getReset() {
		return reset;
	}

	public void setSMSno(String sMSno) {
		SMSno = sMSno;
	}

	public String getSMSno() {
		return SMSno;
	}

	public void setUpdateBOMInd(String updateBOMInd) {
		this.updateBOMInd = updateBOMInd;
	}

	public String getUpdateBOMInd() {
		return updateBOMInd;
	}

	private Action action;
	private String shopCd;
	private String lob;
	private String orderId;
	private String requestDateStr;
	private String origEmailAddr;
	private String emailAddr;
	private boolean emailSendConfirm;
	private String templateId;
	private String orderType;
	private String reset;
	private String SMSno;
	private String updateBOMInd;
}
