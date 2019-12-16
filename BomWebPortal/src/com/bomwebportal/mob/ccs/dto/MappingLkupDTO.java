package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

public class MappingLkupDTO {
	public String getMapType() {
		return mapType;
	}
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}
	public String getMapFrom() {
		return mapFrom;
	}
	public void setMapFrom(String mapFrom) {
		this.mapFrom = mapFrom;
	}
	public String getMapTo() {
		return mapTo;
	}
	public void setMapTo(String mapTo) {
		this.mapTo = mapTo;
	}
	public String getMapDesc() {
		return mapDesc;
	}
	public void setMapDesc(String mapDesc) {
		this.mapDesc = mapDesc;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	private String mapType;
	private String mapFrom;
	private String mapTo;
	private String mapDesc;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
}
