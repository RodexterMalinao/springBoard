/*
 * Created on Nov 12, 2013
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.dto.order;

public class ImsActionLkupDTO extends LtsActionLkupDTO {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 268597014264041156L;

	private String orderType;
	
	private String modemArrangement;

	private String mirror;

	private String offerGrpId;

	public String getLkupKey() {
		return this.getFromProd() + "^" +
		       this.getToProd()   + "^" +
		       this.getModemArrangement() + "^" +
		       this.getMirror() + "^" +
		       this.getOrderType();
	}		
	
	/**
	 * @return the modemArrangement
	 */
	public String getModemArrangement() {
		return modemArrangement;
	}

	/**
	 * @param modemArrangement the modemArrangement to set
	 */
	public void setModemArrangement(String modemArrangement) {
		this.modemArrangement = modemArrangement;
	}

	/**
	 * @return the mirror
	 */
	public String getMirror() {
		return mirror;
	}

	/**
	 * @param mirror the mirror to set
	 */
	public void setMirror(String mirror) {
		this.mirror = mirror;
	}

	/**
	 * @return the offerGrpId
	 */
	public String getOfferGrpId() {
		return offerGrpId;
	}

	/**
	 * @param offerGrpId the offerGrpId to set
	 */
	public void setOfferGrpId(String offerGrpId) {
		this.offerGrpId = offerGrpId;
	}
	
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}
