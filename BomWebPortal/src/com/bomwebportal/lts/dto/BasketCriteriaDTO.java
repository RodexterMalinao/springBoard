package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class BasketCriteriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 627635382340909365L;

	private String relatedSql;
	
	private String basketChannelId;
	private String basketType; // W_BASKET.TYPE e.g 1020, 1030
	private String basketCategory;
	private String deviceType; // W_BASKET_DTL_LTS.DEVICE_TYPE e.g LG-7, ST4-7
	
	private String applicationDate;
	private String locale;
	 
	private boolean duplexProfile;
	private boolean nonNowTvCust;
	private String extendContractMth;
	private String iddFreeMin;
	private String paralleExtension;
	private String bundle2G;

	private String hktPremier;
	private String role;
	private String teamCode; 
	private String areaCode;
	
	private boolean hotBasketInd;
	private boolean contractPeriodGt24;
	private boolean contractPeriodEq21; 
	private boolean contractPeriodEq18; 
	private boolean contractPeriodEq15; 
	private boolean contractPeriodLt12; 
	
	private boolean submitDisForm;
	
	private String[] filterProjectCds; 
	private String[] defaultProjectCds; 
	 
	private String[] profilePsefCds;
	private String fromProduct;

	private String custCampaignCd;
	private String[] basketCampaignCds;
	private String tpExpiredMonths;
	
	private String bundleTvCredit;
	private String rolloutHousingType;
	private String ltsHousingType;
	private String imsHousingType;
	
	private String profileArpu;
	
	private String srvBoundary;
	
	private String osType;
	
	public String getBasketChannelId() {
		return basketChannelId;
	}
	public void setBasketChannelId(String basketChannelId) {
		this.basketChannelId = basketChannelId;
	}
	public String getBasketType() {
		return basketType;
	}
	public void setBasketType(String basketType) {
		this.basketType = basketType;
	}
	public String getBasketCategory() {
		return basketCategory;
	}
	public void setBasketCategory(String basketCategory) {
		this.basketCategory = basketCategory;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public boolean isDuplexProfile() {
		return duplexProfile;
	}
	public void setDuplexProfile(boolean duplexProfile) {
		this.duplexProfile = duplexProfile;
	}
	public boolean isNonNowTvCust() {
		return nonNowTvCust;
	}
	public void setNonNowTvCust(boolean nonNowTvCust) {
		this.nonNowTvCust = nonNowTvCust;
	}
	public String getExtendContractMth() {
		return extendContractMth;
	}
	public void setExtendContractMth(String extendContractMth) {
		this.extendContractMth = extendContractMth;
	}
	public String getIddFreeMin() {
		return iddFreeMin;
	}
	public void setIddFreeMin(String iddFreeMin) {
		this.iddFreeMin = iddFreeMin;
	}
	public String getParalleExtension() {
		return paralleExtension;
	}
	public void setParalleExtension(String paralleExtension) {
		this.paralleExtension = paralleExtension;
	}
	public String getBundle2G() {
		return bundle2G;
	}
	public void setBundle2G(String bundle2g) {
		bundle2G = bundle2g;
	}
	public String getHktPremier() {
		return hktPremier;
	}
	public void setHktPremier(String hktPremier) {
		this.hktPremier = hktPremier;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public boolean isHotBasketInd() {
		return hotBasketInd;
	}
	public void setHotBasketInd(boolean hotBasketInd) {
		this.hotBasketInd = hotBasketInd;
	}
	public boolean isContractPeriodGt24() {
		return contractPeriodGt24;
	}
	public void setContractPeriodGt24(boolean contractPeriodGt24) {
		this.contractPeriodGt24 = contractPeriodGt24;
	}
	public boolean isContractPeriodEq21() {
		return contractPeriodEq21;
	}
	public void setContractPeriodEq21(boolean contractPeriodEq21) {
		this.contractPeriodEq21 = contractPeriodEq21;
	}
	public boolean isContractPeriodEq18() {
		return contractPeriodEq18;
	}
	public void setContractPeriodEq18(boolean contractPeriodEq18) {
		this.contractPeriodEq18 = contractPeriodEq18;
	}
	public boolean isContractPeriodEq15() {
		return contractPeriodEq15;
	}
	public void setContractPeriodEq15(boolean contractPeriodEq15) {
		this.contractPeriodEq15 = contractPeriodEq15;
	}
	public boolean isContractPeriodLt12() {
		return contractPeriodLt12;
	}
	public void setContractPeriodLt12(boolean contractPeriodLt12) {
		this.contractPeriodLt12 = contractPeriodLt12;
	}
	public boolean isSubmitDisForm() {
		return submitDisForm;
	}
	public void setSubmitDisForm(boolean submitDisForm) {
		this.submitDisForm = submitDisForm;
	}
	public String[] getFilterProjectCds() {
		return filterProjectCds;
	}
	public void setFilterProjectCds(String[] filterProjectCds) {
		this.filterProjectCds = filterProjectCds;
	}
	public String[] getDefaultProjectCds() {
		return defaultProjectCds;
	}
	public void setDefaultProjectCds(String[] defaultProjectCds) {
		this.defaultProjectCds = defaultProjectCds;
	}
	public String[] getProfilePsefCds() {
		return profilePsefCds;
	}
	public void setProfilePsefCds(String[] profilePsefCds) {
		this.profilePsefCds = profilePsefCds;
	}
	public String getCustCampaignCd() {
		return custCampaignCd;
	}
	public void setCustCampaignCd(String custCampaignCd) {
		this.custCampaignCd = custCampaignCd;
	}
	public String[] getBasketCampaignCds() {
		return basketCampaignCds;
	}
	public void setBasketCampaignCds(String[] basketCampaignCds) {
		this.basketCampaignCds = basketCampaignCds;
	}
	public String getTpExpiredMonths() {
		return tpExpiredMonths;
	}
	public void setTpExpiredMonths(String tpExpiredMonths) {
		this.tpExpiredMonths = tpExpiredMonths;
	}
	public String getFromProduct() {
		return fromProduct;
	}
	public void setFromProduct(String fromProduct) {
		this.fromProduct = fromProduct;
	}
	public String getBundleTvCredit() {
		return bundleTvCredit;
	}
	public void setBundleTvCredit(String bundleTvCredit) {
		this.bundleTvCredit = bundleTvCredit;
	}
	public String getRolloutHousingType() {
		return rolloutHousingType;
	}
	public void setRolloutHousingType(String rolloutHousingType) {
		this.rolloutHousingType = rolloutHousingType;
	}
	public String getProfileArpu() {
		return profileArpu;
	}
	public void setProfileArpu(String profileArpu) {
		this.profileArpu = profileArpu;
	}
	public String getRelatedSql() {
		return relatedSql;
	}
	public void setRelatedSql(String relatedSql) {
		this.relatedSql = relatedSql;
	}
	public String getSrvBoundary() {
		return srvBoundary;
	}
	public void setSrvBoundary(String srvBoundary) {
		this.srvBoundary = srvBoundary;
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
	public String getImsHousingType() {
		return imsHousingType;
	}
	public void setImsHousingType(String imsHousingType) {
		this.imsHousingType = imsHousingType;
	}
	
	
}
