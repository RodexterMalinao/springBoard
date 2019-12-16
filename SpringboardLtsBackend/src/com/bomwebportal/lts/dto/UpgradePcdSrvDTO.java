package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class UpgradePcdSrvDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3554759424042473994L;

	
	private String AddrCoverage = null;

	private String ImsExitSrv = null;

	private String ImsNewSrv = null;

	private String ModemArrangement = null;
	
	private int MinBandwidth;
	
	private String imsOrderType = null;


	public String getImsOrderType() {
		return imsOrderType;
	}

	public void setImsOrderType(String imsOrderType) {
		this.imsOrderType = imsOrderType;
	}

	public int getMinBandwidth() {
		return this.MinBandwidth;
	}

	public void setMinBandwidth(int pMinBandwidth) {
		this.MinBandwidth = pMinBandwidth;
	}

	public String getAddrCoverage() {
		return this.AddrCoverage;
	}

	public void setAddrCoverage(String pAddrCoverage) {
		this.AddrCoverage = pAddrCoverage;
	}

	public String getImsExitSrv() {
		return this.ImsExitSrv;
	}

	public void setImsExitSrv(String pImsExitSrv) {
		this.ImsExitSrv = pImsExitSrv;
	}

	public String getImsNewSrv() {
		return this.ImsNewSrv;
	}

	public void setImsNewSrv(String pImsNewSrv) {
		this.ImsNewSrv = pImsNewSrv;
	}

	public String getModemArrangement() {
		return this.ModemArrangement;
	}

	public void setModemArrangement(String pModemArrangement) {
		this.ModemArrangement = pModemArrangement;
	}

	
	
	
}
