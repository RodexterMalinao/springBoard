package com.bomwebportal.mob.ccs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;

public class MobCcsStockAssgnController extends SimpleFormController {

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		return new StockAssgnDTO();

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		return new ModelAndView(new RedirectView("mobccsstockassgn.html"));
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobccsstockmodelController ReferenceData called");

		Map referenceData = new HashMap<String, List<String>>();

		return referenceData;
	}
	
}
