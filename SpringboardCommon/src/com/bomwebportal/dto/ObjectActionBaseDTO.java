package com.bomwebportal.dto;

import java.io.Serializable;

public abstract class ObjectActionBaseDTO implements Cloneable, Serializable {

	private static final long serialVersionUID = -19952076031125663L;
	
	public static int ACTION_NO_CHANGE = 0;
	public static int ACTION_ADD = 1;
	public static int ACTION_UPDATED = 2;
	public static int ACTION_DELETE = 3;
	
	private int objectAction = ACTION_ADD;

	private String lastUpdBy = null;
	private String createBy = null;
	private String createDate = null;
	
	
	public int getObjectAction() {
		return objectAction;
	}

	public void setObjectAction(int objectAction) {
		this.objectAction = objectAction;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
