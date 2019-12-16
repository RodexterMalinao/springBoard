package com.bomwebportal.dto;

import java.util.Date;

public class CNMRTSupportDocDTO {
	private String mrtCn;
	private String imageType;
	private int seqNo;
	private String status;
	private Date uploadDate;
	private Double duration;
	private String sessionId;
	
	public String getMrtCn() {
		return mrtCn;
	}
	public void setMrtCn(String mrtCn) {
		this.mrtCn = mrtCn;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Double getDuration() {
		return duration;
	}
	public void setDuration(Double duration) {
		this.duration = duration;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}