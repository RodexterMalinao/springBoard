package com.bomwebportal.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;

public class OrderRemarkController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobCcsOrderRemarkService mobCcsOrderRemarkService;
	
	public MobCcsOrderRemarkService getMobCcsOrderRemarkService() {
		return mobCcsOrderRemarkService;
	}

	public void setMobCcsOrderRemarkService(
			MobCcsOrderRemarkService mobCcsOrderRemarkService) {
		this.mobCcsOrderRemarkService = mobCcsOrderRemarkService;
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		logger.info("OrderRemarkController@formBackingObject called");
		Date now = new Date();
		OrderRemarkDTO orderRemarkDto = new OrderRemarkDTO();
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");	
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		orderRemarkDto.setOrderId(orderId);
		orderRemarkDto.setCreateBy(salesUserDto.getUsername());
		orderRemarkDto.setCreateDate(now);
		orderRemarkDto.setLastUpdBy(salesUserDto.getUsername());
		orderRemarkDto.setLastUpdDate(now);
		return orderRemarkDto;
	}
	
	protected ModelAndView onSubmit(
			HttpServletRequest request,	HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		
		OrderRemarkDTO orderRemarkDto = (OrderRemarkDTO) command;
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView = new ModelAndView("orderremark");
		List<OrderRemarkDTO> remarkList = mobCcsOrderRemarkService.getPTOrderRemarkDTO(orderId);
		modelAndView.addObject("remarkList", remarkList);

		if ("CREATE".equalsIgnoreCase(orderRemarkDto.getActionType())){
			orderRemarkDto.setActionType("CREATE");
		} else if ("SAVE".equalsIgnoreCase(orderRemarkDto.getActionType())){
			int isSuccess = mobCcsOrderRemarkService.insertPTOrderRemark(salesUserDto.getUsername(), 
					orderRemarkDto.getOrderId(), orderRemarkDto.getRemark());
			if (isSuccess == 1) {
				orderRemarkDto.setActionType("success");
				modelAndView = new ModelAndView(new RedirectView("orderremark.html?orderId="+orderId));
				request.getSession().setAttribute("error", "NONE");
			} else {
				orderRemarkDto.setActionType("CREATE");
			}
		} else if ("QUIT".equalsIgnoreCase(orderRemarkDto.getActionType())) {
			orderRemarkDto.setActionType(null);
			request.getSession().setAttribute("error", null);
		}
		
		request.getSession().setAttribute("actionType", orderRemarkDto.getActionType());
		request.getSession().setAttribute("remarkList", remarkList);
		modelAndView.addObject("orderRemark", orderRemarkDto);
		request.getSession().setAttribute("orderRemark", orderRemarkDto);

		return modelAndView;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("OrderRemarkController ReferenceData called");
		
		Map referenceData = new HashMap<String, List<String>>();
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		request.getSession().setAttribute("user", salesUserDto);
		
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		List<OrderRemarkDTO> remarkList = mobCcsOrderRemarkService.getPTOrderRemarkDTO(orderId);
		referenceData.put("remarkList", remarkList);

		if ("success".equalsIgnoreCase((String)request.getSession().getAttribute("actionType")) &&
			request.getSession().getAttribute("error") != null) {
			//keep and display success message once
			request.getSession().setAttribute("error", null);
		} else {
			//reset session
			request.getSession().setAttribute("actionType", null);
			request.getSession().setAttribute("error", null);
		}
		
		return referenceData;
	}
}