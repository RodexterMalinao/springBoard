package com.bomwebportal.lts.service.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.CodeLkupDAO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ChannelAttbDTO;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetAttbDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO.BaCaActionType;
import com.bomwebportal.lts.dto.form.LtsAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.LtsBasketServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsBasketVasDetailFormDTO;
import com.bomwebportal.lts.dto.form.LtsCustomerIdentificationFormDTO;
import com.bomwebportal.lts.dto.form.LtsDnChangeDuplexFormDTO;
import com.bomwebportal.lts.dto.form.LtsDnChangeFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.form.LtsNowTvServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsNowTvServiceFormDTO.AdditionalTvType;
import com.bomwebportal.lts.dto.form.LtsOtherVoiceServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsOtherVoiceServiceFormDTO.DuplexSrvType;
import com.bomwebportal.lts.dto.form.LtsPaymentFormDTO;
import com.bomwebportal.lts.dto.form.LtsRecontractFormDTO;
import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.LtsSupportDocFormDTO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AddressInventoryDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.ChannelAttbLtsDTO;
import com.bomwebportal.lts.dto.order.ChannelDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ContactLtsDTO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ImsL2JobDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.NowtvDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderAttbDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.RecontractLtsDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceCallPlanDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.order.StaffInfoLtsDTO;
import com.bomwebportal.lts.dto.order.ThirdPartyDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemActionLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferActionLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupMemberProfileDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ServiceProfileInventoryDTO;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.LtsSalesInfoService;
import com.bomwebportal.lts.service.OfferChangeService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsDrgService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsConstant.UpgradeOrderAttb;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;
import com.pccw.util.CommonUtil;

