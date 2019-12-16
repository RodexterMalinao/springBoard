package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;

public class BillingAddressLtsDAO extends DaoBaseImpl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4130156195233330345L;
	
	
	private String orderId; // BOMWEB_BILLING_ADDR.ORDER_ID
	private String acctNo; // BOMWEB_BILLING_ADDR.DTL_ID
	private String action; // BOMWEB_BILLING_ADDR.ACTION
	private String instantUpdateInd; // BOMWEB_BILLING_ADDR.INSTANT_UPDATE_IND
	private String addrLine1; // BOMWEB_BILLING_ADDR.ADDR_LINE1
	private String addrLine2; // BOMWEB_BILLING_ADDR.ADDR_LINE2
	private String addrLine3; // BOMWEB_BILLING_ADDR.ADDR_LINE3
	private String addrLine4; // BOMWEB_BILLING_ADDR.ADDR_LINE4
	private String addrLine5; // BOMWEB_BILLING_ADDR.ADDR_LINE5
	private String addrLine6; // BOMWEB_BILLING_ADDR.ADDR_LINE6
	private String createBy; // BOMWEB_BILLING_ADDR.CREATE_BY
	private OraDate createDate = new OraDate(); // BOMWEB_BILLING_ADDR.CREATE_DATE
	private String lastUpdBy; // BOMWEB_BILLING_ADDR.LAST_UPD_BY
	private OraDateLastUpdDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_BILLING_ADDR.LAST_UPD_DATE

	public BillingAddressLtsDAO() {
		primaryKeyFields = new String[] {"orderId", "dtlId"};
	}

	public String getTableName() {
		return "BOMWEB_BILLING_ADDR";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getAction() {
		return this.action;
	}

	public String getInstantUpdateInd() {
		return this.instantUpdateInd;
	}

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public String getAddrLine3() {
		return addrLine3;
	}

	public void setAddrLine3(String addrLine3) {
		this.addrLine3 = addrLine3;
	}

	public String getAddrLine4() {
		return addrLine4;
	}

	public void setAddrLine4(String addrLine4) {
		this.addrLine4 = addrLine4;
	}

	public String getAddrLine5() {
		return addrLine5;
	}

	public void setAddrLine5(String addrLine5) {
		this.addrLine5 = addrLine5;
	}

	public String getAddrLine6() {
		return addrLine6;
	}

	public void setAddrLine6(String addrLine6) {
		this.addrLine6 = addrLine6;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getAcctNo() {
		return this.acctNo;
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

	public OraDateLastUpdDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setAction(String pAction) {
		this.action = pAction;
	}

	public void setInstantUpdateInd(String pInstantUpdateInd) {
		this.instantUpdateInd = pInstantUpdateInd;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setAcctNo(String pAcctNo) {
		this.acctNo = pAcctNo;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

}
