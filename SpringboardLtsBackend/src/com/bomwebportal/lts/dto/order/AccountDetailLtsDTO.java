package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class AccountDetailLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -3359332053657347572L;
	
	private String acctNo = null;
	private String acctName = null;
	private String billFreq = null;
	private String billLang = null;
	private String smsNo = null;
	private String emailAddr = null;
	private String billMedia = null;
	private String billFmt = null;
	private String autopayStatementInd = null;
	private String existBillMedia = null;
	private String existEmailAddr = null;
	private String redemptionMedia = null;
	private String redemptionEmailAddr = null;
	private String redemptionSmsNo = null;
	private CustomerDetailLtsDTO customer = null;
	private PaymentMethodDetailLtsDTO[] paymethods = null;
	private BillingAddressLtsDTO billingAddress =null;
	private String isNew = null;
	private String acctWaivePaperInd = null;
	private String waivePaperReaCd = null;
	private String waivePaperRemarks = null;
	
	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getBillFreq() {
		return billFreq;
	}

	public void setBillFreq(String billFreq) {
		this.billFreq = billFreq;
	}

	public String getBillLang() {
		return billLang;
	}

	public void setBillLang(String billLang) {
		this.billLang = billLang;
	}

	public String getSmsNo() {
		return smsNo;
	}

	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getBillMedia() {
		return billMedia;
	}

	public void setBillMedia(String billMedia) {
		this.billMedia = billMedia;
	}

	public CustomerDetailLtsDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDetailLtsDTO customer) {
		this.customer = customer;
	}

	public PaymentMethodDetailLtsDTO[] getPaymethods() {
		return paymethods;
	}

	public void setPaymethods(PaymentMethodDetailLtsDTO[] paymethods) {
		this.paymethods = paymethods;
	}
	
	public String getBillFmt() {
		return billFmt;
	}

	public void setBillFmt(String billFmt) {
		this.billFmt = billFmt;
	}

	public String getAutopayStatementInd() {
		return autopayStatementInd;
	}

	public void setAutopayStatementInd(String autopayStatementInd) {
		this.autopayStatementInd = autopayStatementInd;
	}

	public String getExistBillMedia() {
		return this.existBillMedia;
	}

	public void setExistBillMedia(String pExistBillMedia) {
		this.existBillMedia = pExistBillMedia;
	}

	public String getExistEmailAddr() {
		return existEmailAddr;
	}

	public void setExistEmailAddr(String existEmailAddr) {
		this.existEmailAddr = existEmailAddr;
	}

	public String getRedemptionMedia() {
		return redemptionMedia;
	}

	public void setRedemptionMedia(String redemptionMedia) {
		this.redemptionMedia = redemptionMedia;
	}

	public String getRedemptionEmailAddr() {
		return redemptionEmailAddr;
	}

	public void setRedemptionEmailAddr(String redemptionEmailAddr) {
		this.redemptionEmailAddr = redemptionEmailAddr;
	}

	public String getRedemptionSmsNo() {
		return redemptionSmsNo;
	}

	public void setRedemptionSmsNo(String redemptionSmsNo) {
		this.redemptionSmsNo = redemptionSmsNo;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
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

	public BillingAddressLtsDTO getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(BillingAddressLtsDTO billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getAcctWaivePaperInd() {
		return acctWaivePaperInd;
	}

	public void setAcctWaivePaperInd(String accWaivePaperInd) {
		this.acctWaivePaperInd = accWaivePaperInd;
	}

	public String getWaivePaperReaCd() {
		return waivePaperReaCd;
	}

	public void setWaivePaperReaCd(String waivePaperReaCd) {
		this.waivePaperReaCd = waivePaperReaCd;
	}

	public String getWaivePaperRemarks() {
		return waivePaperRemarks;
	}

	public void setWaivePaperRemarks(String waivePaperRemarks) {
		this.waivePaperRemarks = waivePaperRemarks;
	}
	
}
