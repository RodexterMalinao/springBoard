package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class AppointmentSRDDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6373340224055349177L;
	
	private String OrderID;
	private String Send;
	private String SerialNum;
	private Date AppntStartTime;
	private Date AppntEndTime;
	private String InstContactName;
	private String InstContactNum;
	private String InstSMSNum;
	private String AppntType;	
	private String ExSerialNum;
	private Date ExAppntStartTime;
	private Date ExAppntEndTime;
	private String ExInstContactName;
	private String ExInstContactNum;
	private String ExInstSmsNum;
	private String ExAppntType;
	
	public String getOrderID() {
		return OrderID;
	}
	public void setOrderID(String orderID) {
		OrderID = orderID;
	}
	public String getSend() {
		return Send;
	}
	public void setSend(String send) {
		Send = send;
	}
	public String getSerialNum() {
		return SerialNum;
	}
	public void setSerialNum(String serialNum) {
		SerialNum = serialNum;
	}
	
	public Date getAppntStartTime() {
		return AppntStartTime;
	}
	public void setAppntStartTime(Date appntStartTime) {
		AppntStartTime = appntStartTime;
	}
	public Date getAppntEndTime() {
		return AppntEndTime;
	}
	public void setAppntEndTime(Date appntEndTime) {
		AppntEndTime = appntEndTime;
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
	public String getInstSMSNum() {
		return InstSMSNum;
	}
	public void setInstSMSNum(String instSMSNum) {
		InstSMSNum = instSMSNum;
	}
	public String getAppntType() {
		return AppntType;
	}
	public void setAppntType(String appntType) {
		AppntType = appntType;
	}
	public String getExSerialNum() {
		return ExSerialNum;
	}
	public void setExSerialNum(String exSerialNum) {
		ExSerialNum = exSerialNum;
	}
	public Date getExAppntStartTime() {
		return ExAppntStartTime;
	}
	public void setExAppntStartTime(Date exAppntStartTime) {
		ExAppntStartTime = exAppntStartTime;
	}
	public Date getExAppntEndTime() {
		return ExAppntEndTime;
	}
	public void setExAppntEndTime(Date exAppntEndTime) {
		ExAppntEndTime = exAppntEndTime;
	}
	public String getExInstContactName() {
		return ExInstContactName;
	}
	public void setExInstContactName(String exInstContactName) {
		ExInstContactName = exInstContactName;
	}
	public String getExInstContactNum() {
		return ExInstContactNum;
	}
	public void setExInstContactNum(String exInstContactNum) {
		ExInstContactNum = exInstContactNum;
	}
	public String getExInstSmsNum() {
		return ExInstSmsNum;
	}
	public void setExInstSmsNum(String exInstSmsNum) {
		ExInstSmsNum = exInstSmsNum;
	}
	public String getExAppntType() {
		return ExAppntType;
	}
	public void setExAppntType(String exAppntType) {
		ExAppntType = exAppntType;
	}


	
	
}
