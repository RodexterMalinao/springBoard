package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.Date;

// DTO Class for Credit Card Direct Debit Authorization Form Generation (jasper report)
public class RptCreditCardDDAuthDTO extends ReportDTO{
	private static final long serialVersionUID = -3024675262815845284L;

	public static final String JASPER_TEMPLATE = "CreditCardDDAuthorization";

	// Reference to com.bomwebportal.dto.PaymentDTO.java
	private String creditCardHolderName;
	private String creditCardType;
	private String creditCardNum;
	private String creditExpiryMonth;
	private String creditExpiryYear;
	private String creditCardIssueBank;
	private String creditCardDocType;
	private String creditCardDocNum;
	private String username;
	//add by Eliot 20110621
	private String companyName;
	
	// Reference to com.bomwebportal.dto.MnpDTO.java
	private String shopCd;
	private String msisdn; // Customer Mobile No.
	
	// Reference to com.bomwebportal.dto.CustomerProfileDTO
	private String contactPhone;
	private String custLastName;
	private String custFirstName;

	// Reference to com.bomwebportal.dto.OrderDTO
	private String agreementNum;
	
	//add eliot 20110819
	private InputStream custSignature;
	
	private String custNameChi;
	private String companyNameChi;
	private String idDocType;
	
	private Date appInDate;
	
	private String ccos7;
	private String ccos8;
	
	public RptCreditCardDDAuthDTO() {
		super(JASPER_TEMPLATE);
	}	

	public RptCreditCardDDAuthDTO(String pLang) {
		super(JASPER_TEMPLATE, pLang);
	}	
	
	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}

	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = creditCardHolderName;
	}

	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	public String getCreditExpiryMonth() {
		return creditExpiryMonth;
	}

	public void setCreditExpiryMonth(String creditExpiryMonth) {
		this.creditExpiryMonth = creditExpiryMonth;
	}

	public String getCreditExpiryYear() {
		return creditExpiryYear;
	}

	public void setCreditExpiryYear(String creditExpiryYear) {
		this.creditExpiryYear = creditExpiryYear;
	}

	public String getCreditCardIssueBank() {
		return creditCardIssueBank;
	}

	public void setCreditCardIssueBank(String creditCardIssueBank) {
		this.creditCardIssueBank = creditCardIssueBank;
	}

	public String getCreditCardDocType() {
		return creditCardDocType;
	}

	public void setCreditCardDocType(String creditCardDocType) {
		this.creditCardDocType = creditCardDocType;
	}

	public String getCreditCardDocNum() {
		return creditCardDocNum;
	}

	public void setCreditCardDocNum(String creditCardDocNum) {
		this.creditCardDocNum = creditCardDocNum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getShopCd() {
		return shopCd;
	}

	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getCustLastName() {
		return custLastName;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public String getAgreementNum() {
		return agreementNum;
	}

	public void setAgreementNum(String agreementNum) {
		this.agreementNum = agreementNum;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public InputStream getCustSignature() {
		return custSignature;
	}

	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}

	/**
	 * @return the custNameChi
	 */
	public String getCustNameChi() {
		return custNameChi;
	}

	/**
	 * @param custNameChi the custNameChi to set
	 */
	public void setCustNameChi(String custNameChi) {
		this.custNameChi = custNameChi;
	}

	/**
	 * @return the companyNameChi
	 */
	public String getCompanyNameChi() {
		return companyNameChi;
	}

	/**
	 * @param companyNameChi the companyNameChi to set
	 */
	public void setCompanyNameChi(String companyNameChi) {
		this.companyNameChi = companyNameChi;
	}

	/**
	 * @return the idDocType
	 */
	public String getIdDocType() {
		return idDocType;
	}

	/**
	 * @param idDocType the idDocType to set
	 */
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	/**
	 * @return the appInDate
	 */
	public Date getAppInDate() {
		return appInDate;
	}

	/**
	 * @param appInDate the appInDate to set
	 */
	public void setAppInDate(Date appInDate) {
		this.appInDate = appInDate;
	}

	public String getCcos7() {
		return ccos7;
	}

	public void setCcos7(String ccos7) {
		this.ccos7 = ccos7;
	}

	public String getCcos8() {
		return ccos8;
	}

	public void setCcos8(String ccos8) {
		this.ccos8 = ccos8;
	}	
	
}
