/*
 * Created on Nov 12, 2013
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.dto.order;

public class LtsActionLkupDTO extends ServiceActionLkupBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6576894193665484747L;

	private String fromProd;

	private String toProd;
       
	public String getLkupKey() {
		return this.getFromProd() + "^" +
		       this.getToProd();
	}	
	
	/**
	 * @return the fromProd
	 */
	public String getFromProd() {
		return fromProd;
	}
	/**
	 * @param fromProd the fromProd to set
	 */
	public void setFromProd(String fromProd) {
		this.fromProd = fromProd;
	}
	/**
	 * @return the toProd
	 */
	public String getToProd() {
		return toProd;
	}
	/**
	 * @param toProd the toProd to set
	 */
	public void setToProd(String toProd) {
		this.toProd = toProd;
	}
}
