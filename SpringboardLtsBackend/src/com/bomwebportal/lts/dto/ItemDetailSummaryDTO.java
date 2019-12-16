package com.bomwebportal.lts.dto;

public class ItemDetailSummaryDTO extends ItemDetailDTO {

	private static final long serialVersionUID = 2365861114676791525L;
	
	private String basketId = null;
	private String imagePath = null;
	private String itemQty = null;
	private String ioInd = null;
	private boolean isPaidVas = true;
	private boolean isExpired = false;
	private String existStartDate = null;
	private String existEndDate = null;
	private boolean isForceRetain = false;

	
	public String getBasketId() {
		return basketId;
	}

	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getItemQty() {
		return this.itemQty;
	}

	public void setItemQty(String pItemQty) {
		this.itemQty = pItemQty;
	}

	public String getIoInd() {
		return this.ioInd;
	}

	public void setIoInd(String pIoInd) {
		this.ioInd = pIoInd;
	}

	public boolean isPaidVas() {
		return this.isPaidVas;
	}

	public void setPaidVas(boolean pIsPaidVas) {
		this.isPaidVas = pIsPaidVas;
	}

	public boolean isExpired() {
		return isExpired;
	}

	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public String getExistStartDate() {
		return existStartDate;
	}

	public void setExistStartDate(String existStartDate) {
		this.existStartDate = existStartDate;
	}

	public String getExistEndDate() {
		return existEndDate;
	}

	public void setExistEndDate(String existEndDate) {
		this.existEndDate = existEndDate;
	}

	public boolean isForceRetain() {
		return isForceRetain;
	}

	public void setForceRetain(boolean isForceRetain) {
		this.isForceRetain = isForceRetain;
	}


}
