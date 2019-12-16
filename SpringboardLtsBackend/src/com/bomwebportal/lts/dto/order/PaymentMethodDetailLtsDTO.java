package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class PaymentMethodDetailLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = 1100117925713713373L;

	private String payMtdKey = null;
	private String autopayAppDate = null;
	private String autopayUpLimAmt = null;
	private String bankAcctHoldName = null;
	private String bankAcctNo = null;
	private String bankAcctHoldType = null;
	private String bankAcctHoldNum = null;
	private String branchCd = null;
	private String bankCd = null;
	private String payMtdType;
	private String thirdPartyInd;
	private String ccType = null;
	private String ccNum = null;
	private String ccHoldName = null;
	private String ccExpDate = null;
	private String ccIssueBank = null;
	private String ccIdDocType = null;
	private String ccIdDocNo = null;
	private String action = null;
	private String ccVerifiedInd = null;
	private String salesMemoNum = null;
	private String prepayAmt = null;
	private String autopayStatementInd = null; 	
	private String termCd = null;
	private String termDate = null;

	public String getPayMtdKey() {
		return payMtdKey;
	}

	public void setPayMtdKey(String payMtdKey) {
		this.payMtdKey = payMtdKey;
	}

	public String getAutopayAppDate() {
		return autopayAppDate;
	}

	public void setAutopayAppDate(String autopayAppDate) {
		this.autopayAppDate = autopayAppDate;
	}

	public String getAutopayUpLimAmt() {
		return autopayUpLimAmt;
	}

	public void setAutopayUpLimAmt(String autopayUpLimAmt) {
		this.autopayUpLimAmt = autopayUpLimAmt;
	}

	public String getBankAcctHoldName() {
		return bankAcctHoldName;
	}

	public void setBankAcctHoldName(String bankAcctHoldName) {
		this.bankAcctHoldName = bankAcctHoldName;
	}

	public String getBankAcctNo() {
		return bankAcctNo;
	}

	public void setBankAcctNo(String bankAcctNo) {
		this.bankAcctNo = bankAcctNo;
	}

	public String getBankAcctHoldType() {
		return bankAcctHoldType;
	}

	public void setBankAcctHoldType(String bankAcctHoldType) {
		this.bankAcctHoldType = bankAcctHoldType;
	}

	public String getBankAcctHoldNum() {
		return bankAcctHoldNum;
	}

	public void setBankAcctHoldNum(String bankAcctHoldNum) {
		this.bankAcctHoldNum = bankAcctHoldNum;
	}

	public String getBranchCd() {
		return branchCd;
	}

	public void setBranchCd(String branchCd) {
		this.branchCd = branchCd;
	}

	public String getBankCd() {
		return bankCd;
	}

	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}

	public String getPayMtdType() {
		return payMtdType;
	}

	public void setPayMtdType(String payMtdType) {
		this.payMtdType = payMtdType;
	}

	public String getThirdPartyInd() {
		return thirdPartyInd;
	}

	public void setThirdPartyInd(String thirdPartyInd) {
		this.thirdPartyInd = thirdPartyInd;
	}

	public String getCcType() {
		return ccType;
	}

	public void setCcType(String ccType) {
		this.ccType = ccType;
	}

	public String getCcNum() {
		return ccNum;
	}

	public void setCcNum(String ccNum) {
		this.ccNum = ccNum;
	}

	public String getCcHoldName() {
		return ccHoldName;
	}

	public void setCcHoldName(String ccHoldName) {
		this.ccHoldName = ccHoldName;
	}

	public String getCcExpDate() {
		return ccExpDate;
	}

	public void setCcExpDate(String ccExpDate) {
		this.ccExpDate = ccExpDate;
	}

	public String getCcIssueBank() {
		return ccIssueBank;
	}

	public void setCcIssueBank(String ccIssueBank) {
		this.ccIssueBank = ccIssueBank;
	}

	public String getCcIdDocType() {
		return ccIdDocType;
	}

	public void setCcIdDocType(String ccIdDocType) {
		this.ccIdDocType = ccIdDocType;
	}

	public String getCcIdDocNo() {
		return ccIdDocNo;
	}

	public void setCcIdDocNo(String ccIdDocNo) {
		this.ccIdDocNo = ccIdDocNo;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCcVerifiedInd() {
		return ccVerifiedInd;
	}

	public void setCcVerifiedInd(String ccVerifiedInd) {
		this.ccVerifiedInd = ccVerifiedInd;
	}

	public String getSalesMemoNum() {
		return this.salesMemoNum;
	}

	public void setSalesMemoNum(String pSalesMemoNum) {
		this.salesMemoNum = pSalesMemoNum;
	}

	public String getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(String prepayAmt) {
		this.prepayAmt = prepayAmt;
	}

	public String getAutopayStatementInd() {
		return autopayStatementInd;
	}

	public void setAutopayStatementInd(String autopayStatementInd) {
		this.autopayStatementInd = autopayStatementInd;
	}

	public String getTermCd() {
		return termCd;
	}

	public void setTermCd(String termCd) {
		this.termCd = termCd;
	}

	public String getTermDate() {
		return termDate;
	}

	public void setTermDate(String termDate) {
		this.termDate = termDate;
	}
	
}
