package com.bomwebportal.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.IssueBankDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.mob.ds.dto.MobDsPaymentTransDTO;
import com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO;
import com.bomwebportal.service.PaymentService;;

public class CeksController extends SimpleFormController{
	protected final Log logger = LogFactory.getLog(getClass());
	
	public CeksController() {
	}

	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
		
		PaymentDTO currentPayment = (PaymentDTO)request.getSession().getAttribute("currentPayment");
		MobDsPaymentUpfrontDTO currentPaymentUpfront = (MobDsPaymentUpfrontDTO)request.getSession().getAttribute("currentPaymentUpfront");
		if ((currentPayment != null) && "Y".equals(currentPayment.getCeksSubmit().trim())) {
			logger.debug("currentPayment = " + currentPayment.getCeksSubmit());
			String token = (String)request.getParameter("v5");
			logger.debug("credit card token: "+ token);
			
			if (currentPayment != null) {
				currentPayment.setCreditCardNum(token);
			}
			
			request.getSession().setAttribute("currentPayment", currentPayment);

			String nextView = "payment.html";
			logger.info("Next View: " + nextView);
			
			//return new ModelAndView(new RedirectView(nextView));
			ModelAndView modelAndView = new ModelAndView(new RedirectView(nextView));
			if (request.getSession().getAttribute("sbuid") instanceof String) {
				modelAndView.addObject("sbuid", currentPayment.getUid());
			}
			return modelAndView;
		} else if ((currentPaymentUpfront != null) && !"".equals(currentPaymentUpfront.getCeksSubmit().trim())) {
			String token = (String)request.getParameter("v5");
			if (currentPaymentUpfront != null) {
				MobDsPaymentTransDTO transDTO = currentPaymentUpfront.getPaymentTransList().get(Integer.parseInt(currentPaymentUpfront.getCeksSubmit()));
				transDTO.setCcNum(token);
			}
			request.getSession().setAttribute("currentPaymentUpfront", currentPaymentUpfront);
			String nextView = "mobdspaymentupfront.html";
			ModelAndView modelAndView = new ModelAndView(new RedirectView(nextView));
			if (request.getSession().getAttribute("sbuid") instanceof String) {
				modelAndView.addObject("sbuid", currentPaymentUpfront.getUid());
			}
			return modelAndView;
		} else {
			return null;
		}

	}	
	
}
