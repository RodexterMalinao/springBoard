package com.bomwebportal.ims.dto.ui;

import java.util.List;

import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.ims.dto.ImsRptBasketDtlDTO;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;
import com.bomwebportal.ims.dto.ImsRptServicePlanDetailDTO;

public class ImsResendEmailHistoryUI extends CustomerUI{
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
	private String otInstallChrg;
	private String waivedOtInstallChrg;
	
	private String warrPeriod;
	private ImsRptServicePlanDetailDTO servPlanDto;
	private ImsRptBasketDtlDTO basketDtl;
	private List<ImsRptBasketItemDTO> ntvPgmItemList;
	
	
	//kinman
	private DisMode disMode;
	private String esigEmailAddr;
	private EsigEmailLang esigEmailLang;
	private String langPreference;
	
	
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
	
	//For email history
	private String templateId;
	private List<OrdEmailReqDTO> ordEmailReqDTOList;
	private boolean emailSendConfirm;
	
	private boolean saleResendEmailAllowed;


	public boolean isSaleResendEmailAllowed() {
		return saleResendEmailAllowed;
	}
	public void setSaleResendEmailAllowed(boolean saleResendEmailAllowed) {
		this.saleResendEmailAllowed = saleResendEmailAllowed;
	}
	public boolean isEmailSendConfirm() {
		return emailSendConfirm;
	}
	public void setEmailSendConfirm(boolean emailSendConfirm) {
		this.emailSendConfirm = emailSendConfirm;
	}
	public List<OrdEmailReqDTO> getOrdEmailReqDTOList() {
		return ordEmailReqDTOList;
	}
	public void setOrdEmailReqDTOList(List<OrdEmailReqDTO> ordEmailReqDTOList) {
		this.ordEmailReqDTOList = ordEmailReqDTOList;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public List<AllOrdDocAssgnDTO> getAllOrdDocAssgnDTOs() {
		return allOrdDocAssgnDTOs;
	}
	public void setAllOrdDocAssgnDTOs(List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs) {
		this.allOrdDocAssgnDTOs = allOrdDocAssgnDTOs;
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
	
	
	
}