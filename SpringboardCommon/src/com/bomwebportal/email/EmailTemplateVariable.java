package com.bomwebportal.email;

import java.io.Serializable;
import java.util.TreeMap;


public class EmailTemplateVariable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5952463097559237960L;
	
	// Initial copied on 20140925 from Version 1.56 of
	// Springboard\SBWPR3\BomWebPortal\src\com\bomwebportal\service\OrderEsignatureServiceImpl.java
	
	public static final Integer EMAIL_ADDRESS = 0; // customer email address
	public static final Integer CUSTOMER_TITLE = 1;
	public static final Integer CUSTOMER_NAME = 2;
	
	
	private TreeMap<Integer, String> emailTemplateVariableMap; 
	private Integer variableCd;
	private String variableStr;
	
		
	public EmailTemplateVariable() {
		this.emailTemplateVariableMap = new TreeMap<Integer, String>();
		
		this.emailTemplateVariableMap.put(EMAIL_ADDRESS, "emailAddress");
		this.emailTemplateVariableMap.put(CUSTOMER_TITLE, "customerTitle");
		this.emailTemplateVariableMap.put(CUSTOMER_NAME, "customerName");
	}
	
	
	public TreeMap<Integer, String> getEmailTemplateVariableMap() {
		return this.emailTemplateVariableMap;
	}

	public void setEmailTemplateVariableMap(TreeMap<Integer, String> pEmailTemplateVariableMap) {
		this.emailTemplateVariableMap = pEmailTemplateVariableMap;
	}
	
	public void addTemplateVariable(Integer pVariableCd, String pVariableStr) {
		this.emailTemplateVariableMap.put(pVariableCd, pVariableStr);
	}
	
	public Integer getLastEmailTemplateVariableKey() {
		return this.emailTemplateVariableMap.lastKey();
	}
	
	public Integer getVariableCd() {
		return this.variableCd;
	}
	
	public void setVariableCd(Integer pVariableCd) {
		this.variableCd = pVariableCd;
		this.setVariableStr(emailTemplateVariableMap.get(pVariableCd));
	}
	
	public String getVariableStr() {
		return this.variableStr;
	}
	
	private void setVariableStr(String pVariableStr) {
		this.variableStr = pVariableStr;
	}
}
