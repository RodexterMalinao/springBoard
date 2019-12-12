package com.pccw.wq.schema.dto;

import java.io.Serializable;

public class SelectionItemDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -949682659959156586L;
	
	private String itemKey;
	private String itemValue;
	
	public String getItemKey() {
		return this.itemKey;
	}
	public void setItemKey(String pItemKey) {
		this.itemKey = pItemKey;
	}
	public String getItemValue() {
		return this.itemValue;
	}
	public void setItemValue(String pItemValue) {
		this.itemValue = pItemValue;
	}
	
	
	

}
