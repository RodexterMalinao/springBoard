package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.Date;

import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class LtsResendAfFormDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -73415665698309991L;
	
	private Action formAction = Action.INITIAL;

	// Search Criteria
	private SearchMethod searchMethod = SearchMethod.REQ_DATE;
	private String searchOrderId;
	private String searchRequestDate = Utility.date2String(new Date(), BomWebPortalConstant.DATE_FORMAT);
	private String teamCd;

	// Preview Option
	private String previewOrderId;
	
	// Resend Option
	private String resendMethod = "E";
	private String resendSms;
	private String resendEmail;
	private String billAddress;
	private Boolean isPaperShow;
	
	public Boolean getIsPaperShow() {
		return isPaperShow;
	}

	public void setIsPaperShow(Boolean isPaperShow) {
		this.isPaperShow = isPaperShow;
	}

	public String getBillAddress() {
		return billAddress;
	}

	public void setBillAddress(String billAddress) {
		this.billAddress = billAddress;
	}

	private String emailTemplateId;
	private String smsTemplateId;

	private boolean allowResend = true;
	
	public enum SearchMethod {
		REQ_DATE,
		ORDER_ID
	};
	
	public enum Action {
		INITIAL,
		SEARCH, 
		PREVIEW, 
		SEND,
		CLEAR
	}


	public Action getFormAction() {
		return formAction;
	}

	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}

	public SearchMethod getSearchMethod() {
		return searchMethod;
	}

	public void setSearchMethod(SearchMethod searchMethod) {
		this.searchMethod = searchMethod;
	}

	public String getSearchOrderId() {
		return searchOrderId;
	}

	public void setSearchOrderId(String searchOrderId) {
		this.searchOrderId = searchOrderId;
	}

	public String getSearchRequestDate() {
		return searchRequestDate;
	}

	public void setSearchRequestDate(String searchRequestDate) {
		this.searchRequestDate = searchRequestDate;
	}

	public String getTeamCd() {
		return teamCd;
	}

	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}

	public String getPreviewOrderId() {
		return previewOrderId;
	}

	public void setPreviewOrderId(String previewOrderId) {
		this.previewOrderId = previewOrderId;
	}

	public String getResendMethod() {
		return resendMethod;
	}

	public void setResendMethod(String resendMethod) {
		this.resendMethod = resendMethod;
	}

	public String getResendSms() {
		return resendSms;
	}

	public void setResendSms(String resendSms) {
		this.resendSms = resendSms;
	}

	public String getResendEmail() {
		return resendEmail;
	}

	public void setResendEmail(String resendEmail) {
		this.resendEmail = resendEmail;
	}

	public String getEmailTemplateId() {
		return emailTemplateId;
	}

	public void setEmailTemplateId(String emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	public String getSmsTemplateId() {
		return smsTemplateId;
	}

	public void setSmsTemplateId(String smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}

	public boolean isAllowResend() {
		return allowResend;
	}

	public void setAllowResend(boolean allowResend) {
		this.allowResend = allowResend;
	};
	

	
}
