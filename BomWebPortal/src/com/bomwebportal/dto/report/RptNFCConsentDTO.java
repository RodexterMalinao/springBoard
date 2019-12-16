package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.Date;

public class RptNFCConsentDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4228977063000522855L;
	public static final String JASPER_TEMPLATE = "NFCMobilePayment";
	
	private String title;
	private String custLastName;
	private String custFirstName;
	private String msisdn;
	private String orderId;
	private Date appInDate;
	private InputStream custSignature;
	
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
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

}
