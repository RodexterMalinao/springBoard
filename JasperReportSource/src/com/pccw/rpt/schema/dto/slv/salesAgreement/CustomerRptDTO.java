package com.pccw.rpt.schema.dto.slv.salesAgreement;

import java.io.Serializable;

import com.pccw.rpt.util.ReportUtil;

public class CustomerRptDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4821872118619017363L;
	
	private String custNo;
	private String firstName;
	private String lastName;
	private String idDocType;
	private String idDocNum;
	private String title;
	private String companyName;
	private String lob;
	private String idVerifiedInd;
	private String contactMobileNum;
	private String contactFixedLineNum;
	private String langWritten;
	private String custType;
	private String emailAddr;
	private boolean csPortal;
	private String csPortalText;

	public String getCustNo() {
		return ReportUtil.defaultString(this.custNo);
	}

	public void setCustNo(String pCustNo) {
		this.custNo = pCustNo;
	}

	public String getFirstName() {
		return ReportUtil.defaultString(this.firstName);
	}

	public void setFirstName(String pFirstName) {
		this.firstName = pFirstName;
	}

	public String getLastName() {
		return ReportUtil.defaultString(this.lastName);
	}

	public void setLastName(String pLastName) {
		this.lastName = pLastName;
	}

	public String getIdDocType() {
		return ReportUtil.defaultString(this.idDocType);
	}

	public void setIdDocType(String pIdDocType) {
		this.idDocType = pIdDocType;
	}

	public String getIdDocNum() {
		return ReportUtil.defaultString(this.idDocNum);
	}

	public void setIdDocNum(String pIdDocNum) {
		this.idDocNum = pIdDocNum;
	}

	public String getTitle() {
		return ReportUtil.defaultString(this.title);
	}

	public void setTitle(String pTitle) {
		this.title = pTitle;
	}

	public String getCompanyName() {
		return ReportUtil.defaultString(this.companyName);
	}

	public void setCompanyName(String pCompanyName) {
		this.companyName = pCompanyName;
	}

	public String getLob() {
		return ReportUtil.defaultString(this.lob);
	}

	public void setLob(String pLob) {
		this.lob = pLob;
	}

	public String getIdVerifiedInd() {
		return ReportUtil.defaultString(this.idVerifiedInd);
	}

	public void setIdVerifiedInd(String pIdVerifiedInd) {
		this.idVerifiedInd = pIdVerifiedInd;
	}

	public String getContactMobileNum() {
		return ReportUtil.defaultString(this.contactMobileNum);
	}

	public void setContactMobileNum(String pContactMobileNum) {
		this.contactMobileNum = pContactMobileNum;
	}

	public String getContactFixedLineNum() {
		return ReportUtil.defaultString(this.contactFixedLineNum);
	}

	public void setContactFixedLineNum(String pContactFixedLineNum) {
		this.contactFixedLineNum = pContactFixedLineNum;
	}

	public String getLangWritten() {
		return ReportUtil.defaultString(this.langWritten);
	}

	public void setLangWritten(String pLangWritten) {
		this.langWritten = pLangWritten;
	}

	public boolean isIdVerified() {
		return "Y".equals(this.idVerifiedInd);
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public boolean isCsPortal() {
		return this.csPortal;
	}

	public void setCsPortal(boolean pCsPortal) {
		this.csPortal = pCsPortal;
	}

	public String getCsPortalText() {
		return this.csPortalText;
	}

	public void setCsPortalText(String pCsPortalText) {
		this.csPortalText = pCsPortalText;
	}
}
