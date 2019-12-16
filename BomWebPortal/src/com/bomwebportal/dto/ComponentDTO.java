package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.Date;

public class ComponentDTO implements Serializable{

	private static final long serialVersionUID = 6973983400217464954L;
	
	private String orderId;
	private String compAttbId;
	private String compAttbVal;
	private Date createDate;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCompAttbId() {
		return compAttbId;
	}
	public void setCompAttbId(String compAttbId) {
		this.compAttbId = compAttbId;
	}
	public String getCompAttbVal() {
		return compAttbVal;
	}
	public void setCompAttbValue(String compAttbVal) {
		this.compAttbVal = compAttbVal;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
