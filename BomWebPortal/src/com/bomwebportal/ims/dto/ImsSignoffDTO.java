package com.bomwebportal.ims.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.bomwebportal.util.SignatureToImage;

public class ImsSignoffDTO {
	
	public enum SignatureTypeEnum {
		CUSTOMER_SIGN, CREDIT_CARD_SIGN, ThirdParty_SIGN, PCD_TDO_SIGN, TV_TDO_SIGN, MOOV_TDO_SIGN, CareCash_SIGN
		;
	}
	private String orderId;
	private SignatureTypeEnum signatureType;
	private boolean sameAsCreditCardSign;
	private boolean test3rdParty;
	private boolean testCreditCard;
	
	private String signatureString;
	
	private String signedInd;	//Tony
	
	private byte[] signature;

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getSignatureString() {
		return signatureString;
	}

	public void setSignatureString(String pSignatureString) {
		this.signatureString = pSignatureString;
		if (pSignatureString == null) {
			this.signature = new byte[0];
		}
		SignatureToImage sti = new SignatureToImage();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		sti.saveSignature(this.signatureString, baos);
		this.signature = baos.toByteArray();
	}
	
	public InputStream getSignatureInputStream() {
		return new ByteArrayInputStream(this.signature == null ? new byte[0]: this.signature);
	}

	public void setSignatureType(SignatureTypeEnum signatureType) {
		this.signatureType = signatureType;
	}

	public SignatureTypeEnum getSignatureType() {
		return signatureType;
	}

	public boolean isSameAsCreditCardSign() {
		return sameAsCreditCardSign;
	}

	public void setSameAsCreditCardSign(boolean sameAsCreditCardSign) {
		this.sameAsCreditCardSign = sameAsCreditCardSign;
	}

	public boolean isTestCreditCard() {
		return testCreditCard;
	}

	public void setTestCreditCard(boolean testCreditCard) {
		this.testCreditCard = testCreditCard;
	}

	public boolean isTest3rdParty() {
		return test3rdParty;
	}

	public void setTest3rdParty(boolean test3rdParty) {
		this.test3rdParty = test3rdParty;
	}

	public void setSignedInd(String signedInd) {
		this.signedInd = signedInd;
	}

	public String getSignedInd() {
		return signedInd;
	}
}