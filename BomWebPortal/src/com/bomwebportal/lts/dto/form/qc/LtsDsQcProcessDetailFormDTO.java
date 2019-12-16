package com.bomwebportal.lts.dto.form.qc;

import java.io.Serializable;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class LtsDsQcProcessDetailFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3482520076519100564L;
	
	public final static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
	
	private String orderId;
	private String lob;
	private String assignDate;
	private String assignee;
	private String qcDateTime;
	private String qcCallTime;
	private String qcFindingDetail;
	private String orderDistrict;
	private String orderPlace;
	private boolean mustQc;
	private String sbStatus;
	private String qcStatus;
	
	//QC Action
	private QcProcessAction action;
	private String qcCannotQcReason;
	private String qcFailedReason;
	
	public enum QcProcessAction {
		PASS("Q01"),  PASS_APPT("Q01"), APPT("Q01"), FAILED("Q02"), CANNOT_QC("Q03"), RNA("Q04");
		private String code = null;
		QcProcessAction(String code){
			this.code = code;
		}
		public String getCode(){
			return this.code;
		}
	};

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getQcCallTime() {
		return qcCallTime;
	}

	public void setQcCallTime(String qcCallTime) {
		this.qcCallTime = qcCallTime;
	}

	public String getQcFindingDetail() {
		return qcFindingDetail;
	}

	public void setQcFindingDetail(String qcFindingDetail) {
		this.qcFindingDetail = qcFindingDetail;
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

	public boolean isMustQc() {
		return mustQc;
	}

	public void setMustQc(boolean mustQc) {
		this.mustQc = mustQc;
	}

	public QcProcessAction getAction() {
		return action;
	}

	public void setAction(QcProcessAction action) {
		this.action = action;
	}

	public String getQcCannotQcReason() {
		return qcCannotQcReason;
	}

	public void setQcCannotQcReason(String qcCannotQcReason) {
		this.qcCannotQcReason = qcCannotQcReason;
	}

	public String getQcFailedReason() {
		return qcFailedReason;
	}

	public void setQcFailedReason(String qcFailedReason) {
		this.qcFailedReason = qcFailedReason;
	}

	public String getSbStatus() {
		return sbStatus;
	}

	public void setSbStatus(String sbStatus) {
		this.sbStatus = sbStatus;
	}

	public String getQcStatus() {
		return qcStatus;
	}

	public void setQcStatus(String qcStatus) {
		this.qcStatus = qcStatus;
	};
	
	
}
