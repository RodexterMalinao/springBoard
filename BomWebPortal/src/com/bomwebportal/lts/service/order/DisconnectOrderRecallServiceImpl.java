package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.ImsPendingOrderDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateBillingInfoFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateServiceSelectionFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateSupportDocFormDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.AddressRolloutService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.LtsPaymentService;
import com.bomwebportal.lts.service.LtsRelatedPcdOrderService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.LoggingService;

public class DisconnectOrderRecallServiceImpl implements DisconnectOrderRecallService {
	
	protected final Log logger = LogFactory.getLog(getClass());

	protected LtsPaymentService ltsPaymentService;
	protected AddressRolloutService addressRolloutService;
	protected LtsRelatedPcdOrderService ltsRelatedPcdOrderService;
	protected LtsOrderDocumentService ltsOrderDocumentService;
	protected CodeLkupCacheService relationshipCodeLkupCacheService;
	protected LtsOfferService ltsOfferService;
	protected OrderRetrieveLtsService orderRetrieveLtsService;
	protected LoggingService loggingService;
	protected SbOrderInfoLtsService sbOrderInfoLtsService;
	protected ServiceProfileLtsService serviceProfileLtsService;
	
	
	
	public SbOrderInfoLtsService getSbOrderInfoLtsService() {
		return sbOrderInfoLtsService;
	}

	public void setSbOrderInfoLtsService(SbOrderInfoLtsService sbOrderInfoLtsService) {
		this.sbOrderInfoLtsService = sbOrderInfoLtsService;
	}

	public LtsPaymentService getLtsPaymentService() {
		return ltsPaymentService;
	}

	public void setLtsPaymentService(LtsPaymentService ltsPaymentService) {
		this.ltsPaymentService = ltsPaymentService;
	}

	public AddressRolloutService getAddressRolloutService() {
		return addressRolloutService;
	}

	public void setAddressRolloutService(AddressRolloutService addressRolloutService) {
		this.addressRolloutService = addressRolloutService;
	}

	public LtsRelatedPcdOrderService getLtsRelatedPcdOrderService() {
		return ltsRelatedPcdOrderService;
	}

	public void setLtsRelatedPcdOrderService(
			LtsRelatedPcdOrderService ltsRelatedPcdOrderService) {
		this.ltsRelatedPcdOrderService = ltsRelatedPcdOrderService;
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}
	
	public CodeLkupCacheService getRelationshipCodeLkupCacheService() {
		return relationshipCodeLkupCacheService;
	}

	public void setRelationshipCodeLkupCacheService(
			CodeLkupCacheService relationshipCodeLkupCacheService) {
		this.relationshipCodeLkupCacheService = relationshipCodeLkupCacheService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}
	
	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public LoggingService getLoggingService() {
		return loggingService;
	}

	public void setLoggingService(LoggingService loggingService) {
		this.loggingService = loggingService;
	}

	public ServiceProfileLtsService getServiceProfileLtsService() {
		return serviceProfileLtsService;
	}

	public void setServiceProfileLtsService(
			ServiceProfileLtsService serviceProfileLtsService) {
		this.serviceProfileLtsService = serviceProfileLtsService;
	}

	// Recall Order
	public TerminateOrderCaptureDTO recallOrder(String sbOrderId, boolean pIsEquiry, BomSalesUserDTO bomSalesUser) {
		SbOrderDTO sbOrder = orderRetrieveLtsService.retrieveSbOrder(sbOrderId, pIsEquiry);
		this.loggingService.logRecallLtsOrder(bomSalesUser.getUsername(), sbOrder);
		TerminateOrderCaptureDTO terminateOrderCapture = convertSbOrderToOrderCapture(sbOrder);
		setImsPendingOrder(terminateOrderCapture, sbOrder);
		setDeceaseCase(terminateOrderCapture, sbOrder);
		return terminateOrderCapture;
	}

	private void setImsPendingOrder(TerminateOrderCaptureDTO terminateOrderCapture,  SbOrderDTO sbOrder) {
		ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(sbOrder);
		
		if (imsService != null && StringUtils.isNotBlank(imsService.getSrvNum())) {
			List<ImsPendingOrderDTO> imsPendingOrderList = sbOrderInfoLtsService.getSbImsLatestPendingOrderBySrvNum(imsService.getSrvNum());
			if (imsPendingOrderList != null && !imsPendingOrderList.isEmpty()) {
				terminateOrderCapture.setImsPendingOrder(imsPendingOrderList.get(0));
			}
		}
	}
	
