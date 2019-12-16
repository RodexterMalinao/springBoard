package com.bomwebportal.dto;


public class RemarkDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = 7621031506543135974L;
	
	private String rmkType = null;
	private String rmkSeq = null;
	private String rmkDtl = null;
	private String wqWpAssgnId = null;

	public String getRmkType() {
		return rmkType;
	}

	public void setRmkType(String rmkType) {
		this.rmkType = rmkType;
	}

	public String getRmkSeq() {
		return rmkSeq;
	}

	public void setRmkSeq(String rmkSeq) {
		this.rmkSeq = rmkSeq;
	}

	public String getRmkDtl() {
		return rmkDtl;
	}

	public void setRmkDtl(String rmkDtl) {
		this.rmkDtl = rmkDtl;
	}

	public String getWqWpAssgnId() {
		return wqWpAssgnId;
	}

	public void setWqWpAssgnId(String pWqWpAssgnId) {
		wqWpAssgnId = pWqWpAssgnId;
	}
	
}
