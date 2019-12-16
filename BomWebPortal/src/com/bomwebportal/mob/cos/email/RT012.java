package com.bomwebportal.mob.cos.email;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.email.EmailTemplateHandler;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.OrderService;

@Component("template.MOB.RT012")
public class RT012 implements EmailTemplateHandler {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CustomerProfileService customerProfileService;
	
	private MessageSource mobMessageSource;
	
	@Autowired
	private OrderService orderService;
	
	
	public MessageSource getMobMessageSource() {
		return mobMessageSource;
	}

	public void setMobMessageSource(MessageSource mobMessageSource) {
		this.mobMessageSource = mobMessageSource;
	}

	public void beforeEmailSent(OrdEmailReqDTO req) {

	}
	
	public Map<String, ?> getSubjectProperties(OrdEmailReqDTO req) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		return map;
	}
	
	public Map<String, ?> getContentProperties(OrdEmailReqDTO req) {
		HashMap<String,Object> map = new HashMap<String,Object>();
	
		try {
			CustomerProfileDTO customerDTO = customerProfileService.getCosCustomerProfile(req.getOrderId());
			map.put("customerTitle", customerDTO.getTitle());
			map.put("customerName", customerDTO.getCustLastName().toUpperCase() + " " + customerDTO.getCustFirstName().toUpperCase());
			
			
			String shopBrand = null;
			String mobileServiceAgreementUrl = null;
			String orderBrand = orderService.getOrderBrand(req.getOrderId());
			
			Locale locale = Locale.ENGLISH;
			if (EsigEmailLang.CHN == req.getLocale()){
				locale = Locale.TRADITIONAL_CHINESE;
			}
			
			
			try{

				if (req.getBrand() == null) {
					
					shopBrand = mobMessageSource.getMessage("mob.RT012.shopBrand.csl", null, locale);
					
				}else if (req.getBrand() != null) {
												
					shopBrand = mobMessageSource.getMessage("mob.RT012.shopBrand."+req.getBrand(), null, locale);
					
				}
				
				if (orderBrand == null) {
					
					mobileServiceAgreementUrl = mobMessageSource.getMessage("mob.RT012.mobileServiceAgreementUrl.csl", null, locale);
					
				}else if (orderBrand != null) {
					if ("0".equals(orderBrand)){
						orderBrand = "1010";
					}else{
						orderBrand = "csl";
					}
										
					mobileServiceAgreementUrl = mobMessageSource.getMessage("mob.RT012.mobileServiceAgreementUrl."+orderBrand, null, locale);
					
				}
	
			
			}catch (Exception ex){
				shopBrand = null;
			}
			
			map.put("shopBrand", (shopBrand == null ? "" : shopBrand));
			map.put("mobileServiceAgreementUrl", (mobileServiceAgreementUrl == null ? "" : mobileServiceAgreementUrl));
		} catch (Exception e) {
			logger.error("Failed to load data for RT012", e);
		}
		
		return map;
	}

	public void afterEmailSent(OrdEmailReqDTO req) {
		
	}


	
}
