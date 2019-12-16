package com.bomltsportal.dto.email;

import java.util.Date;

import com.bomltsportal.dto.email.OrderDTO.EsigEmailLang;

public class EmailTemplateDTO {
	public enum PdfPwdInd {
		Y
		, N
		;
	}
	
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getTemplateSeq() {
		return templateSeq;
	}
	public void setTemplateSeq(String templateSeq) {
		this.templateSeq = templateSeq;
	}
	public String getTemplateDesc() {
		return templateDesc;
	}
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
	public String getTemplateSubject() {
		return templateSubject;
	}
	public void setTemplateSubject(String templateSubject) {
		this.templateSubject = templateSubject;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
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
	public EsigEmailLang getLocale() {
		return locale;
	}
	public void setLocale(EsigEmailLang locale) {
		this.locale = locale;
	}
	public PdfPwdInd getPdfPwdInd() {
		return pdfPwdInd;
	}
	public void setPdfPwdInd(PdfPwdInd pdfPwdInd) {
		this.pdfPwdInd = pdfPwdInd;
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
}
