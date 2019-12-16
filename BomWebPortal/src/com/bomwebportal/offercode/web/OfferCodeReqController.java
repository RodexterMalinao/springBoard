package com.bomwebportal.offercode.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.offercode.exception.AppServiceException;
import com.bomwebportal.offercode.service.OfferCodeReqService;
import com.bomwebportal.offercode.validator.OfferCodeReqFormValidator;


@Controller
public class OfferCodeReqController {
	
	private final Log logger = LogFactory.getLog(getClass());

	@Autowired
	OfferCodeReqService service;
	
	@ModelAttribute("form")
	public Form newForm() {
		return new Form();
	}
	
	@RequestMapping(value="/offercodereq")
	public String showOfferCode() {
		return "offercodereq";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/offercodereq")
	public String showOfferCode(@ModelAttribute Form form,  BindingResult result, Model model, Locale locale, HttpServletRequest request) {

		new OfferCodeReqFormValidator().validate(form, result);
		
		if (result.hasErrors()) 
			return "offercodereq";
		/*
		if ("99999999".equals(form.mobileNum)) {
			result.rejectValue("mobileNum", "mobileNum.invalid", "Invalid mobile number");
			
		}
		*/
		
		if (result.hasErrors()) 
			return "offercodereq";
		
		try {
			String requestedBy = "bwp";
			BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
			if (salesUserDto != null) {
				requestedBy = salesUserDto.getUsername();
			}
			
			if ("true".equals(request.getParameter("_nosms"))) {
				requestedBy = "nobody";
			}
			
			String pin = service.requestCode(form.getMobileNum(), requestedBy, locale);
			System.out.println("Offer Code = " + pin);
		} catch (AppServiceException e) {
			logger.error("Failed to request pin: errCode="+e.getCode()+", errMsg="+e.getMessage());
			resolveError(result, e);
		}
		
		if (result.hasErrors()) {
			return "offercodereq";
		} 
		
		model.addAttribute("mobileNum", form.getMobileNum());
		
		
		return "offercodesent";
	}
	
	
	private void resolveError(Errors error, Throwable e) {
		if (e instanceof AppServiceException) {
			AppServiceException ase = (AppServiceException)e;
			String code = ase.getCode();
			String msg = ase.getMessage();
			
			String errKey = "validator.offercode.mobileNum.errcode_" + code;
			error.rejectValue("mobileNum", errKey, msg);
		} else {
			error.rejectValue("mobileNum", "validator.offercode.mobileNum.errcode_29999", "Unexpected error");
		}
	}
	
	
	
	
	public static class Form {

		String mobileNum;
		
		public Form(){}
		public String getMobileNum() {
			return mobileNum;
		}
		public void setMobileNum(String mobileNum) {
			this.mobileNum = mobileNum;
		};
		
		
	}
}
