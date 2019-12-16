package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.util.LtsDateFormatHelper;

public class IddCallPlanProfileLtsDTO implements Serializable {

	private static final long serialVersionUID = -6208805685689832076L;

	//Fields from IDD FFP API
//	private boolean remove; //Enter Partial WQ
	private String action;
	private String planCd = null;
	private String description = null;
	private String planCharge = null;

	private String contractStartDate;
	private String contractEndDate;
	private String effStartDate;
	private String effEndDate;
	private String grossEffPrice;
	private String tpExpiredMonths;
	private String penaltyHandling;
	
	private String planType; 
	private String planHolderType; 
	private String planHolder; 
	private String dca;
	private String giftInd;
	private boolean giftType;

	//SB use
	private String remainContract;
	private boolean remainContractGt3Mths;
	private String newDca;
	private boolean dcaSameAsSrvAcct;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getPlanCd() {
		return planCd;
	}
	public void setPlanCd(String planCd) {
		this.planCd = planCd;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPlanCharge() {
		return planCharge;
	}
	public void setPlanCharge(String planCharge) {
		this.planCharge = planCharge;
	}
	public String getEffStartDate() {
		return effStartDate;
	}
	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}
	public String getEffEndDate() {
		return effEndDate;
	}
	public void setEffEndDate(String effEndDate) {
		this.effEndDate = effEndDate;
	}
	public String getGrossEffPrice() {
		return grossEffPrice;
	}
	public void setGrossEffPrice(String grossEffPrice) {
		this.grossEffPrice = grossEffPrice;
	}
	public String getTpExpiredMonths() {
		return tpExpiredMonths;
	}
	public void setTpExpiredMonths(String tpExpiredMonths) {
		this.tpExpiredMonths = tpExpiredMonths;
	}
	public String getPenaltyHandling() {
		return penaltyHandling;
	}
	public void setPenaltyHandling(String penaltyHandling) {
		this.penaltyHandling = penaltyHandling;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public String getPlanHolderType() {
		return planHolderType;
	}
	public void setPlanHolderType(String planHolderType) {
		this.planHolderType = planHolderType;
	}
	public String getPlanHolder() {
		return planHolder;
	}
	public void setPlanHolder(String planHolder) {
		this.planHolder = planHolder;
	}
	public String getDca() {
		return dca;
	}
	public void setDca(String dca) {
		this.dca = dca;
	}
	public String getGiftInd() {
		return giftInd;
	}
	public void setGiftInd(String giftInd) {
		this.giftInd = giftInd;
	}
	public boolean isGiftType() {
		return giftType;
	}
	public void setGiftType(boolean giftType) {
		this.giftType = giftType;
	}
	public String getRemainContract() {
		return remainContract;
	}
	public void setRemainContract(String remainContract) {
		this.remainContract = remainContract;
	}
	public boolean isRemainContractGt3Mths() {
		return remainContractGt3Mths;
	}
	public void setRemainContractGt3Mths(boolean remainContractGt3Mths) {
		this.remainContractGt3Mths = remainContractGt3Mths;
	}
	public String getNewDca() {
		return newDca;
	}
	public void setNewDca(String newDca) {
		this.newDca = newDca;
	}
	public String getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getEffStartDateForDisplay() {
		return StringUtils.isEmpty(effStartDate) || StringUtils.equals(effStartDate, "00000000") 
				? "--" : LtsDateFormatHelper.reformatDate(effStartDate, "yyyyMMdd", "yyyy/MM/dd");
	}
	public String getEffEndDateForDisplay() {
		return StringUtils.isEmpty(effEndDate) || StringUtils.equals(effEndDate, "00000000") 
				? "--" : LtsDateFormatHelper.reformatDate(effEndDate, "yyyyMMdd", "yyyy/MM/dd");
	}
	public String getContractStartDateForDisplay() {
		return StringUtils.isEmpty(contractStartDate) || StringUtils.equals(contractStartDate, "00000000") 
				? "--" : LtsDateFormatHelper.reformatDate(contractStartDate, "yyyyMMdd", "yyyy/MM/dd");
	}
	public String getContractEndDateForDisplay() {
		return StringUtils.isEmpty(contractEndDate) || StringUtils.equals(contractEndDate, "00000000") 
				? "--" : LtsDateFormatHelper.reformatDate(contractEndDate, "yyyyMMdd", "yyyy/MM/dd");
	}
	public boolean isDcaSameAsSrvAcct() {
		return dcaSameAsSrvAcct;
	}
	public void setDcaSameAsSrvAcct(boolean dcaSameAsSrvAcct) {
		this.dcaSameAsSrvAcct = dcaSameAsSrvAcct;
	}
}
