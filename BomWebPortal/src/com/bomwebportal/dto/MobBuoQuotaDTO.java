package com.bomwebportal.dto;

public class MobBuoQuotaDTO {

	private String orderId;
	private String itemId;
	private String autoTopUpInd;
	private String maxCntAutoTopUp;
	private String buoId;
	private String createBy;
	private String lastUpdBy;
	
	private String engDesc;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getAutoTopUpInd() {
		return autoTopUpInd;
	}

	public void setAutoTopUpInd(String autoTopUpInd) {
		this.autoTopUpInd = autoTopUpInd;
	}

	public String getMaxCntAutoTopUp() {
		return maxCntAutoTopUp;
	}

	public void setMaxCntAutoTopUp(String maxCntAutoTopUp) {
		this.maxCntAutoTopUp = maxCntAutoTopUp;
	}

	public String getBuoId() {
		return buoId;
	}

	public void setBuoId(String buoId) {
		this.buoId = buoId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public String getEngDesc() {
		return engDesc;
	}

	public void setEngDesc(String engDesc) {
		this.engDesc = engDesc;
	}
}
