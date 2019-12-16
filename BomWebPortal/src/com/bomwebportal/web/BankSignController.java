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

public class BankSignController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Map<String, byte[]> signatureMap = new TreeMap<String, byte[]>();
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		logger.info("SignoffController called formBackingObject()");

		SignoffDTO signDto = (SignoffDTO) request.getSession().getAttribute("signoffSession");

		logger.info("#### - user-agent ************" + request.getHeader("user-agent"));
		
		if (signDto == null) {
			signDto = new SignoffDTO();
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.BANK_SIGN);
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
		signoffDto.setSignatureType(SignoffDTO.SignatureTypeEnum.BANK_SIGN);
		request.getSession().setAttribute("bankSignSession", signoffDto);
		
		OrderDTO orderDto= (OrderDTO)request.getSession().getAttribute("orderDto");
		//if iGuard. redirect to iGuard page
		if(!StringUtils.isEmpty( orderDto.getiGuardSerialNo())){
			String attrUid=(String)request.getParameter("sbuid");
			return new ModelAndView(new RedirectView("iguardsign.html?sbuid="+attrUid));
		}

	
		return new ModelAndView(new RedirectView("closepopup.jsp"));
	}

	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		logger.info("ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();

		
		PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");

		String titleString = "Autopay via Credit Card/Bank Account Direct Debit Authorization and Account Holder’s Signature  <font color='red'>(*Same as Bank/Credit Card A/C Signature)</font><br>"
			+"信用卡/銀行戶口自動轉賬直接扣賬授權及戶口持有人簽署 <font color='red'>(*須與銀行賬戶/信用咭簽署相同) </font>";

		String noteString = "This signature applies to:<br>"
				+ "此簽名適用於：<br>"

				+"- PCCW mobile Application Form 電訊盈科流動通訊服務申請書<br>";
		//credit card + third party
		if (null != paymentDto ) {
			if (StringUtils.isNotEmpty(paymentDto.getPayMethodType())&& "C".equalsIgnoreCase(paymentDto.getPayMethodType())) {
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
