package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.Date;

public class ImsBomPCDOrderDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1747524460939937875L;
	
	private String fsa = null;
	private String custNo = null;
	private Date applicationStartDate;
	private Date applicationEndDate;
	public String getFsa() {
		return fsa;
	}
	public void setFsa(String fsa) {
		this.fsa = fsa;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public Date getApplicationStartDate() {
		return applicationStartDate;
	}
	public void setApplicationStartDate(Date applicationStartDate) {
		this.applicationStartDate = applicationStartDate;
	}
	public Date getApplicationEndDate() {
		return applicationEndDate;
	}
	public void setApplicationEndDate(Date applicationEndDate) {
		this.applicationEndDate = applicationEndDate;
	}
	

	
	
}
