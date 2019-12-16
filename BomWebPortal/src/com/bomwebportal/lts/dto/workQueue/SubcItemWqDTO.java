package com.bomwebportal.lts.dto.workQueue;

import java.io.Serializable;

public class SubcItemWqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6512256634925580990L;
	
	private String itemId;
	private String ioInd;
	private String orderType;
	private String actionType;
	private String wqNatureId;
	private String wqSubType;
	private String wqType;
	private String wqRemarks;
	private String desc;
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getIoInd() {
		return ioInd;
	}
	public void setIoInd(String ioInd) {
		this.ioInd = ioInd;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getWqNatureId() {
		return wqNatureId;
	}
	public void setWqNatureId(String wqNatureId) {
		this.wqNatureId = wqNatureId;
	}
	public String getWqSubType() {
		return wqSubType;
	}
	public void setWqSubType(String wqSubType) {
		this.wqSubType = wqSubType;
	}
	public String getWqType() {
		return wqType;
	}
	public void setWqType(String wqType) {
		this.wqType = wqType;
	}
	public String getWqRemarks() {
		return wqRemarks;
	}
	public void setWqRemarks(String wqRemarks) {
		this.wqRemarks = wqRemarks;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	
}
