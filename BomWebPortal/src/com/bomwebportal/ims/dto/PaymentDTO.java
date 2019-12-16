package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class PaymentDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6058627373650847032L;
	
	private String OrderId;
	private String CustNo;
	private String PayMtdKey;
	private String AcctNo;
	private Date AutopayAppDate;
	private String AutopayUpLimAmt;
	private String BAcctNo;
	private String BAcctHoldName;
	private String BAcctHoldIdType;
	private String BAcctHoldIdNum;
	private String BranchCd;
	private String BankCd;
	private String PayMtdType;
	private String ThirdPartyInd;
	private String CcType;
	private String CcNum;
	private String CcHoldName;
	private String CcExpDate;
	private String CcIssueBank;
	private String CcIdDocType;
	private String CcIdDocNo;	
	private String CcVerified;
	private String DtlId;
	private String Action;
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;

	private String existingPaymentMethod;
	private String disallowPaymentMethod;
	private String isNewCard;
	
	
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
	public String getPayMtdKey() {
		return PayMtdKey;
	}
	public void setPayMtdKey(String payMtdKey) {
		PayMtdKey = payMtdKey;
	}
	public String getAcctNo() {
		return AcctNo;
	}
	public void setAcctNo(String acctNo) {
		AcctNo = acctNo;
	}
	public Date getAutopayAppDate() {
		return AutopayAppDate;
	}
	public void setAutopayAppDate(Date autopayAppDate) {
		AutopayAppDate = autopayAppDate;
	}
	public String getAutopayUpLimAmt() {
		return AutopayUpLimAmt;
	}
	public void setAutopayUpLimAmt(String autopayUpLimAmt) {
		AutopayUpLimAmt = autopayUpLimAmt;
	}
	public String getBAcctNo() {
		return BAcctNo;
	}
	public void setBAcctNo(String bAcctNo) {
		BAcctNo = bAcctNo;
	}
	public String getBAcctHoldName() {
		return BAcctHoldName;
	}
	public void setBAcctHoldName(String bAcctHoldName) {
		BAcctHoldName = bAcctHoldName;
	}
	public String getBAcctHoldIdType() {
		return BAcctHoldIdType;
	}
	public void setBAcctHoldIdType(String bAcctHoldIdType) {
		BAcctHoldIdType = bAcctHoldIdType;
	}
	public String getBAcctHoldIdNum() {
		return BAcctHoldIdNum;
	}
	public void setBAcctHoldIdNum(String bAcctHoldIdNum) {
		BAcctHoldIdNum = bAcctHoldIdNum;
	}
	public String getBranchCd() {
		return BranchCd;
	}
	public void setBranchCd(String branchCd) {
		BranchCd = branchCd;
	}
	public String getBankCd() {
		return BankCd;
	}
	public void setBankCd(String bankCd) {
		BankCd = bankCd;
	}
	public String getPayMtdType() {
		return PayMtdType;
	}
	public void setPayMtdType(String payMtdType) {
		PayMtdType = payMtdType;
	}
	public String getThirdPartyInd() {
		return ThirdPartyInd;
	}
	public void setThirdPartyInd(String thirdPartyInd) {
		ThirdPartyInd = thirdPartyInd;
	}
	public String getCcType() {
		return CcType;
	}
	public void setCcType(String ccType) {
		CcType = ccType;
	}
	public String getCcNum() {
		return CcNum;
	}
	public void setCcNum(String ccNum) {
		CcNum = ccNum;
	}
	public String getCcHoldName() {
		return CcHoldName;
	}
	public void setCcHoldName(String ccHoldName) {
		CcHoldName = ccHoldName;
	}
	public String getCcExpDate() {
		return CcExpDate;
	}
	public void setCcExpDate(String ccExpDate) {
		CcExpDate = ccExpDate;
	}
	public String getCcIssueBank() {
		return CcIssueBank;
	}
	public void setCcIssueBank(String ccIssueBank) {
		CcIssueBank = ccIssueBank;
	}
	public String getCcIdDocType() {
		return CcIdDocType;
	}
	public void setCcIdDocType(String ccIdDocType) {
		CcIdDocType = ccIdDocType;
	}
	public String getCcIdDocNo() {
		return CcIdDocNo;
	}
	public void setCcIdDocNo(String ccIdDocNo) {
		CcIdDocNo = ccIdDocNo;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getCcVerified() {
		return CcVerified;
	}
	public void setCcVerified(String ccVerified) {
		CcVerified = ccVerified;
	}
	public String getDtlId() {
		return DtlId;
	}
	public void setDtlId(String dtlId) {
		DtlId = dtlId;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
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

	public String getExistingPaymentMethod() {
		return existingPaymentMethod;
	}

	public void setExistingPaymentMethod(String existingPaymentMethod) {
		this.existingPaymentMethod = existingPaymentMethod;
	}

	public String getDisallowPaymentMethod() {
		return disallowPaymentMethod;
	}

	public void setDisallowPaymentMethod(String disallowPaymentMethod) {
		this.disallowPaymentMethod = disallowPaymentMethod;
	}
	public String getIsNewCard() {
		return isNewCard;
	}
	public void setIsNewCard(String isNewCard) {
		this.isNewCard = isNewCard;
	}
	

}
