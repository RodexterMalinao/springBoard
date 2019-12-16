package com.bomwebportal.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.ui.SupportDocUI;
import com.bomwebportal.dto.ui.SupportDocUI.GenerateSpringboardForm;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.Utility;

public class CustomerSignController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	public static Map<String, byte[]> signatureMap = new TreeMap<String, byte[]>();
	
	private VasDetailService vasDetailService;

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		logger.info("CustomerSignController called formBackingObject()");
		SignoffDTO signDto = (SignoffDTO) request.getSession().getAttribute(
				"customerSignSession");

		if (signDto == null) {
			signDto = new SignoffDTO();
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.CUSTOMER_SIGN);
		}
		return signDto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {

		logger.info("CustomerSignController called onSubmit()");

		String nextView = (String) request.getAttribute("nextView");
		logger.info("nextView: " + nextView);

		String currentView = (String) request.getParameter("currentView");
		logger.info("currentView: " + currentView);

		SignoffDTO signoffDto = (SignoffDTO) command;
		String orderId = (String) request.getSession().getAttribute(
				"orderIdSession");
		signoffDto.setOrderId(orderId);
		signoffDto.setSignatureType(SignoffDTO.SignatureTypeEnum.CUSTOMER_SIGN);
		request.getSession().setAttribute("customerSignSession", signoffDto);
		

		
		SupportDocUI supportDocUI = (SupportDocUI) request.getSession()
		.getAttribute(SupportDocController.SESSION_SUPPORTDOC);
		///copy signature to mnp
		if (!isTransOwnership(supportDocUI) && isMnp(supportDocUI)) {
			
			SignoffDTO mnpSignDto= new SignoffDTO();
			try{
			BeanUtils.copyProperties(mnpSignDto, signoffDto);
			mnpSignDto.setSignatureType(SignoffDTO.SignatureTypeEnum.MNP_SIGN);
			}catch (Exception e){
				logger.error("Exception caught in copy mnp signature", e);
				throw new AppRuntimeException(e);
			}
			
			
			request.getSession().setAttribute("mnpSignSession", mnpSignDto);
			
		}
		
		//copy bank sign
		if (signoffDto.isSameAsBankSign()){
			SignoffDTO bankSignDto= new SignoffDTO();
			try{
			BeanUtils.copyProperties(bankSignDto, signoffDto);
			bankSignDto.setSignatureType(SignoffDTO.SignatureTypeEnum.BANK_SIGN);
			}catch (Exception e){
				logger.error("Exception caught in copy bank signature", e);
				throw new AppRuntimeException(e);
			}
			
			
			request.getSession().setAttribute("bankSignSession", bankSignDto);
			
		}
		
		
		
		String attrUid=(String)request.getParameter("sbuid");
		if (isTransOwnership(supportDocUI) ) {//true for tick tick
			
    		
    		
			return new ModelAndView(new RedirectView("mnpsign.html?sbuid="+attrUid));
			
		}
		PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");
		if (!"M".equalsIgnoreCase(paymentDto.getPayMethodType())  && !signoffDto.isSameAsBankSign()){
			return new ModelAndView(new RedirectView("banksign.html?sbuid="+attrUid));
		}
		
		OrderDTO orderDto= (OrderDTO)request.getSession().getAttribute("orderDto");
		//if iGuard. redirect to iGuard page
		if(!StringUtils.isEmpty( orderDto.getiGuardSerialNo())){
			return new ModelAndView(new RedirectView("iguardsign.html?sbuid="+attrUid));
		}
		
		

		return new ModelAndView(new RedirectView("closepopup.jsp"));//signoff.html
	}

	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("ReferenceData called");
		}
		Map<String, Object> referenceData = new HashMap<String, Object>();

		SupportDocUI supportDocUI = (SupportDocUI) request.getSession().getAttribute(SupportDocController.SESSION_SUPPORTDOC);
		PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");
		

		//String titleString = "Customer Signature<br>" + "客戶簽署 ";

		//String noteString = "This signature applies to:<br>"
		//		+ "此簽名適用於：<br>"
		//		+ "- PCCW mobile Application Form 電訊盈科流動通訊服務申請書 / NETVIGATOR Everywhere Application 網上行Everywhere服務申請書<br>";
		
		referenceData.put("isCos", isCos(supportDocUI));		
		/*if (isCos( supportDocUI)) {
			noteString +=  "- PCCW mobile Change of Service / Customer Information & Refund Application 電訊盈科流動通訊更改服務 / 客戶資料及退款申請書<br>";
		}*/

		referenceData.put("isSecretarialService", isSecretarialService(supportDocUI));
		/*if (isSecretarialService( supportDocUI)) {
			noteString +=  "- Mobile Secretarial Service Supplement Form 申請或更改流動秘書服務<br>";
		}*/
		
		referenceData.put("isMnpNotTransOwnership", isMnp(supportDocUI) && !isTransOwnership(supportDocUI));
		/*if (isMnp(supportDocUI) && !isTransOwnership(supportDocUI)) {
			noteString += "- Mobile Number Portability Application Form 流動電話號碼可攜服務申請表格<br>";
		}*/
		
		
		//String copyToBankSignString="Replicated to Credit Card/Bank Autopay Authorization? (*Same as Bank/Credit Card A/C Signature)<br>"
		//		+"複製到信用卡/銀行戶口自動轉賬授權 (*須與銀行賬戶/信用咭簽署相同)<br> ";

		boolean copyToBankShowInd = true;
		//not thirt party
		if (paymentDto !=null ){
			//cash no need to show
			if(paymentDto.getPayMethodType()!=null && "M".equalsIgnoreCase(paymentDto.getPayMethodType())){
				copyToBankShowInd = false;
			}
			//third party no need to show
			if("Y".equalsIgnoreCase(paymentDto.getThirdPartyInd())){
				copyToBankShowInd = false;
			}
			
		}
		referenceData.put("copyToBankShowInd", copyToBankShowInd);
		
		// mobileSafetyPhone
		String appDateStr=(String) request.getSession().getAttribute("appDate");
		Date appDate = StringUtils.isBlank(appDateStr) ? null : Utility.string2Date(appDateStr);
		BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
		String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
		referenceData.put("isMobileSafetyPhone", this.isMobileSafetyPhone(appDate, basketDTO, selectedItemList));
		String orderId = (String) request.getSession().getAttribute("orderIdSession");
		
		OrderDTO orderDto = (OrderDTO)request.getSession().getAttribute("orderDto");
		referenceData.put("isNFCSim", this.vasDetailService.isExtraFunctionSimByNfcInd(orderDto.getNfcInd()));
		//referenceData.put("isOctopusSim", this.vasDetailService.hasOctopusSim(orderId));
	//	referenceData.put("hasProductInfo", hasProductInformation(supportDocUI)); //remove 20130725, KIS and PIS no need to signature
		/*referenceData.put("titleString", titleString);
		referenceData.put("noteString", noteString);
		referenceData.put("copyToBankShowInd", copyToBankShowInd);
		referenceData.put("copyToBankSignString", copyToBankSignString);*/
		
		
		return referenceData;
	}

	boolean isMnp(SupportDocUI supportDocUI) {
		List<GenerateSpringboardForm> docAttList = supportDocUI
		.getGenerateSpringboardForms();
		boolean result = false;
		if (docAttList != null) {
			for (GenerateSpringboardForm each : docAttList) {
				switch(each.getSpringboardForm()) {
				case MNP_APPLICATION_FORM:
					if (each.isRequired()){
						result = true;
						break;
					}
				}
				
			}
		}

		return result;
	}

	boolean isSecretarialService(SupportDocUI supportDocUI) {
		List<GenerateSpringboardForm> docAttList = supportDocUI
				.getGenerateSpringboardForms();

		boolean result = false;
		if (docAttList != null) {
			for (GenerateSpringboardForm each : docAttList) {
				switch(each.getSpringboardForm()) {
				case APPLICATION_IN_RESPECT_OF_MOBILE_SECRETARIAL_SERVICE:
					if (each.isRequired()){
						result = true;
						break;
					}
					
				}
				
			}
		}

		return result;
	}
	
	boolean isCos(SupportDocUI supportDocUI) {
		List<GenerateSpringboardForm> docAttList = supportDocUI
		.getGenerateSpringboardForms();
		boolean result = false;
		if (docAttList != null) {
			for (GenerateSpringboardForm each : docAttList) {
				switch(each.getSpringboardForm()) {
				case CHANGE_OF_SERVICE_FORM:
					if (each.isRequired()){
						result = true;
						break;
					}
				}
			}
		}

		return result;
	}
	
	boolean isTransOwnership(SupportDocUI supportDocUI) {
		List<AllOrdDocAssgnDTO> pfList
		= supportDocUI.getAllOrdDocAssgnDTOs();
		
		boolean result = false;
		if (pfList != null) {
			for (AllOrdDocAssgnDTO each : pfList) {
				switch(each.getDocType()) {
				case M009 :
					if (StringUtils.isEmpty( each.getWaiveReason())){
						result = true;
						break;
					}
					
				}
			}
		}

		return result;
	}
	
	private <E> boolean isEmpty(E[] values) {
		return values == null || values.length == 0;
	}
	
	private boolean isMobileSafetyPhone(Date appDate, BasketDTO basketDTO, String[] selectedItemList) {
		if (appDate == null || basketDTO == null || isEmpty(selectedItemList)) {
			return false;
		}
		//String appDateStr=(String) request.getSession().getAttribute("appDate");
		//Date appDate = Utility.string2Date(appDateStr);
		//BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
		//String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
		return this.vasDetailService.isItemsInGroup(basketDTO.getBasketId(), selectedItemList, appDate, "100000103");
	}
	
	/*boolean hasProductInformation(SupportDocUI supportDocUI) {
		List<GenerateSpringboardForm> docAttList = supportDocUI
		.getGenerateSpringboardForms();
		boolean result = false;
		if (docAttList != null) {
			for (GenerateSpringboardForm each : docAttList) {
				switch(each.getSpringboardForm()) {
				case TRADE_DESCRIPTIONS_FOR_ELECTIONIC_PRODUCTS:
					if (each.isRequired()){
						result = true;
						break;
					}
				}
				
			}
		}

		return result;
	}*/
	
}
