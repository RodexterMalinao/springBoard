package com.bomwebportal.lts.dto.form;

import java.io.Serializable;

public class LtsCustomerIdentificationFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5075238007900475469L;
	
	//dummy
	private String dummyDocType;
	private String dummyId;
	private String secDelDummyDocType;
	private String secDelDummyId;
	
	//identification
	private String docType;
	private String id;
	private boolean verified;
	private boolean mustVerify;
	private String dateOfBirth;
	private String dobBom;
	
	//3rd party application
	private String applicantTitle;
	private String applicantFirstName;
	private String applicantLastName;
	private String applicantDocType;
	private String applicantId;
	private boolean applicantVerified;
	private String relationship;
	private String contactNum;

	//2nd DEL
	private String secDelDocType;
	private String secDelId;
	private boolean secDelVerified;
	
	 //2nd DEL 3rd party application
	private String secDelApplicantTitle;
	private String secDelApplicantFirstName;
	private String secDelApplicantLastName;	
	private String secDelApplicantDocType;	
	private String secDelApplicantId;	
	private boolean secDelApplicantVerified;
	private String secDelRelationship;
	private String secDelContactNum;

	//indicator
	private boolean dummy;
	private boolean secDelDummy;
	private boolean thirdPartyApplication;	
	private boolean secDelThirdPartyApplication;
	private boolean createSecDel;
	
	// LTS PDPO
	private String existLtsPdpoStatus;
	private String newLtsPdpoStatus;
	private boolean ltsPdpoDirectMailing;
	private boolean ltsPdpoOutbound;
	private boolean ltsPdpoSms;
	private boolean ltsPdpoEmail;
	private boolean ltsPdpoWebBill;
	private boolean ltsPdpoBillInsert;
	private boolean ltsPdpoCdOutdial;
	private boolean ltsPdpoBm;
	private boolean ltsPdpoSmsEye;
	private boolean ltsPdpoOptOutAll;
	
	private String customerContactEmail;
	private String customerContactMobileNum;
	private String customerContactFixLineNum;
	private String customerOrgContactEmail;
	private String customerOrgContactEmailDate;
	private String customerOrgContactMobileNum;
	private String customerOrgContactMobileNumDate;
	private boolean contactEmailOverDue;
	private boolean contactMobileNumOverDue;
	private String updateContactMsgPrompted = "N";
	
	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isMustVerify() {
		return mustVerify;
	}

	public void setMustVerify(boolean mustVerify) {
		this.mustVerify = mustVerify;
	}

	public boolean isApplicantVerified() {
		return applicantVerified;
	}

	public void setApplicantVerified(boolean applicantVerified) {
		this.applicantVerified = applicantVerified;
	}

	public boolean isSecDelApplicantVerified() {
		return secDelApplicantVerified;
	}

	public void setSecDelApplicantVerified(boolean secDelApplicantVerified) {
		this.secDelApplicantVerified = secDelApplicantVerified;
	}

	//gets and sets
	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplicantDocType() {
		return applicantDocType;
	}

	public void setApplicantDocType(String applicantDocType) {
		this.applicantDocType = applicantDocType;
	}

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getSecDelApplicantDocType() {
		return secDelApplicantDocType;
	}

	public void setSecDelApplicantDocType(String secDelApplicantDocType) {
		this.secDelApplicantDocType = secDelApplicantDocType;
	}

	public String getSecDelApplicantId() {
		return secDelApplicantId;
	}

	public void setSecDelApplicantId(String secDelApplicantId) {
		this.secDelApplicantId = secDelApplicantId;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isThirdPartyApplication() {
		return thirdPartyApplication;
	}

	public void setThirdPartyApplication(boolean thirdPartyApplication) {
		this.thirdPartyApplication = thirdPartyApplication;
	}

	public boolean isSecDelThirdPartyApplication() {
		return secDelThirdPartyApplication;
	}

	public void setSecDelThirdPartyApplication(boolean secDelThirdPartyApplication) {
		this.secDelThirdPartyApplication = secDelThirdPartyApplication;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public boolean isCreateSecDel() {
		return createSecDel;
	}

	public void setCreateSecDel(boolean createSecDel) {
		this.createSecDel = createSecDel;
	}

	public String getSecDelDocType() {
		return secDelDocType;
	}

	public void setSecDelDocType(String secDelDocType) {
		this.secDelDocType = secDelDocType;
	}

	public String getSecDelId() {
		return secDelId;
	}

	public void setSecDelId(String secDelId) {
		this.secDelId = secDelId;
	}

	public boolean isSecDelVerified() {
		return secDelVerified;
	}

	public void setSecDelVerified(boolean secDelVerified) {
		this.secDelVerified = secDelVerified;
	}

	public String getApplicantFirstName() {
		return applicantFirstName;
	}

	public void setApplicantFirstName(String applicantFirstName) {
		this.applicantFirstName = applicantFirstName;
	}

	public String getApplicantLastName() {
		return applicantLastName;
	}

	public void setApplicantLastName(String applicantLastName) {
		this.applicantLastName = applicantLastName;
	}

	public String getSecDelApplicantFirstName() {
		return secDelApplicantFirstName;
	}

	public void setSecDelApplicantFirstName(String secDelApplicantFirstName) {
		this.secDelApplicantFirstName = secDelApplicantFirstName;
	}

	public String getSecDelApplicantLastName() {
		return secDelApplicantLastName;
	}

	public void setSecDelApplicantLastName(String secDelApplicantLastName) {
		this.secDelApplicantLastName = secDelApplicantLastName;
	}

	public String getSecDelRelationship() {
		return secDelRelationship;
	}

	public void setSecDelRelationship(String secDelRelationship) {
		this.secDelRelationship = secDelRelationship;
	}

	public String getSecDelContactNum() {
		return secDelContactNum;
	}

	public void setSecDelContactNum(String secDelContactNum) {
		this.secDelContactNum = secDelContactNum;
	}

	public String getDummyDocType() {
		return dummyDocType;
	}

	public void setDummyDocType(String dummyDocType) {
		this.dummyDocType = dummyDocType;
	}

	public String getDummyId() {
		return dummyId;
	}

	public void setDummyId(String dummyId) {
		this.dummyId = dummyId;
	}

	public boolean isDummy() {
		return dummy;
	}

	public void setDummy(boolean dummy) {
		this.dummy = dummy;
	}

	public boolean isSecDelDummy() {
		return secDelDummy;
	}

	public void setSecDelDummy(boolean secDelDummy) {
		this.secDelDummy = secDelDummy;
	}
	public String getSecDelDummyDocType() {
		return secDelDummyDocType;
	}

	public void setSecDelDummyDocType(String secDelDummyDocType) {
		this.secDelDummyDocType = secDelDummyDocType;
	}

	public String getSecDelDummyId() {
		return secDelDummyId;
	}

	public void setSecDelDummyId(String secDelDummyId) {
		this.secDelDummyId = secDelDummyId;
	}
	
	public String getApplicantTitle() {
		return applicantTitle;
	}

	public void setApplicantTitle(String applicantTitle) {
		this.applicantTitle = applicantTitle;
	}

	public String getSecDelApplicantTitle() {
		return secDelApplicantTitle;
	}

	public void setSecDelApplicantTitle(String secDelApplicantTitle) {
		this.secDelApplicantTitle = secDelApplicantTitle;
	}

	public String getDobBom() {
		return dobBom;
	}

	public void setDobBom(String dobBom) {
		this.dobBom = dobBom;
	}

	public String getExistLtsPdpoStatus() {
		return existLtsPdpoStatus;
	}

	public void setExistLtsPdpoStatus(String existLtsPdpoStatus) {
		this.existLtsPdpoStatus = existLtsPdpoStatus;
	}

	public String getNewLtsPdpoStatus() {
		return newLtsPdpoStatus;
	}

	public void setNewLtsPdpoStatus(String newLtsPdpoStatus) {
		this.newLtsPdpoStatus = newLtsPdpoStatus;
	}

	public boolean isLtsPdpoDirectMailing() {
		return ltsPdpoDirectMailing;
	}

	public void setLtsPdpoDirectMailing(boolean ltsPdpoDirectMailing) {
		this.ltsPdpoDirectMailing = ltsPdpoDirectMailing;
	}

	public boolean isLtsPdpoOutbound() {
		return ltsPdpoOutbound;
	}

	public void setLtsPdpoOutbound(boolean ltsPdpoOutbound) {
		this.ltsPdpoOutbound = ltsPdpoOutbound;
	}

	public boolean isLtsPdpoSms() {
		return ltsPdpoSms;
	}

	public void setLtsPdpoSms(boolean ltsPdpoSms) {
		this.ltsPdpoSms = ltsPdpoSms;
	}

	public boolean isLtsPdpoEmail() {
		return ltsPdpoEmail;
	}

	public void setLtsPdpoEmail(boolean ltsPdpoEmail) {
		this.ltsPdpoEmail = ltsPdpoEmail;
	}

	public boolean isLtsPdpoWebBill() {
		return ltsPdpoWebBill;
	}

	public void setLtsPdpoWebBill(boolean ltsPdpoWebBill) {
		this.ltsPdpoWebBill = ltsPdpoWebBill;
	}

	public boolean isLtsPdpoBillInsert() {
		return ltsPdpoBillInsert;
	}

	public void setLtsPdpoBillInsert(boolean ltsPdpoBillInsert) {
		this.ltsPdpoBillInsert = ltsPdpoBillInsert;
	}

	public boolean isLtsPdpoCdOutdial() {
		return ltsPdpoCdOutdial;
	}

	public void setLtsPdpoCdOutdial(boolean ltsPdpoCdOutdial) {
		this.ltsPdpoCdOutdial = ltsPdpoCdOutdial;
	}

	public boolean isLtsPdpoBm() {
		return ltsPdpoBm;
	}

	public void setLtsPdpoBm(boolean ltsPdpoBm) {
		this.ltsPdpoBm = ltsPdpoBm;
	}

	public boolean isLtsPdpoSmsEye() {
		return ltsPdpoSmsEye;
	}

	public void setLtsPdpoSmsEye(boolean ltsPdpoSmsEye) {
		this.ltsPdpoSmsEye = ltsPdpoSmsEye;
	}

	public boolean isLtsPdpoOptOutAll() {
		return ltsPdpoOptOutAll;
	}

	public void setLtsPdpoOptOutAll(boolean ltsPdpoOptOutAll) {
		this.ltsPdpoOptOutAll = ltsPdpoOptOutAll;
	}

	public String getCustomerContactEmail() {
		return customerContactEmail;
	}

	public void setCustomerContactEmail(String customerContactEmail) {
		this.customerContactEmail = customerContactEmail;
	}

	public String getCustomerContactMobileNum() {
		return customerContactMobileNum;
	}

	public void setCustomerContactMobileNum(String customerContactMobileNum) {
		this.customerContactMobileNum = customerContactMobileNum;
	}

	public String getCustomerContactFixLineNum() {
		return customerContactFixLineNum;
	}

	public void setCustomerContactFixLineNum(String customerContactFixLineNum) {
		this.customerContactFixLineNum = customerContactFixLineNum;
	}

	public boolean isContactEmailOverDue() {
		return contactEmailOverDue;
	}

	public void setContactEmailOverDue(boolean contactEmailOverDue) {
		this.contactEmailOverDue = contactEmailOverDue;
	}

	public boolean isContactMobileNumOverDue() {
		return contactMobileNumOverDue;
	}

	public void setContactMobileNumOverDue(boolean contactMobileNumOverDue) {
		this.contactMobileNumOverDue = contactMobileNumOverDue;
	}
	
	public String getCustomerOrgContactEmail() {
		return customerOrgContactEmail;
	}

	public void setCustomerOrgContactEmail(String customerOrgContactEmail) {
		this.customerOrgContactEmail = customerOrgContactEmail;
	}

	public String getCustomerOrgContactMobileNum() {
		return customerOrgContactMobileNum;
	}

	public void setCustomerOrgContactMobileNum(String customerOrgContactMobileNum) {
		this.customerOrgContactMobileNum = customerOrgContactMobileNum;
	}

	public String getUpdateContactMsgPrompted() {
		return updateContactMsgPrompted;
	}

	public void setUpdateContactMsgPrompted(String updateContactMsgPrompted) {
		this.updateContactMsgPrompted = updateContactMsgPrompted;
	}

	public String getCustomerOrgContactEmailDate() {
		return customerOrgContactEmailDate;
	}

	public void setCustomerOrgContactEmailDate(String customerOrgContactEmailDate) {
		this.customerOrgContactEmailDate = customerOrgContactEmailDate;
	}

	public String getCustomerOrgContactMobileNumDate() {
		return customerOrgContactMobileNumDate;
	}

	public void setCustomerOrgContactMobileNumDate(
			String customerOrgContactMobileNumDate) {
		this.customerOrgContactMobileNumDate = customerOrgContactMobileNumDate;
	}

}
