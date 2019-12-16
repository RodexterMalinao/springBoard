package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;


public class SbRemarksDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 530327011975513116L;
	
	public SbRemarksDTO(){
		
	}

	private String has_rbr;

	public void setHas_rbr(String has_rbr) {
		this.has_rbr = has_rbr;
	}

	public String getHas_rbr() {
		return has_rbr;
	}
	
}
