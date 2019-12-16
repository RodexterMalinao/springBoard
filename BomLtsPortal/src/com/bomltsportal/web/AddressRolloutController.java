package com.bomltsportal.web;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.AddressRolloutFormDTO;
import com.bomltsportal.service.OnlineAddressRolloutService;
import com.bomltsportal.service.OnlineSalesLogService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.lts.dto.AddressDetailDTO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.srvAccess.TechnologyDTO;
import com.bomwebportal.lts.service.AddressRolloutService;
import com.bomwebportal.lts.service.bom.AddressDetailLtsService;


public class AddressRolloutController extends SimpleFormController{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private OnlineAddressRolloutService onlineAddressRolloutService;
	private AddressRolloutService addressRolloutService;
	private AddressDetailLtsService addressDetailLtsService;
	private OnlineSalesLogService onlineSalesLogService;
	
	private final String nextView = "basketselect.html";
	private final String viewName = "addressrollout";
	private final String commandName = "addressRolloutForm";

	public AddressDetailLtsService getAddressDetailLtsService() {
		return addressDetailLtsService;
	}

	public void setAddressDetailLtsService(
			AddressDetailLtsService addressDetailLtsService) {
		this.addressDetailLtsService = addressDetailLtsService;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		if(orderCapture == null || orderCapture.isOrderSignoffed()){
			return SessionHelper.getSessionTimeOutView();
		}
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		Locale locale = RequestContextUtils.getLocale(request);
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		if(orderCapture == null){
			orderCapture = new OrderCaptureDTO();
		}
		String fullAddress = null;
		if("en".equals(locale.toString())){
			fullAddress = orderCapture.getAddressLookupForm().getBuildingMarker().getAddressEn();
		}else{
			fullAddress = orderCapture.getAddressLookupForm().getBuildingMarker().getAddressCh();
		}
		
		AddressRolloutFormDTO form = orderCapture.getAddressRolloutForm();
		if(form == null || !StringUtils.equals(fullAddress, orderCapture.getAddressRolloutForm().getAddress())){
			form = new AddressRolloutFormDTO();
			form.setAddress(fullAddress);
		}
		
		orderCapture.setTopNavInd(BomLtsConstant.TOP_NAV_IND_CONFIRM_ADDRESS);
		return form;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		AddressRolloutFormDTO form = (AddressRolloutFormDTO) command;
		
		BuildingMarkerDTO buildMarker = orderCapture.getAddressLookupForm().getBuildingMarker();
		String floor =  form.getFloor();
		String flat =  StringUtils.isBlank(form.getFlat())?" ":form.getFlat();
		String[] srvBrdys = onlineAddressRolloutService
				.getConsumerEyeSrvBdryByBuildCoordinate(flat, floor,
						buildMarker.getBuildXy(),
						orderCapture.getServiceTypeInd());
		
		
		if (ArrayUtils.isNotEmpty(srvBrdys)) {
			
			if (StringUtils.equals(BomLtsConstant.FAIL_TO_GET_SRVBDRY_IND, srvBrdys[0])) {
				return getShortageView(form, orderCapture);
			}
			
			AddressRolloutDTO addressRollout = onlineAddressRolloutService.getAddressRollout(srvBrdys, flat, floor, orderCapture.getServiceTypeInd());
			
			if (addressRollout != null) {

				AddressDetailDTO addressDetail = addressDetailLtsService
						.getAddressDetail(addressRollout.getSrvBdary());
				orderCapture.setAddressRollout(addressRollout);
				orderCapture.setAddressDetail(addressDetail);
			} else {
				return getShortageView(form, orderCapture);
			}
			
			if (ArrayUtils.isEmpty(addressRollout.getTechnology())
					&& addressRollout.isPonVilla()) {
				return getShortageView(form, orderCapture);
			}

			if(StringUtils.equals(BomLtsConstant.SERVICE_TYPE_EYE, orderCapture.getServiceTypeInd()) && isPCDShortage(addressRollout)){
				return getShortageView(form, orderCapture);
			}
		}else{
			return getShortageView(form, orderCapture);
		}
		
		orderCapture.setAddressRolloutForm(form);
		
//		ModelAndView mav = new ModelAndView("tdo");
//		return new ModelAndView(new RedirectView("tdo.html"));
		
		onlineSalesLogService.logOnlineDetailTrack(
				SessionHelper.getRequestId(request), 
				SessionHelper.getCurrentPage(request), 
				BomLtsConstant.LOG_TRACK_ITEM_CD_SB_NO, 
				orderCapture.getAddressRollout().getSrvBdary(), 
				SessionHelper.getRequestSeq(request));
		
		return new ModelAndView(new RedirectView(nextView));
		
	}
	
	private ModelAndView getShortageView(AddressRolloutFormDTO form, OrderCaptureDTO orderCapture){
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject(commandName, form);
		mav.addObject("isShortage", true);
		mav.addObject("marker_idx", orderCapture.getAddressLookupForm().getMarkerIdx());
		return mav;
	}
	
	private boolean isPCDShortage(AddressRolloutDTO addressRollout){
		for(TechnologyDTO tech: addressRollout.getTechnology()){
			if(StringUtils.equals("N", tech.getIsDeadCase())
					|| StringUtils.equals("N", tech.getIsResrcShort())){
				return false;
			}
		}
		return true;
	}
	
	public OnlineAddressRolloutService getOnlineAddressRolloutService() {
		return onlineAddressRolloutService;
	}

	public void setOnlineAddressRolloutService(
			OnlineAddressRolloutService onlineAddressRolloutService) {
		this.onlineAddressRolloutService = onlineAddressRolloutService;
	}

	public AddressRolloutService getAddressRolloutService() {
		return addressRolloutService;
	}

	public void setAddressRolloutService(AddressRolloutService addressRolloutService) {
		this.addressRolloutService = addressRolloutService;
	}

	public OnlineSalesLogService getOnlineSalesLogService() {
		return onlineSalesLogService;
	}

	public void setOnlineSalesLogService(OnlineSalesLogService onlineSalesLogService) {
		this.onlineSalesLogService = onlineSalesLogService;
	}

	

}
	
	