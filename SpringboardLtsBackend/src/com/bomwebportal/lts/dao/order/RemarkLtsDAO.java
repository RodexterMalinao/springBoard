package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class RemarkLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -7592633062271135802L;
	private String orderId; // BOMWEB_REMARK.ORDER_ID
	private OraNumber dtlId; // BOMWEB_REMARK.DTL_ID
	private String rmkType; // BOMWEB_REMARK.RMK_TYPE
	private OraNumber rmkSeq; // BOMWEB_REMARK.RMK_SEQ
	private String rmkDtl; // BOMWEB_REMARK.RMK_DTL
	private String createBy; // BOMWEB_REMARK.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_REMARK.CREATE_DATE
	private String lastUpdBy; // BOMWEB_REMARK.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_REMARK.LAST_UPD_DATE

	public RemarkLtsDAO() {
		primaryKeyFields = new String[] { "orderId", "dtlId", "rmkSeq", "rmkType" };
	}

	public String getTableName() {
		return "BOMWEB_REMARK";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getRmkType() {
		return this.rmkType;
	}

	public String getRmkDtl() {
		return this.rmkDtl;
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

	public String getRmkSeq() {
		return this.rmkSeq != null ? this.rmkSeq.toString() : null;
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

	public void setRmkType(String pRmkType) {
		this.rmkType = pRmkType;
	}

	public void setRmkDtl(String pRmkDtl) {
		this.rmkDtl = pRmkDtl;
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

	public void setRmkSeq(String pRmkSeq) {
		this.rmkSeq = new OraNumber(pRmkSeq);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
