package com.bomwebportal.lts.service.report;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.service.ReportDataService;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.EyeAppFormRptDTO;
import com.pccw.rpt.schema.dto.paymentSlip.PaymentSlipRptDTO;
import com.pccw.rpt.schema.dto.recontractForm2017.RecontractForm2017RptDTO;

public class EyeReportDataLtsServiceImpl implements ReportDataService {

	private ReportLtsService upgradeEyeReportLtsService;
	private ReportLtsService delReportLtsService;
	private ReportLtsService del2ndReportLtsService;
	private ReportLtsService nsdReportLtsService;
	private ReportLtsService tdoReportLtsService;
	private ReportLtsService productSpecReportLtsService;
	private ReportLtsService itemSpecReportLtsService;
	private ReportLtsService pipbNsdReportLtsService;
	private ReportLtsService pipbCrfReportLtsService;
	private AmendmentReportLtsService amendmentReportLtsService;
	private ReportLtsService smartWrtyReportLtsService;
	private ReportLtsService cslSimReportLtsService;
	private ReportLtsService concertTicketReportLtsService; //JT20170403
	private ReportLtsService paymentSlipReportDataLtsService;
	private ReportLtsService iguardCareCashReportDataLtsService;  //MB2016081 TC
	private ReportLtsService recontractReportLts2017Service;
	private ReportLtsService weeeReportLtsService;
	
