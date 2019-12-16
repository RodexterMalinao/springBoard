package com.bomwebportal.dto;

import java.io.Serializable;

public class LockDTO implements Serializable {

	private static final long serialVersionUID = 1653119690076634544L;

	private String id = null;
	private String type = null;
	private String sessionId = null;
	private String lockBy = null;
	private String lockDate = null;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getLockBy() {
		return lockBy;
	}

	public void setLockBy(String lockBy) {
		this.lockBy = lockBy;
	}

	public String getLockDate() {
		return lockDate;
	}

	public void setLockDate(String lockDate) {
		this.lockDate = lockDate;
	}
}
