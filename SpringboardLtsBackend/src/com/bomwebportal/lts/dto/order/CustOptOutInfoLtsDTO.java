package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class CustOptOutInfoLtsDTO extends ObjectActionBaseDTO {
	
	private static final long serialVersionUID = 4030315988269432018L;

	private String custNo; // BOMWEB_CUST_OPTOUT_INFO.CUST_NO
	private String optInd; // BOMWEB_CUST_OPTOUT_INFO.OPT_IND
	private String directMailing; // BOMWEB_CUST_OPTOUT_INFO.DIRECT_MAILING
	private String outbound; // BOMWEB_CUST_OPTOUT_INFO.OUTBOUND
	private String sms; // BOMWEB_CUST_OPTOUT_INFO.SMS
	private String email; // BOMWEB_CUST_OPTOUT_INFO.EMAIL
	private String webBill; // BOMWEB_CUST_OPTOUT_INFO.WEB_BILL
	private String nonsalesSms; // BOMWEB_CUST_OPTOUT_INFO.NONSALES_SMS
	private String internet; // BOMWEB_CUST_OPTOUT_INFO.INTERNET
	private String bm; // BOMWEB_CUST_OPTOUT_INFO.BM
	private String billInsert; // BOMWEB_CUST_OPTOUT_INFO.BILL_INSERT
	private String smsEye; // BOMWEB_CUST_OPTOUT_INFO.SMS_EYE
	private String cdOutdial; // BOMWEB_CUST_OPTOUT_INFO.CD_OUTDIAL
	private String updBomStatus; // BOMWEB_CUST_OPTOUT_INFO.UPD_BOM_STATUS
	private String errMsg; // BOMWEB_CUST_OPTOUT_INFO.ERR_MSG;
	
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
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
	public String getNonsalesSms() {
		return nonsalesSms;
	}
	public void setNonsalesSms(String nonsalesSms) {
		this.nonsalesSms = nonsalesSms;
	}
	public String getInternet() {
		return internet;
	}
	public void setInternet(String internet) {
		this.internet = internet;
	}
	public String getBm() {
		return bm;
	}
	public void setBm(String bm) {
		this.bm = bm;
	}
	public String getBillInsert() {
		return billInsert;
	}
	public void setBillInsert(String billInsert) {
		this.billInsert = billInsert;
	}
	public String getSmsEye() {
		return smsEye;
	}
	public void setSmsEye(String smsEye) {
		this.smsEye = smsEye;
	}
	public String getCdOutdial() {
		return cdOutdial;
	}
	public void setCdOutdial(String cdOutdial) {
		this.cdOutdial = cdOutdial;
	}
	public String getOptInd() {
		return optInd;
	}
	public void setOptInd(String optInd) {
		this.optInd = optInd;
	}
	public String getUpdBomStatus() {
		return updBomStatus;
	}
	public void setUpdBomStatus(String updBomStatus) {
		this.updBomStatus = updBomStatus;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}
