package com.bomwebportal.mob.ccs.dto.ui;

import java.io.Serializable;

public class StockManualAssgnUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8365360540922674541L;
	
	private String orderId;
	private String itemCode;
	private String location;
	private String status;
	private String itemSerial;
	private String username;
	
	private String selItemSerial;
	private String source;
	
	private String stockPool;
	
	private String errMsg;
	
	
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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
	public String getItemSerial() {
		return itemSerial;
	}
	public void setItemSerial(String itemSerial) {
		this.itemSerial = itemSerial;
	}
	public void setSelItemSerial(String selItemSerial) {
		this.selItemSerial = selItemSerial;
	}
	public String getSelItemSerial() {
		return selItemSerial;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public String getStockPool() {
		return stockPool;
	}
	public void setStockPool(String stockPool) {
		this.stockPool = stockPool;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
