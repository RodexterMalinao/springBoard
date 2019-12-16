package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;


public class ImsRptOptServiceDetailDTO implements Serializable{
	
	private static final long serialVersionUID = 629210834742496125L;
	
	private List<ImsRptBasketItemDTO> optPremItemList;
	private List<ImsRptBasketItemDTO> wlBbItemList;
	private List<ImsRptBasketItemDTO> antiVirItemList;
	private List<ImsRptBasketItemDTO> optServItemList;
	private List<ImsRptBasketItemDTO> mediaItemList;
	private List<ImsRptBasketItemDTO> backendVasItemList;
	
	public ImsRptOptServiceDetailDTO(){
		
	}

	public List<ImsRptBasketItemDTO> getOptPremItemList() {
		return optPremItemList;
	}

	public void setOptPremItemList(List<ImsRptBasketItemDTO> optPremItemList) {
		this.optPremItemList = optPremItemList;
	}

	public List<ImsRptBasketItemDTO> getWlBbItemList() {
		return wlBbItemList;
	}

	public void setWlBbItemList(List<ImsRptBasketItemDTO> wlBbItemList) {
		this.wlBbItemList = wlBbItemList;
	}

	public List<ImsRptBasketItemDTO> getAntiVirItemList() {
		return antiVirItemList;
	}

	public void setAntiVirItemList(List<ImsRptBasketItemDTO> antiVirItemList) {
		this.antiVirItemList = antiVirItemList;
	}

	public List<ImsRptBasketItemDTO> getOptServItemList() {
		return optServItemList;
	}

	public void setOptServItemList(List<ImsRptBasketItemDTO> optServItemList) {
		this.optServItemList = optServItemList;
	}

	public List<ImsRptBasketItemDTO> getMediaItemList() {
		return mediaItemList;
	}

	public void setMediaItemList(List<ImsRptBasketItemDTO> mediaItemList) {
		this.mediaItemList = mediaItemList;
	}

	@Override
	public String toString() {
		return "ImsOptServiceDetailDTO [optPremItemList=" + optPremItemList
				+ ", wlBbItemList=" + wlBbItemList + ", antiVirItemList="
				+ antiVirItemList + ", mediaItemList=" + mediaItemList + ", backendVasItemList=" + backendVasItemList+"]";
	}

	public void setBackendVasItemList(List<ImsRptBasketItemDTO> backendVasItemList) {
		this.backendVasItemList = backendVasItemList;
	}

	public List<ImsRptBasketItemDTO> getBackendVasItemList() {
		return backendVasItemList;
	}

}
