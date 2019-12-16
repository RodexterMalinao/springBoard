package com.bomwebportal.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.VasMaintenanceDTO;
import com.bomwebportal.service.VasMaintenanceService;

public class VasMaintenanceSearchController implements Controller {
	
protected final Log logger =  LogFactory.getLog(getClass());
	
	private VasMaintenanceService vasMaintenanceService;

	public VasMaintenanceService getVasMaintenanceService() {
		return vasMaintenanceService;
	}

	public void setVasMaintenanceService(VasMaintenanceService vasMaintenanceService) {
		this.vasMaintenanceService = vasMaintenanceService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String offerCd = request.getParameter("offerCd");
		List<VasMaintenanceDTO> list = vasMaintenanceService.getVasMaintenanceDTO(offerCd);

		return new ModelAndView("ajax_vasMaintenanceSearch", "jsonArray", JSONArray.fromObject(list));

	}

}
