package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class MrtReserveDTO implements Serializable {

	/**
	 * Amend from BOMWEB_MRT_RESERVE to BOMWEB_MRT_STATUS
	 */
	private static final long serialVersionUID = -2502353511771726006L;

	private String staffId;
	// add by Joyce 20120130
	private String orderId;
	// add by Joyce 20120130
	private String srvNumType;
	private String msisdn;
	// add by Joyce 20120130
	private String msisdnlvl;
	private String status;
	// add by Joyce 20120130
	private String cityCd;
	// comment on Joyce 20120130
//	private String reserveDate;
//	private String expiryDate;
	private String applyDate;
	
	
	private String createdBy;
	private String createdDate;
	private String lastUpdBy;
	private String lastUpdDate;
	
	private String requestId;
	private String reserveId;
	private String resOperId;
	private String reserveType;
	private String approvalSerial;
	
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
//	public String getReserveDate() {
//		return reserveDate;
//	}
//	public void setReserveDate(String reserveDate) {
//		this.reserveDate = reserveDate;
//	}
//	public String getExpiryDate() {
//		return expiryDate;
//	}
//	public void setExpiryDate(String expiryDate) {
//		this.expiryDate = expiryDate;
//	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public String getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(String lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setSrvNumType(String srvNumType) {
		this.srvNumType = srvNumType;
	}
	public String getSrvNumType() {
		return srvNumType;
	}
	public void setMsisdnlvl(String msisdnlvl) {
		this.msisdnlvl = msisdnlvl;
	}
	public String getMsisdnlvl() {
		return msisdnlvl;
	}
	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}
	public String getCityCd() {
		return cityCd;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public String getResOperId() {
		return resOperId;
	}
	public void setResOperId(String resOperId) {
		this.resOperId = resOperId;
	}
	public String getReserveType() {
		return reserveType;
	}
	public void setReserveType(String reserveType) {
		this.reserveType = reserveType;
	}
	public String getApprovalSerial() {
		return approvalSerial;
	}
	public void setApprovalSerial(String approvalSerial) {
		this.approvalSerial = approvalSerial;
	}
	
	
}
