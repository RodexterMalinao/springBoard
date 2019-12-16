package com.bomwebportal.ims.dto.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.*;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.BomwebOrderServiceImsDTO;
import com.bomwebportal.ims.dto.GetCOrderDTO;
import com.bomwebportal.ims.dto.ImsCollectDocDTO;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.dto.OnlineSalesRequestDTO;
import com.bomwebportal.ims.dto.ServiceDetailDTO;
import com.bomwebportal.ims.dto.SubscItemDiscDTO;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;

public class OrderImsUI extends OrderImsDTO{
	private static final long serialVersionUID = 3547358505054175496L;
	private final String UID = Utility.getUid();;
	protected final Log logger = LogFactory.getLog(getClass());
	
	public String getUID(){
		return UID;
	}
	private String isTermDOrderSelfPickTargetCust;
	private String showSelfPickHddForNtvRet;
	private BomSalesUserDTO createByUser;
//	protected final Log logger = LogFactory.getLog(getClass());
	private String shortenUrlAf;
	private String shortenUrlQr;
//	private List<ProfileOfferDTO> profileList;
	private String lastTimeSuccessAmendSeq;
	private BomwebSubscribedOfferImsDTO[] subscribedOffersIms;
	private NowTVAddUI nowTVAddUI;
	private String ActionInd;
	private String OrderActionInd;
	private String PrepayCC;
	private String PrepayCash;
	private String TermExt;
	private AppointmentUI appointment;
	private RemarkUI[] remarks;
	private CustomerUI customer;
	private CustAddrUI billingAddress;
	private InstallAddrUI installAddress;
	private SubscribedItemUI[] subscribedItems;
	private ComponentUI[] components;
	private SubscribedChannelUI[] subscribedChannels;
	private SubscItemDiscDTO[] subscDiscountList;
	private String WQsubmmitted;//Tony added
	private String promoGiftCode;
	private String optInInd;
	private String giftInstDate;
	private String topUpType;
	
	private String pcdNowOneBoxInd;
	private String cslShopCustInd;
	private String preRegInd;
	private OnlineSalesRequestDTO preRegDetails;
	private String preRegSubmitted;
	
	private String giftWithCampaignSubInd;
	//nowTV page gift logic use
	private ComponentUI[] componentsClone;
	private String IsCreditCardOfferNowTVPage;
	private String serviceWaiverNowTVPage;
	//nowTV page gift logic use (end)
	
	private GetCOrderDTO cOrderDTO;
	
	private boolean isAIOSubOwn;
	private boolean isHDDPurchased; // BOM2016063, martin, 20160601
	private String isNtvCallListOffer; // BOM2017086, martin, 20170717
	private String isNtvSwitchingOffer; // BOM2017086, martin, 20170717
	private String ntvFirstPrepaymentAmt; // BOM2018007, martin, 20180208
	private String isStaffOffer; // BOM2018062, martin, 20180614
	
	private String basketPageVisitInd;
	private String oldMobileNum;
	
	private String ponOTChrgType;
	private String adslOTChrgType;
	private String vdslOTChrgType;
	private String vectorOTChrgType;
	private String tmpOTChrgType;
			
	private SubscribedCslOfferDTO[] subscribedCslOffers; 
	private SubscribedCslOfferDTO[] subscribedImsVasParm; 

	public SubscribedCslOfferDTO[] getSubscribedCslOffers() {
		return subscribedCslOffers;
	}
	public void setSubscribedCslOffers(SubscribedCslOfferDTO[] subscribedCslOffers) {
		this.subscribedCslOffers = subscribedCslOffers;
	}
	
	
	public String getTopUpType() {
		return topUpType;
	}
	public void setTopUpType(String topUpType) {
		this.topUpType = topUpType;
	}
	public NowTVAddUI getNowTVAddUI() {
		return nowTVAddUI;
	}
	public void setNowTVAddUI(NowTVAddUI nowTVAddUI) {
		this.nowTVAddUI = nowTVAddUI;
	}
	// for ims direct sales amendment
	private OrderImsUI orderImg;
	private String hasRelatedSB;
	// for ims direct sales amendment(end)
	
	//private String callDate;
	//private String callTime;
	//private String pno;
	private String isCcTv;
	private String isPT;
	private String isCC;
	private String emailAddress;
	private String ChannelCd;
	
	private String edfRef;
	
	private String isDS; //Added by Andy
	
	private String workingLocation;
	private String roadshowLocation;//Celia

	private String supremeFsInd;
	private String oldSupremeFsInd;
	
	private String channelTeamCd;
	private String staffGroup;
	private String serviceCodeStr;
	
	private String tngRebateInd;
	
	//Tony
	private SubscribedItemUI[] subscribedBackEndItems;
	private SubscribedChannelUI[] subscribedBackEndChannels;
	
	//retention new
	private BomwebOrderServiceImsDTO serviceIms;

	public BomwebOrderServiceImsDTO getServiceIms() {
		return serviceIms;
	}
	public void setServiceIms(BomwebOrderServiceImsDTO serviceIms) {
		this.serviceIms = serviceIms;
	}
	public String getChannelCd() {
		return ChannelCd;
	}
	public void setChannelCd(String channelCd) {
		ChannelCd = channelCd;
	}
	//steven added 20130627 start
	private String hasBBDailup;	
	public void setHasBBDailup(String hasBBDailup) {
		this.hasBBDailup = hasBBDailup;
	}
	public String getHasBBDailup() {
		return hasBBDailup;
	}		
	//steven added 20130627 end

	//Tony
	private List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs;

