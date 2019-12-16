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

@Component("template.MOB.RT014")
public class RT014 implements EmailTemplateHandler {
	
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
		
		try {
			Locale locale = Locale.ENGLISH;
			if (EsigEmailLang.CHN == req.getLocale()){
				locale = Locale.TRADITIONAL_CHINESE;
			}
			
			String orderBrandSubject = null;
			String orderBrand = orderService.getOrderBrand(req.getOrderId());
			
			
			try{
				if ( orderBrand == null) {
					
					orderBrandSubject = mobMessageSource.getMessage("mob.RT013.orderBrandSubject.csl", null, locale);
					
				}else if (orderBrand != null) {
					if ("0".equals(orderBrand)){
						orderBrand = "1010";
					}else{
						orderBrand = "csl";
					}
					
					orderBrandSubject = mobMessageSource.getMessage("mob.RT013.orderBrandSubject."+orderBrand, null, locale);
					
				}
				
			}catch (Exception ex){
				orderBrandSubject = null;
			}
			
			map.put("orderBrandSubject", (orderBrandSubject == null ? "" : orderBrandSubject));
			
		} catch (Exception e) {
			logger.error("Failed to load data for RT013", e);
		}
		
		return map;
	}
	
	public Map<String, ?> getContentProperties(OrdEmailReqDTO req) {
		HashMap<String,Object> map = new HashMap<String,Object>();
	
		try {
			CustomerProfileDTO customerDTO = customerProfileService.getCosCustomerProfile(req.getOrderId());
			map.put("customerTitle", (customerDTO.getTitle() == null ? "" : customerDTO.getTitle()));
			if ("BS".equals(customerDTO.getIdDocType())){
				map.put("customerName", customerDTO.getCompanyName().toUpperCase());
			}else{
				map.put("customerName", customerDTO.getCustLastName().toUpperCase() + " " + customerDTO.getCustFirstName().toUpperCase());
			}
			
			
			
			String shopBrand = null;
			Locale locale = Locale.ENGLISH;
			if (EsigEmailLang.CHN == req.getLocale()){
				locale = Locale.TRADITIONAL_CHINESE;
			}
			
			
			try{

				if (req.getBrand() == null) {
					
					shopBrand = mobMessageSource.getMessage("mob.RT014.shopBrand.csl", null, locale);
					
				}else if (req.getBrand() != null) {
												
					shopBrand = mobMessageSource.getMessage("mob.RT014.shopBrand."+req.getBrand(), null, locale);
					
				}
			
			}catch (Exception ex){
				shopBrand = null;
			}
			
			map.put("shopBrand", (shopBrand == null ? "" : shopBrand));
			
			
		} catch (Exception e) {
			logger.error("Failed to load data for RT014", e);
		}
		
		return map;
	}

	public void afterEmailSent(OrdEmailReqDTO req) {
		
	}


	
}
