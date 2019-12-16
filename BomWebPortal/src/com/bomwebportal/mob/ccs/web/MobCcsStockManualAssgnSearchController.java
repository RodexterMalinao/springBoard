package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.service.StockManualAssgnService;

public class MobCcsStockManualAssgnSearchController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private StockManualAssgnService stockManualAssgnService;
	
	public void setStockManualAssgnService(StockManualAssgnService stockManualAssgnService) {
		this.stockManualAssgnService = stockManualAssgnService;
	}
	public StockManualAssgnService getStockManualAssgnService() {
		return stockManualAssgnService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<String> list = new ArrayList<String>();
		String itemCode = request.getParameter("itemCode");
		String location = request.getParameter("location");
		String status = request.getParameter("status");
		String orderId = request.getParameter("orderId");
		String stockPool = request.getParameter("stockPool");

		logger.debug("MobCcsStockManualAssgnSearchController (item code, location, status, orderId): " + itemCode + ", " + location + ", " + status + ", " + orderId);
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		if (user.getChannelId() == 10 || user.getChannelId() == 11) {
			list = stockManualAssgnService.getDSStockManualAssgn(itemCode, orderId);
		} else {
			list = stockManualAssgnService.getStockManualAssgn(itemCode, location, status, stockPool);
		}

		if (list == null) {
			list = Collections.emptyList();
		}
		
		JSONArray jsonArray = JSONArray.fromObject(list);

		return new ModelAndView("ajax_search", "jsonArray", jsonArray);
	}
}
