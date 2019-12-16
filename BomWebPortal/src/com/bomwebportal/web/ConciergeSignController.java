package com.bomwebportal.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.ui.SupportDocUI;

public class ConciergeSignController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Map<String, byte[]> signatureMap = new TreeMap<String, byte[]>();
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		logger.info("SignoffController called formBackingObject()");

		SignoffDTO signDto = (SignoffDTO) request.getSession().getAttribute("signoffSession");

		logger.info("#### - user-agent ************" + request.getHeader("user-agent"));
		
		if (signDto == null) {
			signDto = new SignoffDTO();
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.CONCIERGE_SIGN);
		}

		return signDto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {

		logger.info("SignoffController called onSubmit()");
		
		String nextView = (String) request.getAttribute("nextView");
		logger.info("nextView: " + nextView);

		String currentView = (String) request.getParameter("currentView");
		logger.info("currentView: " + currentView);

		SignoffDTO signoffDto = (SignoffDTO) command;
		
		String orderId = (String)request.getSession().getAttribute("orderIdSession");
		signoffDto.setOrderId(orderId);
		signoffDto.setSignatureType(SignoffDTO.SignatureTypeEnum.CONCIERGE_SIGN);
		request.getSession().setAttribute("conciergeSignSession", signoffDto);

		
		return new ModelAndView(new RedirectView("closepopup.jsp"));
		
	}
	
	
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		logger.info("ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();

		

		String titleString = "Authorization of PCCW Concierge Service<br>"
				+ "授權電訊盈科禮賓服務<br>";

		String noteString = "Thank you for choosing PCCW mobile’s concierge service tailored-made for Customer. We acknowledge receipt with thanks of your instruction to procure an iPhone / iPad on your behalf and we will take action in parallel.<br>" 
			+ "多謝選用由電訊盈科流動通訊為您提供的禮賓服務，我們已收到您的指示並會即時為您代辦指定 iPhone / iPad 乙部.<br>";



		referenceData.put("titleString", titleString);
		referenceData.put("noteString", noteString);
		return referenceData;
	}
	
}
