package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class TechnologyDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7095433133280463252L;
	
	private String technology = null;
	private String isDeadCase = null;
	private String isResrcShort = null;
	private String maximumBandwidth = null;
	private BandwidthDTO bandwidthDTO = null;
	
	public TechnologyDTO() {
		
	}
	
	public TechnologyDTO(String technology) {
		this.technology = technology;
	}
	
	public BandwidthDTO getBandwidthDTO() {
		return this.bandwidthDTO;
	}
	public void setBandwidthDTO(BandwidthDTO pBandwidthDTO) {
		this.bandwidthDTO = pBandwidthDTO;
	}
	public String getMaximumBandwidth() {
		return this.maximumBandwidth;
	}
	public void setMaximumBandwidth(String pMaximumBandwidth) {
		this.maximumBandwidth = pMaximumBandwidth;
	}
	public String getTechnology() {
		return this.technology;
	}
	public void setTechnology(String pTechnology) {
		this.technology = pTechnology;
	}
	public String getIsDeadCase() {
		return this.isDeadCase;
	}
	public void setIsDeadCase(String pIsDeadCase) {
		this.isDeadCase = pIsDeadCase;
	}
	public String getIsResrcShort() {
		return this.isResrcShort;
	}
	public void setIsResrcShort(String pIsResrcShort) {
		this.isResrcShort = pIsResrcShort;
	}

	
	
}
