package com.bomwebportal.dto;

public class OrdDocAssgnAdminDTO extends OrdDocAssgnDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum DmsInd {
		Y 
		, N 
		;
	}
	
	private String shopCd;
	private String lob;
	private String collectMethod;
	private String disMode;
	private DmsInd dmsInd;
	
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getCollectMethod() {
		return collectMethod;
	}
	public void setCollectMethod(String collectMethod) {
		this.collectMethod = collectMethod;
	}
	
	public String getDisMode() {
		return disMode;
	}
	public void setDisMode(String disMode) {
		this.disMode = disMode;
	}
	public DmsInd getDmsInd() {
		return dmsInd;
	}
	public void setDmsInd(DmsInd dmsInd) {
		this.dmsInd = dmsInd;
	}
	public String toString() {
		return "{shopCd:"+shopCd+",orderId:"+getOrderId()+",docType:"+getDocType()+",collectedInd="+this.getCollectedInd()+"}";
	}
	
	
}
