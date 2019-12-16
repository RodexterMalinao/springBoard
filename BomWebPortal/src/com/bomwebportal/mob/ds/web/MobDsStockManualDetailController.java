package com.bomwebportal.mob.ds.web;

import java.util.ArrayList;
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
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockManualDetailDTO;
import com.bomwebportal.mob.ccs.dto.ui.StockManualAssgnUI;
import com.bomwebportal.mob.ccs.dto.ui.StockManualDetailUI;
import com.bomwebportal.mob.ccs.service.StockManualDetailService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.OrderService;

public class MobDsStockManualDetailController extends SimpleFormController {

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
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		StockManualDetailUI sessionStockManualDetailUi
			= (StockManualDetailUI) MobCcsSessionUtil.getSession(request, "stockManualDetailUi");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		if ("Y".equalsIgnoreCase((String)request.getSession().getAttribute("save"))) {
			request.getSession().setAttribute("save", "X");
		} else {
			request.getSession().setAttribute("save", null);
		}
		
		String actionType = null;
		String itemCode = null;
		String itemSerial = null;
		String orderId = null;
		
		if (sessionStockManualDetailUi == null) {
			sessionStockManualDetailUi = new StockManualDetailUI();
			orderId = request.getParameter("orderId");
			if (StringUtils.contains(orderId, ',')) {
				int commapos = orderId.indexOf(",");
				orderId = orderId.substring(0, commapos);
			}
			sessionStockManualDetailUi.setOrderId(orderId);
	    	
		} else {
			actionType = sessionStockManualDetailUi.getActionType();
			itemCode = sessionStockManualDetailUi.getItemCode();
			itemSerial = sessionStockManualDetailUi.getItemSerial();
			orderId = request.getParameter("orderId");
			if (StringUtils.contains(orderId, ',')) {
				int commapos = orderId.indexOf(",");
				orderId = orderId.substring(0, commapos);
			}
			sessionStockManualDetailUi.setOrderId(orderId);
		}

		if (actionType != null && !"".equals(actionType.trim()) && orderId != null) {
			
			if ("DEASSIGN".equalsIgnoreCase(actionType.trim())) {
			
				String ocid = orderService.getOcidByOrderID(orderId);
				if (StringUtils.isBlank(ocid)) {
					StockManualAssgnUI dto = stockManualDetailService.getAssignedItemSerialStatus(orderId, itemCode);
					dto.setOrderId(orderId);
					dto.setItemCode(itemCode);
					stockManualDetailService.dsDeassignPerItem(dto, orderId, user.getUsername(), "ASSIGN");
				} else {
					sessionStockManualDetailUi.setErrMsg("Order submitted to BOM, stock couldn't be changed!!!");
					request.setAttribute("errMsg", "Order submitted to BOM, stock couldn't be changed!!!");
				}
				actionType = "";
			
			}
		
		}

		List<StockManualDetailDTO> list = new ArrayList<StockManualDetailDTO>();
		list = stockManualDetailService.getStockManualDetail(orderId);
		
		List<StockManualDetailDTO> tempList = new ArrayList<StockManualDetailDTO>();
		for (StockManualDetailDTO s: list) {
			List<CodeLkupDTO> colourList = stockManualDetailService.getItemColorlist(s.getItemCode());
			StockManualDetailDTO temp = new StockManualDetailDTO();
			if (colourList == null || colourList.isEmpty()) {
				temp = stockManualDetailService.getPreviousItemSingleItemCode(orderId, s.getItemCode());
			} else {
				temp = stockManualDetailService.getPreviousItem(orderId, s.getItemCode());
			}
			if (temp != null) {
				s.setOldItemCode(temp.getOldItemCode());
				s.setOldSerialNum(temp.getOldSerialNum());
			}
			tempList.add(s);
		}
		list = tempList;
		
		request.setAttribute("actionType", actionType);
		request.setAttribute("orderId", orderId);
		request.setAttribute("stockManualDetailList", list);
		
		sessionStockManualDetailUi.setActionType("");
		
		return sessionStockManualDetailUi;

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		logger.info("MobDsStockManualDetailController onSubmit called");
		
		StockManualDetailUI dto = (StockManualDetailUI) command;
		MobCcsSessionUtil.setSession(request, "stockManualDetailUi", dto);
		
		if (dto != null && dto.getActionType() != null 
				&& "QUIT".equalsIgnoreCase(dto.getActionType())
				&& dto.getOrderId() != null) {
			if (StringUtils.contains(dto.getOrderId(), ',')) {
				int commapos = dto.getOrderId().indexOf(",");
				dto.setOrderId(dto.getOrderId().substring(0, commapos));
			}

			return new ModelAndView(new RedirectView("mobdsstockmanual.html"));
		}
		
		return new ModelAndView(new RedirectView("mobdsstockmanualdetail.html?orderId=" + dto.getOrderId()));
	}
	
}
