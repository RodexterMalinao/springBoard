package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class LtsHousingTypeDTO implements Serializable {

	private static final long serialVersionUID = 2162540730227531226L;

	private String ltsHousingTypeCd;
	private String ltsHousingTypeDesc;
	private String ltsHousingCatCd;
	private String ltsHousingCatDesc;
	
	public String getLtsHousingTypeCd() {
		return ltsHousingTypeCd;
	}
	public void setLtsHousingTypeCd(String ltsHousingTypeCd) {
		this.ltsHousingTypeCd = ltsHousingTypeCd;
	}
	public String getLtsHousingTypeDesc() {
		return ltsHousingTypeDesc;
	}
	public void setLtsHousingTypeDesc(String ltsHousingTypeDesc) {
		this.ltsHousingTypeDesc = ltsHousingTypeDesc;
	}
	public String getLtsHousingCatCd() {
		return ltsHousingCatCd;
	}
	public void setLtsHousingCatCd(String ltsHousingCatCd) {
		this.ltsHousingCatCd = ltsHousingCatCd;
	}
	public String getLtsHousingCatDesc() {
		return ltsHousingCatDesc;
	}
	public void setLtsHousingCatDesc(String ltsHousingCatDesc) {
		this.ltsHousingCatDesc = ltsHousingCatDesc;
	}	
	
}
