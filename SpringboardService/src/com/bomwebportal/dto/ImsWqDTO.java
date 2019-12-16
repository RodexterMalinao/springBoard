package com.bomwebportal.dto;

import java.io.Serializable;

public class ImsWqDTO implements Serializable {

	
	private static final long serialVersionUID = -672131444458078974L;
	private String wqWpAssgnId;
	private String wqId; 
	private String wqBatchId;
	private String wqNatureId;
	public String getWqWpAssgnId() {
		return wqWpAssgnId;
	}
	public void setWqWpAssgnId(String wqWpAssgnId) {
		this.wqWpAssgnId = wqWpAssgnId;
	}
	public String getWqId() {
		return wqId;
	}
	public void setWqId(String wqId) {
		this.wqId = wqId;
	}
	public String getWqBatchId() {
		return wqBatchId;
	}
	public void setWqBatchId(String wqBatchId) {
		this.wqBatchId = wqBatchId;
	}
	public void setWqNatureId(String wqNatureId) {
		this.wqNatureId = wqNatureId;
	}
	public String getWqNatureId() {
		return wqNatureId;
	}
	
	
	
	
}