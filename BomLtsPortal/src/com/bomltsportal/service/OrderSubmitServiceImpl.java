package com.bomltsportal.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomltsportal.dto.DefaultItemListDTO;
import com.bomltsportal.dto.FsaServiceAssgnDTO;
import com.bomltsportal.dto.OnlineBasketDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.ApplicantInfoFormDTO;
import com.bomltsportal.dto.form.BasketDetailFormDTO;
import com.bomltsportal.dto.form.VasDetailFormDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.LtsDateFormatHelper;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dto.AddressDetailDTO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ChannelAttbDTO;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetAttbDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AddressInventoryDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.ChannelAttbLtsDTO;
import com.bomwebportal.lts.dto.order.ChannelDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ContactLtsDTO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.NowtvDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;

public class OrderSubmitServiceImpl implements OrderSubmitService {

	
	private BasketDetailService basketDetailService;
	private ItemDetailService itemDetailService;
	private OrderSaveService orderSaveService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private ShopService shopService;
	
	public OrderSaveService getOrderSaveService() {
		return orderSaveService;
	}

	public void setOrderSaveService(OrderSaveService orderSaveService) {
		this.orderSaveService = orderSaveService;
	}

	public ShopService getShopService() {
		return shopService;
	}

	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public BasketDetailService getBasketDetailService() {
		return basketDetailService;
	}

	public void setBasketDetailService(BasketDetailService basketDetailService) {
		this.basketDetailService = basketDetailService;
	}

	public ItemDetailService getItemDetailService() {
		return itemDetailService;
	}

	public void setItemDetailService(ItemDetailService itemDetailService) {
		this.itemDetailService = itemDetailService;
	}
	
	/*
	 * create sbOrderDTO from orderCaptureDTO
	 */
	public SbOrderDTO submitOrder(OrderCaptureDTO orderCapture) {

		preSubmitOrder(orderCapture);
		
		SbOrderDTO sessionOrder = orderCapture.getSbOrder();
		SbOrderDTO newOrder = createSbOrder(orderCapture);
		if (sessionOrder != null) {
			SbOrderDTO existingOrder = orderRetrieveLtsService.retrieveSbOrder(sessionOrder.getOrderId(), false);
			if (existingOrder != null) {
				return orderSaveService.saveExistingOrder(existingOrder, newOrder);	
			}
		}
		return orderSaveService.saveNewOrder(newOrder);
	}

	
	private void preSubmitOrder(OrderCaptureDTO orderCapture) {
		orderCapture.setDefaultItemList(getDefaultItemList(orderCapture));
	}
	
	private DefaultItemListDTO getDefaultItemList(OrderCaptureDTO orderCapture) {
		
		DefaultItemListDTO defaultItemList = new DefaultItemListDTO();
		
		defaultItemList.setIddVasItemList(itemDetailService.getItemList(
				orderCapture.getChannelId(),
				BomLtsConstant.ITEM_TYPE_IDD,
				BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC,
				BomLtsConstant.LOCALE_ENG, true, false));
		
		if (StringUtils.equals(LtsBackendConstant.SERVICE_TYPE_EYE, orderCapture.getServiceTypeInd())) {
		
			defaultItemList.setNowTvFreeItemList(itemDetailService
					.getBasketItemList(orderCapture.getChannelId(), orderCapture
							.getBasketSelectForm().getSelectedBasketId(),
							BomLtsConstant.ITEM_TYPE_NOWTV_FREE,
							BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC,
							BomLtsConstant.LOCALE_ENG, true, false));
			
			defaultItemList.setE0060VasItemList(itemDetailService.getItemList(
					orderCapture.getChannelId(),
					BomLtsConstant.ITEM_TYPE_0060E,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC,
					BomLtsConstant.LOCALE_ENG, true, false));
			
			
			defaultItemList.setPeFreeItemList(itemDetailService.getBasketItemList(
					orderCapture.getChannelId(),
					orderCapture.getBasketSelectForm().getSelectedBasketId(),				
					BomLtsConstant.ITEM_TYPE_PE_FREE,
					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC,
					BomLtsConstant.LOCALE_ENG, true, false));
			
//			defaultItemList.setInstallWaiveItemList(itemDetailService.getBasketItemList(
//					orderCapture.getChannelId(),
//					orderCapture.getBasketSelectForm().getSelectedBasketId(),				
//					BomLtsConstant.ITEM_TYPE_INSTALL_WAIVE,
//					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC,
//					BomLtsConstant.LOCALE_ENG, true, false));
//			
//			defaultItemList.setInstallItemList(itemDetailService.getBasketItemList(
//					orderCapture.getChannelId(),
//					orderCapture.getBasketSelectForm().getSelectedBasketId(),				
//					BomLtsConstant.ITEM_TYPE_INSTALL_WAIVE,
//					BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC,
//					BomLtsConstant.LOCALE_ENG, true, false));
//			
		}
		return defaultItemList;
	}
	
	private SbOrderDTO createSbOrder(OrderCaptureDTO orderCapture){
		
		List<CustomerDetailLtsDTO> customerDetailLtsList = new ArrayList<CustomerDetailLtsDTO>();
		List<AccountDetailLtsDTO> accountDetailLtsList = new ArrayList<AccountDetailLtsDTO>();
		
		SbOrderDTO sbOrder = new SbOrderDTO();
		sbOrder.setLob(BomLtsConstant.LOB_LTS);
		sbOrder.setAppDate(getApplicationDate());	
		sbOrder.setSrvDtls(createServiceDetail(orderCapture, accountDetailLtsList, customerDetailLtsList));
		sbOrder.setAccounts(accountDetailLtsList.toArray(new AccountDetailLtsDTO[accountDetailLtsList.size()]));
		sbOrder.setCustomers(customerDetailLtsList.toArray(new CustomerDetailLtsDTO[customerDetailLtsList.size()]));
		sbOrder.setAddress(createAddressDetail(orderCapture));
		sbOrder.setContact(createContactInfo(orderCapture));
		sbOrder.setEsigEmailAddr(orderCapture.getApplicantInfoForm().getContactEmailAddr());
		sbOrder.setEsigEmailLang(StringUtils.equals(BomLtsConstant.LOCALE_ENG, orderCapture.getLang()) ? BomLtsConstant.EMAIL_ESIG_LANG_ENG : BomLtsConstant.EMAIL_ESIG_LANG_CHN);
		sbOrder.setDisMode(BomLtsConstant.DISTRIBUTE_MODE_ESIGNATURE);
		sbOrder.setSalesChannel(shopService.getSalesChannel(BomLtsConstant.OLS_SHOP_CD));
		sbOrder.setShopCd(BomLtsConstant.OLS_SHOP_CD);
		sbOrder.setStaffNum(BomLtsConstant.USER_ID);
		sbOrder.setOrderType(BomLtsConstant.ORDER_TYPE_ONLINE_ACQUISITION);
		return sbOrder;
	}
	
