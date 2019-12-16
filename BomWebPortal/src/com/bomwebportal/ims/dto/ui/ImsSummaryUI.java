package com.bomwebportal.ims.dto.ui;

import java.util.List;

import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.BomwebSubscribedOfferImsDTO;
import com.bomwebportal.dto.ui.SupportDocUI.GenerateSpringboardForm;
import com.bomwebportal.ims.dto.ImsAllOrdDocAssgnDTO;
import com.bomwebportal.ims.dto.ImsRptBasketDtlDTO;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;
import com.bomwebportal.ims.dto.ImsRptServicePlanDetailDTO;
//kinman
import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
//
import com.bomwebportal.ims.dto.ImsCollectDocDTO;

public class ImsSummaryUI extends CustomerUI{
	private String ActionInd;
	private String hkbrCapture;
	private String fixedLineNo;
	private String loginId;
	private String appntStartDateDisplay;
	private String appntEndDateDisplay;
	private String appDate;
	private String targetCommDate;
	private String cashFsPrepay;
	private String prepayAmt;
	private String dobStr;
	private String langPreference;
	private String primaryAcctNo;
	private String nowTvFormType;
	private String cReasonCd;
	private String submitTag;
	private String orderId;
	
	private String warrPeriod;
	private ImsRptServicePlanDetailDTO servPlanDto;
	private ImsRptBasketDtlDTO basketDtl;
	private List<ImsRptBasketItemDTO> ntvPgmItemList;
	
	private String clubOptOutReasonDesc;
	
	private String basketTitle;
	private String basketDetail;
	private String imagePath;
	private String recurrentAmt;
	private String mthFix;
	private String mthToMth;
	private String mthToMthRate;
	private String contractPeriod;
	private double totalRecurrentAmt;
	private double totalMthToMthRate;
	
	private InstallAddrUI installAddress;
	private CustAddrUI billingAddress;
	private String billingAddressStr;
	private AppointmentUI appointment;
	
	private String WaivedPrepay;
	private String otInstallChrg; //TT 
	private String installOtCode;//gary
	private String installOTDesc_en;//gary
	private String installOTDesc_zh;//gary


	private String otChrgType; //Anthony
	private String waivedOtInstallChrg;
	private String noBooking;
	private String printFormClickInd;
	
	private String paperModeConfirmInd;
	private String withoutEmailComfirmInd;
	
	private String  selectedDisModeInd;

	//kinman	30102012 	
	private String printPreviewFormSignedInd; 
	private String clickDigitalSignButtonInd;
	
	
	//kinman 	05-11-2012
	private String clickCaptureIpadButtonInd;
	

	private String isPT;
	private String isCC;
	private String emailAddr;
	private String SMSno;
	private String FAXno;
	private String checkDigSignatureInd;
	
	//ims direct sales
	private String isDS;
	private String qcCallingTimePeriod;
	private String waiveQc = "N";
	private String waiveQCReason;
	
	
	private NowTVAddUI nowTVAddUI;//kinman new nowtv
	private String newtvpricingind;//kinman new nowtv
	
	//20150305 martin
	private int ntvConnType;
	
	// martin - NOWTVSALES
	private List<BasketUI> ftaPack;
	private List<BasketUI> selectedBaskets;
	private List<ImsRptBasketItemDTO> ntvPgmItemListGift;
	private List<ImsRptBasketItemDTO> ntvPgmItemListOneTime;
	
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
	
	private String emptySignatureError;
	
	private String mode;
	
	// martin, 20170717, BOM2017086
	private List<String> ntvCampCdList;
	private String isNtvCallListOffer;
	private String isNtvSwitchingOffer;
	private String ntvCallListId;
	
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
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	public String getSMSno() {
		return SMSno;
	}
	public void setSMSno(String sMSno) {
		SMSno = sMSno;
	}
	public String getSelectedDisModeInd() {
		return selectedDisModeInd;
	}
	public void setSelectedDisModeInd(String selectedDisModeInd) {
		this.selectedDisModeInd = selectedDisModeInd;
	}
	public String getPaperModeConfirmInd() {
		return paperModeConfirmInd;
	}
	public void setPaperModeConfirmInd(String paperModeConfirmInd) {
		this.paperModeConfirmInd = paperModeConfirmInd;
	}
	
	
	public String getWithoutEmailComfirmInd() {
		return withoutEmailComfirmInd;
	}
	public void setWithoutEmailComfirmInd(String withoutEmailComfirmInd) {
		this.withoutEmailComfirmInd = withoutEmailComfirmInd;
	}
	
	public String getClickCaptureIpadButtonInd() {
		return clickCaptureIpadButtonInd;
	}
	public void setClickCaptureIpadButtonInd(String clickCaptureIpadButtonInd) {
		this.clickCaptureIpadButtonInd = clickCaptureIpadButtonInd;
	}
	public String getPrintPreviewFormSignedInd() {
		return printPreviewFormSignedInd;
	}
	public void setPrintPreviewFormSignedInd(String printPreviewFormSignedInd) {
		this.printPreviewFormSignedInd = printPreviewFormSignedInd;
	}

	
	public String getClickDigitalSignButtonInd() {
		return clickDigitalSignButtonInd;
	}
	public void setClickDigitalSignButtonInd(String clickDigitalSignButtonInd) {
		this.clickDigitalSignButtonInd = clickDigitalSignButtonInd;
	}

