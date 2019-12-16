/*
 * Created on Nov 12, 2013
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.dto.order;

public class OtherActionLkupDTO extends ServiceActionLkupBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1636180822021155215L;

	private String actionType;

	private String status;
	
	public String getLkupKey() {
		return this.getActionType() + (this.getStatus()==null?"":"^"+this.getStatus());
	}		

	/**
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
