package com.bomwebportal.lts.web.disconnect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateServiceSelectionFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateServiceSelectionFormDTO.Action;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileAcqDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.Idd0060ProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.IddCallPlanProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.service.LtsRetrieveFsaService;
import com.bomwebportal.lts.service.acq.LtsAcqAccountProfileService;
import com.bomwebportal.lts.service.bom.ImsServiceProfileAccessService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsService;
import com.bomwebportal.lts.service.order.CallPlanLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.disconnect.LtsTerminateConstant;
import com.bomwebportal.lts.wsClientLts.BomCustProfileWsClient;
import com.bomwebportal.service.CodeLkupCacheService;
import com.google.common.collect.Lists;
import com.pccw.custProfileGateway.custProfile.CallPlanInfoDTO;
import com.pccw.util.CommonUtil;

public class LtsTerminateServiceSelectionController extends SimpleFormController {
	
	private final String viewName = "lts/disconnect/ltsterminateserviceselection";
	private final String nextView = "ltsterminatesalesinfo.html";
	private final String commandName = "ltsTerminateServiceSelectionCmd";
	
	private BomCustProfileWsClient bomCustProfileWsClient;
	
	private LtsRetrieveFsaService ltsRetrieveFsaService;
	private ImsServiceProfileAccessService imsServiceProfileAccessService;
	private ServiceProfileLtsService serviceProfileLtsService;
	private CallPlanLtsService callPlanLtsService;
	private LtsAcqAccountProfileService ltsAcqAccountProfileService;
	
	private CodeLkupCacheService eyeDisconnectReasonLkupCacheService;
	private CodeLkupCacheService delDisconnectReasonLkupCacheService;
	private CodeLkupCacheService waiveDFormReasonLkupCacheService;
	private CodeLkupCacheService relationshipCodeLkupCacheService;
	private CodeLkupCacheService relationshipBrCodeLkupCacheService;
	private CodeLkupCacheService titleLkupCacheService;
	private CodeLkupCacheService waiveLossPenaltyReasonLkupCacheService;

	
	public BomCustProfileWsClient getBomCustProfileWsClient() {
		return bomCustProfileWsClient;
	}

	public void setBomCustProfileWsClient(
			BomCustProfileWsClient bomCustProfileWsClient) {
		this.bomCustProfileWsClient = bomCustProfileWsClient;
	}

	public LtsRetrieveFsaService getLtsRetrieveFsaService() {
		return ltsRetrieveFsaService;
	}

	public void setLtsRetrieveFsaService(LtsRetrieveFsaService ltsRetrieveFsaService) {
		this.ltsRetrieveFsaService = ltsRetrieveFsaService;
	}

	public ImsServiceProfileAccessService getImsServiceProfileAccessService() {
		return imsServiceProfileAccessService;
	}

	public void setImsServiceProfileAccessService(
			ImsServiceProfileAccessService imsServiceProfileAccessService) {
		this.imsServiceProfileAccessService = imsServiceProfileAccessService;
	}

	public ServiceProfileLtsService getServiceProfileLtsService() {
		return serviceProfileLtsService;
	}

	public void setServiceProfileLtsService(
			ServiceProfileLtsService serviceProfileLtsService) {
		this.serviceProfileLtsService = serviceProfileLtsService;
	}

	public CallPlanLtsService getCallPlanLtsService() {
		return callPlanLtsService;
	}

	public void setCallPlanLtsService(CallPlanLtsService callPlanLtsService) {
		this.callPlanLtsService = callPlanLtsService;
	}

	public CodeLkupCacheService getEyeDisconnectReasonLkupCacheService() {
		return eyeDisconnectReasonLkupCacheService;
	}

	public void setEyeDisconnectReasonLkupCacheService(
			CodeLkupCacheService eyeDisconnectReasonLkupCacheService) {
		this.eyeDisconnectReasonLkupCacheService = eyeDisconnectReasonLkupCacheService;
	}

	public CodeLkupCacheService getDelDisconnectReasonLkupCacheService() {
		return delDisconnectReasonLkupCacheService;
	}

	public void setDelDisconnectReasonLkupCacheService(
			CodeLkupCacheService delDisconnectReasonLkupCacheService) {
		this.delDisconnectReasonLkupCacheService = delDisconnectReasonLkupCacheService;
	}

