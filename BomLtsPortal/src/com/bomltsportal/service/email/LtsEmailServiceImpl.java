package com.bomltsportal.service.email;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomltsportal.service.msgdeliver.MsgDeliveryControlLkupService;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;

public class LtsEmailServiceImpl implements LtsEmailService {
	protected final Log logger = LogFactory.getLog(getClass());
	private OrderEsignatureService orderEsignatureService;
	private MsgDeliveryControlLkupService lkupService;
	
	public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}

	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}

	public String sendSignOffEmail(SbOrderDTO sbOrder,
			String userId, String filePathName1,
			String filePathName2, String filePathName3, boolean isAmend) {
		
		if (!StringUtils.equals(LtsBackendConstant.DISTRIBUTE_MODE_ESIGNATURE,
				sbOrder.getDisMode())) {
			return null;
		}
		
		if(!("Y".equals(this.lkupService.getSendEmailOrNot()))){
			logger.info("No need to send email...");
			return null;
		}
		
		try {
			String template = null;
			
			if(isAmend){
				if(sbOrder != null && sbOrder.getSrvDtls() != null && LtsSbHelper.getDelServices(sbOrder) != null){
					template = LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_AMEND_DEL;
				}
				else if(sbOrder != null && sbOrder.getSrvDtls() != null && LtsSbHelper.getLtsEyeService(sbOrder) != null){
					template = LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_AMEND;
				}
			}
			else if(LtsSbHelper.isPortInOrder(sbOrder)){
				if(sbOrder != null && sbOrder.getSrvDtls() != null && LtsSbHelper.getDelServices(sbOrder) != null){
					template = LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_PORT_IN_ORDER_DEL;
				}
				else if(sbOrder != null && sbOrder.getSrvDtls() != null && LtsSbHelper.getLtsEyeService(sbOrder) != null){
					template = LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_PORT_IN_ORDER;
				}				
			}
			else if (LtsSbHelper.isWorkQueueOrder(sbOrder)) {
				if(sbOrder != null && sbOrder.getSrvDtls() != null && LtsSbHelper.getDelServices(sbOrder) != null){
					template = LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_WQ_DEL;
				}
				else if(sbOrder != null && sbOrder.getSrvDtls() != null && LtsSbHelper.getLtsEyeService(sbOrder) != null){
					template = LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_WQ;
				}								
			}
			else{
				if(sbOrder != null && sbOrder.getSrvDtls() != null && LtsSbHelper.getDelServices(sbOrder) != null){
					template = LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES_DEL;
				}
				else if(sbOrder != null && sbOrder.getSrvDtls() != null && LtsSbHelper.getLtsEyeService(sbOrder) != null){
					template = LtsBackendConstant.EMAIL_TEMPLATE_SIGNOFF_FOR_ONLINE_SALES;
				}		
			}
			
			if(template == null){
				return null;
			}
			
			OrderEsignatureService.EmailReqResult result = orderEsignatureService.createEmailReq(sbOrder.getOrderId(),
					template, new Date(),
					filePathName1, filePathName2, filePathName3,
					sbOrder.getEsigEmailAddr(), sbOrder.getEsigEmailLang(), userId);
			
			if (result == OrderEsignatureService.EmailReqResult.SUCCESS) {
				return null;	
			}
			return result.getMessage();
			
		}
		catch (Exception e) {
			throw new AppRuntimeException(e);
		}
		
	}

	public MsgDeliveryControlLkupService getLkupService() {
		return lkupService;
	}

	public void setLkupService(MsgDeliveryControlLkupService lkupService) {
		this.lkupService = lkupService;
	}	
}
