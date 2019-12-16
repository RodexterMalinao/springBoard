package com.bomwebportal.lts.dto.form.disconnect;

import java.io.Serializable;

public class LtsTerminateCustomerInquiryFormDTO implements Serializable {

	private static final long serialVersionUID = 7758025424952127295L;
	//Fields
	private Action formAction;
	
	private String serviceNum;
	
	public enum Action {
		CLEAR_PROFILE, SUBMIT
	}
	
	public Action getFormAction() {
		return formAction;
	}
	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}
	public String getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}
}
