package com.bomwebportal.dto;

import java.util.Date;

public class BomOrderDTO implements java.io.Serializable {

	private static final long serialVersionUID = 5783900649769448884L;
	private String bomStatus;
	private String status;
	private String statusDesc;
	private String ocid;
	private String busTxnType;
	private Date srvReqDate;
	private String shopCode;

	public String getBomStatus() {
		return bomStatus;
	}

	public void setBomStatus(String bomStatus) {
		this.bomStatus = bomStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getOcid() {
		return ocid;
	}

	public void setOcid(String ocid) {
		this.ocid = ocid;
	}

	public String getBusTxnType() {
		return busTxnType;
	}

	public void setBusTxnType(String busTxnType) {
		this.busTxnType = busTxnType;
	}

	public Date getSrvReqDate() {
		return srvReqDate;
	}

	public void setSrvReqDate(Date srvReqDate) {
		this.srvReqDate = srvReqDate;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	
}
