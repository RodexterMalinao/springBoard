package com.pccw.rpt.schema.dto.slv.salesAgreement;

import java.util.ArrayList;

import com.pccw.rpt.schema.dto.slv.salesAgreement.SectionCRptDTO.ChargingItem;

public class SectionIRptDTO extends FixContentRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1988930517985355723L;

	private ArrayList<ChargingItem> slvSubscribedItemList = new ArrayList<ChargingItem>();
	
	private String rptDetailRemarks;

	public void addSlvSubscribedItemList(String pPackageName, String pDescription) {
		this.slvSubscribedItemList.add(new ChargingItem(pPackageName, pDescription, null, null, null));
	}

	public ArrayList<ChargingItem> getSlvSubscribedItemList() {
		return this.slvSubscribedItemList;
	}

	public void setSlvSubscribedItemList(
			ArrayList<ChargingItem> pSlvSubscribedItemList) {
		this.slvSubscribedItemList = pSlvSubscribedItemList;
	}
	
	public boolean isSlvSubscribedItemListEmpty() {
		return this.slvSubscribedItemList.isEmpty();
	}

	public String getRptDetailRemarks() {
		return this.rptDetailRemarks;
	}

	public void setRptDetailRemarks(String pRptDetailRemarks) {
		this.rptDetailRemarks = pRptDetailRemarks;
	}
}
