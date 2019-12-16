package com.bomwebportal.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import com.bomwebportal.dto.ui.VasMaintenanceUI;
import com.bomwebportal.service.VasMaintenanceService;

public class VasMaintenanceController extends SimpleFormController {
	
	protected final Log logger =  LogFactory.getLog(getClass());
	
	private VasMaintenanceService vasMaintenanceService;

	public VasMaintenanceService getVasMaintenanceService() {
		return vasMaintenanceService;
	}

	public void setVasMaintenanceService(VasMaintenanceService vasMaintenanceService) {
		this.vasMaintenanceService = vasMaintenanceService;
	}
	
	protected Object formBackingObject(HttpServletRequest request) {
		//String id = request.getParameter("id");
				
		VasMaintenanceUI vasMaintenanceUI = new VasMaintenanceUI();
		vasMaintenanceUI.setRecurrentAmt("0");
		vasMaintenanceUI.setOneTimeAmt("0");
		
		return vasMaintenanceUI;
		
	}
	
	
	public Map referenceData(HttpServletRequest request) {
		Map<String, Object> referenceData =  new HashMap<String, Object>();
		
		// item types
		List<String> itemTypes = Arrays.asList("VAS", "BVAS","SIM","BFEE","AP_INC","MNP_INC","RP");
		referenceData.put("itemTypes", itemTypes);
		
		return referenceData;
	}
	
	protected ModelAndView onSubmit(HttpServletRequest request
			, HttpServletResponse response
			, Object command
			, BindException errors) throws Exception {
		VasMaintenanceUI form = (VasMaintenanceUI) command;
		Long itemId = this.vasMaintenanceService.createVasItem(form);
		ModelAndView modelAndView = new ModelAndView(new RedirectView("vasmaintenance.html"));
		modelAndView.addObject("complete", true);
		modelAndView.addObject("itemId", itemId);
		return modelAndView;
	}
}
