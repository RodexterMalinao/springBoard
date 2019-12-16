package com.bomwebportal.ims.dto.ui;

import java.util.List;

public class ImsInstallationUI extends CustomerUI{
	
	//steven 20130711 start
	private String hasBBDailUp;
	public void setHasBBDailUp(String hasBBDailUp) {
		this.hasBBDailUp = hasBBDailUp;
	}
	public String getHasBBDailUp() {
		return hasBBDailUp;
	}
	//steven 20130711 end
	
	private String dobStr;
	private String fixedLineNo;
	private String displayDeliveryNo;
	private String loginId;
	private String hintOne;
	private String hintSecond;
	private String hintThird;
	private String orderActionInd;
	private String orderStatus;
	private String approvalRequested;

	private String ActionInd;

	private InstallAddrUI installAddress;
	private CustAddrUI billingAddress;
	private AddrInventoryUI addrInventory;
	private String pccwCheck;
	private String billingQuickSearch; //String billingQuickSearch
	private String noBillingAddress;
	private String nowTvFormType;
	private String blacklistCustInfo;
	
	private String isPT;
	private String isCC;
	private String isDS; //added by Andy
	private String contactPersonName;
	
	private String createCOrderInd;
	private String relatedFSA;
	private String multiFSAInd;
	
	private String isFromBOM; //Tony
	private String optInInd;
	
	// martin - NOWTVSALES
	private String contactEmail;
	private String sReasonCd;
	private String submitTag;
	private String previewAdultContent;
	private String isAdultChannelSelected;
	// martin, 20180613, BOM2018062
	private String isStaffOffer;
	private String regStaffNum;
	
	private String waived4KService;

	// Frank - NowTVSales Retention third party
	private String isThirdPartyApplication;
	private String applicantTitle;
	private String applicantLastName;
	private String applicantFirstName;
	private String applicantDocType;
	private String applicantIDNum;
	private String applicantIDVerifiedInd;
	private String relationshipwithCustomer;
	private String otherRelationship;
	private String applicantContactNo;
	private String proRataRefund;
	private String waivePenalty;
	private List<String> waiveReasonList;
	private String	selectedWaivedReason;
	
	private String mode;
	private String isPreReged;
	
	public InstallAddrUI getInstallAddress() {
		return installAddress;
	}

	public void setInstallAddress(InstallAddrUI installAddress) {
		this.installAddress = installAddress;
	}

	public CustAddrUI getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(CustAddrUI billingAddress) {
		this.billingAddress = billingAddress;
	}

	public AddrInventoryUI getAddrInventory() {
		return addrInventory;
	}

	public void setAddrInventory(AddrInventoryUI addrInventory) {
		this.addrInventory = addrInventory;
	}

