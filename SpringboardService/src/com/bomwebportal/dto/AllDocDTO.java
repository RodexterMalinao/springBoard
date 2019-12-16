package com.bomwebportal.dto;

import java.util.Date;

public class AllDocDTO {
	public static enum DocType {
		M001 // Springboard Mobile Form
		, M002 // HKID/Passport Copy
		, M003 // Address Proof
		, M004 // BR Copy
		, M005 // BR Name Card
		, M006 // Employment Contract Copy
		, M007 // Pre-paid SIM Copy
		, M008 // Credit Card Copy
		, M009 // MNP Doc Copy (For transfer of ownership)
		, M010 // Concierge Form Collection for AO
		, M011 // IGuard Form
		, M012 // Amended Springboard Mobile Form
		, M013 // Mobile Safety Phone Form
		, M034 // BR Authorized Letter
		, M037 // I-Guard AD Form
		, M052 // Travel Insurance Form
		, M053 // HelperCare Insurance Form
		, M055 // Project Eagle Insurance Form
		, I003, I002, I001, I004 // IMS - E sign
		, I005, I006 //IMS - Call Center
		, I007 //IMS - Direct Sales
		, I008 // IMS - NTV bill statement
		, I009 // IMS - staff verification (NTV-A)
		, I010 // IMS - corporate staff verification (NTV-A)
		;
	}
	public static enum Type {
		O // Out
		, I // In
		;
	}
	
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
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
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
	public String getDocTypeMob() {
		return docTypeMob;
	}
	public void setDocTypeMob(String docTypeMob) {
		this.docTypeMob = docTypeMob;
	}

	private DocType docType;
	private String lob;
	private Type type;
	private String docName;
	private String docNameChi;
	private String docDesc;
	private Date startDate;
	private Date endDate;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String docTypeMob;
}
