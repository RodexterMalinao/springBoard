package com.bomwebportal.ims.dto.ui;

import com.bomwebportal.ims.dto.SubscribedItemDTO;

public class SubscribedItemUI extends SubscribedItemDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5741015112789800195L;
	private String ActionInd;
	private String prodCd;
	private String ContractPeriod;
//	private String OfferCode;
	private String ImagePath;
	private String RecurrentAmt;
	private String MthToMthRate;
	private String PlanType;
	private int Bandwidth;
	
	String basketId;
	String basketHtml;
	String displayTab;

	public String getActionInd() {
		return ActionInd;
	}

	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}
	
	public String getContractPeriod() {
		return ContractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		ContractPeriod = contractPeriod;
	}
	
//	public String getOfferCode() {
//		return OfferCode;
//	}
//
//	public void setOfferCode(String offerCode) {
//		OfferCode = offerCode;
//	}
	
	public String getImagePath() {
		return ImagePath;
	}

	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}

	public String getRecurrentAmt() {
		return RecurrentAmt;
	}

	public void setRecurrentAmt(String recurrentAmt) {
		RecurrentAmt = recurrentAmt;
	}
	
	public String getMthToMthRate() {
		return MthToMthRate;
	}

	public void setMthToMthRate(String mthToMthRate) {
		MthToMthRate = mthToMthRate;
	}
	
	public String getPlanType() {
		return PlanType;
	}

	public void setPlanType(String planType) {
		PlanType = planType;
	}
	
	public int getBandwidth() {
		return Bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		Bandwidth = bandwidth;
	}
	
	public String getBasketHtml() {
		return basketHtml;
	}
	public void setBasketHtml(String basketHtml) {
		this.basketHtml = basketHtml;
	}
	public String getDisplayTab() {
		return displayTab;
	}
	public void setDisplayTab(String displayTab) {
		this.displayTab = displayTab;
	}
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String getProdCd() {
		return prodCd;
	}
	
}