	private void setDeceaseCase(TerminateOrderCaptureDTO pOrderCapture, SbOrderDTO pSbOrder){
		ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(pSbOrder);
		pOrderCapture.getLtsTerminateServiceSelectionForm().setDeceasedType(ltsService.getDeviceType());
	}
	
	private TerminateOrderCaptureDTO convertSbOrderToOrderCapture(SbOrderDTO sbOrder) {
		if (sbOrder == null) {
			return null;
		}
		
		TerminateOrderCaptureDTO orderCapture = new TerminateOrderCaptureDTO();		
		orderCapture.setLtsServiceProfile(createDummyServiceDetailProfile(sbOrder));
		
		orderCapture.setLtsTerminateAppointmentForm(createLtsTerminateAppointmentForm(sbOrder));
		orderCapture.setLtsTerminateBillingInfoForm(createLtsTerminateBillingInfoForm(sbOrder));
		orderCapture.setLtsSalesInfoForm(createLtsTerminateSalesInfoForm(sbOrder));
		orderCapture.setLtsTerminateServiceSelectionForm(createLtsTerminateServiceSelectionForm(sbOrder));
		orderCapture.setLtsTerminateSupportDocForm(createLtsTerminateSupportDocForm(sbOrder));
		orderCapture.setSbOrder(sbOrder);
		orderCapture.setOrderAction(LtsConstant.ORDER_ACTION_RECALL);		
		orderCapture.setOrderType(sbOrder.getOrderType());
		return orderCapture;
	}
	
