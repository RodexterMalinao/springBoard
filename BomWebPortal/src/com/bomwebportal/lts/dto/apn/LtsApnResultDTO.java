package com.bomwebportal.lts.dto.apn;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class LtsApnResultDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2775440287929443785L;

	private String orderId;
	private String dtlId;
	private String serviceNum;
	private String cutOverStartDate;
	private String cutOverEndDate;
	
	public String getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}
/*	public Date getSrdDate() {
		return srdDate;
	}
	public void setSrdDate(String pSrdDate) {
		if(srdDate != null){
			try {
				this.srdDate = df.parse(pSrdDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDtlId() {
		return dtlId;
	}
	public void setDtlId(String dtlId) {
		this.dtlId = dtlId;
	}
	public String getCutOverStartDate() {
		return cutOverStartDate;
	}
	public void setCutOverStartDate(String cutOverStartDate) {
		this.cutOverStartDate = cutOverStartDate;
	}
	public String getCutOverEndDate() {
		return cutOverEndDate;
	}
	public void setCutOverEndDate(String cutOverEndDate) {
		this.cutOverEndDate = cutOverEndDate;
	}
}
