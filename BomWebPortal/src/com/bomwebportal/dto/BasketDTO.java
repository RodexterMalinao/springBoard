package com.bomwebportal.dto;

import java.util.List;

public class BasketDTO implements java.io.Serializable{

	private static final long serialVersionUID = -2884198505109312020L;

	public BasketDTO(){
		this.belowQuotaInd = "Y";
	}
	
	public enum HsExtraFunction {
		NONE("0", false, false)
		, NFC("1", true, false)
		, OCTOPUS("2", false, true)
		, NFC_AND_OCTOPUS("3", true, true)
		;
		HsExtraFunction(String value, boolean supportNfc, boolean supportOctopus) {
			this.value = value;
			this.supportNfc = supportNfc;
			this.supportOctopus = supportOctopus;
		}
		public String getValue() {
			return value;
		}
		public boolean isSupportNfc() {
			return supportNfc;
		}
		public boolean isSupportOctopus() {
			return supportOctopus;
		}
		public static HsExtraFunction getHsExtraFunction(String value) {
			for (HsExtraFunction obj : HsExtraFunction.values()) {
				if (obj.getValue().equals(value)) {
					return obj;
				}
			}
			return null;
		}
		private String value;
		private boolean supportNfc;
		private boolean supportOctopus;
	}
	
	String basketId;
	String basketHtml;
	String displayTab;
	String belowQuotaInd; //'Y'/'N'
	String customerTierId;
	String customerTierDesc; //show more info, in basket desc
	String publicHouseBaksetInd; //Y, N, 
	String recurrentAmt; //add 20120130, for MTR golden number save
	String contractPeriod; //add 20120130, for MTR golden number save
	String grossPlanFee;//add 20141029, for MTR new golden number rules
	String upfrontAmt;//add 20120201 
	String basketOfferTypeCd;//1.PH, 2.student , 3.staff
	String basketOfferTypeDesc;//1.PH, 2.student , 3.staff
	String callListCd;// for sales info page save
	String callListDesc;// for sales info page save
	
	String	rpTypeDesc	    ;//	VOICE + DATA
	String	rpTypeId	;//	3
	String	ratePlanDesc	;//	$478
	String	ratePlanId	;//	478

	String	brandDesc	;//	SONYERI
	String	modelDesc	;//	SE XPERIA MINI PRO
	String	colorDesc	;//	White

	String	offerTypeDesc	;//	Student Offer
	String	offerTypeId	;//	2
	String	simTypeDesc	;//	1C2N Offer
	String	simTypeId	;//	4
	
	String oneCardTwoNumberVasInd; //Y = yes, customer select a 1C2N VAS 
	

	//select * from W_DISPLAY_LKUP DL where DL.TYPE = 'BASKET_TYPE'
	String basketTypeId;//1:SIM + HANDSET,2:SIM ONLY,3:SIM + SMARTPHONE REBATE,4:SIM + TABLET,5:NETVIGATOR Everywhere,6:Concierge
	String basketTypeDesc;//1:SIM + HANDSET,2:SIM ONLY,3:SIM + SMARTPHONE REBATE,4:SIM + TABLET,5:NETVIGATOR Everywhere,6:Concierge
	
	
	String prePaymentAmt;//add 20120214, for summary payment trx. 
	
	String brandId;
	String modelId;
	String colorId;
	
	String sortDesc; //QLOB / Student Offer [offerType]
	String description;//	MASS / SIM + HANDSET / VOICE + DATA / $478 / 18 months / SONYERI SE XPERIA MINI PRO White
	String creditCardInd; //select * from w_basket_attribute_mv where basket_id = '100000682',  new field = 'CREDIT_CARD_IND'- value = 'QLOB 0+0'
	String hsPosItemCd; // for delivery hottest model
	String hottestModelInd; // Y/N for service request check range.
	

	String dummyBasketInd; //Y/N 
	String realBasketId;//if dummyBasketInd='Y', & have mapping, this field will save realbasketId
	String adminCharge;//Athena 20130909
	String dataOnlyInd;
	List<BasketQuotaDTO> basketQuotaList;//get quota info from w_basket_parm 
	String handsetExtraFunction; //20130905
	String handsetSimSize;
	String upfrontCCInd;
	
	String mipBrand;
	String mipSimType;
	
	// MIP.P4 modification
	String nature;
	
	String basketDescription;
	
