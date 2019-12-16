package com.bomltsportal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomltsportal.service.email.LtsEmailService;
import com.bomltsportal.service.sms.SMSService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class OrderPostSubmitServiceImpl implements OrderPostSubmitService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private String user = null;
	private SbOrderDTO sbOrder = null;
	private LtsEmailService ltsEmailService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private SMSService smsService;
	private ReportCreationLtsService reportCreationLtsService;
	private CodeLkupCacheService reportSetLkupCacheService;
	
	
	private void initialize(SbOrderDTO pSbOrder, String pUser) {
		this.sbOrder = pSbOrder;
		this.user = pUser;
	}
	
	public String saveAndSendApplicationForm(SbOrderDTO pSbOrder) {
		this.initialize(pSbOrder, BomLtsConstant.USER_ID);
		this.saveAfForm();
		List<AllOrdDocLtsDTO> generatedFormList = ltsOrderDocumentService.getGeneratedFormList(sbOrder);
		ltsOrderDocumentService.saveGeneratedForm(generatedFormList, user);
		return sendSignOffEmail(pSbOrder);
	}
	
	private String sendSignOffEmail(SbOrderDTO pSbOrder) {
		
		String filePathName1 = sbOrder.getOrderId() + BomLtsConstant.EMAIL_FILE_PATTERN;
		String filePathName2 = null;
		String filePathName3 = null;
		
		if(LtsSbHelper.isPortInOrder(sbOrder)){
			filePathName2 = sbOrder.getOrderId() + BomLtsConstant.EMAIL_PORT_IN_FORM_FILE_PATTERN;
		}
		
		return ltsEmailService.sendSignOffEmail(sbOrder, user, filePathName1, filePathName2, filePathName3, false);
	}
	
	private void saveAfForm() {
		
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(BomLtsConstant.RPT_KEY_SBORDER, this.sbOrder);
		inputMap.put(BomLtsConstant.RPT_KEY_LOB, BomLtsConstant.LOB_LTS);
		
		ReportSetDTO rptSet = new ReportSetDTO();
		rptSet.setLob(BomLtsConstant.LOB_LTS);
		rptSet.setChannelId(this.sbOrder.getOrderId().substring(0,1));
		rptSet.setRptSet(ReportCreationLtsService.RPT_SET_SIGNOFF_AF);
		this.reportCreationLtsService.generateReport((ReportSetDTO)this.reportSetLkupCacheService.get(rptSet.getLkupKey()), inputMap);
	}
		
	public void sendSMSToCustomer(String pSmsNum, String pLang, SbOrderDTO pSbOrder, String pSrvType){
		pLang = StringUtils.equals(pLang, BomLtsConstant.LOCALE_CHI) ? "zh" : BomLtsConstant.LOCALE_ENG;
		Locale locale = new Locale(pLang, "");
		smsService.sendSMSMessage(pSmsNum, locale, pSbOrder, pSrvType);
	}
	
	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}
	
	public LtsEmailService getLtsEmailService() {
		return ltsEmailService;
	}

	public void setLtsEmailService(LtsEmailService ltsEmailService) {
		this.ltsEmailService = ltsEmailService;
	}
	
	public SMSService getSmsService() {
		return smsService;
	}

	public void setSmsService(SMSService smsService) {
		this.smsService = smsService;	
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
