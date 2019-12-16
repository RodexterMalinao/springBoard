package com.bomwebportal.sbs.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.sbs.dto.StOrderPosSmDTO;
import com.bomwebportal.sbs.service.SbsOrderService;
import com.bomwebportal.sbs.validator.StOrderPosSmValidator;
import com.bomwebportal.service.OrderService;

@Controller
public class SbsPosSmInputController {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private SbsOrderService sbsOrderService;
	
	@ModelAttribute
	private void referenceData(String orderId, Model model) {
		OrderDTO orderDTO = orderService.getOrderWithPaidAmount(orderId);
		List<StOrderPosSmDTO> posSms = sbsOrderService.getOrderPosSms(orderId);
		//List<CodeLkupDTO> smTypeList = LookupTableBean.getInstance().getCodeLkupList().get("ORD_STATUS"); // temp get sth ....
		model.addAttribute("order", orderDTO);
		model.addAttribute("posSms", posSms);
		//model.addAttribute("smTypeList", smTypeList);
	}
	
	@ModelAttribute("possm")
	private StOrderPosSmDTO formBackingObject(String orderId) {
		
		StOrderPosSmDTO possm = new StOrderPosSmDTO();
		possm.setOrderId(orderId);
		return possm;
		
	}
	
	@RequestMapping(value="/sbspossminput", method=RequestMethod.GET)
	public String showForm(String orderId, Model model) {
		
		
		
		System.out.println(model.asMap());
		return "sbspossminput";
	}

	@RequestMapping(value="/sbspossminput", method=RequestMethod.POST)
	public String submit(String orderId, @ModelAttribute("possm")StOrderPosSmDTO possm, BindingResult result, HttpSession session, Model model) {
		
		
		new StOrderPosSmValidator().validate(possm, result);
		
		if (result.hasErrors()) {
			return "sbspossminput";
		}
		
		BomSalesUserDTO loginUserDto = (BomSalesUserDTO) session.getAttribute("bomsalesuser");
		OrderDTO order = (OrderDTO)model.asMap().get("order");
		String username = loginUserDto.getUsername();

		
		possm.setOrderId(orderId);
		possm.setCreateBy(username);
		possm.setLastUpdBy(username);
		sbsOrderService.insertStOrderPosSm(possm);
		
		sbsOrderService.insertOrderRemark(orderId, username + " INPUTED POS SM", username);
		
		String type = possm.getSmType();
		if ("03".equals(order.getOrderStatus())
				&& ("9".equals(type) || "10".equals(type))) {
			reportCancel(orderId, loginUserDto.getUsername());
		}
		
		return "redirect:sbspossminput.html?orderId="+orderId;
	}
	
	
	
	private void reportCancel(String orderId, String updateBy) {
		orderService.backupOrder(orderId);
		sbsOrderService.cancelOrder(orderId, "", updateBy);
	}
	
}
