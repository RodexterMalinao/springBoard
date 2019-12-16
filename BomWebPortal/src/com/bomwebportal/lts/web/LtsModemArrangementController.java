package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.LineTypeSelectionDTO;
import com.bomwebportal.lts.dto.ModemAssignDTO;
import com.bomwebportal.lts.dto.ModemTechnologyAissgnDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.UpgradePcdSrvDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupMemberProfileDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.TechnologyDTO;
import com.bomwebportal.lts.service.LtsAddressRolloutService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.LtsRelatedPcdOrderService;
import com.bomwebportal.lts.service.LtsRetrieveFsaService;
import com.bomwebportal.lts.service.ModemAssignService;
import com.bomwebportal.lts.service.UpgradePcdSrvService;
import com.bomwebportal.lts.service.bom.ImsServiceProfileAccessService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsModemArrangementController extends SimpleFormController {
	
	private final String viewName = "ltsmodemarrangement";
	private final String nextView = "ltsdeviceselection.html";
	
	private final String commandName = "ltsModemArrangementCmd";
	
	protected LtsRelatedPcdOrderService ltsRelatedPcdOrderService;
	protected LtsRetrieveFsaService ltsRetrieveFsaService;
	protected UpgradePcdSrvService upgradePcdSrvService;
	protected LtsAddressRolloutService ltsAddressRolloutService;
	protected ImsServiceProfileAccessService imsServiceProfileAccessService;
	protected LtsProfileService ltsProfileService;
	protected ModemAssignService modemAssignService;
	protected LtsOfferService ltsOfferService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	
	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public LtsProfileService getLtsProfileService() {
		return ltsProfileService;
	}

	public void setLtsProfileService(LtsProfileService ltsProfileService) {
		this.ltsProfileService = ltsProfileService;
	}

	public ImsServiceProfileAccessService getImsServiceProfileAccessService() {
		return imsServiceProfileAccessService;
	}

	public void setImsServiceProfileAccessService(
			ImsServiceProfileAccessService imsServiceProfileAccessService) {
		this.imsServiceProfileAccessService = imsServiceProfileAccessService;
	}
	
	public LtsAddressRolloutService getLtsAddressRolloutService() {
		return ltsAddressRolloutService;
	}

	public void setLtsAddressRolloutService(
			LtsAddressRolloutService ltsAddressRolloutService) {
		this.ltsAddressRolloutService = ltsAddressRolloutService;
	}

	public UpgradePcdSrvService getUpgradePcdSrvService() {
		return upgradePcdSrvService;
	}

	public void setUpgradePcdSrvService(UpgradePcdSrvService upgradePcdSrvService) {
		this.upgradePcdSrvService = upgradePcdSrvService;
	}

	public LtsRelatedPcdOrderService getLtsRelatedPcdOrderService() {
		return ltsRelatedPcdOrderService;
	}

	public void setLtsRelatedPcdOrderService(
			LtsRelatedPcdOrderService ltsRelatedPcdOrderService) {
		this.ltsRelatedPcdOrderService = ltsRelatedPcdOrderService;
	}

	public LtsRetrieveFsaService getLtsRetrieveFsaService() {
		return ltsRetrieveFsaService;
	}

	public void setLtsRetrieveFsaService(LtsRetrieveFsaService ltsRetrieveFsaService) {
		this.ltsRetrieveFsaService = ltsRetrieveFsaService;
	}

	public ModemAssignService getModemAssignService() {
		return modemAssignService;
	}

	public void setModemAssignService(ModemAssignService modemAssignService) {
		this.modemAssignService = modemAssignService;
	}

	public LtsModemArrangementController() {
		setCommandClass(LtsModemArrangementFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		String locale = RequestContextUtils.getLocale(request).toString();
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			handleRetentionOrder(orderCapture, locale);
			return new ModelAndView(new RedirectView(nextView));
		}
		
		return super.handleRequestInternal(request, response);
	}
	
	private void handleRetentionOrder(OrderCaptureDTO orderCapture, String locale) {
		LtsModemArrangementFormDTO form = new LtsModemArrangementFormDTO();
		orderCapture.setLtsModemArrangementForm(form);
		
		// EYE
		if (StringUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			List<FsaDetailDTO> profileFsaList = getProfileFsaList(orderCapture);
			FsaDetailDTO selectedFsa = null;
			if (profileFsaList != null && !profileFsaList.isEmpty()) {
				form.setModemType(ModemType.SHARE_EX_FSA);
				form.setExistingFsaList(profileFsaList);
				selectedFsa = profileFsaList.get(0);
				selectedFsa.setSelected(true);
			}
			else {
				form.setModemType(ModemType.STANDALONE);	
			}
			postSubmit(orderCapture, form, selectedFsa, locale);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private List<FsaDetailDTO> getProfileFsaList(OrderCaptureDTO orderCapture) {

		if (orderCapture.getLtsServiceProfile().getEyeFsaProfile() == null) {
			return null;
		}

		ValidationResultDTO result = ltsRetrieveFsaService.createFsaDetailList(
				new FsaServiceDetailOutputDTO[] { orderCapture
						.getLtsServiceProfile().getEyeFsaProfile() },
				orderCapture);
		if (ValidationResultDTO.Status.INVAILD == result.getStatus()
				&& result.getValidateObject() == null) {
			return null;
		}

		return (List<FsaDetailDTO>) result.getValidateObject();
		
	}
	
	private void postSubmit(OrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form, FsaDetailDTO selectedFsa, String locale) {
		if (selectedFsa != null) {
				FsaServiceDetailOutputDTO fsaProfile = selectedFsa.getFsaProfile();
//				imsServiceProfileAccessService.getFsaOfferProfile(selectedFsa.getFsaProfile(), 
//						orderCapture.getLtsServiceProfile().getExistEyeType());
				ltsProfileService.getFsaOfferProfile(orderCapture
						.getLtsServiceProfile(), selectedFsa.getFsaProfile(),
						orderCapture.getLtsServiceProfile().getExistEyeType(),
						locale);
				
				selectedFsa.setFsaProfile(fsaProfile);
				selectedFsa.setExistMirrorInd(fsaProfile.getExistMirrorInd());
		}
		
		ModemTechnologyAissgnDTO assignedModemTechnology = assignModemTechnology(orderCapture, form, selectedFsa);
		
		orderCapture.setModemTechnologyAissgn(assignedModemTechnology);
		orderCapture.setLtsModemArrangementForm(form);
		orderCapture.setLtsDeviceSelectionForm(null);
		resetProfileFsa(form);
		
		addRentalRouterItem(orderCapture, form, locale);
	}

	private void addRentalRouterItem(OrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form, String locale) {
		if ( LtsConstant.ROUTER_OPTION_SHARE_RENTAL_ROUTER.equals(form.getRentalRouterInd())) {
			form.setRentalRouterItemList(ltsOfferService.getItemList(LtsConstant.ITEM_TYPE_RENTAL_ROUTER, locale, true, orderCapture.getBasketChannelId(), null)) ;
		}
	}
	
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		LtsModemArrangementFormDTO form = (LtsModemArrangementFormDTO) command;

		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);

		if (orderCapture == null || orderCapture.getLtsAddressRolloutForm() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		String locale = RequestContextUtils.getLocale(request).toString();
		
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
			postSubmit(orderCapture, form, selectedFsa, locale);
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
			return new ModelAndView(new RedirectView("ltscustomerinquiry.html"));
		default:
			break;
		}
		
		return new ModelAndView(new RedirectView(nextView));
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
	
	private ModemTechnologyAissgnDTO assignModemTechnology(OrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form, FsaDetailDTO selectedFsaDetail) {

		String existingService = null;
		String newService = null;
			
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
		
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			return createRenewalModemTechnologyAssign(orderCapture, selectedFsaDetail, newService);
		}
		
		
		ModemAssignDTO modemAssign = createModemTechnologyAissgn(existingService, newService,
				orderCapture.getNewAddressRollout(), selectedFsaDetail, orderCapture, getSelectedLineType(form));
		
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
	
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		return referenceData;
	}
	
	
	private ModemTechnologyAissgnDTO createRenewalModemTechnologyAssign(OrderCaptureDTO orderCapture, FsaDetailDTO selectedFsaDetail, String newService) {
		
		ModemAssignDTO modemAssign = modemAssignService.createRenewalModemAssign(orderCapture.getNewAddressRollout(), 
				selectedFsaDetail.getFsaProfile(), newService);
		
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
	
	
	private ModemTechnologyAissgnDTO createModemTechnologyAissgn(String existingService, String newService,
			AddressRolloutDTO addressRollout, FsaDetailDTO selectedFsaDetail, OrderCaptureDTO orderCapture, String selectedLineType) {
		
		
		ModemAssignDTO modemAssign = modemAssignService.createModemAssign(addressRollout,
				selectedFsaDetail != null ? selectedFsaDetail.getFsaProfile() : null, existingService,
				newService, (selectedFsaDetail != null ? selectedFsaDetail.getUpgradeBandwidth() : null), 
						orderCapture.getLtsAddressRolloutForm().isExternalRelocate(), selectedLineType);
		
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
	
	private void validate(OrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form, BindException errors) {
		
		if(orderCapture.isChannelPremier()){
			switch (form.getModemType()) {
			case STANDALONE:
				TechnologyDTO[] technology = orderCapture.getNewAddressRollout().getTechnology();
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
				if (form.isPcdSbOrderExist()) {
					break ;
				}
				if (!form.isEdfRefExist()) {
					break;
				}
				if (StringUtils.isBlank(form.getEdfRefNum()) || StringUtils.length(form.getEdfRefNum()) < 16
						|| StringUtils.length(form.getEdfRefNum()) > 20) {
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
			ServiceDetailProfileLtsDTO serviceDetailProfile = orderCapture.getLtsServiceProfile();
			FsaDetailDTO selectedFsa = getSelectedFsaDetail(form);
			
			
			
			if (StringUtils.isNotEmpty(serviceDetailProfile.getRelatedEyeFsa())) {
				if (!StringUtils.equals(serviceDetailProfile.getRelatedEyeFsa(), String.valueOf(selectedFsa.getFsa()))) {
					if (isProfileFsaUnderSameCust(serviceDetailProfile, form)) {
						if (LtsConstant.MODEM_TYPE_2L2B.equals(selectedFsa.getModemArrange())) {
							errors.rejectValue("existingFsaList[0].selected", "lts.fsa.cannot.share");
						}
					}
				}
			}
			
			if (StringUtils.isNotBlank(selectedFsa.getPendingOcid()) && LtsConstant.ORDER_TYPE_DISCONNECT.equals(selectedFsa.getPendingOrderType())
					&& !LtsConstant.MODEM_TYPE_2L2B.equals(selectedFsa.getModemArrange())) {
				errors.rejectValue("existingFsaList[0].selected", "lts.fsa.cannot.share.pendingOrder");
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
	
	
	private boolean isProfileFsaUnderSameCust(ServiceDetailProfileLtsDTO ltsServiceProfile, LtsModemArrangementFormDTO form) {
		if (form.getExistingFsaList() != null && !form.getExistingFsaList().isEmpty()) {
			for (FsaDetailDTO fsaDetail : form.getExistingFsaList()) {
				if (StringUtils.equals(String.valueOf(fsaDetail.getFsa()), ltsServiceProfile.getRelatedEyeFsa())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		
		LtsModemArrangementFormDTO form = orderCapture.getLtsModemArrangementForm();
		
		if (form == null) {
			form = new LtsModemArrangementFormDTO();
			orderCapture.setLtsModemArrangementForm(form);
			BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
			
			String docType = orderCapture.getLtsServiceProfile().getPrimaryCust().getDocType();
			String docNum = orderCapture.getLtsServiceProfile().getPrimaryCust().getDocNum(); 
			
			if (orderCapture.getLtsMiscellaneousForm().isRecontract() && orderCapture.getLtsRecontractForm() != null) {
				docNum = orderCapture.getLtsRecontractForm().getDocId();
				docType = orderCapture.getLtsRecontractForm().getDocType();
			}
			
			ValidationResultDTO result = ltsRetrieveFsaService.retrieveFsaServiceByDocument(docType, 
					docNum, orderCapture, bomSalesUser.getUsername());

			@SuppressWarnings("unchecked")
			List<FsaDetailDTO> existingFsaList = (List<FsaDetailDTO>) result
					.getValidateObject();

			List<ModemType> modemTypeSelectionList = new ArrayList<LtsModemArrangementFormDTO.ModemType>();
			
			if (existingFsaList == null || existingFsaList.isEmpty()) {
				modemTypeSelectionList.add(ModemType.STANDALONE);
				form.setModemType(ModemType.STANDALONE);

			} else {
				
				if (isAllFsaInSameIaNotAllowToShare(existingFsaList)) {
					modemTypeSelectionList.add(ModemType.STANDALONE);
				}
				else {
					setPreSelectFsa(existingFsaList, orderCapture, form);
				}
				
				modemTypeSelectionList.add(ModemType.SHARE_EX_FSA);
				form.setModemType(ModemType.SHARE_EX_FSA);
				form.setExistingFsaList(existingFsaList);

				if (!result.getMessageList().isEmpty()) {
					form.setErrorMsgList(result.getMessageList());
				}
			}
			 
			//if DEL upgrade to EYE
			if (StringUtils.isBlank(orderCapture.getLtsServiceProfile().getExistEyeType())
					&& orderCapture.getLtsServiceProfile().getSrvGrp() == null) {
				
				modemTypeSelectionList.add(ModemType.SHARE_PCD);
				
				// TODO: Call IMS API to determine other fsa exist under same IA
				form.setOtherFsaExistInSameIa(isOtherFsaExistInSameIa(orderCapture));
				modemTypeSelectionList.add(ModemType.SHARE_OTHER_FSA);	
				
				modemTypeSelectionList.add(ModemType.SHARE_TV);
				modemTypeSelectionList.add(ModemType.SHARE_BUNDLE);
				
			}else if(orderCapture.getLtsAddressRolloutForm().isExternalRelocate()){ //EYE to EYE and ER
				modemTypeSelectionList.add(ModemType.SHARE_TV);
				modemTypeSelectionList.add(ModemType.SHARE_BUNDLE);
				
			}
			
			form.setModemTypeSelectionList(modemTypeSelectionList);
			setTvTypeList(orderCapture, form);
			setLineTypeSelectionList(orderCapture, form);
			checkAbnormalAddressCoverage(orderCapture, form);
		}
		
		return form;
	}
	
	private void checkAbnormalAddressCoverage(OrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form) {
		
		form.setAbnormalAddrCoverageInd(null);
		
		AddressRolloutDTO addressRollout = orderCapture.getNewAddressRollout();
		String addrCoverageKey = upgradePcdSrvService.getAddressCoverageKey(addressRollout.getMaximumBandwidth());
		if(addrCoverageKey != null){
			return;
		}
		
		//check for PON shortage
//		if("PON".equals(addressRollout.getPcdResourceShortage())){
//			form.setAbnormalAddrCoverageInd("PON_SHORTAGE");
//		}
		
		//check for PON blockage
		if(addressRollout.isFiberBlockageInd()){
			form.setAbnormalAddrCoverageInd("PON_BLOCKAGE");
		}
		
	}
	
	private void setLineTypeSelectionList(OrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form) {
		AddressRolloutDTO addressRollout = orderCapture.getNewAddressRollout();
		
		if (!form.getModemTypeSelectionList().contains(ModemType.STANDALONE) || StringUtils.isNotEmpty(addressRollout.getHktPremier())) {
			return;
		}

		List<LineTypeSelectionDTO> lineTypeSelectionList = new ArrayList<LineTypeSelectionDTO>();
		
		boolean allowSelectLineType = addressRollout.isFiberBlockageInd(); // ArrayUtils.isNotEmpty(addressRollout.getUimBlockage());
		
		if (allowSelectLineType) {
			for (TechnologyDTO techonology : addressRollout.getTechnology()) {
				if (LtsConstant.TECHNOLOGY_ADSL.equals(techonology.getTechnology())
						&& Double.parseDouble(techonology.getMaximumBandwidth()) < 6){
					continue;
				}
				
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
	
	private void setTvTypeList(OrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form) {
		List<String> tvTypeList = new ArrayList<String>();
		tvTypeList.add(LtsConstant.TV_TYPE_SDTV);
		
		if (orderCapture.getNewAddressRollout() != null) {
			List<UpgradePcdSrvDTO> upgradePcdSrvList = upgradePcdSrvService
				.getUpgradePcdSrv(orderCapture.getNewAddressRollout().getMaximumBandwidth(), null, LtsConstant.TV_TYPE_HDTV);
			
			if (upgradePcdSrvList != null && !upgradePcdSrvList.isEmpty()) {
				tvTypeList.add(LtsConstant.TV_TYPE_HDTV);
			}
		}

		form.setTvTypeList(tvTypeList);
	}
	
	private FsaDetailDTO getPreSelectFsa(OrderCaptureDTO orderCapture, List<FsaDetailDTO> existingFsaList) {
		
		/* 20160408
		 * New enhancement 
		 * If existing group FSA is WG, try to share other service FSA e.g PCD / TV
		 * The technology priority is P > V > A
		 * The service priority is PCD > TV
		*/
		
		FsaDetailDTO existGroupFsa = getExistGroupFsa(orderCapture, existingFsaList);
		
		if (existGroupFsa == null) {
			return null;
		}
		
		if (FsaServiceType.WG != existGroupFsa.getExistingService()) {
			return existGroupFsa;
		}

		Map<String, Integer> technologyMap = new HashMap<String, Integer>();
		technologyMap.put("P", 3);
		technologyMap.put("V", 2);
		technologyMap.put("A", 1);
		
		FsaDetailDTO preSelectFsa = null;
		Integer preSelectFsaTechPrior = null;
		
		for (FsaDetailDTO fsaDetail : existingFsaList) {
			if (fsaDetail.isNotAllowToShare()
					|| fsaDetail.isDifferentIa()
					|| fsaDetail.getFsa() == existGroupFsa.getFsa()) {
				continue;
			}
			
			Integer techPrior = technologyMap.get(fsaDetail.getTechnology()); 
				
			if (preSelectFsaTechPrior != null && techPrior != null) {
				if (
					(StringUtils.contains(fsaDetail.getExistingService().getDesc(), "PCD")
						&& techPrior >= preSelectFsaTechPrior)
					||
					 (StringUtils.contains(fsaDetail.getExistingService().getDesc(), "TV") 
						&& techPrior > preSelectFsaTechPrior)) {
					preSelectFsa = fsaDetail;
					preSelectFsaTechPrior = techPrior;
				}
			}
			else {
				preSelectFsa = fsaDetail;
				preSelectFsaTechPrior = techPrior;
			}
		}
		
		return (preSelectFsa != null ? preSelectFsa : existGroupFsa);
	}
	
	
	private FsaDetailDTO getExistGroupFsa (OrderCaptureDTO orderCapture, List<FsaDetailDTO> existingFsaList) {
		String fsa = null;
		if (orderCapture.getLtsServiceProfile().getSrvGrp() != null) {
			ServiceGroupMemberProfileDTO imsMemberProfile = orderCapture.getLtsServiceProfile().getSrvGrp().getMemberByType(LtsConstant.SERVICE_TYPE_IMS);
			if (imsMemberProfile != null) {
				fsa = imsMemberProfile.getSrvNum();	
			}
		}
		
		if (StringUtils.isNotEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())
				&& StringUtils.isNotEmpty(fsa)) {
			for (FsaDetailDTO fsaDetail : existingFsaList) {
				if (fsaDetail.isNotAllowToShare()) {
					continue;
				}
				
				if (StringUtils.equals(String.valueOf(fsaDetail.getFsa()), fsa)) {
					return fsaDetail;
					
				}
			}
		}
		return null;
	}
	
	private void setPreSelectFsa(List<FsaDetailDTO> existingFsaList, OrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form) {
		FsaDetailDTO existGroupFsa = getExistGroupFsa(orderCapture, existingFsaList);
		if (existGroupFsa != null) {
			existGroupFsa.setSelected(true);
		}
		
		FsaDetailDTO preSelectFsa = getPreSelectFsa(orderCapture, existingFsaList);
		if (preSelectFsa != null) {
//			preSelectFsa.setSelected(true);
			
			if (FsaServiceType.WG != preSelectFsa.getExistingService()) {
				form.setPreSelectFsa(String.valueOf(preSelectFsa.getFsa()));	
			}
		}
	}
	
	private ModelAndView handleSearchOtherFsa(LtsModemArrangementFormDTO form, OrderCaptureDTO orderCapture, HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject(commandName, form);
		
		if (StringUtils.equals(orderCapture.getLtsServiceProfile()
				.getPrimaryCust().getDocType(), form.getInputDocType())
				&& StringUtils.equals(orderCapture.getLtsServiceProfile()
						.getPrimaryCust().getDocNum(), form.getInputDocNum())) {
			mav.addObject("retrieveOtherFsaError", messageSource.getMessage("lts.ltsModemArrg.sameInput", new Object[] {}, this.locale));
			return mav;
		}
		
		ValidationResultDTO result = null;
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		
		// TODO: Call IMS API to get FSA Detail 
		if (StringUtils.isNotEmpty(form.getInputOtherFsa())) {
			result = ltsRetrieveFsaService.retrieveFsaServiceByFsa(form.getInputOtherFsa() , orderCapture, bomSalesUser.getUsername());	
		} else if (StringUtils.isNotEmpty(form.getInputPcdLoginId()) && StringUtils.isNotEmpty(form.getInputPcdLoginDomain())) {
			result = ltsRetrieveFsaService.retrieveFsaServiceByLogin(form.getInputPcdLoginId(), form.getInputPcdLoginDomain(), orderCapture, bomSalesUser.getUsername());
		} else {
			mav.addObject("retrieveOtherFsaError", messageSource.getMessage("lts.ltsModemArrg.noSearchCrit", new Object[] {}, this.locale));
		}
		
		@SuppressWarnings("unchecked")
		List<FsaDetailDTO> otherFsaList = (List<FsaDetailDTO>)result.getValidateObject();
		
		if (otherFsaList == null || otherFsaList.isEmpty()) {
			mav.addObject("retrieveOtherFsaError", messageSource.getMessage("lts.ltsModemArrg.noFSAFound", new Object[] {}, this.locale));
		}
		else {
			form.setOtherFsaList(otherFsaList);
			if (!result.getMessageList().isEmpty()) {
				request.setAttribute("retrieveOtherFsaError", result.getMessageList());
			}
		}
		
		
		return mav;
	}
	
	private ModelAndView handleSearchPcdOrder(LtsModemArrangementFormDTO form, OrderCaptureDTO orderCapture) {
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject(commandName, form);
		
		form.setInputPcdSbOrderId(StringUtils.upperCase(form.getInputPcdSbOrderId()));
		ImsSbOrderDTO pcdSbOrder = null;
		try {
			// TODO: Call IMS API to Retrieve PCD Order
			pcdSbOrder = ltsRelatedPcdOrderService.retrievePcdSbOrder(
					form.getInputPcdSbOrderId(), orderCapture, false);
			if (pcdSbOrder == null) {
				mav.addObject("retrievePcdSbOrderError", messageSource.getMessage("lts.ltsModemArrg.sbOrdNotFound", new Object[] {}, this.locale));
			}
			else if (StringUtils.equalsIgnoreCase("Y", pcdSbOrder.getPreInstallInd())) {
				mav.addObject("retrievePcdSbOrderError", messageSource.getMessage("lts.ltsModemArrg.preInstallOrd", new Object[] {}, this.locale));
				pcdSbOrder = null;
			}
		}
		catch (Exception e) {
			mav.addObject("retrievePcdSbOrderError", messageSource.getMessage("lts.ltsModemArrg.sameInput", new Object[] {ExceptionUtils.getMessage(e)}, this.locale));
		}
		finally {
			orderCapture.setRelatedPcdOrder(pcdSbOrder);
		}
		return mav;
	}
	
	private ModelAndView handleClearPcdOrder(LtsModemArrangementFormDTO form,
			OrderCaptureDTO orderCapture) {
		ModelAndView mav = new ModelAndView(viewName);
		orderCapture.setRelatedPcdOrder(null);
		form.setInputPcdSbOrderId(null);
		mav.addObject(commandName, form);
		return mav;
	}
	
	private ModelAndView handleClearOtherFsa(LtsModemArrangementFormDTO form, OrderCaptureDTO orderCapture) {
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
	
	private boolean isOtherFsaExistInSameIa(OrderCaptureDTO orderCapture) {
		
		String flat = orderCapture.getNewAddressRollout().getFlat();
		String floor = orderCapture.getNewAddressRollout().getFloor();
		String serviceBoundary = orderCapture.getNewAddressRollout().getSrvBdary();
		String docType = orderCapture.getLtsServiceProfile().getPrimaryCust().getDocType();
		String docNum = orderCapture.getLtsServiceProfile().getPrimaryCust().getDocNum();
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

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
