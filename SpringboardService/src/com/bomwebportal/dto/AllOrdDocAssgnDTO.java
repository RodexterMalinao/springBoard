package com.bomwebportal.dto;

import java.util.Date;

import com.bomwebportal.dto.AllDocDTO.DocType;

public class AllOrdDocAssgnDTO {
	public static enum CollectedInd {
		N
		, Y
		;
	}
	public static enum MarkDelInd {
		N
		, Y
		;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public DocType getDocType() {
		return docType;
	}
	public void setDocType(DocType docType) {
		this.docType = docType;
	}
	public String getDocTypeMob() {
		return docTypeMob;
	}
	public void setDocTypeMob(String docTypeMob) {
		this.docTypeMob = docTypeMob;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getWaiveReason() {
		return waiveReason;
	}
	public void setWaiveReason(String waiveReason) {
		this.waiveReason = waiveReason;
	}
	public String getWaivedBy() {
		return waivedBy;
	}
	public void setWaivedBy(String waivedBy) {
		this.waivedBy = waivedBy;
	}
	public CollectedInd getCollectedInd() {
		return collectedInd;
	}
	public void setCollectedInd(CollectedInd collectedInd) {
		this.collectedInd = collectedInd;
	}
	public MarkDelInd getMarkDelInd() {
		return markDelInd;
	}
	public void setMarkDelInd(MarkDelInd markDelInd) {
		this.markDelInd = markDelInd;
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
	private String orderId;
	private DocType docType;
	private String docTypeMob;
	private String docName;
	private String waiveReason;
	private String waivedBy;
	private CollectedInd collectedInd;
	private MarkDelInd markDelInd;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String rowId;
}
