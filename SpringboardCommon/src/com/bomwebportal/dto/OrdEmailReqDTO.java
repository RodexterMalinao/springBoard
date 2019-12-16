package com.bomwebportal.dto;

import java.util.Date;

import com.bomwebportal.dto.EmailTemplateDTO.EsigEmailLang;

public class OrdEmailReqDTO {

	/*
	Initial copied on 20140925 from Version 1.6 of
	\Springboard\SBWPR3\BomWebPortal\src\com\bomwebportal\dto\OrdEmailReqDTO.java
	*/		

	public enum DisMode {
		E // e-Signature
		, P // Paper
		, S // SMS
		, I //email
		, M //Postal
		;
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
	
	//
	private DisMode method;
	private String SMSno;
	private String salesTeam;
	
	//MOB shop brand
	private String brand;	
	private String orderType;	
	
	//added by Leo 141204
	private String actvId;
	private String profileId;
	private String taskSeq;
	private String storePath;
	private String custNo;
	private String paramString;
	
	private Long printReqId;
	
	public DisMode getMethod() {
		return method;
	}
	
	public void setMethod(DisMode pMethod) {
		this.method = pMethod;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}
	
	public String getTemplateId() {
		return templateId;
	}
	
	public void setTemplateId(String pTemplateId) {
		this.templateId = pTemplateId;
	}
	
	public Date getRequestDate() {
		return requestDate;
	}
	
	public void setRequestDate(Date pRequestDate) {
		this.requestDate = pRequestDate;
	}
	
	public String getFilePathName1() {
		return filePathName1;
	}
	
	public void setFilePathName1(String pFilePathName1) {
		this.filePathName1 = pFilePathName1;
	}
	
	public String getFilePathName2() {
		return filePathName2;
	}
	
	public void setFilePathName2(String pFilePathName2) {
		this.filePathName2 = pFilePathName2;
	}
	
	public String getFilePathName3() {
		return filePathName3;
	}
	
	public void setFilePathName3(String pFilePathName3) {
		this.filePathName3 = pFilePathName3;
	}
	
	public Date getSentDate() {
		return sentDate;
	}
	
	public void setSentDate(Date pSentDate) {
		this.sentDate = pSentDate;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
	
	public void setErrMsg(String pErrMsg) {
		this.errMsg = pErrMsg;
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
	
	public String getEmailAddr() {
		return emailAddr;
	}
	
	public void setEmailAddr(String PEmailAddr) {
		this.emailAddr = PEmailAddr;
	}
	
	public EsigEmailLang getLocale() {
		return locale;
	}
	
	public void setLocale(EsigEmailLang pLocale) {
		this.locale = pLocale;
	}
	
	public int getSeqNo() {
		return seqNo;
	}
	
	public void setSeqNo(int pSeqNo) {
		this.seqNo = pSeqNo;
	}
	
	public String getRowId() {
		return rowId;
	}
	
	public void setRowId(String pRowId) {
		this.rowId = pRowId;
	}
	
	public EsigEmailLang getEsigEmailLang() {
		return esigEmailLang;
	}
	
	public void setEsigEmailLang(EsigEmailLang pEsigEmailLang) {
		this.esigEmailLang = pEsigEmailLang;
	}
	
	public String getEsigEmailAddr() {
		return esigEmailAddr;
	}
	
	public void setEsigEmailAddr(String pEsigEmailAddr) {
		this.esigEmailAddr = pEsigEmailAddr;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	
	public void setMsisdn(String pMsisdn) {
		this.msisdn = pMsisdn;
	}
	
	public Date getAppDate() {
		return appDate;
	}
	
	public void setAppDate(Date pAppDate) {
		this.appDate = pAppDate;
	}
	
	public String getSalesName() {
		return salesName;
	}
	
	public void setSalesName(String pSalesName) {
		this.salesName = pSalesName;
	}
	
	public String getShopCd() {
		return shopCd;
	}
	
	public void setShopCd(String pShopCd) {
		this.shopCd = pShopCd;
	}
	
	public String getLob() {
		return lob;
	}
	
	public void setLob(String pLob) {
		this.lob = pLob;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String pTitle) {
		this.title = pTitle;
	}
	
	public String getContactName() {
		return contactName;
	}
	
	public void setContactName(String pContactName) {
		this.contactName = pContactName;
	}
	
	public String getContactPhone() {
		return contactPhone;
	}
	
	public void setContactPhone(String pContactPhone) {
		this.contactPhone = pContactPhone;
	}
	
	public String getEmailType() {
		return emailType;
	}
	
	public void setEmailType(String pEmailType) {
		this.emailType = pEmailType;
	}
	
	public void setSMSno(String pSMSno) {
		SMSno = pSMSno;
	}
	
	public String getSMSno() {
		return SMSno;
	}
	
	public void setSalesTeam(String pSalesTeam) {
		this.salesTeam = pSalesTeam;
	}
	
	public String getSalesTeam() {
		return salesTeam;
	}

	public String getBrand() {
		return brand;
	}
	
	public void setBrand(String pBrand) {
		this.brand = pBrand;
	}

	public String getOrderType() {
		return orderType;
	}
	
	public void setOrderType(String pOrderType) {
		this.orderType = pOrderType;
	}

	public String getActvId() {
		return this.actvId;
	}

	public void setActvId(String pActvId) {
		this.actvId = pActvId;
	}

	public String getProfileId() {
		return this.profileId;
	}

	public void setProfileId(String pProfileId) {
		this.profileId = pProfileId;
	}

	public String getTaskSeq() {
		return this.taskSeq;
	}

	public void setTaskSeq(String pTaskSeq) {
		this.taskSeq = pTaskSeq;
	}

	public String getStorePath() {
		return this.storePath;
	}

	public void setStorePath(String pStorePath) {
		this.storePath = pStorePath;
	}

	public String getCustNo() {
		return this.custNo;
	}

	public void setCustNo(String pCustNo) {
		this.custNo = pCustNo;
	}

	public String getParamString() {
		return this.paramString;
	}

	public void setParamString(String pParamString) {
		this.paramString = pParamString;
	}

	public Long getPrintReqId() {
		return printReqId;
	}

	public void setPrintReqId(Long printReqId) {
		this.printReqId = printReqId;
	}
	
	
}
