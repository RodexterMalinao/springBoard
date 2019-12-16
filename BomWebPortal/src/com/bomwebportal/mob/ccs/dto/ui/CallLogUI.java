package com.bomwebportal.mob.ccs.dto.ui;

import java.util.Date;

public class CallLogUI {
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
	private String orderId;
	private String contactName;
	private String contactPhone;
	private String contactEmail;
	private String nature;
	private String result;
	private String remark;
	private String callTypeCd;
	private Date createDate;
}
