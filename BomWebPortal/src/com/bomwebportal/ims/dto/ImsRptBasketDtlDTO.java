package com.bomwebportal.ims.dto;

import java.io.Serializable;


public class ImsRptBasketDtlDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 629210834742496125L;
	
	private String type;
	private String bandwidth;
	private String canSubcOptSrv;
	private String basketTitle;
	private String imagePath;
	private String offerCd;
	private String incentiveCd;
	private int contractPeriod;
	
	private String Bandwidth_desc;
	
	public ImsRptBasketDtlDTO(){
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getCanSubcOptSrv() {
		return canSubcOptSrv;
	}

	public void setCanSubcOptSrv(String canSubcOptSrv) {
		this.canSubcOptSrv = canSubcOptSrv;
	}

	public String getBasketTitle() {
		return basketTitle;
	}

	public void setBasketTitle(String basketTitle) {
		this.basketTitle = basketTitle;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getOfferCd() {
		return offerCd;
	}

	public void setOfferCd(String offerCd) {
		this.offerCd = offerCd;
	}

	@Override
	public String toString() {
		return "ImsRptBasketDtlDTO [type=" + type + ", bandwidth=" + bandwidth
				+ ", canSubcOptSrv=" + canSubcOptSrv + ", basketTitle="
				+ basketTitle + ", imagePath=" + imagePath + ", offerCd="
				+ offerCd + "]";
	}

	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public int getContractPeriod() {
		return contractPeriod;
	}

	public void setIncentiveCd(String incentiveCd) {
		this.incentiveCd = incentiveCd;
	}

	public String getIncentiveCd() {
		return incentiveCd;
	}

	public void setBandwidth_desc(String bandwidth_desc) {
		Bandwidth_desc = bandwidth_desc;
	}

	public String getBandwidth_desc() {
		return Bandwidth_desc;
	}

}
