package com.bomwebportal.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.ui.SupportDocUI;
import com.bomwebportal.dto.ui.SupportDocUI.GenerateSpringboardForm;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.IGuardService;
import com.bomwebportal.service.ItemDisplayService;
import com.bomwebportal.service.ReportService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.sig.dto.SignCaptureDTO;
import com.bomwebportal.sig.service.SignCaptureService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.util.ReportSessionName;

public class MobSignController extends SimpleFormController{

	private static final String ITEM_SELECTION_GROUP_ID_HELPER_CARE = "6666666664";
	private static final String ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE= "6666666665";
	private static final String PROJECT_EAGLE_ITEM_SELECTTION_GROUP_ID = "6666666663";
	
	private VasDetailService vasDetailService;
	
	private SignCaptureService signCaptureService;
	private IGuardService iGuardService;
	private ItemDisplayService itemDisplayService;
	private ReportService reportService;

	public ItemDisplayService getItemDisplayService() {
		return itemDisplayService;
	}

	public void setItemDisplayService(ItemDisplayService itemDisplayService) {
		this.itemDisplayService = itemDisplayService;
	}
	
	public IGuardService getiGuardService() {
		return iGuardService;
	}

	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}


	public SignCaptureService getSignCaptureService() {
		return signCaptureService;
	}

	public void setSignCaptureService(SignCaptureService signCaptureService) {
		this.signCaptureService = signCaptureService;
	}
	
	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
			
		int mode = getCurrentModeFromSession(request);
		SignoffDTO signDto = new SignoffDTO();
		signDto.setSameAsBankSign(false);
		signDto.setiGuardUadInd(isIGuardUADOptionRequired(request));
		signDto.setMode(mode);
		if (mode == 1) {
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.CUSTOMER_SIGN);
		} else if (mode == 2) {
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.MNP_SIGN);
		} else if (mode == 3) {
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.BANK_SIGN);
		} else if (mode == 4) {
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.IGUARD_SIGN);
		} else if (mode == 5) {
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.MULTISIM_MNP_COO_SIGN);
			signDto.setMnpNumber(getMNPTransferNumMultiSIM(request));
		} else if (mode == 6) {
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.TRAVEL_INSURANCE_FORM_SIGN);
		} else if (mode == 7) {
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.HELPERCARE_INSURANCE_FORM_SIGN);
		} else if (mode == 8) {
			signDto.setSignatureType(SignoffDTO.SignatureTypeEnum.PROJECT_EAGLE_INSURANCE_FORM_SIGN);
		}
		
		
		return signDto;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {
		
		SignoffDTO signoffDto = (SignoffDTO) command;
		String orderId = (String)request.getSession().getAttribute("orderIdSession");
		signoffDto.setOrderId(orderId);
		if (signoffDto.getSignatureType() == SignoffDTO.SignatureTypeEnum.CUSTOMER_SIGN) {
			request.getSession().setAttribute("customerSignSession", signoffDto);
			
			if (signoffDto.isSameAsBankSign()) {
				SignoffDTO bankSignDto= new SignoffDTO();
				try {
					BeanUtils.copyProperties(bankSignDto, signoffDto);
					bankSignDto.setSignatureType(SignoffDTO.SignatureTypeEnum.BANK_SIGN);
				} catch (Exception e) {
					logger.error("Exception caught in copy bank signature", e);
				}
				request.getSession().setAttribute("bankSignSession", bankSignDto);
			}
			
			MnpDTO mnpDto = (MnpDTO) request.getSession().getAttribute("MNP");
			if(mnpDto != null && !isMNPSignRequired(request)) {
				SignoffDTO mnpSignDto= new SignoffDTO();
				try{
					BeanUtils.copyProperties(mnpSignDto, signoffDto);
					mnpSignDto.setSignatureType(SignoffDTO.SignatureTypeEnum.MNP_SIGN);
				}catch (Exception e){
					logger.error("Exception caught in copy mnp signature", e);
				}
				request.getSession().setAttribute("mnpSignSession", mnpSignDto);
			}
			
		} else if (signoffDto.getSignatureType() == SignoffDTO.SignatureTypeEnum.MNP_SIGN) {
			request.getSession().setAttribute("mnpSignSession", signoffDto);
		} else if (signoffDto.getSignatureType() == SignoffDTO.SignatureTypeEnum.BANK_SIGN) {
			request.getSession().setAttribute("bankSignSession", signoffDto);
		} else if (signoffDto.getSignatureType() == SignoffDTO.SignatureTypeEnum.IGUARD_SIGN) {
			if (signoffDto.isiGuardUadInd()){
			iGuardService.updateUADOptInd(orderId, signoffDto.isiGuard3());
			}
			request.getSession().setAttribute("iGuardSignSession", signoffDto);
		} else if (signoffDto.getSignatureType() == SignoffDTO.SignatureTypeEnum.MULTISIM_MNP_COO_SIGN) {
			setMultiSimSign(request,signoffDto);
		} else if (signoffDto.getSignatureType() == SignoffDTO.SignatureTypeEnum.TRAVEL_INSURANCE_FORM_SIGN) {
			request.getSession().setAttribute(ReportSessionName.SIGNOFF.getTravelInsuranceSignDtoName(), signoffDto);
		} else if (signoffDto.getSignatureType() == SignoffDTO.SignatureTypeEnum.HELPERCARE_INSURANCE_FORM_SIGN) {
			request.getSession().setAttribute(ReportSessionName.SIGNOFF.getHelperCareInsuranceSignDtoName(), signoffDto);
		} else if (signoffDto.getSignatureType() == SignoffDTO.SignatureTypeEnum.PROJECT_EAGLE_INSURANCE_FORM_SIGN) {
			request.getSession().setAttribute(ReportSessionName.SIGNOFF.getProjectEagleInsuranceSignDtoName(), signoffDto);
		}
		
		if (getCurrentModeFromSession(request) == 0) {
			reportService.generateAndSaveDigitalModePdf(request, true);
			request.getSession().removeAttribute("mobSignSession");
			return new ModelAndView(new RedirectView("closepopup.jsp"));
		} else {
			String attrUid=(String)request.getParameter("sbuid");
			return new ModelAndView(new RedirectView("mobsign.html?sbuid="+attrUid));
		}
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		
		Map<String, Object> referenceData = new HashMap<String, Object>();
		int mode = getCurrentModeFromSession(request);
		String titleString = "MOB Signature";
		String noteString = "MOB Signature Notes.";
		boolean copyToBankShowInd = false;
		boolean iGuardUAD = isIGuardUADOptionRequired(request);
		boolean isTravelInsurance = isTravelInsurance(request);
		boolean isHelperCareInsurance = isHelperCareInsurance(request);
		boolean isProjectEagleInsurance = isProjectEagleInsurance(request);
		String travelInsuranceDays = "";
		
		if (mode == 8) {
			titleString = "Restart Service Customer Signature<br>Restart Service客戶簽署 ";
			
			noteString = "This signature applies to:<br>此簽名適用於：<br>"
			        + "- Restart Service";
		} else if (mode == 7) {
			titleString = "HKT Care 2-year Helper Insurance Coupon Customer Signature<br>HKT Care 2年家傭保險優惠券客戶簽署 ";
			
			noteString = "This signature applies to:<br>此簽名適用於：<br>"
			        + "- HKT Care 2-year Helper Insurance Coupon";
		
		// Travel Insurance
		} else if (mode == 6) {
			
			String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
			
			travelInsuranceDays = itemDisplayService.getTravelInsuranceDays(selectedVasItemList);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("travelInsuranceDays", travelInsuranceDays);
			
			titleString = "HKT Care EasyShare ${travelInsuranceDays}-day Travel Insurance Package Customer Signature<br>HKT Care好友共享${travelInsuranceDays}日旅遊保險套票客戶簽署 ";
			titleString = Utility.fillInVariables(titleString, params);
			
			noteString = "This signature applies to:<br>此簽名適用於：<br>- HKT Care EasyShare ${travelInsuranceDays}-day Travel Insurance Package";
			noteString = Utility.fillInVariables(noteString, params);
			
		} else if (mode == 5) {
			String mnpNumber = getMNPTransferNumMultiSIM(request);
			titleString = "Mobile Number (" + mnpNumber + ") Portability Transferee’s Signature<br>流動電話號碼(" + mnpNumber + ")可攜服務承讓人簽署";
			noteString = "This signature applies to:<br>"
					+ "此簽名適用於：<br>"
					+ "- Mobile Number Portability Application Form 流動電話號碼可攜服務申請表格<br>"
					+ "- csl mobile Change of Service / Customer Information & Refund Application csl流動通訊更改服務 / 客戶資料及退款申請書<br>";
		} else if (mode == 4) {
			titleString = "i-Guard Customer Signature<br>i-Guard 客戶簽署 ";
	
			noteString = "This signature applies to:<br>此簽名適用於：<br>"
			        + "- i-GUARD Phone & Tablet Accidental Damage Repair Plan 嘉保手機及平板電腦意外保障計劃";
		} else if (mode == 3) {
			PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");

			titleString = "Autopay via Credit Card/Bank Account Direct Debit Authorization and Account Holder’s Signature  <font color='red'>(*Same as Bank/Credit Card A/C Signature)</font><br>"
				+ "信用卡/銀行戶口自動轉賬直接扣賬授權及戶口持有人簽署 <font color='red'>(*須與銀行賬戶/信用咭簽署相同) </font>";
			noteString = "This signature applies to:<br>此簽名適用於：<br>"
					+ "- csl mobile Application Form csl流動通訊服務申請書<br>";
			//credit card + third party
			if (null != paymentDto ) {
				if (StringUtils.isNotEmpty(paymentDto.getPayMethodType())&& "C".equalsIgnoreCase(paymentDto.getPayMethodType())) {
					if (StringUtils.isNotEmpty(paymentDto.getThirdPartyInd())&& "Y".equalsIgnoreCase(paymentDto.getThirdPartyInd())) {
						noteString += "- Autopay via Credit Card – Direct Debit Authorization 信用卡自動轉賬授權書<br>";
					}
				}

			}
		} else if (mode == 2) {
			MnpDTO mnp = (MnpDTO) request.getSession().getAttribute("MNP");
			String mnpNumber = null;
			if (mnp!= null) {
				if ("Y".equals(mnp.getMnp()) && mnp.getMnpMsisdn()!=null) {
					mnpNumber = mnp.getMnpMsisdn();
				} else if ("N".equals(mnp.getMnp()) && mnp.getFutherMnpNumber()!=null) {
					mnpNumber = mnp.getFutherMnpNumber();
				}
			}
			if (mnpNumber != null) {
				titleString = "Mobile Number (" + mnpNumber + ") Portability Transferee’s Signature<br/>流動電話號碼(" + mnpNumber + ")可攜服務承讓人簽署";
			} else {
				titleString = "Mobile Number Portability Transferee’s Signature<br/>流動電話號碼可攜服務承讓人簽署";
			}
			noteString = "This signature applies to:<br>"
					+ "此簽名適用於：<br>"
					+ "- Mobile Number Portability Application Form 流動電話號碼可攜服務申請表格<br>"
					+ "- csl mobile Change of Service / Customer Information & Refund Application csl流動通訊更改服務 / 客戶資料及退款申請書<br>";
		} else if (mode == 1) {
			titleString = "Customer Signature<br>客戶簽署";
			noteString = "This signature applies to:<br>此簽名適用於：<br>"
					+ "- csl mobile Application Form csl流動通訊服務申請書<br>";
			SupportDocUI supportDocUI = (SupportDocUI) request.getSession().getAttribute(SupportDocController.SESSION_SUPPORTDOC);
			if (isMNPSignRequired(request) || (getMNPTransferNumMultiSIM(request) != null)) {
				noteString +=  "- csl mobile Change of Service / Customer Information & Refund Application csl流動通訊更改服務 / 客戶資料及退款申請書<br>";
			}
			if (isSecretarialService( supportDocUI)) {
				noteString +=  "- Mobile Secretarial Service Supplement Form 申請或更改流動秘書服務<br>";
			}
			if ((isMnp(request) && !isMNPSignRequired(request)) || hasMNPSignMultiSim(request)) {
				noteString += "- Mobile Number Portability Application Form 流動電話號碼可攜服務申請表格<br>";
			}
			if (isMobileSafetyPhone(request)) {
				noteString +=  "- Mobile Safety Phone Service Plan Application 平安手機&reg;服務計劃申請書<br>";
			}
			//String orderId = (String) request.getSession().getAttribute("orderIdSession");
			
			/*OrderDTO orderDto = (OrderDTO)request.getSession().getAttribute("orderDto");
			if (this.vasDetailService.isExtraFunctionSimByNfcInd(orderDto.getNfcInd())) {
				noteString +=  "- NFC Mobile Payment服務客戶資料披露同意書 / Agreement for Consent to Disclosure of Customer Information for NFC mobile Payment Service";
			}*/
			
			/*if (this.vasDetailService.hasOctopusSim(orderId)) {
				noteString +=  "- Octopus SIM Service Application 八達通SIM服務計劃申請書";
			}*/
			PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");
			copyToBankShowInd = true;
			//not third party
			if (paymentDto != null){
				//cash no need to show
				if(paymentDto.getPayMethodType()!=null && "M".equalsIgnoreCase(paymentDto.getPayMethodType())){
					copyToBankShowInd = false;
				}
				//third party no need to show
				if("Y".equalsIgnoreCase(paymentDto.getThirdPartyInd())){
					copyToBankShowInd = false;
				}
			}
		}
		referenceData.put("copyToBankShowInd", copyToBankShowInd);	
		referenceData.put("iGuardUAD", iGuardUAD);
		referenceData.put("isTravelInsurance", isTravelInsurance);
		referenceData.put("isHelperCareInsurance", isHelperCareInsurance);
		referenceData.put("isProjectEagleInsurance", isProjectEagleInsurance);
		referenceData.put("titleString", titleString);
		referenceData.put("noteString", noteString);
		referenceData.put("signMode", mode);
		referenceData.put("travelInsuranceDays", travelInsuranceDays);
		
		
		String orderId = (String)request.getSession().getAttribute("orderIdSession");
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		if (salesUserDto != null){
			String md5 = DigestUtils.md5Hex(salesUserDto.getUsername()+":"+orderId);
			SignCaptureDTO signCaptureDTO = createSignCaptureDTO(md5, orderId, salesUserDto.getUsername(),""+mode);
			
			
			signCaptureDTO.setRemark("<tr class=\"table_title\"><td>"+titleString+"</td></tr><tr class=\"contenttext\"><td>"+noteString+"</td></tr>");	
			
			signCaptureService.deleteSignCaptureReq(signCaptureDTO.getReqId());
			signCaptureService.createSignCaptureReq(signCaptureDTO);
			referenceData.put("signCaptureDTO", signCaptureDTO);
					
		}
		
		if (getCurrentModeFromSession(request) == 1) {
			reportService.generateAndSaveDigitalModePdf(request, true);
		}
		
		return referenceData;
	}
	
	private int getCurrentModeFromSession(HttpServletRequest request) {
		int result = 0;
		SignoffDTO customerSignDto = (SignoffDTO) request.getSession().getAttribute("customerSignSession");
		SignoffDTO mnpSignDto = (SignoffDTO) request.getSession().getAttribute("mnpSignSession");
		SignoffDTO bankSignDto = (SignoffDTO) request.getSession().getAttribute("bankSignSession");
		SignoffDTO iGuardSignDto = (SignoffDTO) request.getSession().getAttribute("iGuardSignSession");
		SignoffDTO travelSignDto = (SignoffDTO) request.getSession().getAttribute(ReportSessionName.SIGNOFF.getTravelInsuranceSignDtoName());
		SignoffDTO helperSignDto = (SignoffDTO) request.getSession().getAttribute(ReportSessionName.SIGNOFF.getHelperCareInsuranceSignDtoName());
		SignoffDTO projectEagleSignDto = (SignoffDTO) request.getSession().getAttribute(ReportSessionName.SIGNOFF.getProjectEagleInsuranceSignDtoName());
		String mobSignSession = (String) request.getSession().getAttribute("mobSignSession");
		
		if (mobSignSession == null) {
			result = 1;
			request.getSession().setAttribute("mobSignSession", "mobSignSession");
			request.getSession().removeAttribute("customerSignSession");
			request.getSession().removeAttribute("mnpSignSession");
			request.getSession().removeAttribute("bankSignSession");
			request.getSession().removeAttribute("iGuardSignSession");
			request.getSession().removeAttribute(ReportSessionName.SIGNOFF.getHelperCareInsuranceSignDtoName());
			request.getSession().removeAttribute(ReportSessionName.SIGNOFF.getTravelInsuranceSignDtoName());
			request.getSession().removeAttribute(ReportSessionName.SIGNOFF.getProjectEagleInsuranceSignDtoName());
			removeAllMultiSimSign(request);
		} else if (customerSignDto == null) {
			result = 1;
		} else if (mnpSignDto == null && isMNPSignRequired(request)) {
			result = 2;
		} else if (bankSignDto == null && isBankSignRequired(request)) {
			result = 3;
		} else if (iGuardSignDto == null && isIGuardSignRequired(request)) {
			result = 4;
		} else if (getMNPTransferNumMultiSIM(request) != null) {
			result = 5;
		} else if (travelSignDto == null && isTravelInsurance(request)){
			result = 6;
		} else if (helperSignDto == null && isHelperCareInsurance(request)){
			result = 7;
		} else if (projectEagleSignDto == null && isProjectEagleInsurance(request)) {
			result = 8;
		}
		return result;
	}
	
	boolean isBankSignRequired(HttpServletRequest request) {
		boolean result = false;
		PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");
		if (paymentDto != null && !"M".equalsIgnoreCase(paymentDto.getPayMethodType())) {
			result = true;
		}
		return result;
	}
	
	boolean isIGuardSignRequired(HttpServletRequest request) {
		boolean result = false;
		OrderDTO orderDto= (OrderDTO)request.getSession().getAttribute("orderDto");
		BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		CustomerProfileDTO  customerInfoDto = (CustomerProfileDTO)request.getSession().getAttribute("customer");
	
		List<String> iGuardList=iGuardService.isIGuardOrder(basketDTO.getBasketId(), selectedVasItemList, orderDto.getAppInDate());
		for(int i=0; i<iGuardList.size();i++){
			if("LDS".equalsIgnoreCase(iGuardList.get(i))){
				if(!StringUtils.isEmpty( orderDto.getiGuardSerialNo())){
					result = true;
				}
			}
			else if("AD".equalsIgnoreCase(iGuardList.get(i))){
				result = true;
			}else if ("UAD".equalsIgnoreCase(iGuardList.get(i))){
				result = true;
			}
		}
		if ("Y".equals(customerInfoDto.getCareStatus())&& "I".equals(customerInfoDto.getCareOptInd())){
			result = true;
		}
		return result;
	}
	

	boolean isIGuardUADOptionRequired(HttpServletRequest request) {
		boolean result = false;
		OrderDTO orderDto= (OrderDTO)request.getSession().getAttribute("orderDto");
		BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		
		List<String> iGuardList=iGuardService.isIGuardOrder(basketDTO.getBasketId(), selectedVasItemList, orderDto.getAppInDate());
		for(int i=0; i<iGuardList.size();i++){
			if ("UAD".equalsIgnoreCase(iGuardList.get(i))){
				result = true;
			}
		}
		return result;
	}
		
	boolean isTravelInsurance(HttpServletRequest request) {
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		if (selectedVasItemList!=null){
			return vasDetailService.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE,selectedVasItemList);
		} else {
			return false;
		}
	}
	boolean isHelperCareInsurance(HttpServletRequest request) {
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		if (selectedVasItemList!=null){
			return vasDetailService.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_HELPER_CARE,selectedVasItemList);
		} else {
			return false;
		}
	}
	
	boolean isProjectEagleInsurance(HttpServletRequest request) {
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		if (selectedVasItemList!=null){
			return vasDetailService.existInSelectionGrpList(PROJECT_EAGLE_ITEM_SELECTTION_GROUP_ID,selectedVasItemList);
		} else {
			return false;
		}
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
	
	boolean isMNPSignRequired(HttpServletRequest request) {
		boolean result = false;
		MnpDTO mnpDto = (MnpDTO) request.getSession().getAttribute("MNP");
		if (mnpDto != null) {
			if ("Y".equals(mnpDto.getMnp())) {
				if (!"Prepaid Sim".equals(mnpDto.getCustName())) {
					CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) request.getSession().getAttribute("customer");
					if (!customerInfoDto.getIdDocNum().equalsIgnoreCase(mnpDto.getCustIdDocNum())) {
						result = true;
					}
				}
			} else if ("N".equals(mnpDto.getMnp()) && mnpDto.isFutherMnp()) {
				if (!"Prepaid Sim".equals(mnpDto.getFuthercustName())) {
					CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) request.getSession().getAttribute("customer");
					if (!customerInfoDto.getIdDocNum().equalsIgnoreCase(mnpDto.getFuthercustIdDocNum())) {
						result = true;
					}
				}
			}
		}
		return result;
	}
	
	boolean isMnp(HttpServletRequest request) {
		boolean result = false;
		MnpDTO mnp = (MnpDTO) request.getSession().getAttribute("MNP");
		if (mnp!= null) {
			if ("Y".equals(mnp.getMnp()) && mnp.getMnpMsisdn()!=null) {
				result = true;
			} else if ("N".equals(mnp.getMnp()) && mnp.getFutherMnpNumber()!=null) {
				result = true;
			}
		}
		return result;
	}
	
	private boolean isMobileSafetyPhone(HttpServletRequest request) {
		String appDateStr=(String) request.getSession().getAttribute("appDate");
		Date appDate = StringUtils.isBlank(appDateStr) ? null : Utility.string2Date(appDateStr);
		BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
		String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
		return this.vasDetailService.isItemsInGroup(basketDTO.getBasketId(), selectedItemList, appDate, "100000103");
	}
	
	private String getMNPTransferNumMultiSIM(HttpServletRequest request) {
		String result = null;
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");				
		CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		
		List<String> vasList = vasDetailService.getSubscribedVASList(basketDTO.getBasketId(), selectedVasItemList, customerInfoDto.getBrand(), customerInfoDto.getSimType());
		
		if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
			for (MultiSimInfoDTO msi: multiSimInfoDTOs) {
				if (vasList.contains(msi.getItemId()) && msi.getMembers() != null) {
					for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
						if ((("A".equals(msim.getMnpInd()) && !"Prepaid Sim".equals(msim.getMnpCustName())) || "MUPS05".equals(msim.getMemberOrderType()))
								&& msim.getSignDTO() == null) {
							if (StringUtils.isBlank(msim.getMnpDocNo())) {
								result = msim.getMnpNumber();
							} else {
								if (msim.getMnpDocNo() != null && customerInfoDto != null && !msim.getMnpDocNo().equals(customerInfoDto.getIdDocNum())) {
									result = msim.getMnpNumber();
								}
							}
						}
					}
				}
			}
		}
		
		return result;
	}
	
	private boolean hasMNPSignMultiSim(HttpServletRequest request) {
		boolean hasMNPSign = false;
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );		
		CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		
		List<String> vasList = vasDetailService.getSubscribedVASList(basketDTO.getBasketId(), selectedVasItemList, customerInfoDto.getBrand(), customerInfoDto.getSimType());
		
		if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
			for (MultiSimInfoDTO msi: multiSimInfoDTOs) {
				if (vasList.contains(msi.getItemId()) && msi.getMembers() != null) {
					for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
						if (("A".equals(msim.getMnpInd()) && !"Prepaid Sim".equals(msim.getMnpCustName())) || "MUPS05".equals(msim.getMemberOrderType())) {
							if (msim.getMnpDocNo() != null && customerInfoDto != null 
									&& msim.getMnpDocNo().equals(customerInfoDto.getIdDocNum())) {
								hasMNPSign = true;
							}
						}
					}
				}
			}
		}
		
		return hasMNPSign;
	}
	
	private void setMultiSimSign(HttpServletRequest request, SignoffDTO signDTO) {
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
			for (MultiSimInfoDTO msi: multiSimInfoDTOs) {
				for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
					if (signDTO.getMnpNumber() != null && signDTO.getMnpNumber().equals(msim.getMnpNumber())) {
						msim.setSignDTO(signDTO);
						return;
					}
				}
			}
		}
	}
	
	private void removeAllMultiSimSign(HttpServletRequest request) {
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
			for (MultiSimInfoDTO msi: multiSimInfoDTOs) {
				for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
					msim.setSignDTO(null);
				}
			}
		}
		
	}
	
	
	private SignCaptureDTO createSignCaptureDTO(String reqId, String orderId, String user, String remark){
		SignCaptureDTO signCaptureDTO = new SignCaptureDTO();
		signCaptureDTO.setReqId(reqId);
		signCaptureDTO.setOrderId(orderId);
		signCaptureDTO.setRemark(remark);
		signCaptureDTO.setCreateBy(user);
		signCaptureDTO.setLastUpdBy(user);
		signCaptureDTO.setCreateDate(new Date());
		signCaptureDTO.setLastUpdDate(new Date());
		return signCaptureDTO;
	}
	
}
