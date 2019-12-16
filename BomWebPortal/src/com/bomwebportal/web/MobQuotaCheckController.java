package com.bomwebportal.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.mobquota.dto.MobQuotaUsageDTO;
import com.bomwebportal.mobquota.exception.AppServiceException;
import com.bomwebportal.mobquota.service.MobQuotaService;

@Controller
public class MobQuotaCheckController {
	@Autowired
	private MobQuotaService mobQuotaService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	
	@ModelAttribute
    public void referenceData(String orderId, Model model) {
	}
	
	@RequestMapping(value="/mobquotacheck", method = RequestMethod.GET)
	public String mobQuotaSearch(String orderId,
			Boolean init,
			HttpServletRequest request,
			HttpSession session,
			ModelMap model) throws AppServiceException {
		
		String idDocType = request.getParameter("idDocType");
		String idDocNum = request.getParameter("idDocNum");
		
		if (StringUtils.isNotBlank(idDocType) && StringUtils.isNotBlank(idDocNum)) {
			try {
				MobQuotaUsageDTO[] mobQuotaUsageArray = mobQuotaService.getMobQuotaUsageList(idDocType, idDocNum);
				model.addAttribute("mobQuotaUsageArray", mobQuotaUsageArray);
			} catch (Exception e) {
				model.addAttribute("mobQuotaUsageException", e);
			}
		}
		return "mobquotacheck";
	}
}
