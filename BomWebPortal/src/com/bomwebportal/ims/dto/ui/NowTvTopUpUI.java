package com.bomwebportal.ims.dto.ui;

import java.util.ArrayList;
import java.util.List;

public class NowTvTopUpUI {
	private String parentCampaignId;
	private String itemId;
	private String itemcd;
	private String title;
	private String detail;
	private String type;
	private String tvType;
	private String campaignCd_SOPHIE;
	private String topUpCd;
	private String packCd_SOPHIE;
	private String mdo_ind;
	private boolean selected =false;
	private String tnc;
	
	public String getTnc() {
		return tnc;
	}
	public void setTnc(String tnc) {
		this.tnc = tnc;
	}
	
	
	public String getParentCampaignId() {
		return parentCampaignId;
	}
	public void setParentCampaignId(String parentCampaignId) {
		this.parentCampaignId = parentCampaignId;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTvType() {
		return tvType;
	}
	public void setTvType(String tvType) {
		this.tvType = tvType;
	}
	public String getCampaignCd_SOPHIE() {
		return campaignCd_SOPHIE;
	}
	public void setCampaignCd_SOPHIE(String campaignCd_SOPHIE) {
		this.campaignCd_SOPHIE = campaignCd_SOPHIE;
	}
	public String getPackCd_SOPHIE() {
		return packCd_SOPHIE;
	}
	public void setPackCd_SOPHIE(String packCd_SOPHIE) {
		this.packCd_SOPHIE = packCd_SOPHIE;
	}
	public String getMdo_ind() {
		return mdo_ind;
	}
	public void setMdo_ind(String mdo_ind) {
		this.mdo_ind = mdo_ind;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemcd() {
		return itemcd;
	}
	public void setItemcd(String itemcd) {
		this.itemcd = itemcd;
	}
	public String getTopUpCd() {
		return topUpCd;
	}
	public void setTopUpCd(String topUpCd) {
		this.topUpCd = topUpCd;
	}

	
}
