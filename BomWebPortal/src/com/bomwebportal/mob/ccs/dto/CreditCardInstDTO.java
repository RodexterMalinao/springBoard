package com.bomwebportal.mob.ccs.dto;

public class CreditCardInstDTO implements java.io.Serializable{

	private static final long serialVersionUID = -444574146923263535L;
	
	public String bankCode;
	public String bankName;
	public String minInstAmt;
	public String instSchedule;
	public String startDate;
	public String endDate;
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getMinInstAmt() {
		return minInstAmt;
	}
	public void setMinInstAmt(String minInstAmt) {
		this.minInstAmt = minInstAmt;
	}
	public String getInstSchedule() {
		return instSchedule;
	}
	public void setInstSchedule(String instSchedule) {
		this.instSchedule = instSchedule;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
