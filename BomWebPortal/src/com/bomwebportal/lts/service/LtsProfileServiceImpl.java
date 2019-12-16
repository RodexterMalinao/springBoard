package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.order.OrderDetailDAO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.UpgradeEyeInfoDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsCustomerInquiryFormDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemActionLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferActionLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupMemberProfileDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ServiceProfileInventoryDTO;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.service.bom.ImsServiceProfileAccessService;
import com.bomwebportal.lts.service.bom.OfferProfileLtsService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsDrgService;
import com.bomwebportal.lts.service.order.OfferActionService;
import com.bomwebportal.lts.service.order.OfferItemService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.wsClientLts.BomWsClient;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.LoggingService;

public class LtsProfileServiceImpl implements LtsProfileService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected ServiceProfileLtsDrgService serviceProfileLtsDrgService;
	protected OfferProfileLtsService offerProfileLtsService;
	protected CustomerProfileLtsService customerProfileLtsService;
	protected LtsAddressRolloutService ltsAddressRolloutService;
	protected LtsOfferService ltsOfferService;
	protected LtsPaymentService ltsPaymentService;
	private BomWsClient bomWsClient;
	private OfferItemService offerItemService;
	private LtsAppointmentService ltsAppointmentService;
	private AddressRolloutService addressRolloutService;
	private ImsProfileService imsProfileService;
	private OfferActionService offerActionService;
	private ImsServiceProfileAccessService imsServiceProfileAccessService;
	private CodeLkupCacheService prepaymentRemarkLkupCacheService;
	private LoggingService loggingService;
	private OrderDetailDAO orderDetailDao;
	
	public CodeLkupCacheService getPrepaymentRemarkLkupCacheService() {
		return prepaymentRemarkLkupCacheService;
	}

	public void setPrepaymentRemarkLkupCacheService(
			CodeLkupCacheService prepaymentRemarkLkupCacheService) {
		this.prepaymentRemarkLkupCacheService = prepaymentRemarkLkupCacheService;
	}

	public ImsServiceProfileAccessService getImsServiceProfileAccessService() {
		return imsServiceProfileAccessService;
	}

	public void setImsServiceProfileAccessService(
			ImsServiceProfileAccessService imsServiceProfileAccessService) {
		this.imsServiceProfileAccessService = imsServiceProfileAccessService;
	}

	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}
	
	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}

	public ServiceProfileLtsDrgService getServiceProfileLtsDrgService() {
		return serviceProfileLtsDrgService;
	}

	public void setServiceProfileLtsDrgService(
			ServiceProfileLtsDrgService serviceProfileLtsDrgService) {
		this.serviceProfileLtsDrgService = serviceProfileLtsDrgService;
	}

	public OfferProfileLtsService getOfferProfileLtsService() {
		return offerProfileLtsService;
	}

	public void setOfferProfileLtsService(
			OfferProfileLtsService offerProfileLtsService) {
		this.offerProfileLtsService = offerProfileLtsService;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	public LtsAddressRolloutService getLtsAddressRolloutService() {
		return ltsAddressRolloutService;
	}

	public void setLtsAddressRolloutService(
			LtsAddressRolloutService ltsAddressRolloutService) {
		this.ltsAddressRolloutService = ltsAddressRolloutService;
	}

	public BomWsClient getBomWsClient() {
		return bomWsClient;
	}

	public void setBomWsClient(BomWsClient bomWsClient) {
		this.bomWsClient = bomWsClient;
	}

	public OfferItemService getOfferItemService() {
		return offerItemService;
	}

	public void setOfferItemService(OfferItemService offerItemService) {
		this.offerItemService = offerItemService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}
	
	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
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

	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}

	public OfferActionService getOfferActionService() {
		return offerActionService;
	}

	public void setOfferActionService(OfferActionService offerActionService) {
		this.offerActionService = offerActionService;
	}

	public LoggingService getLoggingService() {
		return loggingService;
	}

	public void setLoggingService(LoggingService loggingService) {
		this.loggingService = loggingService;
	}
	
	public OrderDetailDAO getOrderDetailDao() {
		return orderDetailDao;
	}

	public void setOrderDetailDao(OrderDetailDAO orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	public ServiceDetailProfileLtsDTO retrieveServiceProfile(String serviceNum, String pUser) {
		return retrieveServiceProfile(serviceNum, pUser, LtsConstant.LOCALE_ENG);
	}
	
	public ServiceDetailProfileLtsDTO retrieveServiceProfile(String serviceNum, String pUser, String pLocale) {

		ServiceDetailProfileLtsDTO serviceProfile = this.serviceProfileLtsDrgService.getServiceProfile(serviceNum, LtsConstant.SERVICE_TYPE_TEL, pUser);

		if (serviceProfile == null) {
			return null;
		}
		this.loggingService.logSearchByService(pUser, LtsConstant.SERVICE_TYPE_TEL, serviceNum);
		this.setPendingOrderOverdue(serviceProfile);
		this.customerProfileLtsService.getCustomerRemark(serviceProfile.getPrimaryCust(), LtsConstant.SYSTEM_ID_DRG);
		this.customerProfileLtsService.getCustomerSpecialRemark(serviceProfile.getPrimaryCust());
		this.customerProfileLtsService.getWipMessage(serviceProfile.getPrimaryCust());
		this.getOfferDetails(serviceProfile);
		if (serviceProfile.getAddress() != null) {
			this.setTenure(serviceProfile, serviceProfile.getAddress().getUnitNum(), serviceProfile.getAddress().getFloorNum(), serviceProfile.getAddress().getSrvBdry());	
		}
		this.getAddressRolloutDetail(serviceProfile);
		this.getEyeFsaProfile(serviceProfile, pUser, pLocale);
		this.setProfileItemDetail(serviceProfile, pLocale);
		this.setFsaProfileItemDetail(serviceProfile, pLocale);
		this.setBundleTvCredit(serviceProfile);
		return serviceProfile;
	}
	
	private void setBundleTvCredit(ServiceDetailProfileLtsDTO serviceProfile){
		Map<String,String> bundleTvList = ltsOfferService.getBundleTvMap();
		OfferDetailProfileLtsDTO[] offerProfile = serviceProfile.getOfferProfiles();
		for(int i = 0; i < offerProfile.length; i++){
			if(offerProfile[i].getPsefSet() != null){
			  for(String psef : offerProfile[i].getPsefSet() ){
				if(bundleTvList.containsKey(psef)){
					serviceProfile.setBundleTvCredit(bundleTvList.get(psef).toString());
					break;
				}
			  }
		    }
		}
	}
	
	private void getEyeFsaProfile(ServiceDetailProfileLtsDTO serviceProfile, String userId, String locale) {
		if (StringUtils.isBlank(serviceProfile.getRelatedEyeFsa())) {
			return;
		}
		try {
			FsaServiceDetailOutputDTO eyeFsaProfile = imsServiceProfileAccessService
				.getServiceDetailByFSA(serviceProfile.getRelatedEyeFsa(),userId);
			getFsaOfferProfile(serviceProfile, eyeFsaProfile, serviceProfile.getExistEyeType(), locale);
			
			boolean isStandaloneTv = imsProfileService.isStandaloneTv(serviceProfile.getRelatedEyeFsa());
			
			String srvType = eyeFsaProfile.getSrvType();
			if (!isStandaloneTv) {
				if (LtsConstant.FSA_SRV_TYPE_PCD_HDTV.equals(srvType) || LtsConstant.FSA_SRV_TYPE_PCD_SDTV.equals(srvType)) {
					srvType = LtsConstant.FSA_SRV_TYPE_PCD;
				}
				if (LtsConstant.FSA_SRV_TYPE_HDTV.equals(srvType) || LtsConstant.FSA_SRV_TYPE_SDTV.equals(srvType)) {
					srvType = LtsConstant.FSA_SRV_TYPE_WG;
				}
			}
			eyeFsaProfile.setSrvType(srvType);
			serviceProfile.setEyeFsaProfile(eyeFsaProfile);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
	public void getFsaOfferProfile(ServiceDetailProfileLtsDTO serviceProfile, FsaServiceDetailOutputDTO pFsa, String pExistEyeType, String locale) {
		imsServiceProfileAccessService.getFsaOfferProfile(pFsa, pExistEyeType);
		setFsaProfileItemDetail(serviceProfile, locale);
	}
	
	private void setFsaProfileItemDetail(ServiceDetailProfileLtsDTO serviceProfile, String locale) {
		if (serviceProfile == null 
				|| serviceProfile.getEyeFsaProfile() == null
				|| ArrayUtils.isEmpty(serviceProfile.getEyeFsaProfile().getItems())) {
			return;
		}
		
		for (ItemDetailProfileLtsDTO itemDetailProfile : serviceProfile.getEyeFsaProfile().getItems()) {
			if (StringUtils.isEmpty(itemDetailProfile.getItemId())) {
				continue;
			}
			itemDetailProfile.setItemDetail(ltsOfferService.getTpVasItemDetail(itemDetailProfile.getItemId(), locale));
		}
	}
	
	
	private void setProfileItemDetail(ServiceDetailProfileLtsDTO serviceProfile, String locale) {
		
		if (serviceProfile == null) {
			return;
		}
		
		if (ArrayUtils.isNotEmpty(serviceProfile.getItemDetails())) {
			for (ItemDetailProfileLtsDTO itemDetailProfile : serviceProfile.getItemDetails()) {
				if (StringUtils.isEmpty(itemDetailProfile.getItemId())) {
					continue;
				}
				itemDetailProfile.setItemDetail(ltsOfferService.getTpVasItemDetail(
						itemDetailProfile.getItemId(), locale));
			}	
		}
		
		if (ArrayUtils.isNotEmpty(serviceProfile.getEndedItemDetails())) {
			for (ItemDetailProfileLtsDTO itemDetailProfile : serviceProfile.getEndedItemDetails()) {
				if (StringUtils.isEmpty(itemDetailProfile.getItemId())) {
					continue;
				}
				itemDetailProfile.setItemDetail(ltsOfferService.getTpVasItemDetail(
						itemDetailProfile.getItemId(), locale));
			}	
		}
		
	}
	
	private void setPendingOrderOverdue(ServiceDetailProfileLtsDTO serviceProfile) {
		if (StringUtils.isNotEmpty(serviceProfile.getPendingOcSrd())) {
			serviceProfile.setPendingOrderOverdue(LtsDateFormatHelper.isDateOverdue(serviceProfile.getPendingOcSrd(), null));
		}
	}
	
	private void getAddressRolloutDetail(ServiceDetailProfileLtsDTO serviceProfile) {
		
		AddressDetailProfileLtsDTO profileAddress = serviceProfile.getAddress();
		AddressRolloutDTO addressRollout = addressRolloutService.getAddressRollout(profileAddress.getSrvBdry(), profileAddress.getUnitNum(), profileAddress.getFloorNum());
		
		if (addressRollout != null) {
			List<String> fsaList = new ArrayList<String>();
			TenureDTO[] imsTenures = serviceProfile.getImsTenureDtls();
			
			for (int i=0; imsTenures != null && i < imsTenures.length; ++i) {
				if (StringUtils.isNotEmpty(imsTenures[i].getServiceId())) {
					fsaList.add(imsTenures[i].getServiceId());
				}
			}
			addressRollout.setDiffCustFsaExist(ltsAddressRolloutService.isDiffCustFsaExist(fsaList.toArray(new String[0]), serviceProfile.getPrimaryCust().getDocType(), serviceProfile.getPrimaryCust().getDocNum()));
			addressRollout.setFullAddress(profileAddress.getFullAddress());
		}
		profileAddress.setAddressRollout(addressRollout);
	}
	
	public void getOfferDetails(ServiceDetailProfileLtsDTO pSrvDtlProfile) {
		getOfferDetails(pSrvDtlProfile, LtsDateFormatHelper.getSysDate("dd/MM/yyyy"));
	}

	public void getOfferDetails(ServiceDetailProfileLtsDTO pSrvDtlProfile, String pApplnDate) {
		this.offerProfileLtsService.getProfileDetails(pSrvDtlProfile);
		pSrvDtlProfile.setOfferProfiles(this.offerProfileLtsService.getLtsOfferProfile(pSrvDtlProfile.getCcSrvId(), pApplnDate));
		pSrvDtlProfile.setItemDetails(this.offerItemService.getOfferItemMapping(pSrvDtlProfile.getOfferProfiles()));
		pSrvDtlProfile.setEndedOfferProfiles(this.offerProfileLtsService.getLtsEndedOfferProfile(pSrvDtlProfile.getCcSrvId(), pApplnDate));
		pSrvDtlProfile.setEndedItemDetails(this.offerItemService.getOfferItemMapping(pSrvDtlProfile.getEndedOfferProfiles()));
		this.offerActionService.getLtsOfferActions(pSrvDtlProfile);
		this.offerActionService.getLtsEquipmentAction(pSrvDtlProfile);
		this.offerActionService.getLtsTvAction(pSrvDtlProfile);
		this.offerActionService.getLtsChannelAction(pSrvDtlProfile);
		this.offerActionService.getLtsRestrictedOfferAction(pSrvDtlProfile);
		this.updateBundleTvOfferAction(pSrvDtlProfile);
	}
	
	private void updateBundleTvOfferAction(ServiceDetailProfileLtsDTO serviceProfile) {
		ItemDetailProfileLtsDTO[] profileItems = serviceProfile.getItemDetails();
		if (ArrayUtils.isEmpty(profileItems)) {
			return;
		}
		String toProd = StringUtils.defaultIfEmpty(serviceProfile.getExistEyeType(), LtsConstant.LTS_PRODUCT_TYPE_DEL);
		Map<String,String> bundleTvList = ltsOfferService.getBundleTvMap();
		for (ItemDetailProfileLtsDTO profileItem : profileItems) {
			OfferDetailProfileLtsDTO[] offerProfile = profileItem.getOffers(); 
			for(int i = 0; offerProfile != null && i < offerProfile.length; i++){
				if(offerProfile[i].getPsefSet() != null){
				  for(String psef : offerProfile[i].getPsefSet() ){
					if(bundleTvList.containsKey(psef)){
						ItemActionLtsDTO[] itemActions = profileItem.getItemActions();
						if (ArrayUtils.isNotEmpty(itemActions)) {
							for (ItemActionLtsDTO itemAction : itemActions) {
								if (StringUtils.equalsIgnoreCase(toProd, itemAction.getToProd())) {
									itemAction.setAction(LtsBackendConstant.IO_IND_OUT);
									itemAction.setPenaltyHandle(profileItem.getTpExpiredMonths() > 0 ? null : LtsBackendConstant.OFFER_HANDLE_AUTO_WAIVE);	
								}
							}
						}
					}
				  }
			    }
			}
		}
	}
	
	public String getServiceInventoryStatus(String pSrvNum, String pSrvType) {
		ServiceProfileInventoryDTO serviceInventory = this.bomWsClient.getServiceInvenotry(pSrvNum, pSrvType);
		return serviceInventory == null ? null : serviceInventory.getInventStatus();
	}
	
	public void setTenure(ServiceDetailProfileLtsDTO serviceProfile, String flat, String floor, String srvBdry) {
	
		// Get LTS Tenure
		int newLtsMaxTenure = this.customerProfileLtsService.getMaxLtsTenure(serviceProfile.getPrimaryCust().getParentCustNum(), flat, floor, srvBdry);
		
		if (newLtsMaxTenure > serviceProfile.getLtsTenure()) {
			serviceProfile.setLtsTenure(newLtsMaxTenure);
		}
		
		List<String[]> addrPatternList = LtsSbOrderHelper.getAddrCombinationPattern(flat, floor);

		// Get IMS Tenure
		TenureDTO[] imsTenures = null;
		if (addrPatternList != null && !addrPatternList.isEmpty()) {
			for (String[] addrParrtern : addrPatternList) {
				imsTenures = this.imsProfileService.getImsTenureByAddress(addrParrtern[0], addrParrtern[1], srvBdry);
				if (ArrayUtils.isNotEmpty(imsTenures)) {
					break;
				}
			}
		}
		
		String custDocType = serviceProfile.getPrimaryCust().getDocType();
		String custDocNum = serviceProfile.getPrimaryCust().getDocNum();
		
		List<TenureDTO> custImsTenureList = new ArrayList<TenureDTO>();
		if (!ArrayUtils.isEmpty(imsTenures)) {
			for (TenureDTO tenure : imsTenures) {
				//check if imsTenure is under same cust
				String[] custDocDtl = this.imsProfileService.getImsCustDocByParentCust(tenure.getCustNum());
				if(StringUtils.equals(custDocDtl[0], custDocType)
						&& StringUtils.equals(custDocDtl[1], custDocNum)){
					custImsTenureList.add(tenure);
				}
			}
		}

		imsTenures = custImsTenureList.toArray(new TenureDTO[custImsTenureList.size()]);

		int newImsMaxTenure = getMaxTenure(imsTenures);
		if (newImsMaxTenure > serviceProfile.getImsTenure()) {
			serviceProfile.setImsTenureDtls(imsTenures);
			serviceProfile.setImsTenure(newImsMaxTenure);
		}
	}

	public int getMaxTenure(TenureDTO[] pTenures) {
		int maxTenure = 0;
		
		if (ArrayUtils.isEmpty(pTenures)) {
			return maxTenure;
		}
		
		for (TenureDTO tenure : pTenures) {
			if (tenure.getTenure() > maxTenure) {
				maxTenure = tenure.getTenure();
			}
		}
		return maxTenure;
	}
	
	public ValidationResultDTO retrieveAndValidateServiceProfile(LtsCustomerInquiryFormDTO form, BomSalesUserDTO bomSalesUser, boolean isChannelCs) {
		return validateServiceProfile(form, this.retrieveServiceProfile(form.getServiceNum(), bomSalesUser.getUsername()), bomSalesUser, isChannelCs);
	}

	public ValidationResultDTO validateServiceProfile(LtsCustomerInquiryFormDTO form, ServiceDetailProfileLtsDTO serviceProfile, BomSalesUserDTO bomSalesUser, boolean isChannelCs) {

		List<String> messageList = new ArrayList<String>();
		
		/** CHECKING FOR WON'T DISPLAY PROFILE INFO **/  
		if (serviceProfile == null) {
			messageList.add("Service number not found.");
			return new ValidationResultDTO(Status.INVAILD, messageList, null);
		}
		if (serviceProfile.getPrimaryCust() == null) {
			messageList.add("Customer not found.");
			return new ValidationResultDTO(Status.INVAILD, messageList, null);
		}
		if (serviceProfile.getAddress() == null) {
			messageList.add("Address not found.");
			return new ValidationResultDTO(Status.INVAILD, messageList, null);
		}
		if (ArrayUtils.isEmpty(serviceProfile.getAccounts())) {
			messageList.add("Account not found.");
			return new ValidationResultDTO(Status.INVAILD, messageList, null);
		}

		// Existing pending D order
		if (StringUtils.isNotEmpty(serviceProfile.getPendingOcid()) 
				&& StringUtils.equals("D", serviceProfile.getPendingOrdType())){
			messageList.add("Existing pending D order: " + serviceProfile.getPendingOcid());
		}

		// Service Status
		if (!StringUtils.equals(serviceProfile.getInventStatus(), "20")
				&& !StringUtils.equals(serviceProfile.getInventStatus(), "14")) {
			messageList.add("Service line status is not working.");
		}
		
		if(!isChannelCs){
			
			// WIP = P + Third-Party
			if (form.getThirdPartyApplication() == true && StringUtils.equals("P", serviceProfile.getPrimaryCust().getWipInd())) {
				messageList.add("Third-Party application for WIP(P) profile is not allowed.");
			}
			
		}
		else{

			// WIP
			if (LtsConstant.SALES_ROLE_FRONTLINE.equals(bomSalesUser.getCategory())
					&& StringUtils.isNotBlank(serviceProfile.getPrimaryCust().getWipInd()) 
					&& (StringUtils.equals("W", serviceProfile.getPrimaryCust().getWipInd())
						|| StringUtils.equals("P", serviceProfile.getPrimaryCust().getWipInd())
						|| StringUtils.equals("Y", serviceProfile.getPrimaryCust().getWipInd()))) {
				messageList.add("WIP profile is not allowed.");
			}
		}

		if (!isLineLevelTpExist(serviceProfile) && StringUtils.isEmpty(serviceProfile.getExistEyeType())) {
			messageList.add("Cannot mapped service line TP offer");
		}
		
 		if(serviceProfile.getSrvGrp() != null 
				&& serviceProfile.getSrvGrp().getGrpMems() != null
				&& serviceProfile.getSrvGrp().getGrpMems().length > 2){
			String srvNum = form.getServiceNum();
			ServiceGroupMemberProfileDTO grpMemNcr = serviceProfile.getSrvGrp().getMemberByType("NCR");
			if(grpMemNcr != null
					&& grpMemNcr.getSrvNum().equals(StringUtils.leftPad(srvNum, 12, '0'))){
				messageList.add("Existing eye SIP Service number, not allow to create order. Please input PE Service number.");
			}
			
		}
		
 		if(StringUtils.equals(serviceProfile.getDatCd(), "HUNT")
 				|| StringUtils.equals(serviceProfile.getDatCd(), "CITN")){
			messageList.add(serviceProfile.getDatCd() + " is not supported in Springboard.");
 		}

		
		// Tariff
		if (!StringUtils.equals(serviceProfile.getTariff(), "R")) {
			messageList.add("Tariff is not residential, not allow to upgrade eye family product.");
		}
		
		if (!messageList.isEmpty()) {
			return new ValidationResultDTO(Status.INVAILD, messageList, null);
		}

		/** END CHECKING (WON'T DISPLAY PROFILE INFO) **/
		
		/** CHECKING FOR NOT ALLOW CREATE ORDER **/ 

		//TWIN
		if(isChannelCs){
			// WIP case with manager user
			if ((LtsConstant.SALES_ROLE_MANAGER.equals(bomSalesUser.getCategory())
					|| LtsConstant.SALES_ROLE_SALES_MANAGER.equals(bomSalesUser.getCategory())
					|| LtsConstant.SALES_ROLE_SUPPORT.equals(bomSalesUser.getCategory())
					)
					&& StringUtils.isNotBlank(serviceProfile.getPrimaryCust().getWipInd()) 
					&& (StringUtils.equals("W", serviceProfile.getPrimaryCust().getWipInd())
						|| StringUtils.equals("P", serviceProfile.getPrimaryCust().getWipInd())
						|| StringUtils.equals("Y", serviceProfile.getPrimaryCust().getWipInd()))) {
				messageList.add("WIP profile is not allowed.");
			}
		}
		
		if (StringUtils.equals("TWIN", serviceProfile.getSrvType()) || serviceProfile.isTwinLineInd()) {
			messageList.add("Twin line exist, Springboard not support.");
		}
		
		// Service Status
//		if (StringUtils.equalsIgnoreCase(serviceProfile.getExistEyeType(), LtsConstant.EYE_TYPE_EYE3A)) {
//			messageList.add("Existing eye3A profile. ");
//		}
		
		if(isChannelCs){
			String boc = null;
			try {
				boc = this.orderDetailDao.getUserBoc(bomSalesUser.getUsername());
				form.setBocEmpty(StringUtils.isEmpty(boc));
				if(form.isBocEmpty()){
					messageList.add("User BOC not exist. Forced to Enquiry Mode.");
				}
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		
		
		if (ArrayUtils.isNotEmpty(serviceProfile.getRestrictedOfferActions())) {
			for (OfferActionLtsDTO restrictedOfferAction : serviceProfile.getRestrictedOfferActions()) {
				if (StringUtils.equals("*", restrictedOfferAction.getAction())
						|| LtsConstant.ORDER_TYPE_SB_RETENTION.equals(restrictedOfferAction.getAction())
						|| LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(restrictedOfferAction.getAction()))
				messageList.add(restrictedOfferAction.getDescription());
			}
		}
		
		// IMS Profile crack
		if(!StringUtils.isEmpty(serviceProfile.getExistEyeType()) && serviceProfile.getRelatedEyeFsa() ==null){
			messageList.add("Service group profile corrupt.");
		}
		
		if (!messageList.isEmpty()) {
			return new ValidationResultDTO(null, messageList, serviceProfile);
		}
		/** END CHECKING (NOT ALLOW CREATE ORDER) **/
		
		/** WARNNIG CHECKING (ALLOW CREATE ORDER) **/
		
//		if (ArrayUtils.isNotEmpty(serviceProfile.getChannelOfferActions())) {
//			for (OfferActionLtsDTO channelOfferAction : serviceProfile.getChannelOfferActions()) {
//				messageList.add(channelOfferAction.getDescription());
//			}
//		}
		
		// TOS Account Outstanding Amount (Allow Issue Order)
		if (StringUtils.equals(serviceProfile.getInventStatus(),"14")) {

			for (AccountDetailProfileLtsDTO accountDetail : serviceProfile.getAccounts()) {
				if (StringUtils.isNotBlank(accountDetail.getOutstandingAmount()) && !StringUtils.equals("0", accountDetail.getOutstandingAmount())) {
					messageList.add("Account " + accountDetail.getAcctNum() + " outstanding amount : $" + accountDetail.getOutstandingAmount());
				}
			}
		}
		// Blacklist Customer
		if (serviceProfile.getPrimaryCust().isBlacklistCustInd()) {
			messageList.add("Blacklisted customer.");
		}
		
//		if (LtsConstant.EYE_TYPE_EYE2A.equalsIgnoreCase(serviceProfile.getExistEyeType())
//				&& !LtsSbOrderHelper.isSecondContract(serviceProfile)
//				&& !LtsConstant.CHANNEL_CD_CUSTOMER_SERVICE.equals(bomSalesUser.getChannelCd())) {
//			ItemDetailProfileLtsDTO profileTermPlan = LtsSbOrderHelper.getProfileServiceTermPlan(serviceProfile);
//			if (profileTermPlan != null && profileTermPlan.getTpExpiredMonths() < -3) {
//				messageList.add("eye2A TP remaining more than 3 contract months is not allow to upgrade through Springboard.");
//			}
//		}
		
		
		/** END WARNNIG CHECKING (ALLOW CREATE ORDER) **/
		
		return new ValidationResultDTO(Status.VALID, messageList, serviceProfile);
	}
	
	private boolean isLineLevelTpExist(ServiceDetailProfileLtsDTO serviceProfile) {
		if (ArrayUtils.isEmpty(serviceProfile.getItemDetails())) {
			return false;
		}
		for (ItemDetailProfileLtsDTO profileItemDetail : serviceProfile.getItemDetails()) {
			if (StringUtils.equals(LtsConstant.PROFILE_ITEM_TYPE_SERVICE, profileItemDetail.getItemType())) {
				return true;
			}
		}
		return false;
	}
	
	
	public UpgradeEyeInfoDTO retrieveUpgradeEyeInfo(ServiceDetailProfileLtsDTO serviceProfile, String locale, OrderCaptureDTO pOrderCapture, boolean isUpgradeToStaffPlan) {
		UpgradeEyeInfoDTO returnUpgradeEyeInfo = new UpgradeEyeInfoDTO();
		
		if (serviceProfile == null) {
			return null;
		}
		
		List<String> upgradeDeviceTypeList = new ArrayList<String>();

		if (StringUtils.isEmpty(serviceProfile.getExistEyeType())) {
			upgradeDeviceTypeList.add(LtsConstant.DEVICE_TYPE_EYE3A);
			upgradeDeviceTypeList.add(LtsConstant.DEVICE_TYPE_1020);
		}else if (!StringUtils.equalsIgnoreCase(LtsConstant.EYE_TYPE_EYE3A, serviceProfile.getExistEyeType())) {
			upgradeDeviceTypeList.add(LtsConstant.DEVICE_TYPE_EYE3A);
			if(!StringUtils.equalsIgnoreCase(LtsConstant.EYE_TYPE_EYE2A, serviceProfile.getExistEyeType())){
				upgradeDeviceTypeList.add(LtsConstant.DEVICE_TYPE_1020);
			}
		}
		
		String returnDeviceText = "";
		if(StringUtils.equalsIgnoreCase(LtsConstant.EYE_TYPE_EYE1_5A, serviceProfile.getExistEyeType())){
			returnDeviceText = " (eye1.5a)";
		}else if(StringUtils.equalsIgnoreCase(LtsConstant.EYE_TYPE_EYE1, serviceProfile.getExistEyeType())){
			returnDeviceText = " (eye1)";
		}
		else if(StringUtils.equalsIgnoreCase(LtsConstant.EYE_TYPE_SAMSUNG, serviceProfile.getExistEyeType())){
			returnDeviceText = " (Samsung)";
		}
		
		StringBuilder contractPeriod = new StringBuilder();
		StringBuilder extendContractPeriod = new StringBuilder();
		StringBuilder returnDeviceInd = new StringBuilder();
		StringBuilder adminCharge = new StringBuilder();
		StringBuilder cancelCharge = new StringBuilder();
		
		//Check DeviceOfferActions, overwrite upgradeEyeInfo.returnDeviceInd if exist
		OfferActionLtsDTO eye2aOfferAction = null;
		OfferActionLtsDTO eye3OfferAction = null;
		if (serviceProfile.getDeviceOfferActions() != null){
			for(OfferActionLtsDTO offerAction: serviceProfile.getDeviceOfferActions()){
				if((LtsConstant.EYE_TYPE_EYE2A.equalsIgnoreCase(offerAction.getToProd()) || "*".equals(offerAction.getToProd()))
						&& "O".equals(offerAction.getAction())){
					eye2aOfferAction = offerAction;
				}
				if((LtsConstant.EYE_TYPE_EYE3A.equalsIgnoreCase(offerAction.getToProd()) || "*".equals(offerAction.getToProd()))
						&& "O".equals(offerAction.getAction())){
					eye3OfferAction = offerAction;
				}
			}
		}
		
		if(eye3OfferAction != null){
			returnDeviceInd.append((isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE3A_STAFF : LtsConstant.EYE_TYPE_EYE3A) + "-" 
					+ (StringUtils.equals(eye3OfferAction.getAction(), "O")?"Y":"N") + " (" + eye3OfferAction.getDescription() + ")");
		}
		
		for (String upgradeDeviceType : upgradeDeviceTypeList) {
			UpgradeEyeInfoDTO upgradeEyeInfo = this.ltsOfferService
					.getUpgradeEyeInfo(serviceProfile, upgradeDeviceType, isUpgradeToStaffPlan);
			
			if (upgradeEyeInfo == null) {
				continue;
			}
			
			if (StringUtils.equals(LtsConstant.DEVICE_TYPE_EYE3A, upgradeDeviceType)) {
				if (StringUtils.isNotEmpty(upgradeEyeInfo.getContractPeriod())) {
					contractPeriod.append((isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE3A_STAFF : LtsConstant.EYE_TYPE_EYE3A) + "-" + upgradeEyeInfo.getContractPeriod() + " Months");
				}
				if (StringUtils.isNotEmpty(upgradeEyeInfo.getExtendContractPeriod())) {
					extendContractPeriod.append(upgradeEyeInfo.getExtendContractPeriod()  + " " + (isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE3A_STAFF : LtsConstant.EYE_TYPE_EYE3A));
				}
				if (StringUtils.isNotEmpty(upgradeEyeInfo.getAdminCharge())) {
					adminCharge.append((isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE3A_STAFF : LtsConstant.EYE_TYPE_EYE3A) + "-$" + upgradeEyeInfo.getAdminCharge());
				}
				if (StringUtils.isNotEmpty(upgradeEyeInfo.getCancelCharge())) {
					cancelCharge.append((isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE3A_STAFF : LtsConstant.EYE_TYPE_EYE3A) + "-$" + upgradeEyeInfo.getCancelCharge());
				}
				//overwrite upgradeEyeInfo.returnDeviceInd
				if(eye3OfferAction == null && StringUtils.isNotEmpty(upgradeEyeInfo.getReturnDeviceInd())) {
					returnDeviceInd.append((isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE3A_STAFF : LtsConstant.EYE_TYPE_EYE3A) + "-" + upgradeEyeInfo.getReturnDeviceInd() + returnDeviceText);
				}
			}
	/*		if (StringUtils.equals(LtsConstant.DEVICE_TYPE_1020, upgradeDeviceType)) {
				if (StringUtils.isNotEmpty(upgradeEyeInfo.getContractPeriod())) {
					contractPeriod.append(" / " + (isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE2A_STAFF : LtsConstant.EYE_TYPE_EYE2A) + "-" + upgradeEyeInfo.getContractPeriod()  + "Months " );
				}
				if (StringUtils.isNotEmpty(upgradeEyeInfo.getExtendContractPeriod())) {
					extendContractPeriod.append(" / " + upgradeEyeInfo.getExtendContractPeriod() + " " + (isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE2A_STAFF : LtsConstant.EYE_TYPE_EYE2A));
				}
				if (StringUtils.isNotEmpty(upgradeEyeInfo.getAdminCharge())) {
					adminCharge.append(" /" + (isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE2A_STAFF : LtsConstant.EYE_TYPE_EYE2A)+ "-$" + upgradeEyeInfo.getAdminCharge());
				}
				if (StringUtils.isNotEmpty(upgradeEyeInfo.getCancelCharge())) {
					cancelCharge.append(" /" + (isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE2A_STAFF : LtsConstant.EYE_TYPE_EYE2A)+ "-$" + upgradeEyeInfo.getCancelCharge());
				}
				//overwrite upgradeEyeInfo.returnDeviceInd
				if(eye2aOfferAction == null && StringUtils.isNotEmpty(upgradeEyeInfo.getReturnDeviceInd())) {
					returnDeviceInd.append(" / " + (isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE2A_STAFF : LtsConstant.EYE_TYPE_EYE2A) + "-" + upgradeEyeInfo.getReturnDeviceInd() + returnDeviceText);
				}
			}*/
			
		}
/*
		if(eye2aOfferAction != null){
			returnDeviceInd.append(" / " + (isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE2A_STAFF : LtsConstant.EYE_TYPE_EYE2A) + "-" 
					+ (StringUtils.equals(eye2aOfferAction.getAction(), "O")?"Y":"N") + " (" + eye2aOfferAction.getDescription() + ")");
		}*/

		
		returnUpgradeEyeInfo.setContractPeriod(contractPeriod.toString());
		returnUpgradeEyeInfo.setExtendContractPeriod(extendContractPeriod.toString());
		returnUpgradeEyeInfo.setReturnDeviceInd(returnDeviceInd.toString());
		returnUpgradeEyeInfo.setAdminCharge(adminCharge.toString());
		returnUpgradeEyeInfo.setCancelCharge(cancelCharge.toString());
		
		/*  Get Pre-payment*/
		double tenure = 0;
		if (serviceProfile.getLtsTenure() >= serviceProfile.getImsTenure()){
			tenure = serviceProfile.getLtsTenure();
		} else {
			tenure = serviceProfile.getImsTenure();
		}

		String newPayMethod = null;
		AccountDetailProfileLtsDTO[] accDtl = serviceProfile.getAccounts();
		for (int i = 0; accDtl != null && i < accDtl.length; i++) {
			if (accDtl[i].isPrimaryAcctInd()){
				newPayMethod = accDtl[i].getPayMethod();
			}
		}
		
		String houseType = null;
		if (serviceProfile.getAddress() != null){
			if (serviceProfile.getAddress().getAddressRollout() != null){
				houseType = serviceProfile.getAddress().getAddressRollout().getHousingType();
			}
		}
		
		if (!StringUtils.equalsIgnoreCase(LtsConstant.EYE_TYPE_EYE3A, serviceProfile.getExistEyeType())) {
			StringBuilder prepayment = new StringBuilder();
			ItemDetailDTO eye3aPrepaymentItem = this.ltsPaymentService.getItemDetail(tenure, newPayMethod, houseType, LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale, null, LtsConstant.DEVICE_TYPE_EYE3A);
			ItemDetailDTO eye2aPrepaymentItem = this.ltsPaymentService.getItemDetail(tenure, newPayMethod, houseType, LtsConstant.DISPLAY_TYPE_ITEM_SELECT, locale, null, LtsConstant.DEVICE_TYPE_1020);
			
			if (eye3aPrepaymentItem != null && StringUtils.isNotEmpty(eye3aPrepaymentItem.getOneTimeAmtTxt())){
				prepayment.append((isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE3A_STAFF : LtsConstant.EYE_TYPE_EYE3A) + "-" + eye3aPrepaymentItem.getOneTimeAmtTxt());
			}
			
			if (StringUtils.isEmpty(serviceProfile.getExistEyeType())) {
				if (prepayment.length() != 0) {
					prepayment.append(" / ");
				}
				if (eye2aPrepaymentItem != null && StringUtils.isNotEmpty(eye2aPrepaymentItem.getOneTimeAmtTxt())){
					prepayment.append((isUpgradeToStaffPlan ? LtsConstant.EYE_TYPE_EYE2A_STAFF : LtsConstant.EYE_TYPE_EYE2A) + "-" + eye2aPrepaymentItem.getOneTimeAmtTxt());
				} 	
			}
			
			if (prepayment.length() != 0) {
				String prepaymentRemark = (String)prepaymentRemarkLkupCacheService.get("LTS_PREPAY_REMARK");
				returnUpgradeEyeInfo.setPrepaymentRemark(prepaymentRemark);
			}
			returnUpgradeEyeInfo.setPrepayment(prepayment.toString());
		}
		
		// Get SuggestSRD & SuggestSRDReason
		LtsSRDDTO ltsSRD = ltsAppointmentService.getEarliestSRD(pOrderCapture);
		
		if (ltsSRD.getDate() !=  null){
			StringBuffer suggestSrd = new StringBuffer();
		    String separator = "/";

		    suggestSrd.append(ltsSRD.getDate().get(Calendar.YEAR));
		    suggestSrd.append(separator);
		    suggestSrd.append(ltsSRD.getDate().get(Calendar.MONTH) + 1);
		    suggestSrd.append(separator);
		    suggestSrd.append(ltsSRD.getDate().get(Calendar.DATE));
		    
		    returnUpgradeEyeInfo.setSuggestSrd(suggestSrd.toString());
		}
		returnUpgradeEyeInfo.setSuggestSrdReason(ltsSRD.getInfo());
		
		return returnUpgradeEyeInfo;
	}
	
	
	public ServiceDetailProfileLtsDTO retrieve2ndDelServiceProfile(String srvNum, String pUser) {
		
		try {
			ServiceDetailProfileLtsDTO serviceProfile = this.serviceProfileLtsDrgService.getServiceProfile(srvNum, LtsConstant.SERVICE_TYPE_TEL, pUser);
			this.customerProfileLtsService.getCustomerRemark(serviceProfile.getPrimaryCust(), LtsConstant.SYSTEM_ID_DRG);
			this.customerProfileLtsService.getCustomerSpecialRemark(serviceProfile.getPrimaryCust());
			this.customerProfileLtsService.getWipMessage(serviceProfile.getPrimaryCust());
			this.getOfferDetails(serviceProfile);
			return serviceProfile;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return null;
		}
	}
}
