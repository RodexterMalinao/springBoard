package com.bomwebportal.ims.dto.ui;

import java.util.HashMap;
import java.util.List;

import com.bomwebportal.ims.dto.BasketDetailsDTO;

public class BasketDetailUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5248514987697312232L;

	private List<BasketDetailsDTO> BasketDetailList;
	private List<VASDetailUI> BVasDetailList;  
	private List<VASDetailUI> VasDetailList;  
	private List<VASDetailUI> ExclusiveItemList;
	private List<VASDetailUI> NowExclusiveItemList;
	private List<GiftUI> GiftList;//Tony 20140902
	
	private String promoGiftCode;
	private List<GiftUI> promoGiftList;//Tony 20140902

	private HashMap<String, HashMap<String, Object>> giftSelectCnt; 
	private String[] subscribedGiftsError;
	
	
	private String exclusiveError;
	private String VASError;
	
	private String cslNumPinMissing;
	
	public String basketSpecialOTCDesc;

	

	public String getBasketSpecialOTCDesc() {
		return basketSpecialOTCDesc;
	}

	public void setBasketSpecialOTCDesc(String basketSpecialOTCDesc) {
		this.basketSpecialOTCDesc = basketSpecialOTCDesc;
	}
	

	public String getCslNumPinMissing() {
		return cslNumPinMissing;
	}

	public void setCslNumPinMissing(String cslNumPinMissing) {
		this.cslNumPinMissing = cslNumPinMissing;
	}

	public List<BasketDetailsDTO> getBasketDetailList() {
		return BasketDetailList;
	}

	public void setBasketDetailList(List<BasketDetailsDTO> BasketDetailList) {
		this.BasketDetailList = BasketDetailList;
	}
	
	public List<VASDetailUI> getBVasDetailList() {
		return BVasDetailList;
	}

	public void setBVasDetailList(List<VASDetailUI> BVasDetailList) {
		this.BVasDetailList = BVasDetailList;
	}
	
	public List<VASDetailUI> getVasDetailList() {
		return VasDetailList;
	}

	public void setVasDetailList(List<VASDetailUI> VasDetailList) {
		this.VasDetailList = VasDetailList;
	}

	public List<VASDetailUI> getExclusiveItemList() {
		return ExclusiveItemList;
	}

	public void setExclusiveItemList(List<VASDetailUI> exclusiveItemList) {
		ExclusiveItemList = exclusiveItemList;
	}

	public String getExclusiveError() {
		return exclusiveError;
	}

	public void setExclusiveError(String exclusiveError) {
		this.exclusiveError = exclusiveError;
	}

	public String getVASError() {
		return VASError;
	}

	public void setVASError(String vASError) {
		VASError = vASError;
	}

	public void setGiftList(List<GiftUI> giftList) {
		GiftList = giftList;
	}

	public List<GiftUI> getGiftList() {
		return GiftList;
	}

	public void setPromoGiftCode(String promoGiftCode) {
		this.promoGiftCode = promoGiftCode;
	}

	public String getPromoGiftCode() {
		return promoGiftCode;
	}

	public void setPromoGiftList(List<GiftUI> promoGiftList) {
		this.promoGiftList = promoGiftList;
	}

	public List<GiftUI> getPromoGiftList() {
		return promoGiftList;
	}

	public void setGiftSelectCnt(HashMap<String, HashMap<String, Object>> giftSelectCnt) {
		this.giftSelectCnt = giftSelectCnt;
	}

	public HashMap<String, HashMap<String, Object>> getGiftSelectCnt() {
		return giftSelectCnt;
	}

	public void setSubscribedGiftsError(String[] subscribedGiftsError) {
		this.subscribedGiftsError = subscribedGiftsError;
	}

	public String[] getSubscribedGiftsError() {
		return subscribedGiftsError;
	}

	public void setNowExclusiveItemList(List<VASDetailUI> nowExclusiveItemList) {
		NowExclusiveItemList = nowExclusiveItemList;
	}

	public List<VASDetailUI> getNowExclusiveItemList() {
		return NowExclusiveItemList;
	}
	
}
