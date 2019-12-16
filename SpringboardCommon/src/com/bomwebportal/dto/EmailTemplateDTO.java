package com.bomwebportal.dto;

import java.util.Date;

public class EmailTemplateDTO {
	
	/*
	Initial copied on 20140925 from Version 1.1 of
	Springboard\SBWPR3\BomWebPortal\src\com\bomwebportal\dto\EmailTemplateDTO.java
	*/	
	
	public enum PdfPwdInd {
		Y
		, N
		;
	}
	
	public enum EsigEmailLang {
		CHN // Chinese
		, ENG // English
		;
	}	
	
	private String templateId;
	private String lob;
	private String templateSeq;
	private String templateDesc;
	private String templateSubject;
	private String templateContent;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private EsigEmailLang locale;
	private PdfPwdInd pdfPwdInd;	
	
	
	public String getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(String pTemplateId) {
		this.templateId = pTemplateId;
	}
	
	public String getLob() {
		return lob;
	}
	
	public void setLob(String pLob) {
		this.lob = pLob;
	}
	
	public String getTemplateSeq() {
		return templateSeq;
	}
	
	public void setTemplateSeq(String pTemplateSeq) {
		this.templateSeq = pTemplateSeq;
	}
	
	public String getTemplateDesc() {
		return templateDesc;
	}
	
	public void setTemplateDesc(String pTemplateDesc) {
		this.templateDesc = pTemplateDesc;
	}
	
	public String getTemplateSubject() {
		return templateSubject;
	}
	
	public void setTemplateSubject(String pTemplateSubject) {
		this.templateSubject = pTemplateSubject;
	}
	
	public String getTemplateContent() {
		return templateContent;
	}
	
	public void setTemplateContent(String pTemplateContent) {
		this.templateContent = pTemplateContent;
	}
	
	public String getCreateBy() {
		return createBy;
	}
	
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date pCreateDate) {
		this.createDate = pCreateDate;
	}
	
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}
	
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	
	public void setLastUpdDate(Date pLastUpdDate) {
		this.lastUpdDate = pLastUpdDate;
	}
	
	public EsigEmailLang getLocale() {
		return locale;
	}
	
	public void setLocale(EsigEmailLang pLocale) {
		this.locale = pLocale;
	}
	
	public PdfPwdInd getPdfPwdInd() {
		return pdfPwdInd;
	}
	
	public void setPdfPwdInd(PdfPwdInd pPdfPwdInd) {
		this.pdfPwdInd = pPdfPwdInd;
	}

}
