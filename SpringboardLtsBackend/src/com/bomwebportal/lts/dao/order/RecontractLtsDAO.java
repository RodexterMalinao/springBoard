package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class RecontractLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -5434878996886723525L;
	
	private String orderId; // BOMWEB_ORDER_SRV_RECONTRACT.ORDER_ID
	private OraNumber dtlId; // BOMWEB_ORDER_SRV_RECONTRACT.DTL_ID
	private String acctNum; // BOMWEB_ORDER_SRV_RECONTRACT.ACCT_NUM
	private String custNum; // BOMWEB_ORDER_SRV_RECONTRACT.CUST_NUM
	private String idDocType; // BOMWEB_ORDER_SRV_RECONTRACT.ID_DOC_TYPE
	private String idDocNum; // BOMWEB_ORDER_SRV_RECONTRACT.ID_DOC_NUM
	private String title; // BOMWEB_ORDER_SRV_RECONTRACT.TITLE
	private String custFirstName; // BOMWEB_ORDER_SRV_RECONTRACT.CUST_FIRST_NAME
	private String custLastName; // BOMWEB_ORDER_SRV_RECONTRACT.CUST_LAST_NAME
	private String companyName; // BOMWEB_ORDER_SRV_RECONTRACT.COMPANY_NAME
	private String relationship; // BOMWEB_ORDER_SRV_RECONTRACT.RELATIONSHIP
	private String contactNum; // BOMWEB_ORDER_SRV_RECONTRACT.CONTACT_NUM
	private String emailAddr; // BOMWEB_ORDER_SRV_RECONTRACT.EMAIL_ADDR
	private String blacklistInd; // BOMWEB_ORDER_SRV_RECONTRACT.BLACKLIST_IND
	private String callingCardInd;	// BOMWEB_ORDER_SRV_RECONTRACT.CALLING_CARD_IND
	private String mobIddInd; // BOMWEB_ORDER_SRV_RECONTRACT.MOB_IDD_IND
	private String fixedIddInd; // BOMWEB_ORDER_SRV_RECONTRACT.FIXED_IDD_IND
	private String transMode; // BOMWEB_ORDER_SRV_RECONTRACT.TRANS_MODE
	private String createBy; // BOMWEB_ORDER_SRV_RECONTRACT.CREATE_BY
	private OraDate createDate = new OraDate(); // BOMWEB_ORDER_SRV_RECONTRACT.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ORDER_SRV_RECONTRACT.LAST_UPD_BY
	private OraDateLastUpdDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_SRV_RECONTRACT.LAST_UPD_DATE
	private String optOut; // BOMWEB_ORDER_SRV_RECONTRACT.OPT_OUT

	public RecontractLtsDAO() {
		primaryKeyFields = new String[] {};
	}

	public String getTableName() {
		return "BOMWEB_ORDER_SRV_RECONTRACT";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getAcctNum() {
		return this.acctNum;
	}

	public String getCustNum() {
		return this.custNum;
	}

	public String getIdDocType() {
		return this.idDocType;
	}

	public String getIdDocNum() {
		return this.idDocNum;
	}

	public String getTitle() {
		return this.title;
	}

	public String getCustFirstName() {
		return this.custFirstName;
	}

	public String getCustLastName() {
		return this.custLastName;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public String getRelationship() {
		return this.relationship;
	}

	public String getContactNum() {
		return this.contactNum;
	}

	public String getEmailAddr() {
		return this.emailAddr;
	}

	public String getBlacklistInd() {
		return this.blacklistInd;
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

	public OraDateLastUpdDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setAcctNum(String pAcctNum) {
		this.acctNum = pAcctNum;
	}

	public void setCustNum(String pCustNum) {
		this.custNum = pCustNum;
	}

	public void setIdDocType(String pIdDocType) {
		this.idDocType = pIdDocType;
	}

	public void setIdDocNum(String pIdDocNum) {
		this.idDocNum = pIdDocNum;
	}

	public void setTitle(String pTitle) {
		this.title = pTitle;
	}

	public void setCustFirstName(String pCustFirstName) {
		this.custFirstName = pCustFirstName;
	}

	public void setCustLastName(String pCustLastName) {
		this.custLastName = pCustLastName;
	}

	public void setCompanyName(String pCompanyName) {
		this.companyName = pCompanyName;
	}

	public void setRelationship(String pRelationship) {
		this.relationship = pRelationship;
	}

	public void setContactNum(String pContactNum) {
		this.contactNum = pContactNum;
	}

	public void setEmailAddr(String pEmailAddr) {
		this.emailAddr = pEmailAddr;
	}

	public void setBlacklistInd(String pBlacklistInd) {
		this.blacklistInd = pBlacklistInd;
	}

	public String getCallingCardInd() {
		return callingCardInd;
	}

	public void setCallingCardInd(String callingCardInd) {
		this.callingCardInd = callingCardInd;
	}

	public String getMobIddInd() {
		return mobIddInd;
	}

	public void setMobIddInd(String mobIddInd) {
		this.mobIddInd = mobIddInd;
	}

	public String getFixedIddInd() {
		return fixedIddInd;
	}

	public void setFixedIddInd(String fixedIddInd) {
		this.fixedIddInd = fixedIddInd;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
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
		this.createDate = new OraDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getOptOut() {
		return optOut;
	}

	public void setOptOut(String optOut) {
		this.optOut = optOut;
	}
}
