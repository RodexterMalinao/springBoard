package com.bomltsportal.dto.form;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomltsportal.dto.SrdDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;

public class ApplicantInfoFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -570178918467626139L;

	//applicant information
	private String title;
	private String familyName;
	private String givenName;
	private String docType;
	private String docNum;
	private String dateOfBirth;
	private String contactMobileNum;
	private String contactEmailAddr;
	private boolean exDirectoryConfirm;
	private boolean csPortalConfirm;
	private boolean csPortalTheClubConfirm;
	private boolean	theClubConfirm;
	private boolean csPortalOptIn;
	private boolean csPortalTheClubOptIn;
	private boolean	theClubOptIn;
	
	//customer profile info
	private boolean custExist;
	private boolean custNameMatch;
	private boolean wip;
	private boolean shCust;
	
	//service number
	private Boolean newNum;
	private List<String> displayNewNum;
	private String selectedNum;
	private String importNum;
	
	//appointment
	private boolean tentative;
	private SrdDTO earliestSrd;
	private String installDate;
	private String installTimeAndType;
	private String installTime;
	private String installTimeType;
	private String prebookSerialNum;
	
	//weee
	private List<ItemDetailDTO> epdItemList;
	private String weeeOptions;

	//bill
	private List<ItemDetailDTO> billMethodItemList;
	private String billMethod;
	private String billLang;
	
	//payment
	private String payMtdType;
	private int payAmount;
	private List<ItemDetailDTO> prepayItemList;
	private String tenureCode;
	private Boolean prepayInd;
	
	private boolean privacyR;
	
	//==PDPO==
	private boolean showPdpoStatement;

	//getters and setters
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

	public boolean isCustExist() {
		return custExist;
	}

	public boolean isCustNameMatch() {
		return custNameMatch;
	}

	public void setCustExist(boolean custExist) {
		this.custExist = custExist;
	}

	public void setCustNameMatch(boolean custNameMatch) {
		this.custNameMatch = custNameMatch;
	}

	public Boolean getNewNum() {
		return newNum;
	}

	public void setNewNum(Boolean newNum) {
		this.newNum = newNum;
	}

	public List<String> getDisplayNewNum() {
		return displayNewNum;
	}

	public void setDisplayNewNum(List<String> displayNewNum) {
		this.displayNewNum = displayNewNum;
	}

	public String getSelectedNum() {
		return selectedNum;
	}

	public void setSelectedNum(String selectedNum) {
		this.selectedNum = selectedNum;
	}

	public String getImportNum() {
		return importNum;
	}

	public void setImportNum(String importNum) {
		this.importNum = importNum;
	}

	public boolean isTentative() {
		return tentative;
	}

	public void setTentative(boolean tentative) {
		this.tentative = tentative;
	}

	public SrdDTO getEarliestSrd() {
		return earliestSrd;
	}

	public void setEarliestSrd(SrdDTO earliestSrd) {
		this.earliestSrd = earliestSrd;
	}

	public String getInstallDate() {
		return installDate;
	}

	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}

	public String getInstallTimeAndType() {
		if(this.installTimeAndType != null){
			return this.installTimeAndType;
		}
		return installTime + "||" + installTimeType;
	}

	public void setInstallTimeAndType(String installTimeAndType) {
		this.installTimeAndType = installTimeAndType;
		if(StringUtils.isNotEmpty(installTimeAndType)){
			String[] timeAndType = StringUtils.split(installTimeAndType, "||");
			this.installTime = timeAndType[0];
			this.installTimeType = timeAndType[1];
		}
	}

	public String getInstallTime() {
		return installTime;
	}

	public void setInstallTime(String installTime) {
		this.installTime = installTime;
	}

	public String getInstallTimeType() {
		return installTimeType;
	}

	public void setInstallTimeType(String installTimeType) {
		this.installTimeType = installTimeType;
	}

	public String getPrebookSerialNum() {
		return prebookSerialNum;
	}

	public void setPrebookSerialNum(String prebookSerialNum) {
		this.prebookSerialNum = prebookSerialNum;
	}

	public List<ItemDetailDTO> getEpdItemList() {
		return epdItemList;
	}

	public void setEpdItemList(List<ItemDetailDTO> epdItemList) {
		this.epdItemList = epdItemList;
	}

	public String getWeeeOptions() {
		return weeeOptions;
	}

	public void setWeeeOptions(String weeeOptions) {
		this.weeeOptions = weeeOptions;
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

	public int getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}

	public List<ItemDetailDTO> getBillMethodItemList() {
		return billMethodItemList;
	}

	public void setBillMethodItemList(List<ItemDetailDTO> billMethodItemList) {
		this.billMethodItemList = billMethodItemList;
	}

	public boolean isExDirectoryConfirm() {
		return exDirectoryConfirm;
	}

	public void setExDirectoryConfirm(boolean exDirectoryConfirm) {
		this.exDirectoryConfirm = exDirectoryConfirm;
	}

	public boolean isPrivacyR() {
		return privacyR;
	}

	public void setPrivacyR(boolean privacyR) {
		this.privacyR = privacyR;
	}

	public boolean isWip() {
		return wip;
	}

	public void setWip(boolean wip) {
		this.wip = wip;
	}

	public boolean isCsPortalConfirm() {
		return csPortalConfirm;
	}

	public void setCsPortalConfirm(boolean csPortalConfirm) {
		this.csPortalConfirm = csPortalConfirm;
	}

	public List<ItemDetailDTO> getPrepayItemList() {
		return prepayItemList;
	}

	public void setPrepayItemList(List<ItemDetailDTO> prepayItemList) {
		this.prepayItemList = prepayItemList;
	}

	public boolean isShCust() {
		return shCust;
	}

	public void setShCust(boolean shCust) {
		this.shCust = shCust;
	}

	public boolean isCsPortalTheClubConfirm() {
		return csPortalTheClubConfirm;
	}

	public void setCsPortalTheClubConfirm(boolean csPortalTheClubConfirm) {
		this.csPortalTheClubConfirm = csPortalTheClubConfirm;
	}

	public boolean isTheClubConfirm() {
		return theClubConfirm;
	}

	public void setTheClubConfirm(boolean theClubConfirm) {
		this.theClubConfirm = theClubConfirm;
	}

	public boolean isCsPortalOptIn() {
		return csPortalOptIn;
	}

	public void setCsPortalOptIn(boolean csPortalOptIn) {
		this.csPortalOptIn = csPortalOptIn;
	}

	public boolean isCsPortalTheClubOptIn() {
		return csPortalTheClubOptIn;
	}

	public void setCsPortalTheClubOptIn(boolean csPortalTheClubOptIn) {
		this.csPortalTheClubOptIn = csPortalTheClubOptIn;
	}

	public boolean isTheClubOptIn() {
		return theClubOptIn;
	}

	public void setTheClubOptIn(boolean theClubOptIn) {
		this.theClubOptIn = theClubOptIn;
	}

	public String getTenureCode() {
		return tenureCode;
	}

	public void setTenureCode(String tenureCode) {
		this.tenureCode = tenureCode;
	}

	public Boolean getPrepayInd() {
		return prepayInd;
	}

	public void setPrepayInd(Boolean prepayInd) {
		this.prepayInd = prepayInd;
	}

	//==PDPO==
	public boolean isShowPdpoStatement() {
		return showPdpoStatement;
	}

	public void setShowPdpoStatement(boolean showPdpoStatement) {
		this.showPdpoStatement = showPdpoStatement;
	}
	//==PDPO end==
}
