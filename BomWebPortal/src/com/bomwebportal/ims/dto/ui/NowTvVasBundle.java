package com.bomwebportal.ims.dto.ui;

import java.util.ArrayList;
import java.util.List;

public class NowTvVasBundle {
	private String itemId;
	private String offerId;
	private String prodId;
	private String prodCd;
	private String type;
	private String tnc;
	private List<NowTVOfferUI> offerCd  = new ArrayList<NowTVOfferUI>();
	
	
	
	public List<NowTVOfferUI> getOfferCd() {
		return offerCd;
	}
	public void setOfferCd(List<NowTVOfferUI> offerCd) {
		this.offerCd = offerCd;
	}
	public String getTnc() {
		return tnc;
	}
	public void setTnc(String tnc) {
		this.tnc = tnc;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
