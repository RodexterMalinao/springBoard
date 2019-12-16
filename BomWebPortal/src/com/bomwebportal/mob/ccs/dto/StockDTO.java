package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.List;

public class StockDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 755286073841138699L;
	
	private String searchItemCode;
	
	private String type;
	private String model;
	private String itemCode;
	private List<String> selectedItemCodeList;
	private String status;
	private String imei;
	private String orderId;
	
	private String itemDesc;
	private String itemSerial;
	//add by Eliot 20120509
	private String doaItemCode;
	private String doaItemSerial;
	
	private int sk; // 1-model, 2-itemcode, 3-itemserial, 4-orderid
	
	private List<StockCatalogDTO> tempResult;
	private List<StockResultDTO> stockResult;
	
	private String selectedNum;
	private List<String> selectedNumList;
	
	private String actionType;
	
	private String stockPool;
	
	private String storeCode;
	private String teamCode;
	private String eventCode;
	private String staffId;
	
	private int channelId;
	private String category;
	private String seq;
	
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
	/**
	 * @return the itemDesc
	 */
	public String getItemDesc() {
		return itemDesc;
	}
	/**
	 * @param itemDesc the itemDesc to set
	 */
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	/**
	 * @return the itemSerial
	 */
	public String getItemSerial() {
		return itemSerial;
	}
	/**
	 * @param itemSerial the itemSerial to set
	 */
	public void setItemSerial(String itemSerial) {
		this.itemSerial = itemSerial;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public List<String> getSelectedItemCodeList() {
		return selectedItemCodeList;
	}
	public void setSelectedItemCodeList(List<String> selectedItemCodeList) {
		this.selectedItemCodeList = selectedItemCodeList;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public void setStockResult(List<StockResultDTO> stockResult) {
		this.stockResult = stockResult;
	}
	public List<StockResultDTO> getStockResult() {
		return stockResult;
	}
	public void setTempResult(List<StockCatalogDTO> tempResult) {
		this.tempResult = tempResult;
	}
	public List<StockCatalogDTO> getTempResult() {
		return tempResult;
	}
	public void setSelectedNum(String selectedNum) {
		this.selectedNum = selectedNum;
	}
	public String getSelectedNum() {
		return selectedNum;
	}
	public List<String> getSelectedNumList() {
		return selectedNumList;
	}
	public void setSelectedNumList(List<String> selectedNumList) {
		this.selectedNumList = selectedNumList;
	}
	public void setSearchItemCode(String searchItemCode) {
		this.searchItemCode = searchItemCode;
	}
	public String getSearchItemCode() {
		return searchItemCode;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setSk(int sk) {
		this.sk = sk;
	}
	public int getSk() {
		return sk;
	}
	public String getDoaItemCode() {
		return doaItemCode;
	}
	public void setDoaItemCode(String doaItemCode) {
		this.doaItemCode = doaItemCode;
	}
	public String getDoaItemSerial() {
		return doaItemSerial;
	}
	public void setDoaItemSerial(String doaItemSerial) {
		this.doaItemSerial = doaItemSerial;
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
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
}
