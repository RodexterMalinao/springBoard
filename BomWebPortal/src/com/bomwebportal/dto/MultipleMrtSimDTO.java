package com.bomwebportal.dto;

import java.io.Serializable;

public class MultipleMrtSimDTO implements Serializable {


	private static final long serialVersionUID = -2083738657908818440L;

	// for report display use
	private String orderId;
	private String mrtItemId;
	private String mrtAttbId;
	private String mrtMsisdn;
	private String simItemId;
	private String simAttbId;
	private String simIccid;
	private String rownum;
	
	public MultipleMrtSimDTO() {
		
	}
	
	public MultipleMrtSimDTO(String mrtMsisdn, String simIccid) {
		this.mrtMsisdn = mrtMsisdn;
		this.simIccid = simIccid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMrtItemId() {
		return mrtItemId;
	}

	public void setMrtItemId(String mrtItemId) {
		this.mrtItemId = mrtItemId;
	}

	public String getMrtAttbId() {
		return mrtAttbId;
	}

	public void setMrtAttbId(String mrtAttbId) {
		this.mrtAttbId = mrtAttbId;
	}

	public String getMrtMsisdn() {
		return mrtMsisdn;
	}

	public void setMrtMsisdn(String mrtMsisdn) {
		this.mrtMsisdn = mrtMsisdn;
	}

	public String getSimItemId() {
		return simItemId;
	}

	public void setSimItemId(String simItemId) {
		this.simItemId = simItemId;
	}

	public String getSimAttbId() {
		return simAttbId;
	}

	public void setSimAttbId(String simAttbId) {
		this.simAttbId = simAttbId;
	}

	public String getSimIccid() {
		return simIccid;
	}

	public void setSimIccid(String simIccid) {
		this.simIccid = simIccid;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

}
