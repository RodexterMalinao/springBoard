package com.bomwebportal.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.bomwebportal.util.SignatureToImage;

public class SignoffDTO {
	
	public enum SignatureTypeEnum {
		CUSTOMER_SIGN
		, BANK_SIGN
		, MNP_SIGN
		, CONCIERGE_SIGN
		, IGUARD_SIGN
		, TRADE_DESCRIPTION_ORDINANCE_SIGN //Trade Description Ordinance (TDO)
		, MULTISIM_MNP_COO_SIGN
		, TRANSFEREE_SIGN
		, TRAVEL_INSURANCE_FORM_SIGN
		, HELPERCARE_INSURANCE_FORM_SIGN
		, PROJECT_EAGLE_INSURANCE_FORM_SIGN
		;
	}
	
	private String orderId;
	private SignatureTypeEnum signatureType;
	private boolean sameAsBankSign;
	private boolean iGuard1;
	private boolean iGuard2;
	private boolean iGuard3;
	private boolean helperCare1;
	private boolean travel1;
	private boolean projectEagle;
	private boolean iGuardUadInd;
	private int mode;
	private String mnpNumber;
	
	private String signatureString;
	
	private byte[] signature;

	private String reqId;
	
	public void setSignature(byte[] signature) {
		this.signature = signature;
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

	public boolean isSameAsBankSign() {
		return sameAsBankSign;
	}

	public void setSameAsBankSign(boolean sameAsBankSign) {
		this.sameAsBankSign = sameAsBankSign;
	}
	
	public boolean isiGuard1() {
		return iGuard1;
	}

	public void setiGuard1(boolean iGuard1) {
		this.iGuard1 = iGuard1;
	}

	public boolean isiGuard2() {
		return iGuard2;
	}

	public void setiGuard2(boolean iGuard2) {
		this.iGuard2 = iGuard2;
	}

	public boolean isiGuard3() {
		return iGuard3;
	}

	public void setiGuard3(boolean iGuard3) {
		this.iGuard3 = iGuard3;
	}
	
	public boolean isiGuardUadInd() {
		return iGuardUadInd;
	}

	public void setiGuardUadInd(boolean iGuardUadInd) {
		this.iGuardUadInd = iGuardUadInd;
	}
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getMnpNumber() {
		return mnpNumber;
	}

	public void setMnpNumber(String mnpNumber) {
		this.mnpNumber = mnpNumber;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public boolean isHelperCare1() {
		return helperCare1;
	}

	public void setHelperCare1(boolean helperCare1) {
		this.helperCare1 = helperCare1;
	}


	public boolean isTravel1() {
		return travel1;
	}

	public void setTravel1(boolean travel1) {
		this.travel1 = travel1;
	}

	public boolean isProjectEagle() {
		return projectEagle;
	}

	public void setProjectEagle(boolean projectEagle) {
		this.projectEagle = projectEagle;
	}
	
}