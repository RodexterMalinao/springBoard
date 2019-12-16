package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class MrtInventoryOrderDTO implements Serializable {
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
	public String getMsisdnlvl() {
		return msisdnlvl;
	}
	public void setMsisdnlvl(String msisdnlvl) {
		this.msisdnlvl = msisdnlvl;
	}
	public Date getSrvReqDate() {
		return srvReqDate;
	}
	public void setSrvReqDate(Date srvReqDate) {
		this.srvReqDate = srvReqDate;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getSalesCode() {
		return salesCode;
	}
	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}
	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}
	private String orderId;
	private String msisdn;
	private String msisdnlvl;
	private Date srvReqDate;
	private Date appDate;
	private String salesCode;
	private String salesName;
	private String orderStatus;
	private String orderStatusDesc;
}
