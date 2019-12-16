package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomCustomerVerificationDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAccountSelectionFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPersonalInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSalesInfoFormDTO;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustPdpoProfileDTO;
import com.bomwebportal.lts.dto.profile.CustomerContactProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.acq.LtsAcqCustomerDetailService;
import com.bomwebportal.lts.service.bom.CustPdpoProfileService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.order.ImsSbOrderService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.acq.LtsAcqSbOrderHelper;
import com.bomwebportal.lts.wsClientLts.BomCustProfileWsClient;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.CustomerInformationService;
import com.bomwebportal.util.Utility;
import com.pccw.custProfileGateway.custProfile.AddressMaintDTO;
import com.pccw.custProfileGateway.custProfile.ContactInfoDTO;
import com.pccw.custProfileGateway.custProfile.CustomerDTO;

public class LtsAcqPersonalInfoController extends SimpleFormController {

	protected final Log log = LogFactory.getLog(getClass());

	private String wsUrl;
	private String user;
	private String password;
   
	private BomCustProfileWsClient bomWsCreateCustClient;
	private CustomerProfileLtsService customerProfileLtsService;
	private LtsAcqCustomerDetailService ltsAcqCustomerDetailService;
	private CodeLkupCacheService relationshipCodeLkupCacheService;
	private CodeLkupCacheService relationshipBrCodeLkupCacheService;
	private CodeLkupCacheService newCustTitleAcqLkupCacheService;
	private CodeLkupCacheService titleAcqLkupCacheService;
	private LtsCommonService ltsCommonService;
	private CustPdpoProfileService custPdpoProfileService;
	private CustomerInformationService customerInformationService;
	private final String viewName = "lts/acq/ltsacqpersonalinfo";
	private final String nextView = "ltsacqmodemarrangement.html";
	private final String nextView2 = "ltsacqaccountselection.html";
	private final String commandName = "ltspersonalinfo";

	protected ImsSbOrderService imsSbOrderService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public ImsSbOrderService getImsSbOrderService() {
		return imsSbOrderService;
	}

	public void setImsSbOrderService(ImsSbOrderService imsSbOrderService) {
		this.imsSbOrderService = imsSbOrderService;
	}
	public LtsAcqPersonalInfoController() {
		this.setFormView(viewName);
		this.setCommandName(commandName);
		this.setCommandClass(LtsAcqPersonalInfoFormDTO.class);
	}

	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		if (orderCaptureDTO == null) {
		    return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}

