package com.bomwebportal.web;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

public class AndroidSignoffController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	public static final Map<String, byte[]> signatureMap = new TreeMap<String, byte[]>();

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		logger.info("SignoffAndroidController called onSubmit()");
		
		String nextView = (String) request.getAttribute("nextView");
		logger.info("nextView: " + nextView);

		String currentView = (String) request.getParameter("currentView");
		logger.info("currentView: " + currentView);

		SignoffDTO signoffDto = (SignoffDTO) command;
		
		OrderDTO orderDto = (OrderDTO)request.getSession().getAttribute("orderdetailOrderDto");
		String orderId = orderDto.getOrderId();
		signoffDto.setOrderId(orderId);
		InputStream is = request.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int intByte = is.read();
		while(intByte != -1) {
			baos.write(intByte);
			intByte = is.read();
		}
		signoffDto.setSignature(baos.toByteArray());
		request.getSession().setAttribute("signoffDtoSession", signoffDto);

		baos.close();
		is.close();
		
		return new ModelAndView(new RedirectView("androidsignoff.jsp"));
	}

	protected Map<String, List<String>> referenceData(HttpServletRequest request)
			throws Exception {
		logger.info("ReferenceData called");
		Map<String, List<String>> referenceData = new HashMap<String, List<String>>();

		return referenceData;
	}
}
