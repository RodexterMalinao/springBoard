package com.bomwebportal.dto;

import java.io.Serializable;

public class NtvUtilDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3787323935362610883L;
	
	private String code;
	public String description; 
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
