package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class ItemDetailLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = 4754339621954351786L;

	private String srvItemSeq = null;
	private String ioInd = null;
	private String basketId = null;
	private String itemId = null;
	private String itemType = null;
	private String relatedItemId = null;
	private String relatedItemType = null;
	private String existStartDate = null;
	private String existEndDate = null;
	private String existGrossPrice = null;
	private String existEffectivePrice = null;
	private String membershipId = null;
	private String penaltyHandling = null;
	private String itemQty = null;
	private String paidVasInd = "Y";
	private ItemAttributeDetailLtsDTO[] itemAttbs = null;
	private String profileItemType;	
	private String penaltyWaiveInd;
	private String bundlePcdOrderId;
	private String pcdBundleFree;
	private String earnedClubPts;
	private String forceRetain;
	private String afType;
	private String osType;
	
	public String getSrvItemSeq() {
		return srvItemSeq;
	}

	public void setSrvItemSeq(String srvItemSeq) {
		this.srvItemSeq = srvItemSeq;
	}

	public String getIoInd() {
		return ioInd;
	}

	public void setIoInd(String ioInd) {
		this.ioInd = ioInd;
	}

	public String getBasketId() {
		return basketId;
	}

	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getRelatedItemId() {
		return relatedItemId;
	}

	public void setRelatedItemId(String relatedItemId) {
		this.relatedItemId = relatedItemId;
	}

	public String getRelatedItemType() {
		return relatedItemType;
	}

	public void setRelatedItemType(String relatedItemType) {
		this.relatedItemType = relatedItemType;
	}

	public String getExistStartDate() {
		return existStartDate;
	}

	public void setExistStartDate(String existStartDate) {
		this.existStartDate = existStartDate;
	}

	public String getExistEndDate() {
		return existEndDate;
	}

	public void setExistEndDate(String existEndDate) {
		this.existEndDate = existEndDate;
	}

	public String getExistGrossPrice() {
		return existGrossPrice;
	}

	public void setExistGrossPrice(String existGrossPrice) {
		this.existGrossPrice = existGrossPrice;
	}

	public String getExistEffectivePrice() {
		return existEffectivePrice;
	}

	public void setExistEffectivePrice(String existEffectivePrice) {
		this.existEffectivePrice = existEffectivePrice;
	}

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public ItemAttributeDetailLtsDTO[] getItemAttbs() {
		return itemAttbs;
	}

	public void setItemAttbs(ItemAttributeDetailLtsDTO[] itemAttbs) {
		this.itemAttbs = itemAttbs;
	}

	public String getItemQty() {
		return itemQty;
	}

	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}

	public String getPaidVasInd() {
		return this.paidVasInd;
	}

	public void setPaidVasInd(String pPaidVasInd) {
		this.paidVasInd = pPaidVasInd;
	}

	public String getPenaltyHandling() {
		return penaltyHandling;
	}

	public void setPenaltyHandling(String penaltyHandling) {
		this.penaltyHandling = penaltyHandling;
	}

	public String getProfileItemType() {
		return profileItemType;
	}

	public void setProfileItemType(String profileItemType) {
		this.profileItemType = profileItemType;
	}

	public String getPenaltyWaiveInd() {
		return penaltyWaiveInd;
	}

	public void setPenaltyWaiveInd(String penaltyWaiveInd) {
		this.penaltyWaiveInd = penaltyWaiveInd;
	}

	public String getBundlePcdOrderId() {
		return bundlePcdOrderId;
	}

	public void setBundlePcdOrderId(String bundlePcdOrderId) {
		this.bundlePcdOrderId = bundlePcdOrderId;
	}

	public String getPcdBundleFree() {
		return pcdBundleFree;
	}

	public void setPcdBundleFree(String pcdBundleFree) {
		this.pcdBundleFree = pcdBundleFree;
	}

	public String getEarnedClubPts() {
		return earnedClubPts;
	}

	public void setEarnedClubPts(String earnedClubPts) {
		this.earnedClubPts = earnedClubPts;
	}

	public String getForceRetain() {
		return forceRetain;
	}

	public void setForceRetain(String forceRetain) {
		this.forceRetain = forceRetain;
	}

	public String getAfType() {
		return afType;
	}

	public void setAfType(String afType) {
		this.afType = afType;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}
		
}
