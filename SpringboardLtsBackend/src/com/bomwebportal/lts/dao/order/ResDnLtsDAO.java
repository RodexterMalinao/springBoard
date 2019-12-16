package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ResDnLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 4119304285665180498L;
	
	private String orderId; // BOMWEB_ORDER_RES_DN_LTS.ORDER_ID
	private String srvNum; // BOMWEB_ORDER_RES_DN_LTS.SRV_NUM
	private String sessionId; // BOMWEB_ORDER_RES_DN_LTS.SESSION_ID
	private String dnSource; // BOMWEB_ORDER_RES_DN_LTS.DN_SOURCE
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ORDER_RES_DN_LTS.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ORDER_RES_DN_LTS.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_RES_DN_LTS.LAST_UPD_DATE


	
/*	public ResDnLtsDAO() {
		primaryKeyFields = new String[] { "orderId" };
	}*/

	public String getTableName() {
		return "BOMWEB_ORDER_RES_DN_LTS";
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getSrvNum() {
		return srvNum;
	}


	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}


	public String getSessionId() {
		return sessionId;
	}


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	public String getDnSource() {
		return dnSource;
	}


	public void setDnSource(String dnSource) {
		this.dnSource = dnSource;
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


	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
	
	


}
