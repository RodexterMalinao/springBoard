package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class BasketDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3108156114559188397L;
	
	private String basketId;
	private String type;
	private String locale;
	private String displayType;
	private String desc;
	private String html;
	private String imagePath;
	private String iddFreeMin;
	private String peInd;
	private String contractPeriod;
	private String baseBasketId;
	private String extendContractPeriod;
	private String recurrentAmt;
	private String recurrentAmtTxt;
	private String mthToMthAmt;
	private String mthToMthAmtTxt;
	private String baseBasketDesc;
	private String url;
	private String basketCatg;
	private String custCampaignCd;
	private String deviceType;
	private String bundle2G;
	private String bridgingMth;
	private String basketChannelId;
	private String duplexInd;
	private String staffPlan;
	private String staffNumber;
	private boolean is2NCoverage;
	private boolean isPrepay;
	private String payMtdTypes; 
	private String effectivePrice;
	private String bundleTvCredit;
	private String projectCd;
	private String housingType;
	private String premierInd;
	private String prevSerTermMth;
	private String pcdBundleFreeType;
	private String hotBasketInd;
	private String preinstallCheck;
	private String dnOption;
	private String osType;
	private String programCd;
	
	public String getRecurrentAmtTxt() {
		return recurrentAmtTxt;
	}
	public void setRecurrentAmtTxt(String recurrentAmtTxt) {
		this.recurrentAmtTxt = recurrentAmtTxt;
	}
	public String getMthToMthAmt() {
		return mthToMthAmt;
	}
	public void setMthToMthAmt(String mthToMthAmt) {
		this.mthToMthAmt = mthToMthAmt;
	}
	public String getMthToMthAmtTxt() {
		return mthToMthAmtTxt;
	}
	public void setMthToMthAmtTxt(String mthToMthAmtTxt) {
		this.mthToMthAmtTxt = mthToMthAmtTxt;
	}
	public String getRecurrentAmt() {
		return recurrentAmt;
	}
	public void setRecurrentAmt(String recurrentAmt) {
		this.recurrentAmt = recurrentAmt;
	}
	public String getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getIddFreeMin() {
		return iddFreeMin;
	}
	public void setIddFreeMin(String iddFreeMin) {
		this.iddFreeMin = iddFreeMin;
	}
	public String getPeInd() {
		return peInd;
	}
	public void setPeInd(String peInd) {
		this.peInd = peInd;
	}
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getDisplayType() {
		return displayType;
	}
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getBaseBasketId() {
		return baseBasketId;
	}
	public void setBaseBasketId(String baseBasketId) {
		this.baseBasketId = baseBasketId;
	}
	public String getExtendContractPeriod() {
		return extendContractPeriod;
	}
	public void setExtendContractPeriod(String extendContractPeriod) {
		this.extendContractPeriod = extendContractPeriod;
	}
	public String getActualContractPeriod() {
		
		int contractMonths = 0;
		int extendMonths = 0;
		
		try {
			contractMonths = Integer.parseInt(this.contractPeriod);
		} catch (NumberFormatException ex) {
			return null;
		}
		try {
			extendMonths = Integer.parseInt(this.extendContractPeriod);
		} catch (NumberFormatException ex) {}
		return String.valueOf(contractMonths - extendMonths);
	}
	public String getBaseBasketDesc() {
		return baseBasketDesc;
	}
	public void setBaseBasketDesc(String baseBasketDesc) {
		this.baseBasketDesc = baseBasketDesc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBasketCatg() {
		return basketCatg;
	}
	public void setBasketCatg(String basketCatg) {
		this.basketCatg = basketCatg;
	}
	public String getCustCampaignCd() {
		return custCampaignCd;
	}
	public void setCustCampaignCd(String custCampaignCd) {
		this.custCampaignCd = custCampaignCd;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getBundle2G() {
		return bundle2G;
	}
	public void setBundle2G(String bundle2g) {
		bundle2G = bundle2g;
	}
	
	public String getBasketChannelId() {
		return basketChannelId;
	}
	public void setBasketChannelId(String basketChannelId) {
		this.basketChannelId = basketChannelId;
	}
	public String getBridgingMth() {
		return bridgingMth;
	}
	public void setBridgingMth(String bridgingMth) {
		this.bridgingMth = bridgingMth;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDuplexInd() {
		return duplexInd;
	}
	public void setDuplexInd(String duplexInd) {
		this.duplexInd = duplexInd;
	}
	public String getStaffPlan() {
		return staffPlan;
	}
	public void setStaffPlan(String staffPlan) {
		this.staffPlan = staffPlan;
	}
	public String getStaffNumber() {
		return staffNumber;
	}
	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}
	public boolean isIs2NCoverage() {
		return is2NCoverage;
	}
	public void setIs2NCoverage(String is2nCoverage) {
		this.is2NCoverage = ("Y".equals(is2nCoverage)) ? true : false;
	}
	public boolean isPrepay() {
		return isPrepay;
	}
	public void setPrepay(String isPrepay) {
		this.isPrepay = ("Y".equals(isPrepay)) ? true : false;
	}
	public String getPayMtdTypes() {
		return payMtdTypes;
	}
	public void setPayMtdTypes(String payMtdTypes) {
		this.payMtdTypes = payMtdTypes;
	}
	public String getEffectivePrice() {
		return effectivePrice;
	}
	public void setEffectivePrice(String effectivePrice) {
		this.effectivePrice = effectivePrice;
	}
	public String getBundleTvCredit() {
		return bundleTvCredit;
	}
	public void setBundleTvCredit(String bundleTvCredit) {
		this.bundleTvCredit = bundleTvCredit;
	}
	public String getProjectCd() {
		return projectCd;
	}
	public void setProjectCd(String projectCd) {
		this.projectCd = projectCd;
	}
	public String getHousingType() {
		return housingType;
	}
	public void setHousingType(String housingType) {
		this.housingType = housingType;
	}
	public String getPremierInd() {
		return premierInd;
	}
	public void setPremierInd(String premierInd) {
		this.premierInd = premierInd;
	}
	public String getPrevSerTermMth() {
		return prevSerTermMth;
	}
	public void setPrevSerTermMth(String prevSerTermMth) {
		this.prevSerTermMth = prevSerTermMth;
	}
	public String getPcdBundleFreeType() {
		return pcdBundleFreeType;
	}
	public void setPcdBundleFreeType(String pcdBundleFreeType) {
		this.pcdBundleFreeType = pcdBundleFreeType;
	}
	public String getPreinstallCheck() {
		return preinstallCheck;
	}
	public void setPreinstallCheck(String preinstallCheck) {
		this.preinstallCheck = preinstallCheck;
	}
	public String getDnOption() {
		return dnOption;
	}
	public void setDnOption(String dnOption) {
		this.dnOption = dnOption;
	}
	public String getHotBasketInd() {
		return hotBasketInd;
	}
	public void setHotBasketInd(String hotBasketInd) {
		this.hotBasketInd = hotBasketInd;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getProgramCd() {
		return programCd;
	}
	public void setProgramCd(String programCd) {
		this.programCd = programCd;
	}
}
