package com.pccw.rpt.schema.dto.slv.paymentNotice;

import java.io.Serializable;
import java.text.DecimalFormat;

public class PaymentNoticeDetailRptDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6784913940664151030L;

	private String profileId;
	private String basketDesc;
	private String chargeDesc;
	private double amount;
	private double dtlTotalAmount;
	private double adjustment;
	private String profileIdTitle;

	public PaymentNoticeDetailRptDTO(String pProfileIdTitle, String pProfileId, String pBasketDesc,
			String pChargeDesc, double pAmount, double pDtlTotalAmount) {
		this.profileId = pProfileId;
		this.basketDesc = pBasketDesc;
		this.chargeDesc = pChargeDesc;
		this.amount = pAmount;
		this.dtlTotalAmount = pDtlTotalAmount;
		this.profileIdTitle = pProfileIdTitle;
	}

	public String getProfileId() {
		return this.profileId;
	}

	public void setProfileId(String pProfileId) {
		this.profileId = pProfileId;
	}

	public String getBasketDesc() {
		return this.basketDesc;
	}

	public void setBasketDesc(String pBasketDesc) {
		this.basketDesc = pBasketDesc;
	}

	public String getChargeDesc() {
		return this.chargeDesc;
	}

	public void setChargeDesc(String pChargeDesc) {
		this.chargeDesc = pChargeDesc;
	}

	public String getAmount() {
		DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
		return String.valueOf(formatter.format(this.amount));
	}

	public void setAmount(double pAmount) {
		this.amount = pAmount;
	}

	public String getDtlTotalAmount() {
		DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
		return String.valueOf(formatter.format(this.dtlTotalAmount));
	}

	public void setDtlTotalAmount(double pDtlTotalAmount) {
		this.dtlTotalAmount = pDtlTotalAmount;
	}

	public String getAdjustment() {
		if (this.adjustment==0) {
			return "";
		}
		DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
		return String.valueOf(formatter.format(this.adjustment));
	}

	public void setAdjustment(double pAdjustment) {
		this.adjustment = pAdjustment;
	}

	public String getProfileIdTitle() {
		return this.profileIdTitle;
	}

	public void setProfileIdTitle(String pProfileIdTitle) {
		this.profileIdTitle = pProfileIdTitle;
	}
}
