package com.bomwebportal.lts.dto.apn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LtsApnFileDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7255217185189350434L;
	
	private String batchSeq;
	private String fileName;
	private String status; 
	private String uploadDate;
	List<LtsApnDnDTO> dnMatch = new ArrayList<LtsApnDnDTO>();
	List<LtsApnDnDTO> dnMatchWithProblem = new ArrayList<LtsApnDnDTO>();
	List<LtsApnDnDTO> dnNotMatchNnMatch = new ArrayList<LtsApnDnDTO>();
	List<LtsApnDnDTO> dnIgnored = new ArrayList<LtsApnDnDTO>();
	List<LtsApnDnDTO> allDnRecord = new ArrayList<LtsApnDnDTO>();
	private String failedReason;
	
	
	public String getBatchSeq() {
		return batchSeq;
	}
	public void setBatchSeq(String batchSeq) {
		this.batchSeq = batchSeq;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getFailedReason() {
		return failedReason;
	}
	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}
	public List<LtsApnDnDTO> getDnMatch() {
		return dnMatch;
	}
	public void setDnMatch(List<LtsApnDnDTO> dnMatch) {
		this.dnMatch = dnMatch;
	}
	public List<LtsApnDnDTO> getDnMatchWithProblem() {
		return dnMatchWithProblem;
	}
	public void setDnMatchWithProblem(List<LtsApnDnDTO> dnMatchWithProblem) {
		this.dnMatchWithProblem = dnMatchWithProblem;
	}
	public List<LtsApnDnDTO> getDnNotMatchNnMatch() {
		return dnNotMatchNnMatch;
	}
	public void setDnNotMatchNnMatch(List<LtsApnDnDTO> dnNotMatchNnMatch) {
		this.dnNotMatchNnMatch = dnNotMatchNnMatch;
	}
	public List<LtsApnDnDTO> getDnIgnored() {
		return dnIgnored;
	}
	public void setDnIgnored(List<LtsApnDnDTO> dnIgnored) {
		this.dnIgnored = dnIgnored;
	}
	public List<LtsApnDnDTO> getAllDnRecord() {
		return allDnRecord;
	}
	public void setAllDnRecord(List<LtsApnDnDTO> allDnRecord) {
		this.allDnRecord = allDnRecord;
	}	
}
