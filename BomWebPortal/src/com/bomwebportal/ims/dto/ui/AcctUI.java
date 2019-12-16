package com.bomwebportal.ims.dto.ui;

import com.bomwebportal.ims.dto.AcctDTO;

public class AcctUI extends AcctDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1004668401000796461L;
	private String ActionInd;
	
	private PaymentUI payment;

	public String getActionInd() {
		return ActionInd;
	}

	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}

	public PaymentUI getPayment() {
		return payment;
	}

	public void setPayment(PaymentUI payment) {
		this.payment = payment;
	}
	
	

}
