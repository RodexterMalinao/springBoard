package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ServiceDetailLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 8346582158976619698L;

	private String orderId; // BOMWEB_ORDER_SERVICE_LTS.ORDER_ID
	private OraNumber dtlId; // BOMWEB_ORDER_SERVICE_LTS.DTL_ID
	private String datCd; // BOMWEB_ORDER_SERVICE_LTS.DAT_CD
	private String twoNInd; // BOMWEB_ORDER_SERVICE_LTS.TWO_N_IND
	private String duplexInd; // BOMWEB_ORDER_SERVICE_LTS.DUPLEX_IND
	private String redeemPremiumInd; // BOMWEB_ORDER_SERVICE_LTS.REDEEM_PREMIUM_IND
	private String pendingApprovalCd; // BOMWEB_ORDER_SERVICE_LTS.PENDING_APPROVAL_CD
	private String paperBillInd; // BOMWEB_ORDER_SERVICE_LTS.PAPER_BILL_IND
	private String callPlanDowngradeInd; // BOMWEB_ORDER_SERVICE_LTS.CALL_PLAN_DOWNGRADE_IND
	private String createBy; // BOMWEB_ORDER_SERVICE_LTS.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ORDER_SERVICE_LTS.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ORDER_SERVICE_LTS.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_SERVICE_LTS.LAST_UPD_DATE
	private String cancelVasInd; // BOMWEB_ORDER_SERVICE_LTS.CANCEL_VAS_IND
	private String frozenExchInd; // BOMWEB_ORDER_SERVICE_LTS.FROZEN_EXCH_IND
	private String reservedDnInd; // BOMWEB_ORDER_SERVICE_LTS.RESERVED_DN_IND
	private OraNumber deviceType; // BOMWEB_ORDER_SERVICE_LTS.DEVICE_TYPE
	private String sharedBsnInd; // BOMWEB_ORDER_SERVICE_LTS.SHARED_BSN_IND
	private String custNameNotMatch;
	private String exDirInd;
	private String fromSrvType; // BOMWEB_ORDER_SERVICE_LTS.FROM_SRV_TYPE
	private String toSrvType; // BOMWEB_ORDER_SERVICE_LTS.TO_SRV_TYPE
	private String returnDeviceInd; // BOMWEB_ORDER_SERVICE_LTS.RETURN_DEVICE_IND
	private String oneTwoThreeTvInd; // BOMWEB_ORDER_SERVICE_LTS.ONE_TWO_THREE_TV_IND
	private String discCallingCardInd; // BOMWEB_ORDER_SERVICE_LTS.DISC_CALLING_CARD_IND
	private String discFiveNaInd; // BOMWEB_ORDER_SERVICE_LTS.DISC_FIVE_NA_IND
	private String dFormSerial; // BOMWEB_ORDER_SERVICE_LTS.D_FORM_SERIAL
	private String waiveDFormReasonCd; // BOMWEB_ORDER_SERVICE_LTS.WAIVE_D_FORM_REASON_CD
	private String fchNum; // BOMWEB_ORDER_SERVICE_LTS.FCH_NUM
	private String eqtCollectionAddr; // BOMWEB_ORDER_SERVICE_LTS.EQT_COLLECTION_ADDR
	private String subc2gBundleInd; // BOMWEB_ORDER_SERVICE_LTS.SUBC_2G_BUNDLE_IND
	private String lostEquipmentCd; // BOMWEB_ORDER_SERVICE_LTS.LOST_EQUIPMENT_CD
	private String switchPlanInd; // BOMWEB_ORDER_SERVICE_LTS.SWITCH_PLAN_IND
	private OraNumber adjustAmount; // BOMWEB_ORDER_SERVICE_LTS.ADJUST_AMOUNT
	private String bundle2gNum; // BOMWEB_ORDER_SERVICE_LTS.BUNDLE2G_NUM
    private String deceaseType; //BOMWEB_ORDER_SERVICE_LTS.DECEASE_TYPE
    private OraDate adjustStartDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER.ADJUST_START_DATE
    private OraDate adjustEndDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER.ADJUST_END_DATE
	private OraNumber adjustRate; // BOMWEB_ORDER_SERVICE_LTS.ADJUST_AMOUNT
	private String dnSource; // BOMWEB_ORDER_SERVICE_LTS.DN_SOURCE
    private String nrpAccountCd; // BOMWEB_ORDER_SERVICE_LTS.NRP_ACCOUNT_CD
    private String nrpBoc; // BOMWEB_ORDER_SERVICE_LTS.NRP_BOC
    private String nrpProjectCd; // BOMWEB_ORDER_SERVICE_LTS.NRP_PROJECT_CD
    private String extEqtCollect; // BOMWEB_ORDER_SERVICE_LTS.EXT_EQT_COLLECT
    private String exDirName; // BOMWEB_ORDER_SERVICE_LTS.EX_DIR_NAME
    private String srvNn; // BOMWEB_ORDER_SERVICE_LTS.SRV_NN
    private String dnStatus; // BOMWEB_ORDER_SERVICE_LTS.DN_STATUS
    private String legacyOrdNum; // BOMWEB_ORDER_SERVICE_LTS.LEGACY_ORD_NUM
	private String legacyActvSeq; // BOMWEB_ORDER_SERVICE_LTS.LEGACY_ACTV_SEQ
    private String gatewayNum; // BOMWEB_ORDER_SERVICE_LTS.GATEWAY_NUM
    private String ffpOnlyInd; // BOMWEB_ORDER_SERVICE_LTS.FFP_ONLY_IND
    private String oldOsType; // BOMWEB_ORDER_SERVICE_LTS.OLD_OS_TYPE
    private String newOsType; // BOMWEB_ORDER_SERVICE_LTS.NEW_OS_TYPE
    private String updDnProfile; //BOMWEB_ORDER_SERVICE_LTS.UPD_DN_PROFILE
    
	public ServiceDetailLtsDAO() {
		primaryKeyFields = new String[] { "orderId", "dtlId" };
	}
	
	public String getTableName() {
		return "BOMWEB_ORDER_SERVICE_LTS";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getDatCd() {
		return this.datCd;
	}

	public String getTwoNInd() {
		return this.twoNInd;
	}

	public String getDuplexInd() {
		return this.duplexInd;
	}

	public String getRedeemPremiumInd() {
		return this.redeemPremiumInd;
	}

	public String getPendingApprovalCd() {
		return this.pendingApprovalCd;
	}

	public String getPaperBillInd() {
		return this.paperBillInd;
	}

	public String getCallPlanDowngradeInd() {
		return this.callPlanDowngradeInd;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getCancelVasInd() {
		return this.cancelVasInd;
	}

	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
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

	public void setDatCd(String pDatCd) {
		this.datCd = pDatCd;
	}

	public void setTwoNInd(String pTwoNInd) {
		this.twoNInd = pTwoNInd;
	}

	public void setDuplexInd(String pDuplexInd) {
		this.duplexInd = pDuplexInd;
	}

	public void setRedeemPremiumInd(String pRedeemPremiumInd) {
		this.redeemPremiumInd = pRedeemPremiumInd;
	}

	public void setPendingApprovalCd(String pPendingApprovalCd) {
		this.pendingApprovalCd = pPendingApprovalCd;
	}

	public void setPaperBillInd(String pPaperBillInd) {
		this.paperBillInd = pPaperBillInd;
	}

	public void setCallPlanDowngradeInd(String pCallPlanDowngradeInd) {
		this.callPlanDowngradeInd = pCallPlanDowngradeInd;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setCancelVasInd(String pCancelVasInd) {
		this.cancelVasInd = pCancelVasInd;
	}

	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getFrozenExchInd() {
		return frozenExchInd;
	}

	public void setFrozenExchInd(String frozenExchInd) {
		this.frozenExchInd = frozenExchInd;
	}

	public String getReservedDnInd() {
		return this.reservedDnInd;
	}

	public void setReservedDnInd(String pReservedDnInd) {
		this.reservedDnInd = pReservedDnInd;
	}
	
	public String getDeviceType() {
		return this.deviceType != null ? this.deviceType.toString() : null;
	}
	
	public void setDeviceType(String pDeviceType) {
		this.deviceType = new OraNumber(pDeviceType);
	}

	public String getSharedBsnInd() {
		return sharedBsnInd;
	}

	public void setSharedBsnInd(String sharedBsnInd) {
		this.sharedBsnInd = sharedBsnInd;
	}

	public String getCustNameNotMatch() {
		return custNameNotMatch;
	}

	public void setCustNameNotMatch(String custNameNotMatch) {
		this.custNameNotMatch = custNameNotMatch;
	}

	public String getExDirInd() {
		return exDirInd;
	}

	public void setExDirInd(String exDirInd) {
		this.exDirInd = exDirInd;
	}

	public String getFromSrvType() {
		return fromSrvType;
	}

	public void setFromSrvType(String fromSrvType) {
		this.fromSrvType = fromSrvType;
	}

	public String getToSrvType() {
		return toSrvType;
	}

	public void setToSrvType(String toSrvType) {
		this.toSrvType = toSrvType;
	}

	public String getReturnDeviceInd() {
		return returnDeviceInd;
	}

	public void setReturnDeviceInd(String returnDeviceInd) {
		this.returnDeviceInd = returnDeviceInd;
	}

	public void setDeviceType(OraNumber deviceType) {
		this.deviceType = deviceType;
	}

	public String getOneTwoThreeTvInd() {
		return oneTwoThreeTvInd;
	}

	public void setOneTwoThreeTvInd(String oneTwoThreeTvInd) {
		this.oneTwoThreeTvInd = oneTwoThreeTvInd;
	}

	public String getWaiveDFormReasonCd() {
		return waiveDFormReasonCd;
	}

	public void setWaiveDFormReasonCd(String waiveDFormReasonCd) {
		this.waiveDFormReasonCd = waiveDFormReasonCd;
	}

	public String getFchNum() {
		return fchNum;
	}

	public void setFchNum(String fchNum) {
		this.fchNum = fchNum;
	}

	public String getEqtCollectionAddr() {
		return eqtCollectionAddr;
	}

	public void setEqtCollectionAddr(String eqtCollectionAddr) {
		this.eqtCollectionAddr = eqtCollectionAddr;
	}
	
	public String getDeceaseType() { 
		return this.deceaseType; 
	}

	public String getDiscCallingCardInd() {
		return discCallingCardInd;
	}

	public void setDiscCallingCardInd(String discCallingCardInd) {
		this.discCallingCardInd = discCallingCardInd;
	}

	public String getDiscFiveNaInd() {
		return discFiveNaInd;
	}

	public void setDiscFiveNaInd(String discFiveNaInd) {
		this.discFiveNaInd = discFiveNaInd;
	}

	public String getDFormSerial() {
		return dFormSerial;
	}

	public void setDFormSerial(String dFormSerial) {
		this.dFormSerial = dFormSerial;
	}

	public String getSubc2gBundleInd() {
		return subc2gBundleInd;
	}

	public void setSubc2gBundleInd(String subc2gBundleInd) {
		this.subc2gBundleInd = subc2gBundleInd;
	}

	public String getLostEquipmentCd() {
		return lostEquipmentCd;
	}

	public void setLostEquipmentCd(String lostEquipmentCd) {
		this.lostEquipmentCd = lostEquipmentCd;
	}

	public String getSwitchPlanInd() {
		return switchPlanInd;
	}

	public void setSwitchPlanInd(String switchPlanInd) {
		this.switchPlanInd = switchPlanInd;
	}
	
	public String getBundle2gNum() {
		return this.bundle2gNum;
	}
	
	public void setBundle2gNum(String pBundle2gNum) {
		this.bundle2gNum = pBundle2gNum;
	}
	
	public String getAdjustAmount() {
		return this.adjustAmount != null ? this.adjustAmount.toString() : null;
	}

	public void setAdjustAmount(String pAdjustAmount) {
		this.adjustAmount = new OraNumber(pAdjustAmount);
	}
	
	public void setDeceaseType(String pDeceaseType) { 
		this.deceaseType = pDeceaseType; 
	}

	public void setAdjustStartDate(OraDate adjustStartDate) {
		this.adjustStartDate = adjustStartDate;
	}
	
	public void setAdjustStartDate(String pAdjustStartDate) {
		this.adjustStartDate = new OraDateYYYYMMDDHH24MISS(pAdjustStartDate);
	}
	
	public String getAdjustStartDate() {
		return this.adjustStartDate != null ? this.adjustStartDate.toString() : null;
	}
	
	public OraDate getAdjustStartDateORACLE() {
		return this.adjustStartDate;
	}
	
	public void setAdjustEndDate(OraDate adjustEndDate) {
		this.adjustEndDate = adjustEndDate;
	}
	
	public void setAdjustEndDate(String pAdjustEndDate) {
		this.adjustEndDate = new OraDateYYYYMMDDHH24MISS(pAdjustEndDate);
	}
	
	public String getAdjustEndDate() {
		return this.adjustEndDate != null ? this.adjustEndDate.toString() : null;
	}
	
	public OraDate getAdjustEndDateORACLE() {
		return this.adjustEndDate;
	}
	
	public String getAdjustRate() {
		return this.adjustRate != null ? this.adjustRate.toString() : null;
	}

	public void setAdjustRate(String pAdjustRate) {
		this.adjustRate = new OraNumber(pAdjustRate);
	}

	public String getDnSource() {
		return dnSource;
	}

	public void setDnSource(String dnSource) {
		this.dnSource = dnSource;
	}

	public String getNrpAccountCd() {
		return nrpAccountCd;
	}

	public void setNrpAccountCd(String nrpAccountCd) {
		this.nrpAccountCd = nrpAccountCd;
	}

	public String getNrpBoc() {
		return nrpBoc;
	}

	public void setNrpBoc(String nrpBoc) {
		this.nrpBoc = nrpBoc;
	}

	public String getNrpProjectCd() {
		return nrpProjectCd;
	}

	public void setNrpProjectCd(String nrpProjectCd) {
		this.nrpProjectCd = nrpProjectCd;
	}

	public String getExtEqtCollect() {
		return extEqtCollect;
	}

	public void setExtEqtCollect(String extEqtCollect) {
		this.extEqtCollect = extEqtCollect;
	}

	/**
	 * @return the exDirName
	 */
	public String getExDirName() {
		return exDirName;
	}

	/**
	 * @param exDirName the exDirName to set
	 */
	public void setExDirName(String exDirName) {
		this.exDirName = exDirName;
	}

	/**
	 * @return the srvNn
	 */
	public String getSrvNn() {
		return srvNn;
	}

	/**
	 * @param srvNn the srvNn to set
	 */
	public void setSrvNn(String srvNn) {
		this.srvNn = srvNn;
	}

	/**
	 * @return the dnStatus
	 */
	public String getDnStatus() {
		return dnStatus;
	}

	/**
	 * @param dnStatus the dnStatus to set
	 */
	public void setDnStatus(String dnStatus) {
		this.dnStatus = dnStatus;
	}

	/**
	 * @return the legacyOrdNum
	 */
	public String getLegacyOrdNum() {
		return legacyOrdNum;
	}

	/**
	 * @param legacyOrdNum the legacyOrdNum to set
	 */
	public void setLegacyOrdNum(String legacyOrdNum) {
		this.legacyOrdNum = legacyOrdNum;
	}

	/**
	 * @return the legacyActvSeq
	 */
	public String getLegacyActvSeq() {
		return legacyActvSeq;
	}

	/**
	 * @param legacyActvSeq the legacyActvSeq to set
	 */
	public void setLegacyActvSeq(String legacyActvSeq) {
		this.legacyActvSeq = legacyActvSeq;
	}

	/**
	 * @return the gatewayNum
	 */
	public String getGatewayNum() {
		return gatewayNum;
	}

	/**
	 * @param gatewayNum the gatewayNum to set
	 */
	public void setGatewayNum(String gatewayNum) {
		this.gatewayNum = gatewayNum;
	}

	public String getFfpOnlyInd() {
		return ffpOnlyInd;
	}

	public void setFfpOnlyInd(String ffpOnlyInd) {
		this.ffpOnlyInd = ffpOnlyInd;
	}

	public String getOldOsType() {
		return oldOsType;
	}

	public void setOldOsType(String oldOsType) {
		this.oldOsType = oldOsType;
	}

	public String getNewOsType() {
		return newOsType;
	}

	public void setNewOsType(String newOsType) {
		this.newOsType = newOsType;
	}

	public String getUpdDnProfile() {
		return updDnProfile;
	}

	public void setUpdDnProfile(String updDnProfile) {
		this.updDnProfile = updDnProfile;
	}
	
}
