package com.bomwebportal.lts.dto.qc;

import java.util.Date;
import java.io.Serializable;

public class OrderLtsSystemFindingDTO implements Serializable{
	private String sysF;
	private String serviceNum;
	private String serbdyno;
	private String floorNo;
	private String unitNo;
	private String hiLotNo;
	private String idDocType;
	private String idDocNum;
	private String bomCustNo;
	private String orderID;
	public String getSysF() {
		return sysF;
	}
	public void setSysF(String sysF) {
		this.sysF = sysF;
	}
	public String getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}
	public String getSerbdyno() {
		return serbdyno;
	}
	public void setSerbdyno(String serbdyno) {
		this.serbdyno = serbdyno;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public String getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	public String getHiLotNo() {
		return hiLotNo;
	}
	public void setHiLotNo(String hiLotNo) {
		this.hiLotNo = hiLotNo;
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
	public String getBomCustNo() {
		return bomCustNo;
	}
	public void setBomCustNo(String bomCustNo) {
		this.bomCustNo = bomCustNo;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
}
