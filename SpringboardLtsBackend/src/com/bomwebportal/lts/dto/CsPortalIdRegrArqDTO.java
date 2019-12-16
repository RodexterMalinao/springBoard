package com.bomwebportal.lts.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;


public class CsPortalIdRegrArqDTO extends CsPortalArqDTO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7954858397943864569L;
	private String iDocTy; // Document Type
	private String iDocNum; // Document Number
	private String iPremier;  // Premier Indicator
	private String iLob; // LOB
	private String iSrvNum; // Service Number
	private String iSalesChl; // Sales Channel
	private String iTeamCd; // Team Code
	private String iStaffId; // Staff Id
	private String iSrcSys; // System who invokes this API
	private String iOrderNo; // Order Number
	private String iMyHktLi; // MyHKT Login Id
	private String iClubLi; // Club Login Id
	private String iNick_name; // Nick Name
	private String iCt_mob; // Contact Mobile
	private String iAcptTnc; // Accepted TNC (for MyHKT)
	private String iLang; // Language
	private String oMyHktRes;
	private String oMyHktResZhMsg;
	private String oMyHktResEnMsg;
	private String oClubRes;
	private String oClubResMsg;
	private String iClubOptOutPromo;
	private String iClubOptOutRea;
	private String iClubOptOutRem;
	private String iMyHktOptOutPm;
	
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
	 * @return the iPremier
	 */
	public String getiPremier() {
		return iPremier;
	}
	/**
	 * @param iPremier the iPremier to set
	 */
	public void setiPremier(String iPremier) {
		this.iPremier = iPremier;
	}
	/**
	 * @return the iLob
	 */
	public String getiLob() {
		return iLob;
	}
	/**
	 * @param iLob the iLob to set
	 */
	public void setiLob(String iLob) {
		this.iLob = iLob;
	}
	/**
	 * @return the iSrvNum
	 */
	public String getiSrvNum() {
		return iSrvNum;
	}
	/**
	 * @param iSrvNum the iSrvNum to set
	 */
	public void setiSrvNum(String iSrvNum) {
		this.iSrvNum = iSrvNum;
	}
	/**
	 * @return the iSalesChl
	 */
	public String getiSalesChl() {
		return iSalesChl;
	}
	/**
	 * @param iSalesChl the iSalesChl to set
	 */
	public void setiSalesChl(String iSalesChl) {
		this.iSalesChl = iSalesChl;
	}
	/**
	 * @return the iTeamCd
	 */
	public String getiTeamCd() {
		return iTeamCd;
	}
	/**
	 * @param iTeamCd the iTeamCd to set
	 */
	public void setiTeamCd(String iTeamCd) {
		this.iTeamCd = iTeamCd;
	}
	/**
	 * @return the iStaffId
	 */
	public String getiStaffId() {
		return iStaffId;
	}
	/**
	 * @param iStaffId the iStaffId to set
	 */
	public void setiStaffId(String iStaffId) {
		this.iStaffId = iStaffId;
	}
	/**
	 * @return the iSrcSys
	 */
	public String getiSrcSys() {
		return iSrcSys;
	}
	/**
	 * @param iSrcSys the iSrcSys to set
	 */
	public void setiSrcSys(String iSrcSys) {
		this.iSrcSys = iSrcSys;
	}
	/**
	 * @return the iOrderNo
	 */
	public String getiOrderNo() {
		return iOrderNo;
	}
	/**
	 * @param iOrderNo the iOrderNo to set
	 */
	public void setiOrderNo(String iOrderNo) {
		this.iOrderNo = iOrderNo;
	}
	/**
	 * @return the iMyHktLi
	 */
	public String getiMyHktLi() {
		return iMyHktLi;
	}
	/**
	 * @param iMyHktLi the iMyHktLi to set
	 */
	public void setiMyHktLi(String iMyHktLi) {
		this.iMyHktLi = iMyHktLi;
	}
	/**
	 * @return the iClubLi
	 */
	public String getiClubLi() {
		return iClubLi;
	}
	/**
	 * @param iClubLi the iClubLi to set
	 */
	public void setiClubLi(String iClubLi) {
		this.iClubLi = iClubLi;
	}
	/**
	 * @return the iNick_name
	 */
	public String getiNick_name() {
		return iNick_name;
	}
	/**
	 * @param iNickName the iNick_name to set
	 */
	public void setiNick_name(String iNickName) {
		iNick_name = iNickName;
	}
	/**
	 * @return the iCt_mob
	 */
	public String getiCt_mob() {
		return iCt_mob;
	}
	/**
	 * @param iCtMob the iCt_mob to set
	 */
	public void setiCt_mob(String iCtMob) {
		iCt_mob = iCtMob;
	}
	/**
	 * @return the iAcptTnc
	 */
	public String getiAcptTnc() {
		return iAcptTnc;
	}
	/**
	 * @param iAcptTnc the iAcptTnc to set
	 */
	public void setiAcptTnc(String iAcptTnc) {
		this.iAcptTnc = iAcptTnc;
	}
	/**
	 * @return the iLang
	 */
	public String getiLang() {
		return iLang;
	}
	/**
	 * @param iLang the iLang to set
	 */
	public void setiLang(String iLang) {
		this.iLang = iLang;
	}
	/**
	 * @return the oMyHktRes
	 */
	public String getoMyHktRes() {
		return oMyHktRes;
	}
	/**
	 * @param oMyHktRes the oMyHktRes to set
	 */
	public void setoMyHktRes(String oMyHktRes) {
		this.oMyHktRes = oMyHktRes;
	}
	/**
	 * @return the oMyHktResZhMsg
	 */
	public String getoMyHktResZhMsg() {
		return oMyHktResZhMsg;
	}
	/**
	 * @param oMyHktResZhMsg the oMyHktResZhMsg to set
	 */
	public void setoMyHktResZhMsg(String oMyHktResZhMsg) {
		this.oMyHktResZhMsg = oMyHktResZhMsg;
	}
	/**
	 * @return the oMyHktResEnMsg
	 */
	public String getoMyHktResEnMsg() {
		return oMyHktResEnMsg;
	}
	/**
	 * @param oMyHktResEnMsg the oMyHktResEnMsg to set
	 */
	public void setoMyHktResEnMsg(String oMyHktResEnMsg) {
		this.oMyHktResEnMsg = oMyHktResEnMsg;
	}
	/**
	 * @return the oClubRes
	 */
	public String getoClubRes() {
		return oClubRes;
	}
	/**
	 * @param oClubRes the oClubRes to set
	 */
	public void setoClubRes(String oClubRes) {
		this.oClubRes = oClubRes;
	}
	/**
	 * @return the oClubResMsg
	 */
	public String getoClubResMsg() {
		return oClubResMsg;
	}
	/**
	 * @param oClubResMsg the oClubResMsg to set
	 */
	public void setoClubResMsg(String oClubResMsg) {
		this.oClubResMsg = oClubResMsg;
	}	
	
	public String getiClubOptOutPromo() {
		return iClubOptOutPromo;
	}
	public void setiClubOptOutPromo(String iClubOptOutPromo) {
		this.iClubOptOutPromo = iClubOptOutPromo;
	}
	public String getiClubOptOutRea() {
		return iClubOptOutRea;
	}
	public void setiClubOptOutRea(String iClubOptOutRea) {
		this.iClubOptOutRea = iClubOptOutRea;
	}
	public String getiClubOptOutRem() {
		return iClubOptOutRem;
	}
	public void setiClubOptOutRem(String iClubOptOutRem) {
		this.iClubOptOutRem = iClubOptOutRem;
	}
	public String getiMyHktOptOutPm() {
		return iMyHktOptOutPm;
	}
	public void setiMyHktOptOutPm(String iMyHktOptOutPm) {
		this.iMyHktOptOutPm = iMyHktOptOutPm;
	}

	
	public boolean isMyHktRegSucces() {
		return StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS, this.getReply())
		&& StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS, this.getoMyHktRes());
	}
	
	public boolean isTheClubRegSucces() {
		return StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS, this.getReply())
		&& StringUtils.equals(LtsCsPortalBackendConstant.CSP_REPLY_OK, this.getoClubRes());
	}
	
}
