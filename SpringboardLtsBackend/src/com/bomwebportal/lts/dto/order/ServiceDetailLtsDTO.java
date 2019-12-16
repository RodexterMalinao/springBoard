package com.bomwebportal.lts.dto.order;



public class ServiceDetailLtsDTO extends ServiceDetailDTO {

	private static final long serialVersionUID = -5060937806788827629L;
	
	private String datCd = null;
	private String twoNInd = null;
	private String duplexInd = null;
	private String redeemPremiumInd = null;
	private String pendingApprovalCd = null;
	private String callPlanDowngradeInd = null;
	private String paperBillInd = null;
	private String cancelVasInd = null;
	private String frozenExchInd = null;
	private String dnExchangeId = null;
	private String reservedDnInd = null;
	private boolean isTos = false;
	private String deviceType = null;
	private String sharedBsnInd = null;
	private String custNameNotMatch = null;
	private String exDirInd = null;
	private String fromSrvType = null;
	private String toSrvType = null;
	private ItemDetailLtsDTO[] itemDtls = null;
	private String returnDeviceInd = null;
	private String oneTwoThreeTvInd = null;
	private String discCallingCardInd = null;
	private String discFiveNaInd = null;
	private String dFormSerial = null;
	private String waiveDFormReasonCd = null;
	private String fchNum = null;
	private String eqtCollectionAddr = null;
	private String subc2gBundleInd = null;
	private String lostEquipmentCd = null;
	private String switchPlanInd = null;
    private String adjustAmount; 
    private String bundle2gNum;
    private String deceaseType = null;
    private String adjustStartDate;
    private String adjustEndDate;
    private String adjustRate;
    private String dnSource = null;
	private String nrpAccountCd = null;
	private String nrpBoc = null;
	private String nrpProjectCd = null;
	private String extEqtCollect = null;
	private String exDirName = null; 
	private String dnStatus = null;
	private String srvNn = null;
	private String legacyOrdNum = null;
	private String legacyActvSeq = null;
	private String gatewayNum = null;
    private String ffpOnlyInd = null;
    private String oldOsType = null;
    private String newOsType = null;
    private String updateDnProfile = null;
	
	public String getCustNameNotMatch() {
		return custNameNotMatch;
	}

	public void setCustNameNotMatch(String custNameNotMatch) {
		this.custNameNotMatch = custNameNotMatch;
	}

	public String getDatCd() {
		return datCd;
	}

	public void setDatCd(String datCd) {
		this.datCd = datCd;
	}

	public String getTwoNInd() {
		return twoNInd;
	}

	public void setTwoNInd(String twoNInd) {
		this.twoNInd = twoNInd;
	}

	public String getDuplexInd() {
		return duplexInd;
	}

	public void setDuplexInd(String duplexInd) {
		this.duplexInd = duplexInd;
	}

	public String getRedeemPremiumInd() {
		return redeemPremiumInd;
	}

	public void setRedeemPremiumInd(String redeemPremiumInd) {
		this.redeemPremiumInd = redeemPremiumInd;
	}

	public String getPendingApprovalCd() {
		return pendingApprovalCd;
	}

	public void setPendingApprovalCd(String pendingApprovalCd) {
		this.pendingApprovalCd = pendingApprovalCd;
	}

	public String getCallPlanDowngradeInd() {
		return callPlanDowngradeInd;
	}

	public void setCallPlanDowngradeInd(String callPlanDowngradeInd) {
		this.callPlanDowngradeInd = callPlanDowngradeInd;
	}

	public String getPaperBillInd() {
		return paperBillInd;
	}

	public void setPaperBillInd(String paperBillInd) {
		this.paperBillInd = paperBillInd;
	}

	public String getCancelVasInd() {
		return cancelVasInd;
	}

	public void setCancelVasInd(String cancelVasInd) {
		this.cancelVasInd = cancelVasInd;
	}

	public String getFrozenExchInd() {
		return frozenExchInd;
	}

	public void setFrozenExchInd(String frozenExchInd) {
		this.frozenExchInd = frozenExchInd;
	}

	public String getDnExchangeId() {
		return this.dnExchangeId;
	}

	public void setDnExchangeId(String pDnExchangeId) {
		this.dnExchangeId = pDnExchangeId;
	}

	public boolean isTos() {
		return this.isTos;
	}

	public void setTos(boolean pIsTos) {
		this.isTos = pIsTos;
	}

	public String getReservedDnInd() {
		return this.reservedDnInd;
	}

	public void setReservedDnInd(String pReservedDnInd) {
		this.reservedDnInd = pReservedDnInd;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSharedBsnInd() {
		return sharedBsnInd;
	}

	public void setSharedBsnInd(String sharedBsnInd) {
		this.sharedBsnInd = sharedBsnInd;
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
	
	public ItemDetailLtsDTO[] getItemDtls() {
		return itemDtls;
	}

	public void setItemDtls(ItemDetailLtsDTO[] itemDtls) {
		this.itemDtls = itemDtls;
	}

	public String getReturnDeviceInd() {
		return returnDeviceInd;
	}

	public void setReturnDeviceInd(String returnDeviceInd) {
		this.returnDeviceInd = returnDeviceInd;
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

	public String getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(String adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public String getBundle2gNum() {
		return bundle2gNum;
	}

	public void setBundle2gNum(String bundle2gNum) {
		this.bundle2gNum = bundle2gNum;
	}

	public String getDeceaseType() {
		return deceaseType;
	}

	public void setDeceaseType(String deceaseType) {
		this.deceaseType = deceaseType;
	}

	public String getAdjustStartDate() {
		return adjustStartDate;
	}

	public void setAdjustStartDate(String adjustStartDate) {
		this.adjustStartDate = adjustStartDate;
	}

	public String getAdjustEndDate() {
		return adjustEndDate;
	}

	public void setAdjustEndDate(String adjustEndDate) {
		this.adjustEndDate = adjustEndDate;
	}

	public String getAdjustRate() {
		return adjustRate;
	}

	public void setAdjustRate(String adjustRate) {
		this.adjustRate = adjustRate;
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

	public String getUpdateDnProfile() {
		return updateDnProfile;
	}

	public void setUpdateDnProfile(String updateDnProfile) {
		this.updateDnProfile = updateDnProfile;
	}
	
}
