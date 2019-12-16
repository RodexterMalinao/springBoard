package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class StockModelDetailsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3221876339810708031L;

	private String type;
	private String itemCode;
	private String model;
	private String assignMode;
	private String assignModeRet;
	private String simType;
	private String remarks;
	
	private String actionType;
	
	private String username; // save as create/upd by
	
	private String hsExtraFunction;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getAssignMode() {
		return assignMode;
	}
	public void setAssignMode(String assignMode) {
		this.assignMode = assignMode;
	}
	public String getAssignModeRet() {
		return assignModeRet;
	}
	public void setAssignModeRet(String assignModeRet) {
		this.assignModeRet = assignModeRet;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionType() {
		return actionType;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setSimType(String simType) {
		this.simType = simType;
	}
	public String getSimType() {
		return simType;
	}
	public String getHsExtraFunction() {
		return hsExtraFunction;
	}
	public void setHsExtraFunction(String hsExtraFunction) {
		this.hsExtraFunction = hsExtraFunction;
	}
}
