package com.bomwebportal.lts.dto.ret;

import java.io.Serializable;

public class RenewBasketPolicyDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3344990402648357974L;
	
	private String newBasketCatg;
	private String extendContractPeriod;
	private String earliestSrd;
	private String warningMsg;
	
	public String getNewBasketCatg() {
		return newBasketCatg;
	}
	public void setNewBasketCatg(String newBasketCatg) {
		this.newBasketCatg = newBasketCatg;
	}
	public String getExtendContractPeriod() {
		return extendContractPeriod;
	}
	public void setExtendContractPeriod(String extendContractPeriod) {
		this.extendContractPeriod = extendContractPeriod;
	}
	public String getEarliestSrd() {
		return earliestSrd;
	}
	public void setEarliestSrd(String earliestSrd) {
		this.earliestSrd = earliestSrd;
	}
	public String getWarningMsg() {
		return warningMsg;
	}
	public void setWarningMsg(String warningMsg) {
		this.warningMsg = warningMsg;
	}
	
	
}
