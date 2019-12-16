package com.bomltsportal.dto.email;

import java.util.Date;

public class SubscriberDTO {
	public SubscriberDTO() {
		super();
		// TODO Auto-generated constructor stub
		this.smsLang="00";
		this.ivrsLang="00" ;
		this.childLockValue = "N";
		this.adultSuppressValue="N";
		this.pccwSuppressValue="N";
		this.telemkSuppressValue="N";
		this.bypassAdultPwdValue="N";
		this.smsSuppressValue="N";
		this.dmSuppressValue="N";
		this.sipValue="N";
		this.addrProofInd="1";
		this.subTier= null;
		this.docCopyInd="Y";
		this.emailSuppressValue="N";
	}
	
	private String smsLang;
	private String ivrsLang;
	private String adultSuppressValue;
	private String pwd;
	private String pccwSuppressValue;
	private String telemkSuppressValue;
	private String bypassAdultPwdValue;
	
	private String childLockValue;
	

	private String emailSuppressValue;
	private String smsSuppressValue;
	private String dmSuppressValue;
	private String sipValue;
	private String addrProofInd;
	private String addrProofReferrer;
	private String subTier;
	private String docCopyInd;
	private String actUsrSameAsCustInd;
	private String orderId;
	
	private String privacyInd; //add by  20130321
	private Date privacyStampDate;

	public String getPrivacyInd() {
		return privacyInd;
	}

	public Date getPrivacyStampDate() {
		return privacyStampDate;
	}

	public void setPrivacyInd(String privacyInd) {
		this.privacyInd = privacyInd;
	}

	public void setPrivacyStampDate(Date privacyStampDate) {
		this.privacyStampDate = privacyStampDate;
	}
	
	public String getSmsLang() {
		return smsLang;
	}
	public void setSmsLang(String smsLang) {
		this.smsLang = smsLang;
	}
	public String getIvrsLang() {
		return ivrsLang;
	}
	public void setIvrsLang(String ivrsLang) {
		this.ivrsLang = ivrsLang;
	}
	public String getAdultSuppressValue() {
		return adultSuppressValue;
	}
	public void setAdultSuppressValue(String adultSuppressValue) {
		this.adultSuppressValue = adultSuppressValue;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPccwSuppressValue() {
		return pccwSuppressValue;
	}
	public void setPccwSuppressValue(String pccwSuppressValue) {
		this.pccwSuppressValue = pccwSuppressValue;
	}
	public String getTelemkSuppressValue() {
		return telemkSuppressValue;
	}
	public void setTelemkSuppressValue(String telemkSuppressValue) {
		this.telemkSuppressValue = telemkSuppressValue;
	}
	public String getBypassAdultPwdValue() {
		return bypassAdultPwdValue;
	}
	public void setBypassAdultPwdValue(String bypassAdultPwdValue) {
		this.bypassAdultPwdValue = bypassAdultPwdValue;
	}
	public String getSmsSuppressValue() {
		return smsSuppressValue;
	}
	public void setSmsSuppressValue(String smsSuppressValue) {
		this.smsSuppressValue = smsSuppressValue;
	}
	public String getDmSuppressValue() {
		return dmSuppressValue;
	}
	public void setDmSuppressValue(String dmSuppressValue) {
		this.dmSuppressValue = dmSuppressValue;
	}
	public String getSipValue() {
		return sipValue;
	}
	public void setSipValue(String sipValue) {
		this.sipValue = sipValue;
	}
	public String getAddrProofInd() {
		return addrProofInd;
	}
	public void setAddrProofInd(String addrProofInd) {
		this.addrProofInd = addrProofInd;
	}
	public String getSubTier() {
		return subTier;
	}
	public void setSubTier(String subTier) {
		this.subTier = subTier;
	}
	public String getDocCopyInd() {
		return docCopyInd;
	}
	public void setDocCopyInd(String docCopyInd) {
		this.docCopyInd = docCopyInd;
	}
	public String getActUsrSameAsCustInd() {
		return actUsrSameAsCustInd;
	}
	public void setActUsrSameAsCustInd(String actUsrSameAsCustInd) {
		this.actUsrSameAsCustInd = actUsrSameAsCustInd;
	}
	public String getChildLockValue() {
		return childLockValue;
	}
	public void setChildLockValue(String childLockValue) {
		this.childLockValue = childLockValue;
	}
	public String getEmailSuppressValue() {
		return emailSuppressValue;
	}
	public void setEmailSuppressValue(String emailSuppressValue) {
		this.emailSuppressValue = emailSuppressValue;
	}
	public String getAddrProofReferrer() {
		return addrProofReferrer;
	}
	public void setAddrProofReferrer(String addrProofReferrer) {
		this.addrProofReferrer = addrProofReferrer;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId() {
		return orderId;
	}
	
	
	
	
	
	
	
	
}
