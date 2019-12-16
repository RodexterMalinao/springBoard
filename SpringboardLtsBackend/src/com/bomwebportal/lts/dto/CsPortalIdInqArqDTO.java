package com.bomwebportal.lts.dto;

import java.io.Serializable;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;


public class CsPortalIdInqArqDTO extends CsPortalArqDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8257667414762725487L;
	private String iDocTy; // Document Type
	private String iDocNum; // Document Number
	private String iLi4MyHkt; // Login Id to be created in MyHKT
	private String iLi4Club; // Login Id to be created in the Club
	private String oIdStatus; // The status of the supplied Id Document in MyHKT
	private String oCorrMyHktLi; // The corresponding MyHKT Login Id of the Id Document
	private String oCorrClubLi; // The corresponding the Club Login Id of the Id Document
	private String oCorrClubMbrId; // The corresponding the Club Membership Id of the Id Document
	private String oMyHktLiStatus; // The status of iLi4MyHkt in MyHKT
	private String oClubLiStatus; // The status of iLi4Club in the Club
	private String oPhantomAcc; // Phantom Account indicator
	private String oCareStatus; // status of CARE program, 
								// "" Empty String, no CARE program record
								// "N" Not Registered Yet
								// "O" Opt Out (by Customer)
								// "G" Opt Out (by iGuard)
								// "I" Opt In
	
	private String oCareLuts;	// Last Update Timestamp of CARE program status, YYYYMMDDHHMISS
	private String oBiptStatus;	// status of CARECash program. Refer to oCareStatus for the possible values.
	private String oBiptLuts; 	// Last Update Timestamp of CARECash program status, YYYYMMDDHHMISS
	private String oCareVisit; //Last Visit Date of the CARE and CARE Program (in the format of YYYYMMDDHHMMSS)
	
	/**
	 * @return the iDocTy
	 */
	public String getiDocTy() {
		return iDocTy;
	}
	/**
	 * @param iDocTy the iDocTy to set
	 */
	public void setiDocTy(String iDocTy) {
		this.iDocTy = iDocTy;
	}
	/**
	 * @return the iDocNum
	 */
	public String getiDocNum() {
		return iDocNum;
	}
	/**
	 * @param iDocNum the iDocNum to set
	 */
	public void setiDocNum(String iDocNum) {
		this.iDocNum = iDocNum;
	}	
	/**
	 * @return the iLi4MyHkt
	 */
	public String getiLi4MyHkt() {
		return iLi4MyHkt;
	}
	/**
	 * @param iLi4MyHkt the iLi4MyHkt to set
	 */
	public void setiLi4MyHkt(String iLi4MyHkt) {
		this.iLi4MyHkt = iLi4MyHkt;
	}
	/**
	 * @return the iLi4Club
	 */
	public String getiLi4Club() {
		return iLi4Club;
	}
	/**
	 * @param iLi4Club the iLi4Club to set
	 */
	public void setiLi4Club(String iLi4Club) {
		this.iLi4Club = iLi4Club;
	}
	/**
	 * @return the oIdStatus
	 */
	public String getoIdStatus() {
		return oIdStatus;
	}
	/**
	 * @param oIdStatus the oIdStatus to set
	 */
	public void setoIdStatus(String oIdStatus) {
		this.oIdStatus = oIdStatus;
	}
	/**
	 * @return the oCorrMyHktLi
	 */
	public String getoCorrMyHktLi() {
		return oCorrMyHktLi;
	}
	/**
	 * @param oCorrMyHktLi the oCorrMyHktLi to set
	 */
	public void setoCorrMyHktLi(String oCorrMyHktLi) {
		this.oCorrMyHktLi = oCorrMyHktLi;
	}
	/**
	 * @return the oCorrClubLi
	 */
	public String getoCorrClubLi() {
		return oCorrClubLi;
	}
	/**
	 * @param oCorrClubLi the oCorrClubLi to set
	 */
	public void setoCorrClubLi(String oCorrClubLi) {
		this.oCorrClubLi = oCorrClubLi;
	}
	/**
	 * @return the oCorrClubMbrId
	 */
	public String getoCorrClubMbrId() {
		return oCorrClubMbrId;
	}
	/**
	 * @param oCorrClubMbrId the oCorrClubMbrId to set
	 */
	public void setoCorrClubMbrId(String oCorrClubMbrId) {
		this.oCorrClubMbrId = oCorrClubMbrId;
	}
	/**
	 * @return the oMyHktLiStatus
	 */
	public String getoMyHktLiStatus() {
		return oMyHktLiStatus;
	}
	/**
	 * @param oMyHktLiStatus the oMyHktLiStatus to set
	 */
	public void setoMyHktLiStatus(String oMyHktLiStatus) {
		this.oMyHktLiStatus = oMyHktLiStatus;
	}
	/**
	 * @return the oClubLiStatus
	 */
	public String getoClubLiStatus() {
		return oClubLiStatus;
	}
	/**
	 * @param oClubLiStatus the oClubLiStatus to set
	 */
	public void setoClubLiStatus(String oClubLiStatus) {
		this.oClubLiStatus = oClubLiStatus;
	}
		
	public String getoPhantomAcc() {
		return oPhantomAcc;
	}
	public void setoPhantomAcc(String oPhantomAcc) {
		this.oPhantomAcc = oPhantomAcc;
	}
		
	public String getoCareStatus() {
		return oCareStatus;
	}
	public void setoCareStatus(String oCareStatus) {
		this.oCareStatus = oCareStatus;
	}
	public String getoCareLuts() {
		return oCareLuts;
	}
	public void setoCareLuts(String oCareLuts) {
		this.oCareLuts = oCareLuts;
	}
	public String getoBiptStatus() {
		return oBiptStatus;
	}
	public void setoBiptStatus(String oBiptStatus) {
		this.oBiptStatus = oBiptStatus;
	}
	public String getoBiptLuts() {
		return oBiptLuts;
	}
	public void setoBiptLuts(String oBiptLuts) {
		this.oBiptLuts = oBiptLuts;
	}
		
	public String getoCareVisit() {
		return oCareVisit;
	}
	public void setoCareVisit(String oCareVisit) {
		this.oCareVisit = oCareVisit;
	}
	public boolean isCustAldyMyHkt() {
		return StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS, this.getReply())		
		&& StringUtils.isNotBlank(this.getoCorrMyHktLi());
	}
	
	public boolean isCustAldyTheClub() {
		return StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS, this.getReply())		
		&& StringUtils.isNotBlank(this.getoCorrClubLi());
	}
	
	public boolean isDocValid() {
		return !ArrayUtils.contains(LtsCsPortalBackendConstant.CSP_REPLY_INVALID_DOC_NUM, this.getReply());
	}	
	
	public boolean isLiInUse() {
		return isHktLiInUse() || isClubLiInUse();
	}
	
	public boolean isHktLiInUse() {
		return StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS, this.getReply())
		&& StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_LOGIN_ID_IN_USE, this.getoMyHktLiStatus());
	}
	
	public boolean isClubLiInUse() {
		return StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS, this.getReply())
		&& StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_LOGIN_ID_IN_USE, this.getoClubLiStatus());
	}
			
}
