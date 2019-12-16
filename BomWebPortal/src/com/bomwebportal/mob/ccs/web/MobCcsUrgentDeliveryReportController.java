package com.bomwebportal.mob.ccs.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.mob.ccs.dto.UrgentDeliveryReportDTO;
import com.bomwebportal.mob.ccs.dto.ui.UrgentDeliveryReportUI;
import com.bomwebportal.mob.ccs.service.MobCcsUrgentDeliveryReportService;

public class MobCcsUrgentDeliveryReportController extends SimpleFormController{


	
	private MobCcsUrgentDeliveryReportService mobCcsUrgentDeliveryReportService;
	
	public MobCcsUrgentDeliveryReportService getMobCcsUrgentDeliveryReportService() {
		return mobCcsUrgentDeliveryReportService;
	}
	public void setMobCcsUrgentDeliveryReportService(
			MobCcsUrgentDeliveryReportService mobCcsUrgentDeliveryReportService) {
		this.mobCcsUrgentDeliveryReportService = mobCcsUrgentDeliveryReportService;
	}
	
	public Object formBackingObject(HttpServletRequest request) {
		//return new UrgentDeliveryReportUI();
		UrgentDeliveryReportUI form = new UrgentDeliveryReportUI();
		form.setOrderId(request.getParameter("orderId"));
		form.setProcessDate(request.getParameter("processDate"));
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("MobCcsAuthorizeController ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		String orderId = request.getParameter("orderId");
		String processDate = request.getParameter("processDate");
		if (StringUtils.isNotBlank(orderId)&& StringUtils.isNotBlank(processDate)) {
			Date processingDate = com.bomwebportal.util.Utility.string2Date(processDate);
			List<UrgentDeliveryReportDTO> list = this.mobCcsUrgentDeliveryReportService.getUrgentDeliveryReportDTOALL(orderId, processingDate);
			referenceData.put("results", list);
		}
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		ModelAndView modelAndView;
		UrgentDeliveryReportUI form = (UrgentDeliveryReportUI) command;
		/* 
		   The export csv function is done by the jsp but not the program, 
		   for the mobccsurgentdeliveryreport.jsp, it is only used to
		   display a table that same as the csv.
		   //request.getParameter("Export CSV") == null || 
		*/
		if (!form.isExportCsv()){
			modelAndView = new ModelAndView("mobccsurgentdeliveryreport", model);
		} else {
			modelAndView = new ModelAndView("mobccsurgentdeliveryreport_csv", model);
		}
		

//		if (form.getOrderId()!=null){
//			export_orderId = form.getOrderId();
//		}
		List<UrgentDeliveryReportDTO> results = this.mobCcsUrgentDeliveryReportService.getUrgentDeliveryReportDTOALL(form.getOrderId(), com.bomwebportal.util.Utility.string2Date(form.getProcessDate()));

		modelAndView.addObject("results", results);
		return modelAndView;

	}
	
	
	private boolean isEmpty(List<?> list) {
			return list == null || list.isEmpty();
	}
	
	//static String export_orderId = "";
}
