package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class StockCatalogDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -539937391481433743L;
	
	private String scItemType;
	private String scItemCode;
	private String scItemDesc;
	private String scAssignMode;
	private String scLocation;
	private String mipSimType;
	
	private String rowId;
	
	public void setScItemType(String scItemType) {
		this.scItemType = scItemType;
	}
	public String getScItemType() {
		return scItemType;
	}
	
	public void setScItemCode(String scItemCode) {
		this.scItemCode = scItemCode;
	}
	public String getScItemCode() {
		return scItemCode;
	}
	
	public void setScItemDesc(String scItemDesc) {
		this.scItemDesc = scItemDesc;
	}
	public String getScItemDesc() {
		return scItemDesc;
	}
	
	public void setScAssignMode(String scAssignMode) {
		this.scAssignMode = scAssignMode;
	}
	public String getScAssignMode() {
		return scAssignMode;
	}
	
	public void setScLocation(String scLocation) {
		this.scLocation = scLocation;
	}
	public String getScLocation() {
		return scLocation;
	}
	public String getMipSimType() {
		return mipSimType;
	}
	public void setMipSimType(String mipSimType) {
		this.mipSimType = mipSimType;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getRowId() {
		return rowId;
	}

}
