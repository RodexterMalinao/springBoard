package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

import com.bomwebportal.lts.dto.AddressRolloutDTO;

public class AddressDetailProfileLtsDTO implements Serializable {

	private static final long serialVersionUID = -6858236908283287899L;

	private String addrId = null;
	private String unitNum = null;
	private String floorNum = null;
	private String buildName = null;
	private String sectDesc = null;
	private String hlotNum = null;
	private String streetNum = null;
	private String streetName = null;
	private String streetCat = null;
	private String district = null;
	private String area = null;
	private String districtCd = null;
	private String areaCd = null;
	private String streetCatCd = null;
	private String fullAddress = null;
	private String srvBdry = null;
	private String lotHseInd = null;
	private String ldlotNum = null;
	private String buildID = null;
	private String gridID = null;
	private String sectCd = null;
	
	private AddressRolloutDTO addressRollout = null;
	

	public String getAddrId() {
		return addrId;
	}

	public void setAddrId(String addrId) {
		this.addrId = addrId;
	}

	public String getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum;
	}

	public String getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(String floorNum) {
		this.floorNum = floorNum;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getSectDesc() {
		return sectDesc;
	}

	public void setSectDesc(String sectDesc) {
		this.sectDesc = sectDesc;
	}

	public String getHlotNum() {
		return hlotNum;
	}

	public void setHlotNum(String hlotNum) {
		this.hlotNum = hlotNum;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetCat() {
		return streetCat;
	}

	public void setStreetCat(String streetCat) {
		this.streetCat = streetCat;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getSrvBdry() {
		return srvBdry;
	}

	public void setSrvBdry(String srvBdry) {
		this.srvBdry = srvBdry;
	}

	public String getDistrictCd() {
		return districtCd;
	}

	public void setDistrictCd(String districtCd) {
		this.districtCd = districtCd;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public String getLdlotNum() {
		return ldlotNum;
	}

	public void setLdlotNum(String ldlotNum) {
		this.ldlotNum = ldlotNum;
	}

	public String getBuildID() {
		return buildID;
	}

	public void setBuildID(String buildID) {
		this.buildID = buildID;
	}

	public String getGridID() {
		return gridID;
	}

	public void setGridID(String gridID) {
		this.gridID = gridID;
	}

	public String getLotHseInd() {
		return lotHseInd;
	}

	public void setLotHseInd(String lotHseInd) {
		this.lotHseInd = lotHseInd;
	}

	public String getSectCd() {
		return sectCd;
	}

	public void setSectCd(String sectCd) {
		this.sectCd = sectCd;
	}

	public AddressRolloutDTO getAddressRollout() {
		return addressRollout;
	}

	public void setAddressRollout(AddressRolloutDTO addressRollout) {
		this.addressRollout = addressRollout;
	}

	public String getStreetCatCd() {
		return streetCatCd;
	}

	public void setStreetCatCd(String streetCatCd) {
		this.streetCatCd = streetCatCd;
	}	
}