	public Object formBackingObject(HttpServletRequest request)throws ServletException {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqPersonalInfoFormDTO ltsPersonalInfoFormDTO = orderCaptureDTO.getLtsAcqPersonalInfoForm();
		CustomerDetailProfileLtsDTO cust = orderCaptureDTO.getCustomerDetailProfileLtsDTO();
		if (ltsPersonalInfoFormDTO != null){
			if(StringUtils.isNotBlank(ltsPersonalInfoFormDTO.getCustNum())){
				ltsPersonalInfoFormDTO.setOldCustNum(ltsPersonalInfoFormDTO.getCustNum());
			}
		}
		if (ltsPersonalInfoFormDTO == null) {
			ltsPersonalInfoFormDTO = new LtsAcqPersonalInfoFormDTO();

			
			if(orderCaptureDTO.getBomVerifiedCustomerResult()!=null && orderCaptureDTO.isChannelDirectSales()){
				ltsPersonalInfoFormDTO.setCustNum(orderCaptureDTO.getBomVerifiedCustomerResult().getLtsCustNum());
				ltsPersonalInfoFormDTO.setDocNum(orderCaptureDTO.getBomVerifiedCustomerResult().getInputIdDocNum());
				ltsPersonalInfoFormDTO.setDocumentType(orderCaptureDTO.getBomVerifiedCustomerResult().getInputIdDocType());
				ltsPersonalInfoFormDTO.setFamilyName(orderCaptureDTO.getBomVerifiedCustomerResult().getInputLastName());
				ltsPersonalInfoFormDTO.setGivenName(orderCaptureDTO.getBomVerifiedCustomerResult().getInputFirstName());
			}else if (cust != null && !orderCaptureDTO.isChannelDirectSales()) {
				ltsPersonalInfoFormDTO.setCustNum(cust.getCustNum());
				ltsPersonalInfoFormDTO.setDocNum(cust.getDocNum());
				ltsPersonalInfoFormDTO.setDocumentType(cust.getDocType());
				ltsPersonalInfoFormDTO.setFamilyName(cust.getLastName());
				ltsPersonalInfoFormDTO.setGivenName(cust.getFirstName());
				ltsPersonalInfoFormDTO.setTitle(cust.getTitle());
				ltsPersonalInfoFormDTO.setCompanyName(cust.getCompanyName());
			}

			ltsPersonalInfoFormDTO.setAgreement(true);
		}
		
		//TODO
		setupEligibleDocType(ltsPersonalInfoFormDTO, orderCaptureDTO);
		
//		ltsPersonalInfoFormDTO.setDocTypeNotMatchMsg("");
//		List<ItemDetailDTO> itemDetailList = new ArrayList<ItemDetailDTO>();
//		itemDetailList = ltsCommonService.getAcqBasketItem(orderCaptureDTO);
//		
//		int cntEligibleDocType = 0;
//		for(ItemDetailDTO itemDtl : itemDetailList){
//			if(StringUtils.isNotBlank(itemDtl.getEligibleDocType())){
//				if(cntEligibleDocType == 0){
//					ltsPersonalInfoFormDTO.setEligibleDocType(itemDtl.getEligibleDocType());
//					cntEligibleDocType++;
//				}else{//more than one EligibleDocType
//					if(!StringUtils.equals(itemDtl.getEligibleDocType(), ltsPersonalInfoFormDTO.getEligibleDocType())){
//						ltsPersonalInfoFormDTO.setDocTypeNotMatchMsg(messageSource.getMessage("lts.acq.personalInfo.multiEligibleDocType", new Object[] {}, this.locale));
//					}
//				}
//			}
//		}
		
		
		
//		ltsPersonalInfoFormDTO.setWarningCustomerNotMatchMessage("");
//		if(orderCaptureDTO.isChannelDirectSales() && orderCaptureDTO.getBomVerifiedCustomerResult()!=null){
//			if(!orderCaptureDTO.getBomVerifiedCustomerResult().isMatchWithBomName()){
//				ltsPersonalInfoFormDTO.setWarningCustomerNotMatchMessage("Customer name NOT match.");
//			}
//		}
		ltsPersonalInfoFormDTO.setAcctNumNotMatchMsg("");
		if(orderCaptureDTO.getLtsAcqPaymentMethodFormDTO()!=null){
			if(orderCaptureDTO.getLtsAcqPaymentMethodFormDTO().isAcctNotMatch()){
				ltsPersonalInfoFormDTO.setAcctNumNotMatchMsg(messageSource.getMessage("lts.acq.personalInfo.validationproblem", new Object[] {}, this.locale));
				orderCaptureDTO.setLtsAcqPaymentMethodFormDTO(null);//reset payment 
			}
		}
		
		ltsPersonalInfoFormDTO.setWarningCustomerSpecialRemarkMessage("");
		if(cust != null && !orderCaptureDTO.isChannelDirectSales()){
			if(cust.isBlacklistCustInd()){
				ltsPersonalInfoFormDTO.setWarningCustomerSpecialRemarkMessage(messageSource.getMessage("lts.acq.personalInfo.customerBlacklisted", new Object[] {}, this.locale));
			}
			else if(cust.isSpecialHandle()){
				ltsPersonalInfoFormDTO.setWarningCustomerSpecialRemarkMessage(messageSource.getMessage("lts.acq.personalInfo.customerReqSpecialHandling", new Object[] {}, this.locale));
			}
			else if("R".equals(cust.getWipInd())){
				ltsPersonalInfoFormDTO.setWarningCustomerSpecialRemarkMessage(messageSource.getMessage("lts.acq.personalInfo.customerUnderWIP.", new Object[] {}, this.locale));
			}
		
			CustomerContactProfileLtsDTO[] contact = customerProfileLtsService.getCustContactInfo(cust.getCustNum(), LtsConstant.SYSTEM_ID_DRG);
			if(contact != null){
				for(CustomerContactProfileLtsDTO temp: contact){
					if(StringUtils.equals(temp.getMediaType(), LtsConstant.CONTACT_MEDIA_TYPE_EMAIL)){
						ltsPersonalInfoFormDTO.setContactEmail(temp.getMediaNum());
					}
					else if (StringUtils.equals(temp.getMediaType(), LtsConstant.CONTACT_MEDIA_TYPE_MOBILE)){
						ltsPersonalInfoFormDTO.setMobileNo(temp.getMediaNum());
					}
				}
			}
			
			boolean valid = checkDocId(cust.getDocType(), cust.getDocNum());
			ltsPersonalInfoFormDTO.setValidDocId(valid);
			
			if(!valid){
				ltsPersonalInfoFormDTO.setDummyDoc(true);
			}
			
			try {
				CustPdpoProfileDTO[] dataInfoList = custPdpoProfileService.getCustDataPrivacyInfo(cust.getCustNum(), LtsConstant.SYSTEM_ID_DRG);
				boolean showPdpoStatement = false;
				boolean containOptIndEmpty = false;
				String ltsStatus = "";
				String iddStatus = "";
				if(dataInfoList != null && dataInfoList.length > 0){
					for(CustPdpoProfileDTO dataInfo : dataInfoList){
						if(StringUtils.isBlank(dataInfo.getOptInd())){
							containOptIndEmpty = true;
						}
						
						if(StringUtils.isNotBlank(dataInfo.getOptInd()) 
								&& LtsConstant.DATA_PRIVACY_OPT_IND_OOA_CD.equals(dataInfo.getOptInd())){
							showPdpoStatement = true;
						}
						
						if(LtsConstant.DATA_PRIVACY_BOM_LOB_LTS.equals(dataInfo.getLob())){
							ltsStatus = LtsConstant.DATA_PRIVACY_STATUS_MAP.get(dataInfo.getOptInd());
						}
						
						if(LtsConstant.DATA_PRIVACY_BOM_LOB_IDD.equals(dataInfo.getLob())){
							iddStatus = LtsConstant.DATA_PRIVACY_STATUS_MAP.get(dataInfo.getOptInd());
						}
					}
					
					if(containOptIndEmpty){		// empty field of any record requires to display pdpo statement
						showPdpoStatement = true;
					}						
				}
			
				ltsPersonalInfoFormDTO.setDisplayPICS(showPdpoStatement);
				ltsPersonalInfoFormDTO.setLtsStatus(ltsStatus);
				ltsPersonalInfoFormDTO.setIddStatus(iddStatus);
			} catch (AppRuntimeException e) {
				logger.warn("Exception - getCustDataPrivacyInfo ", e);
			}
		}
		
		ltsPersonalInfoFormDTO.setMustVerify(orderCaptureDTO.isChannelRetail()||orderCaptureDTO.isChannelDirectSales());
		ltsPersonalInfoFormDTO.setDefinedCustNum(orderCaptureDTO.isDefinedCustNum());
		ltsPersonalInfoFormDTO.setChannelDs(orderCaptureDTO.isChannelDirectSales());

		if(ltsCommonService.isNowDrgDownTime()){
			request.setAttribute("isNowDrgDownTime", true);
		}
		ltsPersonalInfoFormDTO.setBasketType(orderCaptureDTO.isEyeOrder()? "EYE" : "DEL");
		if(orderCaptureDTO.isChannelDirectSales() || orderCaptureDTO.isChannelRetail()){
			ltsPersonalInfoFormDTO.setAllowThirdParty(false);
		}else{
			ltsPersonalInfoFormDTO.setAllowThirdParty(true);
		}
		return ltsPersonalInfoFormDTO;
	}
	
