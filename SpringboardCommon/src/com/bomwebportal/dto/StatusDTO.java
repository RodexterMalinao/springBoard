package com.bomwebportal.dto;


import java.io.Serializable;

public class StatusDTO implements Serializable {

	private static final long serialVersionUID = -2297906460818102379L;
	
	private String status = null;
	private String statusDate = null;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
	
	public String getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}
}
