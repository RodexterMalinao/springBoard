package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class ImsCustomerOrderHistoryDTO implements Serializable {

	/**
	 * for customer information section 3 use
	 */
	private static final long serialVersionUID = 6539936023999462672L;
	//steven added 20130628 start
	private String suspendReason;
	public void setSuspendReason(String suspendReason) {
		this.suspendReason = suspendReason;
	}

	public String getSuspendReason() {
		return suspendReason;
	}
	//steven added 20130628 end
	private String orderId;
	private String ocid;
	private String lob;
	private String serviceNum;
	private String imsLoginId;
	// add by Joyce 20111024
	private Date appDate;
	private String orderStatus;
	private String errMsg;
	private String bomStatus;
	private String idDocNum;
	private String todayOrderFlag;
	// add by Joyce 20111215
	private String orderHistCustName;
	private String outstandingWqUrl;
	// add by Joyce 20120803
	private String reasonCd;
	private String checkPoint;
	private String emailAddress;
	
	private String emailSent;
	
	public String getEmailSent() {
		return emailSent;
	}

	public void setEmailSent(String emailSent) {
		this.emailSent = emailSent;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOcid() {
		return ocid;
	}

	public void setOcid(String ocid) {
		this.ocid = ocid;
	}

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(String serviceNum) {
		this.serviceNum = serviceNum;
	}

	public String getImsLoginId() {
		return imsLoginId;
	}

	public void setImsLoginId(String imsLoginId) {
		this.imsLoginId = imsLoginId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getBomStatus() {
		return bomStatus;
	}

	public void setBomStatus(String bomStatus) {
		this.bomStatus = bomStatus;
	}

	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}

	public String getIdDocNum() {
		return idDocNum;
	}
	
	

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public String getTodayOrderFlag() {
		return todayOrderFlag;
	}

	public void setTodayOrderFlag(String todayOrderFlag) {
		this.todayOrderFlag = todayOrderFlag;
	}

	public void setOrderHistCustName(String custName) {
		this.orderHistCustName = custName;
	}

	public String getOrderHistCustName() {
		return orderHistCustName;
	}

	public void setOutstandingWqUrl(String outstandingWqUrl) {
		this.outstandingWqUrl = outstandingWqUrl;
	}

	public String getOutstandingWqUrl() {
		return outstandingWqUrl;
	}

	public String getReasonCd() {
		return reasonCd;
	}

	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}

	public String getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

}
