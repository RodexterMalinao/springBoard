package com.bomwebportal.lts.service.sms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.bomwebportal.dto.EmailTemplateDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.service.LtsEmailService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.uENC;
import com.pccw.netvigator.NetvigatorSMSInterfaceServiceLocator;

public class LtsSmsServiceImpl implements LtsSmsService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private static final String SMS_PREFIX = "852";
	private static final String APP_ID = "sbonline_lts";
	protected CodeLkupCacheService sendSmsLkupCacheService;
	private String endpoint;
	private String ltsRptUrl;
	private ResourceBundleMessageSource ltsMessageSource;
	private OrderEsignatureService orderEsignatureService;
	private OrdEmailReqService ordEmailReqService;
	protected LtsEmailService ltsEmailService;
	private String testSmsNumberRecipient;
	private boolean debugSmsService;
	

	public boolean isDebugSmsService() {
		return debugSmsService;
	}

	public void setDebugSmsService(boolean debugSmsService) {
		this.debugSmsService = debugSmsService;
	}

	public String getTestSmsNumberRecipient() {
		return testSmsNumberRecipient;
	}

	public void setTestSmsNumberRecipient(String testSmsNumberRecipient) {
		this.testSmsNumberRecipient = testSmsNumberRecipient;
	}
	
	public LtsEmailService getLtsEmailService() {
		return ltsEmailService;
	}

	public void setLtsEmailService(LtsEmailService ltsEmailService) {
		this.ltsEmailService = ltsEmailService;
	}

	public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}

	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}

	public CodeLkupCacheService getSendSmsLkupCacheService() {
		return sendSmsLkupCacheService;
	}

	public void setSendSmsLkupCacheService(
			CodeLkupCacheService sendSmsLkupCacheService) {
		this.sendSmsLkupCacheService = sendSmsLkupCacheService;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public String getLtsRptUrl() {
		return ltsRptUrl;
	}

	public void setLtsRptUrl(String ltsRptUrl) {
		this.ltsRptUrl = ltsRptUrl;
	}

	public ResourceBundleMessageSource getLtsMessageSource() {
		return ltsMessageSource;
	}

	public void setLtsMessageSource(ResourceBundleMessageSource ltsMessageSource) {
		this.ltsMessageSource = ltsMessageSource;
	}

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
	}

	public String sendSignOffSms(SbOrderDTO sbOrder, String userId) {
		return sendSignOffSms(sbOrder, sbOrder.getSmsNo(), userId);
	}
	
	public String sendSignOffSms(SbOrderDTO sbOrder, String smsNo, String userId) {
		String sendSms =(String)sendSmsLkupCacheService.get("LTS_SEND_SMS");
		logger.debug("Start calling sendSignOffSms(): " + sbOrder.getOrderId() + " LTS_SEND_SMS :" + sendSms);		
		
		String ref = StringUtils.leftPad(new String(sbOrder.getOrderId()).replaceAll("\\D", ""), 9, "0");
		String msgContent = null;
		String appId = APP_ID;
		String templateId = LtsSbOrderHelper.getSmsTemplateId(sbOrder);
//		Long printRequestId = null;
		
		EmailTemplateDTO smsTemplate = orderEsignatureService.getEmailTemplateDTO(templateId, LtsConstant.LOB_LTS, EsigEmailLang.valueOf(sbOrder.getEsigEmailLang()));
		if (smsTemplate == null || StringUtils.isBlank(smsTemplate.getTemplateContent())) {
			return "FAIL TO RETRIEVE SMS TEMPLATE";
		}
		msgContent = formatMsgContent(sbOrder, smsTemplate.getTemplateContent());
		
//		if (LtsConstant.ORDER_TYPE_DISCONNECT.equals(sbOrder.getOrderType())) {
//			printRequestId = ltsEmailService.insertFormPrintReq(sbOrder.getOrderId(), "SMS", "", "", "", "", "", "", "", "", "", msgContent,  userId, userId);	
//		}

		OrdEmailReqDTO smsRequest = ltsEmailService.createOrderEmailReq(sbOrder.getOrderId(), templateId, null, smsNo, LtsConstant.SEND_METHOD_SMS, userId, sbOrder.getEsigEmailLang(), null, null, null, null, null);
//		OrdEmailReqDTO smsRequest = orderEsignatureService.createSmsReqRecord(sbOrder.getOrderId(), templateId, new Date(), null, null, null, sbOrder.getSmsNo(), seqNo, userId, printRequestId);
		
		try {
			if (!StringUtils.equals("Y", sendSms)) {
				return "FAIL (LTS_SEND_SMS != Y)";
			}
				submitSms(SMS_PREFIX + smsNo, msgContent, ref, appId);
				return orderEsignatureService.updateOrdEmailReq(smsRequest.getOrderId(), smsRequest.getSeqNo(), smsRequest.getTemplateId()).toString();
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return "FAIL TO SEND SMS :" + ExceptionUtils.getFullStackTrace(e);
		}
//		return "NO SMS REQUIRED";
	}
	
	public String formatMsgContent(SbOrderDTO sbOrder, String msgContent) {
		ServiceDetailDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
		String installDate = LtsDateFormatHelper.getDateFromDateTimeSlot(ltsService.getAppointmentDtl().getAppntStartTime());
		String installTime = LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(ltsService.getAppointmentDtl().getAppntStartTime(), ltsService.getAppointmentDtl().getAppntEndTime()));
		String srdDate =  LtsDateFormatHelper.getDateFromDateTimeSlot(sbOrder.getSrvReqDate());
		
		/* Display SRD as TBC for eye resources shortage case*/
		if(LtsSbOrderHelper.isEyeResourceShortage(sbOrder)
				&& (LtsConstant.ORDER_TYPE_SB_ACQUISITION.equals(sbOrder.getOrderType())
						|| LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())) ){
			String tbcMessage = null;
			if(StringUtils.equals(sbOrder.getLangPref(), "ENG")){
				tbcMessage = ltsMessageSource.getMessage("lts.rpt.tbc", null, null);
			}else{
				tbcMessage = ltsMessageSource.getMessage("lts.rpt.tbc.chi", null, null);
			}
			if(StringUtils.isNotBlank(tbcMessage)){
				installDate = tbcMessage;
				installTime = tbcMessage;
			}
		}
		
		msgContent = StringUtils.replace(msgContent, "[SRV_NUM]", StringUtils.removeStart(ltsService.getSrvNum(), "0000") );
		msgContent = StringUtils.replace(msgContent, "[DATE]", StringUtils.defaultIfEmpty(installDate, srdDate));
		msgContent = StringUtils.replace(msgContent, "[TIME]", installTime);
		msgContent = StringUtils.replace(msgContent, "[RPT_URL]", this.ltsRptUrl + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, sbOrder.getOrderId()));		
		
		if (StringUtils.equals("Y", sbOrder.getPrepaymentSlipInd()) && (LtsConstant.ORDER_MODE_DIRECT_SALES.equals(sbOrder.getOrderMode())
				|| LtsConstant.ORDER_MODE_DIRECT_SALES_NOW_TV_QA.equals(sbOrder.getOrderMode()))) {
			msgContent = StringUtils.replace(msgContent, "[PS_URL]", this.ltsRptUrl + uENC.encAES(BomWebPortalConstant.CEKS_ENC_KEY, sbOrder.getOrderId())
					+ "&ltsRptAction=" + LtsConstant.REPORT_ACTION_TYPE_SMS_PREPAYMENT);
		}
		
		return msgContent;
	}
	
	public String submitSms(String srvNum, String msg, String ref, String appId) {
		
		try {
			logger.debug("Start calling submitSms [" + srvNum +":"+ msg +":"+ ref +":"+ appId +"]");
			NetvigatorSMSInterfaceServiceLocator ws = new NetvigatorSMSInterfaceServiceLocator();
			ws.setNetvigatorSMSEndpointAddress(endpoint);
			String result = ws.getNetvigatorSMS().sendSMS(srvNum, msg, ref, appId);
			logger.debug("submitSms result: " + result);
			return result;
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return "Fail to send SMS [" + srvNum+ "]";
		}
	}
	
	public String sendAmendmentNoticeSMS(OrdEmailReqDTO insertedDto) {
		String orderId = insertedDto.getOrderId();
		String ref = StringUtils.leftPad(new String(orderId).replaceAll("\\D", ""), 9, "0");
		String smsNo = SMS_PREFIX + StringUtils.defaultIfEmpty(insertedDto.getSMSno(), "");
		String msgContent = null;
		String appId = APP_ID;
		String templateId = insertedDto.getTemplateId();
		
		EmailTemplateDTO smsTemplate = orderEsignatureService.getEmailTemplateDTO(templateId, LtsConstant.LOB_LTS, insertedDto.getLocale());
		
		String result = null;
		
		if (smsTemplate == null || StringUtils.isBlank(smsTemplate.getTemplateContent())) {
			return "FAIL TO RETRIEVE SMS TEMPLATE";

		}
		msgContent = smsTemplate.getTemplateContent();

		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		msgContent = msgContent.replace("{0}", df.format(insertedDto.getRequestDate()));
		msgContent = msgContent.replace("{1}", insertedDto.getContactPhone());
		
		result = submitSms(smsNo, msgContent, ref, appId);
		insertedDto.setSentDate(new Date());
		insertedDto.setErrMsg(result);
		
		try {
			ordEmailReqService.updateOrdSMSReq(insertedDto);
		} catch (Exception e) {
			logger.warn("Exception in updateOrdEmailReq", e);
//			throw new AppRuntimeException(e == null ? "" : e.getMessage());
			return e.getMessage();
		}
		
		return result;
	}

	public String sendSms(String smsNo, String msgContent, String ref) {
		if(this.isDebugSmsService()){
			smsNo = this.testSmsNumberRecipient;
		}
		String result = submitSms(smsNo, msgContent, ref, APP_ID);
		if(StringUtils.equals("0", result)){
			result = STATUS_SUCCESS + "-" + result;
		}else{
			result = STATUS_FAIL + "-" + result;
		}
		return result;
	}
}
