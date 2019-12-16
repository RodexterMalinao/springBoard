package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class CustOptoutInfoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7238971913452368748L;
	
	private String OrderId;
	private String CustNo;
	private String DirectMailing;
	private String Outbound;
	private String Sms;
	private String Email;
	private String WebBill;
	private String NonsalesSms;
	private String Internet;
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
	public String getDirectMailing() {
		return DirectMailing;
	}
	public void setDirectMailing(String directMailing) {
		DirectMailing = directMailing;
	}
	public String getOutbound() {
		return Outbound;
	}
	public void setOutbound(String outbound) {
		Outbound = outbound;
	}
	public String getSms() {
		return Sms;
	}
	public void setSms(String sms) {
		Sms = sms;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getWebBill() {
		return WebBill;
	}
	public void setWebBill(String webBill) {
		WebBill = webBill;
	}
	public String getNonsalesSms() {
		return NonsalesSms;
	}
	public void setNonsalesSms(String nonsalesSms) {
		NonsalesSms = nonsalesSms;
	}
	public String getInternet() {
		return Internet;
	}
	public void setInternet(String internet) {
		Internet = internet;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
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
