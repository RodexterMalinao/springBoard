package com.bomwebportal.sbs.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.sbs.service.SbsOrderService;
import com.bomwebportal.service.OrderService;

@Controller
public class SbsOrderCancelRequestController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private SbsOrderService sbsOrderService;

	
	@ModelAttribute
	public void referenceData(String orderId, Model model) {
		OrderDTO orderDTO = orderService.getOrder(orderId);
		List<CodeLkupDTO> cancelCodes = LookupTableBean.getInstance().getCodeLkupList().get("ORD_CANCEL_CODE");
		model.addAttribute("order", orderDTO);
		model.addAttribute("cancelCodes", cancelCodes);
	}
	
	
	@RequestMapping(value="/sbsordercancelrequest", method=RequestMethod.GET)
	public String showForm(String orderId, @ModelAttribute("command")Form command, Model model) {
		
		return "sbsordercancelrequest";
	}
	
	@RequestMapping(value="/sbsordercancelrequest", method=RequestMethod.POST)
	public String submit(String orderId, @ModelAttribute("command")Form command, HttpSession session, Model model) {
		
		BomSalesUserDTO loginUserDto = (BomSalesUserDTO) session.getAttribute("bomsalesuser");
		orderService.backupOrder(orderId);

		String reasonCd = command.getReasonCd();
		sbsOrderService.updateOrderStatus(orderId, "03", reasonCd, loginUserDto.getUsername());
		sbsOrderService.insertOrderRemark(orderId, loginUserDto.getUsername() + " REQUEST CANCEL ORDER", loginUserDto.getUsername());
		//model.addAttribute("done", true);
		return "sbsorderdetail_closedialog";
	}
	
	
	
	
	public static class Form {
		String reasonCd;
		
		public Form(){}

		public String getReasonCd() {
			return reasonCd;
		}

		public void setReasonCd(String reasonCd) {
			this.reasonCd = reasonCd;
		};
		
	}
}
