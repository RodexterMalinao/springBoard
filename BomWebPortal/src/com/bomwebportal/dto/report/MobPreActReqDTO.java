package com.bomwebportal.dto.report;

import com.bomwebportal.dto.ShopDTO;

public class MobPreActReqDTO {
	private String agreementNum;
	private String msisdn;
	private String activationCode;
	private String custName;

	private String addrLn1;
	private String addrLn2;
	private String addrLn3;
	private String addrLn4;
	private String addrLn5;
	
	private String templateType;
	private String reqFilename; //batch in file
	private String pdfInd;
	private String tempOutFileName;
	private String outFileName; // generate output file
	
	private String srdDate;
	private String smsLang;
	private String brand;
	private String shopEmail;
	private String shopFax;
	private String shopCd;
	private ShopDTO shopDTO;
	
	protected String companyLogo;
	protected String companyBottomLogo;
	private String brandString;
	private String serviceString;
	private String shopString;
	
	public String getAgreementNum() {
		return agreementNum;
	}
	public void setAgreementNum(String agreementNum) {
		this.agreementNum = agreementNum;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getAddrLn1() {
		return addrLn1;
	}
	public void setAddrLn1(String addrLn1) {
		this.addrLn1 = addrLn1;
	}
	public String getAddrLn2() {
		return addrLn2;
	}
	public void setAddrLn2(String addrLn2) {
		this.addrLn2 = addrLn2;
	}
	public String getAddrLn3() {
		return addrLn3;
	}
	public void setAddrLn3(String addrLn3) {
		this.addrLn3 = addrLn3;
	}
	public String getAddrLn4() {
		return addrLn4;
	}
	public void setAddrLn4(String addrLn4) {
		this.addrLn4 = addrLn4;
	}
	public String getAddrLn5() {
		return addrLn5;
	}
	public void setAddrLn5(String addrLn5) {
		this.addrLn5 = addrLn5;
	}
	public String getTemplateType() {
		return templateType;
	}
	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getReqFilename() {
		return reqFilename;
	}
	public void setReqFilename(String reqFilename) {
		this.reqFilename = reqFilename;
	}
	public String getPdfInd() {
		return pdfInd;
	}
	public void setPdfInd(String pdfInd) {
		this.pdfInd = pdfInd;
	}
	public String getTempOutFileName() {
		return tempOutFileName;
	}
	public void setTempOutFileName(String tempOutFileName) {
		this.tempOutFileName = tempOutFileName;
	}
	public String getOutFileName() {
		return outFileName;
	}
	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}
	public String getSrdDate() {
		return srdDate;
	}
	public void setSrdDate(String srdDate) {
		this.srdDate = srdDate;
	}
	public String getSmsLang() {
		return smsLang;
	}
	public void setSmsLang(String smsLang) {
		this.smsLang = smsLang;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getShopEmail() {
		return shopEmail;
	}
	public void setShopEmail(String shopEmail) {
		this.shopEmail = shopEmail;
	}
	public String getShopFax() {
		return shopFax;
	}
	public void setShopFax(String shopFax) {
		this.shopFax = shopFax;
	}
	public String getShopCd() {
		return shopCd;
	}	
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public ShopDTO getShopDTO() {
		return shopDTO;
	}
	public void setShopDTO(ShopDTO shopDTO) {
		this.shopDTO = shopDTO;
	}
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	public String getCompanyBottomLogo() {
		return companyBottomLogo;
	}
	public void setCompanyBottomLogo(String companyBottomLogo) {
		this.companyBottomLogo = companyBottomLogo;
	}
	public String getBrandString() {
		return brandString;
	}
	public void setBrandString(String brandString) {
		this.brandString = brandString;
	}
	public String getServiceString() {
		return serviceString;
	}
	public void setServiceString(String serviceString) {
		this.serviceString = serviceString;
	}
	public String getShopString() {
		return shopString;
	}
	public void setShopString(String shopString) {
		this.shopString = shopString;
	}
}
