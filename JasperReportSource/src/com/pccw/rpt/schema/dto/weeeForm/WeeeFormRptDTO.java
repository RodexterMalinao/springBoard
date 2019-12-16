package com.pccw.rpt.schema.dto.weeeForm;

import com.pccw.rpt.schema.dto.ReportDTO;

public class WeeeFormRptDTO extends ReportDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9154361561743977820L;
	
	
	private String epdAChkBox;
	private String epdFChkBox;
	private String epdUCChkBox;
	private String orderNum;
	private String appDate;
	private String custName;
	private String telNum;
	private byte[] signature;
	private String noSignatureR;
	private String signDate;
	private String signoffDate;
	private String reeNum;
	
	
	public String getEpdAChkBox() {
		return epdAChkBox;
	}
	public void setEpdAChkBox(String epdAChkBox) {
		this.epdAChkBox = epdAChkBox;
	}
	public String getEpdFChkBox() {
		return epdFChkBox;
	}
	public void setEpdFChkBox(String epdFChkBox) {
		this.epdFChkBox = epdFChkBox;
	}
	public String getEpdUCChkBox() {
		return epdUCChkBox;
	}
	public void setEpdUCChkBox(String epdUCChkBox) {
		this.epdUCChkBox = epdUCChkBox;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getAppDate() {
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getTelNum() {
		return telNum;
	}
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}
	public byte[] getSignature() {
		return signature;
	}
	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
	public String getNoSignatureR() {
		return noSignatureR;
	}
	public void setNoSignatureR(String noSignatureR) {
		this.noSignatureR = noSignatureR;
	}
	public String getSignoffDate() {
		return signoffDate;
	}
	public void setSignoffDate(String signoffDate) {
		this.signoffDate = signoffDate;
	}
	public String getReeNum() {
		return reeNum;
	}
	public void setReeNum(String reeNum) {
		this.reeNum = reeNum;
	}
}
