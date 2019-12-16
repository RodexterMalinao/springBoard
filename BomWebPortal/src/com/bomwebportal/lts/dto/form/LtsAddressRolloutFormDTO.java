package com.bomwebportal.lts.dto.form;

import java.io.Serializable;

import com.bomwebportal.lts.util.LtsConstant;


public class LtsAddressRolloutFormDTO implements Serializable {

	private static final long serialVersionUID = 3194229097612934425L;

	private boolean externalRelocate;
	private boolean baCaRemainUnchange;

	private String quickSearch;
	private String flat;
	private String floor;
	private String serviceBoundary;
	private String lotHouseInd;
	private String streetNum; 
	private String lotNum;
	private String buildingName;
	private String streetName;
	private String streetCatgDesc;
	private String streetCatgCode;
	private String sectionDesc;
	private String sectionCode;
	private String districtDesc;
	private String districtCode;
	private String areaDesc;
	private String areaCode;
	
	private boolean ponSbRequired;
	private String ponSbNum;
	
	//change ba
	private boolean instantUpdateBa;
	private BaCaActionType baCaAction;
	private String baQuickSearch;
	private String billingAddress;
	
	//Max.r.mewng
	private boolean dnchange;
	
	
	public enum BaCaActionType{
		SAME_AS_NEW_IA(LtsConstant.BILL_ADDR_ACTION_IA), 
		REMAIN_UNCHANGE(LtsConstant.BILL_ADDR_ACTION_EXISTING), 
		DIFFERENT_FROM_NEW_OLD_IA(LtsConstant.BILLING_ADDR_ACTION_NEW);
		private String code = null;
		BaCaActionType(String code){
			this.code = code;
		}
		public String getCode(){
			return this.code;
		}
	}
	
	
	public boolean isExternalRelocate() {
		return externalRelocate;
	}
	public void setExternalRelocate(boolean externalRelocate) {
		this.externalRelocate = externalRelocate;
	}
	public boolean isBaCaRemainUnchange() {
		return baCaRemainUnchange;
	}
	public void setBaCaRemainUnchange(boolean baCaRemainUnchange) {
		this.baCaRemainUnchange = baCaRemainUnchange;
	}
	public String getQuickSearch() {
		return quickSearch;
	}
	public void setQuickSearch(String quickSearch) {
		this.quickSearch = quickSearch;
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
	public String getServiceBoundary() {
		return serviceBoundary;
	}
	public void setServiceBoundary(String serviceBoundary) {
		this.serviceBoundary = serviceBoundary;
	}
	public String getLotHouseInd() {
		return lotHouseInd;
	}
	public void setLotHouseInd(String lotHouseInd) {
		this.lotHouseInd = lotHouseInd;
	}
	public String getStreetNum() {
		return streetNum;
	}
	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}
	public String getLotNum() {
		return lotNum;
	}
	public void setLotNum(String lotNum) {
		this.lotNum = lotNum;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getStreetCatgDesc() {
		return streetCatgDesc;
	}
	public void setStreetCatgDesc(String streetCatgDesc) {
		this.streetCatgDesc = streetCatgDesc;
	}
	public String getStreetCatgCode() {
		return streetCatgCode;
	}
	public void setStreetCatgCode(String streetCatgCode) {
		this.streetCatgCode = streetCatgCode;
	}
	public String getSectionDesc() {
		return sectionDesc;
	}
	public void setSectionDesc(String sectionDesc) {
		this.sectionDesc = sectionDesc;
	}
	public String getSectionCode() {
		return sectionCode;
	}
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	public String getDistrictDesc() {
		return districtDesc;
	}
	public void setDistrictDesc(String districtDesc) {
		this.districtDesc = districtDesc;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public boolean isPonSbRequired() {
		return ponSbRequired;
	}
	public void setPonSbRequired(boolean ponSbRequired) {
		this.ponSbRequired = ponSbRequired;
	}
	public String getPonSbNum() {
		return ponSbNum;
	}
	public void setPonSbNum(String ponSbNum) {
		this.ponSbNum = ponSbNum;
	}
	public boolean isInstantUpdateBa() {
		return instantUpdateBa;
	}
	public void setInstantUpdateBa(boolean instantUpdateBa) {
		this.instantUpdateBa = instantUpdateBa;
	}
	public BaCaActionType getBaCaAction() {
		return baCaAction;
	}
	public void setBaCaAction(BaCaActionType baCaAction) {
		this.baCaAction = baCaAction;
	}
	public String getBaQuickSearch() {
		return baQuickSearch;
	}
	public void setBaQuickSearch(String baQuickSearch) {
		this.baQuickSearch = baQuickSearch;
	}
	public String getBillingAddress() {
		return billingAddress;
	}
	public boolean isDnchange() {
		return dnchange;
	}
	public void setDnchange(boolean dnchange) {
		this.dnchange = dnchange;
	}
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	
}
