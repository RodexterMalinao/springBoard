package com.bomwebportal.lts.web;

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

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.report.ReportOutputDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.LtsSignatureService;
import com.bomwebportal.lts.service.order.AmendRetrieveService;
import com.bomwebportal.lts.service.order.OrderRecallService;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.util.uENC;

import com.bomwebportal.lts.dto.form.LtsPaymentFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;

public class LtsReportController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());

	protected OrderRecallService orderRecallService;
	private ReportCreationLtsService reportCreationLtsService = null;
	private CodeLkupCacheService reportSetLkupCacheService = null;
	protected AmendRetrieveService amendRetrieveService;
	private LtsSignatureService ltsSignatureService = null;
	
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String key = request.getParameter("key");
		String action = request.getParameter("ltsRptAction");
		String seq = request.getParameter("seq");
		String ordId = request.getParameter("orderId");
		String editButton = request.getParameter("editButton");
		OrderCaptureDTO orderCapture = null;
		
		BomSalesUserDTO dummyUser = new BomSalesUserDTO();
		dummyUser.setUsername("SELFSERVE");
		
		if(StringUtils.isNotBlank(ordId)){
			orderCapture = this.orderRecallService.recallOrder(ordId, true, dummyUser);	
		}
		else if(StringUtils.isNotBlank(key)) {
			String orderId = uENC.decAES(LtsConstant.URL_PARM_ENC_KEY, key);
			if (StringUtils.equals(LtsConstant.REPORT_ACTION_TYPE_SMS_PREPAYMENT, action)) {
				action = ReportCreationLtsService.RPT_SET_PRINT_PS;
			} else {
				action = ReportCreationLtsService.RPT_SET_SMS_AF;
			}
			orderCapture = this.orderRecallService.recallOrder(orderId, true, dummyUser);	
		}else {
			orderCapture =  LtsSessionHelper.getOrderCapture(request);	
		}
		
		if (orderCapture == null || orderCapture.getSbOrder() == null || action == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		sbOrder.setSignatures(ltsSignatureService.getOrderSignatures(sbOrder.getOrderId()));
		
		if (orderCapture.getLtsDigitalSignatureForm() != null) {
			sbOrder.setSignatures(LtsSbOrderHelper.convertSignature(orderCapture.getLtsDigitalSignatureForm().getSignatureList()));
		}
		try {
			ReportSetDTO rptSet = new ReportSetDTO();
			rptSet.setLob(LtsBackendConstant.LOB_LTS);
			rptSet.setChannelId(sbOrder.getOrderId().substring(0,1));
			rptSet.setRptSet(action);
			rptSet = (ReportSetDTO)this.reportSetLkupCacheService.get(rptSet.getLkupKey());
			
			
			Map<String,Object> inputMap = new HashMap<String,Object>();
			inputMap.put(LtsConstant.RPT_KEY_SBORDER, sbOrder);
			inputMap.put(LtsConstant.RPT_KEY_LOB, LtsConstant.LOB_LTS);
			inputMap.put(LtsConstant.RPT_KEY_EDIT_BUTTON, editButton);
			inputMap.put("ACTION", action); //JT2017062
			if(!StringUtils.isBlank(seq)){
				inputMap.put(LtsBackendConstant.FILE_SEQ, seq);
			}
			
			String fileName = "";
			
			if(StringUtils.equals(action, "PRINT_PS")){
				LtsPaymentFormDTO prepayForm = orderCapture.getLtsPaymentForm();
				if(prepayForm != null){
					if(prepayForm.getPrePayItem() != null && prepayForm.getPrePayItem().isSelected()){
						inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ACCT, prepayForm.getExistBillAccNum());
						inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ITEM, prepayForm.getPrePayItem());
						inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_SERVICE, orderCapture.getSelectedBasket());
					}
				}
				
				fileName = sbOrder.getOrderId() + ("ENG".equals(sbOrder.getLangPref()) ?  "_PS_EN." : "_PS_CHI.") + "pdf";
			}
			else{
				fileName = sbOrder.getOrderId() + ("ENG".equals(sbOrder.getLangPref()) ?  "_EN." : "_CHI.") + "pdf";
			}
			
			ReportOutputDTO rptOut = this.reportCreationLtsService.generateReport(rptSet, inputMap);						
			response.setContentType("application/pdf");
			response.addHeader("Content-disposition","attachment; filename=" + fileName);
			response.setHeader("Cache-Control", "private");			
			response.getOutputStream().write(rptOut.getOutputFileStream().getByteArray());
			response.getOutputStream().flush();		
		} catch (Exception e) {
			logger.error("Exception in ReportController (SBID: " + sbOrder.getOrderId() + "): " + ExceptionUtils.getFullStackTrace(e));
			response.resetBuffer();
			response.setContentType("text/html");
			response.setHeader("Content-Disposition", null);
			response.getWriter().println("Exception in LtsReportController: " + ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}
	
	public OrderRecallService getOrderRecallService() {
		return orderRecallService;
	}

	public void setOrderRecallService(OrderRecallService orderRecallService) {
		this.orderRecallService = orderRecallService;
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

	public AmendRetrieveService getAmendRetrieveService() {
		return amendRetrieveService;
	}

	public void setAmendRetrieveService(AmendRetrieveService amendRetrieveService) {
		this.amendRetrieveService = amendRetrieveService;
	}
	
	public LtsSignatureService getLtsSignatureService() {
		return ltsSignatureService;
	}

	public void setLtsSignatureService(LtsSignatureService ltsSignatureService) {
		this.ltsSignatureService = ltsSignatureService;
	}
}