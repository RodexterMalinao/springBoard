package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class AddressInventoryDetailLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -9093264567246909671L;
	
	private String addrUsage = null;
	private String resourceShortageInd = null;
	private String technology = null;
	private String buildingType = null;
	private String n2BuildingInd = null;
	private String fieldWorkPermitDay = null;
	private String addrId = null;
	private String maxBandwidth = null;
	private String fttcInd = null;
	private String hktPremier = null;

	
	public String getAddrUsage() {
		return addrUsage;
	}

	public void setAddrUsage(String addrUsage) {
		this.addrUsage = addrUsage;
	}

	public String getResourceShortageInd() {
		return resourceShortageInd;
	}

	public void setResourceShortageInd(String resourceShortageInd) {
		this.resourceShortageInd = resourceShortageInd;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getBuildingType() {
		return buildingType;
	}

	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}

	public String getN2BuildingInd() {
		return n2BuildingInd;
	}

	public void setN2BuildingInd(String n2BuildingInd) {
		this.n2BuildingInd = n2BuildingInd;
	}
	
	public String getAddrId() {
		return addrId;
	}

	public void setAddrId(String addrId) {
		this.addrId = addrId;
	}

	public String getFttcInd() {
		return fttcInd;
	}

	public void setFttcInd(String fttcInd) {
		this.fttcInd = fttcInd;
	}

	public String getFieldWorkPermitDay() {
		return fieldWorkPermitDay;
	}

	public void setFieldWorkPermitDay(String fieldWorkPermitDay) {
		this.fieldWorkPermitDay = fieldWorkPermitDay;
	}

	public String getMaxBandwidth() {
		return maxBandwidth;
	}

	public void setMaxBandwidth(String maxBandwidth) {
		this.maxBandwidth = maxBandwidth;
	}

	public String getHktPremier() {
		return hktPremier;
	}

	public void setHktPremier(String hktPremier) {
		this.hktPremier = hktPremier;
	}
	
}
