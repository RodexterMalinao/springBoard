package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class AccountServiceAssignLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 7687242619329910211L;
	private String orderId; // BOMWEB_SERVICE_ACCT_ASSGN.ORDER_ID
	private OraNumber dtlId; // BOMWEB_SERVICE_ACCT_ASSGN.DTL_ID
	private String acctNo; // BOMWEB_SERVICE_ACCT_ASSGN.ACCT_NO
	private String action; // BOMWEB_SERVICE_ACCT_ASSGN.ACTION
	private String createBy; // BOMWEB_SERVICE_ACCT_ASSGN.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_SERVICE_ACCT_ASSGN.CREATE_DATE
	private String lastUpdBy; // BOMWEB_SERVICE_ACCT_ASSGN.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_SERVICE_ACCT_ASSGN.LAST_UPD_DATE
	private String chrgType; // BOMWEB_SERVICE_ACCT_ASSGN.CHRG_TYPE

	public AccountServiceAssignLtsDAO() {
		primaryKeyFields = new String[] {"orderId", "dtlId", "acctNo", "action"};
	}

	public String getTableName() {
		return "BOMWEB_SERVICE_ACCT_ASSGN";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getAcctNo() {
		return this.acctNo;
	}

	public String getAction() {
		return this.action;
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

	public void setAcctNo(String pAcctNo) {
		this.acctNo = pAcctNo;
	}

	public void setAction(String pAction) {
		this.action = pAction;
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

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getChrgType() {
		return chrgType;
	}

	public void setChrgType(String chrgType) {
		this.chrgType = chrgType;
	}
	
	

}
