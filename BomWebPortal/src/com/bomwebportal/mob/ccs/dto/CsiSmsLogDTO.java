package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class CsiSmsLogDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4363114589677620042L;
	
	private String caseNo;
	private String orderId;
	private Date smsRecordDate;
	private String seqNo;
	private String smsRecordDateStr;
	private String smsRecordTimeHour;
	private String smsRecordTimeMin;
	
	
	public String getCaseNo() {
		return caseNo;
	}
	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Date getSmsRecordDate() {
		return smsRecordDate;
	}
	public void setSmsRecordDate(Date smsRecordDate) {
		this.smsRecordDate = smsRecordDate;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getSmsRecordDateStr() {
		return smsRecordDateStr;
	}
	public void setSmsRecordDateStr(String smsRecordDateStr) {
		this.smsRecordDateStr = smsRecordDateStr;
	}
	public String getSmsRecordTimeHour() {
		return smsRecordTimeHour;
	}
	public void setSmsRecordTimeHour(String smsRecordTimeHour) {
		this.smsRecordTimeHour = smsRecordTimeHour;
	}
	public String getSmsRecordTimeMin() {
		return smsRecordTimeMin;
	}
	public void setSmsRecordTimeMin(String smsRecordTimeMin) {
		this.smsRecordTimeMin = smsRecordTimeMin;
	}
	
	
}
