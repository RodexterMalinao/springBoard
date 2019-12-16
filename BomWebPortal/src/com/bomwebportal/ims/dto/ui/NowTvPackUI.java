package com.bomwebportal.ims.dto.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class NowTvPackUI {
	private String parentCampaignId;
	private String parentType;
	private String packId;
	private String channelGroupId;
	private String item_cd;
	private String campaign_title;
	private String title;
	private String detail;
	private String type;
	private String tvType;
	private String campaignCd_SOPHIE;
	private String packCd_SOPHIE;
	private String channelIconCode;
	private String mdo_ind;
	private boolean selected =false;
	private List<NowTvChannelUI> tvChannels = new ArrayList<NowTvChannelUI>();
	private int readRight=0;
	private String parentCampaignPrice;
	private String parentMinCnt;

	
	public String getParentType() {
		return parentType;
	}
	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
	private String tnc;
	
	public String getTnc() {
		return tnc;
	}
	public void setTnc(String tnc) {
		this.tnc = tnc;
	}
	
	public int getReadRight() {
		if("HD".equalsIgnoreCase(tvType)){
			readRight = 4;
		}
		else if("HDR".equalsIgnoreCase(tvType)){
			readRight = 3;
		}
		else if("SD".equalsIgnoreCase(tvType)){
			readRight = 0;
		}
		return readRight;
	}

	public void setReadRight(int readRight) {
		this.readRight = readRight;
	}

	public String getDisplayDtails(){
		String detail="";
			for(NowTvChannelUI channel:this.getTvChannels()){
				if("FTA".equalsIgnoreCase(parentType))
					detail+=" <b>.</b>"+channel.getChannelDisplayDetail();
				else if("HB".equalsIgnoreCase(parentType)){
					if(!"".equalsIgnoreCase(detail))
						detail+=", ";
					if("".equalsIgnoreCase(detail)){
						detail+="<b>"+title+":</b> ";
					}
					detail+=channel.getChannelDisplayDetail();
				}
			}
		return detail;
	}
	
	public String getDisplayDtailsForReport(Boolean cOrder,String newPricingDot, int connType){
		String detail="";
		
			for(NowTvChannelUI channel:this.getTvChannels()){
				if("FTA".equalsIgnoreCase(parentType))
					detail+=newPricingDot+channel.getChannelDisplayDetail();
				else if("HB".equalsIgnoreCase(parentType)){
					if(!"".equalsIgnoreCase(detail))
						detail+=", ";
					if("".equalsIgnoreCase(detail)){
						detail+="<b>"+title+":</b> ";
						if(cOrder){
							detail+=" ( "+campaignCd_SOPHIE+" | "+packCd_SOPHIE+" )<br/>";							
						}
					}
					detail+=channel.getChannelDisplayDetail();
				}				
				else if ("PB".equalsIgnoreCase(parentType)){
					if (connType < channel.getReadRight()) {continue;}
					if(!"".equalsIgnoreCase(detail))
						detail+="<br/>";
					if("".equalsIgnoreCase(detail)){
						detail+=title+"<b> ";
						if(cOrder){
							detail+=" ( "+campaignCd_SOPHIE+" | "+packCd_SOPHIE+" )";
						}
						detail+="</b><br/>";
					}
					if(!StringUtils.isEmpty(channel.getChannelId())&& !StringUtils.isEmpty(channel.getChannelDisplayDetail()))
						detail+=newPricingDot+"Ch."+channel.getChannelId()+" "+channel.getChannelDisplayDetail();	
					else if(!StringUtils.isEmpty(channel.getChannelDisplayDetail()))
						detail+=newPricingDot+channel.getChannelDisplayDetail();				
				}
			}
		return detail;
	}
	
	public String getCampaign_title() {
		return campaign_title;
	}

	public void setCampaign_title(String campaign_title) {
		this.campaign_title = campaign_title;
	}

	public String getChannelGroupId() {
		return channelGroupId;
	}

	public void setChannelGroupId(String channelGroupId) {
		this.channelGroupId = channelGroupId;
	}

	public String getItem_cd() {
		return item_cd;
	}
	public void setItem_cd(String item_cd) {
		this.item_cd = item_cd;
	}
	public String getParentCampaignId() {
		return parentCampaignId;
	}
	public void setParentCampaignId(String parentCampaignId) {
		this.parentCampaignId = parentCampaignId;
	}
	public String getPackId() {
		return packId;
	}
	public void setPackId(String packId) {
		this.packId = packId;
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
	public String getChannelIconCode() {
		return channelIconCode;
	}
	public void setChannelIconCode(String channelIconCode) {
		this.channelIconCode = channelIconCode;
	}
	public String getMdo_ind() {
		return mdo_ind;
	}
	public void setMdo_ind(String mdo_ind) {
		this.mdo_ind = mdo_ind;
	}
	public List<NowTvChannelUI> getTvChannels() {
		return tvChannels;
	}
	public void setTvChannels(List<NowTvChannelUI> tvChannels) {
		this.tvChannels = tvChannels;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public void setParentCampaignPrice(String parentCampaignPrice) {
		this.parentCampaignPrice = parentCampaignPrice;
	}
	public String getParentCampaignPrice() {
		return parentCampaignPrice;
	}
	public void setParentMinCnt(String parentMinCnt) {
		this.parentMinCnt = parentMinCnt;
	}
	public String getParentMinCnt() {
		return parentMinCnt;
	}

	
}
