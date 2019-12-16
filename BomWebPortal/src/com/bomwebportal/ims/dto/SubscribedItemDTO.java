package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class SubscribedItemDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7856013436186506396L;
	
	
	private String OrderId;
	private String Id;
	private String Type;
	private String BasketId;
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;

	/*
	 * sync bom field
	 */
	
	private String OfferId;
	private String OfferSubId;
	private String ProductId;
	private String PlanId;
	private String ProgramId;
	private String IncentiveId;
	private String OfferCode;
	
	private String ImsServiceType;
	
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getBasketId() {
		return BasketId;
	}
	public void setBasketId(String basketId) {
		BasketId = basketId;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public Date getLastUpdDate() {
		return LastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		LastUpdDate = lastUpdDate;
	}
	public String getLastUpdBy() {
		return LastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		LastUpdBy = lastUpdBy;
	}
	public String getOfferId() {
		return OfferId;
	}
	public void setOfferId(String offerId) {
		OfferId = offerId;
	}
	public String getOfferSubId() {
		return OfferSubId;
	}
	public void setOfferSubId(String offerSubId) {
		OfferSubId = offerSubId;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}
	public String getPlanId() {
		return PlanId;
	}
	public void setPlanId(String planId) {
		PlanId = planId;
	}
	public String getProgramId() {
		return ProgramId;
	}
	public void setProgramId(String programId) {
		ProgramId = programId;
	}
	public String getIncentiveId() {
		return IncentiveId;
	}
	public void setIncentiveId(String incentiveId) {
		IncentiveId = incentiveId;
	}
	public void setImsServiceType(String imsServiceType) {
		ImsServiceType = imsServiceType;
	}
	public String getImsServiceType() {
		return ImsServiceType;
	}
	public void setOfferCode(String offerCode) {
		OfferCode = offerCode;
	}
	public String getOfferCode() {
		return OfferCode;
	}
	private String campaignCd;

	private String packCd;

	private String topUpCd;

	 

	public String getCampaignCd() {

	        return campaignCd;

	    }

	    public void setCampaignCd(String campaignCd) {

	        this.campaignCd = campaignCd;

	    }

	    public String getPackCd() {

	        return packCd;

	    }

	    public void setPackCd(String packCd) {

	        this.packCd = packCd;

	    }

	    public String getTopUpCd() {

	        return topUpCd;

	    }

	    public void setTopUpCd(String topUpCd) {

	        this.topUpCd = topUpCd;

	    }


}
