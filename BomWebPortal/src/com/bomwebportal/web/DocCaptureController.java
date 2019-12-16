package com.bomwebportal.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.service.OrdDocService;

public class DocCaptureController extends AbstractController {

	private OrdDocService ordDocService;
	
	public OrdDocService getOrdDocService() {
		return ordDocService;
	}

	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}
	@Override
	protected ModelAndView handleRequestInternal(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String orderId = request.getParameter("orderId");

		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession()
			.getAttribute("bomsalesuser");
		
		List<OrdDocAssgnDTO> docReq = ordDocService.getRequiredDoc(orderId);
		List<OrdDocDTO> docRecord = ordDocService.getOrdDoc(orderId);
		
		
		ModelAndView view = new ModelAndView("doccapture");
		view.addObject("orderId", orderId);
		view.addObject("requiredDocList", docReq);
		view.addObject("capturedRecordList", docRecord);
		
		return view;
	}

}
