package com.bomwebportal.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class VasDetailDTO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 749863143104207478L;
	
	public static String ERROR_PATH = "errorMsg";
	
	String itemId;
	String itemLob;
	String itemMdoInd;
	String itemType;
	String itemHtml;
	String itemHtml2;	
	String itemLocale;
	String itemDisplayType;
	String itemOnetimeAmt;
	String itemRecurrentAmt;
	String itemOneTimeType;   //20130905
	String itemRebateAmt;
	String itemPaymentGroup;
	
	String stockCount;//add 20120131
	
	String [] vasitem;
	String [] simitem;
	String selectedSimItemId;
	BasketDTO basketDto;
	
	List<ProductInfoDTO> productList;
	String posItemCd;
	
	String categoryId; //for testing 20130117
	String categoryDesc;  //for testing for testing 20130117
	
	private String simType;
	private String simExtraFunction;

	private String simWaiveReason;
	private double simCharge;
	private boolean simWaivable;
	private String prodCd;
	String [] systemVasItem;
	
	private MobItemQuotaDTO mobItemQuotaInfo;
	private List<MobItemQuotaDTO> finalOuotaList;// form basket + vas ==> quotaId
	private String isQuotaExceededInd;
	private String isEagleAPIFailedInd;
	private boolean ignoreQuotaCheck = false;
	private boolean ignoreEagleAPICheck = false;
	
	private String rpWaiveReason;
	private boolean rpWaivable;
	private double rpCharge;
	
	String mipBrand;
	String mipSimType;
	
	private String vasGroup;
	private String hardBundledGrpId;
	private double minVas;
	private String minVasAuthInd;
	private boolean chkIguard = false;
	private boolean chkUADPlan=false;
	private boolean hkidInd = false;
	
	//golden number Auth VAS offer
	private String showGoldenNumAuth = "N";
	private String byPassGoldenNum = "N";
	private String contractPeriod;
	private String netMonthlyAmt;
	
	private String offerCd;
	@Override
	public boolean equals(Object o) {
		
		if (o == this) return true;
		if (!(o instanceof VasDetailDTO)) {
            return false;
        }
		
		VasDetailDTO vasDetailDTO = (VasDetailDTO) o;
		
		return StringUtils.equalsIgnoreCase(this.getItemId(), vasDetailDTO.getItemId());
	}
	
	@Override
    public int hashCode() {
        return itemId.hashCode();
    }
	
	public String getCategoryId() {
		return categoryId;
	}

	public String getCategoryDesc() {
		return categoryDesc;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	
	
	private List<PcRelationshipDTO> pcRelationshipList;
	

	public String getPosItemCd() {
		return posItemCd;
	}

	public void setPosItemCd(String posItemCd) {
		this.posItemCd = posItemCd;
	}

	String errorMsg;
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	/**
	 * An object map key to DTO value
	 */
	private Map<String, Object> objectsMap;
	
	/**
	 * Get specific DTO object value which map to certain key
	 * @param key
	 * @return DTO object
	 */
	public Object getValue(String key) {
		if (this.objectsMap == null || this.objectsMap.isEmpty()) {
			return null;
		} else {
			return this.objectsMap.get(key);
		}
	}
	/**
	 * Set specific DTO object value into map which match with a unique key
	 * @param key
	 * @param value DTO object
	 */
	public void setValue(String key, Object value) {
		if (this.objectsMap == null) {
			objectsMap = new HashMap<String, Object>();
		}
		
		objectsMap.put(key, value);
	}
	
	/**
	 * @return the objectsMap
	 */
	public Map<String, Object> getObjectsMap() {
		return objectsMap;
	}
	/**
	 * @param objectsMap the objectsMap to set
	 */
	public void setObjectsMap(Map<String, Object> objectsMap) {
		this.objectsMap = objectsMap;
	}
	public List<ProductInfoDTO> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductInfoDTO> productList) {
		this.productList = productList;
	}
	public String[] getVasitem() {
		return vasitem;
	}
	public void setVasitem(String[] vasitem) {
		this.vasitem = vasitem;
	}
	public String getItemRebateAmt() {
		return itemRebateAmt;
	}
	public void setItemRebateAmt(String itemRebateAmt) {
		this.itemRebateAmt = itemRebateAmt;
	}
	public String getItemPaymentGroup() {
		return itemPaymentGroup;
	}

	public void setItemPaymentGroup(String itemPaymentGroup) {
		this.itemPaymentGroup = itemPaymentGroup;
	}

	public String getItemOnetimeAmt() {
		return itemOnetimeAmt;
	}
	public void setItemOnetimeAmt(String itemOnetimeAmt) {
		this.itemOnetimeAmt = itemOnetimeAmt;
	}
	public String getItemRecurrentAmt() {
		return itemRecurrentAmt;
	}
	public void setItemRecurrentAmt(String itemRecurrentAmt) {
		this.itemRecurrentAmt = itemRecurrentAmt;
	}
	public String getItemOneTimeType() {
		return itemOneTimeType;
	}

	public void setItemOneTimeType(String itemOneTimeType) {
		this.itemOneTimeType = itemOneTimeType;
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
	public String getItemMdoInd() {
		return itemMdoInd;
	}
	public void setItemMdoInd(String itemMdoInd) {
		this.itemMdoInd = itemMdoInd;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemHtml() {
		return itemHtml;
	}
	public void setItemHtml(String itemHtml) {
		this.itemHtml = itemHtml;
	}
	public String getItemHtml2() {
		return itemHtml2;
	}
	public void setItemHtml2(String itemHtml2) {
		this.itemHtml2 = itemHtml2;
	}
	public String getItemLocale() {
		return itemLocale;
	}
	public void setItemLocale(String itemLocale) {
		this.itemLocale = itemLocale;
	}
	public String getItemDisplayType() {
		return itemDisplayType;
	}
	public void setItemDisplayType(String itemDisplayType) {
		this.itemDisplayType = itemDisplayType;
	}
	public String getStockCount() {
		return stockCount;
	}
	public void setStockCount(String stockCount) {
		this.stockCount = stockCount;
	}
	public String[] getSimitem() {
		return simitem;
	}
	public void setSimitem(String[] simitem) {
		this.simitem = simitem;
	}
	public String getSelectedSimItemId() {
		return selectedSimItemId;
	}
	public void setSelectedSimItemId(String selectedSimItemId) {
		this.selectedSimItemId = selectedSimItemId;
	}
	public BasketDTO getBasketDto() {
		return basketDto;
	}
	public void setBasketDto(BasketDTO basketDto) {
		this.basketDto = basketDto;
	}

	public List<PcRelationshipDTO> getPcRelationshipList() {
		return pcRelationshipList;
	}

	public void setPcRelationshipList(List<PcRelationshipDTO> pcRelationshipList) {
		this.pcRelationshipList = pcRelationshipList;
	}

	public String getSimType() {
		return simType;
	}

	public void setSimType(String simType) {
		this.simType = simType;
	}

	public String getSimExtraFunction() {
		return simExtraFunction;
	}

	public void setSimExtraFunction(String simExtraFunction) {
		this.simExtraFunction = simExtraFunction;
	}

	public String getSimWaiveReason() {
		return simWaiveReason;
	}

	public double getSimCharge() {
		return simCharge;
	}

	public boolean isSimWaivable() {
		return simWaivable;
	}

	public void setSimWaiveReason(String simWaiveReason) {
		this.simWaiveReason = simWaiveReason;
	}

	public void setSimCharge(double simCharge) {
		this.simCharge = simCharge;
	}

	public void setSimWaivable(boolean simWaivable) {
		this.simWaivable = simWaivable;
	}

	public String getProdCd() {
		return prodCd;
	}

	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	public String[] getSystemVasItem() {
		return systemVasItem;
	}

	public void setSystemVasItem(String[] systemVasItem) {
		this.systemVasItem = systemVasItem;
	}

	public MobItemQuotaDTO getMobItemQuotaInfo() {
		return mobItemQuotaInfo;
	}

	public void setMobItemQuotaInfo(MobItemQuotaDTO mobItemQuotaInfo) {
		this.mobItemQuotaInfo = mobItemQuotaInfo;
	}

	public List<MobItemQuotaDTO> getFinalOuotaList() {
		return finalOuotaList;
	}

	public void setFinalOuotaList(List<MobItemQuotaDTO> finalOuotaList) {
		this.finalOuotaList = finalOuotaList;
	}

	public String getIsQuotaExceededInd() {
		return isQuotaExceededInd;
	}

	public void setIsQuotaExceededInd(String isQuotaExceededInd) {
		this.isQuotaExceededInd = isQuotaExceededInd;
	}
	
	public String getIsEagleAPIFailedInd() {
		return isEagleAPIFailedInd;
	}

	public void setIsEagleAPIFailedInd(String isEagleAPIFailedInd) {
		this.isEagleAPIFailedInd = isEagleAPIFailedInd;
	}

	public boolean isIgnoreQuotaCheck() {
		return ignoreQuotaCheck;
	}

	public void setIgnoreQuotaCheck(boolean ignoreQuotaCheck) {
		this.ignoreQuotaCheck = ignoreQuotaCheck;
	}

	public boolean isIgnoreEagleAPICheck() {
		return ignoreEagleAPICheck;
	}

	public void setIgnoreEagleAPICheck(boolean ignoreEagleAPICheck) {
		this.ignoreEagleAPICheck = ignoreEagleAPICheck;
	}

	public String getRpWaiveReason() {
		return rpWaiveReason;
	}

	public void setRpWaiveReason(String rpWaiveReason) {
		this.rpWaiveReason = rpWaiveReason;
	}

	public boolean isRpWaivable() {
		return rpWaivable;
	}

	public void setRpWaivable(boolean rpWaivable) {
		this.rpWaivable = rpWaivable;
	}

	public double getRpCharge() {
		return rpCharge;
	}

	public void setRpCharge(double rpCharge) {
		this.rpCharge = rpCharge;
	}
	
	public String getMipBrand() {
		return mipBrand;
	}
	public void setMipBrand(String mipBrand) {
		this.mipBrand = mipBrand;
	}
	public String getMipSimType() {
		return mipSimType;
	}
	public void setMipSimType(String mipSimType) {
		this.mipSimType = mipSimType;
	}
	public String getVasGroup() {
		return vasGroup;
	}
	public void setVasGroup(String vasGroup) {
		this.vasGroup = vasGroup;
	}
	public String getHardBundledGrpId() {
		return hardBundledGrpId;
	}
	public void setHardBundledGrpId(String hardBundledGrpId) {
		this.hardBundledGrpId = hardBundledGrpId;
	}
	public double getMinVas() {
		return minVas;
	}
	public void setMinVas(double minVas) {
		this.minVas = minVas;
	}
	public String getMinVasAuthInd() {
		return minVasAuthInd;
	}
	public void setMinVasAuthInd(String minVasAuthInd) {
		this.minVasAuthInd = minVasAuthInd;
	}
	public boolean isChkIguard() {
		return chkIguard;
	}

	public void setChkIguard(boolean chkIguard) {
		this.chkIguard = chkIguard;
	}

	public boolean isChkUADPlan() {
		return chkUADPlan;
	}

	public void setChkUADPlan(boolean chkUADPlan) {
		this.chkUADPlan = chkUADPlan;
	}

	public boolean isHkidInd() {
		return hkidInd;
	}

	public void setHkidInd(boolean hkidInd) {
		this.hkidInd = hkidInd;
	}

	public String getShowGoldenNumAuth() {
		return showGoldenNumAuth;
	}

	public void setShowGoldenNumAuth(String showGoldenNumAuth) {
		this.showGoldenNumAuth = showGoldenNumAuth;
	}

	public String getByPassGoldenNum() {
		return byPassGoldenNum;
	}

	public void setByPassGoldenNum(String byPassGoldenNum) {
		this.byPassGoldenNum = byPassGoldenNum;
	}
	
    public String getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public String getNetMonthlyAmt() {
		return netMonthlyAmt;
	}

	public void setNetMonthlyAmt(String netMonthlyAmt) {
		this.netMonthlyAmt = netMonthlyAmt;
	}

	public String getOfferCd() {
		return offerCd;
	}

	public void setOfferCd(String offerCd) {
		this.offerCd = offerCd;
	}
	
	
}
