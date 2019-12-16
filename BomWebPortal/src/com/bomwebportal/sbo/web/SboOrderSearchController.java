package com.bomwebportal.sbo.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.sbo.dto.form.SboOrderSearchForm;
import com.bomwebportal.sbo.service.SboOrderService;

public class SboOrderSearchController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private SboOrderService sboOrderService;

	public SboOrderService getSboOrderService() {
		return sboOrderService;
	}

	public void setSboOrderService(SboOrderService sboOrderService) {
		this.sboOrderService = sboOrderService;
	}

	@Override
	protected ModelAndView onSubmit(
			HttpServletRequest request,	HttpServletResponse response, Object command,	BindException errors)
			throws Exception {
		logger.debug("submit()");
		
		
		SboOrderSearchForm searchUI = (SboOrderSearchForm)command;
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		
		ModelAndView modelAndView = new ModelAndView("sboordersearch", model);
		
		if ("SEARCH".equals(searchUI.getAction())) {
			request.getSession().setAttribute("sboordersearch", searchUI);
		}

		return modelAndView;	
	}
	

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException{
		logger.debug("formbackingobject()");
		request.getSession().removeAttribute("sboordersearch");
		return new SboOrderSearchForm();
	}
	
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		Map<String,String> idDocTypeList = new LinkedHashMap<String,String>();
		idDocTypeList.put("HKID", "HKID");
		idDocTypeList.put("PASS", "Passport");
		referenceData.put("idDocTypeList", idDocTypeList);
		
		Map<String,String> serviceNumberTypeList = new LinkedHashMap<String,String>();
		serviceNumberTypeList.put("MRT", "MRT");
		serviceNumberTypeList.put("TEL", "TEL");
		serviceNumberTypeList.put("FSA", "FSA");//Added by IMS Eric

		
		Map<String,String> loginIdSuffixList = new LinkedHashMap<String,String>();
		loginIdSuffixList.put("N", "@netvigator.com");
		loginIdSuffixList.put("S", "@HKStar.com");

		referenceData.put("loginIdSuffixList", loginIdSuffixList);
		referenceData.put("serviceNumberTypeList", serviceNumberTypeList);
		referenceData.put("loginIdSuffixList", loginIdSuffixList);
		return referenceData;
	}
}
