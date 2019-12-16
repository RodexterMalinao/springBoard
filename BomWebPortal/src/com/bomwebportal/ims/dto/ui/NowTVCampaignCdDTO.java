package com.bomwebportal.ims.dto.ui;

public class NowTVCampaignCdDTO {
	
	private String basketId;
	private String itemId;
	private String contractPeriod;
	private String campaignCd;
	private String packCd;
	private String topUpCd;
	
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
	public String getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getCampaignCd() {
		return campaignCd;
	}
	public void setCampaignCd(String campaignCd) {
		this.campaignCd = campaignCd;
	}
	public String getPackCd() {
		return packCd;
	}
	public void setPackCd(String packCd) {
		this.packCd = packCd;
	}
	public String getTopUpCd() {
		return topUpCd;
	}
	public void setTopUpCd(String topUpCd) {
		this.topUpCd = topUpCd;
	}
	
	public NowTVCampaignCdDTO clone() {
		NowTVCampaignCdDTO camp = new NowTVCampaignCdDTO();
		
		camp.setBasketId(this.basketId);
		camp.setCampaignCd(this.campaignCd);
		camp.setContractPeriod(this.contractPeriod);
		camp.setItemId(this.itemId);
		camp.setPackCd(this.packCd);
		camp.setTopUpCd(this.topUpCd);
		
		return camp;
	}
}
