package com.bomwebportal.lts.web;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.ims.dto.ui.CcLtsImsOrderEnquiryUI;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.service.ImsOrderService;
import com.google.gson.Gson;

public class LtsBackDoorController extends AbstractController{

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return new ModelAndView("ltsbackdoor");
	}
	

											
}
