package com.bomwebportal.dto;

import java.io.Serializable;

public class BasketParmDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 81447919458841641L;
	
	private int basketId;
	private String parmType;
	private String parmTypeVal;
	private int parmTypeId;
	public int getBasketId() {
		return basketId;
	}
	public void setBasketId(int basketId) {
		this.basketId = basketId;
	}
	public String getParmType() {
		return parmType;
	}
	public void setParmType(String parmType) {
		this.parmType = parmType;
	}
	public String getParmTypeVal() {
		return parmTypeVal;
	}
	public void setParmTypeVal(String parmTypeVal) {
		this.parmTypeVal = parmTypeVal;
	}
	public int getParmTypeId() {
		return parmTypeId;
	}
	public void setParmTypeId(int parmTypeId) {
		this.parmTypeId = parmTypeId;
	}

	
	
}