	public LtsTerminateBillingInfoFormDTO createLtsTerminateBillingInfoForm(SbOrderDTO sbOrder) {
		
		LtsTerminateBillingInfoFormDTO ltsPaymentForm = new LtsTerminateBillingInfoFormDTO();
		
		ServiceDetailLtsDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
		
		BillingAddressLtsDTO billAddrLts = ltsService.getAccount().getBillingAddress();
		if(billAddrLts != null){
			
			if(LtsConstant.BILL_ADDR_ACTION_EXISTING.equals(billAddrLts.getAction())){
				ltsPaymentForm.setChangeBa(false);
				ltsPaymentForm.setBillingAddress(null);
			}else{
				ltsPaymentForm.setChangeBa(true);

				StringBuilder addrSb = new StringBuilder();
				final String[] addrLines = {billAddrLts.getAddrLine1(), billAddrLts.getAddrLine2(),
						billAddrLts.getAddrLine3(), billAddrLts.getAddrLine4(), billAddrLts.getAddrLine5(), billAddrLts.getAddrLine6()};
				for(String line: addrLines){
					if(StringUtils.isNotEmpty(line)){
						addrSb.append(line + "\n");
					}
				}
				ltsPaymentForm.setNewBillingAddress(addrSb.toString());
				ltsPaymentForm.setNewBillingName(ltsService.getAccount().getAcctName());
				ltsPaymentForm.setInstantUpdateBa("Y".equals(billAddrLts.getInstantUpdateInd()));
			}
		}
		
		return ltsPaymentForm;
	}

	
	public String createRemark(SbOrderDTO sbOrder, String remarkType) {
		
		ServiceDetailDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
		
		if (ltsService.getRemarks() == null || ArrayUtils.isEmpty(ltsService.getRemarks())) {
			return null;
		}
		
		return ltsService.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK);
	}
	
	public LtsTerminateAppointmentFormDTO createLtsTerminateAppointmentForm(SbOrderDTO sbOrder) {

		LtsTerminateAppointmentFormDTO ltsAppointmentForm = null;
		
		ServiceDetailLtsDTO termService = LtsSbHelper.getLtsService(sbOrder);
		
		if (termService == null || termService.getAppointmentDtl() == null) {
			return null;
		}
		
		ltsAppointmentForm = new LtsTerminateAppointmentFormDTO();
		AppointmentDetailLtsDTO appointment = termService.getAppointmentDtl();
		ltsAppointmentForm.setPreBookSerialNum(appointment.getSerialNum());
		//upgrade
		String startDateTime = appointment.getAppntStartTime();
		String endDateTime = appointment.getAppntEndTime();
		
		boolean isFieldVisitRequired = StringUtils.equals(termService.getForceFieldVisitInd(), "Y");
		ltsAppointmentForm.setFieldVisitRequired(isFieldVisitRequired);
		if (isFieldVisitRequired) {
			if(StringUtils.isNotEmpty(startDateTime) && StringUtils.isNotEmpty(endDateTime)){
				ltsAppointmentForm.setAppntDate(startDateTime.split(" ")[0]);
				ltsAppointmentForm.setAppntTimeSlotAndType(
						startDateTime.split(" ")[1].concat("-").concat(endDateTime.split(" ")[1])
						+ LtsAppointmentHelper.TIMESLOT_DELIMITER
						+ appointment.getAppntType());
				if("Y".equals(termService.getForceFieldVisitInd())){
					ltsAppointmentForm.setConfirmedInd(LtsConstant.TRUE_IND);
				}
			}else{
				ltsAppointmentForm.setConfirmedInd(LtsConstant.FALSE_IND);
			}	
		}
		else {
			ltsAppointmentForm.setAppntDate(sbOrder.getSrvReqDate().split(" ")[0]);
			ltsAppointmentForm.setAppntTimeSlotAndType(appointment.getAppntType());
			ltsAppointmentForm.setConfirmedInd(LtsConstant.FALSE_IND);
		}
		ltsAppointmentForm.setCustContactMobileNum(appointment.getCustContactMobile());
		ltsAppointmentForm.setCustContactName(appointment.getInstContactName());
		ltsAppointmentForm.setCustContactNum(appointment.getInstContactNum());	

		ltsAppointmentForm.setFiveNa(StringUtils.equals(termService.getDiscFiveNaInd(), "Y"));
		
		ltsAppointmentForm.setAlternateAddressDetails(termService.getEqtCollectionAddr());
		ltsAppointmentForm.setExternalCollection(StringUtils.equals(termService.getExtEqtCollect(), "Y"));
		ltsAppointmentForm.setCeaseRentalDate(termService.getCeaseRentalDate());
		ltsAppointmentForm.setRemarks(createRemark(sbOrder, LtsConstant.REMARK_TYPE_ADD_ON_REMARK));
		
		return ltsAppointmentForm;
	}
	
	private LtsSalesInfoFormDTO createLtsTerminateSalesInfoForm(SbOrderDTO sbOrder) {
		
		LtsSalesInfoFormDTO ltsSalesInfoForm = null;
		
		if (StringUtils.isNotEmpty(sbOrder.getStaffNum())) {
			ltsSalesInfoForm = new LtsSalesInfoFormDTO();
			ltsSalesInfoForm.setSalesChannel(sbOrder.getSalesChannel());
			ltsSalesInfoForm.setImsApplicationMethod(sbOrder.getSalesChannel());
			ltsSalesInfoForm.setSalesCode(sbOrder.getSalesCd());
			ltsSalesInfoForm.setSalesContact(sbOrder.getSalesContactNum());
			ltsSalesInfoForm.setSalesTeam(sbOrder.getSalesTeam());
			ltsSalesInfoForm.setImsSource(sbOrder.getSalesTeam());
			ltsSalesInfoForm.setStaffId(sbOrder.getStaffNum());
			ltsSalesInfoForm.setStaffName(sbOrder.getSalesName());
			if(StringUtils.isNotBlank(sbOrder.getSalesProcessDate())){
				ltsSalesInfoForm.setDate(sbOrder.getSalesProcessDate().split(" ")[0]);
				ltsSalesInfoForm.setTime(sbOrder.getSalesProcessDate().split(" ")[1]);
			}
			ltsSalesInfoForm.setPosition(sbOrder.getSalesPosition());
			ltsSalesInfoForm.setJobName(sbOrder.getSalesJob());
		}
		
		return ltsSalesInfoForm;
	}
	
	private ServiceDetailProfileLtsDTO createDummyServiceDetailProfile(SbOrderDTO sbOrder) {
		
		if (LtsSbOrderHelper.isOnlineAcquistionOrder(sbOrder)) {
			return null;
		}
		

		ServiceDetailDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailDTO duplexService = LtsSbOrderHelper.getDuplexService(sbOrder);
		ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(sbOrder);
		
		String srvNum = ltsService.getSrvNum();
		String srvType = ltsService.getTypeOfSrv();
		ServiceDetailProfileLtsDTO serviceProfile = serviceProfileLtsService.getServiceProfileBySrvNum(srvNum, srvType);
		if(serviceProfile != null){
			return serviceProfile;
		}
		
		serviceProfile = new ServiceDetailProfileLtsDTO();
		
		serviceProfile.setAddress(createAddressDetailProfileLts(sbOrder));
		
		
		if (LtsConstant.LTS_SRV_TYPE_DNY.equals(((ServiceDetailLtsDTO) ltsService).getFromSrvType())) {
			
			serviceProfile.setDuplexNum(ltsService.getSrvNum());
			serviceProfile.setDuplexCcSrvMemNum(ltsService.getCcServiceMemNum());
			serviceProfile.setDuplexDnExchFrozen(StringUtils.equals(((ServiceDetailLtsDTO)ltsService).getFrozenExchInd(), "Y"));
			serviceProfile.setDuplexTwoNBuildInd(StringUtils.equals(((ServiceDetailLtsDTO)ltsService).getTwoNInd(), "Y"));
			
			if (duplexService != null) {
				serviceProfile.setSrvNum(duplexService.getSrvNum());
				serviceProfile.setCcSrvMemNum(duplexService.getCcServiceMemNum());
				serviceProfile.setDnExchFrozen(StringUtils.equals(((ServiceDetailLtsDTO)duplexService).getFrozenExchInd(), "Y"));
				serviceProfile.setTwoNBuildInd(StringUtils.equals(((ServiceDetailLtsDTO)duplexService).getTwoNInd(), "Y"));
			}
		
		}
		else {
			serviceProfile.setSrvNum(ltsService.getSrvNum());
			serviceProfile.setCcSrvMemNum(ltsService.getCcServiceMemNum());
			serviceProfile.setDnExchFrozen(StringUtils.equals(((ServiceDetailLtsDTO)ltsService).getFrozenExchInd(), "Y"));
			serviceProfile.setTwoNBuildInd(StringUtils.equals(((ServiceDetailLtsDTO)ltsService).getTwoNInd(), "Y"));
			
			if (duplexService != null) {
				serviceProfile.setDuplexNum(duplexService.getSrvNum());
				serviceProfile.setDuplexCcSrvMemNum(duplexService.getCcServiceMemNum());
				serviceProfile.setDuplexDnExchFrozen(StringUtils.equals(((ServiceDetailLtsDTO)duplexService).getFrozenExchInd(), "Y"));
				serviceProfile.setDuplexTwoNBuildInd(StringUtils.equals(((ServiceDetailLtsDTO)duplexService).getTwoNInd(), "Y"));
			}
		}
		serviceProfile.setCcSrvId(ltsService.getCcServiceId());
		serviceProfile.setAccounts(new AccountDetailProfileLtsDTO[] {createAccountDetailProfileLts(ltsService)});

		serviceProfile.setLtsTenure(StringUtils.isNotEmpty(ltsService.getTenure()) ? Integer.parseInt(ltsService.getTenure()) : 0);
		serviceProfile.setPendingOcid(ltsService.getPendingOcid());
		serviceProfile.setPendingOcSrd(ltsService.getPendingOcidSrd());
		serviceProfile.setPendingOrderOverdue(StringUtils.isNotEmpty(ltsService
						.getPendingOcidSrd()) ? LtsDateFormatHelper
						.isDateOverdue(ltsService.getPendingOcidSrd(),
								"dd/MM/yyyy") : false);
		serviceProfile.setPrimaryCust(createCustomerDetailProfileLts(ltsService));
		serviceProfile.setSearchCriteriaDn(ltsService.getSrvNum());
		serviceProfile.setSrvType(ltsService.getTypeOfSrv());
		serviceProfile.setSharedBsn(StringUtils.equals(((ServiceDetailLtsDTO)ltsService).getSharedBsnInd(), "Y"));
		
		if(!LtsConstant.LTS_PRODUCT_TYPE_DEL.equals(ltsService.getFromProd())
				&& !LtsConstant.LTS_PRODUCT_TYPE_NEW.equals(ltsService.getFromProd())){
			serviceProfile.setExistEyeType(StringUtils.lowerCase(ltsService.getFromProd()));
		}
		
		if (imsService != null) {
			serviceProfile.setImsTenure(StringUtils.isNotEmpty(imsService.getTenure()) ? Integer.parseInt(imsService.getTenure()) : 0);	
		}
		
		
		return serviceProfile;
	}
	
	
	private AddressRolloutDTO createAddressRollout(SbOrderDTO sbOrder) {
		AddressDetailLtsDTO addressDetail = sbOrder.getAddress();
		return addressRolloutService.getAddressRollout(addressDetail.getSerbdyno(), addressDetail.getUnitNo(), addressDetail.getFloorNo());
	}
	
	private AddressDetailProfileLtsDTO createAddressDetailProfileLts(SbOrderDTO sbOrder) {
		AddressDetailProfileLtsDTO profileAddress = new AddressDetailProfileLtsDTO();
		
		AddressDetailLtsDTO addressDetail = sbOrder.getAddress();
		
		if (addressDetail == null) {
			return null;
		}
		
		profileAddress.setAddressRollout(createAddressRollout(sbOrder));
		profileAddress.setArea(addressDetail.getAreaDesc());
		profileAddress.setAreaCd(addressDetail.getAreaCd());
		profileAddress.setBuildName(addressDetail.getBuildNo());
		profileAddress.setDistrict(addressDetail.getDistDesc());
		profileAddress.setDistrictCd(addressDetail.getDistNo());
		profileAddress.setFloorNum(addressDetail.getFloorNo());
		profileAddress.setHlotNum(addressDetail.getHiLotNo());
		profileAddress.setSectCd(addressDetail.getSectCd());
		profileAddress.setSectDesc(addressDetail.getSectDesc());
		profileAddress.setSrvBdry(addressDetail.getSerbdyno());
		profileAddress.setStreetCat(addressDetail.getStrCatDesc());
		profileAddress.setStreetCatCd(addressDetail.getStrCatCd());
		profileAddress.setStreetNum(addressDetail.getStrNo());
		profileAddress.setStreetName(addressDetail.getStrName());
		profileAddress.setUnitNum(addressDetail.getUnitNo());
		
		return profileAddress;
	}
	
	private CustomerDetailProfileLtsDTO createCustomerDetailProfileLts(ServiceDetailDTO serviceDetail) {
		
		CustomerDetailLtsDTO customerDetailLts = serviceDetail.getAccount().getCustomer();
		
		if (customerDetailLts == null) {
			return null;
		}
		
		CustomerDetailProfileLtsDTO profileCustomer = new CustomerDetailProfileLtsDTO();
		profileCustomer.setBlacklistCustInd(StringUtils.equals("Y", customerDetailLts.getBlacklistInd()) );
		profileCustomer.setCustNum(customerDetailLts.getCustNo());
//		profileCustomer.setCustRemarks(custRemarks)
		profileCustomer.setDocNum(customerDetailLts.getIdDocNum());
		profileCustomer.setDocType(customerDetailLts.getIdDocType());
		profileCustomer.setFirstName(customerDetailLts.getFirstName());
		profileCustomer.setIdVerifyInd(StringUtils.equals("Y", customerDetailLts.getIdVerifiedInd()));
		profileCustomer.setLastName(customerDetailLts.getLastName());
//		profileCustomer.setParentCustNum(parentCustNum)
		profileCustomer.setTitle(customerDetailLts.getTitle());
//		profileCustomer.setWipInd()
//		profileCustomer.setWipMessage(wipMessage)
		return profileCustomer;
	}
	
	
	private PaymentMethodDetailLtsDTO getExistingPaymentMethod(PaymentMethodDetailLtsDTO[] paymentMethods) {
//		PaymentMethodDetailLtsDTO[] paymentMethods = serviceDetail.getAccount().getPaymethods();
		
		if (paymentMethods == null || ArrayUtils.isEmpty(paymentMethods)) {
			return null;
		}
		for (PaymentMethodDetailLtsDTO paymentMethodDetailLts : paymentMethods) {
			if (StringUtils.equals(LtsConstant.IO_IND_SPACE, paymentMethodDetailLts.getAction())
					|| StringUtils.equals(LtsConstant.IO_IND_OUT, paymentMethodDetailLts.getAction())) {
				return paymentMethodDetailLts;
			}
		}
		return null;
	}
	
	
	private AccountDetailProfileLtsDTO createAccountDetailProfileLts(ServiceDetailDTO serviceDetail) {

		PaymentMethodDetailLtsDTO profilePaymentMethod = getExistingPaymentMethod(serviceDetail.getAccount().getPaymethods());
		AccountDetailProfileLtsDTO accountDetailProfileLts = new AccountDetailProfileLtsDTO();
		
		profilePaymentMethod = getExistingPaymentMethod(serviceDetail.getAccount().getPaymethods());
		accountDetailProfileLts.setAcctNum(serviceDetail.getAccount().getAcctNo());
		accountDetailProfileLts.setBillFreq(serviceDetail.getAccount().getBillFreq());
		accountDetailProfileLts.setBillLang(serviceDetail.getAccount().getBillLang());
		accountDetailProfileLts.setBillMedia(serviceDetail.getAccount().getExistBillMedia());
		accountDetailProfileLts.setPrimaryAcctInd(true);
		
		/*profilePaymentMethod could be null if it is recontract order*/
		if(profilePaymentMethod != null){
			accountDetailProfileLts.setPayMethod(profilePaymentMethod.getPayMtdType());
			accountDetailProfileLts.setBankAcctNum(profilePaymentMethod.getBankAcctNo());
			accountDetailProfileLts.setBankCd(profilePaymentMethod.getBankCd());
			accountDetailProfileLts.setBranchCd(profilePaymentMethod.getBranchCd());
			accountDetailProfileLts.setCreditCardNum(profilePaymentMethod.getCcNum());
		}
		
		return accountDetailProfileLts;
	}
	
