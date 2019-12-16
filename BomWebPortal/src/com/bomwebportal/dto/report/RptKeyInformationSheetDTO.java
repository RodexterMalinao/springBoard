package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.Date;

public class RptKeyInformationSheetDTO extends ReportDTO{
	private static final long serialVersionUID = -3311684335459264341L;
	public static final String JASPER_TEMPLATE = "KeyInformation";
	//public static final String JASPER_TEMPLATE_NE = "KeyInformation";
	
	public RptKeyInformationSheetDTO() {
		this.setJasperName(JASPER_TEMPLATE);
	}
	
	private String agreementNum;
	private String msisdn;
	private String title;
	private String custLastName;
	private String custFirstName;	
	private Date appInDate;
	private InputStream custSignature;
	private String customerCopyInd;
	private boolean mspInd;
	
	public String getAgreementNum() {
		return agreementNum;
	}
	public void setAgreementNum(String agreementNum) {
		this.agreementNum = agreementNum;
	}
	
	public String getMsisdn(){
		return msisdn;
	}
	public void setMsisdn(String msisdn){
		this.msisdn = msisdn;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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

	public Date getAppInDate() {
		return appInDate;
	}
	public void setAppInDate(Date appInDate) {
		this.appInDate = appInDate;
	}
	public InputStream getCustSignature() {
		return custSignature;
	}
	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}
	
	public String getCustomerCopyInd() {
		return customerCopyInd;
	}

	public void setCustomerCopyInd(String customerCopyInd) {
		this.customerCopyInd = customerCopyInd;
	}
	public boolean isMspInd() {
		return mspInd;
	}
	public void setMspInd(boolean mspInd) {
		this.mspInd = mspInd;
	}

}
