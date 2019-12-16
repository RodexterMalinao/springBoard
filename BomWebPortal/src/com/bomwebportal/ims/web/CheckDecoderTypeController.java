package com.bomwebportal.ims.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.dto.ui.ImsLoginIDUI;
import com.bomwebportal.ims.service.CheckLoginNameService;
import com.bomwebportal.ims.service.ImsNowTVService;
import com.bomwebportal.ims.service.ReleaseLoginIDService;
import com.bomwebportal.ims.service.ReserveLoginIDService;
import com.bomwebportal.ims.service.SuggestLoginIDService;
import com.bomwebportal.ims.service.ValidateLoginIDService;

public class CheckDecoderTypeController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsNowTVService imsNowTVService;

	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("CheckDecoderTypeController is started");
		
		String decoderStr = request.getParameter("decoderStr");
		String serbdyno = request.getParameter("serbdyno");
		String nowOneBox = request.getParameter("nowOneBox");
		String decoderType = "";
		
		logger.info("decoderStr: " + decoderStr);
		logger.info("serbdyno: " + serbdyno);

		JSONArray jsonArray = new JSONArray();
		
		if(StringUtils.isBlank(serbdyno)&& StringUtils.isNotBlank(nowOneBox) && "Y".equals(nowOneBox))
			decoderType = imsNowTVService.getDecoderType(decoderStr);
		else				
			decoderType = imsNowTVService.getDecoderType(decoderStr, serbdyno);
		
		jsonArray.add("{\"decoderType\":\"" + decoderType
					+ "\"}");
		
		logger.info("decoderType: " + decoderType);
		logger.info(jsonArray);
		
		logger.info("CheckDecoderTypeController is ended");
		
		return new ModelAndView("ajax_checkloginname", "jsonArray", jsonArray);
	}

	public ImsNowTVService getImsNowTVService() {
		return imsNowTVService;
	}

	public void setImsNowTVService(ImsNowTVService imsNowTVService) {
		this.imsNowTVService = imsNowTVService;
	}
	
}
