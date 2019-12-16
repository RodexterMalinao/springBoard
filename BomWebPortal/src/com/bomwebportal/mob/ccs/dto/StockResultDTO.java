package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class StockResultDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6461674179894112997L;
	
	private String type;
	private String model;
	private String itemCode;
	private String imei;
	private String status;
	private Date stockInDate;
	private String location;
	private String orderId; // add by Joyce 20120227
	private String remarks;
	private String batchRef; // add by Joyce 20120320
	private String stockPool;
	private String eventCode;
	private String storeCode;
	private String teamCode;
	private String staffID;
	
	public String getStockPool() {
		return stockPool;
	}
	public void setStockPool(String stockPool) {
		this.stockPool = stockPool;
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
	public Date getStockInDate() {
		return stockInDate;
	}
	public void setStockInDate(Date stockInDate) {
		this.stockInDate = stockInDate;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getModel() {
		return model;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getBatchRef() {
		return batchRef;
	}
	public void setBatchRef(String batchRef) {
		this.batchRef = batchRef;
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
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

}
