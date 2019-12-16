package com.bomwebportal.lts.web.acq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.service.UpgradePcdSrvService;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsAcqModemLkupController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected UpgradePcdSrvService upgradePcdSrvService;
	
	public UpgradePcdSrvService getUpgradePcdSrvService() {
		return upgradePcdSrvService;
	}

	public void setUpgradePcdSrvService(UpgradePcdSrvService upgradePcdSrvService) {
		this.upgradePcdSrvService = upgradePcdSrvService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JSONObject jsonResult = new JSONObject();
		
		AcqOrderCaptureDTO orderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		
		if (orderCapture == null) {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", "Session timeout, please re-issue order again.");
			return new ModelAndView("ajax_ltsmodemlkup", jsonResult); 
		}
		
		String existingService = request.getParameter("existingService");
		String newService = request.getParameter("newService");
		
		if (StringUtils.isBlank(existingService) || StringUtils.isBlank(newService)) {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", "Missing parameter for modem lookup");
			return new ModelAndView("ajax_ltsmodemlkup", jsonResult);
		}
		
		String addressCoverage = orderCapture.getAddressRollout().getMaximumBandwidth();
		
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
				jsonResult.put("errorMsg",
						"Cannot found modem [addressCoverage:" + addressCoverage 
							+ ", existingService:" + existingService 
							+ ", newService:" + newService + "]");
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

}
