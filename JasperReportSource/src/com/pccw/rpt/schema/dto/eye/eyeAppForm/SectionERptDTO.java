package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import java.util.ArrayList;

import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionFRptDTO.FixedCharge;

public class SectionERptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9082946085983480808L;
	private ArrayList<FixedCharge> eyePremiumList = new ArrayList<FixedCharge>();
	private ArrayList<FixedCharge> resTelPremiumList = new ArrayList<FixedCharge>();

	public void addEyePremiumItem(String pDescription, String pOrgValue) {
		this.eyePremiumList.add(new FixedCharge(this.replaceEyeDeviceKeyword(pDescription), pOrgValue));
	}

	public boolean isEyePremiumListEmpty() {
		return this.eyePremiumList.isEmpty();
	}
	
	public ArrayList<FixedCharge> getEyePremiumList() {
		return this.eyePremiumList;
	}
	
	public void addResTelPremiumItem(String pDescription, String pOrgValue) {
		this.resTelPremiumList.add(new FixedCharge(pDescription, pOrgValue));
	}

	public boolean isResTelPremiumListEmpty() {
		return this.resTelPremiumList.isEmpty();
	}
	
	public ArrayList<FixedCharge> getResTelPremiumList() {
		return this.resTelPremiumList;
	}
}