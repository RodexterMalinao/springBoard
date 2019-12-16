package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class StockModelResultDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3340133427085623713L;

	private String itemCode;
	private String model;
	private String mode;
	
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	
}
