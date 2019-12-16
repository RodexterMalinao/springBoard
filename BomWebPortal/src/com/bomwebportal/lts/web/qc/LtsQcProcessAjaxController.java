package com.bomwebportal.lts.web.qc;

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
import com.bomwebportal.lts.service.qc.LtsDsQcService;

public class LtsQcProcessAjaxController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected LtsDsQcService ltsDsQcService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("LtsQcProcessAjaxController handle Request: (start) ");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String rtnValue = "OK";
		String orderIds = request.getParameter("oid");
		String remarks = request.getParameter("remark");
		logger.info("LtsQcProcessAjaxController remark: " +  remarks);
		
		try{
			ltsDsQcService.updateQcRemarks(orderIds, remarks, user.getUsername());
		}catch(Exception e){
			e.printStackTrace();
			return null;	//tmp 
		}
		
		JSONArray jsonArray = new JSONArray();

		jsonArray.add(
				"{\"testing\":\""	+ rtnValue +
				"\"}");
		
		logger.info("jsonArray : " + jsonArray);
		logger.info("LtsQcProcessAjaxController handle Request: (end)");
		
		return new ModelAndView("ajax_", "jsonArray",	jsonArray);
	}

	public LtsDsQcService getLtsDsQcService() {
		return ltsDsQcService;
	}

	public void setLtsDsQcService(LtsDsQcService ltsDsQcService) {
		this.ltsDsQcService = ltsDsQcService;
	}

}
