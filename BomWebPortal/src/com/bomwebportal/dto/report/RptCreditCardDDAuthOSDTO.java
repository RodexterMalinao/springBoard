package com.bomwebportal.dto.report;

import java.io.InputStream;

public class RptCreditCardDDAuthOSDTO extends ReportDTO {

	private static final long serialVersionUID = 7437719898443344066L;

	public static final String JASPER_TEMPLATE = "CreditCardDDAuthorization_os";

	private String companyLogo;
	private String orderId;
	private String ccos1;
	private String ccos2;
	private String custName;
	private String msisdn;
	private String creditCardType;
	private String creditCardIssueBank;
	private String creditCardNum;
	private String creditCardValidMonth;
	private String creditCardValidYear;
	private String creditCardHolderName;
	private String idDocNum;
	private String contactPhone;
	private String ccos3;
	private String ccos4;
	private String ccos5;
	private String ccos6;
	private InputStream custSignature;
	private String appDate;
	private String ccos7;
	private String ccos8;
	
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCcos1() {
		return ccos1;
	}
	public void setCcos1(String ccos1) {
		this.ccos1 = ccos1;
	}
	public String getCcos2() {
		return ccos2;
	}
	public void setCcos2(String ccos2) {
		this.ccos2 = ccos2;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getCreditCardType() {
		return creditCardType;
	}
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	public String getCreditCardIssueBank() {
		return creditCardIssueBank;
	}
	public void setCreditCardIssueBank(String creditCardIssueBank) {
		this.creditCardIssueBank = creditCardIssueBank;
	}
	public String getCreditCardNum() {
		return creditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	public String getCreditCardValidMonth() {
		return creditCardValidMonth;
	}
	public void setCreditCardValidMonth(String creditCardValidMonth) {
		this.creditCardValidMonth = creditCardValidMonth;
	}
	public String getCreditCardValidYear() {
		return creditCardValidYear;
	}
	public void setCreditCardValidYear(String creditCardValidYear) {
		this.creditCardValidYear = creditCardValidYear;
	}
	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}
	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = creditCardHolderName;
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
	public String getCcos3() {
		return ccos3;
	}
	public void setCcos3(String ccos3) {
		this.ccos3 = ccos3;
	}
	public String getCcos4() {
		return ccos4;
	}
	public void setCcos4(String ccos4) {
		this.ccos4 = ccos4;
	}
	public String getCcos5() {
		return ccos5;
	}
	public void setCcos5(String ccos5) {
		this.ccos5 = ccos5;
	}
	public String getCcos6() {
		return ccos6;
	}
	public void setCcos6(String ccos6) {
		this.ccos6 = ccos6;
	}
	public InputStream getCustSignature() {
		return custSignature;
	}
	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
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
