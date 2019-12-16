package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class ImsAddrHelperDTO implements Serializable {

	private String serviceBoundaryNum;
	
	private String lotHouseInd;
	
	private String houseLotNum;
	
	private String lotNum;
	
	private String buildingName;
	
	private String streetName;
	
	private String streetCatgDesc;
	
	private String streetCatgCode;
	
	private String sectionDesc;
	
	private String sectionCode;
	
	private String districtDesc;
	
	private String districtCode;;
	
	private String areaDesc;
	
	private String areaCode;
	
	private String lob;
	
	private String flat;
	
	private String floor;
	
	private String address;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String pAreaCode) {
		this.areaCode = pAreaCode;
	}

	public String getAreaDesc() {
		return this.areaDesc;
	}

	public void setAreaDesc(String pAreaName) {
		this.areaDesc = pAreaName;
	}

	public String getBuildingName() {
		return this.buildingName;
	}

	public void setBuildingName(String pBuildingName) {
		this.buildingName = pBuildingName;
	}

	public String getDistrictCode() {
		return this.districtCode;
	}

	public void setDistrictCode(String pDistrictCode) {
		this.districtCode = pDistrictCode;
	}

	public String getDistrictDesc() {
		return this.districtDesc;
	}

	public void setDistrictDesc(String pDistrictDesc) {
		this.districtDesc = pDistrictDesc;
	}

	public String getHouseLotNum() {
		return this.houseLotNum;
	}

	public void setHouseLotNum(String pHouseLotNum) {
		this.houseLotNum = pHouseLotNum;
		if (pHouseLotNum != null && !"".equals(pHouseLotNum.trim())) {
			this.lotHouseInd = "S";
			this.lotNum = null;
		}
	}

	public String getLotHouseInd() {
		return this.lotHouseInd;
	}

	public void setLotHouseInd(String pLotHouseInd) {
		this.lotHouseInd = pLotHouseInd;
	}

	public String getLotNum() {
		return this.lotNum;
	}

	public void setLotNum(String pLotNum) {
		this.lotNum = pLotNum;
		if (pLotNum != null && !"".equals(pLotNum.trim())) {
			this.lotHouseInd = "L";
			this.houseLotNum = null;
		}
	}

	public String getSectionCode() {
		return this.sectionCode;
	}

	public void setSectionCode(String pSectionCode) {
		this.sectionCode = pSectionCode;
	}

	public String getSectionDesc() {
		return this.sectionDesc;
	}

	public void setSectionDesc(String pSectionDesc) {
		this.sectionDesc = pSectionDesc;
	}

	public String getServiceBoundaryNum() {
		return this.serviceBoundaryNum;
	}

	public void setServiceBoundaryNum(String pServiceBoundaryNum) {
		this.serviceBoundaryNum = pServiceBoundaryNum;
	}

	public String getStreetCatgCode() {
		return this.streetCatgCode;
	}

	public void setStreetCatgCode(String pStreetCatgCode) {
		this.streetCatgCode = pStreetCatgCode;
	}

	public String getStreetCatgDesc() {
		return this.streetCatgDesc;
	}

	public void setStreetCatgDesc(String pStreetCatgDesc) {
		this.streetCatgDesc = pStreetCatgDesc;
	}

	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String pStreetName) {
		this.streetName = pStreetName;
	}
	
	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getSingleLineAddress() {
		StringBuffer sb = new StringBuffer();

		if (this.flat != null && !"".equals(this.flat.trim())) {
			sb.append("Flat " + this.flat);
			sb.append(" ");
		}
		
		if (this.floor != null && !"".equals(this.floor.trim())) {
			if (this.floor.trim().endsWith("/F")) {
				sb.append(this.floor);
				sb.append(" ");
			} else {
				sb.append(this.floor + "/F");
				sb.append(" ");
			}
		}
		
		if (this.buildingName != null && !"".equals(this.buildingName.trim())) {
			sb.append(this.buildingName);
			sb.append(" ");
		}
		
		if (this.lotNum != null && !"".equals(this.lotNum.trim())) {
			sb.append(this.lotNum);
			sb.append(" ");
		} else if (this.houseLotNum != null && !"".equals(this.houseLotNum.trim())) {
			sb.append(this.houseLotNum);
			sb.append(" ");
		}

		if (this.streetName != null && !"".equals(this.streetName.trim())) {
			sb.append(this.streetName);
			sb.append(" ");
			sb.append(this.streetCatgDesc);
			sb.append(" ");
		}
		
		if (this.sectionDesc != null && !"".equals(this.sectionDesc.trim())) {
			sb.append(this.sectionDesc);
			sb.append(" ");
		}
		
		if (this.districtDesc != null && !"".equals(this.districtDesc.trim())) {
			sb.append(this.districtDesc);
			sb.append(" ");
		}

		if (this.areaDesc != null && !"".equals(this.areaDesc.trim())) {
			sb.append(this.areaDesc);
			sb.append(" ");
		}

		return sb.toString();
	}
}