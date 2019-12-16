package com.bomwebportal.ims.dto.ui;

import com.bomwebportal.ims.dto.CustomerDTO;

public class CustomerUI extends CustomerDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3291989352340537927L;
	private String ActionInd;
		
	private AcctUI account;
	private CustOptoutInfoUI custOptInfo;
	private ContactUI contact;	
	// Gary
	private String isRegHKTLoginId;
	private String isRegClubLoginId;
	private String isRegHKTClubLoginId;
	private String hktLoginId;
	private String clubLoginId;
	private String hktClubLoginId;
	private String hktMobileNum;
	
	private CustomerDTO mobileCutomerInfo;
	
	private String fsPrepayCutOff;

	public String getActionInd() {
		return ActionInd;
	}

	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}

	public AcctUI getAccount() {
		return account;
	}

	public void setAccount(AcctUI account) {
		this.account = account;
	}

	public CustOptoutInfoUI getCustOptInfo() {
		return custOptInfo;
	}

	public void setCustOptInfo(CustOptoutInfoUI custOptInfo) {
		this.custOptInfo = custOptInfo;
	}

	public ContactUI getContact() {
		return contact;
	}

	public void setContact(ContactUI contact) {
		this.contact = contact;
	}

	public void setHktLoginId(String hktLoginId) {
		this.hktLoginId = hktLoginId;
	}

	public String getHktLoginId() {
		return hktLoginId;
	}


	public void setHktMobileNum(String hktMobileNum) {
		this.hktMobileNum = hktMobileNum;
	}

	public String getHktMobileNum() {
		return hktMobileNum;
	}

	public void setIsRegHKTLoginId(String isRegHKTLoginId) {
		this.isRegHKTLoginId = isRegHKTLoginId;
	}

	public String getIsRegHKTLoginId() {
		return isRegHKTLoginId;
	}

	public void setIsRegClubLoginId(String isRegClubLoginId) {
		this.isRegClubLoginId = isRegClubLoginId;
	}

	public String getIsRegClubLoginId() {
		return isRegClubLoginId;
	}

	public void setIsRegHKTClubLoginId(String isRegHKTClubLoginId) {
		this.isRegHKTClubLoginId = isRegHKTClubLoginId;
	}

	public String getIsRegHKTClubLoginId() {
		return isRegHKTClubLoginId;
	}

	public void setClubLoginId(String clubLoginId) {
		this.clubLoginId = clubLoginId;
	}

	public String getClubLoginId() {
		return clubLoginId;
	}

	public void setHktClubLoginId(String hktClubLoginId) {
		this.hktClubLoginId = hktClubLoginId;
	}

	public String getHktClubLoginId() {
		return hktClubLoginId;
	}

	public void setMobileCutomerInfo(CustomerDTO mobileCutomerInfo) {
		this.mobileCutomerInfo = mobileCutomerInfo;
	}

	public CustomerDTO getMobileCutomerInfo() {
		return mobileCutomerInfo;
	}

	public String getFsPrepayCutOff() {
		return fsPrepayCutOff;
	}

	public void setFsPrepayCutOff(String fsPrepayCutOff) {
		this.fsPrepayCutOff = fsPrepayCutOff;
	}
	

}