	private List<ImsAllOrdDocAssgnDTO> qcSupportDoc;

	//
	private List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs;

	public List<AllOrdDocAssgnDTO> getAllOrdDocAssgnDTOs() {
		return allOrdDocAssgnDTOs;
	}
	public void setAllOrdDocAssgnDTOs(List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs) {
		this.allOrdDocAssgnDTOs = allOrdDocAssgnDTOs;
	}
	//Tony
	private List<ImsCollectDocDTO> imsCollectDocDTOs;

	public void setImsCollectDocDTOs(List<ImsCollectDocDTO> imsCollectDocDTOs) {
		this.imsCollectDocDTOs = imsCollectDocDTOs;
	}
	public List<ImsCollectDocDTO> getImsCollectDocDTOs() {
		return imsCollectDocDTOs;
	}
	
	private String edfRef;
	
	public void setEdfRef(String edfRef) {
		this.edfRef = edfRef;
	}
	public String getEdfRef() {
		return edfRef;
	}
	//
	public String getPrintFormClickInd() {
		return printFormClickInd;
	}

	public void setPrintFormClickInd(String printFormClickInd) {
		this.printFormClickInd = printFormClickInd;
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

	public String getLangPreference() {
		return langPreference;
	}

	public void setLangPreference(String langPreference) {
		this.langPreference = langPreference;
	}

	public String getPrimaryAcctNo() {
		return primaryAcctNo;
	}

	public void setPrimaryAcctNo(String primaryAcctNo) {
		this.primaryAcctNo = primaryAcctNo;
	}

	public String getNowTvFormType() {
		return nowTvFormType;
	}

	public void setNowTvFormType(String nowTvFormType) {
		this.nowTvFormType = nowTvFormType;
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

	public String getBillingAddressStr() {
		return billingAddressStr;
	}

	public void setBillingAddressStr(String billingAddressStr) {
		this.billingAddressStr = billingAddressStr;
	}

	public String getActionInd() {
		return ActionInd;
	}

	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}

	public String getWaivedPrepay() {
		return WaivedPrepay;
	}

	public void setWaivedPrepay(String waivedPrepay) {
		WaivedPrepay = waivedPrepay;
	}

	public String getNoBooking() {
		return noBooking;
	}

	public void setNoBooking(String noBooking) {
		this.noBooking = noBooking;
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
	//kinman
	public DisMode getDisMode() {
		return disMode;
	}
	public void setDisMode(DisMode disMode) {
		this.disMode = disMode;
	}
	public String getEsigEmailAddr() {
		return esigEmailAddr;
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

	public CollectMethod getCollectMethod() {
		return collectMethod;
	}
	public void setCollectMethod(CollectMethod collectMethod) {
		this.collectMethod = collectMethod;
	}


	private CollectMethod collectMethod;
	private DisMode disMode;
	private String esigEmailAddr;
	private EsigEmailLang esigEmailLang;
	//
	
	//steven added
	private String noSupportingDoc;
	public void setNoSupportingDoc(String noSupDoc) {
		noSupportingDoc = noSupDoc;
	}
	public String getNoSupportingDoc() {
		return noSupportingDoc;
	}
	// steven added ended
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
	public void setFAXno(String fAXno) {
		FAXno = fAXno;
	}
	public String getFAXno() {
		return FAXno;
	}
	public void setOtChrgType(String otChrgType) {
		this.otChrgType = otChrgType;
	}
	public String getOtChrgType() {
		return otChrgType;
	}
	
	//Tony added
	public void setCheckDigSignatureInd(String checkDigSignatureInd) {
		this.checkDigSignatureInd = checkDigSignatureInd;
	}
	public String getCheckDigSignatureInd() {
		return checkDigSignatureInd;
	}
	//Tony added end
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
	public void setQcCallingTimePeriod(String qcCallingTimePeriod) {
		this.qcCallingTimePeriod = qcCallingTimePeriod;
	}
	public String getQcCallingTimePeriod() {
		return qcCallingTimePeriod;
	}
	public void setWaiveQc(String waiveQc) {
		this.waiveQc = waiveQc;
	}
	public String getWaiveQc() {
		return waiveQc;
	}
	public void setIsDS(String isDS) {
		this.isDS = isDS;
	}
	public String getIsDS() {
		return isDS;
	}
	public void setWaiveQCReason(String waiveQCReason) {
		this.waiveQCReason = waiveQCReason;
	}
	public String getWaiveQCReason() {
		return waiveQCReason;
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

	public void setHkbrCapture(String hkbrCapture) {
		this.hkbrCapture = hkbrCapture;
	}

	public String getHkbrCapture() {
		return hkbrCapture;
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

	public void setEmptySignatureError(String emptySignatureError) {
		this.emptySignatureError = emptySignatureError;
	}

	public String getEmptySignatureError() {
		return emptySignatureError;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getMode() {
		return mode;
	}

	public void setQcSupportDoc(List<ImsAllOrdDocAssgnDTO> qcSupportDoc) {
		this.qcSupportDoc = qcSupportDoc;
	}

	public List<ImsAllOrdDocAssgnDTO> getQcSupportDoc() {
		return qcSupportDoc;
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
}