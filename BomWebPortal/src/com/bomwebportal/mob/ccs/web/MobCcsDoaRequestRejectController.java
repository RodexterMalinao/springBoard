package com.bomwebportal.mob.ccs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.DoaRequestDTO;
import com.bomwebportal.mob.ccs.service.MobCcsDoaRequestService;
import com.bomwebportal.service.OrderService;

public class MobCcsDoaRequestRejectController implements Controller {
	private static final String ORDER_STATUS_COMPLETED = "02";
	private static final String ORDER_STATUS_FALLOUT = "99";
	
	private static final String CHECK_POINT_FALLOUT = "999";
	
	//private static final String ACTION_NAME = "AU04";
	
	private static final String REASON_CD_DOA_SAVED = "N000";
	private static final String REASON_CD_DOA_REJECTED = "03";
	//private static final String REASON_CD_DOA_AFTER_DELIVERY = "N001";

	private OrderService orderService;
	private MobCcsDoaRequestService mobCcsDoaRequestService;
	
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public MobCcsDoaRequestService getMobCcsDoaRequestService() {
		return mobCcsDoaRequestService;
	}

	public void setMobCcsDoaRequestService(
			MobCcsDoaRequestService mobCcsDoaRequestService) {
		this.mobCcsDoaRequestService = mobCcsDoaRequestService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		String rowId = ServletRequestUtils.getRequiredStringParameter(request, "rowId");

		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsdoarequest.html"));
		modelAndView.addObject("orderId", orderId);
		
		DoaRequestDTO doaRequest = this.mobCcsDoaRequestService.getDoaRequestDTO(rowId);
		if (doaRequest == null) {
			return modelAndView;
		}
		if (!REASON_CD_DOA_SAVED.equals(doaRequest.getStatus())) {
			return modelAndView;
		}
		
		OrderDTO orderDto = this.orderService.getOrder(doaRequest.getOrderId());
		if (orderDto == null || !"MOB".equals(orderDto.getOrderSumLob())) {
			return modelAndView;
		}
		if (!(ORDER_STATUS_FALLOUT.equals(orderDto.getOrderStatus()) 
				&& CHECK_POINT_FALLOUT.equals(orderDto.getCheckPoint()) 
				&& REASON_CD_DOA_SAVED.equals(orderDto.getReasonCd()))) {
			return modelAndView;
		}

		this.orderService.updateCcsOrderStatus(orderId, ORDER_STATUS_COMPLETED, null, null, null, null, user.getUsername());
		this.mobCcsDoaRequestService.updateDoaRequestStatus(doaRequest.getRowId(), REASON_CD_DOA_REJECTED);
		
		return modelAndView;
	}
}
