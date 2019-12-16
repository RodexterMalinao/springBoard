package com.bomwebportal.lts.dto.form;

import java.io.Serializable;

public class LtsCustomerInquiryFormDTO implements Serializable {

	private static final long serialVersionUID = -482310554780211647L;

	private Action formAction;
	private String docType;
	private String docNum;
	private String serviceNum;
	private Boolean thirdPartyApplication = false;
	private Boolean dummyDoc = false;
	private boolean allowCreateOrder;
	private boolean upgradeToStaffPlan;
	private String staffId;
	private boolean bocEmpty;
	private boolean allowSearchProfile;
	
	
	public enum Action {
		CLEAR_PROFILE, SEARCH_PROFILE, SUBMIT_RET, SUBMIT_UPG, SUBMIT_STANDALONE_VAS;
	}
	
	public Action getFormAction() {
		return formAction;
	}
	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}
	public Boolean getThirdPartyApplication() {
		return thirdPartyApplication;
	}
	public void setThirdPartyApplication(Boolean thirdPartyApplication) {
		this.thirdPartyApplication = thirdPartyApplication;
	}
	public Boolean getDummyDoc() {
		return dummyDoc;
	}
	public void setDummyDoc(Boolean dummyDoc) {
		this.dummyDoc = dummyDoc;
	}
	public boolean isAllowCreateOrder() {
		return allowCreateOrder;
	}
	public void setAllowCreateOrder(boolean allowCreateOrder) {
		this.allowCreateOrder = allowCreateOrder;
	}
	public boolean isUpgradeToStaffPlan() {
		return upgradeToStaffPlan;
	}
	public void setUpgradeToStaffPlan(boolean upgradeToStaffPlan) {
		this.upgradeToStaffPlan = upgradeToStaffPlan;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public boolean isBocEmpty() {
		return bocEmpty;
	}
	public void setBocEmpty(boolean bocEmpty) {
		this.bocEmpty = bocEmpty;
	}
	public boolean isAllowSearchProfile() {
		return allowSearchProfile;
	}
	public void setAllowSearchProfile(boolean allowSearchProfile) {
		this.allowSearchProfile = allowSearchProfile;
	}

	
}
