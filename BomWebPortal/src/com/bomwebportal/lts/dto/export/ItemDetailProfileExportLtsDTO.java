package com.bomwebportal.lts.dto.export;

import java.io.Serializable;

public class ItemDetailProfileExportLtsDTO implements Serializable {

	private static final long serialVersionUID = -8558198415294447970L;

	private String description = null;
	private String conditionEffStartDate = null;
	private String conditionEffEndDate = null;
	private double tpExpiredMonths;
	private String iddFreeMin = null;
	private String itemId = null;
	private String arpuType = null;
	private String promotionStartDate = null;
	private String promotionEndDate = null;
	private String promotionExpiredMonths = null;
	private String category = null;
	private String contractMonth = null;
	private String grossEffPrice = null;
	private String netEffPrice = null;
	private int returnCd = 0;
	private String returnMsg = null;


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getConditionEffStartDate() {
		return conditionEffStartDate;
	}

	public void setConditionEffStartDate(String conditionEffStartDate) {
		this.conditionEffStartDate = conditionEffStartDate;
	}

	public String getConditionEffEndDate() {
		return conditionEffEndDate;
	}

	public void setConditionEffEndDate(String conditionEffEndDate) {
		this.conditionEffEndDate = conditionEffEndDate;
	}

	public double getTpExpiredMonths() {
		return tpExpiredMonths;
	}

	public void setTpExpiredMonths(double tpExpiredMonths) {
		this.tpExpiredMonths = tpExpiredMonths;
	}

	public String getIddFreeMin() {
		return iddFreeMin;
	}

	public void setIddFreeMin(String iddFreeMin) {
		this.iddFreeMin = iddFreeMin;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getArpuType() {
		return arpuType;
	}

	public void setArpuType(String arpuType) {
		this.arpuType = arpuType;
	}

	public String getPromotionStartDate() {
		return promotionStartDate;
	}

	public void setPromotionStartDate(String promotionStartDate) {
		this.promotionStartDate = promotionStartDate;
	}

	public String getPromotionEndDate() {
		return promotionEndDate;
	}

	public void setPromotionEndDate(String promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	public String getPromotionExpiredMonths() {
		return promotionExpiredMonths;
	}

	public void setPromotionExpiredMonths(String promotionExpiredMonths) {
		this.promotionExpiredMonths = promotionExpiredMonths;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContractMonth() {
		return contractMonth;
	}

	public void setContractMonth(String contractMonth) {
		this.contractMonth = contractMonth;
	}

	public String getGrossEffPrice() {
		return grossEffPrice;
	}

	public void setGrossEffPrice(String grossEffPrice) {
		this.grossEffPrice = grossEffPrice;
	}

	public int getReturnCd() {
		return returnCd;
	}

	public void setReturnCd(int returnCd) {
		this.returnCd = returnCd;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getNetEffPrice() {
		return netEffPrice;
	}

	public void setNetEffPrice(String netEffPrice) {
		this.netEffPrice = netEffPrice;
	}
}
