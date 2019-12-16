package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

/**
Date			Developer		Ref			Remark
10-May-2016		Frank Leung		nowRet		Initial Version
*/
public class BomwebThirdPartyApplnDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7587494728372213581L;
	
	private String OrderId;
	private String DtlId;
	private String AppntFirstName;
	private String AppntLastName;
	private String AppntDocType;
	private String AppntDocId;
	private String RelationshipCd;
	private String AppntIdVerifiedInd;
	private String AppntContactNum;
	private String CreateBy;
	private Date CreateDate;
	private String LastUpdBy;
	private Date LastUpdDate;
	private String Title;
	private String remarks;
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getDtlId() {
		return DtlId;
	}
	public void setDtlId(String dtlId) {
		DtlId = dtlId;
	}
	public String getAppntFirstName() {
		return AppntFirstName;
	}
	public void setAppntFirstName(String appntFirstName) {
		AppntFirstName = appntFirstName;
	}
	public String getAppntLastName() {
		return AppntLastName;
	}
	public void setAppntLastName(String appntLastName) {
		AppntLastName = appntLastName;
	}
	public String getAppntDocType() {
		return AppntDocType;
	}
	public void setAppntDocType(String appntDocType) {
		AppntDocType = appntDocType;
	}
	public String getAppntDocId() {
		return AppntDocId;
	}
	public void setAppntDocId(String appntDocId) {
		AppntDocId = appntDocId;
	}
	public String getRelationshipCd() {
		return RelationshipCd;
	}
	public void setRelationshipCd(String relationshipCd) {
		RelationshipCd = relationshipCd;
	}
	public String getAppntIdVerifiedInd() {
		return AppntIdVerifiedInd;
	}
	public void setAppntIdVerifiedInd(String appntIdVerifiedInd) {
		AppntIdVerifiedInd = appntIdVerifiedInd;
	}
	public String getAppntContactNum() {
		return AppntContactNum;
	}
	public void setAppntContactNum(String appntContactNum) {
		AppntContactNum = appntContactNum;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getLastUpdBy() {
		return LastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		LastUpdBy = lastUpdBy;
	}
	public Date getLastUpdDate() {
		return LastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		LastUpdDate = lastUpdDate;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}


}
