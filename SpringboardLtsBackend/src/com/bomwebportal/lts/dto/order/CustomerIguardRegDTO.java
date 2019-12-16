package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import java.util.Date;

public class CustomerIguardRegDTO extends ObjectActionBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4215525939126159142L;

	private String orderId = null;               
	private String carecashInd = null;    
	// Y - customer want to enroll
	// N - customer dont want to enroll
	// A - customer already enrolled
	// X - customer not applicable to enroll
	// D - customer to decide later

	private String carecashDmInd = null;     
	// Y - DM  opt out
	// N - DM opt in

	private String emailAddr = null;     
	private String contactNum = null;
//	private Date lastUpdDate = null;  
	private String status = null;    
	// S - Success
	// F - Fail
	// N - No registration is required

	private String rtnMsg = null;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCarecashInd() {
		return carecashInd;
	}
	public void setCarecashInd(String carecashInd) {
		this.carecashInd = carecashInd;
	}
	public String getCarecashDmInd() {
		return carecashDmInd;
	}
	public void setCarecashDmInd(String carecashDmInd) {
		this.carecashDmInd = carecashDmInd;
	}
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	public String getContactNum() {
		return contactNum;
	}
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
//	public Date getLastUpdDate() {
//		return lastUpdDate;
//	}
//	public void setLastUpdDate(Date lastUpdDate) {
//		this.lastUpdDate = lastUpdDate;
//	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRtnMsg() {
		return rtnMsg;
	}
	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}      

}
