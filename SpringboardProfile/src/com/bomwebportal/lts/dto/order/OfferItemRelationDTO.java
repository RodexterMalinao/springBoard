package com.bomwebportal.lts.dto.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OfferItemRelationDTO implements Serializable {

	private static final long serialVersionUID = -5087035008317476641L;
	
	private List<OfferTypeDTO> offers = new ArrayList<OfferTypeDTO>();
	private String termExistCdInd = null;
	private String existItemId = null;
	private String iddFreeMin = null;
	private String changeToOfferId = null;
	private String changeToCd = null;
	private String changeToItemId = null;
	private String allowCancel = null;
	private String itemType = null;
	private String tpPremium = null;
	private boolean freeVasInd = false;

	
	public void addOffer(OfferTypeDTO offer) {
		offers.add(offer);
	}
	
	public OfferTypeDTO[] getOffers() {
		return offers.toArray(new OfferTypeDTO[0]);
	}

	public void setOffers(OfferTypeDTO[] offers) {
		this.offers.addAll(Arrays.asList(offers));
	}

	public String getTermExistCdInd() {
		return termExistCdInd;
	}

	public void setTermExistCdInd(String termExistCdInd) {
		this.termExistCdInd = termExistCdInd;
	}

	public String getExistItemId() {
		return existItemId;
	}

	public void setExistItemId(String existItemId) {
		this.existItemId = existItemId;
	}

	public String getIddFreeMin() {
		return iddFreeMin;
	}

	public void setIddFreeMin(String iddFreeMin) {
		this.iddFreeMin = iddFreeMin;
	}

	public String getChangeToOfferId() {
		return changeToOfferId;
	}

	public void setChangeToOfferId(String changeToOfferId) {
		this.changeToOfferId = changeToOfferId;
	}

	public String getChangeToCd() {
		return changeToCd;
	}

	public void setChangeToCd(String changeToCd) {
		this.changeToCd = changeToCd;
	}

	public String getChangeToItemId() {
		return changeToItemId;
	}

	public void setChangeToItemId(String changeToItemId) {
		this.changeToItemId = changeToItemId;
	}

	public String getAllowCancel() {
		return allowCancel;
	}

	public void setAllowCancel(String allowCancel) {
		this.allowCancel = allowCancel;
	}

	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String pItemType) {
		this.itemType = pItemType;
	}

	public boolean isFreeVasInd() {
		return freeVasInd;
	}

	public void setFreeVasInd(boolean freeVasInd) {
		this.freeVasInd = freeVasInd;
	}

	public String getTpPremium() {
		return tpPremium;
	}

	public void setTpPremium(String tpPremium) {
		this.tpPremium = tpPremium;
	}
}
