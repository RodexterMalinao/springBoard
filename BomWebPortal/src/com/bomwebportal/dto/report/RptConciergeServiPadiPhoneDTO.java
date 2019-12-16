package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.Date;

public class RptConciergeServiPadiPhoneDTO extends ReportDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2258548848012218178L;

	public static final String JASPER_TEMPLATE = "ConciergeServiceiPadiPhone";

	private Date appInDate;
	private String custFirstName;
	private String custLastName;
	private String msisdn;	
	private String imei;
	private String modelName;
	private String colorName;
	private String handsetDeviceAmount;
	//add eliot 20110819
	private InputStream custSignature;
	private InputStream custSignature2;
	private String originalPrice;
	private String statement;
	private String statement_zh_hk;
	private String note;
	private String note_zh_hk;

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getNote_zh_hk() {
		return note_zh_hk;
	}
	public void setNote_zh_hk(String note_zh_hk) {
		this.note_zh_hk = note_zh_hk;
	}
	public InputStream getCustSignature() {
		return custSignature;
	}
	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}

	public RptConciergeServiPadiPhoneDTO() {
		super(JASPER_TEMPLATE);
	}

	public Date getAppInDate() {
		return appInDate;
	}
	public void setAppInDate(Date appInDate) {
		this.appInDate = appInDate;
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

	public String getImei() {
		return imei;		
	}
	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getHandsetDeviceAmount() {
		return handsetDeviceAmount;
	}
	public void setHandsetDeviceAmount(String handsetDeviceAmount) {
		this.handsetDeviceAmount = handsetDeviceAmount;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	public String getStatement_zh_hk() {
		return statement_zh_hk;
	}
	public void setStatement_zh_hk(String statement_zh_hk) {
		this.statement_zh_hk = statement_zh_hk;
	}
	/**
	 * @return the custSignature2
	 */
	public InputStream getCustSignature2() {
		return custSignature2;
	}
	/**
	 * @param custSignature2 the custSignature2 to set
	 */
	public void setCustSignature2(InputStream custSignature2) {
		this.custSignature2 = custSignature2;
	}

}
