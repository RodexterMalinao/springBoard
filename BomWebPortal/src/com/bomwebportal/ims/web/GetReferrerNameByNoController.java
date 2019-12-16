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
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsPaymentService;


public class GetReferrerNameByNoController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsPaymentService imsPaymentService;
	

	public ImsPaymentService getImsPaymentService() {
		return imsPaymentService;
	}

	public void setImsPaymentService(ImsPaymentService imsPaymentService) {
		this.imsPaymentService = imsPaymentService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		JSONArray jsonArray = new JSONArray();
    	
		String referrerStaffNo = request.getParameter("referrerStaffNo");
		logger.info("referrerStaffNo:"+referrerStaffNo);
		String referrerStaffName = "";
		
    	if(referrerStaffNo != null && !("").equals(referrerStaffNo)){
    		referrerStaffName = imsPaymentService.getReferrerStaffNameByNo(referrerStaffNo);
    	}
    	
    	if(referrerStaffName == null){
    		referrerStaffName = "";
    	}
    	
    	jsonArray.add("{\"referrerStaffName\":\"" + referrerStaffName
    					+ "\"}");
    	
		return new ModelAndView("ajax_", "jsonArray", jsonArray);
	}
}
