package com.bomwebportal.lts.service.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateBillingInfoFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateServiceSelectionFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateSupportDocFormDTO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.Service0060DetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceCallPlanDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.order.ThirdPartyDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.Idd0060ProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.IddCallPlanProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupMemberProfileDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsRetrieveFsaService;
import com.bomwebportal.lts.service.LtsSalesInfoService;
import com.bomwebportal.lts.service.OfferChangeService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.disconnect.LtsTerminateConstant;

public class DisconnectOrderCreateServiceImpl implements DisconnectOrderCreateService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected LtsAppointmentService ltsAppointmentService;
	protected LtsSalesInfoService ltsSalesInfoService;
	protected LtsRetrieveFsaService ltsRetrieveFsaService;
	protected OfferChangeService offerChangeService;
	
	
	public OfferChangeService getOfferChangeService() {
		return offerChangeService;
	}

	public void setOfferChangeService(OfferChangeService offerChangeService) {
		this.offerChangeService = offerChangeService;
	}
	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}
	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}
	public LtsSalesInfoService getLtsSalesInfoService() {
		return ltsSalesInfoService;
	}
	public void setLtsSalesInfoService(LtsSalesInfoService ltsSalesInfoService) {
		this.ltsSalesInfoService = ltsSalesInfoService;
	}
	public LtsRetrieveFsaService getLtsRetrieveFsaService() {
		return ltsRetrieveFsaService;
	}
	public void setLtsRetrieveFsaService(LtsRetrieveFsaService ltsRetrieveFsaService) {
		this.ltsRetrieveFsaService = ltsRetrieveFsaService;
	}
	
	public enum CreateServiceType {
		CREATE_SRV_TYPE_DISCONNECT,
		CREATE_SRV_TYPE_FSA,
		CREATE_SRV_TYPE_SIP_REMOVE;
	}
	
	public SbOrderDTO createTerminateSbOrder(TerminateOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		
		Map<String, AccountDetailLtsDTO> accountDetailLtsMap = new HashMap<String, AccountDetailLtsDTO>();
		Map<String, CustomerDetailLtsDTO> customerDetailLtsMap = new HashMap<String, CustomerDetailLtsDTO>();
		
		SbOrderDTO sbOrder = new SbOrderDTO();
		
		setLastServiceInd(orderCapture.getLtsTerminateServiceSelectionForm(),sbOrder);
		
		sbOrder.setOrderType(LtsConstant.ORDER_TYPE_SB_DISCONNECT);
		sbOrder.setAppDate(orderCapture.getLtsTerminateServiceSelectionForm().getAppDate());
		sbOrder.setAddress(createTerminateAddressDetailLts(orderCapture));
		sbOrder.setLob(LtsConstant.LOB_LTS);
		sbOrder.setSrv0060Dtls(createService0060DetailLts(orderCapture));
		sbOrder.setSrvDtls(createTerminateServiceDetail(orderCapture, accountDetailLtsMap, customerDetailLtsMap, sbOrder));
		sbOrder.setAccounts(accountDetailLtsMap.values().toArray(new AccountDetailLtsDTO[accountDetailLtsMap.size()]));
		sbOrder.setCustomers(customerDetailLtsMap.values().toArray(new CustomerDetailLtsDTO[customerDetailLtsMap.size()]));
		sbOrder.setBackDateInd(isBackDateOrder(orderCapture)? "Y" : null);
		setTerminateOrderSalesInfo(sbOrder, orderCapture, bomSalesUser);
		setTerminateOrderSupportDoc(sbOrder, orderCapture, bomSalesUser);
		sbOrder.setSrvReqDate(orderCapture.getLtsTerminateAppointmentForm().getAppntDate());
		return sbOrder;
	}
	
	private void setLastServiceInd(LtsTerminateServiceSelectionFormDTO pForm, SbOrderDTO pSbOrder){
		if (pForm != null) {
			if (pForm.getIddCallPlanProfileList() != null) {
				for (IddCallPlanProfileLtsDTO iddCallPlanProfileLts : pForm.getIddCallPlanProfileList()) {
					if (StringUtils.equals(iddCallPlanProfileLts.getAction(),LtsBackendConstant.IDD_ACTION_RETAIN)) {
						pSbOrder.setLastServiceInd("N");
						break;
					}
				}
			}
			if (pForm.getIdd0060ProfileList() != null){
			for (Idd0060ProfileLtsDTO idd0060ProfileLts : pForm.getIdd0060ProfileList()) {
				if (StringUtils.equals(idd0060ProfileLts.getAction(),LtsBackendConstant.IDD_ACTION_RETAIN)) {
					pSbOrder.setLastServiceInd("N");
					break;
				}
			}}

			if (LtsTerminateConstant.CALLING_CARD_HANDLING_RETAIN.equals(pForm.getCallingCardDetailsHandling())) {
				pSbOrder.setLastServiceInd("N");
			}

			if (!"N".equals(pSbOrder.getLastServiceInd())) {
				pSbOrder.setLastServiceInd("Y");
			}
		}
	}
	
	private Service0060DetailLtsDTO[] createService0060DetailLts(TerminateOrderCaptureDTO orderCapture) {
		
		LtsTerminateServiceSelectionFormDTO form = orderCapture.getLtsTerminateServiceSelectionForm();
		if (form == null
				|| form.getIdd0060ProfileList() == null
				|| form.getIdd0060ProfileList().isEmpty()) {
			return null;
		}
		
		List<Service0060DetailLtsDTO> srv0060DtlList = new ArrayList<Service0060DetailLtsDTO>();
		Service0060DetailLtsDTO srv0060Dtl = null;
		for (Idd0060ProfileLtsDTO idd0060ProfileLts : form.getIdd0060ProfileList()) {
			srv0060Dtl = new Service0060DetailLtsDTO();
			srv0060Dtl.setSrvNum(idd0060ProfileLts.getSrvNum());
			srv0060Dtl.setDatCd(idd0060ProfileLts.getDatCode());
			srv0060Dtl.setTypeOfSrv(idd0060ProfileLts.getSrvType());
			srv0060Dtl.setIoInd(LtsBackendConstant.IDD_ACTION_REMOVE.equals(idd0060ProfileLts.getAction()) ? LtsConstant.IO_IND_OUT : LtsConstant.IO_IND_SPACE);
			srv0060DtlList.add(srv0060Dtl);
		}
		
		
		return srv0060DtlList.toArray(new Service0060DetailLtsDTO[srv0060DtlList.size()]);
	}

	protected AddressDetailLtsDTO createTerminateAddressDetailLts (TerminateOrderCaptureDTO orderCapture) {

		AddressDetailProfileLtsDTO profileAddress = orderCapture.getLtsServiceProfile().getAddress();
		
		AddressDetailLtsDTO addressDetailLts = new AddressDetailLtsDTO();
		
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
		addressDetailLts.setAddrUsage("IA");
		
//		addressDetailLts.setBlacklistInd(orderCapture.getNewAddressRollout().isImsAddressBlacklist() ? "Y" : null);
//		addressDetailLts.setLtsBlacklistInd(orderCapture.getNewAddressRollout().isLtsAddressBlacklist() ? "Y" : null);
		
		return addressDetailLts;
	}
	
	protected AllOrdDocAssgnLtsDTO[] createTerminateAllOrdDocAssgnLts (TerminateOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		
		LtsTerminateSupportDocFormDTO supportDocForm = orderCapture.getLtsTerminateSupportDocForm();
		
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
	
	protected void setTerminateOrderSupportDoc(SbOrderDTO pSbOrder, TerminateOrderCaptureDTO pOrderCapture, BomSalesUserDTO pBomSalesUser) {
		if (pOrderCapture.getLtsTerminateSupportDocForm() != null) {
			pSbOrder.setDisMode(pOrderCapture.getLtsTerminateSupportDocForm().getDistributionMode());
			pSbOrder.setEsigEmailLang(pOrderCapture.getLtsTerminateSupportDocForm().getDistributeLang());	
			pSbOrder.setCollectMethod(pOrderCapture.getLtsTerminateSupportDocForm().getCollectMethod());
			pSbOrder.setAllOrdDocAssgns(createTerminateAllOrdDocAssgnLts(pOrderCapture, pBomSalesUser));
			
			if (StringUtils.equals(LtsConstant.DISTRIBUTE_MODE_ESIGNATURE, 
					pOrderCapture.getLtsTerminateSupportDocForm().getDistributionMode())) {
					pSbOrder.setSmsNo(pOrderCapture.getLtsTerminateSupportDocForm().getDistributeSms());
			}
		}
	}
	
	protected void setTerminateOrderSalesInfo(SbOrderDTO pSbOrder, TerminateOrderCaptureDTO pOrderCapture, BomSalesUserDTO bomSalesUser) {
		if (pOrderCapture.getLtsSalesInfoForm() != null) {
			if(pOrderCapture.isChannelCs() || pOrderCapture.isChannelPremier()){
				pSbOrder.setSalesChannel(pOrderCapture.getLtsSalesInfoForm().getImsApplicationMethod());
				pSbOrder.setSalesTeam(pOrderCapture.getLtsSalesInfoForm().getImsSource());
			}else{
				pSbOrder.setSalesChannel(pOrderCapture.getLtsSalesInfoForm().getSalesChannel());
				pSbOrder.setSalesTeam(pOrderCapture.getLtsSalesInfoForm().getSalesTeam());
			}
			
			pSbOrder.setSalesContactNum(pOrderCapture.getLtsSalesInfoForm().getSalesContact());
			pSbOrder.setStaffNum(pOrderCapture.getLtsSalesInfoForm().getStaffId());
			pSbOrder.setSalesCd(pOrderCapture.getLtsSalesInfoForm().getSalesCode());

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
		}
	}
	
	protected ServiceDetailDTO[] createTerminateServiceDetail(TerminateOrderCaptureDTO orderCapture,
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap,
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap, SbOrderDTO pSbOrder) {
		List<ServiceDetailDTO> serviceDetailList = new ArrayList<ServiceDetailDTO>();
		
		ServiceDetailDTO disconnectService = createTerminateServiceDetailLts(orderCapture, accountDetailLtsMap, customerDetailLtsMap, pSbOrder);
		if (disconnectService != null) {
			serviceDetailList.add(disconnectService);
		}
		
		ServiceDetailDTO sipRemoveService = createSipRemoveService(orderCapture, accountDetailLtsMap, customerDetailLtsMap);
		if (sipRemoveService != null) {
			serviceDetailList.add(sipRemoveService);
		}
		
		ServiceDetailOtherLtsDTO fsaServiceDetail = createTerminateServiceDetailOther(orderCapture, disconnectService.getSrvNum());
		if (fsaServiceDetail != null) {
			serviceDetailList.add(fsaServiceDetail);
		}
		
		return serviceDetailList.toArray(new ServiceDetailDTO[serviceDetailList.size()]);	
	}
	
	protected ServiceDetailDTO createSipRemoveService(TerminateOrderCaptureDTO pOrderCapture,
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		ServiceDetailProfileLtsDTO serviceProfile = pOrderCapture.getLtsServiceProfile();
		
		if (!LtsSbOrderHelper.isExistingEyeProfile(serviceProfile)) {
			return null;
		}
		
		ServiceGroupMemberProfileDTO[] profileGroupMembers = serviceProfile.getSrvGrp().getGrpMems();
		
		if (ArrayUtils.isEmpty(profileGroupMembers) || profileGroupMembers.length <= 2) {
			return null;
		}
		
		for (ServiceGroupMemberProfileDTO profileGroupMember : profileGroupMembers) {
			if (LtsConstant.DAT_CD_NCR.equals(profileGroupMember.getDatCd())) {
				return createTerminateServiceDetailLts(CreateServiceType.CREATE_SRV_TYPE_SIP_REMOVE, serviceProfile,
						pOrderCapture, accountDetailLtsMap, customerDetailLtsMap);		
			}
		}
		return null;
	}
	
	protected ServiceDetailOtherLtsDTO createTerminateServiceDetailOther(TerminateOrderCaptureDTO orderCapture, String upgradeServiceNum) {
		
		FsaServiceDetailOutputDTO fsaProfile=orderCapture.getLtsServiceProfile().getEyeFsaProfile();
		if(fsaProfile == null) {
			return null;
		}
		ServiceDetailOtherLtsDTO serviceDetailOtherLts = new ServiceDetailOtherLtsDTO();
		serviceDetailOtherLts.setAppointmentDtl(createTerminateAppointmentDetailLts(CreateServiceType.CREATE_SRV_TYPE_FSA, orderCapture));
		serviceDetailOtherLts.setRelatedSbOrderId(orderCapture.getImsPendingOrder() != null ? orderCapture.getImsPendingOrder().getOrderId() : null);
//		serviceDetailOtherLts.setSuggestSrd(getSuggestedTerminateSrd(orderCapture));
		String imsOrderType = getTerminateImsOrderType(orderCapture);
		serviceDetailOtherLts.setOrderType(imsOrderType);
		serviceDetailOtherLts.setTypeOfSrv(LtsConstant.SERVICE_TYPE_IMS);
		serviceDetailOtherLts.setLoginId(fsaProfile.getLoginID());
		serviceDetailOtherLts.setTenure(String.valueOf(orderCapture.getLtsServiceProfile().getImsTenure()));
		serviceDetailOtherLts.setOfferDtls(createDisconnectAllImsOfferDetails(orderCapture));
		
		// 20160310 Force Field Visit while IMS order type is C 
		serviceDetailOtherLts.setForceFieldVisitInd(LtsConstant.ORDER_TYPE_CHANGE.equals(imsOrderType) ? "Y" : orderCapture.getLtsTerminateAppointmentForm().isFieldVisitRequired()?"Y":null);
		
		FsaServiceType imsSrvType = ltsRetrieveFsaService.getExistSrvType(orderCapture.getLtsServiceProfile().getEyeFsaProfile());

		serviceDetailOtherLts.setCcServiceId(String.valueOf(fsaProfile.getFsa()));
		
		if(FsaServiceType.WG==imsSrvType) {
			serviceDetailOtherLts.setFromProd(LtsConstant.IMS_PRODUCT_TYPE_WALL_GARDEN);
			serviceDetailOtherLts.setExistSrvTypeCd(FsaServiceType.WG.getDesc());
			serviceDetailOtherLts.setNewSrvTypeCd(FsaServiceType.WG.getDesc());
			serviceDetailOtherLts.setToProd(LtsConstant.LTS_SRV_TYPE_REMOVE);
		}
		else{
			serviceDetailOtherLts.setFromProd(fsaProfile.getSrvType());
			serviceDetailOtherLts.setExistSrvTypeCd(fsaProfile.getExistingSrv());
			serviceDetailOtherLts.setNewSrvTypeCd(fsaProfile.getExistingSrv());
			serviceDetailOtherLts.setToProd(fsaProfile.getSrvType());
		}
		
		serviceDetailOtherLts.setExistBandwidth(fsaProfile.getBandwidth());
		serviceDetailOtherLts.setPendingOcid(fsaProfile.getPendingOcid());
		serviceDetailOtherLts.setPendingOcidSrd(fsaProfile.getPendingOrderSrd());
		serviceDetailOtherLts.setSrvNum(String.valueOf(fsaProfile.getFsa()));
		serviceDetailOtherLts.setNewBandwidth(fsaProfile.getBandwidth());
		serviceDetailOtherLts.setExistModem(fsaProfile.getExistModem());
		serviceDetailOtherLts.setExistTechnology(fsaProfile.getTechnology());
		serviceDetailOtherLts.setExistMirrorInd(fsaProfile.getExistMirrorInd());
		if (StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_WG)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_PCD)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_PCD_SD)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_PCD_HD)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_SD)
				|| StringUtils.equals(serviceDetailOtherLts.getSrvTypeCdAction(), LtsConstant.SRV_ACTION_TYPE_CD_NEW_HD)) {
			serviceDetailOtherLts.setExistModem(LtsConstant.EXIST_MODEM_TYPE_NIL);
		}
		serviceDetailOtherLts.setTerminatePcd(orderCapture.getLtsTerminateServiceSelectionForm().isRemoveBoardband()?"Y":"N");
		serviceDetailOtherLts.setTerminateTv(orderCapture.getLtsTerminateServiceSelectionForm().isRemoveNowtv()?"Y":"N");
		if(orderCapture.getLtsTerminateServiceSelectionForm().isLostModem()){
			serviceDetailOtherLts.setLostModem(orderCapture.getLtsTerminateServiceSelectionForm().getLostModemUsageInd());	
		}
		
		return serviceDetailOtherLts;
	}

	protected ServiceDetailDTO createTerminateServiceDetailLts(TerminateOrderCaptureDTO orderCapture,
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap, Map<String, CustomerDetailLtsDTO> customerDetailLtsMap, SbOrderDTO pSbOrder) {
		
		List<ItemDetailLtsDTO> itemDetailLtsList = new ArrayList<ItemDetailLtsDTO>();
		ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getLtsServiceProfile();
		
		ServiceDetailLtsDTO termService = createTerminateServiceDetailLts(
				CreateServiceType.CREATE_SRV_TYPE_DISCONNECT, serviceProfile,
				orderCapture, accountDetailLtsMap, customerDetailLtsMap);

		termService.setPendingOcid(serviceProfile.getPendingOcid());
		termService.setPendingOcidSrd(serviceProfile.getPendingOcSrd());
		termService.setItemDtls(createAllItemDetailLts(orderCapture.getLtsTerminateServiceSelectionForm(), itemDetailLtsList, orderCapture.getLtsTerminateAppointmentForm().getAppntDate()));
		termService.setSrvCallPlanDtls(createServiceCallPlanDetailLts(orderCapture, pSbOrder));
		return termService;
	}
	
	private AccountServiceAssignLtsDTO[] createAccountServiceAssigns(CreateServiceType createServiceType, TerminateOrderCaptureDTO orderCapture,
			ServiceDetailProfileLtsDTO serviceProfile, Map<String, AccountDetailLtsDTO> accountDetailLtsMap,
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		List<AccountServiceAssignLtsDTO> accountServiceAssignList = new ArrayList<AccountServiceAssignLtsDTO>();
		AccountServiceAssignLtsDTO profileSrvAccountAssign = new AccountServiceAssignLtsDTO();
		profileSrvAccountAssign.setAccount(createTerminateAccountDetailLts(createServiceType, orderCapture, serviceProfile, accountDetailLtsMap, customerDetailLtsMap));
		profileSrvAccountAssign.setAction(LtsConstant.IO_IND_OUT);
		profileSrvAccountAssign.setChrgType(LtsConstant.ACCOUNT_CHARGE_TYPE_R);
		accountServiceAssignList.add(profileSrvAccountAssign);
		return accountServiceAssignList.toArray(new AccountServiceAssignLtsDTO[accountServiceAssignList.size()]);
	}
	
	
	protected ServiceDetailLtsDTO createTerminateServiceDetailLts(CreateServiceType createServiceType, ServiceDetailProfileLtsDTO serviceProfile,
			TerminateOrderCaptureDTO orderCapture, Map<String, AccountDetailLtsDTO> accountDetailLtsMap,
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		String serviceNum = getTerminateServiceNum(createServiceType, orderCapture, serviceProfile);
		
		ServiceDetailLtsDTO serviceDetailLts = new ServiceDetailLtsDTO();		
		serviceDetailLts.setAccounts(createAccountServiceAssigns(createServiceType, orderCapture, serviceProfile, accountDetailLtsMap, customerDetailLtsMap));
		serviceDetailLts.setAppointmentDtl(createTerminateAppointmentDetailLts(createServiceType, orderCapture));
		
		serviceDetailLts.setOrderType(LtsConstant.ORDER_TYPE_DISCONNECT);
		serviceDetailLts.setSrvNum(StringUtils.leftPad(serviceNum, 12, "0"));
		serviceDetailLts.setTypeOfSrv(LtsConstant.SERVICE_TYPE_TEL);
		serviceDetailLts.setFromProd(getLtsTerminateFromProductType(serviceProfile));
		serviceDetailLts.setToProd(LtsConstant.LTS_SRV_TYPE_REMOVE);
		serviceDetailLts.setFromSrvType(getLtsTerminateFromSrvType(createServiceType, serviceNum, orderCapture));
		serviceDetailLts.setToSrvType(LtsConstant.LTS_SRV_TYPE_REMOVE);
		serviceDetailLts.setRemarks(getTerminateRemarkDetailLts(createServiceType, orderCapture));
		serviceDetailLts.setActualDocType(orderCapture.getLtsServiceProfile().getPrimaryCust().getDocType());
		serviceDetailLts.setActualDocId(orderCapture.getLtsServiceProfile().getPrimaryCust().getDocNum());
		String imsOrderType = getTerminateImsOrderType(orderCapture);
		
		serviceDetailLts.setForceFieldVisitInd(LtsConstant.ORDER_TYPE_CHANGE.equals(imsOrderType) ? "Y" : orderCapture.getLtsTerminateAppointmentForm().isFieldVisitRequired()?"Y":null);
		
		if (LtsSbOrderHelper.isDeceasedCase(orderCapture.getLtsTerminateServiceSelectionForm())) {
			serviceDetailLts.setDeceaseType(orderCapture.getLtsTerminateServiceSelectionForm().getDeceasedType());	
		}
		
		if(orderCapture.getLtsTerminateServiceSelectionForm().isThirdParty()) {
			ThirdPartyDetailLtsDTO thirdPartyDetail = createTerminateThirdPartyDetailLts(createServiceType, orderCapture);
			serviceDetailLts.setThirdPartyAppln("Y");
			serviceDetailLts.setThirdPartyDtl(thirdPartyDetail);
		}

		if (serviceProfile != null) {
			serviceDetailLts.setCcServiceId(getCcServiceId(createServiceType, serviceProfile));
			serviceDetailLts.setCcServiceMemNum(getCcServiceMemNum(createServiceType, serviceProfile));
			serviceDetailLts.setDatCd(serviceProfile.getDatCd());
			serviceDetailLts.setDuplexInd(getDuplexInd(serviceNum, serviceProfile));
			serviceDetailLts.setTenure(String.valueOf(serviceProfile.getLtsTenure()));
			serviceDetailLts.setTwoNInd(getTwoNInd(serviceNum, serviceProfile));
			serviceDetailLts.setSharedBsnInd(serviceProfile.isSharedBsn() ? "Y" : null);
			setDisconnectReason(serviceDetailLts,orderCapture);
			setDForm(serviceDetailLts,orderCapture);
			setFchSerial(serviceDetailLts,orderCapture);
			setCollectionAddress(serviceDetailLts,orderCapture);
			setDiscCallingCard(serviceDetailLts,orderCapture);
			setLostEquipmentHandling(serviceDetailLts,orderCapture);
		}
		return serviceDetailLts;
	}
	
	protected void setDisconnectReason(ServiceDetailLtsDTO serviceDetailLts,TerminateOrderCaptureDTO orderCapture) {
		LtsTerminateServiceSelectionFormDTO ssForm=orderCapture.getLtsTerminateServiceSelectionForm();
		LtsTerminateAppointmentFormDTO aForm=orderCapture.getLtsTerminateAppointmentForm();
		serviceDetailLts.setDiscReasonCode(ssForm.getDisconnectReason());
		serviceDetailLts.setCeaseRentalDate(aForm.getCeaseRentalDate());
		if(StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getRelatedEyeFsa())) {
			serviceDetailLts.setBackDateApplnDate(aForm.getApplicationDate());
		}
		else {
			serviceDetailLts.setBackDateApplnDate(null);
		}
	}
	
	protected void setDForm(ServiceDetailLtsDTO pServiceDetailLts,TerminateOrderCaptureDTO pOrderCapture) {
		LtsTerminateServiceSelectionFormDTO ssForm=pOrderCapture.getLtsTerminateServiceSelectionForm();
		pServiceDetailLts.setDFormSerial(ssForm.getDisconnectFormSerial());
		pServiceDetailLts.setDiscFiveNaInd(pOrderCapture.getLtsTerminateAppointmentForm().isFiveNa() ? "Y" : null);
		pServiceDetailLts.setWaiveDFormReasonCd(ssForm.getWaiveDFormReason());
	}
	
	protected void setFchSerial(ServiceDetailLtsDTO pServiceDetailLts,TerminateOrderCaptureDTO pOrderCapture) {
		LtsTerminateServiceSelectionFormDTO ssForm=pOrderCapture.getLtsTerminateServiceSelectionForm();
		pServiceDetailLts.setFchNum(ssForm.isFsaWgInd() ? ssForm.getFch() : null);
	}
	
	protected void setCollectionAddress(ServiceDetailLtsDTO pServiceDetailLts,TerminateOrderCaptureDTO pOrderCapture) {
		LtsTerminateAppointmentFormDTO aForm=pOrderCapture.getLtsTerminateAppointmentForm();
		String collectAddr = aForm.getAlternateAddressDetails();
		String existAddr = pOrderCapture.getLtsServiceProfile().getAddress().getFullAddress();
		pServiceDetailLts.setEqtCollectionAddr(aForm.isExternalCollection() ? collectAddr : existAddr);
		pServiceDetailLts.setExtEqtCollect(aForm.isExternalCollection() ? "Y" : "N");
	}
	
	protected void setDiscCallingCard(ServiceDetailLtsDTO pServiceDetailLts,TerminateOrderCaptureDTO pOrderCapture) {
		LtsTerminateServiceSelectionFormDTO ssForm=pOrderCapture.getLtsTerminateServiceSelectionForm();
		if (ssForm.isCallingCardInd()) {
			if (LtsTerminateConstant.CALLING_CARD_HANDLING_DISCONNECT.equals(ssForm.getCallingCardDetailsHandling())) {
				pServiceDetailLts.setDiscCallingCardInd("Y");
				
			}
			if (LtsTerminateConstant.CALLING_CARD_HANDLING_RETAIN.equals(ssForm.getCallingCardDetailsHandling())) {
				pServiceDetailLts.setDiscCallingCardInd("N");

			}
		}
		
	}
	
	protected void setLostEquipmentHandling(ServiceDetailLtsDTO pServiceDetailLts,TerminateOrderCaptureDTO pOrderCapture) {
		LtsTerminateServiceSelectionFormDTO ssForm = pOrderCapture.getLtsTerminateServiceSelectionForm();
		String equipmentLoss = ssForm.getLostEquipmentPenalty();
		if(!StringUtils.isEmpty(equipmentLoss)) {
			pServiceDetailLts.setLostEquipmentCd(equipmentLoss);
		}
	}
	
	protected String getCcServiceId(CreateServiceType createServiceType, ServiceDetailProfileLtsDTO serviceProfile) {
		switch (createServiceType) {
		case CREATE_SRV_TYPE_SIP_REMOVE:
			return serviceProfile.getSrvGrp().getMemberByType(LtsConstant.DAT_CD_NCR).getCcSrvId();
		default:
			return serviceProfile.getCcSrvId();
		}
	}
	
	protected String getCcServiceMemNum(CreateServiceType createServiceType, ServiceDetailProfileLtsDTO serviceProfile) {
		return serviceProfile.getCcSrvMemNum();
	}
	
	protected String getLtsTerminateFromProductType(ServiceDetailProfileLtsDTO pServiceProfile) {
		if (pServiceProfile == null) {
			return null;
		}
		if (StringUtils.isBlank(pServiceProfile.getExistEyeType()) || pServiceProfile.getSrvGrp() == null) {
			return LtsConstant.LTS_PRODUCT_TYPE_DEL;
		}
		return pServiceProfile.getExistEyeType().toUpperCase();
	}
	
	protected String getLtsTerminateFromSrvType(CreateServiceType pCreateServiceType, String pSrvNum, TerminateOrderCaptureDTO pOrderCapture) {
		ServiceDetailProfileLtsDTO serviceProfile = pOrderCapture.getLtsServiceProfile();
		if (serviceProfile == null) {
			return null;
		}
		// EXISTING EYE
		if (serviceProfile.getSrvGrp() != null && ArrayUtils.isNotEmpty(serviceProfile.getSrvGrp().getGrpMems())) {
			for (ServiceGroupMemberProfileDTO groupMemberProfile : serviceProfile.getSrvGrp().getGrpMems()) {
				if (StringUtils.equals(pSrvNum, groupMemberProfile.getSrvNum())) {
					return StringUtils.equals(LtsConstant.DAT_CD_DEL,
							groupMemberProfile.getDatCd()) ? LtsConstant.LTS_SRV_TYPE_PE
									: LtsConstant.LTS_SRV_TYPE_SIP;
				}
			}
		}
		
		if (StringUtils.isNotEmpty(serviceProfile.getDuplexNum())) {
			return LtsConstant.LTS_SRV_TYPE_DNX;
		}

		return LtsConstant.LTS_SRV_TYPE_DEL;
	}
	
	protected AccountDetailLtsDTO createTerminateAccountDetailLts(CreateServiceType createServiceType, TerminateOrderCaptureDTO orderCapture,
			ServiceDetailProfileLtsDTO serviceProfile, Map<String, AccountDetailLtsDTO> accountDetailLtsMap,
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap) {
		
		if (serviceProfile == null) {
			serviceProfile = orderCapture.getLtsServiceProfile(); 
		}
		
		AccountDetailProfileLtsDTO primaryProfileAccount = LtsSbOrderHelper.getPrimaryAccount(serviceProfile);
		AccountDetailLtsDTO accountDetailLts = null;
		
		if (accountDetailLtsMap.containsKey(primaryProfileAccount.getAcctNum())) {
			accountDetailLts = accountDetailLtsMap.get(primaryProfileAccount.getAcctNum());
			accountDetailLts.setPaymethods(createTerminatePaymentMethodDetailLts(orderCapture, primaryProfileAccount));
			accountDetailLts.setBillingAddress(createTerminateBillingAddressLtsDTO(orderCapture, createServiceType));
			return accountDetailLts;
		}
		
		accountDetailLts = new AccountDetailLtsDTO();
		accountDetailLts.setPaymethods(createTerminatePaymentMethodDetailLts(orderCapture, primaryProfileAccount));
		accountDetailLts.setBillingAddress(createTerminateBillingAddressLtsDTO(orderCapture, createServiceType));
		accountDetailLts.setAcctNo(primaryProfileAccount.getAcctNum());
		accountDetailLts.setBillFreq(primaryProfileAccount.getBillFreq());
		accountDetailLts.setBillLang(primaryProfileAccount.getBillLang());
		accountDetailLts.setCustomer(createTerminateCustomerDetailLts(serviceProfile.getPrimaryCust(), customerDetailLtsMap, orderCapture));
		accountDetailLts.setBillFmt(primaryProfileAccount.getBillFmt());
		accountDetailLts.setExistBillMedia(primaryProfileAccount.getBillMedia());
		accountDetailLts.setBillMedia(primaryProfileAccount.getBillMedia());
		accountDetailLts.setAutopayStatementInd(primaryProfileAccount.isAutopayStatementInd() ? "Y" : null);
		accountDetailLts.setBillingAddress(createTerminateBillingAddressLtsDTO(orderCapture, createServiceType));
		accountDetailLts.setAcctName(orderCapture.getLtsTerminateBillingInfoForm().getNewBillingName());
		accountDetailLts.setEmailAddr(primaryProfileAccount.getEmailAddr());
		accountDetailLtsMap.put(accountDetailLts.getAcctNo(), accountDetailLts);

		return accountDetailLts;
	}
	
	protected CustomerDetailLtsDTO createTerminateCustomerDetailLts(CustomerDetailProfileLtsDTO customerDetailProfileLts, 
			Map<String, CustomerDetailLtsDTO> customerDetailLtsMap,
			TerminateOrderCaptureDTO orderCapture) {
		
		if (customerDetailLtsMap.containsKey(customerDetailProfileLts.getCustNum())) {
			return customerDetailLtsMap.get(customerDetailProfileLts.getCustNum());
		}
		
		CustomerDetailLtsDTO customerDetailLts = new CustomerDetailLtsDTO();
		customerDetailLts.setBlacklistInd(customerDetailProfileLts.isBlacklistCustInd() ? "Y" : null);
		customerDetailLts.setCompanyName(customerDetailProfileLts.getCompanyName());
//		customerDetailLts.setContactMobileNum(contactMobileNum);
		customerDetailLts.setCustNo(customerDetailProfileLts.getCustNum());
//		customerDetailLts.setDob(dob);
		customerDetailLts.setFirstName(customerDetailProfileLts.getFirstName());
		customerDetailLts.setIdDocNum(customerDetailProfileLts.getDocNum());
		customerDetailLts.setIdDocType(customerDetailProfileLts.getDocType());
		customerDetailLts.setIdVerifiedInd("Y");
//		customerDetailLts.setLangWritten(langWritten);
		customerDetailLts.setLastName(customerDetailProfileLts.getLastName());
		customerDetailLts.setLob(LtsConstant.LOB_LTS);
		customerDetailLts.setTitle(customerDetailProfileLts.getTitle());
		
		customerDetailLtsMap.put(customerDetailLts.getCustNo(), customerDetailLts);
		return customerDetailLts;
	}
	
	protected PaymentMethodDetailLtsDTO[] createTerminatePaymentMethodDetailLts(TerminateOrderCaptureDTO orderCapture, 
			AccountDetailProfileLtsDTO accountDetailProfileLts) {
		
		List<PaymentMethodDetailLtsDTO> paymentMethodDetailLtsList = new ArrayList<PaymentMethodDetailLtsDTO>();
		AccountDetailProfileLtsDTO upgradeProfileAccount = LtsSbOrderHelper.getPrimaryAccount(orderCapture.getLtsServiceProfile());
		
		if (accountDetailProfileLts == null) {
			accountDetailProfileLts = upgradeProfileAccount;
		}
		
		PaymentMethodDetailLtsDTO existingPaymentDetail = setupExistingPaymentDetail(accountDetailProfileLts);
		
		
		LtsTerminateBillingInfoFormDTO ltsPaymentForm = orderCapture.getLtsTerminateBillingInfoForm();
		
		if (ltsPaymentForm == null 
				|| !StringUtils.equals(upgradeProfileAccount.getAcctNum(), accountDetailProfileLts.getAcctNum())) {
			existingPaymentDetail.setAction(LtsConstant.IO_IND_SPACE);
			return new PaymentMethodDetailLtsDTO[] {existingPaymentDetail}; 
		}
		
		existingPaymentDetail.setAction(LtsConstant.IO_IND_OUT);
		paymentMethodDetailLtsList.add(existingPaymentDetail);
		
		return paymentMethodDetailLtsList.toArray(new PaymentMethodDetailLtsDTO[paymentMethodDetailLtsList.size()]);
	}
	
	protected PaymentMethodDetailLtsDTO setupExistingPaymentDetail(AccountDetailProfileLtsDTO accountDetailProfileLts){

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
	
//	protected String getSuggestedTerminateSrd(TerminateOrderCaptureDTO orderCapture) {
//		if (orderCapture.getLtsTerminateAppointmentForm() == null
//				|| orderCapture.getLtsTerminateAppointmentForm().getSrDate() == null) {
//			return null;
//		}
//		
//		return orderCapture.getLtsTerminateAppointmentForm().getSrDate();
//	}
	protected String getTerminateImsOrderType(TerminateOrderCaptureDTO orderCapture) {
		if (orderCapture.getLtsServiceProfile().getEyeFsaProfile() == null) {
			return null;
		}
		
		FsaServiceType imsSrvType = ltsRetrieveFsaService.getExistSrvType(orderCapture.getLtsServiceProfile().getEyeFsaProfile());
		
		if (FsaServiceType.WG == imsSrvType) {
			return LtsConstant.ORDER_TYPE_DISCONNECT;
		}
		
		return LtsConstant.ORDER_TYPE_CHANGE;
	}
	
	protected AppointmentDetailLtsDTO createTerminateAppointmentDetailLts(CreateServiceType createServiceType, TerminateOrderCaptureDTO orderCapture) {

		LtsTerminateAppointmentFormDTO appointment = orderCapture.getLtsTerminateAppointmentForm();
		
		if (appointment == null) {
			return null;
		}
		
		AppointmentDetailLtsDTO appointmentDetailLts = new AppointmentDetailLtsDTO();

		appointmentDetailLts.setInstContactName(appointment.getCustContactName());
		appointmentDetailLts.setInstContactNum(appointment.getCustContactNum());
		appointmentDetailLts.setInstContactMobile(appointment.getCustContactMobileNum());
		appointmentDetailLts.setCustContactMobile(appointment.getCustContactMobileNum());
		appointmentDetailLts.setCustContactFix(appointment.getCustContactNum());
		appointmentDetailLts.setInstSmsNum(appointment.getCustContactMobileNum());
		
		if(!LtsConstant.TRUE_IND.equals(appointment.getConfirmedInd()) && appointment.isFieldVisitRequired()){
			return appointmentDetailLts;
		}
		
		String appntTimeSlot = null;
		String appntDate = null;
		String[] appntDateString = null;
		
		if (StringUtils.isNotBlank(appointment.getAppntDate()) 
				&& StringUtils.isNotBlank(appointment.getAppntTimeSlotAndType())) {
			appntTimeSlot = LtsDateFormatHelper.convertPonTime(appointment.getAppntTimeSlot());
			appntTimeSlot = LtsDateFormatHelper.revertToBomTime(appntTimeSlot);
			appointmentDetailLts.setAppntType(appointment.getAppntTimeSlotType());	
			appntDate = appointment.getAppntDate();
		}

		if (StringUtils.isNotBlank(appntTimeSlot) && StringUtils.isNotBlank(appntDate)) {
			appntDateString = ltsAppointmentService.reformatDateTimeSlot(appntDate, appntTimeSlot);
			appointmentDetailLts.setAppntStartTime(appntDateString[0]);
			appointmentDetailLts.setAppntEndTime(appntDateString[1]);	
		}

		appointmentDetailLts.setSerialNum(appointment.getPreBookSerialNum());
		// 20160310 Set TID Indicator while IMS order type is C 
		// 20171009 also for lost modem case
		if (LtsConstant.ORDER_TYPE_CHANGE.equals(getTerminateImsOrderType(orderCapture))
				|| orderCapture.getLtsTerminateServiceSelectionForm().isLostModem()) {
			appointmentDetailLts.setTidInd("Y");
			if (ArrayUtils.isNotEmpty(appntDateString) && appntDateString.length >=2 ) {
				appointmentDetailLts.setTidStartTime(appntDateString[0]);
				appointmentDetailLts.setTidEndTime(appntDateString[1]);	
			}
		}
		
		return appointmentDetailLts;
	}
	
	protected String getTwoNInd(String serviceNum, ServiceDetailProfileLtsDTO serviceProfile) {
		if (StringUtils.equals(serviceNum, serviceProfile.getSrvNum())) {
			return serviceProfile.isTwoNBuildInd() ? "Y" : null;
		}
		if (StringUtils.equals(serviceNum, serviceProfile.getDuplexNum())) {
			return serviceProfile.isDuplexTwoNBuildInd() ? "Y" : null;
		}
		return null;
	}
	
	protected String getDuplexInd(String serviceNum, ServiceDetailProfileLtsDTO serviceProfile) {
		if (StringUtils.equals(serviceProfile.getSrvNum(), serviceNum)) {
			return LtsConstant.DUPLEX_X_IND;
		}
		if (StringUtils.equals(serviceProfile.getDuplexNum(), serviceNum)) {
			return LtsConstant.DUPLEX_Y_IND;
		}
		return null;
	}
	
	protected String getTerminateServiceNum(CreateServiceType createService, TerminateOrderCaptureDTO orderCapture, ServiceDetailProfileLtsDTO serviceProfile) {
		switch (createService) {
		case CREATE_SRV_TYPE_SIP_REMOVE:
			ServiceGroupMemberProfileDTO[] groupProfiles  = serviceProfile.getSrvGrp().getGrpMems();
			for (ServiceGroupMemberProfileDTO groupProfile : groupProfiles) {
				if (LtsConstant.DAT_CD_NCR.equals(groupProfile.getDatCd())) {
					return groupProfile.getSrvNum();
				}
			}
			return null;
		default:
			return serviceProfile.getSrvNum();		
		}
	}
	protected ThirdPartyDetailLtsDTO createTerminateThirdPartyDetailLts (CreateServiceType createServiceType, TerminateOrderCaptureDTO orderCapture) {
		
		LtsTerminateServiceSelectionFormDTO custIdForm = orderCapture.getLtsTerminateServiceSelectionForm();
		
		if (custIdForm == null) {
			return null;
		}
		
		ThirdPartyDetailLtsDTO thirdPartyDetailLts = new ThirdPartyDetailLtsDTO();
		
		thirdPartyDetailLts.setTitle(custIdForm.getThirdTitle());
		thirdPartyDetailLts.setAppntDocId(custIdForm.getThirdDocNum());
		thirdPartyDetailLts.setAppntDocType(custIdForm.getThirdDocType());
		thirdPartyDetailLts.setAppntContactNum(custIdForm.getThirdContactNum());
		thirdPartyDetailLts.setAppntFirstName(custIdForm.getThirdFirstName());
		thirdPartyDetailLts.setAppntLastName(custIdForm.getThirdLastName());
		thirdPartyDetailLts.setAppntIdVerifiedInd(custIdForm.isThirdIdVerify()?"Y":null);
		thirdPartyDetailLts.setRelationshipCode(custIdForm.getThirdRelationship());

		if(StringUtils.equals(custIdForm.getThirdRelationship(), "NP")
				&& StringUtils.isNotBlank(custIdForm.getThirdOtherRelationship())){
			thirdPartyDetailLts.setRemarks("Actual Relationship: " + custIdForm.getThirdOtherRelationship());
		}
		
		return thirdPartyDetailLts;
	}
	
	protected List<RemarkDetailLtsDTO> createTerminateAddonRemarkDetailLts(TerminateOrderCaptureDTO orderCapture) {
		
		if (orderCapture.getLtsTerminateAppointmentForm() == null) {
			return null;
		}
		List<RemarkDetailLtsDTO> remarkDetailLtsList = new ArrayList<RemarkDetailLtsDTO>();
		remarkDetailLtsList.addAll(this.createRemarkDetailLts(orderCapture.getLtsTerminateAppointmentForm().getRemarks(), LtsConstant.REMARK_TYPE_ADD_ON_REMARK));
		remarkDetailLtsList.addAll(this.createRemarkDetailLts(orderCapture.getLtsTerminateAppointmentForm().getEarliestSrdReason(), LtsConstant.REMARK_TYPE_EARLIEST_SRD_REMARK));
		return remarkDetailLtsList;
	}
	
	protected List<RemarkDetailLtsDTO> createRemarkDetailLts(String pRemarkContent, String pRemarkType) {
		
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
	
	protected RemarkDetailLtsDTO[] getTerminateRemarkDetailLts(CreateServiceType createServiceType, TerminateOrderCaptureDTO orderCapture) {
		List<RemarkDetailLtsDTO> remarkDetailList = new ArrayList<RemarkDetailLtsDTO>();
		
		
		List<RemarkDetailLtsDTO> addonRemarkList = createTerminateAddonRemarkDetailLts(orderCapture);
		if (addonRemarkList != null && !addonRemarkList.isEmpty()) {
			remarkDetailList.addAll(addonRemarkList);	
		}
		
		if (remarkDetailList.isEmpty()) {
			return null;
		}
		return remarkDetailList.toArray(new RemarkDetailLtsDTO[remarkDetailList.size()]);
	}
	
	protected BillingAddressLtsDTO createTerminateBillingAddressLtsDTO(TerminateOrderCaptureDTO orderCapture, CreateServiceType createServiceType){

		LtsTerminateBillingInfoFormDTO form = orderCapture.getLtsTerminateBillingInfoForm();
		
		BillingAddressLtsDTO billingAddressLts = new BillingAddressLtsDTO();
		String fullAddr = null;
		if(form != null  && form.isChangeBa()){
			fullAddr = form.getNewBillingAddress();
			billingAddressLts.setInstantUpdateInd(form.isInstantUpdateBa()? "Y" : "N");
			billingAddressLts.setAction(LtsConstant.BILLING_ADDR_ACTION_NEW);
		}else{
			fullAddr = form.getBillingAddress();
			billingAddressLts.setAction(LtsConstant.BILL_ADDR_ACTION_EXISTING);
		}
		if(StringUtils.isNotBlank(fullAddr)){
			String[] addrLines = LtsSbOrderHelper.addresslinesFixer(fullAddr.split("\n"));
			billingAddressLts.setAddrLine1(StringUtils.strip(addrLines[0]));
			billingAddressLts.setAddrLine2(addrLines.length > 1? StringUtils.strip(addrLines[1]) :null);
			billingAddressLts.setAddrLine3(addrLines.length > 2? StringUtils.strip(addrLines[2]) :null);
			billingAddressLts.setAddrLine4(addrLines.length > 3? StringUtils.strip(addrLines[3]) :null);
			billingAddressLts.setAddrLine5(addrLines.length > 4? StringUtils.strip(addrLines[4]) :null);
			billingAddressLts.setAddrLine6(addrLines.length > 5? StringUtils.strip(addrLines[5]) :null);
		}
		return billingAddressLts;
	}
	

	protected ItemDetailLtsDTO[] createAllItemDetailLts(LtsTerminateServiceSelectionFormDTO ssForm, List<ItemDetailLtsDTO> itemDetailLtsList, String srd) {
		DateTime srDate = null;
		if(StringUtils.isNotBlank(srd)){
			srDate = DateTime.parse(srd, DateTimeFormat.forPattern("dd/MM/yyyy"));
		}
		
		// Existing Service TP
		addProfileItemDetail(ssForm.getSrvPlanItemDetails(), itemDetailLtsList, srDate);

		// Existing Other VAS Offer
		addProfileItemDetail(ssForm.getVasPlanItemDetails(), itemDetailLtsList, srDate);

		// Existing FSA VAS Offer
//		addProfileItemDetail(ssForm.getFsaVasPlanItemDetails(), itemDetailLtsList, srDate);
		
		return itemDetailLtsList.toArray(new ItemDetailLtsDTO[itemDetailLtsList.size()]);
	}
	
	protected void addProfileItemDetail(List<ItemDetailProfileLtsDTO> itemDetailProfileLtsList, List<ItemDetailLtsDTO> itemDetailLtsList, DateTime srd ) {
		
		if (itemDetailProfileLtsList == null || itemDetailProfileLtsList.isEmpty()) {
			return;
		}
		
		for (ItemDetailProfileLtsDTO itemDetailProfileLts : itemDetailProfileLtsList) {
			ItemDetailDTO itemDetail = itemDetailProfileLts.getItemDetail();
			ItemDetailLtsDTO itemDetailLts = createItemDetailLts(itemDetail, String.valueOf(itemDetailLtsList.size() + 1), LtsConstant.IO_IND_OUT);
			itemDetailLts.setExistEffectivePrice(itemDetail.getNetEffPrice());
			itemDetailLts.setExistGrossPrice(itemDetail.getGrossEffPrice());
			itemDetailLts.setExistStartDate(itemDetailProfileLts.getConditionEffStartDate());
			itemDetailLts.setExistEndDate(itemDetailProfileLts.getConditionEffEndDate());
			itemDetailLts.setPaidVasInd(itemDetailProfileLts.isPaidVas() ? "Y" : null);
			itemDetailLts.setProfileItemType(itemDetailProfileLts.getItemType());

			itemDetailLts.setPenaltyHandling(getPenaltyHandling(itemDetail));
			if(srd != null && StringUtils.isNotBlank(itemDetailProfileLts.getConditionEffEndDate())){
				DateTime ced = DateTime.parse(itemDetailProfileLts.getConditionEffEndDate().substring(0, 10), DateTimeFormat.forPattern("dd/MM/yyyy"));
				if(srd.isEqual(ced) || srd.isAfter(ced)){
					itemDetailLts.setPenaltyHandling(LtsConstant.PENALTY_ACTION_AUTO_WAIVE);
				}
			}
			
			itemDetailLtsList.add(itemDetailLts);
		}
	}
	
	protected ItemDetailLtsDTO createItemDetailLts (ItemDetailDTO itemDetail, String srvItemSeq, String ioInd) {
		ItemDetailLtsDTO itemDetailLts = new ItemDetailLtsDTO();
		itemDetailLts.setSrvItemSeq(srvItemSeq);
		itemDetailLts.setBasketId("9999999999");
		itemDetailLts.setItemAttbs(createItemAttributeDetailLts(itemDetail.getItemAttbs(), srvItemSeq));
		itemDetailLts.setItemId(itemDetail.getItemId());
		itemDetailLts.setItemType(itemDetail.getItemType());
		itemDetailLts.setIoInd(ioInd);
		itemDetailLts.setItemQty(StringUtils.isNotBlank(itemDetail.getSelectedQty()) ? itemDetail.getSelectedQty() : "1");
		return itemDetailLts;
	}
	
	protected ItemAttributeDetailLtsDTO[] createItemAttributeDetailLts(ItemAttbDTO[] itemAttbs, String srvItemSeq) {
		
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
	
	private ServiceCallPlanDetailLtsDTO[] createServiceCallPlanDetailLts(TerminateOrderCaptureDTO orderCapture, SbOrderDTO pSbOrder) {
		
		LtsTerminateServiceSelectionFormDTO form = orderCapture.getLtsTerminateServiceSelectionForm();
		if (form == null
				|| form.getIddCallPlanProfileList()== null
				|| form.getIddCallPlanProfileList().isEmpty()) {
			return null;
		}
		
		List<ServiceCallPlanDetailLtsDTO> srvCPDtlList = new ArrayList<ServiceCallPlanDetailLtsDTO>();
		ServiceCallPlanDetailLtsDTO srvCPDtl = null;
		for (IddCallPlanProfileLtsDTO iddCallPlanProfileLts : form.getIddCallPlanProfileList()) {
			srvCPDtl = new ServiceCallPlanDetailLtsDTO();
			srvCPDtl.setPlanCd(iddCallPlanProfileLts.getPlanCd());
			srvCPDtl.setPlanHolder(iddCallPlanProfileLts.getPlanHolder());
			srvCPDtl.setPlanHolderType(iddCallPlanProfileLts.getPlanHolderType());
			srvCPDtl.setPlanType(iddCallPlanProfileLts.getPlanType());
			srvCPDtl.setIoInd(LtsBackendConstant.IDD_ACTION_REMOVE.equals(iddCallPlanProfileLts.getAction()) ? LtsConstant.IO_IND_OUT : LtsConstant.IO_IND_SPACE);
			srvCPDtl.setEffStartDate(iddCallPlanProfileLts.getEffStartDate()); 
			srvCPDtl.setEffEndDate(calculateCallPlanEffEndDate(orderCapture, iddCallPlanProfileLts));
			srvCPDtl.setContractMonth(iddCallPlanProfileLts.getTpExpiredMonths());
			srvCPDtl.setPenaltyHandling(iddCallPlanProfileLts.getPenaltyHandling());
			srvCPDtl.setContractStartDate(iddCallPlanProfileLts.getContractStartDate());
			srvCPDtl.setContractEndDate(iddCallPlanProfileLts.getContractEndDate());
			srvCPDtl.setDca(iddCallPlanProfileLts.getDca());
			srvCPDtl.setPlanCharge(iddCallPlanProfileLts.getPlanCharge());
			
			if(LtsBackendConstant.IDD_ACTION_CHG_DCA.equals(iddCallPlanProfileLts.getAction())){
				srvCPDtl.setChgDcaInd("Y");
				srvCPDtl.setNewDca(iddCallPlanProfileLts.getNewDca());
			}else if(LtsBackendConstant.IDD_ACTION_CHG_ACCT_DCA.equals(iddCallPlanProfileLts.getAction())){
				srvCPDtl.setChgDcaInd("A");
				srvCPDtl.setNewDca(iddCallPlanProfileLts.getNewDca());
			}
			
			srvCPDtlList.add(srvCPDtl);
		}
		
		return srvCPDtlList.toArray(new ServiceCallPlanDetailLtsDTO[srvCPDtlList.size()]);
	}
	
	protected String calculateCallPlanEffEndDate(TerminateOrderCaptureDTO orderCapture, IddCallPlanProfileLtsDTO iddCallPlanProfileLts){

		if (LtsBackendConstant.IDD_ACTION_REMOVE.equals(iddCallPlanProfileLts.getAction())) {
			
			//use original effective end date (if available) for NON deceased case.
			if (!LtsSbOrderHelper.isDeceasedCase(orderCapture.getLtsTerminateServiceSelectionForm())) {
				if(StringUtils.isNotBlank(iddCallPlanProfileLts.getEffEndDate())
						&& !StringUtils.equals(iddCallPlanProfileLts.getEffEndDate(), "00000000")){
					return iddCallPlanProfileLts.getEffEndDate();
				}
			}
			
			//check if effStartDate < today
			try {
				Date effStartDate = DateUtils.parseDate(iddCallPlanProfileLts.getEffStartDate(), new String[]{"yyyyMMdd"});
				Date currentDate = new Date();
				if (currentDate.before(effStartDate)) {
					return LocalDate.now().plusDays(2).toString(DateTimeFormat.forPattern("yyyyMMdd"));
				}
			}
			catch (Exception e) {
				throw new AppRuntimeException(e);
			}
			
			return LtsDateFormatHelper.reformatDate(orderCapture.getLtsTerminateAppointmentForm().getAppntDate(), "dd/MM/yyyy", "yyyyMMdd");
		}
		
		return iddCallPlanProfileLts.getEffEndDate();
	}
	
	protected String calculateCallPlanEffEndDate(String action, String effStartDate,String effEndDate, String appntDate, String lastSrvInd ){
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat df_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		
		if (LtsBackendConstant.IDD_ACTION_REMOVE
				.equals(action)) {
			try {
				return df_yyyyMMdd.format(getNextBillDate(df_yyyyMMdd
						.parse(effStartDate), df
						.parse(appntDate), lastSrvInd.equals("Y") ? true : false));
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}else {
			return effEndDate;
		}
	}

	public static Date getNextBillDate(Date pEffStartDate, Date pSRD,boolean pLastServiceInd) {
		Calendar calNextBillDate = Calendar.getInstance();
		
		Calendar calEffStartDate = Calendar.getInstance();
		calEffStartDate.setTime(pEffStartDate);
		
		Calendar calSRD = Calendar.getInstance();
		calSRD.setTime(pSRD);

		if (pLastServiceInd) {
			return calSRD.getTime();
		}

		calNextBillDate.set(calSRD.get(Calendar.YEAR),
				calSRD.get(Calendar.MONTH),
				calEffStartDate.get(Calendar.DAY_OF_MONTH));

		if (calNextBillDate.getTimeInMillis() < calSRD.getTimeInMillis()) {
			calNextBillDate.add(Calendar.MONTH, 1);
		}

		return calNextBillDate.getTime();

	}

	protected String getPenaltyHandling(ItemDetailDTO itemDetail) {
		
		if (itemDetail == null 
				|| StringUtils.isEmpty(itemDetail.getPenaltyHandling())) {
			return null;
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
	
	protected boolean isBackDateOrder(TerminateOrderCaptureDTO orderCapture){
		DateTime srDate = DateTime.parse(orderCapture.getLtsTerminateAppointmentForm().getAppntDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
		DateTime now = DateTime.parse(DateTime.now().toString(DateTimeFormat.forPattern("dd/MM/yyyy")), DateTimeFormat.forPattern("dd/MM/yyyy"));
		if(srDate.isBefore(now)){
			return true;
		}
		return false;
	}
	
	private ImsOfferDetailDTO[] createDisconnectAllImsOfferDetails(TerminateOrderCaptureDTO orderCapture) {
		List<ImsOfferDetailDTO> imsOfferDetailList = new ArrayList<ImsOfferDetailDTO>();
		
		List<ItemDetailProfileLtsDTO> fsaVasPlanFormItemList = orderCapture.getLtsTerminateServiceSelectionForm().getFsaVasPlanItemDetails();
		
		if(orderCapture.getLtsServiceProfile().getEyeFsaProfile() != null
				&& ArrayUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getEyeFsaProfile().getItems())){
			for(ItemDetailProfileLtsDTO imsProfileItemDetail : orderCapture.getLtsServiceProfile().getEyeFsaProfile().getItems()){
				ItemDetailProfileLtsDTO fsaVasItem = imsProfileItemDetail;
				if(fsaVasPlanFormItemList != null && fsaVasPlanFormItemList.size() > 0){
					for(ItemDetailProfileLtsDTO fsaVasPlanFormItem : fsaVasPlanFormItemList){
						if(StringUtils.equals(fsaVasPlanFormItem.getItemId(), imsProfileItemDetail.getItemId())){
							fsaVasItem = fsaVasPlanFormItem;
						}
					}
				}
				createImsOfferDetail(fsaVasItem, LtsConstant.IO_IND_OUT, imsOfferDetailList);
			}
		}
		
//		for(ItemDetailProfileLtsDTO imsProfileItemDetail : orderCapture.getLtsTerminateServiceSelectionForm().getFsaVasPlanItemDetails()){
//			createImsOfferDetail(imsProfileItemDetail, LtsConstant.IO_IND_OUT, imsOfferDetailList);
//		}
		
		addOfferChangeDetails(orderCapture, imsOfferDetailList);
		
		if (imsOfferDetailList.isEmpty()) {
			return null;
		}
		
		return imsOfferDetailList.toArray(new ImsOfferDetailDTO[imsOfferDetailList.size()]);
	}
	
	private void addOfferChangeDetails(TerminateOrderCaptureDTO orderCapture, List<ImsOfferDetailDTO> imsOfferDetailList) {
		
		String ltsFromProd = StringUtils.defaultIfEmpty(orderCapture.getLtsServiceProfile().getExistEyeType(), "DEL");
		String ltsToProd = LtsConstant.LTS_PRODUCT_TYPE_REMOVE;
		String imsFromProd = null;
		String imsToProd = null;
		String mirror = null;
		String rentalRouter = null;
		
		FsaServiceDetailOutputDTO eyeFsaProfile = orderCapture.getLtsServiceProfile().getEyeFsaProfile();
		
		if (eyeFsaProfile != null) {
			FsaServiceType imsSrvType = ltsRetrieveFsaService.getExistSrvType(eyeFsaProfile);
			imsFromProd = imsSrvType.getDesc();
			imsToProd = imsSrvType == FsaServiceType.WG ? LtsConstant.IMS_PRODUCT_TYPE_REMOVE : imsSrvType.getDesc();
			mirror = eyeFsaProfile.getExistMirrorInd();
			
			OfferDetailProfileLtsDTO[] profileImsOffers = eyeFsaProfile.getOffers();
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
//						imsOfferDetail.setPenaltyWaiveInd(getPenaltyWaiveInd(imsProfileItemDetail.getItemDetail()));
//						imsOfferDetail.setPenaltyHandling(getPenaltyHandling(imsProfileItemDetail.getItemDetail()));
						imsOfferDetail.setItemType(StringUtils.defaultIfEmpty(offerChanges[2], LtsConstant.ITEM_TYPE_OFFER_CHANGE));
						imsOfferDetailList.add(imsOfferDetail);
					}
				}
			}
		}
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
}
