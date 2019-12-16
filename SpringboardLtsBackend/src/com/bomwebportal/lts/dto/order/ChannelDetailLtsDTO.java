package com.bomwebportal.lts.dto.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class ChannelDetailLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = 6456095240675653984L;

	private String channelId = null;
	private String campaignCd = null;
	private List<ChannelAttbLtsDTO> channelAttbList = null;

	
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getCampaignCd() {
		return campaignCd;
	}

	public void setCampaignCd(String campaignCd) {
		this.campaignCd = campaignCd;
	}

	public ChannelAttbLtsDTO[] getChannelAttbs() {
		
		if (this.channelAttbList == null || this.channelAttbList.size() == 0) {
			return null;
		}
		return this.channelAttbList.toArray(new ChannelAttbLtsDTO[this.channelAttbList.size()]);
	}
	
	public void setChannelAttb(ChannelAttbLtsDTO[] pChannelAttbs) {

		if (ArrayUtils.isEmpty(pChannelAttbs)) {
			return;
		}
		if (this.channelAttbList == null) {
			this.channelAttbList = new ArrayList<ChannelAttbLtsDTO>();
		}
		this.channelAttbList.addAll(Arrays.asList(pChannelAttbs));
	}
	
	public void addChannelAttb(ChannelAttbLtsDTO pChannelAttb) {
		
		if (pChannelAttb == null) {
			return;
		}
		if (this.channelAttbList == null) {
			this.channelAttbList = new ArrayList<ChannelAttbLtsDTO>();
		}
		this.channelAttbList.add(pChannelAttb);
	}
}
