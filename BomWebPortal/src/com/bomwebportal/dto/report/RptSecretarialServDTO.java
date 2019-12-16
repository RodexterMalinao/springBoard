package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.Date;

public class RptSecretarialServDTO extends ReportDTO {
	private static final long serialVersionUID = 4648404968065252453L;

	public static final String JASPER_TEMPLATE = "SecretarialServiceForm";
	
	private String title;
	private String custFirstName;
	private String custLastName;
	private String msisdn;
	private String agreementNum;
	private Date appInDate;
	private Date srvReqDate;
	private String shopCode;
	private String salesCd;	
	private String smsBillLanguage;
	private String secSrvContractPeriod;
	private String idDocType;
	private String companyName;
	private InputStream custSignature;
	
	public RptSecretarialServDTO() {
		super(JASPER_TEMPLATE);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getAgreementNum() {
		return agreementNum;
	}

	public void setAgreementNum(String agreementNum) {
		this.agreementNum = agreementNum;
	}

	public Date getAppInDate() {
		return appInDate;
	}

	public void setAppInDate(Date appInDate) {
		this.appInDate = appInDate;
	}

	public Date getSrvReqDate() {
		return srvReqDate;
	}

	public void setSrvReqDate(Date srvReqDate) {
		this.srvReqDate = srvReqDate;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getSalesCd() {
		return salesCd;
	}

	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}

	public String getSmsBillLanguage() {
		return smsBillLanguage;
	}

	public void setSmsBillLanguage(String smsBillLanguage) {
		this.smsBillLanguage = smsBillLanguage;
	}

	public String getSecSrvContractPeriod() {
		return secSrvContractPeriod;
	}

	public void setSecSrvContractPeriod(String secSrvContractPeriod) {
		this.secSrvContractPeriod = secSrvContractPeriod;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the custSignature
	 */
	public InputStream getCustSignature() {
		return custSignature;
	}

	/**
	 * @param custSignature the custSignature to set
	 */
	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}	
		
}
