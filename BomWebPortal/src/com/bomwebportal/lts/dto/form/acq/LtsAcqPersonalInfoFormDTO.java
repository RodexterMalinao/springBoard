package com.bomwebportal.lts.dto.form.acq;

import java.io.Serializable;

public class LtsAcqPersonalInfoFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -575203981999231537L;
	/**
	 * 
	 */

	/**
	 * 
	 */
	private String documentType;
	private String docNum;
	private boolean verified;
	private String companyName;
	private String title; 
	private String familyName;
	private String givenName;
	private String dateOfBirth;
	private String contactEmail;
	private String mobileNo;
	private String jobName;
	private boolean thirdParty;
	private Boolean thirdPartyInd;
	private boolean dummyDoc;
	private boolean agreement;
	private boolean disable;
	private String thirdPartyTitle;
	private String thirdPartyGivenName;
	private String thirdPartyFamilyName;
	private String thirdPartyDoctype;
	private String thirdPartyDocId;
	private String thirdPartyDateOfBirth;
	private boolean thirdPartyAppIdVerify;
	private String thirdPartyRelationship;
	private String thirdPartyContactNum;
	private boolean createNewCust;
	private String custNum;
	private String oldCustNum;
	private boolean displayPICS;
	
	private boolean secondDelDummyDoc;
	private String secondDelDocNum;
	private boolean secDelDocNumVerified;
	private boolean validDocId;
	private boolean definedCustNum;
	
	private boolean ChannelDs;
	
	private String warningCustomerSpecialRemarkMessage; 
	private String warningCustomerNotMatchMessage; 
	private String wipMsg; 
	
	private String iddStatus;
	private String ltsStatus;
	
	private String fixedLineNo;
	
	private boolean mustVerify;
	
	private String optInOutInd;
	
	private boolean matchWithBomName;
	
	private String basketType;
	
	private boolean allowThirdParty;
	
	private String acctNumNotMatchMsg;
	
	private String alertUpdContactMsg;
	private String oldContactEmail;
	private String oldMobileNo;
	private String alertUpdEmailMsg;
	private String alertUpdMobileNoMsg;
	private String oldContactEmailDate;
	private String oldMobileNoDate;
	private String contactUpdAlert;	
	
	private boolean eligibleDocTypeMatch;
	private String docTypeNotMatchMsg;
	private String eligibleDocType;

	
	public boolean isCreateNewCust() {
		return createNewCust;
	}
	public void setCreateNewCust(boolean createNewCust) {
		this.createNewCust = createNewCust;
	}
	public String getThirdPartyDoctype() {
		return thirdPartyDoctype;
	}
	public void setThirdPartyDoctype(String thirdPartyDoctype) {
		this.thirdPartyDoctype = thirdPartyDoctype;
	}
	public String getThirdPartyDocId() {
		return thirdPartyDocId;
	}
	public void setThirdPartyDocId(String thirdPartyDocId) {
		this.thirdPartyDocId = thirdPartyDocId;
	}
	public boolean isThirdPartyAppIdVerify() {
		return thirdPartyAppIdVerify;
	}
	public void setThirdPartyAppIdVerify(boolean thirdPartyAppIdVerify) {
		this.thirdPartyAppIdVerify = thirdPartyAppIdVerify;
	}
	public String getThirdPartyRelationship() {
		return thirdPartyRelationship;
	}
	public void setThirdPartyRelationship(String thirdPartyRelationship) {
		this.thirdPartyRelationship = thirdPartyRelationship;
	}
	public String getThirdPartyContactNum() {
		return thirdPartyContactNum;
	}
	public void setThirdPartyContactNum(String thirdPartyContactNum) {
		this.thirdPartyContactNum = thirdPartyContactNum;
	}
	
	public String getThirdPartyGivenName() {
		return thirdPartyGivenName;
	}
	public void setThirdPartyGivenName(String thirdPartyGivenName) {
		this.thirdPartyGivenName = thirdPartyGivenName;
	}
	public String getThirdPartyFamilyName() {
		return thirdPartyFamilyName;
	}
	public void setThirdPartyFamilyName(String thirdPartyFamilyName) {
		this.thirdPartyFamilyName = thirdPartyFamilyName;
	}

	
	public String getThirdPartyTitle() {
		return thirdPartyTitle;
	}
	public void setThirdPartyTitle(String thirdPartyTitle) {
		this.thirdPartyTitle = thirdPartyTitle;
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public boolean getVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String compnayName) {
		this.companyName = compnayName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public boolean isThirdParty() {
		return thirdParty;
	}
	public void setThirdParty(boolean thirdParty) {
		this.thirdParty = thirdParty;
	}
	
	public boolean isDummyDoc() {
		return dummyDoc;
	}
	public void setDummyDoc(boolean dummyDoc) {
		this.dummyDoc = dummyDoc;
	}
	public boolean getAgreement() {
		return agreement;
	}
	public void setAgreement(boolean agreement) {
		this.agreement = agreement;
	}
	
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public boolean isDisable() {
		return disable;
	}
	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	public String getCustNum() {
		return custNum;
	}
	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}
	public boolean isSecondDelDummyDoc() {
		return secondDelDummyDoc;
	}
	public void setSecondDelDummyDoc(boolean secondDelDummyDoc) {
		this.secondDelDummyDoc = secondDelDummyDoc;
	}
	public String getSecondDelDocNum() {
		return secondDelDocNum;
	}
	public void setSecondDelDocNum(String secondDelDocNum) {
		this.secondDelDocNum = secondDelDocNum;
	}
	public boolean isSecDelDocNumVerified() {
		return secDelDocNumVerified;
	}
	public void setSecDelDocNumVerified(boolean secDelDocNumVerified) {
		this.secDelDocNumVerified = secDelDocNumVerified;
	}
	public String getWarningCustomerSpecialRemarkMessage() {
		return warningCustomerSpecialRemarkMessage;
	}
	public void setWarningCustomerSpecialRemarkMessage(
			String warningCustomerSpecialRemarkMessage) {
		this.warningCustomerSpecialRemarkMessage = warningCustomerSpecialRemarkMessage;
	}
	public String getWarningCustomerNotMatchMessage() {
		return warningCustomerNotMatchMessage;
	}
	public void setWarningCustomerNotMatchMessage(
			String warningCustomerNotMatchMessage) {
		this.warningCustomerNotMatchMessage = warningCustomerNotMatchMessage;
	}
	public boolean isValidDocId() {
		return validDocId;
	}
	public void setValidDocId(boolean validDocId) {
		this.validDocId = validDocId;
	}
	public boolean isDisplayPICS() {
		return displayPICS;
	}
	public void setDisplayPICS(boolean displayPICS) {
		this.displayPICS = displayPICS;
	}
	public boolean isDefinedCustNum() {
		return definedCustNum;
	}
	public void setDefinedCustNum(boolean definedCustNum) {
		this.definedCustNum = definedCustNum;
	}
	public String getIddStatus() {
		return iddStatus;
	}
	public void setIddStatus(String iddStatus) {
		this.iddStatus = iddStatus;
	}
	public String getLtsStatus() {
		return ltsStatus;
	}
	public void setLtsStatus(String ltsStatus) {
		this.ltsStatus = ltsStatus;
	}
	public String getFixedLineNo() {
		return fixedLineNo;
	}
	public void setFixedLineNo(String fixedLineNo) {
		this.fixedLineNo = fixedLineNo;
	}
	public boolean isMustVerify() {
		return mustVerify;
	}
	public void setMustVerify(boolean mustVerify) {
		this.mustVerify = mustVerify;
	}
	public String getOptInOutInd() {
		return optInOutInd;
	}
	public void setOptInOutInd(String optInOutInd) {
		this.optInOutInd = optInOutInd;
	}
	public Boolean getThirdPartyInd() {
		return thirdPartyInd;
	}
	public void setThirdPartyInd(Boolean thirdPartyInd) {
		this.thirdPartyInd = thirdPartyInd;
	}
	public boolean isChannelDs() {
		return ChannelDs;
	}
	public void setChannelDs(boolean channelDs) {
		ChannelDs = channelDs;
	}
	public String getThirdPartyDateOfBirth() {
		return thirdPartyDateOfBirth;
	}
	public void setThirdPartyDateOfBirth(String thirdPartyDateOfBirth) {
		this.thirdPartyDateOfBirth = thirdPartyDateOfBirth;
	}
	public boolean isMatchWithBomName() {
		return matchWithBomName;
	}
	public void setMatchWithBomName(boolean matchWithBomName) {
		this.matchWithBomName = matchWithBomName;
	}
	public String getBasketType() {
		return basketType;
	}
	public void setBasketType(String basketType) {
		this.basketType = basketType;
	}
	public boolean isAllowThirdParty() {
		return allowThirdParty;
	}
	public void setAllowThirdParty(boolean allowThirdParty) {
		this.allowThirdParty = allowThirdParty;
	}
	public String getWipMsg() {
		return wipMsg;
	}
	public void setWipMsg(String wipMsg) {
		this.wipMsg = wipMsg;
	}
	public String getOldCustNum() {
		return oldCustNum;
	}
	public void setOldCustNum(String oldCustNum) {
		this.oldCustNum = oldCustNum;
	}
	public String getAcctNumNotMatchMsg() {
		return acctNumNotMatchMsg;
	}
	public void setAcctNumNotMatchMsg(String acctNumNotMatchMsg) {
		this.acctNumNotMatchMsg = acctNumNotMatchMsg;
	}
	public String getAlertUpdContactMsg() {
		return alertUpdContactMsg;
	}
	public void setAlertUpdContactMsg(String alertUpdContactMsg) {
		this.alertUpdContactMsg = alertUpdContactMsg;
	}
	public String getOldContactEmail() {
		return oldContactEmail;
	}
	public void setOldContactEmail(String oldContactEmail) {
		this.oldContactEmail = oldContactEmail;
	}
	public String getOldMobileNo() {
		return oldMobileNo;
	}
	public void setOldMobileNo(String oldMobileNo) {
		this.oldMobileNo = oldMobileNo;
	}
	public String getAlertUpdEmailMsg() {
		return alertUpdEmailMsg;
	}
	public void setAlertUpdEmailMsg(String alertUpdEmailMsg) {
		this.alertUpdEmailMsg = alertUpdEmailMsg;
	}
	public String getAlertUpdMobileNoMsg() {
		return alertUpdMobileNoMsg;
	}
	public void setAlertUpdMobileNoMsg(String alertUpdMobileNoMsg) {
		this.alertUpdMobileNoMsg = alertUpdMobileNoMsg;
	}
	public String getOldContactEmailDate() {
		return oldContactEmailDate;
	}
	public void setOldContactEmailDate(String oldContactEmailDate) {
		this.oldContactEmailDate = oldContactEmailDate;
	}
	public String getOldMobileNoDate() {
		return oldMobileNoDate;
	}
	public void setOldMobileNoDate(String oldMobileNoDate) {
		this.oldMobileNoDate = oldMobileNoDate;
	}
	public String getContactUpdAlert() {
		return contactUpdAlert;
	}
	public void setContactUpdAlert(String contactUpdAlert) {
		this.contactUpdAlert = contactUpdAlert;
	}
	public boolean isEligibleDocTypeMatch() {
		return eligibleDocTypeMatch;
	}
	public void setEligibleDocTypeMatch(boolean eligibleDocTypeMatch) {
		this.eligibleDocTypeMatch = eligibleDocTypeMatch;
	}
	public String getDocTypeNotMatchMsg() {
		return docTypeNotMatchMsg;
	}
	public void setDocTypeNotMatchMsg(String docTypeNotMatchMsg) {
		this.docTypeNotMatchMsg = docTypeNotMatchMsg;
	}
	public String getEligibleDocType() {
		return eligibleDocType;
	}
	public void setEligibleDocType(String eligibleDocType) {
		this.eligibleDocType = eligibleDocType;
	}			

}
