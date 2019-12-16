package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.Date;

public class OrdDocDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum VerifyInd {
		N, Y;
	}

	private String orderId;
	private String docType;
	private int seqNum;
	private String filePathName;
	private String serial;

	private String docName;
	private String docNameChi;

	private String captureBy;
	private Date captureDate;

	private String lastUpdBy;
	private Date lastUpdDate;

	private VerifyInd verifyInd;
	private String verifyBy;
	private Date verifyDate;
	
	private String linkedPath;

	private byte[] fileContent;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
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

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocNameChi() {
		return docNameChi;
	}

	public void setDocNameChi(String docNameChi) {
		this.docNameChi = docNameChi;
	}

	public String getCaptureBy() {
		return captureBy;
	}

	public void setCaptureBy(String captureBy) {
		this.captureBy = captureBy;
	}

	public Date getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
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

	public VerifyInd getVerifyInd() {
		return verifyInd;
	}

	public void setVerifyInd(VerifyInd verifyInd) {
		this.verifyInd = verifyInd;
	}

	public String getVerifyBy() {
		return verifyBy;
	}

	public void setVerifyBy(String verifyBy) {
		this.verifyBy = verifyBy;
	}

	public Date getVerifyDate() {
		return verifyDate;
	}

	public void setVerifyDate(Date verifyDate) {
		this.verifyDate = verifyDate;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getFullPathName() {
		return this.getOrderId() + "/" + this.getFilePathName();
	}

	public byte[] getFileContent() {
		return this.fileContent;
	}

	public void setFileContent(byte[] pFileContent) {
		this.fileContent = pFileContent;
	}

	public String getLinkedPath() {
		return this.linkedPath;
	}

	public void setLinkedPath(String pLinkedPath) {
		this.linkedPath = pLinkedPath;
	}
}
