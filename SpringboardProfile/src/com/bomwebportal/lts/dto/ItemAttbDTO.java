package com.bomwebportal.lts.dto;

import java.util.List;

public class ItemAttbDTO extends ItemAttbBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5407690407547261292L;

	private String comptId;
	
	private String offerId;
	
	private String prodId;
	
	private List<ItemAttbInfoDTO> itemAttbInfoList;

	public String getComptId() {
		return comptId;
	}

	public void setComptId(String comptId) {
		this.comptId = comptId;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	
	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public List<ItemAttbInfoDTO> getItemAttbInfoList() {
		return itemAttbInfoList;
	}

	public void setItemAttbInfoList(List<ItemAttbInfoDTO> itemAttbInfoList) {
		this.itemAttbInfoList = itemAttbInfoList;
	}
	
	
}
