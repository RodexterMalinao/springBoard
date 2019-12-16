package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.util.FastByteArrayInputStream;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionCRptDTO.ChargingItem;
import com.pccw.rpt.util.ReportUtil;

public class SectionKRptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3069315069158379824L;
	
	
	private String existPaymentMethodInd;
	
	private String existPaymentStatement;
	
	private String paymentMethodInd;
	
	private String cardType;
	
	private String cardHolderName;
	
	private String cardNum;
	
	private String expiryDate;
	
	private String cardVerifiedInd;
	
	private String creditCardStatement;
	
	private String autoPayStatement;
	
	private String acctNum;
	
	private String acctHolderName;
	
	private String idDocType;
	
	private String idDocNum;
	
	private String bankCd;
	
	private String branchCd;
	
	private String bankAcctNum;
	
	private byte[] cardHolderSignature;
	
	private byte[] autoPayCustSignature;
	
	private ArrayList<ChargingItem> prepaymentList = new ArrayList<ChargingItem>();
	
	private String salesMemoNum;
	
	private String thirdPartyContentA;
	
	private String thirdPartyContentB;
	
	private String thirdPartyContentC;
	
	private String thirdPartyIdDocType;
	
	private String thirdPartyIdDocNum;
	
	private String thirdPartyCreditCardInd;
	
	private String thirdPartyCreditCardStatement;
	
	private String olsPrepaymentAmt;
	
	private String olsPrepaymentText;
	
	private String deviceType;
	
	public String getExistPaymentMethodInd() {
		return ReportUtil.defaultString(this.existPaymentMethodInd);
	}

	public void setExistPaymentMethodInd(String existPaymentMethodInd) {
		this.existPaymentMethodInd = existPaymentMethodInd;
	}
	
	public String getExistPaymentStatement() {
		return ReportUtil.defaultString(this.existPaymentStatement);
	}

	public void setExistPaymentStatement(String existPaymentStatement) {
		this.existPaymentStatement = existPaymentStatement;
	}

	public String getPaymentMethodInd() {
		return ReportUtil.defaultString(this.paymentMethodInd);
	}

	public void setPaymentMethodInd(String paymentMethodInd) {
		this.paymentMethodInd = paymentMethodInd;
	}

	public String getCardType() {
		return ReportUtil.defaultString(cardType);
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardHolderName() {
		return ReportUtil.defaultString(cardHolderName);
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardNum() {
		return ReportUtil.defaultString(cardNum);
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getExpiryDate() {
		return ReportUtil.defaultString(expiryDate);
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCardVerifiedInd() {
		return ReportUtil.defaultString(cardVerifiedInd);
	}

	public void setCardVerifiedInd(String cardVerifiedInd) {
		this.cardVerifiedInd = cardVerifiedInd;
	}
	
	public boolean isCardVerified() {
		return "Y".equals(this.cardVerifiedInd);
	}

	public String getCreditCardStatement() {
		return ReportUtil.defaultString(this.creditCardStatement);
	}

	public void setCreditCardStatement(String creditCardStatement) {
		this.creditCardStatement = creditCardStatement;
	}

	public String getAutoPayStatement() {
		return ReportUtil.defaultString(this.autoPayStatement);
	}

	public void setAutoPayStatement(String autoPayStatement) {
		this.autoPayStatement = autoPayStatement;
	}

	public String getAcctNum() {
		return ReportUtil.defaultString(this.acctNum);
	}

	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

	public String getAcctHolderName() {
		return ReportUtil.defaultString(this.acctHolderName);
	}

	public void setAcctHolderName(String acctHolderName) {
		this.acctHolderName = acctHolderName;
	}

	public String getIdDocType() {
		return ReportUtil.defaultString(this.idDocType);
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getIdDocNum() {
		return ReportUtil.defaultString(this.idDocNum);
	}

	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}

	public String getBankCd() {
		return ReportUtil.defaultString(this.bankCd);
	}

	public void setBankCd(String bankCd) {
		this.bankCd = bankCd;
	}

	public String getBranchCd() {
		return ReportUtil.defaultString(this.branchCd);
	}

	public void setBranchCd(String branchCd) {
		this.branchCd = branchCd;
	}

	public String getBankAcctNum() {
		return ReportUtil.defaultString(this.bankAcctNum);
	}

	public void setBankAcctNum(String bankAcctNum) {
		this.bankAcctNum = bankAcctNum;
	}

	public InputStream getCardHolderSignatureStream() {
		return new FastByteArrayInputStream(this.cardHolderSignature, ArrayUtils.isEmpty(this.cardHolderSignature) ? 0 : this.cardHolderSignature.length);
	}
	
	public byte[] getCardHolderSignature() {
		return this.cardHolderSignature;
	}

	public void setCardHolderSignature(byte[] cardHolderSignature) {
		this.cardHolderSignature = cardHolderSignature;
	}

	public InputStream getAutoPayCustSignatureStream() {
		return new FastByteArrayInputStream(this.autoPayCustSignature, ArrayUtils.isEmpty(this.autoPayCustSignature) ? 0 : this.autoPayCustSignature.length);
	}

	public byte[] getAutoPayCustSignature() {
		return this.autoPayCustSignature;
	}

	public void setAutoPayCustSignature(byte[] autoPayCustSignature) {
		this.autoPayCustSignature = autoPayCustSignature;
	}
	
	public ArrayList<ChargingItem> getPrepaymentList() {
		return this.prepaymentList;
	}

	public void addPrepayment(String pDescription) {
		this.prepaymentList.add(new ChargingItem(this.replaceEyeDeviceKeyword(pDescription),
				null, null));
	}
	public boolean isPrepaymentListEmpty() {
		return this.prepaymentList.isEmpty();
	}

	public boolean isSalesMemoNumEmpty() {
		return StringUtils.isBlank(this.salesMemoNum);
	}
	
	public String getSalesMemoNum() {
		return ReportUtil.defaultString(this.salesMemoNum);
	}

	public void setSalesMemoNum(String salesMemoNum) {
		this.salesMemoNum = salesMemoNum;
	}

	public String getThirdPartyContentA() {
		return ReportUtil.defaultString(this.thirdPartyContentA);
	}

	public void setThirdPartyContentA(String thirdPartyContentA) {
		this.thirdPartyContentA = thirdPartyContentA;
	}

	public String getThirdPartyContentB() {
		return ReportUtil.defaultString(this.thirdPartyContentB);
	}

	public void setThirdPartyContentB(String thirdPartyContentB) {
		this.thirdPartyContentB = thirdPartyContentB;
	}

	public String getThirdPartyContentC() {
		return ReportUtil.defaultString(this.thirdPartyContentC);
	}

	public void setThirdPartyContentC(String thirdPartyContentC) {
		this.thirdPartyContentC = thirdPartyContentC;
	}

	public String getThirdPartyIdDocType() {
		return ReportUtil.defaultString(this.thirdPartyIdDocType);
	}

	public void setThirdPartyIdDocType(String thirdPartyIdDocType) {
		this.thirdPartyIdDocType = thirdPartyIdDocType;
	}

	public String getThirdPartyIdDocNum() {
		return ReportUtil.defaultString(this.thirdPartyIdDocNum);
	}

	public void setThirdPartyIdDocNum(String thirdPartyIdDocNum) {
		this.thirdPartyIdDocNum = thirdPartyIdDocNum;
	}

	public String getThirdPartyCreditCardInd() {
		return ReportUtil.defaultString(this.thirdPartyCreditCardInd);
	}

	public void setThirdPartyCreditCardInd(String thirdPartyCreditCardInd) {
		this.thirdPartyCreditCardInd = thirdPartyCreditCardInd;
	}
	
	public boolean isThirdPartyCreditCard() {
		return "Y".equals(this.thirdPartyCreditCardInd);
	}

	public String getThirdPartyCreditCardStatement() {
		return ReportUtil.defaultString(this.thirdPartyCreditCardStatement);
	}

	public void setThirdPartyCreditCardStatement(
			String thirdPartyCreditCardStatement) {
		this.thirdPartyCreditCardStatement = thirdPartyCreditCardStatement;
	}

	public String getOlsPrepaymentAmt() {
		return this.olsPrepaymentAmt;
	}

	public void setOlsPrepaymentAmt(String pOlsPrepaymentAmt) {
		this.olsPrepaymentAmt = pOlsPrepaymentAmt;
	}

	public String getOlsPrepaymentText() {
		return this.olsPrepaymentText;
	}

	public void setOlsPrepaymentText(String pOlsPrepaymentText) {
		this.olsPrepaymentText = pOlsPrepaymentText;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String pDeviceType) {
		this.deviceType = pDeviceType;
	}
}