package com.pccw.wq.schema.dto;

import java.io.Serializable;

public class WorkQueueInquiryCriteriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2941387489837050848L;

	/**
	 * 
	 */

	private String datePattern;
	
	private String wqWpAssgnId;

	private String wpId;
	
	private String[] wpIds;
	
	private String wqSerialRangeFrom;
	
	private String wqSerialRangeTo;
	
	private String sbShopTeamCode; 
	
	private String[] sbIds;
	
	private String bomOcId;
	
	private String wqReceiveDateRangeFrom;
	
	private String wqReceiveDateRangeTo;
	
    private String srdRangeFrom;
	
	private String srdRangeTo;
	
    private String srvType;
    
    private String srvNum;

    private String relatedSrvType;
    
    private String relatedSrvNum;

	private String wqType;
	
	private String wqSubType;

	private String wqStatus;
	
	private String assignee;
	
	private String[] wqNature;
	
	private String sbActvId;

	public String getWqSerialRangeFrom() {
		return this.wqSerialRangeFrom;
	}

	public void setWqSerialRangeFrom(String pWqSerialRangeFrom) {
		this.wqSerialRangeFrom = pWqSerialRangeFrom;
	}

	public String getWqSerialRangeTo() {
		return this.wqSerialRangeTo;
	}

	public void setWqSerialRangeTo(String pWqSerialRangeTo) {
		this.wqSerialRangeTo = pWqSerialRangeTo;
	}

	public String getSbShopTeamCode() {
		return this.sbShopTeamCode;
	}

	public void setSbShopTeamCode(String pSbShopTeamCode) {
		this.sbShopTeamCode = pSbShopTeamCode;
	}

	public String[] getSbIds() {
		return this.sbIds;
	}

	public void setSbIds(String[] pSbIds) {
		this.sbIds = pSbIds;
	}

	public String getBomOcId() {
		return this.bomOcId;
	}

	public void setBomOcId(String pBomOcId) {
		this.bomOcId = pBomOcId;
	}

	public String getWqReceiveDateRangeFrom() {
		return this.wqReceiveDateRangeFrom;
	}

	public void setWqReceiveDateRangeFrom(String pWqReceiveDateRangeFrom) {
		this.wqReceiveDateRangeFrom = pWqReceiveDateRangeFrom;
	}

	public String getWqReceiveDateRangeTo() {
		return this.wqReceiveDateRangeTo;
	}

	public void setWqReceiveDateRangeTo(String pWqReceiveDateRangeTo) {
		this.wqReceiveDateRangeTo = pWqReceiveDateRangeTo;
	}

	public String getSrdRangeFrom() {
		return this.srdRangeFrom;
	}

	public void setSrdRangeFrom(String pSrdRangeFrom) {
		this.srdRangeFrom = pSrdRangeFrom;
	}

	public String getSrdRangeTo() {
		return this.srdRangeTo;
	}

	public void setSrdRangeTo(String pSrdRangeTo) {
		this.srdRangeTo = pSrdRangeTo;
	}

	public String getSrvType() {
		return this.srvType;
	}

	public void setSrvType(String pSrvType) {
		this.srvType = pSrvType;
	}

	public String getSrvNum() {
		return this.srvNum;
	}

	public void setSrvNum(String pSrvNum) {
		this.srvNum = pSrvNum;
	}

	public String getWqType() {
		return this.wqType;
	}

	public void setWqType(String pWqType) {
		this.wqType = pWqType;
	}

	public String getWqSubType() {
		return this.wqSubType;
	}

	public void setWqSubType(String pWqSubType) {
		this.wqSubType = pWqSubType;
	}

	public String getWqStatus() {
		return this.wqStatus;
	}

	public void setWqStatus(String pWqStatus) {
		this.wqStatus = pWqStatus;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String pAssignee) {
		this.assignee = pAssignee;
	}

	public String[] getWqNature() {
		return this.wqNature;
	}

	public void setWqNature(String[] pWqNature) {
		this.wqNature = pWqNature;
	}

	public String getWqWpAssgnId() {
		return this.wqWpAssgnId;
	}

	public void setWqWpAssgnId(String pWqWpAssgnId) {
		this.wqWpAssgnId = pWqWpAssgnId;
	}

	public String getDatePattern() {
		return this.datePattern;
	}

	public void setDatePattern(String pDatePattern) {
		this.datePattern = pDatePattern;
	}

	public String getWpId() {
		return this.wpId;
	}

	public void setWpId(String pWpId) {
		this.wpId = pWpId;
	}

	public String getRelatedSrvType() {
		return this.relatedSrvType;
	}

	public void setRelatedSrvType(String pRelatedSrvType) {
		this.relatedSrvType = pRelatedSrvType;
	}

	public String getRelatedSrvNum() {
		return this.relatedSrvNum;
	}

	public void setRelatedSrvNum(String pRelatedSrvNum) {
		this.relatedSrvNum = pRelatedSrvNum;
	}

	public String[] getWpIds() {
		return this.wpIds;
	}

	public void setWpIds(String[] pWpIds) {
		this.wpIds = pWpIds;
	}

	public String getSbActvId() {
		return this.sbActvId;
	}

	public void setSbActvId(String pSbActvId) {
		this.sbActvId = pSbActvId;
	}
}