	public CodeLkupCacheService getWaiveDFormReasonLkupCacheService() {
		return waiveDFormReasonLkupCacheService;
	}

	public void setWaiveDFormReasonLkupCacheService(
			CodeLkupCacheService waiveDFormReasonLkupCacheService) {
		this.waiveDFormReasonLkupCacheService = waiveDFormReasonLkupCacheService;
	}

	public CodeLkupCacheService getRelationshipCodeLkupCacheService() {
		return relationshipCodeLkupCacheService;
	}

	public void setRelationshipCodeLkupCacheService(
			CodeLkupCacheService relationshipCodeLkupCacheService) {
		this.relationshipCodeLkupCacheService = relationshipCodeLkupCacheService;
	}

	public CodeLkupCacheService getRelationshipBrCodeLkupCacheService() {
		return relationshipBrCodeLkupCacheService;
	}

	public void setRelationshipBrCodeLkupCacheService(
			CodeLkupCacheService relationshipBrCodeLkupCacheService) {
		this.relationshipBrCodeLkupCacheService = relationshipBrCodeLkupCacheService;
	}

	public CodeLkupCacheService getTitleLkupCacheService() {
		return titleLkupCacheService;
	}

	public void setTitleLkupCacheService(CodeLkupCacheService titleLkupCacheService) {
		this.titleLkupCacheService = titleLkupCacheService;
	}

	public CodeLkupCacheService getWaiveLossPenaltyReasonLkupCacheService() {
		return waiveLossPenaltyReasonLkupCacheService;
	}

	public void setWaiveLossPenaltyReasonLkupCacheService(
			CodeLkupCacheService waiveLossPenaltyReasonLkupCacheService) {
		this.waiveLossPenaltyReasonLkupCacheService = waiveLossPenaltyReasonLkupCacheService;
	}

	public LtsAcqAccountProfileService getLtsAcqAccountProfileService() {
		return ltsAcqAccountProfileService;
	}

	public void setLtsAcqAccountProfileService(
			LtsAcqAccountProfileService ltsAcqAccountProfileService) {
		this.ltsAcqAccountProfileService = ltsAcqAccountProfileService;
	}

