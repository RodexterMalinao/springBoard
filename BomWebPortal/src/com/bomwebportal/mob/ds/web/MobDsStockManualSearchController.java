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
import com.bomwebportal.mob.ccs.dto.StockManualDTO;
import com.bomwebportal.mob.ccs.service.StockManualService;

public class MobDsStockManualSearchController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private StockManualService stockManualService;
	
	public StockManualService getStockManualService() {
		return stockManualService;
	}
	public void setStockManualService(StockManualService stockManualService) {
		this.stockManualService = stockManualService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String orderStatus = request.getParameter("orderStatus");
		String orderId = request.getParameter("orderId");
		String searchCriteria = request.getParameter("searchCriteria");
		
		List<StockManualDTO> list = new ArrayList<StockManualDTO>();
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		//WAREHOUSE SUPPORT ONLY
		list = stockManualService.getDSStockManual(startDate, endDate, 
				orderStatus, orderId, searchCriteria, user.getShopCd());

		if (list == null || list.isEmpty()) {
			list = Collections.emptyList();
		}
		
		JSONArray jsonArray = JSONArray.fromObject(list);

		return new ModelAndView("ajax_search", "jsonArray", jsonArray);
	}
}
