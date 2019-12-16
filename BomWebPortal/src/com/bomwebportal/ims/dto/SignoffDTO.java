package com.bomwebportal.ims.dto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

//import com.bomwebportal.util.SignatureToImage;

public class SignoffDTO {
	
	private String orderId;
	
	private String signatureString;
	
	private byte[] signature;

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

/*	public String getSignatureString() {
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
*/	
	public InputStream getSignatureInputStream() {
		return new ByteArrayInputStream(this.signature == null ? new byte[0]: this.signature);
	}
}