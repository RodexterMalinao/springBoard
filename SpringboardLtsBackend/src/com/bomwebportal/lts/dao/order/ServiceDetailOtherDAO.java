package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ServiceDetailOtherDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -5874144339745627751L;

	private String orderId; // BOMWEB_ORDER_SERVICE_OTHER.ORDER_ID
	private OraNumber dtlId; // BOMWEB_ORDER_SERVICE_OTHER.DTL_ID
	private String loginId; // BOMWEB_ORDER_SERVICE_OTHER.LOGIN_ID
	private String existSrvTypeCd; // BOMWEB_ORDER_SERVICE_OTHER.EXIST_SRV_TYPE_CD
	private String newSrvTypeCd; // BOMWEB_ORDER_SERVICE_OTHER.NEW_SRV_TYPE_CD
	private OraNumber existBandwidth; // BOMWEB_ORDER_SERVICE_OTHER.EXIST_BANDWIDTH
	private OraNumber newBandwidth; // BOMWEB_ORDER_SERVICE_OTHER.NEW_BANDWIDTH
	private OraNumber assgnBandwidth; // BOMWEB_ORDER_SERVICE_OTHER.ASSGN_BANDWIDTH
	private String relatedSbOrderId; // BOMWEB_ORDER_SERVICE_OTHER.RELATED_SB_ORDER_ID
	private String existTvSrvType; // BOMWEB_ORDER_SERVICE_OTHER.EXIST_TV_SRV_TYPE
	private String newTvSrvType; // BOMWEB_ORDER_SERVICE_OTHER.NEW_TV_SRV_TYPE
	private String deactNowtvInd; // BOMWEB_ORDER_SERVICE_OTHER.DEACT_NOWTV_IND
	private String nowtvGrpCd; // BOMWEB_ORDER_SERVICE_OTHER.NOWTV_GRP_CD
	private String nowtvTvCd; // BOMWEB_ORDER_SERVICE_OTHER.NOWTV_TV_CD
	private String nowtvMirrorInd; // BOMWEB_ORDER_SERVICE_OTHER.NOWTV_MIRROR_IND
	private String createBy; // BOMWEB_ORDER_SERVICE_OTHER.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ORDER_SERVICE_OTHER.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ORDER_SERVICE_OTHER.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_SERVICE_OTHER.LAST_UPD_DATE
	private String modemArrangement; // BOMWEB_ORDER_SERVICE_OTHER.MODEM_ARRANGEMENT
	private String assgnTechnology; // BOMWEB_ORDER_SERVICE_OTHER.ASSGN_TECHNOLOGY
	private String shareFsaType;  // BOMWEB_ORDER_SERVICE_OTHER.SHARE_FSA_TYPE
	private String mirrorFsa;  // BOMWEB_ORDER_SERVICE_OTHER.MIRROR_FSA
	private String existModem;	// BOMWEB_ORDER_SERVICE_OTHER.EXIST_MONDEM
	private String existArpu;	// BOMWEB_ORDER_SERVICE_OTHER.EXIST_ARPU
	private String existTechnology;	// BOMWEB_ORDER_SERVICE_OTHER.EXIST_TECHNOLOGY
	private String replaceExistFsa; // BOMWEB_ORDER_SERVICE_OTHER.REPLACE_EXIST_FSA
	private String existMirrorInd; // BOMWEB_ORDER_SERVICE_OTHER.EXIST_MIRROR_IND
	private String edfRef; // BOMWEB_ORDER_SERVICE_OTHER.EDF_REF
	private String existNowtvTvCd; // BOMWEB_ORDER_SERVICE_OTHER.EXIST_NOWTV_TV_CD
	private String repackNowtvInd; // BOMWEB_ORDER_SERVICE_OTHER.REPACK_NOWTV_IND
	private String terminatePcd; //BOMWEB_ORDER_SERVICE_OTHER.TERMINATE_PCD
	private String terminateTv; //BOMWEB_ORDER_SERVICE_OTHER.TERMINATE_TV
	private String manualLineTypeInd; //BOMWEB_ORDER_SERVICE_OTHER.MANUAL_LINE_TYPE_IND
	private String autoUpgraded; //BOMWEB_ORDER_SERVICE_OTHER.AUTO_UPGRADED
	private String noEyeFsa; //BOMWEB_ORDER_SERVICE_OTHER.NO_EYE_FSA_LIST
	private String lostModem; //BOMWEB_ORDER_SERVICE_OTHER.LOST_MODEM
	private String shareRentalRouter; //BOMWEB_ORDER_SERVICE_OTHER.SHARE_RENTAL_ROUTER
	private String shareBrmWifi; //BOMWEB_ORDER_SERVICE_OTHER.SHARE_BRM_WIFI
	
	public ServiceDetailOtherDAO() {
		primaryKeyFields = new String[] {"orderId", "dtlId"};
	}

	public String getTableName() {
		return "BOMWEB_ORDER_SERVICE_OTHER";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public String getExistSrvTypeCd() {
		return this.existSrvTypeCd;
	}

	public String getNewSrvTypeCd() {
		return this.newSrvTypeCd;
	}

	public String getRelatedSbOrderId() {
		return this.relatedSbOrderId;
	}

	public String getExistTvSrvType() {
		return this.existTvSrvType;
	}

	public String getNewTvSrvType() {
		return this.newTvSrvType;
	}

	public String getDeactNowtvInd() {
		return this.deactNowtvInd;
	}

	public String getNowtvGrpCd() {
		return this.nowtvGrpCd;
	}
//
//	public String getNowtvTvCd() {
//		return this.nowtvTvCd;
//	}

	public String getNowtvMirrorInd() {
		return this.nowtvMirrorInd;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}

	public String getExistBandwidth() {
		return this.existBandwidth != null ? this.existBandwidth.toString()
				: null;
	}

	public String getNewBandwidth() {
		return this.newBandwidth != null ? this.newBandwidth.toString() : null;
	}

	public String getAssgnBandwidth() {
		return this.assgnBandwidth != null ? this.assgnBandwidth.toString() : null;
	}
	
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}
	
	

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setLoginId(String pLoginId) {
		this.loginId = pLoginId;
	}

	public void setExistSrvTypeCd(String pExistSrvTypeCd) {
		this.existSrvTypeCd = pExistSrvTypeCd;
	}

	public void setNewSrvTypeCd(String pNewSrvTypeCd) {
		this.newSrvTypeCd = pNewSrvTypeCd;
	}

	public void setRelatedSbOrderId(String pRelatedSbOrderId) {
		this.relatedSbOrderId = pRelatedSbOrderId;
	}

	public void setExistTvSrvType(String pExistTvSrvType) {
		this.existTvSrvType = pExistTvSrvType;
	}

	public void setNewTvSrvType(String pNewTvSrvType) {
		this.newTvSrvType = pNewTvSrvType;
	}

	public void setDeactNowtvInd(String pDeactNowtvInd) {
		this.deactNowtvInd = pDeactNowtvInd;
	}

	public void setNowtvGrpCd(String pNowtvGrpCd) {
		this.nowtvGrpCd = pNowtvGrpCd;
	}

