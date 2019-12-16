package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.Date;

public class RptOctopusConsentDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7143008601276961754L;

	public static final String JASPER_TEMPLATE = "OctopusMobilePayment";
	
	private String title;
	private String custLastName;
	private String custFirstName;
	private String msisdn;
	private String orderId;
	private Date appInDate;
	private InputStream custSignature;
	private String octFormP1;//Athena 20130924
	private String octFormP2;//Athena 20130924
	
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
	public String getOctFormP1() {//Athena 20130924
		return octFormP1;
	}
	public void setOctFormP1(String octFormP1) {//Athena 20130924
		this.octFormP1 = octFormP1;
	}
	public String getOctFormP2() {//Athena 20130924
		return octFormP2;
	}
	public void setOctFormP2(String octFormP2) {//Athena 20130924
		this.octFormP2 = octFormP2;
	}

}
