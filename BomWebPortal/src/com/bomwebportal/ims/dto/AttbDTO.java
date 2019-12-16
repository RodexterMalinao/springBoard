package com.bomwebportal.ims.dto;

public class AttbDTO {
	private String itemId;
	private String AttbId;
	private String AttbDesc;
	private String InputMethod;
	private String InputFormat;
	private String InputValue;
	private String defaultValue;
	private String minLength;
	private String maxLength;
	private String visualInd;
	
	public void setAttbId(String attbId) {
		AttbId = attbId;
	}
	public String getAttbId() {
		return AttbId;
	}
	public void setAttbDesc(String attbDesc) {
		AttbDesc = attbDesc;
	}
	public String getAttbDesc() {
		return AttbDesc;
	}
	public void setInputMethod(String inputMethod) {
		InputMethod = inputMethod;
	}
	public String getInputMethod() {
		return InputMethod;
	}
	public void setInputValue(String inputValue) {
		InputValue = inputValue;
	}
	public String getInputValue() {
		return InputValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}
	public String getMinLength() {
		return minLength;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setInputFormat(String inputFormat) {
		InputFormat = inputFormat;
	}
	public String getInputFormat() {
		return InputFormat;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public String getMaxLength() {
		return maxLength;
	}
	public void setVisualInd(String visualInd) {
		this.visualInd = visualInd;
	}
	public String getVisualInd() {
		return visualInd;
	}
	
	public AttbDTO clone() {
		AttbDTO attb = new AttbDTO();
		
		attb.setAttbDesc(this.AttbDesc);
		attb.setAttbId(this.AttbId);
		attb.setDefaultValue(this.defaultValue);
		attb.setInputFormat(this.InputFormat);
		attb.setInputMethod(this.InputMethod);
		attb.setInputValue(this.InputValue);
		attb.setItemId(this.itemId);
		attb.setMinLength(this.minLength);
		attb.setMaxLength(this.maxLength);
		attb.setVisualInd(this.visualInd);
		
        return attb;
    }
}
