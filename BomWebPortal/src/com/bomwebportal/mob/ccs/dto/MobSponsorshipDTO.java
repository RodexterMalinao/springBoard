package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class MobSponsorshipDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4585243565898836821L;
	
	
	private String orderId;
	
	
	private String staffId;
	private String ccc;
	private String sponsorLevel;
	
	private String basketId;
	private String sponsorLevelDesc;
	
	private boolean dirty;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getCcc() {
		return ccc;
	}
	public void setCcc(String ccc) {
		this.ccc = ccc;
	}
	public String getSponsorLevel() {
		return sponsorLevel;
	}
	public void setSponsorLevel(String sponsorLevel) {
		this.sponsorLevel = sponsorLevel;
	}
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	public String getSponsorLevelDesc() {
		return sponsorLevelDesc;
	}
	public void setSponsorLevelDesc(String sponsorLevelDesc) {
		this.sponsorLevelDesc = sponsorLevelDesc;
	}
	public boolean isDirty() {
		return dirty;
	}
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	
	
}