//	private ImsSbOrderDTO createRelatedPcdOrder(SbOrderDTO sbOrder, TerminateOrderCaptureDTO orderCapture) {
//		ServiceDetailOtherLtsDTO imsService = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(sbOrder);
//		if (imsService == null || StringUtils.isEmpty(imsService.getRelatedSbOrderId())) {
//			return null;
//		}
//		
//		return ltsRelatedPcdOrderService.retrieveTerminatePcdSbOrder(imsService.getRelatedSbOrderId(), orderCapture, false);
//	}
	
	private LtsTerminateServiceSelectionFormDTO createLtsTerminateServiceSelectionForm (SbOrderDTO sbOrder) {
		
		LtsTerminateServiceSelectionFormDTO form = new LtsTerminateServiceSelectionFormDTO();
		ServiceDetailLtsDTO termService = LtsSbHelper.getLtsService(sbOrder);
		ServiceDetailOtherLtsDTO imsService = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(sbOrder);
		 
		form.setDisconnectReason(termService.getDiscReasonCode());
		form.setDeceasedType(termService.getDeceaseType());
		form.setThirdParty(StringUtils.equals("Y", termService.getThirdPartyAppln()));
		
		if(imsService != null && StringUtils.isNotBlank(imsService.getLostModem())){
			form.setLostModem(true);
			form.setLostModemUsageInd(imsService.getLostModem());
		}
		
		if (StringUtils.isNotEmpty(termService.getActualDocId())
				&& StringUtils.isNotEmpty(termService.getActualDocType())) {
//			ltsCustomerIdentificationForm.setCustId(termService.getActualDocId());
//			ltsCustomerIdentificationForm.setCustDocType(termService.getActualDocType());
		}
		
		return form;
		
	}
	
	private LtsTerminateSupportDocFormDTO createLtsTerminateSupportDocForm(SbOrderDTO sbOrder) {
		LtsTerminateSupportDocFormDTO ltsSupportDocForm = new LtsTerminateSupportDocFormDTO();
		ltsSupportDocForm.setCollectMethod(sbOrder.getCollectMethod());
		ltsSupportDocForm.setDistributionMode(sbOrder.getDisMode());
		ltsSupportDocForm.setSupportDocumentList(createSupportDocumentList(sbOrder));
		ltsSupportDocForm.setDistributeSms(sbOrder.getSmsNo());
		ltsSupportDocForm.setDistributeLang(sbOrder.getEsigEmailLang());
		return ltsSupportDocForm;
	}
	
	private List<OrderDocDTO> createSupportDocumentList(SbOrderDTO sbOrder) {
		if (ArrayUtils.isEmpty(sbOrder.getAllOrdDocAssgns())) {
			return null;
		}
		
		List<OrderDocDTO> supportDocumentList = new ArrayList<OrderDocDTO>();
		OrderDocDTO orderDoc = null;
		for (AllOrdDocAssgnLtsDTO allOrdDocAssgnLts : sbOrder.getAllOrdDocAssgns()) {
			if (StringUtils.equals("Y", allOrdDocAssgnLts.getMarkDelInd())) {
				continue;
			}
			
			orderDoc = ltsOrderDocumentService.getOrderDoc(allOrdDocAssgnLts.getDocType());
			
			if (orderDoc == null) {
				continue;
			}
			
			orderDoc.setCollected(StringUtils.equals("Y", allOrdDocAssgnLts.getCollectedInd()));
			orderDoc.setMarkDelete(StringUtils.equals("Y", allOrdDocAssgnLts.getMarkDelInd()));
			orderDoc.setWaiveReasonCd(allOrdDocAssgnLts.getWaiveReason());
			orderDoc.setWaiveReasonBy(allOrdDocAssgnLts.getWaivedBy());
			supportDocumentList.add(orderDoc);
		}
		return supportDocumentList;
	}
	
	
	
}
