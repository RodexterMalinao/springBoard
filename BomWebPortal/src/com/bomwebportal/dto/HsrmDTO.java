package com.bomwebportal.dto;

public class HsrmDTO extends ResultDTO {

	private String queueStatus;
	private String queueBrand;
	private String basketBrand;

	public String getQueueStatus() {
		return queueStatus;
	}

	public void setQueueStatus(String queueStatus) {
		this.queueStatus = queueStatus;
	}

	public String getQueueBrand() {
		return queueBrand;
	}

	public void setQueueBrand(String queueBrand) {
		this.queueBrand = queueBrand;
	}

	public String getBasketBrand() {
		return basketBrand;
	}

	public void setBasketBrand(String basketBrand) {
		this.basketBrand = basketBrand;
	}

}
