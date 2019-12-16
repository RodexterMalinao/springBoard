package com.bomwebportal.ims.dto.ui;

import java.util.List;

import com.bomwebportal.ims.dto.DiscountedOtInstallChrgDTO;
import com.bomwebportal.ims.dto.SalesReferrerDTO;
import com.bomwebportal.ims.dto.BomwebOTDTO;

public class ImsPaymentUI extends PaymentUI{
	private String ceksSubmit;
	private String expiryMonth;
	private String expiryYear;
	private String emailAddr;
	private String billMedia;
	private String prepayAmt;
	private String prepayCc;
	private String prepayCash;
	private String cashFsPrepay;
	private String processCreditCard;
	private String isCcOffer;
	private String idDocType;
	private String shopCd;
	private String salesCd;
	private String salesName;
	private String salesContactNum;
	private String sReasonCd;
	private String cReasonCd;
	private String submitTag;
	private String orderId;
	//private String loginId;
	private String month;
	private String year;
	private String nowTvFormType;
	private String registerName;
	private String orderActionInd;
	private String orderStatus;
	private String otInstallChrg;       //TT
	private String installOtCode;//gary
	private String installOtQty;
	private String installOTDesc_en;//gary
	private String installOTDesc_zh;//gary
	private String installmentCode;//gary
	private String otChrgType;			// Anthony
	private String isValidForWaive;     //TT
	private String waivedOtInstallChrg; //TT
	private String installInstallmentAmt;	//Kinman
	private String installInstallmentMth;	//Kinman
	private String isValidForInstallInstallment;	//Kinman	
	private String Tempinstallmentplan; //kinman
	private String InstallPlanDisplay; //kinman
	private String errorCode;
	private String errorText;
	
	private String cashApproval;
	private String waiveApproval;
	private String waiveOtApproval;     //TT
	private String waivedPrepay;
	private String preInstallInd;
	
	private String installInstalmentInd;
	private String onlyOneInstallInstallmentPlanInd; //kinman
	
	private String approvalRequested;
	
	private List<DiscountedOtInstallChrgDTO> discountedOtInstallChrgList;
	private double discountedOtInstallAmt;
	private String discountOTCApproval;
	private String requestSelectDiscountOTC;
	private String callDate;
	private String callTime;
	private String appMethod;
	private String appMethodDesc;
	private String sourcecode;
	private String pno;
	
	private String isPT;
	private String isCC;
	private String isDS; //Added by Andy
	private String isCcTv;
	private String isAmendOrder;
	
	//kinman20140428	
	private String sourceCodeDefault;
	private String appMethodCdDefault;
	
	//NOWTVSALES
	private String depositAmt;
	
	private String existingBillingAddress;
	private String existingBillingEmail;
	private String existingBillingMedia;
	private String keepExistingBillingAddress;
	private String updateBillingMethod;
	private String isFreeFormat;
	private CustAddrUI billingAddress;
	private String billingQuickSearch;
	private String freeFormatAddress1;
	private String freeFormatAddress2;
	private String freeFormatAddress3;
	private String freeFormatAddress4;
	
	private String mobileOfferInd;
	
	private String mode;
	
	private List<BomwebOTDTO> bomwebOTList;		//override ISF/ASF list
	private String specialOTString;
	private String specialOTCWaivedInd;
	private String hideOriginalOTCInd;
	
	private SalesReferrerDTO salesReferrerDTO= new SalesReferrerDTO();
	
	public SalesReferrerDTO getSalesReferrerDTO() {
		return salesReferrerDTO;
	}

	public void setSalesReferrerDTO(SalesReferrerDTO salesReferrerDTO) {
		this.salesReferrerDTO = salesReferrerDTO;
	}
	
/*	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
*/
	
	
	
	public String getAppMethodCdDefault() {
		return appMethodCdDefault;
	}

	public void setAppMethodCdDefault(String appMethodCdDefault) {
		this.appMethodCdDefault = appMethodCdDefault;
	}

	public String getSourceCodeDefault() {
		return sourceCodeDefault;
	}

