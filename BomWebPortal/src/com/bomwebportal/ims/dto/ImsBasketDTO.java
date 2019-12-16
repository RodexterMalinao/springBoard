package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.BasketDTO;


public class ImsBasketDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 629210834742496125L;
	
	/*
	 * IMS related
	 */
	
	private String PlanType;
	private String BasketId;
	private String Title;
	private String Summary;
	private String RecurrentAmt;
	private String MthFixText;
	private String MthToMthRate;
	private String MthToMthText;
	private String ContractPeriod;
	private String ItemId;	
	private String OfferCode;
	private String ImagePath;
	private String Bandwidth;	
	private String ItemType;
	private String IsPreInst;
	private String Technology;
	private String IsPreUse;
	private String bandwidth_desc;
	private String HasGoogleRouter;

	

	/*
	 * DTO members
	 */
	
	public String getTechnology() {
		return Technology;
	}


	public void setTechnology(String technology) {
		Technology = technology;
	}


	public ImsBasketDTO(){
		
	}

	
	public String getBasketId() {
		return BasketId;
	}


	public void setBasketId(String basketId) {
		BasketId = basketId;
	}


	public String getTitle() {
		return Title;
	}


	public void setTitle(String title) {
		Title = title;
	}


	public String getSummary() {
		return Summary;
	}


	public void setSummary(String summary) {
		Summary = summary;
	}


	public String getMthFixText() {
		return MthFixText;
	}


	public void setMthFixText(String mthFixText) {
		MthFixText = mthFixText;
	}


	public String getMthToMthText() {
		return MthToMthText;
	}


	public void setMthToMthText(String mthToMthText) {
		MthToMthText = mthToMthText;
	}


	public String getItemId() {
		return ItemId;
	}


	public void setItemId(String itemId) {
		ItemId = itemId;
	}


	public String getItemType() {
		return ItemType;
	}


	public void setItemType(String itemType) {
		ItemType = itemType;
	}


	public String getIsPreInst() {
		return IsPreInst;
	}


	public void setIsPreInst(String isPreInst) {
		IsPreInst = isPreInst;
	}


	public String getPlanType() {
		return PlanType;
	}

	public void setPlanType(String planType) {
		PlanType = planType;
	}
	
	public String getContractPeriod() {
		return ContractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		ContractPeriod = contractPeriod;
	}
	
	public String getOfferCode() {
		return OfferCode;
	}

	public void setOfferCode(String offerCode) {
		OfferCode = offerCode;
	}
	
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
	
	public String getBandwidth() {
		return Bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		Bandwidth = bandwidth;
	}


	public void setIsPreUse(String isPreUse) {
		IsPreUse = isPreUse;
	}


	public String getIsPreUse() {
		return IsPreUse;
	}


	public void setBandwidth_desc(String bandwidth_desc) {
		this.bandwidth_desc = bandwidth_desc;
	}


	public String getBandwidth_desc() {
		return bandwidth_desc;
	}


	public void setHasGoogleRouter(String hasGoogleRouter) {
		HasGoogleRouter = hasGoogleRouter;
	}


	public String getHasGoogleRouter() {
		return HasGoogleRouter;
	}

	
	
}