public class OrderCreateServiceImpl implements OrderCreateService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected LtsBasketService ltsBasketService;
	protected LtsOfferService ltsOfferService;
	protected LtsAppointmentService ltsAppointmentService;
	protected CodeLkupCacheService eye2GCallPlanLkupCacheService;
	protected LtsCommonService ltsCommonService;
	protected LtsSalesInfoService ltsSalesInfoService;
	protected CallPlanLtsService callPlanLtsService;
	protected LtsProfileService ltsProfileService;
	protected OfferChangeService offerChangeService;
	protected CodeLkupCacheService rentalRouterL2JobCacheService;
	protected CodeLkupCacheService brmTechChangeL2JobCacheService;
	protected CodeLkupCacheService osTypeIosL2JobCacheService;
	protected CodeLkupCacheService brmL2JobCacheService;
	protected CodeLkupCacheService brmViL2JobCacheService;
	
	public OfferChangeService getOfferChangeService() {
		return offerChangeService;
	}

	public void setOfferChangeService(OfferChangeService offerChangeService) {
		this.offerChangeService = offerChangeService;
	}

	public LtsProfileService getLtsProfileService() {
		return ltsProfileService;
	}

	public void setLtsProfileService(LtsProfileService ltsProfileService) {
		this.ltsProfileService = ltsProfileService;
	}

	public CallPlanLtsService getCallPlanLtsService() {
		return callPlanLtsService;
	}

	public void setCallPlanLtsService(CallPlanLtsService callPlanLtsService) {
		this.callPlanLtsService = callPlanLtsService;
	}

	public CodeLkupCacheService getEye2GCallPlanLkupCacheService() {
		return eye2GCallPlanLkupCacheService;
	}

	public void setEye2GCallPlanLkupCacheService(
			CodeLkupCacheService eye2gCallPlanLkupCacheService) {
		eye2GCallPlanLkupCacheService = eye2gCallPlanLkupCacheService;
	}

	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}

	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public LtsSalesInfoService getLtsSalesInfoService() {
		return ltsSalesInfoService;
	}

	public void setLtsSalesInfoService(LtsSalesInfoService ltsSalesInfoService) {
		this.ltsSalesInfoService = ltsSalesInfoService;
	}

	public CodeLkupCacheService getRentalRouterL2JobCacheService() {
		return rentalRouterL2JobCacheService;
	}

	public void setRentalRouterL2JobCacheService(
			CodeLkupCacheService rentalRouterL2JobCacheService) {
		this.rentalRouterL2JobCacheService = rentalRouterL2JobCacheService;
	}

	public CodeLkupCacheService getBrmTechChangeL2JobCacheService() {
		return brmTechChangeL2JobCacheService;
	}

	public void setBrmTechChangeL2JobCacheService(
			CodeLkupCacheService brmTechChangeL2JobCacheService) {
		this.brmTechChangeL2JobCacheService = brmTechChangeL2JobCacheService;
	}
	
	public CodeLkupCacheService getOsTypeIosL2JobCacheService() {
		return osTypeIosL2JobCacheService;
	}

	public void setOsTypeIosL2JobCacheService(
			CodeLkupCacheService osTypeIosL2JobCacheService) {
		this.osTypeIosL2JobCacheService = osTypeIosL2JobCacheService;
	}

	public CodeLkupCacheService getBrmL2JobCacheService() {
		return brmL2JobCacheService;
	}

	public void setBrmL2JobCacheService(CodeLkupCacheService brmL2JobCacheService) {
		this.brmL2JobCacheService = brmL2JobCacheService;
	}

	public CodeLkupCacheService getBrmViL2JobCacheService() {
		return brmViL2JobCacheService;
	}

	public void setBrmViL2JobCacheService(
			CodeLkupCacheService brmViL2JobCacheService) {
		this.brmViL2JobCacheService = brmViL2JobCacheService;
	}

	protected enum CreateServiceType {
		CREATE_SRV_TYPE_RENEW_DEL,
		CREATE_SRV_TYPE_RENEW_EYE,
		CREATE_SRV_TYPE_UPGRADE,
		CREATE_SRV_TYPE_FSA,
		CREATE_SRV_TYPE_DUPLEX_REMOVE,
		CREATE_SRV_TYPE_DUPLEX_TO_2DEL,
		CREATE_SRV_TYPE_NEW_2DEL,
		CREATE_SRV_TYPE_SIP_REMOVE,
		CREATE_SRV_TYPE_DUPLEX_NUM_CHANGE,
		CREATE_SRV_TYPE_DUPLEX_NUM_NO_CHANGE;
	}
	
	public SbOrderDTO createSbOrder(OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		
		Map<String, AccountDetailLtsDTO> accountDetailLtsMap = new HashMap<String, AccountDetailLtsDTO>();
		Map<String, CustomerDetailLtsDTO> customerDetailLtsMap = new HashMap<String, CustomerDetailLtsDTO>();
		
		SbOrderDTO sbOrder = new SbOrderDTO();
		sbOrder.setBackDateInd(orderCapture.getLtsMiscellaneousForm().isBackDateOrder() ? "Y" : null);
		sbOrder.setAppDate(getAppDate(orderCapture));
		sbOrder.setAddress(createAddressDetailLts(orderCapture));
		sbOrder.setLob(LtsConstant.LOB_LTS);
		sbOrder.setSrvDtls(createServiceDetail(orderCapture, accountDetailLtsMap, customerDetailLtsMap));
		sbOrder.setAccounts(accountDetailLtsMap.values().toArray(new AccountDetailLtsDTO[accountDetailLtsMap.size()]));
		sbOrder.setCustomers(customerDetailLtsMap.values().toArray(new CustomerDetailLtsDTO[customerDetailLtsMap.size()]));
		sbOrder.setOrderType(orderCapture.getOrderType());
		sbOrder.setOrderSubType(orderCapture.getOrderSubType());
		setOrderSalesInfo(sbOrder, orderCapture, bomSalesUser);
		setOrderSupportDoc(sbOrder, orderCapture, bomSalesUser);
		sbOrder.setSrvReqDate(orderCapture.getLtsAppointmentForm() != null? orderCapture.getLtsAppointmentForm().getInstallationDate() : null);
		setContactInfo(sbOrder, orderCapture);
		return sbOrder;
	}

	public String getAppDate(OrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsMiscellaneousForm().isBackDateOrder()) {
			return orderCapture.getLtsMiscellaneousForm().getBackDate();
		}
		return orderCapture.getLtsMiscellaneousForm().getApplicationDate();
	}
	
	
	private StaffInfoLtsDTO createStaffInfo(OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		
		if (orderCapture.getLtsSalesInfoForm() == null) {
			return null;
		}
		
		LtsSalesInfoFormDTO salesInfoForm = orderCapture.getLtsSalesInfoForm();
		
		StaffInfoLtsDTO staffInfo = new StaffInfoLtsDTO();
		staffInfo.setContactPhone(salesInfoForm.getSalesContact());
		staffInfo.setSalesCd(salesInfoForm.getSalesCode());
		staffInfo.setTeamCd(salesInfoForm.getSalesTeam());
		staffInfo.setSalesName(salesInfoForm.getStaffName());
		staffInfo.setPosition(salesInfoForm.getPosition());
		staffInfo.setCallDate(salesInfoForm.getDate().concat(" ").concat(salesInfoForm.getTime()));
		staffInfo.setRefCentreCd(salesInfoForm.getRefereeSalesCenter());
		staffInfo.setRefSalesCd(salesInfoForm.getRefereeSalesId());
		staffInfo.setRefSalesName(salesInfoForm.getRefereeSalesName());
		staffInfo.setRefTeamCd(salesInfoForm.getRefereeSalesTeam());
		staffInfo.setCentreCd(salesInfoForm.getSalesCenter());
		
		return staffInfo;
	}
	
	private AddressDetailLtsDTO createAddressDetailLts (OrderCaptureDTO orderCapture) {

		LtsAddressRolloutFormDTO ltsAddressRolloutForm = orderCapture.getLtsAddressRolloutForm();
		AddressDetailProfileLtsDTO profileAddress = orderCapture.getLtsServiceProfile().getAddress();
		
		AddressDetailLtsDTO addressDetailLts = new AddressDetailLtsDTO();

		if (ltsAddressRolloutForm.isExternalRelocate()) {
			addressDetailLts.setAreaCd(ltsAddressRolloutForm.getAreaCode());
			addressDetailLts.setAreaDesc(ltsAddressRolloutForm.getAreaDesc());
			addressDetailLts.setBuildNo(ltsAddressRolloutForm.getBuildingName());
			addressDetailLts.setDistDesc(ltsAddressRolloutForm.getDistrictDesc());
			addressDetailLts.setDistNo(ltsAddressRolloutForm.getDistrictCode());
			addressDetailLts.setFloorNo(ltsAddressRolloutForm.getFloor());
			addressDetailLts.setHiLotNo(ltsAddressRolloutForm.getLotNum());
			addressDetailLts.setSectCd(ltsAddressRolloutForm.getSectionCode());
			addressDetailLts.setSectDesc(ltsAddressRolloutForm.getSectionDesc());
			addressDetailLts.setSerbdyno(ltsAddressRolloutForm.getServiceBoundary());
			addressDetailLts.setStrCatCd(ltsAddressRolloutForm.getStreetCatgCode());
			addressDetailLts.setStrCatDesc(ltsAddressRolloutForm.getStreetCatgDesc());
			addressDetailLts.setStrName(ltsAddressRolloutForm.getStreetName());
			addressDetailLts.setStrNo(ltsAddressRolloutForm.getStreetNum());
			addressDetailLts.setUnitNo(ltsAddressRolloutForm.getFlat());
		}
		else {
			addressDetailLts.setAreaCd(profileAddress.getAreaCd());
			addressDetailLts.setAreaDesc(profileAddress.getArea());
			addressDetailLts.setBuildNo(profileAddress.getBuildName());
			addressDetailLts.setDistDesc(profileAddress.getDistrict());
			addressDetailLts.setDistNo(profileAddress.getDistrictCd());
			addressDetailLts.setFloorNo(profileAddress.getFloorNum());
			addressDetailLts.setHiLotNo(profileAddress.getHlotNum());
			addressDetailLts.setSectCd(profileAddress.getSectCd());
			addressDetailLts.setSectDesc(profileAddress.getSectDesc());
			addressDetailLts.setSerbdyno(profileAddress.getSrvBdry());
			addressDetailLts.setStrCatCd(profileAddress.getStreetCatCd());
			addressDetailLts.setStrCatDesc(profileAddress.getStreetCat());
			addressDetailLts.setStrName(profileAddress.getStreetName());
			addressDetailLts.setStrNo(profileAddress.getStreetNum());
			addressDetailLts.setUnitNo(profileAddress.getUnitNum());
		}
		
		addressDetailLts.setAddrUsage("IA");
		addressDetailLts.setBlacklistInd(orderCapture.getNewAddressRollout().isImsAddressBlacklist() ? "Y" : null);
		addressDetailLts.setLtsBlacklistInd(orderCapture.getNewAddressRollout().isLtsAddressBlacklist() ? "Y" : null);
		addressDetailLts.setAddrInventory(createAddressInventoryDetailLts(orderCapture));
		
		return addressDetailLts;
	}
	
	private AddressInventoryDetailLtsDTO createAddressInventoryDetailLts(OrderCaptureDTO orderCapture) {
		AddressInventoryDetailLtsDTO addressInventory = new AddressInventoryDetailLtsDTO();
		addressInventory.setBuildingType(orderCapture.getNewAddressRollout().getHousingType());
		addressInventory.setFieldWorkPermitDay(orderCapture.getNewAddressRollout().getFieldWorkPermit());
		addressInventory.setFttcInd(orderCapture.getNewAddressRollout().isFttcInd() ? "Y" : null);
		addressInventory.setMaxBandwidth(orderCapture.getNewAddressRollout().getMaximumBandwidth());
		
		if (orderCapture.getModemTechnologyAissgn() != null) {
			// Set assigned technology for IMS order creation 
			addressInventory.setTechnology(orderCapture.getModemTechnologyAissgn().getTechnology());
			addressInventory.setResourceShortageInd(orderCapture.getModemTechnologyAissgn().isBbShortage() ? "Y" : null);	
		}
		
		if (orderCapture.getNewAddressRollout().isIs2nBuilding()) {
			addressInventory.setN2BuildingInd(StringUtils.defaultIfEmpty(orderCapture.getNewAddressRollout().getN2BuildingInd(), "Y"));	
		}
		
		
		return addressInventory;
	}	
	
	protected AllOrdDocAssgnLtsDTO[] createAllOrdDocAssgnLts (OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		
		LtsSupportDocFormDTO supportDocForm = orderCapture.getLtsSupportDocForm();
		
		if (supportDocForm == null) {
			return null;
		}
		
		List<OrderDocDTO> supportDocList = supportDocForm.getSupportDocumentList();
		
		if (supportDocList == null || supportDocList.isEmpty()) {
			return null;
		}
		
		List<AllOrdDocAssgnLtsDTO> allOrdDocAssgnList = new ArrayList<AllOrdDocAssgnLtsDTO>();
		
		AllOrdDocAssgnLtsDTO allOrdDocAssgnLts = null;
		for (OrderDocDTO supportDoc : supportDocList) {
			allOrdDocAssgnLts = new AllOrdDocAssgnLtsDTO();
			allOrdDocAssgnLts.setDocType(supportDoc.getDocType());
			allOrdDocAssgnLts.setWaiveReason(supportDoc.getWaiveReasonCd());
			allOrdDocAssgnLts.setWaivedBy(StringUtils.isNotBlank(supportDoc.getWaiveReasonCd()) ? bomSalesUser.getUsername() : null);
			allOrdDocAssgnList.add(allOrdDocAssgnLts);
		}
		
		return allOrdDocAssgnList.toArray(new AllOrdDocAssgnLtsDTO[allOrdDocAssgnList.size()]); 
	}
	
	private void setOrderSupportDoc(SbOrderDTO pSbOrder, OrderCaptureDTO pOrderCapture, BomSalesUserDTO pBomSalesUser) {
		if (pOrderCapture.getLtsSupportDocForm() != null) {
			pSbOrder.setDisMode(pOrderCapture.getLtsSupportDocForm().getDistributionMode());
			pSbOrder.setEsigEmailLang(pOrderCapture.getLtsSupportDocForm().getDistributeLang());	
			pSbOrder.setCollectMethod(pOrderCapture.getLtsSupportDocForm().getCollectMethod());
			pSbOrder.setAllOrdDocAssgns(createAllOrdDocAssgnLts(pOrderCapture, pBomSalesUser));	
			pSbOrder.setSignoffMode(pOrderCapture.getLtsSupportDocForm().getSignoffMode());
			
			if (StringUtils.equals(LtsConstant.DISTRIBUTE_MODE_ESIGNATURE, 
					pOrderCapture.getLtsSupportDocForm().getDistributionMode())) {
				if (pOrderCapture.getLtsSupportDocForm().isSendSms()) {
					pSbOrder.setSmsNo(pOrderCapture.getLtsSupportDocForm().getDistributeSms());
				}
				if (pOrderCapture.getLtsSupportDocForm().isSendEmail()) {
					pSbOrder.setEsigEmailAddr(pOrderCapture.getLtsSupportDocForm().getDistributeEmail());
				}
			}
		}
	}
	
	private void setOrderSalesInfo(SbOrderDTO pSbOrder, OrderCaptureDTO pOrderCapture, BomSalesUserDTO bomSalesUser) {
		if (pOrderCapture.getLtsSalesInfoForm() != null) {
			pSbOrder.setSalesChannel(pOrderCapture.getLtsSalesInfoForm().getSalesChannel());
			pSbOrder.setSalesContactNum(pOrderCapture.getLtsSalesInfoForm().getSalesContact());
			pSbOrder.setStaffNum(pOrderCapture.getLtsSalesInfoForm().getStaffId());
			pSbOrder.setSalesCd(pOrderCapture.getLtsSalesInfoForm().getSalesCode());
			pSbOrder.setSalesTeam(pOrderCapture.getLtsSalesInfoForm().getSalesTeam());
			pSbOrder.setSalesName(pOrderCapture.getLtsSalesInfoForm().getStaffName());
			pSbOrder.setSalesPosition(pOrderCapture.getLtsSalesInfoForm().getPosition());
			pSbOrder.setSalesJob(pOrderCapture.getLtsSalesInfoForm().getJobName());
			
			if(StringUtils.isNotBlank(pOrderCapture.getLtsSalesInfoForm().getDate())
					&& StringUtils.isNotBlank(pOrderCapture.getLtsSalesInfoForm().getTime())){
				pSbOrder.setSalesProcessDate(pOrderCapture.getLtsSalesInfoForm().getDate().concat(" ").concat(pOrderCapture.getLtsSalesInfoForm().getTime()));
			}
			
			pSbOrder.setCreateChannel(bomSalesUser.getChannelCd());
			LtsSalesInfoFormDTO tempSales = ltsSalesInfoService.getSalesInfo(pOrderCapture.getLtsSalesInfoForm().getStaffId());
			pSbOrder.setShopCd(tempSales != null? tempSales.getSalesTeam(): null);
			pSbOrder.setStaffInfo(createStaffInfo(pOrderCapture, bomSalesUser));
		}
	}

	private ServiceDetailDTO[] createServiceDetail(OrderCaptureDTO orderCapture,
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap,
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		BasketDetailDTO selectedBasket = ltsBasketService.getBasket(orderCapture.getLtsBasketSelectionForm().getSelectedBasketId(),
				LtsConstant.LOCALE_ENG, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
		
		List<ServiceDetailDTO> serviceDetailList = new ArrayList<ServiceDetailDTO>();
		
		ServiceDetailDTO renewalService = createRenewalServiceDetail(orderCapture, selectedBasket, accountDetailLtsMap, customerDetailLtsMap);
		if (renewalService != null) {
			serviceDetailList.add(renewalService);
		}
		
		ServiceDetailDTO upgradeService = createUpgradeServiceDetail(orderCapture, selectedBasket, accountDetailLtsMap, customerDetailLtsMap);
		if (upgradeService != null) {
			serviceDetailList.add(upgradeService);
		}
		
		ServiceDetailDTO new2DelService = createNew2DelService(orderCapture, selectedBasket, accountDetailLtsMap, customerDetailLtsMap);
		if (new2DelService != null) {
			serviceDetailList.add(new2DelService);
		}
		
		ServiceDetailDTO duplexTo2DelService = createDuplexTo2DelService(orderCapture, selectedBasket, accountDetailLtsMap, customerDetailLtsMap);
		if (duplexTo2DelService != null) {
			serviceDetailList.add(duplexTo2DelService);
		}
		
		ServiceDetailDTO duplexRemoveService = createDuplexRemoveService(orderCapture, selectedBasket, accountDetailLtsMap, customerDetailLtsMap);
		if (duplexRemoveService != null) {
			serviceDetailList.add(duplexRemoveService);
		}
		
		ServiceDetailDTO sipRemoveService = createSipRemoveService(orderCapture, selectedBasket, accountDetailLtsMap, customerDetailLtsMap);
		if (sipRemoveService != null) {
			serviceDetailList.add(sipRemoveService);
		}		
		
		//Handle DNX OR DNY Without Change ---- Modified BY Max.R.MENG
		ServiceDetailDTO duplexNoChangeService = createDuplexNoChangeService(orderCapture, selectedBasket, accountDetailLtsMap, customerDetailLtsMap);
		if(duplexNoChangeService != null){
			serviceDetailList.add(duplexNoChangeService);
		}
		
		String srvNum = renewalService != null ? renewalService.getSrvNum() : upgradeService != null ? upgradeService.getSrvNum() : null;
		
		ServiceDetailOtherLtsDTO fsaServiceDetail = createServiceDetailOther(orderCapture, selectedBasket, srvNum);
		if (fsaServiceDetail != null) {
			serviceDetailList.add(fsaServiceDetail);
		}
		return serviceDetailList.toArray(new ServiceDetailDTO[serviceDetailList.size()]);
	}

	private ServiceDetailOtherLtsDTO createServiceDetailOther(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket, String upgradeServiceNum) {
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
				&& StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			return null;
		}
		
		FsaDetailDTO selectedFsaDetail = getSelectedFsaDetail(orderCapture);
		
		ServiceDetailOtherLtsDTO serviceDetailOtherLts = new ServiceDetailOtherLtsDTO();
		serviceDetailOtherLts.setAppointmentDtl(createAppointmentDetailLts(CreateServiceType.CREATE_SRV_TYPE_FSA, orderCapture));
		serviceDetailOtherLts.setAssgnBandwidth(orderCapture.getModemTechnologyAissgn().getBandwidth());
		serviceDetailOtherLts.setAssgnTechnology(orderCapture.getModemTechnologyAissgn().getTechnology());

		serviceDetailOtherLts.setChannels(createChannelDetailLts(orderCapture));
		serviceDetailOtherLts.setExistArpu(getExistNowTvArpu(orderCapture));
		serviceDetailOtherLts.setModemArrangement(orderCapture.getModemTechnologyAissgn().getModemArrangment());
		serviceDetailOtherLts.setNowtvDetail(createNowTvDetailLts(orderCapture));
		serviceDetailOtherLts.setNowtvMirrorInd(isMirrorNowTv(orderCapture) ? "Y" : null);
		serviceDetailOtherLts.setNowtvTvCd(getNowBundleTv(orderCapture));
		serviceDetailOtherLts.setRelatedSbOrderId(getRelatedSbOrderId(orderCapture));
		serviceDetailOtherLts.setEdfRef(getEdfRef(orderCapture));
		serviceDetailOtherLts.setSuggestSrd(getSuggestedSrd(orderCapture));
		serviceDetailOtherLts.setSuggestSrdReasonCd(getSuggestedSrdReason(orderCapture));
		serviceDetailOtherLts.setFromProd(getImsFromProductType(orderCapture));
		serviceDetailOtherLts.setToProd(getImsToProductType(orderCapture));
		
		String orderType = getImsOrderType(orderCapture);
		serviceDetailOtherLts.setOrderType(orderType);
		serviceDetailOtherLts.setTypeOfSrv(LtsConstant.SERVICE_TYPE_IMS); 
		serviceDetailOtherLts.setGrpType(LtsConstant.GROUP_TYPE_EYE);
		
		ModemType selectedModemType = orderCapture.getLtsModemArrangementForm().getModemType();
		
		serviceDetailOtherLts.setShareFsaType(selectedModemType.getName());
		// if isMirrorNowTV, set select Mirror FSA
		if (isMirrorNowTv(orderCapture) && orderCapture.getLtsNowTvServiceForm() != null){
			serviceDetailOtherLts.setMirrorFsa(orderCapture.getLtsNowTvServiceForm().getMirrorFsa());
		}
		serviceDetailOtherLts.setLoginId(getLoginId(orderType, selectedFsaDetail, upgradeServiceNum, selectedModemType));
		serviceDetailOtherLts.setTenure(String.valueOf(orderCapture.getLtsServiceProfile().getImsTenure()));
		serviceDetailOtherLts.setOfferDtls(createAllImsOfferDetails(orderCapture));
		
		serviceDetailOtherLts.setManualLineTypeInd(LtsSbOrderHelper.getSelectedLineType(orderCapture.getLtsModemArrangementForm()));	
		serviceDetailOtherLts.setAutoUpgraded(orderCapture.getModemTechnologyAissgn().isAutoUpgraded() ? "Y" : "N");
		serviceDetailOtherLts.setNoEyeFsa(LtsSbOrderHelper.getNoEyeFsa(orderCapture.getLtsModemArrangementForm()));
		
		if (selectedFsaDetail != null ) {
			serviceDetailOtherLts.setDeactNowTvInd(selectedFsaDetail.getDeactNowTv());

			if (orderCapture.getModemTechnologyAissgn() != null
					&& !StringUtils.equals(LtsConstant.MODEM_TYPE_2L2B,
							orderCapture.getModemTechnologyAissgn().getModemArrangment())) {
				serviceDetailOtherLts.setCcServiceId(String.valueOf(selectedFsaDetail.getFsa()));
			}
			
			serviceDetailOtherLts.setErInd(isFsaPerformEr(orderCapture, selectedFsaDetail) ? "Y" : null);
			serviceDetailOtherLts.setExistBandwidth(selectedFsaDetail.getBandwidth());
			serviceDetailOtherLts.setExistSrvTypeCd(selectedFsaDetail.getExistingService().getDesc());
			serviceDetailOtherLts.setPendingOcid(selectedFsaDetail.getPendingOcid());
			serviceDetailOtherLts.setSrvNum(String.valueOf(selectedFsaDetail.getFsa()));
			serviceDetailOtherLts.setNewSrvTypeCd(getNewSrvTypeCode(selectedFsaDetail));
			serviceDetailOtherLts.setNewBandwidth(getNewBandwidth(selectedFsaDetail));
			serviceDetailOtherLts.setExistModem(selectedFsaDetail.getExistingModemArrange());
			serviceDetailOtherLts.setExistTechnology(selectedFsaDetail.getTechnology());
			serviceDetailOtherLts.setExistMirrorInd(selectedFsaDetail.getExistMirrorInd());
		}
		if (StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_WG)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_PCD)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_PCD_SD)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_PCD_HD)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_SD)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_HD)) {
			serviceDetailOtherLts.setExistModem(LtsConstant.EXIST_MODEM_TYPE_NIL);
		}
		serviceDetailOtherLts.setReplaceExistFsa(getReplaceExistFsa(orderCapture, selectedFsaDetail, orderType));
		
		if (orderCapture.getLtsAppointmentForm()!= null) {
			serviceDetailOtherLts.setForceFieldVisitInd(orderCapture.getLtsAppointmentForm().isFieldVisitRequired() ?  "Y" : null);
		}		
		
		serviceDetailOtherLts.setLostModem(orderCapture.getLtsModemArrangementForm().isLostModem()? "Y" : null);
		serviceDetailOtherLts.setShareRentalRouter(getShareRentalRouterInd(orderCapture));
		serviceDetailOtherLts.setShareBrmWifi(getBrmWifiInd(orderCapture, selectedFsaDetail));
		serviceDetailOtherLts.setImsL2Jobs(createImsL2job(orderCapture, selectedBasket));
		
		return serviceDetailOtherLts;
	}
	
	private String getBrmWifiInd(OrderCaptureDTO orderCapture, FsaDetailDTO selectedFsaDetail) {
		
		switch(orderCapture.getLtsModemArrangementForm().getModemType()){
		case SHARE_EX_FSA:
			if(selectedFsaDetail != null && selectedFsaDetail.isBrmWifiInd()){
				return "Y";
			}
			break;
			
		case SHARE_PCD: 
			if(orderCapture.getRelatedPcdOrder() != null){
				return orderCapture.getRelatedPcdOrder().getHasBrmWifi();
			}
			break;
		}
		
		return null;
	}
	
	private String getShareRentalRouterInd(OrderCaptureDTO orderCapture) {
		if(orderCapture.getLtsModemArrangementForm().isRentalRouterExistFsaVas()){
			return "E";
		}else if(LtsConstant.ROUTER_OPTION_SHARE_RENTAL_ROUTER.equals(orderCapture.getLtsModemArrangementForm().getRentalRouterInd())){
			return "Y";
		}else if(LtsConstant.ROUTER_OPTION_BRM.equals(orderCapture.getLtsModemArrangementForm().getRentalRouterInd())){
			return "N";
		}else{
			return null;
		}
	}
	
	private String getReplaceExistFsa(OrderCaptureDTO orderCapture, FsaDetailDTO selectedFsaDetail, String orderType) {
		if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType()) 
				|| orderCapture.getLtsServiceProfile().getEyeFsaProfile() == null ) {
			return null;
		}
		
		switch (orderCapture.getLtsModemArrangementForm().getModemType()) {
		case SHARE_BUNDLE:
		case SHARE_PCD:
		case SHARE_TV:
		case STANDALONE:
			return orderCapture.getLtsServiceProfile().getRelatedEyeFsa();
		case SHARE_EX_FSA:
		case SHARE_OTHER_FSA:
			if (!StringUtils.equals(orderCapture.getLtsServiceProfile().getRelatedEyeFsa(), String.valueOf(selectedFsaDetail.getFsa()))) {
				return orderCapture.getLtsServiceProfile().getRelatedEyeFsa();
			}
			if (orderCapture.getLtsServiceProfile().getEyeFsaProfile() != null && LtsConstant.ORDER_TYPE_INSTALL.equals(orderType)) {
				return orderCapture.getLtsServiceProfile().getRelatedEyeFsa();
			}
		default:
			return null;
		}
		
	}
	
	private ChannelAttbLtsDTO[] createChannelAttbs(ChannelAttbDTO[] pChannelAttbs) {
		
		if (ArrayUtils.isEmpty(pChannelAttbs)) {
			return null;
		}
		ChannelAttbLtsDTO[] channelAttbs = new ChannelAttbLtsDTO[pChannelAttbs.length];
		String attbValue = null;
		
		for (int i=0; i<pChannelAttbs.length; ++i) {
			if (StringUtils.equals("TEXT", pChannelAttbs[i].getInputMethod())) {
				continue;
			}
			channelAttbs[i] = new ChannelAttbLtsDTO();
			channelAttbs[i].setAttbId(pChannelAttbs[i].getAttbID());
			
			if (StringUtils.equals("CHECK", pChannelAttbs[i].getInputMethod())) {
				attbValue = pChannelAttbs[i].isSelected() ? "Y" : "N";
			} else {
				attbValue = pChannelAttbs[i].getAttbValue();
			}
			channelAttbs[i].setAttbValue(attbValue);
		}
		return channelAttbs;
	}
	

	
	private boolean isDummyDoc(CreateServiceType createServiceType, OrderCaptureDTO orderCapture) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_NEW_2DEL:
			return orderCapture.getLtsCustomerIdentificationForm().isSecDelDummy();
		default:
			return orderCapture.getLtsCustomerIdentificationForm().isDummy();
		}
	}
	
	private boolean isServiceEr (CreateServiceType createServiceType, OrderCaptureDTO orderCapture) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_UPGRADE:
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:			
			return orderCapture.getLtsAddressRolloutForm().isExternalRelocate();
		case CREATE_SRV_TYPE_NEW_2DEL:
			if (orderCapture.getLtsOtherVoiceServiceForm().getSecondDelEr() != null) {
				return orderCapture.getLtsOtherVoiceServiceForm().getSecondDelEr().booleanValue();
			}
			break;
		default:
			return false;
		}
		return false;
	}
	
	private void addCommonItemDetail(
			CreateServiceType createServiceType, OrderCaptureDTO orderCapture,
			BasketDetailDTO selectedBasket, List<ItemDetailLtsDTO> itemDetailLtsList) {
		
		AccountDetailProfileLtsDTO upgradeSrvPrimaryAcct = LtsSbOrderHelper.getPrimaryAccount(orderCapture.getLtsServiceProfile());
		AccountDetailProfileLtsDTO secDelSrvPrimaryAcct = orderCapture.getSecondDelServiceProfile() == null ? LtsSbOrderHelper.getPrimaryAccount(orderCapture.getLtsServiceProfile())
				: LtsSbOrderHelper.getPrimaryAccount(orderCapture.getSecondDelServiceProfile());	
		
		switch (createServiceType) {
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_NEW_2DEL:
			if (!StringUtils.equals(upgradeSrvPrimaryAcct.getAcctNum(), secDelSrvPrimaryAcct.getAcctNum())) {
				List<ItemDetailDTO> existBillList = ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_KEEP_EXIST_BILL, LtsConstant.LOCALE_ENG, true, orderCapture.getBasketChannelId(), orderCapture.getLtsMiscellaneousForm().getApplicationDate());
				addItemDetail(selectedBasket, existBillList, itemDetailLtsList);
			}	
			break;
		default:
			break;
		}
		
		if (orderCapture.getLtsPaymentForm() != null ) {
			switch (createServiceType) {
			case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
				addItemDetail(selectedBasket, orderCapture.getLtsPaymentForm().getBillItemList(), itemDetailLtsList);
				if(orderCapture.getLtsPaymentForm().getCsPortalItem() != null){
					addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsPaymentForm().getCsPortalItem()), itemDetailLtsList);
				}
				if(orderCapture.getLtsPaymentForm().getViewBillItem() != null){
					addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsPaymentForm().getViewBillItem()), itemDetailLtsList);
				}
				break;
			case CREATE_SRV_TYPE_NEW_2DEL:
				if (StringUtils.equals(upgradeSrvPrimaryAcct.getAcctNum(), secDelSrvPrimaryAcct.getAcctNum())) {
					addItemDetail(selectedBasket, orderCapture.getLtsPaymentForm().getBillItemList(), itemDetailLtsList);	
					if(orderCapture.getLtsPaymentForm().getCsPortalItem() != null){
						addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsPaymentForm().getCsPortalItem()), itemDetailLtsList);
					}
					if(orderCapture.getLtsPaymentForm().getViewBillItem() != null){
						addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsPaymentForm().getViewBillItem()), itemDetailLtsList);
					}
				}
				break;
			case CREATE_SRV_TYPE_UPGRADE:
			case CREATE_SRV_TYPE_RENEW_DEL:
			case CREATE_SRV_TYPE_RENEW_EYE:
				addItemDetail(selectedBasket, orderCapture.getLtsPaymentForm().getBillItemList(), itemDetailLtsList);	
				if(orderCapture.getLtsPaymentForm().getCsPortalItem() != null){
					addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsPaymentForm().getCsPortalItem()), itemDetailLtsList);
				}
				if(orderCapture.getLtsPaymentForm().getViewBillItem() != null){
					addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsPaymentForm().getViewBillItem()), itemDetailLtsList);
				}
				if (orderCapture.getLtsPaymentForm().getPrePayItem() != null) {
					addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsPaymentForm().getPrePayItem()) , itemDetailLtsList);
				}
				break;
			default:
				break;
			}
		}
	}	
	
	
	private ItemDetailLtsDTO[] createDuplexServiceItemDetail(CreateServiceType createServiceType, OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket) {
		
		List<ItemDetailLtsDTO> itemDetailLtsList = new ArrayList<ItemDetailLtsDTO>();
		
		addItemDetail(selectedBasket, orderCapture.getLtsOtherVoiceServiceForm().getSecondDelHotVasItemList(), itemDetailLtsList);
		addItemDetail(selectedBasket, orderCapture.getLtsOtherVoiceServiceForm().getSecondDelOtherVasItemList(), itemDetailLtsList);
		addItemDetail(selectedBasket, orderCapture.getLtsOtherVoiceServiceForm().getSecondDelStandaloneVasItemList(), itemDetailLtsList);
		addItemDetail(selectedBasket, orderCapture.getLtsOtherVoiceServiceForm().getSecondDelIddItemList(), itemDetailLtsList);
		addItemDetail(selectedBasket, orderCapture.getLtsOtherVoiceServiceForm().getSecondDelOptOutIddItemList(), itemDetailLtsList);
		
		switch (createServiceType) {
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
			addItemDetail(selectedBasket, orderCapture.getLtsOtherVoiceServiceForm().getDuplex2ndDelAutoInVasList(), itemDetailLtsList);
			if(orderCapture.getLtsDnChangeForm() != null 
					&& (!StringUtils.isEmpty(orderCapture.getLtsDnChangeForm().getConfirmedDn()) || !StringUtils.isEmpty(orderCapture.getLtsDnChangeForm().getReservedSrvNum()))
					&& orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType().name() == "NEW_2ND_DEL"
					&& orderCapture.getLtsDnChangeForm().getChargeItem() != null){
				addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeForm().getChargeItem()), itemDetailLtsList);
				if(orderCapture.getLtsDnChangeForm().getRebateItem() != null){
					addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeForm().getRebateItem()), itemDetailLtsList);
				}
			}
			if(orderCapture.getLtsDnChangeDuplexForm() != null 
					&& (!StringUtils.isEmpty(orderCapture.getLtsDnChangeDuplexForm().getConfirmedDn()) || !StringUtils.isEmpty(orderCapture.getLtsDnChangeDuplexForm().getReservedSrvNum()))
					&& orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType().name() == "NEW_2ND_DEL"
					&& orderCapture.getLtsDnChangeDuplexForm().getChargeItem() != null){
				addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeDuplexForm().getChargeItem()), itemDetailLtsList);	
				if(orderCapture.getLtsDnChangeForm().getRebateItem() != null){
					addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeDuplexForm().getRebateItem()), itemDetailLtsList);
				}
			}
			break;
		case CREATE_SRV_TYPE_NEW_2DEL: 
			addItemDetail(selectedBasket, orderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelAutoChangeTpList(), itemDetailLtsList);
			cancelExistingVasItem(selectedBasket, orderCapture.getLtsOtherVoiceServiceForm().getSecondDelCancelVasItemList(), itemDetailLtsList);
			break;			
		default:
			break;
		}
		
		addCommonItemDetail(createServiceType, orderCapture, selectedBasket, itemDetailLtsList);
		return itemDetailLtsList.toArray(new ItemDetailLtsDTO[itemDetailLtsList.size()]);
	}
	
	private ItemDetailLtsDTO[] createAllServiceItemDetail(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket, CreateServiceType createServiceType) {
		
		List<ItemDetailLtsDTO> itemDetailLtsList = new ArrayList<ItemDetailLtsDTO>();
		
		if (orderCapture.getLtsBasketServiceForm() != null) {
			addItemDetail(selectedBasket, orderCapture.getLtsBasketServiceForm().getBbRentalItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketServiceForm().getInstallItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketServiceForm().getBvasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketServiceForm().getPlanItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketServiceForm().getContentItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketServiceForm().getMoovItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketServiceForm().getAdminChargeItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketServiceForm().getCancelChargeItemList(), itemDetailLtsList);
			addItemSetDetail(selectedBasket, orderCapture.getLtsBasketServiceForm().getContItemSetList(), itemDetailLtsList);
			addItemSetDetail(selectedBasket, orderCapture.getLtsBasketServiceForm().getDeviceRedemSetList(), itemDetailLtsList);
		}
		
		
		
		if (CreateServiceType.CREATE_SRV_TYPE_RENEW_DEL == createServiceType
				|| CreateServiceType.CREATE_SRV_TYPE_RENEW_EYE == createServiceType
				|| CreateServiceType.CREATE_SRV_TYPE_UPGRADE == createServiceType) {
			if (orderCapture.getLtsDnChangeForm() != null ){
				// IF　NEW_DN   MAX.R.MENG
				if(orderCapture.getLtsDnChangeForm().getNumSelection() == LtsDnChangeFormDTO.Selection.USE_NEW_DN){
					if(orderCapture.getLtsOtherVoiceServiceForm() != null && orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() != null){
						if(StringUtils.equals(orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType().name() , "UPGRADE") 
							|| StringUtils.equals(orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType().name() , "RETAIN")){							
							addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeForm().getChargeItem()), itemDetailLtsList);
							if(orderCapture.getLtsDnChangeForm().getRebateItem() != null){
								addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeForm().getRebateItem()), itemDetailLtsList);
							}
						 }
					}else{
						addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeForm().getChargeItem()), itemDetailLtsList);
						if(orderCapture.getLtsDnChangeForm().getRebateItem() != null){
							addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeForm().getRebateItem()), itemDetailLtsList);
						}
					}							
			}
			if (orderCapture.getLtsDnChangeDuplexForm() != null ){
				// IF　NEW_DN
				if(orderCapture.getLtsDnChangeDuplexForm().getNumSelection() == LtsDnChangeDuplexFormDTO.Selection.USE_NEW_DN){
					if(orderCapture.getLtsOtherVoiceServiceForm() != null && orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType() != null){
						if(StringUtils.equals(orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType().name() , "UPGRADE") 
							|| StringUtils.equals(orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType().name() , "RETAIN")){
							
							addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeDuplexForm().getChargeItem()), itemDetailLtsList);
							if(orderCapture.getLtsDnChangeDuplexForm().getRebateItem() != null){
								addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeDuplexForm().getRebateItem()), itemDetailLtsList);
							}
						}
					}else{
						
						addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeDuplexForm().getChargeItem()), itemDetailLtsList);
						if(orderCapture.getLtsDnChangeDuplexForm().getRebateItem() != null){
							addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeDuplexForm().getRebateItem()), itemDetailLtsList);
						}
					}
					
				}			
			}
		 }
			
		}
		
		if (orderCapture.getLtsNowTvServiceForm() != null) {
			
			addItemDetail(selectedBasket, orderCapture.getLtsNowTvServiceForm().getNowTvFreeItemList(), itemDetailLtsList);
			
			if (AdditionalTvType.SD_PAY_CHANNEL == orderCapture.getLtsNowTvServiceForm().getAdditionalTvType()) {
				addItemDetail(selectedBasket, orderCapture.getLtsNowTvServiceForm().getNowTvPayItemList(), itemDetailLtsList);
				addItemDetail(selectedBasket, orderCapture.getLtsNowTvServiceForm().getNowTvSportItemList(), itemDetailLtsList);
			}
			else if (AdditionalTvType.SD_SPECIAL_CHANNEL == orderCapture.getLtsNowTvServiceForm().getAdditionalTvType()) {
				addItemDetail(selectedBasket, orderCapture.getLtsNowTvServiceForm().getNowTvSpecItemList(), itemDetailLtsList);
				addItemDetail(selectedBasket, orderCapture.getLtsNowTvServiceForm().getNowTvCeleItemList(), itemDetailLtsList);
				addItemDetail(selectedBasket, orderCapture.getLtsNowTvServiceForm().getNowTvSportItemList(), itemDetailLtsList);
			}
			else if (AdditionalTvType.MIRROR == orderCapture.getLtsNowTvServiceForm().getAdditionalTvType()) {
				addItemDetail(selectedBasket, orderCapture.getLtsNowTvServiceForm().getNowTvMirrorItemList(), itemDetailLtsList);
			}
			else if (AdditionalTvType.ADDITIONAL_CHANNELS == orderCapture.getLtsNowTvServiceForm().getAdditionalTvType()) {
				addItemDetail(selectedBasket, orderCapture.getLtsNowTvServiceForm().getNowTvSportItemList(), itemDetailLtsList);
			}

			addItemDetail(selectedBasket, orderCapture.getLtsNowTvServiceForm().getNowTvDeviceItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsNowTvServiceForm().getNowTvEmailItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsNowTvServiceForm().getNowTvPrintItemList(), itemDetailLtsList);				
		}
		
		if (orderCapture.getLtsBasketVasDetailForm() != null) {
			
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getE0060VasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getE0060OutVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getExistVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getHotVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getIddOutVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getIddVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getOtherVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getPeFreeItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getPeSocketItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getOptAccessaryItemList(), itemDetailLtsList);
			addItemSetDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getTeamVasSetList(), itemDetailLtsList);		
			addItemSetDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getSmartWrtySetList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getFfpHotItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getFfpOtherItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getFfpStaffItemList(), itemDetailLtsList);
			addItemSetDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getBundleVasSetList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getMoovItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getContentItemList(), itemDetailLtsList);
			addItemSetDetail(selectedBasket, orderCapture.getLtsBasketVasDetailForm().getContItemSetList(), itemDetailLtsList);
			
			// Add Profile TP/VAS 
			addItemDetailProfileLts(selectedBasket, orderCapture, itemDetailLtsList);
		}
		
		if (orderCapture.getLtsPremiumSelectionForm() != null) {
			addItemSetDetail(selectedBasket, orderCapture.getLtsPremiumSelectionForm().getPremiumSetList(), itemDetailLtsList);
			addItemSetDetail(selectedBasket, orderCapture.getLtsPremiumSelectionForm().getGiftSetList(), itemDetailLtsList);
		}

		if (orderCapture.getLtsPaymentForm() != null) {
			addItemDetail(selectedBasket, orderCapture.getLtsPaymentForm().getErChargeItemList(), itemDetailLtsList);
		}
		
		if (orderCapture.getLtsModemArrangementForm() != null) {
			addItemDetail(selectedBasket, orderCapture.getLtsModemArrangementForm().getRentalRouterItemList(), itemDetailLtsList);
		}
		
		//BOM2018061
		if (orderCapture.getLtsAppointmentForm() != null){
			addItemDetail(selectedBasket, orderCapture.getLtsAppointmentForm().getEpdItemList(), itemDetailLtsList);
		}
		
		addCommonItemDetail(createServiceType, orderCapture, selectedBasket, itemDetailLtsList);
		return itemDetailLtsList.toArray(new ItemDetailLtsDTO[itemDetailLtsList.size()]);
	}
	
	
	
	private FsaDetailDTO getSelectedFsaDetail(OrderCaptureDTO orderCapture) {
		
		List<FsaDetailDTO> fsaDetailList = null;
		
		switch (orderCapture.getLtsModemArrangementForm().getModemType()) {
		case STANDALONE:
		case SHARE_PCD:
		case SHARE_TV:
		case SHARE_BUNDLE:
			return null;
		case SHARE_EX_FSA:
			fsaDetailList = orderCapture.getLtsModemArrangementForm().getExistingFsaList();
			break;
		case SHARE_OTHER_FSA:
			fsaDetailList = orderCapture.getLtsModemArrangementForm().getOtherFsaList();
			break;
		default:
			break;
		}
		
		if (fsaDetailList != null && !fsaDetailList.isEmpty()) {
			for (FsaDetailDTO fsaDetail : fsaDetailList) {
				if (fsaDetail.isSelected()) {
					return fsaDetail;
				}
			}
		}
		return null;
	}
	
	private ChannelDetailLtsDTO[] createChannelDetailLts(OrderCaptureDTO orderCapture) {
		
		if (orderCapture.getLtsNowTvServiceForm() == null) {
			return null;
		}
		
		List<ChannelGroupDetailDTO> channelGroupDetailList = null;
		List<ChannelDetailLtsDTO> channelDetailLtsList = new ArrayList<ChannelDetailLtsDTO>();
		
		List<ItemDetailDTO> allItemDetailList = new ArrayList<ItemDetailDTO>();
		List<ItemDetailDTO> nowTvFreeItemList = orderCapture.getLtsNowTvServiceForm().getNowTvFreeItemList();
		List<ItemDetailDTO> nowTvCeleItemList = orderCapture.getLtsNowTvServiceForm().getNowTvCeleItemList();
		List<ItemDetailDTO> nowTvSportItemList = orderCapture.getLtsNowTvServiceForm().getNowTvSportItemList();
		
		if (nowTvFreeItemList != null && !nowTvFreeItemList.isEmpty()) {
			allItemDetailList.addAll(nowTvFreeItemList);
		}
		if (nowTvCeleItemList != null && !nowTvCeleItemList.isEmpty()) {
			allItemDetailList.addAll(nowTvCeleItemList);
		}
		if (nowTvSportItemList != null && !nowTvSportItemList.isEmpty()
				&& orderCapture.getLtsNowTvServiceForm().getAdditionalTvType() != AdditionalTvType.NO_ADDITIONAL_TV) {
			allItemDetailList.addAll(nowTvSportItemList);
		}

		if (!allItemDetailList.isEmpty()) {
			for (ItemDetailDTO itemDetail : allItemDetailList) {
				if (itemDetail.isSelected()) {
					ChannelDetailLtsDTO channelDetailLts = new ChannelDetailLtsDTO();
					channelDetailLts.setChannelId(itemDetail.getItemId());
					channelDetailLtsList.add(channelDetailLts);
				}
			}
		}
		
		if ( AdditionalTvType.SD_PAY_CHANNEL == orderCapture.getLtsNowTvServiceForm().getAdditionalTvType()) {
			channelGroupDetailList = orderCapture.getLtsNowTvServiceForm().getPayChannelGroupList();
		}
		if (AdditionalTvType.SD_SPECIAL_CHANNEL == orderCapture.getLtsNowTvServiceForm().getAdditionalTvType()) {
			channelGroupDetailList = orderCapture.getLtsNowTvServiceForm().getSpecChannelGroupList();
		}
		
		if (channelGroupDetailList != null && !channelGroupDetailList.isEmpty()) {
			for (ChannelGroupDetailDTO channelGroupDetail : channelGroupDetailList) {
				ChannelDetailDTO[] channelDetails = channelGroupDetail.getChannelDetails();
				if (ArrayUtils.isEmpty(channelDetails)) {
					continue;
				}
				for (ChannelDetailDTO channelDetail : channelDetails) {
					if (channelDetail.isSelected()) {
						ChannelDetailLtsDTO channelDetailLts = new ChannelDetailLtsDTO();
						channelDetailLts.setChannelId(channelDetail.getChannelId());
						channelDetailLts.setChannelAttb(this.createChannelAttbs(channelDetail.getChannelAttbs()));
						channelDetailLtsList.add(channelDetailLts);
					}
				}
			}
		}		
		return channelDetailLtsList.toArray(new ChannelDetailLtsDTO[channelDetailLtsList.size()]);
	}
	
	private ServiceCallPlanDetailLtsDTO[] createServiceCallPlanDetailLts(OrderCaptureDTO orderCapture) {
		
		LtsBasketVasDetailFormDTO vasForm = orderCapture.getLtsBasketVasDetailForm();
		
		if (vasForm == null) {
			return null;
		}
		List<ServiceCallPlanDetailLtsDTO> serviceCallPlanList = new ArrayList<ServiceCallPlanDetailLtsDTO>();
		
		addCallPlanDetailList(orderCapture, vasForm.getFfpHotItemList(), serviceCallPlanList);
		addCallPlanDetailList(orderCapture, vasForm.getFfpOtherItemList(), serviceCallPlanList);
		addCallPlanDetailList(orderCapture, vasForm.getFfpStaffItemList(), serviceCallPlanList);
		
		if(vasForm.getBundleVasSetList() != null){
			for(ItemSetDetailDTO itemSet : vasForm.getBundleVasSetList()){
				addCallPlanDetailList(orderCapture, Arrays.asList(itemSet.getItemDetails()) , serviceCallPlanList);
			}
		}
		
		if (serviceCallPlanList.isEmpty()) {
			return null;
		}
		
		return serviceCallPlanList.toArray(new ServiceCallPlanDetailLtsDTO[serviceCallPlanList.size()]);
		
	}
	
	private ServiceCallPlanDetailLtsDTO[] addCallPlanDetailList(OrderCaptureDTO orderCapture, List<ItemDetailDTO> callPlanItemList, List<ServiceCallPlanDetailLtsDTO> serviceCallPlanList) {
		
		if (callPlanItemList == null || callPlanItemList.isEmpty()) {
			return null;
		}
		
		AccountDetailProfileLtsDTO profileIAccount = LtsSbOrderHelper.getProfileAccount(orderCapture.getLtsServiceProfile(), LtsConstant.ACCOUNT_CHARGE_TYPE_I);
		
		for (ItemDetailDTO callPlanItem : callPlanItemList) {
			if (!callPlanItem.isSelected()) {
				continue;
			}
			
			if(!LtsConstant.ITEM_TYPE_FFP_HOT.equals(callPlanItem.getItemType())
					&& !LtsConstant.ITEM_TYPE_FFP_OTHER.equals(callPlanItem.getItemType())
					&& !LtsConstant.ITEM_TYPE_FFP_STAFF.equals(callPlanItem.getItemType())
					&& !LtsConstant.ITEM_TYPE_VAS_FFP.equals(callPlanItem.getItemType())){
				continue;
			}
			
			String[] callPlanCds = callPlanLtsService.getCallPlanCd(callPlanItem.getItemId());
			
			if (ArrayUtils.isEmpty(callPlanCds)) {
				continue;
			}
			
			for (String callPlanCd : callPlanCds) {
				
				String planType = callPlanLtsService.getCallPlanType(callPlanCd);
				String contractMonth = ltsOfferService.getItemContractPeriod(callPlanItem.getItemId());
				if (StringUtils.equalsIgnoreCase("GIFT", planType)) {
					contractMonth = callPlanLtsService.getCallPlanContractPeriod(callPlanCd);
				}
				
				ServiceCallPlanDetailLtsDTO callPlanDetail = new ServiceCallPlanDetailLtsDTO();
				callPlanDetail.setItemId(callPlanItem.getItemId());
				callPlanDetail.setContractMonth(contractMonth);
				if (orderCapture.getLtsAppointmentForm() != null) {
					callPlanDetail.setEffStartDate(orderCapture.getLtsAppointmentForm().getInstallationDate());
				}
				callPlanDetail.setIoInd(LtsConstant.IO_IND_INSTALL);
				callPlanDetail.setPlanType(planType);
				callPlanDetail.setPlanCd(callPlanCd);
				callPlanDetail.setPlanHolder(profileIAccount != null ? profileIAccount.getAcctNum() : "");
				callPlanDetail.setPlanHolderType("A");
				serviceCallPlanList.add(callPlanDetail);
			}
		}
		if (serviceCallPlanList.isEmpty()) {
			return null;
		}
		return serviceCallPlanList.toArray(new ServiceCallPlanDetailLtsDTO[serviceCallPlanList.size()]);
	}
	
	
	private ServiceDetailDTO createRenewalServiceDetail(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket, 
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getLtsServiceProfile();
		
		if (!LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			return null;
		}
		
		CreateServiceType createServiceType = StringUtils
				.isNotEmpty(serviceProfile.getExistEyeType()) ? CreateServiceType.CREATE_SRV_TYPE_RENEW_EYE
				: CreateServiceType.CREATE_SRV_TYPE_RENEW_DEL;
		
		ServiceDetailLtsDTO renewalService = createServiceDetailLts(
				createServiceType, serviceProfile, selectedBasket,
				orderCapture, accountDetailLtsMap, customerDetailLtsMap);
		
		renewalService.setGrpType(StringUtils.isNotEmpty(serviceProfile.getExistEyeType()) ? LtsConstant.GROUP_TYPE_EYE : null);
		renewalService.setRedeemPremiumInd(orderCapture.getLtsMiscellaneousForm().isRedeemPrevPremium() ? "Y" : null);
		renewalService.setPendingApprovalCd(getPendingApprovalCd(createServiceType, orderCapture));
		renewalService.setPaperBillInd(isPaperBillSelected(orderCapture) ? "Y" : null);
		renewalService.setPendingOcid(serviceProfile.getPendingOcid());
		renewalService.setPendingOcidSrd(serviceProfile.getPendingOcSrd());
		renewalService.setCallPlanDowngradeInd(getCallPlanDowngradeInd(orderCapture, serviceProfile, selectedBasket));
		renewalService.setCancelVasInd(isCancelProfileVas(orderCapture) ? "Y" : null);
		renewalService.setSuggestSrd(orderCapture.getLtsAppointmentForm() != null ? orderCapture.getLtsAppointmentForm().getEarliestSRD().getDateString() : null);
		renewalService.setItemDtls(createAllServiceItemDetail(orderCapture, selectedBasket, createServiceType));
		renewalService.setOneTwoThreeTvInd(LtsSbOrderHelper.getBundleTvInd(orderCapture, serviceProfile));
		renewalService.setSrvCallPlanDtls(createServiceCallPlanDetailLts(orderCapture));
		if (orderCapture.getLtsAppointmentForm() != null) {
			renewalService.setForceFieldVisitInd(orderCapture.getLtsAppointmentForm().isFieldVisitRequired() ? "Y" : null);	
		}
		
		//recontract
		if(orderCapture.getLtsMiscellaneousForm().isRecontract()
				&& orderCapture.getLtsRecontractForm() != null){
			renewalService.setRecontractInd("Y");
		}
		renewalService.setRecontract(createRecontractLtsDTO(orderCapture, createServiceType));
		
		//TODO BOM2018053
		renewalService.setOrderAttbs(createOrderAttbs(createServiceType, orderCapture));
		
		return renewalService;
	}
	
	
	private ServiceDetailDTO createUpgradeServiceDetail(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket,
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		if (!LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())) {
			return null;
		}
		
		ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getLtsServiceProfile();
		ServiceDetailLtsDTO upgradeService = createServiceDetailLts(
				CreateServiceType.CREATE_SRV_TYPE_UPGRADE, serviceProfile, selectedBasket,
				orderCapture, accountDetailLtsMap, customerDetailLtsMap);
		upgradeService.setGrpType(LtsConstant.GROUP_TYPE_EYE);
		upgradeService.setRedeemPremiumInd(orderCapture.getLtsMiscellaneousForm().isRedeemPrevPremium() ? "Y" : null);
		upgradeService.setPendingApprovalCd(getPendingApprovalCd(CreateServiceType.CREATE_SRV_TYPE_UPGRADE, orderCapture));
		upgradeService.setPaperBillInd(isPaperBillSelected(orderCapture) ? "Y" : null);
		upgradeService.setPendingOcid(serviceProfile.getPendingOcid());
		upgradeService.setPendingOcidSrd(serviceProfile.getPendingOcSrd());
		upgradeService.setCallPlanDowngradeInd(getCallPlanDowngradeInd(orderCapture, serviceProfile, selectedBasket));
		upgradeService.setCancelVasInd(isCancelProfileVas(orderCapture) ? "Y" : null);
		upgradeService.setItemDtls(createAllServiceItemDetail(orderCapture, selectedBasket, CreateServiceType.CREATE_SRV_TYPE_UPGRADE));
		upgradeService.setOneTwoThreeTvInd(LtsSbOrderHelper.getBundleTvInd(orderCapture, serviceProfile));
		upgradeService.setSrvCallPlanDtls(createServiceCallPlanDetailLts(orderCapture));

		LtsAppointmentFormDTO appntForm = orderCapture.getLtsAppointmentForm();
		if(appntForm != null){
			upgradeService.setSuggestSrd(appntForm.getEarliestSRD().getDateString());
			upgradeService.setForceFieldVisitInd(appntForm.isFieldVisitRequired() ? "Y" : "N");	
		}
		
		if(serviceProfile.getDeviceOfferActions() != null){
			for(OfferActionLtsDTO offerAction: serviceProfile.getDeviceOfferActions()){
				if((LtsConstant.EYE_TYPE_EYE2A.equalsIgnoreCase(offerAction.getToProd()) || "*".equals(offerAction.getToProd()))
						&& "O".equals(offerAction.getAction())
						&& LtsConstant.DEVICE_TYPE_1020.equals(orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType())){
					upgradeService.setReturnDeviceInd("Y");
					break;
				}
				if((LtsConstant.EYE_TYPE_EYE3A.equalsIgnoreCase(offerAction.getToProd()) || "*".equals(offerAction.getToProd()))
						&& "O".equals(offerAction.getAction())
						&& LtsConstant.DEVICE_TYPE_EYE3A.equals(orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType())){
					upgradeService.setReturnDeviceInd("Y");
					break;
				}
			}
		}
		if (orderCapture.getUpgradeEyeInfo() != null) {
			upgradeService.setReturnDeviceInd(orderCapture.getUpgradeEyeInfo().getReturnDeviceInd());
		}
		
		//recontract
		if(orderCapture.getLtsMiscellaneousForm().isRecontract()
				&& orderCapture.getLtsRecontractForm() != null){
			upgradeService.setRecontractInd("Y");
		}
		upgradeService.setRecontract(createRecontractLtsDTO(orderCapture, CreateServiceType.CREATE_SRV_TYPE_UPGRADE));
		
		//TODO BOM2018053
		upgradeService.setOrderAttbs(createOrderAttbs(CreateServiceType.CREATE_SRV_TYPE_UPGRADE, orderCapture));
		
		return upgradeService;
	}
	
	private ServiceDetailDTO createNew2DelService(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket,
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		if (orderCapture.getLtsOtherVoiceServiceForm() == null
				|| orderCapture.getLtsOtherVoiceServiceForm().getCreate2ndDel() == null
				|| !orderCapture.getLtsOtherVoiceServiceForm().getCreate2ndDel().booleanValue()) {
			return null;
		}
		if (orderCapture.getLtsOtherVoiceServiceForm().isDuplexProfile()
				//Modified by Max.R.MENG
				&& null != orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType()
				&& null != orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType()
				&& DuplexSrvType.CANCEL != orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() 
				&& DuplexSrvType.CANCEL != orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType()) {
			
			    if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			    	
			      if (orderCapture.getLtsOtherVoiceServiceForm().isRemoveRenewalDuplex()) {
					return null;
				  }
			}
			else {
				return null;
			}
		}
		if (StringUtils.isEmpty(orderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelDn())
				|| StringUtils.isEmpty(orderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus())
				|| !orderCapture.getLtsOtherVoiceServiceForm().isSecondDelProfileValid()) {
			return null;
		}
		if (StringUtils.equals(LtsConstant.INVENT_STS_WORKING, orderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus())
				&& orderCapture.getSecondDelServiceProfile() == null) {
			return null;
		}
		
		ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getSecondDelServiceProfile();
		
		ServiceDetailLtsDTO new2DelService = createServiceDetailLts(
				CreateServiceType.CREATE_SRV_TYPE_NEW_2DEL, serviceProfile, selectedBasket,
				orderCapture, accountDetailLtsMap, customerDetailLtsMap);
		
		if (orderCapture.getLtsAppointmentForm() != null) {
			new2DelService.setForceFieldVisitInd(orderCapture.getLtsAppointmentForm().isSecDelFieldVisitRequired() ? "Y" : "N");	
		}
		
		new2DelService.setItemDtls(createDuplexServiceItemDetail(
				CreateServiceType.CREATE_SRV_TYPE_NEW_2DEL, orderCapture, selectedBasket));
		
		if (orderCapture.getSecondDelServiceProfile() != null 
				&& orderCapture.getLtsOtherVoiceServiceForm().isShowSecondDelRedeemPremium()
				&& orderCapture.getLtsOtherVoiceServiceForm().getSecondDelRedeemPremium() != null ) {
			new2DelService.setRedeemPremiumInd(orderCapture.getLtsOtherVoiceServiceForm().getSecondDelRedeemPremium().booleanValue() ? "Y" : null);
			new2DelService.setPendingApprovalCd(getPendingApprovalCd(CreateServiceType.CREATE_SRV_TYPE_NEW_2DEL, orderCapture));
		}
		if (StringUtils.equals(LtsConstant.INVENT_STS_RESERVED, orderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus())
				&& orderCapture.getSecondDelServiceProfile() == null) {
			new2DelService.setReservedDnInd("Y");
		}
		
