package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.Date;

public class RptIGuardCareCashDTO extends ReportDTO{
	/**
	 * 
	 */
	
	public static final String JASPER_TEMPLATE = "IguardCareCash";	
	private static final long serialVersionUID = -6236337996690156984L;
	private String orderId;
	private String shopCd;
	private String staffId;
	private Date dob;
	private String idDocNum;
	private String custEngName;// FIRST_NAME VARCHAR2(40 BYTE),
	private String signatureInd;
	private String emailAddr;
	private String contactPhone;
	private String mob;
	private String lts;
	private String ims;
	private String ntv;
	private String privacyInd;
	private InputStream custSignature;
	private Date appDate;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}	
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getIdDocNum() {
		return idDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	public String getCustEngName() {
		return custEngName;
	}
	public void setCustEngName(String custEngName) {
		this.custEngName = custEngName;
	}	
	public String getSignatureInd() {
		return signatureInd;
	}
	public void setSignatureInd(String signatureInd) {
		this.signatureInd = signatureInd;
	}
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	public String getMob() {
		return mob;
	}
	public void setMob(String mob) {
		this.mob = mob;
	}
	public String getLts() {
		return lts;
	}
	public void setLts(String lts) {
		this.lts = lts;
	}
	public String getIms() {
		return ims;
	}
	public void setIms(String ims) {
		this.ims = ims;
	}
	public String getNtv() {
		return ntv;
	}
	public void setNtv(String ntv) {
		this.ntv = ntv;
	}
	public String getPrivacyInd() {
		return privacyInd;
	}
	public void setPrivacyInd(String privacyInd) {
		this.privacyInd = privacyInd;
	}
	public InputStream getCustSignature() {
		return custSignature;
	}
	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
}
