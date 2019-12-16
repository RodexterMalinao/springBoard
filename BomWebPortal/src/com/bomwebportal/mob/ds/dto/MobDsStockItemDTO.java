package com.bomwebportal.mob.ds.dto;

public class MobDsStockItemDTO {
	private String itemType;
	private String itemCode;
	private String itemDesc;
	private String itemSerial;
	private String oldStatusId;
	private String statusId;
	private String statusDesc;
	private String aoInd;
	
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
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getItemSerial() {
		return itemSerial;
	}
	public void setItemSerial(String itemSerial) {
		this.itemSerial = itemSerial;
	}
	public String getOldStatusId() {
		return oldStatusId;
	}
	public void setOldStatusId(String oldStatusId) {
		this.oldStatusId = oldStatusId;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getAoInd() {
		return aoInd;
	}
	public void setAoInd(String aoInd) {
		this.aoInd = aoInd;
	}
}
