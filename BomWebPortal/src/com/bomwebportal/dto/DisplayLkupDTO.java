package com.bomwebportal.dto;

import java.io.Serializable;

public class DisplayLkupDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8591684494982570393L;
	
	private String type;
	private int id;
	private String locale;
	private String description;
	
	public DisplayLkupDTO() {
		this.type = "";
		this.setId(0);
		this.locale = "";
		this.description = "";
	}
	
	public DisplayLkupDTO(String type, int id, String locale, String description) {
		this.type = type;
		this.setId(id);
		this.locale = locale;
		this.description = description;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
