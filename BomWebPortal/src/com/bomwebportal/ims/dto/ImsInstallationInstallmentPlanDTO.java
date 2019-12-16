package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.BasketDTO;


public class ImsInstallationInstallmentPlanDTO implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 629210834742422558L;
	
	/*
	 * IMS related
	 */
	
	private String OneTimeFee;
	private String InstallmentPlanAmt;
	private String InstallmentPlanMnth;
	
	private String installPlanValue;
	private String installPlanDisplay;
	private String otChrgType;
	private String installOtCode;//gary
	private String installOTDesc_en;//gary
	private String installOTDesc_zh;//gary
	private String InstallmentCode;//gary
	private String installOtQty;
	
	/*
	 * DTO members
	 */
	
	public ImsInstallationInstallmentPlanDTO(){
		
	}

	public String getOneTimeFee() {
		return OneTimeFee;
	}

	public void setOneTimeFee(String oneTimeFee) {
		OneTimeFee = oneTimeFee;
	}

	
	public String getInstallPlanValue() {
		return installPlanValue;
	}

	public void setInstallPlanValue(String installPlanValue) {
		this.installPlanValue = installPlanValue;
	}

	public String getInstallPlanDisplay() {
		return installPlanDisplay;
	}

	public void setInstallPlanDisplay(String installPlanDisplay) {
		this.installPlanDisplay = installPlanDisplay;
	}
	 
	
	public String getInstallmentPlanAmt() {
		return InstallmentPlanAmt;
	}


	public void setInstallmentPlanAmt(String installmentPlanAmt) {
		InstallmentPlanAmt = installmentPlanAmt;
	}


	public String getInstallmentPlanMnth() {
		return InstallmentPlanMnth;
	}


	public void setInstallmentPlanMnth(String installmentPlanMnth) {
		InstallmentPlanMnth = installmentPlanMnth;
	}

	public void setOtChrgType(String otChrgType) {
		this.otChrgType = otChrgType;
	}

	public String getOtChrgType() {
		return otChrgType;
	}

	public String getInstallOtCode() {
		return installOtCode;
	}

	public void setInstallOtCode(String installOtCode) {
		this.installOtCode = installOtCode;
	}

	public String getInstallOTDesc_en() {
		return installOTDesc_en;
	}

	public void setInstallOTDesc_en(String installOTDesc_en) {
		this.installOTDesc_en = installOTDesc_en;
	}

	public String getInstallOTDesc_zh() {
		return installOTDesc_zh;
	}

	public void setInstallOTDesc_zh(String installOTDesc_zh) {
		this.installOTDesc_zh = installOTDesc_zh;
	}

	public void setInstallmentCode(String installmentCode) {
		InstallmentCode = installmentCode;
	}

	public String getInstallmentCode() {
		return InstallmentCode;
	}

	public void setInstallOtQty(String installOtQty) {
		this.installOtQty = installOtQty;
	}

	public String getInstallOtQty() {
		return installOtQty;
	}
	
}
