package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class SourceCodeDTO implements Serializable {
	private String appMethod;
	private String sourceCode;
	public String getAppMethod() {
		return appMethod;
	}
	public void setAppMethod(String appMethod) {
		this.appMethod = appMethod;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
}
