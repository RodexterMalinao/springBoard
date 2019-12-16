package com.bomwebportal.ims.dto;

import java.util.Date;

import com.bomwebportal.dto.AllDocDTO.DocType;

public class ImsAllOrdDocWaiveDTO {
	public DocType getDocType() {
		return docType;
	}
	public void setDocType(DocType docType) {
		this.docType = docType;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getWaiveReason() {
		return waiveReason;
	}
	public void setWaiveReason(String waiveReason) {
		this.waiveReason = waiveReason;
	}
	public String getWaiveDesc() {
		return waiveDesc;
	}
	public void setWaiveDesc(String waiveDesc) {
		this.waiveDesc = waiveDesc;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	private DocType docType;
	private String lob;
	private String waiveReason;
	private String waiveDesc;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String rowId;
	
	private String DEFAULT_WAIVE_IND;
	public void setDEFAULT_WAIVE_IND(String dEFAULT_WAIVE_IND) {
		DEFAULT_WAIVE_IND = dEFAULT_WAIVE_IND;
	}
	public String getDEFAULT_WAIVE_IND() {
		return DEFAULT_WAIVE_IND;
	}
}
