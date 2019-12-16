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
import com.bomwebportal.ims.service.ImsReportService;
import com.bomwebportal.ims.service.ImsSignOffLogService;

public class ImsCustomerSignController extends SimpleFormController {

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
	
	private ImsSignOffLogService signOffLogService;	
	
	public void setSignOffLogService(ImsSignOffLogService signOffLogService) {
		this.signOffLogService = signOffLogService;
	}
	
	public ImsSignOffLogService getSignOffLogService() {
		return signOffLogService;
	}
	
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		logger.info("CustomerSignController called formBackingObject()");
		ImsSignoffDTO signDto = (ImsSignoffDTO) request.getSession().getAttribute(
				ImsConstants.IMS_CUST_SIGN);
		
		
		request.getSession().setAttribute(ImsConstants.IMS_CUST_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_CREDIT_CARD_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_3_PARTY_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_TDO_MOOV_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_TDO_TV_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_TDO_PCD_SIGN, null);// make all sign null
		request.getSession().setAttribute(ImsConstants.IMS_CARE_CASH_SIGN, null);// make all sign null
	

		String esigEmailAddr = request.getParameter("esigEmailAddr");
		String disMode = request.getParameter("disMode");
		String langPreference = request.getParameter("langPreference");
		
	
		logger.info("esigEmailAddr: " + esigEmailAddr);
		logger.info("disMode: " + disMode);
		logger.info("langPreference: " + langPreference);
	
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			String SMSno = request.getParameter("SMSno");
			logger.info("SMSno: " + SMSno);
			sessionOrder.setSMSno(SMSno);
		}//celia ims ds 20141208
		sessionOrder.setEsigEmailAddr(esigEmailAddr);
		sessionOrder.setDisMode(DisMode.valueOf(disMode));
		sessionOrder.setLangPreference(langPreference.equals("en") ?"ENG" : "CHI");
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
			signDto.setSignatureType(ImsSignoffDTO.SignatureTypeEnum.CUSTOMER_SIGN);
		}
		signDto.setSameAsCreditCardSign(false);
		return signDto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {

		ImsSignoffDTO signoffDto = (ImsSignoffDTO) command;

		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null){logger.info("CustomerSignController called onSubmit() ImsOrderID:"+sessionOrder.getOrderId());
		signOffLogService.signOffOrderLog(sessionOrder, "CustSign", null);};

		String nextView = (String) request.getAttribute("nextView");
		logger.info("nextView: " + nextView);

		String currentView = (String) request.getParameter("currentView");
		logger.info("currentView: " + currentView);

		String orderId = (String) request.getSession().getAttribute(
				"orderIdSession");
		signoffDto.setOrderId(orderId);
		signoffDto.setSignatureType(ImsSignoffDTO.SignatureTypeEnum.CUSTOMER_SIGN);
		request.getSession().setAttribute(ImsConstants.IMS_CUST_SIGN, signoffDto);

		//copy CREDIT_CARD_SIGN
		if (signoffDto.isSameAsCreditCardSign()){
			ImsSignoffDTO CreditCardSign= new ImsSignoffDTO();
			try{
			BeanUtils.copyProperties(CreditCardSign, signoffDto);
			CreditCardSign.setSignatureType(ImsSignoffDTO.SignatureTypeEnum.CREDIT_CARD_SIGN);
			}catch (Exception e){
				logger.error("Exception caught in copy Credit Card signature", e);
				throw new AppRuntimeException(e);
			}
			request.getSession().setAttribute(ImsConstants.IMS_CREDIT_CARD_SIGN, CreditCardSign);
			System.out.println("Yes, isSameAsCreditCardSign, check box is checked, custSign and CreditCardSign are the same");
			
			if(sessionOrder!=null&&"I".equalsIgnoreCase(sessionOrder.getCustomer().getCareCashInd())){
				return new ModelAndView(new RedirectView("imscarecashsign.html?dM=Y"));
			}
			
		}else {
			if(sessionOrder!=null){
				if("Y".equalsIgnoreCase(sessionOrder.getCustomer().getAccount().getPayment().getThirdPartyInd())){
					return new ModelAndView(new RedirectView("ims3partysign.html?dM=Y"));
				}
			}
			System.out.println("No, not isSameAsCreditCardSign, check box is not shown or not checked");
			
			if(sessionOrder!=null && "C".equalsIgnoreCase(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType())){// M - Cash, C - Credit Card
				if (!sessionOrder.isOrderTypeNowRet() || "Y".equals(sessionOrder.getCustomer().getAccount().getPayment().getIsNewCard()) || ("M".equals(sessionOrder.getCustomer().getAccount().getPayment().getExistingPaymentMethod()) && "C".equals(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType())))
				{
					System.out.println("credit card sign needed, show new sign window");
					return new ModelAndView(new RedirectView("imscreditcardsign.html?dM=Y"));
				}
			}
			
			if(sessionOrder!=null&&"I".equalsIgnoreCase(sessionOrder.getCustomer().getCareCashInd())){
				return new ModelAndView(new RedirectView("imscarecashsign.html?dM=Y"));
			}		
			
		}
		
		return new ModelAndView(new RedirectView("closepopup.jsp"));//signoff.html
	}

	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		logger.info("ReferenceData called");

		Map<String, Object> referenceData = new HashMap<String, Object>();

		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);		
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		
		String titleString = "Customer Signature<br>" + "客戶簽署 ";

		String noteString = "This signature applies to:<br>"
			+ "此簽名適用於：<br>"
			+ "- NETVIGATOR Application 網上行服務申請書<br><br>";
//			+ "- PCCW mobile Application Form 電訊盈科流動通訊服務申請書 / NETVIGATOR Everywhere Application 網上行Everywhere服務申請書<br>";	
		
		String copyToCreditCardShowInd="Y";
		//not third party
		if (sessionOrder !=null ){// M - Cash, C - Credit Card
			//cash no need to show
			if(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType()!=null && "M".equalsIgnoreCase(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType())){
				copyToCreditCardShowInd="N";
			}
			//third party no need to show
			if("Y".equalsIgnoreCase(sessionOrder.getCustomer().getAccount().getPayment().getThirdPartyInd())){
				copyToCreditCardShowInd="N";
			}
			
			//third party no need to show
			if (sessionOrder.isOrderTypeNowRet())
			{
				if("C".equals(sessionOrder.getCustomer().getAccount().getPayment().getExistingPaymentMethod()) && !"Y".equalsIgnoreCase(sessionOrder.getCustomer().getAccount().getPayment().getIsNewCard())){
					copyToCreditCardShowInd="N";
				}
			}
		}
		String copyToCreditCardSignString="Replicate to Credit Card Autopay Authorization (*Same as Credit Card A/C Signature)<br>"
			+"複製到信用卡自動轉賬授權 (*須與信用咭簽署相同)<br> ";
		
		
		referenceData.put("titleString", titleString);
		referenceData.put("noteString", noteString);
		referenceData.put("copyToCreditCardShowInd", copyToCreditCardShowInd);
		referenceData.put("copyToCreditCardSignString", copyToCreditCardSignString);
		
		
		return referenceData;
	}

	
}

