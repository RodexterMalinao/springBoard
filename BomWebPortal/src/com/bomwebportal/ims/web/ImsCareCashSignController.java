package com.bomwebportal.ims.web;


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

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ImsSignoffDTO;
import com.bomwebportal.ims.dto.ui.ImsPaymentUI;

public class ImsCareCashSignController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Map<String, byte[]> signatureMap = new TreeMap<String, byte[]>();
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		logger.info("ImsCareCashSignController called formBackingObject()");

		ImsSignoffDTO signDto = (ImsSignoffDTO) request.getSession().getAttribute("signoffSession");

		logger.info("#### - user-agent ************" + request.getHeader("user-agent"));
		
		if (signDto == null) {
			signDto = new ImsSignoffDTO();
			signDto.setSignatureType(ImsSignoffDTO.SignatureTypeEnum.CareCash_SIGN);
		}
		
		request.getSession().setAttribute(ImsConstants.IMS_CARE_CASH_SIGN, signDto);

		return signDto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {

		logger.info("ImsCareCashSignController called onSubmit()");
		
		String nextView = (String) request.getAttribute("nextView");
		logger.info("nextView: " + nextView);

		String currentView = (String) request.getParameter("currentView");
		logger.info("currentView: " + currentView);

		ImsSignoffDTO signoffDto = (ImsSignoffDTO) command;
		
		String orderId = (String)request.getSession().getAttribute("orderIdSession");
		signoffDto.setOrderId(orderId);
		signoffDto.setSignatureType(ImsSignoffDTO.SignatureTypeEnum.CareCash_SIGN);
		request.getSession().setAttribute(ImsConstants.IMS_CARE_CASH_SIGN, signoffDto);

		return new ModelAndView(new RedirectView("closepopup.jsp"));//
	}

	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		logger.info("ImsCareCashSignController ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();

		String titleString = "Customer Signature<br>" + "客戶簽署 ";

		String noteString = "This signature applies to:<br>"
				+ "此簽名適用於：<br>"
				+ "- CARECash Customer Information Form / 貼心錢客戶資料表<br><br>";
		
			

		referenceData.put("titleString", titleString);
		referenceData.put("noteString", noteString);
		return referenceData;
	}
}
