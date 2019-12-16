package com.pccw.rpt.schema.dto.productSpec;

import com.pccw.rpt.schema.dto.ReportDTO;

public class ProductSpecRptDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6519029231071270758L;

	private String reference;
	private byte[] signature;
	private String date;
	private boolean customerCopy;
	
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
}
