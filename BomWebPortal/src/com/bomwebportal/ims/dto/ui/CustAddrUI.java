package com.bomwebportal.ims.dto.ui;

import com.bomwebportal.ims.dto.CustAddrDTO;

public class CustAddrUI extends CustAddrDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8224114645621698178L;
	private String ActionInd;
	
	public String getLotNoStreetNo() {
		return LotNoStreetNo;
	}

	public void setLotNoStreetNo(String lotNoStreetNo) {
		LotNoStreetNo = lotNoStreetNo;
	}

	public String getStreetCatStreetName() {
		return StreetCatStreetName;
	}

	public void setStreetCatStreetName(String streetCatStreetName) {
		StreetCatStreetName = streetCatStreetName;
	}

	public String getStreetCatStreetNameStreetNo() {
		return StreetCatStreetNameStreetNo;
	}

	public void setStreetCatStreetNameStreetNo(String streetCatStreetNameStreetNo) {
		StreetCatStreetNameStreetNo = streetCatStreetNameStreetNo;
	}

	public String getBuildingNameStreetNoLotNo() {
		return BuildingNameStreetNoLotNo;
	}

	public void setBuildingNameStreetNoLotNo(String buildingNameStreetNoLotNo) {
		BuildingNameStreetNoLotNo = buildingNameStreetNoLotNo;
	}

	private String LotNoStreetNo;
	private String StreetCatStreetName;
	private String StreetCatStreetNameStreetNo;
	private String BuildingNameStreetNoLotNo;

	public String getActionInd() {
		return ActionInd;
	}

	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}
	
	
	
	

}
