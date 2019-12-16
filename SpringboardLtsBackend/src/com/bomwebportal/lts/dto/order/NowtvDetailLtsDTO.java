package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class NowtvDetailLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -9140843335473013933L;

	private String viewAvInd = null;
	private String dob = null;
	private String emailBillInd = null;
	private String emailAddress = null;
	private String printBillInd = null;
	private String receiveNowtvBillInd = null;
	private String billMedia = null;
	

	public String getViewAvInd() {
		return viewAvInd;
	}

	public void setViewAvInd(String viewAvInd) {
		this.viewAvInd = viewAvInd;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getEmailBillInd() {
		return emailBillInd;
	}

	public void setEmailBillInd(String emailBillInd) {
		this.emailBillInd = emailBillInd;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPrintBillInd() {
		return printBillInd;
	}

	public void setPrintBillInd(String printBillInd) {
		this.printBillInd = printBillInd;
	}

	public String getReceiveNowtvBillInd() {
		return receiveNowtvBillInd;
	}

	public void setReceiveNowtvBillInd(String receiveNowtvBillInd) {
		this.receiveNowtvBillInd = receiveNowtvBillInd;
	}

	public String getBillMedia() {
		return this.billMedia;
	}

	public void setBillMedia(String pBillMedia) {
		this.billMedia = pBillMedia;
	}
}
