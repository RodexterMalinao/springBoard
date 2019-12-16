package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;

import com.bomwebportal.ims.dto.ui.NowTVAddUI;
import com.bomwebportal.ims.dto.ui.NowTvPackUI;


public class ImsRptNtvServiceDetailDTO implements Serializable{
	
	private static final long serialVersionUID = 629210834742496125L;
	
	private List<ImsRptBasketItemDTO> ntvFreeItemList;
	private List<ImsRptBasketItemDTO> ntvPayItemList;
	private List<ImsRptBasketItemDTO> ntvOtherItemList;
	private List<ImsRptChannelDTO> ntvFreeSPChannelList;
	private List<ImsRptChannelDTO> ntvFreeEPChannelList;
	private List<ImsRptChannelDTO> ntvFreeHDChannelList;
	private List<ImsRptChannelDTO> ntvPayChannelList;
	private List<ImsRptChannelDTO> ntvBackendChannelList;
	private NowTVAddUI newTVPricingItemList;
	
	//NOWTVSALES
	private List<ImsRptBasketItemDTO> ntvPgmItemList;
	private List<ImsRptBasketItemDTO> getGiftNowDollarOtherList;
	
	public ImsRptNtvServiceDetailDTO(){
		
	}

	public List<ImsRptBasketItemDTO> getNtvFreeItemList() {
		return ntvFreeItemList;
	}

	public void setNtvFreeItemList(List<ImsRptBasketItemDTO> ntvFreeItemList) {
		this.ntvFreeItemList = ntvFreeItemList;
	}

	public List<ImsRptBasketItemDTO> getNtvPayItemList() {
		return ntvPayItemList;
	}

	public void setNtvPayItemList(List<ImsRptBasketItemDTO> ntvPayItemList) {
		this.ntvPayItemList = ntvPayItemList;
	}

	public List<ImsRptBasketItemDTO> getNtvOtherItemList() {
		return ntvOtherItemList;
	}

	public void setNtvOtherItemList(List<ImsRptBasketItemDTO> ntvOtherItemList) {
		this.ntvOtherItemList = ntvOtherItemList;
	}

	public List<ImsRptChannelDTO> getNtvFreeSPChannelList() {
		return ntvFreeSPChannelList;
	}

	public void setNtvFreeSPChannelList(List<ImsRptChannelDTO> ntvFreeSPChannelList) {
		this.ntvFreeSPChannelList = ntvFreeSPChannelList;
	}

	public List<ImsRptChannelDTO> getNtvFreeEPChannelList() {
		return ntvFreeEPChannelList;
	}

	public void setNtvFreeEPChannelList(List<ImsRptChannelDTO> ntvFreeEPChannelList) {
		this.ntvFreeEPChannelList = ntvFreeEPChannelList;
	}

	public List<ImsRptChannelDTO> getNtvFreeHDChannelList() {
		return ntvFreeHDChannelList;
	}

	public void setNtvFreeHDChannelList(List<ImsRptChannelDTO> ntvFreeHDChannelList) {
		this.ntvFreeHDChannelList = ntvFreeHDChannelList;
	}

	public List<ImsRptChannelDTO> getNtvPayChannelList() {
		return ntvPayChannelList;
	}

	public void setNtvPayChannelList(List<ImsRptChannelDTO> ntvPayChannelList) {
		this.ntvPayChannelList = ntvPayChannelList;
	}

	@Override
	public String toString() {
		return "ImsNtvServiceDetailDTO [ntvFreeItemList=" + ntvFreeItemList
				+ ", ntvPayItemList=" + ntvPayItemList + ", ntvOtherItemList="
				+ ntvOtherItemList + ", ntvFreeSPChannelList="
				+ ntvFreeSPChannelList + ", ntvFreeEPChannelList="
				+ ntvFreeEPChannelList + ", ntvFreeHDChannelList="
				+ ntvFreeHDChannelList + ", ntvPayChannelList="
				+ ntvPayChannelList + "]";
	}

	public void setNtvBackendChannelList(List<ImsRptChannelDTO> ntvBackendChannelList) {
		this.ntvBackendChannelList = ntvBackendChannelList;
	}

	public List<ImsRptChannelDTO> getNtvBackendChannelList() {
		return ntvBackendChannelList;
	}
	
	public void setNewTVPricingItemList(NowTVAddUI newTVPricingItemList) {
		this.newTVPricingItemList = newTVPricingItemList;
	}

	public NowTVAddUI getNewTVPricingItemList() {
		return newTVPricingItemList;
	}

	public List<ImsRptBasketItemDTO> getNtvPgmItemList() {
		return ntvPgmItemList;
	}

	public void setNtvPgmItemList(List<ImsRptBasketItemDTO> ntvPgmItemList) {
		this.ntvPgmItemList = ntvPgmItemList;
	}

	public void setGetGiftNowDollarOtherList(
			List<ImsRptBasketItemDTO> getGiftNowDollarOtherList) {
		this.getGiftNowDollarOtherList = getGiftNowDollarOtherList;
	}

	public List<ImsRptBasketItemDTO> getGetGiftNowDollarOtherList() {
		return getGiftNowDollarOtherList;
	}

}
