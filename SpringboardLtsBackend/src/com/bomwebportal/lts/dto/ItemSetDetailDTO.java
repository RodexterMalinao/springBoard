package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class ItemSetDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3949763742215618996L;
	
	private String itemSetId;
	private String itemSetCode;
	private String itemSetType;
	private String itemSetDesc;
	private int maxQty;
	private int minQty;
	private boolean defaultInd;
	private ItemSetAttbDTO[] itemSetAttbs;
	private ItemDetailDTO[] itemDetails;
	
	private String displayDesc;
	private String displayHtml;
	
	private String selectedItemId;
	private String housingType;
	
	public String getDisplayDesc() {
		return displayDesc;
	}
	public void setDisplayDesc(String displayDesc) {
		this.displayDesc = displayDesc;
	}
	public String getDisplayHtml() {
		return displayHtml;
	}
	public void setDisplayHtml(String displayHtml) {
		this.displayHtml = displayHtml;
	}
	public String getItemSetId() {
		return itemSetId;
	}
	public void setItemSetId(String itemSetId) {
		this.itemSetId = itemSetId;
	}
	public String getItemSetCode() {
		return itemSetCode;
	}
	public void setItemSetCode(String itemSetCode) {
		this.itemSetCode = itemSetCode;
	}
	public String getItemSetType() {
		return itemSetType;
	}
	public void setItemSetType(String itemSetType) {
		this.itemSetType = itemSetType;
	}
	public String getItemSetDesc() {
		return itemSetDesc;
	}
	public void setItemSetDesc(String itemSetDesc) {
		this.itemSetDesc = itemSetDesc;
	}
	public int getMaxQty() {
		return maxQty;
	}
	public void setMaxQty(int maxQty) {
		this.maxQty = maxQty;
	}
	public int getMinQty() {
		return minQty;
	}
	public void setMinQty(int minQty) {
		this.minQty = minQty;
	}
	public boolean isDefaultInd() {
		return defaultInd;
	}
	public void setDefaultInd(boolean defaultInd) {
		this.defaultInd = defaultInd;
	}
	public ItemSetAttbDTO[] getItemSetAttbs() {
		return itemSetAttbs;
	}
	public void setItemSetAttbs(ItemSetAttbDTO[] itemSetAttbs) {
		this.itemSetAttbs = itemSetAttbs;
	}
	public ItemDetailDTO[] getItemDetails() {
		return itemDetails;
	}
	public void setItemDetails(ItemDetailDTO[] itemDetails) {
		this.itemDetails = itemDetails;
	}
	public String getSelectedItemId() {
		return selectedItemId;
	}
	public void setSelectedItemId(String selectedItemId) {
		this.selectedItemId = selectedItemId;
	}
	public String getHousingType() {
		return housingType;
	}
	public void setHousingType(String housingType) {
		this.housingType = housingType;
	}
	
	
}
