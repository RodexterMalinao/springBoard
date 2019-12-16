package com.pccw.rpt.schema.dto.recontractFormSuppl;

import org.apache.commons.lang.StringUtils;

import com.pccw.rpt.schema.dto.ReportDTO;

public class RecontractFormSupplRptDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2279452394893582561L;
	
	private String toCompanyName;
	private String toBusinessRegNo;
	private String toCntctNameTelNo;
	private byte[] toSignChop;
	private String toAuthrzName;
	private String toSignDate;
	
	private String fromCompanyName;
	private String fromBusinessRegNo;
	private String fromCntctNameTelNo;
	private byte[] fromSignChop;
	private String fromAuthrzName;
	private String fromSignDate;
	
	private String toCustTitle;
	private String toCustFamilyName;
	private String toCustGivenName;
	
	private String fromCustTitle;
	private String fromCustFamilyName;
	private String fromCustGivenName;
	
	

	public String getToCompanyName() {
		return toCompanyName;
	}
	public void setToCompanyName(String toCompanyName) {
		this.toCompanyName = toCompanyName;
	}
	public String getToBusinessRegNo() {
		return toBusinessRegNo;
	}
	public void setToBusinessRegNo(String toBusinessRegNo) {
		this.toBusinessRegNo = toBusinessRegNo;
	}
	public String getToCntctNameTelNo() {
		return toCntctNameTelNo;
	}
	public void setToCntctNameTelNo(String toCntctNameTelNo) {
		this.toCntctNameTelNo = toCntctNameTelNo;
	}
	public byte[] getToSignChop() {
		return toSignChop;
	}
	public void setToSignChop(byte[] toSignChop) {
		this.toSignChop = toSignChop;
	}
//	public String getToAuthrzName() {
//		return toAuthrzName;
//	}
//	public void setToAuthrzName(String toAuthrzName) {
//		this.toAuthrzName = toAuthrzName;
//	}
	public String getToSignDate() {
		return toSignDate;
	}
	public void setToSignDate(String toSignDate) {
		this.toSignDate = toSignDate;
	}
	public String getFromCompanyName() {
		return fromCompanyName;
	}
	public void setFromCompanyName(String fromCompanyName) {
		this.fromCompanyName = fromCompanyName;
	}
	public String getFromBusinessRegNo() {
		return fromBusinessRegNo;
	}
	public void setFromBusinessRegNo(String fromBusinessRegNo) {
		this.fromBusinessRegNo = fromBusinessRegNo;
	}
	public String getFromCntctNameTelNo() {
		return fromCntctNameTelNo;
	}
	public void setFromCntctNameTelNo(String fromCntctNameTelNo) {
		this.fromCntctNameTelNo = fromCntctNameTelNo;
	}
	public byte[] getFromSignChop() {
		return fromSignChop;
	}
	public void setFromSignChop(byte[] fromSignChop) {
		this.fromSignChop = fromSignChop;
	}
//	public String getFromAuthrzName() {
//		return fromAuthrzName;
//	}
//	public void setFromAuthrzName(String fromAuthrzName) {
//		this.fromAuthrzName = fromAuthrzName;
//	}
	public String getFromSignDate() {
		return fromSignDate;
	}
	public void setFromSignDate(String fromSignDate) {
		this.fromSignDate = fromSignDate;
	}
	public String getToCustFamilyName() {
		return toCustFamilyName;
	}
	public void setToCustFamilyName(String toCustFamilyName) {
		this.toCustFamilyName = toCustFamilyName;
	}
	public String getToCustGivenName() {
		return toCustGivenName;
	}
	public void setToCustGivenName(String toCustGivenName) {
		this.toCustGivenName = toCustGivenName;
	}
	public String getFromCustFamilyName() {
		return fromCustFamilyName;
	}
	public void setFromCustFamilyName(String fromCustFamilyName) {
		this.fromCustFamilyName = fromCustFamilyName;
	}
	public String getFromCustGivenName() {
		return fromCustGivenName;
	}
	public String getToCustTitle() {
		return toCustTitle;
	}
	public void setToCustTitle(String toCustTitle) {
		this.toCustTitle = toCustTitle;
	}
	public String getFromCustTitle() {
		return fromCustTitle;
	}
	public void setFromCustTitle(String fromCustTitle) {
		this.fromCustTitle = fromCustTitle;
	}
	public void setFromCustGivenName(String fromCustGivenName) {
		this.fromCustGivenName = fromCustGivenName;
	}
//	public String getFromAuthrzName() {
//		return (StringUtils.isEmpty(this.fromCustTitle)? "" : this.fromCustTitle) 
//				+ " " + (StringUtils.isEmpty(this.fromCustFamilyName)? "" : this.fromCustFamilyName) 
//				+ " " + (StringUtils.isEmpty(this.fromCustGivenName)? "" : this.fromCustGivenName);
//	
//	}
//	public String getToAuthrzName() {
//		return (StringUtils.isEmpty(this.toCustTitle)? "" : this.toCustTitle) 
//				+ " " + (StringUtils.isEmpty(this.toCustFamilyName)? "" : this.toCustFamilyName) 
//				+ " " + (StringUtils.isEmpty(this.toCustGivenName)? "" : this.toCustGivenName);
//	}
	public String getToAuthrzName() {
		return toAuthrzName;
	}
	public void setToAuthrzName(String toAuthrzName) {
		this.toAuthrzName = toAuthrzName;
	}
	public String getFromAuthrzName() {
		return fromAuthrzName;
	}
	public void setFromAuthrzName(String fromAuthrzName) {
		this.fromAuthrzName = fromAuthrzName;
	}

}
