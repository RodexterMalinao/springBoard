package com.bomwebportal.ims.web;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.dto.ImsSignoffDTO;
import com.bomwebportal.ims.service.ImsOrderDetailService;

public class ImsCustomerSignMoovTdoController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Map<String, byte[]> signatureMap = new TreeMap<String, byte[]>();
	
	private ImsOrderDetailService imsOrderDetailService;

	public ImsOrderDetailService getImsOrderDetailService() {
		return imsOrderDetailService;
	}

	public void setImsOrderDetailService(ImsOrderDetailService imsOrderDetailService) {
		this.imsOrderDetailService = imsOrderDetailService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		logger.info("ImsCustomerSignMoovTdoController called formBackingObject()");
		ImsSignoffDTO signDto = (ImsSignoffDTO) request.getSession().getAttribute(ImsConstants.IMS_TDO_MOOV_SIGN);
		
		if (signDto == null) {
			signDto = new ImsSignoffDTO();
			signDto.setSignatureType(ImsSignoffDTO.SignatureTypeEnum.MOOV_TDO_SIGN);
		}
		
		return signDto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {

		ImsSignoffDTO signoffDto = (ImsSignoffDTO) command;

		logger.info("ImsCustomerSignMoovTdoController called onSubmit()");

		String orderId = (String) request.getSession().getAttribute("orderIdSession");
		signoffDto.setOrderId(orderId);
		signoffDto.setSignatureType(ImsSignoffDTO.SignatureTypeEnum.MOOV_TDO_SIGN);
		request.getSession().setAttribute(ImsConstants.IMS_TDO_MOOV_SIGN, signoffDto);
		
		logger.info("signoffDto.signatureString is null: " + (signoffDto.getSignatureString() == null) );
		
	
		return new ModelAndView(new RedirectView("imscustomersign.html"));
		
		
	}

//	protected Map<String, Object> referenceData(HttpServletRequest request)
//			throws Exception {
//		logger.info("ReferenceData called");
//
//		Map<String, Object> referenceData = new HashMap<String, Object>();
//
//
//		String titleString = "MOOV/MEDIA_TDO Customer Signature<br>" + "MOOV/MEDIA_TDO 客戶簽署 ";
//
//		String noteString = "This signature applies to:<br>"
//			+ "此簽名適用於：<br>"
//			+ "- NETVIGATOR Application 網上行服務申請書<br><br>";
////			+ "- PCCW mobile Application Form 電訊盈科流動通訊服務申請書 / NETVIGATOR Everywhere Application 網上行Everywhere服務申請書<br>";	
//		
//	
//		String copyToCreditCardSignString="Replicate to Credit Card Autopay Authorization (*Same as Credit Card A/C Signature)<br>"
//			+"複製到信用卡自動轉賬授權 (*須與信用咭簽署相同)<br> ";
//		
//		
//		referenceData.put("titleString", titleString);
//		referenceData.put("noteString", noteString);
//		//referenceData.put("copyToCreditCardSignString", copyToCreditCardSignString);
//		
//		
//		return referenceData;
//	}

	
}

