package com.bomwebportal.mob.ccs.dto.ui;


import java.io.Serializable;
import java.util.List;

public class SpecialMRTReserveUI implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3234275405400926729L;
	private List<SpecialMRTReserveUI> reserveSpecialMRTList;
//	private List<SpecialMRTReserveUI> rejectedSpecialMRTList;	
	private boolean twoRequestsAlready;
	private String validDateTill;
	private String requestId;
	private String requestDate;
	private String firstName;
	private String lastName;
	private String msisdnPattern;
	private String approvalResult;
	private String msisdn;
	private String msisdnlvl;
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMsisdnPattern() {
		return msisdnPattern;
	}
	public void setMsisdnPattern(String msisdnPattern) {
		this.msisdnPattern = msisdnPattern;
	}
	public String getApprovalResult() {
		return approvalResult;
	}
	public void setApprovalResult(String approvalResult) {
		this.approvalResult = approvalResult;
	}
	public boolean isTwoRequestsAlready() {
		return twoRequestsAlready;
	}
	public void setTwoRequestsAlready(boolean twoRequestsAlready) {
		this.twoRequestsAlready = twoRequestsAlready;
	}
	public List<SpecialMRTReserveUI> getReserveSpecialMRTList() {
		return reserveSpecialMRTList;
	}
	public void setReserveSpecialMRTList(
			List<SpecialMRTReserveUI> reserveSpecialMRTList) {
		this.reserveSpecialMRTList = reserveSpecialMRTList;
	}
	public String getValidDateTill() {
		return validDateTill;
	}
	public void setValidDateTill(String validDateTill) {
		this.validDateTill = validDateTill;
	}
	/*public List<SpecialMRTReserveUI> getRejectedSpecialMRTList() {
		return rejectedSpecialMRTList;
	}
	public void setRejectedSpecialMRTList(
			List<SpecialMRTReserveUI> rejectedSpecialMRTList) {
		this.rejectedSpecialMRTList = rejectedSpecialMRTList;
	}*/
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getMsisdnlvl() {
		return msisdnlvl;
	}
	public void setMsisdnlvl(String msisdnlvl) {
		this.msisdnlvl = msisdnlvl;
	}
	
	
	

}
