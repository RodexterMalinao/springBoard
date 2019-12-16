package com.bomltsportal.dto.email;

import java.util.Date;

import com.bomltsportal.dto.email.OrderDTO.EsigEmailLang;

public class OrdEmailReqDTO {
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getFilePathName1() {
		return filePathName1;
	}
	public void setFilePathName1(String filePathName1) {
		this.filePathName1 = filePathName1;
	}
	public String getFilePathName2() {
		return filePathName2;
	}
	public void setFilePathName2(String filePathName2) {
		this.filePathName2 = filePathName2;
	}
	public String getFilePathName3() {
		return filePathName3;
	}
	public void setFilePathName3(String filePathName3) {
		this.filePathName3 = filePathName3;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	public EsigEmailLang getLocale() {
		return locale;
	}
	public void setLocale(EsigEmailLang locale) {
		this.locale = locale;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public EsigEmailLang getEsigEmailLang() {
		return esigEmailLang;
	}
	public void setEsigEmailLang(EsigEmailLang esigEmailLang) {
		this.esigEmailLang = esigEmailLang;
	}
	public String getEsigEmailAddr() {
		return esigEmailAddr;
	}
	public void setEsigEmailAddr(String esigEmailAddr) {
		this.esigEmailAddr = esigEmailAddr;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	private String orderId;
	private String templateId;
	private Date requestDate;
	private String filePathName1;
	private String filePathName2;
	private String filePathName3;
	private Date sentDate;
	private String errMsg;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String emailAddr;
	private EsigEmailLang locale;
	private int seqNo;
	private String rowId;
	// bomweb_order
	private EsigEmailLang esigEmailLang;
	private String esigEmailAddr;
	private String msisdn;
	private Date appDate;
	private String salesName;
	private String shopCd;
	private String lob;
	// bomweb_contact
	private String title;
	private String contactName;
	// bomweb_shop
	private String contactPhone;
	
	//added by Gary 121016
	private String emailType;
}
