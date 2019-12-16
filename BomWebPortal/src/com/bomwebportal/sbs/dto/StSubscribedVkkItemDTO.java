package com.bomwebportal.sbs.dto;

import java.util.Date;

public class StSubscribedVkkItemDTO extends StSubscribedItemDTO {
	private String msisdn;
	private Date expDate;
	private String password;
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