	public String getActionInd() {
		return ActionInd;
	}

	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}

	public String getBlacklistCustInfo() {
		return blacklistCustInfo;
	}

	public void setBlacklistCustInfo(String blacklistCustInfo) {
		this.blacklistCustInfo = blacklistCustInfo;
	}

	public String getNowTvFormType() {
		return nowTvFormType;
	}

	public void setNowTvFormType(String nowTvFormType) {
		this.nowTvFormType = nowTvFormType;
	}

	public String getHintOne() {
		return hintOne;
	}

	public void setHintOne(String hintOne) {
		this.hintOne = hintOne;
	}

	public String getHintSecond() {
		return hintSecond;
	}

	public void setHintSecond(String hintSecond) {
		this.hintSecond = hintSecond;
	}

	public String getHintThird() {
		return hintThird;
	}

	public void setHintThird(String hintThird) {
		this.hintThird = hintThird;
	}

	public String getOrderActionInd() {
		return orderActionInd;
	}

	public void setOrderActionInd(String orderActionInd) {
		this.orderActionInd = orderActionInd;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getApprovalRequested() {
		return approvalRequested;
	}

	public void setApprovalRequested(String approvalRequested) {
		this.approvalRequested = approvalRequested;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getFixedLineNo() {
		return fixedLineNo;
	}

	public void setFixedLineNo(String fixedLineNo) {
		this.fixedLineNo = fixedLineNo;
	}

	public String getDisplayDeliveryNo() {
		return displayDeliveryNo;
	}
	public void setDisplayDeliveryNo(String displayDeliveryNo) {
		this.displayDeliveryNo = displayDeliveryNo;
	}
	public String getDobStr() {
		return dobStr;
	}

	public void setDobStr(String dobStr) {
		this.dobStr = dobStr;
	}

	public String getPccwCheck() {
		return pccwCheck;
	}

	public void setPccwCheck(String pccwCheck) {
		this.pccwCheck = pccwCheck;
	}

	public String getBillingQuickSearch() {
		return billingQuickSearch;
	}

	public void setBillingQuickSearch(String billingQuickSearch) {
		this.billingQuickSearch = billingQuickSearch;
	}

	public String getNoBillingAddress() {
		return noBillingAddress;
	}

	public void setNoBillingAddress(String noBillingAddress) {
		this.noBillingAddress = noBillingAddress;
	}
	public void setIsPT(String isPT) {
		this.isPT = isPT;
	}
	public String getIsPT() {
		return isPT;
	}
	public void setIsCC(String isCC) {
		this.isCC = isCC;
	}
	public String getIsCC() {
		return isCC;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setCreateCOrderInd(String createCOrderInd) {
		this.createCOrderInd = createCOrderInd;
	}
	public String getCreateCOrderInd() {
		return createCOrderInd;
	}
	public void setRelatedFSA(String relatedFSA) {
		this.relatedFSA = relatedFSA;
	}
	public String getRelatedFSA() {
		return relatedFSA;
	}
	public void setIsFromBOM(String isFromBOM) {
		this.isFromBOM = isFromBOM;
	}
	public String getIsFromBOM() {
		return isFromBOM;
	}
	public void setIsDS(String isDS) {
		this.isDS = isDS;
	}
	public String getIsDS() {
		return isDS;
	}
	public void setOptInInd(String optInInd) {
		this.optInInd = optInInd;
	}
	public String getOptInInd() {
		return optInInd;
	}
	
	
	private String isValidForNowId;
	private String existingLoginId;
	private String IsChangeLoginId;
	public String getIsValidForNowId() {
		return isValidForNowId;
	}
	public void setIsValidForNowId(String isValidForNowId) {
		this.isValidForNowId = isValidForNowId;
	}
	public String getExistingLoginId() {
		return existingLoginId;
	}
	public void setExistingLoginId(String existingLoginId) {
		this.existingLoginId = existingLoginId;
	}
	public String getIsChangeLoginId() {
		return IsChangeLoginId;
	}
	public void setIsChangeLoginId(String isChangeLoginId) {
		IsChangeLoginId = isChangeLoginId;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getsReasonCd() {
		return sReasonCd;
	}
	public void setsReasonCd(String sReasonCd) {
		this.sReasonCd = sReasonCd;
	}
	public String getSubmitTag() {
		return submitTag;
	}
	public void setSubmitTag(String submitTag) {
		this.submitTag = submitTag;
	}
	public String getPreviewAdultContent() {
		return previewAdultContent;
	}
	public void setPreviewAdultContent(String previewAdultContent) {
		this.previewAdultContent = previewAdultContent;
	}
	public String getIsAdultChannelSelected() {
		return isAdultChannelSelected;
	}
	public void setIsAdultChannelSelected(String isAdultChannelSelected) {
		this.isAdultChannelSelected = isAdultChannelSelected;
	}
	public String getWaived4KService() {
		return waived4KService;
	}
	public void setWaived4KService(String waived4kService) {
		waived4KService = waived4kService;
	}
	public String getIsThirdPartyApplication() {
		return isThirdPartyApplication;
	}
	public void setIsThirdPartyApplication(String isThirdPartyApplication) {
		this.isThirdPartyApplication = isThirdPartyApplication;
	}
	public String getApplicantTitle() {
		return applicantTitle;
	}
	public void setApplicantTitle(String applicantTitle) {
		this.applicantTitle = applicantTitle;
	}
	public String getApplicantLastName() {
		return applicantLastName;
	}
	public void setApplicantLastName(String applicantLastName) {
		this.applicantLastName = applicantLastName;
	}
	public String getApplicantFirstName() {
		return applicantFirstName;
	}
	public void setApplicantFirstName(String applicantFirstName) {
		this.applicantFirstName = applicantFirstName;
	}
	public String getApplicantDocType() {
		return applicantDocType;
	}
	public void setApplicantDocType(String applicantDocType) {
		this.applicantDocType = applicantDocType;
	}
	public String getApplicantIDNum() {
		return applicantIDNum;
	}
	public void setApplicantIDNum(String applicantIDNum) {
		this.applicantIDNum = applicantIDNum;
	}
	public String getApplicantIDVerifiedInd() {
		return applicantIDVerifiedInd;
	}
	public void setApplicantIDVerifiedInd(String applicantIDVerifiedInd) {
		this.applicantIDVerifiedInd = applicantIDVerifiedInd;
	}
	public String getRelationshipwithCustomer() {
		return relationshipwithCustomer;
	}
	public void setRelationshipwithCustomer(String relationshipwithCustomer) {
		this.relationshipwithCustomer = relationshipwithCustomer;
	}
	public String getOtherRelationship() {
		return otherRelationship;
	}
	public void setOtherRelationship(String otherRelationship) {
		this.otherRelationship = otherRelationship;
	}
	public String getApplicantContactNo() {
		return applicantContactNo;
	}
	public void setApplicantContactNo(String applicantContactNo) {
		this.applicantContactNo = applicantContactNo;
	}
	public String getProRataRefund() {
		return proRataRefund;
	}
	public void setProRataRefund(String proRataRefund) {
		this.proRataRefund = proRataRefund;
	}
	public String getWaivePenalty() {
		return waivePenalty;
	}
	public void setWaivePenalty(String waivePenalty) {
		this.waivePenalty = waivePenalty;
	}
	public List<String> getWaiveReasonList() {
		return waiveReasonList;
	}
	public void setWaiveReasonList(List<String> waiveReasonList) {
		this.waiveReasonList = waiveReasonList;
	}
	public String getSelectedWaivedReason() {
		return selectedWaivedReason;
	}
	public void setSelectedWaivedReason(String selectedWaivedReason) {
		this.selectedWaivedReason = selectedWaivedReason;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getMode() {
		return mode;
	}
	public void setIsPreReged(String isPreReged) {
		this.isPreReged = isPreReged;
	}
	public String getIsPreReged() {
		return isPreReged;
	}
	public String getIsStaffOffer() {
		return isStaffOffer;
	}
	public void setIsStaffOffer(String isStaffOffer) {
		this.isStaffOffer = isStaffOffer;
	}
	public String getRegStaffNum() {
		return regStaffNum;
	}
	public void setRegStaffNum(String regStaffNum) {
		this.regStaffNum = regStaffNum;
	}
	public void setMultiFSAInd(String multiFSAInd) {
		this.multiFSAInd = multiFSAInd;
	}
	public String getMultiFSAInd() {
		return multiFSAInd;
	}

	
}