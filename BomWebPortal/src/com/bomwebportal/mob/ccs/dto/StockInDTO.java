package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class StockInDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -694367723008012934L;

	private String type;
	private String model;
	private String itemCode;
	private String imei;
	private String location;
	private String status;
	private String remarks;
	private String quantity; // add by Joyce 20120320
	private String batchRef; // add by Joyce 20120320
	private String stockPool;
	private String storeCode;
	private String teamCode;
	private String eventCode;
	private String staffId;
	private String bookOutDate;
	private String mipSimType;
	
	private String actionType;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionType() {
		return actionType;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getBatchRef() {
		return batchRef;
	}
	public void setBatchRef(String batchRef) {
		this.batchRef = batchRef;
	}
	public String getStockPool() {
		return stockPool;
	}
	public void setStockPool(String stockPool) {
		this.stockPool = stockPool;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getBookOutDate() {
		return bookOutDate;
	}
	public void setBookOutDate(String bookOutDate) {
		this.bookOutDate = bookOutDate;
	}
	public String getMipSimType() {
		return mipSimType;
	}
	public void setMipSimType(String mipSimType) {
		this.mipSimType = mipSimType;
	}
	
}