	@SuppressWarnings("unchecked")
	private void setupEligibleDocType(LtsAcqPersonalInfoFormDTO form, AcqOrderCaptureDTO order){

		form.setDocTypeNotMatchMsg("");
		List<ItemDetailDTO> itemDetailList = new ArrayList<ItemDetailDTO>();
		itemDetailList = ltsCommonService.getAcqBasketItem(order);
		
		List<String> finalDocTypeList = new ArrayList<String>();
		boolean hasEligibleDocType = false;
		for(ItemDetailDTO itemDtl : itemDetailList){
			if(StringUtils.isNotBlank(itemDtl.getEligibleDocType())){
				List<String> eligibleDocTypeList = new ArrayList<String>();
				for(String docType: StringUtils.split(itemDtl.getEligibleDocType(), "||")){
					eligibleDocTypeList.add(StringUtils.trim(docType));
				}
				if(!hasEligibleDocType){
					finalDocTypeList.addAll(eligibleDocTypeList);
				}
				hasEligibleDocType = true;
				finalDocTypeList = ListUtils.intersection(finalDocTypeList, eligibleDocTypeList);
			}
		}
		
		if(!finalDocTypeList.isEmpty()){
			form.setEligibleDocType(StringUtils.join(finalDocTypeList, " / "));
		}
		
		if(hasEligibleDocType && finalDocTypeList.isEmpty()){
			form.setDocTypeNotMatchMsg(messageSource.getMessage("lts.acq.personalInfo.multiEligibleDocType", new Object[] {}, this.locale));
		}
	}

