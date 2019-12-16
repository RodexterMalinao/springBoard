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

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketSelectionFormDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.LtsAddressRolloutService;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.LtsSalesInfoService;
import com.bomwebportal.lts.service.PipbActvLtsService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsAcqAddressRolloutController extends SimpleFormController {
	private final String viewName = "lts/acq/ltsacqaddressrollout";
	private final String nextView = "ltsacqbasketselection.html";
	private final String commandName = "ltsAcqAddressRolloutCmd";

	private Locale locale;
	private MessageSource messageSource;
	protected LtsAddressRolloutService ltsAddressRolloutService;
	private LtsSalesInfoService ltsSalesInfoService;
	private LtsCommonService ltsCommonService;
//	private PipbActvLtsService pipbActvLtsService;
//	private OrderRetrieveLtsService orderRetrieveLtsService;
//	
//	SbOrderDTO pSbOrder = orderRetrieveLtsService.retrieveSbOrder("RTTWE00001569", false);

	public LtsAcqAddressRolloutController() {
		setCommandClass(LtsAddressRolloutFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setLocale(RequestContextUtils.getLocale(request));
		AcqOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getAcqOrderCapture(request);
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		setLocale(RequestContextUtils.getLocale(request));
		//setLocale(new Locale("zh", "HK")); // for test
		
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		
		LtsAddressRolloutFormDTO form = order.getLtsAddressRolloutForm();
		if(form == null){
			form = new LtsAddressRolloutFormDTO();
		}
		
		checkAllowProcess(request, order);
		
		request.setAttribute("isChannelDS", order.isChannelDirectSales());
				
		if(ltsCommonService.isShowDrgDownTimeMsg()){
			request.setAttribute("showDrgDownTimeMsg", true);
		}
		
		LtsAcqBasketSelectionFormDTO basketForm = order.getLtsAcqBasketSelectionForm();
		if(basketForm != null){
			basketForm.setPcdSbidErrMsg("");
		}
		
		return form;
	}
	
	private void checkAllowProcess(HttpServletRequest request, AcqOrderCaptureDTO order){
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);

		boolean notAllowContinue = false;
		String notAllowContinueMsg = null;
		
		String userBoc = (order.isChannelRetail())? bomSalesUser.getBoc() : ltsSalesInfoService.getUserBoc(bomSalesUser.getUsername());

		if(order.isChannelDirectSales()){
			userBoc = LtsConstant.CHANNEL_DIRECT_SALES_DEFAULT_BOC;
		}
		

		//String message = messageSource.getMessage("amend.warn3", new Object[] {pOrderId}, locale)
		if(StringUtils.isBlank(userBoc)){
			notAllowContinue = true;
			notAllowContinueMsg = messageSource.getMessage("lts.acq.address.BOCMissing", new Object[] {bomSalesUser.getUsername()}, locale);
		}else{
			String[] wipInds = {"W", "Y", "P"};
			if(order.getCustomerDetailProfileLtsDTO() != null
					&& StringUtils.isNotBlank(order.getCustomerDetailProfileLtsDTO().getWipInd())
					&& !order.isChannelRetail()
					&& StringUtils.equals(bomSalesUser.getCategory(), LtsConstant.SALES_ROLE_FRONTLINE)
					&& ArrayUtils.contains(wipInds, order.getCustomerDetailProfileLtsDTO().getWipInd())){
				notAllowContinue = true;
				notAllowContinueMsg = messageSource.getMessage("lts.acq.address.WIPProfile", new Object[] {}, locale);
			}
		}
		
		request.setAttribute("notAllowContinue", notAllowContinue);
		request.setAttribute("notAllowContinueMsg", notAllowContinueMsg);
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		//setLocale(RequestContextUtils.getLocale(request));
		//setLocale(new Locale("zh", "HK")); // for test
		
//		pipbActvLtsService.updatePipbActivity(pSbOrder, "User", null, "ORD_AMEND_WITH_SRD_CHG", null);
		
		LtsAddressRolloutFormDTO form = (LtsAddressRolloutFormDTO) command;
		AcqOrderCaptureDTO order = LtsSessionHelper.getAcqOrderCapture(request);
		
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		ValidationResultDTO result = validateAddressRollout(order, form, bomSalesUser);
		
		if (Status.INVAILD == result.getStatus()) {
			ModelAndView mav = new ModelAndView(viewName);
			mav.addObject(commandName, form);
			mav.addObject("errorMsgList", result.getMessageList());
			mav.addObject("addressAreaList", LookupTableBean.getInstance().getAddressAreaList());
			return mav;
		}
		
		
		AddressRolloutDTO addressRollout =(AddressRolloutDTO)order.getAddressRollout();
		addressRollout.setFullAddress(getFullAddress(form));
		
		if(order.getLtsAddressRolloutForm() == null
				|| !StringUtils.equals(order.getLtsAddressRolloutForm().getServiceBoundary(), form.getServiceBoundary())){
			order.setLtsAddressRolloutForm(form);
		}
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();
		referenceData.put("addressAreaList", LookupTableBean.getInstance().getAddressAreaList());
		referenceData.put("addressDistrictList", LookupTableBean.getInstance().getAddressDistrictList());
		referenceData.put("addressSectionList", LookupTableBean.getInstance().getAddressSectionList());
		
		return referenceData;
	}
	
	private void clearSubmittedForm(LtsAddressRolloutFormDTO form) {
		
		if (form.isExternalRelocate()) {
			form.setPonSbRequired(false);
			form.setPonSbNum(null);
		}
		else {
			form.setAreaCode(null);
			form.setAreaDesc(null);
//			form.setBaCaRemainUnchange(false);
			form.setBaCaAction(null);
			form.setBuildingName(null);
			form.setDistrictCode(null);
			form.setDistrictDesc(null);
			form.setFlat(null);
			form.setFloor(null);
			form.setLotHouseInd(null);
			form.setLotNum(null);
			form.setQuickSearch(null);
			form.setSectionCode(null);
			form.setSectionDesc(null);
			form.setServiceBoundary(null);
			form.setStreetCatgCode(null);
			form.setStreetCatgDesc(null);
			form.setStreetName(null);
			form.setStreetNum(null);
		}
		
	}
	
	private String getFullAddress(LtsAddressRolloutFormDTO form) {
		
		StringBuilder targetIa = new StringBuilder();
		if (StringUtils.isNotBlank(form.getFlat())) {
			targetIa.append("FLAT ").append(form.getFlat()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getFloor())) {
			targetIa.append(form.getFloor()).append("/FLOOR ").append(", ");	
		}
		if (StringUtils.isNotBlank(form.getBuildingName())) {
			targetIa.append(form.getBuildingName()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getSectionDesc())) {
			targetIa.append(form.getSectionDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getLotNum())) {
			targetIa.append("HSE ").append(form.getLotNum()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getStreetNum())) {
			targetIa.append(form.getStreetNum()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getStreetName())) {
			targetIa.append(form.getStreetName()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getStreetCatgDesc())) {
			targetIa.append(form.getStreetCatgDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getDistrictDesc())) {
			targetIa.append(form.getDistrictDesc()).append(", ");	
		}
		if (StringUtils.isNotBlank(form.getAreaDesc())) {
			targetIa.append(form.getAreaDesc()).append(", ");	
		}
		return targetIa.substring(0, targetIa.length()-2).toString();
	}

public ValidationResultDTO validateAddressRollout(AcqOrderCaptureDTO orderCapture, LtsAddressRolloutFormDTO form, BomSalesUserDTO bomSalesUser) {
		
		List<String> messageList = new ArrayList<String>();
//		AddressRolloutDTO addressRollout = orderCapture.getLtsServiceProfile().getAddress().getAddressRollout();
//		String sb = orderCapture.getLtsServiceProfile().getAddress().getSrvBdry();
		String erMsg = "";
		
//		if (form.isExternalRelocate() || form.isPonSbRequired()) {
//			addressRollout = orderCapture.getNewAddressRollout();
//			sb = form.getServiceBoundary();
//			erMsg = " Please perform external relocation.";
//		}
		

		AddressRolloutDTO addressRollout = orderCapture.getAddressRollout();
		String sb = form.getServiceBoundary();
		
		if (addressRollout == null) {
			
			messageList.add(messageSource.getMessage("lts.acq.address.notFoundAddressBySb", new Object[] {sb}, locale) + erMsg);
			return new ValidationResultDTO(Status.INVAILD, messageList, null);
		}

		if ( StringUtils.isNotBlank(addressRollout.getHktPremier())) {
			//JT BOM2017062 starts
			if (Arrays.asList(LtsConstant.SALES_ROLES_ALL_MANAGERS).contains(bomSalesUser.getCategory())) {
				return new ValidationResultDTO(Status.VALID, messageList, null);
			}
			//JT BOM2017062 ends
			
			else {
				if (!orderCapture.isChannelPremier()) {
					messageList.add(messageSource.getMessage("lts.acq.address.HKTPremierAddress1", new Object[] {addressRollout.getHktPremier()}, locale));
					return new ValidationResultDTO(Status.INVAILD, messageList, null);	
				}
				else {
					List<String> teamCdList = ltsAddressRolloutService.getTeamByPremierAddr(addressRollout.getHktPremier(), "0");
					
					boolean allowCreatePremierOrder = false;
					if (teamCdList != null && !teamCdList.isEmpty()) {
						for (String teamCd : teamCdList) {
							if (StringUtils.equals(teamCd, bomSalesUser.getShopCd())) {
								allowCreatePremierOrder = true;
								break;
							}
						}
					}
					
					if (!allowCreatePremierOrder) {
						messageList.add(messageSource.getMessage("lts.acq.address.HKTPremierAddress2", new Object[] {addressRollout.getHktPremier()}, locale));
						return new ValidationResultDTO(Status.INVAILD, messageList, null);	
					}
				}
			}
		}
		else {
			if (orderCapture.isChannelPremier()) {
				messageList.add(messageSource.getMessage("lts.acq.address.massAddress", new Object[] {}, locale));
				return new ValidationResultDTO(Status.INVAILD, messageList, null);	
			}
		}
		return new ValidationResultDTO(Status.VALID, messageList, null);
	}
		
	/*if (!addressRollout.isEyeCoverage()) {
		messageList.add("This address is not under eye coverage."+ erMsg);
		return new ValidationResultDTO(Status.INVAILD, messageList, null);
	}*/

	public LtsAddressRolloutService getLtsAddressRolloutService() {
		return ltsAddressRolloutService;
	}

	public void setLtsAddressRolloutService(
			LtsAddressRolloutService ltsAddressRolloutService) {
		this.ltsAddressRolloutService = ltsAddressRolloutService;
	}

	public LtsSalesInfoService getLtsSalesInfoService() {
		return ltsSalesInfoService;
	}

	public void setLtsSalesInfoService(LtsSalesInfoService ltsSalesInfoService) {
		this.ltsSalesInfoService = ltsSalesInfoService;
	}

	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
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
    
//	public PipbActvLtsService getPipbActvLtsService() {
//		return pipbActvLtsService;
//	}
//
//	public void setPipbActvLtsService(PipbActvLtsService pipbActvLtsService) {
//		this.pipbActvLtsService = pipbActvLtsService;
//	}

//	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
//		return orderRetrieveLtsService;
//	}
//
//	public void setOrderRetrieveLtsService(
//			OrderRetrieveLtsService orderRetrieveLtsService) {
//		this.orderRetrieveLtsService = orderRetrieveLtsService;
//	}
}
