package com.pccw.wq.schema.form;

import java.io.Serializable;

import com.pccw.wq.schema.dto.WorkQueueDTO;

public class WqInquiryResultFormDTO extends WorkQueueDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9099949772065286906L;

	private String wqWpAssgnId;

	private String wpId;
	
	private String wpDesc;

	private String wqSerial;
	
	private String wqBatchId;
	
	private String wqReceiveDate;

	private String reasonCode;
	
	private String reasonDesc;
	
	private String wqStatus;

	private String wqStatusDesc;

	private String wqStatusDate;
	
	private String assignee;

	private String wqNatureDesc;

	private String wqType;

	private String wqTypeDesc;

	private String wqSubType;

	private String wqSubTypeDesc;
	
	private String wqDocumentIdString;
	
	private String nextStatus;

	public String getWqWpAssgnId() {
		return this.wqWpAssgnId;
	}

	public void setWqWpAssgnId(String pWqWpAssgnId) {
		this.wqWpAssgnId = pWqWpAssgnId;
	}

	public String getWqSerial() {
		return this.wqSerial;
	}

	public void setWqSerial(String pWqSerial) {
		this.wqSerial = pWqSerial;
	}

	public String getWqReceiveDate() {
		return this.wqReceiveDate;
	}

	public void setWqReceiveDate(String pWqReceiveDate) {
		this.wqReceiveDate = pWqReceiveDate;
	}

	public String getReasonCode() {
		return this.reasonCode;
	}

	public void setReasonCode(String pReasonCode) {
		this.reasonCode = pReasonCode;
	}

	public String getReasonDesc() {
		return this.reasonDesc;
	}

	public void setReasonDesc(String pReasonDesc) {
		this.reasonDesc = pReasonDesc;
	}

	public String getWqStatus() {
		return this.wqStatus;
	}

	public void setWqStatus(String pWqStatus) {
		this.wqStatus = pWqStatus;
	}

	public String getWqStatusDesc() {
		return this.wqStatusDesc;
	}

	public void setWqStatusDesc(String pWqStatusDesc) {
		this.wqStatusDesc = pWqStatusDesc;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String pAssignee) {
		this.assignee = pAssignee;
	}

	public String getWqNatureDesc() {
		return this.wqNatureDesc;
	}

	public void setWqNatureDesc(String pWqNatureDesc) {
		this.wqNatureDesc = pWqNatureDesc;
	}

	public String getWqType() {
		return this.wqType;
	}

	public void setWqType(String pWqType) {
		this.wqType = pWqType;
	}

	public String getWqTypeDesc() {
		return this.wqTypeDesc;
	}

	public void setWqTypeDesc(String pWqTypeDesc) {
		this.wqTypeDesc = pWqTypeDesc;
	}

	public String getWqSubType() {
		return this.wqSubType;
	}

	public void setWqSubType(String pWqSubType) {
		this.wqSubType = pWqSubType;
	}

	public String getWqSubTypeDesc() {
		return this.wqSubTypeDesc;
	}

	public void setWqSubTypeDesc(String pWqSubTypeDesc) {
		this.wqSubTypeDesc = pWqSubTypeDesc;
	}

	public String getWqDocumentIdString() {
		return this.wqDocumentIdString;
	}

	public void setWqDocumentIdString(String pWqDocumentIdString) {
		this.wqDocumentIdString = pWqDocumentIdString;
	}

	public String getWpId() {
		return this.wpId;
	}

	public void setWpId(String pWpId) {
		this.wpId = pWpId;
	}

	public String getWqStatusDate() {
		return this.wqStatusDate;
	}

	public void setWqStatusDate(String pWqStatusDate) {
		this.wqStatusDate = pWqStatusDate;
	}

	public String getWpDesc() {
		return this.wpDesc;
	}

	public void setWpDesc(String pWpDesc) {
		this.wpDesc = pWpDesc;
	}

	public String getNextStatus() {
		return this.nextStatus;
	}

	public void setNextStatus(String pNextStatus) {
		this.nextStatus = pNextStatus;
	}

	public String getWqBatchId() {
		return this.wqBatchId;
	}

	public void setWqBatchId(String pWqBatchId) {
		this.wqBatchId = pWqBatchId;
	}
}