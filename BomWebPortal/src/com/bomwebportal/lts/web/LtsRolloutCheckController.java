package com.bomwebportal.lts.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.service.AddressRolloutService;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.bom.EyeProfileCountService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsRolloutCheckController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected LtsAppointmentService ltsAppointmentService;
	
	private AddressRolloutService addressRolloutService;
	
	private EyeProfileCountService eyeProfileCountService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}

	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}

	public AddressRolloutService getAddressRolloutService() {
		return addressRolloutService;
	}

	public void setAddressRolloutService(AddressRolloutService addressRolloutService) {
		this.addressRolloutService = addressRolloutService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonResult = new JSONObject();
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		setLocale(RequestContextUtils.getLocale(request));
		
		if (orderCapture == null) {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", messageSource.getMessage("lts.ltsRolloutCheck.sessionTimeOut", new Object[] {}, this.locale));
			return new ModelAndView("ajax_ltsrolloutcheck", jsonResult); 
		}
		
		String flat = request.getParameter("flat");
		String floor = request.getParameter("floor");
		String serviceBoundary = request.getParameter("sb");
		int numOfEye = 0;
		
		try {
			
			AddressRolloutDTO addressRollout = addressRolloutService.getAddressRollout(serviceBoundary, flat, floor);
			orderCapture.setNewAddressRollout(addressRollout);
			
			
			if (addressRollout == null) {
				jsonResult.put("status", "false");
				jsonResult.put("errorMsg", messageSource.getMessage("lts.ltsRolloutCheck.cannotFound", new Object[] {serviceBoundary}, this.locale));
				return new ModelAndView("ajax_ltsrolloutcheck", jsonResult);
			}else{
				if(addressRollout.isEyeCoverage()){

					LtsSRDDTO ltsSRD = ltsAppointmentService.getEarliestSRD(orderCapture, true);
					if (ltsSRD != null && ltsSRD.getDate() !=  null){
					    addressRollout.setSuggestedSrdReason(LtsAppointmentHelper.getEarilestSrdReason(ltsSRD, RequestContextUtils.getLocale(request)));
					}
					numOfEye = eyeProfileCountService.getEyeProfileCountByAddr(flat, floor, serviceBoundary, true);
				}
			}
			
			addressRollout.setNumOfEyeProfile(numOfEye);
			
			jsonResult.put("status", "true");
			jsonResult.put("addressRollout", addressRollout);
			jsonResult.put("numOfEye", numOfEye);
			jsonArray.add(jsonResult);
			return new ModelAndView("ajax_ltsrolloutcheck", "jsonArray", jsonArray);
		}
		catch (Exception e) {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", ExceptionUtils.getFullStackTrace(e));
			return new ModelAndView("ajax_ltsrolloutcheck", jsonResult);
		}

		
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

	public EyeProfileCountService getEyeProfileCountService() {
		return eyeProfileCountService;
	}

	public void setEyeProfileCountService(EyeProfileCountService eyeProfileCountService) {
		this.eyeProfileCountService = eyeProfileCountService;
	}

	
	
	

}
