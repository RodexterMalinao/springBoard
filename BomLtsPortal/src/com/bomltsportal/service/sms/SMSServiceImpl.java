package com.bomltsportal.service.sms;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

import com.bomltsportal.dao.OnlineBasketDAO;
import com.bomltsportal.service.msgdeliver.MsgDeliveryControlLkupService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.LtsDateFormatHelper;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.pccw.netvigator.NetvigatorSMSInterfaceServiceLocator;

public class SMSServiceImpl implements SMSService {
	protected final Log logger = LogFactory.getLog(getClass());
	private String endpoint;
	private MsgDeliveryControlLkupService lkupService;
	private OnlineBasketDAO onlineBasketDAO;
	
	public OnlineBasketDAO getOnlineBasketDAO() {
		return onlineBasketDAO;
	}

	public void setOnlineBasketDAO(OnlineBasketDAO onlineBasketDAO) {
		this.onlineBasketDAO = onlineBasketDAO;
	}

	public MsgDeliveryControlLkupService getLkupService() {
		return lkupService;
	}

	public void setLkupService(MsgDeliveryControlLkupService lkupService) {
		this.lkupService = lkupService;
	}

	private ResourceBundleMessageSource messageSource;

	public void sendSMSMessage(String mobileno, Locale locale, SbOrderDTO pSbOrder, String pSrvType) {
		// TODO Auto-generated method stub
		try{
			logger.info("send SMS Message");			
			logger.debug("OrderID:" + pSbOrder.getOrderId());
			String ref =(pSbOrder.getOrderId().replaceAll("\\D", ""));
			
			logger.debug("OrderID after cut chars:" + ref);
			while(ref.length()<9){
				ref="0"+ref;
			}
			logger.debug("OrderID after add zero:" + ref);
			 
			NetvigatorSMSInterfaceServiceLocator ws = new NetvigatorSMSInterfaceServiceLocator();
			logger.debug("endpoint = " + endpoint);
			ws.setNetvigatorSMSEndpointAddress(endpoint);
									
			String v1 = "852"+mobileno;

  			String msg = "";
  			
  			ServiceDetailLtsDTO ltsService = LtsSbHelper.getLtsService(pSbOrder);
  			
			if(StringUtils.equals(pSrvType, BomLtsConstant.SERVICE_TYPE_DEL)){
				msg = messageSource.getMessage("bomltsportal.del.sms", null, locale);
				String serviceKey = getServiceKey(LtsBackendConstant.SERVICE_TYPE_DEL, ltsService, pSbOrder);
				
				if(locale.getLanguage().equals(BomLtsConstant.LOCALE_ENG)){
					String srv = BomLtsConstant.DEL_SMS_STRING_REPLACEMENT_MAP_ENG.get(serviceKey);
					String date = LtsDateFormatHelper.getDayFromDateTimeSlot(ltsService.getAppointmentDtl().getAppntStartTime()) + "-" + LtsDateFormatHelper.getMonthFromDateTimeSlot(ltsService.getAppointmentDtl().getAppntStartTime(), BomLtsConstant.LOCALE_ENG);
					msg = StringUtils.replace(msg, "[del]", srv);
					msg = StringUtils.replace(msg, "[date]", date);
					msg = StringUtils.replace(msg, "[tel_num]", BomLtsConstant.SMS_INQUIRIES);
				}				
				else{
					String srv = BomLtsConstant.DEL_SMS_STRING_REPLACEMENT_MAP_CHI.get(serviceKey);
					String month = LtsDateFormatHelper.getMonthFromDateTimeSlot(ltsService.getAppointmentDtl().getAppntStartTime(), BomLtsConstant.LOCALE_CHI);
					String day = LtsDateFormatHelper.getDayFromDateTimeSlot(ltsService.getAppointmentDtl().getAppntStartTime());
					msg = StringUtils.replace(msg, "[del]", srv);
					msg = StringUtils.replace(msg, "[month]", month);
					msg = StringUtils.replace(msg, "[day]", day);
					msg = StringUtils.replace(msg, "[tel_num]", BomLtsConstant.SMS_INQUIRIES);
				}
			}
			else if(StringUtils.equals(pSrvType, BomLtsConstant.SERVICE_TYPE_EYE)){
				msg = messageSource.getMessage("bomltsportal.eye.sms", null, locale);
				String serviceKey = getServiceKey(LtsBackendConstant.SERVICE_TYPE_EYE, ltsService, pSbOrder);
				
				if(locale.getLanguage().equals(BomLtsConstant.LOCALE_ENG)){
					String srv = BomLtsConstant.EYE_SMS_STRING_REPLACEMENT_MAP_ENG.get(serviceKey);
					String date = LtsDateFormatHelper.getDayFromDateTimeSlot(ltsService.getAppointmentDtl().getAppntStartTime()) + "-" + LtsDateFormatHelper.getMonthFromDateTimeSlot(ltsService.getAppointmentDtl().getAppntStartTime(), BomLtsConstant.LOCALE_ENG);
					msg = StringUtils.replace(msg, "[eye]", srv);
					msg = StringUtils.replace(msg, "[date]", date);
					msg = StringUtils.replace(msg, "[tel_num]", BomLtsConstant.SMS_INQUIRIES);
				}
				else{
					String srv = BomLtsConstant.EYE_SMS_STRING_REPLACEMENT_MAP_CHI.get(serviceKey);
					String month = LtsDateFormatHelper.getMonthFromDateTimeSlot(ltsService.getAppointmentDtl().getAppntStartTime(), BomLtsConstant.LOCALE_CHI);
					String day = LtsDateFormatHelper.getDayFromDateTimeSlot(ltsService.getAppointmentDtl().getAppntStartTime());
					msg = StringUtils.replace(msg, "[eye]", srv);
					msg = StringUtils.replace(msg, "[month]", month);
					msg = StringUtils.replace(msg, "[day]", day);
					msg = StringUtils.replace(msg, "[tel_num]", BomLtsConstant.SMS_INQUIRIES);
				}
			}	
			
			logger.debug("SMS : " + msg);
			String v2 = msg;
			String v3 = ref;
			String v4 = "sbonline_lts";
			
			
			logger.info("v1:"+v1);
			logger.info("v2:"+v2);
			logger.info("v3:"+v3);
			logger.info("v4:"+v4);
			String SmsOrNot = lkupService.getSendSMSorNot();
			logger.info("SmsOrNot:"+SmsOrNot);
			if(("Y").equals(SmsOrNot)){
				String result = ws.getNetvigatorSMS().sendSMS(v1, v2, v3, v4);
				logger.debug("result:"+result);
			}else{
				logger.info("No need to send sms");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private String getServiceKey(String pSrvTyp, ServiceDetailLtsDTO pService, SbOrderDTO pSbOrder){
		if(pSrvTyp.equals(LtsBackendConstant.SERVICE_TYPE_EYE)){
			ServiceDetailLtsDTO eyeService = LtsSbHelper.getLtsEyeService(pSbOrder);
			return StringUtils.equals(BomLtsConstant.DEVICE_TYPE_1030, eyeService.getDeviceType())? "EYE3":"EYE";
		}
		else{
			return isTpDel(pService)?"DEL_TP":"DEL_NON_TP";
		}
	}
	
	private boolean isTpDel(ServiceDetailLtsDTO pService){
		ItemDetailLtsDTO[] items = pService.getItemDtls();
		if(items == null){
			return false;
		}
		else{
			for(int i=0; i<items.length; i++){
				if(LtsBackendConstant.ITEM_TYPE_PLAN.equals(items[i].getItemType())){
					try {
						BasketDetailDTO basket = onlineBasketDAO.getBasket(items[i].getBasketId(), LtsBackendConstant.LOCALE_ENG, LtsBackendConstant.DISPLAY_TYPE_ONLINE_DESC);
						if(basket != null && StringUtils.isNotBlank(basket.getContractPeriod()) && Integer.parseInt(basket.getContractPeriod()) > 0){
							return true;	// Is Term Plan Del Service
						}
					} catch (DAOException de) {
						logger.error(ExceptionUtils.getFullStackTrace(de));
						throw new RuntimeException(de.getCustomMessage());
					}					
				}
			}
		}		
		return false;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getEndpoint() {
		return endpoint;
	}


	public void setMessageSource(ResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public ResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

}