	public void fillReportData(ReportDTO pReportDTO, Map<String, Object>pResourceMap) {
		
		String reportType = (String)pResourceMap.get(LtsBackendConstant.RPT_KEY_DTL_TYPE);
		String locale = (String)pResourceMap.get(ReportDataService.PARAM_RPT_LANGUAGE);
		SbOrderDTO sbOrder = (SbOrderDTO)pResourceMap.get(LtsBackendConstant.RPT_KEY_SBORDER);
		AmendLtsDTO amend= (AmendLtsDTO) pResourceMap.get(LtsBackendConstant.RPT_SRV_TYPE_AMEND_FORM);
		String rptName = (String)pResourceMap.get(LtsBackendConstant.RPT_KEY_NAME);
		boolean isEditable = "Y".equals(pResourceMap.get(LtsBackendConstant.RPT_KEY_EDIT_BUTTON));
		boolean isPrintSignature = StringUtils.equals("Y", (String)pResourceMap.get(LtsBackendConstant.RPT_KEY_ERASE_SIGNATURE));
		boolean isPrintTerms = "Y".equals((String)pResourceMap.get(LtsBackendConstant.RPT_KEY_PRINT_TERMS));
		String action = (String)pResourceMap.get(LtsBackendConstant.RPT_KEY_ACTION);
		
		if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_PORT_IN, reportType)) {
			this.nsdReportLtsService.fillReport(pReportDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_TDO_EYE, reportType)) {
			this.tdoReportLtsService.fillReport(pReportDTO, sbOrder, locale, LtsBackendConstant.RPT_NAME_TDO_EYE , isEditable, isPrintSignature);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_TDO_DEL, reportType)) {
			this.tdoReportLtsService.fillReport(pReportDTO, sbOrder, locale, LtsBackendConstant.RPT_NAME_TDO_2DEL , isEditable, isPrintSignature);
//		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_TDO_0060, reportType)) {
//			this.tdoReportLtsService.fillReport(pReportDTO, sbOrder, locale, LtsBackendConstant.RPT_NAME_TDO_0060 , isEditable, isPrintSignature);
//		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_TDO_NOWTV, reportType)) {
//			this.tdoReportLtsService.fillReport(pReportDTO, sbOrder, locale, LtsBackendConstant.RPT_NAME_TDO_NOWTV , isEditable, isPrintSignature);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_EYE_SPEC, reportType)) {
			this.productSpecReportLtsService.fillReport(pReportDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_ITEM_SPEC, reportType)) {
			this.itemSpecReportLtsService.fillReport(pReportDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_SMART_WARRANTY, reportType)){
			smartWrtyReportLtsService.fillReport(pReportDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_CSL_SIM, reportType)){
			cslSimReportLtsService.fillReport(pReportDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_CONCERT_TICKET, reportType)){
			concertTicketReportLtsService.fillReport(pReportDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_RECONTRACT, reportType)) {	
			RecontractForm2017RptDTO rptDTO = (RecontractForm2017RptDTO) pReportDTO;
			rptDTO.setPrintTermsCondition(isPrintTerms);
			this.recontractReportLts2017Service.fillReport(rptDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_WEEE, reportType)) {	
			this.weeeReportLtsService.fillReport(pReportDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_AMEND_FORM, reportType)) {	
			this.amendmentReportLtsService.fillReport(pReportDTO, sbOrder, locale, rptName, isEditable, isPrintSignature,amend);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_NSD_PIPB, reportType)) {	
			this.pipbNsdReportLtsService.fillReport(pReportDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_CRF_PIPB, reportType)) {	
			this.pipbCrfReportLtsService.fillReport(pReportDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
		//MB2016081 TC
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_IGUARD_CARECASH, reportType)){
			this.iguardCareCashReportDataLtsService.fillReport(pReportDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
//		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_IGUARD_PICS, reportType)){
//			return;
		//MB2016081 TC			
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_PAYMENT_SLIP, reportType)) {
			sbOrder.setPrepayItem((ItemDetailDTO)pResourceMap.get(LtsBackendConstant.RPT_KEY_PREPAYMENT_ITEM));
			sbOrder.setPrepayAcctNum((String)pResourceMap.get(LtsBackendConstant.RPT_KEY_PREPAYMENT_ACCT));
			sbOrder.setSelectedBasket((BasketDetailDTO)pResourceMap.get(LtsBackendConstant.RPT_KEY_PREPAYMENT_SERVICE));
			this.paymentSlipReportDataLtsService.fillReport(pReportDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
			//MB2016081 TC		
		} else if (StringUtils.startsWith(rptName, "pdfTemplate")) {
			return;
			//MB2016081 TC
		} else {
			EyeAppFormRptDTO rptDTO = (EyeAppFormRptDTO)pReportDTO;
			rptDTO.setApplicationNumber(sbOrder.getOrderId());
			rptDTO.setApplicationDate(LtsDateFormatHelper.getDateFromDTOFormat(sbOrder.getAppDate()));
			rptDTO.setPrintTermsCondition(isPrintTerms);
			rptDTO.setAction(action);
			if (StringUtils.equals(LtsBackendConstant.ORDER_PREFIX_DIRECT_SALES, sbOrder.getOrderId().substring(0, 1))
					||StringUtils.equals(LtsBackendConstant.ORDER_PREFIX_DIRECT_SALES_NOW_TV_QA, sbOrder.getOrderId().substring(0, 1))){
				rptDTO.setDirectrSales(true);
				if(StringUtils.equals("PREVIEW_AF", action)){
					rptDTO.setHighlightSectionTitle(true);					
				}
		    }
			
			if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_EYE, reportType)) {
				rptDTO.setEyeApplication(true);
				rptDTO.setResTelApplication(false);
				this.upgradeEyeReportLtsService.fillReport(rptDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);	
			} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_DEL, reportType)) {
				rptDTO.setEyeApplication(false);
				rptDTO.setResTelApplication(true);
				this.delReportLtsService.fillReport(rptDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
			} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_2ND_DEL, reportType)) {
				rptDTO.setEyeApplication(false);
				rptDTO.setResTelApplication(true);
				this.del2ndReportLtsService.fillReport(rptDTO, sbOrder, locale, rptName, isEditable, isPrintSignature);
			}
		}
	}

	public ReportLtsService getUpgradeEyeReportLtsService() {
		return this.upgradeEyeReportLtsService;
	}

	public void setUpgradeEyeReportLtsService(
			ReportLtsService pUpgradeEyeReportLtsService) {
		this.upgradeEyeReportLtsService = pUpgradeEyeReportLtsService;
	}

	public ReportLtsService getDelReportLtsService() {
		return this.delReportLtsService;
	}

	public void setDelReportLtsService(ReportLtsService pDelReportLtsService) {
		this.delReportLtsService = pDelReportLtsService;
	}

	public ReportLtsService getNsdReportLtsService() {
		return nsdReportLtsService;
	}

	public void setNsdReportLtsService(ReportLtsService nsdReportLtsService) {
		this.nsdReportLtsService = nsdReportLtsService;
	}

	public ReportLtsService getTdoReportLtsService() {
		return tdoReportLtsService;
	}

	public void setTdoReportLtsService(ReportLtsService tdoReportLtsService) {
		this.tdoReportLtsService = tdoReportLtsService;
	}

	public ReportLtsService getProductSpecReportLtsService() {
		return productSpecReportLtsService;
	}

	public void setProductSpecReportLtsService(
			ReportLtsService productSpecReportLtsService) {
		this.productSpecReportLtsService = productSpecReportLtsService;
	}
	
	public ReportLtsService getItemSpecReportLtsService() {
		return itemSpecReportLtsService;
	}

	public void setItemSpecReportLtsService(
			ReportLtsService itemSpecReportLtsService) {
		this.itemSpecReportLtsService = itemSpecReportLtsService;
	}

	public ReportLtsService getRecontractReportLts2017Service() {
		return recontractReportLts2017Service;
	}

	public void setRecontractReportLts2017Service(
			ReportLtsService recontractReportLts2017Service) {
		this.recontractReportLts2017Service = recontractReportLts2017Service;
	}

	public ReportLtsService getDel2ndReportLtsService() {
		return del2ndReportLtsService;
	}

	public void setDel2ndReportLtsService(ReportLtsService del2ndReportLtsService) {
		this.del2ndReportLtsService = del2ndReportLtsService;
	}

	public ReportLtsService getPipbNsdReportLtsService() {
		return pipbNsdReportLtsService;
	}

	public void setPipbNsdReportLtsService(ReportLtsService pipbNsdReportLtsService) {
		this.pipbNsdReportLtsService = pipbNsdReportLtsService;
	}

	public ReportLtsService getPipbCrfReportLtsService() {
		return pipbCrfReportLtsService;
	}

	public void setPipbCrfReportLtsService(ReportLtsService pipbCrfReportLtsService) {
		this.pipbCrfReportLtsService = pipbCrfReportLtsService;
	}

	public AmendmentReportLtsService getAmendmentReportLtsService() {
		return amendmentReportLtsService;
	}

	public void setAmendmentReportLtsService(
			AmendmentReportLtsService amendmentReportLtsService) {
		this.amendmentReportLtsService = amendmentReportLtsService;
	}
	public ReportLtsService getSmartWrtyReportLtsService() {
		return smartWrtyReportLtsService;
	}

	public void setSmartWrtyReportLtsService(
			ReportLtsService smartWrtyReportLtsService) {
		this.smartWrtyReportLtsService = smartWrtyReportLtsService;
	}
	
	public ReportLtsService getPaymentSlipReportDataLtsService() {
		return paymentSlipReportDataLtsService;
	}

	public void setPaymentSlipReportDataLtsService(
			ReportLtsService paymentSlipReportDataLtsService) {
		this.paymentSlipReportDataLtsService = paymentSlipReportDataLtsService;
	}

	public ReportLtsService getCslSimReportLtsService() {
		return cslSimReportLtsService;
	}

	public void setCslSimReportLtsService(ReportLtsService cslSimReportLtsService) {
		this.cslSimReportLtsService = cslSimReportLtsService;
	}
	
	//MB2016081 TC
	public ReportLtsService getIguardCareCashReportDataLtsService() {
		return iguardCareCashReportDataLtsService;
	}

	public void setIguardCareCashReportDataLtsService(ReportLtsService iguardCareCashReportDataLtsService) {
		this.iguardCareCashReportDataLtsService = iguardCareCashReportDataLtsService;
	}
	//MB2016081 TC

	public ReportLtsService getConcertTicketReportLtsService() {
		return concertTicketReportLtsService;
	}

	public void setConcertTicketReportLtsService(
			ReportLtsService concertTicketReportLtsService) {
		this.concertTicketReportLtsService = concertTicketReportLtsService;
	}

	public ReportLtsService getWeeeReportLtsService() {
		return weeeReportLtsService;
	}

	public void setWeeeReportLtsService(ReportLtsService weeeReportLtsService) {
		this.weeeReportLtsService = weeeReportLtsService;
	}
	
}
