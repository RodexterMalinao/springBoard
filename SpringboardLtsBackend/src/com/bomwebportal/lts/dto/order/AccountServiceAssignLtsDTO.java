package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;


public class AccountServiceAssignLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -8875727777667261121L;

	private AccountDetailLtsDTO account = null;
	private String action = null;
	private String chrgType; 

	
	public AccountDetailLtsDTO getAccount() {
		return account;
	}

	public void setAccount(AccountDetailLtsDTO account) {
		this.account = account;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getChrgType() {
		return chrgType;
	}

	public void setChrgType(String chrgType) {
		this.chrgType = chrgType;
	}
	
	
}