	private HsExtraFunction hsExtraFunction;
	private List<MobItemQuotaDTO> basketMobItemQuotaInfoList; //QUOTA
	private List<VasDetailDTO> optionalVasItemList; //QUOTA
	
	//Golden Number Authorization Enhancement
	String showGoldenNumAuth = "N";
	String byPassGoldenNum = "N";
	
	public String getBasketId() {
		return basketId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	public String getBasketHtml() {
		return basketHtml;
	}
	public void setBasketHtml(String basketHtml) {
		this.basketHtml = basketHtml;
	}
	public String getDisplayTab() {
		return displayTab;
	}
	public void setDisplayTab(String displayTab) {
		this.displayTab = displayTab;
	}
	public String getCustomerTierDesc() {
		return customerTierDesc;
	}
	public void setCustomerTierDesc(String customerTierDesc) {
		this.customerTierDesc = customerTierDesc;
	}
	public String getBelowQuotaInd() {
		return belowQuotaInd;
	}
	public void setBelowQuotaInd(String belowQuotaInd) {
		this.belowQuotaInd = belowQuotaInd;
	}
	
	public String getBasketOfferTypeCd() {
		return basketOfferTypeCd;
	}

	public void setBasketOfferTypeCd(String basketOfferTypeCd) {
		this.basketOfferTypeCd = basketOfferTypeCd;
	}

	public String getBasketOfferTypeDesc() {
		return basketOfferTypeDesc;
	}

	public void setBasketOfferTypeDesc(String basketOfferTypeDesc) {
		this.basketOfferTypeDesc = basketOfferTypeDesc;
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

	public String getPublicHouseBaksetInd() {
		return publicHouseBaksetInd;
	}

	public void setPublicHouseBaksetInd(String publicHouseBaksetInd) {
		this.publicHouseBaksetInd = publicHouseBaksetInd;
	}
	public String getUpfrontAmt() {
		return upfrontAmt;
	}
	public void setUpfrontAmt(String upfrontAmt) {
		this.upfrontAmt = upfrontAmt;
	}
	public String getCallListCd() {
		return callListCd;
	}
	public void setCallListCd(String callListCd) {
		this.callListCd = callListCd;
	}
	public String getCallListDesc() {
		return callListDesc;
	}
	public void setCallListDesc(String callListDesc) {
		this.callListDesc = callListDesc;
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
	public String getPrePaymentAmt() {
		return prePaymentAmt;
	}
	public void setPrePaymentAmt(String prePaymentAmt) {
		this.prePaymentAmt = prePaymentAmt;
	}
	public String getBasketTypeId() {
		return basketTypeId;
	}
	public void setBasketTypeId(String basketTypeId) {
		this.basketTypeId = basketTypeId;
	}
	public String getBasketTypeDesc() {
		return basketTypeDesc;
	}
	public void setBasketTypeDesc(String basketTypeDesc) {
		this.basketTypeDesc = basketTypeDesc;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCustomerTierId() {
		return customerTierId;
	}
	public void setCustomerTierId(String customerTierId) {
		this.customerTierId = customerTierId;
	}
	public String getRpTypeDesc() {
		return rpTypeDesc;
	}
	public void setRpTypeDesc(String rpTypeDesc) {
		this.rpTypeDesc = rpTypeDesc;
	}
	public String getRpTypeId() {
		return rpTypeId;
	}
	public void setRpTypeId(String rpTypeId) {
		this.rpTypeId = rpTypeId;
	}
	public String getRatePlanDesc() {
		return ratePlanDesc;
	}
	public void setRatePlanDesc(String ratePlanDesc) {
		this.ratePlanDesc = ratePlanDesc;
	}
	public String getRatePlanId() {
		return ratePlanId;
	}
	public void setRatePlanId(String ratePlanId) {
		this.ratePlanId = ratePlanId;
	}
	public String getBrandDesc() {
		return brandDesc;
	}
	public void setBrandDesc(String brandDesc) {
		this.brandDesc = brandDesc;
	}
	public String getModelDesc() {
		return modelDesc;
	}
	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}
	public String getColorDesc() {
		return colorDesc;
	}
	public void setColorDesc(String colorDesc) {
		this.colorDesc = colorDesc;
	}
	public String getOfferTypeDesc() {
		return offerTypeDesc;
	}
	public void setOfferTypeDesc(String offerTypeDesc) {
		this.offerTypeDesc = offerTypeDesc;
	}
	public String getOfferTypeId() {
		return offerTypeId;
	}
	public void setOfferTypeId(String offerTypeId) {
		this.offerTypeId = offerTypeId;
	}
	
	public String getSimTypeId() {
		return simTypeId;
	}
	public void setSimTypeId(String simTypeId) {
		this.simTypeId = simTypeId;
	}
	public String getSortDesc() {
		return sortDesc;
	}
	public void setSortDesc(String sortDesc) {
		this.sortDesc = sortDesc;
	}
	public String getSimTypeDesc() {
		return simTypeDesc;
	}
	public void setSimTypeDesc(String simTypeDesc) {
		this.simTypeDesc = simTypeDesc;
	}
	public String getHsPosItemCd() {
		return hsPosItemCd;
	}
	public void setHsPosItemCd(String hsPosItemCd) {
		this.hsPosItemCd = hsPosItemCd;
	}
	public String getDummyBasketInd() {
		return dummyBasketInd;
	}
	public void setDummyBasketInd(String dummyBasketInd) {
		this.dummyBasketInd = dummyBasketInd;
	}
	public String getRealBasketId() {
		return realBasketId;
	}
	public void setRealBasketId(String realBasketId) {
		this.realBasketId = realBasketId;
	}
	public String getCreditCardInd() {
		return creditCardInd;
	}
	public void setCreditCardInd(String creditCardInd) {
		this.creditCardInd = creditCardInd;
	}
	public String getDataOnlyInd() {
		return dataOnlyInd;
	}
	public void setDataOnlyInd(String dataOnlyInd) {
		this.dataOnlyInd = dataOnlyInd;
	}
	public String getAdminCharge() {
		return adminCharge;
	}
	public void setAdminCharge(String adminCharge) {
		this.adminCharge = adminCharge;
	}
	public String getHottestModelInd() {
		return hottestModelInd;
	}
	public void setHottestModelInd(String hottestModelInd) {
		this.hottestModelInd = hottestModelInd;
	}
	public List<BasketQuotaDTO> getBasketQuotaList() {
		return basketQuotaList;
	}
	public void setBasketQuotaList(List<BasketQuotaDTO> basketQuotaList) {
		this.basketQuotaList = basketQuotaList;
	}
	public String getOneCardTwoNumberVasInd() {
		return oneCardTwoNumberVasInd;
	}
	public void setOneCardTwoNumberVasInd(String oneCardTwoNumberVasInd) {
		this.oneCardTwoNumberVasInd = oneCardTwoNumberVasInd;
	}
	public String getHandsetExtraFunction() {
		return handsetExtraFunction;
	}
	public void setHandsetExtraFunction(String handsetExtraFunction) {
		this.handsetExtraFunction = handsetExtraFunction;
	}
	public HsExtraFunction getHsExtraFunction() {
		return hsExtraFunction;
	}
	public void setHsExtraFunction(HsExtraFunction hsExtraFunction) {
		this.hsExtraFunction = hsExtraFunction;
	}
	public String getHandsetSimSize() {
		return handsetSimSize;
	}
	public void setHandsetSimSize(String handsetSimSize) {
		this.handsetSimSize = handsetSimSize;
	}
	public void setGrossPlanFee(String grossPlanFee) {
		this.grossPlanFee = grossPlanFee;
	}
	public String getGrossPlanFee() {
		return grossPlanFee;
	}
	public List<MobItemQuotaDTO> getBasketMobItemQuotaInfoList() {
		return basketMobItemQuotaInfoList;
	}
	public void setBasketMobItemQuotaInfoList(
			List<MobItemQuotaDTO> basketMobItemQuotaInfoList) {
		this.basketMobItemQuotaInfoList = basketMobItemQuotaInfoList;
	}
	public List<VasDetailDTO> getOptionalVasItemList() {
		return optionalVasItemList;
	}
	public void setOptionalVasItemList(List<VasDetailDTO> optionalVasItemList) {
		this.optionalVasItemList = optionalVasItemList;
	}
	public String getUpfrontCCInd() {
		return upfrontCCInd;
	}
	public void setUpfrontCCInd(String upfrontCCInd) {
		this.upfrontCCInd = upfrontCCInd;
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
	// MIP.P4 modification
	public String getNature() {
		return nature;
	}
	// MIP.P4 modification
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getBasketDescription() {
		return basketDescription;
	}
	public void setBasketDescription(String basketDescription) {
		this.basketDescription = basketDescription;
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

	
}

