package com.bomwebportal.sbs.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.sbs.dto.VirtualPrepaidResult;
import com.bomwebportal.sbs.service.VirtualPrepaidService;
import com.bomwebportal.service.OrdEmailReqService;

@Controller
public class SbsOrdEmailHistoryController {

	@Autowired
	private OrdEmailReqService ordEmailReqService;
		
	@RequestMapping(value="/sbsordemailhistory", method=RequestMethod.GET)
	public String showHistory(String orderId, Model model) {
		
		
		List<OrdEmailReqDTO> ordEmailReqList = ordEmailReqService.getOrdEmailReqDTOALLByOnlyOrderId(orderId);
		
		model.addAttribute("ordEmailReqList", ordEmailReqList);
		return "sbsordemailhistory";
	}
	
}
