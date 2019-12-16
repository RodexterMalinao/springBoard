package com.bomltsportal.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ItemDetailDTO;

public class SummaryFormDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7300148582040104182L;

	private boolean paymentFailed = false;
	
	//basket item
	private List<ItemDetailDTO> vasItemList;
	private List<ItemDetailDTO> premiumItemList;
	private List<ItemDetailDTO> contentItemList;
	private List<ItemDetailDTO> nowTvItemList;
	private List<String> nowTvDescList;
	private ItemDetailDTO planItem;
	
	private boolean showReminder = true;
	
	//applicant information
	private String title;
	private String familyName;
	private String givenName;
	private String docType;
	private String docNum;
	private String dateOfBirth;
	private String contactMobileNum;
	private String contactEmailAddr;
	private String srvNum;
	
	//appointment
	private String installDate;
	private String installTime;
	
	//weee
	private List<ItemDetailDTO> epdItemList;
	
	//bill
	private String billMethod;
	private String billLang;
	
	//payment
	private String payMtdType;
	private int paymentAmount;
	private boolean noPay;
	
	//address
	private String address;
	
	private boolean showExDirStatement;
	private boolean showCsPoralStatement;
	private boolean showCsPoralTheClubStatement;
	private boolean showTheClubStatement;
	private boolean showPrivacyStatement;

	public boolean isPaymentFailed() {
		return paymentFailed;
	}

	public void setPaymentFailed(boolean paymentFailed) {
		this.paymentFailed = paymentFailed;
	}

	public List<ItemDetailDTO> getVasItemList() {
		return vasItemList;
	}

	public List<ItemDetailDTO> getPremiumItemList() {
		return premiumItemList;
	}

	public List<ItemDetailDTO> getContentItemList() {
		return contentItemList;
	}

	public ItemDetailDTO getPlanItem() {
		return planItem;
	}

	public void setVasItemList(List<ItemDetailDTO> vasItemList) {
		this.vasItemList = vasItemList;
	}

	public void setPremiumItemList(List<ItemDetailDTO> premiumItemList) {
		this.premiumItemList = premiumItemList;
	}

	public void setContentItemList(List<ItemDetailDTO> contentItemList) {
		this.contentItemList = contentItemList;
	}

	public void setPlanItem(ItemDetailDTO planItem) {
		this.planItem = planItem;
	}

	public boolean isShowReminder() {
		return showReminder;
	}

	public void setShowReminder(boolean showReminder) {
		this.showReminder = showReminder;
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getContactMobileNum() {
		return contactMobileNum;
	}

	public void setContactMobileNum(String contactMobileNum) {
		this.contactMobileNum = contactMobileNum;
	}

	public String getContactEmailAddr() {
		return contactEmailAddr;
	}

	public void setContactEmailAddr(String contactEmailAddr) {
		this.contactEmailAddr = contactEmailAddr;
	}

	public String getSrvNum() {
		return srvNum;
	}

	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}

	public String getInstallDate() {
		return installDate;
	}

	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}

	public String getInstallTime() {
		return installTime;
	}

	public void setInstallTime(String installTime) {
		this.installTime = installTime;
	}

	public List<ItemDetailDTO> getEpdItemList() {
		return epdItemList;
	}

	public void setEpdItemList(List<ItemDetailDTO> epdItemList) {
		this.epdItemList = epdItemList;
	}

	public String getBillMethod() {
		return billMethod;
	}

	public void setBillMethod(String billMethod) {
		this.billMethod = billMethod;
	}

	public String getBillLang() {
		return billLang;
	}

	public void setBillLang(String billLang) {
		this.billLang = billLang;
	}

	public String getPayMtdType() {
		return payMtdType;
	}

	public void setPayMtdType(String payMtdType) {
		this.payMtdType = payMtdType;
	}

	public int getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(int paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getNowTvDescList() {
		return nowTvDescList;
	}

	public void setNowTvDescList(List<String> nowTvDescList) {
		this.nowTvDescList = nowTvDescList;
	}

	public List<ItemDetailDTO> getNowTvItemList() {
		return nowTvItemList;
	}

	public void setNowTvItemList(List<ItemDetailDTO> nowTvItemList) {
		this.nowTvItemList = nowTvItemList;
	}

	public boolean isShowExDirStatement() {
		return showExDirStatement;
	}

	public void setShowExDirStatement(boolean showExDirStatement) {
		this.showExDirStatement = showExDirStatement;
	}

	public boolean isShowCsPoralStatement() {
		return showCsPoralStatement;
	}

	public void setShowCsPoralStatement(boolean showCsPoralStatement) {
		this.showCsPoralStatement = showCsPoralStatement;
	}

	public boolean isShowPrivacyStatement() {
		return showPrivacyStatement;
	}

	public void setShowPrivacyStatement(boolean showPrivacyStatement) {
		this.showPrivacyStatement = showPrivacyStatement;
	}

	public boolean isNoPay() {
		return noPay;
	}

	public void setNoPay(boolean noPay) {
		this.noPay = noPay;
	}

	public boolean isShowCsPoralTheClubStatement() {
		return showCsPoralTheClubStatement;
	}

	public void setShowCsPoralTheClubStatement(boolean showCsPoralTheClubStatement) {
		this.showCsPoralTheClubStatement = showCsPoralTheClubStatement;
	}

	public boolean isShowTheClubStatement() {
		return showTheClubStatement;
	}

	public void setShowTheClubStatement(boolean showTheClubStatement) {
		this.showTheClubStatement = showTheClubStatement;
	}



}
