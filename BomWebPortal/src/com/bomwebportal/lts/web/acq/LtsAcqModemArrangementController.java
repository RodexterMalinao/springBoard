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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.LineTypeSelectionDTO;
import com.bomwebportal.lts.dto.ModemAssignDTO;
import com.bomwebportal.lts.dto.ModemTechnologyAissgnDTO;
import com.bomwebportal.lts.dto.UpgradePcdSrvDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.TechnologyDTO;
import com.bomwebportal.lts.service.LtsAddressRolloutService;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsRetrieveFsaService;
import com.bomwebportal.lts.service.ModemAssignService;
import com.bomwebportal.lts.service.UpgradePcdSrvService;
import com.bomwebportal.lts.service.acq.LtsAcqRelatedPcdOrderService;
import com.bomwebportal.lts.service.acq.LtsAcqRetrieveFsaService;
import com.bomwebportal.lts.service.bom.AddressDetailLtsService;
import com.bomwebportal.lts.service.bom.ImsServiceProfileAccessService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsAcqModemArrangementController extends SimpleFormController {

	private final String viewName = "lts/acq/ltsacqmodemarrangement";
	private final String nextView = "ltsacqaccountselection.html"; //"ltsacqappointment.html";
	private final String nextView2 = "ltsacqbillingaddress.html"; //new
	private final String commandName = "ltsAcqModemArrangementCmd";

	protected LtsAcqRetrieveFsaService ltsAcqRetrieveFsaService;
	protected LtsAcqRelatedPcdOrderService ltsAcqRelatedPcdOrderService;
	protected LtsRetrieveFsaService ltsRetrieveFsaService;
	protected UpgradePcdSrvService upgradePcdSrvService;
	protected LtsAddressRolloutService ltsAddressRolloutService;
	protected ImsServiceProfileAccessService imsServiceProfileAccessService;
	protected LtsBasketService ltsBasketService;
	protected AddressDetailLtsService addressDetailLtsService;
	protected ModemAssignService modemAssignService;
	protected LtsOfferService ltsOfferService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsAcqModemArrangementController(){
		this.setFormView(viewName);
		this.setCommandName(commandName);
		this.setCommandClass(LtsModemArrangementFormDTO.class);
	}
	
	private boolean isPcdBundleBasket;
	private boolean isPcdBundlePremium;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		if (order == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}else{
			String channelId = order.getBasketChannelId();
			if(StringUtils.equals(channelId, LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_MASS_DEL)
					|| StringUtils.equals(channelId, LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_PT_DEL)){
				return new ModelAndView(new RedirectView(nextView));
			}
		}
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		
		LtsModemArrangementFormDTO form = orderCapture.getLtsModemArrangementForm();
		
		if (form == null) {
			form = new LtsModemArrangementFormDTO();
			orderCapture.setLtsModemArrangementForm(form);
			String docType = getDocType(orderCapture);
			String docNum = getDocNum(orderCapture);
			
			ValidationResultDTO result = ltsAcqRetrieveFsaService.retrieveFsaServiceByDocument(docType, 
					docNum, orderCapture, orderCapture.getLtsAcqSalesInfoForm().getStaffId());
			
			@SuppressWarnings("unchecked")
			List<FsaDetailDTO> existingFsaList = (List<FsaDetailDTO>) result
					.getValidateObject();
			
			List<ModemType> modemTypeSelectionList = new ArrayList<LtsModemArrangementFormDTO.ModemType>();
			
			if(LtsSessionHelper.isChannelDirectSales(request)){
				existingFsaList = filterFsaBySbBuildXy(existingFsaList, orderCapture.getAddressRollout().getSrvBdary());
				
				if(orderCapture.getLtsAcqPersonalInfoForm().isThirdParty()){
					modemTypeSelectionList.add(ModemType.SHARE_EX_FSA);
					form.setModemType(ModemType.SHARE_EX_FSA);
					form.setModemTypeSelectionList(modemTypeSelectionList);
					form.setExistingFsaList(existingFsaList);
					
					if (existingFsaList == null || existingFsaList.isEmpty()) {
						String errorMsg = messageSource.getMessage("lts.acq.modemArrangement.notFindExistingFSAtoShare", new Object[] {}, this.locale);
						form.setErrorMsgList(Arrays.asList(new String[]{errorMsg}));
						request.setAttribute("notAllowContinue", true);
					}
					
					return form;
				}
			}
			
			if (existingFsaList == null || existingFsaList.isEmpty()) {
				modemTypeSelectionList.add(ModemType.STANDALONE);
				form.setModemType(ModemType.STANDALONE);

			} else {
				
				if (isAllFsaInSameIaNotAllowToShare(existingFsaList)) {
					modemTypeSelectionList.add(ModemType.STANDALONE);
				}
//				else {
//					preSelectFsa(existingFsaList, orderCapture);
//				}
				
				modemTypeSelectionList.add(ModemType.SHARE_EX_FSA);
				form.setModemType(ModemType.SHARE_EX_FSA);
				form.setExistingFsaList(existingFsaList);

				if (!result.getMessageList().isEmpty()) {
					form.setErrorMsgList(result.getMessageList());
				}
			}

			form.setModemTypeSelectionList(modemTypeSelectionList);
			setTvTypeList(orderCapture, form);
			setLineTypeSelectionList(orderCapture, form);
			
			BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
			orderCapture.getSelectedBasket().getBasketChannelId();
			List<LookupItemDTO> projectCd = ltsBasketService.getProjectCdList(orderCapture.getSelectedBasket().getBasketChannelId(), bomSalesUser.getShopCd(), "ACQ");
			boolean containsFee3Mth = false;
			
			for(LookupItemDTO item : projectCd){
				if(LtsConstant.BASKET_PROJECT_CD_EYE3_FREE_3MTH.equals(item.getItemKey())){
					containsFee3Mth = true;
				}
			}

			modemTypeSelectionList.add(ModemType.SHARE_PCD);
			if(containsFee3Mth){
				form.setModemType(ModemType.SHARE_PCD);
				form.setFilterModemType(ModemType.SHARE_PCD.name());
			}
			
			// TODO: Call IMS API to determine other fsa exist under same IA
			form.setOtherFsaExistInSameIa(isOtherFsaExistInSameIa(orderCapture));
			if(!orderCapture.isChannelDirectSales()){
				modemTypeSelectionList.add(ModemType.SHARE_OTHER_FSA);	
			}			
			
			modemTypeSelectionList.add(ModemType.SHARE_TV);
			modemTypeSelectionList.add(ModemType.SHARE_BUNDLE);
			
			isPcdBundleBasket = false;
			isPcdBundlePremium = false;
			
			if(orderCapture.getLtsAcqBasketSelectionForm() != null && StringUtils.isNotBlank(orderCapture.getLtsAcqBasketSelectionForm().getPcdSbid()))
			{
				isPcdBundleBasket = true;
			}
			
			if(orderCapture.getLtsPremiumSelectionForm() != null && StringUtils.isNotBlank(orderCapture.getLtsPremiumSelectionForm().getPcdSbid()))
			{
				List<ItemSetDetailDTO> premiumSetList = null;
				if(orderCapture.getLtsPremiumSelectionForm().getPremiumSetList()!=null)
				{
					premiumSetList = orderCapture.getLtsPremiumSelectionForm().getPremiumSetList();
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
				}
			}
			
			if(isPcdBundleBasket || isPcdBundlePremium)
			{
				modemTypeSelectionList = new ArrayList<LtsModemArrangementFormDTO.ModemType>();
				modemTypeSelectionList.add(ModemType.SHARE_PCD);
				form.setModemTypeSelectionList(modemTypeSelectionList);

				form.setModemType(ModemType.SHARE_PCD);
				form.setFilterModemType(ModemType.SHARE_PCD.name());
								
				if(isPcdBundleBasket)
				{
					form.setInputPcdSbOrderId(orderCapture.getLtsAcqBasketSelectionForm().getPcdSbid());
				}
				else
				{
					form.setInputPcdSbOrderId(orderCapture.getLtsPremiumSelectionForm().getPcdSbid());
				}
				form.setPcdSbOrderExist(true);
			}
		}
		return form;
	}
	
	private void setLineTypeSelectionList(AcqOrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form) {
		AddressRolloutDTO addressRollout = orderCapture.getAddressRollout();
		
		if (!form.getModemTypeSelectionList().contains(ModemType.STANDALONE) || StringUtils.isNotEmpty(addressRollout.getHktPremier())) {
			return;
		}

		List<LineTypeSelectionDTO> lineTypeSelectionList = new ArrayList<LineTypeSelectionDTO>();
		boolean allowSelectLineType = ArrayUtils.isNotEmpty(addressRollout.getUimBlockage());
		
		if (allowSelectLineType) {
			for (TechnologyDTO techonology : addressRollout.getTechnology()) {
				LineTypeSelectionDTO lineTypeSelection = new LineTypeSelectionDTO();
				try {
					BeanUtils.copyProperties(lineTypeSelection, techonology);	
				}
				catch (Exception e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
					throw new AppRuntimeException(e);
				}

				if (StringUtils.equals("Y", techonology.getIsResrcShort())) {
					lineTypeSelection.setStatus("Resource Shortage");
					lineTypeSelectionList.add(lineTypeSelection);
				}
				else if (LtsConstant.TECHNOLOGY_PON.equals(techonology.getTechnology())) {
					lineTypeSelection.setStatus(addressRollout.getUimBlockage()[0].getBlockageMessage());
					lineTypeSelectionList.add(lineTypeSelection);
				}
				else {
					allowSelectLineType = false;
					break;
				}
			}					
		}
		
		if (allowSelectLineType) {
			form.setLineTypeSelectionList(lineTypeSelectionList);
		}
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		AcqOrderCaptureDTO orderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		
		if(isPcdBundleBasket || isPcdBundlePremium)
		{
			referenceData.put("isPcdBundle", "Yes");
		}
		
		return referenceData; //super.referenceData(request);
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		LtsModemArrangementFormDTO form = (LtsModemArrangementFormDTO) command;

		AcqOrderCaptureDTO orderCapture = LtsSessionHelper.getAcqOrderCapture(request);

		if (orderCapture == null || orderCapture.getLtsAddressRolloutForm() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
				
		switch (form.getFormAction()) {
		case SUBMIT :
			validate(orderCapture, form, errors);
			
			if (errors.hasFieldErrors()) {
				ModelAndView mav = new ModelAndView(viewName);
				mav.addAllObjects(errors.getModel());
				mav.addObject(commandName, form);
				return mav;
			}
			
			FsaDetailDTO selectedFsa = getSelectedFsaDetail(form);
			
			if (selectedFsa != null) {
				FsaServiceDetailOutputDTO fsaProfile = selectedFsa.getFsaProfile();
				imsServiceProfileAccessService.getFsaOfferProfile(selectedFsa.getFsaProfile(), "");
//				imsServiceProfileAccessService.getFsaOfferProfile(selectedFsa.getFsaProfile(), 
//						orderCapture.getLtsServiceProfile().getExistEyeType());
				selectedFsa.setFsaProfile(fsaProfile);
				selectedFsa.setExistMirrorInd(fsaProfile.getExistMirrorInd());
			}
			
			ModemTechnologyAissgnDTO assignedModemTechnology = assignModemTechnology(orderCapture, form);
			
			orderCapture.setModemTechnologyAssign(assignedModemTechnology);
			orderCapture.setLtsModemArrangementForm(form);
//			orderCapture.setLtsDeviceSelectionForm(null);
			resetProfileFsa(form);
			addRentalRouterItem(orderCapture, form, locale.toString());
			break;
		case SEARCH_OTHER_FSA :
			return handleSearchOtherFsa(form, orderCapture, request);
		case SEARCH_PCD_ORDER :
			return handleSearchPcdOrder(form, orderCapture);
		case CLEAR_OTHER_FSA :
			return handleClearOtherFsa(form, orderCapture);
		case CLEAR_PCD_ORDER :
			return handleClearPcdOrder(form, orderCapture);
		case HOME:
			LtsSessionHelper.clearAllSessionAttb(request);
			if(LtsSessionHelper.isChannelDirectSales(request)){
				return new ModelAndView(new RedirectView("welcome.html"));
			}else{
				return new ModelAndView(new RedirectView("ltscustomerinquiry.html"));
			}
		default:
			break;
		}
		
		//return new ModelAndView(new RedirectView(orderCapture.isChannelDirectSales()?nextView2:nextView));
		return new ModelAndView(new RedirectView(nextView));
	}
	
	
	private void addRentalRouterItem(AcqOrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form, String locale) {
		if ( LtsConstant.ROUTER_OPTION_SHARE_RENTAL_ROUTER.equals(form.getRentalRouterInd())) {
			form.setRentalRouterItemList(ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_RENTAL_ROUTER, locale, true, orderCapture.getBasketChannelId(), null)) ;
		}
	}
	
	
	private void validate(AcqOrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form, BindException errors) {
		
		/*Validation for specific Channels*/
		if(orderCapture.isChannelPremier()){
			switch (form.getModemType()) {
			case STANDALONE:
				TechnologyDTO[] technology = orderCapture.getAddressRollout().getTechnology();
				if(technology != null 
						&& technology.length == 1 
						&& LtsConstant.TECHNOLOGY_PON.equals(technology[0].getTechnology())){
					errors.rejectValue("modemType", "lts.pon.only.addr.modem");
				}
			}
		}
		
		if ((orderCapture.isChannelCs() || orderCapture.isChannelPremier())) {
			switch (form.getModemType()) {
			case SHARE_TV:
				if (!form.isEdfRefExist()) {
					break;
				}
				if (StringUtils.isBlank(form.getEdfRefNum()) || StringUtils.length(form.getEdfRefNum()) < 16
						|| StringUtils.length(form.getEdfRefNum()) > 20) {
					errors.rejectValue("edfRefNum", "lts.pcdEdfRef.required");
				}
				break;
			case SHARE_PCD:
			case SHARE_BUNDLE:
				if (!form.isPcdSbOrderExist() && !form.isEdfRefExist()) {
					errors.rejectValue("pcdSbOrderExist", "lts.option.required");
				}
				if (form.isPcdSbOrderExist()) {
					break ;
				}
				if (!form.isEdfRefExist()) {
					break;
				}
				if (StringUtils.isNotBlank(form.getEdfRefNum()) 
						&& ( StringUtils.length(form.getEdfRefNum()) < 16 
								|| StringUtils.length(form.getEdfRefNum()) > 20)) {
					errors.rejectValue("edfRefNum", "lts.pcdEdfRef.required");
				}
				break;
			case SHARE_EX_FSA:
			case SHARE_OTHER_FSA:
				
				FsaDetailDTO selectedFsa = getSelectedFsaDetail(form);
				if (form.isEdfRefExist()) {
					if ((selectedFsa.getExistingService() != selectedFsa.getNewService() && selectedFsa.getNewService() != null)
							|| StringUtils.isNotBlank(selectedFsa.getUpgradeBandwidth())) {
						if (StringUtils.isBlank(form.getEdfRefNum()) || StringUtils.length(form.getEdfRefNum()) < 16
								|| StringUtils.length(form.getEdfRefNum()) > 20) {
							errors.rejectValue("edfRefNum", "lts.pcdEdfRef.required");
						}
					}
				}
//				 if (form.isExistingFsaER() || form.isOtherFsaER()) {
//					 if (StringUtils.isBlank(form.getEdfRefNum()) || StringUtils.length(form.getEdfRefNum()) < 16
//								|| StringUtils.length(form.getEdfRefNum()) > 20) {
//							errors.rejectValue("edfRefNum", "lts.pcdEdfRef.required");
//					} 
//				 }
				break;
			}
		}
		
		/*Common validation*/
		switch (form.getModemType()) {
		case SHARE_PCD:
		case SHARE_BUNDLE:
			if (form.isPcdSbOrderExist()) {
				if (orderCapture.getRelatedPcdOrder() == null) {
					errors.rejectValue("pcdSbOrderExist", "lts.pcdSbOrder.required");
					return;
				}
				if (orderCapture.getRelatedPcdOrder().isCustNotMatch()
						&& (!orderCapture.getRelatedPcdOrder().isAllowConfirmSameCust()
								|| (orderCapture.getRelatedPcdOrder().isAllowConfirmSameCust()
										&& !form.isConfirmSameCustWithPcdOrder()))) {
					errors.rejectValue("confirmSameCustWithPcdOrder", "lts.pcdSbOrder.notSameCust");
				}
				if (orderCapture.getRelatedPcdOrder().isIaNotMatch()
						&& (!orderCapture.getRelatedPcdOrder().isAllowConfirmSameIa()
								|| (orderCapture.getRelatedPcdOrder().isAllowConfirmSameIa()
										&& !form.isConfirmSameIaWithPcdOrder()))) {
					errors.rejectValue("confirmSameIaWithPcdOrder", "lts.pcdSbOrder.notSameIa");
				}
			}
			break;
		case SHARE_EX_FSA:
		case SHARE_OTHER_FSA:
			FsaDetailDTO selectedFsa = getSelectedFsaDetail(form);
			
//			ServiceDetailProfileLtsDTO serviceDetailProfile = orderCapture.getLtsServiceProfile();
//			if (StringUtils.isNotEmpty(serviceDetailProfile.getRelatedEyeFsa())) {
//				if (!StringUtils.equals(serviceDetailProfile.getRelatedEyeFsa(), String.valueOf(selectedFsa.getFsa()))) {
//					if (LtsConstant.MODEM_TYPE_2L2B.equals(selectedFsa.getModemArrange())) {
//						errors.rejectValue("existingFsaList[0].selected", "lts.fsa.cannot.share");
//					}
//				}
//			}
			
			if (StringUtils.isNotBlank(selectedFsa.getPendingOcid()) && LtsConstant.ORDER_TYPE_DISCONNECT.equals(selectedFsa.getPendingOrderType())
					&& !LtsConstant.MODEM_TYPE_2L2B.equals(selectedFsa.getModemArrange())) {
				errors.rejectValue("existingFsaList[0].selected", "lts.fsa.cannot.share.pendingOrder");
			}
			
			if(Arrays.asList(LtsConstant.ROUTER_RENTAL_ROUTER_GROUP_CODES).contains(selectedFsa.getRouterGrp())
					|| selectedFsa.isMeshRouterGrp()){
				if(StringUtils.isBlank(form.getRentalRouterInd())){
					errors.rejectValue("rentalRouterInd", "lts.modemArrangement.rentalRouter.error");
				}
			}
			
			break;
		case STANDALONE:
			if (form.getLineTypeSelectionList() != null && !form.getLineTypeSelectionList().isEmpty()) {
				String selectedLineType = getSelectedLineType(form);
				if (StringUtils.isBlank(selectedLineType)) {
					errors.rejectValue("lineTypeSelectionList[0].selected", "lts.lineType.required");
				}
			}
		default:
			break;
		}
		
	}
	
	private void resetProfileFsa(LtsModemArrangementFormDTO form) {
		
		List<FsaDetailDTO> existingFsaList = form.getExistingFsaList();
		if (existingFsaList != null && !existingFsaList.isEmpty()) {
			for (FsaDetailDTO fsaDetail : existingFsaList) {
				if (ModemType.SHARE_EX_FSA != form.getModemType()) {
					fsaDetail.setSelected(false);
				}
				if (!fsaDetail.isSelected()) {
					fsaDetail.setNewModemArrange(null);
					fsaDetail.setNewService(null);
					fsaDetail.setUpgradeBandwidth(null);
				}
			}
		}
		
		List<FsaDetailDTO> otherFsaList = form.getOtherFsaList();
		if (otherFsaList != null && !otherFsaList.isEmpty()) {
			for (FsaDetailDTO fsaDetail : otherFsaList) {
				if (ModemType.SHARE_OTHER_FSA != form.getModemType()) {
					fsaDetail.setSelected(false);
				}
				if (!fsaDetail.isSelected()) {
					fsaDetail.setNewModemArrange(null);
					fsaDetail.setNewService(null);
					fsaDetail.setUpgradeBandwidth(null);
				}
			}
		}
	}
	
	private FsaDetailDTO getSelectedFsaDetail(LtsModemArrangementFormDTO form) {
		
		List<FsaDetailDTO> fsaDetailList = null;
		
		switch (form.getModemType()) {
		case STANDALONE:
		case SHARE_PCD:
		case SHARE_TV:
		case SHARE_BUNDLE:
			return null;
		case SHARE_EX_FSA:
			fsaDetailList = form.getExistingFsaList();
			break;
		case SHARE_OTHER_FSA:
			fsaDetailList = form.getOtherFsaList();
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

	private ModemTechnologyAissgnDTO assignModemTechnology(AcqOrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form) {

		String existingService = null;
		String newService = null;
		
		FsaDetailDTO selectedFsaDetail = null;
			
		switch (form.getModemType()) {
		case STANDALONE:
		case SHARE_PCD:
			newService = FsaServiceType.PCD.getDesc();
			break;
		case SHARE_TV:
			newService = StringUtils.equals(LtsConstant.TV_TYPE_SDTV,
					form.getNowTvServiceType()) ? FsaServiceType.SDTV.getDesc()
					: FsaServiceType.HDTV.getDesc(); 
			break;
		case SHARE_BUNDLE:
			newService = StringUtils.equals(LtsConstant.TV_TYPE_SDTV,
					form.getNowTvServiceType()) ? FsaServiceType.PCD_SDTV
					.getDesc() : FsaServiceType.PCD_HDTV.getDesc();  
			break;
		case SHARE_EX_FSA:
		case SHARE_OTHER_FSA:
			selectedFsaDetail = getSelectedFsaDetail(form);

			// Assume PCD_EW == PCD in lookup table
			if (FsaServiceType.PCD_EW == selectedFsaDetail.getExistingService()) {
				selectedFsaDetail.setExistingService(FsaServiceType.PCD);
			}
			else if (FsaServiceType.PCD_EW_SDTV == selectedFsaDetail.getExistingService()) {
				selectedFsaDetail.setExistingService(FsaServiceType.PCD_SDTV);
			}
			if (FsaServiceType.PCD_EW_HDTV == selectedFsaDetail.getExistingService()) {
				selectedFsaDetail.setExistingService(FsaServiceType.PCD_HDTV);
			}
			
			existingService = selectedFsaDetail.getExistingService().getDesc();
			newService = selectedFsaDetail.getNewService() != null ? newService = selectedFsaDetail
					.getNewService().getDesc() : selectedFsaDetail
					.getExistingService().getDesc();
			
		default:
			break;
		}
		
		ModemAssignDTO modemAssign = modemAssignService.createModemAssign(
				orderCapture.getAddressRollout(),
				selectedFsaDetail != null ? selectedFsaDetail.getFsaProfile() : null, existingService, newService, null, false, getSelectedLineType(form));
		
		ModemTechnologyAissgnDTO returnObj = new ModemTechnologyAissgnDTO(); 
		try {
			BeanUtils.copyProperties(returnObj, modemAssign);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e);	
		}
		return returnObj;
	}
	
	private String getSelectedLineType(LtsModemArrangementFormDTO form) {
		if (form.getLineTypeSelectionList() == null || form.getLineTypeSelectionList().isEmpty()) {
			return null;
		}
		
		for (LineTypeSelectionDTO lineTypeSelection : form.getLineTypeSelectionList()) {
			if (lineTypeSelection.isSelected()) {
				return lineTypeSelection.getTechnology();
			}
		}
		return null;
	}
	
	private void setTvTypeList(AcqOrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form) {
		List<String> tvTypeList = new ArrayList<String>();
		tvTypeList.add(LtsConstant.TV_TYPE_SDTV);
		
		if (orderCapture.getAddressRollout() != null) {
			List<UpgradePcdSrvDTO> upgradePcdSrvList = upgradePcdSrvService
				.getUpgradePcdSrv(orderCapture.getAddressRollout().getMaximumBandwidth(), null, LtsConstant.TV_TYPE_HDTV);
			
			if (upgradePcdSrvList != null && !upgradePcdSrvList.isEmpty()) {
				tvTypeList.add(LtsConstant.TV_TYPE_HDTV);
			}
		}

		form.setTvTypeList(tvTypeList);
	}

	private ModelAndView handleSearchOtherFsa(LtsModemArrangementFormDTO form, AcqOrderCaptureDTO orderCapture, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject(commandName, form);
		
		if (StringUtils.equals(getDocType(orderCapture), form.getInputDocType())
				&& StringUtils.equals(getDocNum(orderCapture), form.getInputDocNum())) {
			mav.addObject("retrieveOtherFsaError", messageSource.getMessage("lts.acq.modemArrangement.inputDocNumUnderSameCustomer", new Object[] {}, this.locale));
			return mav;
		}
		
		ValidationResultDTO result = null;
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		
		// TODO: Call IMS API to get FSA Detail 
		if (StringUtils.isNotEmpty(form.getInputOtherFsa())) {
			result = ltsAcqRetrieveFsaService.retrieveFsaServiceByFsa(form.getInputOtherFsa() , orderCapture, bomSalesUser.getUsername());	
		} else if (StringUtils.isNotEmpty(form.getInputPcdLoginId()) && StringUtils.isNotEmpty(form.getInputPcdLoginDomain())) {
			result = ltsAcqRetrieveFsaService.retrieveFsaServiceByLogin(form.getInputPcdLoginId(), form.getInputPcdLoginDomain(), orderCapture, bomSalesUser.getUsername());
		} else {
			mav.addObject("retrieveOtherFsaError", messageSource.getMessage("lts.acq.modemArrangement.noCritiriaToRetrieveFSAservice", new Object[] {}, this.locale));
		}
		
		@SuppressWarnings("unchecked")
		List<FsaDetailDTO> otherFsaList = (List<FsaDetailDTO>)result.getValidateObject();
		
		if (otherFsaList == null || otherFsaList.isEmpty()) {
			mav.addObject("retrieveOtherFsaError", messageSource.getMessage("lts.acq.modemArrangement.noFSAFound.", new Object[] {}, this.locale));
		}
		else {
			form.setOtherFsaList(otherFsaList);
			if (!result.getMessageList().isEmpty()) {
				request.setAttribute("retrieveOtherFsaError", result.getMessageList());
			}
		}
		
		
		return mav;
	}
	
	private ModelAndView handleSearchPcdOrder(LtsModemArrangementFormDTO form, AcqOrderCaptureDTO orderCapture) {
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject(commandName, form);
		
		form.setInputPcdSbOrderId(StringUtils.upperCase(form.getInputPcdSbOrderId()));
		ImsSbOrderDTO pcdSbOrder = null;
		try {
			// TODO: Call IMS API to Retrieve PCD Order
			pcdSbOrder = ltsAcqRelatedPcdOrderService.retrievePcdSbOrder(
					form.getInputPcdSbOrderId(), orderCapture, false);
			if (pcdSbOrder == null) {
				mav.addObject("retrievePcdSbOrderError", messageSource.getMessage("lts.acq.modemArrangement.orderNotFound", new Object[] {}, this.locale));
			}
			else if (StringUtils.equalsIgnoreCase("Y", pcdSbOrder.getPreInstallInd())) {
				mav.addObject("retrievePcdSbOrderError", messageSource.getMessage("lts.acq.modemArrangement.preInstallOffer", new Object[] {}, this.locale));
				pcdSbOrder = null;
			}			
		}
		catch (Exception e) {
			mav.addObject("retrievePcdSbOrderError", messageSource.getMessage("lts.acq.modemArrangement.failToRetrieveOrder", new Object[] {}, this.locale) + ExceptionUtils.getMessage(e));
		}
		finally {
			orderCapture.setRelatedPcdOrder(pcdSbOrder);
		}
		return mav;
	}
	
	private ModelAndView handleClearPcdOrder(LtsModemArrangementFormDTO form,
			AcqOrderCaptureDTO orderCapture) {
		ModelAndView mav = new ModelAndView(viewName);
		orderCapture.setRelatedPcdOrder(null);
		form.setInputPcdSbOrderId(null);
		mav.addObject(commandName, form);
		return mav;
	}
	
	private ModelAndView handleClearOtherFsa(LtsModemArrangementFormDTO form, AcqOrderCaptureDTO orderCapture) {
		ModelAndView mav = new ModelAndView(viewName);
		
		form.setOtherFsaList(null);
		form.setInputDocNum(null);
		form.setInputDocType(null);
		form.setInputOtherFsa(null);
		form.setInputPcdLoginId(null);
		form.setInputPcdLoginDomain(null);
		
		form.setOtherFsaER(false);
		form.setOtherFsaConfirmSameIa(false);
		form.setOtherFsaConfirmSameCust(false);
		
		mav.addObject(commandName, form);
		return mav;
	}

	private boolean isOtherFsaExistInSameIa(AcqOrderCaptureDTO orderCapture) {
		
		String flat = orderCapture.getAddressRollout().getFlat();
		String floor = orderCapture.getAddressRollout().getFloor();
		String serviceBoundary = orderCapture.getAddressRollout().getSrvBdary();
		String docType = getDocType(orderCapture);
		String docNum = getDocNum(orderCapture);
		return ltsAddressRolloutService.isDiffCustFsaExist(serviceBoundary, flat, floor, docType, docNum);
		
	}
	
	private boolean isAllFsaInSameIaNotAllowToShare(List<FsaDetailDTO> fsaDetailList) {
		for (FsaDetailDTO fsaDetail : fsaDetailList) {
			if (!fsaDetail.isNotAllowToShare() && !fsaDetail.isDifferentIa()) {
				return false;
			}
		}
		return true;
	}
	
	private List<FsaDetailDTO> filterFsaBySbBuildXy(List<FsaDetailDTO> fsaList, String srvBdy){
		if(fsaList == null || fsaList.isEmpty()){
			return null;
		}
		
		List<FsaDetailDTO> filteredFsaList = new ArrayList<FsaDetailDTO>();
		String buildXy = addressDetailLtsService.getAddressBuildXy(srvBdy);
		for(FsaDetailDTO fsa: fsaList){
			String fsaBuildXy = addressDetailLtsService.getAddressBuildXy(fsa.getAddressDetail().getSrvBdry());
			if(StringUtils.isNotBlank(buildXy) && StringUtils.isNotBlank(fsaBuildXy)){
				if(StringUtils.equals(buildXy, fsaBuildXy)){
					filteredFsaList.add(fsa);
				}
			}else{
				if(StringUtils.equals(srvBdy, fsa.getAddressDetail().getSrvBdry())){
					filteredFsaList.add(fsa);
				}
			}
			
		}
		
		return filteredFsaList.isEmpty()? null: filteredFsaList;
	}
	
	private String getDocType(AcqOrderCaptureDTO orderCapture){
		return orderCapture.getCustomerDetailProfileLtsDTO().getDocType();
	}
	
	private String getDocNum(AcqOrderCaptureDTO orderCapture){
		return orderCapture.getCustomerDetailProfileLtsDTO().getDocNum();
	}
	
	public LtsAcqRetrieveFsaService getLtsAcqRetrieveFsaService() {
		return ltsAcqRetrieveFsaService;
	}

	public void setLtsAcqRetrieveFsaService(
			LtsAcqRetrieveFsaService ltsAcqRetrieveFsaService) {
		this.ltsAcqRetrieveFsaService = ltsAcqRetrieveFsaService;
	}

	public LtsAcqRelatedPcdOrderService getLtsAcqRelatedPcdOrderService() {
		return ltsAcqRelatedPcdOrderService;
	}

	public void setLtsAcqRelatedPcdOrderService(
			LtsAcqRelatedPcdOrderService ltsAcqRelatedPcdOrderService) {
		this.ltsAcqRelatedPcdOrderService = ltsAcqRelatedPcdOrderService;
	}

	public LtsRetrieveFsaService getLtsRetrieveFsaService() {
		return ltsRetrieveFsaService;
	}

	public void setLtsRetrieveFsaService(LtsRetrieveFsaService ltsRetrieveFsaService) {
		this.ltsRetrieveFsaService = ltsRetrieveFsaService;
	}

	public UpgradePcdSrvService getUpgradePcdSrvService() {
		return upgradePcdSrvService;
	}

	public void setUpgradePcdSrvService(UpgradePcdSrvService upgradePcdSrvService) {
		this.upgradePcdSrvService = upgradePcdSrvService;
	}

	public LtsAddressRolloutService getLtsAddressRolloutService() {
		return ltsAddressRolloutService;
	}

	public void setLtsAddressRolloutService(
			LtsAddressRolloutService ltsAddressRolloutService) {
		this.ltsAddressRolloutService = ltsAddressRolloutService;
	}

	public ImsServiceProfileAccessService getImsServiceProfileAccessService() {
		return imsServiceProfileAccessService;
	}

	public void setImsServiceProfileAccessService(
			ImsServiceProfileAccessService imsServiceProfileAccessService) {
		this.imsServiceProfileAccessService = imsServiceProfileAccessService;
	}

	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}

	public AddressDetailLtsService getAddressDetailLtsService() {
		return addressDetailLtsService;
	}

	public void setAddressDetailLtsService(
			AddressDetailLtsService addressDetailLtsService) {
		this.addressDetailLtsService = addressDetailLtsService;
	}

	public ModemAssignService getModemAssignService() {
		return modemAssignService;
	}

	public void setModemAssignService(ModemAssignService modemAssignService) {
		this.modemAssignService = modemAssignService;
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

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}
	
}
