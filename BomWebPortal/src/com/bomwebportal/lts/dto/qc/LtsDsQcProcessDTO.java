package com.bomwebportal.lts.dto.qc;

import java.io.Serializable;

import com.bomwebportal.ims.dto.ImsAlertMsgDTO;

public class LtsDsQcProcessDTO extends ImsAlertMsgDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8699131707092798477L;
	
	private String qcCallTime;
	private String orderDistrict;
	private String orderPlace;
	private String mustQc;
	private String sbStatus;
	
	public String getQcCallTime() {
		return qcCallTime;
	}
	public void setQcCallTime(String qcCallTime) {
		this.qcCallTime = qcCallTime;
	}
	public String getOrderDistrict() {
		return orderDistrict;
	}
	public void setOrderDistrict(String orderDistrict) {
		this.orderDistrict = orderDistrict;
	}
	public String getOrderPlace() {
		return orderPlace;
	}
	public void setOrderPlace(String orderPlace) {
		this.orderPlace = orderPlace;
	}
	public String getMustQc() {
		return mustQc;
	}
	public void setMustQc(String mustQc) {
		this.mustQc = mustQc;
	}
	public String getSbStatus() {
		return sbStatus;
	}
	public void setSbStatus(String sbStatus) {
		this.sbStatus = sbStatus;
	}
	

}
