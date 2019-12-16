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

public class MnpSignController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Map<String, byte[]> signatureMap = new TreeMap<String, byte[]>();
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		logger.info("SignoffController called formBackingObject()");

		SignoffDTO signDto = (SignoffDTO) request.getSession().getAttribute("signoffSession");

		logger.info("#### - user-agent ************" + request.getHeader("user-agent"));
		
		if (signDto == null) {
			signDto = new SignoffDTO();
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.MNP_SIGN);
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
		signoffDto.setSignatureType(SignoffDTO.SignatureTypeEnum.MNP_SIGN);
		request.getSession().setAttribute("mnpSignSession", signoffDto);
		
		PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");
		if (!"M".equalsIgnoreCase(paymentDto.getPayMethodType())){
			String attrUid=(String)request.getParameter("sbuid");
			return new ModelAndView(new RedirectView("banksign.html?sbuid="+attrUid));
		}
		
		OrderDTO orderDto= (OrderDTO)request.getSession().getAttribute("orderDto");
		//if iGuard. redirect to iGuard page
		if(!StringUtils.isEmpty( orderDto.getiGuardSerialNo())){
			String attrUid=(String)request.getParameter("sbuid");
			return new ModelAndView(new RedirectView("iguardsign.html?sbuid="+attrUid));
		}

		return new ModelAndView(new RedirectView("closepopup.jsp"));//signoff.html

		
	}

	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		logger.info("ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();

		SupportDocUI supportDocUI = (SupportDocUI) request.getSession()
				.getAttribute(SupportDocController.SESSION_SUPPORTDOC);

		String titleString = "Mobile Number Portability Transferee’s Signature<br>流動電話號碼可攜服務承讓人簽署";

		String noteString = "This signature applies to:<br>"
				+ "此簽名適用於：<br>"
				+ "- Mobile Number Portability Application Form 流動電話號碼可攜服務申請表格<br>"
				+ "- PCCW mobile Change of Service / Customer Information & Refund Application 電訊盈科流動通訊更改服務 / 客戶資料及退款申請書<br>";

		referenceData.put("titleString", titleString);
		referenceData.put("noteString", noteString);
		return referenceData;
	}
}
