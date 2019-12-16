package com.bomwebportal.lts.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.lts.dto.DeviceDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsDeviceSelectionFormDTO;
import com.bomwebportal.lts.dto.profile.OfferActionLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsDeviceService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsDeviceSelectionController extends SimpleFormController {
	
	private final String nextView = "ltsbasketselection.html";
	private final String viewName = "ltsdeviceselection";
	private final String commandName = "ltsDeviceSelectionCmd";

	protected LtsDeviceService ltsDeviceService;
	
	public LtsDeviceService getLtsDeviceService() {
		return ltsDeviceService;
	}

	public void setLtsDeviceService(LtsDeviceService ltsDeviceService) {
		this.ltsDeviceService = ltsDeviceService;
	}

	public LtsDeviceSelectionController() {
		setCommandClass(LtsDeviceSelectionFormDTO.class);
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
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			handleRetentionOrder(orderCapture);
			return new ModelAndView(new RedirectView(nextView));
		}
		
		return super.handleRequestInternal(request, response);
	}
	
	
	private void handleRetentionOrder(OrderCaptureDTO orderCapture) {
		String profileDeviceType = null;
		// DEL
		if (StringUtils.isEmpty(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			profileDeviceType = LtsConstant.DEVICE_TYPE_DEL;
		}
		// EYE3A
		else if(LtsConstant.EYE_TYPE_EYE3A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			profileDeviceType = LtsConstant.DEVICE_TYPE_EYE3A;
		}
		else if(LtsConstant.EYE_TYPE_EYE1_5A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			profileDeviceType = LtsConstant.DEVICE_TYPE_EYE1_5_A;
		}
		else if(LtsConstant.EYE_TYPE_EYE2A.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			profileDeviceType = LtsConstant.DEVICE_TYPE_EYE2A;
		}
		else if(LtsConstant.EYE_TYPE_EYE1.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			profileDeviceType = LtsConstant.DEVICE_TYPE_EYE1;
		}
		else if(LtsConstant.EYE_TYPE_EYE2.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			profileDeviceType = LtsConstant.DEVICE_TYPE_EYE2;
		}
		else if(LtsConstant.EYE_TYPE_SAMSUNG.equalsIgnoreCase(orderCapture.getLtsServiceProfile().getExistEyeType())) {
			profileDeviceType = LtsConstant.DEVICE_TYPE_SAMSUNG;
		}
		
		LtsDeviceSelectionFormDTO form = new LtsDeviceSelectionFormDTO();
		form.setSelectedDeviceType(profileDeviceType);
		postSubmit(orderCapture, form);
	}
	
	private void postSubmit(OrderCaptureDTO orderCapture, LtsDeviceSelectionFormDTO form) {
		orderCapture.setLtsBasketSelectionForm(null);
		orderCapture.setLtsBasketServiceForm(null);
		orderCapture.setLtsNowTvServiceForm(null);
		orderCapture.setLtsBasketVasDetailForm(null);
		orderCapture.setLtsDeviceSelectionForm(form);
	}
	
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		LtsDeviceSelectionFormDTO form = (LtsDeviceSelectionFormDTO) command;
		orderCapture.setLtsBasketSelectionForm(null);
		orderCapture.setLtsBasketServiceForm(null);
		orderCapture.setLtsNowTvServiceForm(null);
		orderCapture.setLtsBasketVasDetailForm(null);
		orderCapture.setLtsDeviceSelectionForm(form);
		return new ModelAndView(new RedirectView(nextView));
	}
	
	
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		return new LtsDeviceSelectionFormDTO();
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();

		String locale = RequestContextUtils.getLocale(request).toString();
		
		List<DeviceDetailDTO> eyeDeviceList = ltsDeviceService.getEyeDeviceList(locale);
		filterDeviceList(LtsSessionHelper.getOrderCapture(request), eyeDeviceList);
		referenceData.put("eyeDeviceList", eyeDeviceList);
		return referenceData;
	}
	
	
	private void filterDeviceList(OrderCaptureDTO orderCapture, List<DeviceDetailDTO> eyeDeviceList) {
		String existEyeType = orderCapture.getLtsServiceProfile().getExistEyeType();
		if(StringUtils.equalsIgnoreCase(existEyeType, LtsConstant.EYE_TYPE_EYE2A)){
			for(DeviceDetailDTO device: eyeDeviceList){
				if(StringUtils.equalsIgnoreCase(device.getType(), LtsConstant.DEVICE_TYPE_1020)){
					eyeDeviceList.remove(device);
					break;
				}
			}
		}
		
		//  
		ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getLtsServiceProfile();
		OfferActionLtsDTO[] channelOfferActions = serviceProfile.getChannelOfferActions();
		
		if (ArrayUtils.isNotEmpty(channelOfferActions)) {
			for (OfferActionLtsDTO channelOfferAction : channelOfferActions) {
				if (StringUtils.equalsIgnoreCase(LtsConstant.EYE_TYPE_EYE2A, channelOfferAction.getToProd())) {
					for(DeviceDetailDTO device: eyeDeviceList){
						if(StringUtils.equalsIgnoreCase(device.getType(), LtsConstant.DEVICE_TYPE_1020)){
							eyeDeviceList.remove(device);
							break;
						}
					}
					break;
				}
			}
		}
	}
	
}
