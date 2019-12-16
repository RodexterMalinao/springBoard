package com.bomwebportal.ims.dto.report;

import java.io.InputStream;

public class RptIms3PartyCreditCardDTO extends ReportDTO {
	private static final long serialVersionUID = -3311684335459264341L;
	
	// Reference From OrderDTO
	private String appNo;
	private String appDate;
	private String shopCd;

	// Customer Details	
	private String custLastName;	// LAST_NAME VARCHAR2(40 BYTE),
	private String custFirstName;	// FIRST_NAME VARCHAR2(40 BYTE),
	private String idDocType;		// ID_DOC_TYPE VARCHAR2(4 BYTE),
	private String idDocNum;		// ID_DOC_NUM VARCHAR2(30 BYTE)
	private String contactPhone;
	
	// Section K - payment
	private String creditCardType;
	private String creditCardHolderName;
	private String creditCardNum;
	private String creditExpiryDate;
	
	private InputStream custSignature;
	private InputStream thirdPartySignature;
	
	private String companyName;
	private String companyFaxNo;
	
	
	// new fields for 3rdPartyPcdCpqAuth (start)
	private String ntvLogo;
	private String netvigatorLogo;
	
	private String pcdAppNo;
	private String ntvAppNo;
	
	private String titleChi;
	private String titleEn;
	private String introChi;
	private String introEn;
	
	private String subscribeMethod;
	private String subMethodSalesChi;
	private String subMethodSalesEn;
	private String subMethodHotlineChi;
	private String subMethodHotlineEn;
	
	private String declarationChi;
	private String declarationEn;
	private String authChi;
	private String authEn;
	private String footnoteChi;
	private String footnoteEn;
	private String showVerNum;
	private String verNum;
	// new field for 3rdPartyPcdCpqAuth (end)
	
	
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getCustLastName() {
		return custLastName;
	}
	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}
	public String getCustFirstName() {
		return custFirstName;
	}
	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}
	public String getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}
	public String getIdDocNum() {
		return idDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getCreditCardType() {
		return creditCardType;
	}
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}
	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = creditCardHolderName;
	}
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public String getCreditExpiryDate() {
		return creditExpiryDate;
	}
	public void setCreditExpiryDate(String creditExpiryDate) {
		this.creditExpiryDate = creditExpiryDate;
	}
	public InputStream getCustSignature() {
		return custSignature;
	}
	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}
	public void setThirdPartySignature(InputStream thirdPartySignature) {
		this.thirdPartySignature = thirdPartySignature;
	}
	public InputStream getThirdPartySignature() {
		return thirdPartySignature;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyFaxNo() {
		return companyFaxNo;
	}
	public void setCompanyFaxNo(String companyFaxNo) {
		this.companyFaxNo = companyFaxNo;
	}
	
	public String getNtvLogo() {
		return ntvLogo;
	}
	public void setNtvLogo(String ntvLogo) {
		this.ntvLogo = ntvLogo;
	}
	public String getNetvigatorLogo() {
		return netvigatorLogo;
	}
	public void setNetvigatorLogo(String netvigatorLogo) {
		this.netvigatorLogo = netvigatorLogo;
	}
	public String getPcdAppNo() {
		return pcdAppNo;
	}
	public void setPcdAppNo(String pcdAppNo) {
		this.pcdAppNo = pcdAppNo;
	}
	public String getNtvAppNo() {
		return ntvAppNo;
	}
	public void setNtvAppNo(String ntvAppNo) {
		this.ntvAppNo = ntvAppNo;
	}
	public String getTitleChi() {
		return titleChi;
	}
	public void setTitleChi(String titleChi) {
		this.titleChi = titleChi;
	}
	public String getTitleEn() {
		return titleEn;
	}
	public void setTitleEn(String titleEn) {
		this.titleEn = titleEn;
	}
	public String getIntroChi() {
		return introChi;
	}
	public void setIntroChi(String introChi) {
		this.introChi = introChi;
	}
	public String getIntroEn() {
		return introEn;
	}
	public void setIntroEn(String introEn) {
		this.introEn = introEn;
	}
	public String getSubscribeMethod() {
		return subscribeMethod;
	}
	public void setSubscribeMethod(String subscribeMethod) {
		this.subscribeMethod = subscribeMethod;
	}
	public String getSubMethodSalesChi() {
		return subMethodSalesChi;
	}
	public void setSubMethodSalesChi(String subMethodSalesChi) {
		this.subMethodSalesChi = subMethodSalesChi;
	}
	public String getSubMethodSalesEn() {
		return subMethodSalesEn;
	}
	public void setSubMethodSalesEn(String subMethodSalesEn) {
		this.subMethodSalesEn = subMethodSalesEn;
	}
	public String getSubMethodHotlineChi() {
		return subMethodHotlineChi;
	}
	public void setSubMethodHotlineChi(String subMethodHotlineChi) {
		this.subMethodHotlineChi = subMethodHotlineChi;
	}
	public String getSubMethodHotlineEn() {
		return subMethodHotlineEn;
	}
	public void setSubMethodHotlineEn(String subMethodHotlineEn) {
		this.subMethodHotlineEn = subMethodHotlineEn;
	}
	public String getDeclarationChi() {
		return declarationChi;
	}
	public void setDeclarationChi(String declarationChi) {
		this.declarationChi = declarationChi;
	}
	public String getDeclarationEn() {
		return declarationEn;
	}
	public void setDeclarationEn(String declarationEn) {
		this.declarationEn = declarationEn;
	}
	public String getAuthChi() {
		return authChi;
	}
	public void setAuthChi(String authChi) {
		this.authChi = authChi;
	}
	public String getAuthEn() {
		return authEn;
	}
	public void setAuthEn(String authEn) {
		this.authEn = authEn;
	}
	public String getFootnoteChi() {
		return footnoteChi;
	}
	public void setFootnoteChi(String footnoteChi) {
		this.footnoteChi = footnoteChi;
	}
	public String getFootnoteEn() {
		return footnoteEn;
	}
	public void setFootnoteEn(String footnoteEn) {
		this.footnoteEn = footnoteEn;
	}
	public String getShowVerNum() {
		return showVerNum;
	}
	public void setShowVerNum(String showVerNum) {
		this.showVerNum = showVerNum;
	}
	public String getVerNum() {
		return verNum;
	}
	public void setVerNum(String verNum) {
		this.verNum = verNum;
	}
	
	@Override
	public String toString() {
		return "RptIms3PartyCreditCardDTO [appNo=" + appNo + ", appDate="
				+ appDate + ", shopCd=" + shopCd + ", lastName=" + custLastName
				+ ", firstName=" + custFirstName + ", idDocType=" + idDocType
				+ ", idDocNum=" + idDocNum + ", contactPhone=" + contactPhone
				+ ", creditCardType=" + creditCardType
				+ ", creditCardHolderName=" + creditCardHolderName
				+ ", creditCardNum=" + creditCardNum + ", creditExpiryDate="
				+ creditExpiryDate + ", custSignature=" + custSignature
				+ ", companyName=" + companyName + ", companyFaxNo="
				+ companyFaxNo + "]";
	}
	
}
