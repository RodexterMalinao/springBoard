package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.SignatureDTO;

public class LtsDigitalSignatureFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6216785015733776530L;
	
	private List<SignatureDTO> signatureList;
	private boolean sameAsBankSign = false;
	private boolean sameAsSecDelCustSign = false;
	private boolean sameAsRecontractToSign = false;
	private boolean sameAsPipbNsdSign = false;
	private boolean sameAsRegIguardCareCash = false; //MB2016081 TC
	
	private boolean bankSignRequired = false;

	private boolean secDelCustSignRequired = false;
	private boolean recontractToSignRequired = false; 
	private boolean pipbNsdSignRequired = false; 
	private boolean thirdPartyCard = false;
	private boolean allowReplicate = false;
	private boolean regIguardCareCash = false; //MB2016081 TC
	
	private int signatureIndex = 0;
	
	public List<SignatureDTO> getSignatureList() {
		return signatureList;
	}
	public void setSignatureList(List<SignatureDTO> signatureList) {
		this.signatureList = signatureList;
	}
	public boolean isSameAsBankSign() {
		return sameAsBankSign;
	}
	public void setSameAsBankSign(boolean sameAsBankSign) {
		this.sameAsBankSign = sameAsBankSign;
	}
	public boolean isBankSignRequired() {
		return bankSignRequired;
	}
	public void setBankSignRequired(boolean bankSignRequired) {
		this.bankSignRequired = bankSignRequired;
	}
	public boolean isSameAsSecDelCustSign() {
		return sameAsSecDelCustSign;
	}
	public void setSameAsSecDelCustSign(boolean sameAsSecDelCustSign) {
		this.sameAsSecDelCustSign = sameAsSecDelCustSign;
	}
	public boolean isSecDelCustSignRequired() {
		return secDelCustSignRequired;
	}
	public void setSecDelCustSignRequired(boolean secDelCustSignRequired) {
		this.secDelCustSignRequired = secDelCustSignRequired;
	}
	public boolean isPipbNsdSignRequired() {
		return pipbNsdSignRequired;
	}
	public void setPipbNsdSignRequired(boolean pipbNsdSignRequired) {
		this.pipbNsdSignRequired = pipbNsdSignRequired;
	}
	public boolean isSameAsPipbNsdSign() {
		return sameAsPipbNsdSign;
	}
	public void setSameAsPipbNsdSign(boolean sameAsPipbNsdSign) {
		this.sameAsPipbNsdSign = sameAsPipbNsdSign;
	}
	public int getSignatureIndex() {
		return signatureIndex;
	}
	public void setSignatureIndex(int signatureIndex) {
		this.signatureIndex = signatureIndex;
	}
	public boolean isSameAsRecontractToSign() {
		return sameAsRecontractToSign;
	}
	public void setSameAsRecontractToSign(boolean sameAsRecontractToSign) {
		this.sameAsRecontractToSign = sameAsRecontractToSign;
	}
	public boolean isRecontractToSignRequired() {
		return recontractToSignRequired;
	}
	public void setRecontractToSignRequired(boolean recontractToSignRequired) {
		this.recontractToSignRequired = recontractToSignRequired;
	}
	public boolean isThirdPartyCard() {
		return thirdPartyCard;
	}
	public void setThirdPartyCard(boolean thirdPartyCard) {
		this.thirdPartyCard = thirdPartyCard;
	}
	public boolean isAllowReplicate() {
		return allowReplicate;
	}
	public void setAllowReplicate(boolean allowReplicate) {
		this.allowReplicate = allowReplicate;
	}
	
	//MB2016081 TC++	
	public boolean isRegIguardCareCash() {
		return regIguardCareCash;
	}
	public void setRegIguardCareCash(boolean regIguardCareCash) {
		this.regIguardCareCash = regIguardCareCash;
	}
	public boolean isSameAsRegIguardCareCash() {
		return sameAsRegIguardCareCash;
	}
	public void setSameAsRegIguardCareCash(boolean sameAsRegIguardCareCash) {
		this.sameAsRegIguardCareCash = sameAsRegIguardCareCash;
	}
	//MB2016081 TC--
	
}
