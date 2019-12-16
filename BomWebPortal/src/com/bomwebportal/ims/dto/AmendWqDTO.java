package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class AmendWqDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5096531629663008744L;
	
	private String wqNatureId;
	private String wqNatureDesc;
	public String getWqNatureId() {
		return wqNatureId;
	}
	public void setWqNatureId(String wqNatureId) {
		this.wqNatureId = wqNatureId;
	}
	public String getWqNatureDesc() {
		return wqNatureDesc;
	}
	public void setWqNatureDesc(String wqNatureDesc) {
		this.wqNatureDesc = wqNatureDesc;
	}

	

}
