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

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ValidateFixedLineService;

public class ValidateFixedLineController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ValidateFixedLineService validateFixedLineService;

	public ValidateFixedLineService getValidateFixedLineService() {
		return validateFixedLineService;
	}

	public void setValidateFixedLineService(
			ValidateFixedLineService validateFixedLineService) {
		this.validateFixedLineService = validateFixedLineService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		JSONArray jsonArray = new JSONArray();
    	
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		String srvNum = request.getParameter("fixedLineNo");
    	String serbdyno = sessionOrder.getInstallAddress().getSerbdyno();
    	String unit = sessionOrder.getInstallAddress().getUnitNo();
    	String floor = sessionOrder.getInstallAddress().getFloorNo();
    	
    	String valid = "N";
    	if(srvNum == null || srvNum.equals("")){
    		valid = "";
    		
    		jsonArray.add("{\"valid\":\"" + valid
					+ "\"}");
    	}else{
    		valid = validateFixedLineService.validateFixedLine(srvNum, serbdyno, unit, floor);
    		
    		jsonArray.add("{\"valid\":\"" + valid
						+ "\"}");
    	}
    	
		return new ModelAndView("ajax_validatefixedline", "jsonArray", jsonArray);
	}
}
