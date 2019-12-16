package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsFalloutUI;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.service.OrderService;

public class MobCcsFalloutController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	MobCcsOrderRemarkService mobCcsOrderRemarkService;
	
	OrderService orderService;
	
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
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException{
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		MobCcsFalloutUI mobCcsFalloutUI = new MobCcsFalloutUI();
		
		mobCcsFalloutUI.setStaffId(salesUserDto.getUsername());
		
		return mobCcsFalloutUI;
		
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		MobCcsFalloutUI mobCcsFalloutUI = (MobCcsFalloutUI) command;
		String orderId = (String) request.getParameter("orderId");
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		String falloutCag = null;
		
		if ("A004".equalsIgnoreCase(mobCcsFalloutUI.getFalloutCode())) {
			falloutCag = "STOCK_FAIL";
		} else if ("C006".equalsIgnoreCase(mobCcsFalloutUI.getFalloutCode())) {
			falloutCag = "PAY_FAIL";
		} else if ("O002".equalsIgnoreCase(mobCcsFalloutUI.getFalloutCode())) {
			falloutCag = "DUM_BSK";
		}
		
		orderService.insertOrderFallout(orderId, salesUserDto.getUsername(),
				falloutCag, mobCcsFalloutUI.getFalloutCode(), mobCcsFalloutUI.getRemarks());
		
		OrderDTO dto = new OrderDTO();
		
		dto.setOrderId(orderId);
		dto.setReasonCd(mobCcsFalloutUI.getFalloutCode());
		dto.setLastUpdateBy(salesUserDto.getUsername());
		
		orderService.updateOrderFallOut(dto);
		
		return new ModelAndView(new RedirectView("mobccsordersearch.html"));
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		
		Map referenceData = new HashMap<String, List<String>>();
		
		Map<String, List<CodeLkupDTO>> entityCodeMap = LookupTableBean.getInstance().getCodeLkupList();
		List<CodeLkupDTO> falloutCodeList = new ArrayList<CodeLkupDTO>(); 		
		
		for (CodeLkupDTO dto : entityCodeMap.get("FNS_ORD_FALLOUT_CODE")) {
			falloutCodeList.add(dto);
		}
				
		String orderId = (String) request.getParameter("orderId");
		
		referenceData.put("falloutCodeList", falloutCodeList);
		referenceData.put("orderId", orderId);
		
		return referenceData;
	}
	
}
