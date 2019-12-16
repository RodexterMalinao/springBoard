package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class SubscItemDiscDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9056929905279139171L;
	
	private String OrderId;
	private String Id;
	private String Type;
	private String BasketId;
	private String OfferId;
	private String OfferSubId;
	private String ProductId;
	private String DisId;
	private String DisCd;
	
	
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
	public String getDisId() {
		return DisId;
	}
	public void setDisId(String disId) {
		DisId = disId;
	}
	public String getDisCd() {
		return DisCd;
	}
	public void setDisCd(String disCd) {
		DisCd = disCd;
	}
	public String getBasketId() {
		return BasketId;
	}
	public void setBasketId(String basketId) {
		BasketId = basketId;
	}
	
	

}
