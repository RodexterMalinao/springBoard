package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class StockInventoryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4745765495804962333L;

	private String siItemCode;
	private String siItemSerial;
	private String siStatusId;
	private String siLocation;
	private String siRemarks;
	
	public void setSiItemCode(String siItemCode) {
		this.siItemCode = siItemCode;
	}
	public String getSiItemCode() {
		return siItemCode;
	}
	
	public void setSiItemSerial(String siItemSerial) {
		this.siItemSerial = siItemSerial;
	}
	public String getSiItemSerial() {
		return siItemSerial;
	}
	
	public void setSiStatusId(String siStatusId) {
		this.siStatusId = siStatusId;
	}
	public String getSiStatusId() {
		return siStatusId;
	}
	
	public void setSiLocation(String siLocation) {
		this.siLocation = siLocation;
	}
	public String getSiLocation() {
		return siLocation;
	}
	
	public void setSiRemarks(String siRemarks) {
		this.siRemarks = siRemarks;
	}
	public String getSiRemarks() {
		return siRemarks;
	}
	
}
