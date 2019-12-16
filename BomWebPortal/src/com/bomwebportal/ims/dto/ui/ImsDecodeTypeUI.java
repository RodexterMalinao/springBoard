package com.bomwebportal.ims.dto.ui;

import java.io.Serializable;

public class ImsDecodeTypeUI implements Serializable{
	
	private String code;
	private String description;
	
	public ImsDecodeTypeUI(){
		
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
