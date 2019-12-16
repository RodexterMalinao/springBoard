package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.io.Serializable;

import com.pccw.rpt.util.ReportUtil;

public class AddressRptDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2457177142811072482L;

	private String unitNo;
	
	private String floorNo;
	
	private String blockNo;
	
	private String buildNo;
	
	private String sectDesc;
	
	private String distDesc;
	
	private String areaDesc;

	private String strNo = null;

	private String strName = null;

	private String strCatDesc = null;
	
	public String getUnitNo() {
		return ReportUtil.defaultString(unitNo);
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getFloorNo() {
		return ReportUtil.defaultString(floorNo);
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getBuildNo() {
		return ReportUtil.defaultString(buildNo);
	}

	public void setBuildNo(String buildNo) {
		this.buildNo = buildNo;
	}
	
	public String getSectDesc() {
		return ReportUtil.defaultString(sectDesc);
	}

	public void setSectDesc(String sectDesc) {
		this.sectDesc = sectDesc;
	}

	public String getDistDesc() {
		return ReportUtil.defaultString(distDesc);
	}

	public void setDistDesc(String distDesc) {
		this.distDesc = distDesc;
	}

	public String getAreaDesc() {
		return ReportUtil.defaultString(areaDesc);
	}

	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}

	public String getBlockNo() {
		return ReportUtil.defaultString(blockNo);
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

	public String getStrNo() {
		return ReportUtil.defaultString(this.strNo);
	}

	public void setStrNo(String pStrNo) {
		this.strNo = pStrNo;
	}

	public String getStrName() {
		return ReportUtil.defaultString(this.strName);
	}

	public void setStrName(String pStrName) {
		this.strName = pStrName;
	}

	public String getStrCatDesc() {
		return ReportUtil.defaultString(this.strCatDesc);
	}

	public void setStrCatDesc(String pStrCatDesc) {
		this.strCatDesc = pStrCatDesc;
	}

	public String getStrDesc() {
		return ReportUtil.defaultString(this.getStrNo() + " " + this.getStrName() + " " + this.getStrCatDesc());
	}
}