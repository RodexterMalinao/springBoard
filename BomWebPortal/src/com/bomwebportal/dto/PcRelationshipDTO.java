package com.bomwebportal.dto;

public class PcRelationshipDTO {
	public enum ItemType {
		O // offer
		, P // product
	}
	public enum RelType {
		CO("Co-Exists") // Co-Exists
		, EX("Exclusive") // Exclusive
		, PR("Prerequisite") // Prerequisite
		, PO("Prerequisite Or") // Prerequisite Or
		, CP("Compatible") // Compatible
		;
		private RelType(String desc) {
			this.desc = desc;
		}
		public String getDesc() {
			return desc;
		}
		private String desc;
	}
	
	public String getDependsOnItemID() {
		return dependsOnItemID;
	}
	public void setDependsOnItemID(String dependsOnItemID) {
		this.dependsOnItemID = dependsOnItemID;
	}
	public String getDependsOnItemType() {
		return dependsOnItemType;
	}
	public void setDependsOnItemType(String dependsOnItemType) {
		this.dependsOnItemType = dependsOnItemType;
	}
	public String getDependsOnItemCode() {
		return dependsOnItemCode;
	}
	public void setDependsOnItemCode(String dependsOnItemCode) {
		this.dependsOnItemCode = dependsOnItemCode;
	}
	public String getPrimaryItemID() {
		return primaryItemID;
	}
	public void setPrimaryItemID(String primaryItemID) {
		this.primaryItemID = primaryItemID;
	}
	public String getPrimaryItemType() {
		return primaryItemType;
	}
	public void setPrimaryItemType(String primaryItemType) {
		this.primaryItemType = primaryItemType;
	}
	public String getPrimaryItemCode() {
		return primaryItemCode;
	}
	public void setPrimaryItemCode(String primaryItemCode) {
		this.primaryItemCode = primaryItemCode;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getSecondaryItemID() {
		return secondaryItemID;
	}
	public void setSecondaryItemID(String secondaryItemID) {
		this.secondaryItemID = secondaryItemID;
	}
	public String getSecondaryItemType() {
		return secondaryItemType;
	}
	public void setSecondaryItemType(String secondaryItemType) {
		this.secondaryItemType = secondaryItemType;
	}
	public String getSecondaryItemCode() {
		return secondaryItemCode;
	}
	public void setSecondaryItemCode(String secondaryItemCode) {
		this.secondaryItemCode = secondaryItemCode;
	}
	private String dependsOnItemID;
	private String dependsOnItemType;
	private String dependsOnItemCode;
	private String primaryItemID;
	private String primaryItemType;
	private String primaryItemCode;
	private String relationship;
	private String secondaryItemID;
	private String secondaryItemType;
	private String secondaryItemCode;
}
