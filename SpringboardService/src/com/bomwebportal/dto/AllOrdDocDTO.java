package com.bomwebportal.dto;

import java.util.Date;

import com.bomwebportal.dto.AllDocDTO.DocType;

public class AllOrdDocDTO {
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
	public int getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}
	public String getFilePathName() {
		return filePathName;
	}
	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
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
	private int seqNum;
	private String filePathName;
	private Date processDate;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String rowId;
}
