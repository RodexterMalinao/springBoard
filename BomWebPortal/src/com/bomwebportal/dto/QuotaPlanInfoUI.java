package com.bomwebportal.dto;

import java.util.List;

public class QuotaPlanInfoUI {

	private List<QuotaPlanInfoDTO> quotaPlanInfoDTO;
	private ItemDisplayDTO itemDisplayDTO;
	
	private String autoTopUpInd;
	private String selectedQuotaPlanOption;
	private String maxTopUpTimes;
	private String itemId;
	private String engDesc;
	private String iotPlan;
	private String suppressLocal;
	private String suppressRoam;

	public List<QuotaPlanInfoDTO> getQuotaPlanInfoDTO() {
		return quotaPlanInfoDTO;
	}

	public void setQuotaPlanInfoDTO(List<QuotaPlanInfoDTO> quotaPlanInfoDTO) {
		this.quotaPlanInfoDTO = quotaPlanInfoDTO;
	}

	public ItemDisplayDTO getItemDisplayDTO() {
		return itemDisplayDTO;
	}

	public void setItemDisplayDTO(ItemDisplayDTO itemDisplayDTO) {
		this.itemDisplayDTO = itemDisplayDTO;
	}

	public String getAutoTopUpInd() {
		return autoTopUpInd;
	}

	public void setAutoTopUpInd(String autoTopUpInd) {
		this.autoTopUpInd = autoTopUpInd;
	}

	public String getSelectedQuotaPlanOption() {
		return selectedQuotaPlanOption;
	}

	public void setSelectedQuotaPlanOption(String selectedQuotaPlanOption) {
		this.selectedQuotaPlanOption = selectedQuotaPlanOption;
	}

	public String getMaxTopUpTimes() {
		return maxTopUpTimes;
	}

	public void setMaxTopUpTimes(String maxTopUpTimes) {
		this.maxTopUpTimes = maxTopUpTimes;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getEngDesc() {
		return engDesc;
	}

	public void setEngDesc(String engDesc) {
		this.engDesc = engDesc;
	}

	public String getIotPlan() {
		return iotPlan;
	}

	public void setIotPlan(String iotPlan) {
		this.iotPlan = iotPlan;
	}

	public String getSuppressLocal() {
		return suppressLocal;
	}

	public void setSuppressLocal(String suppressLocal) {
		this.suppressLocal = suppressLocal;
	}

	public String getSuppressRoam() {
		return suppressRoam;
	}

	public void setSuppressRoam(String suppressRoam) {
		this.suppressRoam = suppressRoam;
	}
}