	public LtsTerminateServiceSelectionController() {
		setCommandClass(LtsTerminateServiceSelectionFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request,response);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		LtsTerminateServiceSelectionFormDTO form = orderCapture.getLtsTerminateServiceSelectionForm();

		if (form != null) {
			return form;
		}
		
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		form = initForm(orderCapture.getLtsServiceProfile(), bomSalesUser);
		orderCapture.setLtsTerminateServiceSelectionForm(form);
		
		return form;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response,Object command,BindException errors) throws Exception {
		
		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		LtsTerminateServiceSelectionFormDTO form = (LtsTerminateServiceSelectionFormDTO) command;
		orderCapture.setLtsTerminateServiceSelectionForm(form);
		switch (form.getFormAction()) {

		case UPDATE_LIST:
			setProfileItemList(orderCapture.getLtsServiceProfile(), form);
			setIddCallPlanProfileList(orderCapture.getLtsServiceProfile(), form);
			
			return new ModelAndView(new RedirectView("ltsterminateserviceselection.html"));

		default:
			break;
		}
		
		

		return new ModelAndView(new RedirectView(nextView));
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		Map<String, String> penaltyMap = new TreeMap<String, String>();
		penaltyMap.put(LtsConstant.OFFER_HANDLE_AUTO_GENERATE, "Generate");
		penaltyMap.put(LtsConstant.OFFER_HANDLE_MANUAL_WAIVE, "Waive");	
		referenceData.put("penaltyMap", penaltyMap);

		referenceData.put("relationshipCodeList", Arrays.asList(relationshipCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("relationshipBrCodeList", Arrays.asList(relationshipBrCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("titleList", Arrays.asList(titleLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("eyeDisconenctReasonList", Arrays.asList(eyeDisconnectReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("delDisconenctReasonList", Arrays.asList(delDisconnectReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("waiveDFormReasonList", Arrays.asList(waiveDFormReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("waiveLossPenaltyReasonList", Arrays.asList(waiveLossPenaltyReasonLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		
		return referenceData;
	}
	
	private LtsTerminateServiceSelectionFormDTO initForm(ServiceDetailProfileLtsDTO ltsServiceProfile, BomSalesUserDTO bomSalesUser) {
		LtsTerminateServiceSelectionFormDTO form = new LtsTerminateServiceSelectionFormDTO();
		setServiceToTerminateOption(ltsServiceProfile, form);
		setFsaWgInd(ltsServiceProfile, bomSalesUser, form);
		setThirdPartyInd(ltsServiceProfile, form);
		setProfileItemList(ltsServiceProfile, form);
		setIdd0060Info(ltsServiceProfile, form);
		setIddCallPlanProfileList(ltsServiceProfile, form);
		setCallingCardInfo(ltsServiceProfile, form);
		checkSplitAccount(ltsServiceProfile, form);
		
		form.setBundle2GTp(LtsSbOrderHelper.isBundle2GTp(ltsServiceProfile));
		form.setToday(LocalDate.now().toString(DateTimeFormat.forPattern("yyyyMMdd")));
		
		form.setSrvAcctNum(getProfileAcctNumByChrgType(ltsServiceProfile, LtsConstant.ACCOUNT_CHARGE_TYPE_R));
		form.setHasOtherSrvSameAcc(isAccContainsOtherSrv(ltsServiceProfile, form));
		
		return form;
	}
	
	private boolean isAccContainsOtherSrv(ServiceDetailProfileLtsDTO ltsServiceProfile, LtsTerminateServiceSelectionFormDTO form){
		String custNum = ltsServiceProfile.getPrimaryCust().getCustNum();
		String profileAccNum = getProfileAcctNumByChrgType(ltsServiceProfile, LtsConstant.ACCOUNT_CHARGE_TYPE_R);
		String profileSrvNum = ltsServiceProfile.getSrvNum();
		
		ServiceDetailProfileLtsDTO[] allTelSrv = serviceProfileLtsService.getServiceProfileByCustomer(custNum, LtsConstant.SERVICE_TYPE_TEL);
		
		if(allTelSrv == null || allTelSrv.length == 0){
			return false;
		}
		
		for(ServiceDetailProfileLtsDTO srv: allTelSrv){
			String accNum = getProfileAcctNumByChrgType(srv, LtsConstant.ACCOUNT_CHARGE_TYPE_R);
			if(StringUtils.equals(srv.getSrvType(), LtsConstant.SERVICE_TYPE_TEL)
					&& StringUtils.equals(accNum, profileAccNum)
					&& !StringUtils.equals(srv.getSrvNum(), profileSrvNum)){
				return true;
			}
		}
		
		return false;
	}
	
	private void checkSplitAccount(ServiceDetailProfileLtsDTO ltsServiceProfile, LtsTerminateServiceSelectionFormDTO form){
		if (ArrayUtils.isEmpty(ltsServiceProfile.getAccounts()) || ltsServiceProfile.getAccounts().length < 2) {
			return;
		}
		form.setSplitAcctInd(false);
		for(AccountDetailProfileLtsDTO profileAcctDtl: ltsServiceProfile.getAccounts()){
			String chrgType = profileAcctDtl.getAcctChrgType();
			if(chrgType.contains("R") && !chrgType.contains("I")
					|| chrgType.contains("I") && !chrgType.contains("R")){
				form.setSplitAcctInd(true);
				return;
			}
		}
		
	}
	
	private void setCallingCardInfo(ServiceDetailProfileLtsDTO ltsServiceProfile, LtsTerminateServiceSelectionFormDTO form) {
		if (ltsServiceProfile.getPrimaryCust() == null) {
			return;
		}
		
		String custNum = ltsServiceProfile.getPrimaryCust().getCustNum();
		String srvAcctNum = getProfileAcctNumByChrgType(ltsServiceProfile, LtsConstant.ACCOUNT_CHARGE_TYPE_R);
		
		ServiceDetailProfileLtsDTO[] itsProfiles = serviceProfileLtsService.getServiceProfileByCustomer(custNum, LtsConstant.SERVICE_TYPE_ITS);

		if (ArrayUtils.isNotEmpty(itsProfiles)) {
			HashSet<String> itsAccSet = new HashSet<String>();
			
			for(ServiceDetailProfileLtsDTO itsProfile: itsProfiles){
				String itsProfileAcc = getProfileAcctNumByChrgType(itsProfile, LtsConstant.ACCOUNT_CHARGE_TYPE_R);
				if(StringUtils.equals(itsProfileAcc, srvAcctNum)){
					form.setCallingCardSameAcct(true);
				}
				itsAccSet.add(itsProfileAcc);
			}

			form.setCallingCardAcctList(StringUtils.join(itsAccSet, ','));
			form.setCallingCardInd(true);
			form.setCallingCardDetailsHandling(LtsTerminateConstant.CALLING_CARD_HANDLING_DISCONNECT);
		}
	}
	
	private String getProfileAcctNumByChrgType(ServiceDetailProfileLtsDTO serviceProfile, String chrgType){
		
		if(serviceProfile == null || StringUtils.isEmpty(chrgType)){
			return null;
		}
		
		AccountDetailProfileLtsDTO acct = LtsSbOrderHelper.getProfileAccount(serviceProfile, chrgType);

		if(acct != null){
			return acct.getAcctNum();
		}
		
		return null;
	}
	
	private void setIdd0060Info(ServiceDetailProfileLtsDTO ltsServiceProfile, LtsTerminateServiceSelectionFormDTO form) {
		if (ltsServiceProfile.getPrimaryCust() == null) {
			return;
		}
		
		String custNum = ltsServiceProfile.getPrimaryCust().getCustNum();
		String srvAcctNum = getProfileAcctNumByChrgType(ltsServiceProfile, LtsConstant.ACCOUNT_CHARGE_TYPE_R);
		
		ServiceDetailProfileLtsDTO[] iddProfiles = serviceProfileLtsService.getServiceProfileByCustomer(custNum, LtsConstant.SERVICE_TYPE_MOB);
		
		if (ArrayUtils.isEmpty(iddProfiles)) {
			return;
		}	
		List<Idd0060ProfileLtsDTO> idd0060ProfileList = new ArrayList<Idd0060ProfileLtsDTO>();
		Idd0060ProfileLtsDTO iddProfileDetail = null;
		
		for (ServiceDetailProfileLtsDTO iddProfile : iddProfiles) {
			
			iddProfileDetail = new Idd0060ProfileLtsDTO();
			
			iddProfileDetail.setSrvNum(iddProfile.getSrvNum());
			iddProfileDetail.setSrvType(iddProfile.getSrvType());
			iddProfileDetail.setDatCode(iddProfile.getDatCd());
			iddProfileDetail.setAction(LtsConstant.IDD_ACTION_RETAIN);

			String iddSrvAcctNum = getProfileAcctNumByChrgType(iddProfile, LtsConstant.ACCOUNT_CHARGE_TYPE_R);
			iddProfileDetail.setSameAcct(StringUtils.equals(srvAcctNum, iddSrvAcctNum));
			iddProfileDetail.setAcctNum(iddSrvAcctNum);
			
			idd0060ProfileList.add(iddProfileDetail);
			
		}
		form.setIdd0060ProfileList(idd0060ProfileList);
	}
	
	private void setServiceToTerminateOption(ServiceDetailProfileLtsDTO ltsServiceProfile, LtsTerminateServiceSelectionFormDTO form){
		if (StringUtils.isEmpty(ltsServiceProfile.getExistEyeType())
				|| StringUtils.isEmpty(ltsServiceProfile.getRelatedEyeFsa())) {
			return;
		}
		
		if(StringUtils.contains(ltsServiceProfile.getEyeFsaProfile().getSrvType(), LtsConstant.FSA_SRV_TYPE_PCD)){
			form.setTerminatePCDind(true);
		}
		if(StringUtils.contains(ltsServiceProfile.getEyeFsaProfile().getSrvType(), LtsConstant.FSA_SRV_TYPE_HDTV )
				|| StringUtils.contains(ltsServiceProfile.getEyeFsaProfile().getSrvType(), LtsConstant.FSA_SRV_TYPE_SDTV  )){
			form.setTerminateHDTVind(true);
		}
		
	}
	
	private void setFsaWgInd(ServiceDetailProfileLtsDTO ltsServiceProfile, BomSalesUserDTO bomSalesUser, LtsTerminateServiceSelectionFormDTO form) {
		
		if (StringUtils.isEmpty(ltsServiceProfile.getExistEyeType())
				|| StringUtils.isEmpty(ltsServiceProfile.getRelatedEyeFsa())) {
			return;
		}
		
		FsaServiceDetailOutputDTO fsaProfile = ltsServiceProfile.getEyeFsaProfile();
		
		if (fsaProfile == null) {
			return;
		}
		
		if (FsaServiceType.WG == ltsRetrieveFsaService.getExistSrvType(fsaProfile)) {
			form.setFsaWgInd(true);
		}
	}
	
	private void setProfileItemList(ServiceDetailProfileLtsDTO ltsServiceProfile, LtsTerminateServiceSelectionFormDTO form) {
		
		if (ArrayUtils.isEmpty(ltsServiceProfile.getItemDetails())) {
			return;
		}
		
		
		List<ItemDetailProfileLtsDTO> allProfileItems = Lists.newArrayList(ltsServiceProfile.getItemDetails());
		if(ltsServiceProfile.getEyeFsaProfile() != null && ArrayUtils.isNotEmpty(ltsServiceProfile.getEyeFsaProfile().getItems())){
			allProfileItems.addAll(Arrays.asList(ltsServiceProfile.getEyeFsaProfile().getItems()));
		}
		
		List<ItemDetailProfileLtsDTO> srvPlanItemDetails = new ArrayList<ItemDetailProfileLtsDTO>();
		List<ItemDetailProfileLtsDTO> vasPlanItemDetails = new ArrayList<ItemDetailProfileLtsDTO>();
		List<ItemDetailProfileLtsDTO> fsaVasPlanItemDetails = new ArrayList<ItemDetailProfileLtsDTO>();
		
		for(ItemDetailProfileLtsDTO profileItem : allProfileItems) {

			try {
				ItemDetailProfileLtsDTO clonedProfileItem = (ItemDetailProfileLtsDTO)CommonUtil.cloneNestedSerializableObject(profileItem);
				
				if(LtsConstant.ITEM_TYPE_SERVICE.equals(clonedProfileItem.getItemType())) {
					if(StringUtils.isNotEmpty(clonedProfileItem.getRemainContractMonth())) {
						if(form.getFormAction()== Action.UPDATE_LIST && LtsSbOrderHelper.isDeceasedCase(form) ){
							clonedProfileItem.getItemDetail().setPenaltyHandling(LtsConstant.OFFER_HANDLE_AUTO_WAIVE);
						}else{
						clonedProfileItem.getItemDetail().setPenaltyHandling(LtsConstant.OFFER_HANDLE_AUTO_GENERATE);
						}
					}
					srvPlanItemDetails.add(clonedProfileItem);
				}
				
				if(LtsConstant.ITEM_TYPE_VAS.equals(clonedProfileItem.getItemType())) {
					if(StringUtils.isNotEmpty(clonedProfileItem.getConditionEffEndDate())) {
						if(form.getFormAction()== Action.UPDATE_LIST && LtsSbOrderHelper.isDeceasedCase(form) ){
							clonedProfileItem.getItemDetail().setPenaltyHandling(LtsConstant.OFFER_HANDLE_AUTO_WAIVE);
						}else{
							clonedProfileItem.getItemDetail().setPenaltyHandling(LtsConstant.OFFER_HANDLE_AUTO_GENERATE);
						}
					}
					vasPlanItemDetails.add(clonedProfileItem);
				}
				
				if(LtsConstant.ITEM_TYPE_WALL_GARDEN.equals(clonedProfileItem.getItemType()) 
						|| LtsConstant.ITEM_TYPE_MIRROR_PLAN.equals(clonedProfileItem.getItemType()) 
						|| LtsConstant.ITEM_TYPE_EXPIRED_MIRROR_PLAN.equals(clonedProfileItem.getItemType())) {
					
					if(StringUtils.isNotEmpty(clonedProfileItem.getRemainContractMonth())) {
						if(form.getFormAction()== Action.UPDATE_LIST && LtsSbOrderHelper.isDeceasedCase(form) ){
							clonedProfileItem.getItemDetail().setPenaltyHandling(LtsConstant.OFFER_HANDLE_AUTO_WAIVE);
						}else{
							clonedProfileItem.getItemDetail().setPenaltyHandling(LtsConstant.OFFER_HANDLE_AUTO_GENERATE);
						}
					}
					fsaVasPlanItemDetails.add(clonedProfileItem);
				}
			}
			catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
				throw new AppRuntimeException(e.getMessage());
			}
		}
		
		form.setSrvPlanItemDetails(srvPlanItemDetails);
		form.setVasPlanItemDetails(vasPlanItemDetails);
		form.setFsaVasPlanItemDetails(fsaVasPlanItemDetails);
	}
	
	
	private void setThirdPartyInd(ServiceDetailProfileLtsDTO ltsServiceProfile, LtsTerminateServiceSelectionFormDTO form) {
		if (ltsServiceProfile.getPrimaryCust() == null) {
			return;
		}
		form.setThirdParty(LtsConstant.DOC_TYPE_HKBR.equalsIgnoreCase(ltsServiceProfile.getPrimaryCust().getDocType()));
	}
	
	private String[] getAllPlanCodes(List<CallPlanInfoDTO> callPlanInfoList) {
		if(callPlanInfoList == null || callPlanInfoList.isEmpty()) {
			return null;
		}
		String[] callPlanCodes = new String[callPlanInfoList.size()];
		for (int i = 0; i < callPlanCodes.length; i++) {
			callPlanCodes[i] = callPlanInfoList.get(i).getPlanCd();
		}
		return callPlanCodes;
	}
	
	private void setIddCallPlanProfileList(ServiceDetailProfileLtsDTO ltsServiceProfile , LtsTerminateServiceSelectionFormDTO form){
		
		AccountDetailProfileLtsDTO[] profileAccounts = ltsServiceProfile.getAccounts();
		
		String acctNum = null;
		String custNum = null;
		
		if (ArrayUtils.isNotEmpty(profileAccounts)) {
			for (AccountDetailProfileLtsDTO profileAccount : profileAccounts) {
				if (profileAccount.isPrimaryAcctInd()) {
					acctNum = profileAccount.getAcctNum();
					custNum = profileAccount.getCustomerProfile().getCustNum();
				}
			}
		}

		if (StringUtils.isEmpty(acctNum)) {
			return;
		}
		
		/*check if Billable account exist*/
		List<AccountDetailProfileAcqDTO> custAcctList = this.ltsAcqAccountProfileService.getAcctListByCustNum(custNum);
		List<AccountDetailProfileAcqDTO> billableAcctList = new ArrayList<AccountDetailProfileAcqDTO>();
		if(custAcctList != null && custAcctList.size() > 0){
			for(AccountDetailProfileAcqDTO accDtl : custAcctList){
				if(StringUtils.isNotBlank(accDtl.getSrvNum()) && !StringUtils.equals(accDtl.getAcctNum(), acctNum)){
					billableAcctList.add(accDtl);
				}
			}
		}
		
		if(billableAcctList.size() > 0){
			form.setHasBillableAcct(true);
			form.setBillableAcctList(billableAcctList);
		}
		
		try {
			List<CallPlanInfoDTO> callPlanInfoList = bomCustProfileWsClient.iddCallPlanEnquiry(acctNum);
//			List<CallPlanInfoDTO> callPlanInfoList = null;
			
			if(callPlanInfoList == null || callPlanInfoList.isEmpty()){
				return;
			} 

//			IddCallPlanProfileLtsDTO[] iddCallPlanProfiles = mapIddCallPlan(callPlanInfoList);
			IddCallPlanProfileLtsDTO[] iddCallPlanProfiles = mapIddCallPlan(removeOverDueAndFreeCallPlan(callPlanInfoList));
			
			if (ArrayUtils.isEmpty(iddCallPlanProfiles)) {
				return;
			}

			List<IddCallPlanProfileLtsDTO> clonedIddCallPlanProfiles = new ArrayList<IddCallPlanProfileLtsDTO>();
			
			for (CallPlanInfoDTO callPlanInfo : callPlanInfoList) {
				for (IddCallPlanProfileLtsDTO iddCallPlanProfile : iddCallPlanProfiles) {
					iddCallPlanProfile.setAction(LtsConstant.IDD_ACTION_RETAIN);
					if (StringUtils.equals(iddCallPlanProfile.getPlanCd(), callPlanInfo.getPlanCd())) {
						IddCallPlanProfileLtsDTO clonedIddCallPlanProfile = (IddCallPlanProfileLtsDTO)CommonUtil.cloneNestedSerializableObject(iddCallPlanProfile);

						if(StringUtils.equals(callPlanInfo.getContractEndDate(),"00000000") || !StringUtils.isBlank(callPlanInfo.getRegTermnDate())){
						
							if(StringUtils.equals(callPlanInfo.getContractStartDate(),"00000000") || StringUtils.isBlank(callPlanInfo.getContractStartDate())){
								clonedIddCallPlanProfile.setContractStartDate(null);
							}else{
								clonedIddCallPlanProfile.setContractStartDate(callPlanInfo.getContractStartDate());
							}
							
							if(StringUtils.equals(callPlanInfo.getContractEndDate(),"00000000") ||  StringUtils.isBlank(callPlanInfo.getContractEndDate())){
								clonedIddCallPlanProfile.setContractEndDate(null);
							}else{
								clonedIddCallPlanProfile.setContractEndDate(callPlanInfo.getContractEndDate());
							}
							
							if(StringUtils.equals(callPlanInfo.getRegEffDate(), "00000000") ||  StringUtils.isBlank(callPlanInfo.getRegEffDate())){
						    	clonedIddCallPlanProfile.setEffStartDate(null);
						    }else{
						    	clonedIddCallPlanProfile.setEffStartDate(callPlanInfo.getRegEffDate());
						    }
							
							if(StringUtils.equals(callPlanInfo.getRegTermnDate(), "00000000") || StringUtils.isBlank(callPlanInfo.getRegTermnDate()) ){
								clonedIddCallPlanProfile.setEffEndDate(null);
							}else{
								clonedIddCallPlanProfile.setEffEndDate(callPlanInfo.getRegTermnDate());
							}
							//
							/* Calculate remain contract in format 00y 00m 00d*/
							clonedIddCallPlanProfile.setRemainContract("--");
							if(!StringUtils.equals(callPlanInfo.getContractEndDate(), "00000000") && !StringUtils.isBlank(callPlanInfo.getContractEndDate()) ){
								LocalDate sysDateForCalc = LocalDate.now();
								LocalDate contractEndDateForCalc = LocalDate.parse(callPlanInfo.getContractEndDate(), DateTimeFormat.forPattern("yyyyMMdd"));
								LocalDate contractStartDateForCalc = LocalDate.parse(callPlanInfo.getContractStartDate(), DateTimeFormat.forPattern("yyyyMMdd"));
								
								if(contractEndDateForCalc.isAfter(sysDateForCalc)
										&& contractStartDateForCalc.isBefore(sysDateForCalc)){
									int daysToSubtract = (sysDateForCalc.getDayOfMonth() - 1);
									int monthsToSubtract = (sysDateForCalc.getMonthOfYear() - 1);
									sysDateForCalc = sysDateForCalc.minusDays(daysToSubtract);
									sysDateForCalc = sysDateForCalc.minusMonths(monthsToSubtract);
									contractEndDateForCalc = contractEndDateForCalc.minusDays(daysToSubtract);
									contractEndDateForCalc = contractEndDateForCalc.minusMonths(monthsToSubtract);
									Period remainPeriod = Period.fieldDifference(sysDateForCalc, contractEndDateForCalc);
									clonedIddCallPlanProfile.setRemainContract(remainPeriod.toString(
											new PeriodFormatterBuilder().printZeroAlways()
											.appendYears().appendSuffix("y ")
											.appendMonths().appendSuffix("m ")
											.appendDays().appendSuffix("d")
											.toFormatter()
									));

									if(remainPeriod.getYears() > 0
												|| remainPeriod.getMonths() > 3
												|| (remainPeriod.getMonths() == 3 && remainPeriod.getDays() > 0) ){
										clonedIddCallPlanProfile.setRemainContractGt3Mths(true);
									}
								}
							}
							
							if(callPlanInfo.getPlanFixedFreeChrg() != null){
								double doubleGrossPrice = Double.parseDouble(callPlanInfo.getPlanFixedFreeChrg().replace("+",""));
								String grossPrice = String.format("%.2f", doubleGrossPrice);
								clonedIddCallPlanProfile.setGrossEffPrice(grossPrice);
							}else{
								clonedIddCallPlanProfile.setGrossEffPrice(callPlanInfo.getPlanFixedFreeChrg());
							}
							
							clonedIddCallPlanProfile.setPlanType(callPlanInfo.getPlanType());
							clonedIddCallPlanProfile.setPlanHolderType(callPlanInfo.getPlanHolderType());
							clonedIddCallPlanProfile.setTpExpiredMonths(callPlanInfo.getContractDuratn());
							clonedIddCallPlanProfile.setDca(callPlanInfo.getCharingAccount());
						
							if(StringUtils.equals(callPlanInfo.getPlanHolderType(),"C")){
								clonedIddCallPlanProfile.setPlanHolder(custNum);
							}else if (StringUtils.equals(callPlanInfo.getPlanHolderType(),"A")){
								clonedIddCallPlanProfile.setPlanHolder(acctNum);
							}
							else if (StringUtils.equals(callPlanInfo.getPlanHolderType(),"R")){
								clonedIddCallPlanProfile.setPlanHolder(callPlanInfo.getPlanHolderId());
							}
						
							//default penalty handling
							if(form.getFormAction() == Action.UPDATE_LIST && LtsSbOrderHelper.isDeceasedCase(form) ){
								clonedIddCallPlanProfile.setPenaltyHandling(LtsConstant.OFFER_HANDLE_AUTO_WAIVE);
							}else{								
								if(!StringUtils.equals(callPlanInfo.getRegTermnDate(), "00000000") && !StringUtils.isBlank(callPlanInfo.getRegTermnDate()) ){
									clonedIddCallPlanProfile.setPenaltyHandling(LtsConstant.CALL_PLAN_PEN_OTHER_PARTY_HNDL);
								}else{
									clonedIddCallPlanProfile.setPenaltyHandling(null);
								}
							}
							
						}
						else continue;
						
						clonedIddCallPlanProfiles.add(clonedIddCallPlanProfile);
						
						break;
					}
				}
			}
			
			Collections.sort(clonedIddCallPlanProfiles, new Comparator<IddCallPlanProfileLtsDTO>(){
				public int compare(IddCallPlanProfileLtsDTO o1, IddCallPlanProfileLtsDTO o2) {
					return o1.getPlanCd().compareTo(o2.getPlanCd());
				}
			});
		
			form.setIddCallPlanProfileList(clonedIddCallPlanProfiles);

		} catch(Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
	public IddCallPlanProfileLtsDTO[] mapIddCallPlan(List<CallPlanInfoDTO> callPlanInfoList) {
		if(callPlanInfoList == null || callPlanInfoList.isEmpty()){
			return null;
		}
		String[] allCallPlanCodes = getAllPlanCodes(callPlanInfoList);
		IddCallPlanProfileLtsDTO[] mappedCallPlan = callPlanLtsService.mapIddCallPlan(allCallPlanCodes);
		
		for(IddCallPlanProfileLtsDTO callPlan : mappedCallPlan){
			callPlan.setGiftType("GIFT".equals(callPlanLtsService.getCallPlanType(callPlan.getPlanCd()))? true:false);
		}
		return mappedCallPlan;

	}
	
	private List<CallPlanInfoDTO> removeOverDueAndFreeCallPlan(List<CallPlanInfoDTO> pList){
		
		if(pList == null || pList.isEmpty()){
			
			return null;			
		}
		
		List<CallPlanInfoDTO> newCallPlanList = new ArrayList<CallPlanInfoDTO>();
		
		for(CallPlanInfoDTO callPlanDto : pList ){
			
			if(StringUtils.isBlank(callPlanDto.getRegTermnDate())
					|| (!LtsDateFormatHelper.isDateOverdue(callPlanDto.getRegTermnDate(), "yyyyMMdd") 
							&& !LtsDateFormatHelper.getSysDate("yyyyMMdd").equals(callPlanDto.getRegTermnDate()))
					|| StringUtils.equals("00000000", callPlanDto.getRegTermnDate())){
				
				if(callPlanDto.getPlanFixedFreeChrg()!= null 
						&& !StringUtils.isBlank(callPlanDto.getPlanFixedFreeChrg()) 
						&& !StringUtils.equals(callPlanDto.getPlanFixedFreeChrg(), "+000000000.00")){
					newCallPlanList.add(callPlanDto);
				}
			}
		}
		
		if(newCallPlanList.isEmpty()){ 
			return null;
		}
		
		return newCallPlanList;
		
	}

	
	
}
