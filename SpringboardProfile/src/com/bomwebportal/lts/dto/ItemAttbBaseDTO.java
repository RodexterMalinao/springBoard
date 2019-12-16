package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class ItemAttbBaseDTO implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -679049204475003284L;

	private String attbID;
	
	private String attbDesc;
	
	private String inputMethod;
	
	private String inputFormat;
	
	private int minLength;
	
	private int maxLength;
	
	private String defaultValue;
	
	private String attbInfoKey;
	
	private String visualInd;

	private String attbValue; 
	
	public String getAttbID() {
		return attbID;
	}

	public void setAttbID(String attbID) {
		this.attbID = attbID;
	}

	public String getAttbDesc() {
		return attbDesc;
	}

	public void setAttbDesc(String attbDesc) {
		this.attbDesc = attbDesc;
	}

	public String getInputMethod() {
		return inputMethod;
	}

	public void setInputMethod(String inputMethod) {
		this.inputMethod = inputMethod;
	}

	public String getInputFormat() {
		return inputFormat;
	}

	public void setInputFormat(String inputFormat) {
		this.inputFormat = inputFormat;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getAttbInfoKey() {
		return attbInfoKey;
	}

	public void setAttbInfoKey(String attbInfoKey) {
		this.attbInfoKey = attbInfoKey;
	}

	public String getVisualInd() {
		return visualInd;
	}

	public void setVisualInd(String visualInd) {
		this.visualInd = visualInd;
	}

	public String getAttbValue() {
		return attbValue;
	}

	public void setAttbValue(String attbValue) {
		this.attbValue = attbValue;
	}
}
