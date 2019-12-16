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

public abstract class ServiceActionLkupBaseDTO implements Serializable,
		Cloneable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2591746210396386168L;

	private WqActionLkupDTO wqActionLkup;

	private String suspendBomReasonCd;

	public abstract String getLkupKey();

	/**
	 * @return the wqActionLkup
	 */
	public WqActionLkupDTO getWqActionLkup() {
		return wqActionLkup;
	}

	/**
	 * @param wqActionLkup
	 *            the wqActionLkup to set
	 */
	public void setWqActionLkup(WqActionLkupDTO wqActionLkup) {
		this.wqActionLkup = wqActionLkup;
	}

	/**
	 * @return the suspendBomReasonCd
	 */
	public String getSuspendBomReasonCd() {
		return suspendBomReasonCd;
	}

	/**
	 * @param suspendBomReasonCd
	 *            the suspendBomReasonCd to set
	 */
	public void setSuspendBomReasonCd(String suspendBomReasonCd) {
		this.suspendBomReasonCd = suspendBomReasonCd;
	}
}
