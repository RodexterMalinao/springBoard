package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class StockManualDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 730698246247506661L;
	
	private String orderId;
	private String itemType;
	private String itemCode;
	private String descriptions;
	private String serialNum;
	private String location;
	private String locationId;
	private String oldItemCode;
	private String oldSerialNum;
	private String issueInd;
	
	public String getIssueInd() {
		return issueInd;
	}
	public void setIssueInd(String issueInd) {
		this.issueInd = issueInd;
	}
	public String getOldItemCode() {
		return oldItemCode;
	}
	public void setOldItemCode(String oldItemCode) {
		this.oldItemCode = oldItemCode;
	}
	/**
	 * @return the oldSerialNum
	 */
	public String getOldSerialNum() {
		return oldSerialNum;
	}
	/**
	 * @param oldSerialNum the oldSerialNum to set
	 */
	public void setOldSerialNum(String oldSerialNum) {
		this.oldSerialNum = oldSerialNum;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public String getDescriptions() {
		return descriptions;
	}
}
