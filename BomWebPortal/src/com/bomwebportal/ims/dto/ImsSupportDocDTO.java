package com.bomwebportal.ims.dto;
import java.io.Serializable;

public class ImsSupportDocDTO implements Serializable{
	
	private static final long serialVersionUID = 5248514987697312232L;

	private String CreditCardNum;
	private String Dis_Mode;
	private String Pay_Mtd_Type;
	private String THIRD_PARTY_IND;
	private String CC_HOLD_NAME;
	private String CC_EXP_DATE;
	

	public String getPay_Mtd_Type() {
		return Pay_Mtd_Type;
	}
	public void setPay_Mtd_Type(String pay_Mtd_Type) {
		Pay_Mtd_Type = pay_Mtd_Type;
	}
	public String getDis_Mode() {
		return Dis_Mode;
	}
	public void setDis_Mode(String disMode) {
		Dis_Mode = disMode;
	}

	public String getCreditCardNum() {
		return CreditCardNum;
	}
	public void setCreditCardNum(String creditCardNum) {
		CreditCardNum = creditCardNum;
	}
	public void setTHIRD_PARTY_IND(String tHIRD_PARTY_IND) {
		THIRD_PARTY_IND = tHIRD_PARTY_IND;
	}
	public String getTHIRD_PARTY_IND() {
		return THIRD_PARTY_IND;
	}
	public void setCC_HOLD_NAME(String cC_HOLD_NAME) {
		CC_HOLD_NAME = cC_HOLD_NAME;
	}
	public String getCC_HOLD_NAME() {
		return CC_HOLD_NAME;
	}
	public void setCC_EXP_DATE(String cC_EXP_DATE) {
		CC_EXP_DATE = cC_EXP_DATE;
	}
	public String getCC_EXP_DATE() {
		return CC_EXP_DATE;
	}
	
}
