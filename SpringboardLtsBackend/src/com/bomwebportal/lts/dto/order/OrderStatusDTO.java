package com.bomwebportal.lts.dto.order;

import java.io.Serializable;

public class OrderStatusDTO implements Serializable {

	private static final long serialVersionUID = -2297906460818102379L;
	
	private String dtlId = null;
	private String sbStatus = null;
	private String reasonCd = null;
	private String bomStatus = null;
	private String bomIssueDate = null;
	private String wqType = null;
	private String statusDate = null;
	private String lastUpdBy = null;
	private String legacyStatus = null;

	
	public String getDtlId() {
		return dtlId;
	}

	public void setDtlId(String dtlId) {
		this.dtlId = dtlId;
	}

	public String getSbStatus() {
		return sbStatus;
	}

	public void setSbStatus(String sbStatus) {
		this.sbStatus = sbStatus;
	}

	public String getReasonCd() {
		return reasonCd;
	}

	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}

	public String getBomStatus() {
		return bomStatus;
	}

	public void setBomStatus(String bomStatus) {
		this.bomStatus = bomStatus;
	}

	public String getBomIssueDate() {
		return bomIssueDate;
	}

	public void setBomIssueDate(String bomIssueDate) {
		this.bomIssueDate = bomIssueDate;
	}

	public String getWqType() {
		return wqType;
	}

	public void setWqType(String wqType) {
		this.wqType = wqType;
	}

	public String getStatusDate() {
		return this.statusDate;
	}

	public void setStatusDate(String pStatusDate) {
		this.statusDate = pStatusDate;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public String getLegacyStatus() {
		return legacyStatus;
	}

	public void setLegacyStatus(String legacyStatus) {
		this.legacyStatus = legacyStatus;
	}
}
