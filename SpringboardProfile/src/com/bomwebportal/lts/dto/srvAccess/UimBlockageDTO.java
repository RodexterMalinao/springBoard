package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class UimBlockageDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6799131486389152865L;
	
	
	private String blockageCd;
	private String blockageDesc;
	private String blockageDate; // yyyy/MM/dd
	private String flat;
	private String floor;
	private String serviceBoundary;
	
	public String getBlockageCd() {
		return blockageCd;
	}
	public void setBlockageCd(String blockageCd) {
		this.blockageCd = blockageCd;
	}
	public String getBlockageDesc() {
		return blockageDesc;
	}
	public void setBlockageDesc(String blockageDesc) {
		this.blockageDesc = blockageDesc;
	}
	public String getBlockageDate() {
		return blockageDate;
	}
	public void setBlockageDate(String blockageDate) {
		this.blockageDate = blockageDate;
	}
	public String getFlat() {
		return flat;
	}
	public void setFlat(String flat) {
		this.flat = flat;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getServiceBoundary() {
		return serviceBoundary;
	}
	public void setServiceBoundary(String serviceBoundary) {
		this.serviceBoundary = serviceBoundary;
	}
	public String getBlockageMessage() {
		return "Blockage Code " + blockageCd + ", " + blockageDesc + ", Blockage Date: " + blockageDate;
	}
	
}
