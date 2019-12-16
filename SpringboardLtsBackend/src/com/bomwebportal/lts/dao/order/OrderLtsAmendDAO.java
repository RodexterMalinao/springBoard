package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class OrderLtsAmendDAO extends DaoBaseImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8387156590525962272L;
	
	private String orderId; // BOMWEB_ORDER_LTS_AMEND.ORDER_ID
	private OraNumber dtlId; // BOMWEB_ORDER_LTS_AMEND.DTL_ID
	private OraNumber batchSeq; // BOMWEB_ORDER_LTS_AMEND.BATCH_SEQ
	private String staffNum; // BOMWEB_ORDER_LTS_AMEND.STAFF_NUM
	private String salesCd; // BOMWEB_ORDER_LTS_AMEND.SALES_CD
	private String salesChannel; // BOMWEB_ORDER_LTS_AMEND.SALES_CHANNEL
	private String shopCd; // BOMWEB_ORDER_LTS_AMEND.SHOP_CD
	private String salesContactNum; // BOMWEB_ORDER_LTS_AMEND.SALES_CONTACT_NUM
	private String appName; // BOMWEB_ORDER_LTS_AMEND.APP_NAME
	private String appContactNum; // BOMWEB_ORDER_LTS_AMEND.APP_CONTACT_NUM
	private String appRelationCd; // BOMWEB_ORDER_LTS_AMEND.APP_RELATION_CD
	private String requireApproval; // BOMWEB_ORDER_LTS_AMEND.REQUIRE_APPROVAL
	private String approvalStatus; // BOMWEB_ORDER_LTS_AMEND.APPROVAL_STATUS
	private String createBy; // BOMWEB_ORDER_LTS_AMEND.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ORDER_LTS_AMEND.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ORDER_LTS_AMEND.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_LTS_AMEND.LAST_UPD_DATE
	private String emailAddr; // BOMWEB_ORDER_LTS_AMEND.EMAIL_ADDR
	private String smsNo; // BOMWEB_ORDER_LTS_AMEND.SMS_NO
	
	public OrderLtsAmendDAO() {
		primaryKeyFields = new String[] {"orderId", "dtlId"};
	}

	public String getTableName() {
		return "BOMWEB_ORDER_LTS_AMEND";
	}
	
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}


	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}
	
	/**
	 * @return the batchSeq
	 */
	public String getBatchSeq() {
		return this.batchSeq != null ? this.batchSeq.toString() : null;
	}
	/**
	 * @param batchSeq the batchSeq to set
	 */
	public void setBatchSeq(String batchSeq) {
		this.batchSeq = new OraNumber(batchSeq);
	}
	/**
	 * @return the staffNum
	 */
	public String getStaffNum() {
		return staffNum;
	}
	/**
	 * @param staffNum the staffNum to set
	 */
	public void setStaffNum(String staffNum) {
		this.staffNum = staffNum;
	}
	/**
	 * @return the salesCd
	 */
	public String getSalesCd() {
		return salesCd;
	}
	/**
	 * @param salesCd the salesCd to set
	 */
	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}
	/**
	 * @return the salesChannel
	 */
	public String getSalesChannel() {
		return salesChannel;
	}
	/**
	 * @param salesChannel the salesChannel to set
	 */
	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}
	/**
	 * @return the shopCd
	 */
	public String getShopCd() {
		return shopCd;
	}
	/**
	 * @param shopCd the shopCd to set
	 */
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	/**
	 * @return the salesContactNum
	 */
	public String getSalesContactNum() {
		return salesContactNum;
	}
	/**
	 * @param salesContactNum the salesContactNum to set
	 */
	public void setSalesContactNum(String salesContactNum) {
		this.salesContactNum = salesContactNum;
	}
	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * @param appName the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * @return the appContactNum
	 */
	public String getAppContactNum() {
		return appContactNum;
	}
	/**
	 * @param appContactNum the appContactNum to set
	 */
	public void setAppContactNum(String appContactNum) {
		this.appContactNum = appContactNum;
	}
	/**
	 * @return the appRelationCd
	 */
	public String getAppRelationCd() {
		return appRelationCd;
	}
	/**
	 * @param appRelationCd the appRelationCd to set
	 */
	public void setAppRelationCd(String appRelationCd) {
		this.appRelationCd = appRelationCd;
	}
	/**
	 * @return the requireApproval
	 */
	public String getRequireApproval() {
		return requireApproval;
	}
	/**
	 * @param requireApproval the requireApproval to set
	 */
	public void setRequireApproval(String requireApproval) {
		this.requireApproval = requireApproval;
	}
	/**
	 * @return the approvalStatus
	 */
	public String getApprovalStatus() {
		return approvalStatus;
	}
	/**
	 * @param approvalStatus the approvalStatus to set
	 */
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
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


	public String getLastUpdBy() {
		return lastUpdBy;
	}


	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getSmsNo() {
		return smsNo;
	}

	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}
	
}
