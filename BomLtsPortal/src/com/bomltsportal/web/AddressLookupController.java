package com.bomltsportal.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.AddressLookupFormDTO;
import com.bomltsportal.service.AddressLookupService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class AddressLookupController extends SimpleFormController{

	protected final Log logger = LogFactory.getLog(getClass());
	private String nextView = "addressrollout.html";

	private AddressLookupService addressLookupService;
	private CodeLkupCacheService googleApiKeyCacheService;
	
	private String environment;
	
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
		
		OrderCaptureDTO orderCaptureDTO = SessionHelper.getOrderCapture(request);
		AddressLookupFormDTO form = new AddressLookupFormDTO();
		String mapKey = null;
		String mapKeyLkup = null;
		if(orderCaptureDTO == null){
			orderCaptureDTO = new OrderCaptureDTO();
		}
		orderCaptureDTO.setTopNavInd(BomLtsConstant.TOP_NAV_IND_CONFIRM_ADDRESS);
		mapKey = "v=3.9";
		if(!StringUtils.equals("dev",environment)){
			mapKeyLkup = (String) googleApiKeyCacheService.get(BomLtsConstant.GOOGLE_MAP_API_KEY);
			if(StringUtils.isNotBlank(mapKeyLkup)){
				mapKey = "key="+mapKeyLkup;
			}
		}
		request.setAttribute("mapKey", mapKey);
		return form;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
			throws ServletException {
		OrderCaptureDTO orderCaptureDTO = SessionHelper.getOrderCapture(request);
		AddressLookupFormDTO form = (AddressLookupFormDTO) command;
		BuildingMarkerDTO buildingMarker = addressLookupService.getBuildingMarkerDetailByKey(form.getMarkerIdx());
		form.setBuildingMarker(buildingMarker);
		orderCaptureDTO.setAddressLookupForm(form);
		return new ModelAndView(new RedirectView(nextView));
	}

	public AddressLookupService getAddressLookupService() {
		return addressLookupService;
	}

	public void setAddressLookupService(AddressLookupService addressLookupService) {
		this.addressLookupService = addressLookupService;
	}

	public CodeLkupCacheService getGoogleApiKeyCacheService() {
		return googleApiKeyCacheService;
	}

	public void setGoogleApiKeyCacheService(
			CodeLkupCacheService googleApiKeyCacheService) {
		this.googleApiKeyCacheService = googleApiKeyCacheService;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
	
}
