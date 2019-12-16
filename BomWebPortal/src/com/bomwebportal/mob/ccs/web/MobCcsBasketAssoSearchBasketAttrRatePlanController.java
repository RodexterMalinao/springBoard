package com.bomwebportal.mob.ccs.web;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrRatePlanDTO;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoWBasketAttrService;

public class MobCcsBasketAssoSearchBasketAttrRatePlanController implements Controller {
	protected final Log logger = LogFactory.getLog(getClass());
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
		BasketAssoWBasketAttrDTO dto = new BasketAssoWBasketAttrDTO();
		dto.setCustomerTierId(ServletRequestUtils.getStringParameter(request, "customerTierId"));
		dto.setBasketTypeId(ServletRequestUtils.getStringParameter(request, "basketTypeId"));

		List<BasketAssoWBasketAttrRatePlanDTO> jsonArray = null;
		
		if (dto.getCustomerTierId() != null || dto.getBasketTypeId() != null) {
			jsonArray = this.mobCcsBasketAssoWBasketAttrService.getWBasketAttrRatePlanDTOBySearch(dto);
		}
		
		if (jsonArray == null) {
			jsonArray = Collections.emptyList();
		}

		return new ModelAndView("ajax_searchBasketAttr", "jsonArray", JSONArray.fromObject(jsonArray));
	}
}
