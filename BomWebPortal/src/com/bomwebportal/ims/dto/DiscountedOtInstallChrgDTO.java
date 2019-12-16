package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class DiscountedOtInstallChrgDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4867410961711636671L;

	private String discountedOtInstallChrgAmt;
	private String discountedOtInstallChrgDisplay;
	
	
	public String getDiscountedOtInstallChrgDisplay() {
		return discountedOtInstallChrgDisplay;
	}
	public void setDiscountedOtInstallChrgDisplay(
			String discountedOtInstallChrgDisplay) {
		this.discountedOtInstallChrgDisplay = discountedOtInstallChrgDisplay;
	}
	public void setDiscountedOtInstallChrgAmt(String discountedOtInstallChrgAmt) {
		this.discountedOtInstallChrgAmt = discountedOtInstallChrgAmt;
	}
	public String getDiscountedOtInstallChrgAmt() {
		return discountedOtInstallChrgAmt;
	}
}
