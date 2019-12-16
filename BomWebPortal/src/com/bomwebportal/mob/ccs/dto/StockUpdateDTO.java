package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class StockUpdateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4718621840354967178L;

	private String type;
	private String model;
	private String itemCode;
	private String imei;
	private String location;
	private String status;
	private String oldStatus;
	private String remarks;
	private String batchRef; // add by Joyce 20120320
	private String orderId; // add by Joyce 20120327
	private String oldStockPool;
	private String stockPool;
	private String actionType;
	private String eventCode;
	private String storeCode;
	private String teamCode;
	private String staffId;
	private String channelCd;
	private String itemSerialReal;
	
	
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
	public String getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionType() {
		return actionType;
	}
	public String getBatchRef() {
		return batchRef;
	}
	public void setBatchRef(String batchRef) {
		this.batchRef = batchRef;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getOldStockPool() {
		return oldStockPool;
	}
	public void setOldStockPool(String oldStockPool) {
		this.oldStockPool = oldStockPool;
	}
	public String getStockPool() {
		return stockPool;
	}
	public void setStockPool(String stockPool) {
		this.stockPool = stockPool;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
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
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	public String getItemSerialReal() {
		return itemSerialReal;
	}
	public void setItemSerialReal(String itemSerialReal) {
		this.itemSerialReal = itemSerialReal;
	}
	
	
	
}
