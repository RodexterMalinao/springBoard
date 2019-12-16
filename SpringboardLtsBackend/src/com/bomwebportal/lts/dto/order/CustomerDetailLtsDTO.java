package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class CustomerDetailLtsDTO extends ObjectActionBaseDTO  {

	private static final long serialVersionUID = 7795676532315686263L;

	private String custNo = null;
	private String firstName = null;
	private String lastName = null;
	private String idDocType = null;
	private String idDocNum = null;
	private String dob = null;
	private String title = null;
	private String companyName = null;
	private String lob = null;
	private String idVerifiedInd = null;
	private String langWritten = null;
	private String blacklistInd = null;
	private String indType = null;
	private String indSubType = null;
	private String csPortalInd = null;
	private String csPortalLogin = null;
	private String csPortalMobile = null;
	private String theClubLogin = null;
	private String theClubMobile = null;
	private String theClubInd = null;
	private String mismatchInd = null;
	private String hktOptOut = null;
	private String clubOptOut = null;
	private String clubOptRea = null;
	private String clubOptRmk = null;
	
	private CustOptOutInfoLtsDTO custOptOutInfo = null;


	public String getIndType() {
		return indType;
	}

	public void setIndType(String indType) {
		this.indType = indType;
	}

	public String getIndSubType() {
		return indSubType;
	}

	public void setIndSubType(String indSubType) {
		this.indSubType = indSubType;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getIdDocNum() {
		return idDocNum;
	}

	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getIdVerifiedInd() {
		return idVerifiedInd;
	}

	public void setIdVerifiedInd(String idVerifiedInd) {
		this.idVerifiedInd = idVerifiedInd;
	}

	public String getLangWritten() {
		return langWritten;
	}

	public void setLangWritten(String langWritten) {
		this.langWritten = langWritten;
	}

	public String getBlacklistInd() {
		return blacklistInd;
	}

	public void setBlacklistInd(String blacklistInd) {
		this.blacklistInd = blacklistInd;
	}

	public CustOptOutInfoLtsDTO getCustOptOutInfo() {
		return custOptOutInfo;
	}

	public void setCustOptOutInfo(CustOptOutInfoLtsDTO custOptOutInfo) {
		this.custOptOutInfo = custOptOutInfo;
	}

	public String getCsPortalInd() {
		return csPortalInd;
	}

	public void setCsPortalInd(String csPortalInd) {
		this.csPortalInd = csPortalInd;
	}

	public String getCsPortalLogin() {
		return csPortalLogin;
	}

	public void setCsPortalLogin(String csPortalLogin) {
		this.csPortalLogin = csPortalLogin;
	}

	public String getCsPortalMobile() {
		return csPortalMobile;
	}

	public void setCsPortalMobile(String csPortalMobile) {
		this.csPortalMobile = csPortalMobile;
	}
	
	public String getTheClubLogin() {
		return theClubLogin;
	}

	public void setTheClubLogin(String theClubLogin) {
		this.theClubLogin = theClubLogin;
	}

	public String getTheClubMobile() {
		return theClubMobile;
	}

	public void setTheClubMobile(String theClubMobile) {
		this.theClubMobile = theClubMobile;
	}

	public String getTheClubInd() {
		return theClubInd;
	}

	public void setTheClubInd(String theClubInd) {
		this.theClubInd = theClubInd;
	}

	public String getMismatchInd() {
		return mismatchInd;
	}

	public void setMismatchInd(String mismatchInd) {
		this.mismatchInd = mismatchInd;
	}

	public String getHktOptOut() {
		return hktOptOut;
	}

	public void setHktOptOut(String hktOptOut) {
		this.hktOptOut = hktOptOut;
	}

	public String getClubOptOut() {
		return clubOptOut;
	}

	public void setClubOptOut(String clubOptOut) {
		this.clubOptOut = clubOptOut;
	}

	public String getClubOptRea() {
		return clubOptRea;
	}

	public void setClubOptRea(String clubOptRea) {
		this.clubOptRea = clubOptRea;
	}

	public String getClubOptRmk() {
		return clubOptRmk;
	}

	public void setClubOptRmk(String clubOptRmk) {
		this.clubOptRmk = clubOptRmk;
	}
	

	
}
