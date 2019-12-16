package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class AddrInventoryDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3255416914065736230L;
	
	
	private String OrderId;
	private String DtlId;
	private String AddrUsage;
	private String ResourceShortage;
	private String Technology;
	private String BuildingType;
	private String N2BuildingInd;
	private String PON2NBuildingInd;
	private String FieldPermitDay;
	private String AddrId;
	private String MaxBandwidth;
	private String FttcInd;	
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;
	
	private String h264Ind;
	private String adsl18MInd;
	private String ntvCoverage;
	

	public void setAdsl8MInd(String adsl8mInd) {
		adsl8MInd = adsl8mInd;
	}
	private String adsl8MInd;
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getAddrUsage() {
		return AddrUsage;
	}
	public void setAddrUsage(String addrUsage) {
		AddrUsage = addrUsage;
	}
	public String getResourceShortage() {
		return ResourceShortage;
	}
	public void setResourceShortage(String resourceShortage) {
		ResourceShortage = resourceShortage;
	}
	public String getTechnology() {
		return Technology;
	}
	public void setTechnology(String technology) {
		Technology = technology;
	}
	public String getBuildingType() {
		return BuildingType;
	}
	public void setBuildingType(String buildingType) {
		BuildingType = buildingType;
	}
	public String getN2BuildingInd() {
		return N2BuildingInd;
	}
	public void setN2BuildingInd(String n2BuildingInd) {
		N2BuildingInd = n2BuildingInd;
	}
	public String getFieldPermitDay() {
		return FieldPermitDay;
	}
	public void setFieldPermitDay(String fieldPermitDay) {
		FieldPermitDay = fieldPermitDay;
	}
	public String getAddrId() {
		return AddrId;
	}
	public void setAddrId(String addrId) {
		AddrId = addrId;
	}
	public String getMaxBandwidth() {
		return MaxBandwidth;
	}
	public void setMaxBandwidth(String maxBandwidth) {
		MaxBandwidth = maxBandwidth;
	}
	public String getFttcInd() {
		return FttcInd;
	}
	public void setFttcInd(String fttcInd) {
		FttcInd = fttcInd;
	}
	public String getDtlId() {
		return DtlId;
	}
	public void setDtlId(String dtlId) {
		DtlId = dtlId;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public Date getLastUpdDate() {
		return LastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		LastUpdDate = lastUpdDate;
	}
	public String getLastUpdBy() {
		return LastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		LastUpdBy = lastUpdBy;
	}
	public void setPON2NBuildingInd(String pON2NBuildingInd) {
		PON2NBuildingInd = pON2NBuildingInd;
	}
	public String getPON2NBuildingInd() {
		return PON2NBuildingInd;
	}
	public void setH264Ind(String h264Ind) {
		this.h264Ind = h264Ind;
	}
	public String getH264Ind() {
		return h264Ind;
	}
	public void setAdsl18MInd(String adsl18MInd) {
		this.adsl18MInd = adsl18MInd;
	}
	public String getAdsl18MInd() {
		return adsl18MInd;
	}
	
	public String getNtvCoverage() {
		return ntvCoverage;
	}
	public void setNtvCoverage(String ntvCoverage) {
		this.ntvCoverage = ntvCoverage;
	}
	public String getAdsl8MInd() {
		return adsl8MInd;
	}
}
