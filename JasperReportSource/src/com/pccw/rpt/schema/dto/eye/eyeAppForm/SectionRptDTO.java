package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.pccw.rpt.util.ReportUtil;

public class SectionRptDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1144577017856416896L;
	
	private static final String EYE_DEVICE_KEYWORD = "[EYE DEVICE]";
	
	private static final String EYE_PACKAGE_KEYWORD = "[EYE PACKAGE]";

	private String sectionTitle;
	
	private String sectionPreviewTitle;
	
	private String sectionName;

	private String eyeDevice;

	private String eyePackage;

	public String getSectionTitle() {
		return ReportUtil.defaultString(this.sectionTitle);
	}

	public void setSectionTitle(String pSectionTitle) {
		this.sectionTitle = pSectionTitle;
	}
	
	public String getSectionPreviewTitle() {
		return ReportUtil.defaultString(this.sectionPreviewTitle);
	}

	public void setSectionPreviewTitle(String pSectionPreviewTitle) {
		this.sectionPreviewTitle = pSectionPreviewTitle;
	}
	
	public String getSectionName() {
		return ReportUtil.defaultString(this.sectionName);
	}

	public void setSectionName(String pSectionName) {
		this.sectionName = pSectionName;
	}


	public String getEyeDevice() {
		return this.eyeDevice;
	}

	public void setEyeDevice(String pEyeDevice) {
		this.eyeDevice = pEyeDevice;
	}
	
	public String getEyePackage() {
		return this.eyePackage;
	}

	public void setEyePackage(String pEyePackage) {
		this.eyePackage = pEyePackage;
	}

	protected String replaceEyeDeviceKeyword(String pString) {
		return StringUtils.replace(StringUtils.replace(pString, EYE_DEVICE_KEYWORD, this.getEyeDevice()), EYE_PACKAGE_KEYWORD, this.getEyePackage());
	}
}
