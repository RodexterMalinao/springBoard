package com.bomwebportal.ims.dto.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bomwebportal.ims.dto.AttbDTO;

public class GiftUI {
	private String itemDetail;
	private String giftTitle;
	private String giftDetail;
	private String id;
	private String type;
	private String typeDesc;
	private String bandwidth;
	private String paymentMethod;
	private String serviceBoundary;
	private String checked;
	private String imagePath;
	private String AttbId;
	private String AttbDesc;
	private String InputMethod;
	private String InputValue;
	
	private String quota;//Tony added for pormo gift
	private String promoCode;//Tony
	
	private String requiredInstDate;//Tony
	
	private List<AttbDTO> giftAttbList;
	private Map<String,AttbDTO> giftAttbMap;
	
	private List<NowTVCampaignCdDTO> campaignCdList;

	private String giftDesc;
	private String giftValue;
	
	private String dediOfferInd;
	
	private String linkVas;
	private String showInd;
	private int normalGiftCnt;
	
	private String waiveISFInd;
	private String waiveASFInd;
	private String giftTitlePlainText;
	private String minVasPrice;
	
	private List<VASDetailUI> dediVASList;
	
	//steven added
	private String gift_start_date;
	private String gift_end_date;
	public String getGift_start_date() {
		return gift_start_date;
	}
	public void setGift_start_date(String gift_start_date) {
		this.gift_start_date = gift_start_date;
	}
	public String getGift_end_date() {
		return gift_end_date;
	}
	public void setGift_end_date(String gift_end_date) {
		this.gift_end_date = gift_end_date;
	}
	//steven added end
	public void setItemDetail(String itemDetail) {
		this.itemDetail = itemDetail;
	}
	public String getItemDetail() {
		return itemDetail;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getBandwidth() {
		return bandwidth;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setServiceBoundary(String serviceBoundary) {
		this.serviceBoundary = serviceBoundary;
	}
	public String getServiceBoundary() {
		return serviceBoundary;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getChecked() {
		return checked;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setAttbId(String attbId) {
		AttbId = attbId;
	}
	public String getAttbId() {
		return AttbId;
	}
	public void setAttbDesc(String attbDesc) {
		AttbDesc = attbDesc;
	}
	public String getAttbDesc() {
		return AttbDesc;
	}
	public void setInputMethod(String inputMethod) {
		InputMethod = inputMethod;
	}
	public String getInputMethod() {
		return InputMethod;
	}
	public void setInputValue(String inputValue) {
		InputValue = inputValue;
	}
	public String getInputValue() {
		return InputValue;
	}
	public void setGiftDesc(String giftDesc) {
		this.giftDesc = giftDesc;
	}
	public String getGiftDesc() {
		return giftDesc;
	}
	public void setGiftValue(String giftValue) {
		this.giftValue = giftValue;
	}
	public String getGiftValue() {
		return giftValue;
	}

	public Map<String, AttbDTO> getGiftAttbMap() {
		return giftAttbMap;
	}
	public void setGiftAttbMap(Map<String, AttbDTO> giftAttbMap) {
		this.giftAttbMap = giftAttbMap;
	}
	public List<AttbDTO> getGiftAttbList() {
		return giftAttbList;
	}
	public void setGiftAttbList(List<AttbDTO> giftAttbList) {
		this.giftAttbList = giftAttbList;
	}
	public void setGiftTitle(String giftTitle) {
		this.giftTitle = giftTitle;
	}
	public String getGiftTitle() {
		return giftTitle;
	}
	public void setGiftDetail(String giftDetail) {
		this.giftDetail = giftDetail;
	}
	public String getGiftDetail() {
		return giftDetail;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getTypeDesc() {
		return typeDesc;
	}
	
	public void setQuota(String quota) {
		this.quota = quota;
	}
	public String getQuota() {
		return quota;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public GiftUI clone() {
        
		GiftUI gift = new GiftUI();
		gift.setItemDetail(this.itemDetail);
		gift.setGiftTitle(this.giftTitle);
		gift.setGiftDetail(this.giftDetail);
		gift.setId(this.id);
		gift.setType(this.type);
		gift.setTypeDesc(this.typeDesc);
		gift.setBandwidth(this.bandwidth);
		gift.setPaymentMethod(this.paymentMethod);
		gift.setServiceBoundary(this.serviceBoundary);
		gift.setChecked(this.checked);
		gift.setImagePath(this.imagePath);
		gift.setAttbId(this.AttbId);
		gift.setAttbDesc(this.AttbDesc);
		gift.setInputMethod(this.InputMethod);
		gift.setInputValue(this.InputValue);
		gift.setQuota(this.quota);
		gift.setPromoCode(this.promoCode);
		gift.setRequiredInstDate(this.requiredInstDate);
		gift.setLinkVas(this.linkVas);
		gift.setShowInd(this.showInd);
		gift.setNormalGiftCnt(this.normalGiftCnt);
		List<AttbDTO> list = new ArrayList<AttbDTO>();
		if(this.giftAttbList!=null){
			for(AttbDTO attb:this.giftAttbList){
				list.add(attb.clone());
			}
		}
		List<NowTVCampaignCdDTO> campaignList = new ArrayList<NowTVCampaignCdDTO>();
		if(this.campaignCdList!=null){
			for(NowTVCampaignCdDTO campaign:this.campaignCdList){
				campaignList.add(campaign.clone());
			}
		}
		List<VASDetailUI> vasList = new ArrayList<VASDetailUI>();
		if(this.dediVASList!=null){
			for(VASDetailUI vas:this.dediVASList){
				vasList.add(vas);
			}
		}
		gift.setGiftAttbList(list);
		gift.setCampaignCdList(campaignList);
		gift.setDediVASList(vasList);
		//gift.setGiftAttbMap(this.giftAttbMap);
		gift.setGiftDesc(this.giftDesc);
		gift.setGiftValue(this.giftValue);
		gift.setGift_start_date(this.gift_start_date);
		gift.setGift_end_date(this.gift_end_date);
		gift.setDediOfferInd(this.dediOfferInd);
		gift.setWaiveISFInd(this.waiveISFInd);
		gift.setWaiveASFInd(this.waiveASFInd);
		gift.setGiftTitlePlainText(this.giftTitlePlainText);
		gift.setMinVasPrice(this.minVasPrice);
        return gift;
    }
	public void setRequiredInstDate(String requiredInstDate) {
		this.requiredInstDate = requiredInstDate;
	}
	public String getRequiredInstDate() {
		return requiredInstDate;
	}
	public void setDediOfferInd(String dediOfferInd) {
		this.dediOfferInd = dediOfferInd;
	}
	public String getDediOfferInd() {
		return dediOfferInd;
	}
	public void setLinkVas(String linkVas) {
		this.linkVas = linkVas;
	}
	public String getLinkVas() {
		return linkVas;
	}
	public void setShowInd(String showInd) {
		this.showInd = showInd;
	}
	public String getShowInd() {
		return showInd;
	}
	public void setNormalGiftCnt(int normalGiftCnt) {
		this.normalGiftCnt = normalGiftCnt;
	}
	public int getNormalGiftCnt() {
		return normalGiftCnt;
	}
	public void setWaiveISFInd(String waiveISFInd) {
		this.waiveISFInd = waiveISFInd;
	}
	public String getWaiveISFInd() {
		return waiveISFInd;
	}
	public void setWaiveASFInd(String waiveASFInd) {
		this.waiveASFInd = waiveASFInd;
	}
	public String getWaiveASFInd() {
		return waiveASFInd;
	}
	public void setCampaignCdList(List<NowTVCampaignCdDTO> campaignCdList) {
		this.campaignCdList = campaignCdList;
	}
	public List<NowTVCampaignCdDTO> getCampaignCdList() {
		return campaignCdList;
	}
	public void setMinVasPrice(String minVasPrice) {
		this.minVasPrice = minVasPrice;
	}
	public String getMinVasPrice() {
		return minVasPrice;
	}
	public void setGiftTitlePlainText(String giftTitlePlainText) {
		this.giftTitlePlainText = giftTitlePlainText;
	}
	public String getGiftTitlePlainText() {
		return giftTitlePlainText;
	}
	public void setDediVASList(List<VASDetailUI> dediVASList) {
		this.dediVASList = dediVASList;
	}
	public List<VASDetailUI> getDediVASList() {
		return dediVASList;
	}
}
