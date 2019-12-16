package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.srvAccess.TechnologyDTO;
import com.bomwebportal.lts.dto.srvAccess.UimBlockageDTO;

public class AddressRolloutDTO implements Serializable, Cloneable{

	// Address Inventory
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2332212077091099474L;
	
	private String srvBdary;
	private String flat;
	private String floor;
	private String fullAddress;
	private String housingType;
	private boolean is2nBuilding;
	private String n2BuildingInd;
	private String pcdResourceShortage;
	private boolean ltsAddressBlacklist;
	private boolean imsAddressBlacklist;
	private String fieldWorkPermit;
	private boolean fttcInd;
	private String suggestedSrd;
	private String suggestedSrdReason;
	private boolean eyeCoverage;
	private boolean peCoverage;
	private String maximumBandwidth;
	private String maximumTechnology;
	private boolean fiberBlockageInd;
	private String ponSrvBdary;
	private boolean diffCustFsaExist;
	private boolean ponVilla;
	private String availableBandwidth;
	private String hktPremier;
	
	private String housingTypeCd;
	private String housingTypeDesc;
	
	private String ltsHousingType;
	private String ltsHousingCatCd;
	private String ltsHousingCatDesc;
	
	private TechnologyDTO[] technology;
	private UimBlockageDTO[] uimBlockage;
	private int numOfEyeProfile;
	

	public UimBlockageDTO[] getUimBlockage() {
		return uimBlockage;
	}
	public void setUimBlockage(UimBlockageDTO[] uimBlockage) {
		this.uimBlockage = uimBlockage;
	}
	public TechnologyDTO[] getTechnology() {
		return technology;
	}
	public void setTechnology(TechnologyDTO[] technology) {
		this.technology = technology;
	}
	public String getSrvBdary() {
		return srvBdary;
	}
	public void setSrvBdary(String srvBdary) {
		this.srvBdary = srvBdary;
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
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public String getHousingType() {
		return housingType;
	}
	public void setHousingType(String housingType) {
		this.housingType = housingType;
	}
	public boolean isIs2nBuilding() {
		return is2nBuilding;
	}
	public void setIs2nBuilding(boolean is2nBuilding) {
		this.is2nBuilding = is2nBuilding;
	}
	public String getPcdResourceShortage() {
		return pcdResourceShortage;
	}
	public void setPcdResourceShortage(String pcdResourceShortage) {
		this.pcdResourceShortage = pcdResourceShortage;
	}
	public boolean isLtsAddressBlacklist() {
		return ltsAddressBlacklist;
	}
	public void setLtsAddressBlacklist(boolean ltsAddressBlacklist) {
		this.ltsAddressBlacklist = ltsAddressBlacklist;
	}
	public boolean isImsAddressBlacklist() {
		return imsAddressBlacklist;
	}
	public void setImsAddressBlacklist(boolean imsAddressBlacklist) {
		this.imsAddressBlacklist = imsAddressBlacklist;
	}
	public String getFieldWorkPermit() {
		return fieldWorkPermit;
	}
	public void setFieldWorkPermit(String fieldWorkPermit) {
		this.fieldWorkPermit = fieldWorkPermit;
	}
	public boolean isFttcInd() {
		return fttcInd;
	}
	public void setFttcInd(boolean fttcInd) {
		this.fttcInd = fttcInd;
	}
	public String getSuggestedSrd() {
		return suggestedSrd;
	}
	public void setSuggestedSrd(String suggestedSrd) {
		this.suggestedSrd = suggestedSrd;
	}
	public String getSuggestedSrdReason() {
		return suggestedSrdReason;
	}
	public void setSuggestedSrdReason(String suggestedSrdReason) {
		this.suggestedSrdReason = suggestedSrdReason;
	}
	public boolean isEyeCoverage() {
		return eyeCoverage;
	}
	public void setEyeCoverage(boolean eyeCoverage) {
		this.eyeCoverage = eyeCoverage;
	}
	public boolean isPeCoverage() {
		return peCoverage;
	}
	public void setPeCoverage(boolean peCoverage) {
		this.peCoverage = peCoverage;
	}
	public String getMaximumBandwidth() {
		return maximumBandwidth;
	}
	public void setMaximumBandwidth(String maximumBandwidth) {
		this.maximumBandwidth = maximumBandwidth;
	}
	public boolean isFiberBlockageInd() {
		return fiberBlockageInd;
	}
	public void setFiberBlockageInd(boolean fiberBlockageInd) {
		this.fiberBlockageInd = fiberBlockageInd;
	}
	public String getPonSrvBdary() {
		return ponSrvBdary;
	}
	public void setPonSrvBdary(String ponSrvBdary) {
		this.ponSrvBdary = ponSrvBdary;
	}
	public boolean isDiffCustFsaExist() {
		return diffCustFsaExist;
	}
	public void setDiffCustFsaExist(boolean diffCustFsaExist) {
		this.diffCustFsaExist = diffCustFsaExist;
	}
	public String getMaximumTechnology() {
		return maximumTechnology;
	}
	public void setMaximumTechnology(String maximumTechnology) {
		this.maximumTechnology = maximumTechnology;
	}
	public boolean isPonVilla() {
		return ponVilla;
	}
	public void setPonVilla(boolean ponVilla) {
		this.ponVilla = ponVilla;
	}
	public String getAvailableBandwidth() {
		return availableBandwidth;
	}
	public void setAvailableBandwidth(String availableBandwidth) {
		this.availableBandwidth = availableBandwidth;
	}
	public String getN2BuildingInd() {
		return n2BuildingInd;
	}
	public void setN2BuildingInd(String n2BuildingInd) {
		this.n2BuildingInd = n2BuildingInd;
	}
	public String getHktPremier() {
		return hktPremier;
	}
	public void setHktPremier(String hktPremier) {
		this.hktPremier = hktPremier;
	}
	public String getHousingTypeCd() {
		return housingTypeCd;
	}
	public void setHousingTypeCd(String housingTypeCd) {
		this.housingTypeCd = housingTypeCd;
	}
	public String getHousingTypeDesc() {
		return housingTypeDesc;
	}
	public void setHousingTypeDesc(String housingTypeDesc) {
		this.housingTypeDesc = housingTypeDesc;
	}
	public int getNumOfEyeProfile() {
		return numOfEyeProfile;
	}
	public void setNumOfEyeProfile(int numOfEyeProfile) {
		this.numOfEyeProfile = numOfEyeProfile;
	}
	public String getLtsHousingType() {
		return ltsHousingType;
	}
	public void setLtsHousingType(String ltsHousingType) {
		this.ltsHousingType = ltsHousingType;
	}
	public String getLtsHousingCatCd() {
		return ltsHousingCatCd;
	}
	public void setLtsHousingCatCd(String ltsHousingCatCd) {
		this.ltsHousingCatCd = ltsHousingCatCd;
	}
	public String getLtsHousingCatDesc() {
		return ltsHousingCatDesc;
	}
	public void setLtsHousingCatDesc(String ltsHousingCatDesc) {
		this.ltsHousingCatDesc = ltsHousingCatDesc;
	}
}
