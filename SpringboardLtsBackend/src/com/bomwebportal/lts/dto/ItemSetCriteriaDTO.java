package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.List;

public class ItemSetCriteriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6168059651437620523L;
	
	private String basketId;
	private String channelId;
	private String itemSetId;
	private String itemSetType;
	private String[] standaloneItemSetTypes;
	private List<String> eligibleItemTypeList;
	private String eligibleItemType;
	private String relatedItemId;
	
	private boolean selectDefault;
	private String displayType;
	private String locale; 
	
	private String orderType;
	private String toProd;
	private String fromProd;
	private String baseContractPeriod;
	private String tpExpiredMonths;
	private double profileArpu = 0.0; 
	private double newArpu = 0.0;
	private double additionalFee = 0.0;
	private String defaultInd;
	
	private String custCampaignCd; 
	 
	private String areaCode; 
	private String teamCode;
	
	private String applnDate; 
	
	private boolean isAtLeastHave1Premium;
	private Integer assignedQty;
	private String housingType;
	private String ltsHousingType;
	
	private String[] profilePsefCds;
	
	private String srvBoundary;
	private double srvTenure;
	private String eligibleDocType;
	private String osType;
	
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getItemSetType() {
		return itemSetType;
	}
	public void setItemSetType(String itemSetType) {
		this.itemSetType = itemSetType;
	}
	public String[] getStandaloneItemSetTypes() {
		return standaloneItemSetTypes;
	}
	public void setStandaloneItemSetTypes(String[] standaloneItemSetTypes) {
		this.standaloneItemSetTypes = standaloneItemSetTypes;
	}
	public List<String> getEligibleItemTypeList() {
		return eligibleItemTypeList;
	}
	public void setEligibleItemTypeList(List<String> eligibleItemTypeList) {
		this.eligibleItemTypeList = eligibleItemTypeList;
	}
	public String getEligibleItemType() {
		return eligibleItemType;
	}
	public void setEligibleItemType(String eligibleItemType) {
		this.eligibleItemType = eligibleItemType;
	}
	public boolean isSelectDefault() {
		return selectDefault;
	}
	public void setSelectDefault(boolean selectDefault) {
		this.selectDefault = selectDefault;
	}
	public String getDisplayType() {
		return displayType;
	}
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getToProd() {
		return toProd;
	}
	public void setToProd(String toProd) {
		this.toProd = toProd;
	}
	public String getBaseContractPeriod() {
		return baseContractPeriod;
	}
	public void setBaseContractPeriod(String baseContractPeriod) {
		this.baseContractPeriod = baseContractPeriod;
	}
	public String getTpExpiredMonths() {
		return tpExpiredMonths;
	}
	public void setTpExpiredMonths(String tpExpiredMonths) {
		this.tpExpiredMonths = tpExpiredMonths;
	}
	public double getProfileArpu() {
		return profileArpu;
	}
	public void setProfileArpu(double profileArpu) {
		this.profileArpu = profileArpu;
	}
	public double getNewArpu() {
		return newArpu;
	}
	public void setNewArpu(double newArpu) {
		this.newArpu = newArpu;
	}
	public double getAdditionalFee() {
		return additionalFee;
	}
	public void setAdditionalFee(double additionalFee) {
		this.additionalFee = additionalFee;
	}
	public String getCustCampaignCd() {
		return custCampaignCd;
	}
	public void setCustCampaignCd(String custCampaignCd) {
		this.custCampaignCd = custCampaignCd;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getApplnDate() {
		return applnDate;
	}
	public void setApplnDate(String applnDate) {
		this.applnDate = applnDate;
	}
	public boolean isAtLeastHave1Premium() {
		return isAtLeastHave1Premium;
	}
	public void setAtLeastHave1Premium(boolean isAtLeastHave1Premium) {
		this.isAtLeastHave1Premium = isAtLeastHave1Premium;
	}
	public Integer getAssignedQty() {
		return assignedQty;
	}
	public void setAssignedQty(Integer assignedQty) {
		this.assignedQty = assignedQty;
	}
	public String getItemSetId() {
		return itemSetId;
	}
	public void setItemSetId(String itemSetId) {
		this.itemSetId = itemSetId;
	}
	public String getRelatedItemId() {
		return relatedItemId;
	}
	public void setRelatedItemId(String relatedItemId) {
		this.relatedItemId = relatedItemId;
	}
	public String getDefaultInd() {
		return defaultInd;
	}
	public void setDefaultInd(String defaultInd) {
		this.defaultInd = defaultInd;
	}
	public String getHousingType() {
		return housingType;
	}
	public void setHousingType(String housingType) {
		this.housingType = housingType;
	}
	public String[] getProfilePsefCds() {
		return profilePsefCds;
	}
	public void setProfilePsefCds(String[] profilePsefCds) {
		this.profilePsefCds = profilePsefCds;
	}
	public String getFromProd() {
		return fromProd;
	}
	public void setFromProd(String fromProd) {
		this.fromProd = fromProd;
	}
	public String getSrvBoundary() {
		return srvBoundary;
	}
	public void setSrvBoundary(String srvBoundary) {
		this.srvBoundary = srvBoundary;
	}
	public double getSrvTenure() {
		return srvTenure;
	}
	public void setSrvTenure(double srvTenure) {
		this.srvTenure = srvTenure;
	}
	public String getEligibleDocType() {
		return eligibleDocType;
	}
	public void setEligibleDocType(String eligibleDocType) {
		this.eligibleDocType = eligibleDocType;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getLtsHousingType() {
		return ltsHousingType;
	}
	public void setLtsHousingType(String ltsHousingType) {
		this.ltsHousingType = ltsHousingType;
	}
	

}
