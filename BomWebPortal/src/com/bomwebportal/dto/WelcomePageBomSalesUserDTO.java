package com.bomwebportal.dto;

public class WelcomePageBomSalesUserDTO extends BomSalesUserDTO {

	private static final long serialVersionUID = 3428829050012441907L;

	String falloutOrderCount;
	String reviewOrderCount;
	// added by karen
	String ltsAlertCount;
	String imsAlertCount;

	public String getFalloutOrderCount() {
		return falloutOrderCount;
	}

	public void setFalloutOrderCount(String falloutOrderCount) {
		this.falloutOrderCount = falloutOrderCount;
	}

	public String getReviewOrderCount() {
		return reviewOrderCount;
	}

	public void setReviewOrderCount(String reviewOrderCount) {
		this.reviewOrderCount = reviewOrderCount;
	}

	public String getLtsAlertCount() {
		return this.ltsAlertCount;
	}

	public void setLtsAlertCount(String pLtsAlertCount) {
		this.ltsAlertCount = pLtsAlertCount;
	}

	public String getImsAlertCount() {
		return this.imsAlertCount;
	}

	public void setImsAlertCount(String pImsAlertCount) {
		this.imsAlertCount = pImsAlertCount;
	}

}