	public void setSourceCodeDefault(String sourceCodeDefault) {
		this.sourceCodeDefault = sourceCodeDefault;
	}

	public List<DiscountedOtInstallChrgDTO> getDiscountedOtInstallChrgList() {
		return discountedOtInstallChrgList;
	}

	public void setDiscountedOtInstallChrgList(
			List<DiscountedOtInstallChrgDTO> discountedOtInstallChrgList) {
		this.discountedOtInstallChrgList = discountedOtInstallChrgList;
	}

	public double getDiscountedOtInstallAmt() {
		return discountedOtInstallAmt;
	}

	public void setDiscountedOtInstallAmt(double discountedOtInstallAmt) {
		this.discountedOtInstallAmt = discountedOtInstallAmt;
	}

	public String getDiscountOTCApproval() {
		return discountOTCApproval;
	}

	public void setDiscountOTCApproval(String discountOTCApproval) {
		this.discountOTCApproval = discountOTCApproval;
	}

	public String getRequestSelectDiscountOTC() {
		return requestSelectDiscountOTC;
	}

	public void setRequestSelectDiscountOTC(String requestSelectDiscountOTC) {
		this.requestSelectDiscountOTC = requestSelectDiscountOTC;
	}

	public String getCallDate() {
		return callDate;
	}

	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
	}

	public String getAppMethod() {
		return appMethod;
	}

	public void setAppMethod(String appMethod) {
		this.appMethod = appMethod;
	}

	public String getSourcecode() {
		return sourcecode;
	}

	public void setSourcecode(String sourcecode) {
		this.sourcecode = sourcecode;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getOrderActionInd() {
		return orderActionInd;
	}

	public String getOnlyOneInstallInstallmentPlanInd() {
		return onlyOneInstallInstallmentPlanInd;
	}

	public void setOnlyOneInstallInstallmentPlanInd(
			String onlyOneInstallInstallmentPlanInd) {
		this.onlyOneInstallInstallmentPlanInd = onlyOneInstallInstallmentPlanInd;
	}

	public void setOrderActionInd(String orderActionInd) {
		this.orderActionInd = orderActionInd;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getNowTvFormType() {
		return nowTvFormType;
	}

	public void setNowTvFormType(String nowTvFormType) {
		this.nowTvFormType = nowTvFormType;
	}
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCeksSubmit() {
		return ceksSubmit;
	}

	public void setCeksSubmit(String ceksSubmit) {
		this.ceksSubmit = ceksSubmit;
	}

	public String getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public String getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getBillMedia() {
		return billMedia;
	}

	public void setBillMedia(String billMedia) {
		this.billMedia = billMedia;
	}

	public String getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(String prepayAmt) {
		this.prepayAmt = prepayAmt;
	}

	public String getPrepayCc() {
		return prepayCc;
	}

	public void setPrepayCc(String prepayCc) {
		this.prepayCc = prepayCc;
	}

	public String getPrepayCash() {
		return prepayCash;
	}

	public void setPrepayCash(String prepayCash) {
		this.prepayCash = prepayCash;
	}

	public String getCashFsPrepay() {
		return cashFsPrepay;
	}

	public void setCashFsPrepay(String cashFsPrepay) {
		this.cashFsPrepay = cashFsPrepay;
	}

	public String getProcessCreditCard() {
		return processCreditCard;
	}

	public void setProcessCreditCard(String processCreditCard) {
		this.processCreditCard = processCreditCard;
	}

	public String getIsCcOffer() {
		return isCcOffer;
	}

	public void setIsCcOffer(String isCcOffer) {
		this.isCcOffer = isCcOffer;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public String getShopCd() {
		return shopCd;
	}

	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	public String getSalesCd() {
		return salesCd;
	}

	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getSalesContactNum() {
		return salesContactNum;
	}

	public void setSalesContactNum(String salesContactNum) {
		this.salesContactNum = salesContactNum;
	}

	public String getsReasonCd() {
		return sReasonCd;
	}

	public void setsReasonCd(String sReasonCd) {
		this.sReasonCd = sReasonCd;
	}

	public String getcReasonCd() {
		return cReasonCd;
	}

	public void setcReasonCd(String cReasonCd) {
		this.cReasonCd = cReasonCd;
	}

	public String getSubmitTag() {
		return submitTag;
	}

	public void setSubmitTag(String submitTag) {
		this.submitTag = submitTag;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCashApproval() {
		return cashApproval;
	}

	public void setCashApproval(String cashApproval) {
		this.cashApproval = cashApproval;
	}

	public String getWaiveApproval() {
		return waiveApproval;
	}

	public void setWaiveApproval(String waiveApproval) {
		this.waiveApproval = waiveApproval;
	}
	
	public String getWaiveOtApproval() {
		return waiveOtApproval;
	}

	public void setWaiveOtApproval(String waiveOtApproval) {
		this.waiveOtApproval = waiveOtApproval;
	}

	public String getWaivedPrepay() {
		return waivedPrepay;
	}

	public void setWaivedPrepay(String waivedPrepay) {
		this.waivedPrepay = waivedPrepay;
	}

	public String getPreInstallInd() {
		return preInstallInd;
	}

	public void setPreInstallInd(String preInstallInd) {
		this.preInstallInd = preInstallInd;
	}

	public String getInstallInstalmentInd() {
		return installInstalmentInd;
	}

	public void setInstallInstalmentInd(String installInstalmentInd) {
		this.installInstalmentInd = installInstalmentInd;
	}

	public String getApprovalRequested() {
		return approvalRequested;
	}

	public void setApprovalRequested(String approvalRequested) {
		this.approvalRequested = approvalRequested;
	}	
	
	public String getOtInstallChrg() {
		return otInstallChrg;
	}

	public void setOtInstallChrg(String otInstallChrg) {
		this.otInstallChrg = otInstallChrg;
	}
	
	public String getIsValidForWaive() {
		return isValidForWaive;
	}

	public void setIsValidForWaive(String isValidForWaive) {
		this.isValidForWaive = isValidForWaive;
	}
	
	public String getWaivedOtInstallChrg() {
		return waivedOtInstallChrg;
	}

	public void setWaivedOtInstallChrg(String waivedOtInstallChrg) {
		this.waivedOtInstallChrg = waivedOtInstallChrg;
	}
	
	
	//Kinman
	
	public String getInstallInstallmentAmt() {
		return installInstallmentAmt;
	}

	public void setInstallInstallmentAmt(String installInstallmentAmt) {
		this.installInstallmentAmt = installInstallmentAmt;
	}

	public String getInstallInstallmentMth() {
		return installInstallmentMth;
	}

	public void setInstallInstallmentMth(String installInstallmentMth) {
		this.installInstallmentMth = installInstallmentMth;
	}
	
	public String getIsValidForInstallInstallment() {
		return isValidForInstallInstallment;
	}

	public void setIsValidForInstallInstallment(String isValidForInstallInstallment) {
		this.isValidForInstallInstallment = isValidForInstallInstallment;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getTempinstallmentplan() {
		return Tempinstallmentplan;
	}

	public void setTempinstallmentplan(String tempinstallmentplan) {
		Tempinstallmentplan = tempinstallmentplan;
	}
	
	public String getInstallPlanDisplay() {
		return InstallPlanDisplay;
	}

	public void setInstallPlanDisplay(String installPlanDisplay) {
		InstallPlanDisplay = installPlanDisplay;
	}

	public void setIsPT(String isPT) {
		this.isPT = isPT;
	}

	public String getIsPT() {
		return isPT;
	}

	public void setIsCC(String isCC) {
		this.isCC = isCC;
	}

	public String getIsCC() {
		return isCC;
	}

	public void setOtChrgType(String otChrgType) {
		this.otChrgType = otChrgType;
	}

	public String getOtChrgType() {
		return otChrgType;
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
		this.installmentCode = installmentCode;
	}

	public String getInstallmentCode() {
		return installmentCode;
	}

	public void setIsDS(String isDS) {
		this.isDS = isDS;
	}

	public String getIsDS() {
		return isDS;
	}

	public void setIsAmendOrder(String isAmendOrder) {
		this.isAmendOrder = isAmendOrder;
	}

	public String getIsAmendOrder() {
		return isAmendOrder;
	}

	public String getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(String depositAmt) {
		this.depositAmt = depositAmt;
	}

	public String getExistingBillingAddress() {
		return existingBillingAddress;
	}

	public void setExistingBillingAddress(String existingBillingAddress) {
		this.existingBillingAddress = existingBillingAddress;
	}
	
	public String getExistingBillingEmail() {
		return existingBillingEmail;
	}

	public void setExistingBillingEmail(String existingBillingEmail) {
		this.existingBillingEmail = existingBillingEmail;
	}

	public String getExistingBillingMedia() {
		return existingBillingMedia;
	}

	public void setExistingBillingMedia(String existingBillingMedia) {
		this.existingBillingMedia = existingBillingMedia;
	}

	public String getKeepExistingBillingAddress() {
		return keepExistingBillingAddress;
	}

	public void setKeepExistingBillingAddress(String keepExistingBillingAddress) {
		this.keepExistingBillingAddress = keepExistingBillingAddress;
	}

	public String getIsFreeFormat() {
		return isFreeFormat;
	}

	public void setIsFreeFormat(String isFreeFormat) {
		this.isFreeFormat = isFreeFormat;
	}

	public CustAddrUI getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(CustAddrUI billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingQuickSearch() {
		return billingQuickSearch;
	}

	public void setBillingQuickSearch(String billingQuickSearch) {
		this.billingQuickSearch = billingQuickSearch;
	}

	public String getFreeFormatAddress1() {
		return freeFormatAddress1;
	}

	public void setFreeFormatAddress1(String freeFormatAddress1) {
		this.freeFormatAddress1 = freeFormatAddress1;
	}

	public String getFreeFormatAddress2() {
		return freeFormatAddress2;
	}

	public void setFreeFormatAddress2(String freeFormatAddress2) {
		this.freeFormatAddress2 = freeFormatAddress2;
	}

	public String getFreeFormatAddress3() {
		return freeFormatAddress3;
	}

	public void setFreeFormatAddress3(String freeFormatAddress3) {
		this.freeFormatAddress3 = freeFormatAddress3;
	}

	public String getFreeFormatAddress4() {
		return freeFormatAddress4;
	}

	public void setFreeFormatAddress4(String freeFormatAddress4) {
		this.freeFormatAddress4 = freeFormatAddress4;
	}

	public String getUpdateBillingMethod() {
		return updateBillingMethod;
	}

	public void setUpdateBillingMethod(String updateBillingMethod) {
		this.updateBillingMethod = updateBillingMethod;
	}

	public void setMobileOfferInd(String mobileOfferInd) {
		this.mobileOfferInd = mobileOfferInd;
	}

	public String getMobileOfferInd() {
		return mobileOfferInd;
	}

	public void setIsCcTv(String isCcTv) {
		this.isCcTv = isCcTv;
	}

	public String getIsCcTv() {
		return isCcTv;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}

	public void setBomwebOTList(List<BomwebOTDTO> bomwebOTList) {
		this.bomwebOTList = bomwebOTList;
	}

	public List<BomwebOTDTO> getBomwebOTList() {
		return bomwebOTList;
	}

	public void setInstallOtQty(String installOtQty) {
		this.installOtQty = installOtQty;
	}

	public String getInstallOtQty() {
		return installOtQty;
	}

	public void setSpecialOTString(String specialOTString) {
		this.specialOTString = specialOTString;
	}

	public String getSpecialOTString() {
		return specialOTString;
	}

	public void setSpecialOTCWaivedInd(String specialOTCWaivedInd) {
		this.specialOTCWaivedInd = specialOTCWaivedInd;
	}

	public String getSpecialOTCWaivedInd() {
		return specialOTCWaivedInd;
	}

	public void setHideOriginalOTCInd(String hideOriginalOTCInd) {
		this.hideOriginalOTCInd = hideOriginalOTCInd;
	}

	public String getHideOriginalOTCInd() {
		return hideOriginalOTCInd;
	}
	
	
}
