package com.bomwebportal.mob.ds.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.mob.ds.service.MobDsOrderService;

public class MobDsOrderReviewController implements Controller {
	
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobDsOrderService mobDsOrderService;
	
	private MobCcsOrderRemarkService remarkService;

	public MobDsOrderService getMobDsOrderService() {
		return mobDsOrderService;
	}

	public void setMobDsOrderService(
			MobDsOrderService mobDsOrderReviewService) {
		this.mobDsOrderService = mobDsOrderReviewService;
	}
	
	public MobCcsOrderRemarkService getRemarkService() {
		return remarkService;
	}

	public void setRemarkService(MobCcsOrderRemarkService remarkService) {
		this.remarkService = remarkService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actionType = request.getParameter("actionType");
		String orderId = request.getParameter("orderId");
		String reason = request.getParameter("reason");

		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		List<OrderDTO> orderList = null;
		String docInd = null;

		if (actionType != null) {
			if ("APPROVE".equalsIgnoreCase(actionType)) {
				mobDsOrderService.approveOrderReview(orderId, ("" + salesUserDto.getChannelId()), salesUserDto.getCategory(), salesUserDto.getUsername());
				remarkService.insertOrderRemark(salesUserDto.getUsername(), orderId, salesUserDto.getUsername() + " approved order.");
			} else if ("REJECT".equalsIgnoreCase(actionType)) {
				mobDsOrderService.rejectOrderReview(orderId, reason, salesUserDto.getCategory(), salesUserDto.getUsername());
				remarkService.insertOrderRemark(salesUserDto.getUsername(), orderId, salesUserDto.getUsername() + " rejected order.");
			} 
		}
		mobDsOrderService.updateOrderReviewStatus();
		orderList = mobDsOrderService.findOrderReview(salesUserDto.getUsername(), ("" + salesUserDto.getChannelId()), salesUserDto.getChannelCd(), 
				 salesUserDto.getCategory());
		
		
		for (OrderDTO order: orderList) {
			docInd = mobDsOrderService.getAllDocAssgn(order.getOrderId());
		    order.setDocUpldInd(docInd);
		}
		
		ModelAndView myView = new ModelAndView("mobdsorderreview");
		myView.addObject("orderList", orderList);
		myView.addObject("salesUserDto", salesUserDto);
		myView.addObject("docInd", docInd);
		return myView;
	}
	
	
}