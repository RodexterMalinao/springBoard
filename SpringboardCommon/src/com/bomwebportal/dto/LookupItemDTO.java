package com.bomwebportal.dto;

import java.io.Serializable;

public class LookupItemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2089871239759487895L;
	
	private String itemKey = null;
	private Object itemValue = null;

	
	public String getItemKey() {
		return itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	public Object getItemValue() {
		return itemValue;
	}

	public void setItemValue(Object itemValue) {
		this.itemValue = itemValue;
	}
}
