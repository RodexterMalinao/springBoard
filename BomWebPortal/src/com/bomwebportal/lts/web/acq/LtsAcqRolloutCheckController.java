package com.bomwebportal.lts.web.acq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.service.AddressRolloutService;
import com.bomwebportal.lts.service.acq.LtsAcqAppointmentService;
import com.bomwebportal.lts.service.bom.EyeProfileCountService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsAcqRolloutCheckController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected LtsAcqAppointmentService ltsAcqAppointmentService;
	
	private AddressRolloutService addressRolloutService;
	
	private EyeProfileCountService eyeProfileCountService;
	

	public LtsAcqAppointmentService getLtsAcqAppointmentService() {
		return ltsAcqAppointmentService;
	}

	public void setLtsAcqAppointmentService(
			LtsAcqAppointmentService ltsAcqAppointmentService) {
		this.ltsAcqAppointmentService = ltsAcqAppointmentService;
	}

	public AddressRolloutService getAddressRolloutService() {
		return addressRolloutService;
	}

	public void setAddressRolloutService(AddressRolloutService addressRolloutService) {
		this.addressRolloutService = addressRolloutService;
	}

	public EyeProfileCountService getEyeProfileCountService() {
		return eyeProfileCountService;
	}

	public void setEyeProfileCountService(
			EyeProfileCountService eyeProfileCountService) {
		this.eyeProfileCountService = eyeProfileCountService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonResult = new JSONObject();
		
		AcqOrderCaptureDTO orderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		
//		if (orderCapture == null) {
//			jsonResult.put("status", "false");
//			jsonResult.put("errorMsg", "Session timeout, please re-issue order again.");
//			return new ModelAndView("ajax_ltsrolloutcheck", jsonResult); 
//		}
		
		String flat = request.getParameter("flat");
		String floor = request.getParameter("floor");
		String serviceBoundary = request.getParameter("sb");
		int numOfEye = 0;
		
		try {
			
			AddressRolloutDTO addressRollout = addressRolloutService.getAddressRollout(serviceBoundary, flat, floor);
			orderCapture.setAddressRollout(addressRollout);
			
			
			if (addressRollout == null) {
				jsonResult.put("status", "false");
				jsonResult.put("errorMsg", "Cannot found address rollout information [service boundary:"+serviceBoundary+"]");
				return new ModelAndView("ajax_ltsrolloutcheck", jsonResult);
			}else{
				if(addressRollout.isEyeCoverage()){

					LtsSRDDTO ltsSRD = ltsAcqAppointmentService.getEarliestSRD(orderCapture, LtsAppointmentHelper.getToday());
					if (ltsSRD != null && ltsSRD.getDate() !=  null){
					    addressRollout.setSuggestedSrdReason(LtsAppointmentHelper.getEarilestSrdReason(ltsSRD, RequestContextUtils.getLocale(request)));
					}
					numOfEye = eyeProfileCountService.getEyeProfileCountByAddr(flat, floor, serviceBoundary, true);
				}
				
			}
			orderCapture.getAddressRollout().setNumOfEyeProfile(numOfEye);
			
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

	
	
	

}
