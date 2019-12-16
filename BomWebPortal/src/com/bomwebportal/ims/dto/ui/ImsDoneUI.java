package com.bomwebportal.ims.dto.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.BomwebSubscribedOfferImsDTO;
import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.ims.dto.ImsCollectDocDTO;
import com.bomwebportal.ims.dto.ImsRptBasketDtlDTO;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;
import com.bomwebportal.ims.dto.ImsRptServicePlanDetailDTO;

public class ImsDoneUI extends CustomerUI{
	private String ActionInd;
	
	private String fixedLineNo;
	private String loginId;
	private String appntStartDateDisplay;
	private String appntEndDateDisplay;
	private String appDate;
	private String targetCommDate;
	private String cashFsPrepay;
	private String prepayAmt;
	private String dobStr;
	private String orderId;
	private String signOffDate;
	private String submitDate;
	private String otInstallChrg;
	private String otChrgType;
	private String waivedOtInstallChrg;
	private String reasonCd;
	private String callDate;
	private String callTime;
	private String positionNo;
	private String FAXno;
	private String installOtCode;//gary
	private String installOTDesc_en;//gary
	private String installOTDesc_zh;//gary
	
	private String clubOptOutReasonDesc;
	
	//ims direct sales
	private String shopCode;
	private String transactionCd;
	private AppointmentUI appntUI;
	private String srdAvailble;
	private String l1ordnum;
	
	private String newtvpricingind;
	private NowTVAddUI nowTVAddUI;
	
	//20150305 martin
	private int ntvConnType;
	
	//Tony
	private String cReasonCd;
	private String submitTag;
	private String cancelReason;
	
	//NOWTVSALES
	private List<BasketUI> ftaPack;
	private List<BasketUI> selectedBaskets;
	private String encodedId;
	private String qr_submit_day;
	private String qr_submit_time_h;
	private String qr_submit_time_m;
	private String qr_dep_accnt;
	private String resendAfEmail;
	private String resendAfSMS;
	private List<ImsRptBasketItemDTO> ntvPgmItemListGift;
	private List<ImsRptBasketItemDTO> ntvPgmItemListOneTime;
	
	private String billingAddressStr;
	
	private List<String> optList;
	
	private String inOfferMthFixTotal;
	private String inOfferMth2MthTotal;
	
	private BomwebSubscribedOfferImsDTO[] subscribedOffersIms;

	private String serviceEffectiveDateStrDMY;	
	private String isThirdPartyApplication;
	private String applicantTitle;
	private String applicantLastName;
	private String applicantFirstName;
	private String applicantDocType;
	private String applicantIDNum;
	private String relationshipwithCustomer;
	private String otherRelationship;
	private String applicantContactNo;
	
	// martin, 20170717, BOM2017086
	private List<String> ntvCampCdList;
	private String isNtvCallListOffer;
	private String isNtvSwitchingOffer;
	private String ntvCallListId;
	private String hasSupportDoc;
	// 20190610, BOM2019039
	private String cashPayMtdType;
	
	public int getNtvConnType() {
	    return ntvConnType;
	}

	public void setNtvConnType(int ntvConnType) {
	    this.ntvConnType = ntvConnType;
	}
	
