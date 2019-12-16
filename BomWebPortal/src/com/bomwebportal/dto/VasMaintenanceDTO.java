package com.bomwebportal.dto;

import java.io.Serializable;

public class VasMaintenanceDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 502802904802848138L;
	
	//private String offerCdSearch;
	
	//Fields for Search Result
	
	private String offerCd;
	private String offerDesc;
	private String prodCd;
	private String prodDesc;
	private String posItemCd;
	private String offerType;
	private String offerId;
	private String offerSubId;
	private String prodType;
	private String prodId;
	private String mth2MthRate;
	private String chrgAmt;
	
	private String brand;
	private String simType;
	
	private String offerBrand;
	private String offerSimType;
	
	public String getMth2MthRate() {
		return mth2MthRate;
	}
	public void setMth2MthRate(String mth2MthRate) {
		this.mth2MthRate = mth2MthRate;
	}
	public String getOfferCd() {
		return offerCd;
	}
	public void setOfferCd(String offerCd) {
		this.offerCd = offerCd;
	}
	public String getOfferDesc() {
		return offerDesc;
	}
	public void setOfferDesc(String offerDesc) {
		this.offerDesc = offerDesc;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getProdDesc() {
		return prodDesc;
	}
	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}
	public String getPosItemCd() {
		return posItemCd;
	}
	public void setPosItemCd(String posItemCd) {
		this.posItemCd = posItemCd;
	}
	public String getOfferType() {
		return offerType;
	}
	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getOfferSubId() {
		return offerSubId;
	}
	public void setOfferSubId(String offerSubId) {
		this.offerSubId = offerSubId;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getChrgAmt() {
		return chrgAmt;
	}
	public void setChrgAmt(String chrgAmt) {
		this.chrgAmt = chrgAmt;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSimType() {
		return simType;
	}
	public void setSimType(String simType) {
		this.simType = simType;
	}
	public String getOfferBrand() {
		return offerBrand;
	}
	public void setOfferBrand(String offerBrand) {
		this.offerBrand = offerBrand;
	}
	public String getOfferSimType() {
		return offerSimType;
	}
	public void setOfferSimType(String offerSimType) {
		this.offerSimType = offerSimType;
	}
	
	
	
	
	
	

}
