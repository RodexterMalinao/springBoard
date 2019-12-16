package com.bomwebportal.lts.dto.order;

import java.io.Serializable;

public class SignatureLtsDTO implements Serializable {

	private static final long serialVersionUID = 8048634104975642329L;

	private String signType = null;
	private byte[] signatureBytes = null;

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public byte[] getSignatureBytes() {
		return signatureBytes;
	}

	public void setSignatureBytes(byte[] signatureBytes) {
		this.signatureBytes = signatureBytes;
	}
}
