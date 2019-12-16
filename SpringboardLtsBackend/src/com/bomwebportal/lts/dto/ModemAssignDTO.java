package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class ModemAssignDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7365285972797040310L;
	private String newImsService;
	private String modemArrangment;
	private String technology;
	private String bandwidth;
	private String imsOrderType;
	private boolean isBbShortage;
	private boolean autoUpgraded;
	
	
	public ModemAssignDTO() {
		super();
	}
			
	public ModemAssignDTO(String newImsService, String modemArrangment,
			String technology, String bandwidth, String imsOrderType,
			boolean isBbShortage) {
		super();
		this.newImsService = newImsService;
		this.modemArrangment = modemArrangment;
		this.technology = technology;
		this.bandwidth = bandwidth;
		this.imsOrderType = imsOrderType;
		this.isBbShortage = isBbShortage;
	}
	
	public String getNewImsService() {
		return newImsService;
	}
	public void setNewImsService(String newImsService) {
		this.newImsService = newImsService;
	}
	public String getModemArrangment() {
		return modemArrangment;
	}
	public void setModemArrangment(String modemArrangment) {
		this.modemArrangment = modemArrangment;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getImsOrderType() {
		return imsOrderType;
	}
	public void setImsOrderType(String imsOrderType) {
		this.imsOrderType = imsOrderType;
	}
	public boolean isBbShortage() {
		return isBbShortage;
	}
	public void setBbShortage(boolean isBbShortage) {
		this.isBbShortage = isBbShortage;
	}
	public boolean isAutoUpgraded() {
		return autoUpgraded;
	}
	public void setAutoUpgraded(boolean autoUpgraded) {
		this.autoUpgraded = autoUpgraded;
	}
	
}
