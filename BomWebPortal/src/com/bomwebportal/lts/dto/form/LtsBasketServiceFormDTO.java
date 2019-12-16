package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;

public class LtsBasketServiceFormDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2994747197944819557L;
	
	private List<ItemDetailDTO> planItemList;
	private List<ItemDetailDTO> bvasItemList;
	private List<ItemDetailDTO> bbRentalItemList;
	private List<ItemSetDetailDTO> contItemSetList;
	private List<ItemSetDetailDTO> deviceRedemSetList;
	private List<ItemDetailDTO> contentItemList;
	private List<ItemDetailDTO> moovItemList;
	private List<ItemDetailDTO> installItemList; 
	private List<ItemDetailDTO> adminChargeItemList;
	private List<ItemDetailDTO> cancelChargeItemList;
	private boolean excludeQuotaCheck;
	private boolean isAllowSelfPickup;
	
	public List<ItemDetailDTO> getPlanItemList() {
		return planItemList;
	}
	public void setPlanItemList(List<ItemDetailDTO> planItemList) {
		this.planItemList = planItemList;
	}
	public List<ItemDetailDTO> getBvasItemList() {
		return bvasItemList;
	}
	public void setBvasItemList(List<ItemDetailDTO> bvasItemList) {
		this.bvasItemList = bvasItemList;
	}
	public List<ItemDetailDTO> getBbRentalItemList() {
		return bbRentalItemList;
	}
	public void setBbRentalItemList(List<ItemDetailDTO> bbRentalItemList) {
		this.bbRentalItemList = bbRentalItemList;
	}
	public List<ItemSetDetailDTO> getContItemSetList() {
		return contItemSetList;
	}
	public void setContItemSetList(List<ItemSetDetailDTO> contItemSetList) {
		this.contItemSetList = contItemSetList;
	}
	public List<ItemDetailDTO> getContentItemList() {
		return contentItemList;
	}
	public void setContentItemList(List<ItemDetailDTO> contentItemList) {
		this.contentItemList = contentItemList;
	}
	public List<ItemDetailDTO> getMoovItemList() {
		return moovItemList;
	}
	public void setMoovItemList(List<ItemDetailDTO> moovItemList) {
		this.moovItemList = moovItemList;
	}
	public List<ItemDetailDTO> getInstallItemList() {
		return installItemList;
	}
	public void setInstallItemList(List<ItemDetailDTO> installItemList) {
		this.installItemList = installItemList;
	}
	public List<ItemDetailDTO> getAdminChargeItemList() {
		return adminChargeItemList;
	}
	public void setAdminChargeItemList(List<ItemDetailDTO> adminChargeItemList) {
		this.adminChargeItemList = adminChargeItemList;
	}
	public List<ItemDetailDTO> getCancelChargeItemList() {
		return cancelChargeItemList;
	}
	public void setCancelChargeItemList(List<ItemDetailDTO> cancelChargeItemList) {
		this.cancelChargeItemList = cancelChargeItemList;
	}
	public boolean isExcludeQuotaCheck() {
		return excludeQuotaCheck;
	}
	public void setExcludeQuotaCheck(boolean excludeQuotaCheck) {
		this.excludeQuotaCheck = excludeQuotaCheck;
	}
	public List<ItemSetDetailDTO> getDeviceRedemSetList() {
		return deviceRedemSetList;
	}
	public void setDeviceRedemSetList(List<ItemSetDetailDTO> deviceRedemSetList) {
		this.deviceRedemSetList = deviceRedemSetList;
	}
	public boolean isAllowSelfPickup() {
		return isAllowSelfPickup;
	}
	public void setAllowSelfPickup(boolean isAllowSelfPickup) {
		this.isAllowSelfPickup = isAllowSelfPickup;
	}
	
}
