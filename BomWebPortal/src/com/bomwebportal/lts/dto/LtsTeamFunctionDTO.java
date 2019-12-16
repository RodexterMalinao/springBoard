package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class LtsTeamFunctionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3878769997103649467L;

	private String enquiryOnly;
	private String displayStaffPlan;
	private String forceDocId;
	private String renewalOrder;
	private String upgradeOrder;
	private String standaloneVasOrder;
	
	public String getEnquiryOnly() {
		return enquiryOnly;
	}
	public void setEnquiryOnly(String enquiryOnly) {
		this.enquiryOnly = enquiryOnly;
	}
	public String getDisplayStaffPlan() {
		return displayStaffPlan;
	}
	public void setDisplayStaffPlan(String displayStaffPlan) {
		this.displayStaffPlan = displayStaffPlan;
	}
	public String getForceDocId() {
		return forceDocId;
	}
	public void setForceDocId(String forceDocId) {
		this.forceDocId = forceDocId;
	}
	public String getRenewalOrder() {
		return renewalOrder;
	}
	public void setRenewalOrder(String renewalOrder) {
		this.renewalOrder = renewalOrder;
	}
	public String getUpgradeOrder() {
		return upgradeOrder;
	}
	public void setUpgradeOrder(String upgradeOrder) {
		this.upgradeOrder = upgradeOrder;
	}
	public String getStandaloneVasOrder() {
		return standaloneVasOrder;
	}
	public void setStandaloneVasOrder(String standaloneVasOrder) {
		this.standaloneVasOrder = standaloneVasOrder;
	}
	
}