//		new2DelService.setRecontract(createRecontractLtsDTO(orderCapture, CreateServiceType.CREATE_SRV_TYPE_NEW_2DEL));
		
		return new2DelService;
	}  
	
	private ServiceDetailDTO createDuplexTo2DelService(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket,
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getDuplexNum())) {
			return null;
		}
		
		if (orderCapture.getLtsOtherVoiceServiceForm() == null
				|| orderCapture.getLtsOtherVoiceServiceForm().getCreate2ndDel() == null
				|| !orderCapture.getLtsOtherVoiceServiceForm().getCreate2ndDel().booleanValue()) {
			return null;
		}
		if (orderCapture.getLtsOtherVoiceServiceForm().isDuplexProfile()
				&& !(DuplexSrvType.NEW_2ND_DEL == orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() 
				|| DuplexSrvType.NEW_2ND_DEL == orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType())) {
			return null;
		}
		
		ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getLtsServiceProfile();
		ServiceDetailLtsDTO duplexTo2DelService = createServiceDetailLts(
				CreateServiceType.CREATE_SRV_TYPE_DUPLEX_TO_2DEL, serviceProfile, selectedBasket,
				orderCapture, accountDetailLtsMap, customerDetailLtsMap);
		duplexTo2DelService.setActualDocId(orderCapture
				.getLtsCustomerIdentificationForm().getSecDelId());
		duplexTo2DelService.setActualDocType(orderCapture
				.getLtsCustomerIdentificationForm().getSecDelDocType());
		duplexTo2DelService.setItemDtls(createDuplexServiceItemDetail(
				CreateServiceType.CREATE_SRV_TYPE_DUPLEX_TO_2DEL, orderCapture, selectedBasket));
		duplexTo2DelService.setForceFieldVisitInd(orderCapture.getLtsAppointmentForm().isSecDelFieldVisitRequired() ? "Y" : "N");
