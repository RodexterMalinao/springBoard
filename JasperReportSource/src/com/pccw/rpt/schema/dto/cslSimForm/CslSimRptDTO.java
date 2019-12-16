package com.pccw.rpt.schema.dto.cslSimForm;

import com.pccw.rpt.schema.dto.ReportDTO;

public class CslSimRptDTO extends ReportDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8373706170960941591L;
	
	
	private String reference;
	private byte[] signature;
	private String date;
	private boolean customerCopy;
	private String noSignatureR;
	
	
	public String getReference() {
		return this.reference;
	}
	public void setReference(String pReference) {
		this.reference = pReference;
	}
	public byte[] getSignature() {
		return this.signature;
	}
	public void setSignature(byte[] pSignature) {
		this.signature = pSignature;
	}
	public String getDate() {
		return this.date;
	}
	public void setDate(String pDate) {
		this.date = pDate;
	}
	public boolean isCustomerCopy() {
		return this.customerCopy;
	}
	public void setCustomerCopy(boolean pCustomerCopy) {
		this.customerCopy = pCustomerCopy;
	}
	public String getNoSignatureR() {
		return noSignatureR;
	}
	public void setNoSignatureR(String noSignatureR) {
		this.noSignatureR = noSignatureR;
	}
	
}