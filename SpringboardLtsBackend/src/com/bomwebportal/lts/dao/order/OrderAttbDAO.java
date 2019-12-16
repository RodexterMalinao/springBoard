package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class OrderAttbDAO extends DaoBaseImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5507326603595796218L;
	private String orderId; // BOMWEB_ORDER_SERVICE_ATTB.ORDER_ID
	private OraNumber dtlId; // BOMWEB_ORDER_SERVICE_ATTB.DTL_ID
	private OraNumber attbId; // BOMWEB_ORDER_SERVICE_ATTB.ATTB_ID
	private String attbValue; // BOMWEB_ORDER_SERVICE_ATTB.ATTB_VALUE
	private String attbName; // BOMWEB_ORDER_SERVICE_ATTB.ATTB_NAME
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ORDER_SERVICE_ATTB.CREATE_DATE
	private String createBy; // BOMWEB_ORDER_SERVICE_ATTB.CREATE_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_SERVICE_ATTB.LAST_UPD_DATE
	private String lastUpdBy; // BOMWEB_ORDER_SERVICE_ATTB.LAST_UPD_BY

	public OrderAttbDAO() {
		primaryKeyFields = new String[] { "orderId", "dtlId", "attbId"};
	}

	public String getTableName() {
		return "BOMWEB_ORDER_SERVICE_ATTB";
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}
	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}
	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}
	public String getAttbId() {
		return this.attbId != null ? this.attbId.toString() : null;
	}
	public void setAttbId(String pAttbId) {
		this.attbId = new OraNumber(pAttbId);
	}
	public String getAttbValue() {
		return attbValue;
	}
	public void setAttbValue(String pAttbValue) {
		this.attbValue = pAttbValue;
	}
	public String getAttbName() {
		return attbName;
	}
	public void setAttbName(String pAttbName) {
		this.attbName = pAttbName;
	}
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}
	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}
	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
}
