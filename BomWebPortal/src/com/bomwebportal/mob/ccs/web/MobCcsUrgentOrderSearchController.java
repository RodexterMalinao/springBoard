package com.bomwebportal.mob.ccs.web;

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

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsUrgentOrderSearchUI;
import com.bomwebportal.mob.ccs.service.MobCcsUrgentOrderSearchService;

public class MobCcsUrgentOrderSearchController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	MobCcsUrgentOrderSearchService mobCcsUrgentOrderSearchService;
	
	/**
	 * @return the mobCcsUrgentOrderSearchService
	 */
	public MobCcsUrgentOrderSearchService getMobCcsUrgentOrderSearchService() {
		return mobCcsUrgentOrderSearchService;
	}

	/**
	 * @param mobCcsUrgentOrderSearchService the mobCcsUrgentOrderSearchService to set
	 */
	public void setMobCcsUrgentOrderSearchService(
			MobCcsUrgentOrderSearchService mobCcsUrgentOrderSearchService) {
		this.mobCcsUrgentOrderSearchService = mobCcsUrgentOrderSearchService;
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		
		Map referenceData = new HashMap<String, List<String>>();
		
		return referenceData;
	}
	
	protected ModelAndView onSubmit(
			HttpServletRequest request,	HttpServletResponse response, Object command,	BindException errors)
			throws Exception {
		
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		MobCcsUrgentOrderSearchUI ui = (MobCcsUrgentOrderSearchUI) command;
		
		ModelAndView modelAndView = new ModelAndView("mobccsurgentordersearch", model);
		
		List<OrderDTO> urgentList = null;
		
		if (!ui.getSearchDeliveryDate().isEmpty() || !ui.getSearchOrderId().isEmpty())	{
			urgentList = mobCcsUrgentOrderSearchService.getUrgentOrder(ui.getSearchDeliveryDate(), ui.getSearchOrderId());
		}
		
		modelAndView.addObject("urgentList", urgentList);
		
		return modelAndView;
	}
}
