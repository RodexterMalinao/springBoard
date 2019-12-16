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
import com.bomwebportal.ims.service.ValidateHKIDService;
import com.bomwebportal.util.Utility;

public class LogCeksController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsPaymentService paymentService;

	

	public ImsPaymentService getPaymentService() {
		return paymentService;
	}



	public void setPaymentService(ImsPaymentService paymentService) {
		this.paymentService = paymentService;
	}



	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		logger.debug("LogCeksController");
    	String cardnum = request.getParameter("cardnum");

    	OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
    	BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	

    	paymentService.logCreditCardInfo(sessionOrder.getOrderId(),sessionOrder.getLoginId(),"", cardnum, user.getUsername());
    		
    
    	
		return new ModelAndView("ajax_validatehkid");
	}
}
