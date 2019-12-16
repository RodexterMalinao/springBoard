package com.bomwebportal.dto;

import java.io.Serializable;

import java.util.List;

public class ItemEditDTO implements Serializable{
	

	private static final long serialVersionUID = 7651636149868495094L;

	private String formAction; //for form controller SEARCH, INSERT, UPDATE, DELETE
	private String formMessage;//for form controller return message
	
	private String env;//uat, prd, for get sequence using

	//W_ITEM
	private int id	;//	NUMBER
	private String lob	;//	VARCHAR2
	private String type	;//	VARCHAR2
	private String  description	;//	VARCHAR2
	private String createBy	;//	VARCHAR2
	private String createDate	;//	DATE
	private String lastUpdBy	;//	VARCHAR2
	private String lastUpdDate	;//	DATE

	//W_ITEM_DTL_HS
	//private String id	;//	NUMBER
	private String brandId	;//	NUMBER
	//private String brandName	;//	String
	private String modelId	;//	NUMBER
	//private String modelName	;//	String
	private String colorId	;//	NUMBER
	//private String colorName	;//	String
	private String hsContractPeriod	;//	NUMBER
	//createBy	;//	VARCHAR2
	//createDate	;//	DATE
	//lastUpdBy	;//	VARCHAR2
	//lastUpdDate	;//	DATE
			
			
	//W_ITEM_DTL_RP_RB_VAS
	//private String id	;//	NUMBER (10)
	private String rpType	;//	NUMBER (10)
	private String rpRbVasContractPeriod	;//	NUMBER (3)
	private String rebatePeriod;//	NUMBER (3)
	private String rebateSchedule	;//	VARCHAR2 (100 Byte)
	private String rebateAmt	;//	NUMBER (10,2)
	private String penaltyType	;//	VARCHAR2 (1 Byte)
	private String penaltyAmt	;//	NUMBER (10,2)
	
	
	//createBy	;//	VARCHAR2 (20 Byte)
	//createDate	;//	DATE
	//lastUpdBy	;//	VARCHAR2 (20 Byte)
	//private DATE lastUpdDate	;//	DATE
	
	List<ItemPricingDTO> itemPricing;
	private String itemPricingString;
	
	
	//W_ITEM_PRICING
	//private int id	;//	NUMBER
	
	private String effStartDate	;//	DATE
	private String effEndDate	;//	DATE
	private String onetimeType	;//	VARCHAR2 (1 Byte)
	private  String 	onetimeAmt	;//	NUMBER (10,2)
	private  String 	recurrentAmt	;//	NUMBER (10,2)
	
	//for compare using
	private String EffStartDateOriginal	;//	DATE
	private String effEndDateOriginal	;//	DATE
	private String onetimeTypeOriginal	;//	VARCHAR2 (1 Byte)
	private  String onetimeAmtOriginal	;//	NUMBER (10,2)
	private  String recurrentAmtOriginal	;//	NUMBER (10,2)
	
	
	private  String offerString; //20110328 for textArea insert to db W_ITEM_OFFER_ASSGN, W_ITEM_OFFER_PRODUCT_ASSGN

	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLob() {
		return lob;
	}


	public void setLob(String lob) {
		this.lob = lob;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCreateBy() {
		return createBy;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public String getLastUpdBy() {
		return lastUpdBy;
	}


	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}


	public String getLastUpdDate() {
		return lastUpdDate;
	}


	public void setLastUpdDate(String lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}


	public String getBrandId() {
		return brandId;
	}


	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}


	public String getModelId() {
		return modelId;
	}


	public void setModelId(String modelId) {
		this.modelId = modelId;
	}


	public String getColorId() {
		return colorId;
	}


	public void setColorId(String colorId) {
		this.colorId = colorId;
	}


	public String getHsContractPeriod() {
		return hsContractPeriod;
	}


	public void setHsContractPeriod(String hsContractPeriod) {
		this.hsContractPeriod = hsContractPeriod;
	}


	public String getRpType() {
		return rpType;
	}


	public void setRpType(String rpType) {
		this.rpType = rpType;
	}


	public String getRpRbVasContractPeriod() {
		return rpRbVasContractPeriod;
	}


	public void setRpRbVasContractPeriod(String rpRbVasContractPeriod) {
		this.rpRbVasContractPeriod = rpRbVasContractPeriod;
	}


