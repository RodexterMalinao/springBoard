package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class ItemAttbInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5020971227450935428L;

	private String attbInfoKey;
	private String attbValue;
	private String attbDesc;
	
	public String getAttbInfoKey() {
		return attbInfoKey;
	}
	public void setAttbInfoKey(String attbInfoKey) {
		this.attbInfoKey = attbInfoKey;
	}
	public String getAttbValue() {
		return attbValue;
	}
	public void setAttbValue(String attbValue) {
		this.attbValue = attbValue;
	}
	public String getAttbDesc() {
		return attbDesc;
	}
	public void setAttbDesc(String attbDesc) {
		this.attbDesc = attbDesc;
	}
	
	
}
