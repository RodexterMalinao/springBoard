package com.activity.dto;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class ActivityAttributeDTO extends ObjectActionBaseDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1455680415038196481L;

	private String statusSeq;
	private String attbType;
	private String attbId;	
	private String attbValue;

	public String getStatusSeq() {
		return this.statusSeq;
	}
	public void setStatusSeq(String pStatusSeq) {
		this.statusSeq = pStatusSeq;
	}
	public String getAttbType() {
		return this.attbType;
	}
	public void setAttbType(String pAttbType) {
		this.attbType = pAttbType;
	}
	public String getAttbId() {
		return this.attbId;
	}
	public void setAttbId(String pAttbId) {
		this.attbId = pAttbId;
	}
	public String getAttbValue() {
		return this.attbValue;
	}
	public void setAttbValue(String pAttbValue) {
		this.attbValue = pAttbValue;
	}
	
	
}