	public List<AllOrdDocAssgnDTO> getAllOrdDocAssgnDTOs() {
		return allOrdDocAssgnDTOs;
	}
	public void setAllOrdDocAssgnDTOs(List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs) {
		this.allOrdDocAssgnDTOs = allOrdDocAssgnDTOs;
	}
	private List<ImsCollectDocDTO> imsCollectDocDtoList;
	public String getSelectedBasketSupportHD(){
		if(getBandwidth()!=null && Float.parseFloat(getBandwidth())>=18) return "Y";  
		return "N";
	}
	public List<ImsCollectDocDTO> getImsCollectDocDtoList() {
		return imsCollectDocDtoList;
	}
	public void setImsCollectDocDtoList(List<ImsCollectDocDTO> imsCollectDocDtoList) {
		this.imsCollectDocDtoList = imsCollectDocDtoList;
	}
	//
	
	/*
	 * Get installation data and timeslot for resending OnlineSales email
	 */
	public String[] getInstallationDisplayTimeString(){
		String[] timeString = new String[3];		
		try{
			String dateString = Utility.date2String(this.getAppointment().getAppntEndDateDisplay(), "dd/MM/yyyy");												
//			String startTime = Utility.date2String(this.getAppointment().getAppntStartDateDisplay(), "hh:mma");
//			String endTime = Utility.date2String(this.getAppointment().getAppntEndDateDisplay(), "hh:mma");
			String startTime = Utility.date2String(this.getAppointment().getAppntStartDateDisplay(), "HH:mm");
			String endTime = Utility.date2String(this.getAppointment().getAppntEndDateDisplay(), "HH:mm");
			
			if(Integer.parseInt(Utility.date2String(this.getAppointment().getAppntStartDateDisplay(), "H"))==9 &&
					Integer.parseInt(Utility.date2String(this.getAppointment().getAppntEndDateDisplay(), "H"))==13 ){
//				startTime="10:00AM";
//				endTime="01:00PM";
				startTime="10:00";
				endTime="13:00";	
			}else if(Integer.parseInt(Utility.date2String(this.getAppointment().getAppntStartDateDisplay(), "H"))==9 &&
					Integer.parseInt(Utility.date2String(this.getAppointment().getAppntEndDateDisplay(), "H"))==18 ){
//				startTime="10:00AM";
//				endTime="06:00PM";
				startTime="10:00";
				endTime="18:00";
			}
			
			timeString = new String[]{dateString, startTime, endTime};
			
		}catch(Exception e){
			e.printStackTrace();			
		}		
		return timeString;
	}
	
	
	
	
	public void setBmoAlertRemarks(String rmk){
		for(int i=0; i<this.remarks.length; i++){
			if(ImsConstants.IMS_RMK_TYPE_BMO.equals(this.remarks[i].getRmkType())){
				this.remarks[i].setRmkDtl(rmk);
				return;
			}
		}
		ArrayList<RemarkUI> _remarks = new ArrayList<RemarkUI>(Arrays.asList(remarks));
		RemarkUI _remark = new RemarkUI();
		_remark.setRmkType(ImsConstants.IMS_RMK_TYPE_BMO);
		_remark.setRmkDtl(rmk);
		_remarks.add(_remark);
		remarks = (RemarkUI[])_remarks.toArray(new RemarkUI[0]);
	}
	
	public String isBookingNotAllowed(){
		System.out.println("installAddress.getAddrInventory().getResourceShortage():"+installAddress.getAddrInventory().getResourceShortage());
		System.out.println("installAddress.getBlacklistInd():"+installAddress.getBlacklistInd());
		System.out.println("customer.getBlacklistInd():"+customer.getBlacklistInd());
		if("Y".equals(installAddress.getAddrInventory().getResourceShortage()) || 
				"Y".equals(installAddress.getBlacklistInd()) ||
				"Y".equals(customer.getBlacklistInd())){
			return "Y";
		}else{
			return "N";
		}
	}
	
	public String getApprovalRequested(){
		if(getCashApprovalResult()!=null || getWaiveApprovalResult()!=null || getWaiveOtApprovalResult()!=null){
			return "Y";
		}else{
			return "N";
		}
	}
	
	public String getCashApprovalResult(){
		for(int i=0; i<this.remarks.length; i++){
			if(ImsConstants.IMS_RMK_TYPE_CASH_APPROVED.equals(this.remarks[i].getRmkType())){
				return "Y";
			}else if(ImsConstants.IMS_RMK_TYPE_CASH_REJECTED.equals(this.remarks[i].getRmkType())){
				return "N";
			}
		}
		return null;
	}
	
	public String getWaiveApprovalResult(){
		for(int i=0; i<this.remarks.length; i++){
			if(ImsConstants.IMS_RMK_TYPE_WAIVE_APPROVED.equals(this.remarks[i].getRmkType())){
				return "Y";
			}else if(ImsConstants.IMS_RMK_TYPE_WAIVE_REJECTED.equals(this.remarks[i].getRmkType())){
				return "N";
			}
		}
		return null;
	}
	
	public String getWaiveOtApprovalResult(){
		for(int i=0; i<this.remarks.length; i++){
			if(ImsConstants.IMS_RMK_TYPE_WAIVE_OT_APPROVED.equals(this.remarks[i].getRmkType())){
				return "Y";
			}else if(ImsConstants.IMS_RMK_TYPE_WAIVE_OT_REJECTED.equals(this.remarks[i].getRmkType())){
				return "N";
			}
		}
		return null;
	}
	
	//Tony added for installation charge discount for PT
	public String getOtDiscountApprovalResult(){
		for(int i=0; i<this.remarks.length; i++){
			if(ImsConstants.IMS_RMK_TYPE_OT_DISCOUNT_APPROVED.equals(this.remarks[i].getRmkType())){
				return "Y";
			}else if(ImsConstants.IMS_RMK_TYPE_OT_DISCOUNT_REJECTED.equals(this.remarks[i].getRmkType())){
				return "N";
			}
		}
		return null;
	}
	//Tony added end
	
