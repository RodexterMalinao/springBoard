package com.bomwebportal.mob.ds.web;

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

public class MobDsStockManualAssgnSearchController implements Controller {
	
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
		String orderId = request.getParameter("orderId");

		logger.debug("MobDsStockManualAssgnSearchController (item code, orderId): " + itemCode + ", " + orderId);
		
		list = stockManualAssgnService.getDSStockManualAssgn(itemCode, orderId);

		if (list == null) {
			list = Collections.emptyList();
		}
		
		JSONArray jsonArray = JSONArray.fromObject(list);

		return new ModelAndView("ajax_search", "jsonArray", jsonArray);
	}
}
