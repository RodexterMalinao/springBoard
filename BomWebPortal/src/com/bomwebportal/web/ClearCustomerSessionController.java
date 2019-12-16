package com.bomwebportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;

public class ClearCustomerSessionController implements Controller{

    protected final Log logger = LogFactory.getLog(getClass());

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//not use in next version 20111026
		/******************* Update order status and remove session *******************************/	     		
     	//Delete Session
	
     	request.getSession().removeAttribute("customer");
     	request.getSession().removeAttribute("payment");
     	request.getSession().removeAttribute("MNP");
     	request.getSession().removeAttribute("MobileSimInfo");
     	request.getSession().removeAttribute("orderIdSession");
    	request.getSession().removeAttribute("customerTierSession");
    	request.getSession().removeAttribute("baskettypeSession");
    	request.getSession().removeAttribute("rptypeSession");        	
    	request.getSession().removeAttribute("selectedVasItemList");
    	request.getSession().removeAttribute("componentList");
    	//add eliot 20110824
    	request.getSession().removeAttribute("signoffDtoSession");
    	
 	/******************* Update order status and remove session(END) ***************************/ 
    	
    	BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	
        return new ModelAndView("welcome", "userId", user.getUsername());
	}
}
