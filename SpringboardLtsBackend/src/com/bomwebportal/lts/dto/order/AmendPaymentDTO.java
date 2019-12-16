package com.bomwebportal.lts.dto.order;


public class AmendPaymentDTO extends AmendCategoryLtsDTO {

	private static final long serialVersionUID = -9020471637473829857L;

	private String ccThirdPartyInd = null;
	private String ccType = null;
	private String ccNum = null;
	private String ccHoldName = null;
	private String ccExpDate = null;
	private String ccIssueBank = null;
	private String ccIdDocType = null;
	private String ccIdDocNo = null;
	private String ccVerifiedInd = null;
	private String faxSerialNum = null;

	
	public String getCcThirdPartyInd() {
		return ccThirdPartyInd;
	}

	public void setCcThirdPartyInd(String ccThirdPartyInd) {
		this.ccThirdPartyInd = ccThirdPartyInd;
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

	public String getCcVerifiedInd() {
		return ccVerifiedInd;
	}

	public void setCcVerifiedInd(String ccVerifiedInd) {
		this.ccVerifiedInd = ccVerifiedInd;
	}

	public String getFaxSerialNum() {
		return this.faxSerialNum;
	}

	public void setFaxSerialNum(String pFaxSerialNum) {
		this.faxSerialNum = pFaxSerialNum;
	}
	
}
