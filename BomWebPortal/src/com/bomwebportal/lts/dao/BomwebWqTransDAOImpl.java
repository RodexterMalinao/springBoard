package com.bomwebportal.lts.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class BomwebWqTransDAOImpl extends DaoBaseImpl implements BomwebWqTransDAO  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2622188182962455755L;

	private String orderId; // BOMWEB_WQ_TRANS.ORDER_ID

	private OraNumber dtlId; // BOMWEB_WQ_TRANS.DTL_ID

	private OraNumber wqNatureId; // BOMWEB_WQ_TRANS.WQ_NATURE_ID

	private String wqSubtype; // BOMWEB_WQ_TRANS.WQ_SUBTYPE

	private String wqType; // BOMWEB_WQ_TRANS.WQ_TYPE

	private String wqRemarks; // BOMWEB_WQ_TRANS.WQ_REMARKS

	private String lkupKey; // BOMWEB_WQ_TRANS.LKUP_KEY

	private String lkupCache; // BOMWEB_WQ_TRANS.LKUP_CACHE

	private String status; // BOMWEB_WQ_TRANS.STATUS

	private String action; // BOMWEB_WQ_TRANS.ACTION

	private String shopCd; // BOMWEB_WQ_TRANS.SHOP_CD

	private String userId; // BOMWEB_WQ_TRANS.USER_ID
	
	private String standardRemarks; // BOMWEB_WQ_TRANS.STANDARD_REMARKS
	
	private String createBy; // BOMWEB_WQ_TRANS.CREATE_BY

	private OraDateCreateDate createDate = new OraDateCreateDate(); // BOMWEB_WQ_TRANS.CREATE_DATE

	private String lastUpdBy; // BOMWEB_WQ_TRANS.LAST_UPD_BY

	private OraDateLastUpdDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_WQ_TRANS.LAST_UPD_DATE

	private String hostIp;
	
	private String msgLog;
	
	public BomwebWqTransDAOImpl() {
		primaryKeyFields = new String[] {};
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getTableName()
	 */
	public String getTableName() {
		return "BOMWEB_WQ_TRANS";
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getOrderId()
	 */
	public String getOrderId() {
		return this.orderId;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getWqSubtype()
	 */
	public String getWqSubtype() {
		return this.wqSubtype;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getWqType()
	 */
	public String getWqType() {
		return this.wqType;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getWqRemarks()
	 */
	public String getWqRemarks() {
		return this.wqRemarks;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getLkupKey()
	 */
	public String getLkupKey() {
		return this.lkupKey;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getLkupCache()
	 */
	public String getLkupCache() {
		return this.lkupCache;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getStatus()
	 */
	public String getStatus() {
		return this.status;
	}


	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getCreateBy()
	 */
	public String getCreateBy() {
		return this.createBy;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getLastUpdBy()
	 */
	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getDtlId()
	 */
	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getWqNatureId()
	 */
	public String getWqNatureId() {
		return this.wqNatureId != null ? this.wqNatureId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getCreateDate()
	 */
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getLastUpdDate()
	 */
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getCreateDateORACLE()
	 */
	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#getLastUpdDateORACLE()
	 */
	public OraDateLastUpdDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setOrderId(java.lang.String)
	 */
	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setWqSubtype(java.lang.String)
	 */
	public void setWqSubtype(String pWqSubtype) {
		this.wqSubtype = pWqSubtype;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setWqType(java.lang.String)
	 */
	public void setWqType(String pWqType) {
		this.wqType = pWqType;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setWqRemarks(java.lang.String)
	 */
	public void setWqRemarks(String pWqRemarks) {
		this.wqRemarks = pWqRemarks;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setLkupKey(java.lang.String)
	 */
	public void setLkupKey(String pLkupKey) {
		this.lkupKey = pLkupKey;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setLkupCache(java.lang.String)
	 */
	public void setLkupCache(String pLkupCache) {
		this.lkupCache = pLkupCache;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setStatus(java.lang.String)
	 */
	public void setStatus(String pStatus) {
		this.status = pStatus;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setCreateBy(java.lang.String)
	 */
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setLastUpdBy(java.lang.String)
	 */
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setDtlId(java.lang.String)
	 */
	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setWqNatureId(java.lang.String)
	 */
	public void setWqNatureId(String pWqNatureId) {
		this.wqNatureId = new OraNumber(pWqNatureId);
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setCreateDate(java.lang.String)
	 */
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.dao.BomwebWqTransDAO#setLastUpdDate(java.lang.String)
	 */
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getShopCd() {
		return shopCd;
	}

	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStandardRemarks() {
		return standardRemarks;
	}

	public void setStandardRemarks(String standardRemarks) {
		this.standardRemarks = standardRemarks;
	}

	/**
	 * @return the hostIp
	 */
	public String getHostIp() {
		return hostIp;
	}

	/**
	 * @param hostIp the hostIp to set
	 */
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}

	/**
	 * @return the msgLog
	 */
	public String getMsgLog() {
		return msgLog;
	}

	/**
	 * @param msgLog the msgLog to set
	 */
	public void setMsgLog(String msgLog) {
		this.msgLog = msgLog;
	}
}
