package com.bomwebportal.mob.ccs.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.StockManualDetailDTO;
import com.bomwebportal.mob.ccs.service.StockManualDetailService;
import com.bomwebportal.service.OrderService;

public class MobCcsStockManualOrderStatusProcessController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private StockManualDetailService stockManualDetailService;
	private OrderService orderService;

	public void setStockManualDetailService(StockManualDetailService stockManualDetailService) {
		this.stockManualDetailService = stockManualDetailService;
	}
	public StockManualDetailService getStockManualDetailService() {
		return stockManualDetailService;
	}
	public OrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		logger.debug("handleRequest@MobCcsStockManualOrderStatusProcessController");
		
		String orderId = request.getParameter("orderId");
		OrderDTO orderDTO = orderService.getOrder(orderId);
		boolean needProcess = true;
		if ("M001".equalsIgnoreCase(orderDTO.getReasonCd())) {
			needProcess = false;
			List<StockManualDetailDTO> stockList = stockManualDetailService.getStockManualDetail(orderId);
			if (CollectionUtils.isNotEmpty(stockList)) {
				for (StockManualDetailDTO stock : stockList) {
					if (StringUtils.isBlank(stock.getSerialNum())) {
						needProcess = true;
						break;
					}
				}
			}
		}
		
		if (needProcess) {
			stockManualDetailService.manualOrderStatusProcess(orderId);
		}
		
		JSONArray jsonArray = new JSONArray();
		
		return new ModelAndView("ajax_mobCcsStockManualOrderStatusProcess", "jsonArray", jsonArray);
		
	}
	
}
