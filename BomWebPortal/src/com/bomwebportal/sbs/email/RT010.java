package com.bomwebportal.sbs.email;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.email.EmailTemplateHandler;
import com.bomwebportal.sbs.dto.StSubscribedVkkItemDTO;
import com.bomwebportal.sbs.service.SbsOrderService;
import com.bomwebportal.sbs.service.VirtualPrepaidService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.Utility;

public class RT010 implements EmailTemplateHandler {

	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SbsOrderService sbsOrderService;

	@Autowired
	private VirtualPrepaidService virtualPrepaidService;
	
	public void beforeEmailSent(OrdEmailReqDTO req) {
		// nothing ...
	}
	
	public Map<String, ?> getSubjectProperties(OrdEmailReqDTO req) {
		logger.debug("Loading subject properties for email template RT010");
		HashMap<String,Object> map = new HashMap<String,Object>();
		return map;
	}
	
	public Map<String, ?> getContentProperties(OrdEmailReqDTO req) {
		logger.debug("Loading content properties for email template RT010");
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		StSubscribedVkkItemDTO vkk = sbsOrderService.getSubscribedVkkItem(req.getOrderId());
		map.put("referenceNumber", req.getOrderId());
		if (vkk != null) {
			map.put("msisdn", vkk.getMsisdn());
			map.put("expDate", Utility.date2String(vkk.getExpDate(), "dd/MM/yyyy"));
		}
		return map;
	}

	public void afterEmailSent(OrdEmailReqDTO req) {
		boolean success = (req.getSentDate() != null);
		if (success) {
			logger.debug("Updating VKK Service");
			try {
				updateVkk(req.getOrderId(), req.getEmailAddr(), req.getLastUpdBy());
			} catch (Exception e) {
				logger.error("Failed to update VKK service", e);
			}
		}
		
	}
	
	
	private void updateVkk(String orderId, String email, String createBy) {
		OrderDTO orderDTO = orderService.getOrder(orderId);
		String mrt = orderDTO.getMsisdn();
		virtualPrepaidService.updateEmail(orderId, mrt, email, createBy);
	}

}
