package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;


public class ServiceDetailDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 530327011975513116L;
	
	public ServiceDetailDTO(){
		
	}

	private String technology;
	private String technologySupported;
	private String isDeadCase;
	private String isResrcShort;
	private int leadTime;
	private Date estEarliestSrd;
	private String rtnCd;
	private String rtnDesc;
	private String BmoRmk;
	private String BmoAlert;
	private String BmoLeadDay;
	
	//Celia 
	private String hasSDTV;
	private String hasHDTV;
	private String hasSPHDTV;
	
	public String getHasSDTV() {
		return hasSDTV;
	}
	public void setHasSDTV(String hasSDTV) {
		this.hasSDTV = hasSDTV;
	}
	public String getHasHDTV() {
		return hasHDTV;
	}
	public void setHasHDTV(String hasHDTV) {
		this.hasHDTV = hasHDTV;
	}
	public String getHasSPHDTV() {
		return hasSPHDTV;
	}
	public void setHasSPHDTV(String hasSPHDTV) {
		this.hasSPHDTV = hasSPHDTV;
	}
	public String getBmoLeadDay() {
		return BmoLeadDay;
	}
	public void setBmoLeadDay(String bmoLeadDay) {
		BmoLeadDay = bmoLeadDay;
	}
	
	public String getBmoRmk() {
		return BmoRmk;
	}
	public void setBmoRmk(String bmoRmk) {
		BmoRmk = bmoRmk;
	}
	public String getBmoAlert() {
		return BmoAlert;
	}
	public void setBmoAlert(String bmoAlert) {
		BmoAlert = bmoAlert;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getTechnologySupported() {
		return technologySupported;
	}
	public void setTechnologySupported(String technologySupported) {
		this.technologySupported = technologySupported;
	}

	public String getIsDeadCase() {
		return isDeadCase;
	}
	public void setIsDeadCase(String isDeadCase) {
		this.isDeadCase = isDeadCase;
	}

	public String getIsResrcShort() {
		return isResrcShort;
	}
	public void setIsResrcShort(String isResrcShort) {
		this.isResrcShort = isResrcShort;
	}

	public int getLeadTime() {
		return leadTime;
	}
	public void setLeadTime(int leadTime) {
		this.leadTime = leadTime;
	}

	public Date getEstEarliestSrd() {
		return estEarliestSrd;
	}
	public void setEstEarliestSrd(Date estEarliestSrd) {
		this.estEarliestSrd = estEarliestSrd;
	}

	public String getRtnCd() {
		return rtnCd;
	}
	public void setRtnCd(String rtnCd) {
		this.rtnCd = rtnCd;
	}

	public String getRtnDesc() {
		return rtnDesc;
	}
	public void setRtnDesc(String rtnDesc) {
		this.rtnDesc = rtnDesc;
	}
	
	@Override
	public String toString() {
		return "ServiceDetailDTO [technology=" + technology
				+ ", technologySupported=" + technologySupported
				+ ", isDeadCase=" + isDeadCase + ", isResrcShort="
				+ isResrcShort + ", leadTime=" + leadTime + ", estEarliestSrd="
				+ estEarliestSrd + ", rtnCd=" + rtnCd + ", rtnDesc=" + rtnDesc
				+ "]";
	}
	
}
