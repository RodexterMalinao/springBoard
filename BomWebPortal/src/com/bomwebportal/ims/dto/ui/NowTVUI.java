package com.bomwebportal.ims.dto.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bomwebportal.ims.dto.ui.NowTVAddUI;
import com.bomwebportal.ims.dto.ui.NowTVVasUI;

public class NowTVUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5248514987697312232L;

	private List<NowTVVasUI> nowTVStarterList;
	private List<NowTVVasUI> nowTVStarterDescList;
	private List<NowTVVasUI> nowTVEntDescList;	
	private List<NowTVVasUI> nowTVVasList;
	private List<NowTVVasUI> nowTVOtherList;
	private List<ChannelUI> nowTVChannelList;
	private List<ChannelUI> nowTVHDList;
	private List<ChannelUI> ExclusiveList;
	private List<ImsDecodeTypeUI> decodeTypeList;
	private List<String> ptTvList;
	//nowTV pcd gift
	private List<GiftUI> pcdGiftList;
	private String giftExclusiveError;
	private String giftSelectError;
	private HashMap<String, HashMap<String, Object>> giftSelectCnt; 
	//nowTV pcd gift end
	
	private String nowTVStarterDesc;
	
	private String IsAdult;
	private String exclusiveError;
	private String channelError;


	private String DecodeType;
	private String NowTVFormType;
	private String IsCouponOffer;
	private String InFormType;
	private String Technology;
	private String FreeHD;
	
	private String OtherTVList;

	private String Serbdyno;
	
	private String HDAct;
	private String bandwidth;
	
	private String ntvPricingInd;
	private NowTVAddUI nowTVAddUI;
	
	private String isReceiveAdultReadyOnly;
	
	private String[] selectedVas;
	private String[] subscribedItems;
	private String contractPeriod;
	
	public String[] getSelectedVas() {
		return selectedVas;
	}

	public void setSelectedVas(String[] selectedVas) {
		this.selectedVas = selectedVas;
	}

	public String getIsReceiveAdultReadyOnly() {
		return isReceiveAdultReadyOnly;
	}

	public void setIsReceiveAdultReadyOnly(String isReceiveAdultReadyOnly) {
		this.isReceiveAdultReadyOnly = isReceiveAdultReadyOnly;
	}

	public NowTVUI(){
	}
	
	public List<String> getPtTvList() {
		return ptTvList;
	}

	public void setPtTvList(List<String> ptTvList) {
		this.ptTvList = ptTvList;
	}

	
	public List<NowTVVasUI> getNowTVStarterList() {
		return nowTVStarterList;
	}

	public void setNowTVStarterList(List<NowTVVasUI> nowTVStarterList) {
		this.nowTVStarterList = nowTVStarterList;
	}
	
	public List<NowTVVasUI> getNowTVStarterDescList() {
		return nowTVStarterDescList;
	}

	public void setNowTVStarterDescList(List<NowTVVasUI> nowTVStarterDescList) {
		this.nowTVStarterDescList = nowTVStarterDescList;
	}

	public List<NowTVVasUI> getNowTVEntDescList() {
		return nowTVEntDescList;
	}

	public void setNowTVEntDescList(List<NowTVVasUI> nowTVEntDescList) {
		this.nowTVEntDescList = nowTVEntDescList;
	}

	public List<NowTVVasUI> getNowTVVasList() {
		return nowTVVasList;
	}

	public void setNowTVasList(List<NowTVVasUI> nowTVVasList) {
		this.nowTVVasList = nowTVVasList;
	}
	
	public List<NowTVVasUI> getNowTVOtherList() {
		return nowTVOtherList;
	}

	public void setNowTVOtherList(List<NowTVVasUI> nowTVOtherList) {
		this.nowTVOtherList = nowTVOtherList;
	}

	public List<ChannelUI> getNowTVChannelList() {
		return nowTVChannelList;
	}

	public void setNowTVChannelList(List<ChannelUI> nowTVChannelList) {
		this.nowTVChannelList = nowTVChannelList;
	}
	
	public List<ChannelUI> getNowTVHDList() {
		return nowTVHDList;
	}

	public void setNowTVHDList(List<ChannelUI> nowTVHDList) {
		this.nowTVHDList = nowTVHDList;
	}
	
	public List<ChannelUI> getExclusiveList() {
		return ExclusiveList;
	}

	public void setExclusiveList(List<ChannelUI> exclusiveList) {
		ExclusiveList = exclusiveList;
	}

	public List<ImsDecodeTypeUI> getDecodeTypeList() {
		return decodeTypeList;
	}

	public void setDecodeTypeList(List<ImsDecodeTypeUI> decodeTypeList) {
		this.decodeTypeList = decodeTypeList;
	}

	public String getNowTVStarterDesc() {
		return nowTVStarterDesc;
	}

	public void setNowTVStarterDesc(String nowTVStarterDesc) {
		this.nowTVStarterDesc = nowTVStarterDesc;
	}
	
	
	
	public String getIsAdult() {
		return IsAdult;
	}

	public void setIsAdult(String isAdult) {
		IsAdult = isAdult;
	}

	public String getExclusiveError() {
		return exclusiveError;
	}

	public void setExclusiveError(String exclusiveError) {
		this.exclusiveError = exclusiveError;
	}

	public String getChannelError() {
		return channelError;
	}

	public void setChannelError(String channelError) {
		this.channelError = channelError;
	}

	public String getDecodeType() {
		return DecodeType;
	}

	public void setDecodeType(String DecodeType) {
		this.DecodeType = DecodeType;
	}
	
	public String getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getNowTVFormType() {
		return NowTVFormType;
	}

	public void setNowTVFormType(String nowTVFormType) {
		NowTVFormType = nowTVFormType;
	}

	public String getIsCouponOffer() {
		return IsCouponOffer;
	}

	public void setIsCouponOffer(String isCouponOffer) {
		IsCouponOffer = isCouponOffer;
	}
	
	public String getInFormType() {
		return InFormType;
	}

	public void setInFormType(String inFormType) {
		InFormType = inFormType;
	}

	public String getTechnology() {
		return Technology;
	}

	public void setTechnology(String technology) {
		Technology = technology;
	}

	public String getFreeHD() {
		return FreeHD;
	}

	public void setFreeHD(String freehd) {
		FreeHD = freehd;
	}

	public String getOtherTVList() {
		return OtherTVList;
	}

	public void setOtherTVList(String otherTVList) {
		OtherTVList = otherTVList;
	}

	public String getSerbdyno() {
		return Serbdyno;
	}

	public void setSerbdyno(String serbdyno) {
		Serbdyno = serbdyno;
	}
	
	public String getHDAct() {
		return HDAct;
	}

	public void setHDAct(String hDAct) {
		HDAct = hDAct;
	}

	public String getNtvPricingInd() {
		return ntvPricingInd;
	}

	public void setNtvPricingInd(String ntvPricingInd) {
		this.ntvPricingInd = ntvPricingInd;
	}

	public NowTVAddUI getNowTVAddUI() {
		return nowTVAddUI;
	}

	public void setNowTVAddUI(NowTVAddUI nowTVAddUI) {
		this.nowTVAddUI = nowTVAddUI;
	}
	
	public String[] getSubscribedItems() {
		return subscribedItems;
	}

	public void setSubscribedItems(String[] subscribedItems) {
		this.subscribedItems = subscribedItems;
	}

	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public String getContractPeriod() {
		return contractPeriod;
	}
	
	private Map<String, NowTVVasUI> vMap;
	
	public Map<String, NowTVVasUI> getvMap() {

		if(vMap == null){
			Map <String, NowTVVasUI> tmpMap = new HashMap<String, NowTVVasUI>();
			List<NowTVVasUI> tmp = new ArrayList<NowTVVasUI>();
			if(nowTVStarterList!=null) tmp.addAll(nowTVStarterList);
			if(nowTVVasList!=null) tmp.addAll(nowTVVasList);
			if(nowTVOtherList!=null) tmp.addAll(nowTVOtherList);
			if(tmp.size()>0){
				for(NowTVVasUI v:tmp){
					tmpMap.put(v.getItemID(), v);
				}
			}
			vMap = tmpMap;
		}
		
		return vMap;
	}

	public void setPcdGiftList(List<GiftUI> pcdGiftList) {
		this.pcdGiftList = pcdGiftList;
	}

	public List<GiftUI> getPcdGiftList() {
		return pcdGiftList;
	}

	public void setGiftExclusiveError(String giftExclusiveError) {
		this.giftExclusiveError = giftExclusiveError;
	}

	public String getGiftExclusiveError() {
		return giftExclusiveError;
	}

	public void setGiftSelectError(String giftSelectError) {
		this.giftSelectError = giftSelectError;
	}

	public String getGiftSelectError() {
		return giftSelectError;
	}

	public void setGiftSelectCnt(HashMap<String, HashMap<String, Object>> giftSelectCnt) {
		this.giftSelectCnt = giftSelectCnt;
	}

	public HashMap<String, HashMap<String, Object>> getGiftSelectCnt() {
		return giftSelectCnt;
	}
}
