package com.bomwebportal.mob.ccs.dto.ui;

import java.util.HashMap;
import java.util.Map;

public class PaymentMonthyUI implements java.io.Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = -5355317310493025297L;
	
	private String orderId;

	private String payMethodKey;
	
	private String creditCardHolderName;
	
	//M - Cash, C - Credit Card, A-AutoPay
	private String payMethodType;
	
	private String thirdPartyInd;
	
	private String creditCardType;
	
	private String creditCardNum;
	
	private String creditExpiryMonth;
	
	private String creditExpiryYear;
	
	private String creditCardIssueBankCd;
	
	private String creditCardIssueBankName;
	
	private String creditCardDocType;
	
	private String creditCardDocNum;
	
	private String ceksSubmit;
	
	private String username;
	
	private String creditCardIssueBank;
	
	private String creditCardVerifiedInd; //add by herbert 20120201 

	private String bankAcctHolderIdType; //autoPay
	private String bankAcctHolderIdNum; //autoPay
	private String bankCode; //autoPay
	private String branchCode; //autoPay
	private String bankAcctHolderName; //autoPay
	private String autopayUpperLimitAmt; //autoPay
	private String bankAcctNum;	 //autoPay
	private String autopayApplDateStr; //autoPay
	private String bankName; //for summary 
	private String branchName; //for summary 
	
	private String creditCardInd;
	
	private String actionType; //add by herbert 20120309
	
	
	private String mFlag;
	private String ccFlag;
	private String aFlag;
	
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
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
	private boolean byPassValidation=false;// add by wilson 20120301, for draft-pre-pend order
	public boolean isByPassValidation() {
		return byPassValidation;
	}
	public void setByPassValidation(boolean byPassValidation) {
		this.byPassValidation = byPassValidation;
	}
	public boolean getByPassValidation() {
		return byPassValidation;
	}
	
	public String getBankAcctHolderIdType() {
		return bankAcctHolderIdType;
	}

	public String getBankAcctHolderIdNum() {
		return bankAcctHolderIdNum;
	}

	public String getBankCode() {
		return bankCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getBankAcctHolderName() {
		return bankAcctHolderName;
	}

	public String getAutopayUpperLimitAmt() {
		return autopayUpperLimitAmt;
	}

	public String getBankAcctNum() {
		return bankAcctNum;
	}

	public String getAutopayApplDateStr() {
		return autopayApplDateStr;
	}

	public void setBankAcctHolderIdType(String bankAcctHolderIdType) {
		this.bankAcctHolderIdType = bankAcctHolderIdType;
	}

	public void setBankAcctHolderIdNum(String bankAcctHolderIdNum) {
		this.bankAcctHolderIdNum = bankAcctHolderIdNum;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setBankAcctHolderName(String bankAcctHolderName) {
		this.bankAcctHolderName = bankAcctHolderName;
	}

	public void setAutopayUpperLimitAmt(String autopayUpperLimitAmt) {
		this.autopayUpperLimitAmt = autopayUpperLimitAmt;
	}

	public void setBankAcctNum(String bankAcctNum) {
		this.bankAcctNum = bankAcctNum;
	}

	public void setAutopayApplDateStr(String autopayApplDateStr) {
		this.autopayApplDateStr = autopayApplDateStr;
	}

	public String getCreditCardIssueBank() {
		return creditCardIssueBank;
	}

	public void setCreditCardIssueBank(String creditCardIssueBank) {
		this.creditCardIssueBank = creditCardIssueBank;
	}

	public String getPayMethodKey() {
		return payMethodKey;
	}

	public void setPayMethodKey(String payMethodKey) {
		this.payMethodKey = payMethodKey;
	}

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

	public String getCreditCardDocType() {
		return creditCardDocType;
	}

	public void setCreditCardDocType(String creditCardDocType) {
		this.creditCardDocType = creditCardDocType;
	}
	
	public String getCreditCardDocNum() {
		return creditCardDocNum;
	}

	public void setCreditCardDocNum(String creditCardDocNum) {
		this.creditCardDocNum = creditCardDocNum;
	}

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

	public String getCreditCardVerifiedInd() {
		return creditCardVerifiedInd;
	}

	public void setCreditCardVerifiedInd(String creditCardVerifiedInd) {
		this.creditCardVerifiedInd = creditCardVerifiedInd;
	}

	public PaymentMonthyUI() {
		this.payMethodKey = "-1";
	}
	public String getCreditCardInd() {
		return creditCardInd;
	}
	public void setCreditCardInd(String creditCardInd) {
		this.creditCardInd = creditCardInd;
	}

	public String getmFlag() {
		return mFlag;
	}

	public void setmFlag(String mFlag) {
		this.mFlag = mFlag;
	}

	public String getCcFlag() {
		return ccFlag;
	}

	public void setCcFlag(String ccFlag) {
		this.ccFlag = ccFlag;
	}

	public String getaFlag() {
		return aFlag;
	}

	public void setaFlag(String aFlag) {
		this.aFlag = aFlag;
	}
	
//	public String getPaymentCombination() {
//	    return paymentCombination;
//	}
//
//	public void setPaymentCombination(String paymentCombination) {
//	    this.paymentCombination = paymentCombination;
//	}
//
//	public String getInAdvanceAmount() {
//	    return inAdvanceAmount;
//	}
//
//	public void setInAdvanceAmount(String inAdvanceAmount) {
//	    this.inAdvanceAmount = inAdvanceAmount;
//	}
	
	
}