//		duplexTo2DelService.setRecontract(createRecontractLtsDTO(orderCapture, CreateServiceType.CREATE_SRV_TYPE_DUPLEX_TO_2DEL));
		
		return duplexTo2DelService;
			
	} 
	
	private ServiceDetailDTO createDuplexRemoveService(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket,
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {

		if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getDuplexNum())) {
			return null;
		}
		
		if (orderCapture.getLtsOtherVoiceServiceForm() == null
				|| orderCapture.getLtsOtherVoiceServiceForm().getCreate2ndDel() == null
				|| orderCapture.getLtsOtherVoiceServiceForm().getCreate2ndDel() == false) {
			return null;
		}
		if (orderCapture.getLtsOtherVoiceServiceForm().isDuplexProfile()
				&& !(DuplexSrvType.CANCEL == orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() 
				|| DuplexSrvType.CANCEL == orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType())) {
			return null;
		}
		
		ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getLtsServiceProfile();
		ServiceDetailLtsDTO duplexRemoveService = createServiceDetailLts(
				CreateServiceType.CREATE_SRV_TYPE_DUPLEX_REMOVE, serviceProfile, selectedBasket,
				orderCapture, accountDetailLtsMap, customerDetailLtsMap);
		return duplexRemoveService;
	}

	private ServiceDetailDTO createSipRemoveService(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket,
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		
		ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getLtsServiceProfile();
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())
				|| !LtsSbOrderHelper.isExistingEyeProfile(serviceProfile)) {
			return null;
		}
		
		ServiceGroupMemberProfileDTO[] profileGroupMembers = serviceProfile.getSrvGrp().getGrpMems();
		
		if (ArrayUtils.isEmpty(profileGroupMembers) || profileGroupMembers.length <= 2) {
			return null;
		}
		
		for (ServiceGroupMemberProfileDTO profileGroupMember : profileGroupMembers) {
			if (LtsConstant.DAT_CD_NCR.equals(profileGroupMember.getDatCd())) {
				return createServiceDetailLts(
						CreateServiceType.CREATE_SRV_TYPE_SIP_REMOVE, serviceProfile, selectedBasket,
						orderCapture, accountDetailLtsMap, customerDetailLtsMap);		
			}
		}
		
		return null;
	}
	
	//Modified By Max.R.Meng
	private ServiceDetailLtsDTO createDuplexNoChangeService(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket,
				Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap){
			
			if(!orderCapture.getLtsMiscellaneousForm().isDnChange()){
				return null;
			}
		
			if( orderCapture.getLtsDnChangeDuplexForm() == null){
				return null;
			}		
			
			if(orderCapture.getLtsDnChangeDuplexForm().getConfirmedDn() == null && orderCapture.getLtsDnChangeDuplexForm().getReservedSrvNum() == null){
				return null;
			}		
			
			if(orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.CANCEL
					|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.NEW_2ND_DEL
					|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType() == DuplexSrvType.CANCEL
					|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType() == DuplexSrvType.NEW_2ND_DEL){
				
				return null;				
			}
			ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getLtsServiceProfile();
			ServiceDetailLtsDTO duplexChangeService = createServiceDetailLts(
					CreateServiceType.CREATE_SRV_TYPE_DUPLEX_NUM_NO_CHANGE, serviceProfile, selectedBasket,
					orderCapture, accountDetailLtsMap, customerDetailLtsMap);
//			List<ItemDetailLtsDTO> itemDetailLtsList = new ArrayList<ItemDetailLtsDTO>();
//			if(orderCapture.getLtsDnChangeDuplexForm().getChargeItem() != null){
//				addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeDuplexForm().getChargeItem()), itemDetailLtsList);			
//			}
//			if(orderCapture.getLtsDnChangeForm().getRebateItem() != null){
//				addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsDnChangeDuplexForm().getRebateItem()), itemDetailLtsList);
//			}
//			duplexChangeService.setItemDtls(itemDetailLtsList.toArray(new ItemDetailLtsDTO[itemDetailLtsList.size()]));	
			return duplexChangeService;
		}
	
	private ServiceDetailLtsDTO createServiceDetailLts(CreateServiceType createServiceType, ServiceDetailProfileLtsDTO serviceProfile,
			BasketDetailDTO selectedBasket, OrderCaptureDTO orderCapture, Map<String, AccountDetailLtsDTO> accountDetailLtsMap,
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {

		String serviceNum = getServiceNum(createServiceType, orderCapture, serviceProfile);
		ServiceDetailLtsDTO serviceDetailLts = new ServiceDetailLtsDTO();		
		serviceDetailLts.setAccounts(createAccountServiceAssigns(createServiceType, orderCapture, serviceProfile, accountDetailLtsMap, customerDetailLtsMap));
		
		serviceDetailLts.setAppointmentDtl(createAppointmentDetailLts(createServiceType, orderCapture));
		serviceDetailLts.setCopyErIaToBa(isCopyErIaToBa(createServiceType, orderCapture) ? "Y" : null);
		
		serviceDetailLts.setDummyDocIdInd(isDummyDoc(createServiceType, orderCapture) ? "Y" : null);
		
		serviceDetailLts.setErInd(isServiceEr(createServiceType, orderCapture) ? "Y" : null);
		serviceDetailLts.setOrderType(getLtsOrderType(createServiceType, orderCapture));
		serviceDetailLts.setSrvNum(StringUtils.leftPad(serviceNum, 12, "0"));
		
		serviceDetailLts.setSuggestSrd(getSuggestedSrd(orderCapture));
		serviceDetailLts.setSuggestSrdReasonCd(getSuggestedSrdReason(orderCapture));
		serviceDetailLts.setTypeOfSrv(LtsConstant.SERVICE_TYPE_TEL);
		serviceDetailLts.setDeviceType(orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType());
		serviceDetailLts.setFromProd(getLtsFromProductType(createServiceType, serviceProfile, orderCapture));
		serviceDetailLts.setToProd(getLtsToProductType(createServiceType, selectedBasket, serviceProfile, orderCapture));
		serviceDetailLts.setFromSrvType(getLtsFromSrvType(createServiceType, orderCapture));
		serviceDetailLts.setToSrvType(getLtsToSrvType(createServiceType, orderCapture, selectedBasket));
		serviceDetailLts.setRemarks(getRemarkDetailLts(createServiceType, orderCapture));
		serviceDetailLts.setActualDocType(getActualDocType(createServiceType, orderCapture));
		serviceDetailLts.setActualDocId(getActualDocId(createServiceType, orderCapture));
		serviceDetailLts.setSwitchPlanInd(orderCapture.getLtsMiscellaneousForm().isSwitchTp() ? "Y" : null);
		serviceDetailLts.setDFormSerial(orderCapture.getLtsMiscellaneousForm().isSubmitDisForm() ? orderCapture.getLtsMiscellaneousForm().getDisFormSerial() : null);
		serviceDetailLts.setWaiveDFormReasonCd(orderCapture.getLtsMiscellaneousForm().isSubmitDisForm() ? orderCapture.getLtsMiscellaneousForm().getWaiveDisFormReason() : null);
		serviceDetailLts.setSubc2gBundleInd(getSubc2gBundleInd(orderCapture));
		serviceDetailLts.setBundle2gNum(orderCapture.getLtsBasketVasDetailForm().getBundle2gNum());
		setAdjustmentDtl(orderCapture, serviceDetailLts);
		
	    
		serviceDetailLts.setNewSrvNum(orderCapture.getLtsMiscellaneousForm().isDnChange()? getNewSrvNum(createServiceType, orderCapture, serviceProfile):null);
		serviceDetailLts.setDnSource(getDnSource(createServiceType, orderCapture));
		
		serviceDetailLts.setDeceaseType(getDeceasedType(orderCapture));
		
		ThirdPartyDetailLtsDTO thirdPartyDetail = createThirdPartyDetailLts(createServiceType, orderCapture);
		serviceDetailLts.setThirdPartyAppln(thirdPartyDetail != null ? "Y" : null);
		serviceDetailLts.setThirdPartyDtl(thirdPartyDetail);
		serviceDetailLts.setUpdateDnProfile(orderCapture.getLtsRecontractForm() != null ? orderCapture.getLtsRecontractForm().getUpdateDnProfile() : null);
		
		if (serviceProfile != null) {
			serviceDetailLts.setCcServiceId(getCcServiceId(createServiceType, serviceProfile));
			serviceDetailLts.setCcServiceMemNum(getCcServiceMemNum(createServiceType, serviceProfile));
			serviceDetailLts.setDatCd(serviceProfile.getDatCd());
			serviceDetailLts.setDuplexInd(getDuplexInd(serviceNum, serviceProfile));
			serviceDetailLts.setTenure(String.valueOf(serviceProfile.getLtsTenure()));
			serviceDetailLts.setTwoNInd(getTwoNInd(serviceNum, serviceProfile));
			serviceDetailLts.setSharedBsnInd(serviceProfile.isSharedBsn() ? "Y" : null);
			serviceDetailLts.setOldOsType(getOldOsType(serviceProfile, orderCapture));
			serviceDetailLts.setNewOsType(getNewOsType(createServiceType, selectedBasket));
		}
		
		serviceDetailLts.setFfpOnlyInd(orderCapture.getLtsBasketVasDetailForm().isSelectedFfpOnly()? "Y" : "N");
		
		
		return serviceDetailLts;
	}
	
	private String getNewOsType(CreateServiceType createServiceType, BasketDetailDTO selectedBasket){
		
		if(StringUtils.isNotBlank(selectedBasket.getOsType())){
			return selectedBasket.getOsType();
		}else{
			switch (createServiceType) {
			case CREATE_SRV_TYPE_RENEW_DEL:
				return null;
			case CREATE_SRV_TYPE_RENEW_EYE:
			case CREATE_SRV_TYPE_UPGRADE:
				return LtsConstant.OS_TYPE_AND;
			default:
				return null;
			}
		}
		
	}
	
	private String getOldOsType(ServiceDetailProfileLtsDTO serviceProfile, OrderCaptureDTO orderCapture){
		
		if(serviceProfile == null || !LtsSbOrderHelper.isExistingEyeProfile(serviceProfile)){
			return null;
		}
		

		ItemDetailProfileLtsDTO profile = null;
		if(LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType())){
			profile = LtsSbOrderHelper.getProfileServiceTermPlan(serviceProfile);
		} 
		else if(LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())){
			profile = LtsSbOrderHelper.getExpiredTermPlanWithin6Mths(serviceProfile);
		}
		
		String profileItemOsType = ltsOfferService.getItemProfileOsType(profile);
		
		if(profileItemOsType != null){
			return profileItemOsType;
		}
		
		return LtsConstant.OS_TYPE_AND;
	}
	
	private String getDeceasedType(OrderCaptureDTO orderCapture) {
		if (!LtsSbOrderHelper.isRecontractOrder(orderCapture)) {
			return null; 
		}
		if (orderCapture.getLtsRecontractForm().isDeceasedCase()) {
			return orderCapture.getLtsRecontractForm().getRefundType();
		}
		return null;
	}
	
	public void setAdjustmentDtl(OrderCaptureDTO orderCapture, ServiceDetailLtsDTO serviceDetailLts) {
		if (orderCapture.getLtsAppointmentForm() != null) {
			serviceDetailLts.setAdjustAmount(orderCapture.getLtsAppointmentForm().getAdjResult());
			serviceDetailLts.setAdjustRate(orderCapture.getLtsAppointmentForm().getAdjAmount());
			serviceDetailLts.setAdjustStartDate(orderCapture.getLtsAppointmentForm().getAdjStartDate());
			serviceDetailLts.setAdjustEndDate(orderCapture.getLtsAppointmentForm().getAdjEndDate());
		}
	}
	
	private String getCcServiceId(CreateServiceType createServiceType, ServiceDetailProfileLtsDTO serviceProfile) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_SIP_REMOVE:
			return serviceProfile.getSrvGrp().getMemberByType(LtsConstant.DAT_CD_NCR).getCcSrvId();
		default:
			return serviceProfile.getCcSrvId();
		}
	}
	
	private String getCcServiceMemNum(CreateServiceType createServiceType, ServiceDetailProfileLtsDTO serviceProfile) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_DUPLEX_REMOVE:
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
			return serviceProfile.getDuplexCcSrvMemNum();
		default:
			return serviceProfile.getCcSrvMemNum();
		}
	}
	
	private String getLtsFromProductType(CreateServiceType pCreateSrvType, ServiceDetailProfileLtsDTO pServiceProfile, OrderCaptureDTO pOrderCapture) {
		
		switch (pCreateSrvType) {
		case CREATE_SRV_TYPE_RENEW_DEL:
			return LtsConstant.LTS_PRODUCT_TYPE_DEL;
		case CREATE_SRV_TYPE_RENEW_EYE:
			return pServiceProfile.getExistEyeType().toUpperCase();
		case CREATE_SRV_TYPE_UPGRADE:
		case CREATE_SRV_TYPE_SIP_REMOVE:
			if (pServiceProfile == null) {
				return null;
			}
			if (StringUtils.isBlank(pServiceProfile.getExistEyeType()) || pServiceProfile.getSrvGrp() == null) {
				return LtsConstant.LTS_PRODUCT_TYPE_DEL;
			}
			return pServiceProfile.getExistEyeType().toUpperCase();
		case CREATE_SRV_TYPE_DUPLEX_REMOVE:
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_DUPLEX_NUM_CHANGE:
		case CREATE_SRV_TYPE_DUPLEX_NUM_NO_CHANGE:
			return LtsConstant.LTS_PRODUCT_TYPE_DEL;
		case CREATE_SRV_TYPE_NEW_2DEL:
			return LtsConstant.INVENT_STS_WORKING.equals(pOrderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus()) ?
				 LtsConstant.LTS_PRODUCT_TYPE_DEL : LtsConstant.LTS_PRODUCT_TYPE_NEW;
		default:
			return null;
		}
	}
	
	private String getLtsToProductType(CreateServiceType pCreateSrvType, BasketDetailDTO pSelectedBasket, ServiceDetailProfileLtsDTO serviceProfile, OrderCaptureDTO orderCapture) {
		
		if (LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType())) {
			return getLtsFromProductType(pCreateSrvType, serviceProfile, orderCapture);
		}
		
		switch (pCreateSrvType) {
		
		case CREATE_SRV_TYPE_RENEW_DEL:
		case CREATE_SRV_TYPE_DUPLEX_NUM_CHANGE:
		case CREATE_SRV_TYPE_DUPLEX_NUM_NO_CHANGE:
			return LtsConstant.LTS_PRODUCT_TYPE_DEL;
		case CREATE_SRV_TYPE_RENEW_EYE:
			return serviceProfile.getExistEyeType().toUpperCase();
		case CREATE_SRV_TYPE_UPGRADE:
			if (StringUtils.equals(LtsConstant.DEVICE_TYPE_1020, pSelectedBasket.getType())) {
				return LtsConstant.LTS_PRODUCT_TYPE_EYE_2_A;
			}
			if (StringUtils.equals(LtsConstant.DEVICE_TYPE_EYE3A, pSelectedBasket.getType())) {
				return LtsConstant.LTS_PRODUCT_TYPE_EYE_3_A;
			}
			return null;
		case CREATE_SRV_TYPE_SIP_REMOVE:
		case CREATE_SRV_TYPE_DUPLEX_REMOVE:
			return LtsConstant.LTS_PRODUCT_TYPE_REMOVE;
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_NEW_2DEL:
			return LtsConstant.LTS_PRODUCT_TYPE_2DEL;
		default:
			return null;
		}
	}
	
	private String getLtsFromSrvType(CreateServiceType pCreateServiceType, OrderCaptureDTO pOrderCapture) {
		ServiceDetailProfileLtsDTO serviceProfile = pOrderCapture.getLtsServiceProfile();
		switch (pCreateServiceType) {
		case CREATE_SRV_TYPE_RENEW_DEL:
		case CREATE_SRV_TYPE_RENEW_EYE:
		case CREATE_SRV_TYPE_UPGRADE:
			if (serviceProfile == null) {
				return null;
			}
			
			// DUPLEX
			if (pOrderCapture.getLtsOtherVoiceServiceForm() != null && pOrderCapture.getLtsOtherVoiceServiceForm().isDuplexProfile()) {
				if (pOrderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.UPGRADE
						|| pOrderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.RETAIN) {
					return LtsConstant.LTS_SRV_TYPE_DNX;
				}
				if (pOrderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType() == DuplexSrvType.UPGRADE
						|| pOrderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType() == DuplexSrvType.RETAIN) {
					return LtsConstant.LTS_SRV_TYPE_DNY;
				}
			}
			
			// EXISTING EYE
			if (serviceProfile.getSrvGrp() != null && ArrayUtils.isNotEmpty(serviceProfile.getSrvGrp().getGrpMems())) {
				 for (ServiceGroupMemberProfileDTO groupMemberProfile : serviceProfile.getSrvGrp().getGrpMems()) {
					 if (StringUtils.equals(serviceProfile.getSrvNum(), groupMemberProfile.getSrvNum())) {
						return StringUtils.equals(LtsConstant.DAT_CD_DEL,
								groupMemberProfile.getDatCd()) ? LtsConstant.LTS_SRV_TYPE_PE
								: LtsConstant.LTS_SRV_TYPE_SIP;
					 }
				 }
			}
			
			return LtsConstant.LTS_SRV_TYPE_DEL;
		case CREATE_SRV_TYPE_DUPLEX_REMOVE:
			if (pOrderCapture.getLtsOtherVoiceServiceForm() != null && pOrderCapture.getLtsOtherVoiceServiceForm().isDuplexProfile()) {
				if (pOrderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.CANCEL) {
					return LtsConstant.LTS_SRV_TYPE_DNX;
				}
				if (pOrderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType() == DuplexSrvType.CANCEL) {
					return LtsConstant.LTS_SRV_TYPE_DNY;
				}
			}
			break;
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
			if (pOrderCapture.getLtsOtherVoiceServiceForm() != null && pOrderCapture.getLtsOtherVoiceServiceForm().isDuplexProfile()) {
				if (pOrderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.NEW_2ND_DEL) {
					return LtsConstant.LTS_SRV_TYPE_DNX;
				}
				if (pOrderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType() == DuplexSrvType.NEW_2ND_DEL) {
					return LtsConstant.LTS_SRV_TYPE_DNY;
				}
			}
			break;

		case CREATE_SRV_TYPE_NEW_2DEL:
			return LtsConstant.INVENT_STS_WORKING.equals(pOrderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus()) ?
					 LtsConstant.LTS_PRODUCT_TYPE_DEL : LtsConstant.LTS_PRODUCT_TYPE_NEW;
		case CREATE_SRV_TYPE_SIP_REMOVE:
			return LtsConstant.LTS_SRV_TYPE_SIP;
		case CREATE_SRV_TYPE_DUPLEX_NUM_CHANGE:
		case CREATE_SRV_TYPE_DUPLEX_NUM_NO_CHANGE:
			return LtsConstant.LTS_SRV_TYPE_DNY;
		}
		return null;
	}
	
	private String getLtsToSrvType(CreateServiceType pCreateServiceType, OrderCaptureDTO pOrderCapture, BasketDetailDTO pSelectedBasket) {
		
		if (LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(pOrderCapture.getOrderSubType())) {
			return getLtsFromSrvType(pCreateServiceType, pOrderCapture);
		}
		
		switch (pCreateServiceType) {
		case CREATE_SRV_TYPE_RENEW_DEL:
			if (pSelectedBasket != null) {
				if (StringUtils.isNotEmpty(pSelectedBasket.getDuplexInd())) {
					if (StringUtils.equals(pOrderCapture.getLtsServiceProfile().getSrvNum(), pOrderCapture.getLtsCustomerInquiryForm().getServiceNum())) {
						return LtsConstant.LTS_SRV_TYPE_DNX;
					}
					if (StringUtils.equals(pOrderCapture.getLtsServiceProfile().getDuplexNum(), pOrderCapture.getLtsCustomerInquiryForm().getServiceNum())) {
						return LtsConstant.LTS_SRV_TYPE_DNY;
					}
				}
			}
			return LtsConstant.LTS_SRV_TYPE_DEL;
		case CREATE_SRV_TYPE_UPGRADE:
		case CREATE_SRV_TYPE_RENEW_EYE:
			if (pSelectedBasket != null) {
				return StringUtils.equalsIgnoreCase("Y", pSelectedBasket.getPeInd()) ? LtsConstant.LTS_SRV_TYPE_PE : LtsConstant.LTS_SRV_TYPE_SIP;
			}
		case CREATE_SRV_TYPE_DUPLEX_REMOVE:
		case CREATE_SRV_TYPE_SIP_REMOVE:
			return LtsConstant.LTS_SRV_TYPE_REMOVE;
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL :
		case CREATE_SRV_TYPE_NEW_2DEL:
			return LtsConstant.LTS_SRV_TYPE_2DEL;
		case CREATE_SRV_TYPE_DUPLEX_NUM_CHANGE:
		case CREATE_SRV_TYPE_DUPLEX_NUM_NO_CHANGE:
			return LtsConstant.LTS_SRV_TYPE_DNY;
		default:
			return null;
		}
	}
	
	private String getImsFromProductType(OrderCaptureDTO pOrderCapture) {
		LtsModemArrangementFormDTO modemArrangementForm = pOrderCapture.getLtsModemArrangementForm();
		if (modemArrangementForm == null) {
			return null;
		}
		
		switch (modemArrangementForm.getModemType()) {
		case SHARE_EX_FSA:
		case SHARE_OTHER_FSA:	
			FsaDetailDTO selectedFsaDetail = LtsSbOrderHelper.getSelectedFsa(modemArrangementForm);
			if (selectedFsaDetail == null) {
				return null;
			}
			return StringUtils.replace(selectedFsaDetail.getExistingService().getDesc(), "TV", "").toUpperCase();
		case SHARE_BUNDLE:
		case SHARE_PCD:
		case SHARE_TV:
		case STANDALONE:
			return LtsConstant.IMS_PRODUCT_TYPE_NEW;
		default:
			return null;
		}
	}
	
	private String getImsToProductType(OrderCaptureDTO pOrderCapture) {
		
		if (LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(pOrderCapture.getOrderSubType())) {
			return getImsFromProductType(pOrderCapture);
		}
		
		LtsModemArrangementFormDTO modemArrangementForm = pOrderCapture.getLtsModemArrangementForm();
		if (modemArrangementForm == null) {
			return null;
		}

//		if (LtsConstant.MODEM_TYPE_2L2B.equals(pOrderCapture.getModemTechnologyAissgn().getModemArrangment())
//				&& LtsConstant.ORDER_TYPE_INSTALL.equals(pOrderCapture.getModemTechnologyAissgn().getImsOrderType())) {
//			return LtsConstant.IMS_PRODUCT_TYPE_WALL_GARDEN;
//		}
		
		switch (modemArrangementForm.getModemType()) {
		case SHARE_EX_FSA:
		case SHARE_OTHER_FSA:
			FsaDetailDTO selectedFsaDetail = LtsSbOrderHelper.getSelectedFsa(modemArrangementForm);
			if (selectedFsaDetail == null) {
				return null;
			}
			if (selectedFsaDetail.getNewService() != null) {
				return StringUtils.replace(selectedFsaDetail.getNewService().getDesc(), "TV", "").toUpperCase();
			}
			if (FsaServiceType.WG == selectedFsaDetail.getExistingService()) {
				if (StringUtils.isNotBlank(selectedFsaDetail.getUpgradeBandwidth())) {
					return LtsConstant.IMS_PRODUCT_TYPE_PCD; 
				}
			}
			return StringUtils.replace(selectedFsaDetail.getExistingService().getDesc(), "TV", "").toUpperCase();
		case SHARE_PCD:
			return LtsConstant.IMS_PRODUCT_TYPE_PCD;
		case SHARE_TV:
			return StringUtils.equals(modemArrangementForm.getNowTvServiceType(),
					LtsConstant.TV_TYPE_HDTV) ? LtsConstant.IMS_PRODUCT_TYPE_HD
					: LtsConstant.IMS_PRODUCT_TYPE_SD;
		case SHARE_BUNDLE:
			return StringUtils.equals(modemArrangementForm.getNowTvServiceType(),
					LtsConstant.TV_TYPE_HDTV) ? LtsConstant.IMS_PRODUCT_TYPE_PCD_HD
					: LtsConstant.IMS_PRODUCT_TYPE_PCD_SD;
		case STANDALONE:
			return LtsConstant.IMS_PRODUCT_TYPE_WALL_GARDEN;
		default:
			return null;
		}
	}
	
	private boolean isRecontract(OrderCaptureDTO orderCapture) {
		return orderCapture.getLtsMiscellaneousForm().isRecontract() && orderCapture.getLtsRecontractForm() != null;
	}
	
	private AccountServiceAssignLtsDTO[] createAccountServiceAssigns(CreateServiceType createServiceType, OrderCaptureDTO orderCapture,
			ServiceDetailProfileLtsDTO serviceProfile, Map<String, AccountDetailLtsDTO> accountDetailLtsMap,
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		List<AccountServiceAssignLtsDTO> accountServiceAssignList = new ArrayList<AccountServiceAssignLtsDTO>();
		
		boolean recontract = isRecontract(orderCapture);
		boolean isNew2ndDel = CreateServiceType.CREATE_SRV_TYPE_NEW_2DEL == createServiceType 
								&& orderCapture.getSecondDelServiceProfile() == null
								&& (LtsConstant.INVENT_STS_RESERVED.equals(orderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus())
										|| LtsConstant.INVENT_STS_PRE_ASSIGN.equals(orderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus())); 
		
		// Create Profile Account  ** Except Recontract + 2nd DEL + reserved DN 
		if (! (recontract && isNew2ndDel)) {
			AccountServiceAssignLtsDTO profileSrvAccountAssign = new AccountServiceAssignLtsDTO();
			profileSrvAccountAssign.setAccount(createAccountDetailLts(createServiceType, orderCapture, serviceProfile, accountDetailLtsMap, customerDetailLtsMap));
			profileSrvAccountAssign.setAction(recontract ? LtsConstant.IO_IND_OUT : isNew2ndDel ? LtsConstant.IO_IND_INSTALL : LtsConstant.IO_IND_SPACE);
			profileSrvAccountAssign.setChrgType(LtsConstant.ACCOUNT_CHARGE_TYPE_R);
			accountServiceAssignList.add(profileSrvAccountAssign);	
		}

		// For Recontract
		if (recontract) {
			switch (createServiceType) {
			case CREATE_SRV_TYPE_UPGRADE:
			case CREATE_SRV_TYPE_RENEW_DEL:
			case CREATE_SRV_TYPE_RENEW_EYE:
			case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
			case CREATE_SRV_TYPE_NEW_2DEL:
				AccountServiceAssignLtsDTO recontractSrvAccountAssign = new AccountServiceAssignLtsDTO();
				recontractSrvAccountAssign.setAccount(createRecontractAccountDetailLts(createServiceType, orderCapture, serviceProfile, accountDetailLtsMap, customerDetailLtsMap));
				recontractSrvAccountAssign.setAction(LtsConstant.IO_IND_INSTALL);
				recontractSrvAccountAssign.setChrgType(LtsConstant.ACCOUNT_CHARGE_TYPE_R);
				accountServiceAssignList.add(recontractSrvAccountAssign);
			}	
		}
		
		return accountServiceAssignList.toArray(new AccountServiceAssignLtsDTO[accountServiceAssignList.size()]);
	}
	
	protected String getRecontractAccountNum(OrderCaptureDTO orderCapture) {
		LtsRecontractFormDTO recontractForm = orderCapture.getLtsRecontractForm();
		if (recontractForm != null && recontractForm.getAccountDetailProfile() != null) {
			return recontractForm.getAccountDetailProfile().getAcctNum();
		}
		return LtsConstant.DUMMY_ACCT_NO;
	}
	
	protected AccountDetailLtsDTO createRecontractAccountDetailLts(CreateServiceType createServiceType, OrderCaptureDTO orderCapture,
			ServiceDetailProfileLtsDTO serviceProfile, Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		LtsRecontractFormDTO recontractForm = orderCapture.getLtsRecontractForm();
		AccountDetailProfileLtsDTO profileAcct = null;
		String acctNum = getRecontractAccountNum(orderCapture);
		
		if (recontractForm != null && recontractForm.getAccountDetailProfile() != null) {
			profileAcct = recontractForm.getAccountDetailProfile();
		}
		
		if (accountDetailLtsMap.containsKey(acctNum)) {
			return accountDetailLtsMap.get(acctNum);
		}
		
		AccountDetailLtsDTO recontractAcct = new AccountDetailLtsDTO();
		accountDetailLtsMap.put(acctNum, recontractAcct);
		recontractAcct.setAcctNo(acctNum);
		recontractAcct.setCustomer(createRecontractCustomerDetailLts(recontractForm.getCustDetailProfile(), customerDetailLtsMap, orderCapture));
		recontractAcct.setBillingAddress(createBillingAddressLtsDTO(orderCapture, createServiceType));
		
		if (LtsConstant.DUMMY_ACCT_NO.equals(acctNum)) {
			return recontractAcct;
		}
		
		recontractAcct.setAcctName(profileAcct.getAcctName());
		recontractAcct.setAutopayStatementInd(profileAcct.isAutopayStatementInd() ? "Y" : null);
		recontractAcct.setBillFmt(profileAcct.getBillFmt());
		recontractAcct.setBillFreq(profileAcct.getBillFreq());
		recontractAcct.setBillLang(profileAcct.getBillLang());
		recontractAcct.setBillMedia(getBillMedia(createServiceType, orderCapture, profileAcct));
		recontractAcct.setExistBillMedia(profileAcct.getBillMedia());
		recontractAcct.setPaymethods(createRecontractPaymentMethodDetailLts(orderCapture, profileAcct));
		
		if(orderCapture.getLtsPaymentForm() != null){
			recontractAcct.setAcctWaivePaperInd(StringUtils.equals(orderCapture.getLtsPaymentForm().getPaperBillCharge(), "NONE")? null : orderCapture.getLtsPaymentForm().getPaperBillCharge());
			recontractAcct.setWaivePaperReaCd(orderCapture.getLtsPaymentForm().getPaperWaiveReason());
	    	if(LtsConstant.PAPER_BILL_WAIVE_REASON_OTHER.equals(orderCapture.getLtsPaymentForm().getPaperWaiveReason())){
	    		recontractAcct.setWaivePaperRemarks(orderCapture.getLtsPaymentForm().getPaperBillWaiveOtherStaffId());	
	    	}
		}
		
		setRedemptionMediaDetail(orderCapture, recontractAcct);
		return recontractAcct;
	}
	
	
	private AccountDetailLtsDTO createAccountDetailLts(CreateServiceType createServiceType, OrderCaptureDTO orderCapture,
			ServiceDetailProfileLtsDTO serviceProfile, Map<String, AccountDetailLtsDTO> accountDetailLtsMap,
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		if (serviceProfile == null) {
			serviceProfile = orderCapture.getLtsServiceProfile(); 
		}
		
		AccountDetailProfileLtsDTO primaryProfileAccount = LtsSbOrderHelper.getPrimaryAccount(serviceProfile);
		AccountDetailLtsDTO accountDetailLts = null;
		
		if (accountDetailLtsMap.containsKey(primaryProfileAccount.getAcctNum())) {
			accountDetailLts = accountDetailLtsMap.get(primaryProfileAccount.getAcctNum());
			return accountDetailLts;
		}
		
		boolean recontract = isRecontract(orderCapture);
		
		accountDetailLts = new AccountDetailLtsDTO();
		accountDetailLts.setAcctName(primaryProfileAccount.getAcctName());
		accountDetailLts.setAcctNo(primaryProfileAccount.getAcctNum());
		accountDetailLts.setBillFreq(primaryProfileAccount.getBillFreq());
		accountDetailLts.setBillLang(primaryProfileAccount.getBillLang());
		accountDetailLts.setCustomer(createCustomerDetailLts(serviceProfile.getPrimaryCust(), customerDetailLtsMap, orderCapture));
		accountDetailLts.setBillFmt(primaryProfileAccount.getBillFmt());
		accountDetailLts.setExistBillMedia(primaryProfileAccount.getBillMedia());
		accountDetailLts.setAutopayStatementInd(primaryProfileAccount.isAutopayStatementInd() ? "Y" : null);	
		accountDetailLts.setPaymethods(createPaymentMethodDetailLts(orderCapture, primaryProfileAccount));
		
		if (orderCapture.getLtsPaymentForm() != null) {
			accountDetailLts.setAcctWaivePaperInd(StringUtils.equals(orderCapture.getLtsPaymentForm().getPaperBillCharge(), "NONE")? null : orderCapture.getLtsPaymentForm().getPaperBillCharge());
			accountDetailLts.setWaivePaperReaCd(orderCapture.getLtsPaymentForm().getPaperWaiveReason());	
	    	if(LtsConstant.PAPER_BILL_WAIVE_REASON_OTHER.equals(orderCapture.getLtsPaymentForm().getPaperWaiveReason())){
		    	accountDetailLts.setWaivePaperRemarks(orderCapture.getLtsPaymentForm().getPaperBillWaiveOtherStaffId());	
	    	}
		}
    	
		if (!recontract) {
			accountDetailLts.setBillingAddress(createBillingAddressLtsDTO(orderCapture, createServiceType));
			accountDetailLts.setBillMedia(getBillMedia(createServiceType, orderCapture, primaryProfileAccount));
			setRedemptionMediaDetail(orderCapture, accountDetailLts);
		}
		else {
			if (orderCapture.getLtsRecontractForm().isDeceasedCase()
					&& StringUtils.isNotBlank(orderCapture.getLtsRecontractForm().getNewBillingName())) {
				accountDetailLts.setAcctName(orderCapture.getLtsRecontractForm().getNewBillingName());
			}
			accountDetailLts.setBillingAddress(createRecontractTransferorBillingAddressLtsDTO(orderCapture));	
		}
		
		accountDetailLtsMap.put(accountDetailLts.getAcctNo(), accountDetailLts);
		return accountDetailLts;
	}
	
	protected void setRedemptionMediaDetail(OrderCaptureDTO orderCapture, AccountDetailLtsDTO accountDetailLts) {
		if (orderCapture.getLtsPaymentForm() != null) {
			if (orderCapture.getLtsPaymentForm().isRequireRedemPremium()) {
				accountDetailLts.setRedemptionMedia(orderCapture.getLtsPaymentForm().getRedemptionMedia());
				if (LtsConstant.REDEMPTION_MEDIA_SMS.equals(orderCapture.getLtsPaymentForm().getRedemptionMedia())) {
					accountDetailLts.setRedemptionSmsNo(orderCapture.getLtsPaymentForm().getRedemMediaSmsNum());	
				}
				if (LtsConstant.REDEMPTION_MEDIA_EMAIL.equals(orderCapture.getLtsPaymentForm().getRedemptionMedia())) {
					accountDetailLts.setRedemptionEmailAddr(orderCapture.getLtsPaymentForm().getRedemMediaEmailAddr());	
				}
			}
			
			if (ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_EMAIL_BILL)
					&& StringUtils.isNotBlank(orderCapture.getLtsPaymentForm().getEmailBillAddress())) {
				accountDetailLts.setEmailAddr(orderCapture.getLtsPaymentForm().getEmailBillAddress());
			}
		}
	}
	
	private void setCsPortalDetail(OrderCaptureDTO orderCapture, CustomerDetailLtsDTO customerDetailLts) {
		if(orderCapture.getLtsPaymentForm() != null) {
			
			if(orderCapture.getLtsPaymentForm().getCsPortalItem() != null){ 
		
				String itemType = orderCapture.getLtsPaymentForm().getCsPortalItem().getItemType();
						
				if((StringUtils.equals(LtsConstant.ITEM_TYPE_MYHKT_BILL,itemType )
						||StringUtils.equals(LtsConstant.ITEM_TYPE_THE_CLUB_BILL,itemType )
						||StringUtils.equals(LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL,itemType )) 
					&& orderCapture.getLtsPaymentForm().getCsPortalItem().isSelected())
				{
					setHktClubItemAttb(customerDetailLts, orderCapture.getLtsPaymentForm());
				}
			}else if(orderCapture.getLtsPaymentForm().getViewBillItem()!= null
						&& orderCapture.getLtsPaymentForm().getCsPortalItem() == null
						&& StringUtils.equals(LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL, orderCapture.getLtsPaymentForm().getViewBillItem().getItemType())
						&& orderCapture.getLtsPaymentForm().getViewBillItem().isSelected()){ // for viewBillItem only
				
					setHktClubItemAttb(customerDetailLts, orderCapture.getLtsPaymentForm());
			}
			setHktClubInd(orderCapture.getLtsPaymentForm(), customerDetailLts);
		}	
	}
	

	
	protected void setHktClubItemAttb(CustomerDetailLtsDTO customerDetailLts, LtsPaymentFormDTO pForm){
		
		if(pForm == null)
		{return;}
  
			if(!StringUtils.isEmpty(pForm.getCspEmail())){
				
				customerDetailLts.setCsPortalLogin(pForm.getCspEmail());
			}
		
			if(!StringUtils.isEmpty(pForm.getClubEmail())){
				
				customerDetailLts.setTheClubLogin(pForm.getClubEmail());
			}
		
			if(!StringUtils.isEmpty(pForm.getCspMobile())){
				
				customerDetailLts.setCsPortalMobile(pForm.getCspMobile());
			}
		
			if(!StringUtils.isEmpty(pForm.getClubMobile())){
				
				customerDetailLts.setTheClubMobile(pForm.getClubMobile());
			}
			if(!StringUtils.isEmpty(pForm.getOptOutReason())){
				customerDetailLts.setClubOptRea(pForm.getOptOutReason());
			}
			
			if(!StringUtils.isEmpty(pForm.getOptOutRemark())){
				customerDetailLts.setClubOptRmk(pForm.getOptOutRemark());
			}
			
			if(!pForm.isCsPortalExist()&& pForm.isClubMembExist()){
				customerDetailLts.setHktOptOut(pForm.getOptOut());
			}else if(!pForm.isClubMembExist() && pForm.isCsPortalExist()){
				customerDetailLts.setClubOptOut(pForm.getOptOut());
			}else if(!pForm.isCsPortalExist()&& !pForm.isClubMembExist()){
				customerDetailLts.setHktOptOut(pForm.getOptOut());
				customerDetailLts.setClubOptOut(pForm.getOptOut());
			}
			
	}
	
	protected void setHktClubInd(LtsPaymentFormDTO pForm, CustomerDetailLtsDTO customerDetailLts){			
		
		if(pForm == null){
			return;
		}
		
		//initialize the indicator
		customerDetailLts.setTheClubInd(LtsConstant.CSP_NOT_REG);
		customerDetailLts.setCsPortalInd(LtsConstant.CSP_NOT_REG);
		
		if(pForm.getViewBillItem()!= null 
				&& pForm.getCsPortalItem() == null
				&& StringUtils.equals(pForm.getViewBillItem().getItemType(), LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL)){
			//club & hkt			
				customerDetailLts.setTheClubInd(pForm.isCsldFailInd()? LtsConstant.CSP_FAIL_REG : LtsConstant.CSP_ALDY_REG);
				customerDetailLts.setCsPortalInd(pForm.isCsldFailInd()? LtsConstant.CSP_FAIL_REG : LtsConstant.CSP_ALDY_REG);
		}else if(pForm.getCsPortalItem() != null){ 
			
			if(StringUtils.equals(pForm.getCsPortalItem().getItemType(), LtsConstant.ITEM_TYPE_THE_CLUB_BILL)){
			
				//!club & hkt		
				customerDetailLts.setCsPortalInd(pForm.isCsldFailInd()? LtsConstant.CSP_FAIL_REG : LtsConstant.CSP_ALDY_REG);
			
				if(pForm.getCsPortalItem().isSelected()){
					customerDetailLts.setTheClubInd(pForm.isCsldFailInd()? LtsConstant.CSP_FAIL_REG : LtsConstant.CSP_REG);
				}else{
					customerDetailLts.setTheClubInd(LtsConstant.CSP_NOT_REG);
				}
			
			}else if(StringUtils.equals(pForm.getCsPortalItem().getItemType(), LtsConstant.ITEM_TYPE_MYHKT_BILL)){
				
				//club & !hkt
				customerDetailLts.setTheClubInd(pForm.isCsldFailInd()? LtsConstant.CSP_FAIL_REG : LtsConstant.CSP_ALDY_REG);
				if(pForm.getCsPortalItem().isSelected()){
					customerDetailLts.setCsPortalInd(pForm.isCsldFailInd()? LtsConstant.CSP_FAIL_REG : LtsConstant.CSP_REG);
				}else{
					customerDetailLts.setCsPortalInd(LtsConstant.CSP_NOT_REG);
				}
			}else if(StringUtils.equals(pForm.getCsPortalItem().getItemType(), LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL)){
			
				//!club & !hkt
			
				if(pForm.getCsPortalItem().isSelected()){
					customerDetailLts.setTheClubInd(pForm.isCsldFailInd()? LtsConstant.CSP_FAIL_REG : LtsConstant.CSP_REG);
					customerDetailLts.setCsPortalInd(pForm.isCsldFailInd()? LtsConstant.CSP_FAIL_REG : LtsConstant.CSP_REG);
				}else{
					customerDetailLts.setTheClubInd(LtsConstant.CSP_NOT_REG);
					customerDetailLts.setCsPortalInd(LtsConstant.CSP_NOT_REG);
				}
			}
		}
	}
	
	private CustomerDetailLtsDTO createRecontractCustomerDetailLts(CustomerDetailProfileLtsDTO customerDetailProfileLts, 
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap,
			OrderCaptureDTO orderCapture) {
		
		String custNum = LtsConstant.DUMMY_CUST_NO;
		
		if (customerDetailProfileLts != null) {
			custNum = customerDetailProfileLts.getCustNum();
		}
		
		if (customerDetailLtsMap.containsKey(custNum)) {
			return customerDetailLtsMap.get(custNum);
		}
		
		LtsRecontractFormDTO recontractForm = orderCapture.getLtsRecontractForm();
		
		CustomerDetailLtsDTO customerDetailLts = new CustomerDetailLtsDTO();
		customerDetailLts.setCustNo(custNum);
		
		if (customerDetailLts != null) {
			customerDetailLts.setBlacklistInd(customerDetailProfileLts.isBlacklistCustInd() ? "Y" : null);
		}
		
		customerDetailLts.setCompanyName(recontractForm.getCompanyName());
		customerDetailLts.setFirstName(recontractForm.getFirstName());
		customerDetailLts.setLastName(recontractForm.getLastName());
		customerDetailLts.setIdDocNum(recontractForm.getDocId());
		customerDetailLts.setIdDocType(recontractForm.getDocType());
		customerDetailLts.setIdVerifiedInd(orderCapture.getLtsCustomerIdentificationForm().isVerified()? "Y" : "N");
		customerDetailLts.setLob(LtsConstant.LOB_LTS);
		customerDetailLts.setTitle(recontractForm.getTitle());
		customerDetailLts.setDob(orderCapture.getLtsCustomerIdentificationForm().getDateOfBirth());
		setCsPortalDetail(orderCapture, customerDetailLts);
		customerDetailLts.setCustOptOutInfo(createCustOptOutInfo(orderCapture));
		customerDetailLtsMap.put(customerDetailLts.getCustNo(), customerDetailLts);
		return customerDetailLts;
	}
	
	private CustomerDetailLtsDTO createCustomerDetailLts(CustomerDetailProfileLtsDTO customerDetailProfileLts, 
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap,
			OrderCaptureDTO orderCapture) {
		
		if (customerDetailLtsMap.containsKey(customerDetailProfileLts.getCustNum())) {
			return customerDetailLtsMap.get(customerDetailProfileLts.getCustNum());
		}
		
		CustomerDetailLtsDTO customerDetailLts = new CustomerDetailLtsDTO();
		customerDetailLts.setBlacklistInd(customerDetailProfileLts.isBlacklistCustInd() ? "Y" : null);
		customerDetailLts.setCompanyName(customerDetailProfileLts.getCompanyName());
//		customerDetailLts.setContactMobileNum(contactMobileNum);
		customerDetailLts.setCustNo(customerDetailProfileLts.getCustNum());
		
		//update dob to bom when sign off order.
		LtsCustomerIdentificationFormDTO customerIdentification = orderCapture.getLtsCustomerIdentificationForm();
		if(customerIdentification != null
				&& customerIdentification.getDobBom() == null 
				&& customerIdentification.getDateOfBirth() != null)
				{ if (!LtsSbOrderHelper.isRecontractOrder(orderCapture)) {
					customerDetailLts.setDob(customerIdentification.getDateOfBirth());
				}
		}
		
//		customerDetailLts.setDob(dob);
		customerDetailLts.setFirstName(customerDetailProfileLts.getFirstName());
		customerDetailLts.setIdDocNum(customerDetailProfileLts.getDocNum());
		customerDetailLts.setIdDocType(customerDetailProfileLts.getDocType());
		customerDetailLts.setIdVerifiedInd(orderCapture.getLtsCustomerIdentificationForm().isVerified()? "Y" : "N");
//		customerDetailLts.setLangWritten(langWritten);
		customerDetailLts.setLastName(customerDetailProfileLts.getLastName());
		customerDetailLts.setLob(LtsConstant.LOB_LTS);
		customerDetailLts.setTitle(customerDetailProfileLts.getTitle());

		if (!isRecontract(orderCapture)) {
			setCsPortalDetail(orderCapture, customerDetailLts);
			customerDetailLts.setCustOptOutInfo(createCustOptOutInfo(orderCapture));
		}
		customerDetailLtsMap.put(customerDetailLts.getCustNo(), customerDetailLts);
		return customerDetailLts;
	}
	
	protected PaymentMethodDetailLtsDTO[] createRecontractPaymentMethodDetailLts(OrderCaptureDTO orderCapture, 
			AccountDetailProfileLtsDTO accountDetailProfileLts) {
		
		LtsPaymentFormDTO ltsPaymentForm = orderCapture.getLtsPaymentForm();
		
		if (ltsPaymentForm == null || accountDetailProfileLts == null) {
			return null;
		}
		
		List<PaymentMethodDetailLtsDTO> paymentMethodDetailLtsList = new ArrayList<PaymentMethodDetailLtsDTO>();
		PaymentMethodDetailLtsDTO existingPaymentDetail = setupRecontractExistingPaymentDetail(orderCapture.getLtsPaymentForm());
		
		if(StringUtils.equals(ltsPaymentForm.getExistingPayMethodType(), ltsPaymentForm.getNewPayMethodType())){
			existingPaymentDetail.setSalesMemoNum(ltsPaymentForm.getSalesMemoNum());
			existingPaymentDetail.setAction(LtsConstant.IO_IND_SPACE);
			return new PaymentMethodDetailLtsDTO[] {existingPaymentDetail}; 
		}
		
		existingPaymentDetail.setAction(LtsConstant.IO_IND_OUT);
		paymentMethodDetailLtsList.add(existingPaymentDetail);
		paymentMethodDetailLtsList.add(setupNewPaymentDetail(ltsPaymentForm));	
		
		return paymentMethodDetailLtsList.toArray(new PaymentMethodDetailLtsDTO[paymentMethodDetailLtsList.size()]);
	}
	
	protected PaymentMethodDetailLtsDTO[] createPaymentMethodDetailLts(OrderCaptureDTO orderCapture, 
			AccountDetailProfileLtsDTO accountDetailProfileLts) {
		
		List<PaymentMethodDetailLtsDTO> paymentMethodDetailLtsList = new ArrayList<PaymentMethodDetailLtsDTO>();
		AccountDetailProfileLtsDTO upgradeProfileAccount = LtsSbOrderHelper.getPrimaryAccount(orderCapture.getLtsServiceProfile());
		
		if (accountDetailProfileLts == null) {
			accountDetailProfileLts = upgradeProfileAccount;
		}
		
		PaymentMethodDetailLtsDTO existingPaymentDetail = setupExistingPaymentDetail(accountDetailProfileLts);
		boolean recontract = isRecontract(orderCapture);
		
		LtsPaymentFormDTO ltsPaymentForm = orderCapture.getLtsPaymentForm();
		
		if (recontract 
				|| ltsPaymentForm == null 
				|| !StringUtils.equals(upgradeProfileAccount.getAcctNum(), accountDetailProfileLts.getAcctNum())) {
			existingPaymentDetail.setAction(LtsConstant.IO_IND_SPACE);
			return new PaymentMethodDetailLtsDTO[] {existingPaymentDetail}; 
		}
		if(StringUtils.equals(ltsPaymentForm.getExistingPayMethodType(), ltsPaymentForm.getNewPayMethodType())){
			existingPaymentDetail.setSalesMemoNum(ltsPaymentForm.getSalesMemoNum());
			existingPaymentDetail.setAction(LtsConstant.IO_IND_SPACE);
			return new PaymentMethodDetailLtsDTO[] {existingPaymentDetail}; 
		}
		
		existingPaymentDetail.setAction(LtsConstant.IO_IND_OUT);
		paymentMethodDetailLtsList.add(existingPaymentDetail);
		
		
		paymentMethodDetailLtsList.add(setupNewPaymentDetail(ltsPaymentForm));	
		
		return paymentMethodDetailLtsList.toArray(new PaymentMethodDetailLtsDTO[paymentMethodDetailLtsList.size()]);
	}
	
	private PaymentMethodDetailLtsDTO setupExistingPaymentDetail(AccountDetailProfileLtsDTO accountDetailProfileLts){

		PaymentMethodDetailLtsDTO existingPaymentDetail = new PaymentMethodDetailLtsDTO();
		existingPaymentDetail.setPayMtdType(accountDetailProfileLts.getPayMethod());
		
		if (StringUtils.equals(accountDetailProfileLts.getPayMethod(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {
			existingPaymentDetail.setBankAcctNo(accountDetailProfileLts.getBankAcctNum());
			existingPaymentDetail.setBankCd(accountDetailProfileLts.getBankCd());
			existingPaymentDetail.setBranchCd(accountDetailProfileLts.getBranchCd());
		}
		else if (StringUtils.equals(accountDetailProfileLts.getPayMethod(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
			existingPaymentDetail.setCcNum(accountDetailProfileLts.getCreditCardNum());
		}
		
		return existingPaymentDetail;
	}
	
	private PaymentMethodDetailLtsDTO setupRecontractExistingPaymentDetail(LtsPaymentFormDTO ltsPaymentForm){

		PaymentMethodDetailLtsDTO existingPaymentDetail = new PaymentMethodDetailLtsDTO();
		existingPaymentDetail.setPayMtdType(ltsPaymentForm.getExistingPayMethodType());
		setupPaymentMethodDetailLtsByForm(ltsPaymentForm, existingPaymentDetail);
		
		return existingPaymentDetail;
	}
	
	private PaymentMethodDetailLtsDTO setupNewPaymentDetail(LtsPaymentFormDTO ltsPaymentForm){
		PaymentMethodDetailLtsDTO newPaymentDetail = new PaymentMethodDetailLtsDTO();
		newPaymentDetail.setAction(LtsConstant.IO_IND_INSTALL);
		newPaymentDetail.setPayMtdKey("-1");
		newPaymentDetail.setPayMtdType(ltsPaymentForm.getNewPayMethodType());
		
		setupPaymentMethodDetailLtsByForm(ltsPaymentForm, newPaymentDetail);
		return newPaymentDetail;
	}
	
	private void setupPaymentMethodDetailLtsByForm(LtsPaymentFormDTO ltsPaymentForm, PaymentMethodDetailLtsDTO paymentDetail){

		if(StringUtils.equals(LtsConstant.PAYMENT_TYPE_AUTO_PAY, paymentDetail.getPayMtdType())){
			paymentDetail.setAutopayAppDate(ltsPaymentForm.getApplicationDate());
			paymentDetail.setAutopayUpLimAmt(ltsPaymentForm.getAutoPayUpperLimit());
			paymentDetail.setBankAcctHoldName(ltsPaymentForm.getBankAccHolderName());
			paymentDetail.setBankAcctHoldNum(ltsPaymentForm.getBankAccHolderDocNum());
			paymentDetail.setBankAcctHoldType(ltsPaymentForm.getBankAccHolderDocType());
			paymentDetail.setBankAcctNo(ltsPaymentForm.getBankAccNum());
			paymentDetail.setBankCd(ltsPaymentForm.getBankCode());
			paymentDetail.setBranchCd(ltsPaymentForm.getBranchCode());
			paymentDetail.setThirdPartyInd(ltsPaymentForm.getThirdPartyBankAccount() != null ? (ltsPaymentForm.getThirdPartyBankAccount()?"Y":null ) : null);
			paymentDetail.setAutopayStatementInd("Y");
		}
		else if(StringUtils.equals(LtsConstant.PAYMENT_TYPE_CREDIT_CARD, paymentDetail.getPayMtdType())){
			String expDate = StringUtils.leftPad(String.valueOf(ltsPaymentForm.getExpireMonth()), 2, '0')  + "/" + ltsPaymentForm.getExpireYear();
			paymentDetail.setCcExpDate(expDate);
			paymentDetail.setCcHoldName(ltsPaymentForm.getCardHolderName());
			paymentDetail.setCcIdDocNo(ltsPaymentForm.getCardHolderDocNum());
			paymentDetail.setCcIdDocType(ltsPaymentForm.getCardHolderDocType());
			paymentDetail.setCcVerifiedInd(ltsPaymentForm.isCardVerified()?"Y":null);
			paymentDetail.setCcNum(ltsPaymentForm.getCardNum());
			paymentDetail.setCcType(ltsPaymentForm.getCardType());
			if(ltsPaymentForm.getThirdPartyCard() != null && ltsPaymentForm.getThirdPartyCard()){
				paymentDetail.setThirdPartyInd("Y");
				if(StringUtils.isEmpty(paymentDetail.getCcIdDocNo())){
					paymentDetail.setCcIdDocNo("NOT_PROVIDED");
					paymentDetail.setCcIdDocType(LtsConstant.DOC_TYPE_PASSPORT);
				}
			}else{
				paymentDetail.setThirdPartyInd(null);
			}
			
			paymentDetail.setCcIssueBank("999");
		}		
		paymentDetail.setSalesMemoNum(ltsPaymentForm.getSalesMemoNum());
	}
	
	protected String getBillMedia(CreateServiceType createServiceType, OrderCaptureDTO orderCapture, AccountDetailProfileLtsDTO profileAccount) {
		
		if (orderCapture.getLtsPaymentForm() == null) {
			return null;
		}

		if (! (CreateServiceType.CREATE_SRV_TYPE_UPGRADE == createServiceType 
				|| CreateServiceType.CREATE_SRV_TYPE_RENEW_EYE == createServiceType
				|| CreateServiceType.CREATE_SRV_TYPE_RENEW_DEL == createServiceType)) {
			return profileAccount.getBillMedia();
		}

		if (ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL)
				|| ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE)
				|| ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE)
				|| ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL_BR)) {
			return LtsConstant.LTS_BILL_MEDIA_PAPER_BILL;
		}
		else if (ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE)) {
			return LtsConstant.LTS_BILL_MEDIA_DEVICE_BILL;
		}
		else if (ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_EBILL)) {
			return LtsConstant.LTS_BILL_MEDIA_PPS_BILL;
		}
		else if (ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_EMAIL_BILL)) {
			return LtsConstant.LTS_BILL_MEDIA_PPS_BILL;
		}	
		
		return null;
	}
	
	protected String getBillMedia(OrderCaptureDTO orderCapture) {
		
		if (orderCapture.getLtsPaymentForm() == null) {
			return null;
		}
		if (ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL)
				|| ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE)
				|| ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE)
				|| ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL_BR)) {
			return LtsConstant.LTS_BILL_MEDIA_PAPER_BILL;
		}
		else if (ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE)) {
			return LtsConstant.LTS_BILL_MEDIA_DEVICE_BILL;
		}
		else if (ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_EBILL)) {
			return LtsConstant.LTS_BILL_MEDIA_PPS_BILL;
		}
		else if (ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_EMAIL_BILL)) {
			return LtsConstant.LTS_BILL_MEDIA_PPS_BILL;
		}	
		
		return null;
	}
	
	private String getExistNowTvArpu(OrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsNowTvServiceForm() == null
				|| AdditionalTvType.MIRROR != orderCapture.getLtsNowTvServiceForm().getAdditionalTvType()) {
			return null;
		}

		for (ItemDetailDTO selectedMirrorItem : orderCapture
				.getLtsNowTvServiceForm().getNowTvMirrorItemList()) {
			if (selectedMirrorItem.isSelected()) {
				return ltsOfferService.getUpperApru(selectedMirrorItem.getItemId());
			}
		}
		return null;
	}
	
	

	
	private String getLoginId(String orderType, FsaDetailDTO selectedFsaDetail,
			String upgradeServiceNum, ModemType selectedModemType) {
		
		if (StringUtils.equals(orderType, LtsConstant.ORDER_TYPE_INSTALL)) {
			return generateWgLoginId(upgradeServiceNum);
		}
		else if (selectedFsaDetail != null) {
			return selectedFsaDetail.getLoginId();
		}
		return null;
	}
	
	private String generateWgLoginId(String upgradeServiceNum ) {
		return "wg" + StringUtils.right(upgradeServiceNum, 8);
	}
	
	
	private String getNowBundleTv(OrderCaptureDTO orderCapture) {
		
		if (orderCapture.getLtsNowTvServiceForm() == null) {
			return null;
		}
		
		if (orderCapture.getLtsNowTvServiceForm().getNowTvSpecItemList() != null &&
				!orderCapture.getLtsNowTvServiceForm().getNowTvSpecItemList().isEmpty()) {
			for (ItemDetailDTO itemDetail : orderCapture.getLtsNowTvServiceForm().getNowTvSpecItemList()) {
				if (itemDetail.isSelected()) {
					if (orderCapture.getLtsNowTvServiceForm().isSelectedBasketBundleTv()) {
						return String.valueOf(Integer.parseInt(itemDetail.getCredit()) * 100);	
					}
					return itemDetail.getCredit();
				}
			}
		}
		
		if (orderCapture.getLtsNowTvServiceForm().getNowTvPayItemList() != null && 
				!orderCapture.getLtsNowTvServiceForm().getNowTvPayItemList().isEmpty()) {
			for (ItemDetailDTO itemDetail : orderCapture.getLtsNowTvServiceForm().getNowTvPayItemList()) {
				if (itemDetail.isSelected()) {
					if (orderCapture.getLtsNowTvServiceForm().isSelectedBasketBundleTv()) {
						return String.valueOf(Integer.parseInt(itemDetail.getCredit()) * 100);	
					}
					return itemDetail.getCredit();
				}
			}
		}
		
		return null;
	}
	
	private String getSuggestedSrdReason(OrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsAppointmentForm() == null
				|| orderCapture.getLtsAppointmentForm().getEarliestSRD() == null) {
			return null;
		}
		return orderCapture.getLtsAppointmentForm().getEarliestSRD().getReasonCd();
	}
	
	private String getSuggestedSrd(OrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsAppointmentForm() == null
				|| orderCapture.getLtsAppointmentForm().getEarliestSRD() == null) {
			return null;
		}
		
		return orderCapture.getLtsAppointmentForm().getEarliestSRD().getDateString();
	}
	
	private String getNewSrvTypeCode(FsaDetailDTO fsaDetail) {
		if (fsaDetail.getNewService() == null) {
			return fsaDetail.getExistingService().getDesc();
		}
		return fsaDetail.getNewService().getDesc();
	}
	
	private String getNewBandwidth(FsaDetailDTO fsaDetail) {
		if (StringUtils.isEmpty(fsaDetail.getUpgradeBandwidth()) ) {
			return fsaDetail.getBandwidth();
		}
		if (StringUtils.equals(LtsConstant.TECHNOLOGY_PON, fsaDetail.getUpgradeBandwidth())) {
			return "1000";	
		}
		return fsaDetail.getUpgradeBandwidth();
	}
	
	private String getImsOrderType(OrderCaptureDTO orderCapture) {
		
		if (orderCapture.getModemTechnologyAissgn() != null 
				&& !StringUtils.isEmpty(orderCapture.getModemTechnologyAissgn().getImsOrderType()) ) {
			return orderCapture.getModemTechnologyAissgn().getImsOrderType();
		}
		
		switch (orderCapture.getLtsModemArrangementForm().getModemType()) {
		case STANDALONE:
		case SHARE_PCD:
		case SHARE_TV:
		case SHARE_BUNDLE:
			return LtsConstant.ORDER_TYPE_INSTALL;
			
		case SHARE_EX_FSA:
		case SHARE_OTHER_FSA:
			return LtsConstant.ORDER_TYPE_CHANGE;
		default:
			break;
		}
		return null;
	}
	
	private String getLtsOrderType(CreateServiceType createServiceType, OrderCaptureDTO orderCapture) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_RENEW_DEL:
		case CREATE_SRV_TYPE_RENEW_EYE:
		case CREATE_SRV_TYPE_UPGRADE:
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_DUPLEX_NUM_CHANGE:
		case CREATE_SRV_TYPE_DUPLEX_NUM_NO_CHANGE:
			return LtsConstant.ORDER_TYPE_CHANGE;
			
		case CREATE_SRV_TYPE_DUPLEX_REMOVE:
		case CREATE_SRV_TYPE_SIP_REMOVE:
			return LtsConstant.ORDER_TYPE_DISCONNECT;
		
		case CREATE_SRV_TYPE_NEW_2DEL:
			if (StringUtils.equals(orderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus(), LtsConstant.INVENT_STS_WORKING)) {
				return LtsConstant.ORDER_TYPE_CHANGE;
			}
			if (StringUtils.equals(orderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus(), LtsConstant.INVENT_STS_RESERVED)
					|| StringUtils.equals(orderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus(), LtsConstant.INVENT_STS_PRE_ASSIGN)) {
				return LtsConstant.ORDER_TYPE_INSTALL;
			}
		default:
			return null;
		}
	}
	
	private String getRelatedSbOrderId(OrderCaptureDTO orderCapture) {
		if (ModemType.SHARE_PCD == orderCapture.getLtsModemArrangementForm().getModemType()
				|| ModemType.SHARE_BUNDLE == orderCapture.getLtsModemArrangementForm().getModemType()) {
			if (orderCapture.getLtsModemArrangementForm().isPcdSbOrderExist() && orderCapture.getRelatedPcdOrder() != null) {
				return orderCapture.getRelatedPcdOrder().getOrderId(); 
			}else if(orderCapture.getLtsAppointmentForm().getPCDOrderId() != null){
				return orderCapture.getLtsAppointmentForm().getPCDOrderId();
			}
		}
		return null;
	}
	
	private String getEdfRef(OrderCaptureDTO orderCapture) {
		return orderCapture.getLtsModemArrangementForm().getEdfRefNum(); 
	}
	
	private boolean isMirrorNowTv(OrderCaptureDTO orderCapture) {
		
		if (!LtsConstant.DEVICE_TYPE_1020.equals(orderCapture.getLtsDeviceSelectionForm().getSelectedDeviceType())) {
			return false;
		}
		
		FsaDetailDTO selectedFsa = getSelectedFsaDetail(orderCapture);
		boolean existMirror = false;
		if (selectedFsa != null) {
			existMirror = StringUtils.equals("Y", selectedFsa.getFsaProfile().getExistMirrorInd());
		}
		LtsNowTvServiceFormDTO ltsNowTvServiceForm = orderCapture.getLtsNowTvServiceForm();
		
		if (ltsNowTvServiceForm == null || 
				AdditionalTvType.MIRROR != ltsNowTvServiceForm.getAdditionalTvType() ) {
			return existMirror;
		}
		
		if (ltsNowTvServiceForm.getNowTvMirrorItemList() != null && !ltsNowTvServiceForm.getNowTvMirrorItemList().isEmpty()) {
			for (ItemDetailDTO itemDetail : ltsNowTvServiceForm.getNowTvMirrorItemList()) {
				if (itemDetail.isSelected()) {
					return true;
				}
			}
		}
		
		return existMirror;
	}
	
	
	private NowtvDetailLtsDTO createNowTvDetailLts(OrderCaptureDTO orderCapture) {
		
		LtsNowTvServiceFormDTO ltsNowTvServiceForm = orderCapture.getLtsNowTvServiceForm();
		
		if (ltsNowTvServiceForm == null) {
			return null;
		}
		
		NowtvDetailLtsDTO nowtvDetailLts = new NowtvDetailLtsDTO();
		
		if (ltsNowTvServiceForm.getAdditionalTvType() != AdditionalTvType.NO_ADDITIONAL_TV) {
			nowtvDetailLts.setDob(ltsNowTvServiceForm.getDateOfBirth());
			nowtvDetailLts.setViewAvInd(ltsNowTvServiceForm.isConfirmAvAdultChannel() ? "Y" : null);
		}
		
		if(ltsNowTvServiceForm.getNowTvEmailItemList() != null && !ltsNowTvServiceForm.getNowTvEmailItemList().isEmpty()) {
			for (ItemDetailDTO itemDetail : ltsNowTvServiceForm.getNowTvEmailItemList()) {
				if (itemDetail.isSelected()) {
					nowtvDetailLts.setBillMedia(LtsConstant.NOWTV_BILL_MEDIA_EMAIL_BILL);
					nowtvDetailLts.setEmailAddress(ltsNowTvServiceForm.getNowTvEmail());
					break;
				}
			}
		}

		if (ltsNowTvServiceForm.getNowTvPrintItemList() != null && !ltsNowTvServiceForm.getNowTvPrintItemList().isEmpty()) {
			for (ItemDetailDTO itemDetail : ltsNowTvServiceForm.getNowTvPrintItemList()) {
				if (itemDetail.isSelected()) {
					nowtvDetailLts.setBillMedia(LtsConstant.NOWTV_BILL_MEDIA_PAPER_BILL);
					break;
				}
			}
		}
		
		return nowtvDetailLts;
	}
	
	
	private boolean isFsaPerformEr(OrderCaptureDTO orderCapture, FsaDetailDTO selectedFsaDetail) {
		if (selectedFsaDetail.isDifferentIa()) {
			if (ModemType.SHARE_EX_FSA == orderCapture.getLtsModemArrangementForm().getModemType()) {
				return orderCapture.getLtsModemArrangementForm().isExistingFsaER();
			}
			if (ModemType.SHARE_OTHER_FSA == orderCapture.getLtsModemArrangementForm().getModemType()) {
				return orderCapture.getLtsModemArrangementForm().isOtherFsaER();
			}
		}
		return false;
	}
	
	protected AppointmentDetailLtsDTO createAppointmentDetailLts(CreateServiceType createServiceType, OrderCaptureDTO orderCapture) {

		LtsAppointmentFormDTO appointment = orderCapture.getLtsAppointmentForm();
		
		if (appointment == null) {
			return null;
		}
		
		AppointmentDetailLtsDTO appointmentDetailLts = new AppointmentDetailLtsDTO();
		appointmentDetailLts.setInstContactMobile(appointment.getInstallationMobileSMSAlert());
		appointmentDetailLts.setInstContactName(appointment.getInstallationContactName());
		appointmentDetailLts.setInstContactNum(appointment.getInstallationContactNum());
		appointmentDetailLts.setInstSmsNum(appointment.getInstallationMobileSMSAlert());
		
		LtsCustomerIdentificationFormDTO custIdentForm = orderCapture.getLtsCustomerIdentificationForm();
		if (custIdentForm != null) {
			appointmentDetailLts.setCustContactMobile(custIdentForm.getCustomerContactMobileNum());
			appointmentDetailLts.setCustContactFix(custIdentForm.getCustomerContactFixLineNum());
		}
		
		
		if(!LtsConstant.TRUE_IND.equals(appointment.getConfirmedInd())){
			return appointmentDetailLts;
		}
		
		String appntTimeSlot = null;
		String appntDate = null;
		String[] appntDateString = null;
		String cutOverTimeSlot = null;
		String cutOverDate = null;
		String[] cutOverDateString = null;
		
		switch (createServiceType) {
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_NEW_2DEL:
			
			if (StringUtils.isNotBlank(appointment.getSecDelInstallationDate()) 
					&& StringUtils.isNotBlank(appointment.getSecDelInstallationTime())) {
				appntTimeSlot = LtsDateFormatHelper.convertPonTime(appointment.getSecDelInstallationTime());
				appntTimeSlot = LtsDateFormatHelper.revertToBomTime(appntTimeSlot);
				appointmentDetailLts.setAppntType(appointment.getSecDelInstallationType());	
				appntDate = appointment.getSecDelInstallationDate();
			}

			if (StringUtils.isNotBlank(appntTimeSlot) && StringUtils.isNotBlank(appntDate)) {
				appntDateString = ltsAppointmentService.reformatDateTimeSlot(appntDate, appntTimeSlot);
				appointmentDetailLts.setAppntStartTime(appntDateString[0]);
				appointmentDetailLts.setAppntEndTime(appntDateString[1]);	
			}
			
			cutOverTimeSlot = appointment.getSecDelCutOverTime();
			cutOverDate = appointment.getSecDelCutOverDate();
			break;
			
		default:
			if (StringUtils.isNotBlank(appointment.getInstallationDate()) 
					&& StringUtils.isNotBlank(appointment.getInstallationTime())) {
				appntTimeSlot = LtsDateFormatHelper.convertPonTime(appointment.getInstallationTime());
				appntTimeSlot = LtsDateFormatHelper.revertToBomTime(appntTimeSlot);
				appointmentDetailLts.setAppntType(appointment.getInstallationType());	
				appntDate = appointment.getInstallationDate();
			}
			
			if (StringUtils.isNotBlank(appntTimeSlot) && StringUtils.isNotBlank(appntDate)) {
				appntDateString = ltsAppointmentService.reformatDateTimeSlot(appntDate, appntTimeSlot);
				appointmentDetailLts.setAppntStartTime(appntDateString[0]);
				appointmentDetailLts.setAppntEndTime(appntDateString[1]);	
			}
			
			/*BOM2019014 Self pickup new device set to TID for ims service*/
			if(createServiceType == CreateServiceType.CREATE_SRV_TYPE_FSA){
				if(!ltsOfferService.isSelectedNewDeviceFieldService(orderCapture)){
					appointmentDetailLts.setTidInd("Y");
					appntDateString = ltsAppointmentService.reformatDateTimeSlot(appointment.getInstallationDate(), LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
					appointmentDetailLts.setTidStartTime(appntDateString[0]);
					appointmentDetailLts.setTidEndTime(appntDateString[1]);
					appointmentDetailLts.setAppntType(LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
				}
			}
			
			cutOverTimeSlot = appointment.getCutOverTime();
			cutOverDate = appointment.getCutOverDate();
			
			appointmentDetailLts.setSerialNum(appointment.getPreBookSerialNum());
			if(StringUtils.equals(appointment.getBbShortageInd(), LtsConstant.TRUE_IND)){
				appointmentDetailLts.setTidInd("Y");
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				int leadtime = appointment.getEarliestSRD().getLeadTime() + appointment.getEarliestSRD().getSkippedTime();
				LtsSRDDTO newEarliestSrd = new LtsSRDDTO(orderCapture.getLtsMiscellaneousForm().getApplicationDate());
				newEarliestSrd.setLeadTime(7);
				appntDate = df.format(leadtime > 7 ? appointment.getEarliestSRD().getDate().getTime():newEarliestSrd.getDate().getTime());
				appntDateString = ltsAppointmentService.reformatDateTimeSlot(appntDate, LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
				appointmentDetailLts.setTidStartTime(appntDateString[0]);
				appointmentDetailLts.setTidEndTime(appntDateString[1]);
			}else if("02".equals(appointment.getEarliestSRD().getReasonCd())
					|| "08".equals(appointment.getEarliestSRD().getReasonCd())
					|| "07".equals(appointment.getEarliestSRD().getReasonCd())){
				appointmentDetailLts.setTidInd("Y");
				SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Calendar tid = new GregorianCalendar();
				tid.set(Calendar.YEAR, tid.get(Calendar.YEAR)+1);
				tid.set(Calendar.MONTH, Calendar.DECEMBER);
				tid.set(Calendar.DAY_OF_MONTH, 31);
				appntDate = df.format(tid.getTime());
				appntDateString = ltsAppointmentService.reformatDateTimeSlot(appntDate, LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
				appointmentDetailLts.setTidStartTime(appntDateString[0]);
				appointmentDetailLts.setTidEndTime(appntDateString[1]);
			}

			break;
		}
		
		
		
		if(StringUtils.isNotBlank(cutOverTimeSlot) && StringUtils.isNotBlank(cutOverDate)){
			cutOverDateString = ltsAppointmentService.reformatDateTimeSlot(cutOverDate, cutOverTimeSlot);
			appointmentDetailLts.setCutOverStartTime(cutOverDateString[0]);
			appointmentDetailLts.setCutOverEndTime(cutOverDateString[1]);
		}
		
		return appointmentDetailLts;
	}
	
	private boolean isCopyErIaToBa(CreateServiceType createServiceType, OrderCaptureDTO orderCapture) {
		LtsAddressRolloutFormDTO ltsAddressRolloutForm = orderCapture.getLtsAddressRolloutForm();
		LtsOtherVoiceServiceFormDTO ltsOtherVoiceServiceForm = orderCapture.getLtsOtherVoiceServiceForm();
		switch (createServiceType) {
		case CREATE_SRV_TYPE_UPGRADE:
			if (ltsAddressRolloutForm.isExternalRelocate() && ltsAddressRolloutForm.getBaCaAction() == BaCaActionType.SAME_AS_NEW_IA) {
				return true;
			}
			break;
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_NEW_2DEL:
			if (ltsOtherVoiceServiceForm.getSecondDelEr() != null && ltsOtherVoiceServiceForm.getSecondDelEr().booleanValue()
					&& ltsOtherVoiceServiceForm.getSecondDelBaCaChange() != null && ltsOtherVoiceServiceForm.getSecondDelBaCaChange().booleanValue()) {
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}

	private boolean isCancelProfileVas(OrderCaptureDTO orderCapture) {
		LtsBasketVasDetailFormDTO ltsBasketVasDetailForm = orderCapture.getLtsBasketVasDetailForm();
		List<ItemDetailProfileLtsDTO> profileVasItemList = ltsBasketVasDetailForm.getProfileExistingVasItemList();
		
		if (profileVasItemList == null || profileVasItemList.isEmpty()) {
			return false;
		}
		
		for (ItemDetailProfileLtsDTO itemDetailProfileLts : profileVasItemList) {
			if (itemDetailProfileLts.getItemDetail() != null
					&& itemDetailProfileLts.getItemDetail().isSelected()) {
				return true;
			}
		}
		
		return false;
	}
	
	private String getPenaltyHandling(ItemDetailDTO itemDetail) {
		
		if (itemDetail == null 
				|| StringUtils.isEmpty(itemDetail.getPenaltyHandling())) {
			return null;
		}
		
		else if (LtsConstant.ITEM_TYPE_ER_CHARGE.equals(itemDetail.getItemType())) {
			return itemDetail.getPenaltyHandling();
		}
		else if (LtsConstant.OFFER_HANDLE_AUTO_WAIVE.equals(itemDetail.getPenaltyHandling())) {
			return LtsConstant.PENALTY_ACTION_AUTO_WAIVE;
		}
		else if (LtsConstant.OFFER_HANDLE_MANUAL_WAIVE.equals(itemDetail.getPenaltyHandling())){
			return LtsConstant.PENALTY_ACTION_AWAIT_APPROVAL;
		}
		else if (LtsConstant.OFFER_HANDLE_AUTO_GENERATE.equals(itemDetail.getPenaltyHandling())){
			return LtsConstant.PENALTY_ACTION_GENERATE;
		}
		
		return null;
	}
	
	
	protected void revisePenaltyForRenewalEffectiveDate (List<ItemDetailProfileLtsDTO> profileItemList, ItemDetailProfileLtsDTO clonedProfileItem, String toProd) {
		if (profileItemList == null || profileItemList.isEmpty()) {
			return;
		}
		
		for (ItemDetailProfileLtsDTO profileItem : profileItemList) {
			if (StringUtils.equals(profileItem.getItemId(), clonedProfileItem.getItemId())
					&& profileItem.getItemDetail() != null) {
				
				ItemActionLtsDTO itemAction = clonedProfileItem.getItemActionByToProd(toProd);
				
				if (itemAction == null) {
					continue;
				}
				
				if (LtsConstant.PENALTY_ACTION_GENERATE.equals(profileItem.getItemDetail().getPenaltyHandling())
						|| LtsConstant.OFFER_HANDLE_AUTO_GENERATE.equals(profileItem.getItemDetail().getPenaltyHandling())
						|| LtsConstant.PENALTY_ACTION_MANUAL_WAIVE.equals(profileItem.getItemDetail().getPenaltyHandling())
						|| LtsConstant.OFFER_HANDLE_MANUAL_WAIVE.equals(profileItem.getItemDetail().getPenaltyHandling())) {
					if (LtsConstant.PENALTY_ACTION_AUTO_WAIVE.equals(itemAction.getPenaltyHandle())) {
						profileItem.getItemDetail().setPenaltyHandling(LtsConstant.PENALTY_ACTION_AUTO_WAIVE);
					}
				}
				
				if (LtsConstant.PENALTY_ACTION_AUTO_WAIVE.equals(profileItem.getItemDetail().getPenaltyHandling())) {
					if (LtsConstant.PENALTY_ACTION_GENERATE.equals(itemAction.getPenaltyHandle())
									|| LtsConstant.OFFER_HANDLE_AUTO_GENERATE.equals(itemAction.getPenaltyHandle())) {
						profileItem.getItemDetail().setPenaltyHandling(LtsConstant.PENALTY_ACTION_GENERATE);
					}
				}
			}
		}
	}
	
	protected void revisePenaltyForRenewalEffectiveDate(SbOrderDTO sbOrder, ItemDetailProfileLtsDTO clonedProfileItem, String toProd) {
		ServiceDetailLtsDTO ltsCoreService = LtsSbOrderHelper.getLtsService(sbOrder);
		ItemDetailLtsDTO[] subcItems = ltsCoreService.getItemDtls();
		
		if (ArrayUtils.isEmpty(subcItems)) {
			return;
		}
		
		for (ItemDetailLtsDTO subcItem : subcItems) {
			if (StringUtils.equals(clonedProfileItem.getItemId(), subcItem.getItemId())) {
				ItemActionLtsDTO itemAction = clonedProfileItem.getItemActionByToProd(toProd);
				
				if (itemAction == null) {
					continue;
				}
				
				if (LtsConstant.PENALTY_ACTION_GENERATE.equals(subcItem.getPenaltyHandling())
						|| LtsConstant.OFFER_HANDLE_AUTO_GENERATE.equals(subcItem.getPenaltyHandling())
						|| LtsConstant.PENALTY_ACTION_MANUAL_WAIVE.equals(subcItem.getPenaltyHandling())
						|| LtsConstant.OFFER_HANDLE_MANUAL_WAIVE.equals(subcItem.getPenaltyHandling())) {
					if (LtsConstant.PENALTY_ACTION_AUTO_WAIVE.equals(itemAction.getPenaltyHandle())) {
						subcItem.setPenaltyHandling(LtsConstant.PENALTY_ACTION_AUTO_WAIVE);
						subcItem.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
					}
				}
				
				if (LtsConstant.PENALTY_ACTION_AUTO_WAIVE.equals(subcItem.getPenaltyHandling())) {
					if (LtsConstant.PENALTY_ACTION_GENERATE.equals(itemAction.getPenaltyHandle())
									|| LtsConstant.OFFER_HANDLE_AUTO_GENERATE.equals(itemAction.getPenaltyHandle())) {
						subcItem.setPenaltyHandling(LtsConstant.PENALTY_ACTION_GENERATE);
						subcItem.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
					}
				}
			}
		}
		
	}
	
	
	protected void revisePenaltyForRenewalEffectiveDate(OrderCaptureDTO orderCapture) {
		
		if (!LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			return;
		}
		
		try { 
			ServiceDetailProfileLtsDTO clonedSrvProfile = (ServiceDetailProfileLtsDTO)CommonUtil.cloneNestedSerializableObject(orderCapture.getLtsServiceProfile());
			
			String effectiveDate = LtsDateFormatHelper.getSysDate("dd/MM/yyyy");
			if (orderCapture.getLtsAppointmentForm() != null) {
				effectiveDate = orderCapture.getLtsAppointmentForm().getInstallationDate();
			}
			ltsProfileService.getOfferDetails(clonedSrvProfile, effectiveDate);
			
			String toProd = StringUtils.defaultIfEmpty(orderCapture.getLtsServiceProfile().getExistEyeType(), LtsConstant.LTS_PRODUCT_TYPE_DEL);
			if (ArrayUtils.isNotEmpty(clonedSrvProfile.getItemDetails())) {
				for (ItemDetailProfileLtsDTO clonedProfileItem : clonedSrvProfile.getItemDetails()) {
					
					if (orderCapture.getSbOrder() != null) {
						revisePenaltyForRenewalEffectiveDate(orderCapture.getSbOrder(), clonedProfileItem, toProd);
					}
					else {
						revisePenaltyForRenewalEffectiveDate(orderCapture.getLtsBasketVasDetailForm().getProfileAutoChangeTpItemList(), clonedProfileItem, toProd);
						revisePenaltyForRenewalEffectiveDate(orderCapture.getLtsBasketVasDetailForm().getProfileAutoOutTpItemList(), clonedProfileItem, toProd);
						revisePenaltyForRenewalEffectiveDate(orderCapture.getLtsBasketVasDetailForm().getProfileAutoOutVasItemList(), clonedProfileItem, toProd);
//						revisePenaltyForRenewalEffectiveDate(orderCapture.getLtsBasketVasDetailForm().getProfileExistingTpItemList(), clonedProfileItem, toProd);
//						revisePenaltyForRenewalEffectiveDate(orderCapture.getLtsBasketVasDetailForm().getProfileExistingVasItemList(), clonedProfileItem, toProd);						
					}

				}
			}
			
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	private void addItemDetailProfileLts(BasketDetailDTO selectedBasket, OrderCaptureDTO orderCapture, List<ItemDetailLtsDTO> itemDetailLtsList) {
		
		
		 LtsBasketVasDetailFormDTO ltsBasketVasDetailForm = orderCapture.getLtsBasketVasDetailForm();
		 ServiceDetailProfileLtsDTO srvProfile = orderCapture.getLtsServiceProfile();
		 
		 revisePenaltyForRenewalEffectiveDate(orderCapture);
		 
		// User cancel existing VAS manually 
		cancelExistingVasItem(selectedBasket, ltsBasketVasDetailForm.getProfileExistingVasItemList(), itemDetailLtsList);

		// System auto out expired TP VAS
		if (ltsBasketVasDetailForm.getProfileExpiredItemList() != null && !ltsBasketVasDetailForm.getProfileExpiredItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO  itemDetailProfileLts : ltsBasketVasDetailForm.getProfileExpiredItemList()) {
				ItemDetailDTO itemDetail =  itemDetailProfileLts.getItemDetail();
				ItemDetailLtsDTO itemDetailLts = createItemDetailLts(selectedBasket, itemDetail, String.valueOf(itemDetailLtsList.size() + 1), 
						LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType()) ? LtsConstant.IO_IND_SPACE : LtsConstant.IO_IND_OUT);
				itemDetailLts.setExistEffectivePrice(itemDetail.getNetEffPrice());
				itemDetailLts.setExistGrossPrice(itemDetail.getGrossEffPrice());
				itemDetailLts.setExistStartDate(itemDetailProfileLts.getConditionEffStartDate());
				itemDetailLts.setExistEndDate(itemDetailProfileLts.getConditionEffEndDate());
//				itemDetailLts.setPenaltyWaiveInd(getPenaltyWaiveInd(itemDetail));
				itemDetailLts.setRelatedItemId(itemDetailProfileLts.getChangeToItemId());
				itemDetailLts.setPaidVasInd(itemDetailProfileLts.isPaidVas() ? "Y" : null);
				itemDetailLts.setPenaltyHandling(getPenaltyHandling(itemDetail));
				itemDetailLts.setProfileItemType(itemDetailProfileLts.getItemType());
				itemDetailLtsList.add(itemDetailLts);
			}
		}
		
		
		// System auto out existing VAS
		if (ltsBasketVasDetailForm.getProfileAutoOutVasItemList() != null && !ltsBasketVasDetailForm.getProfileAutoOutVasItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO  itemDetailProfileLts : ltsBasketVasDetailForm.getProfileAutoOutVasItemList()) {
				ItemDetailDTO itemDetail =  itemDetailProfileLts.getItemDetail();
				ItemDetailLtsDTO itemDetailLts = createItemDetailLts(selectedBasket, itemDetail, String.valueOf(itemDetailLtsList.size() + 1), LtsConstant.IO_IND_OUT);
				itemDetailLts.setExistEffectivePrice(itemDetail.getNetEffPrice());
				itemDetailLts.setExistGrossPrice(itemDetail.getGrossEffPrice());
				itemDetailLts.setExistStartDate(itemDetailProfileLts.getConditionEffStartDate());
				itemDetailLts.setExistEndDate(itemDetailProfileLts.getConditionEffEndDate());
//				itemDetailLts.setPenaltyWaiveInd(getPenaltyWaiveInd(itemDetail));
				itemDetailLts.setRelatedItemId(itemDetailProfileLts.getChangeToItemId());
				itemDetailLts.setPaidVasInd(itemDetailProfileLts.isPaidVas() ? "Y" : null);
				itemDetailLts.setPenaltyHandling(getPenaltyHandling(itemDetail));
				itemDetailLts.setProfileItemType(itemDetailProfileLts.getItemType());
				itemDetailLts.setForceRetain(itemDetailProfileLts.isForceRetain() ? "Y" : null);
				itemDetailLtsList.add(itemDetailLts);
				
				if(LtsSbOrderHelper.isRecontractOrder(orderCapture)){
					if(itemDetailProfileLts.isForceRetain()){
						itemDetailLts.setForceRetain(null);
						if(!orderCapture.getLtsRecontractForm().isDeceasedCase() ){
							itemDetailLts.setPenaltyHandling(LtsConstant.PENALTY_ACTION_GENERATE);
						}
					}
				}
			}
		}
		
		// Existing Service TP
		if (ltsBasketVasDetailForm.getProfileExistingTpItemList() != null && !ltsBasketVasDetailForm.getProfileExistingTpItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO  itemDetailProfileLts : ltsBasketVasDetailForm.getProfileExistingTpItemList()) {
				ItemDetailDTO itemDetail =  itemDetailProfileLts.getItemDetail();
				add2GCallPlanItem(orderCapture, selectedBasket, itemDetail, itemDetailLtsList, srvProfile);
				ItemDetailLtsDTO itemDetailLts = createItemDetailLts(selectedBasket, itemDetail, String.valueOf(itemDetailLtsList.size() + 1), LtsConstant.IO_IND_SPACE);
				itemDetailLts.setExistEffectivePrice(itemDetail.getNetEffPrice());
				itemDetailLts.setExistGrossPrice(itemDetail.getGrossEffPrice());
				itemDetailLts.setExistStartDate(itemDetailProfileLts.getConditionEffStartDate());
				itemDetailLts.setExistEndDate(itemDetailProfileLts.getConditionEffEndDate());
//				itemDetailLts.setPenaltyWaiveInd(null);
				itemDetailLts.setPaidVasInd(itemDetailProfileLts.isPaidVas() ? "Y" : null);
				itemDetailLts.setPenaltyHandling(getPenaltyHandling(itemDetail));
				itemDetailLts.setProfileItemType(itemDetailProfileLts.getItemType());
				itemDetailLtsList.add(itemDetailLts);
			}
			
		}
		
		// System auto change TP (Can unselect by user manually)
		if (ltsBasketVasDetailForm.getProfileAutoChangeTpItemList() != null && !ltsBasketVasDetailForm.getProfileAutoChangeTpItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO  itemDetailProfileLts : ltsBasketVasDetailForm.getProfileAutoChangeTpItemList()) {
				
				ItemDetailDTO itemDetail =  itemDetailProfileLts.getItemDetail();
				if (!itemDetail.isSelected()) {
					continue;
				}
	
				ItemDetailLtsDTO itemDetailLts = createItemDetailLts(selectedBasket, itemDetail, String.valueOf(itemDetailLtsList.size() + 1), LtsConstant.IO_IND_INSTALL);
				itemDetailLts.setExistEffectivePrice(itemDetail.getNetEffPrice());
				itemDetailLts.setExistGrossPrice(itemDetail.getGrossEffPrice());
				itemDetailLts.setExistStartDate(itemDetailProfileLts.getConditionEffStartDate());
				itemDetailLts.setExistEndDate(itemDetailProfileLts.getConditionEffEndDate());
//				itemDetailLts.setPenaltyWaiveInd(null);
				itemDetailLts.setPaidVasInd(itemDetailProfileLts.isPaidVas() ? "Y" : null);
				itemDetailLts.setPenaltyHandling(getPenaltyHandling(itemDetail));
				itemDetailLts.setProfileItemType(itemDetailProfileLts.getItemType());
				itemDetailLtsList.add(itemDetailLts);
			}
		}
		
		// System auto out TP
		if (ltsBasketVasDetailForm.getProfileAutoOutTpItemList() != null && !ltsBasketVasDetailForm.getProfileAutoOutTpItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO  itemDetailProfileLts : ltsBasketVasDetailForm.getProfileAutoOutTpItemList()) {
				ItemDetailDTO itemDetail =  itemDetailProfileLts.getItemDetail();
				add2GCallPlanItem(orderCapture, selectedBasket, itemDetail, itemDetailLtsList, srvProfile);
				ItemDetailLtsDTO itemDetailLts = createItemDetailLts(selectedBasket, itemDetail, String.valueOf(itemDetailLtsList.size() + 1), LtsConstant.IO_IND_OUT);
				itemDetailLts.setExistEffectivePrice(itemDetail.getNetEffPrice());
				itemDetailLts.setExistGrossPrice(itemDetail.getGrossEffPrice());
				itemDetailLts.setExistStartDate(itemDetailProfileLts.getConditionEffStartDate());
				itemDetailLts.setExistEndDate(itemDetailProfileLts.getConditionEffEndDate());
//				itemDetailLts.setPenaltyWaiveInd(getPenaltyWaiveInd(itemDetail));
				itemDetailLts.setRelatedItemId(itemDetailProfileLts.getChangeToItemId());
				itemDetailLts.setPaidVasInd(itemDetailProfileLts.isPaidVas() ? "Y" : null);
				itemDetailLts.setPenaltyHandling(getPenaltyHandling(itemDetail));
				itemDetailLts.setProfileItemType(itemDetailProfileLts.getItemType());
				itemDetailLtsList.add(itemDetailLts);
				
			}
		}
	}
	
	
	private void addItemSetDetail(BasketDetailDTO selectedBasket, List<ItemSetDetailDTO> itemSetDetailList, List<ItemDetailLtsDTO> itemDetailLtsList) {
		if (itemSetDetailList == null || itemSetDetailList.isEmpty()) {
			return;
		}
		
		for (ItemSetDetailDTO itemSetDetail : itemSetDetailList) {
			
			String membershipId = null;
			ItemSetAttbDTO[] itemSetAttbs = itemSetDetail.getItemSetAttbs();
			
			if (ArrayUtils.isNotEmpty(itemSetAttbs)) {
				membershipId = itemSetAttbs[0].getAttbValue();
			}
			
			for (ItemDetailDTO itemDetail : itemSetDetail.getItemDetails()) {
				
//				if (StringUtils.equals(LtsConstant.ITEM_TYPE_NOWTV_PAY, itemDetail.getItemType()) 
//						|| StringUtils.equals(LtsConstant.ITEM_TYPE_NOWTV_SPEC, itemDetail.getItemType())) {
//					continue;
//				}
				
				if (itemDetail.isSelected()) {
					ItemDetailLtsDTO itemDetailLts = createItemDetailLts(selectedBasket, itemDetail,
							String.valueOf(itemDetailLtsList.size() + 1),
							LtsConstant.IO_IND_INSTALL);
					itemDetailLts.setMembershipId(membershipId);
					itemDetailLtsList.add(itemDetailLts);
				}
			}
		}
	}
	
	protected void addItemDetail(BasketDetailDTO selectedBasket, List<ItemDetailDTO> itemDetailList, List<ItemDetailLtsDTO> itemDetailLtsList) {
		if (itemDetailList == null || itemDetailList.isEmpty()) {
			return;
		}
		
		for (ItemDetailDTO itemDetail : itemDetailList) {
			if (!itemDetail.isSelected()) {
				continue;
			}
			itemDetailLtsList
					.add(createItemDetailLts(
							selectedBasket,
							itemDetail,
							String.valueOf(itemDetailLtsList.size() + 1),
							LtsConstant.IO_IND_INSTALL));
		}
	}
	
	private ItemDetailLtsDTO createItemDetailLts (BasketDetailDTO selectedBasket, ItemDetailDTO itemDetail, String srvItemSeq, String ioInd) {
		ItemDetailLtsDTO itemDetailLts = new ItemDetailLtsDTO();
		itemDetailLts.setSrvItemSeq(srvItemSeq);
		itemDetailLts.setBasketId(selectedBasket.getBasketId());
		itemDetailLts.setItemAttbs(createItemAttributeDetailLts(itemDetail.getItemAttbs(), srvItemSeq));
		itemDetailLts.setItemId(itemDetail.getItemId());
		itemDetailLts.setItemType(itemDetail.getItemType());
		itemDetailLts.setIoInd(ioInd);
		itemDetailLts.setItemQty(StringUtils.isNotBlank(itemDetail.getSelectedQty()) ? itemDetail.getSelectedQty() : "1");
		itemDetailLts.setPenaltyHandling(getPenaltyHandling(itemDetail));
		itemDetailLts.setAfType(itemDetail.getAfType());
		return itemDetailLts;
	}
	
	private ItemAttributeDetailLtsDTO[] createItemAttributeDetailLts(ItemAttbDTO[] itemAttbs, String srvItemSeq) {
		
		if (ArrayUtils.isEmpty(itemAttbs)) {
			return null;
		}
		
		List<ItemAttributeDetailLtsDTO> itemAttributeDetailLtsList = new ArrayList<ItemAttributeDetailLtsDTO>();
		
		for (ItemAttbDTO itemAttb : itemAttbs) {
			ItemAttributeDetailLtsDTO itemAttributeDetailLts = new ItemAttributeDetailLtsDTO();
			itemAttributeDetailLts.setAttbCd(itemAttb.getAttbID());
			itemAttributeDetailLts.setAttbValue(itemAttb.getAttbValue());
			itemAttributeDetailLts.setSrvItemSeq(srvItemSeq);
			itemAttributeDetailLts.setComptId(itemAttb.getComptId());
			itemAttributeDetailLts.setProductId(itemAttb.getProdId());
			itemAttributeDetailLts.setOfferId(itemAttb.getOfferId());
			itemAttributeDetailLtsList.add(itemAttributeDetailLts);
		}
		return itemAttributeDetailLtsList.toArray(new ItemAttributeDetailLtsDTO[itemAttributeDetailLtsList.size()]);
	}
	
	
	private String getTwoNInd(String serviceNum, ServiceDetailProfileLtsDTO serviceProfile) {
		if (StringUtils.equals(serviceNum, serviceProfile.getSrvNum())) {
			return serviceProfile.isTwoNBuildInd() ? "Y" : null;
		}
		if (StringUtils.equals(serviceNum, serviceProfile.getDuplexNum())) {
			return serviceProfile.isDuplexTwoNBuildInd() ? "Y" : null;
		}
		return null;
	}
	
	private String getDuplexInd(String serviceNum, ServiceDetailProfileLtsDTO serviceProfile) {
		
		if (StringUtils.isEmpty(serviceProfile.getDuplexNum())) {
			return null;
		}
		
		if (StringUtils.equals(serviceProfile.getSrvNum(), serviceNum)) {
			return LtsConstant.DUPLEX_X_IND;
		}
		if (StringUtils.equals(serviceProfile.getDuplexNum(), serviceNum)) {
			return LtsConstant.DUPLEX_Y_IND;
		}
		return null;
	}
	
	
	private String getDnSource(CreateServiceType createService, OrderCaptureDTO orderCapture){
		
		LtsOtherVoiceServiceFormDTO otherVoiceForm = orderCapture.getLtsOtherVoiceServiceForm();
		LtsDnChangeFormDTO dnxForm = orderCapture.getLtsDnChangeForm();
		LtsDnChangeDuplexFormDTO dnyForm = orderCapture.getLtsDnChangeDuplexForm();
		switch (createService) {
		  case CREATE_SRV_TYPE_RENEW_DEL:
		  case CREATE_SRV_TYPE_RENEW_EYE:
			  
			  if(otherVoiceForm.getDuplexXSrvType() !=null
				      && otherVoiceForm.getDuplexXSrvType() == LtsOtherVoiceServiceFormDTO.DuplexSrvType.RETAIN
				      && dnxForm != null){
					  return dnxForm.getDnSource();
				  }
			  
			  if(otherVoiceForm.getDuplexYSrvType() != null
					  && otherVoiceForm.getDuplexYSrvType() == LtsOtherVoiceServiceFormDTO.DuplexSrvType.RETAIN
					  && dnyForm != null){
				  return dnyForm.getDnSource();
			  }
			  
			  if(dnxForm != null){
					 return dnxForm.getDnSource();
			  }
			  
			  return null;

		  case CREATE_SRV_TYPE_UPGRADE:
			  
			  if(otherVoiceForm.getDuplexXSrvType()  == LtsOtherVoiceServiceFormDTO.DuplexSrvType.UPGRADE){
				  if(dnxForm != null){
					 return dnxForm.getDnSource();
				  }
			  }else if(otherVoiceForm.getDuplexYSrvType()  == LtsOtherVoiceServiceFormDTO.DuplexSrvType.UPGRADE){
				  if(dnyForm != null){
					 return dnyForm.getDnSource();
				  }
			  }else if (dnxForm != null ) {
				  return dnxForm.getDnSource();
			  }
			  return null;
		  case CREATE_SRV_TYPE_DUPLEX_NUM_CHANGE:
		  case CREATE_SRV_TYPE_DUPLEX_NUM_NO_CHANGE:
			  if (dnyForm != null) {
				  return dnyForm.getDnSource();
			  }
			  return null;
		  case	CREATE_SRV_TYPE_NEW_2DEL:
			  if (otherVoiceForm != null 
				  && LtsConstant.INVENT_STS_PRE_ASSIGN.equals(orderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelSrvStatus())) {
				  return LtsConstant.DN_SOURCE_DN_POOL;
			  }
			  return null;
		  case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
			  //dnsource setting --MAX.R.MENG
			  if( dnxForm != null && otherVoiceForm.getDuplexXSrvType() == LtsOtherVoiceServiceFormDTO.DuplexSrvType.NEW_2ND_DEL){
				  return dnxForm.getDnSource();
			  }
			  
			  if(dnyForm != null && otherVoiceForm.getDuplexYSrvType() == LtsOtherVoiceServiceFormDTO.DuplexSrvType.NEW_2ND_DEL){
				  return dnyForm.getDnSource();
			  }
			  return null;
			  
		default:
			return null;
		}
	}
	
	private String getNewSrvNum(CreateServiceType createService, OrderCaptureDTO orderCapture, ServiceDetailProfileLtsDTO serviceProfile) {
		
		switch (createService) {
		  case CREATE_SRV_TYPE_RENEW_DEL:
		  case CREATE_SRV_TYPE_RENEW_EYE:
		  case CREATE_SRV_TYPE_UPGRADE:
			  
		if(StringUtils.isEmpty(serviceProfile.getDuplexNum()) 
			|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == null){
			
		   if(orderCapture.getLtsDnChangeForm() != null){
				if(StringUtils.equals(orderCapture.getLtsDnChangeForm().getDnSource() , "R")){
					 return orderCapture.getLtsDnChangeForm().getReservedSrvNum();
				    }
					
				 if(StringUtils.equals(orderCapture.getLtsDnChangeForm().getDnSource() , "D")){
					 return orderCapture.getLtsDnChangeForm().getConfirmedDn();
				  }
			}
		   return null;
		}
		
		if(orderCapture.getLtsDnChangeForm() != null){
			if(orderCapture.getLtsDnChangeForm().getConfirmedDn() != null || orderCapture.getLtsDnChangeForm().getReservedSrvNum() != null){
				if (orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.UPGRADE
						|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.RETAIN
						|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == null) {
					
						
						if(StringUtils.equals(orderCapture.getLtsDnChangeForm().getDnSource() , "R")){
						 return orderCapture.getLtsDnChangeForm().getReservedSrvNum();
					    }
						
				        if(StringUtils.equals(orderCapture.getLtsDnChangeForm().getDnSource() , "D")){
						 return orderCapture.getLtsDnChangeForm().getConfirmedDn();
					    }
					    
					}
			}else{
				if(orderCapture.getLtsDnChangeDuplexForm().getConfirmedDn() != null || orderCapture.getLtsDnChangeDuplexForm().getReservedSrvNum() != null){
					return null;
				}
			}
		}
		
		if(orderCapture.getLtsDnChangeDuplexForm() != null){
			if(orderCapture.getLtsDnChangeDuplexForm().getConfirmedDn() != null || orderCapture.getLtsDnChangeDuplexForm().getReservedSrvNum() != null){
				if (orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType() == DuplexSrvType.UPGRADE
						|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType() == DuplexSrvType.RETAIN
						|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType() == null) {
					
						
						if(orderCapture.getLtsDnChangeDuplexForm().getDnSource() == "R"){
						 return orderCapture.getLtsDnChangeDuplexForm().getReservedSrvNum();
					    }
						
				        if(orderCapture.getLtsDnChangeDuplexForm().getDnSource() == "D"){
						 return orderCapture.getLtsDnChangeDuplexForm().getConfirmedDn();
					    }
					    
					}
			}
		}
	    return null;
	   
	      case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
	    	  if(orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.NEW_2ND_DEL){
	    		 if(orderCapture.getLtsDnChangeForm() != null){
	    			if(orderCapture.getLtsDnChangeForm().getDnSource() == "R"){
	    				return orderCapture.getLtsDnChangeForm().getReservedSrvNum();
	    			}
	    					
	    			if(orderCapture.getLtsDnChangeForm().getDnSource() == "D"){
	    				return orderCapture.getLtsDnChangeForm().getConfirmedDn();
	    			}
	  			}
	    	  }
	    	  
	    	  if(orderCapture.getLtsOtherVoiceServiceForm().getDuplexYSrvType() == DuplexSrvType.NEW_2ND_DEL){
	    	     if(orderCapture.getLtsDnChangeDuplexForm() != null ){
	    	    	 
	    	    	if(orderCapture.getLtsDnChangeDuplexForm().getDnSource() == "R"){
	 					   return orderCapture.getLtsDnChangeDuplexForm().getReservedSrvNum() ;
	 				   }
	 				
	 				if(orderCapture.getLtsDnChangeDuplexForm().getDnSource() == "D"){
	 					   return orderCapture.getLtsDnChangeDuplexForm().getConfirmedDn(); 
	 				   }
	    			}
	    	  }
	    	  
	    	  return null;
	      case CREATE_SRV_TYPE_DUPLEX_NUM_CHANGE:
	    	  if(orderCapture.getLtsDnChangeDuplexForm().getDnSource() == "R"){
	    		  return orderCapture.getLtsDnChangeDuplexForm().getReservedSrvNum() ;
	    	  }
	    	  if(orderCapture.getLtsDnChangeDuplexForm().getDnSource() == "D"){
				   return orderCapture.getLtsDnChangeDuplexForm().getConfirmedDn(); 
			  }
	    	  return null;
	    
	      case CREATE_SRV_TYPE_DUPLEX_NUM_NO_CHANGE:
	    	  
	    	  if(StringUtils.equals(orderCapture.getLtsDnChangeDuplexForm().getDnSource(),"R")){
	    		  return orderCapture.getLtsDnChangeDuplexForm().getReservedSrvNum() ;
	    	  }
	    	  if(StringUtils.equals(orderCapture.getLtsDnChangeDuplexForm().getDnSource() , "D")){
				   return orderCapture.getLtsDnChangeDuplexForm().getConfirmedDn(); 
			  }
	    	  
	    	  return null;
	    	  
	  	default:
			return null;
		}
	}
	
	private String getServiceNum(CreateServiceType createService, OrderCaptureDTO orderCapture, ServiceDetailProfileLtsDTO serviceProfile) {
		
		switch (createService) {
		case CREATE_SRV_TYPE_RENEW_DEL:
		case CREATE_SRV_TYPE_RENEW_EYE:
		case CREATE_SRV_TYPE_UPGRADE:
			if (StringUtils.isEmpty(serviceProfile.getDuplexNum())) {
				return serviceProfile.getSrvNum();
			}
			
			if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType()) 
					&& orderCapture.getLtsOtherVoiceServiceForm().isDuplexProfile() 
					&& !orderCapture.getLtsOtherVoiceServiceForm().isRemoveRenewalDuplex()
					&& !orderCapture.getLtsMiscellaneousForm().isDnChange()
					) {
				return serviceProfile.getSrvNum();
			}
			
			if(orderCapture.getLtsMiscellaneousForm().isDnChange()){				
					if(orderCapture.getLtsDnChangeForm() != null){
				      if(orderCapture.getLtsDnChangeForm().getConfirmedDn() != null
						   || orderCapture.getLtsDnChangeForm().getReservedSrvNum() != null){
					     if (orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == null
							|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.UPGRADE
							|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.RETAIN) {
						     return serviceProfile.getSrvNum();
					      }
				       }else{
				    	   if(orderCapture.getLtsDnChangeDuplexForm().getConfirmedDn() != null
						   || orderCapture.getLtsDnChangeDuplexForm().getReservedSrvNum() != null){
				    		   if (orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == null
										|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.UPGRADE
										|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.RETAIN) {
									     return serviceProfile.getSrvNum();
								      }
				    	   }
				       }
					}
			}
			
			if (orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.UPGRADE
					|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.RETAIN
					|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == null) {
				return serviceProfile.getSrvNum();
			}
			return serviceProfile.getDuplexNum();
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_DUPLEX_REMOVE:
			if (!(orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.UPGRADE
					|| orderCapture.getLtsOtherVoiceServiceForm().getDuplexXSrvType() == DuplexSrvType.RETAIN)) {
				return serviceProfile.getSrvNum();
			}
			return serviceProfile.getDuplexNum();
			
		case CREATE_SRV_TYPE_NEW_2DEL:
			if (!orderCapture.getLtsOtherVoiceServiceForm().getCreate2ndDel().booleanValue()) {
				return null;
			}
			return StringUtils.leftPad(orderCapture.getLtsOtherVoiceServiceForm().getNew2ndDelDn(), 12, "0");
		case CREATE_SRV_TYPE_SIP_REMOVE:
			
			ServiceGroupMemberProfileDTO[] groupProfiles  = serviceProfile.getSrvGrp().getGrpMems();
			
			for (ServiceGroupMemberProfileDTO groupProfile : groupProfiles) {
				if (LtsConstant.DAT_CD_NCR.equals(groupProfile.getDatCd())) {
					return groupProfile.getSrvNum();
				}
			}
			return null;
		 case CREATE_SRV_TYPE_DUPLEX_NUM_CHANGE:
			 if (StringUtils.isEmpty(serviceProfile.getDuplexNum())) {
					return null;
				}
			 if (orderCapture.getLtsDnChangeForm() != null && orderCapture.getLtsDnChangeDuplexForm() != null){
				 if((orderCapture.getLtsDnChangeForm().getConfirmedDn() != null || orderCapture.getLtsDnChangeForm().getReservedSrvNum() != null)
						 && (orderCapture.getLtsDnChangeDuplexForm().getConfirmedDn() != null || orderCapture.getLtsDnChangeDuplexForm().getReservedSrvNum() != null)){
					 return serviceProfile.getDuplexNum();
				 }
			 }
		 case CREATE_SRV_TYPE_DUPLEX_NUM_NO_CHANGE:
			 if (StringUtils.isEmpty(serviceProfile.getDuplexNum())) {
					return null;
			  }
			
			 return serviceProfile.getDuplexNum();

		default:
			return null;
		}
	}

	private void cancelExistingVasItem(BasketDetailDTO selectedBasket, List<ItemDetailProfileLtsDTO> cancelVasItemList, List<ItemDetailLtsDTO> itemDetailLtsList) {
		
		if (cancelVasItemList == null || cancelVasItemList.isEmpty()) {
			return;
		}
		
		for (ItemDetailProfileLtsDTO itemDetailProfile : cancelVasItemList) {
			ItemDetailDTO itemDetail =  itemDetailProfile.getItemDetail();
			ItemDetailLtsDTO itemDetailLts = createItemDetailLts(
					selectedBasket, itemDetail,
					String.valueOf(itemDetailLtsList.size() + 1),
					itemDetail.isSelected() ? LtsConstant.IO_IND_OUT
							: LtsConstant.IO_IND_SPACE);
			itemDetailLts.setExistEffectivePrice(itemDetail.getNetEffPrice());
			itemDetailLts.setExistGrossPrice(itemDetail.getGrossEffPrice());
			itemDetailLts.setExistEndDate(itemDetailProfile.getConditionEffEndDate());
			if(itemDetail.isSelected()
					&& StringUtils.isNotBlank(itemDetailProfile.getConditionEffEndDate())
					&& itemDetailProfile.getTpExpiredMonths() <= 0){
				itemDetailLts.setPenaltyHandling(LtsConstant.PENALTY_ACTION_GENERATE);
			}else{
				itemDetailLts.setPenaltyHandling(null);
			}
			itemDetailLts.setPaidVasInd(itemDetailProfile.isPaidVas() ? "Y" : null);
			itemDetailLtsList.add(itemDetailLts);
		}
	}
	
	private ThirdPartyDetailLtsDTO createThirdPartyDetailLts (CreateServiceType createServiceType, OrderCaptureDTO orderCapture) {
		
		LtsCustomerIdentificationFormDTO custIdForm = orderCapture.getLtsCustomerIdentificationForm();
		
		if (custIdForm == null) {
			return null;
		}
		
		ThirdPartyDetailLtsDTO thirdPartyDetailLts = new ThirdPartyDetailLtsDTO();
		
		switch (createServiceType) {
		case CREATE_SRV_TYPE_UPGRADE:
		case CREATE_SRV_TYPE_RENEW_DEL:
		case CREATE_SRV_TYPE_RENEW_EYE:
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
			if (!custIdForm.isThirdPartyApplication()) {
				return null;
			}
			thirdPartyDetailLts.setTitle(custIdForm.getApplicantTitle());
			thirdPartyDetailLts.setAppntDocId(custIdForm.getApplicantId());
			thirdPartyDetailLts.setAppntDocType(custIdForm.getApplicantDocType());
			thirdPartyDetailLts.setAppntContactNum(custIdForm.getContactNum());
			thirdPartyDetailLts.setAppntFirstName(custIdForm.getApplicantFirstName());
			thirdPartyDetailLts.setAppntLastName(custIdForm.getApplicantLastName());
			thirdPartyDetailLts.setAppntIdVerifiedInd(custIdForm.isApplicantVerified()?"Y":null);
			thirdPartyDetailLts.setRelationshipCode(custIdForm.getRelationship());
			
			break;
		case CREATE_SRV_TYPE_NEW_2DEL:
			if (!custIdForm.isSecDelThirdPartyApplication()) {
				return null;
			}
			thirdPartyDetailLts.setTitle(custIdForm.getSecDelApplicantTitle());
			thirdPartyDetailLts.setAppntDocId(custIdForm.getSecDelApplicantId());
			thirdPartyDetailLts.setAppntDocType(custIdForm.getSecDelApplicantDocType());
			thirdPartyDetailLts.setAppntFirstName(custIdForm.getSecDelApplicantFirstName());
			thirdPartyDetailLts.setAppntLastName(custIdForm.getSecDelApplicantLastName());
			thirdPartyDetailLts.setAppntContactNum(custIdForm.getSecDelContactNum());
			thirdPartyDetailLts.setRelationshipCode(custIdForm.getSecDelRelationship());
			thirdPartyDetailLts.setAppntIdVerifiedInd(custIdForm.isSecDelApplicantVerified() ? "Y" : null);
			break;
		default:
			return null;
		}
		
		return thirdPartyDetailLts;
	}
	
	private boolean isPaperBillSelected (OrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsPaymentForm() == null) {
			return false;
		}
		
		return ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL)
				|| ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE)
				|| ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE)
				|| ltsOfferService.isItemSelected(orderCapture.getLtsPaymentForm().getBillItemList(), LtsConstant.ITEM_TYPE_PAPER_BILL_BR);
	}
	
	private String getPendingApprovalCd(CreateServiceType createServiceType, OrderCaptureDTO orderCapture) {
		
		switch (createServiceType) {
		case CREATE_SRV_TYPE_UPGRADE:
		case CREATE_SRV_TYPE_RENEW_EYE:
		case CREATE_SRV_TYPE_RENEW_DEL:
			if (orderCapture.getLtsMiscellaneousForm() != null
					&& !orderCapture.getLtsMiscellaneousForm().isRedeemPrevPremium()) {
				return LtsConstant.APPROVAL_CD_NO_REDEEM_PREMIUM;
			}
			break;
		case CREATE_SRV_TYPE_NEW_2DEL:
			if (orderCapture.getLtsOtherVoiceServiceForm() != null
					&& orderCapture.getLtsOtherVoiceServiceForm().isShowSecondDelRedeemPremium()
					&& orderCapture.getLtsOtherVoiceServiceForm().getSecondDelRedeemPremium() != null
					&& !orderCapture.getLtsOtherVoiceServiceForm().getSecondDelRedeemPremium().booleanValue()) {
				return LtsConstant.APPROVAL_CD_NO_REDEEM_PREMIUM;
			}
			break;
		}		
		return null;
	}
	
	private String getCallPlanDowngradeInd(OrderCaptureDTO orderCapture, ServiceDetailProfileLtsDTO serviceProfile, BasketDetailDTO selectedBasket) {
		if (ArrayUtils.isEmpty(serviceProfile.getItemDetails())) {
			return null;
		}
		String profileIddMin = null;
		for (ItemDetailProfileLtsDTO itemDetailProfileLts : serviceProfile.getItemDetails()) {
			if (StringUtils.isNotBlank(itemDetailProfileLts.getIddFreeMin())) {
				profileIddMin = itemDetailProfileLts.getIddFreeMin();
				break;
			}
		}
		if (StringUtils.isEmpty(profileIddMin) || StringUtils.equals("0", profileIddMin)) {
			return null;
		}
		if (LtsConstant.ORDER_SUB_TYPE_SB_STANDALONE_VAS.equals(orderCapture.getOrderSubType()) ) {
			return LtsConstant.IDD_CALL_PLAN_UNCHANGED;
		}
		if (StringUtils.isEmpty(selectedBasket.getIddFreeMin()) || StringUtils.equals("0", selectedBasket.getIddFreeMin())) {
			return LtsConstant.IDD_CALL_PLAN_CANCEL;
		}
		
		if (!StringUtils.equals(profileIddMin, selectedBasket.getIddFreeMin())) {
			return LtsConstant.IDD_CALL_PLAN_CHANGED;
		}
		
		return LtsConstant.IDD_CALL_PLAN_UNCHANGED;
	}
	
	protected List<RemarkDetailLtsDTO> createWqRemarkDetailLts(OrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsWqRemarkForm() == null) {
			return null;
		}
		List<RemarkDetailLtsDTO> remarkDetailLtsList = new ArrayList<RemarkDetailLtsDTO>();
		remarkDetailLtsList.addAll(this.createRemarkDetailLts(orderCapture.getLtsWqRemarkForm().getWqRemark(), LtsConstant.REMARK_TYPE_APPROVL_REMARK));
		return remarkDetailLtsList;
	}
	
	protected List<RemarkDetailLtsDTO> createAddonRemarkDetailLts(OrderCaptureDTO orderCapture) {
		
		if (orderCapture.getLtsAppointmentForm() == null) {
			return null;
		}
		List<RemarkDetailLtsDTO> remarkDetailLtsList = new ArrayList<RemarkDetailLtsDTO>();
		remarkDetailLtsList.addAll(this.createRemarkDetailLts(orderCapture.getLtsAppointmentForm().getRemarks(), LtsConstant.REMARK_TYPE_ADD_ON_REMARK));
		remarkDetailLtsList.addAll(this.createRemarkDetailLts(orderCapture.getLtsAppointmentForm().getEarliestSrdReason(), LtsConstant.REMARK_TYPE_EARLIEST_SRD_REMARK));
		return remarkDetailLtsList;
	}
	
	
	private List<RemarkDetailLtsDTO> createRemarkDetailLts(String pRemarkContent, String pRemarkType) {
		
		List<RemarkDetailLtsDTO> remarkDetailLtsList = new ArrayList<RemarkDetailLtsDTO>();
		RemarkDetailLtsDTO remarkDetailLts = null;
		
		for (int start = 0; pRemarkContent != null && start < pRemarkContent.length(); start += 250) {
			remarkDetailLts = new RemarkDetailLtsDTO();
			remarkDetailLts.setRmkDtl(pRemarkContent.substring(start, Math.min(pRemarkContent.length(), start + 250))); 
			remarkDetailLts.setRmkSeq(String.valueOf(remarkDetailLtsList.size() + 1));
			remarkDetailLts.setRmkType(pRemarkType);
			remarkDetailLtsList.add(remarkDetailLts);
	    }
		return remarkDetailLtsList;
	}	
	
	private void add2GCallPlanItem(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket, ItemDetailDTO itemDetail, List<ItemDetailLtsDTO> itemDetailLtsList, ServiceDetailProfileLtsDTO srvProfile) {
		
		if (StringUtils.isEmpty(itemDetail.getTpCatg()) 
				|| !StringUtils.equals(LtsConstant.IDD_CALL_PLAN_UNCHANGED, this.getCallPlanDowngradeInd(orderCapture, srvProfile, selectedBasket))) {
			return;
		}
		
		String callPlanItemId = (String)eye2GCallPlanLkupCacheService.get(itemDetail.getTpCatg());
		
		if (StringUtils.isEmpty(callPlanItemId)) {
			return;
		}
		
		List<ItemDetailDTO> callPlanItemList = ltsOfferService.getItem(
				new String[] { callPlanItemId },
				LtsConstant.DISPLAY_TYPE_ITEM_SELECT, LtsConstant.LOCALE_ENG,
				true);
		
		if (callPlanItemList != null && !callPlanItemList.isEmpty()) {
			for (ItemDetailDTO callPlanItem : callPlanItemList) {
				itemDetailLtsList.add(createItemDetailLts(selectedBasket,
						callPlanItem,
						String.valueOf(itemDetailLtsList.size() + 1),
						LtsConstant.IO_IND_INSTALL));
			}
		}
	}
	
	private RemarkDetailLtsDTO[] getRemarkDetailLts(CreateServiceType createServiceType, OrderCaptureDTO orderCapture) {
		List<RemarkDetailLtsDTO> remarkDetailList = new ArrayList<RemarkDetailLtsDTO>();
		
		switch (createServiceType) {
		case CREATE_SRV_TYPE_UPGRADE:
		case CREATE_SRV_TYPE_RENEW_DEL:
		case CREATE_SRV_TYPE_RENEW_EYE:
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_DUPLEX_REMOVE:
			List<RemarkDetailLtsDTO> addonRemarkList = createAddonRemarkDetailLts(orderCapture);
			if (addonRemarkList != null && !addonRemarkList.isEmpty()) {
				remarkDetailList.addAll(addonRemarkList);	
			}
		default:
			break;
		}
		
		switch (createServiceType) {
		
		case CREATE_SRV_TYPE_UPGRADE:
		case CREATE_SRV_TYPE_RENEW_DEL:
		case CREATE_SRV_TYPE_RENEW_EYE:
			List<RemarkDetailLtsDTO> wqRemarkList = createWqRemarkDetailLts(orderCapture);
			if (wqRemarkList != null && !wqRemarkList.isEmpty()) {
				remarkDetailList.addAll(wqRemarkList);	
			}
			break;
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_DUPLEX_REMOVE:
		case CREATE_SRV_TYPE_NEW_2DEL:
			List<RemarkDetailLtsDTO> secDelRemarkList = createRemarkDetailLts(
					orderCapture.getLtsOtherVoiceServiceForm().getSecondDelWqRemark(),
					LtsConstant.REMARK_TYPE_2ND_DEL);  
			
			if (secDelRemarkList != null && !secDelRemarkList.isEmpty()) {
				remarkDetailList.addAll(secDelRemarkList);	
			}
		default:
			break;
		}
		
		if (remarkDetailList.isEmpty()) {
			return null;
		}
		return remarkDetailList.toArray(new RemarkDetailLtsDTO[remarkDetailList.size()]);
	}
	
	
	private String getActualDocId(CreateServiceType createServiceType, OrderCaptureDTO orderCapture) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_UPGRADE:
		case CREATE_SRV_TYPE_RENEW_DEL:
		case CREATE_SRV_TYPE_RENEW_EYE:
		case CREATE_SRV_TYPE_DUPLEX_NUM_CHANGE:
		case CREATE_SRV_TYPE_DUPLEX_NUM_NO_CHANGE:
			return orderCapture.getLtsCustomerIdentificationForm().getId();
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_NEW_2DEL:	
			return orderCapture.getLtsCustomerIdentificationForm().getSecDelId();
		default:
			return null;
		}
	}
	
	private String getActualDocType(CreateServiceType createServiceType, OrderCaptureDTO orderCapture) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_UPGRADE:
		case CREATE_SRV_TYPE_RENEW_DEL:
		case CREATE_SRV_TYPE_RENEW_EYE:	
		case CREATE_SRV_TYPE_DUPLEX_NUM_CHANGE:
		case CREATE_SRV_TYPE_DUPLEX_NUM_NO_CHANGE:
			return orderCapture.getLtsCustomerIdentificationForm().getDocType();
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_NEW_2DEL:
			return orderCapture.getLtsCustomerIdentificationForm().getSecDelDocType();
		default:
			return null;
		}
	}
	
	protected BillingAddressLtsDTO createRecontractTransferorBillingAddressLtsDTO(OrderCaptureDTO orderCapture){
		LtsRecontractFormDTO recontractForm = orderCapture.getLtsRecontractForm();
		BillingAddressLtsDTO billingAddressLts = new BillingAddressLtsDTO();
		String fullAddr = null;

		if (!recontractForm.isDeceasedCase()
				|| StringUtils.isBlank(orderCapture.getLtsRecontractForm().getNewBillingAddress())) {
			billingAddressLts.setAction(LtsConstant.BILL_ADDR_ACTION_EXISTING);
			return billingAddressLts;
		}
		
		billingAddressLts.setAction(LtsConstant.BILLING_ADDR_ACTION_NEW);
		billingAddressLts.setInstantUpdateInd("Y");
		fullAddr = recontractForm.getNewBillingAddress();
		
		if(StringUtils.isNotBlank(fullAddr)){
			String[] addrLines = fullAddr.split("\n");
			billingAddressLts.setAddrLine1(StringUtils.strip(addrLines[0]));
			billingAddressLts.setAddrLine2(addrLines.length > 1? StringUtils.strip(addrLines[1]) :null);
			billingAddressLts.setAddrLine3(addrLines.length > 2? StringUtils.strip(addrLines[2]) :null);
			billingAddressLts.setAddrLine4(addrLines.length > 3? StringUtils.strip(addrLines[3]) :null);
			billingAddressLts.setAddrLine5(addrLines.length > 4? StringUtils.strip(addrLines[4]) :null);
			billingAddressLts.setAddrLine6(addrLines.length > 5? StringUtils.strip(addrLines[5]) :null);
		}
		return billingAddressLts;
	}
	
	
	protected BillingAddressLtsDTO createBillingAddressLtsDTO(OrderCaptureDTO orderCapture, CreateServiceType createServiceType){

		LtsPaymentFormDTO paymentForm = orderCapture.getLtsPaymentForm();
		LtsAddressRolloutFormDTO addressRolloutForm  = orderCapture.getLtsAddressRolloutForm();
		BillingAddressLtsDTO billingAddressLts = new BillingAddressLtsDTO();
		
		switch (createServiceType) {
		case CREATE_SRV_TYPE_UPGRADE:
		case CREATE_SRV_TYPE_SIP_REMOVE:
		case CREATE_SRV_TYPE_DUPLEX_REMOVE:
		case CREATE_SRV_TYPE_DUPLEX_TO_2DEL:
		case CREATE_SRV_TYPE_RENEW_DEL:
		case CREATE_SRV_TYPE_RENEW_EYE:
			String fullAddr = null;

			if(addressRolloutForm.isExternalRelocate()){
				billingAddressLts.setAction(addressRolloutForm.getBaCaAction().getCode());
				billingAddressLts.setInstantUpdateInd(addressRolloutForm.isInstantUpdateBa()? "Y" : "N");
				if(addressRolloutForm.getBaCaAction() == BaCaActionType.DIFFERENT_FROM_NEW_OLD_IA){
					fullAddr = addressRolloutForm.getBillingAddress();
				}
			}else if(paymentForm != null && paymentForm.isAllowChangeBa() && paymentForm.isChangeBa()){
				billingAddressLts.setAction(BaCaActionType.DIFFERENT_FROM_NEW_OLD_IA.getCode());
				fullAddr = paymentForm.getBillingAddress();
				billingAddressLts.setInstantUpdateInd(paymentForm.isInstantUpdateBa()? "Y" : "N");
			}else{
				billingAddressLts.setAction(LtsConstant.BILL_ADDR_ACTION_EXISTING);
			}
			if(StringUtils.isNotBlank(fullAddr)){
				String[] addrLines = fullAddr.split("\n");
				billingAddressLts.setAddrLine1(StringUtils.strip(addrLines[0]));
				billingAddressLts.setAddrLine2(addrLines.length > 1? StringUtils.strip(addrLines[1]) :null);
				billingAddressLts.setAddrLine3(addrLines.length > 2? StringUtils.strip(addrLines[2]) :null);
				billingAddressLts.setAddrLine4(addrLines.length > 3? StringUtils.strip(addrLines[3]) :null);
				billingAddressLts.setAddrLine5(addrLines.length > 4? StringUtils.strip(addrLines[4]) :null);
				billingAddressLts.setAddrLine6(addrLines.length > 5? StringUtils.strip(addrLines[5]) :null);
			}
			return billingAddressLts;
		case CREATE_SRV_TYPE_NEW_2DEL:
			if(Boolean.TRUE.equals(orderCapture.getLtsOtherVoiceServiceForm().getSecondDelBaCaChange())){
				billingAddressLts.setAction(LtsConstant.BILL_ADDR_ACTION_IA);
			}else{
				billingAddressLts.setAction(LtsConstant.BILL_ADDR_ACTION_EXISTING);
			}
			return billingAddressLts;
		default:
			billingAddressLts.setAction(LtsConstant.BILL_ADDR_ACTION_EXISTING);
			return billingAddressLts;
		}
	}
	
	private RecontractLtsDTO createRecontractLtsDTO(OrderCaptureDTO orderCapture, CreateServiceType createServiceType){
		LtsRecontractFormDTO recontractForm = orderCapture.getLtsRecontractForm();
		
		if(! (orderCapture.getLtsMiscellaneousForm().isRecontract() && recontractForm != null)){
			return null;
		}			
		RecontractLtsDTO recontractLtsDTO = new RecontractLtsDTO();
		recontractLtsDTO.setAcctNum(getRecontractAccountNum(orderCapture));
		recontractLtsDTO.setContactNum(recontractForm.getMobileNum());
		recontractLtsDTO.setCustFirstName(recontractForm.getFirstName());
		recontractLtsDTO.setCustLastName(recontractForm.getLastName());
		recontractLtsDTO.setEmailAddr(recontractForm.getEmailAddr());
		recontractLtsDTO.setIdDocNum(recontractForm.getDocId());
		recontractLtsDTO.setIdDocType(recontractForm.getDocType());
		recontractLtsDTO.setRelationship(recontractForm.getRelationship());
		if(recontractForm.getCustDetailProfile() != null){
			recontractLtsDTO.setBlacklistInd(recontractForm.getCustDetailProfile().isBlacklistCustInd()? "Y": "N");
			recontractLtsDTO.setCustNum(recontractForm.getCustDetailProfile().getCustNum());
			recontractLtsDTO.setCompanyName(recontractForm.getCustDetailProfile().getCompanyName());
			recontractLtsDTO.setTitle(recontractForm.getCustDetailProfile().getTitle());
		}else{
			recontractLtsDTO.setTitle(recontractForm.getTitle());
		}
		recontractLtsDTO.setMobIddInd(recontractForm.getMobileIdd0060Service());
		recontractLtsDTO.setCallingCardInd(recontractForm.getGlobalCallCardService());
		recontractLtsDTO.setFixedIddInd(recontractForm.getIdd0060FixedFeePlan());
		recontractLtsDTO.setTransMode(recontractForm.getRecontractMode());
		recontractLtsDTO.setOptOut(recontractForm.isOptOut()? "Y" : "N");
		return recontractLtsDTO;
	}
	
	
	private void addOfferChangeDetails(OrderCaptureDTO orderCapture, List<ImsOfferDetailDTO> imsOfferDetailList) {
		
		
		
		String ltsFromProd = StringUtils.defaultIfEmpty(orderCapture.getLtsServiceProfile().getExistEyeType(), "DEL");
		String ltsToProd = LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType()) ? ltsFromProd : LtsConstant.LTS_PRODUCT_TYPE_EYE_3_A;
		String imsFromProd = this.getImsFromProductType(orderCapture);
		String imsToProd = this.getImsToProductType(orderCapture);
		String rentalRouter = LtsConstant.ROUTER_OPTION_SHARE_RENTAL_ROUTER.equals(orderCapture.getLtsModemArrangementForm().getRentalRouterInd()) ? "Y" : null;
		
		OfferDetailProfileLtsDTO[] profileImsOffers = null; 
		String mirror = null;
		FsaDetailDTO selectedFsaDetail = getSelectedFsaDetail(orderCapture);
		if (selectedFsaDetail != null) {
			profileImsOffers = selectedFsaDetail.getFsaProfile().getOffers();
			mirror = selectedFsaDetail.getExistMirrorInd();
		}
		
		
		List<String> offerIdList = new ArrayList<String>();
		
		if(ArrayUtils.isNotEmpty(profileImsOffers)){
			for (OfferDetailProfileLtsDTO offerDetail : profileImsOffers) {
				offerIdList.add(offerDetail.getOfferId());
			}
		}
	
		List<String[]> offerChangeList = offerChangeService.getOfferChangeList(ltsFromProd, ltsToProd, imsFromProd, imsToProd, mirror, offerIdList.toArray(new String[offerIdList.size()]), rentalRouter);
		if (offerChangeList != null && !offerChangeList.isEmpty()) {
			for (String[] offerChanges : offerChangeList) {
				if (ArrayUtils.isNotEmpty(offerChanges)) {
					ImsOfferDetailDTO imsOfferDetail = new ImsOfferDetailDTO();
					imsOfferDetail.setOfferId(offerChanges[0]);
					imsOfferDetail.setItemId(null);
					imsOfferDetail.setIoInd(offerChanges[1]);
	//				imsOfferDetail.setPenaltyWaiveInd(getPenaltyWaiveInd(imsProfileItemDetail.getItemDetail()));
	//				imsOfferDetail.setPenaltyHandling(getPenaltyHandling(imsProfileItemDetail.getItemDetail()));
					imsOfferDetail.setItemType(StringUtils.defaultIfEmpty(offerChanges[2], LtsConstant.ITEM_TYPE_OFFER_CHANGE));
					imsOfferDetail.setItemId(offerChanges[3]);
					imsOfferDetailList.add(imsOfferDetail);
				}
			}
		}
	}
	
	private ImsOfferDetailDTO[] createAllImsOfferDetails(OrderCaptureDTO orderCapture) {
		List<ImsOfferDetailDTO> imsOfferDetailList = new ArrayList<ImsOfferDetailDTO>();
		
		
		if (orderCapture.getLtsBasketVasDetailForm() == null) {
			return null;
		}
		
		if (orderCapture.getLtsBasketVasDetailForm().getImsProfileAutoOutItemList() != null 
				&& !orderCapture.getLtsBasketVasDetailForm().getImsProfileAutoOutItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO imsProfileAutoOutItem : orderCapture.getLtsBasketVasDetailForm().getImsProfileAutoOutItemList()) {
				createImsOfferDetail(imsProfileAutoOutItem, LtsConstant.IO_IND_OUT, imsOfferDetailList);
			}
		}
		
		if (orderCapture.getLtsBasketVasDetailForm().getImsProfileExistingItemList() != null 
				&& !orderCapture.getLtsBasketVasDetailForm().getImsProfileExistingItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO imsProfileExistingItem : orderCapture.getLtsBasketVasDetailForm().getImsProfileExistingItemList()) {
				if (imsProfileExistingItem.getItemDetail() != null 
						&& imsProfileExistingItem.getItemDetail().isSelected()
						&& StringUtils.isNotBlank(imsProfileExistingItem.getConditionEffEndDate())
						&& imsProfileExistingItem.getTpExpiredMonths() <= 0) {
					imsProfileExistingItem.getItemDetail().setPenaltyHandling(LtsConstant.OFFER_HANDLE_AUTO_GENERATE);
				}
				createImsOfferDetail(
						imsProfileExistingItem,
						imsProfileExistingItem.getItemDetail().isSelected() ? LtsConstant.IO_IND_OUT
								: LtsConstant.IO_IND_SPACE, imsOfferDetailList);
			}
		}
		
		
		if (orderCapture.getLtsBasketVasDetailForm().getImsProfileIngoreItemList() != null 
				&& !orderCapture.getLtsBasketVasDetailForm().getImsProfileIngoreItemList().isEmpty()) {
			for (ItemDetailProfileLtsDTO imsProfileIngoreItem : orderCapture.getLtsBasketVasDetailForm().getImsProfileIngoreItemList()) {
				createImsOfferDetail(imsProfileIngoreItem, LtsConstant.IO_IND_SPACE, imsOfferDetailList);
			}
		}
		
		addOfferChangeDetails(orderCapture, imsOfferDetailList);
		
		if (imsOfferDetailList.isEmpty()) {
			return null;
		}
		
		return imsOfferDetailList.toArray(new ImsOfferDetailDTO[imsOfferDetailList.size()]);
	}
	
	private void createImsOfferDetail(ItemDetailProfileLtsDTO imsProfileItemDetail, String ioInd, List<ImsOfferDetailDTO> imsOfferDetailList) {
		if (imsProfileItemDetail == null) {
			return;
		}
		
		if (ArrayUtils.isEmpty(imsProfileItemDetail.getOffers())) {
			return;
		}
		
		for (OfferDetailProfileLtsDTO profileOfferDetail : imsProfileItemDetail.getOffers()) {
			ImsOfferDetailDTO imsOfferDetail = new ImsOfferDetailDTO();
			imsOfferDetail.setOfferId(profileOfferDetail.getOfferId());
			imsOfferDetail.setItemId(imsProfileItemDetail.getItemId());
			imsOfferDetail.setIoInd(ioInd);
//			imsOfferDetail.setPenaltyWaiveInd(getPenaltyWaiveInd(imsProfileItemDetail.getItemDetail()));
			imsOfferDetail.setPenaltyHandling(getPenaltyHandling(imsProfileItemDetail.getItemDetail()));
			imsOfferDetail.setItemType(imsProfileItemDetail.getItemType());
			imsOfferDetailList.add(imsOfferDetail);
		}
	}

	private String getSubc2gBundleInd(OrderCaptureDTO orderCapture) {
		if (orderCapture.getSelectedBasket() != null) {
			return orderCapture.getSelectedBasket().getBundle2G();
		}
		return null;
	}
	
	
	private CustOptOutInfoLtsDTO createCustOptOutInfo(OrderCaptureDTO orderCapture){
		
		if(orderCapture.getLtsCustomerIdentificationForm() == null) {
			return null;
		}
		
		LtsCustomerIdentificationFormDTO custIdenForm = orderCapture.getLtsCustomerIdentificationForm(); 

		CustOptOutInfoLtsDTO custOptOutInfo = new CustOptOutInfoLtsDTO();
		custOptOutInfo.setOptInd(custIdenForm.getExistLtsPdpoStatus());
		custOptOutInfo.setUpdBomStatus(LtsConstant.PDPO_UPDATE_BOM_STATUS_INGORE);
		
		// Only allow change PDPO for Status is blank or 'OOA'
		if (StringUtils.isBlank(custIdenForm.getExistLtsPdpoStatus())
				|| LtsConstant.DATA_PRIVACY_OPT_IND_OOA_CD.equals(custIdenForm.getExistLtsPdpoStatus())) {
			
			String newOptInd = LtsSbOrderHelper.isOptOutAllMeans(custIdenForm) ? LtsConstant.DATA_PRIVACY_OPT_IND_OOA_CD : custIdenForm.getNewLtsPdpoStatus();
			
			if (!StringUtils.equals(newOptInd, custIdenForm.getExistLtsPdpoStatus())) {
				custOptOutInfo.setOptInd(newOptInd);
				custOptOutInfo.setEmail(custIdenForm.isLtsPdpoEmail() ? "Y" : "N");
				custOptOutInfo.setDirectMailing(custIdenForm.isLtsPdpoDirectMailing() ? "Y" : "N");
				custOptOutInfo.setOutbound(custIdenForm.isLtsPdpoOutbound() ? "Y" : "N");
				custOptOutInfo.setSms(custIdenForm.isLtsPdpoSms() ? "Y" : "N");
				custOptOutInfo.setWebBill(custIdenForm.isLtsPdpoWebBill() ? "Y" : "N");
				custOptOutInfo.setBm(custIdenForm.isLtsPdpoBm() ? "Y" : "N");
				custOptOutInfo.setBillInsert(custIdenForm.isLtsPdpoBillInsert() ? "Y" : "N");
				custOptOutInfo.setSmsEye(custIdenForm.isLtsPdpoSmsEye() ? "Y" : "N");
				custOptOutInfo.setCdOutdial(custIdenForm.isLtsPdpoCdOutdial() ? "Y" : "N");
				custOptOutInfo.setUpdBomStatus(LtsConstant.PDPO_UPDATE_BOM_STATUS_PENDING);	
			}
		}
		return custOptOutInfo;
	}
	
	
	public void setContactInfo(SbOrderDTO pSbOrder, OrderCaptureDTO pOrderCapture){
		
		ContactLtsDTO contactInfo = new ContactLtsDTO();
		String contactName = null;
		String title = null;
		String idDocType = null;
		String idDocNum = null;
		CustomerDetailProfileLtsDTO targetCustomer = pOrderCapture.getLtsServiceProfile().getPrimaryCust();
		
		if (LtsSbOrderHelper.isRecontractOrder(pOrderCapture)) {
			idDocType = pOrderCapture.getLtsRecontractForm().getDocType();
			idDocNum = pOrderCapture.getLtsRecontractForm().getDocId();
			if (LtsConstant.DOC_TYPE_HKBR.equals(idDocType)) {
				contactName = pOrderCapture.getLtsRecontractForm().getCompanyName();
			}
			else {
				title = pOrderCapture.getLtsRecontractForm().getTitle();
				contactName = pOrderCapture.getLtsRecontractForm().getLastName() + " " + pOrderCapture.getLtsRecontractForm().getFirstName();
			}
		}
		else {
			idDocType = targetCustomer.getDocType();
			idDocNum = targetCustomer.getDocNum();
			if (LtsConstant.DOC_TYPE_HKBR.equals(idDocType)) {
				contactName = targetCustomer.getCompanyName();
			}
			else {
				contactName = targetCustomer.getLastName() + " " + targetCustomer.getFirstName();
				title = targetCustomer.getTitle();
			}
		}
		
		contactInfo.setTitle(title);
		contactInfo.setContactName(contactName);
		contactInfo.setIdDocType(idDocType);
		contactInfo.setIdDocNum(idDocNum);
		
		if (targetCustomer != null) {
			contactInfo.setCustNo(targetCustomer.getCustNum());	
		}
		
		if (pOrderCapture.getLtsCustomerIdentificationForm() != null) {
			
			if (StringUtils.isNotBlank(pOrderCapture.getLtsCustomerIdentificationForm().getCustomerContactMobileNum())) {
				contactInfo.setContactPhone(pOrderCapture.getLtsCustomerIdentificationForm().getCustomerContactMobileNum());	
			}
			else {
				contactInfo.setContactPhone(pOrderCapture.getLtsCustomerIdentificationForm().getCustomerContactFixLineNum());
			}
			contactInfo.setEmailAddr(pOrderCapture.getLtsCustomerIdentificationForm().getCustomerContactEmail());
			contactInfo.setContactMobile(pOrderCapture.getLtsCustomerIdentificationForm().getCustomerContactMobileNum());
		    contactInfo.setContactFixedLine(pOrderCapture.getLtsCustomerIdentificationForm().getCustomerContactFixLineNum());
		    contactInfo.setOldContactMobile(pOrderCapture.getLtsCustomerIdentificationForm().getCustomerOrgContactMobileNum());
		    contactInfo.setOldEmailAddr(pOrderCapture.getLtsCustomerIdentificationForm().getCustomerOrgContactEmail());
		    contactInfo.setOldContactMobileDate(pOrderCapture.getLtsCustomerIdentificationForm().getCustomerOrgContactMobileNumDate());
		    contactInfo.setOldEmailAddrDate(pOrderCapture.getLtsCustomerIdentificationForm().getCustomerOrgContactEmailDate());
		    if(StringUtils.equals("Y", pOrderCapture.getLtsCustomerIdentificationForm().getUpdateContactMsgPrompted())){
		    	contactInfo.setContactUpdAlert("Y");
		    }else{
		    	contactInfo.setContactUpdAlert("N");
		    }
		    
		}

		pSbOrder.setContact(contactInfo);
	}
	
	private ImsL2JobDTO createImsL2Job(String l2Cd, String l2Qty, String ftInd, String actInd) {
		ImsL2JobDTO imsL2Job = new ImsL2JobDTO();
		imsL2Job.setActInd(actInd);
		imsL2Job.setL2Cd(l2Cd);
		imsL2Job.setL2Oty(l2Qty);
		imsL2Job.setFtInd(ftInd);
		return imsL2Job;
	}
	
	private ImsL2JobDTO[] createImsL2job(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket) {

		List<ImsL2JobDTO> imsL2JobList = new ArrayList<ImsL2JobDTO>();
		
		try {
		
			// L2 Job for Lose Modem 
			if(orderCapture.getLtsModemArrangementForm().isLostModem()){
				imsL2JobList.add(createImsL2Job(LtsConstant.LOST_MODEM_L2_JOB_CODE, "1", "T", LtsConstant.IO_IND_INSTALL));
			}
			
			// L2 Job for Share Rental Router
			if (StringUtils.equals("Y", getShareRentalRouterInd(orderCapture))) {
				CodeLkupDAO rentalRouterL2JobCodeLkupDao = rentalRouterL2JobCacheService.getCodeLkupDAO();
				if (rentalRouterL2JobCodeLkupDao != null 
						&& ArrayUtils.isNotEmpty(rentalRouterL2JobCodeLkupDao.getCodeLkup())) {
					for (LookupItemDTO lookupItem : rentalRouterL2JobCodeLkupDao.getCodeLkup()) {
						imsL2JobList.add(createImsL2Job(lookupItem.getItemKey(), "1", (String) lookupItem.getItemValue(), LtsConstant.IO_IND_INSTALL));
					}
				}	
			}
			
			// L2 Job for PON BRM no stock
			if (LtsConstant.TECHNOLOGY_PON.equals(orderCapture.getModemTechnologyAissgn().getTechnology())
					&& (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType()) 
							|| ltsOfferService.isRenewalWithNewDevice(orderCapture))
					&& (StringUtils.isBlank(getShareRentalRouterInd(orderCapture)) 
							|| StringUtils.equals("N", getShareRentalRouterInd(orderCapture)))
					&& !StringUtils.equals("Y", getBrmWifiInd(orderCapture, getSelectedFsaDetail(orderCapture)))) {
				// BRM for standalone TV
				if (LtsConstant.IMS_PRODUCT_TYPE_HD.equals(getImsToProductType(orderCapture))
						|| LtsConstant.IMS_PRODUCT_TYPE_SD.equals(getImsToProductType(orderCapture))) {
					CodeLkupDAO brmViL2JobCodeLkupDao = brmViL2JobCacheService.getCodeLkupDAO();
					if (brmViL2JobCodeLkupDao != null 
							&& ArrayUtils.isNotEmpty(brmViL2JobCodeLkupDao.getCodeLkup())) {
						for (LookupItemDTO lookupItem : brmViL2JobCodeLkupDao.getCodeLkup()) {
							imsL2JobList.add(createImsL2Job(lookupItem.getItemKey(), "1", (String) lookupItem.getItemValue(), LtsConstant.IO_IND_INSTALL));
						}
					}
				}// BRM for PCD
				else if (StringUtils.contains(getImsToProductType(orderCapture), LtsConstant.IMS_PRODUCT_TYPE_PCD)) {
					CodeLkupDAO brmViL2JobCodeLkupDao = brmViL2JobCacheService.getCodeLkupDAO();
					if (brmViL2JobCodeLkupDao != null 
							&& ArrayUtils.isNotEmpty(brmViL2JobCodeLkupDao.getCodeLkup())) {
						for (LookupItemDTO lookupItem : brmViL2JobCodeLkupDao.getCodeLkup()) {
							imsL2JobList.add(createImsL2Job(lookupItem.getItemKey(), "1", (String) lookupItem.getItemValue(), LtsConstant.IO_IND_INSTALL));
						}
					}
				}
				else {
					CodeLkupDAO brmL2JobCodeLkupDao = brmL2JobCacheService.getCodeLkupDAO();
					if (brmL2JobCodeLkupDao != null 
							&& ArrayUtils.isNotEmpty(brmL2JobCodeLkupDao.getCodeLkup())) {
						for (LookupItemDTO lookupItem : brmL2JobCodeLkupDao.getCodeLkup()) {
							imsL2JobList.add(createImsL2Job(lookupItem.getItemKey(), "1", (String) lookupItem.getItemValue(), LtsConstant.IO_IND_INSTALL));
						}
					}
				}
			}
			
			
			//  1. eye renewal with new device order
			//  2. Share with BRM modem
		    //  3. Involved line type change A to V / P
			if (ltsOfferService.isRenewalWithNewDevice(orderCapture)
					&& (StringUtils.isBlank(getShareRentalRouterInd(orderCapture)) ||
							StringUtils.equals("N", getShareRentalRouterInd(orderCapture)))
					&& orderCapture.getModemTechnologyAissgn().isAutoUpgraded()) {
				
				CodeLkupDAO brmTechChangeL2JobCodeLkupDao = brmTechChangeL2JobCacheService.getCodeLkupDAO();
				
				if (brmTechChangeL2JobCodeLkupDao != null 
						&& ArrayUtils.isNotEmpty(brmTechChangeL2JobCodeLkupDao.getCodeLkup())) {
					for (LookupItemDTO lookupItem : brmTechChangeL2JobCodeLkupDao.getCodeLkup()) {
						imsL2JobList.add(createImsL2Job(lookupItem.getItemKey(), "1", (String) lookupItem.getItemValue(), LtsConstant.IO_IND_INSTALL));
					}
				}	
			}
			
			// If OS Type = IOS
			if (selectedBasket != null) {
				if(LtsConstant.OS_TYPE_IOS.equals(selectedBasket.getOsType())) {
					CodeLkupDAO osTypeIosL2JobCodeLkupDao = osTypeIosL2JobCacheService.getCodeLkupDAO();
					if (osTypeIosL2JobCodeLkupDao != null 
							&& ArrayUtils.isNotEmpty(osTypeIosL2JobCodeLkupDao.getCodeLkup())) {
						for (LookupItemDTO lookupItem : osTypeIosL2JobCodeLkupDao.getCodeLkup()) {
							imsL2JobList.add(createImsL2Job(lookupItem.getItemKey(), "1", (String) lookupItem.getItemValue(), LtsConstant.IO_IND_INSTALL));
						}
					}	
				}
			}
		
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(ExceptionUtils.getFullStackTrace(e));
		}
		
		if (imsL2JobList.size() == 0) {
			return null;
		}
		
		return imsL2JobList.toArray(new ImsL2JobDTO[imsL2JobList.size()]);
	}
	
	//TODO
	protected OrderAttbDTO[] createOrderAttbs(CreateServiceType createServiceType, OrderCaptureDTO orderCapture){
		List<OrderAttbDTO> attbList = new ArrayList<OrderAttbDTO>();

		switch(createServiceType){
			case CREATE_SRV_TYPE_UPGRADE:				
				LtsBasketServiceFormDTO basketSrvForm = orderCapture.getLtsBasketServiceForm();
				if(basketSrvForm != null && basketSrvForm.isAllowSelfPickup()){
//					if(recheckForceFieldService(orderCapture)){
					if(ltsCommonService.recheckForceFieldService(orderCapture)){
						attbList.add(createOrderAttb(UpgradeOrderAttb.ALLOW_SELF_PICKUP_DEVICE, "N"));
					}else{
						attbList.add(createOrderAttb(UpgradeOrderAttb.ALLOW_SELF_PICKUP_DEVICE, "Y"));
					}
				}
				break;
		}
		
		if(attbList != null && attbList.size() > 0){
			return attbList.toArray(new OrderAttbDTO[0]);
		}
		
		return null;
	}
	
	protected OrderAttbDTO createOrderAttb(UpgradeOrderAttb attb,  String attbValue){
		return createOrderAttb(attb.getAttbId(), attb.name(), attbValue);
	}
	
	protected OrderAttbDTO createOrderAttb(String attbId, String attbName, String attbValue){
		OrderAttbDTO attb = new OrderAttbDTO();
		attb.setAttbId(attbId);
		attb.setAttbName(attbName);
		attb.setAttbValue(attbValue);
		return attb;
	}
	
}
