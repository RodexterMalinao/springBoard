package com.bomwebportal.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class DocImgUploadDTO {
	
	
	private String orderId;
	private String seqNo;
	private String docType;
	private String username;

	private MultipartFile file;
	
	private String docName;
	private String docNameChi;
	private long size;
	private String fileName;
	
	private String[] allowedExtensions;
	private long maxUploadSize;
	
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
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}


	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
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

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String[] getAllowedExtensions() {
		return allowedExtensions;
	}

	public void setAllowedExtensions(String[] allowedExtensions) {
		this.allowedExtensions = allowedExtensions;
	}

	public long getMaxUploadSize() {
		return maxUploadSize;
	}

	public void setMaxUploadSize(long maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
}
