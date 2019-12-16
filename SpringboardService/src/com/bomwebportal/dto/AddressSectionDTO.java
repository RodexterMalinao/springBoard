package com.bomwebportal.dto;

import java.io.Serializable;

public class AddressSectionDTO implements Serializable {

	//SELECT CODE, SECT_DESC, DISTRNUM FROM W_ADDRLKUP_SECTION;	AddressSectionDTO

	private static final long serialVersionUID = -3580449543844639607L;
	private String sectionCode; // W_ADDRLKUP_SECTION.CODE
	private String sectionDescription; // W_ADDRLKUP_SECTION.SECT_DESC
	private String districtCode;// W_ADDRLKUP_SECTION.DISTRNUM ==? means districtCode ??
	
	public String getSectionCode() {
		return sectionCode;
	}
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	public String getSectionDescription() {
		return sectionDescription;
	}
	public void setSectionDescription(String sectionDescription) {
		this.sectionDescription = sectionDescription;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	
}