package com.bomwebportal.ims.dto.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.ims.dto.BasketDetailsDTO;





public class BasketUI extends BasketDetailsDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String key;
	private NowChannelGroupUI channelGroup;
	private GenreUI genre;
	
	private List<NowTvNewPriceVasUI> BVasDetailList;
	private List<GiftUI> giftList;
	private List<NowTvNewPriceVasUI> hdConnectionList;
	
	//for pricing 2.0
	private List<NowTvNewPriceVasUI> itemList;
	private String qualityConstraint;
	private String campaignType;
	private String campaignName;
	private String campaignDesc;
	private String minSelectCnt;
	private String maxSelectCnt;
	
	private Map<String, NowTvNewPriceVasUI> pMap;
	
	private String tnc;
	
	private String isFoxPlus = "N";
	
	public String getMinSelectCnt() {
		return minSelectCnt;
	}
	public void setMinSelectCnt(String minSelectCnt) {
		this.minSelectCnt = minSelectCnt;
	}
	public String getMaxSelectCnt() {
		return maxSelectCnt;
	}
	public void setMaxSelectCnt(String maxSelectCnt) {
		this.maxSelectCnt = maxSelectCnt;
	}
	public void setBVasDetailList(List<NowTvNewPriceVasUI> bVasDetailList) {
		BVasDetailList = bVasDetailList;
	}
	public List<NowTvNewPriceVasUI> getBVasDetailList() {
		return BVasDetailList;
	}
	public void setChannelGroup(NowChannelGroupUI channelGroup) {
		this.channelGroup = channelGroup;
	}
	public NowChannelGroupUI getChannelGroup() {
		return channelGroup;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}

	public void setGenre(GenreUI genre) {
		this.genre = genre;
	}
	public GenreUI getGenre() {
		return genre;
	}
	
	public NowTvNewPriceVasUI getBvas(String contractPeriod){
		for(NowTvNewPriceVasUI bvas:BVasDetailList){
			if(contractPeriod.equals(bvas.getVASContractPeriod())) return bvas;
		}
		return null;
	}
	public void setGiftList(List<GiftUI> giftList) {
		this.giftList = giftList;
	}
	public List<GiftUI> getGiftList() {
		return giftList;
	}
	public void setHdConnectionList(List<NowTvNewPriceVasUI> hdConnectionList) {
		this.hdConnectionList = hdConnectionList;
	}
	public List<NowTvNewPriceVasUI> getHdConnectionList() {
		return hdConnectionList;
	}
	public void setItemList(List<NowTvNewPriceVasUI> itemList) {
		this.itemList = itemList;
	}
	public List<NowTvNewPriceVasUI> getItemList() {
		return itemList;
	}
	public void setQualityConstraint(String qualityConstraint) {
		this.qualityConstraint = qualityConstraint;
	}
	public String getQualityConstraint() {
		return qualityConstraint;
	}
	public String getCampaignType() {
		return campaignType;
	}
	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}
	public String getCampaignName() {
		return campaignName;
	}
	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	public String getCampaignDesc() {
		return campaignDesc;
	}
	public void setCampaignDesc(String campaignDesc) {
		this.campaignDesc = campaignDesc;
	}
	public void setpMap(Map<String, NowTvNewPriceVasUI> pMap) {
		this.pMap = pMap;
	}
	public String getTnc() {
		return tnc;
	}
	public void setTnc(String tnc) {
		this.tnc = tnc;
	}
	
	public Map<String, NowTvNewPriceVasUI> getpMap() {
		if(pMap == null){
			pMap = new HashMap<String, NowTvNewPriceVasUI>();
			if (itemList != null) {
				for(NowTvNewPriceVasUI p : itemList){
					pMap.put(p.getKey(), p);
					pMap.put(p.getItemId(), p);
				}
			}
		}
		
		return pMap;
	}
	
	public Boolean isContainPackCode(String packCd, String contract){
		
		if(itemList!=null) {
			for (NowTvNewPriceVasUI v:itemList){
				if (v.getCampaignCdList(contract)!=null){
					for(NowTVCampaignCdDTO c : v.getCampaignCdList(contract)){
						if(StringUtils.equals(packCd, c.getPackCd()) && !StringUtils.isEmpty(c.getPackCd())) return true; 
					}
				}
			}
		}
		
		return false;
	}
	
	public Boolean containPackOfType (String type){

		if(itemList!=null) {
			for (NowTvNewPriceVasUI v:itemList){
				if(StringUtils.equals(v.getItemType(), type)) return true;
			}
		} 
		
		return false;
	}
	
	public Boolean checkPayBundleContractPeriod (String contractPeriod){

		Boolean result = false;
		
		if(itemList!=null) {
			for (NowTvNewPriceVasUI v:itemList){
				if(StringUtils.equals(v.getItemType(), "NTV_COMM")){
					if("0".equalsIgnoreCase(v.getVASContractPeriod())||contractPeriod.equalsIgnoreCase(v.getVASContractPeriod())){
						result = true;
					}
				}
			}
		} 
		
		return result;
	}
	
	public void removePack(String packKey){
		
		if(itemList!=null) {
			List<NowTvNewPriceVasUI> l = new ArrayList<NowTvNewPriceVasUI>();
			for (NowTvNewPriceVasUI v:itemList){
				if(!StringUtils.equals(v.getKey(), packKey)) l.add(v);
			}
			
			itemList = l;
		}
		
	}
	
	public String getIsFoxPlus() {
		return isFoxPlus;
	}
	
	public void setIsFoxPlus(String isFoxPlus) {
		this.isFoxPlus = isFoxPlus;
	};
}
