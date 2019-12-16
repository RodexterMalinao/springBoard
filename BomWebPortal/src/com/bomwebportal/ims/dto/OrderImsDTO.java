package com.bomwebportal.ims.dto;

import java.util.Date;

import com.bomwebportal.ims.dto.ui.InstallAddrUI;
import com.bomwebportal.ims.dto.SalesReferrerDTO;

public class OrderImsDTO extends OrderDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6299417359333761904L;	

	private String fieldInd;
	private String appMethodDesc;
	private String OrderId;
	private String LoginId;
	private String DecodeType;
	private String AdultViewAllow;
	private Date TargetCommDate;
	private String ShopContactNum;
	private String PrepayAmt;
	private String MovingChrg;
	private String WaivedPrepay;
	private String CashFsPrepay;
	private String NowTvFormType;
	private String FixedLineNo;
	private String IsCreditCardOffer;
	private String LangPreference;
	private String ProcessCreditCard;
	private String ProcessVim;
	private String ProcessVms;
	private String ProcessWifi;
	private String PrimaryAcctNo;
	private String TvCredit;
	private String Bandwidth;
	private String ChooseNowtvCoupon;
	private String OtInstallChrg;
	private String installOtCode;//gary
	private String installOTDesc_en;//gary
	private String installOTDesc_zh;//gary
	private String InstallmentCode;//gary
	private String installOtQty;//Tony
	private String specialOTCId;
	private String WaivedOtInstallChrg;
	private String InstallmentMonth;
	private String InstallmentChrg;
	private Boolean Installment;
	private String ImsOrderType;
	private String OtChrgType;
	private String AppMethod;
	private String SourceCd;
	private Date CallDate;
	private String PositionNo;
	private String FAXno;
	
	//ims direct sales
	private String dsType;
	private String dsLocation;
	private String dsCoolOff;
	private String dsWaiveCoolOff;
	private String dsQcCallTime;
	private String dsWaiveQC;
	private String sysF;//Added by Andy
	private String shopCode;
	private String tranCode;
	private CustAddrDTO addressDTO;
	private String serviceNum;
	private String tvPriceInd; //kinman new nowtv
	
	//ims direct sales nowtvsales
	private String ARPU_in;
	private String ARPU_out;
	private String ride_on_FSA_Ind;
	private String must_QC_Ind;
	private String isCreditCardOnly;
	private String isCashOnly;
	private String depositAmt;
	private Date qr_submit_date;
	private String isAdultChannelSelected;
	private String isIncludeGoodsDelivery = "N"; //BOM2019022
	private String notMatchPaymentMethod;
	private BomwebOrderL2JobDTO[] bomwebOrderL2JobList;
	
	private String pcdNowOneBoxInd;	
	private String pcdLike100Ind;
	private String preInstallInd;
	private String specialRequestCd;
	private String collectViStb1;
	private String collectViStb2;
	
	private String preUseInd;
	
	private String mobileOfferInd;
	private String mrt;
	
	private String ltsServiceInd;
	
	private String related_FSA;
	private String ride_on_FSA_reason_Cd;
	
	private String serviceWaiver; 
	
	private String mode;
	
	// 20190530, BOM2019039
	private String hkqrString;
	private String hkqrUrl;
	private String cashPayMtdType;
	private String outTradeNo;
	private String tradeNo;
	private String fpsReferenceId;

	public String getRide_on_FSA_Ind() {
		return ride_on_FSA_Ind;
	}
	public void setRide_on_FSA_Ind(String ride_on_FSA_Ind) {
		this.ride_on_FSA_Ind = ride_on_FSA_Ind;
	}
	public String getMust_QC_Ind() {
		return must_QC_Ind;
	}
	public void setMust_QC_Ind(String must_QC_Ind) {
		this.must_QC_Ind = must_QC_Ind;
	}
	
	private SalesReferrerDTO salesReferrerDTO= new SalesReferrerDTO();
	
	public SalesReferrerDTO getSalesReferrerDTO() {
		return salesReferrerDTO;
	}

	public void setSalesReferrerDTO(SalesReferrerDTO salesReferrerDTO) {
		this.salesReferrerDTO = salesReferrerDTO;
	}
		
	public String getTvPriceInd() {
		return tvPriceInd;
	}
	public void setTvPriceInd(String tvPriceInd) {
		this.tvPriceInd = tvPriceInd;
	}
	private String serbdyno;
	private String floorNo;
	private String unitNo;
	
	private String isNewCust;
	
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public String getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	public String getHiLotNo() {
		return hiLotNo;
	}
	public void setHiLotNo(String hiLotNo) {
		this.hiLotNo = hiLotNo;
	}
	private String hiLotNo;
	
	
	public String getAppMethod() {
		return AppMethod;
	}
	public void setAppMethod(String appMethod) {
		AppMethod = appMethod;
	}
	public String getSourceCd() {
		return SourceCd;
	}
	public void setSourceCd(String sourceCd) {
		SourceCd = sourceCd;
	}
	public String getImsOrderType() {
		return ImsOrderType;
	}
	public void setImsOrderType(String imsOrderType) {
		ImsOrderType = imsOrderType;
	}
	public String getInstallmentMonth() {
		return InstallmentMonth;
	}
	public void setInstallmentMonth(String installmentMonth) {
		InstallmentMonth = installmentMonth;
	}
	public String getInstallmentChrg() {
		return InstallmentChrg;
	}
	public void setInstallmentChrg(String installmentChrg) {
		InstallmentChrg = installmentChrg;
	}
	public Boolean getInstallment() {
		return Installment;
	}
	public void setInstallment(Boolean installment) {
		Installment = installment;
	}
		
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getLoginId() {
		return LoginId;
	}
	public void setLoginId(String loginId) {
		LoginId = loginId;
	}
	public String getDecodeType() {
		return DecodeType;
	}
	public void setDecodeType(String decodeType) {
		DecodeType = decodeType;
	}
	public String getAdultViewAllow() {
		return AdultViewAllow;
	}
	public void setAdultViewAllow(String adultViewAllow) {
		AdultViewAllow = adultViewAllow;
	}
	public Date getTargetCommDate() {
		return TargetCommDate;
	}
	public void setTargetCommDate(Date targetCommDate) {
		TargetCommDate = targetCommDate;
	}
	public String getShopContactNum() {
		return ShopContactNum;
	}
	public void setShopContactNum(String shopContactNum) {
		ShopContactNum = shopContactNum;
	}
	public String getPrepayAmt() {
		return PrepayAmt;
	}
	public void setPrepayAmt(String prepayAmt) {
		PrepayAmt = prepayAmt;
	}
	public String getMovingChrg() {
		return MovingChrg;
	}
	public void setMovingChrg(String movingChrg) {
		MovingChrg = movingChrg;
	}
	public String getWaivedPrepay() {
		return WaivedPrepay;
	}
	public void setWaivedPrepay(String waivedPrepay) {
		WaivedPrepay = waivedPrepay;
	}
	public String getCashFsPrepay() {
		return CashFsPrepay;
	}
	public void setCashFsPrepay(String cashFsPrepay) {
		CashFsPrepay = cashFsPrepay;
	}
	public String getNowTvFormType() {
		return NowTvFormType;
	}
	public void setNowTvFormType(String nowTvFormType) {
		NowTvFormType = nowTvFormType;
	}
	public String getFixedLineNo() {
		return FixedLineNo;
	}
	public void setFixedLineNo(String fixedLineNo) {
		FixedLineNo = fixedLineNo;
	}
	public String getIsCreditCardOffer() {
		return IsCreditCardOffer;
	}
	public void setIsCreditCardOffer(String isCreditCardOffer) {
		IsCreditCardOffer = isCreditCardOffer;
	}
	public String getLangPreference() {
		return LangPreference;
	}
	public void setLangPreference(String langPreference) {
		LangPreference = langPreference;
	}
	public String getProcessCreditCard() {
		return ProcessCreditCard;
	}
	public void setProcessCreditCard(String processCreditCard) {
		ProcessCreditCard = processCreditCard;
	}
	public String getProcessVim() {
		return ProcessVim;
	}
	public void setProcessVim(String processVim) {
		ProcessVim = processVim;
	}
	public String getProcessVms() {
		return ProcessVms;
	}
	public void setProcessVms(String processVms) {
		ProcessVms = processVms;
	}
	public String getProcessWifi() {
		return ProcessWifi;
	}
	public void setProcessWifi(String processWifi) {
		ProcessWifi = processWifi;
	}
	public String getPrimaryAcctNo() {
		return PrimaryAcctNo;
	}
	public void setPrimaryAcctNo(String primaryAcctNo) {
		PrimaryAcctNo = primaryAcctNo;
	}
	public String getTvCredit() {
		return TvCredit;
	}
	public void setTvCredit(String tvCredit) {
		TvCredit = tvCredit;
	}
	public String getBandwidth() {
		return Bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		Bandwidth = bandwidth;
	}
	public String getChooseNowtvCoupon() {
		return ChooseNowtvCoupon;
	}
	public void setChooseNowtvCoupon(String chooseNowtvCoupon) {
		ChooseNowtvCoupon = chooseNowtvCoupon;
	}	
	public String getOtInstallChrg() {
		return OtInstallChrg;
	}
	public void setOtInstallChrg(String otInstallChrg) {
		OtInstallChrg = otInstallChrg;
	}	
	public String getWaivedOtInstallChrg() {
		return WaivedOtInstallChrg;
	}
	public void setWaivedOtInstallChrg(String waivedOtInstallChrg) {
		WaivedOtInstallChrg = waivedOtInstallChrg;
	}
	public void setOtChrgType(String otChrgType) {
		OtChrgType = otChrgType;
	}
	public String getOtChrgType() {
		return OtChrgType;
	}
	public void setCallDate(Date callDate) {
		CallDate = callDate;
	}
	public Date getCallDate() {
		return CallDate;
	}
	public void setPositionNo(String positionNo) {
		PositionNo = positionNo;
	}
	public String getPositionNo() {
		return PositionNo;
	}
	public void setFAXno(String fAXno) {
		FAXno = fAXno;
	}
	public String getFAXno() {
		return FAXno;
	}
	public void setAppMethodDesc(String appMethodDesc) {
		this.appMethodDesc = appMethodDesc;
	}
	public String getAppMethodDesc() {
		return appMethodDesc;
	}
	public String getInstallOtCode() {
		return installOtCode;
	}
	public void setInstallOtCode(String installOtCode) {
		this.installOtCode = installOtCode;
	}
	public String getInstallOTDesc_en() {
		return installOTDesc_en;
	}
	public void setInstallOTDesc_en(String installOTDesc_en) {
		this.installOTDesc_en = installOTDesc_en;
	}
	public String getInstallOTDesc_zh() {
		return installOTDesc_zh;
	}
	public void setInstallOTDesc_zh(String installOTDesc_zh) {
		this.installOTDesc_zh = installOTDesc_zh;
	}
	public void setInstallmentCode(String installmentCode) {
		InstallmentCode = installmentCode;
	}
	public String getInstallmentCode() {
		return InstallmentCode;
	}
	public String getDsType() {
		if(dsType==null)
			dsType="";
		return dsType;
	}
	public void setDsType(String dsType) {
		this.dsType = dsType;
	}
	public String getDsLocation() {
		return dsLocation;
	}
	public void setDsLocation(String dsLocation) {
		this.dsLocation = dsLocation;
	}
	public String getDsCoolOff() {
		return dsCoolOff;
	}
	public void setDsCoolOff(String dsCoolOff) {
		this.dsCoolOff = dsCoolOff;
	}
	public String getDsWaiveCoolOff() {
		return dsWaiveCoolOff;
	}
	public void setDsWaiveCoolOff(String dsWaiveCoolOff) {
		this.dsWaiveCoolOff = dsWaiveCoolOff;
	}
	public String getDsQcCallTime() {
		return dsQcCallTime;
	}
	public void setDsQcCallTime(String dsQcCallTime) {
		this.dsQcCallTime = dsQcCallTime;
	}
	public String getDsWaiveQC() {
		return dsWaiveQC;
	}
	public void setDsWaiveQC(String dsWaiveQC) {
		this.dsWaiveQC = dsWaiveQC;
	}
	public void setSysF(String sysF) {
		this.sysF = sysF;
	}
	public String getSysF() {
		return sysF;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setIsNewCust(String isNewCust) {
		this.isNewCust = isNewCust;
	}
	public String getIsNewCust() {
		return isNewCust;
	}
	public void setARPU_in(String aRPU_in) {
		ARPU_in = aRPU_in;
	}
	public String getARPU_in() {
		return ARPU_in;
	}
	public void setARPU_out(String aRPU_out) {
		ARPU_out = aRPU_out;
	}
	public String getARPU_out() {
		return ARPU_out;
	}
	public String getIsCreditCardOnly() {
		return isCreditCardOnly;
	}
	public void setIsCreditCardOnly(String isCreditCardOnly) {
		this.isCreditCardOnly = isCreditCardOnly;
	}
	public String getIsCashOnly() {
		return isCashOnly;
	}
	public void setIsCashOnly(String isCashOnly) {
		this.isCashOnly = isCashOnly;
	}
	public String getDepositAmt() {
		return depositAmt;
	}
	public void setDepositAmt(String depositAmt) {
		this.depositAmt = depositAmt;
	}
	public Date getQr_submit_date() {
		return qr_submit_date;
	}
	public void setQr_submit_date(Date qr_submit_date) {
		this.qr_submit_date = qr_submit_date;
	}
	public void setIsAdultChannelSelected(String isAdultChannelSelected) {
		this.isAdultChannelSelected = isAdultChannelSelected;
	}
	public String getIsAdultChannelSelected() {
		return isAdultChannelSelected;
	}
	public String getNotMatchPaymentMethod() {
		return notMatchPaymentMethod;
	}
	public void setNotMatchPaymentMethod(String notMatchPaymentMethod) {
		this.notMatchPaymentMethod = notMatchPaymentMethod;
	}
	public BomwebOrderL2JobDTO[] getBomwebOrderL2JobList() {
		return bomwebOrderL2JobList;
	}
	public void setBomwebOrderL2JobList(BomwebOrderL2JobDTO[] bomwebOrderL2JobList) {
		this.bomwebOrderL2JobList = bomwebOrderL2JobList;
	}
	public void setPcdLike100Ind(String pcdLike100Ind) {
		this.pcdLike100Ind = pcdLike100Ind;
	}
	public String getPcdLike100Ind() {
		return pcdLike100Ind;
	}
	public void setPcdNowOneBoxInd(String pcdNowOneBoxInd) {
		this.pcdNowOneBoxInd = pcdNowOneBoxInd;
	}
	public String getPcdNowOneBoxInd() {
		return pcdNowOneBoxInd;
	}
	public String getSpecialRequestCd() {
		return specialRequestCd;
	}
	public void setSpecialRequestCd(String specialRequestCd) {
		this.specialRequestCd = specialRequestCd;
	}
	public void setFieldInd(String fieldInd) {
		this.fieldInd = fieldInd;
	}
	public String getFieldInd() {
		return fieldInd;
	}
	public void setCollectViStb1(String collectViStb1) {
		this.collectViStb1 = collectViStb1;
	}
	public String getCollectViStb1() {
		return collectViStb1;
	}
	
	public void setPreInstallInd(String preInstallInd) {
		this.preInstallInd = preInstallInd;
	}
	public String getPreInstallInd() {
		return preInstallInd;
	}
	public void setMobileOfferInd(String mobileOfferInd) {
		this.mobileOfferInd = mobileOfferInd;
	}
	public String getMobileOfferInd() {
		return mobileOfferInd;
	}
	public void setMrt(String mrt) {
		this.mrt = mrt;
	}
	public String getMrt() {
		return mrt;
	}
	public void setPreUseInd(String preUseInd) {
		this.preUseInd = preUseInd;
	}
	public String getPreUseInd() {
		return preUseInd;
	}
	public void setLtsServiceInd(String ltsServiceInd) {
		this.ltsServiceInd = ltsServiceInd;
	}
	public String getLtsServiceInd() {
		return ltsServiceInd;
	}
	public void setRelated_FSA(String related_FSA) {
		this.related_FSA = related_FSA;
	}
	public String getRelated_FSA() {
		return related_FSA;
	}
	public void setRide_on_FSA_reason_Cd(String ride_on_FSA_reason_Cd) {
		this.ride_on_FSA_reason_Cd = ride_on_FSA_reason_Cd;
	}
	public String getRide_on_FSA_reason_Cd() {
		return ride_on_FSA_reason_Cd;
	}
	public void setServiceWaiver(String serviceWaiver) {
		this.serviceWaiver = serviceWaiver;
	}
	public String getServiceWaiver() {
		return serviceWaiver;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getMode() {
		return mode;
	}
	public void setInstallOtQty(String installOtQty) {
		this.installOtQty = installOtQty;
	}
	public String getInstallOtQty() {
		return installOtQty;
	}
	public void setSpecialOTCId(String specialOTCId) {
		this.specialOTCId = specialOTCId;
	}
	public String getSpecialOTCId() {
		return specialOTCId;
	}
	public void setCollectViStb2(String collectViStb2) {
		this.collectViStb2 = collectViStb2;
	}
	public String getCollectViStb2() {
		return collectViStb2;
	}
	public String getIsIncludeGoodsDelivery() {
		return isIncludeGoodsDelivery;
	}
	public void setIsIncludeGoodsDelivery(String isIncludeGoodsDelivery) {
		this.isIncludeGoodsDelivery = isIncludeGoodsDelivery;
	}
	public String getHkqrString() {
		return hkqrString;
	}
	public void setHkqrString(String hkqrString) {
		this.hkqrString = hkqrString;
	}
	public String getHkqrUrl() {
		return hkqrUrl;
	}
	public void setHkqrUrl(String hkqrUrl) {
		this.hkqrUrl = hkqrUrl;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getCashPayMtdType() {
		return cashPayMtdType;
	}
	public void setCashPayMtdType(String cashPayMtdType) {
		this.cashPayMtdType = cashPayMtdType;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getFpsReferenceId() {
		return fpsReferenceId;
	}
	public void setFpsReferenceId(String fpsReferenceId) {
		this.fpsReferenceId = fpsReferenceId;
	}
	
}
