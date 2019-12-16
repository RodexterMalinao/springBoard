package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LtsMiscellaneousFormDTO implements Serializable{

	private static final long serialVersionUID = 6502042760117825623L;
	
	private String applicationDate;
	private boolean recontract;
	private boolean dnChange;
	private boolean baCaChange;
	private boolean useDuplexNumAsNewEye;
	private boolean redeemPrevPremium;
	private boolean profilePremiumTp;
	private boolean changeExisting123TvOrMirror;
	private String backDate;
	private boolean allowBackDateOrder;
	private boolean backDateOrder;
	private boolean switchTp;
	private String maxBackDateDays;
	private boolean submitDisForm;
	private String waiveDisFormReason;
	private String disFormSerial;
	private boolean allowSubmitDisForm;
	private boolean barUpgradeEye;
	private String termPlanBarSlot;
	
	public LtsMiscellaneousFormDTO() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		applicationDate = dateFormat.format(new Date());
		redeemPrevPremium = true;
		profilePremiumTp = false;
		setChangeExisting123TvOrMirror(false);
	}

	public boolean isSubmitDisForm() {
		return submitDisForm;
	}

	public void setSubmitDisForm(boolean submitDisForm) {
		this.submitDisForm = submitDisForm;
	}

	public String getWaiveDisFormReason() {
		return waiveDisFormReason;
	}

	public void setWaiveDisFormReason(String waiveDisFormReason) {
		this.waiveDisFormReason = waiveDisFormReason;
	}

	public String getDisFormSerial() {
		return disFormSerial;
	}

	public void setDisFormSerial(String disFormSerial) {
		this.disFormSerial = disFormSerial;
	}

	public String getApplicationDate() {
		if (backDateOrder) {
			return backDate;
		}
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public boolean isRecontract() {
		return recontract;
	}
	public void setRecontract(boolean recontract) {
		this.recontract = recontract;
	}
	public boolean isDnChange() {
		return dnChange;
	}
	public void setDnChange(boolean dnChange) {
		this.dnChange = dnChange;
	}
	public boolean isBaCaChange() {
		return baCaChange;
	}
	public void setBaCaChange(boolean baCaChange) {
		this.baCaChange = baCaChange;
	}
	public boolean isUseDuplexNumAsNewEye() {
		return useDuplexNumAsNewEye;
	}
	public void setUseDuplexNumAsNewEye(boolean useDuplexNumAsNewEye) {
		this.useDuplexNumAsNewEye = useDuplexNumAsNewEye;
	}
	public boolean isRedeemPrevPremium() {
		return redeemPrevPremium;
	}
	public void setRedeemPrevPremium(boolean redeemPrevPremium) {
		this.redeemPrevPremium = redeemPrevPremium;
	}
	public boolean isProfilePremiumTp() {
		return profilePremiumTp;
	}
	public void setProfilePremiumTp(boolean profilePremiumTp) {
		this.profilePremiumTp = profilePremiumTp;
	}
	public boolean isChangeExisting123TvOrMirror() {
		return changeExisting123TvOrMirror;
	}
	public void setChangeExisting123TvOrMirror(boolean changeExisting123TvOrMirror) {
		this.changeExisting123TvOrMirror = changeExisting123TvOrMirror;
	}

	public String getBackDate() {
		return backDate;
	}

	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}

	public boolean isAllowBackDateOrder() {
		return allowBackDateOrder;
	}

	public void setAllowBackDateOrder(boolean allowBackDateOrder) {
		this.allowBackDateOrder = allowBackDateOrder;
	}

	public boolean isBackDateOrder() {
		return backDateOrder;
	}

	public void setBackDateOrder(boolean backDateOrder) {
		this.backDateOrder = backDateOrder;
	}

	public boolean isSwitchTp() {
		return switchTp;
	}

	public void setSwitchTp(boolean switchTp) {
		this.switchTp = switchTp;
	}

	public String getMaxBackDateDays() {
		return maxBackDateDays;
	}

	public void setMaxBackDateDays(String maxBackDateDays) {
		this.maxBackDateDays = maxBackDateDays;
	}

	public boolean isAllowSubmitDisForm() {
		return allowSubmitDisForm;
	}

	public void setAllowSubmitDisForm(boolean allowSubmitDisForm) {
		this.allowSubmitDisForm = allowSubmitDisForm;
	}

	public boolean isBarUpgradeEye() {
		return barUpgradeEye;
	}

	public void setBarUpgradeEye(boolean barUpgradeEye) {
		this.barUpgradeEye = barUpgradeEye;
	}

	public String getTermPlanBarSlot() {
		return termPlanBarSlot;
	}

	public void setTermPlanBarSlot(String termPlanBarSlot) {
		this.termPlanBarSlot = termPlanBarSlot;
	}
	
	
}