	public void setInstallationRemark(String remarks){
		for(int i=0; i<this.remarks.length; i++){
			if(ImsConstants.IMS_RMK_TYPE_INSTALL.equals(this.remarks[i].getRmkType())){
				this.remarks[i].setRmkDtl(remarks);
			}
		}
	}
	
	public String getInstallationRemark(){
		for(int i=0; i<this.remarks.length; i++){
			if(ImsConstants.IMS_RMK_TYPE_INSTALL.equals(this.remarks[i].getRmkType())){
				return this.remarks[i].getRmkDtl();
			}
		}
		return null;
	}
	
	public void setSpecialRequestRemarks(String rmk){
		for(int i=0; i<this.remarks.length; i++){
			if(ImsConstants.IMS_RMK_TYPE_SPECIAL_REQUEST.equals(this.remarks[i].getRmkType())){
				this.remarks[i].setRmkDtl(rmk);
				return;
			}
		}
		ArrayList<RemarkUI> _remarks = new ArrayList<RemarkUI>(Arrays.asList(remarks));
		RemarkUI _remark = new RemarkUI();
		_remark.setRmkType(ImsConstants.IMS_RMK_TYPE_SPECIAL_REQUEST);
		_remark.setRmkDtl(rmk);
		_remarks.add(_remark);
		remarks = (RemarkUI[])_remarks.toArray(new RemarkUI[0]);
	}
	
	public String getSpecialRequestRemarks(){
		for(int i=0; i<this.remarks.length; i++){
			if(ImsConstants.IMS_RMK_TYPE_SPECIAL_REQUEST.equals(this.remarks[i].getRmkType())){
				return this.remarks[i].getRmkDtl();
			}
		}
		return null;
	}
	
	
	public String isPending(){
		if(getOrderStatus()==null || 
				getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_SUSPENDED) ||//10
				getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_WAITING_APPROVAL) ||//30
				getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_APPROVED) ||//31
				getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_APPROVALREJ) ||//32
				getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_SIGNOFF_AWAIT_CASH) ||//06
				getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_AWAIT_CASH_DEPOSIT) ||//08
				getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_VER_DOC_FINISH_13) ||//13
				getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_AWAIT_CASH_FPS_SRD_PASSED) || //15
				getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_AWAIT_CASH_FPS_ERROR) || // 16
				getOrderStatus().equals(ImsConstants.IMS_ORDER_STATUS_AWAIT_SIGNOFF)){//11
			return "Y";
		}else{
			return "N";
		}
	}
	
	public String isSignoffed(){
		if(!"Y".equals(isPending()) && !ImsConstants.IMS_ORDER_STATUS_CANCELLED.equals(getOrderStatus())){
			return "Y";
		}else{
			return "N";
		}
	}
	
	public String isMonthlyPlan(){
		if(this.getWarrPeriod()==null || this.getWarrPeriod() == "" || Integer.parseInt(this.getWarrPeriod())==0){
			return "Y";
		}else{
			return "N";
		}		
	}
	
	public String isBasicPlan(){
		if(this.getInstallAddress()!=null && this.getInstallAddress().getAddrInventory()!=null &&
				("ADSL".equals(this.getInstallAddress().getAddrInventory().getTechnology()) ||
				 "VDSL".equals(this.getInstallAddress().getAddrInventory().getTechnology()))){
			return "Y";
		}else{
			return "N";
		}
	}
	
	public String isFtth(){
		if(this.getInstallAddress()!=null && this.getInstallAddress().getAddrInventory()!=null &&				
				 "PON".equals(this.getInstallAddress().getAddrInventory().getTechnology())){
			return "Y";
		}else{
			return "N";
		}
	}
	
	public String isPh(){
		if(this.getInstallAddress()!=null && this.getInstallAddress().getHousingTypeList()!=null){
			for(int i=0; i<this.getInstallAddress().getHousingTypeList().size(); i++){
				/*
				if("PH".equals(this.getInstallAddress().getHousingTypeList().get(i).getHousingType())){
					return "Y";
				}*/
				if(this.getInstallAddress().getHousingTypeList().get(i).getHousingType().length()>=2 &&
						"PH".equals(this.getInstallAddress().getHousingTypeList().get(i).getHousingType().substring(0, 2))){
					return "Y";
				}
			}
			return "N";
		}else{
			return "N";
		}
	}
	
	public String isHos(){
		if(this.getInstallAddress()!=null && this.getInstallAddress().getHousingTypeList()!=null){
			for(int i=0; i<this.getInstallAddress().getHousingTypeList().size(); i++){
				/*
				if("HOS".equals(this.getInstallAddress().getHousingTypeList().get(i).getHousingType())){
					return "Y";
				}
				*/
				if(this.getInstallAddress().getHousingTypeList().get(i).getHousingType().length()>=3 &&
						"HOS".equals(this.getInstallAddress().getHousingTypeList().get(i).getHousingType().substring(0, 3))){
					return "Y";					
				}
			}
			return "N";
		}else{
			return "N";
		}
	}
	
	public String isPrivateHousing(){
		if(isPh().equals("N") && isHos().equals("N")){
			return "Y";
		}else{
			return "N";
		}
	}
	
	public String isPreinstallation(){
		if(subscribedItems!=null){
			for(int i=0; i<subscribedItems.length; i++){				
				if(ImsConstants.IMS_PRE_INSTALL_ITEM_TYPE.equals(subscribedItems[i].getType())){
					return "Y"; 
				}
			}
		}
		return "N";		
	}

	public String getActionInd() {
		return ActionInd;
	}

	public void setActionInd(String actionInd) {
		ActionInd = actionInd;
	}

	public AppointmentUI getAppointment() {
		return appointment;
	}

	public void setAppointment(AppointmentUI appointment) {
		this.appointment = appointment;
	}

	public String getPrepayCC() {
		return PrepayCC;
	}
	public void setPrepayCC(String prepayCC) {
		PrepayCC = prepayCC;
	}
	public String getPrepayCash() {
		return PrepayCash;
	}
	public void setPrepayCash(String prepayCash) {
		PrepayCash = prepayCash;
	}
	
	public String getTermExt() {
		return TermExt;
	}

	public void setTermExt(String termExt) {
		TermExt = termExt;
	}

	public RemarkUI[] getRemarks() {
		return remarks;
	}

	public void setRemarks(RemarkUI[] remarks) {
		this.remarks = remarks;
	}

	public CustomerUI getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerUI customer) {
		this.customer = customer;
	}

	public CustAddrUI getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(CustAddrUI billingAddress) {
		this.billingAddress = billingAddress;
	}

	public InstallAddrUI getInstallAddress() {
		return installAddress;
	}

	public void setInstallAddress(InstallAddrUI installAddress) {
		this.installAddress = installAddress;
	}

	public SubscribedItemUI[] getSubscribedItems() {
		return subscribedItems;
	}

	public void setSubscribedItems(SubscribedItemUI[] subscribedItems) {
		this.subscribedItems = subscribedItems;
	}

	public ComponentUI[] getComponents() {
		return components;
	}

	public void setComponents(ComponentUI[] components) {
		this.components = components;
	}

	public SubscribedChannelUI[] getSubscribedChannels() {
		return subscribedChannels;
	}

	public void setSubscribedChannels(SubscribedChannelUI[] subscribedChannels) {
		this.subscribedChannels = subscribedChannels;
	}

	public String getOrderActionInd() {
		return OrderActionInd;
	}

	public void setOrderActionInd(String orderActionInd) {
		OrderActionInd = orderActionInd;
	}

	public SubscItemDiscDTO[] getSubscDiscountList() {
		return subscDiscountList;
	}

	public void setSubscDiscountList(SubscItemDiscDTO[] subscDiscountList) {
		this.subscDiscountList = subscDiscountList;
	}		

	public void setWQsubmmitted(String wQsubmmitted) {
		WQsubmmitted = wQsubmmitted;
	}

	public String getWQsubmmitted() {
		return WQsubmmitted;
	}
