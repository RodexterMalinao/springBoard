package com.pccw.rpt.schema.dto.itemSpec;

import com.pccw.rpt.schema.dto.ReportDTO;

public class ItemSpecRptDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5589543529653224566L;
	
	private String reference;
	private byte[] itemSpecSign;
	private String noSignatureR;
	private String membershipId;

	public String getReference() {
		return this.reference;
	}
	public void setReference(String pReference) {
		this.reference = pReference;
	}
	public byte[] getItemSpecSign() {
		return itemSpecSign;
	}
	public void setItemSpecSign(byte[] itemSpecSign) {
		this.itemSpecSign = itemSpecSign;
	}
	public String getNoSignatureR() {
		return noSignatureR;
	}
	public void setNoSignatureR(String noSignatureR) {
		this.noSignatureR = noSignatureR;
	}
	public String getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

}
