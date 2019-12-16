package com.bomwebportal.mob.ccs.dto.ui;

import java.io.Serializable;

public class StockQuantityEnquiryUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8365360540922674541L;
	
	private String stockPool;
	private String type;
	private String itemCode;
	private String model;
	private String eventCode;
	private String storeCode;
	private String teamCode;

	public String getStockPool() {
		return stockPool;
	}
	public void setStockPool(String stockPool) {
		this.stockPool = stockPool;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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
	
}
