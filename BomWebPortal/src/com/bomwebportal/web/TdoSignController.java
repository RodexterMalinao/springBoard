package com.bomwebportal.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.ui.SupportDocUI;

public class TdoSignController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Map<String, byte[]> signatureMap = new TreeMap<String, byte[]>();
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		logger.info("SignoffController called formBackingObject()");

		SignoffDTO signDto = (SignoffDTO) request.getSession().getAttribute("signoffSession");

		logger.info("#### - user-agent ************" + request.getHeader("user-agent"));
		
		if (signDto == null) {
			signDto = new SignoffDTO();
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.TRADE_DESCRIPTION_ORDINANCE_SIGN);
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
		signoffDto.setSignatureType(SignoffDTO.SignatureTypeEnum.TRADE_DESCRIPTION_ORDINANCE_SIGN);
		request.getSession().setAttribute("tdoSignSession", signoffDto);
		
	
		String attrUid=(String)request.getParameter("sbuid");
		return new ModelAndView(new RedirectView("customersign.html?sbuid="+attrUid));

		
	}

	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		logger.info("ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();

		String titleString = "PCCW-HKT MOBILE SERVICE - KEY INFORMATION SHEET Signature<br>PCCW-HKT 流動通訊服務 - 重要資料表簽署";

		String noteString = "This signature applies to:<br>"
				+ "此簽名適用於：<br>"
				+ "- PCCW-HKT MOBILE SERVICE - KEY INFORMATION SHEET(PCCW-HKT 流動通訊服務 - 重要資料表)<br>"	;

		referenceData.put("titleString", titleString);
		referenceData.put("noteString", noteString);
		return referenceData;
	}
}
