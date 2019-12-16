package com.bomwebportal.mob.ccs.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.service.MobCcsOrderSearchService;

public class MobCcsOrderSearchLookupController implements Controller{
	
	MobCcsOrderSearchService mobCcsOrderSearchService;

	/**
	 * @return the mobCcsOrderSearchService
	 */
	public MobCcsOrderSearchService getMobCcsOrderSearchService() {
		return mobCcsOrderSearchService;
	}

	/**
	 * @param mobCcsOrderSearchService the mobCcsOrderSearchService to set
	 */
	public void setMobCcsOrderSearchService(
			MobCcsOrderSearchService mobCcsOrderSearchService) {
		this.mobCcsOrderSearchService = mobCcsOrderSearchService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String ocid = request.getParameter("ocid");
		
		String status = mobCcsOrderSearchService.getOcidStatus(ocid);
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add("{\"status\":\"" + status + "\"}");
		
		return new ModelAndView("ajax_mobCcsOrderSearchLookup", "jsonArray",	jsonArray);
		
	}
	
}
