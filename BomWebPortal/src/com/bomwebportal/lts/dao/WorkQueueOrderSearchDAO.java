package com.bomwebportal.lts.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class WorkQueueOrderSearchDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 575995497038992139L;

	private OraNumber wqId; // Q_WQ_WP_SEARCH_ID_V.WQ_ID
	private String sbId; // Q_WQ_WP_SEARCH_ID_V.SB_ID
	private OraNumber sbDtlId; // Q_WQ_WP_SEARCH_ID_V.SB_DTL_ID
	private String sbShopCd; // Q_WQ_WP_SEARCH_ID_V.SB_SHOP_CD
	private String typeOfSrv; // Q_WQ_WP_SEARCH_ID_V.TYPE_OF_SRV
	private String srvId; // Q_WQ_WP_SEARCH_ID_V.SRV_ID
	private String relatedSrvType; // Q_WQ_WP_SEARCH_ID_V.RELATED_SRV_TYPE
	private String relatedSrvNum; // Q_WQ_WP_SEARCH_ID_V.RELATED_SRV_NUM
	private OraDate srd = new OraDateYYYYMMDDHH24MISS(); // Q_WQ_WP_SEARCH_ID_V.SRD
	private OraNumber bomOcId; // Q_WQ_WP_SEARCH_ID_V.BOM_OC_ID
	private OraNumber bomDtlId; // Q_WQ_WP_SEARCH_ID_V.BOM_DTL_ID
	private OraNumber bomDtlSeq; // Q_WQ_WP_SEARCH_ID_V.BOM_DTL_SEQ
	private String bomStatus; // Q_WQ_WP_SEARCH_ID_V.BOM_STATUS
	private String bomLegacyOrdStatus; // Q_WQ_WP_SEARCH_ID_V.BOM_LEGACY_ORD_STATUS
	private OraNumber wqWpAssgnId; // Q_WQ_WP_SEARCH_ID_V.WQ_WP_ASSGN_ID
	private OraNumber wqBatchId; // Q_WQ_WP_SEARCH_ID_V.WQ_BATCH_ID
	private String wqSerial; // Q_WQ_WP_SEARCH_ID_V.WQ_SERIAL
	private OraDate receiveDate = new OraDateYYYYMMDDHH24MISS(); // Q_WQ_WP_SEARCH_ID_V.RECEIVE_DATE
	private String wqType; // Q_WQ_WP_SEARCH_ID_V.WQ_TYPE
	private String wqSubtype; // Q_WQ_WP_SEARCH_ID_V.WQ_SUBTYPE
	private OraNumber wpId; // Q_WQ_WP_SEARCH_ID_V.WP_ID
	private OraNumber wqNatureId; // Q_WQ_WP_SEARCH_ID_V.WQ_NATURE_ID
	private String assignee; // Q_WQ_WP_SEARCH_ID_V.ASSIGNEE
	private String reasonCd; // Q_WQ_WP_SEARCH_ID_V.REASON_CD
	private String statusCd; // Q_WQ_WP_SEARCH_ID_V.STATUS_CD

	
	public WorkQueueOrderSearchDAO() {
		primaryKeyFields = new String[] {};
		this.addExcludeColumn("oracleRowID");
	}

	public String getTableName() {
		return "Q_WQ_WP_SEARCH_ID_V";
	}

	public String getSbId() {
		return this.sbId;
	}

	public String getSbShopCd() {
		return this.sbShopCd;
	}

	public String getTypeOfSrv() {
		return this.typeOfSrv;
	}

	public String getSrvId() {
		return this.srvId;
	}

	public String getRelatedSrvType() {
		return this.relatedSrvType;
	}

	public String getRelatedSrvNum() {
		return this.relatedSrvNum;
	}

	public String getBomStatus() {
		return this.bomStatus;
	}

	public String getBomLegacyOrdStatus() {
		return this.bomLegacyOrdStatus;
	}

	public String getWqSerial() {
		return this.wqSerial;
	}

	public String getWqType() {
		return this.wqType;
	}

	public String getWqSubtype() {
		return this.wqSubtype;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public String getReasonCd() {
		return this.reasonCd;
	}

	public String getStatusCd() {
		return this.statusCd;
	}

	public String getWqId() {
		return this.wqId != null ? this.wqId.toString() : null;
	}

	public String getSbDtlId() {
		return this.sbDtlId != null ? this.sbDtlId.toString() : null;
	}

	public String getBomOcId() {
		return this.bomOcId != null ? this.bomOcId.toString() : null;
	}

	public String getBomDtlId() {
		return this.bomDtlId != null ? this.bomDtlId.toString() : null;
	}

	public String getBomDtlSeq() {
		return this.bomDtlSeq != null ? this.bomDtlSeq.toString() : null;
	}

	public String getWqWpAssgnId() {
		return this.wqWpAssgnId != null ? this.wqWpAssgnId.toString() : null;
	}

	public String getWqBatchId() {
		return this.wqBatchId != null ? this.wqBatchId.toString() : null;
	}

	public String getWpId() {
		return this.wpId != null ? this.wpId.toString() : null;
	}

	public String getWqNatureId() {
		return this.wqNatureId != null ? this.wqNatureId.toString() : null;
	}

	public String getSrd() {
		return this.srd != null ? this.srd.toString() : null;
	}

	public String getReceiveDate() {
		return this.receiveDate != null ? this.receiveDate.toString() : null;
	}

	public OraDate getSrdORACLE() {
		return this.srd;
	}

	public OraDate getReceiveDateORACLE() {
		return this.receiveDate;
	}

	public void setSbId(String pSbId) {
		this.sbId = pSbId;
	}

	public void setSbShopCd(String pSbShopCd) {
		this.sbShopCd = pSbShopCd;
	}

	public void setTypeOfSrv(String pTypeOfSrv) {
		this.typeOfSrv = pTypeOfSrv;
	}

	public void setSrvId(String pSrvId) {
		this.srvId = pSrvId;
	}

	public void setRelatedSrvType(String pRelatedSrvType) {
		this.relatedSrvType = pRelatedSrvType;
	}

	public void setRelatedSrvNum(String pRelatedSrvNum) {
		this.relatedSrvNum = pRelatedSrvNum;
	}

	public void setBomStatus(String pBomStatus) {
		this.bomStatus = pBomStatus;
	}

	public void setBomLegacyOrdStatus(String pBomLegacyOrdStatus) {
		this.bomLegacyOrdStatus = pBomLegacyOrdStatus;
	}

	public void setWqSerial(String pWqSerial) {
		this.wqSerial = pWqSerial;
	}

	public void setWqType(String pWqType) {
		this.wqType = pWqType;
	}

	public void setWqSubtype(String pWqSubtype) {
		this.wqSubtype = pWqSubtype;
	}

	public void setAssignee(String pAssignee) {
		this.assignee = pAssignee;
	}

	public void setReasonCd(String pReasonCd) {
		this.reasonCd = pReasonCd;
	}

	public void setStatusCd(String pStatusCd) {
		this.statusCd = pStatusCd;
	}

	public void setWqId(String pWqId) {
		this.wqId = new OraNumber(pWqId);
	}

	public void setSbDtlId(String pSbDtlId) {
		this.sbDtlId = new OraNumber(pSbDtlId);
	}

	public void setBomOcId(String pBomOcId) {
		this.bomOcId = new OraNumber(pBomOcId);
	}

	public void setBomDtlId(String pBomDtlId) {
		this.bomDtlId = new OraNumber(pBomDtlId);
	}

	public void setBomDtlSeq(String pBomDtlSeq) {
		this.bomDtlSeq = new OraNumber(pBomDtlSeq);
	}

	public void setWqWpAssgnId(String pWqWpAssgnId) {
		this.wqWpAssgnId = new OraNumber(pWqWpAssgnId);
	}

	public void setWqBatchId(String pWqBatchId) {
		this.wqBatchId = new OraNumber(pWqBatchId);
	}

	public void setWpId(String pWpId) {
		this.wpId = new OraNumber(pWpId);
	}

	public void setWqNatureId(String pWqNatureId) {
		this.wqNatureId = new OraNumber(pWqNatureId);
	}

	public void setSrd(String pSrd) {
		this.srd = new OraDateYYYYMMDDHH24MISS(pSrd);
	}

	public void setReceiveDate(String pReceiveDate) {
		this.receiveDate = new OraDateYYYYMMDDHH24MISS(pReceiveDate);
	}
}
