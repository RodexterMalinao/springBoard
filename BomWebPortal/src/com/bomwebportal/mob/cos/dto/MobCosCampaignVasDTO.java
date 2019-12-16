package com.bomwebportal.mob.cos.dto;

public class MobCosCampaignVasDTO implements java.io.Serializable {
	private static final long serialVersionUID = -711274386226162214L;
	
	private String basketId;
	private String itemId;
	private String offerCd;
	private String offerDesc;
	private String prodCd;
	private String prodDesc;
	private String itemHtml;
	private boolean action;
	private String actionType;
	
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
	public String getItemHtml() {
		return itemHtml;
	}
	public void setItemHtml(String itemHtml) {
		this.itemHtml = itemHtml;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public boolean getAction() {
		return action;
	}
	public void setAction(boolean action) {
		this.action = action;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
}
