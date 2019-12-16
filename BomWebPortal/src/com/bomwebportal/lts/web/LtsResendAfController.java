package com.bomwebportal.lts.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.EmailTemplateDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.lts.dto.OrderSendRequestDTO;
import com.bomwebportal.lts.dto.form.LtsResendAfFormDTO;
import com.bomwebportal.lts.dto.form.LtsResendAfFormDTO.Action;
import com.bomwebportal.lts.dto.form.LtsResendAfFormDTO.SearchMethod;
import com.bomwebportal.lts.dto.order.CustomerIguardRegDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.service.LtsEmailService;
import com.bomwebportal.lts.service.LtsSalesInfoService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.service.sms.LtsSmsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;


public class LtsResendAfController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private final String formView = "ltsresendaf";
	private final String commandName = "ltsResendAfCmd";
	
	protected LtsSalesInfoService ltsSalesInfoService;
	protected LtsEmailService ltsEmailService;
	protected LtsSmsService ltsSmsService;
	protected OrderEsignatureService orderEsignatureService;
	protected OrderRetrieveLtsService orderRetrieveLtsService;
	protected OrdEmailReqService ordEmailReqService;
	protected CodeLkupCacheService ltsSendLPCutoffLkupCacheService;
	protected ReportCreationLtsService reportCreationLtsService;
	protected CodeLkupCacheService reportSetLkupCacheService;
	
	private String reportServerPath; //dataFilePath
	private Locale locale;
	private MessageSource messageSource;


	public CodeLkupCacheService getLtsSendLPCutoffLkupCacheService() {
		return ltsSendLPCutoffLkupCacheService;
	}

	public void setLtsSendLPCutoffLkupCacheService(
			CodeLkupCacheService ltsSendLPCutoffLkupCacheService) {
		this.ltsSendLPCutoffLkupCacheService = ltsSendLPCutoffLkupCacheService;
	}

	public String getReportServerPath() {
		return reportServerPath;
	}

	public void setReportServerPath(String reportServerPath) {
		this.reportServerPath = reportServerPath;
	}

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
	}

	public LtsSmsService getLtsSmsService() {
		return ltsSmsService;
	}

	public void setLtsSmsService(LtsSmsService ltsSmsService) {
		this.ltsSmsService = ltsSmsService;
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}

	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}

	public LtsSalesInfoService getLtsSalesInfoService() {
		return ltsSalesInfoService;
	}

	public void setLtsSalesInfoService(LtsSalesInfoService ltsSalesInfoService) {
		this.ltsSalesInfoService = ltsSalesInfoService;
	}

	public LtsEmailService getLtsEmailService() {
		return ltsEmailService;
	}

	public void setLtsEmailService(LtsEmailService ltsEmailService) {
		this.ltsEmailService = ltsEmailService;
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

	public LtsResendAfController() {
		setCommandClass(LtsResendAfFormDTO.class);
		setCommandName(commandName);
		setFormView(formView);
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		
		LtsResendAfFormDTO form = new LtsResendAfFormDTO();
		setLocale(RequestContextUtils.getLocale(request));
		
		return form;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		LtsResendAfFormDTO form = (LtsResendAfFormDTO) command;
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		
		ModelAndView modelAndView = new ModelAndView(formView, referenceData(request));
		
		switch (form.getFormAction()) {
		case CLEAR:
			modelAndView.addObject(commandName, new LtsResendAfFormDTO());	
			break;
		case SEARCH:
			performSearch(request, form, bomSalesUser);
			modelAndView.addObject(commandName, form);
			break;
		case PREVIEW:
			performPreview(request, form);
			modelAndView.addObject(commandName, form);
			break;
		case SEND:
			performSend(request, form);
			performPreview(request, form);
			modelAndView.addObject(commandName, form);
			break;
		default:
			break;
		}
		
		return modelAndView;
	}
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		List<String> teamCdList = null;
		if (LtsConstant.SALES_ROLE_SALES_MANAGER.equals(bomSalesUser.getCategory())
				|| LtsConstant.SALES_ROLE_SUPPORT.equals(bomSalesUser.getCategory())) {
			teamCdList = ltsSalesInfoService.getTeamCdListByChannelCd(bomSalesUser.getChannelCd());
		}
		else if (LtsConstant.SALES_ROLE_MANAGER.equals(bomSalesUser.getCategory())){
			teamCdList = new ArrayList<String>();
			teamCdList.add(bomSalesUser.getShopCd());
		}
		
		if (teamCdList != null && !teamCdList.isEmpty()) {
			referenceData.put("teamCdList", teamCdList);	
		}
		return referenceData;
	}
	
	private void performSend(HttpServletRequest request, LtsResendAfFormDTO form) {
		
		SbOrderDTO sbOrder = orderRetrieveLtsService.retrieveSbOrder(form.getSearchOrderId(), true);
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		String templateId = null;
		String result = null;
		boolean isIguard = false;
		
		templateId = form.getEmailTemplateId();
		if(LtsConstant.EMAIL_TEMPLATE_IGUARD_CUST_EMAIL.equals(templateId)
				|| LtsConstant.EMAIL_TEMPLATE_IGUARD_ADMIN_EMAIL.equals(templateId))
		{
			isIguard = true;
		}
		
		if (!isAfExist(sbOrder) && !LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType()) && !isIguard) {
			request.setAttribute("result", messageSource.getMessage("lts.ltspayment.afNotFound", new Object[] {}, this.locale));
			form.setFormAction(Action.PREVIEW);
			return;
		}

		if (StringUtils.equals(LtsConstant.SEND_METHOD_EMAIL, form.getResendMethod())) {
			if(!isIguard)
			{
				result = ltsEmailService.sendSignOffEmail(sbOrder, StringUtils.trim(form.getResendEmail()), bomSalesUser.getUsername());
			}
			else
			{
				CustomerIguardRegDTO custIguard = new CustomerIguardRegDTO();
				custIguard = sbOrder.getCustIguardReg();			
				if(custIguard!=null)
				{
					boolean isIguardAfExist = false;
					boolean isIguardPicsAfExist = false;
					if(custIguard.getCarecashInd().equalsIgnoreCase("I"))
					{
						isIguardAfExist = isAfExist(sbOrder, LtsConstant.IGUARD_FORM_AF_NAME);
						if(!isIguardAfExist)
						{
							saveForms(sbOrder, "IGCC_AF");
						}
						
						isIguardPicsAfExist = isAfExist(sbOrder, LtsConstant.IGUARD_PICS_AF_NAME);					
						if(!isIguardPicsAfExist)
						{
							saveForms(sbOrder, "IGCP_AF");
						}
						
						if(LtsConstant.EMAIL_TEMPLATE_IGUARD_CUST_EMAIL.equals(templateId))
						{
							result = ltsEmailService.sendCareCashEmailToCustomer(sbOrder, StringUtils.trim(form.getResendEmail()), bomSalesUser.getUsername());
						}
						else
						{
							result = ltsEmailService.sendCareCashEmailToAdmin(sbOrder, bomSalesUser.getUsername());
						}
						
					}
					else
					{
						result = messageSource.getMessage("lts.ltspayment.noIguardReg", new Object[] {}, this.locale);
					}
					
				}
				else
				{
					result = messageSource.getMessage("lts.ltspayment.failIguardObj", new Object[] {}, this.locale);
				}
			}
		}
		else if (StringUtils.equals(LtsConstant.SEND_METHOD_SMS, form.getResendMethod())) {
			templateId = form.getSmsTemplateId();
			result = ltsSmsService.sendSignOffSms(sbOrder, StringUtils.trim(form.getResendSms()), bomSalesUser.getUsername());
		}
		else if (StringUtils.equals(LtsConstant.SEND_METHOD_PAPER, form.getResendMethod())) {
			result = ltsEmailService.createLetterPrintReq(sbOrder, bomSalesUser.getUsername());
			if (OrderEsignatureService.EmailReqResult.SUCCESS.toString().equals(result)) {
				result = messageSource.getMessage("lts.ltspayment.letterPrintReq", new Object[] {}, this.locale);
			}
		}
		
		if (StringUtils.equals(LtsConstant.SEND_METHOD_EMAIL, form.getResendMethod()) 
				|| StringUtils.equals(LtsConstant.SEND_METHOD_SMS, form.getResendMethod())) {
			if (OrderEsignatureService.EmailReqResult.SUCCESS.toString().equals(result)) {
				List<OrdEmailReqDTO> latestList = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(sbOrder.getOrderId(), templateId);
				if (latestList != null && !latestList.isEmpty()) {
					request.setAttribute("sentDate", Utility.date2String(latestList.get(latestList.size() - 1).getSentDate(), BomWebPortalConstant.DATE_FORMAT + " HH:mm:ss"));
				}
			}
		}
		
		request.setAttribute("result", result);
		form.setFormAction(Action.PREVIEW);
	}
	
	private void saveForms(SbOrderDTO sbOrder, String action) {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(LtsConstant.RPT_KEY_SBORDER, sbOrder);
		inputMap.put(LtsConstant.RPT_KEY_LOB, LtsConstant.LOB_LTS);		
		ReportSetDTO rptSet = new ReportSetDTO();
		rptSet.setLob(LtsConstant.LOB_LTS);
		rptSet.setChannelId(sbOrder.getOrderId().substring(0,1));
		rptSet.setRptSet(action);
		reportCreationLtsService.generateReport((ReportSetDTO)reportSetLkupCacheService.get(rptSet.getLkupKey()), inputMap);
	}
	
	private void performPreview(HttpServletRequest request, LtsResendAfFormDTO form) {
		
		String orderId_AfType = form.getPreviewOrderId();
		//String orderId = form.getPreviewOrderId();
		String[] tempArray = null;
		
		if(orderId_AfType==null)
		{
			orderId_AfType = form.getSearchOrderId();
		}
		
		if(orderId_AfType.contains("_"))
		{
			tempArray = orderId_AfType.split("_");
		}
		
		String orderId = "";
		String afType = "";
		boolean isIguard = false;
		
		if(tempArray != null && tempArray.length>1)
		{
			orderId = tempArray[0];
			afType = tempArray[1];
		}
		else
		{
			orderId = orderId_AfType;
		}
		//form.setPreviewOrderId(orderId);
		form.setSearchOrderId(orderId);
		
		List<OrderSendRequestDTO> orderSendRequestListTemp;

		try {
			List<OrderSendRequestDTO> orderSendRequestList = ltsEmailService.getCCOrderSendRequest(orderId, null, null, null, null, null, null);
			
			if (orderSendRequestList != null && !orderSendRequestList.isEmpty()) {
				
				SbOrderDTO sbOrder = orderRetrieveLtsService.retrieveSbOrder(orderId, true);
				if (sbOrder == null) {
					request.setAttribute("resendException", messageSource.getMessage("lts.ltspayment.failRreSbOrder", new Object[] {orderId}, this.locale));
					return;
				}
				request.setAttribute("isPaperShow", isPaperModeShow(sbOrder));
				request.setAttribute("isDisconnectOrder", isDisconnectOrder(sbOrder));
				
				String emailTemplateId = LtsSbOrderHelper.getEmailTemplateId(sbOrder, false);
				if(LtsConstant.EMAIL_TEMPLATE_IGUARD_CUST_EMAIL.equals(afType)
						|| LtsConstant.EMAIL_TEMPLATE_IGUARD_ADMIN_EMAIL.equals(afType))
				{
					emailTemplateId = afType;
					isIguard = true;					
				}
				
				EmailTemplateDTO emailTemplate = orderEsignatureService
						.getEmailTemplateDTO(emailTemplateId, LtsConstant.LOB_LTS, EsigEmailLang.valueOf(sbOrder.getEsigEmailLang()));
				if (emailTemplate != null) {
					String emailSubject = orderEsignatureService.formatSubjectLts(emailTemplate.getTemplateSubject(), sbOrder);
					String emailContent = orderEsignatureService.formatContentLts(emailTemplate.getTemplateContent(), sbOrder, emailTemplate.getTemplateId());
					form.setEmailTemplateId(emailTemplateId);
					request.setAttribute("emailSubject", emailSubject);
					request.setAttribute("emailContent", emailContent);	
				}
				
				String smsTemplateId = LtsSbOrderHelper.getSmsTemplateId(sbOrder);
				EmailTemplateDTO smsTemplate = orderEsignatureService
						.getEmailTemplateDTO(smsTemplateId, LtsConstant.LOB_LTS, EsigEmailLang.valueOf(sbOrder.getEsigEmailLang()));
				if (smsTemplate != null) {
					String smsSubject = smsTemplate.getTemplateSubject();
					String smsContent = ltsSmsService.formatMsgContent(sbOrder, smsTemplate.getTemplateContent());
					form.setSmsTemplateId(smsTemplateId);
					request.setAttribute("smsSubject", smsSubject);
					request.setAttribute("smsContent", smsContent);
				}
				
				ServiceDetailDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
				form.setBillAddress(ltsEmailService.getBomBillAddress(ltsServiceDetail));
				
				for (OrderSendRequestDTO orderSendRequest : orderSendRequestList) {
					if (LtsConstant.SEND_METHOD_EMAIL.equals(orderSendRequest.getMethod())) {
						if (StringUtils.isBlank(form.getResendEmail())) {
							if(orderSendRequest.getTemplateId().equalsIgnoreCase(emailTemplateId))
							{
								form.setResendEmail(StringUtils.trim(orderSendRequest.getEmail()));
							}								
						}
					}
					if (LtsConstant.SEND_METHOD_SMS.equals(orderSendRequest.getMethod())) {
						if (StringUtils.isBlank(form.getResendSms())) {
							form.setResendSms(orderSendRequest.getSmsNo());	
						}
					}
				}
				
				// Filter duplicate seq record
				orderSendRequestListTemp = new ArrayList<OrderSendRequestDTO>();
				String dulSeqNo = "";
				for (OrderSendRequestDTO orderSendRequest : orderSendRequestList) {
					if(!orderSendRequest.getSeqNo().equals(dulSeqNo)){
						orderSendRequestListTemp.add(orderSendRequest);
					}
					dulSeqNo = orderSendRequest.getSeqNo();
				}
				request.setAttribute("orderSendRequestList", orderSendRequestListTemp);
			}
			request.setAttribute("isIguard", isIguard);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			request.setAttribute("resendException", ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	private void performSearch(HttpServletRequest request, LtsResendAfFormDTO form, BomSalesUserDTO bomSalesUser) {
		String staffId = null;
		String orderId = null;
		String requestDate = null;
		String teamCd = null;
		String[] channelId = null;
		if (LtsConstant.CHANNEL_CD_DIRECT_SALES_QC.equals(bomSalesUser.getChannelCd())){
			channelId = new String[]{LtsConstant.CHANNEL_ID_DIRECT_SALES, LtsConstant.CHANNEL_ID_DIRECT_SALES_NOW_TV_QA};
		}else{
			if (LtsConstant.SALES_ROLE_FRONTLINE.equals(bomSalesUser.getCategory())) {
				staffId = bomSalesUser.getUsername();
			}
			else {
				teamCd = form.getTeamCd();
			}			
		}
		if (SearchMethod.ORDER_ID == form.getSearchMethod()) {
			orderId = StringUtils.trim(form.getSearchOrderId()).toUpperCase();
		}
		if (SearchMethod.REQ_DATE == form.getSearchMethod()) {
			requestDate = form.getSearchRequestDate();
		}
		List<OrderSendRequestDTO> orderSendRequestList = ltsEmailService.getCCOrderSendRequest(orderId, requestDate, staffId, teamCd, null, null, channelId);
		request.setAttribute("orderSendRequestList", orderSendRequestList);
		
	}
	
	private boolean isAfExist(SbOrderDTO sbOrder) {
		String pathname = reportServerPath + File.separator + sbOrder.getOrderId() + File.separator + sbOrder.getOrderId() + LtsConstant.EMAIL_FILE_PATTERN;
		File file = new File(pathname);
		return file.exists();
	}
	
	private boolean isAfExist(SbOrderDTO sbOrder, String afName) {
		String pathname = reportServerPath + File.separator + sbOrder.getOrderId() + File.separator + sbOrder.getOrderId() + afName + ".pdf";
		File file = new File(pathname);
		return file.exists();
	}
	
	private boolean isPaperModeShow(SbOrderDTO sbOrder){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date paperModeAvailableDate = null;
		Date signOffDate = null;
		try {
			paperModeAvailableDate = sdf.parse((String)ltsSendLPCutoffLkupCacheService.get(LtsConstant.LETTER_PRINT_PAPER_MODE_AVAILABLE_DATE_LKUP));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			signOffDate = sdf.parse(sbOrder.getSignOffDate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return signOffDate.compareTo(paperModeAvailableDate) > 0 ? true : false;
	}
	
	private boolean isDisconnectOrder(SbOrderDTO sbOrder){
		return LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType()) ? true : false;
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
}
