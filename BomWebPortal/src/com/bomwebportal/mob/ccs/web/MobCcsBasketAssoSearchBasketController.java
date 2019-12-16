package com.bomwebportal.mob.ccs.web;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrDTO;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoWBasketAttrService;

public class MobCcsBasketAssoSearchBasketController implements Controller {
	protected final Log logger = LogFactory.getLog(getClass());
	private MobCcsBasketAssoWBasketAttrService mobCcsBasketAssoWBasketAttrService;
	
	public MobCcsBasketAssoWBasketAttrService getMobCcsBasketAssoWBasketAttrService() {
		return mobCcsBasketAssoWBasketAttrService;
	}
	
	public void setMobCcsBasketAssoWBasketAttrService(
			MobCcsBasketAssoWBasketAttrService mobCcsBasketAssoWBasketAttrService) {
		this.mobCcsBasketAssoWBasketAttrService = mobCcsBasketAssoWBasketAttrService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<BasketAssoWBasketAttrDTO> list = null;
		String basketDesc = StringUtils.trim(ServletRequestUtils.getStringParameter(request, "basketDesc"));
		if (StringUtils.isBlank(basketDesc)) {
			BasketAssoWBasketAttrDTO dto = new BasketAssoWBasketAttrDTO();
			dto.setCustomerTierId(ServletRequestUtils.getStringParameter(request, "customerTierId"));
			dto.setBasketTypeId(ServletRequestUtils.getStringParameter(request, "basketTypeId"));
			dto.setRatePlanId(ServletRequestUtils.getStringParameter(request, "ratePlanId"));
			dto.setBrandId(ServletRequestUtils.getStringParameter(request, "brandId"));
			dto.setModelId(ServletRequestUtils.getStringParameter(request, "modelId"));
			list = this.mobCcsBasketAssoWBasketAttrService.getBasketAssoWBasketAttrDTOBySearch(dto);
		} else {
			list = this.mobCcsBasketAssoWBasketAttrService.getBasketAssoWBasketAttrDTOBySearch(basketDesc);
		}
		JSONArray jsonArray = JSONArray.fromObject(list == null ? Collections.emptyList() : list);
		return new ModelAndView("ajax_searchBasket", "jsonArray", jsonArray);
	}
}
