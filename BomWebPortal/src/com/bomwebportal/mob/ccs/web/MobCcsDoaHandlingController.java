package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtDTO;
import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;
import com.bomwebportal.mob.ccs.dto.StockManualDetailDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.StockManualDetailUI;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.mob.ccs.service.StockManualDetailService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsDoaHandlingController extends SimpleFormController {

	private StockManualDetailService stockManualDetailService;
	
	private DeliveryService deliveryService;
	
	private OrderService orderService;
	
	private MobCcsMrtService mobCcsMrtService;
	
	private MobCcsOrderRemarkService mobCcsOrderRemarkService;
	
	/**
	 * @return the mobCcsOrderRemarkService
	 */
	public MobCcsOrderRemarkService getMobCcsOrderRemarkService() {
		return mobCcsOrderRemarkService;
	}

	/**
	 * @param mobCcsOrderRemarkService the mobCcsOrderRemarkService to set
	 */
	public void setMobCcsOrderRemarkService(
			MobCcsOrderRemarkService mobCcsOrderRemarkService) {
		this.mobCcsOrderRemarkService = mobCcsOrderRemarkService;
	}

	/**
	 * @return the mobCcsMrtService
	 */
	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	/**
	 * @param mobCcsMrtService the mobCcsMrtService to set
	 */
	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
	}

	/**
	 * @return the orderService
	 */
	public OrderService getOrderService() {
		return orderService;
	}

	/**
	 * @param orderService the orderService to set
	 */
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * @return the deliveryService
	 */
	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	/**
	 * @param deliveryService the deliveryService to set
	 */
	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

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
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		logger.info("MobCcsDoaHandlingController onSubmit called");
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		StockManualDetailUI dto = (StockManualDetailUI) command;
		
//		deliveryService.updateOrderDeliveryDateTime(dto.getOrderId(), Utility.string2Date(dto.getDeliveryDateStr()), 
//				dto.getDeliveryTimeSlot(), salesUserDto.getUsername());
//		
		orderService.updateOrderSrvReqDate(dto.getOrderId(), Utility.string2Date(dto.getSrvReqDateStr()), 
				salesUserDto.getUsername());
		
		if(dto.getMnpInd().equalsIgnoreCase("Y")) {
			mobCcsMrtService.updateMnpTicketNum(dto.getOrderId(), dto.getMnpTicketNum(), Utility.string2Date(dto.getSrvReqDateStr()), salesUserDto.getUsername());
		}
		
		mobCcsMrtService.updateMrtServiceDate(dto.getOrderId(), dto.getMnpInd(), Utility.string2Date(dto.getSrvReqDateStr()), salesUserDto.getUsername());
				
		OrderRemarkDTO orderRemarkDTO = new OrderRemarkDTO();
	    Date currentDate = new Date();
		orderRemarkDTO.setOrderId(dto.getOrderId());
		
		orderRemarkDTO.setRemark(salesUserDto.getUsername()+" DOA HANDLING");
				
		orderRemarkDTO.setCreateBy(salesUserDto.getUsername());
		orderRemarkDTO.setCreateDate(currentDate);
		orderRemarkDTO.setLastUpdBy(salesUserDto.getUsername());
		orderRemarkDTO.setLastUpdDate(currentDate);
		mobCcsOrderRemarkService.insertOrderRemarkDTO(orderRemarkDTO);
		
		orderService.updateOrderReasonCode(dto.getOrderId(), "N002", salesUserDto.getUsername());
		
		orderService.backupOrder(dto.getOrderId());
		
		return new ModelAndView(new RedirectView("mobccsdoahandling.html?orderId=" + dto.getOrderId() + "&completed=YES"));
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		StockManualDetailUI stockManualDetailUI = new StockManualDetailUI();
		MRTUI mrtUI = new MRTUI();
		
		String orderId = request.getParameter("orderId");
		
		List<StockManualDetailDTO> list = new ArrayList<StockManualDetailDTO>();
		list = stockManualDetailService.getStockManualDetail(orderId);
		
		String location = null; 
		
		for (int i = 0; i < list.size(); i++) {
			StockManualDetailDTO dto = list.get(i);
			if (dto.getItemType().equalsIgnoreCase("HANDSET")) {
				location = dto.getLocation();
			}
			
			if ((location == null || location.isEmpty()) && i > 0) {
				location = dto.getLocation();
			}
		}
		
		if (location == null || location.isEmpty()) {
			location = "NONE";
		}
		
		DeliveryUI deliveryUI = deliveryService.getMobCcsDelivery(orderId);
		
		OrderDTO orderDTO = orderService.getOrder(orderId);
		if (orderDTO != null && orderDTO.getSrvReqDate() != null) {
			stockManualDetailUI.setSrvReqDateStr(Utility.date2String(orderDTO.getSrvReqDate(), BomWebPortalConstant.DATE_FORMAT));
			stockManualDetailUI.setMnpInd(orderDTO.getMnpInd());
		}
		
		ArrayList<MobCcsMrtBaseDTO> mobCcsMrtDtoList = mobCcsMrtService.getMobCcsMrtDTO(orderId);
	    if (mobCcsMrtDtoList != null && !mobCcsMrtDtoList.isEmpty()) {
	    	mrtUI = mobCcsMrtService.mrtDtoChangeToUiDto(mobCcsMrtDtoList);
	    }
		
	    stockManualDetailUI.setMnpTicketNum(mrtUI.getMnpTicketNum());
	    
	    String slotType = "SCH";
	    /*if (StringUtils.startsWith(orderId, "CSBSM")) {
			slotType = "OSF";
		}*/
		
		List<String[]> timeSlot = deliveryService.findTimeSlot(deliveryUI.getDistrictCode(),slotType,orderDTO.getAppInDate());
		List<CodeLkupDTO> codeList = new ArrayList<CodeLkupDTO>();
		
		if (timeSlot != null) {
			
			for (String[] s : timeSlot) {
				
				CodeLkupDTO dto = new CodeLkupDTO();
		
				dto.setCodeId(s[0]);
				dto.setCodeDesc(s[1] + "-" + s[2]);
				
				codeList.add(dto);
			}
		}
				
		request.setAttribute("manualDetailList", list);
		request.setAttribute("orderId", orderId);
		request.setAttribute("codeList", codeList);
		request.setAttribute("deliveryUI", deliveryUI);
		request.setAttribute("orderDTO", orderDTO);
		request.setAttribute("mrtUI", mrtUI);
		request.setAttribute("location", location);
		
		return stockManualDetailUI;
	
	}
	
}
