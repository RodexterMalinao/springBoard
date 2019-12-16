package com.bomwebportal.ims.dto.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.ims.dto.VASDetailDTO;
import com.bomwebportal.util.ImsUtil;





public class NowTvNewPriceVasUI extends VASDetailDTO{
	private List<ChannelUI> channelList;
	private String key;
	
	private String freeMth;
	private String freeMthTxt;
	
	private String tvType;
	private String toHD;
	private String toSD;
	
	//for pricing 2.0
	private String qualityConstraint;
	private String campaignCd;
	private String packCd;
	private String packName;
	private String packDesc;
	private List<NowChannelUI> channelIconList;
	private List<NowTVCampaignCdDTO> campaignCdList;
	private String mdoInd;
	private List<NowTvNewPriceVasUI> relatedItemList = new ArrayList<NowTvNewPriceVasUI>();
	private String tnc;
	private String offerCd;
	
	public String getTnc() {
		return tnc;
	}

	public void setTnc(String tnc) {
		this.tnc = tnc;
	}

	public List<NowTvNewPriceVasUI> getRelatedItemList() {
		return relatedItemList;
	}

	public void setRelatedItemList(List<NowTvNewPriceVasUI> relatedItemList) {
		this.relatedItemList = relatedItemList;
	}

	public String getMdoInd() {
		return mdoInd;
	}

	public void setMdoInd(String mdoInd) {
		this.mdoInd = mdoInd;
	}

	public void setChannelList(List<ChannelUI> channelList) {
		this.channelList = channelList;
	}

	public List<ChannelUI> getChannelList() {
		return channelList;
	}

	public void setFreeMth(String freeMth) {
		this.freeMth = freeMth;
	}

	public String getFreeMth() {
		return freeMth;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setFreeMthTxt(String freeMthTxt) {
		this.freeMthTxt = freeMthTxt;
	}

	public String getFreeMthTxt() {
		return ImsUtil.GetHTMLString(freeMthTxt);
	}

	public void setTvType(String tvType) {
		this.tvType = tvType;
	}

	public String getTvType() {
		return tvType;
	}

	public void setToHD(String toHD) {
		this.toHD = toHD;
	}

	public String getToHD() {
		return toHD;
	}

	public void setToSD(String toSD) {
		this.toSD = toSD;
	}

	public String getToSD() {
		return toSD;
	}

	public void setCampaignCd(String campaignCd) {
		this.campaignCd = campaignCd;
	}

	public String getCampaignCd() {
		return campaignCd;
	}

	public void setQualityConstraint(String qualityConstraint) {
		this.qualityConstraint = qualityConstraint;
	}

	public String getQualityConstraint() {
		return qualityConstraint;
	}

	public void setPackCd(String packCd) {
		this.packCd = packCd;
	}

	public String getPackCd() {
		return packCd;
	}

	public void setPackName(String packName) {
		this.packName = packName;
	}

	public String getPackName() {
		return packName;
	}

	public void setPackDesc(String packDesc) {
		this.packDesc = packDesc;
	}

	public String getPackDesc() {
		return packDesc;
	}

	public void setChannelIconList(List<NowChannelUI> channelIconList) {
		this.channelIconList = channelIconList;
	}

	public List<NowChannelUI> getChannelIconList() {
		return channelIconList;
	}

	public void setCampaignCdList(List<NowTVCampaignCdDTO> campaignCdList) {
		this.campaignCdList = campaignCdList;
	}

	public List<NowTVCampaignCdDTO> getCampaignCdList() {
		return campaignCdList;
	}

	public List<NowTVCampaignCdDTO> getCampaignCdList(String contractPeriod) {
		List<NowTVCampaignCdDTO> r = new ArrayList<NowTVCampaignCdDTO>();
		if (campaignCdList == null) return r;
		else {
			for (NowTVCampaignCdDTO c:campaignCdList){
				if (StringUtils.equals(contractPeriod, c.getContractPeriod())) {
					r.add(c);
				}
			}
			return r;
		}
	}

	public void setOfferCd(String offerCd) {
		this.offerCd = offerCd;
	}

	public String getOfferCd() {
		return offerCd;
	}
}
