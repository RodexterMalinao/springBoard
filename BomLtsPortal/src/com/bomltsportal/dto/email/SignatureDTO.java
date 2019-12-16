package com.bomltsportal.dto.email;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.bomltsportal.util.SignatureToImage;


public class SignatureDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6760486422946562709L;

	public enum SignatureType {
		EYEX_CUST_SIGN, EYEX_THIRD_PARTY_SIGN, BANK_SIGN, CCARD_SIGN, SEC_DEL_CUST_SIGN, SEC_DEL_THIRD_PARTY_SIGN;
	}
	
	private String titleEng;
	private String titleChi;
	private String noteEng;
	private String noteChi;
	private SignatureType signType;
	private boolean signed = false;
	private byte[] signatureBytes;
	private String signatureString;
	private SignatureType replicatedSignType;
	private boolean replicated;
	
	public String getTitleEng() {
		return titleEng;
	}
	public void setTitleEng(String titleEng) {
		this.titleEng = titleEng;
	}
	public String getTitleChi() {
		return titleChi;
	}
	public void setTitleChi(String titleChi) {
		this.titleChi = titleChi;
	}
	public String getNoteEng() {
		return noteEng;
	}
	public void setNoteEng(String noteEng) {
		this.noteEng = noteEng;
	}
	public String getNoteChi() {
		return noteChi;
	}
	public void setNoteChi(String noteChi) {
		this.noteChi = noteChi;
	}
	public SignatureType getSignType() {
		return signType;
	}
	public void setSignType(SignatureType signType) {
		this.signType = signType;
	}
	public boolean isSigned() {
		return signed;
	}
	public void setSigned(boolean signed) {
		this.signed = signed;
	}
	public byte[] getSignatureBytes() {
		return signatureBytes;
	}
	public void setSignatureBytes(byte[] signatureBytes) {
		this.signatureBytes = signatureBytes;
	}
	public String getSignatureString() {
		return signatureString;
	}

	public void setSignatureString(String signatureString) {
		this.signatureString = signatureString;
//		this.signatureBytes = new byte[0];
		this.setSigned(StringUtils.isNotBlank(signatureString));
		
		if (StringUtils.isNotBlank(signatureString)) {
			SignatureToImage sti = new SignatureToImage();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			sti.saveSignature(this.signatureString, baos);
			this.setSignatureBytes(baos.toByteArray());	
		}
		else {
			this.setSignatureBytes(null);
		}
	}

	public InputStream getSignatureInputStream() {
		return new ByteArrayInputStream(
				this.signatureBytes == null ? new byte[0] : this.signatureBytes);
	}

	public SignatureType getReplicatedSignType() {
		return replicatedSignType;
	}

	public void setReplicatedSignType(SignatureType replicatedSignType) {
		this.replicatedSignType = replicatedSignType;
		setReplicated(replicatedSignType != null);
	}

	public boolean isReplicated() {
		return replicated;
	}

	public void setReplicated(boolean replicated) {
		this.replicated = replicated;
	}
	
	
}