//	public void setCallDate(String callDate) {
//		this.callDate = callDate;
//	}
//	public String getCallDate() {
//		return callDate;
//	}
//	public void setCallTime(String callTime) {
//		this.callTime = callTime;
//	}
//	public String getCallTime() {
//		return callTime;
//	}
//	public void setPno(String pno) {
//		this.pno = pno;
//	}
//	public String getPno() {
//		return pno;
//	}
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
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEdfRef(String edfRef) {
		this.edfRef = edfRef;
	}
	public String getEdfRef() {
		return edfRef;
	}
	public void setIsDS(String isDS) {
		this.isDS = isDS;
	}
	public String getIsDS() {
		return isDS;
	}
	public void setPromoGiftCode(String promoGiftCode) {
		this.promoGiftCode = promoGiftCode;
	}
	public String getPromoGiftCode() {
		return promoGiftCode;
	}
	public void setOptInInd(String optInInd) {
		this.optInInd = optInInd;
	}
	public String getOptInInd() {
		return optInInd;
	}
	public void setOrderImg(OrderImsUI orderImg) {
		this.orderImg = orderImg;
	}
	public OrderImsUI getOrderImg() {
		return orderImg;
	}
	public void setHasRelatedSB(String hasRelatedSB) {
		this.hasRelatedSB = hasRelatedSB;
	}
	public String getHasRelatedSB() {
		return hasRelatedSB;
	}
	public void setSubscribedBackEndItems(SubscribedItemUI[] subscribedBackEndItems) {
		this.subscribedBackEndItems = subscribedBackEndItems;
	}
	public SubscribedItemUI[] getSubscribedBackEndItems() {
		return subscribedBackEndItems;
	}
	public void setSubscribedBackEndChannels(SubscribedChannelUI[] subscribedBackEndChannels) {
		this.subscribedBackEndChannels = subscribedBackEndChannels;
	}
	public SubscribedChannelUI[] getSubscribedBackEndChannels() {
		return subscribedBackEndChannels;
	}
	public void setGiftInstDate(String giftInstDate) {
		this.giftInstDate = giftInstDate;
	}
	public String getGiftInstDate() {
		return giftInstDate;
	}
	public void setWorkingLocation(String workingLocation) {
		this.workingLocation = workingLocation;
	}
	public String getWorkingLocation() {
		return workingLocation;
	}
	public void setRoadshowLocation(String roadshowLocation) {
		this.roadshowLocation = roadshowLocation;
	}
	public String getRoadshowLocation() {
		return roadshowLocation;
	}
	
	public Boolean isOrderTypeNowRet(){
		if("NTVAO".equals(this.getOrderType())||"NTVUS".equals(this.getOrderType())||"NTVRE".equals(this.getOrderType())){
			return true;
		}
		return false;
	}
	
	public String getCPQTechRet(String oldFSATech,boolean isStandAloneTV,boolean hasADSL8M,boolean hasADSL18M,boolean isEyeBundle, boolean isPcdBundle){
		logger.info("getCPQTechRet oldFSATech:"+oldFSATech+" isStandAloneTV:"+isStandAloneTV+" hasADSL8M:"+hasADSL8M+" hasADSL18M:"+hasADSL18M+" isEyeBundle:"+isEyeBundle+" isPcdBundle:"+isPcdBundle);	

		if(oldFSATech.equals("PON")){
			logger.info(this.getCpqTxnId()+" oldFSATech.equals(PON) ");	
			return null;
		}	
		//this function see if need upgrade tech
		boolean buySHD = false;
		boolean buyHD = false;
		boolean buySD = false;
		boolean buyAIO = false;
		boolean is1L2B = false;
		//for now(20160614), isPcdBundle||isEyeBundle not allow change tech, 
		//but maybe ok in future, so below logic include change tech for isPcdBundle||isEyeBundle		
		boolean supportPon=false;
		boolean supportVDSL=false;
		boolean ponShortage=false;
		boolean vDSLShortage=false;
		
		if(subscribedItems!=null){
			logger.info(this.getCpqTxnId()+" getCPQTechRet subscribedItems:"+new Gson().toJson(subscribedItems));	
			for(SubscribedItemUI subItems:subscribedItems){
				if(subItems.getType().equalsIgnoreCase("NTV_SHD")||subItems.getType().equalsIgnoreCase("NTV_4K")){
					buySHD = true;
				}
				if(subItems.getType().equalsIgnoreCase("NTV_SD")){
					buySD = true;
				}
				if(subItems.getType().equalsIgnoreCase("NTV_HD")){
					buyHD = true;
				}
				if(subItems.getType().contains("AIO")){
					buyAIO = true;
				}
				if(subItems.getType().equalsIgnoreCase("NTV_BOX")){
					is1L2B = true;
				}
			}	
		}else{
			logger.info("subscribedItems==null");	
		}

		logger.info(this.getCpqTxnId()+" getCPQTechRet subscribedOffersIms:"+new Gson().toJson(subscribedOffersIms));	
//		System.out.println(this.getCpqTxnId()+" getCPQTechRet profileList:"+new Gson().toJson(profileList));	
		if(subscribedOffersIms!=null){
			for(BomwebSubscribedOfferImsDTO profile:subscribedOffersIms){
				if(" ".equals(profile.getIoInd())){
					if(profile!=null&&profile.getOfferType()!=null){
						if("NTVSHD".equalsIgnoreCase(profile.getOfferType())){
							buySHD = true;
						}
						if("NTVSD".equalsIgnoreCase(profile.getOfferType())){
							buySD = true;
						}
						if("NTVHD".equalsIgnoreCase(profile.getOfferType())){
							buyHD = true;
						}
						if("NTVHD0AIO".equalsIgnoreCase(profile.getOfferType())){
							buyHD = true;
						}
						if(profile.getOfferType().contains("NTVAIO")){
							buyAIO = true;
						}
						if("NTV1L2B".equalsIgnoreCase(profile.getOfferType())){
							is1L2B = true;
						}
					}
				}
			}			
		}
		logger.info(this.getCpqTxnId()+" getCPQTechRet buySHD:"+buySHD+" buyAIO:"+buyAIO+" buyHD:"+buyHD+" buySD:"+buySD);	
		if(oldFSATech.equals("VDSL")){
			if(!buyAIO&&!buySHD){
				logger.info("oldFSATech.equals(VDSL) && !buyAIO&&!buySHD");	
				return null;
			}
		}
		if(installAddress!=null&&installAddress.getServiceDetailList()!=null){
			for (ServiceDetailDTO dto :installAddress.getServiceDetailList()){
				if("PON".equals(dto.getTechnology())&&"Y".equals(dto.getTechnologySupported())&&"N".equals(dto.getIsDeadCase())){
					supportPon=true;
					if("Y".equals(dto.getIsResrcShort())){
						ponShortage=true;
					}
				}else if ("VDSL".equals(dto.getTechnology())&&"Y".equals(dto.getTechnologySupported())&&"N".equals(dto.getIsDeadCase())){	
					supportVDSL=true;
					if("Y".equals(dto.getIsResrcShort()))
						vDSLShortage=true;
				}
			}
		}else{
			logger.info("installAddress==null or installAddress.getServiceDetailList()=null");	
		}
		

		
		if(buySHD//||
				//(buyAIO&&!buyHD&&!buySD)
				){
			if(supportPon&&!ponShortage){
				return "PON";
			}else if(supportPon&&ponShortage){
				return "PON(shortage)";	
			}else if(!supportPon){
//				return "Error:not support PON but buy buySHD||(buyAIO&&!buyHD&&!buySD) ";		
				return "Error:not support PON but buy buySHD";					
			}
		}

		
		if(buyHD){
			if(oldFSATech.equals("ADSL")){
				if (isStandAloneTV){
					if(hasADSL8M||hasADSL18M){
						logger.info(" no need upgrade, has hasADSL8M||hasADSL18M can support HD 	 ");	
						return null;//no need upgrade, has hasADSL8M||hasADSL18M can support HD 						
					}else{
						if(supportPon&&!ponShortage){
							return "PON";
						}else if(supportVDSL&&!vDSLShortage){
							return "VDSL";	
						}else if(supportPon&&ponShortage&&vDSLShortage){
							return "PON(shortage)";	
						}else if(supportVDSL&&vDSLShortage){
							return "VDSL(shortage)";							
						}else{
							return "Error:no ADSL8,18,PON or VDSL but buy HD";
						}
					}
				}
				if(		(isPcdBundle)||//isPcdBundle + HD
						(isEyeBundle)||//isEyeBundle + HD
						(isStandAloneTV&&is1L2B)//isStandAloneTV + is1L2B +HD
						){
					if(hasADSL18M){
						logger.info(" no need upgrade, has hasADSL18M can support Bundle+HD		 	 ");
						return null;//no need upgrade, has hasADSL18M can support Bundle+HD		
					}else{
						if(supportPon&&!ponShortage){
							return "PON";
						}else if(supportVDSL&&!vDSLShortage){
							return "VDSL";	
						}else if(supportPon&&ponShortage&&vDSLShortage){
							return "PON(shortage)";	
						}else if(supportVDSL&&vDSLShortage){
							return "VDSL(shortage)";							
						}else{
							return "Error:no ADSL18,PON or VDSL but buy Bundle+HD";
						}
					}
				}
			}
		}
		logger.info("final null");
		return null;
	}
	
	public String getCPQTechnology(){
		Boolean isHD = false;
		Boolean isSD = false;
		Boolean isSHD = false;
		Boolean isBOXselected = false;
		Boolean isNowOne = false;
		String technology = null;
		Integer highestP = 7;
		List<Integer> supportedTech = new ArrayList<Integer>();
		Map<Integer,String> m = new HashMap<Integer,String>();
		int ponKey = 1, vdslKey = 2, adslKey = 3, ponSKey = 4, vdslSKey = 5, adslSKey = 6; // default: P > V > A
		String buildingType = installAddress.getAddrInventory().getBuildingType();
		
		if ("PT".equals(buildingType) && "NTV-A".equals(getOrderType())) { // PT: V > A > P
			vdslKey = 1;
			adslKey = 2;
			ponKey = 3;
			vdslSKey = 4;
			adslSKey = 5;
			ponSKey = 6;
		}
		m.put(ponKey, "PON");
		m.put(vdslKey, "VDSL");
		m.put(adslKey, "ADSL");
		m.put(ponSKey, "PON(shortage)");
		m.put(vdslSKey, "VDSL(shortage)");
		m.put(adslSKey, "ADSL(shortage)");
		
		for(SubscribedItemUI subItems:subscribedItems){
			if("NTV_SHD".equalsIgnoreCase(subItems.getType()) || "NTV_4K".equalsIgnoreCase(subItems.getType())){
				isSHD = true;
				//return "PON";
			}
			if("NTV_HD".equalsIgnoreCase(subItems.getType())){
				isHD = true;
			}
			if("NTV_SD".equalsIgnoreCase(subItems.getType())){
				isSD = true;
			}
			if("NTV_BOX".equalsIgnoreCase(subItems.getType())){
				isBOXselected = true;
			}
			if("AIO_RENTAL".equalsIgnoreCase(subItems.getType()) || "AIO_SUBOWN".equalsIgnoreCase(subItems.getType())) {
				isNowOne = true;
			}
		}		
		for (ServiceDetailDTO dto :installAddress.getServiceDetailList()){
			if("PON".equals(dto.getTechnology())&&"Y".equals(dto.getTechnologySupported())&&"N".equals(dto.getIsDeadCase())){				
				if("Y".equals(dto.getIsResrcShort())) {
					supportedTech.add(ponSKey);
					if (isSHD == true && isHD == false && isSD == false) {
						return "PON(shortage)";
					}
				} else {
					supportedTech.add(ponKey);
					if (isSHD == true && isHD == false && isSD == false) {
						return "PON";
					}
				}
			}
			else if ("VDSL".equals(dto.getTechnology())&&"Y".equals(dto.getTechnologySupported())&&"N".equals(dto.getIsDeadCase())){			
				if("Y".equals(dto.getIsResrcShort()))
					supportedTech.add(vdslSKey);
				else
					supportedTech.add(vdslKey);
			}
			else if ("ADSL".equals(dto.getTechnology())&&"Y".equals(dto.getTechnologySupported())&&"N".equals(dto.getIsDeadCase())){				
				if("Y".equals(dto.getIsResrcShort()))
					supportedTech.add(adslSKey);
				else
					supportedTech.add(adslKey);
			}
		}
		
		if(isHD) {	//remove ADSL if bw is below ADSL 8 and HD is subscribed
			if (!"Y".equals(installAddress.getHasADSL18()) && !"Y".equals(installAddress.getHasADSL8())) { 
				if (supportedTech.contains(adslKey)) supportedTech.remove((Integer)adslKey); 
				if (supportedTech.contains(adslSKey)) supportedTech.remove((Integer)adslSKey);
			}
		}
		
		if(isHD && isBOXselected) {	//remove ADSL if bw is below ADSL 18 and HD is subscribed and box is selected 
			if (!"Y".equals(installAddress.getHasADSL18())) { 
				if (supportedTech.contains(adslKey)) supportedTech.remove((Integer)adslKey);
				if (supportedTech.contains(adslSKey)) supportedTech.remove((Integer)adslSKey); 
			}
		}
		
		
		highestP = supportedTech.isEmpty()?7:Collections.min(supportedTech);
		technology = highestP==7?"NONE":m.get(highestP);
		
		
		/*if(isHD && !isBOXselected){
			if(!("Y".equals(installAddress.getHasADSL18())||"Y".equals(installAddress.getHasADSL8()))&& highestP>2)
				technology = supportedTech.contains(4)?m.get(4):(supportedTech.contains(5)?m.get(5):"NONE");
		}
		
		if(isBOXselected ){		
			if(!"Y".equals(installAddress.getHasADSL18())&& highestP>2)
				technology = supportedTech.contains(4)?m.get(4):(supportedTech.contains(5)?m.get(5):"NONE");					
		}
		*/
		
		return technology;
		
	}
	public void setcOrderDTO(GetCOrderDTO cOrderDTO) {
		this.cOrderDTO = cOrderDTO;
	}
	public GetCOrderDTO getcOrderDTO() {
		return cOrderDTO;
	}
	public void setPcdNowOneBoxInd(String pcdNowOneBoxInd) {
		this.pcdNowOneBoxInd = pcdNowOneBoxInd;
	}
	public String getPcdNowOneBoxInd() {
		return pcdNowOneBoxInd;
	}
	public boolean isAIOSubOwn() {
		return isAIOSubOwn;
	}
	public void setAIOSubOwn(boolean isAIOSubOwn) {
		this.isAIOSubOwn = isAIOSubOwn;
	}
	
	//By Frank
	//nowTVSales Retention third party
	private String proRataRefund;
	private String waivePenalty;
	private List<String> waiveReasonList;
	private String	selectedWaivedReason;
	private boolean alreadyInit;
	private String specialRequest;
	private String specialRequestCdDesc;
	
	public String getProRataRefund() {
		return proRataRefund;
	}
	public void setProRataRefund(String proRataRefund) {
		this.proRataRefund = proRataRefund;
	}
	public String getWaivePenalty() {
		return waivePenalty;
	}
	public void setWaivePenalty(String waivePenalty) {
		this.waivePenalty = waivePenalty;
	}
	public void setSubscribedOffersIms(BomwebSubscribedOfferImsDTO[] subscribedOffersIms) {
		this.subscribedOffersIms = subscribedOffersIms;
	}
	public BomwebSubscribedOfferImsDTO[] getSubscribedOffersIms() {
		return subscribedOffersIms;
	}
	public void setLastTimeSuccessAmendSeq(String lastTimeSuccessAmendSeq) {
		this.lastTimeSuccessAmendSeq = lastTimeSuccessAmendSeq;
	}
	public String getLastTimeSuccessAmendSeq() {
		return lastTimeSuccessAmendSeq;
	}
	public List<String> getWaiveReasonList() {
		return waiveReasonList;
	}
	public void setWaiveReasonList(List<String> waiveReasonList) {
		this.waiveReasonList = waiveReasonList;
	}
	public String getSelectedWaivedReason() {
		return selectedWaivedReason;
	}
	public void setSelectedWaivedReason(String selectedWaivedReason) {
		this.selectedWaivedReason = selectedWaivedReason;
	}
	public boolean isAlreadyInit() {
		return alreadyInit;
	}
	public void setAlreadyInit(boolean alreadyInit) {
		this.alreadyInit = alreadyInit;
	}
	public String getSpecialRequest() {
		return specialRequest;
	}
	public void setSpecialRequest(String specialRequest) {
		this.specialRequest = specialRequest;
	}
	public String getSpecialRequestCdDesc() {
		return specialRequestCdDesc;
	}
	public void setSpecialRequestCdDesc(String specialRequestCdDesc) {
		this.specialRequestCdDesc = specialRequestCdDesc;
	}
	public void setCreateByUser(BomSalesUserDTO createByUser) {
		this.createByUser = createByUser;
	}
	public BomSalesUserDTO getCreateByUser() {
		return createByUser;
	}
	
	public Boolean isDs(){
		if(this.createByUser!=null){
			if(this.createByUser.getChannelId()==12||this.createByUser.getChannelId()==13){
				return true;
			}
		}
		return false;		
	}
	public void setShortenUrlAf(String shortenUrlAf) {
		this.shortenUrlAf = shortenUrlAf;
	}
	public String getShortenUrlAf() {
		return shortenUrlAf;
	}
	public boolean isHDDPurchased() {
		return isHDDPurchased;
	}
	public void setHDDPurchased(boolean isHDDPurchased) {
		this.isHDDPurchased = isHDDPurchased;
	}
	public SubscribedCslOfferDTO[] getSubscribedImsVasParm() {
		return subscribedImsVasParm;
	}
	public void setSubscribedImsVasParm(SubscribedCslOfferDTO[] subscribedImsVasParm) {
		this.subscribedImsVasParm = subscribedImsVasParm;
	}
