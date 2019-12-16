package com.bomwebportal.lts.dto.form.acq;

import java.util.List;

import com.bomwebportal.lts.dto.acq.LtsAcqNumberSelectionInfoDTO;

public class LtsAcqNumConfirmationFormDTO {
	
	private List<LtsAcqNumberSelectionInfoDTO> reservedDnList;
	private String newDn;
	private String pipbDn;
	private String duplexDn;
	private String sessionId;
	private String suspendReason;
	private FormAction formAction;
	private String suspendRemarks;
	
	public static enum FormAction {
		SUBMIT, SUSPEND;
	}

	/**
	 * @return the reservedDnList
	 */
	public List<LtsAcqNumberSelectionInfoDTO> getReservedDnList() {
		return reservedDnList;
	}

	/**
	 * @param reservedDnList the reservedDnList to set
	 */
	public void setReservedDnList(List<LtsAcqNumberSelectionInfoDTO> reservedDnList) {
		this.reservedDnList = reservedDnList;
	}

	/**
	 * @return the newDn
	 */
	public String getNewDn() {
		return newDn;
	}

	/**
	 * @param newDn the newDn to set
	 */
	public void setNewDn(String newDn) {
		this.newDn = newDn;
	}

	/**
	 * @return the pipbDn
	 */
	public String getPipbDn() {
		return pipbDn;
	}

	/**
	 * @param pipbDn the pipbDn to set
	 */
	public void setPipbDn(String pipbDn) {
		this.pipbDn = pipbDn;
	}
	
	/**
	 * @return the duplexDn
	 */
	public String getDuplexDn() {
		return duplexDn;
	}

	/**
	 * @param duplexDn the duplexDn to set
	 */
	public void setDuplexDn(String duplexDn) {
		this.duplexDn = duplexDn;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * @return the suspendReason
	 */
	public String getSuspendReason() {
		return suspendReason;
	}

	/**
	 * @param suspendReason the suspendReason to set
	 */
	public void setSuspendReason(String suspendReason) {
		this.suspendReason = suspendReason;
	}

	/**
	 * @return the formAction
	 */
	public FormAction getFormAction() {
		return formAction;
	}

	/**
	 * @param formAction the formAction to set
	 */
	public void setFormAction(FormAction formAction) {
		this.formAction = formAction;
	}

	public String getSuspendRemarks() {
		return suspendRemarks;
	}

	public void setSuspendRemarks(String suspendRemarks) {
		this.suspendRemarks = suspendRemarks;
	}
	

}
