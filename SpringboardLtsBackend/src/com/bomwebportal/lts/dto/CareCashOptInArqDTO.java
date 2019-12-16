package com.bomwebportal.lts.dto;

import java.io.Serializable;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;


public class CareCashOptInArqDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8257667414762725489L;

	private String apiTy; // Constant, must be "CARE_CASH_OPTIN"
	private String reply; // Reply Code returned from API
	/*
	 *  RC_SUCC = success
	 *  
	 *  fail:
	 *  RC_ALDY_OPT_IN = Customer is already opt-in
	 *  RC_NOT_ELIG = Customer is not eligible to opt-in.
	 *  RC_NO_CUSTOMER = no such Customer exists
	 *  RC_BUSY = The relevant rows in the database are locked.
	 *  RC_GATEBUSY = Too many attempts to the API
	 *  RC_CARE_ICCTMA = email needs all lower letters
	 */
	
	private String clnVer; // Client Version, must be empty string in this moment
	private String sysId; // The System Id assigned to your application
	private String sysPwd; // The Password assigned to your System Id
	private String userId; // It must be empty string
	private String psnTy; // It must be empty string
	private String phylum; // It must be empty string
	private String iOptFor; // Opt In or Opt Out the CARECash Program
	private String iDocTy; // Document Type
	private String iDocNum; // Document Number
	private String iFormNum; // Form Number, sbid
	private String iChnlCode; // Channel Code, sales channel code in SB
	private String iEnName; // English Name, last + first name
	private String iCtMail; // Contact Email
	private String iCtPhone; // Contact Phone Number
	private String iDob; // Date of Birth, YYYYMMDD
	private String iOpt4Dm; // Opt In or Opt Out the Direct Marketing for CARECash Program
	private String iLang; // Language
	private int iNuLtsSubn; // Number of LTS Subscription Under this Id Document
	private int iNuMobSubn; // Number of Mobile Subscription Under this Id Document
	private int iNuImsSubn; // Number of IMS Subscription Under this Id Document
	private int iNuTvSubn; // Number of NowTV Subscription Under this Id Document
	private String iVisitTs; // Last Visit Timestamp, YYYYMMDDHHMISS
	
	public String getApiTy() {
		return apiTy;
	}
	public void setApiTy(String apiTy) {
		this.apiTy = apiTy;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getClnVer() {
		return clnVer;
	}
	public void setClnVer(String clnVer) {
		this.clnVer = clnVer;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
	public String getSysPwd() {
		return sysPwd;
	}
	public void setSysPwd(String sysPwd) {
		this.sysPwd = sysPwd;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPsnTy() {
		return psnTy;
	}
	public void setPsnTy(String psnTy) {
		this.psnTy = psnTy;
	}
	public String getPhylum() {
		return phylum;
	}
	public void setPhylum(String phylum) {
		this.phylum = phylum;
	}
	public String getiOptFor() {
		return iOptFor;
	}
	public void setiOptFor(String iOptFor) {
		this.iOptFor = iOptFor;
	}
	public String getiDocTy() {
		return iDocTy;
	}
	public void setiDocTy(String iDocTy) {
		this.iDocTy = iDocTy;
	}
	public String getiDocNum() {
		return iDocNum;
	}
	public void setiDocNum(String iDocNum) {
		this.iDocNum = iDocNum;
	}
	public String getiFormNum() {
		return iFormNum;
	}
	public void setiFormNum(String iFormNum) {
		this.iFormNum = iFormNum;
	}
	public String getiChnlCode() {
		return iChnlCode;
	}
	public void setiChnlCode(String iChnlCode) {
		this.iChnlCode = iChnlCode;
	}
	public String getiEnName() {
		return iEnName;
	}
	public void setiEnName(String iEnName) {
		this.iEnName = iEnName;
	}
	public String getiCtMail() {
		return iCtMail;
	}
	public void setiCtMail(String iCtMail) {
		this.iCtMail = iCtMail;
	}
	public String getiCtPhone() {
		return iCtPhone;
	}
	public void setiCtPhone(String iCtPhone) {
		this.iCtPhone = iCtPhone;
	}
	public String getiDob() {
		return iDob;
	}
	public void setiDob(String iDob) {
		this.iDob = iDob;
	}
	public String getiOpt4Dm() {
		return iOpt4Dm;
	}
	public void setiOpt4Dm(String iOpt4Dm) {
		this.iOpt4Dm = iOpt4Dm;
	}
	public String getiLang() {
		return iLang;
	}
	public void setiLang(String iLang) {
		this.iLang = iLang;
	}
	public int getiNuLtsSubn() {
		return iNuLtsSubn;
	}
	public void setiNuLtsSubn(int iNuLtsSubn) {
		this.iNuLtsSubn = iNuLtsSubn;
	}
	public int getiNuMobSubn() {
		return iNuMobSubn;
	}
	public void setiNuMobSubn(int iNuMobSubn) {
		this.iNuMobSubn = iNuMobSubn;
	}
	public int getiNuImsSubn() {
		return iNuImsSubn;
	}
	public void setiNuImsSubn(int iNuImsSubn) {
		this.iNuImsSubn = iNuImsSubn;
	}
	public int getiNuTvSubn() {
		return iNuTvSubn;
	}
	public void setiNuTvSubn(int iNuTvSubn) {
		this.iNuTvSubn = iNuTvSubn;
	}
	public String getiVisitTs() {
		return iVisitTs;
	}
	public void setiVisitTs(String iVisitTs) {
		this.iVisitTs = iVisitTs;
	}
		
}