	public ModelAndView onSubmit(HttpServletRequest request,HttpServletResponse response, Object command, BindException errors)throws ServletException {
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqPersonalInfoFormDTO ltsPersonalInfoFormDTO = (LtsAcqPersonalInfoFormDTO) command;
		LtsAddressRolloutFormDTO addressRolloutDTO = orderCaptureDTO.getLtsAddressRolloutForm();
		CustomerDetailProfileLtsDTO custDetailDTO = orderCaptureDTO.getCustomerDetailProfileLtsDTO();
		LtsAcqSalesInfoFormDTO salesInfo = orderCaptureDTO.getLtsAcqSalesInfoForm();
		BomCustomerVerificationDTO custVerifi = new BomCustomerVerificationDTO();
				
		if(ltsPersonalInfoFormDTO != null){
			if("out".equals(ltsPersonalInfoFormDTO.getOptInOutInd())){
				ltsPersonalInfoFormDTO.setAgreement(true);
			}
			else{
				ltsPersonalInfoFormDTO.setAgreement(false);
			}
			if(Boolean.TRUE.equals(ltsPersonalInfoFormDTO.getThirdPartyInd())){
				ltsPersonalInfoFormDTO.setThirdParty(true);
			}else{
				ltsPersonalInfoFormDTO.setThirdParty(false);
			}
			
			logger.info("Confirm LTS Acq Doc Num: "+ltsPersonalInfoFormDTO.getDocNum()+" by Sales "+ salesInfo.getStaffId());
			//System.out.print("Confirm LTS Acq Doc Num: "+ltsPersonalInfoFormDTO.getDocNum()+" by Sales "+ salesInfo.getStaffId());
			
		}		
		if (LtsConstant.DOC_TYPE_HKBR.equals(ltsPersonalInfoFormDTO.getDocumentType())) {
			ltsPersonalInfoFormDTO.setThirdParty(true);
		}
		
		if(orderCaptureDTO.isChannelDirectSales()){
			LtsAcqAccountSelectionFormDTO ltsAcqAccountSelectionForm = new LtsAcqAccountSelectionFormDTO();
			ltsAcqAccountSelectionForm.setNewAccountSelected(true);
			
			if(StringUtils.isNotBlank(ltsPersonalInfoFormDTO.getOldCustNum()) 
					&& StringUtils.equals(ltsPersonalInfoFormDTO.getOldCustNum(), ltsPersonalInfoFormDTO.getCustNum())
					&& orderCaptureDTO.getAccountDetailProfileLtsDTO()!=null){
				ltsAcqAccountSelectionForm.setNewAccount(false);
			}else{
				ltsAcqAccountSelectionForm.setNewAccount(true);
				AccountDetailProfileLtsDTO[] newAcctArray = {new AccountDetailProfileLtsDTO()};
				orderCaptureDTO.setAccountDetailProfileLtsDTO(newAcctArray);
				orderCaptureDTO.setLtsAcqBillMediaBillLangForm(null); // reset form if custnum change
				orderCaptureDTO.setLtsAcqPaymentMethodFormDTO(null);
				orderCaptureDTO.setLtsAcqBillingAddressForm(null);
			}
					
			orderCaptureDTO.setLtsAcqAccountSelectionForm(ltsAcqAccountSelectionForm);
			if(orderCaptureDTO.isEyeOrder()){
				custVerifi = customerInformationService.getBomCustomerVerificationResult(ltsPersonalInfoFormDTO.getDocumentType(), ltsPersonalInfoFormDTO.getDocNum(), ltsPersonalInfoFormDTO.getGivenName(), ltsPersonalInfoFormDTO.getFamilyName());
			}else{
				custVerifi = customerInformationService.getLtsCustomerVerificationResult(ltsPersonalInfoFormDTO.getDocumentType(), ltsPersonalInfoFormDTO.getDocNum(), ltsPersonalInfoFormDTO.getGivenName(), ltsPersonalInfoFormDTO.getFamilyName());
			}
			if(custVerifi!=null){
				ltsPersonalInfoFormDTO.setMatchWithBomName(custVerifi.isMatchWithBomName());
			}
			if(custVerifi.getLtsCustNum() != null && custVerifi.isContainLtsProfile()){
				if(custDetailDTO==null){
					custDetailDTO = new CustomerDetailProfileLtsDTO();
				}
				custDetailDTO = customerProfileLtsService.getCustByDoc(ltsPersonalInfoFormDTO.getDocumentType(), ltsPersonalInfoFormDTO.getDocNum(), LtsConstant.SYSTEM_ID_DRG);
				custDetailDTO.setFirstName(ltsPersonalInfoFormDTO.getGivenName());
				custDetailDTO.setLastName(ltsPersonalInfoFormDTO.getFamilyName());
				custDetailDTO.setDocNum(ltsPersonalInfoFormDTO.getDocNum());
				custDetailDTO.setDocType(ltsPersonalInfoFormDTO.getDocumentType());
				custDetailDTO.setTitle(ltsPersonalInfoFormDTO.getTitle());
				
				ltsPersonalInfoFormDTO.setCreateNewCust(false);
				
				orderCaptureDTO.setCustomerDetailProfileLtsDTO(custDetailDTO);
			}
		}
		
		if ((custDetailDTO == null || !custVerifi.isContainLtsProfile()) && ltsPersonalInfoFormDTO.isCreateNewCust()) {
			try {
				CustomerDTO custDTO = new CustomerDTO();
				if(ltsCommonService.isNowDrgDownTime()){
					custDTO = getNewDummyCustomer(ltsPersonalInfoFormDTO, addressRolloutDTO, salesInfo.getStaffId());
				}else{
					//Spring web service
					logger.warn("LtsAcqPersonalInfoController before create Cust, userid " + salesInfo.getStaffId());
					custDTO = bomWsCreateCustClient.ltsAcqCreateNewCust(ltsPersonalInfoFormDTO, addressRolloutDTO, salesInfo.getStaffId());
					logger.warn("LtsAcqPersonalInfoController after create Cust, userid " + salesInfo.getStaffId());
				}

				/* if create success */
				if(custDTO != null && !LtsAcqSbOrderHelper.isDummyCustomer(custDTO.getCustNum())){
					logger.warn("LtsAcqPersonalInfoController after create Cust, custnum " + custDTO.getCustNum());
					custDetailDTO = customerProfileLtsService.getCustByDoc(custDTO.getIdDocType(), custDTO.getIdDocNum(), LtsConstant.SYSTEM_ID_DRG);
					orderCaptureDTO.setCustomerDetailProfileLtsDTO(custDetailDTO);
				}else{
					if(custDetailDTO==null){
						custDetailDTO = new CustomerDetailProfileLtsDTO();
					}
					custDetailDTO.setCustNum(custDTO.getCustNum());
					custDetailDTO.setFirstName(ltsPersonalInfoFormDTO.getGivenName());
					custDetailDTO.setLastName(ltsPersonalInfoFormDTO.getFamilyName());
					custDetailDTO.setDocNum(ltsPersonalInfoFormDTO.getDocNum());
					custDetailDTO.setDocType(ltsPersonalInfoFormDTO.getDocumentType());
					custDetailDTO.setTitle(ltsPersonalInfoFormDTO.getTitle());
					orderCaptureDTO.setCustomerDetailProfileLtsDTO(custDetailDTO);
				}
			} catch (Exception e) {
				logger.warn("Exception in call remote service", e);
				ModelAndView mav = new ModelAndView(viewName);
				List<String> messageList = new ArrayList<String>();
				messageList.add(messageSource.getMessage("lts.acq.personalInfo.failToCreateaNewCustomer", new Object[] {}, this.locale));
				mav.addObject("errorMsgList", messageList);
				mav.addObject(commandName, ltsPersonalInfoFormDTO);
				return mav;
			}
						
		}
		
		orderCaptureDTO.getCustomerDetailProfileLtsDTO().setIdVerifyInd(ltsPersonalInfoFormDTO.getVerified());		
		orderCaptureDTO.setLtsAcqPersonalInfoForm(ltsPersonalInfoFormDTO);

		boolean isPcdBundleBasket = false;
		boolean isPcdBundlePremium = false;
		String pcdSbid = null;
		if(orderCaptureDTO.getLtsAcqBasketSelectionForm() != null && StringUtils.isNotBlank(orderCaptureDTO.getLtsAcqBasketSelectionForm().getPcdSbid()))
		{
			isPcdBundleBasket = true;
			pcdSbid = orderCaptureDTO.getLtsAcqBasketSelectionForm().getPcdSbid();
		}		
		if(orderCaptureDTO.getLtsPremiumSelectionForm() != null && StringUtils.isNotBlank(orderCaptureDTO.getLtsPremiumSelectionForm().getPcdSbid()))
		{
			List<ItemSetDetailDTO> premiumSetList = null;
			if(orderCaptureDTO.getLtsPremiumSelectionForm().getPremiumSetList()!=null)


			{
				premiumSetList = orderCaptureDTO.getLtsPremiumSelectionForm().getPremiumSetList();
			}
			
			ItemDetailDTO[] itemDetails = null;
			int selectedPcdItemCount = 0;
			for (int i=0; premiumSetList != null && i < premiumSetList.size(); i++  ) {
				int selectedItemCount = 0;
				
				if(premiumSetList.get(i).getItemSetType().equalsIgnoreCase("PREM-PCD"))
				{				
					itemDetails = premiumSetList.get(i).getItemDetails();
					if (!ArrayUtils.isEmpty(itemDetails)) {
						for (ItemDetailDTO itemDetail : itemDetails) {
							if (itemDetail.isSelected()) {
								selectedItemCount++;
							}
						}
					}
				}


				
				selectedPcdItemCount += selectedItemCount;
			}
			
			if(selectedPcdItemCount>0)
			{
				isPcdBundlePremium = true;
				pcdSbid = orderCaptureDTO.getLtsPremiumSelectionForm().getPcdSbid();
			}
		}
		
		if(isPcdBundleBasket || isPcdBundlePremium)
		{
			ImsSbOrderDTO imsSbOrder = imsSbOrderService.getPcdSbOrder(pcdSbid);
			if (!(StringUtils.equalsIgnoreCase(imsSbOrder.getIdDocType(), ltsPersonalInfoFormDTO.getDocumentType()) 
				&& StringUtils.equalsIgnoreCase(imsSbOrder.getIdDocNum(), ltsPersonalInfoFormDTO.getDocNum()))) {
				
				ModelAndView mav = new ModelAndView(viewName);
				List<String> messageList = new ArrayList<String>();
				messageList.add(messageSource.getMessage("lts.acq.personalInfo.documentTypeOrNumberDiffFromPCD", new Object[] {pcdSbid}, this.locale));
				mav.addObject("errorMsgList", messageList);
				mav.addObject(commandName, ltsPersonalInfoFormDTO);
				return mav;
			}			
		}
		
		if(orderCaptureDTO.isChannelDirectSales()){
			logger.warn("LtsAcqPersonalInfoController custnum: " + orderCaptureDTO.getLtsAcqPersonalInfoForm().getCustNum() + " userid " + orderCaptureDTO.getLtsAcqSalesInfoForm().getStaffId());
			if(orderCaptureDTO.getAccountDetailProfileLtsDTO()!=null){
				for(AccountDetailProfileLtsDTO acctDtl : orderCaptureDTO.getAccountDetailProfileLtsDTO()){
				    logger.warn("LtsAcqPersonalInfoController acctnum: " + acctDtl.getAcctNum());
				}
			}else{
				logger.warn("LtsAcqPersonalInfoController AccountDetailProfileLtsDTO is null ");
			}
			//orderCaptureDTO.setLtsModemArrangementForm(null); //Remove modem form for DS as 3rd party change will affect modem selection
			//return new ModelAndView(new RedirectView(orderCaptureDTO.isEyeOrder()?nextView:nextView3));
			
			//System.out.println("DS personal nextView:"+(orderCaptureDTO.isEyeOrder()?nextView:nextView2));
			
			return new ModelAndView(new RedirectView(orderCaptureDTO.isEyeOrder()?nextView:nextView2));
		}
		else{
			logger.warn("LtsAcqPersonalInfoController custnum: " + orderCaptureDTO.getLtsAcqPersonalInfoForm().getCustNum() + " userid " + orderCaptureDTO.getLtsAcqSalesInfoForm().getStaffId());
			if(orderCaptureDTO.getAccountDetailProfileLtsDTO()!=null){
				for(AccountDetailProfileLtsDTO acctDtl : orderCaptureDTO.getAccountDetailProfileLtsDTO()){
				    logger.warn("LtsAcqPersonalInfoController acctnum: " + acctDtl.getAcctNum());
				}
			}else{
				logger.warn("LtsAcqPersonalInfoController AccountDetailProfileLtsDTO is null ");
			}
			return new ModelAndView(new RedirectView(orderCaptureDTO.isEyeOrder()?nextView:nextView2));
		}
	}

