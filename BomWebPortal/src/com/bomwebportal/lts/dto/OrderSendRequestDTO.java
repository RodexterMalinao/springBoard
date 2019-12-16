package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class OrderSendRequestDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1915952129254582581L;
	
	
	private String orderId;
	private String templateId;
	private String templateDesc;
	private String templateSubject;
	private String templateContent;
	private String requestDate;
	private String createBy;
	private String sentDate;
	private String email;
	private String lang;
	private String seqNo;
	private String errMsg;
	private String filePathName1;
	private String filePathName2;
	private String filePathName3;
	private String smsNo;
	private String method;
	private String disMode;
	private String billingAddress;
	private String printRequestStatus;
	private String printDate;
	
	public String getPrintRequestStatus() {
		return printRequestStatus;
	}
	public void setPrintRequestStatus(String printRequestStatus) {
		this.printRequestStatus = printRequestStatus;
	}
	public String getPrintDate() {
		return printDate;
	}
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
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
	public String getTemplateDesc() {
		return templateDesc;
	}
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getSentDate() {
		return sentDate;
	}
	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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
	public String getSmsNo() {
		return smsNo;
	}
	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getDisMode() {
		return disMode;
	}
	public void setDisMode(String disMode) {
		this.disMode = disMode;
	}
	
	
	
	
	
}
