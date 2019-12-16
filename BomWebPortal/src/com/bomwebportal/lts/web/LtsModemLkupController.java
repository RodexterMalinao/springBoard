package com.bomwebportal.lts.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Locale;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.service.UpgradePcdSrvService;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsModemLkupController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected UpgradePcdSrvService upgradePcdSrvService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public UpgradePcdSrvService getUpgradePcdSrvService() {
		return upgradePcdSrvService;
	}

	public void setUpgradePcdSrvService(UpgradePcdSrvService upgradePcdSrvService) {
		this.upgradePcdSrvService = upgradePcdSrvService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JSONObject jsonResult = new JSONObject();
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		setLocale(RequestContextUtils.getLocale(request));
		
		if (orderCapture == null) {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", messageSource.getMessage("lts.ltsModemLkup.timeout", new Object[] {}, this.locale));
			return new ModelAndView("ajax_ltsmodemlkup", jsonResult); 
		}
		
		String existingService = request.getParameter("existingService");
		String newService = request.getParameter("newService");
		
		if (StringUtils.isBlank(existingService) || StringUtils.isBlank(newService)) {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", messageSource.getMessage("lts.ltsModemLkup.missParm", new Object[] {}, this.locale));
			return new ModelAndView("ajax_ltsmodemlkup", jsonResult);
		}
		
		String addressCoverage = orderCapture.getNewAddressRollout().getMaximumBandwidth();
		
		if (StringUtils.equals(existingService, FsaServiceType.PCD_EW.getName())) {
			existingService = FsaServiceType.PCD.getName();
		}
		else if (StringUtils.equals(existingService, FsaServiceType.PCD_EW_SDTV.getName())) {
			existingService = FsaServiceType.PCD_SDTV.getName();
		}
		else if (StringUtils.equals(existingService, FsaServiceType.PCD_EW_HDTV.getName())) {
			existingService = FsaServiceType.PCD_HDTV.getName();
		}
		
		try {
			
			String modemArrangment = upgradePcdSrvService.getModemArrangment(addressCoverage, existingService.replace("_", "+"), newService.replace("_", "+"));
			
			if (StringUtils.isEmpty(modemArrangment)) {
				jsonResult.put("status", "false");
				jsonResult.put("errorMsg", messageSource.getMessage("lts.ltsModemLkup.modNotFound", new Object[] {addressCoverage,existingService,newService}, this.locale));
			}
			else {
				jsonResult.put("status", "true");
				jsonResult.put("modemArrangment", modemArrangment);	
			}
			
			
			return new ModelAndView("ajax_ltsmodemlkup", jsonResult);
		}
		catch (Exception e) {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", ExceptionUtils.getFullStackTrace(e));
			return new ModelAndView("ajax_ltsmodemlkup", jsonResult);
		}
		
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
