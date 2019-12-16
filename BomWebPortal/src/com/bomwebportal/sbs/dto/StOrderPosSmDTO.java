package com.bomwebportal.sbs.dto;

import java.util.Date;

public class StOrderPosSmDTO {

	private String orderId;
	private String smNum;
	private String smTypeDesc;
	private String remark;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	
	private String smType;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSmNum() {
		return smNum;
	}
	public void setSmNum(String smNum) {
		this.smNum = smNum;
	}
	public String getSmTypeDesc() {
		return smTypeDesc;
	}
	public void setSmTypeDesc(String smTypeDesc) {
		this.smTypeDesc = smTypeDesc;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public String getSmType() {
		return smType;
	}
	public void setSmType(String smType) {
		this.smType = smType;
	}

	
	
}
