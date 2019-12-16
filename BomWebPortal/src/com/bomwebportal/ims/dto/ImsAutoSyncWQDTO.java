package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.dto.BomSalesUserDTO;

public class ImsAutoSyncWQDTO implements Serializable{
	
	
	/**
	 * created by steven
	 */
	private static final long serialVersionUID = 3101488332783993790L;
	private String sbid;
	private String wqWpAssgnId;
	private String amendSeq;
	private String wqNatureID;
	private String wqBatchId;
	
	public String getAmendSeq() {
		return amendSeq;
	}
	public void setAmendSeq(String amendSeq) {
		this.amendSeq = amendSeq;
	}
	public String getWqNatureID() {
		return wqNatureID;
	}
	public void setWqNatureID(String wqNatureID) {
		this.wqNatureID = wqNatureID;
	}
	public String getSbid() {
		return sbid;
	}
	public void setSbid(String sbid) {
		this.sbid = sbid;
	}
	public String getWqWpAssgnId() {
		return wqWpAssgnId;
	}
	public void setWqWpAssgnId(String wqWpAssgnId) {
		this.wqWpAssgnId = wqWpAssgnId;
	}
	public void setWqBatchId(String wqBatchId) {
		this.wqBatchId = wqBatchId;
	}
	public String getWqBatchId() {
		return wqBatchId;
	}
	
	
	
}
