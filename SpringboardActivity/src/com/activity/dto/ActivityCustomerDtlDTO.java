package com.activity.dto;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class ActivityCustomerDtlDTO extends ObjectActionBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3839630569913681557L;
	private String custNo;
	private String custType;
	
	public String getCustNo() {
		return this.custNo;
	}
	public void setCustNo(String pCustNo) {
		this.custNo = pCustNo;
	}
	public String getCustType() {
		return this.custType;
	}
	public void setCustType(String pCustType) {
		this.custType = pCustType;
	}
	
	

}
