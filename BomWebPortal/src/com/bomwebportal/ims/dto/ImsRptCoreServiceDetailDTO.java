package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;


public class ImsRptCoreServiceDetailDTO implements Serializable{
	
	private static final long serialVersionUID = 629210834742496125L;
	
	private String bandwidth;
	private String bandwidth_desc;
	private String canSubcOptSrv;
	private String offerCd;
	private String incentiveCd;
	private String offerCdOfOther;
	private ImsRptBasketItemDTO progItem;
	private List<ImsRptBasketItemDTO> bvasMandItemList;
	private List<ImsRptBasketItemDTO> bvasNonMItemList;
	private List<ImsRptBasketItemDTO> preInstItemList;
	private List<ImsRptBasketItemDTO> otherItemList;
	
	public ImsRptCoreServiceDetailDTO(){
		
	}

	public String getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getCanSubcOptSrv() {
		return canSubcOptSrv;
	}

	public void setCanSubcOptSrv(String canSubcOptSrv) {
		this.canSubcOptSrv = canSubcOptSrv;
	}

	public String getOfferCd() {
		return offerCd;
	}

	public void setOfferCd(String offerCd) {
		this.offerCd = offerCd;
	}

	public ImsRptBasketItemDTO getProgItem() {
		return progItem;
	}

	public void setProgItem(ImsRptBasketItemDTO progItem) {
		this.progItem = progItem;
	}

	public List<ImsRptBasketItemDTO> getBvasMandItemList() {
		return bvasMandItemList;
	}

	public void setBvasMandItemList(List<ImsRptBasketItemDTO> bvasMandItemList) {
		this.bvasMandItemList = bvasMandItemList;
	}

	public List<ImsRptBasketItemDTO> getBvasNonMItemList() {
		return bvasNonMItemList;
	}

	public void setBvasNonMItemList(List<ImsRptBasketItemDTO> bvasNonMItemList) {
		this.bvasNonMItemList = bvasNonMItemList;
	}

	public List<ImsRptBasketItemDTO> getPreInstItemList() {
		return preInstItemList;
	}

	public void setPreInstItemList(List<ImsRptBasketItemDTO> preInstItemList) {
		this.preInstItemList = preInstItemList;
	}

	@Override
	public String toString() {
		return "ImsRptCoreServiceDetailDTO [bandwidth=" + bandwidth
				+ ", canSubcOptSrv=" + canSubcOptSrv + ", offerCd=" + offerCd
				+ ", progItem=" + progItem + ", bvasMandItemList="
				+ bvasMandItemList + ", bvasNonMItemList=" + bvasNonMItemList
				+ ", preInstItemList=" + preInstItemList + "]";
	}

	public void setOtherItemList(List<ImsRptBasketItemDTO> otherItemList) {
		this.otherItemList = otherItemList;
	}

	public List<ImsRptBasketItemDTO> getOtherItemList() {
		return otherItemList;
	}

	public void setIncentiveCd(String incentiveCd) {
		this.incentiveCd = incentiveCd;
	}

	public String getIncentiveCd() {
		return incentiveCd;
	}

	public void setOfferCdOfOther(String offerCdOfOther) {
		this.offerCdOfOther = offerCdOfOther;
	}

	public String getOfferCdOfOther() {
		return offerCdOfOther;
	}

	public void setBandwidth_desc(String bandwidth_desc) {
		this.bandwidth_desc = bandwidth_desc;
	}

	public String getBandwidth_desc() {
		return bandwidth_desc;
	}

}
