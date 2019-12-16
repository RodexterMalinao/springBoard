package com.bomwebportal.ims.dto;

import java.io.Serializable;


public class ImsRptBasketItemDTO implements Serializable{
	
	private static final long serialVersionUID = 629210834742496125L;
	
	private String type;
	private String mdoInd;
	private String itemTitle;
	private String itemDetails;
	private String itemMthFix;
	private String itemMth2Mth;
	private String itemMthFixAmt;
	private String itemMth2MthAmt;
	private String itemTvType;
	private String offerCode;
	private String incentiveCd;
	private int contractPeriod;
	private String mobileNum;
	private String itemID;
	private String preInstOfferCd;
	private String preUseOfferCd;
	
	// martin, 20160302
	private String campCd;
	private String topUpCode;
	
	// martin, BOM2016063, 20160608
	private String l2JobCode;
	// martin, BOM2017089, 20170808
	private String itemFreeMth;
	
	private String vasDummyGiftDesc;
	
	public ImsRptBasketItemDTO(){
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMdoInd() {
		return mdoInd;
	}

	public void setMdoInd(String mdoInd) {
		this.mdoInd = mdoInd;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(String itemDetails) {
		this.itemDetails = itemDetails;
	}

	public String getItemMthFix() {
		return itemMthFix;
	}

	public void setItemMthFix(String itemMthFix) {
		this.itemMthFix = itemMthFix;
	}

	public String getItemMth2Mth() {
		return itemMth2Mth;
	}

	public void setItemMth2Mth(String itemMth2Mth) {
		this.itemMth2Mth = itemMth2Mth;
	}

	public String getItemMthFixAmt() {
		return itemMthFixAmt;
	}

	public void setItemMthFixAmt(String itemMthFixAmt) {
		this.itemMthFixAmt = itemMthFixAmt;
	}

	public String getItemMth2MthAmt() {
		return itemMth2MthAmt;
	}

	public void setItemMth2MthAmt(String itemMth2MthAmt) {
		this.itemMth2MthAmt = itemMth2MthAmt;
	}

	public String getItemTvType() {
		return itemTvType;
	}

	public void setItemTvType(String itemTvType) {
		this.itemTvType = itemTvType;
	}

	public String getItemDetailName() {
		return getFirstLine(this.itemDetails);
	}
	
	public String getItemDetailInfo() {
		return getMinusFirstLine(this.itemDetails);
	}
	
	@Override
	public String toString() {
		return "ImsRptBasketItemDTO [type=" + type + ", mdoInd=" + mdoInd
				+ ", itemTitle=" + itemTitle + ", itemDetails=" + itemDetails
				+ ", itemMthFix=" + itemMthFix + ", itemMth2Mth=" + itemMth2Mth
				+ ", itemMthFixAmt=" + itemMthFixAmt + ", itemMth2MthAmt="
				+ itemMth2MthAmt + ", itemTvType=" + itemTvType + "]";
	}

	private String getFirstLine(String input) {
		String output = "";
		
		if (input != null && input.length() > 0) {
			
			
			if (input.indexOf(((char)10)+"") > 0) {
				output = input.substring(0, input.indexOf(((char)10)+""));
				
			}
			else if (input.indexOf("<br/>") > 0)
			{
				output = input.substring(0, input.indexOf("<br/>"));
			}
			else 
			{
				output = input;
			}
			
			
		}
		
		return output;
		
	}
	
	private String getMinusFirstLine(String input) {
		String output = "";
		
		if (input != null && input.length() > 0) {
			
			if (input.indexOf(((char)10)+"") > 0) {
				output = input.substring(input.indexOf(((char)10)+""));
				
			}
			else if (input.indexOf("<br/>") > 0)
			{
				output = input.substring(input.indexOf("<br/>") + 5);
			}
		}
		
		return output;
		
	}

	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}

	public String getOfferCode() {
		return offerCode;
	}

	public void setIncentiveCd(String incentiveCd) {
		this.incentiveCd = incentiveCd;
	}

	public String getIncentiveCd() {
		return incentiveCd;
	}

	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public int getContractPeriod() {
		return contractPeriod;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getItemID() {
		return itemID;
	}

	public String getTopUpCode() {
		return topUpCode;
	}

	public void setTopUpCode(String topUpCode) {
		this.topUpCode = topUpCode;
	}

	public void setCampCd(String campCd) {
		this.campCd = campCd;
	}

	public String getCampCd() {
		return campCd;
	}

	public String getL2JobCode() {
		return l2JobCode;
	}

	public void setL2JobCode(String l2JobCode) {
		this.l2JobCode = l2JobCode;
	}

	public void setPreInstOfferCd(String preInstOfferCd) {
		this.preInstOfferCd = preInstOfferCd;
	}

	public String getPreInstOfferCd() {
		return preInstOfferCd;
	}

	public void setPreUseOfferCd(String preUseOfferCd) {
		this.preUseOfferCd = preUseOfferCd;
	}

	public String getPreUseOfferCd() {
		return preUseOfferCd;
	}

	public String getItemFreeMth() {
		return itemFreeMth;
	}

	public void setItemFreeMth(String itemFreeMth) {
		this.itemFreeMth = itemFreeMth;
	}

	public void setVasDummyGiftDesc(String vasDummyGiftDesc) {
		this.vasDummyGiftDesc = vasDummyGiftDesc;
	}

	public String getVasDummyGiftDesc() {
		return vasDummyGiftDesc;
	}
	
}