//	public void setNowtvTvCd(String pNowtvTvCd) {
//		this.nowtvTvCd = pNowtvTvCd;
//	}

	public void setNowtvMirrorInd(String pNowtvMirrorInd) {
		this.nowtvMirrorInd = pNowtvMirrorInd;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}

	public void setExistBandwidth(String pExistBandwidth) {
		this.existBandwidth = new OraNumber(pExistBandwidth);
	}
	
	public void setNewBandwidth(String pNewBandwidth) {
		this.newBandwidth = new OraNumber(pNewBandwidth);
	}

	public void setAssgnBandwidth(String pAssgnBandwidth) {
		this.assgnBandwidth = new OraNumber(pAssgnBandwidth);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
	
	public String getModemArrangement() {
		return this.modemArrangement;
	}
	
	public void setModemArrangement(String pModemArrangement) {
		this.modemArrangement = pModemArrangement;
	}

	public String getAssgnTechnology() {
		return assgnTechnology;
	}

	public void setAssgnTechnology(String assgnTechnology) {
		this.assgnTechnology = assgnTechnology;
	}

	public String getShareFsaType() {
		return shareFsaType;
	}

	public void setShareFsaType(String shareFsaType) {
		this.shareFsaType = shareFsaType;
	}

	public String getMirrorFsa() {
		return mirrorFsa;
	}

	public void setMirrorFsa(String mirrorFsa) {
		this.mirrorFsa = mirrorFsa;
	}

	public String getNowtvTvCd() {
		return nowtvTvCd;
	}

	public void setNowtvTvCd(String nowtvTvCd) {
		this.nowtvTvCd = nowtvTvCd;
	}

	public String getExistModem() {
		return existModem;
	}

	public void setExistModem(String existModem) {
		this.existModem = existModem;
	}

	public String getExistArpu() {
		return existArpu;
	}

	public void setExistArpu(String existArpu) {
		this.existArpu = existArpu;
	}

	public String getExistTechnology() {
		return this.existTechnology;
	}

	public void setExistTechnology(String pExistTechnology) {
		this.existTechnology = pExistTechnology;
	}

	public String getReplaceExistFsa() {
		return replaceExistFsa;
	}

	public void setReplaceExistFsa(String replaceExistFsa) {
		this.replaceExistFsa = replaceExistFsa;
	}

	public String getExistMirrorInd() {
		return existMirrorInd;
	}

	public void setExistMirrorInd(String existMirrorInd) {
		this.existMirrorInd = existMirrorInd;
	}

	public String getEdfRef() {
		return edfRef;
	}

	public void setEdfRef(String edfRef) {
		this.edfRef = edfRef;
	}

	public String getExistNowtvTvCd() {
		return existNowtvTvCd;
	}

	public String getTerminatePcd() { 
		return this.terminatePcd; 
	}
	  
	public String getTerminateTv() { 
		return this.terminateTv; 
	}
	
	public void setExistNowtvTvCd(String existNowtvTvCd) {
		this.existNowtvTvCd = existNowtvTvCd;
	}

	public String getRepackNowtvInd() {
		return repackNowtvInd;
	}

	public void setRepackNowtvInd(String repackNowtvInd) {
		this.repackNowtvInd = repackNowtvInd;
	}
	
	public void setTerminateTv(String pTerminateTv) {
		this.terminateTv = pTerminateTv; 
	}
	
	public void setTerminatePcd(String pTerminatePcd) { 
		this.terminatePcd = pTerminatePcd; 
	}

	public String getManualLineTypeInd() {
		return manualLineTypeInd;
	}

	public void setManualLineTypeInd(String manualLineTypeInd) {
		this.manualLineTypeInd = manualLineTypeInd;
	}

	public String getAutoUpgraded() {
		return autoUpgraded;
	}

	public void setAutoUpgraded(String autoUpgraded) {
		this.autoUpgraded = autoUpgraded;
	}

	public String getNoEyeFsa() {
		return noEyeFsa;
	}

	public void setNoEyeFsa(String noEyeFsa) {
		this.noEyeFsa = noEyeFsa;
	}

	public String getLostModem() {
		return lostModem;
	}

	public void setLostModem(String lostModem) {
		this.lostModem = lostModem;
	}

	public String getShareRentalRouter() {
		return shareRentalRouter;
	}

	public void setShareRentalRouter(String shareRentalRouter) {
		this.shareRentalRouter = shareRentalRouter;
	}

	public String getShareBrmWifi() {
		return shareBrmWifi;
	}

	public void setShareBrmWifi(String shareBrmWifi) {
		this.shareBrmWifi = shareBrmWifi;
	}

}
