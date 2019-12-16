package com.bomwebportal.ims.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.google.gson.Gson;

public class ImsKeepAliveController  implements Controller {
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		
		
		OrderImsUI order = new Gson().fromJson(request.getParameter("test"), OrderImsUI.class);
		 
		request.getSession().setAttribute("order_for_amendment", order);
		
		System.out.println("ImsKeepAliveController called!");
		return null;
	}

}
