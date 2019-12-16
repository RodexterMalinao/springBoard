package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class UpgradeEyeInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4097131128463244808L;
	
	private String contractPeriod;
	private String suggestSrd;
	private String suggestSrdReason;
	private String prepayment;
	private String extendContractPeriod;
	private String adminChargeItemId;
	private String adminCharge;
	private String returnDeviceInd;
	private String cancelChargeItemId;
	private String cancelCharge;
	private String prepaymentRemark;
	private String warningMsg;
	
	public String getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getSuggestSrd() {
		return suggestSrd;
	}
	public void setSuggestSrd(String suggestSrd) {
		this.suggestSrd = suggestSrd;
	}
	public String getSuggestSrdReason() {
		return suggestSrdReason;
	}
	public void setSuggestSrdReason(String suggestSrdReason) {
		this.suggestSrdReason = suggestSrdReason;
	}
	public String getPrepayment() {
		return prepayment;
	}
	public void setPrepayment(String prepayment) {
		this.prepayment = prepayment;
	}
	public String getExtendContractPeriod() {
		return extendContractPeriod;
	}
	public void setExtendContractPeriod(String extendContractPeriod) {
		this.extendContractPeriod = extendContractPeriod;
	}
	public String getAdminChargeItemId() {
		return adminChargeItemId;
	}
	public void setAdminChargeItemId(String adminChargeItemId) {
		this.adminChargeItemId = adminChargeItemId;
	}
	public String getAdminCharge() {
		return adminCharge;
	}
	public void setAdminCharge(String adminCharge) {
		this.adminCharge = adminCharge;
	}
	public String getReturnDeviceInd() {
		return returnDeviceInd;
	}
	public void setReturnDeviceInd(String returnDeviceInd) {
		this.returnDeviceInd = returnDeviceInd;
	}
	public String getCancelChargeItemId() {
		return cancelChargeItemId;
	}
	public void setCancelChargeItemId(String cancelChargeItemId) {
		this.cancelChargeItemId = cancelChargeItemId;
	}
	public String getCancelCharge() {
		return cancelCharge;
	}
	public void setCancelCharge(String cancelCharge) {
		this.cancelCharge = cancelCharge;
	}
	public String getPrepaymentRemark() {
		return prepaymentRemark;
	}
	public void setPrepaymentRemark(String prepaymentRemark) {
		this.prepaymentRemark = prepaymentRemark;
	}
	public String getWarningMsg() {
		return warningMsg;
	}
	public void setWarningMsg(String warningMsg) {
		this.warningMsg = warningMsg;
	}

}
