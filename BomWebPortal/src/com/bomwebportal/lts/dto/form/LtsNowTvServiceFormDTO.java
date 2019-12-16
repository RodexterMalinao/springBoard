package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;

public class LtsNowTvServiceFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7203148604801265157L;
	
	private List<ItemDetailDTO> nowTvSportItemList;
	private List<ItemDetailDTO> nowTvCeleItemList;
	private List<ItemDetailDTO> nowTvFreeItemList;
	private List<ItemDetailDTO> nowTvMirrorItemList;
	private List<ItemDetailDTO> nowTvPayItemList;
	private List<ItemDetailDTO> nowTvSpecItemList;
	private List<ItemDetailDTO> nowTvEmailItemList;
	private List<ItemDetailDTO> nowTvPrintItemList;
	private List<ItemDetailDTO> nowTvDeviceItemList;
	private List<ChannelGroupDetailDTO> payChannelGroupList;
	private List<ChannelGroupDetailDTO> specChannelGroupList;
	
	// FOR DISPLAY
	private List<AdditionalTvType> additionalTvTypeSelectionList;
	private boolean existingNowTvCust;
	private String existingBillType;
	private boolean confirmAvAdultChannel;
	private String dateOfBirth;
	private AdditionalTvType additionalTvType;
	private String nowTvEmail;
	private boolean showDateOfBirth = true;
	
	private boolean selectedBasketBundleTv;
	
	private boolean allow2L2B = false;
	
	private List<FsaDetailDTO> mirrorTvFsaList;
	private boolean allowPcdMirror = false;
	private String mirrorFsa;
	
	public enum AdditionalTvType {
		MIRROR("Receive nowTV Services via eye Device"), 
		SD_PAY_CHANNEL("SD Pay Channel Service Plan"), 
		SD_SPECIAL_CHANNEL("SD Special Pay Channel Service Plan"), 
		NO_ADDITIONAL_TV("Starter Pack Only"),
		ADDITIONAL_CHANNELS("Additional Channels");
		
		private String desc;
		   private AdditionalTvType(String desc) {
		       this.desc = desc;
		   }
		   public String getDesc() {
		       return desc;
		   }
		   public String getName() {
		       return name();
		   } 
		   
		   @Override
		   public String toString () {
		       return getDesc();
		   } 
	}


	public List<ItemDetailDTO> getNowTvSportItemList() {
		return nowTvSportItemList;
	}

	public void setNowTvSportItemList(List<ItemDetailDTO> nowTvSportItemList) {
		this.nowTvSportItemList = nowTvSportItemList;
	}

	public boolean isExistingNowTvCust() {
		return existingNowTvCust;
	}

	public void setExistingNowTvCust(boolean existingNowTvCust) {
		this.existingNowTvCust = existingNowTvCust;
	}

	public String getExistingBillType() {
		return existingBillType;
	}

	public void setExistingBillType(String existingBillType) {
		this.existingBillType = existingBillType;
	}

	public List<ItemDetailDTO> getNowTvCeleItemList() {
		return nowTvCeleItemList;
	}

	public void setNowTvCeleItemList(List<ItemDetailDTO> nowTvCeleItemList) {
		this.nowTvCeleItemList = nowTvCeleItemList;
	}

	public List<ItemDetailDTO> getNowTvFreeItemList() {
		return nowTvFreeItemList;
	}

	public void setNowTvFreeItemList(List<ItemDetailDTO> nowTvFreeItemList) {
		this.nowTvFreeItemList = nowTvFreeItemList;
	}

	public List<ItemDetailDTO> getNowTvMirrorItemList() {
		return nowTvMirrorItemList;
	}

	public void setNowTvMirrorItemList(List<ItemDetailDTO> nowTvMirrorItemList) {
		this.nowTvMirrorItemList = nowTvMirrorItemList;
	}

	public List<ItemDetailDTO> getNowTvPayItemList() {
		return nowTvPayItemList;
	}

	public void setNowTvPayItemList(List<ItemDetailDTO> nowTvPayItemList) {
		this.nowTvPayItemList = nowTvPayItemList;
	}

	public List<ItemDetailDTO> getNowTvSpecItemList() {
		return nowTvSpecItemList;
	}

	public void setNowTvSpecItemList(List<ItemDetailDTO> nowTvSpecItemList) {
		this.nowTvSpecItemList = nowTvSpecItemList;
	}

	public List<ChannelGroupDetailDTO> getPayChannelGroupList() {
		return payChannelGroupList;
	}

	public void setPayChannelGroupList(
			List<ChannelGroupDetailDTO> payChannelGroupList) {
		this.payChannelGroupList = payChannelGroupList;
	}

	public List<ChannelGroupDetailDTO> getSpecChannelGroupList() {
		return specChannelGroupList;
	}

	public void setSpecChannelGroupList(
			List<ChannelGroupDetailDTO> specChannelGroupList) {
		this.specChannelGroupList = specChannelGroupList;
	}

	public boolean isConfirmAvAdultChannel() {
		return confirmAvAdultChannel;
	}

	public void setConfirmAvAdultChannel(boolean confirmAvAdultChannel) {
		this.confirmAvAdultChannel = confirmAvAdultChannel;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public AdditionalTvType getAdditionalTvType() {
		return additionalTvType;
	}

	public void setAdditionalTvType(AdditionalTvType additionalTvType) {
		this.additionalTvType = additionalTvType;
	}

	public List<AdditionalTvType> getAdditionalTvTypeSelectionList() {
		return additionalTvTypeSelectionList;
	}

	public void setAdditionalTvTypeSelectionList(
			List<AdditionalTvType> additionalTvTypeSelectionList) {
		this.additionalTvTypeSelectionList = additionalTvTypeSelectionList;
	}

	public List<ItemDetailDTO> getNowTvEmailItemList() {
		return nowTvEmailItemList;
	}

	public void setNowTvEmailItemList(List<ItemDetailDTO> nowTvEmailItemList) {
		this.nowTvEmailItemList = nowTvEmailItemList;
	}

	public List<ItemDetailDTO> getNowTvPrintItemList() {
		return nowTvPrintItemList;
	}

	public void setNowTvPrintItemList(List<ItemDetailDTO> nowTvPrintItemList) {
		this.nowTvPrintItemList = nowTvPrintItemList;
	}

	public List<ItemDetailDTO> getNowTvDeviceItemList() {
		return nowTvDeviceItemList;
	}

	public void setNowTvDeviceItemList(List<ItemDetailDTO> nowTvDeviceItemList) {
		this.nowTvDeviceItemList = nowTvDeviceItemList;
	}

	public String getNowTvEmail() {
		return nowTvEmail;
	}

	public void setNowTvEmail(String nowTvEmail) {
		this.nowTvEmail = nowTvEmail;
	}

	public boolean isSelectedBasketBundleTv() {
		return selectedBasketBundleTv;
	}

	public void setSelectedBasketBundleTv(boolean selectedBasketBundleTv) {
		this.selectedBasketBundleTv = selectedBasketBundleTv;
	}

	public boolean isShowDateOfBirth() {
		return showDateOfBirth;
	}

	public void setShowDateOfBirth(boolean showDateOfBirth) {
		this.showDateOfBirth = showDateOfBirth;
	}

	public boolean isAllow2L2B() {
		return allow2L2B;
	}

	public void setAllow2L2B(boolean allow2l2b) {
		allow2L2B = allow2l2b;
	}

	public List<FsaDetailDTO> getMirrorTvFsaList() {
		return mirrorTvFsaList;
	}

	public void setMirrorTvFsaList(List<FsaDetailDTO> mirrorTvFsaList) {
		this.mirrorTvFsaList = mirrorTvFsaList;
	}

	public boolean isAllowPcdMirror() {
		return allowPcdMirror;
	}

	public void setAllowPcdMirror(boolean allowPcdMirror) {
		this.allowPcdMirror = allowPcdMirror;
	}

	public String getMirrorFsa() {
		return mirrorFsa;
	}

	public void setMirrorFsa(String mirrorFsa) {
		this.mirrorFsa = mirrorFsa;
	}
	
}
