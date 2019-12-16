package com.bomwebportal.lts.dto.form.disconnect;

import java.io.Serializable;

public class LtsTerminateBillingInfoFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6291487839131056426L;
	
	public static final String CHANGE_BILLING_NAME_IND_FORCE = "F";
	public static final String CHANGE_BILLING_NAME_IND_DISALLOW = "D";
	public static final String CHANGE_BILLING_NAME_IND_OPTIONAL = "O";
	
	private String billingAddress;
	private String existingBillingName;
	private String existingPayMethodType;
	private String existingBillMedia;
	private String existingBillingEmail;
	
	private boolean changeBa;
	private boolean instantUpdateBa;
	private String baQuickSearch;
	private String newBillingAddress;
	private String newBillingName;
	
	private String changeBillingNameInd;  
	
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	public String getExistingPayMethodType() {
		return existingPayMethodType;
	}
	public void setExistingPayMethodType(String existingPayMethodType) {
		this.existingPayMethodType = existingPayMethodType;
	}
	public boolean isChangeBa() {
		return changeBa;
	}
	public void setChangeBa(boolean changeBa) {
		this.changeBa = changeBa;
	}
	public boolean isInstantUpdateBa() {
		return instantUpdateBa;
	}
	public void setInstantUpdateBa(boolean instantUpdateBa) {
		this.instantUpdateBa = instantUpdateBa;
	}
	public String getBaQuickSearch() {
		return baQuickSearch;
	}
	public void setBaQuickSearch(String baQuickSearch) {
		this.baQuickSearch = baQuickSearch;
	}
	public String getNewBillingAddress() {
		return newBillingAddress;
	}
	public void setNewBillingAddress(String newBillingAddress) {
		this.newBillingAddress = newBillingAddress;
	}
	public String getNewBillingName() {
		return newBillingName;
	}
	public void setNewBillingName(String newBillingName) {
		this.newBillingName = newBillingName;
	}
	public String getExistingBillingName() {
		return existingBillingName;
	}
	public void setExistingBillingName(String existingBillingName) {
		this.existingBillingName = existingBillingName;
	}
	public String getChangeBillingNameInd() {
		return changeBillingNameInd;
	}
	public void setChangeBillingNameInd(String changeBillingNameInd) {
		this.changeBillingNameInd = changeBillingNameInd;
	}
	public String getExistingBillMedia() {
		return existingBillMedia;
	}
	public void setExistingBillMedia(String existingBillMedia) {
		this.existingBillMedia = existingBillMedia;
	}
	public String getExistingBillingEmail() {
		return existingBillingEmail;
	}
	public void setExistingBillingEmail(String existingBillingEmail) {
		this.existingBillingEmail = existingBillingEmail;
	}


}
