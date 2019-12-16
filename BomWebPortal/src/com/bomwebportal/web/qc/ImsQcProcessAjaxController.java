package com.bomwebportal.web.qc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.service.qc.ImsDSQCService;



public class ImsQcProcessAjaxController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsDSQCService imsDSQCService; 
	
	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}
	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}
    
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("ImsQcProcessAjaxController handle Request: (start) ");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String rtnValue;
		rtnValue = "hello world";
		String orderIds = request.getParameter("oid");
		String remarks = request.getParameter("remark");
		logger.info("ImsQcProcessAjaxController remark: " +  remarks);
		
		try {
				imsDSQCService.updateQcRemarks(orderIds,user,remarks);			
		} catch (Exception e){
			e.printStackTrace();
			return null;	//tmp 
		}
		JSONArray jsonArray = new JSONArray();

		jsonArray.add(
				"{\"testing\":\""	+ rtnValue +
				"\"}");
		
		logger.info("jsonArray : " + jsonArray);
		logger.info("ImsQcProcessAjaxController handle Request: (end)");

		
		return new ModelAndView("ajax_", "jsonArray",	jsonArray);
	}
}
