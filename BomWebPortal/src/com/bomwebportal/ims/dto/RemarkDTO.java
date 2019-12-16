package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class RemarkDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4770706980550743097L;
	
	
	private String OrderId;
	private String DtlId;
	private String RmkType;
	private String RmkSeq;
	private String RmkDtl;
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;

	
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getRmkType() {
		return RmkType;
	}
	public void setRmkType(String rmkType) {
		RmkType = rmkType;
	}
	public String getRmkSeq() {
		return RmkSeq;
	}
	public void setRmkSeq(String rmkSeq) {
		RmkSeq = rmkSeq;
	}
	public String getRmkDtl() {
		return RmkDtl;
	}
	public void setRmkDtl(String rmkDtl) {
		RmkDtl = rmkDtl;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getDtlId() {
		return DtlId;
	}
	public void setDtlId(String dtlId) {
		DtlId = dtlId;
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


	
}
