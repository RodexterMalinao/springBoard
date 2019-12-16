package com.bomwebportal.ims.dto;

import java.io.Serializable;


public class BandwidthDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 530327011975513116L;
	
	public BandwidthDTO(){
		
	}

	private String bandwidth;
	
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

	@Override
	public String toString() {
		return "BandwidthDTO [bandwidth=" + bandwidth + "]";
	}

}
