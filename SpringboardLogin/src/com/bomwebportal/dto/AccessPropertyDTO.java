package com.bomwebportal.dto;

import java.io.Serializable;

public class AccessPropertyDTO implements Serializable {

	private static final long serialVersionUID = -1404745558099816065L;

	private String propertyName = null;
	
	private String accessRight = null;

	
	public AccessPropertyDTO(String pPropertyName, String pAccessRight) {
		this.propertyName = pPropertyName;
		this.accessRight = pAccessRight;
	}
	
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getAccessRight() {
		return accessRight;
	}

	public void setAccessRight(String accessRight) {
		this.accessRight = accessRight;
	}
}
