package com.bomwebportal.dto;

import bom.mob.schema.javabean.si.springboard.xsd.SubActiveContract;

public class MigrateCurrentLineMrtCheckDTO {

	private String title;
	private String custLastName;
	private String custFirstName;
	private String idDocType;
	private String idDocNum;
	private String bomCustNum;
	private String acctNum;
	private String mobCustNo;
	private String subSimIccid;
	private String subSimDesc;
	private String subSimItemCd;
	private String subSimType;
	private String wsResult;
	private String wsErrorMessage;
	private SubActiveContract subActiveContract;
	private String subBrand;
	private String too1BrmOrder;
	private String mnpOrder;
	private String mdoInd;
	private boolean oneNumber;
	
	public String getMnpOrder() {
		return mnpOrder;
	}

	public void setMnpOrder(String mnpOrder) {
		this.mnpOrder = mnpOrder;
	}

	public String getToo1BrmOrder() {
		return too1BrmOrder;
	}

	public void setToo1BrmOrder(String too1BrmOrder) {
		this.too1BrmOrder = too1BrmOrder;
	}

	public String getSubBrand() {
		return subBrand;
	}

	public void setSubBrand(String subBrand) {
		this.subBrand = subBrand;
	}
	
	public String getMobCustNo() {
		return mobCustNo;
	}

	public void setMobCustNo(String mobCustNo) {
		this.mobCustNo = mobCustNo;
	}

	public String getSubSimType() {
		return subSimType;
	}

	public void setSubSimType(String subSimType) {
		this.subSimType = subSimType;
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
		return bomCustNum;
	}

	public void setBomCustNum(String bomCustNum) {
		this.bomCustNum = bomCustNum;
	}

	public String getAcctNum() {
		return acctNum;
	}

	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

	public String getSubSimIccid() {
		return subSimIccid;
	}

	public void setSubSimIccid(String subSimIccid) {
		this.subSimIccid = subSimIccid;
	}

	public String getSubSimDesc() {
		return subSimDesc;
	}

	public void setSubSimDesc(String subSimDesc) {
		this.subSimDesc = subSimDesc;
	}

	public String getSubSimItemCd() {
		return subSimItemCd;
	}

	public void setSubSimItemCd(String subSimItemCd) {
		this.subSimItemCd = subSimItemCd;
	}

	public String getWsResult() {
		return wsResult;
	}

	public void setWsResult(String wsResult) {
		this.wsResult = wsResult;
	}

	public String getWsErrorMessage() {
		return wsErrorMessage;
	}

	public void setWsErrorMessage(String wsErrorMessage) {
		this.wsErrorMessage = wsErrorMessage;
	}

	public SubActiveContract getSubActiveContract() {
		return subActiveContract;
	}

	public void setSubActiveContract(SubActiveContract subActiveContract) {
		this.subActiveContract = subActiveContract;
	}

	public String getMdoInd() {
		return mdoInd;
	}

	public void setMdoInd(String mdoInd) {
		this.mdoInd = mdoInd;
	}

	public boolean isOneNumber() {
		return oneNumber;
	}

	public void setOneNumber(boolean oneNumber) {
		this.oneNumber = oneNumber;
	}
	
}