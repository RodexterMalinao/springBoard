package com.bomwebportal.mob.ccs.dto.ui;

import java.util.HashMap;
import java.util.Map;


public class PaymentUpFrontUI implements java.io.Serializable{
    
	/**
     * 
     */
    private static final long serialVersionUID = 3346842962639632787L;
		
	private String orderId;

//	private String payMethodKey;
	
	private String creditCardHolderName;
	
	//M - Cash, C - Credit Card, I Credit Card Installement
	private String payMethodType;
	
	private String thirdPartyInd;
	
	private String creditCardType;
	
	private String creditCardNum;
	
	private String creditExpiryMonth;
	
	private String creditExpiryYear;
	
//	private String creditExpiryDate;
	
	private String creditCardIssueBankCd;
	
	private String creditCardIssueBankName;
	
//	private String creditCardDocType;
//	
//	private String creditCardDocNum;
	
	private String ceksSubmit;
	
	private String username;
	
	private String creditCardIssueBank;

//	private String bankAcctHolderIdType; //autoPay
//	private String bankAcctHolderIdNum; //autoPay
	private String bankCode; //autoPay
//	private String branchCode; //autoPay
//	private String bankAcctHolderName; //autoPay
//	private String autopayUpperLimitAmt; //autoPay
//	private String bankAcctNum;	 //autoPay
//	private String autopayApplDateStr; //autoPay
//	private Date autopayApplDate ; //autoPay
	private String bankName; //for summary display
	private String branchName; //for summary display
	private String paymentCombination;
	private String inAdvanceAmount;
	private String ccInstSchedule;
	private String upfrontAmt; 
	private String ccInstScheduleFlag;
	private String ccFlag;
	
	//private String creditCardInd;
	private String ccLockFlag;
	private String workStatus;
	
	private String recallCCInstWarnInd; //add by herbert 20120424
	
	private String creditCardVerifiedInd; //add by herbert 20120425
	
	private String actionType; //add by herbert 20120628
	
	private boolean byPassValidation=false;// add by wilson 20120301, for draft-pre-pend order
	
	private String upfrontCCInd;
	
	/**
	 * An object map key to DTO value
	 */
	private Map<String, Object> objectsMap;

	public Map<String, Object> getObjectsMap() {
		return objectsMap;
	}

	public void setObjectsMap(Map<String, Object> objectsMap) {
		this.objectsMap = objectsMap;
	}

	/**
	 * Get specific DTO object value which map to certain key
	 * 
	 * @param key
	 * @return DTO object
	 */
	public Object getValue(String key) {
		if (this.objectsMap == null || this.objectsMap.isEmpty()) {
			return null;
		} else {
			return this.objectsMap.get(key);
		}
	}

	/**
	 * Set specific DTO object value into map which match with a unique key
	 */
	public void setValue(String key, Object value) {
		if (this.objectsMap == null) {
			objectsMap = new HashMap<String, Object>();
		}

		objectsMap.put(key, value);
	}
	public boolean isByPassValidation() {
		return byPassValidation;
	}

	public void setByPassValidation(boolean byPassValidation) {
		this.byPassValidation = byPassValidation;
	}
	
	
	public PaymentUpFrontUI(){
		this.ccInstScheduleFlag = "N";
		this.ccInstSchedule = "";
		this.ccLockFlag = "N";
		this.recallCCInstWarnInd = "N";
		this.creditCardVerifiedInd="";
	}
	
	
//	public Date getAutopayApplDate() {
//		return autopayApplDate;
//	}
//
//	public void setAutopayApplDate(Date autopayApplDate) {
//		this.autopayApplDate = autopayApplDate;
//	}
//
//	public String getBankAcctHolderIdType() {
//		return bankAcctHolderIdType;
//	}
//
//	public String getBankAcctHolderIdNum() {
//		return bankAcctHolderIdNum;
//	}

	public String getBankCode() {
		return bankCode;
	}

//	public String getBranchCode() {
//		return branchCode;
//	}
//
//	public String getBankAcctHolderName() {
//		return bankAcctHolderName;
//	}
//
//	public String getAutopayUpperLimitAmt() {
//		return autopayUpperLimitAmt;
//	}
//
//	public String getBankAcctNum() {
//		return bankAcctNum;
//	}
//
//	public String getAutopayApplDateStr() {
//		return autopayApplDateStr;
//	}
//
//	public void setBankAcctHolderIdType(String bankAcctHolderIdType) {
//		this.bankAcctHolderIdType = bankAcctHolderIdType;
//	}
//
//	public void setBankAcctHolderIdNum(String bankAcctHolderIdNum) {
//		this.bankAcctHolderIdNum = bankAcctHolderIdNum;
//	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

//	public void setBranchCode(String branchCode) {
//		this.branchCode = branchCode;
//	}
//
//	public void setBankAcctHolderName(String bankAcctHolderName) {
//		this.bankAcctHolderName = bankAcctHolderName;
//	}
//
//	public void setAutopayUpperLimitAmt(String autopayUpperLimitAmt) {
//		this.autopayUpperLimitAmt = autopayUpperLimitAmt;
//	}
//
//	public void setBankAcctNum(String bankAcctNum) {
//		this.bankAcctNum = bankAcctNum;
//	}
//
//	public void setAutopayApplDateStr(String autopayApplDateStr) {
//		this.autopayApplDateStr = autopayApplDateStr;
//	}

