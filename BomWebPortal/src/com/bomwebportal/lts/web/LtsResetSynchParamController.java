package com.bomwebportal.lts.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.lts.service.LtsJobs;

public class LtsResetSynchParamController extends SimpleFormController {

	LtsJobs ltsJobs = null;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
 	    ModelAndView modelAndView = new ModelAndView("ltsresetsynchparam");
		try {
			ltsJobs.refreshParam();				
  		   modelAndView.addObject("message", "OK");
		} catch (Exception e) {
	  	   modelAndView.addObject("message", e.getMessage());			
		}
		
		return modelAndView;
	}

	public LtsJobs getLtsJobs() {
		return ltsJobs;
	}

	public void setLtsJobs(LtsJobs ltsJobs) {
		this.ltsJobs = ltsJobs;
	}



}
