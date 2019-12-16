package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class BasketPremiumDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9177138330142548700L;
	
	private String itemId;
	
	private String itemDesc;
	
	private String basketDesc;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getBasketDesc() {
		return basketDesc;
	}

	public void setBasketDesc(String basketDesc) {
		this.basketDesc = basketDesc;
	}

}
