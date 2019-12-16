package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class CustPdpoProfileDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4940046852538549035L;

	private String custNum;
	private String lob;
	private String optInd;
	private String directMailing;
	private String outbound;
	private String sms;
	private String email;
	private String webBill;
	private String billInsert;
	private String cdOutdial;
	private String bm;
	private String smsEye;
	private String datacast;
	
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getOptInd() {
		return optInd;
	}
	public void setOptInd(String optInd) {
		this.optInd = optInd;
	}
	public String getCustNum() {
		return custNum;
	}
	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}
	public String getDirectMailing() {
		return directMailing;
	}
	public void setDirectMailing(String directMailing) {
		this.directMailing = directMailing;
	}
	public String getOutbound() {
		return outbound;
	}
	public void setOutbound(String outbound) {
		this.outbound = outbound;
	}
	public String getSms() {
		return sms;
	}
	public void setSms(String sms) {
		this.sms = sms;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebBill() {
		return webBill;
	}
	public void setWebBill(String webBill) {
		this.webBill = webBill;
	}
	public String getBillInsert() {
		return billInsert;
	}
	public void setBillInsert(String billInsert) {
		this.billInsert = billInsert;
	}
	public String getCdOutdial() {
		return cdOutdial;
	}
	public void setCdOutdial(String cdOutdial) {
		this.cdOutdial = cdOutdial;
	}
	public String getBm() {
		return bm;
	}
	public void setBm(String bm) {
		this.bm = bm;
	}
	public String getSmsEye() {
		return smsEye;
	}
	public void setSmsEye(String smsEye) {
		this.smsEye = smsEye;
	}
	public String getDatacast() {
		return datacast;
	}
	public void setDatacast(String datacast) {
		this.datacast = datacast;
	}
	
}
