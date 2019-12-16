package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class IOOfferOTCSchemeDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7856013436186506396L;
	
	
	private String ProdId;
	private String IOInd;
	private String SchemeCode;
	private String SchemeDesc;
	public void setProdId(String prodId) {
		ProdId = prodId;
	}
	public String getProdId() {
		return ProdId;
	}
	public void setIOInd(String iOInd) {
		IOInd = iOInd;
	}
	public String getIOInd() {
		return IOInd;
	}
	public void setSchemeCode(String schemeCode) {
		SchemeCode = schemeCode;
	}
	public String getSchemeCode() {
		return SchemeCode;
	}
	public void setSchemeDesc(String schemeDesc) {
		SchemeDesc = schemeDesc;
	}
	public String getSchemeDesc() {
		return SchemeDesc;
	}
	
	


}
