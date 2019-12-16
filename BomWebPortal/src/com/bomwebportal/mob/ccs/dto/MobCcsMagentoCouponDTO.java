package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class MobCcsMagentoCouponDTO implements Serializable{
	private String orderId;
	private String couponNum;
	private Double faceVal;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCouponNum() {
		return couponNum;
	}
	public void setCouponNum(String couponNum) {
		this.couponNum = couponNum;
	}
	public Double getFaceVal() {
		return faceVal;
	}
	public void setFaceVal(Double faceVal) {
		this.faceVal = faceVal;
	}
	
	
	
}
