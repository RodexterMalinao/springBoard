package com.bomwebportal.lts.dto.form.acq;

import java.io.Serializable;

public class LtsAcqAccountSelectionFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1834462585064034407L;
	/**
	 * 
	 */

	/**
	 * 
	 */
	private String accountNum;
	private String serviceNumber;
	private String serviceType;
	private String selectedAccount;
	private boolean newAccount;
	private boolean newAccountSelected;
	
	
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSelectedAccount() {
		return selectedAccount;
	}
	public void setSelectedAccount(String selectedAccount) {
		this.selectedAccount = selectedAccount;
	}
	public boolean isNewAccount() {
		return newAccount;
	}
	public void setNewAccount(boolean newAccount) {
		this.newAccount = newAccount;
	}
	public boolean isNewAccountSelected() {
		return newAccountSelected;
	}
	public void setNewAccountSelected(boolean newAccountSelected) {
		this.newAccountSelected = newAccountSelected;
	}
	

	
	
}
