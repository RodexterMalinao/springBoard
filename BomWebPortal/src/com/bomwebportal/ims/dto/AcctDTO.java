package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class AcctDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5096531629663008744L;
	
	
	private String OrderId;
	private String CustNo;
	private String AcctName;
	private String BillFreq;
	private String BillLang;
	private String SmsNo;
	private String EmailAddr;
	private String AcctNo;	
	private String BillPeriod;
	private String BillMedia;
	private String existingBillingAddress;
	private String existingBillingEmail;
	private String existingBillingMedia;
	private String keepExistingBillingAddress;
	private String updateBillingMethod; // C - change bill method, empty keep existing bill method 
	private String DtlId;
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;

	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getCustNo() {
		return CustNo;
	}
	public void setCustNo(String custNo) {
		CustNo = custNo;
	}
	public String getAcctName() {
		return AcctName;
	}
	public void setAcctName(String acctName) {
		AcctName = acctName;
	}
	public String getBillFreq() {
		return BillFreq;
	}
	public void setBillFreq(String billFreq) {
		BillFreq = billFreq;
	}
	public String getBillLang() {
		return BillLang;
	}
	public void setBillLang(String billLang) {
		BillLang = billLang;
	}
	public String getExistingBillingAddress() {
		return existingBillingAddress;
	}
	public void setExistingBillingAddress(String existingBillingAddress) {
		this.existingBillingAddress = existingBillingAddress;
	}
	public String getExistingBillingEmail() {
		return existingBillingEmail;
	}
	public void setExistingBillingEmail(String existingBillingEmail) {
		this.existingBillingEmail = existingBillingEmail;
	}
	public String getExistingBillingMedia() {
		return existingBillingMedia;
	}
	public void setExistingBillingMedia(String existingBillingMedia) {
		this.existingBillingMedia = existingBillingMedia;
	}
	public String getKeepExistingBillingAddress() {
		return keepExistingBillingAddress;
	}
	public void setKeepExistingBillingAddress(String keepExistingBillingAddress) {
		this.keepExistingBillingAddress = keepExistingBillingAddress;
	}
	public String getUpdateBillingMethod() {
		return updateBillingMethod;
	}
	public void setUpdateBillingMethod(String updateBillingMethod) {
		this.updateBillingMethod = updateBillingMethod;
	}
	public String getSmsNo() {
		return SmsNo;
	}
	public void setSmsNo(String smsNo) {
		SmsNo = smsNo;
	}
	public String getEmailAddr() {
		return EmailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		EmailAddr = emailAddr;
	}
	public String getAcctNo() {
		return AcctNo;
	}
	public void setAcctNo(String acctNo) {
		AcctNo = acctNo;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getBillPeriod() {
		return BillPeriod;
	}
	public void setBillPeriod(String billPeriod) {
		BillPeriod = billPeriod;
	}
	public String getBillMedia() {
		return BillMedia;
	}
	public void setBillMedia(String billMedia) {
		BillMedia = billMedia;
	}	
	public String getDtlId() {
		return DtlId;
	}
	public void setDtlId(String dtlId) {
		DtlId = dtlId;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public Date getLastUpdDate() {
		return LastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		LastUpdDate = lastUpdDate;
	}
	public String getLastUpdBy() {
		return LastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		LastUpdBy = lastUpdBy;
	}	

}
