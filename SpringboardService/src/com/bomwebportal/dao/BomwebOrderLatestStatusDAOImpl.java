package com.bomwebportal.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class BomwebOrderLatestStatusDAOImpl extends DaoBaseImpl implements BomwebOrderLatestStatusDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5787980751235102833L;

	private String orderId; // BOMWEB_ORDER_LATEST_STATUS.ORDER_ID
	private OraNumber dtlId; // BOMWEB_ORDER_LATEST_STATUS.DTL_ID
	private OraDate lastHistDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER_LATEST_STATUS.LAST_HIST_DATE
	private String sbStatus; // BOMWEB_ORDER_LATEST_STATUS.SB_STATUS
	private String bomStatus; // BOMWEB_ORDER_LATEST_STATUS.BOM_STATUS
	private String reaCd; // BOMWEB_ORDER_LATEST_STATUS.REA_CD
	private OraDate bomIssueDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER_LATEST_STATUS.BOM_ISSUE_DATE
	private String lastUpdBy; // BOMWEB_ORDER_LATEST_STATUS.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_LATEST_STATUS.LAST_UPD_DATE
	private String legacyStatus; // BOMWEB_ORDER_LATEST_STATUS.LEGACY_STATUS
	private String wqType; // BOMWEB_ORDER_LATEST_STATUS.WQ_TYPE

	public BomwebOrderLatestStatusDAOImpl() {
		primaryKeyFields = new String[] {};
	}

	@Override
	public String getTableName() {
		return "BOMWEB_ORDER_LATEST_STATUS";
	}

	@Override
	public String getOrderId() {
		return this.orderId;
	}

	@Override
	public String getSbStatus() {
		return this.sbStatus;
	}

	@Override
	public String getBomStatus() {
		return this.bomStatus;
	}

	@Override
	public String getReaCd() {
		return this.reaCd;
	}

	@Override
	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	@Override
	public String getLegacyStatus() {
		return this.legacyStatus;
	}

	@Override
	public String getWqType() {
		return this.wqType;
	}

	@Override
	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}

	@Override
	public String getLastHistDate() {
		return this.lastHistDate != null ? this.lastHistDate.toString() : null;
	}

	@Override
	public String getBomIssueDate() {
		return this.bomIssueDate != null ? this.bomIssueDate.toString() : null;
	}

	@Override
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	@Override
	public OraDate getLastHistDateORACLE() {
		return this.lastHistDate;
	}

	@Override
	public OraDate getBomIssueDateORACLE() {
		return this.bomIssueDate;
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
	public void setSbStatus(String pSbStatus) {
		this.sbStatus = pSbStatus;
	}

	@Override
	public void setBomStatus(String pBomStatus) {
		this.bomStatus = pBomStatus;
	}

	@Override
	public void setReaCd(String pReaCd) {
		this.reaCd = pReaCd;
	}

	@Override
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	@Override
	public void setLegacyStatus(String pLegacyStatus) {
		this.legacyStatus = pLegacyStatus;
	}

	@Override
	public void setWqType(String pWqType) {
		this.wqType = pWqType;
	}

	@Override
	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}

	@Override
	public void setLastHistDate(String pLastHistDate) {
		this.lastHistDate = new OraDateYYYYMMDDHH24MISS(pLastHistDate);
	}

	@Override
	public void setBomIssueDate(String pBomIssueDate) {
		this.bomIssueDate = new OraDateYYYYMMDDHH24MISS(pBomIssueDate);
	}

	@Override
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
