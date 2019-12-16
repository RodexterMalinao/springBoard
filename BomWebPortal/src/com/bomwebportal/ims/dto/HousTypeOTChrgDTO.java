package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;


public class HousTypeOTChrgDTO implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 629210834742422558L;
	
	/*
	 * IMS related
	 */
	
	private String OTChrg;
	private String OTChrgProdType;
	private String OTChrgType;
	private String installOtCode;//gary
	private String installOTDesc_en;//gary
	private String installOTDesc_zh;//gary
	
	/*
	 * DTO members
	 */
	
	public HousTypeOTChrgDTO(){
		
	}
		
	public String getOTChrg() {
		return OTChrg;
	}

	public void setOTChrg(String oTChrg) {
		OTChrg = oTChrg;
	}

	public String getOTChrgProdType() {
		return OTChrgProdType;
	}

	public void setOTChrgProdType(String oTChrgProdType) {
		OTChrgProdType = oTChrgProdType;
	}

	public void setOTChrgType(String oTChrgType) {
		OTChrgType = oTChrgType;
	}

	public String getOTChrgType() {
		return OTChrgType;
	}

	public String getInstallOtCode() {
		return installOtCode;
	}

	public void setInstallOtCode(String installOtCode) {
		this.installOtCode = installOtCode;
	}

	public String getInstallOTDesc_en() {
		return installOTDesc_en;
	}

	public void setInstallOTDesc_en(String installOTDesc_en) {
		this.installOTDesc_en = installOTDesc_en;
	}

	public String getInstallOTDesc_zh() {
		return installOTDesc_zh;
	}

	public void setInstallOTDesc_zh(String installOTDesc_zh) {
		this.installOTDesc_zh = installOTDesc_zh;
	}

		
}
