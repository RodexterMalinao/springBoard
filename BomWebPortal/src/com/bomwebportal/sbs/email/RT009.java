package com.bomwebportal.sbs.email;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.email.EmailTemplateHandler;
import com.bomwebportal.sbs.dto.SbsDeliveryInfoDTO;
import com.bomwebportal.sbs.service.SbsDeliveryService;
import com.bomwebportal.sbs.service.SbsOrderService;
import com.bomwebportal.sbs.service.VirtualPrepaidService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class RT009 implements EmailTemplateHandler {

	protected final Log logger = LogFactory.getLog(getClass());
	
	//@Autowired
	//private OrderService orderService;
	@Autowired
	private SbsOrderService sbsOrderService;
	@Autowired
	private SbsDeliveryService sbsDeliveryService;
	@Autowired
	private VirtualPrepaidService virtualPrepaidService;
	
	
	public Map<String, ?> getSubjectProperties(OrdEmailReqDTO req) {
		logger.debug("Loading subject properties for email template RT009");
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("referenceNumber", req.getOrderId());
		
		return map;
	}
	

	public Map<String, ?> getContentProperties(OrdEmailReqDTO req) {
		logger.debug("Loading content properties for email template RT009");
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		try {
			//OrderDTO orderDTO = orderService.getOrder(orderId);
			SbsDeliveryInfoDTO deliveryInfo = sbsOrderService.getDeliveryInfo(req.getOrderId());

			Date deliveryDate = deliveryInfo.getDeliveryDate();
			
			String sDeliveryDate = Utility.date2String(deliveryDate, BomWebPortalConstant.DATE_FORMAT);
			String sTimeSlot = sbsDeliveryService.getTimeSlotDesc(deliveryInfo.getDeliveryTimeSlot());
			
			map.put("referenceNumber", req.getOrderId());
			map.put("deliveryDate", sDeliveryDate);
			map.put("deliveryTimeSlot", sTimeSlot);
		} catch (Exception e) {
			logger.error("Failed to load data for RT009", e);
		}
		return map;
	}

	public void beforeEmailSent(OrdEmailReqDTO req) {
		// nothing ...
	}
	public void afterEmailSent(OrdEmailReqDTO req) {
		// nothing ...
	}

}
