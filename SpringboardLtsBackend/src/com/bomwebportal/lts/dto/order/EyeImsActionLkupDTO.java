/*
 * Created on Nov 12, 2013
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.dto.order;

public class EyeImsActionLkupDTO extends ServiceActionLkupBaseDTO {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 262594415508471364L;

	private String fromEyeProd;

	private String toEyeProd;
	
	private String fromImsProd;

	private String existModemArrangement;

	private String existMirror;

	public String getLkupKey() {
		return this.getFromEyeProd() + "^" +
		       this.getToEyeProd()   + "^" +
			   this.fromImsProd + "^" +
		       this.getExistModemArrangement() + "^" +
		       this.getExistMirror();
	}
	
	/**
	 * @return the fromEyeProd
	 */
	public String getFromEyeProd() {
		return fromEyeProd;
	}

	/**
	 * @param fromEyeProd the fromEyeProd to set
	 */
	public void setFromEyeProd(String fromEyeProd) {
		this.fromEyeProd = fromEyeProd;
	}

	/**
	 * @return the toEyeProd
	 */
	public String getToEyeProd() {
		return toEyeProd;
	}

	/**
	 * @param toEyeProd the toEyeProd to set
	 */
	public void setToEyeProd(String toEyeProd) {
		this.toEyeProd = toEyeProd;
	}

	/**
	 * @return the existModemArrangement
	 */
	public String getExistModemArrangement() {
		return existModemArrangement;
	}

	/**
	 * @param existModemArrangement the existModemArrangement to set
	 */
	public void setExistModemArrangement(String existModemArrangement) {
		this.existModemArrangement = existModemArrangement;
	}

	/**
	 * @return the existMirror
	 */
	public String getExistMirror() {
		return existMirror;
	}

	/**
	 * @param existMirror the existMirror to set
	 */
	public void setExistMirror(String existMirror) {
		this.existMirror = existMirror;
	}

	/**
	 * @return the fromImsProd
	 */
	public String getFromImsProd() {
		return fromImsProd;
	}
	
	/**
	 * @param fromImsProd the fromImsProd to set
	 */
	public void setFromImsProd(String fromImsProd) {
		this.fromImsProd = fromImsProd;
	}

}
