package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.io.Serializable;

import com.pccw.rpt.util.ReportUtil;

public class CustomerRptDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5699284247565562949L;

	private String custNo;
	private String firstName;
	private String lastName;
	private String idDocType;
	private String idDocNum;
	private String dob;
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
	private String cspTextHkt;
	private String cspTextClub;
	private String cspTextClubHkt;
	private String signoffMode; //JT2017062

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

	public String getDob() {
		return ReportUtil.defaultString(this.dob);
	}

	public void setDob(String pDob) {
		this.dob = pDob;
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

	public String getCspTextHkt() {
		return cspTextHkt;
	}

	public void setCspTextHkt(String cspTextHkt) {
		this.cspTextHkt = cspTextHkt;
	}

	public String getCspTextClub() {
		return cspTextClub;
	}

	public void setCspTextClub(String cspTextClub) {
		this.cspTextClub = cspTextClub;
	}

	public String getCspTextClubHkt() {
		return cspTextClubHkt;
	}

	public void setCspTextClubHkt(String cspTextClubHkt) {
		this.cspTextClubHkt = cspTextClubHkt;
	}

	public String getSignoffMode() {
		return signoffMode;
	}

	public void setSignoffMode(String signoffMode) {
		this.signoffMode = signoffMode;
	}
}
