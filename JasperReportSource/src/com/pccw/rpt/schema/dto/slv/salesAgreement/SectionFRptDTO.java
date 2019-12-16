package com.pccw.rpt.schema.dto.slv.salesAgreement;

import java.util.ArrayList;

import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionCRptDTO.ChargingItem;

public class SectionFRptDTO extends SectionRptDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9216946418496719408L;
	private String distributionMode;
	private String distributeTo;
	private String email;
	
	public String getDistributionMode() {
		return this.distributionMode;
	}
	public void setDistributionMode(String pDistributionMode) {
		this.distributionMode = pDistributionMode;
	}
	public String getDistributeTo() {
		return this.distributeTo;
	}
	public void setDistributeTo(String pDistributeTo) {
		this.distributeTo = pDistributeTo;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String pEmail) {
		this.email = pEmail;
	}
}
