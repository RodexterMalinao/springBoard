package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

public class CallLogDTO {
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactBy() {
		return contactBy;
	}
	public void setContactBy(String contactBy) {
		this.contactBy = contactBy;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCallTypeCd() {
		return callTypeCd;
	}
	public void setCallTypeCd(String callTypeCd) {
		this.callTypeCd = callTypeCd;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	private String orderId;
	private String contactName;
	private String contactPhone;
	private String contactEmail;
	private String contactBy;
	private String nature;
	private String result;
	private String remark;
	private Date createDate;
	private String callTypeCd;
	private String rowId;
}
