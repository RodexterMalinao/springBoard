package com.bomltsportal.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomltsportal.util.BomLtsConstant;

public class MessageController implements Controller {

	private final String[] removeBannerMsgCodes = { BomLtsConstant.URL_PARM_MSG_CODE_TIMEOUT };
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Enumeration en = request.getAttributeNames();
		while(en.hasMoreElements()){
			String element = (String) en.nextElement();
			logger.info(" - " + element + ": \t" + request.getAttribute(element));
		}
		Exception e = (Exception) request.getAttribute("javax.servlet.error.exception");
		if(e != null){
			e.printStackTrace();
		}
		
		String msgCode = request.getParameter(BomLtsConstant.URL_PARM_MSG_CODE);
		String msg = request.getParameter(BomLtsConstant.URL_PARM_MSG);
		if(StringUtils.isNotBlank(msgCode)){
			
			for (String removeBannerMsgCode : removeBannerMsgCodes) {
				if (StringUtils.equals(removeBannerMsgCode, msgCode)) {
					request.setAttribute("HideLogo", true);
				}
			}
			
			return new ModelAndView(BomLtsConstant.URL_VIEW_MSG, BomLtsConstant.URL_PARM_MSG_CODE, msgCode);
		}
		if(StringUtils.isNotBlank(msg)){
			return new ModelAndView(BomLtsConstant.URL_VIEW_MSG, BomLtsConstant.URL_PARM_MSG, msg);
		}
		return new ModelAndView(BomLtsConstant.URL_VIEW_MSG, BomLtsConstant.URL_PARM_MSG_CODE, BomLtsConstant.URL_PARM_MSG_CODE_TIMEOUT);
	}

}
