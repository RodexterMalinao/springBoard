package com.bomwebportal.dto;

import java.io.Serializable;

public class AccountDTO implements Serializable{

	private static final long serialVersionUID = 8757207868430214658L;
	
	public AccountDTO(){
		this.systemId = "MOB";
		this.billFreq = "MTHLY";
	//	this.billLang = "BILN";
		
	}
	private String orderId;

	private String systemId;
	
	private String acctName;
	
	private String billFreq;
	
	private String billLang;
	
	private String smsNum;
	
	private String emailAddr;
	
	private String billPeriod;
	
	
	
	private String sameAsCustInd;
	
	private String isNew;
	
	private String acctNum;
	
	private String mobCustNum;
	
	private String BomCustNum;
	
	private String acctType;
	
	private String srvNum;
	
	private String idDocType;
	
	private String idDocNum;
	
	private String brand;
	
	private String fraudMsg;
	
	
	public String getBillPeriod() {
		return billPeriod;
	}

	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}

	public String getSystemId() {		
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getBillFreq() {
		return billFreq;
	}

	public void setBillFreq(String billFreq) {
		this.billFreq = billFreq;
	}

	public String getBillLang() {
		return billLang;
	}

	public void setBillLang(String billLang) {
		this.billLang = billLang;
	}

	public String getSmsNum() {
		return smsNum;
	}

	public void setSmsNum(String smsNum) {
		this.smsNum = smsNum;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSameAsCustInd() {
		return sameAsCustInd;
	}

	public void setSameAsCustInd(String sameAsCustInd) {
		this.sameAsCustInd = sameAsCustInd;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getAcctNum() {
		return acctNum;
	}

	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

	public String getMobCustNum() {
		return mobCustNum;
	}

	public void setMobCustNum(String mobCustNum) {
		this.mobCustNum = mobCustNum;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getSrvNum() {
		return srvNum;
	}

	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getIdDocNum() {
		return idDocNum;
	}

	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}

	public String getBomCustNum() {
		return BomCustNum;
	}

	public void setBomCustNum(String bomCustNum) {
		BomCustNum = bomCustNum;
	}

	public String getFraudMsg() {
		return fraudMsg;
	}

	public void setFraudMsg(String fraudMsg) {
		this.fraudMsg = fraudMsg;
	}
	
	
	
}
