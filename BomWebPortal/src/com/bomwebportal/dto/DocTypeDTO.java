package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.Date;

public class DocTypeDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String docType;
	private String lob;
	private String type;
	private String docName;
	private String docNameChi;
	private String docDesc;
	private Date startDate;
	private Date endDate;
	private String waterMark;

	
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getDocDesc() {
		return docDesc;
	}
	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getWaterMark() {
		return waterMark;
	}
	public void setWaterMark(String waterMark) {
		this.waterMark = waterMark;
	}


}
