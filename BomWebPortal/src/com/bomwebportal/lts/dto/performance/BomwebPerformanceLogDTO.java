package com.bomwebportal.lts.dto.performance;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class BomwebPerformanceLogDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8149024998690806959L;

	private long logId;
	
	private String lob;
	
	private String staffId;
	
	private String sessionId;
	
	private String orderId;
	
	private String className;
	
	private String methodName;
	
	private Timestamp startTime;
	
	private Timestamp endTime;
	
	private long durationMs;
	
	private Date lastUpdDate;
	
	private String lastUpdBy;
	
	private Date createDate;
	
	private String createBy;
	
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss.SSS");

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public String getStartTimeAsString() {
		return dateFormatter.format(this.startTime);
	}	
	
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}
	
	public String getEndTimeAsString() {
		return dateFormatter.format(this.endTime);
	}	

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public long getDurationMs() {
		return durationMs;
	}

	public void setDurationMs(long durationMs) {
		this.durationMs = durationMs;
	}

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
