package com.bomwebportal.ims.dto;

import java.util.Date;

import com.bomwebportal.dto.VasParmDTO;

public class SubscribedCslOfferDTO extends VasParmDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7856013436186506396L;
	
		
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;
	
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
	
	

}
