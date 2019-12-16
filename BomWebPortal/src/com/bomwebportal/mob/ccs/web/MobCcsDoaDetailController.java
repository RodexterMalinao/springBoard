package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;
import com.bomwebportal.mob.ccs.dto.StockManualDetailDTO;
import com.bomwebportal.mob.ccs.dto.ui.StockManualDetailUI;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.mob.ccs.service.StockManualDetailService;
import com.bomwebportal.service.OrderService;

public class MobCcsDoaDetailController extends SimpleFormController {

	private StockManualDetailService stockManualDetailService;
	
	private OrderService orderService;
	
	private MobCcsOrderRemarkService mobCcsOrderRemarkService;
	
	public MobCcsOrderRemarkService getMobCcsOrderRemarkService() {
		return mobCcsOrderRemarkService;
	}

	public void setMobCcsOrderRemarkService(
			MobCcsOrderRemarkService mobCcsOrderRemarkService) {
		this.mobCcsOrderRemarkService = mobCcsOrderRemarkService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public StockManualDetailService getStockManualDetailService() {
		return stockManualDetailService;
	}

	public void setStockManualDetailService(
			StockManualDetailService stockManualDetailService) {
		this.stockManualDetailService = stockManualDetailService;
	} 
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		logger.info("MobCcsDoaDetailController onSubmit called");
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		StockManualDetailUI dto = (StockManualDetailUI) command;
		
		OrderRemarkDTO orderRemarkDTO = new OrderRemarkDTO();
	    Date currentDate = new Date();
		orderRemarkDTO.setOrderId(dto.getOrderId());
		
		orderRemarkDTO.setRemark(salesUserDto.getUsername()+" DOA HANDLING");
				
		orderRemarkDTO.setCreateBy(salesUserDto.getUsername());
		orderRemarkDTO.setCreateDate(currentDate);
		orderRemarkDTO.setLastUpdBy(salesUserDto.getUsername());
		orderRemarkDTO.setLastUpdDate(currentDate);
		mobCcsOrderRemarkService.insertOrderRemarkDTO(orderRemarkDTO);
		
//		orderService.updateOrderReasonCode(dto.getOrderId(), "N002", salesUserDto.getUsername());
		
		orderService.backupOrder(dto.getOrderId());
		
		return new ModelAndView(new RedirectView("mobccsdoahandling.html?orderId=" + dto.getOrderId() + "&completed=YES"));
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		StockManualDetailUI stockManualDetailUI = new StockManualDetailUI();
		
		String orderId = request.getParameter("orderId");
				
		List<StockManualDetailDTO> list = new ArrayList<StockManualDetailDTO>();
		list = stockManualDetailService.getDoaDetail(orderId);
		
		String location = "NONE";
		
		for (StockManualDetailDTO dto : list ) {
			if (dto.getLocation() != null || !dto.getLocation().isEmpty()) {
				location = dto.getLocation();
				break;
			}
		}
		
		request.setAttribute("orderId", orderId);
		request.setAttribute("doaDetailList", list);
		request.setAttribute("location", location);
		
		return stockManualDetailUI;
	}
}
