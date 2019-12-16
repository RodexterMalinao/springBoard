package com.bomwebportal.mob.ccs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoWBasketAttrService;

public class MobCcsBasketCompareController implements Controller {
	private MobCcsBasketAssoWBasketAttrService mobCcsBasketAssoWBasketAttrService;

	public MobCcsBasketAssoWBasketAttrService getMobCcsBasketAssoWBasketAttrService() {
		return mobCcsBasketAssoWBasketAttrService;
	}

	public void setMobCcsBasketAssoWBasketAttrService(
			MobCcsBasketAssoWBasketAttrService mobCcsBasketAssoWBasketAttrService) {
		this.mobCcsBasketAssoWBasketAttrService = mobCcsBasketAssoWBasketAttrService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("mobccsbasketcompare");
		
		modelAndView.addObject("customerTierALL", this.mobCcsBasketAssoWBasketAttrService.getWBasketAttrCustomerTierDTOALL());
		modelAndView.addObject("basketTypeALL", this.mobCcsBasketAssoWBasketAttrService.getWBasketAttrBasketTypeDTOALL());
		//modelAndView.addObject("ratePlanALL", this.mobCcsBasketAssoWBasketAttrService.getWBasketAttrRatePlanDTOALL());
		//modelAndView.addObject("brandModelALL", this.mobCcsBasketAssoWBasketAttrService.getWBasketAttrBrandModelDTOALL());
		
		return modelAndView;
	}

}