	public String getCreditCardIssueBank() {
		return creditCardIssueBank;
	}

	public void setCreditCardIssueBank(String creditCardIssueBank) {
		this.creditCardIssueBank = creditCardIssueBank;
	}

//	public String getPayMethodKey() {
//		return payMethodKey;
//	}
//
//	public void setPayMethodKey(String payMethodKey) {
//		this.payMethodKey = payMethodKey;
//	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCreditCardIssueBankCd() {
		return creditCardIssueBankCd;
	}

	public void setCreditCardIssueBankCd(String creditCardIssueBankCd) {
		this.creditCardIssueBankCd = creditCardIssueBankCd;
	}
	
	public String getCreditCardType() {
		return creditCardType;
	}

	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}
	
	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}

	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = creditCardHolderName;
	}
	
	public String getPayMethodType() {
		return payMethodType;
	}

	public void setPayMethodType(String payMethodType) {
		this.payMethodType = payMethodType;
	}
	
	public String getThirdPartyInd() {
		return thirdPartyInd;
	}

	public void setThirdPartyInd(String thirdPartyInd) {
		this.thirdPartyInd = thirdPartyInd;
	}
	
//	public String getCreditExpiryDate() {
//		return creditExpiryDate;
//	}
//
//	public void setCreditExpiryDate(String creditExpiryDate) {
//		this.creditExpiryDate = creditExpiryDate;
//	}
	
	public String getCreditExpiryMonth() {
		return creditExpiryMonth;
	}

	public void setCreditExpiryMonth(String creditExpiryMonth) {
		this.creditExpiryMonth = creditExpiryMonth;
	}

	public String getCreditExpiryYear() {
		return creditExpiryYear;
	}

	public void setCreditExpiryYear(String creditExpiryYear) {
		this.creditExpiryYear = creditExpiryYear;
	}

//	public String getCreditCardDocType() {
//		return creditCardDocType;
//	}
//
//	public void setCreditCardDocType(String creditCardDocType) {
//		this.creditCardDocType = creditCardDocType;
//	}
//	
//	public String getCreditCardDocNum() {
//		return creditCardDocNum;
//	}
//
//	public void setCreditCardDocNum(String creditCardDocNum) {
//		this.creditCardDocNum = creditCardDocNum;
//	}

	public String getCeksSubmit() {
		return ceksSubmit;
	}

	public void setCeksSubmit(String ceksSubmit) {
		this.ceksSubmit = ceksSubmit;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getCreditCardIssueBankName() {
		return creditCardIssueBankName;
	}

	public void setCreditCardIssueBankName(String creditCardIssueBankName) {
		this.creditCardIssueBankName = creditCardIssueBankName;
	}

	public String getPaymentCombination() {
	    return paymentCombination;
	}

	public void setPaymentCombination(String paymentCombination) {
	    this.paymentCombination = paymentCombination;
	}

	public String getInAdvanceAmount() {
	    return inAdvanceAmount;
	}

	public void setInAdvanceAmount(String inAdvanceAmount) {
	    this.inAdvanceAmount = inAdvanceAmount;
	}

	public String getCcInstSchedule() {
		return ccInstSchedule;
	}

	public void setCcInstSchedule(String ccInstSchedule) {
		this.ccInstSchedule = ccInstSchedule;
	}
	
	public String getCcFlag() {
		return ccFlag;
	}

	public void setCcFlag(String ccFlag) {
		this.ccFlag = ccFlag;
	}

	public String getUpfrontAmt() {
		return upfrontAmt;
	}

	public void setUpfrontAmt(String upfrontAmt) {
		this.upfrontAmt = upfrontAmt;
	}

	public String getCcInstScheduleFlag() {
		return ccInstScheduleFlag;
	}

	public void setCcInstScheduleFlag(String ccInstScheduleFlag) {
		this.ccInstScheduleFlag = ccInstScheduleFlag;
	}

	public String getCcLockFlag() {
		return ccLockFlag;
	}

	public void setCcLockFlag(String ccLockFlag) {
		this.ccLockFlag = ccLockFlag;
	}

	public String getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	/*public String getCreditCardInd() {
		return creditCardInd;
	}

	public void setCreditCardInd(String creditCardInd) {
		this.creditCardInd = creditCardInd;
	}*/

	public String getRecallCCInstWarnInd() {
		return recallCCInstWarnInd;
	}

	public void setRecallCCInstWarnInd(String recallCCInstWarnInd) {
		this.recallCCInstWarnInd = recallCCInstWarnInd;
	}

	public String getCreditCardVerifiedInd() {
		return creditCardVerifiedInd;
	}

	public void setCreditCardVerifiedInd(String creditCardVerifiedInd) {
		this.creditCardVerifiedInd = creditCardVerifiedInd;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getUpfrontCCInd() {
		return upfrontCCInd;
	}

	public void setUpfrontCCInd(String upfrontCCInd) {
		this.upfrontCCInd = upfrontCCInd;
	}

    
}
