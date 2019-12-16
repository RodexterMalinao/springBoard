package com.bomwebportal.dto;

import java.io.Serializable;

public class AddressDistrictDTO implements Serializable {


	private static final long serialVersionUID = 8558205082190961391L;
	private String districtCode; // W_ADDRLKUP_DISTRICT.CODE
	private String districtDescription; // W_ADDRLKUP_DISTRICT.DISTDSC
	private String areaCode;// W_ADDRLKUP_DISTRICT.AREACD
	
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getDistrictDescription() {
		return districtDescription;
	}
	public void setDistrictDescription(String districtDescription) {
		this.districtDescription = districtDescription;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	
}