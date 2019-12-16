package com.bomwebportal.lts.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.lts.util.LtsConstant;

public class OrderAmendClearSessionController extends SimpleFormController {
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_ORDER_AMEND);
		request.getSession().removeAttribute(LtsConstant.SESSION_LTS_DUMMY_SB_ORDER);
		
		return null;
	}
}
