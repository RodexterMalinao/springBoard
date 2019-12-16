package com.bomwebportal.lts.web.acq;

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
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.LtsSignatureService;
import com.bomwebportal.lts.service.order.acq.LtsAcqOrderRecallService;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.util.uENC;

import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;

public class LtsAcqReportController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());

	protected LtsAcqOrderRecallService ltsAcqOrderRecallService;
	private ReportCreationLtsService reportCreationLtsService = null;
	private CodeLkupCacheService reportSetLkupCacheService = null;
	private LtsSignatureService ltsSignatureService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String key = request.getParameter("key");
		String action = request.getParameter("ltsRptAction");
		AcqOrderCaptureDTO acqOrderCapture = null;
		
		if (StringUtils.isNotBlank(key)) {
			String orderId = uENC.decAES(LtsConstant.URL_PARM_ENC_KEY, key);
			if (StringUtils.equals(LtsConstant.REPORT_ACTION_TYPE_SMS_PREPAYMENT, action)) {
				action = ReportCreationLtsService.RPT_SET_PRINT_PS;
			} else {
				action = ReportCreationLtsService.RPT_SET_SMS_AF;
			}
			acqOrderCapture = this.ltsAcqOrderRecallService.recallOrder(orderId, true, new BomSalesUserDTO());	
		} else {
			acqOrderCapture =  LtsSessionHelper.getAcqOrderCapture(request);	
		}		
		
		if (acqOrderCapture == null || acqOrderCapture.getSbOrder() == null || action == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
		sbOrder.setSignatures(ltsSignatureService.getOrderSignatures(sbOrder.getOrderId()));
		
		try {
			ReportSetDTO rptSet = new ReportSetDTO();
			rptSet.setLob(LtsBackendConstant.LOB_LTS);
			rptSet.setChannelId(sbOrder.getOrderId().substring(0,1));
			rptSet.setRptSet(action);
			rptSet = (ReportSetDTO)this.reportSetLkupCacheService.get(rptSet.getLkupKey());
			
			Map<String,Object> inputMap = new HashMap<String,Object>();
			inputMap.put(LtsConstant.RPT_KEY_SBORDER, sbOrder);
			inputMap.put(LtsConstant.RPT_KEY_LOB, LtsConstant.LOB_LTS);
			inputMap.put("ACTION", action);
		
			String fileName = "";
			if(StringUtils.equals(action, "PRINT_PS")){
				LtsAcqPaymentMethodFormDTO prepayForm = acqOrderCapture.getLtsAcqPaymentMethodFormDTO();
				if(prepayForm != null){
					for(PaymentMethodDtl prepayDtl : prepayForm.getPaymentMethodDtlList()){
						if(prepayDtl.getAutopayPrePayItem() != null){
							inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ACCT, prepayDtl.getAcctNum());
							inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ITEM, prepayDtl.getAutopayPrePayItem());
							inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_SERVICE, acqOrderCapture.getSelectedBasket());
							break;
						}
						if(prepayDtl.getCashPrePayItem() != null){
							inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ACCT, prepayDtl.getAcctNum());
							inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ITEM, prepayDtl.getCashPrePayItem());
							inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_SERVICE, acqOrderCapture.getSelectedBasket());
							break;
						}
						if(prepayDtl.getCreditCardPrePayItem() != null){
							inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ACCT, prepayDtl.getAcctNum());
							inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_ITEM, prepayDtl.getCreditCardPrePayItem());
							inputMap.put(LtsConstant.RPT_KEY_PREPAYMENT_SERVICE, acqOrderCapture.getSelectedBasket());
							break;
						}
					}
				}
				
				fileName = sbOrder.getOrderId() + ("ENG".equals(sbOrder.getLangPref()) ?  "_PS_EN." : "_PS_CHI.") + "pdf";
			}
			else{
				fileName = sbOrder.getOrderId() + ("ENG".equals(sbOrder.getLangPref()) ?  "_EN." : "_CHI.") + "pdf";
			}
			
			ReportOutputDTO rptOut = this.reportCreationLtsService.generateReport(rptSet, inputMap);
			response.setContentType("pdf");
			response.addHeader("Content-disposition","attachment; filename=" + fileName);
			response.setHeader("Cache-Control", "private");			
			response.getOutputStream().write(rptOut.getOutputFileStream().getByteArray());
			response.getOutputStream().flush();		
		} catch (Exception e) {
			logger.error("Exception in ReportController (SBID: " + sbOrder.getOrderId() + "): " + ExceptionUtils.getFullStackTrace(e));
			response.resetBuffer();
			response.setContentType("text/html");
			response.setHeader("Content-Disposition", null);
			response.getWriter().println("Exception in LtsAcqReportController: " + ExceptionUtils.getFullStackTrace(e));
		}
		return null;
	}

	/**
	 * @return the ltsAcqOrderRecallService
	 */
	public LtsAcqOrderRecallService getLtsAcqOrderRecallService() {
		return ltsAcqOrderRecallService;
	}

	/**
	 * @param ltsAcqOrderRecallService the ltsAcqOrderRecallService to set
	 */
	public void setLtsAcqOrderRecallService(
			LtsAcqOrderRecallService ltsAcqOrderRecallService) {
		this.ltsAcqOrderRecallService = ltsAcqOrderRecallService;
	}

	/**
	 * @return the reportCreationLtsService
	 */
	public ReportCreationLtsService getReportCreationLtsService() {
		return reportCreationLtsService;
	}

	/**
	 * @param reportCreationLtsService the reportCreationLtsService to set
	 */
	public void setReportCreationLtsService(
			ReportCreationLtsService reportCreationLtsService) {
		this.reportCreationLtsService = reportCreationLtsService;
	}

	/**
	 * @return the reportSetLkupCacheService
	 */
	public CodeLkupCacheService getReportSetLkupCacheService() {
		return reportSetLkupCacheService;
	}

	/**
	 * @param reportSetLkupCacheService the reportSetLkupCacheService to set
	 */
	public void setReportSetLkupCacheService(
			CodeLkupCacheService reportSetLkupCacheService) {
		this.reportSetLkupCacheService = reportSetLkupCacheService;
	}

	public LtsSignatureService getLtsSignatureService() {
		return ltsSignatureService;
	}

	public void setLtsSignatureService(LtsSignatureService ltsSignatureService) {
		this.ltsSignatureService = ltsSignatureService;
	}	
	
}