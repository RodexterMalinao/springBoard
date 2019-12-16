package com.bomwebportal.lts.dto.bom;

import java.io.Serializable;

public class BomAttbDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6240424028395379066L;
	
	private String attbId;
	private String attbDesc;
	private String attbValue;
	private String attbLevel; // 'O'=Offer; 'P'=Product; 'C'=Component;
	private String attbTypeDesc;
	
	public String getAttbId() {
		return attbId;
	}
	public void setAttbId(String attbId) {
		this.attbId = attbId;
	}
	public String getAttbDesc() {
		return attbDesc;
	}
	public void setAttbDesc(String attbDesc) {
		this.attbDesc = attbDesc;
	}
	public String getAttbValue() {
		return attbValue;
	}
	public void setAttbValue(String attbValue) {
		this.attbValue = attbValue;
	}
	public String getAttbLevel() {
		return attbLevel;
	}
	public void setAttbLevel(String attbLevel) {
		this.attbLevel = attbLevel;
	}
	public String getAttbTypeDesc() {
		return attbTypeDesc;
	}
	public void setAttbTypeDesc(String attbTypeDesc) {
		this.attbTypeDesc = attbTypeDesc;
	}
	
	
}
