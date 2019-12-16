package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;

public class AllOrdDocAssgnLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 7816951976989801076L;
	
	private String orderId; // BOMWEB_ALL_ORD_DOC_ASSGN.ORDER_ID
	private String docType; // BOMWEB_ALL_ORD_DOC_ASSGN.DOC_TYPE
	private String waiveReason; // BOMWEB_ALL_ORD_DOC_ASSGN.WAIVE_REASON
	private String waivedBy; // BOMWEB_ALL_ORD_DOC_ASSGN.WAIVED_BY
	private String collectedInd; // BOMWEB_ALL_ORD_DOC_ASSGN.COLLECTED_IND
	private String markDelInd; // BOMWEB_ALL_ORD_DOC_ASSGN.MARK_DEL_IND
	private String createBy; // BOMWEB_ALL_ORD_DOC_ASSGN.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ALL_ORD_DOC_ASSGN.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ALL_ORD_DOC_ASSGN.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ALL_ORD_DOC_ASSGN.LAST_UPD_DATE

	
	public AllOrdDocAssgnLtsDAO() {
		primaryKeyFields = new String[] {"orderId", "docType"};
	}

	public String getTableName() {
		return "BOMWEB_ALL_ORD_DOC_ASSGN";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getDocType() {
		return this.docType;
	}

	public String getWaiveReason() {
		return this.waiveReason;
	}

	public String getWaivedBy() {
		return this.waivedBy;
	}

	public String getCollectedInd() {
		return this.collectedInd;
	}

	public String getMarkDelInd() {
		return this.markDelInd;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
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

	public void setDocType(String pDocType) {
		this.docType = pDocType;
	}

	public void setWaiveReason(String pWaiveReason) {
		this.waiveReason = pWaiveReason;
	}

	public void setWaivedBy(String pWaivedBy) {
		this.waivedBy = pWaivedBy;
	}

	public void setCollectedInd(String pCollectedInd) {
		this.collectedInd = pCollectedInd;
	}

	public void setMarkDelInd(String pMarkDelInd) {
		this.markDelInd = pMarkDelInd;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
