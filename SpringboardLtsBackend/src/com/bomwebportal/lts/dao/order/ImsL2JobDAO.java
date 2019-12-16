package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ImsL2JobDAO extends DaoBaseImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -863900757450128747L;
	private String orderId; // BOMWEB_ORDER_L2_JOB.ORDER_ID
	private OraNumber dtlId; // BOMWEB_ORDER_L2_JOB.DTL_ID
	private OraNumber dtlSeq; // BOMWEB_ORDER_L2_JOB.DTL_SEQ
	private OraNumber productId; // BOMWEB_ORDER_L2_JOB.PRODUCT_ID
	private String l2Cd; // BOMWEB_ORDER_L2_JOB.L2_CD
	private String ftInd; // BOMWEB_ORDER_L2_JOB.FT_IND
	private OraNumber l2Oty; // BOMWEB_ORDER_L2_JOB.L2_OTY
	private String actInd; // BOMWEB_ORDER_L2_JOB.ACT_IND
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_L2_JOB.LAST_UPD_DATE
	private String lastUpdBy; // BOMWEB_ORDER_L2_JOB.LAST_UPD_BY

	public ImsL2JobDAO() {
		primaryKeyFields = new String[] {};
	}

	public String getTableName() {
		return "BOMWEB_ORDER_L2_JOB";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getL2Cd() {
		return this.l2Cd;
	}

	public String getFtInd() {
		return this.ftInd;
	}

	public String getActInd() {
		return this.actInd;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}

	public String getDtlSeq() {
		return this.dtlSeq != null ? this.dtlSeq.toString() : null;
	}

	public String getProductId() {
		return this.productId != null ? this.productId.toString() : null;
	}

	public String getL2Oty() {
		return this.l2Oty != null ? this.l2Oty.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setL2Cd(String pL2Cd) {
		this.l2Cd = pL2Cd;
	}

	public void setFtInd(String pFtInd) {
		this.ftInd = pFtInd;
	}

	public void setActInd(String pActInd) {
		this.actInd = pActInd;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}

	public void setDtlSeq(String pDtlSeq) {
		this.dtlSeq = new OraNumber(pDtlSeq);
	}

	public void setProductId(String pProductId) {
		this.productId = new OraNumber(pProductId);
	}

	public void setL2Oty(String pL2Oty) {
		this.l2Oty = new OraNumber(pL2Oty);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

}
