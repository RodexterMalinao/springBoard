package com.bomwebportal.dto;

public class VasAttbComponentDTO extends ComponentDTO{
	
	private static final long serialVersionUID = 5626807938703260352L;
	
	private String compAttbDesc;
	private String inputMethod;
	private String compAttbValueDesc;
	private String inputFormat;
	private int minLength;
	private int maxLength;
	
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
	public String getCompAttbDesc() {
		return compAttbDesc;
	}
	public void setCompAttbDesc(String compAttbDesc) {
		this.compAttbDesc = compAttbDesc;
	}
	public String getInputMethod() {
		return inputMethod;
	}
	public void setInputMethod(String inputMethod) {
		this.inputMethod = inputMethod;
	}
	public String getCompAttbValueDesc() {
		return compAttbValueDesc;
	}
	public void setCompAttbValueDesc(String compAttbValueDesc) {
		this.compAttbValueDesc = compAttbValueDesc;
	}
}
