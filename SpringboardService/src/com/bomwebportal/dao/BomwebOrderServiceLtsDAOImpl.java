package com.bomwebportal.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class BomwebOrderServiceLtsDAOImpl extends DaoBaseImpl implements BomwebOrderServiceLtsDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6513415047179755824L;

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

	public BomwebOrderServiceLtsDAOImpl() {
		primaryKeyFields = new String[] {"orderId", "dtlId"};
	}

	@Override
	public String getTableName() {
		return "BOMWEB_ORDER_SERVICE_LTS";
	}

	@Override
	public String getOrderId() {
		return this.orderId;
	}

	@Override
	public String getDatCd() {
		return this.datCd;
	}

	@Override
	public String getTwoNInd() {
		return this.twoNInd;
	}

	@Override
	public String getDuplexInd() {
		return this.duplexInd;
	}

	@Override
	public String getRedeemPremiumInd() {
		return this.redeemPremiumInd;
	}

	@Override
	public String getPendingApprovalCd() {
		return this.pendingApprovalCd;
	}

	@Override
	public String getPaperBillInd() {
		return this.paperBillInd;
	}

	@Override
	public String getCallPlanDowngradeInd() {
		return this.callPlanDowngradeInd;
	}

	@Override
	public String getCreateBy() {
		return this.createBy;
	}

	@Override
	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	@Override
	public String getCancelVasInd() {
		return this.cancelVasInd;
	}

	@Override
	public String getFrozenExchInd() {
		return this.frozenExchInd;
	}

	@Override
	public String getReservedDnInd() {
		return this.reservedDnInd;
	}

	@Override
	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}

	@Override
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	@Override
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	@Override
	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	@Override
	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	@Override
	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	@Override
	public void setDatCd(String pDatCd) {
		this.datCd = pDatCd;
	}

	@Override
	public void setTwoNInd(String pTwoNInd) {
		this.twoNInd = pTwoNInd;
	}

	@Override
	public void setDuplexInd(String pDuplexInd) {
		this.duplexInd = pDuplexInd;
	}

	@Override
	public void setRedeemPremiumInd(String pRedeemPremiumInd) {
		this.redeemPremiumInd = pRedeemPremiumInd;
	}

	@Override
	public void setPendingApprovalCd(String pPendingApprovalCd) {
		this.pendingApprovalCd = pPendingApprovalCd;
	}

	@Override
	public void setPaperBillInd(String pPaperBillInd) {
		this.paperBillInd = pPaperBillInd;
	}

	@Override
	public void setCallPlanDowngradeInd(String pCallPlanDowngradeInd) {
		this.callPlanDowngradeInd = pCallPlanDowngradeInd;
	}

	@Override
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	@Override
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	@Override
	public void setCancelVasInd(String pCancelVasInd) {
		this.cancelVasInd = pCancelVasInd;
	}

	@Override
	public void setFrozenExchInd(String pFrozenExchInd) {
		this.frozenExchInd = pFrozenExchInd;
	}

	@Override
	public void setReservedDnInd(String pReservedDnInd) {
		this.reservedDnInd = pReservedDnInd;
	}

	@Override
	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}

	@Override
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	@Override
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
