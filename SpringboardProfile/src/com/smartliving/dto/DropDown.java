package com.smartliving.dto;

import java.io.Serializable;

public class DropDown implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6886753245880399974L;
	String key;
	String value;
	public String getKey() {
		return this.key;
	}
	public void setKey(String pKey) {
		this.key = pKey;
	}
	public String getValue() {
		return this.value;
	}
	public void setValue(String pValue) {
		this.value = pValue;
	}
}
