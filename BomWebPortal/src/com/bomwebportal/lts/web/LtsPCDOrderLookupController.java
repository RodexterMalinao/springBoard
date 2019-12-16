package com.bomwebportal.lts.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsRelatedPcdOrderService;
import com.bomwebportal.lts.service.order.ImsSbOrderService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsConstant;

public class LtsPCDOrderLookupController  implements Controller {

	private LtsAppointmentService ltsAppointmentService;
	private ImsSbOrderService imsSbOrderService;
	private LtsRelatedPcdOrderService ltsRelatedPcdOrderService;
	
	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}
	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}
	public ImsSbOrderService getImsSbOrderService() {
		return imsSbOrderService;
	}
	public void setImsSbOrderService(ImsSbOrderService imsSbOrderService) {
		this.imsSbOrderService = imsSbOrderService;
	}
	public LtsRelatedPcdOrderService getLtsRelatedPcdOrderService() {
		return ltsRelatedPcdOrderService;
	}
	public void setLtsRelatedPcdOrderService(LtsRelatedPcdOrderService ltsRelatedPcdOrderService) {
		this.ltsRelatedPcdOrderService = ltsRelatedPcdOrderService;
	}
	
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String pcdOrderId = request.getParameter("parm");
		OrderCaptureDTO orderCapture = (OrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_ORDER_CAPTURE);
		try{
		ImsSbOrderDTO pcdOrder = ltsRelatedPcdOrderService.retrievePcdSbOrder
				(pcdOrderId, orderCapture, orderCapture.getLtsCustomerIdentificationForm().isVerified());
		
		if(pcdOrder == null){
			jsonObject.put("success", false);
			
		}else {
			jsonObject.put("success", true);
			if(pcdOrder.isCustNotMatch()){
				jsonObject.put("cusNotMatch", true);
				if(pcdOrder.isAllowConfirmSameCust()){
					jsonObject.put("allowCustConfirm", true);
				}else{
					jsonObject.put("allowCustConfirm", false);
				}
			}
			if(pcdOrder.isIaNotMatch()){
				jsonObject.put("iaNotMatch", true);
				if(pcdOrder.isAllowConfirmSameIa()){
					jsonObject.put("allowIaConfirm", true);
				}else{
					jsonObject.put("allowIaConfirm", false);
				}
			}
			jsonObject.put("fullAddr", pcdOrder.getFullAddress());
			if(StringUtils.isNotBlank(pcdOrder.getAppntStartTime())
					&& StringUtils.isNotBlank(pcdOrder.getAppntEndTime())){
				String apptDate = LtsAppointmentHelper.getDateFromDwfmFormat(pcdOrder.getAppntStartTime());
				String srd = LtsAppointmentHelper.getDateFromDwfmFormat(pcdOrder.getSrvReqDate());
				if(StringUtils.equals(apptDate, srd)){
					//apptdate == srd; normal case
					jsonObject.put("installDate", apptDate);
					String slot = LtsAppointmentHelper.getTimeFromDwfmFormat(pcdOrder.getAppntStartTime());
					slot = slot.concat("-").concat(LtsAppointmentHelper.getTimeFromDwfmFormat(pcdOrder.getAppntEndTime()));
					if(LtsConstant.APPOINTMENT_TIMESLOT_18_TO_20.equals(slot) && "PON".equals(pcdOrder.getTechnology())){
						jsonObject.put("installTime", LtsConstant.APPOINTMENT_TIMESLOT_EVENING);
						jsonObject.put("installTimeType", LtsConstant.APPOINTMENT_TIMESLOT_TYPE_EVENING);
					}else{
						jsonObject.put("installTime", slot);
						jsonObject.put("installTimeType", pcdOrder.getAppntType());
					}
				}else{
					//apptdate != srd; assume blacklist
					jsonObject.put("installDate", srd);
					jsonObject.put("installTime", LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
					jsonObject.put("installTimeType", LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
				}
			}else{
				//apptdate == null; resource shortage
				jsonObject.put("installDate", LtsAppointmentHelper.getDateFromDwfmFormat(pcdOrder.getSrvReqDate()));
				jsonObject.put("installTime", LtsConstant.APPOINTMENT_TIMESLOT_WHOLE);
				jsonObject.put("installTimeType", LtsConstant.APPOINTMENT_TIMESLOT_TYPE_WHOLE);
			}
			
			jsonObject.put("serial", pcdOrder.getSerialNum());
			jsonObject.put("name", pcdOrder.getInstContactName());
			jsonObject.put("smsNum", pcdOrder.getInstSmsNum());
			jsonObject.put("contactNum", pcdOrder.getInstContactNum());

		}
		}catch(NullPointerException npe){
			jsonObject.put("success", false);
			npe.printStackTrace();
			
		}catch(AppRuntimeException are){
			jsonObject.put("success", false);
			are.printStackTrace();
		}
		
		return new ModelAndView("ajax_ltspcdorderlookup", jsonObject);
	}
}
