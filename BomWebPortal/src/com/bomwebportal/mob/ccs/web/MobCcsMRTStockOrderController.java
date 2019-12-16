package com.bomwebportal.mob.ccs.web;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.dto.MrtInventoryOrderDTO;
import com.bomwebportal.mob.ccs.service.MrtInventoryOrderService;

public class MobCcsMRTStockOrderController implements Controller {
	private MrtInventoryOrderService mrtInventoryOrderService;
	public MrtInventoryOrderService getMrtInventoryOrderService() {
		return mrtInventoryOrderService;
	}
	public void setMrtInventoryOrderService(
			MrtInventoryOrderService mrtInventoryOrderService) {
		this.mrtInventoryOrderService = mrtInventoryOrderService;
	}
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("mobccsmrtstockorder");
		String msisdn = request.getParameter("msisdn");
		String stockInDate = request.getParameter("stockInDate");
		if (StringUtils.isNotBlank(msisdn) && StringUtils.isNotBlank(stockInDate)) {
			MrtInventoryOrderDTO record = this.mrtInventoryOrderService.getMrtInventoryOrderDTO(msisdn, (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(stockInDate));
			modelAndView.addObject("record", record);
		}
		return modelAndView;
	}
	
	
}
