package com.bomwebportal.ims.web;

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
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.bomwebportal.service.qc.ImsDSQCService;

public class ImsDsAjaxController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsDSQCService imsDSQCService; 
	
	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}
	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}
    
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("ImsDsAjaxController handle Request: ");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String salesType = request.getParameter("st");
		String location = request.getParameter("lo");
		String staffId = request.getParameter("sId");
		String rtnValue = null;
		String salesCode = null;
		
		logger.info("salesType : " + salesType);
		logger.info("location : " + location);
		logger.info("staffId : " + staffId);
		
		
		if(salesType.length()>0){
			//rtnValue = "hello world";
			try {
				salesCode = imsDSQCService.getSalesTypeCode(salesType);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BomSalesUserDTO amend = new BomSalesUserDTO();
			amend.setSalesType(salesCode);
			amend.setLocation(location);
			amend.setOrgStaffId(staffId);
			amend.setUsername(user.getUsername());
			
			try {
				if (imsDSQCService.isDcStaffExist(staffId)){			
					imsDSQCService.updateSalesTypeAndLocation(amend);
				}else{imsDSQCService.insertSalesTypeAndLocation(amend);}
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			user.setSalesType(salesType);
			user.setLocation(location);
			request.getSession().setAttribute("bomsalesuser", user);
			
		} else {
			rtnValue = null;
		}
		
		JSONArray jsonArray = new JSONArray();

		jsonArray.add(
				"{\"testing\":\""	+ salesCode +
				"\"}");
		
		logger.info("jsonArray : " + jsonArray);
		logger.info("ImsDsAjaxController handle Request: (end)");
		
		return new ModelAndView("ajax_", "jsonArray",	jsonArray);
	}
}
