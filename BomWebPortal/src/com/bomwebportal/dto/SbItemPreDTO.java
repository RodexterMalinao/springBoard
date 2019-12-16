package com.bomwebportal.dto;

public class SbItemPreDTO {
	public String getSourceItemId() {
		return sourceItemId;
	}
	public void setSourceItemId(String sourceItemId) {
		this.sourceItemId = sourceItemId;
	}
	public String getSourceDesc() {
		return sourceDesc;
	}
	public void setSourceDesc(String sourceDesc) {
		this.sourceDesc = sourceDesc;
	}
	public String getTargetItemId() {
		return targetItemId;
	}
	public void setTargetItemId(String targetItemId) {
		this.targetItemId = targetItemId;
	}
	public String getTargetDesc() {
		return targetDesc;
	}
	public void setTargetDesc(String targetDesc) {
		this.targetDesc = targetDesc;
	}
	private String sourceItemId;
	private String sourceDesc;
	private String targetItemId;
	private String targetDesc;
}
