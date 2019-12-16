package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.Date;

public class ItemFuncAssgnMobDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3617944985480345884L;
	
	private String itemId;
	private String funcDesc;
	private String funcId;
	private Date effStartDate;
	private Date effEndDate;
	
	private String extraInfo;
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getFuncDesc() {
		return funcDesc;
	}
	public void setFuncDesc(String funcDesc) {
		this.funcDesc = funcDesc;
	}
	public String getFuncId() {
		return funcId;
	}
	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}
	public Date getEffStartDate() {
		return effStartDate;
	}
	public void setEffStartDate(Date effStartDate) {
		this.effStartDate = effStartDate;
	}
	public Date getEffEndDate() {
		return effEndDate;
	}
	public void setEffEndDate(Date effEndDate) {
		this.effEndDate = effEndDate;
	}
	public String getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

}
