package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.acq.AcqBasketSelectionInfoDTO;
import com.bomwebportal.lts.dto.form.LtsBasketSelectionFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketSelectionFormDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.acq.LtsAcqRelatedPcdOrderService;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.service.bom.ImsServiceProfileAccessService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.google.common.collect.Lists;

public class LtsAcqBasketSelectionController extends SimpleFormController {

	private final String viewName = "lts/acq/ltsacqbasketselection";
	private final String nextView = "ltsacqbasketservice.html";
	private final String commandName = "ltsAcqBasketSelectionCmd";
	
	private ImsServiceProfileAccessService imsServiceProfileAccessService;
	private ImsProfileService imsProfileService;
	private LtsAcqRelatedPcdOrderService ltsAcqRelatedPcdOrderService;

	protected LtsBasketService ltsBasketService;
	private LtsOfferService ltsOfferService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsAcqBasketSelectionController(){
		this.setFormView(viewName);
		this.setCommandName(commandName);
		this.setCommandClass(LtsAcqBasketSelectionFormDTO.class);
	}
	
	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}
	
	public ImsServiceProfileAccessService getImsServiceProfileAccessService() {
		return imsServiceProfileAccessService;
	}

	public void setImsServiceProfileAccessService(
			ImsServiceProfileAccessService imsServiceProfileAccessService) {
		this.imsServiceProfileAccessService = imsServiceProfileAccessService;
	}
		
	public LtsAcqRelatedPcdOrderService getLtsAcqRelatedPcdOrderService() {
		return ltsAcqRelatedPcdOrderService;
	}

	public void setLtsAcqRelatedPcdOrderService(
			LtsAcqRelatedPcdOrderService ltsAcqRelatedPcdOrderService) {
		this.ltsAcqRelatedPcdOrderService = ltsAcqRelatedPcdOrderService;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		if (order == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqBasketSelectionFormDTO form = order.getLtsAcqBasketSelectionForm();
		
		if(form == null){
			form = new LtsAcqBasketSelectionFormDTO();
			form.setCurrentTab("0");
		}
		
		order.setLtsAcqBasketSelectionForm(form);
		return form;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException, Exception {

		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqBasketSelectionFormDTO form = (LtsAcqBasketSelectionFormDTO)command;
		String submitInd = request.getParameter("submitInd");

		
		if("SEARCH".equals(submitInd)){
			order.setLtsAcqBasketSelectionForm(form);
			
			if (form.getCurrentTab().equals("8") || form.getCurrentTab().equals("9")) // 8-th tab = pcd bundle tab, 9-th tab = del free bundle tab
			{
				boolean isPcdSbidValid = ltsBasketService.isPcdSbidValid(form.getPcdSbid());
				if(!isPcdSbidValid)
				{
					form.setPcdSbidErrMsg(messageSource.getMessage("lts.acq.basketSelection.inalidPCDSBID", new Object[] {}, locale));
				}
				else
				{
					form.setPcdSbidErrMsg("");
				}
				
			}
			else
			{
				form.setPcdSbidErrMsg("");
			}
			
			
			return new ModelAndView(new RedirectView("ltsacqbasketselection.html"));
		}else{

			ModelAndView mav = new ModelAndView(viewName, referenceData(request, command, errors));
			ValidationResultDTO result = validateSubmit(request, order, form);
			if (result.getStatus() == Status.INVAILD) {
				mav.addAllObjects(errors.getModel());
				mav.addObject(commandName, form);
				mav.addObject("errorMsgList", result.getMessageList());
				return mav;
			}
			
			request.getSession().removeAttribute(LtsConstant.SESSION_BASKET_SELECTION_INFO);
			
			if (form.getCurrentTab().equals("9"))
			{
				form.setDelFreeBundle(true);
			}
			else
			{
				form.setDelFreeBundle(false);
			}

			order.setLtsAcqBasketSelectionForm(form);

			
		}
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	
	private ValidationResultDTO validateSubmit(HttpServletRequest request, AcqOrderCaptureDTO order, LtsAcqBasketSelectionFormDTO form){
		
		AcqBasketSelectionInfoDTO basketsInfo =  (AcqBasketSelectionInfoDTO)request.getSession().getAttribute(LtsConstant.SESSION_BASKET_SELECTION_INFO);
		if(basketsInfo != null){
			BasketDetailDTO basketDtl = basketsInfo.getAllBasketMap().get(form.getSelectedBasketId());
			
			if(StringUtils.isNotBlank(basketDtl.getProgramCd())){
				String tempProgramDesc = ltsOfferService.validateQuotaByProgramCdRtnStr(basketDtl.getProgramCd(), 1);
				if(tempProgramDesc!=null)
					return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.offerDtl.outQuotaMsg", new Object[] {tempProgramDesc}, this.locale)), null);
			}
			
			basketDtl.setStaffNumber(form.getStaffNumber());
			form.setSelectedBasketChannel(basketDtl.getBasketChannelId());
			form.setSelectedDeviceType(basketDtl.getDeviceType());
			form.setSelectedType(basketDtl.getType());
			form.setSelectedBasketId(basketDtl.getBasketId());
			
			String pcdBundleFreeType = "";
			
			if(StringUtils.isNotBlank(basketDtl.getPcdBundleFreeType())){
				pcdBundleFreeType = basketDtl.getPcdBundleFreeType();
			}
			
			if (form.getCurrentTab().equals("8") || form.getCurrentTab().equals("9"))
			{					
				boolean isPcdSbidValid = ltsBasketService.isPcdSbidValid(form.getPcdSbid());
				if(!isPcdSbidValid)
				{
					return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.acq.basketSelection.inalidPCDSBID", new Object[] {}, this.locale)), null);
//					form.setPcdSbidErrMsg(messageSource.getMessage("lts.acq.basketSelection.inalidPCDSBID", new Object[] {}, locale));
//					return new ModelAndView(new RedirectView("ltsacqbasketselection.html"));
				}
				else
				{
					if(form.getCurrentTab().equals("9"))
					{
						if(!ltsAcqRelatedPcdOrderService.isDelFreePcdSbOrder(form.getPcdSbid(),pcdBundleFreeType))
						{
							return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.acq.basketSelection.PCDwithoutdelightoffer", new Object[] {}, this.locale)), null);
//							form.setPcdSbidErrMsg(messageSource.getMessage("lts.acq.basketSelection.PCDwithoutdelightoffer", new Object[] {}, locale));
//							return new ModelAndView(new RedirectView("ltsacqbasketselection.html"));
						}
					}
					form.setPcdSbidErrMsg("");
				}
			}
			else
			{
				form.setPcdSbidErrMsg("");
			}

			String prevSerTermMth = basketDtl.getPrevSerTermMth();
			String srvbdry_num = "";
			String floor_num = "";
			String unit_num = "";
			
			if(form.getCurrentTab().equals("9") && StringUtils.isBlank(prevSerTermMth)){
					prevSerTermMth = "3"; //PCD Bundle case default to 3
			}
			if(StringUtils.isNotBlank(prevSerTermMth)){ 
				if(order.getLtsAddressRolloutForm()!=null)
				{
					srvbdry_num = order.getLtsAddressRolloutForm().getServiceBoundary();
					floor_num = order.getLtsAddressRolloutForm().getFloor();
					unit_num = order.getLtsAddressRolloutForm().getFlat();
				}
				if(ltsAcqRelatedPcdOrderService.checkAnyActiveServiceWithinXMonths(srvbdry_num, floor_num, unit_num, prevSerTermMth))
				{
					return new ValidationResultDTO(Status.INVAILD, Lists.newArrayList(messageSource.getMessage("lts.acq.basketSelection.targetAddresshasServiceInXM", new Object[] {prevSerTermMth}, this.locale)), null);
//					form.setErrMsg(messageSource.getMessage("lts.acq.basketSelection.targetAddresshasServiceInXM", new Object[] {prevSerTermMth}, locale));
//					return new ModelAndView(new RedirectView("ltsacqbasketselection.html"));
				}
			}
			
			if(StringUtils.equals(basketDtl.getBasketChannelId(), LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_MASS_EYE)
					|| StringUtils.equals(basketDtl.getBasketChannelId(), LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_PT_EYE)){
				order.setEyeOrder(true);
			}else{
				order.setEyeOrder(false);
			}
			
			order.setSelectedBasket(basketDtl);

			
		}
		
		
		

		return new ValidationResultDTO(Status.VALID, null, null);
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		String locale = RequestContextUtils.getLocale(request).toString();
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		LtsAcqBasketSelectionFormDTO form = order.getLtsAcqBasketSelectionForm();

		boolean get18Mth = form.isFilterCommitPeriod18mth();
		boolean getGt24Mth = form.isFilterCommitPeriodGt24mth();
		boolean getLt12Mth = form.isFilterCommitPeriodLt12mth();
		if(!get18Mth && !getGt24Mth && !getLt12Mth){
			get18Mth = true;
			getGt24Mth = true;
			getLt12Mth = true;
		}
		
		String tab = form.getCurrentTab();
		boolean getStaffPlan = false;
		AcqBasketSelectionInfoDTO basketSelectionInfo = new AcqBasketSelectionInfoDTO();
		
		if(LtsConstant.TAB_BASKET_TYPE_EYE.equals(LtsConstant.BASKET_TAB_MAP.get(tab))){
			getStaffPlan = form.isStaffPlan();
		}
		
		if(StringUtils.isBlank(form.getPeInd())){
			form.setPeInd(order.getAddressRollout().isPeCoverage()?"Y":"N");
		}
		
//		referenceData.put(LtsConstant.SESSION_BASKET_SELECTION_INFO, basketSelectionInfo);
		basketSelectionInfo = getBasketSelectionInfo(bomSalesUser, form, order, locale, form.getFilterDeviceType(), form.getFilterBridgingMonth(), getGt24Mth, get18Mth, getLt12Mth, getStaffPlan);
		request.getSession().setAttribute(LtsConstant.SESSION_BASKET_SELECTION_INFO, basketSelectionInfo);
		
		referenceData.put("acqPeCoverage", order.getAddressRollout().isPeCoverage()?"Y":"N");
		referenceData.put("filterProjectCdList", ltsBasketService.getProjectCdList(bomSalesUser.getChannelCd(), bomSalesUser.getShopCd(), "ACQ"));
		referenceData.put("pcdSbidErrMsg", form.getPcdSbidErrMsg());
		
//		String currentTab = request.getParameter("currentTab");
//		referenceData.put("currentTab", StringUtils.isNotBlank(currentTab)? currentTab: "0");
		
		return referenceData; //super.referenceData(request);
	}
	
	private AcqBasketSelectionInfoDTO getBasketSelectionInfo(BomSalesUserDTO bomSalesUser, LtsAcqBasketSelectionFormDTO form, AcqOrderCaptureDTO orderCapture, String locale, 
			String deviceType, String[] bridgingMth, boolean contractPeriodGt24, boolean contractPeriodEq18, boolean contractPeriodLt12, boolean filterStaffPlan) {
		String housingType = orderCapture.getAddressRollout().getHousingType();
		boolean eyeCoverage = orderCapture.getAddressRollout().isEyeCoverage();
		boolean is2NCoverage = orderCapture.getAddressRollout().isIs2nBuilding();
		String ltsHousingType = orderCapture.getAddressRollout().getLtsHousingCatCd();
		String teamCode = bomSalesUser.getShopCd();
		String areaCode = bomSalesUser.getAreaCd();
		String role = bomSalesUser.getCategory();
		boolean isPremier = !StringUtils.isEmpty(orderCapture.getAddressRollout().getHktPremier());
		String parallelExtension = orderCapture.getAddressRollout().isPeCoverage()?"Y":"N";
		
		AcqBasketSelectionInfoDTO basketSelectionInfo = new AcqBasketSelectionInfoDTO();
		
		Map<String, BasketDetailDTO> allBasketMap = new HashMap<String, BasketDetailDTO>();
		List<BasketDetailDTO> hotBasketList = new ArrayList<BasketDetailDTO>();

		String eyeChannelId = isPremier? LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_PT_EYE: LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_MASS_EYE;
		String delChannelId = isPremier? LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_PT_DEL: LtsConstant.CHANNEL_ID_SPRINGBOARD_ACQ_MASS_DEL;
		
		String[] defaultProjectCds = getDefaultProjectCodes(orderCapture, bomSalesUser.getUsername());
		String[] filterProjectCds = getFilterProjectCodes(form);
		
		String hktPremier = "";
		String srvBoundary = "";
		
		if(orderCapture != null && orderCapture.getAddressRollout() != null){
			hktPremier = orderCapture.getAddressRollout().getHktPremier();
			srvBoundary = orderCapture.getAddressRollout().getSrvBdary();
		}

		List<BasketDetailDTO> pcdBundleBasketList = new ArrayList<BasketDetailDTO>();
		
		if(eyeCoverage){
			List<BasketDetailDTO> hotEyeBasketList = 
					ltsBasketService.getAcqBasketList(LtsConstant.DEVICE_TYPE_EYE3A, parallelExtension, locale, deviceType, 
							LtsConstant.BASKET_CATEGORY_HOT, teamCode, eyeChannelId, null, true, true, true, filterStaffPlan, housingType, is2NCoverage,
							filterProjectCds, defaultProjectCds, hktPremier, srvBoundary, areaCode, role, form.getOsType(), ltsHousingType);
			hotEyeBasketList = filterPeBasketsSearch(hotEyeBasketList, form.getPeInd());
			addToAllBaksetMap(hotEyeBasketList, allBasketMap);
			
			List<BasketDetailDTO> eyeOtherBasketList = 
					ltsBasketService.getAcqBasketList(LtsConstant.DEVICE_TYPE_EYE3A, parallelExtension, locale, deviceType, 
							LtsConstant.BASKET_CATEGORY_OTHER, teamCode, eyeChannelId, null, true, true, true, filterStaffPlan, housingType, is2NCoverage,
							filterProjectCds, defaultProjectCds, hktPremier, srvBoundary, areaCode, role, form.getOsType(), ltsHousingType);
			eyeOtherBasketList = filterPeBasketsSearch(eyeOtherBasketList, form.getPeInd());
			addToAllBaksetMap(eyeOtherBasketList, allBasketMap);

			List<BasketDetailDTO> eyePcdBundleBasketList = 
					ltsBasketService.getAcqBasketList(LtsConstant.DEVICE_TYPE_EYE3A, parallelExtension, locale, deviceType, 
							LtsConstant.BASKET_CATEGORY_PCD_BUNDLE, teamCode, eyeChannelId, 
							bridgingMth, contractPeriodGt24, contractPeriodEq18, contractPeriodLt12, false, housingType, is2NCoverage,
							filterProjectCds, defaultProjectCds, hktPremier, srvBoundary, areaCode, role, form.getOsType(), ltsHousingType);
			eyePcdBundleBasketList = filterPeBasketsSearch(eyePcdBundleBasketList, form.getPeInd());
			addToAllBaksetMap(eyePcdBundleBasketList, allBasketMap);
			
			List<BasketDetailDTO> eyeBasketList = 
					ltsBasketService.getAcqBasketList(LtsConstant.DEVICE_TYPE_EYE3A, parallelExtension, locale, deviceType, 
							null, teamCode, eyeChannelId, null, true, true, true, filterStaffPlan, housingType, is2NCoverage,
							filterProjectCds, defaultProjectCds, hktPremier, srvBoundary, areaCode, role, form.getOsType(), ltsHousingType);
			eyeBasketList = filterPeBasketsSearch(eyeBasketList, form.getPeInd());
			
			List<BasketDetailDTO> filteredEyeBasketList = new ArrayList<BasketDetailDTO>();
			
			if (eyeBasketList != null && !eyeBasketList.isEmpty()) {
				for (BasketDetailDTO basketDetail : eyeBasketList) {
					if (!allBasketMap.containsKey(basketDetail.getBasketId())) {
						allBasketMap.put(basketDetail.getBasketId(), basketDetail);
						filteredEyeBasketList.add(basketDetail);
					}
				}
			}

			hotBasketList.addAll(hotEyeBasketList);
			basketSelectionInfo.setEyeBasketList(filteredEyeBasketList);
			basketSelectionInfo.setEyeOtherBasketList(eyeOtherBasketList);
			pcdBundleBasketList.addAll(eyePcdBundleBasketList);
		}
		
		List<BasketDetailDTO> hotDelBasketList = 
				ltsBasketService.getAcqBasketList(null, null, locale, null, 
						LtsConstant.BASKET_CATEGORY_HOT, teamCode, delChannelId, 
						bridgingMth, contractPeriodGt24, contractPeriodEq18, contractPeriodLt12, false, housingType, is2NCoverage,
						filterProjectCds, defaultProjectCds, hktPremier, srvBoundary, areaCode, role, form.getOsType(), ltsHousingType);
		hotBasketList.addAll(hotDelBasketList);
		addToAllBaksetMap(hotDelBasketList, allBasketMap);
		
		List<BasketDetailDTO> delPremiumBasketList = 
				ltsBasketService.getAcqBasketList(null, null, locale, null, 
						LtsConstant.BASKET_CATEGORY_PREMIUM, teamCode, delChannelId, 
						bridgingMth, contractPeriodGt24, contractPeriodEq18, contractPeriodLt12, false, housingType, is2NCoverage,
						filterProjectCds, defaultProjectCds, hktPremier, srvBoundary, areaCode, role, form.getOsType(), ltsHousingType);

		List<BasketDetailDTO> delRebateBasketList = 
				ltsBasketService.getAcqBasketList(null, null, locale, null, 
						LtsConstant.BASKET_CATEGORY_REBATE, teamCode, delChannelId, 
						bridgingMth, contractPeriodGt24, contractPeriodEq18, contractPeriodLt12, false, housingType, is2NCoverage,
						filterProjectCds, defaultProjectCds, hktPremier, srvBoundary, areaCode, role, form.getOsType(), ltsHousingType);
		
		List<BasketDetailDTO> delRebateCouponBasketList = 
				ltsBasketService.getAcqBasketList(null, null, locale, null, 
						LtsConstant.BASKET_CATEGORY_REBATE_COUPON, teamCode, delChannelId, 
						bridgingMth, contractPeriodGt24, contractPeriodEq18, contractPeriodLt12, false, housingType, is2NCoverage,
						filterProjectCds, defaultProjectCds, hktPremier, srvBoundary, areaCode, role, form.getOsType(), ltsHousingType);
		
		List<BasketDetailDTO> delRebatePremiumBasketList = 
				ltsBasketService.getAcqBasketList(null, null, locale, null, 
						LtsConstant.BASKET_CATEGORY_REBATE_PREMIUM, teamCode, delChannelId, 
						bridgingMth, contractPeriodGt24, contractPeriodEq18, contractPeriodLt12, false, housingType, is2NCoverage,
						filterProjectCds, defaultProjectCds, hktPremier, srvBoundary, areaCode, role, form.getOsType(), ltsHousingType);
		
		List<BasketDetailDTO> delOtherBasketList = 
				ltsBasketService.getAcqBasketList(null, null, locale, null, 
						LtsConstant.BASKET_CATEGORY_OTHER, teamCode, delChannelId, 
						bridgingMth, contractPeriodGt24, contractPeriodEq18, contractPeriodLt12, false, housingType, is2NCoverage,
						filterProjectCds, defaultProjectCds, hktPremier, srvBoundary, areaCode, role, form.getOsType(), ltsHousingType);
		
		List<BasketDetailDTO> delPcdBundleBasketList = 
				ltsBasketService.getAcqBasketList(null, null, locale, null, 
						LtsConstant.BASKET_CATEGORY_PCD_BUNDLE, teamCode, delChannelId, 
						bridgingMth, contractPeriodGt24, contractPeriodEq18, contractPeriodLt12, false, housingType, is2NCoverage,
						filterProjectCds, defaultProjectCds, hktPremier, srvBoundary, areaCode, role, form.getOsType(), ltsHousingType);
		
		List<BasketDetailDTO> delfreeBundleBasketList = 
				ltsBasketService.getAcqBasketList(null, null, locale, null, 
						LtsConstant.BASKET_CATEGORY_PCD_BUNDLE_FREE, teamCode, delChannelId, 
						bridgingMth, contractPeriodGt24, contractPeriodEq18, contractPeriodLt12, false, housingType, is2NCoverage,
						filterProjectCds, defaultProjectCds, hktPremier, srvBoundary, areaCode, role, form.getOsType(), ltsHousingType);
		
		basketSelectionInfo.setHotBasketList(hotBasketList);
		basketSelectionInfo.setFixLinePremiumBasketList(delPremiumBasketList);
		basketSelectionInfo.setFixLineRebateBasketList(delRebateBasketList);
		basketSelectionInfo.setFixLineRebateCouponBasketList(delRebateCouponBasketList);
		basketSelectionInfo.setFixLineOtherBasketList(delOtherBasketList);
		basketSelectionInfo.setFixLineRebatePremiumBasketList(delRebatePremiumBasketList);
		pcdBundleBasketList.addAll(delPcdBundleBasketList);
		basketSelectionInfo.setPcdBundleBasketList(pcdBundleBasketList);
		basketSelectionInfo.setDelfreeBundleBasketList(delfreeBundleBasketList);
		
		addToAllBaksetMap(delPremiumBasketList, allBasketMap);
		addToAllBaksetMap(delRebateBasketList, allBasketMap);
		addToAllBaksetMap(delRebateCouponBasketList, allBasketMap);
		addToAllBaksetMap(delOtherBasketList, allBasketMap);
		addToAllBaksetMap(delRebatePremiumBasketList, allBasketMap);
		addToAllBaksetMap(delPcdBundleBasketList, allBasketMap);
		addToAllBaksetMap(delfreeBundleBasketList, allBasketMap);
		
		
		basketSelectionInfo.setAllBasketMap(allBasketMap);
		return basketSelectionInfo;
	}
	
	
	private List<BasketDetailDTO> filterPeBasketsSearch(List<BasketDetailDTO> pBasketList, String pPeInd){
		List<BasketDetailDTO> basketList = new ArrayList<BasketDetailDTO>();
		for(BasketDetailDTO basket : pBasketList){
			if(pPeInd.equals(basket.getPeInd())){
				basketList.add(basket);
			}
		}
		return basketList;
	}

	private void addToAllBaksetMap(List<BasketDetailDTO> basketList, Map<String, BasketDetailDTO> allBasketMap){

		if (basketList != null && !basketList.isEmpty()) {
			for (BasketDetailDTO basketDetail : basketList) {
				
				if (!allBasketMap.containsKey(basketDetail.getBasketId())) {
					allBasketMap.put(basketDetail.getBasketId(), basketDetail);
				}
			
			}	
		}
	}
	
	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}
	
	private String[] getFilterProjectCodes(LtsAcqBasketSelectionFormDTO form) {
		if (StringUtils.isNotEmpty(form.getFilterProjectCd())) {
			return new String[] {form.getFilterProjectCd()};
		}
		return null;
	}
	
	private String[] getDefaultProjectCodes(AcqOrderCaptureDTO orderCapture, String userId) {
		List<String> projectCodeList = new ArrayList<String>();
		projectCodeList.add(LtsConstant.BASKET_PROJECT_CD_EYE3_FREE_3MTH);
		return projectCodeList.toArray(new String[projectCodeList.size()] );
	}
	
    public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
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
