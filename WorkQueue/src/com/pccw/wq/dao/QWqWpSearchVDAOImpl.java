package com.pccw.wq.dao;

import com.pccw.util.db.DaoBaseReadonlyImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class QWqWpSearchVDAOImpl extends DaoBaseReadonlyImpl implements QWqWpSearchVDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 563766665423730325L;

	private OraNumber wqId; // Q_WQ_WP_SEARCH_V.WQ_ID
	private String sbId; // Q_WQ_WP_SEARCH_V.SB_ID
	private OraNumber sbDtlId; // Q_WQ_WP_SEARCH_V.SB_DTL_ID
	private String sbShopCd; // Q_WQ_WP_SEARCH_V.SB_SHOP_CD
	private String typeOfSrv; // Q_WQ_WP_SEARCH_V.TYPE_OF_SRV
	private String srvId; // Q_WQ_WP_SEARCH_V.SRV_ID
	private OraDate srd = new OraDateYYYYMMDDHH24MISS(); // Q_WQ_WP_SEARCH_V.SRD
	private OraNumber bomOcId; // Q_WQ_WP_SEARCH_V.BOM_OC_ID
	private OraNumber bomDtlId; // Q_WQ_WP_SEARCH_V.BOM_DTL_ID
	private OraNumber bomDtlSeq; // Q_WQ_WP_SEARCH_V.BOM_DTL_SEQ
	private String bomStatus; // Q_WQ_WP_SEARCH_V.BOM_STATUS
	private String bomLegacyOrdStatus; // Q_WQ_WP_SEARCH_V.BOM_LEGACY_ORD_STATUS
	private OraNumber wqWpAssgnId; // Q_WQ_WP_SEARCH_V.WQ_WP_ASSGN_ID
	private String wqSerial; // Q_WQ_WP_SEARCH_V.WQ_SERIAL
	private OraDate receiveDate = new OraDateYYYYMMDDHH24MISS(); // Q_WQ_WP_SEARCH_V.RECEIVE_DATE
	private String wqType; // Q_WQ_WP_SEARCH_V.WQ_TYPE
	private String wqTypeDesc; // Q_WQ_WP_SEARCH_V.WQ_TYPE_DESC
	private String wqSubtype; // Q_WQ_WP_SEARCH_V.WQ_SUBTYPE
	private String wqSubTypeDesc; // Q_WQ_WP_SEARCH_V.WQ_SUB_TYPE_DESC
	private OraNumber wpId; // Q_WQ_WP_SEARCH_V.WP_ID
	private String wpDesc; // Q_WQ_WP_SEARCH_V.WP_DESC
	private String assignee; // Q_WQ_WP_SEARCH_V.ASSIGNEE
	private String reasonCd; // Q_WQ_WP_SEARCH_V.REASON_CD
	private String statusCd; // Q_WQ_WP_SEARCH_V.STATUS_CD
	private String statusDesc; // Q_WQ_WP_SEARCH_V.STATUS_DESC

	public QWqWpSearchVDAOImpl() {
		primaryKeyFields = new String[] {"wqWpAssgnId"};
		this.addExcludeColumn("oracleRowID");
	}

	public String getTableName() {
		return "Q_WQ_WP_SEARCH_V";
	}

	@Override
	public String getSbId() {
		return this.sbId;
	}

	@Override
	public String getSbShopCd() {
		return this.sbShopCd;
	}

	@Override
	public String getTypeOfSrv() {
		return this.typeOfSrv;
	}

	@Override
	public String getSrvId() {
		return this.srvId;
	}

	@Override
	public String getBomStatus() {
		return this.bomStatus;
	}

	@Override
	public String getBomLegacyOrdStatus() {
		return this.bomLegacyOrdStatus;
	}

	@Override
	public String getWqSerial() {
		return this.wqSerial;
	}

	@Override
	public String getWqType() {
		return this.wqType;
	}

	@Override
	public String getWqTypeDesc() {
		return this.wqTypeDesc;
	}

	@Override
	public String getWqSubtype() {
		return this.wqSubtype;
	}

	@Override
	public String getWqSubTypeDesc() {
		return this.wqSubTypeDesc;
	}

	@Override
	public String getAssignee() {
		return this.assignee;
	}

	@Override
	public String getReasonCd() {
		return this.reasonCd;
	}

	@Override
	public String getStatusCd() {
		return this.statusCd;
	}

	@Override
	public String getStatusDesc() {
		return this.statusDesc;
	}

	@Override
	public String getWqId() {
		return this.wqId != null ? this.wqId.toString() : null;
	}

	@Override
	public String getSbDtlId() {
		return this.sbDtlId != null ? this.sbDtlId.toString() : null;
	}

	@Override
	public String getBomOcId() {
		return this.bomOcId != null ? this.bomOcId.toString() : null;
	}

	@Override
	public String getBomDtlId() {
		return this.bomDtlId != null ? this.bomDtlId.toString() : null;
	}
	
	@Override
	public String getBomDtlSeq() {
		return this.bomDtlSeq != null ? this.bomDtlSeq.toString() : null;
	}

	@Override
	public String getWqWpAssgnId() {
		return this.wqWpAssgnId != null ? this.wqWpAssgnId.toString() : null;
	}

	@Override
	public String getWpId() {
		return this.wpId != null ? this.wpId.toString() : null;
	}

	@Override
	public String getSrd() {
		return this.srd != null ? this.srd.toString() : null;
	}

	@Override
	public String getReceiveDate() {
		return this.receiveDate != null ? this.receiveDate.toString() : null;
	}

	@Override
	public OraDate getSrdORACLE() {
		return this.srd;
	}

	@Override
	public OraDate getReceiveDateORACLE() {
		return this.receiveDate;
	}

	@Override
	public void setSbId(String pSbId) {
		this.sbId = pSbId;
	}

	@Override
	public void setSbShopCd(String pSbShopCd) {
		this.sbShopCd = pSbShopCd;
	}

	@Override
	public void setTypeOfSrv(String pTypeOfSrv) {
		this.typeOfSrv = pTypeOfSrv;
	}

	@Override
	public void setSrvId(String pSrvId) {
		this.srvId = pSrvId;
	}

	@Override
	public void setBomStatus(String pBomStatus) {
		this.bomStatus = pBomStatus;
	}

	@Override
	public void setBomLegacyOrdStatus(String pBomLegacyOrdStatus) {
		this.bomLegacyOrdStatus = pBomLegacyOrdStatus;
	}

	@Override
	public void setWqSerial(String pWqSerial) {
		this.wqSerial = pWqSerial;
	}

	@Override
	public void setWqType(String pWqType) {
		this.wqType = pWqType;
	}

	@Override
	public void setWqTypeDesc(String pWqTypeDesc) {
		this.wqTypeDesc = pWqTypeDesc;
	}

	@Override
	public void setWqSubtype(String pWqSubtype) {
		this.wqSubtype = pWqSubtype;
	}

	@Override
	public void setWqSubTypeDesc(String pWqSubTypeDesc) {
		this.wqSubTypeDesc = pWqSubTypeDesc;
	}

	@Override
	public void setAssignee(String pAssignee) {
		this.assignee = pAssignee;
	}

	@Override
	public void setReasonCd(String pReasonCd) {
		this.reasonCd = pReasonCd;
	}

	@Override
	public void setStatusCd(String pStatusCd) {
		this.statusCd = pStatusCd;
	}

	@Override
	public void setStatusDesc(String pStatusDesc) {
		this.statusDesc = pStatusDesc;
	}

	@Override
	public void setWqId(String pWqId) {
		this.wqId = new OraNumber(pWqId);
	}

	@Override
	public void setSbDtlId(String pSbDtlId) {
		this.sbDtlId = new OraNumber(pSbDtlId);
	}

	@Override
	public void setBomOcId(String pBomOcId) {
		this.bomOcId = new OraNumber(pBomOcId);
	}

	@Override
	public void setBomDtlId(String pBomDtlId) {
		this.bomDtlId = new OraNumber(pBomDtlId);
	}

	@Override
	public void setBomDtlSeq(String pBomDtlSeq) {
		this.bomDtlSeq = new OraNumber(pBomDtlSeq);
	}

	@Override
	public void setWqWpAssgnId(String pWqWpAssgnId) {
		this.wqWpAssgnId = new OraNumber(pWqWpAssgnId);
	}

	@Override
	public void setWpId(String pWpId) {
		this.wpId = new OraNumber(pWpId);
	}

	@Override
	public void setSrd(String pSrd) {
		this.srd = new OraDateYYYYMMDDHH24MISS(pSrd);
	}

	@Override
	public void setReceiveDate(String pReceiveDate) {
		this.receiveDate = new OraDateYYYYMMDDHH24MISS(pReceiveDate);
	}

	public String getWpDesc() {
		return this.wpDesc;
	}

	public void setWpDesc(String pWpDesc) {
		this.wpDesc = pWpDesc;
	}
}