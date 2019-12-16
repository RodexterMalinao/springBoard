package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class AmendmentModeDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5910596815761492285L;  

	private String orderId;
	private String cancelOrder;
	private String amendAppointment;
	private String updateCreditCard;
	private String changeLoginId;
	private String updateContactEmail;
	private String fsAmendment;
	private String changeSRD;	
	private String changeCommDate;
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setCancelOrder(String cancelOrder) {
		this.cancelOrder = cancelOrder;
	}
	public String getCancelOrder() {
		return cancelOrder;
	}
	public void setAmendAppointment(String amendAppointment) {
		this.amendAppointment = amendAppointment;
	}
	public String getAmendAppointment() {
		return amendAppointment;
	}
	public void setUpdateCreditCard(String updateCreditCard) {
		this.updateCreditCard = updateCreditCard;
	}
	public String getUpdateCreditCard() {
		return updateCreditCard;
	}
	public void setChangeLoginId(String changeLoginId) {
		this.changeLoginId = changeLoginId;
	}
	public String getChangeLoginId() {
		return changeLoginId;
	}
	public void setUpdateContactEmail(String updateContactEmail) {
		this.updateContactEmail = updateContactEmail;
	}
	public String getUpdateContactEmail() {
		return updateContactEmail;
	}
	public void setFsAmendment(String fsAmendment) {
		this.fsAmendment = fsAmendment;
	}
	public String getFsAmendment() {
		return fsAmendment;
	}
	public void setChangeSRD(String changeSRD) {
		this.changeSRD = changeSRD;
	}
	public String getChangeSRD() {
		return changeSRD;
	}
	public void setChangeCommDate(String changeCommDate) {
		this.changeCommDate = changeCommDate;
	}
	public String getChangeCommDate() {
		return changeCommDate;
	}
	

}
