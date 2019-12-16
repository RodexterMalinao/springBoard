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
import com.bomwebportal.ims.dto.ui.OrderImsUI;

public class ImsCreditCardSignController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Map<String, byte[]> signatureMap = new TreeMap<String, byte[]>();
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		logger.info("ImsCreditCardSignController called formBackingObject()");

		ImsSignoffDTO signDto = (ImsSignoffDTO) request.getSession().getAttribute("signoffSession");

		logger.info("#### - user-agent ************" + request.getHeader("user-agent"));
		
		if (signDto == null) {
			signDto = new ImsSignoffDTO();
			signDto.setSignatureType(ImsSignoffDTO.SignatureTypeEnum.CREDIT_CARD_SIGN);
		}
		
		request.getSession().setAttribute(ImsConstants.IMS_CREDIT_CARD_SIGN, signDto);

		return signDto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {

		logger.info("ImsCreditCardSignController called onSubmit()");
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		String nextView = (String) request.getAttribute("nextView");
		logger.info("nextView: " + nextView);

		String currentView = (String) request.getParameter("currentView");
		logger.info("currentView: " + currentView);

		ImsSignoffDTO signoffDto = (ImsSignoffDTO) command;
		
		String orderId = (String)request.getSession().getAttribute("orderIdSession");
		signoffDto.setOrderId(orderId);
		signoffDto.setSignatureType(ImsSignoffDTO.SignatureTypeEnum.CREDIT_CARD_SIGN);
		request.getSession().setAttribute(ImsConstants.IMS_CREDIT_CARD_SIGN, signoffDto);
		
		if(sessionOrder!=null&&"I".equalsIgnoreCase(sessionOrder.getCustomer().getCareCashInd())){
			return new ModelAndView(new RedirectView("imscarecashsign.html?dM=Y"));
		}
		

		return new ModelAndView(new RedirectView("closepopup.jsp"));//
	}

	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		logger.info("ImsCreditCardSignController ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();

//		SupportDocUI supportDocUI = (SupportDocUI) request.getSession()
//				.getAttribute(SupportDocController.SESSION_SUPPORTDOC);
		ImsPaymentUI paymentDto = (ImsPaymentUI)request.getSession().getAttribute("payment");

		String titleString = "Autopay via Credit Card Payment Authorization and Card Holder’s Signature  <font color='red'>(*Same as Credit Card A/C Signature)</font><br>"
			+"信用卡持有人簽署 <font color='red'>(*須與信用咭簽署相同) </font>";

		String noteString = "This signature applies to:<br>"
				+ "此簽名適用於：<br>"
				+ "- NETVIGATOR Application 網上行服務申請書<br><br>";
		
//				+"- PCCW mobile Application Form 電訊盈科流動通訊服務申請書<br>";
		
		
		//credit card + third party
		if (null != paymentDto ) {
			if (StringUtils.isNotEmpty(paymentDto.getPayMtdType())&& "C".equalsIgnoreCase(paymentDto.getPayMtdType())) {
				if (StringUtils.isNotEmpty(paymentDto.getThirdPartyInd())&& "Y".equalsIgnoreCase(paymentDto.getThirdPartyInd())) {
					noteString += "- Autopay via Credit Card – Direct Debit Authorization 信用卡自動轉賬授權書<br>";
				}
			}
		}
			

		referenceData.put("titleString", titleString);
		referenceData.put("noteString", noteString);
		return referenceData;
	}
}
