package com.bomwebportal.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.service.ServiceSelectionService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class ServiceSelectionLookupController implements Controller{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ServiceSelectionService serviceSelectionService;

	public ServiceSelectionService getServiceSelectionService() {
		return serviceSelectionService;
	}

	public void setServiceSelectionService(
			ServiceSelectionService serviceSelectionService) {
		this.serviceSelectionService = serviceSelectionService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String jobList = request.getParameter("jobList");
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		String appDateStr = (String) request.getSession().getAttribute("appDate");
		String appDate = null ;
		if (null == appDateStr) {
			appDate = Utility.date2String(new Date(), BomWebPortalConstant.DATE_FORMAT);
			request.getSession().setAttribute("appDate", appDate);
		} else {
			appDate = appDateStr;
		}
		
		List<String[]> resultList = serviceSelectionService.getRatePlan2(jobList, appDate); 
		
		JSONArray jsonArray = new JSONArray();
		
			jsonArray.add("{\"basketType\":\""	+ "----------Select----------" 
					+ "\",\"basketTypeId\":\""	+ "NONE" + "\"}");	
			
		for (int i = 0; i < resultList.size(); i++) {
			jsonArray.add("{\"basketType\":\""	+ (resultList.get(i))[0] 
					+ "\",\"basketTypeId\":\""	+ (resultList.get(i))[1] + "\"}");
		}
		
		logger.info("jsonArray : " + jsonArray);

		return new ModelAndView("ajax_mobCcsCommonLookup", "jsonArray",	jsonArray);
	}
}
