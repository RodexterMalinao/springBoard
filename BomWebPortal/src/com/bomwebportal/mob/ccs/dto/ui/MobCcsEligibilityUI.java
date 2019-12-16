package com.bomwebportal.mob.ccs.dto.ui;

import com.bomwebportal.mob.ccs.dto.MobCcsEligibilityDTO.IdDocType;

public class MobCcsEligibilityUI {
	public IdDocType getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(IdDocType idDocType) {
		this.idDocType = idDocType;
	}
	public String getIdDocNum() {
		return idDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	public String getEligibility() {
		return eligibility;
	}
	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}
	private IdDocType idDocType;
	private String idDocNum;
	private String eligibility;
}
