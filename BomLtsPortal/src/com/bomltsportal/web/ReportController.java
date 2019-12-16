package com.bomltsportal.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomltsportal.util.uENC;
import com.bomwebportal.dto.report.ReportOutputDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.service.CodeLkupCacheService;

public class ReportController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private OrderRetrieveLtsService orderRetrieveLtsService = null;
	private ReportCreationLtsService reportCreationLtsService = null;
	private CodeLkupCacheService reportSetLkupCacheService = null;


	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String key = request.getParameter("key");
		String action = request.getParameter("action");
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		String orderId = request.getParameter("orderId");
		String editButton = request.getParameter("editButton");
		
		if (StringUtils.isNotBlank(key)) {
			orderId = uENC.decAES(BomLtsConstant.URL_PARM_ENC_KEY, key);
			action = ReportCreationLtsService.RPT_SET_SMS_AF;
		} 
		else if (orderCapture != null && orderCapture.getSbOrder() != null) {
			orderId = orderCapture.getSbOrder().getOrderId();
		} 
		else if(StringUtils.isBlank(orderId)){
			return SessionHelper.getSessionTimeOutView();
		}
		SbOrderDTO sbOrder = this.orderRetrieveLtsService.retrieveSbOrder(orderId, true);
		
		try {
			ReportSetDTO rptSet = new ReportSetDTO();
			rptSet.setLob(LtsBackendConstant.LOB_LTS);
			rptSet.setChannelId(sbOrder.getOrderId().substring(0,1));
			rptSet.setRptSet(action);
			rptSet = (ReportSetDTO)this.reportSetLkupCacheService.get(rptSet.getLkupKey());
			
			Map<String,Object> inputMap = new HashMap<String,Object>();
			inputMap.put(BomLtsConstant.RPT_KEY_SBORDER, sbOrder);
			inputMap.put(BomLtsConstant.RPT_KEY_LOB, BomLtsConstant.LOB_LTS);
			inputMap.put(BomLtsConstant.RPT_KEY_EDIT_BUTTON, editButton);
			ReportOutputDTO rptOut = this.reportCreationLtsService.generateReport(rptSet, inputMap);
			String fileName = sbOrder.getOrderId() + ("ENG".equals(sbOrder.getLangPref()) ?  "_EN." : "_CHI.") + "pdf";

			response.setContentType("application/pdf");
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Type", "application/octet-stream");
			response.addHeader("Content-disposition","attachment; filename=" + "\"" + fileName + "\"");
			response.setHeader("Cache-Control", "private");			
			response.getOutputStream().write(rptOut.getOutputFileStream().getByteArray());
			response.getOutputStream().flush();		
		} catch (Exception e) {
			logger.error("Exception in ReportController: " + ExceptionUtils.getFullStackTrace(e));
			response.resetBuffer();
			response.setContentType("text/html");
			response.setHeader("Content-Disposition", null);
			response.getWriter().println("Exception in ReportController: " + ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}
	
	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public ReportCreationLtsService getReportCreationLtsService() {
		return reportCreationLtsService;
	}

	public void setReportCreationLtsService(
			ReportCreationLtsService reportCreationLtsService) {
		this.reportCreationLtsService = reportCreationLtsService;
	}

	public CodeLkupCacheService getReportSetLkupCacheService() {
		return reportSetLkupCacheService;
	}

	public void setReportSetLkupCacheService(
			CodeLkupCacheService reportSetLkupCacheService) {
		this.reportSetLkupCacheService = reportSetLkupCacheService;
	}
}