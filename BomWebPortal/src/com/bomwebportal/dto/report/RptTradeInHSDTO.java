package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.Date;

public class RptTradeInHSDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2115582729863597479L;

	public static final String JASPER_TEMPLATE = "TradeInHS";
	
	private String imei;
	private Date tradeInExpiryDate;
	
	//Inherited properties
	private String title;
	private String custLastName;
	private String custFirstName;
	private String contactPhone;
	private String msisdn;
	private String orderId;
	private String rebateSchedule;
	private Date deliveryDate;
	private InputStream custSignature;
	
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public Date getTradeInExpiryDate() {
		return tradeInExpiryDate;
	}
	public void setTradeInExpiryDate(Date tradeInExpiryDate) {
		this.tradeInExpiryDate = tradeInExpiryDate;
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
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
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
	public String getRebateSchedule() {
		return rebateSchedule;
	}
	public void setRebateSchedule(String rebateSchedule) {
		this.rebateSchedule = rebateSchedule;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public InputStream getCustSignature() {
		return custSignature;
	}
	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}
	
}