//	public void setProfileList(List<ProfileOfferDTO> profileList) {
//		this.profileList = profileList;
//	}
//	public List<ProfileOfferDTO> getProfileList() {
//		return profileList;
//	}
	public void setCslShopCustInd(String cslShopCustInd) {
		this.cslShopCustInd = cslShopCustInd;
	}
	public String getCslShopCustInd() {
		return cslShopCustInd;
	}
	public String getShortenUrlQr() {
		return shortenUrlQr;
	}
	public void setShortenUrlQr(String shortenUrlQr) {
		this.shortenUrlQr = shortenUrlQr;
	}
	public void setIsCcTv(String isCcTv) {
		this.isCcTv = isCcTv;
	}
	public String getIsCcTv() {
		return isCcTv;
	}
	public void setBasketPageVisitInd(String basketPageVisitInd) {
		this.basketPageVisitInd = basketPageVisitInd;
	}
	public String getBasketPageVisitInd() {
		return basketPageVisitInd;
	}
	public void setOldMobileNum(String oldMobileNum) {
		this.oldMobileNum = oldMobileNum;
	}
	public String getOldMobileNum() {
		return oldMobileNum;
	}

	public void setPonOTChrgType(String ponOTChrgType) {
		this.ponOTChrgType = ponOTChrgType;
	}

	public String getPonOTChrgType() {
		return ponOTChrgType;
	}

	public void setAdslOTChrgType(String adslOTChrgType) {
		this.adslOTChrgType = adslOTChrgType;
	}

	public String getAdslOTChrgType() {
		return adslOTChrgType;
	}

	public void setVdslOTChrgType(String vdslOTChrgType) {
		this.vdslOTChrgType = vdslOTChrgType;
	}

	public String getVdslOTChrgType() {
		return vdslOTChrgType;
	}

	public void setVectorOTChrgType(String vectorOTChrgType) {
		this.vectorOTChrgType = vectorOTChrgType;
	}

	public String getVectorOTChrgType() {
		return vectorOTChrgType;
	}

	public void setTmpOTChrgType(String tmpOTChrgType) {
		this.tmpOTChrgType = tmpOTChrgType;
	}

	public String getTmpOTChrgType() {
		return tmpOTChrgType;
	}

	public void setSupremeFsInd(String supremeFsInd) {
		this.supremeFsInd = supremeFsInd;
	}

	public String getSupremeFsInd() {
		return supremeFsInd;
	}

	public void setOldSupremeFsInd(String oldSupremeFsInd) {
		this.oldSupremeFsInd = oldSupremeFsInd;
	}

	public String getOldSupremeFsInd() {
		return oldSupremeFsInd;	
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
	public List<String> getRemarkDtlByType(String rmkType) {
		List<String> rmkDtl = new ArrayList<String>();
		if (rmkType != null && !"".equals(rmkType)) {
			for(int i=0; i<this.remarks.length; i++){
				if (rmkType.equals(this.remarks[i].getRmkType())) {
					rmkDtl.add(this.remarks[i].getRmkDtl());
				}
			}
		}
		return rmkDtl;
	}
	public void setChannelTeamCd(String channelTeamCd) {
		this.channelTeamCd = channelTeamCd;
	}
	public String getChannelTeamCd() {
		return channelTeamCd;
	}
	public void setStaffGroup(String staffGroup) {
		this.staffGroup = staffGroup;
	}
	public String getStaffGroup() {
		return staffGroup;
	}
	public void setServiceCodeStr(String serviceCodeStr) {
		this.serviceCodeStr = serviceCodeStr;
	}
	public String getServiceCodeStr() {
		return serviceCodeStr;
	}
	public void setShowSelfPickHddForNtvRet(String showSelfPickHddForNtvRet) {
		this.showSelfPickHddForNtvRet = showSelfPickHddForNtvRet;
	}
	public String getShowSelfPickHddForNtvRet() {
		return showSelfPickHddForNtvRet;
	}
	public void setGiftWithCampaignSubInd(String giftWithCampaignSubInd) {
		this.giftWithCampaignSubInd = giftWithCampaignSubInd;
	}
	public String getGiftWithCampaignSubInd() {
		return giftWithCampaignSubInd;
	}
	public String getNtvFirstPrepaymentAmt() {
		return ntvFirstPrepaymentAmt;
	}
	public void setNtvFirstPrepaymentAmt(String ntvFirstPrepaymentAmt) {
		this.ntvFirstPrepaymentAmt = ntvFirstPrepaymentAmt;
	}
	public void setPreRegInd(String preRegInd) {
		this.preRegInd = preRegInd;
	}
	public String getPreRegInd() {
		return preRegInd;
	}
	public void setPreRegDetails(OnlineSalesRequestDTO preRegDetails) {
		this.preRegDetails = preRegDetails;
	}
	public OnlineSalesRequestDTO getPreRegDetails() {
		return preRegDetails;
	}
	public void setPreRegSubmitted(String preRegSubmitted) {
		this.preRegSubmitted = preRegSubmitted;
	}
	public String getPreRegSubmitted() {
		return preRegSubmitted;
	}
	
	public String isDorder(){
		try{
			if(this.getServiceIms()!=null &&
					(this.getServiceIms().getEyeType()==null||"Y".equalsIgnoreCase(this.getServiceIms().getRemoveWithEye()))&&
					("D".equals(this.getServiceIms().getNowTvInd())||"N".equals(this.getServiceIms().getNowTvInd()))&&
					("D".equals(this.getServiceIms().getPcdInd())||"N".equals(this.getServiceIms().getPcdInd()))
					){
				//System.out.println("orderId:"+this.getOrderId()+" isDorder:Y");
				return "Y";
			}else{
				//System.out.println("orderId:"+this.getOrderId()+" isDorder:N");
				return "N";
			}
		}catch (Exception e) {
			logger.error("error in orderimsui isDorder:",e);
			return "N";
		}
		
	}
	public String getIsStaffOffer() {
		return isStaffOffer;
	}
	public void setIsStaffOffer(String isStaffOffer) {
		this.isStaffOffer = isStaffOffer;
	}
	public void setTngRebateInd(String tngRebateInd) {
		this.tngRebateInd = tngRebateInd;
	}
	public String getTngRebateInd() {
		return tngRebateInd;
	}
	public void setComponentsClone(ComponentUI[] componentsClone) {
		this.componentsClone = componentsClone;
	}
	public ComponentUI[] getComponentsClone() {
		return componentsClone;
	}
	public void setIsCreditCardOfferNowTVPage(String isCreditCardOfferNowTVPage) {
		IsCreditCardOfferNowTVPage = isCreditCardOfferNowTVPage;
	}
	public String getIsCreditCardOfferNowTVPage() {
		return IsCreditCardOfferNowTVPage;
	}
	public void setServiceWaiverNowTVPage(String serviceWaiverNowTVPage) {
		this.serviceWaiverNowTVPage = serviceWaiverNowTVPage;
	}
	public String getServiceWaiverNowTVPage() {
		return serviceWaiverNowTVPage;
	}
	public void setIsTermDOrderSelfPickTargetCust(
			String isTermDOrderSelfPickTargetCust) {
		this.isTermDOrderSelfPickTargetCust = isTermDOrderSelfPickTargetCust;
	}
	public String getIsTermDOrderSelfPickTargetCust() {
		return isTermDOrderSelfPickTargetCust;
	}

}
