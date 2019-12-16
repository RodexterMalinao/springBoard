package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class AccountDetailProfileAcqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6321250429849689050L;

	private String acctNum;
	private String srvNum;
	private String serviceType;
	private String chargeType;
	private String displayChargeType;
	private String custNum;
	
	
	public String getAcctNum() {
		return acctNum;
	}
	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}
	public String getSrvNum() {
		return srvNum;
	}
	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	public String getCustNum() {
		return custNum;
	}
	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}
	public String getDisplayChargeType() {
		return displayChargeType;
	}
	public void setDisplayChargeType(String displayChargeType) {
		this.displayChargeType = displayChargeType;
	}
}
