package com.pccw.dto;

import java.io.Serializable;
import java.util.TreeMap;

public abstract class AbstractSecuritySupportFormDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4410174411785096645L;

	public static final String SECURITY_DISABLE = "D";
	
	public static final String SECURITY_ENABLE = "E";
	
	public static final String SECURITY_READONLY = "R";
	
	public static final String SECURITY_HIDDEN = "H";

	private String functionId;
	
	private String defaultSecurity;
	
	private TreeMap<String, String> propertySecurityMap = new TreeMap<String, String>();

	public String getDefaultSecurity() {
		return this.defaultSecurity;
	}

	public String getFunctionId() {
		return this.functionId;
	}

	public void addPropertySecurity(String pProperty, String pSecurity) {
		this.propertySecurityMap.put(pProperty, pSecurity);
	}
	
	public String getPropertySecurity(String pProperty) {
		return this.propertySecurityMap.get(pProperty);
	}
	
	public AbstractSecuritySupportFormDTO(String pFunctionId, String pDefaultSecurity) {
		this.functionId = pFunctionId;
		this.defaultSecurity = pDefaultSecurity;
	}
}