	private ContactLtsDTO createContactInfo(OrderCaptureDTO orderCapture){
		ContactLtsDTO contactInfo = new ContactLtsDTO();
		ApplicantInfoFormDTO applicantInfoForm = orderCapture.getApplicantInfoForm();
		CustomerDetailProfileLtsDTO customerProfile = orderCapture.getCustomerDetailProfile();
		
		contactInfo.setContactName(applicantInfoForm.getFamilyName() + " " + applicantInfoForm.getGivenName());
		contactInfo.setTitle(applicantInfoForm.getTitle());
		contactInfo.setIdDocType(applicantInfoForm.getDocType());
		contactInfo.setIdDocNum(applicantInfoForm.getDocNum());
		contactInfo.setEmailAddr(applicantInfoForm.getContactEmailAddr());
		contactInfo.setContactMobile(applicantInfoForm.getContactMobileNum());
		contactInfo.setContactPhone(applicantInfoForm.getContactMobileNum());
		if(customerProfile != null){
			contactInfo.setCustNo(customerProfile.getCustNum());
		}else{
			contactInfo.setCustNo("-1");
		}
		
		return contactInfo;
	}

	private String getApplicationDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return dateFormat.format(new Date());
	}
	
	private AccountServiceAssignLtsDTO[] createAccountServiceAssigns(
			OrderCaptureDTO orderCapture,
			List<CustomerDetailLtsDTO> customerDetailLtsList,
			List<AccountDetailLtsDTO> accountDetailLtsList) {
		
		List<AccountServiceAssignLtsDTO> accountServiceAssignList = new ArrayList<AccountServiceAssignLtsDTO>();
		
		String[] chrgTypes = {BomLtsConstant.ACCOUNT_CHARGE_TYPE_R,
								BomLtsConstant.ACCOUNT_CHARGE_TYPE_I,
								BomLtsConstant.ACCOUNT_CHARGE_TYPE_A,
								BomLtsConstant.ACCOUNT_CHARGE_TYPE_P};
		
		AccountDetailLtsDTO dummyAccDtl = createAccountDetail(orderCapture, customerDetailLtsList, accountDetailLtsList);
		
		for(String chrgType: chrgTypes){
			AccountServiceAssignLtsDTO profileSrvAccountAssign = new AccountServiceAssignLtsDTO();
			profileSrvAccountAssign.setAccount(dummyAccDtl);
			profileSrvAccountAssign.setAction(BomLtsConstant.IO_IND_INSTALL);
			profileSrvAccountAssign.setChrgType(chrgType);
			accountServiceAssignList.add(profileSrvAccountAssign);	
		}
		
		return accountServiceAssignList.toArray(new AccountServiceAssignLtsDTO[accountServiceAssignList.size()]);
	}
	
	
	private AccountDetailLtsDTO createAccountDetail(
			OrderCaptureDTO orderCapture,
			List<CustomerDetailLtsDTO> customerDetailLtsList,
			List<AccountDetailLtsDTO> accountDetailLtsList) {

		AccountDetailLtsDTO accountDetail = new AccountDetailLtsDTO();
		
		
		accountDetail.setAcctNo(BomLtsConstant.DUMMY_ACCT_NO);
		accountDetail.setCustomer(createCustomerDetail(orderCapture, customerDetailLtsList));

//		accountDetail.setBillMedia(BomLtsConstant.LTS_BILL_MEDIA_ONLINE_BILL);
		accountDetail.setBillMedia(BomLtsConstant.LTS_BILL_MEDIA_PPS_BILL);
		
		accountDetail.setEmailAddr(orderCapture.getApplicantInfoForm().getContactEmailAddr());
//		accountDetail.setBillFmt(billFmt);
//		accountDetail.setPaymethods(new PaymentMethodDetailLtsDTO[] { createPaymentMethodDetail(orderCapture) });
		accountDetail.setBillFreq(BomLtsConstant.ACCT_BILL_FREQ_MONTHLY);
		accountDetail.setBillLang(orderCapture.getApplicantInfoForm().getBillLang());
		accountDetail.setBillingAddress(createBillingAddressLtsDTO());
		accountDetailLtsList.add(accountDetail);
		return accountDetail;
	}
	
	private BillingAddressLtsDTO createBillingAddressLtsDTO() {
		BillingAddressLtsDTO billingAddress = new BillingAddressLtsDTO();
		billingAddress.setAction(BomLtsConstant.BILL_ADDR_ACTION_IA);
		return billingAddress;
	}
	
	
	private PaymentMethodDetailLtsDTO createPaymentMethodDetail(OrderCaptureDTO orderCapture) {
		
		final String CC_HOLDER_NAME = "ONLINE APPLICATION";
		
		PaymentMethodDetailLtsDTO paymentMethod = new PaymentMethodDetailLtsDTO();
		paymentMethod.setAction(BomLtsConstant.IO_IND_INSTALL);
		
		paymentMethod.setCcHoldName(CC_HOLDER_NAME);
		paymentMethod.setCcIdDocNo(orderCapture.getApplicantInfoForm().getDocNum());
		paymentMethod.setCcIdDocType(orderCapture.getApplicantInfoForm().getDocType());
		paymentMethod.setCcIssueBank("999");
		if(!StringUtils.isEmpty(orderCapture.getCreditPaymentForm().getCcExpDate())){
			paymentMethod.setCcExpDate(LtsDateFormatHelper.reformatDate(orderCapture.getCreditPaymentForm().getCcExpDate(), "MMyy", "MM/yyyy"));
		}
//		String expDate = LtsDateFormatHelper.reformatDate(orderCapture.getCreditPaymentForm().getCcExpDate(), "MMyy", "MM/yyyy");
//		paymentMethod.setCcExpDate(expDate);
		paymentMethod.setCcNum(orderCapture.getCreditPaymentForm().getCcNum());
		paymentMethod.setCcType(orderCapture.getCreditPaymentForm().getBomCcType());
		paymentMethod.setPayMtdKey("-1");
		paymentMethod.setPayMtdType(BomLtsConstant.PAYMENT_TYPE_CREDIT_CARD);
		
		if (orderCapture.getSummaryForm() != null) {
			paymentMethod.setPrepayAmt(Integer.toString(orderCapture.getSummaryForm().getPaymentAmount()));
		}
		
		
		return paymentMethod;
	}
	
	private CustomerDetailLtsDTO createCustomerDetail(OrderCaptureDTO orderCapture, List<CustomerDetailLtsDTO> customerDetailLtsList ) {
		
		CustomerDetailLtsDTO customerDetail = new CustomerDetailLtsDTO();
		
		CustomerDetailProfileLtsDTO customerProfile = orderCapture.getCustomerDetailProfile();
		ApplicantInfoFormDTO applicantInfoForm = orderCapture.getApplicantInfoForm();
		
		customerDetail.setLob(BomLtsConstant.LOB_LTS);
		customerDetail.setIdVerifiedInd("N");
		customerDetail.setIndType(BomLtsConstant.ONLINE_INDUSTRY_TYPE);
		customerDetail.setIndSubType(BomLtsConstant.ONLINE_INDUSTRY_SUB_TYPE);
		customerDetail.setDob(LtsDateFormatHelper.reformatDate(applicantInfoForm.getDateOfBirth(), "yyyy/MM/dd", "dd/MM/yyyy"));
		customerDetail.setFirstName(applicantInfoForm.getGivenName());
		customerDetail.setLastName(applicantInfoForm.getFamilyName());
		customerDetail.setCsPortalInd(applicantInfoForm.isCsPortalTheClubConfirm() ? "Y" : applicantInfoForm.isCsPortalConfirm() ? "Y" : "R");
		customerDetail.setTheClubInd(applicantInfoForm.isCsPortalTheClubConfirm() ? "Y" : applicantInfoForm.isTheClubConfirm() ? "Y" : "R");
		if(applicantInfoForm.isCsPortalTheClubConfirm()||applicantInfoForm.isTheClubConfirm()){
			customerDetail.setClubOptOut(applicantInfoForm.isCsPortalTheClubOptIn()? "N" : applicantInfoForm.isTheClubOptIn() ? "N" : "Y");
		}
		if(applicantInfoForm.isCsPortalTheClubConfirm()||applicantInfoForm.isCsPortalConfirm()){
			customerDetail.setHktOptOut(applicantInfoForm.isCsPortalTheClubOptIn()? "N" : applicantInfoForm.isCsPortalOptIn() ? "N" : "Y");
		}
		customerDetail.setCsPortalLogin(applicantInfoForm.getContactEmailAddr());
		customerDetail.setCsPortalMobile(applicantInfoForm.getContactMobileNum());
		customerDetail.setTheClubLogin(applicantInfoForm.getContactEmailAddr());
		customerDetail.setTheClubMobile(applicantInfoForm.getContactMobileNum());
		
		if (customerProfile != null) {
			customerDetail.setBlacklistInd(customerProfile.isBlacklistCustInd() ? "Y" : null);
			customerDetail.setCustNo(customerProfile.getCustNum());
			customerDetail.setIdDocNum(customerProfile.getDocNum());
			customerDetail.setIdDocType(customerProfile.getDocType());
			customerDetail.setTitle(customerProfile.getTitle());
		}
		else {
			customerDetail.setCustNo(BomLtsConstant.DUMMY_CUST_NO);
			customerDetail.setIdDocNum(applicantInfoForm.getDocNum());
			customerDetail.setIdDocType(applicantInfoForm.getDocType());
			customerDetail.setTitle(applicantInfoForm.getTitle());
		}
		customerDetail.setCustOptOutInfo(createCustOptOutInfo(orderCapture));
		customerDetailLtsList.add(customerDetail);
		return customerDetail;
	}
	
	private CustOptOutInfoLtsDTO createCustOptOutInfo(OrderCaptureDTO orderCapture){
		if(!orderCapture.getApplicantInfoForm().isCustExist()){
			CustOptOutInfoLtsDTO custOptOutInfo = new CustOptOutInfoLtsDTO();
			String ind = "N";
			if(orderCapture.getApplicantInfoForm().isPrivacyR()){
				ind = "Y";
			}
			custOptOutInfo.setEmail(ind);
			custOptOutInfo.setDirectMailing(ind);
			custOptOutInfo.setInternet(ind);
			custOptOutInfo.setNonsalesSms(ind);
			custOptOutInfo.setOutbound(ind);
			custOptOutInfo.setSms(ind);
			custOptOutInfo.setWebBill(ind);
			
			return custOptOutInfo;
		}else{
			return null;
		}
		
	}
	
	private AddressDetailLtsDTO createAddressDetail(OrderCaptureDTO orderCapture){
		AddressDetailLtsDTO addressDetailLts = new AddressDetailLtsDTO();
		AddressRolloutDTO addressRollout = orderCapture.getAddressRollout();
		AddressDetailDTO addressDetail = orderCapture.getAddressDetail();
		BuildingMarkerDTO buildingMarker = orderCapture.getAddressLookupForm().getBuildingMarker();
		
		if(addressRollout != null){
			addressDetailLts.setAreaCd(addressDetail.getAreaCd());
			addressDetailLts.setBuildNo(addressDetail.getBuildName());
			addressDetailLts.setDistNo(addressDetail.getDistrictCd());
			addressDetailLts.setFloorNo(addressRollout.getFloor());
			addressDetailLts.setSectCd(addressDetail.getSectCd());
			addressDetailLts.setSerbdyno(addressRollout.getSrvBdary());
			addressDetailLts.setStrNo(addressDetail.getStreetNum());
			addressDetailLts.setUnitNo(addressRollout.getFlat());
//			addressDetailLts.setBlacklistInd(addressRollout.isImsAddressBlacklist()?"Y":null);
			addressDetailLts.setLtsBlacklistInd(addressRollout.isLtsAddressBlacklist()?"Y":null);
			addressDetailLts.setAddrUsage("IA"); //?
			
			addressDetailLts.setAddrInventory(createAddressInventoryDetailLts(orderCapture));
			
			addressDetailLts.setDistDesc(addressDetail.getDistrict());
			addressDetailLts.setStrName(addressDetail.getStreetName());
			addressDetailLts.setAreaDesc(addressDetail.getArea());
			addressDetailLts.setSectDesc(addressDetail.getSectDesc());
			
			addressDetailLts.setHiLotNo(addressDetail.getHlotNum());
			addressDetailLts.setStrCatCd(addressDetail.getStreetCatCd());
			addressDetailLts.setStrCatDesc(addressDetail.getStreetCat());
			addressDetailLts.setBuildXy(buildingMarker.getBuildXy());
			addressDetailLts.setDisFullAddrDescEn(getFullAddressEn(buildingMarker, addressRollout));
			addressDetailLts.setDisFullAddrDescCh(getFullAdressCh(buildingMarker, addressRollout));
		}
		return addressDetailLts;
	}

	private String getFullAddressEn(BuildingMarkerDTO buildingMarker, AddressRolloutDTO addressRollout) {
		
		StringBuilder addressEn = new StringBuilder();
		
		if (StringUtils.isNotBlank(addressRollout.getFlat())) {
			addressEn.append("RM ").append(addressRollout.getFlat()).append(", ");
		}
		
		if (StringUtils.isNotBlank(addressRollout.getFloor())) {
			addressEn.append(addressRollout.getFloor()).append("/F, ");
		}
		addressEn.append(buildingMarker.getAddressEn());
		return addressEn.toString();
	}
	
	private String getFullAdressCh(BuildingMarkerDTO buildingMarker, AddressRolloutDTO addressRollout) {
		StringBuilder addressCh = new StringBuilder();
		addressCh.append(buildingMarker.getAddressCh());
		
		if (StringUtils.isNotBlank(addressRollout.getFloor())) {
			addressCh.append(addressRollout.getFloor()).append("\u6A13");
		}
		
		if (StringUtils.isNotBlank(addressRollout.getFlat())) {
			addressCh.append(addressRollout.getFlat()).append("\u5BA4");
		}
		
		return addressCh.toString();
	}
	
	private AddressInventoryDetailLtsDTO createAddressInventoryDetailLts(OrderCaptureDTO orderCapture) {
		AddressInventoryDetailLtsDTO addressInventory = new AddressInventoryDetailLtsDTO();
		AddressRolloutDTO addressRollout = orderCapture.getAddressRollout();
		
		if(addressRollout != null){
	
			addressInventory.setBuildingType(addressRollout.getHousingType());
			addressInventory.setFieldWorkPermitDay(addressRollout.getFieldWorkPermit());
			addressInventory.setFttcInd(addressRollout.isFttcInd() ? "Y" : null);
			addressInventory.setMaxBandwidth(addressRollout.getMaximumBandwidth());
			addressInventory.setResourceShortageInd(orderCapture.getFsaServiceAssgn().isBbShortage() ? "Y" : null);
			
			if (addressRollout.isIs2nBuilding()) {
				addressInventory.setN2BuildingInd(StringUtils.defaultIfEmpty(addressRollout.getN2BuildingInd(), "Y"));	
			}
			
			if (StringUtils.equals(BomLtsConstant.SERVICE_TYPE_EYE, orderCapture.getServiceTypeInd())) {
				addressInventory.setTechnology(orderCapture.getFsaServiceAssgn().getTechnology());				
			}
		}
		return addressInventory;
	}

	private ServiceDetailDTO[] createServiceDetail(OrderCaptureDTO orderCapture, List<AccountDetailLtsDTO> accountDetailLtsList, List<CustomerDetailLtsDTO> customerDetailLtsList){
		
		List<ServiceDetailDTO> serviceDetailList = new ArrayList<ServiceDetailDTO>();
		
		
		ServiceDetailDTO serviceDetailLts = createServiceDetailLts(orderCapture, accountDetailLtsList, customerDetailLtsList);
		
		serviceDetailList.add(serviceDetailLts);
		
		if (StringUtils.equals(BomLtsConstant.SERVICE_TYPE_EYE, orderCapture.getServiceTypeInd())) {
			ServiceDetailDTO serviceDetailOther = createServiceDetailOther(orderCapture, serviceDetailLts.getSrvNum());
			serviceDetailList.add(serviceDetailOther);
		}
		
		
		return serviceDetailList.toArray(new ServiceDetailDTO[serviceDetailList.size()]);
	}
	
	private ServiceDetailDTO createServiceDetailOther(OrderCaptureDTO orderCapture, String ltsServiceNum) {
		
		
		FsaServiceDetailOutputDTO profileFsaService = orderCapture.getImsServiceProfile();
		FsaServiceAssgnDTO fsaServiceAssgn = orderCapture.getFsaServiceAssgn();
		
		ServiceDetailOtherLtsDTO serviceDetailOther = new ServiceDetailOtherLtsDTO();
		
		serviceDetailOther.setAppointmentDtl(createAppointmentDetailLtsDTO(orderCapture));
		serviceDetailOther.setGrpType(LtsBackendConstant.GROUP_TYPE_EYE);
		serviceDetailOther.setTypeOfSrv(LtsBackendConstant.SERVICE_TYPE_IMS);
		
		serviceDetailOther.setOrderType(fsaServiceAssgn.getImsOrderType());
		serviceDetailOther.setAssgnBandwidth(fsaServiceAssgn.getBandwidth());
		serviceDetailOther.setAssgnTechnology(fsaServiceAssgn.getTechnology());
		serviceDetailOther.setModemArrangement(fsaServiceAssgn.getModem());
		
		serviceDetailOther.setChannels(createChannelDetailLts(orderCapture));
		serviceDetailOther.setLoginId(getLoginId(fsaServiceAssgn.getImsOrderType(), profileFsaService, ltsServiceNum));
		
//		serviceDetailOther.setSrvTypeCdAction(getFsaSrvTypeCdAction(profileFsaService));
//		serviceDetailOther.setSuggestSrd(suggestSrd)
//		serviceDetailOther.setSuggestSrdReasonCd(suggestSrdReasonCd)
		serviceDetailOther.setFromProd(getImsFromProductType(profileFsaService));
		serviceDetailOther.setToProd(getImsToProductType(profileFsaService));
		
		
		if (profileFsaService != null) {
			serviceDetailOther.setPendingOcid(profileFsaService.getPendingOcid());	
			serviceDetailOther.setPendingOcidSrd(profileFsaService.getPendingOrderSrd());
			serviceDetailOther.setSrvNum(profileFsaService.getFsa());
			serviceDetailOther.setDeactNowTvInd(profileFsaService.getDeactNowtvInd());
			serviceDetailOther.setExistBandwidth(profileFsaService.getBandwidth());
			serviceDetailOther.setExistModem(profileFsaService.getExistModem());
			serviceDetailOther.setExistSrvTypeCd(profileFsaService.getSrvType());
			serviceDetailOther.setExistTechnology(profileFsaService.getTechnology());
			serviceDetailOther.setNewBandwidth(profileFsaService.getBandwidth());
			serviceDetailOther.setNewSrvTypeCd(profileFsaService.getSrvType());
			serviceDetailOther.setTenure(profileFsaService.getTenure());
			
			if (!StringUtils.equals(fsaServiceAssgn.getImsOrderType(), BomLtsConstant.ORDER_TYPE_INSTALL)) {
				serviceDetailOther.setCcServiceId(profileFsaService.getFsa());	
			}
		}
		
//		serviceDetailOther.setRemarks(remarks)
		serviceDetailOther.setNowtvDetail(createNowTvDetailLts(orderCapture));		
		serviceDetailOther.setNowtvTvCd(getNowBundleTvCredit(orderCapture));
		
		return serviceDetailOther;
	}
	
	
	private String getImsFromProductType(FsaServiceDetailOutputDTO profileFsaService) {
		if (profileFsaService == null) {
			return BomLtsConstant.IMS_PRODUCT_TYPE_NEW;
		}
		return StringUtils.replace(profileFsaService.getSrvType(), "TV", "");
	}
	
	private String getImsToProductType(FsaServiceDetailOutputDTO profileFsaService) {
		if (profileFsaService == null) {
			return BomLtsConstant.IMS_PRODUCT_TYPE_WALL_GARDEN;
		}
		return StringUtils.replace(profileFsaService.getSrvType(), "TV", "");
	}
	
	private String getNowBundleTvCredit(OrderCaptureDTO orderCapture) {
	
		if (orderCapture.getBasketDetailForm().getContItemSetList() != null 
				&& !orderCapture.getBasketDetailForm().getContItemSetList().isEmpty()) {
			
			for (ItemSetDetailDTO contSet : orderCapture.getBasketDetailForm().getContItemSetList()) {
				for (ItemDetailDTO itemDetail : contSet.getItemDetails()) {
					if (!itemDetail.isSelected()) {
						continue;
					}
					if (StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_PAY, itemDetail.getItemType()) 
							|| StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_SPEC, itemDetail.getItemType()) ){
						return String.valueOf(Integer.parseInt(itemDetail.getCredit()) * 100);
					}
				}
			}
		}
		
		
		
		if (orderCapture.getVasDetailForm().getNowTvSpecItems() != null &&
				!orderCapture.getVasDetailForm().getNowTvSpecItems().isEmpty()) {
			for (ItemDetailDTO itemDetail : orderCapture.getVasDetailForm().getNowTvSpecItems()) {
				if (itemDetail.isSelected()) {
					return itemDetail.getCredit();
				}
			}
		}
		
		if (orderCapture.getVasDetailForm().getNowTvPayItems() != null && 
				!orderCapture.getVasDetailForm().getNowTvPayItems().isEmpty()) {
			for (ItemDetailDTO itemDetail : orderCapture.getVasDetailForm().getNowTvPayItems()) {
				if (itemDetail.isSelected()) {
					return itemDetail.getCredit();
				}
			}
		}
		
		return null;
	}
	
	private List<ChannelGroupDetailDTO> getSelectedChannelGroupList(OrderCaptureDTO orderCapture) {
		
		BasketDetailFormDTO basketDetailForm = orderCapture.getBasketDetailForm();
		
		if (orderCapture.getBasketDetailForm().getContItemSetList() != null 
				&& !orderCapture.getBasketDetailForm().getContItemSetList().isEmpty()) {
			for (ItemSetDetailDTO contSetDetail : orderCapture.getBasketDetailForm().getContItemSetList()) {
				if (ArrayUtils.isEmpty(contSetDetail.getItemDetails())) {
					continue;
				}
				for (ItemDetailDTO itemDetail : contSetDetail.getItemDetails()) {
					if (StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_PAY, itemDetail.getItemType()) 
							|| StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_SPEC, itemDetail.getItemType())) {
						if (itemDetail.isSelected()) {
							return basketDetailForm.getChannelGroupList();
						}
					}
				}
			}
		}
		
		VasDetailFormDTO vasDetailForm = orderCapture.getVasDetailForm();
		if (itemDetailService.isItemSelected(vasDetailForm.getNowTvPayItems(), BomLtsConstant.ITEM_TYPE_NOWTV_PAY)) {
			return vasDetailForm.getPayChannelGroupList();
		}
				
		if (itemDetailService.isItemSelected(vasDetailForm.getNowTvSpecItems(), BomLtsConstant.ITEM_TYPE_NOWTV_SPEC)) {
			return vasDetailForm.getSpecChannelGroupList();
		}
		
		return null;
	}

	private boolean isConfirmAvAdultChannel(OrderCaptureDTO orderCapture) {
		BasketDetailFormDTO basketDetailForm = orderCapture.getBasketDetailForm();
		
		if (basketDetailForm.getContItemSetList() != null && !basketDetailForm.getContItemSetList().isEmpty()) {
			for (ItemSetDetailDTO contSetDetail : basketDetailForm.getContItemSetList()) {
				if (ArrayUtils.isEmpty(contSetDetail.getItemDetails())) {
					continue;
				}
				for (ItemDetailDTO itemDetail : contSetDetail.getItemDetails()) {
					if (StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_PAY, itemDetail.getItemType()) 
							|| StringUtils.equals(BomLtsConstant.ITEM_TYPE_NOWTV_SPEC, itemDetail.getItemType())) {
						if (itemDetail.isSelected()) {
							return basketDetailForm.isSelectedAdultChannel();
						}
					}
				}
				
			}
		}
		
		VasDetailFormDTO vasDetailForm = orderCapture.getVasDetailForm();
		
		if (itemDetailService.isItemSelected(vasDetailForm.getNowTvPayItems(), BomLtsConstant.ITEM_TYPE_NOWTV_PAY) ||
				itemDetailService.isItemSelected(vasDetailForm.getNowTvSpecItems(), BomLtsConstant.ITEM_TYPE_NOWTV_SPEC)) {
			return vasDetailForm.isSelectedAdultChannel();
		}
		
		return false;
	}
	
	
	private ChannelDetailLtsDTO[] createChannelDetailLts(OrderCaptureDTO orderCapture) {
		
		
		List<ChannelDetailLtsDTO> channelDetailLtsList = new ArrayList<ChannelDetailLtsDTO>();
		
		
		List<ItemDetailDTO> nowtvFreeItemList = orderCapture.getDefaultItemList().getNowTvFreeItemList();
		
		if (nowtvFreeItemList != null && !nowtvFreeItemList.isEmpty()) {
			for (ItemDetailDTO itemDetail : nowtvFreeItemList) {
				if (itemDetail.isSelected()) {
					ChannelDetailLtsDTO channelDetailLts = new ChannelDetailLtsDTO();
					channelDetailLts.setChannelId(itemDetail.getItemId());
					channelDetailLtsList.add(channelDetailLts);
				}
			}
		}
		
		List<ChannelGroupDetailDTO> selectedChannelGroupList = getSelectedChannelGroupList(orderCapture);
		
		if (selectedChannelGroupList != null && !selectedChannelGroupList.isEmpty()) {
			for (ChannelGroupDetailDTO channelGroupDetail : selectedChannelGroupList) {
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
	
	private NowtvDetailLtsDTO createNowTvDetailLts(OrderCaptureDTO orderCapture) {
		
		NowtvDetailLtsDTO nowtvDetailLts = new NowtvDetailLtsDTO();
		nowtvDetailLts.setBillMedia(BomLtsConstant.NOWTV_BILL_MEDIA_EMAIL_BILL);
		nowtvDetailLts.setEmailAddress(orderCapture.getApplicantInfoForm().getContactEmailAddr());
		nowtvDetailLts.setEmailBillInd("Y");
		nowtvDetailLts.setDob(LtsDateFormatHelper.reformatDate(orderCapture.getApplicantInfoForm().getDateOfBirth(), "yyyy/MM/dd", "dd/MM/yyyy"));
		nowtvDetailLts.setViewAvInd(isConfirmAvAdultChannel(orderCapture) ? "Y" : null);
		
		return nowtvDetailLts;
	}
	
	

	
	private String getFsaSrvTypeCdAction(FsaServiceDetailOutputDTO profileFsaService) {
		if (profileFsaService == null) {
			return BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_WG;
		}
		
		if (StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_PCD, profileFsaService.getSrvType())) {
			return BomLtsConstant.SRV_ACTION_TYPE_CD_PCD_PCD;
		}
		
		if (StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_PCD_SDTV, profileFsaService.getSrvType())) {
			return BomLtsConstant.SRV_ACTION_TYPE_CD_PCD_SD_PCD_SD;
		}
		
		if (StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_PCD_HDTV, profileFsaService.getSrvType())) {
			return BomLtsConstant.SRV_ACTION_TYPE_CD_PCD_HD_PCD_HD ;
		}
		
		if (StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_SDTV, profileFsaService.getSrvType())) {
			return BomLtsConstant.SRV_ACTION_TYPE_CD_SD_SD;
		}
		
		if (StringUtils.equals(BomLtsConstant.FSA_SRV_TYPE_HDTV, profileFsaService.getSrvType())) {
			return BomLtsConstant.SRV_ACTION_TYPE_CD_HD_HD;
		}
		
		return null;
	}
	
	private String getLoginId(String orderType, FsaServiceDetailOutputDTO profileFsaService,
			String ltsServiceNum) {
		
		if (StringUtils.equals(orderType, LtsBackendConstant.ORDER_TYPE_INSTALL)) {
			return generateWgLoginId(ltsServiceNum);
		}
		else if (profileFsaService != null) {
			return profileFsaService.getLoginID();
		}
		return null;
	}
	
	private String generateWgLoginId(String ltsServiceNum ) {
		return "wg" + StringUtils.right(ltsServiceNum, 8);
	}
	
	
	private AppointmentDetailLtsDTO createAppointmentDetailLtsDTO(OrderCaptureDTO order){
		AppointmentDetailLtsDTO appointmentDetailLts = new AppointmentDetailLtsDTO();
		ApplicantInfoFormDTO applicantInfo = order.getApplicantInfoForm();
		if(applicantInfo == null){
			return null;
		}
		
		String timeSlot = null;
		String date = null;
		String[] dateString = null;
		
		if (StringUtils.isNotBlank(applicantInfo.getInstallDate()) 
				&& StringUtils.isNotBlank(applicantInfo.getInstallTime())) {
			timeSlot = LtsDateFormatHelper.convertPonTimeSlot(applicantInfo.getInstallTime());
			timeSlot = LtsDateFormatHelper.revertToBomTimeSlot(timeSlot);
			date = applicantInfo.getInstallDate();
			dateString = LtsDateFormatHelper.reformatDateTimeSlot(LtsDateFormatHelper.reformatDate(date, "yyyy/MM/dd", "dd/MM/yyyy"), timeSlot);
			appointmentDetailLts.setAppntStartTime(dateString[0]);
			appointmentDetailLts.setAppntEndTime(dateString[1]);
			appointmentDetailLts.setAppntType(applicantInfo.getInstallTimeType());
		}
		if(applicantInfo.isTentative()){
			appointmentDetailLts.setTidInd("Y");
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Calendar tid = new GregorianCalendar();
			tid.set(Calendar.YEAR, tid.get(Calendar.YEAR)+1);
			tid.set(Calendar.MONTH, Calendar.DECEMBER);
			tid.set(Calendar.DAY_OF_MONTH, 31);
			date = df.format(tid.getTime());
			dateString = LtsDateFormatHelper.reformatDateTimeSlot(date, BomLtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
			appointmentDetailLts.setTidStartTime(dateString[0]);
			appointmentDetailLts.setTidEndTime(dateString[1]);
		}
		
		appointmentDetailLts.setInstContactMobile(applicantInfo.getContactMobileNum());
		appointmentDetailLts.setCustContactMobile(applicantInfo.getContactMobileNum());
		appointmentDetailLts.setInstContactName(applicantInfo.getFamilyName() + ", " + applicantInfo.getGivenName());
		appointmentDetailLts.setInstContactNum(applicantInfo.getContactMobileNum());
		appointmentDetailLts.setInstSmsNum(applicantInfo.getContactMobileNum());
//		appointmentDetailLts.setSerialNum(applicantInfo.getPrebookSerialNum());
		return appointmentDetailLts;
	}
	
	private ServiceDetailDTO createServiceDetailLts(OrderCaptureDTO orderCapture, List<AccountDetailLtsDTO> accountDetailLtsList,
			List<CustomerDetailLtsDTO> customerDetailLtsList) {
		
		ServiceDetailLtsDTO serviceDetail = new ServiceDetailLtsDTO();
		
		
		BasketDetailDTO selectedBasket = basketDetailService.getBasket(
				orderCapture.getBasketSelectForm().getSelectedBasketId(),
				BomLtsConstant.LOCALE_ENG,
				BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC);

//		serviceDetail.setAccount(createAccountDetail(orderCapture, customerDetailLtsList, accountDetailLtsList));
		serviceDetail.setAccounts(createAccountServiceAssigns(orderCapture, customerDetailLtsList, accountDetailLtsList));
		serviceDetail.setActualDocType(orderCapture.getApplicantInfoForm().getDocType());
		serviceDetail.setActualDocId(orderCapture.getApplicantInfoForm().getDocNum());
		serviceDetail.setAppointmentDtl(createAppointmentDetailLtsDTO(orderCapture));
//		serviceDetail.setDtlId(dtlId);
		
		serviceDetail.setItemDtls(createAllItemDetailLts(orderCapture, selectedBasket));
		serviceDetail.setOrderType(BomLtsConstant.ORDER_TYPE_INSTALL);
		
		serviceDetail.setSrvNum(getLtsSrvNum(orderCapture));
//		serviceDetail.setSrvTypeCdAction(getLtsSrvTypeCdAction(orderCapture));
		
		
//		serviceDetail.setSuggestSrd(suggestSrd)
//		serviceDetail.setSuggestSrdReasonCd(suggestSrdReasonCd);
		serviceDetail.setTypeOfSrv(BomLtsConstant.SERVICE_TYPE_TEL);
		
//		serviceDetail.setCcServiceId()
		
		serviceDetail.setDeviceType(selectedBasket.getType());
		serviceDetail.setCustNameNotMatch(isCustNameNotMatch(orderCapture) ? "Y" : null );
		serviceDetail.setExDirInd(orderCapture.getApplicantInfoForm().isExDirectoryConfirm()?"N":"Y");
		
		serviceDetail.setFromProd(getLtsFromProductType(orderCapture));
		serviceDetail.setToProd(getLtsToProductType(orderCapture));
		serviceDetail.setFromSrvType(getLtsFromSrvType(orderCapture));
		serviceDetail.setToSrvType(getLtsToSrvType(orderCapture, selectedBasket));
		
		if (StringUtils.equals(BomLtsConstant.SERVICE_TYPE_EYE, orderCapture.getServiceTypeInd())) {
			serviceDetail.setGrpType(BomLtsConstant.GROUP_TYPE_EYE);
			serviceDetail.setDatCd(StringUtils.equals(
					selectedBasket.getPeInd(), "Y") ? BomLtsConstant.DAT_CD_DEL
					: BomLtsConstant.DAT_CD_NCR);	
		}
		else if (StringUtils.equals(BomLtsConstant.SERVICE_TYPE_DEL, orderCapture.getServiceTypeInd())){
			serviceDetail.setDatCd(BomLtsConstant.DAT_CD_DEL);
		}
		
		return serviceDetail;
	}
	
	private String getLtsFromSrvType(OrderCaptureDTO orderCapture) {
		
		if (!orderCapture.getApplicantInfoForm().getNewNum()) {
			return BomLtsConstant.LTS_SRV_TYPE_PORT_IN;
		}
		return BomLtsConstant.LTS_SRV_TYPE_NEW;
	}
	
	private String getLtsToSrvType(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket) {
		
		if (StringUtils.equals(BomLtsConstant.SERVICE_TYPE_DEL, orderCapture.getServiceTypeInd())) {
			return BomLtsConstant.LTS_SRV_TYPE_DEL;
		}
		else if (StringUtils.equals(BomLtsConstant.SERVICE_TYPE_EYE, orderCapture.getServiceTypeInd())) {
			if (StringUtils.equals("Y", selectedBasket.getPeInd())) {
				return BomLtsConstant.LTS_SRV_TYPE_PE;	
			}
			return BomLtsConstant.LTS_SRV_TYPE_SIP;
		}
		return null;
	}
	
	private String getLtsFromProductType(OrderCaptureDTO orderCapture) {
		
		if (!orderCapture.getApplicantInfoForm().getNewNum()) {
			return BomLtsConstant.LTS_PRODUCT_TYPE_PORT_IN;
		}
		return BomLtsConstant.LTS_PRODUCT_TYPE_NEW;
	}
	
	private String getLtsToProductType(OrderCaptureDTO orderCapture) {
		
		if (StringUtils.equals(BomLtsConstant.SERVICE_TYPE_DEL, orderCapture.getServiceTypeInd())) {
			return BomLtsConstant.LTS_PRODUCT_TYPE_DEL;
		}
		else if (StringUtils.equals(BomLtsConstant.SERVICE_TYPE_EYE, orderCapture.getServiceTypeInd())) {
			return BomLtsConstant.LTS_PRODUCT_TYPE_EYE_3_A;
		}
		return null;
	}
	
	
	private String getLtsSrvNum(OrderCaptureDTO orderCapture) {
		if (orderCapture.getApplicantInfoForm().getNewNum()) {
			return StringUtils.leftPad(orderCapture.getApplicantInfoForm().getSelectedNum(), 12, '0');
		}
		return StringUtils.leftPad(orderCapture.getApplicantInfoForm().getImportNum(), 12, '0');
	}
	
	
	private boolean isCustNameNotMatch(OrderCaptureDTO orderCapture) {
		if (orderCapture.getCustomerDetailProfile() == null) {
			return false;
		}
		
		if (StringUtils.equalsIgnoreCase(orderCapture.getApplicantInfoForm().getGivenName(), orderCapture.getCustomerDetailProfile().getFirstName())
				&& StringUtils.equalsIgnoreCase(orderCapture.getApplicantInfoForm().getFamilyName(), orderCapture.getCustomerDetailProfile().getLastName())) {
			return false;
		}
		return true;
	}
	
	private String getLtsSrvTypeCdAction(OrderCaptureDTO orderCapture) {
		
//		if (isCustNameNotMatch(orderCapture)) {
//			return BomLtsConstant.SRV_ACTION_TYPE_CD_CUST_NAME_NOT_MATCH;
//		}
		
		if (StringUtils.equals(BomLtsConstant.SERVICE_TYPE_DEL, orderCapture.getServiceTypeInd())) {
			if (!orderCapture.getApplicantInfoForm().getNewNum()) {
				return BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_PORT_IN_DEL;
			}
			return BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_DEL;
		}
		
		if (StringUtils.equals(BomLtsConstant.SERVICE_TYPE_EYE, orderCapture.getServiceTypeInd())) {
			if (!orderCapture.getApplicantInfoForm().getNewNum()) {
				return BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_PORT_IN_EYE;
			}
			return BomLtsConstant.SRV_ACTION_TYPE_CD_NEW_EYE;
		}
		
		return null;
	}
	
	private ItemDetailLtsDTO[] createAllItemDetailLts(OrderCaptureDTO orderCapture, BasketDetailDTO selectedBasket) {
		
		List<ItemDetailLtsDTO> itemDetailLtsList = new ArrayList<ItemDetailLtsDTO>();
		
		if (orderCapture.getBasketSelectForm() != null) {
			
			List<OnlineBasketDTO> onlineBasketList = orderCapture.getBasketSelectForm().getOnlineBasketList();
			
			for (OnlineBasketDTO onlineBasket : onlineBasketList) {
				if (onlineBasket.isSelected()) {
					if (StringUtils.equals(BomLtsConstant.SERVICE_TYPE_DEL, orderCapture.getServiceTypeInd())) {
						addItemSetDetail(selectedBasket, onlineBasket.getPremiumDelItemSetList(), itemDetailLtsList);	
					}
				}
			}
		}
		
		if (orderCapture.getBasketDetailForm() != null) {
			addItemDetail(selectedBasket, orderCapture.getBasketDetailForm().getPlanItemList(), itemDetailLtsList);
			addItemSetDetail(selectedBasket, orderCapture.getBasketDetailForm().getContItemSetList(), itemDetailLtsList);
			addItemSetDetail(selectedBasket, orderCapture.getBasketDetailForm().getPremiumItemSetList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getBasketDetailForm().getInstallFeeItemList(), itemDetailLtsList);
		}
		
		if (orderCapture.getVasDetailForm() != null) {
			addItemDetail(selectedBasket, orderCapture.getVasDetailForm().getMoovItems(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getVasDetailForm().getOtherItems(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getVasDetailForm().getNowTvPayItems(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getVasDetailForm().getNowTvSpecItems(), itemDetailLtsList);
		}
		
		if (orderCapture.getDefaultItemList() != null) {
			addItemDetail(selectedBasket, orderCapture.getDefaultItemList().getE0060VasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getDefaultItemList().getIddVasItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getDefaultItemList().getNowTvFreeItemList(), itemDetailLtsList);
			addItemDetail(selectedBasket, orderCapture.getDefaultItemList().getPeFreeItemList(), itemDetailLtsList);
//			addItemDetail(selectedBasket, orderCapture.getDefaultItemList().getInstallWaiveItemList(), itemDetailLtsList);
//			addItemDetail(selectedBasket, orderCapture.getDefaultItemList().getInstallItemList(), itemDetailLtsList);
		}
		
		if(orderCapture.getApplicantInfoForm().getEpdItemList() != null){
			addItemDetail(selectedBasket, orderCapture.getApplicantInfoForm().getEpdItemList(), itemDetailLtsList);
		}
		if(orderCapture.getApplicantInfoForm().getBillMethodItemList() != null){
			addItemDetail(selectedBasket, orderCapture.getApplicantInfoForm().getBillMethodItemList(), itemDetailLtsList);
		}
		if(orderCapture.getApplicantInfoForm().getPrepayItemList() != null){
			addItemDetail(selectedBasket, orderCapture.getApplicantInfoForm().getPrepayItemList(), itemDetailLtsList);
		}
		
		return itemDetailLtsList.toArray(new ItemDetailLtsDTO[itemDetailLtsList.size()]);
		
		
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
				
				if (itemDetail.isSelected()) {
					ItemDetailLtsDTO itemDetailLts = createItemDetailLts(selectedBasket, itemDetail,
							String.valueOf(itemDetailLtsList.size() + 1),
							BomLtsConstant.IO_IND_INSTALL);
					itemDetailLts.setMembershipId(membershipId);
					itemDetailLtsList.add(itemDetailLts);
				}
			}
		}
	}
	
	private void addItemDetail(BasketDetailDTO selectedBasket, List<ItemDetailDTO> itemDetailList, List<ItemDetailLtsDTO> itemDetailLtsList) {
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
							BomLtsConstant.IO_IND_INSTALL));
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
	
	/*
	 * create orderCaptureDTO from sbOrderDTO
	 */
	public OrderCaptureDTO createOrderCapture(SbOrderDTO sbOrder){
		
		return null;
	}
	
	public void submitOrderPayment(OrderCaptureDTO orderCapture) {
		
		SbOrderDTO sbOrder = orderRetrieveLtsService.retrieveSbOrder(orderCapture.getSbOrder().getOrderId(), false);
		
		if (sbOrder == null) {
			return;
		}
		
		AccountDetailLtsDTO[] accountDetails = sbOrder.getAccounts();
		
		if (ArrayUtils.isEmpty(accountDetails)) {
			return;
		}
		
		List<PaymentMethodDetailLtsDTO> paymentMethodDetailList = null;
		
		for (AccountDetailLtsDTO accountDetail : accountDetails) {
			
			paymentMethodDetailList = new ArrayList<PaymentMethodDetailLtsDTO>();
			
			PaymentMethodDetailLtsDTO[] paymentMethodDetails = accountDetail.getPaymethods();
			
			// Remove Existing Payments
			if (ArrayUtils.isNotEmpty(paymentMethodDetails)) {
				for (PaymentMethodDetailLtsDTO paymentMethodDetail : paymentMethodDetails) {
					paymentMethodDetail.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
					paymentMethodDetailList.add(paymentMethodDetail);
				}
			}
			
			// Create NEW Payment
			paymentMethodDetailList.add(createPaymentMethodDetail(orderCapture));
			accountDetail.setPaymethods(paymentMethodDetailList.toArray(new PaymentMethodDetailLtsDTO[paymentMethodDetailList.size()]));
		}
		generateCreditCardPreimumItem(orderCapture, sbOrder);
		
		orderSaveService.saveSbOrder(sbOrder);
	}
	
	
	private void generateCreditCardPreimumItem(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder) {
		
		List<OnlineBasketDTO> onlineBasketList = orderCapture.getBasketSelectForm().getOnlineBasketList();
		
		List<ItemDetailDTO> generateItemList = new ArrayList<ItemDetailDTO>();
		String creditCardPrefix = getCreditCardPrefix(orderCapture);
		if (onlineBasketList != null && !onlineBasketList.isEmpty()) {
			for (OnlineBasketDTO onlineBasket : onlineBasketList) {
				if (!onlineBasket.isSelected()
						|| onlineBasket.getPremiumImageSetList() == null
						|| onlineBasket.getPremiumImageSetList().isEmpty()) {
					continue;
				}
				
				for (ItemSetDetailDTO premImageItemSet : onlineBasket.getPremiumImageSetList()) {
					if (premImageItemSet == null || ArrayUtils.isEmpty(premImageItemSet.getItemDetails())) {
						continue;
					}
					
					for (ItemDetailDTO premImageItem : premImageItemSet.getItemDetails()) {
						if (StringUtils.equalsIgnoreCase(creditCardPrefix, itemDetailService.getItemEligibleCardPrefix(premImageItem.getItemId()))) {
							premImageItem.setSelected(true);
							generateItemList.add(premImageItem);
						}
					}
				}
			}
		}
		
		if (!generateItemList.isEmpty()) {
			ServiceDetailLtsDTO eyeService = LtsSbHelper.getLtsEyeService(sbOrder);
			ServiceDetailLtsDTO delService = LtsSbHelper.getDelServices(sbOrder);
			appendOrderItem(orderCapture, eyeService != null ? eyeService : delService, generateItemList);
		}
		
	}
	
	private String getCreditCardPrefix(OrderCaptureDTO orderCapture) {
		if (orderCapture.getCreditPaymentForm() != null) {
			return StringUtils.left(orderCapture.getCreditPaymentForm().getCcNum(), 4);	
		}
		return null;
	}

	private void appendOrderItem(OrderCaptureDTO orderCapture, ServiceDetailLtsDTO serviceDetail, List<ItemDetailDTO> appendItemList) {
		
		List<ItemDetailLtsDTO> itemDetailLtsList = new ArrayList<ItemDetailLtsDTO>();
		
		if (ArrayUtils.isNotEmpty(serviceDetail.getItemDtls())) {
			itemDetailLtsList.addAll(Arrays.asList(serviceDetail.getItemDtls()) );	
		}
		
		BasketDetailDTO selectedBasket = basketDetailService.getBasket(
				orderCapture.getBasketSelectForm().getSelectedBasketId(),
				BomLtsConstant.LOCALE_ENG,
				BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC);

		addItemDetail(selectedBasket, appendItemList, itemDetailLtsList);
		serviceDetail.setItemDtls(itemDetailLtsList.toArray(new ItemDetailLtsDTO[itemDetailLtsList.size()]));
	}
	
}
