package com.activity.dao.dataAccess;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ActivityCustomerDtlDAO extends DaoBaseImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6279242732789348039L;
	private OraNumber actvId; // SB_ACTV_CUST_DTL.ACTV_ID
	private String custNo; // SB_ACTV_CUST_DTL.CUST_NO
	private String custType; // SB_ACTV_CUST_DTL.CUST_TYPE
	private String createBy; // SB_ACTV_CUST_DTL.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // SB_ACTV_CUST_DTL.CREATE_DATE
	private String lastUpdBy; // SB_ACTV_CUST_DTL.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // SB_ACTV_CUST_DTL.LAST_UPD_DATE

	public ActivityCustomerDtlDAO() {
		primaryKeyFields = new String[] { "actvId", "custNo" };
	}

	public String getTableName() {
		return "SB_ACTV_CUST_DTL";
	}

	public String getCustNo() {
		return this.custNo;
	}

	public String getCustType() {
		return this.custType;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getActvId() {
		return this.actvId != null ? this.actvId.toString() : null;
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

	public void setCustNo(String pCustNo) {
		this.custNo = pCustNo;
	}

	public void setCustType(String pCustType) {
		this.custType = pCustType;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setActvId(String pActvId) {
		this.actvId = new OraNumber(pActvId);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
