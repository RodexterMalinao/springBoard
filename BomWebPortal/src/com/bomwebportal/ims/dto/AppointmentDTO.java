package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class AppointmentDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5910596815761492285L;  

	private String OrderId;
	private String SerialNum;
	private String DtlId;
	private Date AppntStartDate;
	private Date AppntEndDate;
	private String InstContactName;
	private String InstContactNum;
	private String InstSmsNum;	
	private Date ExactAppntTime;
	private String ForcedDelayInd;
	private Date PreWiringStartTime;	
	private Date PreWiringEndTime;
	private String PreWiringType;
	private String TidInd;
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;
	private String AppntType;
	
	//steven added 20131106 start
	private int storProReturnValue;
	private int storProErrorCode;
	private String storProErrorText;
	private String alignWithBillDate;
	
	public int getStorProReturnValue() {
		return storProReturnValue;
	}
	public void setStorProReturnValue(int storProReturnValue) {
		this.storProReturnValue = storProReturnValue;
	}
	public int getStorProErrorCode() {
		return storProErrorCode;
	}
	public void setStorProErrorCode(int storProErrorCode) {
		this.storProErrorCode = storProErrorCode;
	}
	public String getStorProErrorText() {
		return storProErrorText;
	}
	public void setStorProErrorText(String storProErrorText) {
		this.storProErrorText = storProErrorText;
	}
	//steven added 20131106 end
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getSerialNum() {
		return SerialNum;
	}
	public void setSerialNum(String serialNum) {
		SerialNum = serialNum;
	}
	public Date getAppntStartDate() {
		return AppntStartDate;
	}
	public void setAppntStartDate(Date appntStartDate) {
		AppntStartDate = appntStartDate;
	}
	public Date getAppntEndDate() {
		return AppntEndDate;
	}
	public void setAppntEndDate(Date appntEndDate) {
		AppntEndDate = appntEndDate;
	}
	public String getInstContactName() {
		return InstContactName;
	}
	public void setInstContactName(String instContactName) {
		InstContactName = instContactName;
	}
	public String getInstContactNum() {
		return InstContactNum;
	}
	public void setInstContactNum(String instContactNum) {
		InstContactNum = instContactNum;
	}
	public String getInstSmsNum() {
		return InstSmsNum;
	}
	public void setInstSmsNum(String instSmsNum) {
		InstSmsNum = instSmsNum;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public Date getExactAppntTime() {
		return ExactAppntTime;
	}
	public void setExactAppntTime(Date exactAppntTime) {
		ExactAppntTime = exactAppntTime;
	}
	public String getForcedDelayInd() {
		return ForcedDelayInd;
	}
	public void setForcedDelayInd(String forcedDelayInd) {
		ForcedDelayInd = forcedDelayInd;
	}
	public Date getPreWiringStartTime() {
		return PreWiringStartTime;
	}
	public void setPreWiringStartTime(Date preWiringStartTime) {
		PreWiringStartTime = preWiringStartTime;
	}
	public Date getPreWiringEndTime() {
		return PreWiringEndTime;
	}
	public void setPreWiringEndTime(Date preWiringEndTime) {
		PreWiringEndTime = preWiringEndTime;
	}
	public String getPreWiringType() {
		return PreWiringType;
	}
	public void setPreWiringType(String preWiringType) {
		PreWiringType = preWiringType;
	}
	public String getTidInd() {
		return TidInd;
	}
	public void setTidInd(String tidInd) {
		TidInd = tidInd;
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
	public String getAppntType() {
		return AppntType;
	}
	public void setAppntType(String appntType) {
		AppntType = appntType;
	}	

	public void setBmoLeadDay(String bmoLeadDay) {
		BmoLeadDay = bmoLeadDay;
	}
	public String getBmoLeadDay() {
		return BmoLeadDay;
	}

	private String BmoLeadDay;
	

	public String getAlignWithBillDate() {
		return alignWithBillDate;
	}

	public void setAlignWithBillDate(String alignWithBillDate) {
		this.alignWithBillDate = alignWithBillDate;
	}

}
