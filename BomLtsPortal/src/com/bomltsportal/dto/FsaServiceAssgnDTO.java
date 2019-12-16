package com.bomltsportal.dto;

import java.io.Serializable;

public class FsaServiceAssgnDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6439011697810588541L;
	
	
	private String imsOrderType;
	private String modem;
	private String technology;
	private String bandwidth;
	private boolean bbShortage;
	
	public String getImsOrderType() {
		return imsOrderType;
	}
	public void setImsOrderType(String imsOrderType) {
		this.imsOrderType = imsOrderType;
	}
	public String getModem() {
		return modem;
	}
	public void setModem(String modem) {
		this.modem = modem;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public boolean isBbShortage() {
		return bbShortage;
	}
	public void setBbShortage(boolean bbShortage) {
		this.bbShortage = bbShortage;
	}

}
