package com.pccw.wq.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MI;
import com.pccw.util.db.stringOracleType.OraNumber;
import com.pccw.util.db.stringOracleType.OraNumberInsertValueFromSelect;

public class QWorkQueueDAOImpl extends DaoBaseImpl implements QWorkQueueDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1038980861630480712L;

	private OraNumberInsertValueFromSelect wqId = null; // Q_WORK_QUEUE.WQ_ID
	private String sbId; // Q_WORK_QUEUE.SB_ID
	private OraNumber sbDtlId; // Q_WORK_QUEUE.SB_DTL_ID
	private String sbShopCd; // Q_WORK_QUEUE.SB_SHOP_CD
	private String typeOfSrv; // Q_WORK_QUEUE.TYPE_OF_SRV
	private String srvId; // Q_WORK_QUEUE.SRV_ID
	private OraDate srd = new OraDateYYYYMMDDHH24MI(); // Q_WORK_QUEUE.SRD
	private OraNumber bomOcId; // Q_WORK_QUEUE.BOM_OC_ID
	private OraNumber bomDtlId; // Q_WORK_QUEUE.BOM_DTL_ID
	private OraNumber bomDtlSeq; // Q_WORK_QUEUE.BOM_DTL_SEQ
	private String bomStatus; // Q_WORK_QUEUE.BOM_STATUS
	private String bomLegacyOrdStatus; // Q_WORK_QUEUE.BOM_LEGACY_ORD_STATUS
	private String createBy; // Q_WORK_QUEUE.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // Q_WORK_QUEUE.CREATE_DATE
	private String lastUpdBy; // Q_WORK_QUEUE.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // Q_WORK_QUEUE.LAST_UPD_DATE
	private String relatedSrvType; // Q_WORK_QUEUE.RELATED_SRV_TYPE
	private String relatedSrvNum; // Q_WORK_QUEUE.RELATED_SRV_NUM
	private String sbActvId; // Q_WORK_QUEUE.SB_ACTV_ID

	public QWorkQueueDAOImpl() {
		primaryKeyFields = new String[] { "wqId" };
	}

	public String getTableName() {
		return "Q_WORK_QUEUE";
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
	public String getCreateBy() {
		return this.createBy;
	}

	@Override
	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	@Override
	public String getWqId() {
		return this.wqId != null ? this.wqId.toString() : null;
	}

	@Override
	public OraNumberInsertValueFromSelect getWqIdORACLE() {
		return this.wqId;
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
	public String getSrd() {
		return this.srd != null ? this.srd.toString() : null;
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
	public OraDate getSrdORACLE() {
		return this.srd;
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
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	@Override
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	@Override
	public void setWqIdORACLE(OraNumberInsertValueFromSelect pWqId) {
		this.wqId = pWqId;
	}
	
	@Override
	public void setWqId(String pWqId) {
		this.wqId.setValue(pWqId);
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
	public void setSrd(String pSrd) {
		this.srd = new OraDateYYYYMMDDHH24MI(pSrd);
	}

	@Override
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	@Override
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	@Override
	public String getRelatedSrvType() {
		return this.relatedSrvType;
	}

	@Override
	public void setRelatedSrvType(String pRelatedSrvType) {
		this.relatedSrvType = pRelatedSrvType;
	}

	@Override
	public String getRelatedSrvNum() {
		return this.relatedSrvNum;
	}

	@Override
	public void setRelatedSrvNum(String pRelatedSrvNum) {
		this.relatedSrvNum = pRelatedSrvNum;
	}

	@Override
	public String getSbActvId() {
		return this.sbActvId;
	}

	@Override
	public void setSbActvId(String pSbActvId) {
		this.sbActvId = pSbActvId;
	}
}