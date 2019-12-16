package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class TenureDTO implements Serializable {

	private static final long serialVersionUID = -7600686207390646769L;
	
	private String custNum = null;
	private int tenure = 0;
	private String serviceId = null;

	public String getCustNum() {
		return custNum;
	}

	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}

	public int getTenure() {
		return tenure;
	}

	public void setTenure(int tenure) {
		this.tenure = tenure;
	}

	//legacy
	public void setTenure(double tenure) {
		this.tenure = (int) tenure;
	}
	
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
}
