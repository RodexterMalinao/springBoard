package com.bomwebportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.csportal.object.CsldInqArq;
import com.bomwebportal.csportal.service.CsPortalService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CsPortalController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private CsPortalService csPortalService;

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String type = request.getParameter("type");
		String dt = request.getParameter("dt");
		String dn = request.getParameter("dn");
		JSONArray jsonArray = new JSONArray();
		
		if (type.equalsIgnoreCase("idck")) {
			
			CsldInqArq result = csPortalService.idCheck(dt, dn, null, null);
			
			GsonBuilder builder = new GsonBuilder();
	        Gson gson = builder.create();
	        logger.info("CSPortal Inquiry Result: " + gson.toJson(result));
	        return new ModelAndView("ajax_csportal", "gson", gson.toJson(result));
		}
		
		return new ModelAndView("ajax_csportal", "jsonArray",	jsonArray);
	}



	public CsPortalService getCsPortalService() {
		return csPortalService;
	}



	public void setCsPortalService(CsPortalService csPortalService) {
		this.csPortalService = csPortalService;
	}

}
