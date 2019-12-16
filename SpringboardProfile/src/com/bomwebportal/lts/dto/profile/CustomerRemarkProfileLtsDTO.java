package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class CustomerRemarkProfileLtsDTO implements Serializable {

	private static final long serialVersionUID = -1056380699061452566L;
	
	private String remarks = null;
	private String remarkSeq = null;
	private String lastUpdDate = null;
	private String lastUpdBy = null;
	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarkSeq() {
		return remarkSeq;
	}
	public void setRemarkSeq(String remarkSeq) {
		this.remarkSeq = remarkSeq;
	}
	public String getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(String lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
}
