package com.pccw.rpt.schema.dto.slv.salesAgreement;

import com.pccw.rpt.util.ReportUtil;

public class SectionARptDTO extends SectionRptDTO {



	/**
	 * 
	 */
	private static final long serialVersionUID = -581392752634436030L;
	private CustomerRptDTO customer = new CustomerRptDTO();
	private CustomerRptDTO contractor = new CustomerRptDTO();
	private AddressRptDTO installAddress = new AddressRptDTO();
	private AddressRptDTO billingAddress = new AddressRptDTO();
	private boolean hasCustomer=false;
	private boolean hasContractor=false;

	private String iaInd;
	private String baInd;
	
	private String fullAddress;
	
	private boolean instantUpdBA;
	private String customerTitle;
	
	private String contractorTitleDisplay;
	private String contractorTitle;
	private String designerTitle;
	private String developerTitle;
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

	public AddressRptDTO getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(AddressRptDTO billingAddress) {
		this.billingAddress = billingAddress;
	}

	public boolean isInstantUpdBA() {
		return this.instantUpdBA;
	}

	public void setInstantUpdBA(boolean pInstantUpdBA) {
		this.instantUpdBA = pInstantUpdBA;
	}

	public String getCustomerTitle() {
		return this.customerTitle;
	}

	public void setCustomerTitle(String pCustomerTitle) {
		this.customerTitle = pCustomerTitle;
	}

	public CustomerRptDTO getContractor() {
		return this.contractor;
	}

	public void setContractor(CustomerRptDTO pContractor) {
		this.contractor = pContractor;
	}


	public String getContractorTitle() {
		return this.contractorTitle;
	}

	public void setContractorTitle(String pContractorTitle) {
		this.contractorTitle = pContractorTitle;
	}

	public String getDesignerTitle() {
		return this.designerTitle;
	}

	public void setDesignerTitle(String pDesignerTitle) {
		this.designerTitle = pDesignerTitle;
	}

	public String getDeveloperTitle() {
		return this.developerTitle;
	}

	public void setDeveloperTitle(String pDeveloperTitle) {
		this.developerTitle = pDeveloperTitle;
	}

	public boolean isHasCustomer() {
		return this.hasCustomer;
	}

	public void setHasCustomer(boolean pHasCustomer) {
		this.hasCustomer = pHasCustomer;
	}

	public boolean isHasContractor() {
		return this.hasContractor;
	}

	public void setHasContractor(boolean pHasContractor) {
		this.hasContractor = pHasContractor;
	}

	public String getContractorTitleDisplay() {
		return this.contractorTitleDisplay;
	}

	public void setContractorTitleDisplay(String pContractorTitleDisplay) {
		this.contractorTitleDisplay = pContractorTitleDisplay;
	}
}
