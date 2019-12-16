package com.bomwebportal.lts.dto.form.acq;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;

public class LtsAcqBasketServiceFormDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2994747197944819557L;
	
	private List<ItemDetailDTO> planItemList;
	private List<ItemDetailDTO> bvasItemList;
	private List<ItemSetDetailDTO> contItemSetList;
	private List<ItemDetailDTO> contentItemList;
	private List<ItemDetailDTO> moovItemList;
	private List<ItemDetailDTO> installItemList; 
	private boolean excludeQuotaCheck;
	
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
	public boolean isExcludeQuotaCheck() {
		return excludeQuotaCheck;
	}
	public void setExcludeQuotaCheck(boolean excludeQuotaCheck) {
		this.excludeQuotaCheck = excludeQuotaCheck;
	}
	
	
}
