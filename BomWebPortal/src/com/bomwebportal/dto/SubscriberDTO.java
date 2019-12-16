package com.bomwebportal.dto;

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
		this.suppressLocalTopUpInd="N";
		this.suppressRoamTopUpInd="N";
		this.mob0060OptOutInd="N";
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
	private String suppressLocalTopUpInd;  //20130827
	private String suppressRoamTopUpInd;
	private String mob0060OptOutInd;
	
	//CSL origActDate 
	private Date origActDate;
	
	private String activationCd;
	private String brand;
	
	private String pcrfAlertEmail;
	private String sameAsEbillAddrInd;
	private String smsOptOutFirstRoam;
	private String smsOptOutRoamHu;
	private String pcrfMupAlert;
	private String pcrfSmsNum;
	private String pcrfSmsRecipient;
	private String pcrfAlertType;
	
	private String secSrvNum;
	private String oldSecSrvNum;
	
	public Date getOrigActDate() {
		return origActDate;
	}

	public void setOrigActDate(Date origActDate) {
		this.origActDate = origActDate;
	}
	
	public String getSuppressLocalTopUpInd (){
		return suppressLocalTopUpInd;
	}
	
	public void setSuppressLocalTopUpInd(String suppressLocalTopUpInd){
		this.suppressLocalTopUpInd = suppressLocalTopUpInd;
	}
	
	public String getSuppressRoamTopUpInd (){
		return suppressRoamTopUpInd;
	}
	
	public void setSuppressRoamTopUpInd (String suppressRoamTopUpInd){
		this.suppressRoamTopUpInd = suppressRoamTopUpInd;
	}
	
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

	public String getMob0060OptOutInd() {
		return mob0060OptOutInd;
	}

	public void setMob0060OptOutInd(String mob0060OptOutInd) {
		this.mob0060OptOutInd = mob0060OptOutInd;
	}

	public String getActivationCd() {
		return activationCd;
	}

	public void setActivationCd(String activationCd) {
		this.activationCd = activationCd;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPcrfAlertEmail() {
		return pcrfAlertEmail;
	}

	public void setPcrfAlertEmail(String pcrfAlertEmail) {
		this.pcrfAlertEmail = pcrfAlertEmail;
	}

	public String getSameAsEbillAddrInd() {
		return sameAsEbillAddrInd;
	}

	public void setSameAsEbillAddrInd(String sameAsEbillAddrInd) {
		this.sameAsEbillAddrInd = sameAsEbillAddrInd;
	}

	public String getSmsOptOutFirstRoam() {
		return smsOptOutFirstRoam;
	}

	public void setSmsOptOutFirstRoam(String smsOptOutFirstRoam) {
		this.smsOptOutFirstRoam = smsOptOutFirstRoam;
	}

	public String getSmsOptOutRoamHu() {
		return smsOptOutRoamHu;
	}

	public void setSmsOptOutRoamHu(String smsOptOutRoamHu) {
		this.smsOptOutRoamHu = smsOptOutRoamHu;
	}

	public String getPcrfMupAlert() {
		return pcrfMupAlert;
	}

	public void setPcrfMupAlert(String pcrfMupAlert) {
		this.pcrfMupAlert = pcrfMupAlert;
	}

	public String getPcrfSmsNum() {
		return pcrfSmsNum;
	}

	public void setPcrfSmsNum(String pcrfSmsNum) {
		this.pcrfSmsNum = pcrfSmsNum;
	}

	public String getPcrfSmsRecipient() {
		return pcrfSmsRecipient;
	}

	public void setPcrfSmsRecipient(String pcrfSmsRecipient) {
		this.pcrfSmsRecipient = pcrfSmsRecipient;
	}

	public String getPcrfAlertType() {
		return pcrfAlertType;
	}

	public void setPcrfAlertType(String pcrfAlertType) {
		this.pcrfAlertType = pcrfAlertType;
	}

	public String getSecSrvNum() {
		return secSrvNum;
	}

	public void setSecSrvNum(String secSrvNum) {
		this.secSrvNum = secSrvNum;
	}

	public String getOldSecSrvNum() {
		return oldSecSrvNum;
	}

	public void setOldSecSrvNum(String oldSecSrvNum) {
		this.oldSecSrvNum = oldSecSrvNum;
	}	
	
	
}
