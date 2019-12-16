package com.bomwebportal.lts.dto.form;

import java.io.Serializable;

public class LtsOrderCancelFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1666543561334290268L;

	private String orderId;
	private String cancelReason;
	private String cancelRemark;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getCancelRemark() {
		return cancelRemark;
	}
	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}
	
	
}
