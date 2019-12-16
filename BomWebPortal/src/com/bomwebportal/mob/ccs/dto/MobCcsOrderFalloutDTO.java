package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class MobCcsOrderFalloutDTO implements Serializable {

	private Integer mrt; 
	private String orderId;
	private Date appDate;
	private String incidentId;
	private Date occuranceDate;
	private String reportBy;
	private String falloutCat;
	private String reasonCode;
	private String resolveCode;
	private Date resolveDate;
	private String resolveBy;
	private String serialInd;
	private String visitInd;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String remark;
	public Integer getMrt() {
		return mrt;
	}
	public void setMrt(Integer mrt) {
		this.mrt = mrt;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	public Date getOccuranceDate() {
		return occuranceDate;
	}
	public void setOccuranceDate(Date occuranceDate) {
		this.occuranceDate = occuranceDate;
	}
	public String getReportBy() {
		return reportBy;
	}
	public void setReportBy(String reportBy) {
		this.reportBy = reportBy;
	}
	public String getFalloutCat() {
		return falloutCat;
	}
	public void setFalloutCat(String falloutCat) {
		this.falloutCat = falloutCat;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getResolveCode() {
		return resolveCode;
	}
	public void setResolveCode(String resolveCode) {
		this.resolveCode = resolveCode;
	}
	public Date getResolveDate() {
		return resolveDate;
	}
	public void setResolveDate(Date resolveDate) {
		this.resolveDate = resolveDate;
	}
	public String getResolveBy() {
		return resolveBy;
	}
	public void setResolveBy(String resolveBy) {
		this.resolveBy = resolveBy;
	}
	public String getSerialInd() {
		return serialInd;
	}
	public void setSerialInd(String serialInd) {
		this.serialInd = serialInd;
	}
	public String getVisitInd() {
		return visitInd;
	}
	public void setVisitInd(String visitInd) {
		this.visitInd = visitInd;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
		
}
