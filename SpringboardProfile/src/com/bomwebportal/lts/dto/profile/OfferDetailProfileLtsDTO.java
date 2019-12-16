package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class OfferDetailProfileLtsDTO implements Serializable {

	private static final long serialVersionUID = -5038001276253408883L;
	
	private String offerSubKey = null;
	private String offerId = null;
	private String offerSubCd = null;
	private String offerSubId = null;
	private String effStartDate = null;
	private String effEndDate = null;
	private String conditionEffStartDate = null;
	private String conditionEffEndDate = null;
	private String offerType = null;
	private double expiredMonths = 0;
	private String promotionStartDate = null;
	private String promotionEndDate = null;
	private String promotionExpiredMonths = null;
	private Set<String> psefSet = null;
	private List<OfferActionLtsDTO> offerActionList = null;
	
	
	public Set<String> getPsefSet() {
		return psefSet;
	}

	public void setPsefSet(Set<String> psefSet) {
		this.psefSet = psefSet;
	}

	public String getOfferSubKey() {
		return offerSubKey;
	}

	public void setOfferSubKey(String offerSubKey) {
		this.offerSubKey = offerSubKey;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getOfferSubCd() {
		return offerSubCd;
	}

	public void setOfferSubCd(String offerSubCd) {
		this.offerSubCd = offerSubCd;
	}

	public String getOfferSubId() {
		return offerSubId;
	}

	public void setOfferSubId(String offerSubId) {
		this.offerSubId = offerSubId;
	}

	public String getEffStartDate() {
		return effStartDate;
	}

	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}

	public String getEffEndDate() {
		return effEndDate;
	}

	public void setEffEndDate(String effEndDate) {
		this.effEndDate = effEndDate;
	}

	public String getConditionEffStartDate() {
		return conditionEffStartDate;
	}

	public void setConditionEffStartDate(String conditionEffStartDate) {
		this.conditionEffStartDate = conditionEffStartDate;
	}

	public String getConditionEffEndDate() {
		return conditionEffEndDate;
	}

	public void setConditionEffEndDate(String conditionEffEndDate) {
		this.conditionEffEndDate = conditionEffEndDate;
	}

	public String getOfferType() {
		return offerType;
	}

	public void setOfferType(String offerType) {
		this.offerType = offerType;
	}

	public double getExpiredMonths() {
		return this.expiredMonths;
	}

	public void setExpiredMonths(double pExpiredMonths) {
		this.expiredMonths = pExpiredMonths;
	}

	public String getPromotionStartDate() {
		return this.promotionStartDate;
	}

	public void setPromotionStartDate(String pPromotionStartDate) {
		this.promotionStartDate = pPromotionStartDate;
	}

	public String getPromotionEndDate() {
		return this.promotionEndDate;
	}

	public void setPromotionEndDate(String pPromotionEndDate) {
		this.promotionEndDate = pPromotionEndDate;
	}

	public String getPromotionExpiredMonths() {
		return this.promotionExpiredMonths;
	}

	public void setPromotionExpiredMonths(String pPromotionExpiredMonths) {
		this.promotionExpiredMonths = pPromotionExpiredMonths;
	}

	public String[] getPsefs() {
		
		if (this.psefSet == null || this.psefSet.size() == 0) {
			return null;
		}
		return this.psefSet.toArray(new String[this.psefSet.size()]);
	}

	public void setPsefs(String[] pPsefs) {
		this.psefSet = new HashSet<String>();
		this.psefSet.addAll(Arrays.asList(pPsefs));
	}
	
	public void addPsef(String pPsef) {
		
		if (this.psefSet == null) {
			this.psefSet = new HashSet<String>();
		}
		this.psefSet.add(pPsef);
	}
	
	public OfferActionLtsDTO[] getOfferActions() {
		
		if (this.offerActionList == null || this.offerActionList.size() == 0) {
			return null;
		}
		return this.offerActionList.toArray(new OfferActionLtsDTO[this.offerActionList.size()]);
	}
	
	public void setOfferActions(OfferActionLtsDTO[] pOfferActions) {
		this.offerActionList = new ArrayList<OfferActionLtsDTO>();
		this.offerActionList.addAll(Arrays.asList(pOfferActions));
	}
	
	public void addOfferAction(OfferActionLtsDTO pOfferAction) {
		
		if (this.offerActionList == null) {
			this.offerActionList = new ArrayList<OfferActionLtsDTO>();
		}
		this.offerActionList.add(pOfferAction);
	}
}
