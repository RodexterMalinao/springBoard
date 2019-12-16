package com.bomwebportal.ims.web;

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

import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.ims.dto.ImsSignoffDTO;
import com.bomwebportal.ims.service.ImsOrderDetailService;
import com.bomwebportal.ims.service.ImsReportService;

public class ImsCustomerSignPcdTdoController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Map<String, byte[]> signatureMap = new TreeMap<String, byte[]>();
	
	private ImsOrderDetailService imsOrderDetailService;

	public ImsOrderDetailService getImsOrderDetailService() {
		return imsOrderDetailService;
	}

	public void setImsOrderDetailService(ImsOrderDetailService imsOrderDetailService) {
		this.imsOrderDetailService = imsOrderDetailService;
	}

	private ImsReportService service;

	public ImsReportService getService() {
		return service;
	}

	public void setService(ImsReportService service) {
		this.service = service;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		logger.info("ImsCustomerSignPcdTdoController called formBackingObject()");
		ImsSignoffDTO signDto = (ImsSignoffDTO) request.getSession().getAttribute(ImsConstants.IMS_TDO_PCD_SIGN);	
		
		String esigEmailAddr = request.getParameter("esigEmailAddr");
		String disMode = request.getParameter("disMode");
		String langPreference = request.getParameter("langPreference");
		
		logger.info("esigEmailAddr: " + esigEmailAddr);
		logger.info("disMode: " + disMode);
		logger.info("langPreference: " + langPreference);
	
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		
		
		sessionOrder.setEsigEmailAddr(esigEmailAddr);
		sessionOrder.setDisMode(DisMode.valueOf(disMode));
		sessionOrder.setLangPreference(langPreference);
		sessionOrder.setEsigEmailLang((langPreference.equals("en")) ? EsigEmailLang.ENG : EsigEmailLang.CHN);
		
		logger.info("sessionOrder.getOrderStatus(): " + sessionOrder.getOrderStatus());
		
		
		//save order initial order image		
		//if(sessionOrder.getOrderStatus()==null){

		if(!sessionOrder.getOrderStatus().equalsIgnoreCase(ImsConstants.IMS_ORDER_STATUS_SIGNOFF)&&
				!service.isDBSignOffEd(sessionOrder.getOrderId())){
			imsOrderDetailService.suspendOrder(sessionOrder, "S000"); //await sign off					
			request.getSession().setAttribute("imsLoginIdUi", null);
			request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, null);
			request.getSession().setAttribute(ImsConstants.IMS_ORDER, sessionOrder);
						request.getSession().setAttribute("imsMobileOfferUiList", null);
		}
		
		
		if (signDto == null) {
			signDto = new ImsSignoffDTO();
			signDto.setSignatureType(ImsSignoffDTO.SignatureTypeEnum.PCD_TDO_SIGN);
		}
		
		return signDto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {

		ImsSignoffDTO signoffDto = (ImsSignoffDTO) command;

		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		
		logger.info("ImsCustomerSignPcdTdoController called onSubmit()");

		String orderId = (String) request.getSession().getAttribute("orderIdSession");
		signoffDto.setOrderId(orderId);
		signoffDto.setSignatureType(ImsSignoffDTO.SignatureTypeEnum.PCD_TDO_SIGN);
		request.getSession().setAttribute(ImsConstants.IMS_TDO_PCD_SIGN, signoffDto);
		
		logger.info("signoffDto.signatureString is null: " + (signoffDto.getSignatureString() == null) );
		

		
		logger.info("ProcessVIM: " + sessionOrder.getProcessVim());
		logger.info("ProcessVms: " + sessionOrder.getProcessVms());
		
		if(sessionOrder.getProcessVim() != null)
			return new ModelAndView(new RedirectView("imscustomersigntvtdo.html"));
		else{
			if(sessionOrder.getProcessVms() != null)
				return new ModelAndView(new RedirectView("imscustomersignmoovtdo.html"));
			else
				return new ModelAndView(new RedirectView("imscustomersign.html"));
		}
		
	}

//	protected Map<String, Object> referenceData(HttpServletRequest request)
//			throws Exception {
//		logger.info("ReferenceData called");
//
//		Map<String, Object> referenceData = new HashMap<String, Object>();
//
//		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
//		
//		String titleString = "PCD_TDO Customer Signature<br>" + "PCD_TDO 客戶簽署 ";
//
//		String noteString = "This signature applies to:<br>"
//			+ "此簽名適用於：<br>"
//			+ "- NETVIGATOR Application 網上行服務申請書<br><br>";
//		
//		
//	
//		
//		referenceData.put("titleString", titleString);
//		referenceData.put("noteString", noteString);
//		
//		
//		
//		return referenceData;
//	}

	
}

