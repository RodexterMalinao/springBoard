package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class ItemAttributeDetailLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -520908013579992173L;

	private String srvItemSeq = null;
	private String attbCd = null;
	private String attbValue = null;
	private String offerId = null;
	private String offerSubId = null;
	private String productId = null;
	private String comptId = null;

	
	public String getSrvItemSeq() {
		return srvItemSeq;
	}

	public void setSrvItemSeq(String srvItemSeq) {
		this.srvItemSeq = srvItemSeq;
	}

	public String getAttbCd() {
		return attbCd;
	}

	public void setAttbCd(String attbCd) {
		this.attbCd = attbCd;
	}

	public String getAttbValue() {
		return attbValue;
	}

	public void setAttbValue(String attbValue) {
		this.attbValue = attbValue;
	}

	public String getOfferId() {
		return this.offerId;
	}

	public void setOfferId(String pOfferId) {
		this.offerId = pOfferId;
	}

	public String getOfferSubId() {
		return this.offerSubId;
	}

	public void setOfferSubId(String pOfferSubId) {
		this.offerSubId = pOfferSubId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String pProductId) {
		this.productId = pProductId;
	}

	public String getComptId() {
		return this.comptId;
	}

	public void setComptId(String pComptId) {
		this.comptId = pComptId;
	}
}
