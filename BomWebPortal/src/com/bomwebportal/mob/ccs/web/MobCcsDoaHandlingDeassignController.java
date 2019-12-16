package com.bomwebportal.mob.ccs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.service.StockManualDetailService;

public class MobCcsDoaHandlingDeassignController implements Controller {
	
	private StockManualDetailService stockManualDetailService;
	
	/**
	 * @return the stockManualDetailService
	 */
	public StockManualDetailService getStockManualDetailService() {
		return stockManualDetailService;
	}

	/**
	 * @param stockManualDetailService the stockManualDetailService to set
	 */
	public void setStockManualDetailService(
			StockManualDetailService stockManualDetailService) {
		this.stockManualDetailService = stockManualDetailService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JSONArray jsonArray = new JSONArray();
		
		String orderId = ServletRequestUtils.getStringParameter(request, "orderId");
		String itemCode = ServletRequestUtils.getStringParameter(request, "itemCode");
		String itemSerial = ServletRequestUtils.getStringParameter(request, "itemSerial");
		String oldItemCode = itemCode;
		
		String result = stockManualDetailService.doaDeassignItem(orderId, itemCode, itemSerial, oldItemCode);
		
		jsonArray.add(result);
		
		return new ModelAndView("ajax_deassign", "jsonArray", jsonArray);
	}

}
