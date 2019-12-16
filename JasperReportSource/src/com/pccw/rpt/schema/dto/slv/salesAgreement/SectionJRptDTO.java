package com.pccw.rpt.schema.dto.slv.salesAgreement;

public class SectionJRptDTO extends FixContentRptDTO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4084101184299071185L;

	private String custType;
	private boolean optOut = false;
	private String optText;
	private String checkMark;
	private String bottomText;
	
	public String getCustType() {
		return this.custType;
	}
	public void setCustType(String pCustType) {
		this.custType = pCustType;
	}
	public boolean isOptOut() {
		return this.optOut;
	}
	public void setOptOut(boolean pOptOut) {
		this.optOut = pOptOut;
	}
	public String getOptText() {
		return this.optText;
	}
	public void setOptText(String pOptText) {
		this.optText = pOptText;
	}
	public String getCheckMark() {
		return this.checkMark;
	}
	public void setCheckMark(String pCheckMark) {
		this.checkMark = pCheckMark;
	}
	public String getBottomText() {
		return this.bottomText;
	}
	public void setBottomText(String pBottomText) {
		this.bottomText = pBottomText;
	}
}
