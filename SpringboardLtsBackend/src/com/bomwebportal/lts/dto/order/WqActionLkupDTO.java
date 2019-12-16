/*
 * Created on Nov 12, 2013
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.dto.order;

import java.io.Serializable;

public class WqActionLkupDTO implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6565810028377896200L;

	private long   wqKey;

	private String wqType;

	private String wqSubtype;

	private long   wqNatureId;
	
	/**
	 * @return the wqKey
	 */
	public long getWqKey() {
		return wqKey;
	}
	/**
	 * @param wqKey the wqKey to set
	 */
	public void setWqKey(long wqKey) {
		this.wqKey = wqKey;
	}
	/**
	 * @return the wqType
	 */
	public String getWqType() {
		return wqType;
	}
	/**
	 * @param wqType the wqType to set
	 */
	public void setWqType(String wqType) {
		this.wqType = wqType;
	}
	/**
	 * @return the wqSubtype
	 */
	public String getWqSubtype() {
		return wqSubtype;
	}
	/**
	 * @param wqSubtype the wqSubtype to set
	 */
	public void setWqSubtype(String wqSubtype) {
		this.wqSubtype = wqSubtype;
	}
	/**
	 * @return the wqNatureId
	 */
	public long getWqNatureId() {
		return wqNatureId;
	}
	/**
	 * @param wqNatureId the wqNatureId to set
	 */
	public void setWqNatureId(long wqNatureId) {
		this.wqNatureId = wqNatureId;
	}

	
}
