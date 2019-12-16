package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class RecontractLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = 1768585587920431524L;
	
	private String acctNum = null;
	private String custNum = null;
	private String idDocType = null;
	private String idDocNum = null;
	private String title = null;
	private String custFirstName = null;
	private String custLastName = null;
	private String companyName = null;
	private String relationship = null;
	private String contactNum = null;
	private String emailAddr = null;
	private String blacklistInd = null;
	private String callingCardInd = null;
	private String mobIddInd = null;
	private String fixedIddInd = null;
	private String transMode = null;
	private String optOut = null;
	
	private PaymentMethodDetailLtsDTO[] paymethods = null;
	private BillingAddressLtsDTO billingAddress = null;

	
	public String getAcctNum() {
		return acctNum;
	}

	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

	public String getCustNum() {
		return custNum;
	}

	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getIdDocNum() {
		return idDocNum;
	}

	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getCustLastName() {
		return custLastName;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getBlacklistInd() {
		return blacklistInd;
	}

	public void setBlacklistInd(String blacklistInd) {
		this.blacklistInd = blacklistInd;
	}

	public String getCallingCardInd() {
		return callingCardInd;
	}

	public void setCallingCardInd(String callingCardInd) {
		this.callingCardInd = callingCardInd;
	}

	public String getMobIddInd() {
		return mobIddInd;
	}

	public void setMobIddInd(String mobIddInd) {
		this.mobIddInd = mobIddInd;
	}

	public String getFixedIddInd() {
		return fixedIddInd;
	}

	public void setFixedIddInd(String fixedIddInd) {
		this.fixedIddInd = fixedIddInd;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}

	public PaymentMethodDetailLtsDTO[] getPaymethods() {
		return paymethods;
	}

	public void setPaymethods(PaymentMethodDetailLtsDTO[] paymethods) {
		this.paymethods = paymethods;
	}

	public BillingAddressLtsDTO getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(BillingAddressLtsDTO billingAddress) {
		this.billingAddress = billingAddress;
	}
	
	public PaymentMethodDetailLtsDTO getFuturePayment() {
		
		if (paymethods == null) {
			return null;
		}
		
		for (int i=0; i < this.paymethods.length; ++i) {
			if (LtsBackendConstant.IO_IND_INSTALL.equals(this.paymethods[i].getAction())
					|| LtsBackendConstant.IO_IND_SPACE.equals(this.paymethods[i].getAction())) {
				return this.paymethods[i];
			}
		}
		return null;
	}
	
	public PaymentMethodDetailLtsDTO getExistPayment() {
		
		if (paymethods == null) {
			return null;
		}
		
		for (int i=0; i < this.paymethods.length; ++i) {
			if (LtsBackendConstant.IO_IND_OUT.equals(this.paymethods[i].getAction())
					|| LtsBackendConstant.IO_IND_SPACE.equals(this.paymethods[i].getAction())) {
				return this.paymethods[i];
			}
		}
		return null;
	}

	public String getOptOut() {
		return optOut;
	}

	public void setOptOut(String optOut) {
		this.optOut = optOut;
	}
}
