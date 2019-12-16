package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;


public class Ims1AMSInfoWithoutPendingDTO implements Serializable{
	
	private static final long serialVersionUID = 629210834742496125L;
	
	private String ocid;
	private String orderType;
	private String srdDate;
	private String isPCD;
	private String isStandAloneTV;
	private String isStandAloneEYE;
	private String isStandAloneEasyWatch;
	private String isEYE;
	private String isEYEX;
	private String isTV;
	private String isPCDTV;
	private String pID;
	private String is1L1B;
	private String isVI;
	private String custName;
	private String pCDAccStatus;
	private String vIAccStatus;
	private String isILRC;
	private int returnValue;
	private int errorCode;
	private String errorMsg;
	
	public Ims1AMSInfoWithoutPendingDTO(){
		
	}

	public String getOcid() {
		return ocid;
	}

	public void setOcid(String ocid) {
		this.ocid = ocid;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSrdDate() {
		return srdDate;
	}

	public void setSrdDate(String srdDate) {
		this.srdDate = srdDate;
	}

	public String getIsPCD() {
		return isPCD;
	}

	public void setIsPCD(String isPCD) {
		this.isPCD = isPCD;
	}

	public String getIsStandAloneTV() {
		return isStandAloneTV;
	}

	public void setIsStandAloneTV(String isStandAloneTV) {
		this.isStandAloneTV = isStandAloneTV;
	}

	public String getIsStandAloneEYE() {
		return isStandAloneEYE;
	}

	public void setIsStandAloneEYE(String isStandAloneEYE) {
		this.isStandAloneEYE = isStandAloneEYE;
	}

	public String getIsStandAloneEasyWatch() {
		return isStandAloneEasyWatch;
	}

	public void setIsStandAloneEasyWatch(String isStandAloneEasyWatch) {
		this.isStandAloneEasyWatch = isStandAloneEasyWatch;
	}

	public String getIsEYE() {
		return isEYE;
	}

	public void setIsEYE(String isEYE) {
		this.isEYE = isEYE;
	}

	public String getIsEYEX() {
		return isEYEX;
	}

	public void setIsEYEX(String isEYEX) {
		this.isEYEX = isEYEX;
	}

	public String getIsTV() {
		return isTV;
	}

	public void setIsTV(String isTV) {
		this.isTV = isTV;
	}

	public String getIsPCDTV() {
		return isPCDTV;
	}

	public void setIsPCDTV(String isPCDTV) {
		this.isPCDTV = isPCDTV;
	}

	public String getpID() {
		return pID;
	}

	public void setpID(String pID) {
		this.pID = pID;
	}

	public String getIs1L1B() {
		return is1L1B;
	}

	public void setIs1L1B(String is1l1b) {
		is1L1B = is1l1b;
	}

	public String getIsVI() {
		return isVI;
	}

	public void setIsVI(String isVI) {
		this.isVI = isVI;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getpCDAccStatus() {
		return pCDAccStatus;
	}

	public void setpCDAccStatus(String pCDAccStatus) {
		this.pCDAccStatus = pCDAccStatus;
	}

	public String getvIAccStatus() {
		return vIAccStatus;
	}

	public void setvIAccStatus(String vIAccStatus) {
		this.vIAccStatus = vIAccStatus;
	}

	public String getIsILRC() {
		return isILRC;
	}

	public void setIsILRC(String isILRC) {
		this.isILRC = isILRC;
	}

	public int getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(int returnValue) {
		this.returnValue = returnValue;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	
	
}
