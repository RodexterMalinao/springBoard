package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

import com.bomwebportal.dto.BasketDTO;


public class NowTVCheckDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 629210834742496125L;
	
	/*
	 * IMS related
	 */
	private String Max_BB;
	private String NTV_srv_cd_found;


	/*
	 * DTO members
	 */
	
	public NowTVCheckDTO(){
		
	}


	public String getMax_BB() {
		return Max_BB;
	}


	public void setMax_BB(String max_BB) {
		Max_BB = max_BB;
	}


	public String getNTV_srv_cd_found() {
		return NTV_srv_cd_found;
	}


	public void setNTV_srv_cd_found(String nTV_srv_cd_found) {
		NTV_srv_cd_found = nTV_srv_cd_found;
	}

}
