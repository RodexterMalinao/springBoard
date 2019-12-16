package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class GoldenMrtAdminDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2004004787783921122L;
    private String orderId;
    private Date requestDate;
    private String firstName;
    private String lastName;
    private String goldenSuffix;
    private String msisdn;
    private String msisdnlvl;
    private String monthlyCharge;
    private String contractPeriod;
    private String reserveId;
    private String approvedBy;
    private Date approvedDate;
    private String remarks;
    private String createdBy;
    private Date createdDate;
    private String lastUpdBy;
    private Date lastUpdDate;
    private String title;
    private String salesName;
    private String rowId;
    private String requestStatus;

    public String getOrderId() {
	return orderId;
    }

    public void setOrderId(String orderId) {
	this.orderId = orderId;
    }

    public Date getRequestDate() {
	return requestDate;
    }

    public void setRequestDate(Date requestDate) {
	this.requestDate = requestDate;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getGoldenSuffix() {
	return goldenSuffix;
    }

    public void setGoldenSuffix(String goldenSuffix) {
	this.goldenSuffix = goldenSuffix;
    }

    public String getMsisdn() {
	return msisdn;
    }

    public void setMsisdn(String msisdn) {
	this.msisdn = msisdn;
    }

    public String getMsisdnlvl() {
	return msisdnlvl;
    }

    public void setMsisdnlvl(String msisdnlvl) {
	this.msisdnlvl = msisdnlvl;
    }

    public String getMonthlyCharge() {
	return monthlyCharge;
    }

    public void setMonthlyCharge(String monthlyCharge) {
	this.monthlyCharge = monthlyCharge;
    }

    public String getContractPeriod() {
	return contractPeriod;
    }

    public void setContractPeriod(String contractPeriod) {
	this.contractPeriod = contractPeriod;
    }

    public String getReserveId() {
	return reserveId;
    }

    public void setReserveId(String reserveId) {
	this.reserveId = reserveId;
    }

    public String getApprovedBy() {
	return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
	this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
	return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
	this.approvedDate = approvedDate;
    }

    public String getRemarks() {
	return remarks;
    }

    public void setRemarks(String remarks) {
	this.remarks = remarks;
    }

    public String getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public String getLastUpdBy() {
	return lastUpdBy;
    }

    public void setLastUpdBy(String lastUpdBy) {
	this.lastUpdBy = lastUpdBy;
    }

    public Date getLastUpdDate() {
	return lastUpdDate;
    }

    public void setLastUpdDate(Date lastUpdDate) {
	this.lastUpdDate = lastUpdDate;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	
}