	protected Map<String, List<LookupItemDTO>> referenceData(HttpServletRequest request) throws Exception {
		Map<String, List<LookupItemDTO>> referenceData = new HashMap<String, List<LookupItemDTO>>();
		referenceData.put("relationshipCodeList", Arrays.asList(relationshipCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("relationshipBrCodeList", Arrays.asList(relationshipBrCodeLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("titleList", Arrays.asList(newCustTitleAcqLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		referenceData.put("thirdPartyTitleList", Arrays.asList(newCustTitleAcqLkupCacheService.getCodeLkupDAO().getCodeLkup()));
		
		return referenceData;
	}
	
	private boolean checkDocId(String docType, String docNum){
		if((StringUtils.contains(docNum, '#')
				|| StringUtils.containsIgnoreCase(docNum, "Dum"))){
			return false;
		}else if(StringUtils.equals(docType, LtsConstant.DOC_TYPE_HKID)
				&& !Utility.validateHKIDcheckDigit(docNum) 
				&& !Utility.validateHKID(docNum)){
			return false;
		}else if(StringUtils.equals(docType, LtsConstant.DOC_TYPE_PASSPORT)
				&& Utility.validateHKIDcheckDigit(docNum) 
				&& Utility.validateHKID(docNum)){
			return false;
		}else if(StringUtils.equals(docType, LtsConstant.DOC_TYPE_HKBR)
				&& !Utility.validateHKBRcheckDigit(docNum) 
				&& !Utility.validateHKBR(docNum)){
			return false;
		}
		
		return true;
	}
	
    public CustomerDTO getNewDummyCustomer(LtsAcqPersonalInfoFormDTO personalInfo,LtsAddressRolloutFormDTO addressRollout, String userId) {
    	    int MAXIMUN_LENGTH_OF_CONTACT_NAME = 40;
			CustomerDTO custDTO = new CustomerDTO();
				custDTO.setCustNum(ltsAcqCustomerDetailService.getBomDummyCustNum());
				custDTO.setSystemId(LtsConstant.SYSTEM_ID_DRG);			  
				custDTO.setCustCatg(LtsConstant.CUST_CATGORY_NORMAL);			  
				custDTO.setGrpIdDocNum(personalInfo.getDocNum());
				custDTO.setGrpIdDocType(personalInfo.getDocumentType());
				custDTO.setIdDocNum(personalInfo.getDocNum());
				custDTO.setIdDocType(personalInfo.getDocumentType());
				if(LtsConstant.DOC_TYPE_HKID.equals(personalInfo.getDocumentType())
						  || LtsConstant.DOC_TYPE_PASSPORT.equals(personalInfo.getDocumentType())) {
					custDTO.setCustType("P");
					custDTO.setTitle(personalInfo.getTitle());				  
					custDTO.setCustFirstName(personalInfo.getGivenName());
					custDTO.setCustLastName(personalInfo.getFamilyName());
				} else if (LtsConstant.DOC_TYPE_HKBR.equals(personalInfo.getDocumentType())) {
					custDTO.setCustType("B");
					custDTO.setCompanyName(personalInfo.getCompanyName());				 
				}
				custDTO.setIdVerifyInd(personalInfo.getVerified()? "Y" : "N");
				custDTO.setIndustryType(LtsConstant.INDUSTRY_TYPE_RESIDENTIAL);
				custDTO.setIndustrySubType(LtsConstant.INDUSTRY_SUBTYPE_RESIDENTIAL_BLANK);
				custDTO.setLastUpdBy(userId);  
			
			AddressMaintDTO address = new AddressMaintDTO();  
			   address.setAddrType("S");
			   address.setAddrUsage("MA");
			   address.setAreaCode(addressRollout.getAreaCode());
			   address.setAreaDesc(addressRollout.getAreaDesc());
			   address.setBuildNum(addressRollout.getBuildingName());
			   address.setDistrictNum(addressRollout.getDistrictCode());
			   address.setDistrict(addressRollout.getDistrictDesc());
			   address.setFloorNum(addressRollout.getFloor());
			   address.setForeignAddrFlag("N");
			   address.setStreetCatCode(addressRollout.getStreetCatgCode());
			   address.setStreetCatCodeDesc(addressRollout.getStreetCatgDesc());
			   address.setStreetName(addressRollout.getStreetName());
			   address.setStreetNum(addressRollout.getStreetNum());
			   address.setUnitNum(addressRollout.getFlat());
			   address.setLastUpdBy(userId);
			   address.setSysId(LtsConstant.SYSTEM_ID_DRG);
			   address.setModify(true);
			   custDTO.setAddressMaintDTO(address);
			  
			  
			  
			ContactInfoDTO contactInfoDTO = new ContactInfoDTO();			  
			  if (StringUtils.isNotBlank(personalInfo.getContactEmail()) 
					  || StringUtils.isNotBlank(personalInfo.getMobileNo())
					  || StringUtils.isNotBlank(personalInfo.getFixedLineNo())) {
				  contactInfoDTO.setActionInd("A");
				  if(LtsConstant.DOC_TYPE_HKID.equals(personalInfo.getDocumentType())
						  || LtsConstant.DOC_TYPE_PASSPORT.equals(personalInfo.getDocumentType())) {
					  String contactName = personalInfo.getFamilyName() + " " + personalInfo.getGivenName();
					  if (contactName.length() > MAXIMUN_LENGTH_OF_CONTACT_NAME) {
						  contactName = personalInfo.getFamilyName();
					  }
					  contactInfoDTO.setCntctName(contactName);
					  contactInfoDTO.setTitle(personalInfo.getTitle());
				  } else if (LtsConstant.DOC_TYPE_HKBR.equals(personalInfo.getDocumentType())) {
					  String contactName = personalInfo.getThirdPartyFamilyName() + " (" + personalInfo.getCompanyName() + ")";
					  if (contactName.length() > MAXIMUN_LENGTH_OF_CONTACT_NAME) {
						  contactName = personalInfo.getThirdPartyFamilyName();
					  }
					  contactInfoDTO.setCntctName(contactName);
					  contactInfoDTO.setTitle(personalInfo.getThirdPartyTitle());
				  }
				  contactInfoDTO.setEmail(personalInfo.getContactEmail());
				  contactInfoDTO.setMobile(personalInfo.getMobileNo());
				  contactInfoDTO.setDayPhone(personalInfo.getFixedLineNo());
				  contactInfoDTO.setNightPhone(personalInfo.getFixedLineNo());
				  contactInfoDTO.setPrimaryInd("Y");
			  }
			  custDTO.setPrimaryContactInfoDTO(contactInfoDTO);
			  
			  return custDTO;
	}
	
	public String getWsUrl() {
		return wsUrl;
	}

	public void setWsUrl(String wsUrl) {
		this.wsUrl = wsUrl;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BomCustProfileWsClient getBomWsCreateCustClient() {
		return bomWsCreateCustClient;
	}

	public void setBomWsCreateCustClient(BomCustProfileWsClient bomWsCreateCustClient) {
		this.bomWsCreateCustClient = bomWsCreateCustClient;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
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

	public CodeLkupCacheService getNewCustTitleAcqLkupCacheService() {
		return newCustTitleAcqLkupCacheService;
	}

	public void setNewCustTitleAcqLkupCacheService(
			CodeLkupCacheService newCustTitleAcqLkupCacheService) {
		this.newCustTitleAcqLkupCacheService = newCustTitleAcqLkupCacheService;
	}

	public CodeLkupCacheService getTitleAcqLkupCacheService() {
		return titleAcqLkupCacheService;
	}

	public void setTitleAcqLkupCacheService(
			CodeLkupCacheService titleAcqLkupCacheService) {
		this.titleAcqLkupCacheService = titleAcqLkupCacheService;
	}

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public CustomerInformationService getCustomerInformationService() {
		return customerInformationService;
	}

	public void setCustomerInformationService(
			CustomerInformationService customerInformationService) {
		this.customerInformationService = customerInformationService;
	}

	public LtsAcqCustomerDetailService getLtsAcqCustomerDetailService() {
		return ltsAcqCustomerDetailService;
	}

	public void setLtsAcqCustomerDetailService(
			LtsAcqCustomerDetailService ltsAcqCustomerDetailService) {
		this.ltsAcqCustomerDetailService = ltsAcqCustomerDetailService;
	}
	
	public CustPdpoProfileService getCustPdpoProfileService() {
		return custPdpoProfileService;
	}

	public void setCustPdpoProfileService(
			CustPdpoProfileService custPdpoProfileService) {
		this.custPdpoProfileService = custPdpoProfileService;
	}

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
}
