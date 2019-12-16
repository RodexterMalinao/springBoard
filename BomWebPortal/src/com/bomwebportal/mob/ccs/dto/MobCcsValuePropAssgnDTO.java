package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

public class MobCcsValuePropAssgnDTO {
	public String getValuePropId() {
		return valuePropId;
	}
	public void setValuePropId(String valuePropId) {
		this.valuePropId = valuePropId;
	}
	public String getValuePropDesc() {
		return valuePropDesc;
	}
	public void setValuePropDesc(String valuePropDesc) {
		this.valuePropDesc = valuePropDesc;
	}
	public int getCustomerTierId() {
		return customerTierId;
	}
	public void setCustomerTierId(int customerTierId) {
		this.customerTierId = customerTierId;
	}
	public String getCustomerTierDesc() {
		return customerTierDesc;
	}
	public void setCustomerTierDesc(String customerTierDesc) {
		this.customerTierDesc = customerTierDesc;
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
	private String valuePropId;
	private String valuePropDesc;
	private int customerTierId;
	private String customerTierDesc;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
}
