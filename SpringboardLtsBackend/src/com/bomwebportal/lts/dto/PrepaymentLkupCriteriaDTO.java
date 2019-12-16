package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class PrepaymentLkupCriteriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3494065273576593539L;

	private String psefCd;
	private String channel;
	private String orderType;
	private String docType;
	private String paymentMethod;
	private int tenure;
	private String houseType;
	private int eyeProfileCount;
	private String appDate;
	
	private String referenceSql;
	
	public String getPsefCd() {
		return psefCd;
	}
	public void setPsefCd(String psefCd) {
		this.psefCd = psefCd;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public int getTenure() {
		return tenure;
	}
	public void setTenure(int tenure) {
		this.tenure = tenure;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public int getEyeProfileCount() {
		return eyeProfileCount;
	}
	public void setEyeProfileCount(int eyeProfileCount) {
		this.eyeProfileCount = eyeProfileCount;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	/**
	 * @return the referenceSql
	 */
	public String getReferenceSql() {
		return referenceSql;
	}
	/**
	 * @param referenceSql the referenceSql to set
	 */
	public void setReferenceSql(String referenceSql) {
		this.referenceSql = referenceSql;
	}
	
	
}
