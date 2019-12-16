package com.bomwebportal.lts.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ItemDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3145348661178886741L;
	
	private boolean selected;
	private String itemId;
	private String itemLob; 
	private String itemType;
	private String itemDesc;
	private String itemDisplayHtml;
	private String locale;
	private String oneTimeAmt;
	private String oneTimeAmtTxt;
	private String recurrentAmt;
	private String recurrentAmtTxt;
	private String mthToMthAmt;
	private String mthToMthAmtTxt;
	private String mdoInd;
	private String credit;
	private String selectedQty;
	private ItemAttbDTO[] itemAttbs;
	private String penaltyAmt;
	private String penaltyAmtTxt;
	private String rebateAmt;
	private String rebateAmtTxt;
	private String imagePath;
	private String url;
	private String imageDetailPath;
	private String afType;
	private String eligibleDocType;
	private String programCd;
	
	// For premium item set
	private String itemSetId;
	
	/* W_TP_VAS_LKUP*/
	private String tpCatg;
	private String contractMonth;
	private String payingMonth;
	private String grossEffPrice;
	private String netEffPrice;
	private String isPremiumTp;
	private String nature;
	
	private String displayAmt;
	private String displayAmtTxt;

	private String penaltyHandling;
	
	public String getIsPremiumTp() {
		return isPremiumTp;
	}
	public void setIsPremiumTp(String isPremiumTp) {
		this.isPremiumTp = isPremiumTp;
	}
	public String getTpCatg() {
		return tpCatg;
	}
	public void setTpCatg(String tpCatg) {
		this.tpCatg = tpCatg;
	}
	public String getContractMonth() {
		return contractMonth;
	}
	public void setContractMonth(String contractMonth) {
		this.contractMonth = contractMonth;
	}
	public String getPayingMonth() {
		return payingMonth;
	}
	public void setPayingMonth(String payingMonth) {
		this.payingMonth = payingMonth;
	}
	public String getGrossEffPrice() {
		return grossEffPrice;
	}
	public void setGrossEffPrice(String grossEffPrice) {
		this.grossEffPrice = grossEffPrice;
	}
	public String getNetEffPrice() {
		return netEffPrice;
	}
	public void setNetEffPrice(String netEffPrice) {
		this.netEffPrice = netEffPrice;
	}
	public String getRebateAmt() {
		return rebateAmt;
	}
	public void setRebateAmt(String rebateAmt) {
		this.rebateAmt = rebateAmt;
	}
	public String getRebateAmtTxt() {
		return rebateAmtTxt;
	}
	public void setRebateAmtTxt(String rebateAmtTxt) {
		this.rebateAmtTxt = rebateAmtTxt;
	}
	public String getPenaltyAmt() {
		return penaltyAmt;
	}
	public void setPenaltyAmt(String penaltyAmt) {
		this.penaltyAmt = penaltyAmt;
	}
	public String getPenaltyAmtTxt() {
		return penaltyAmtTxt;
	}
	public void setPenaltyAmtTxt(String penaltyAmtTxt) {
		this.penaltyAmtTxt = penaltyAmtTxt;
	}
	public ItemAttbDTO[] getItemAttbs() {
		return itemAttbs;
	}
	public void setItemAttbs(ItemAttbDTO[] itemAttbs) {
		this.itemAttbs = itemAttbs;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getMdoInd() {
		return mdoInd;
	}
	public void setMdoInd(String mdoInd) {
		this.mdoInd = mdoInd;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemLob() {
		return itemLob;
	}
	public void setItemLob(String itemLob) {
		this.itemLob = itemLob;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getItemDisplayHtml() {
		return itemDisplayHtml;
	}
	public void setItemDisplayHtml(String itemDisplayHtml) {
		this.itemDisplayHtml = itemDisplayHtml;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getOneTimeAmt() {
		return oneTimeAmt;
	}
	public void setOneTimeAmt(String oneTimeAmt) {
		this.oneTimeAmt = oneTimeAmt;
	}
	public String getOneTimeAmtTxt() {
		return oneTimeAmtTxt;
	}
	public void setOneTimeAmtTxt(String oneTimeAmtTxt) {
		this.oneTimeAmtTxt = oneTimeAmtTxt;
	}
	public String getRecurrentAmt() {
		return recurrentAmt;
	}
	public void setRecurrentAmt(String recurrentAmt) {
		this.recurrentAmt = recurrentAmt;
	}
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
	public String getSelectedQty() {
		return selectedQty;
	}
	public void setSelectedQty(String selectedQty) {
		this.selectedQty = selectedQty;
	}
	public String getItemSetId() {
		return itemSetId;
	}
	public void setItemSetId(String itemSetId) {
		this.itemSetId = itemSetId;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDisplayAmt() {
		if(("0".equals(getRecurrentAmt()) || StringUtils.isBlank(getRecurrentAmt()))){
			if(("0".equals(getMthToMthAmt()) || StringUtils.isBlank(getMthToMthAmt()))){
				return null;
			}else{
				return "$"+getMthToMthAmt();
			}
		}else{
			return "$"+getRecurrentAmt();
		}
	}
	public void setDisplayAmt(String displayAmt) {
		this.displayAmt = displayAmt;
	}
	public String getDisplayAmtTxt() {
		if(("0".equals(getRecurrentAmt()) || StringUtils.isBlank(getRecurrentAmt()))){
			return getMthToMthAmtTxt();
		}else{
			return getRecurrentAmtTxt();
		}
	}
	public void setDisplayAmtTxt(String displayAmtTxt) {
		this.displayAmtTxt = displayAmtTxt;
	}
	public String getImageDetailPath() {
		return imageDetailPath;
	}
	public void setImageDetailPath(String imageDetailPath) {
		this.imageDetailPath = imageDetailPath;
	}
	public String getPenaltyHandling() {
		return penaltyHandling;
	}
	public void setPenaltyHandling(String penaltyHandling) {
		this.penaltyHandling = penaltyHandling;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getAfType() {
		return afType;
	}
	public void setAfType(String afType) {
		this.afType = afType;
	}
	public String getEligibleDocType() {
		return eligibleDocType;
	}
	public void setEligibleDocType(String eligibleDocType) {
		this.eligibleDocType = eligibleDocType;
	}
	public String getProgramCd() {
		return programCd;
	}
	public void setProgramCd(String programCd) {
		this.programCd = programCd;
	}
	
	
}