	public String getRebatePeriod() {
		return rebatePeriod;
	}


	public void setRebatePeriod(String rebatePeriod) {
		this.rebatePeriod = rebatePeriod;
	}


	public String getRebateSchedule() {
		return rebateSchedule;
	}


	public void setRebateSchedule(String rebateSchedule) {
		this.rebateSchedule = rebateSchedule;
	}


	public String getRebateAmt() {
		return rebateAmt;
	}


	public void setRebateAmt(String rebateAmt) {
		this.rebateAmt = rebateAmt;
	}


	public String getPenaltyType() {
		return penaltyType;
	}


	public void setPenaltyType(String penaltyType) {
		this.penaltyType = penaltyType;
	}


	public String getPenaltyAmt() {
		return penaltyAmt;
	}


	public void setPenaltyAmt(String penaltyAmt) {
		this.penaltyAmt = penaltyAmt;
	}


	public List<ItemPricingDTO> getItemPricing() {
		return itemPricing;
	}


	public void setItemPricing(List<ItemPricingDTO> itemPricing) {
		for ( int i=0; i<itemPricing.size(); i++){
		itemPricingString +=itemPricing.get(i).getId()+","+itemPricing.get(i).getEffStartDate();
		}
		this.itemPricing = itemPricing;
	}


	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}


	public String getFormAction() {
		return formAction;
	}


	public void setFormMessage(String formMessage) {
		this.formMessage = formMessage;
	}


	public String getFormMessage() {
		return formMessage;
	}
	
	
	/*public String getBrandName() {
		return brandName;
	}


	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}


	public String getModelName() {
		return modelName;
	}


	public void setModelName(String modelName) {
		this.modelName = modelName;
	}


	public String getColorName() {
		return colorName;
	}


	public void setColorName(String colorName) {
		this.colorName = colorName;
	}*/


	public void setItemPricingString(String itemPricingString) {
		this.itemPricingString = itemPricingString;
	}


	public String getItemPricingString() {
		return itemPricingString;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getDisplayType() {
		return displayType;
	}
	
	
private String displayType;
	
	public String getEffStartDate() {
		return effStartDate;
	}

	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}


	public String getEffEndDate() {
		return effEndDate;
	}


	public void setEffEndDate(String effEndDate) {
		this.effEndDate = effEndDate;
	}


	public String getOnetimeType() {
		return onetimeType;
	}


	public void setOnetimeType(String onetimeType) {
		this.onetimeType = onetimeType;
	}


	public String getOnetimeAmt() {
		return onetimeAmt;
	}


	public void setOnetimeAmt(String onetimeAmt) {
		this.onetimeAmt = onetimeAmt;
	}


	public String getRecurrentAmt() {
		return recurrentAmt;
	}


	public void setRecurrentAmt(String recurrentAmt) {
		this.recurrentAmt = recurrentAmt;
	}


	public void setOfferString(String offerString) {
		this.offerString = offerString;
	}


	public String getOfferString() {
		return offerString;
	}
	
	public String getEnv() {
		return env;
	}


	public void setEnv(String env) {
		this.env = env;
	}


	public String getEffStartDateOriginal() {
		return EffStartDateOriginal;
	}


	public void setEffStartDateOriginal(String effStartDateOriginal) {
		EffStartDateOriginal = effStartDateOriginal;
	}


	public String getEffEndDateOriginal() {
		return effEndDateOriginal;
	}


	public void setEffEndDateOriginal(String effEndDateOriginal) {
		this.effEndDateOriginal = effEndDateOriginal;
	}


	public String getOnetimeTypeOriginal() {
		return onetimeTypeOriginal;
	}


	public void setOnetimeTypeOriginal(String onetimeTypeOriginal) {
		this.onetimeTypeOriginal = onetimeTypeOriginal;
	}


	public String getOnetimeAmtOriginal() {
		return onetimeAmtOriginal;
	}


	public void setOnetimeAmtOriginal(String onetimeAmtOriginal) {
		this.onetimeAmtOriginal = onetimeAmtOriginal;
	}


	public String getRecurrentAmtOriginal() {
		return recurrentAmtOriginal;
	}


	public void setRecurrentAmtOriginal(String recurrentAmtOriginal) {
		this.recurrentAmtOriginal = recurrentAmtOriginal;
	}




}
