package com.bomwebportal.mob.ds.dto;

import java.util.Date;

public class MobDsQaRecordDTO {
	private String orderId;
	private int seqNo;
	private Date dateTime;
	private String staffId;
	private String mustQ;
	private int qaOption;
	private String optionDesc;
	private String remark;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getMustQ() {
		return mustQ;
	}
	public void setMustQ(String mustQ) {
		this.mustQ = mustQ;
	}
	public int getQaOption() {
		return qaOption;
	}
	public void setQaOption(int qaOption) {
		this.qaOption = qaOption;
	}
	public String getOptionDesc() {
		return optionDesc;
	}
	public void setOptionDesc(String optionDesc) {
		this.optionDesc = optionDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
