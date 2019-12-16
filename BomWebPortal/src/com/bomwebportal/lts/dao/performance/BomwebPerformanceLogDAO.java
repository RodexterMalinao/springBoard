package com.bomwebportal.lts.dao.performance;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateTimestamp;
import com.pccw.util.db.stringOracleType.OraNumber;

public class BomwebPerformanceLogDAO  extends DaoBaseImpl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7004698911492657809L;

	private OraNumber logId; // BOMWEB_PERFORMANCE_LOG.LOG_ID
	private String lob; // BOMWEB_PERFORMANCE_LOG.LOB
	private String staffId; // BOMWEB_PERFORMANCE_LOG.STAFF_ID
	private String sessionId; // BOMWEB_PERFORMANCE_LOG.SESSION_ID
	private String orderId; // BOMWEB_PERFORMANCE_LOG.ORDER_ID
	private String className; // BOMWEB_PERFORMANCE_LOG.CLASS_NAME
	private String methodName; // BOMWEB_PERFORMANCE_LOG.METHOD_NAME
	private OraDateTimestamp startTime = new OraDateTimestamp(); // BOMWEB_PERFORMANCE_LOG.START_TIME
	private OraDateTimestamp endTime = new OraDateTimestamp(); // BOMWEB_PERFORMANCE_LOG.END_TIME
	private OraNumber durationMs; // BOMWEB_PERFORMANCE_LOG.DURATION_MS
	private OraDateLastUpdDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_PERFORMANCE_LOG.LAST_UPD_DATE
	private String lastUpdBy; // BOMWEB_PERFORMANCE_LOG.LAST_UPD_BY
	private OraDate createDate = new OraDate(); // BOMWEB_PERFORMANCE_LOG.CREATE_DATE
	private String createBy; // BOMWEB_PERFORMANCE_LOG.CREATE_BY

	public BomwebPerformanceLogDAO() {
		primaryKeyFields = new String[] { "logId" };
	}

	public String getTableName() {
		return "BOMWEB_PERFORMANCE_LOG";
	}

	public String getLob() {
		return this.lob;
	}

	public String getStaffId() {
		return this.staffId;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getClassName() {
		return this.className;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLogId() {
		return this.logId != null ? this.logId.toString() : null;
	}

	public String getDurationMs() {
		return this.durationMs != null ? this.durationMs.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getStartTime() {
		return this.startTime != null ? this.startTime.toString() : null;
	}

	public String getEndTime() {
		return this.endTime != null ? this.endTime.toString() : null;
	}

	public OraDateLastUpdDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDateTimestamp getStartTimeORACLE() {
		return this.startTime;
	}

	public OraDateTimestamp getEndTimeORACLE() {
		return this.endTime;
	}

	public void setLob(String pLob) {
		this.lob = pLob;
	}

	public void setStaffId(String pStaffId) {
		this.staffId = pStaffId;
	}

	public void setSessionId(String pSessionId) {
		this.sessionId = pSessionId;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setClassName(String pClassName) {
		this.className = pClassName;
	}

	public void setMethodName(String pMethodName) {
		this.methodName = pMethodName;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLogId(String pLogId) {
		this.logId = new OraNumber(pLogId);
	}

	public void setDurationMs(String pDurationMs) {
		this.durationMs = new OraNumber(pDurationMs);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDate(pCreateDate);
	}

	public void setStartTime(String pStartTime) {
		this.startTime = new OraDateTimestamp(pStartTime);
	}

	public void setEndTime(String pEndTime) {
		this.endTime = new OraDateTimestamp(pEndTime);
	}
}
