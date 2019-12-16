package com.bomwebportal.dto;

import java.io.Serializable;

public class AddressAreaDTO implements Serializable {


	private static final long serialVersionUID = 2576131415444674638L;
	
	private String areaCode;//W_ADDRLKUP_AREA.CODE
	private String areaDescription;//W_ADDRLKUP_AREA.DESCRIPTION
	
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaDescription() {
		return areaDescription;
	}
	public void setAreaDescription(String areaDescription) {
		this.areaDescription = areaDescription;
	}
	
	
}