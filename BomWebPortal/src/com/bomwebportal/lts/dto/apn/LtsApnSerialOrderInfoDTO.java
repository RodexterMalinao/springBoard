package com.bomwebportal.lts.dto.apn;

import java.io.Serializable;

public class LtsApnSerialOrderInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7891792133171677106L;
	private String orderId;
	private String dtlId;
	private String srvNn;
	private String isPortBack;
	private String duplexAction;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDtlId() {
		return dtlId;
	}
	public void setDtlId(String dtlId) {
		this.dtlId = dtlId;
	}
	public String getSrvNn() {
		return srvNn;
	}
	public void setSrvNn(String srvNn) {
		this.srvNn = srvNn;
	}
	public String getIsPortBack() {
		return isPortBack;
	}
	public void setIsPortBack(String isPortBack) {
		this.isPortBack = isPortBack;
	}
	public String getDuplexAction() {
		return duplexAction;
	}
	public void setDuplexAction(String duplexAction) {
		this.duplexAction = duplexAction;
	}
}
