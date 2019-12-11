package com.pccw.wq.schema.dto;

import java.io.Serializable;

public class WorkingPartyDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5320182325540862025L;

	private String wpId;
	
	private String description;
	
	private String readonlyLevel;
	
	private String editableLevel;
	
	private String alertLevel;
	
	private String role;
	
	private String roleId;

	public String getWpId() {
		return this.wpId;
	}

	public void setWpId(String pWpId) {
		this.wpId = pWpId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String pDescription) {
		this.description = pDescription;
	}

	public String getReadonlyLevel() {
		return this.readonlyLevel;
	}

	public void setReadonlyLevel(String pReadonlyLevel) {
		this.readonlyLevel = pReadonlyLevel;
	}

	public String getEditableLevel() {
		return this.editableLevel;
	}

	public void setEditableLevel(String pEditableLevel) {
		this.editableLevel = pEditableLevel;
	}

	public String getAlertLevel() {
		return this.alertLevel;
	}

	public void setAlertLevel(String pAlertLevel) {
		this.alertLevel = pAlertLevel;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String pRole) {
		this.role = pRole;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String pRoleId) {
		this.roleId = pRoleId;
	}
}
