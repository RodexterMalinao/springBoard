package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class NowTvServiceDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7078553821864123906L;
	
	private String status;
	private boolean receiveAdultContent;
	private String tvType;
	private int lineRate;
	private int stbNum;
	private int eye2Num;
	private int vstbNum;
	private String vstbType;
	private String billType;
	private String dateOfBirth;
	private boolean tvbSubscriber;
	
	// Total Charge Amount
	private float arpu;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isReceiveAdultContent() {
		return receiveAdultContent;
	}
	public void setReceiveAdultContent(boolean receiveAdultContent) {
		this.receiveAdultContent = receiveAdultContent;
	}
	public String getTvType() {
		return tvType;
	}
	public void setTvType(String tvType) {
		this.tvType = tvType;
	}
	public int getLineRate() {
		return lineRate;
	}
	public void setLineRate(int lineRate) {
		this.lineRate = lineRate;
	}
	public int getStbNum() {
		return stbNum;
	}
	public void setStbNum(int stbNum) {
		this.stbNum = stbNum;
	}
	public int getEye2Num() {
		return eye2Num;
	}
	public void setEye2Num(int eye2Num) {
		this.eye2Num = eye2Num;
	}
	public int getVstbNum() {
		return vstbNum;
	}
	public void setVstbNum(int vstbNum) {
		this.vstbNum = vstbNum;
	}
	public String getVstbType() {
		return vstbType;
	}
	public void setVstbType(String vstbType) {
		this.vstbType = vstbType;
	}
	public float getArpu() {
		return arpu;
	}
	public void setArpu(float arpu) {
		this.arpu = arpu;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public boolean isTvbSubscriber() {
		return tvbSubscriber;
	}
	public void setTvbSubscriber(boolean tvbSubscriber) {
		this.tvbSubscriber = tvbSubscriber;
	}
	
	
}
