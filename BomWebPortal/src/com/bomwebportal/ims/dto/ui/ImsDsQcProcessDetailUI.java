package com.bomwebportal.ims.dto.ui;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.QcStaffDTO;

public class ImsDsQcProcessDetailUI extends QcStaffDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8875418939250311513L;
	protected final Log logger = LogFactory.getLog(getClass());
	
	private String action;
	private String assignDate;
	private String assignee;
	private String qcDateTime;
	private String qcFinding;
	private String orderDistict;
	private String orderPlace;
	private String orderId;
	private String qcStatus;
	private String reasonFailCode;
	private String reasonFailDesc;
	private String reasonCQCode;
	private String reasonCQDesc;
	private String reasonCode;
	private String qcCallTime;
	private String reSelectSrd;	
	private String lob;
	private String sameCustOrder;
	private String preInstallInd;
	
	public String getSameCustOrder() {
		return sameCustOrder;
	}
	public void setSameCustOrder(String sameCustOrder) {
		this.sameCustOrder = sameCustOrder;
	}
	public String getReSelectSrd() {
		return reSelectSrd;
	}
	public void setReSelectSrd(String reSelectSrd) {
		this.reSelectSrd = reSelectSrd;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getAssignDate() {
		return assignDate;
	}
	public void setAssignDate(String assignDate) {
		this.assignDate = assignDate;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getQcDateTime() {
		return qcDateTime;
	}
	public void setQcDateTime(String qcDateTime) {
		this.qcDateTime = qcDateTime;
	}
	public String getQcFinding() {
		return qcFinding;
	}
	public void setQcFinding(String qcFinding) {
		this.qcFinding = qcFinding;
	}
	public String getOrderDistict() {
		return orderDistict;
	}
	public void setOrderDistict(String orderDistict) {
		this.orderDistict = orderDistict;
	}
	public String getOrderPlace() {
		return orderPlace;
	}
	public void setOrderPlace(String orderPlace) {
		this.orderPlace = orderPlace;
	}
	public Log getLogger() {
		return logger;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getAction() {
		return action;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setQcStatus(String qcStatus) {
		this.qcStatus = qcStatus;
	}
	public String getQcStatus() {
		return qcStatus;
	}
	
	public void setReasonFailDesc(String reasonFailDesc) {
		this.reasonFailDesc = reasonFailDesc;
	}
	public String getReasonFailDesc() {
		return reasonFailDesc;
	}
	public void setReasonCQDesc(String reasonCQDesc) {
		this.reasonCQDesc = reasonCQDesc;
	}
	public String getReasonCQDesc() {
		return reasonCQDesc;
	}
	public void setReasonFailCode(String reasonFailCode) {
		this.reasonFailCode = reasonFailCode;
	}
	public String getReasonFailCode() {
		return reasonFailCode;
	}
	public void setReasonCQCode(String reasonCQCode) {
		this.reasonCQCode = reasonCQCode;
	}
	public String getReasonCQCode() {
		return reasonCQCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setQcCallTime(String qcCallTime) {
		this.qcCallTime = qcCallTime;
	}
	public String getQcCallTime() {
		return qcCallTime;
	}
	public void setPreInstallInd(String preInstallInd) {
		this.preInstallInd = preInstallInd;
	}
	public String getPreInstallInd() {
		return preInstallInd;
	}
	
}
