package com.bomwebportal.lts.dto.order;

import java.io.Serializable;

public class OfferTypeDTO implements Serializable {

	private static final long serialVersionUID = 7107386812030409466L;
	
	private String psefCd = null;
	private String offerId = null;
	private String offerType = null;
	private String offerCd = null;
	

	public String getPsefCd() {
		return psefCd;
	}

	public void setPsefCd(String psefCd) {
		this.psefCd = psefCd;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public String getOfferCd() {
		return offerCd;
	}

	public void setOfferCd(String offerCd) {
		this.offerCd = offerCd;
	}
}