	public NowTVAddUI getNowTVAddUI() {
		return nowTVAddUI;
	}
	public void setNowTVAddUI(NowTVAddUI nowTVAddUI) {
		this.nowTVAddUI = nowTVAddUI;
	}
	public String getNewtvpricingind() {
		return newtvpricingind;
	}
	public void setNewtvpricingind(String newtvpricingind) {
		this.newtvpricingind = newtvpricingind;
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
	private String warrPeriod;
	private ImsRptServicePlanDetailDTO servPlanDto;
	private ImsRptBasketDtlDTO basketDtl;
	private List<ImsRptBasketItemDTO> ntvPgmItemList;
	
	
	//kinman
	private CollectMethod collectMethod;
	private DisMode disMode;
	private String esigEmailAddr;
	private EsigEmailLang esigEmailLang;
	private String langPreference;
	private String emailAddr;
	private String SMSno;
	
	private String isCC;
	private String isPT;
	private String orderStatus;
	
	
/*	private String basketTitle;
	private String basketDetail;
	private String imagePath;
	private String recurrentAmt;
	private String mthToMthRate;
	private String mthFix;
	private String mthToMth;
	private String contractPeriod;
*/
	private double totalRecurrentAmt;
	private double totalMthToMthRate;
	
	private InstallAddrUI installAddress;
	private CustAddrUI billingAddress;
	private AppointmentUI appointment;
	
	private String pendingOrder;
	private String signoffedOrder;
	private String noBooking;

	private List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs;

	public List<AllOrdDocAssgnDTO> getAllOrdDocAssgnDTOs() {
		return allOrdDocAssgnDTOs;
	}
	public void setAllOrdDocAssgnDTOs(List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs) {
		this.allOrdDocAssgnDTOs = allOrdDocAssgnDTOs;
	}
	private String edfRef;
	
	public void setEdfRef(String edfRef) {
		this.edfRef = edfRef;
	}
	public String getEdfRef() {
		return edfRef;
	}
	private List<ImsCollectDocDTO> imsCollectDocDTOs;

	public void setImsCollectDocDTOs(List<ImsCollectDocDTO> imsCollectDocDTOs) {
		this.imsCollectDocDTOs = imsCollectDocDTOs;
	}
	public List<ImsCollectDocDTO> getImsCollectDocDTOs() {
		return imsCollectDocDTOs;
	}

	public String getPendingOrder() {
		return pendingOrder;
	}

	public void setPendingOrder(String pendingOrder) {
		this.pendingOrder = pendingOrder;
	}

	public String getFixedLineNo() {
		return fixedLineNo;
	}

	public void setFixedLineNo(String fixedLineNo) {
		this.fixedLineNo = fixedLineNo;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getAppntStartDateDisplay() {
		return appntStartDateDisplay;
	}

	public void setAppntStartDateDisplay(String appntStartDateDisplay) {
		this.appntStartDateDisplay = appntStartDateDisplay;
	}

	public String getAppntEndDateDisplay() {
		return appntEndDateDisplay;
	}

	public void setAppntEndDateDisplay(String appntEndDateDisplay) {
		this.appntEndDateDisplay = appntEndDateDisplay;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getTargetCommDate() {
		return targetCommDate;
	}

	public void setTargetCommDate(String targetCommDate) {
		this.targetCommDate = targetCommDate;
	}

	public String getCashFsPrepay() {
		return cashFsPrepay;
	}

	public void setCashFsPrepay(String cashFsPrepay) {
		this.cashFsPrepay = cashFsPrepay;
	}

	public String getPrepayAmt() {
		return prepayAmt;
	}

	public void setPrepayAmt(String prepayAmt) {
		this.prepayAmt = prepayAmt;
	}

	public String getDobStr() {
		return dobStr;
	}

	public void setDobStr(String dobStr) {
		this.dobStr = dobStr;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getSignOffDate() {
		return signOffDate;
	}

	public void setSignOffDate(String signOffDate) {
		this.signOffDate = signOffDate;
	}

	public String getWarrPeriod() {
		return warrPeriod;
	}

	public void setWarrPeriod(String warrPeriod) {
		this.warrPeriod = warrPeriod;
	}

	public ImsRptServicePlanDetailDTO getServPlanDto() {
		return servPlanDto;
	}

	public void setServPlanDto(ImsRptServicePlanDetailDTO servPlanDto) {
		this.servPlanDto = servPlanDto;
	}

	public ImsRptBasketDtlDTO getBasketDtl() {
		return basketDtl;
	}

	public void setBasketDtl(ImsRptBasketDtlDTO basketDtl) {
		this.basketDtl = basketDtl;
	}

	public List<ImsRptBasketItemDTO> getNtvPgmItemList() {
		return ntvPgmItemList;
	}

	public void setNtvPgmItemList(List<ImsRptBasketItemDTO> ntvPgmItemList) {
		this.ntvPgmItemList = ntvPgmItemList;
	}
/*
	public String getBasketTitle() {
		return basketTitle;
	}

	public void setBasketTitle(String basketTitle) {
		this.basketTitle = basketTitle;
	}

	public String getBasketDetail() {
		return basketDetail;
	}

	public void setBasketDetail(String basketDetail) {
		this.basketDetail = basketDetail;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getRecurrentAmt() {
		return recurrentAmt;
	}

	public void setRecurrentAmt(String recurrentAmt) {
		this.recurrentAmt = recurrentAmt;
	}

	public String getMthToMthRate() {
		return mthToMthRate;
	}

	public void setMthToMthRate(String mthToMthRate) {
		this.mthToMthRate = mthToMthRate;
	}

	public String getMthFix() {
		return mthFix;
	}

	public void setMthFix(String mthFix) {
		this.mthFix = mthFix;
	}

	public String getMthToMth() {
		return mthToMth;
	}

	public void setMthToMth(String mthToMth) {
		this.mthToMth = mthToMth;
	}

	public String getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
*/
	public double getTotalRecurrentAmt() {
		return totalRecurrentAmt;
	}

	public void setTotalRecurrentAmt(double totalRecurrentAmt) {
		this.totalRecurrentAmt = totalRecurrentAmt;
	}

	public double getTotalMthToMthRate() {
		return totalMthToMthRate;
	}

	public void setTotalMthToMthRate(double totalMthToMthRate) {
		this.totalMthToMthRate = totalMthToMthRate;
	}

	public AppointmentUI getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentUI appointment) {
		this.appointment = appointment;
	}

	public InstallAddrUI getInstallAddress() {
		return installAddress;
	}

	public void setInstallAddress(InstallAddrUI installAddress) {
		this.installAddress = installAddress;
	}

	public CustAddrUI getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(CustAddrUI billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getActionInd() {
		return ActionInd;
	}

	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}

	public String getSignoffedOrder() {
		return signoffedOrder;
	}

	public void setSignoffedOrder(String signoffedOrder) {
		this.signoffedOrder = signoffedOrder;
	}

	public String getNoBooking() {
		return noBooking;
	}

	public void setNoBooking(String noBooking) {
		this.noBooking = noBooking;
	}
	
	
	
	//Kinman
	public CollectMethod getCollectMethod() {
		return collectMethod;
	}

	public void setCollectMethod(CollectMethod collectMethod) {
		this.collectMethod = collectMethod;
	}

	public DisMode getDisMode() {
		return disMode;
	}

	public void setDisMode(DisMode disMode) {
		this.disMode = disMode;
	}

	public String getEsigEmailAddr() {
		return esigEmailAddr;
	}

	public String getOtInstallChrg() {
		return otInstallChrg;
	}

	public void setOtInstallChrg(String otInstallChrg) {
		this.otInstallChrg = otInstallChrg;
	}

	public String getWaivedOtInstallChrg() {
		return waivedOtInstallChrg;
	}

	public void setWaivedOtInstallChrg(String waivedOtInstallChrg) {
		this.waivedOtInstallChrg = waivedOtInstallChrg;
	}



	public void setEsigEmailAddr(String esigEmailAddr) {
		this.esigEmailAddr = esigEmailAddr;
	}

	public EsigEmailLang getEsigEmailLang() {
		return esigEmailLang;
	}

	public void setEsigEmailLang(EsigEmailLang esigEmailLang) {
		this.esigEmailLang = esigEmailLang;
	}

	public String getLangPreference() {
		return langPreference;
	}


	public void setLangPreference(String langPreference) {
		this.langPreference = langPreference;
	}
	public void setIsCC(String isCC) {
		this.isCC = isCC;
	}
	public String getIsCC() {
		return isCC;
	}
	public void setIsPT(String isPT) {
		this.isPT = isPT;
	}
	public String getIsPT() {
		return isPT;
	}
	public void setOtChrgType(String otChrgType) {
		this.otChrgType = otChrgType;
	}
	public String getOtChrgType() {
		return otChrgType;
	}
	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}
	public String getReasonCd() {
		return reasonCd;
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
	public String getPositionNo() {
		return positionNo;
	}
	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setFAXno(String fAXno) {
		FAXno = fAXno;
	}
	public String getFAXno() {
		return FAXno;
	}		
	
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getTransactionCd() {
		return transactionCd;
	}
	public void setTransactionCd(String transactionCd) {
		this.transactionCd = transactionCd;
	}
	public AppointmentUI getAppntUI() {
		return appntUI;
	}
	public void setAppntUI(AppointmentUI appntUI) {
		this.appntUI = appntUI;
	}	
	
	///////// ims direct sales only
	public String getErrorMsg() {
		return this.appntUI.getErrorMsg();
	}

	public void setErrorMsg(String errorMsg) {
		this.appntUI.setErrorMsg(errorMsg);
	} 
	
	public String getSerialNum() {
		return this.appntUI.getSerialNum();
	}
	public void setSerialNum(String serialNum) {
		this.appntUI.setSerialNum(serialNum);
	}
	
	public String getTargetInstallDate() {
		return this.appntUI.getTargetInstallDate();
	}

	public void setTargetInstallDate(String targetInstallDate) {
		this.appntUI.setTargetInstallDate(targetInstallDate);
	}
	
	public String getTimeSlot() {
		return this.appntUI.getTimeSlot();
	}

	public void setTimeSlot(String timeSlot) {
		this.appntUI.setTimeSlot(timeSlot);
	}
	
	public String getTimeSlotDisplay() {
		return this.appntUI.getTimeSlotDisplay();
	}

	public void setTimeSlotDisplay(String timeSlotDisplay) {
		this.appntUI.setTimeSlotDisplay(timeSlotDisplay);
	}
	
	public String getBmoRmk() {
		return this.appntUI.getBmoRmk();
	}

	public void setBmoRmk(String bmoRmk) {
		this.appntUI.setBmoRmk(bmoRmk);
	}
	
	public void setWaiveCoolingOffPeriod(String waiveCoolingOffPeriod) {
		this.appntUI.setWaiveCoolingOffPeriod(waiveCoolingOffPeriod);
	}

	public String getWaiveCoolingOffPeriod() {
		return this.appntUI.getWaiveCoolingOffPeriod();
	}
	
	public String getAdditionRemarks() {
		return this.appntUI.getAdditionRemarks();
	}

	public void setAdditionRemarks(String additionRemarks) {
		this.appntUI.setAdditionRemarks(additionRemarks);
	}
	////////////ims direct sales only(end)
	public void setSrdAvailble(String srdAvailble) {
		this.srdAvailble = srdAvailble;
	}
	public String getSrdAvailble() {
		return srdAvailble;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setSMSno(String sMSno) {
		SMSno = sMSno;
	}
	public String getSMSno() {
		return SMSno;
	}
	public void setL1ordnum(String l1ordnum) {
		this.l1ordnum = l1ordnum;
	}
	public String getL1ordnum() {
		return l1ordnum;
	}

	public void setcReasonCd(String cReasonCd) {
		this.cReasonCd = cReasonCd;
	}

	public String getcReasonCd() {
		return cReasonCd;
	}

	public void setSubmitTag(String submitTag) {
		this.submitTag = submitTag;
	}

	public String getSubmitTag() {
		return submitTag;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public List<BasketUI> getFtaPack() {
		return ftaPack;
	}

	public void setFtaPack(List<BasketUI> ftaPack) {
		this.ftaPack = ftaPack;
	}

	public List<BasketUI> getSelectedBaskets() {
		return selectedBaskets;
	}

	public void setSelectedBaskets(List<BasketUI> selectedBaskets) {
		this.selectedBaskets = selectedBaskets;
	}
	
	public List<GiftUI> getGiftList(){
		 List<GiftUI> g = new ArrayList<GiftUI>();
		 
		 if (selectedBaskets != null){
			 for(BasketUI b :selectedBaskets){
				 g.addAll(b.getGiftList());
			 }
		 }
		 return g;
	}

	public String getEncodedId() {
		return encodedId;
	}

	public void setEncodedId(String encodedId) {
		this.encodedId = encodedId;
	}

	public String getQr_submit_day() {
		return qr_submit_day;
	}

	public void setQr_submit_day(String qr_submit_day) {
		this.qr_submit_day = qr_submit_day;
	}

	public String getQr_submit_time_h() {
		return qr_submit_time_h;
	}

	public void setQr_submit_time_h(String qr_submit_time_h) {
		this.qr_submit_time_h = qr_submit_time_h;
	}

	public String getQr_submit_time_m() {
		return qr_submit_time_m;
	}

	public void setQr_submit_time_m(String qr_submit_time_m) {
		this.qr_submit_time_m = qr_submit_time_m;
	}

	public String getResendAfEmail() {
		return resendAfEmail;
	}

	public void setResendAfEmail(String resendAfEmail) {
		this.resendAfEmail = resendAfEmail;
	}

	public String getResendAfSMS() {
		return resendAfSMS;
	}

	public void setResendAfSMS(String resendAfSMS) {
		this.resendAfSMS = resendAfSMS;
	}

	public String getQr_dep_accnt() {
		return qr_dep_accnt;
	}

	public void setQr_dep_accnt(String qr_dep_accnt) {
		this.qr_dep_accnt = qr_dep_accnt;
	}

	public List<ImsRptBasketItemDTO> getNtvPgmItemListGift() {
		return ntvPgmItemListGift;
	}

	public void setNtvPgmItemListGift(List<ImsRptBasketItemDTO> ntvPgmItemListGift) {
		this.ntvPgmItemListGift = ntvPgmItemListGift;
	}

	public List<ImsRptBasketItemDTO> getNtvPgmItemListOneTime() {
		return ntvPgmItemListOneTime;
	}

	public void setNtvPgmItemListOneTime(
			List<ImsRptBasketItemDTO> ntvPgmItemListOneTime) {
		this.ntvPgmItemListOneTime = ntvPgmItemListOneTime;
	}

	public void setClubOptOutReasonDesc(String clubOptOutReasonDesc) {
		this.clubOptOutReasonDesc = clubOptOutReasonDesc;
	}

	public String getClubOptOutReasonDesc() {
		return clubOptOutReasonDesc;
	}

	public List<String> getOptList() {
		return optList;
	}

	public void setOptList(List<String> optList) {
		this.optList = optList;
	}

	public String getBillingAddressStr() {
		return billingAddressStr;
	}

	public void setBillingAddressStr(String billingAddressStr) {
		this.billingAddressStr = billingAddressStr;
	}

	public String getInOfferMthFixTotal() {
		return inOfferMthFixTotal;
	}

	public void setInOfferMthFixTotal(String inOfferMthFixTotal) {
		this.inOfferMthFixTotal = inOfferMthFixTotal;
	}

	public String getInOfferMth2MthTotal() {
		return inOfferMth2MthTotal;
	}

	public void setInOfferMth2MthTotal(String inOfferMth2MthTotal) {
		this.inOfferMth2MthTotal = inOfferMth2MthTotal;
	}

	public BomwebSubscribedOfferImsDTO[] getSubscribedOffersIms() {
		return subscribedOffersIms;
	}

	public void setSubscribedOffersIms(
			BomwebSubscribedOfferImsDTO[] subscribedOffersIms) {
		this.subscribedOffersIms = subscribedOffersIms;
	}

	public String getServiceEffectiveDateStrDMY() {
		return serviceEffectiveDateStrDMY;
	}

	public void setServiceEffectiveDateStrDMY(String serviceEffectiveDateStrDMY) {
		this.serviceEffectiveDateStrDMY = serviceEffectiveDateStrDMY;
	}

	public String getIsThirdPartyApplication() {
		return isThirdPartyApplication;
	}

	public void setIsThirdPartyApplication(String isThirdPartyApplication) {
		this.isThirdPartyApplication = isThirdPartyApplication;
	}

	public String getApplicantTitle() {
		return applicantTitle;
	}

	public void setApplicantTitle(String applicantTitle) {
		this.applicantTitle = applicantTitle;
	}

	public String getApplicantLastName() {
		return applicantLastName;
	}

	public void setApplicantLastName(String applicantLastName) {
		this.applicantLastName = applicantLastName;
	}

	public String getApplicantFirstName() {
		return applicantFirstName;
	}

	public void setApplicantFirstName(String applicantFirstName) {
		this.applicantFirstName = applicantFirstName;
	}

	public String getApplicantDocType() {
		return applicantDocType;
	}

	public void setApplicantDocType(String applicantDocType) {
		this.applicantDocType = applicantDocType;
	}

	public String getApplicantIDNum() {
		return applicantIDNum;
	}

	public void setApplicantIDNum(String applicantIDNum) {
		this.applicantIDNum = applicantIDNum;
	}

	public String getRelationshipwithCustomer() {
		return relationshipwithCustomer;
	}

	public void setRelationshipwithCustomer(String relationshipwithCustomer) {
		this.relationshipwithCustomer = relationshipwithCustomer;
	}

	public String getOtherRelationship() {
		return otherRelationship;
	}

	public void setOtherRelationship(String otherRelationship) {
		this.otherRelationship = otherRelationship;
	}

	public String getApplicantContactNo() {
		return applicantContactNo;
	}

	public void setApplicantContactNo(String applicantContactNo) {
		this.applicantContactNo = applicantContactNo;
	}
	
	public List<String> getNtvCampCdList() {
		return ntvCampCdList;
	}

	public void setNtvCampCdList(List<String> ntvCampCdList) {
		this.ntvCampCdList = ntvCampCdList;
	}
	
	public String getIsNtvCallListOffer() {
		return isNtvCallListOffer;
	}

	public void setIsNtvCallListOffer(String isNtvCallListOffer) {
		this.isNtvCallListOffer = isNtvCallListOffer;
	}
	
	public String getIsNtvSwitchingOffer() {
		return isNtvSwitchingOffer;
	}

	public void setIsNtvSwitchingOffer(String isNtvSwitchingOffer) {
		this.isNtvSwitchingOffer = isNtvSwitchingOffer;
	}

	public String getNtvCallListId() {
		return ntvCallListId;
	}

	public void setNtvCallListId(String ntvCallListId) {
		this.ntvCallListId = ntvCallListId;
	}

	public String getHasSupportDoc() {
		return hasSupportDoc;
	}

	public void setHasSupportDoc(String hasSupportDoc) {
		this.hasSupportDoc = hasSupportDoc;
	}

	public String getCashPayMtdType() {
		return cashPayMtdType;
	}

	public void setCashPayMtdType(String cashPayMtdType) {
		this.cashPayMtdType = cashPayMtdType;
	}
	
}