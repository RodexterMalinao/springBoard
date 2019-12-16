package com.bomwebportal.lts.dto.form;

import java.io.Serializable;

import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;

public class LtsRecontractFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3749792013406391463L;
	
	private boolean verifyRequried = false;
	private boolean newCustVerified = false;
	private boolean oldCustVerified = false;
	
	private String docType = null;
	private String docId = null;
	private String title = null;
	private String firstName = null;
	private String lastName = null;
	private String companyName = null;
	private String mobileNum = null;
	private String emailAddr = null;
	private String relationship = null;
	private boolean blacklistInd = false;
	private String outstandingAmount = null;
	private boolean optOut= false;
	
	private boolean deceasedCase;
	private String refundType;
	
	private String newBillingName;
	private String newBillingAddress;
	private String baQuickSearch;
	
	private String recontractMode;
	private String updateDnProfile;
	private String existingBillingName;
	
	private String globalCallCardService;
	private String mobileIdd0060Service;
	private String idd0060FixedFeePlan;

	private AccountDetailProfileLtsDTO accountDetailProfile;
	
	private CustomerDetailProfileLtsDTO custDetailProfile = null;
	
	
	private boolean splitAccount;
	
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public boolean isBlacklistInd() {
		return blacklistInd;
	}
	public void setBlacklistInd(boolean blacklistInd) {
		this.blacklistInd = blacklistInd;
	}
	public String getOutstandingAmount() {
		return outstandingAmount;
	}
	public void setOutstandingAmount(String outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}
	public CustomerDetailProfileLtsDTO getCustDetailProfile() {
		return custDetailProfile;
	}
	public void setCustDetailProfile(CustomerDetailProfileLtsDTO custDetailProfile) {
		this.custDetailProfile = custDetailProfile;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getGlobalCallCardService() {
		return globalCallCardService;
	}
	public void setGlobalCallCardService(String globalCallCardService) {
		this.globalCallCardService = globalCallCardService;
	}
	public String getMobileIdd0060Service() {
		return mobileIdd0060Service;
	}
	public void setMobileIdd0060Service(String mobileIdd0060Service) {
		this.mobileIdd0060Service = mobileIdd0060Service;
	}
	public String getIdd0060FixedFeePlan() {
		return idd0060FixedFeePlan;
	}
	public void setIdd0060FixedFeePlan(String idd0060FixedFeePlan) {
		this.idd0060FixedFeePlan = idd0060FixedFeePlan;
	}
	public String getRecontractMode() {
		return recontractMode;
	}
	public void setRecontractMode(String recontractMode) {
		this.recontractMode = recontractMode;
	}
	public AccountDetailProfileLtsDTO getAccountDetailProfile() {
		return accountDetailProfile;
	}
	public void setAccountDetailProfile(AccountDetailProfileLtsDTO accountDetailProfile) {
		this.accountDetailProfile = accountDetailProfile;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isOptOut() {
		return optOut;
	}
	public void setOptOut(boolean optOut) {
		this.optOut = optOut;
	}
	public boolean isDeceasedCase() {
		return deceasedCase;
	}
	public void setDeceasedCase(boolean deceasedCase) {
		this.deceasedCase = deceasedCase;
	}
	public String getNewBillingName() {
		return newBillingName;
	}
	public void setNewBillingName(String newBillingName) {
		this.newBillingName = newBillingName;
	}
	public String getNewBillingAddress() {
		return newBillingAddress;
	}
	public void setNewBillingAddress(String newBillingAddress) {
		this.newBillingAddress = newBillingAddress;
	}
	public String getBaQuickSearch() {
		return baQuickSearch;
	}
	public void setBaQuickSearch(String baQuickSearch) {
		this.baQuickSearch = baQuickSearch;
	}
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	public String getExistingBillingName() {
		return existingBillingName;
	}
	public void setExistingBillingName(String existingBillingName) {
		this.existingBillingName = existingBillingName;
	}
	public boolean isSplitAccount() {
		return splitAccount;
	}
	public void setSplitAccount(boolean splitAccount) {
		this.splitAccount = splitAccount;
	}
	public boolean isNewCustVerified() {
		return newCustVerified;
	}
	public void setNewCustVerified(boolean newCustVerified) {
		this.newCustVerified = newCustVerified;
	}
	public boolean isOldCustVerified() {
		return oldCustVerified;
	}
	public void setOldCustVerified(boolean oldCustVerified) {
		this.oldCustVerified = oldCustVerified;
	}
	public boolean isVerifyRequried() {
		return verifyRequried;
	}
	public void setVerifyRequried(boolean verifyRequried) {
		this.verifyRequried = verifyRequried;
	}
	public String getUpdateDnProfile() {
		return updateDnProfile;
	}
	public void setUpdateDnProfile(String updateDnProfile) {
		this.updateDnProfile = updateDnProfile;
	}
}
