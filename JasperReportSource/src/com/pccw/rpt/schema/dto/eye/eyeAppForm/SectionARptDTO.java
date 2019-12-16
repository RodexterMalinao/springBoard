package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import com.pccw.rpt.util.ReportUtil;

public class SectionARptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8290853461961649207L;

	private CustomerRptDTO customer = new CustomerRptDTO();
	private AddressRptDTO installAddress = new AddressRptDTO();

	private String iaInd;
	private String baInd;
	
	private String fullAddress;
	private String billingAddress;
	
	private boolean instantUpdBA;
	
	public CustomerRptDTO getCustomer() {
		return this.customer;
	}

	public void setCustomer(CustomerRptDTO pCustomer) {
		this.customer = pCustomer;
	}
	
	public AddressRptDTO getInstallAddress() {
		return this.installAddress;
	}

	public void setInstallAddress(AddressRptDTO pInstallAddress) {
		this.installAddress = pInstallAddress;
	}

	public void setAddress(AddressRptDTO pAddress) {
		this.installAddress = pAddress;
	}	
	
	public String getIaInd() {
		return ReportUtil.defaultString(this.iaInd);
	}

	public void setIaInd(String iaInd) {
		this.iaInd = iaInd;
	}

	public String getBaInd() {
		return ReportUtil.defaultString(this.baInd);
	}

	public void setBaInd(String baInd) {
		this.baInd = baInd;
	}

	public String getIaType() {
		return "Y".equals(this.iaInd) ? "External removal" : "Same as existing address";
	}

	public String getBaType() {
		return "I".equals(this.baInd) ? "Same as installation address" : ("N".equals(this.baInd) ? "New Billing address": "Keep existing");
	}

	public String getFullAddress() {
		return this.fullAddress;
	}

	public void setFullAddress(String pFullAddress) {
		this.fullAddress = pFullAddress;
	}

	public String getBillingAddress() {
		return this.billingAddress;
	}

	public void setBillingAddress(String pBillingAddress) {
		this.billingAddress = pBillingAddress;
	}

	public boolean isInstantUpdBA() {
		return this.instantUpdBA;
	}

	public void setInstantUpdBA(boolean pInstantUpdBA) {
		this.instantUpdBA = pInstantUpdBA;
	}
}
