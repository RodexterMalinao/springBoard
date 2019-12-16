package com.pccw.rpt.schema.dto.slv.salesAgreement;

import java.util.ArrayList;

import com.pccw.rpt.schema.dto.slv.salesAgreement.SectionERptDTO.FixedCharge;

public class SectionDRptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -797803605447128607L;
	private ArrayList<FixedCharge> slvPremiumList = new ArrayList<FixedCharge>();

	public void addSlvPremiumItem(String pDescription, String pOrgValue) {
		this.slvPremiumList.add(new FixedCharge(pDescription, pOrgValue));
	}

	public boolean isSlvPremiumListEmpty() {
		return this.slvPremiumList.isEmpty();
	}
	
	public ArrayList<FixedCharge> getSlvPremiumList() {
		return this.slvPremiumList;
	}
}