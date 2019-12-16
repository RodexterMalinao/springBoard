package com.bomwebportal.web.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.dto.IGuardDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.ServicePlanReportDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.dto.VasOnetimeAmtDTO;
import com.bomwebportal.dto.ui.SupportDocUI;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.IGuardService;
import com.bomwebportal.service.MobileDetailService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.ReportService.Form;
import com.bomwebportal.service.SupportDocService;
import com.bomwebportal.service.VasDetailService;

public class ReportHelper {
	public static enum PdfLang {
		ZH
		, EN
		;
	}
	
	private VasDetailService vasDetailService;// by wilson 20110801, for chinese report display
	private OrderService orderService;// by wilson 20110809, for chinese report display rebate
	private MobileDetailService mobileDetailService;
	private SupportDocService supportDocService; // retrieve proof fm db instead of session
	private IGuardService iGuardService; // for iGuard
	
	/**
	 * @return the vasDetailService
	 */
	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	/**
	 * @param vasDetailService the vasDetailService to set
	 */
	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	/**
	 * @return the orderService
	 */
	public OrderService getOrderService() {
		return orderService;
	}

	/**
	 * @param orderService the orderService to set
	 */
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * @return the mobileDetailService
	 */
	public MobileDetailService getMobileDetailService() {
		return mobileDetailService;
	}

	/**
	 * @param mobileDetailService the mobileDetailService to set
	 */
	public void setMobileDetailService(MobileDetailService mobileDetailService) {
		this.mobileDetailService = mobileDetailService;
	}
	
	public SupportDocService getSupportDocService() {
		return supportDocService;
	}

	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}

	public IGuardService getiGuardService() {
		return iGuardService;
	}

	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}

	public List<Object> getData(HttpServletRequest request, ReportSessionName reportSessionName, String pdfLang) {
		return this.getData(request, reportSessionName, pdfLang, (List<Form>) null);
	}
	
	public List<Object> getData(HttpServletRequest request, ReportSessionName reportSessionName, String pdfLang, Form form) {
		return this.getData(request, reportSessionName, pdfLang, Arrays.asList(form));
	}
	
	public List<Object> getData(HttpServletRequest request, ReportSessionName reportSessionName, String pdfLang, List<Form> forms) {
		CustomerProfileDTO dto = null;
		OrderDTO orderDto = null;
		ServicePlanReportDTO servicePlanRptDto = null;
		PaymentDTO paymentDto = null;
		MnpDTO mnpDto = null;
		MobileSimInfoDTO mobileSimInfo = null;
		BomSalesUserDTO bomsalesuser = null;
		HSTradeDescDTO hsTradeDescDto = null;
		BasketDTO basketDto = null;
		
		SupportDocUI supportDocUI = null;
		SignoffDTO signoffDto  = null;
		SignoffDTO bankSignDto = null;
		SignoffDTO mnpSignDto = null;
		SignoffDTO conciergeSignDto = null;
		SignoffDTO iGuardSignDto = null;
		SignoffDTO tdoSignDto = null;
		IGuardDTO iGuardDto = null;
		
		SignoffDTO travelInsuranceSignDto = null;
		SignoffDTO helperCareInsuranceSignDto = null;
		SignoffDTO projectEagleInsuranceSignDto = null;
						
		orderDto = (OrderDTO) request.getSession().getAttribute(reportSessionName.getOrderDtoName());
		dto = (CustomerProfileDTO) request.getSession().getAttribute(reportSessionName.getCustomerDtoName());
		servicePlanRptDto = (ServicePlanReportDTO) request.getSession().getAttribute(reportSessionName.getServicePlanReportDtoName());
		paymentDto = (PaymentDTO) request.getSession().getAttribute(reportSessionName.getPaymentDtoName());
		mnpDto = (MnpDTO) request.getSession().getAttribute(reportSessionName.getMnpDtoName());
		mobileSimInfo = (MobileSimInfoDTO) request.getSession().getAttribute(reportSessionName.getMobileSimInfoDtoName());
		bomsalesuser = (BomSalesUserDTO) request.getSession().getAttribute(reportSessionName.getBomSalesUserDtoName());
		hsTradeDescDto = (HSTradeDescDTO) request.getSession().getAttribute(reportSessionName.getHsTradeDescDtoName());
		basketDto = (BasketDTO) MobCcsSessionUtil.getSession(request, reportSessionName.getBasketDtoName());
		
		supportDocUI = (SupportDocUI) request.getSession().getAttribute(reportSessionName.getSupportDocDtoName());
		if (orderDto != null && supportDocUI != null) {
			supportDocUI.setAllOrdDocAssgnDTOs(this.supportDocService.getInUseAllOrdDocAssgnDTOALL(orderDto.getOrderId()));
		}
		signoffDto = (SignoffDTO) request.getSession().getAttribute(reportSessionName.getCustSignDtoName());
		bankSignDto = (SignoffDTO) request.getSession().getAttribute(reportSessionName.getBankSignDtoName());
		mnpSignDto = (SignoffDTO) request.getSession().getAttribute(reportSessionName.getMnpSignDtoName());
		conciergeSignDto = (SignoffDTO) request.getSession().getAttribute(reportSessionName.getConciSignDtoName());
		iGuardSignDto = (SignoffDTO) request.getSession().getAttribute(reportSessionName.getiGuardSignDtoName());
		travelInsuranceSignDto = (SignoffDTO) request.getSession().getAttribute(reportSessionName.getTravelInsuranceSignDtoName());
		helperCareInsuranceSignDto = (SignoffDTO) request.getSession().getAttribute(reportSessionName.getHelperCareInsuranceSignDtoName());
		projectEagleInsuranceSignDto = (SignoffDTO) request.getSession().getAttribute(reportSessionName.getProjectEagleInsuranceSignDtoName());
		tdoSignDto = (SignoffDTO) request.getSession().getAttribute(reportSessionName.getTdoSignDtoName());
		List<ComponentDTO> componentList = (List<ComponentDTO>) request.getSession().getAttribute(reportSessionName.getComponentListName());
		List<MultiSimInfoDTO> multiSimInfoList = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList"); //MultiSim Athena 20140128 
		VasMrtSelectionDTO vasMrtSelectionDTO = (VasMrtSelectionDTO)request.getSession().getAttribute("vasMrtSelectionSession");
		VasMrtSelectionDTO ssMrtSelectionDTO = (VasMrtSelectionDTO)request.getSession().getAttribute("ssMrtSelectionSession");
		
		if (this.containsForm(forms, Form.IGUARD_LDS)) {
			iGuardDto = iGuardService.getRsIGuardDTO(dto, basketDto, mobileSimInfo, iGuardSignDto, mnpDto, orderDto, componentList, "LDS");
		}
		
		if (this.containsForm(forms, Form.IGUARD_AD)) {
		    iGuardDto = iGuardService.getRsIGuardDTO(dto, basketDto, mobileSimInfo, iGuardSignDto, mnpDto, orderDto, componentList, "AD");
		}
		
		if (this.containsForm(forms, Form.IGUARD_UAD)) {
		    iGuardDto = iGuardService.getRsIGuardDTO(dto, basketDto, mobileSimInfo, iGuardSignDto, mnpDto, orderDto, componentList, "UAD");
		}
		
		switch (reportSessionName) {
		case ORDER_DETAIL:
			return this.forOrderDetail(pdfLang, dto, orderDto, servicePlanRptDto, 
					paymentDto, mnpDto, mobileSimInfo, bomsalesuser, hsTradeDescDto, 
					basketDto, supportDocUI, iGuardDto,multiSimInfoList, vasMrtSelectionDTO, ssMrtSelectionDTO,componentList);
		case SIGNOFF:
			return this.forSignoff(pdfLang, dto, orderDto, servicePlanRptDto, 
					paymentDto, mnpDto, mobileSimInfo, bomsalesuser, hsTradeDescDto, 
					basketDto, supportDocUI, signoffDto, bankSignDto, mnpSignDto, 
					conciergeSignDto, iGuardDto, tdoSignDto,multiSimInfoList, vasMrtSelectionDTO, ssMrtSelectionDTO,iGuardSignDto,
					travelInsuranceSignDto, helperCareInsuranceSignDto, projectEagleInsuranceSignDto, componentList);
		}
		return Collections.emptyList();
	}

	private List<Object> forOrderDetail(String pdfLang, CustomerProfileDTO dto,
			OrderDTO orderDto, ServicePlanReportDTO servicePlanRptDto,
			PaymentDTO paymentDto, MnpDTO mnpDto,
			MobileSimInfoDTO mobileSimInfo, BomSalesUserDTO bomsalesuser,
			HSTradeDescDTO hsTradeDescDto, BasketDTO basketDto,
			SupportDocUI supportDocUI, IGuardDTO iGuardDto
			, List<MultiSimInfoDTO> multiSimInfoList
			, VasMrtSelectionDTO vasMrtSelectionDTO, VasMrtSelectionDTO ssMrtSelectionDTO, List<ComponentDTO> componentList) {

		String locale = "en";

		if ("zh".equals(pdfLang)) {
			locale = "zh_HK";
		} else {
			locale = "en";
		}

		String orderId = "";
		String selectedBasketId = "";

		if (orderDto != null) {
			orderId = orderDto.getOrderId();
			selectedBasketId = orderDto.getBasketId();
		}

		servicePlanRptDto = getSrvPlanReportDTODetails(servicePlanRptDto,
				locale, orderId, selectedBasketId);

		if (paymentDto != null && 
				(!("C".equals(paymentDto.getPayMethodType()) || "A"
				.equals(paymentDto.getPayMethodType())))) {
			paymentDto = null;
		}
		
		SignoffDTO travelInsuranceSignDto = null;
		SignoffDTO helperCareInsuranceSignDto = null;
		SignoffDTO projectEagleInsuranceSignDto = null;

		return this.getPdfData(dto, orderDto, servicePlanRptDto, paymentDto,
				mnpDto, mobileSimInfo, bomsalesuser, hsTradeDescDto, basketDto,
				supportDocUI, null, null, null, null, iGuardDto, null, multiSimInfoList, vasMrtSelectionDTO, ssMrtSelectionDTO,null,
				travelInsuranceSignDto, helperCareInsuranceSignDto, projectEagleInsuranceSignDto, componentList);
	}

	private List<Object> forSignoff(String pdfLang, CustomerProfileDTO dto,
			OrderDTO orderDto, ServicePlanReportDTO servicePlanRptDto,
			PaymentDTO paymentDto, MnpDTO mnpDto,
			MobileSimInfoDTO mobileSimInfo, BomSalesUserDTO bomsalesuser,
			HSTradeDescDTO hsTradeDescDto, BasketDTO basketDto,
			SupportDocUI supportDocUI, SignoffDTO signoffDto,
			SignoffDTO bankSignDto, SignoffDTO mnpSignDto,
			SignoffDTO conciergeSignDto, IGuardDTO iGuardDto, SignoffDTO tdoSignDto
			, List<MultiSimInfoDTO> multiSimInfoList
			, VasMrtSelectionDTO vasMrtSelectionDTO, VasMrtSelectionDTO ssMrtSelectionDTO,SignoffDTO iGuardSignDto,
			SignoffDTO travelInsuranceSignDto, SignoffDTO helperCareInsuranceSignDto, SignoffDTO projectEagleInsuranceSignDto, List<ComponentDTO> componentList ) {

		String locale = "en";

		if ("zh".equals(pdfLang)) {
			locale = "zh_HK";
		} else {
			locale = "en";
		}

		String orderId = "";
		String selectedBasketId = "";

		if (orderDto != null) {
			orderId = orderDto.getOrderId();
			selectedBasketId = orderDto.getBasketId();
		}

		servicePlanRptDto = getSrvPlanReportDTODetails(servicePlanRptDto,
				locale, orderId, selectedBasketId);

		if (paymentDto != null &&
				(!("C".equals(paymentDto.getPayMethodType()) || "A"
				.equals(paymentDto.getPayMethodType())))) {
			paymentDto = null;
		}

		return this.getPdfData(dto, orderDto, servicePlanRptDto, paymentDto,
				mnpDto, mobileSimInfo, bomsalesuser, hsTradeDescDto, basketDto,
				supportDocUI, signoffDto, bankSignDto, mnpSignDto,
				conciergeSignDto, iGuardDto, tdoSignDto,multiSimInfoList, vasMrtSelectionDTO, ssMrtSelectionDTO,iGuardSignDto,
				travelInsuranceSignDto, helperCareInsuranceSignDto, projectEagleInsuranceSignDto, componentList);
	}

	private List<Object> getPdfData(CustomerProfileDTO dto, OrderDTO orderDto,
			ServicePlanReportDTO servicePlanRptDto, PaymentDTO paymentDto,
			MnpDTO mnpDto, MobileSimInfoDTO mobileSimInfo,
			BomSalesUserDTO bomsalesuser, HSTradeDescDTO hsTradeDescDto,
			BasketDTO basketDto, SupportDocUI supportDocUI,
			SignoffDTO signoffDto, SignoffDTO bankSignDto,
			SignoffDTO mnpSignDto, SignoffDTO conciergeSignDto,
			IGuardDTO iGuardDto, SignoffDTO tdoSignDto
			,List<MultiSimInfoDTO> multiSimInfoList
			, VasMrtSelectionDTO vasMrtSelectionDTO, VasMrtSelectionDTO ssMrtSelectionDTO,SignoffDTO iGuardSignDto,
			SignoffDTO travelInsuranceSignDto, SignoffDTO helperCareInsuranceSignDto, SignoffDTO projectEagleInsuranceSignDto, List<ComponentDTO> componentList) {

		List<Object> allDtos = new ArrayList<Object>();

		for (int i = 0; i < 24; i++) {     // 20130709
			allDtos.add(null);
		}

		allDtos.set(0, dto);
		allDtos.set(1, orderDto);
		allDtos.set(2, servicePlanRptDto);
		allDtos.set(3, paymentDto);
		allDtos.set(4, mnpDto);

		allDtos.set(5, mobileSimInfo);
		allDtos.set(6, bomsalesuser);
		allDtos.set(7, hsTradeDescDto);
		allDtos.set(8, basketDto);
		allDtos.set(9, supportDocUI);

		allDtos.set(10, signoffDto);
		allDtos.set(11, bankSignDto);
		allDtos.set(12, mnpSignDto);
		allDtos.set(13, conciergeSignDto);
		allDtos.set(14, iGuardDto);
        allDtos.set(15, tdoSignDto);  //20130709
        allDtos.set(16, multiSimInfoList);  //MultiSim Athena 20140128
        allDtos.set(17, vasMrtSelectionDTO);
        allDtos.set(18, ssMrtSelectionDTO);
        allDtos.set(19, iGuardSignDto);
        allDtos.set(20, travelInsuranceSignDto);
        allDtos.set(21, helperCareInsuranceSignDto);
        allDtos.set(22, componentList);
        allDtos.set(23, projectEagleInsuranceSignDto);
        
		return allDtos;
	}

	private ServicePlanReportDTO getSrvPlanReportDTODetails(
			ServicePlanReportDTO dto, String locale, String orderId,
			String selectedBasketId) {

		List<VasDetailDTO> vasReportUseDetailList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasHSRPReportUseList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasGifsReportUseDetailList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasOptionalReportUseList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> rebateReportUseList = new ArrayList<VasDetailDTO>();
		List<VasOnetimeAmtDTO> vasOnetimeAmtList = new ArrayList<VasOnetimeAmtDTO>();
		int vasOnetimeAmtFee = 0;

		if (!"".equals(selectedBasketId) && selectedBasketId != null) {

			vasReportUseDetailList = vasDetailService.getReportUseRPHSRPList(
					locale, selectedBasketId, "SS_FORM_RP", orderId);

			vasHSRPReportUseList = vasDetailService.getReportUseVasDetailtList(
					locale, orderId, selectedBasketId);
			// REPORT use free Gifs VAS
			vasGifsReportUseDetailList = vasDetailService
					.getReportUseFreeGifsDetailtList(locale, orderId,
							selectedBasketId);
			// REPORT use optional VAS
			vasOptionalReportUseList = vasDetailService
					.getReportUseVasOptionalDetailtList(locale, orderId,
							selectedBasketId);
			rebateReportUseList = orderService.getRebateList(locale, orderId);// wilson
																				// 20110809
			vasOnetimeAmtList = orderService.getVasOnetimeAmtList(locale, orderId);
			if (vasOnetimeAmtList != null
					&& vasOnetimeAmtList.size() >= 0) {
				for (VasOnetimeAmtDTO temp: vasOnetimeAmtList) {
					vasOnetimeAmtFee += Integer.parseInt(temp.getVasOnetimeAmt());
				}
			};
		}

		if (dto != null) {
			dto.setLocale(locale);
			dto.setMainServDtls(vasReportUseDetailList);
			dto.setVasFreeGifsDtls(vasGifsReportUseDetailList);
			dto.setVasOptionalDtls(vasOptionalReportUseList);
			dto.setVasDtls(vasHSRPReportUseList);
			dto.setRebateList(rebateReportUseList);
			dto.setHandsetDeviceDescription(mobileDetailService
					.getSSFormDesc(orderId));
			dto.setVasOnetimeAmtList(vasOnetimeAmtList);
			dto.setVasOnetimeAmtFee("" + vasOnetimeAmtFee);
		}

		return dto;
	}
	
	private boolean containsForm(List<Form> forms, Form form) {
		if (form == null) {
			return false;
		}
		if (this.isEmpty(forms)) {
			return false;
		}
		return forms.contains(form);
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	// generate file name
	public String getSignedFormsFileName(String orderId, PdfLang pdfLang) {
		switch (pdfLang) {
		case ZH:
			return orderId + "_CHI.pdf";
		case EN:
			return orderId + "_EN.pdf";
		}
		return null;
	}
	
	public String getIGuardSignedFormsFileName(String orderId, String msisdn, String iGuardSerialNo, String iGuardType) {
		if("LDS".equalsIgnoreCase(iGuardType)){
			return msisdn + "_" + iGuardSerialNo + "_" + iGuardType+ ".pdf";
		}else if("AD".equalsIgnoreCase(iGuardType)){
			return msisdn + "_" + iGuardType+ ".pdf";
		}else if("UAD".equalsIgnoreCase(iGuardType)){
			return orderId + "_" + iGuardType+ ".pdf";
		}
		
		return null;
	}
	
	public String getHktCareSignedFormsFileName(String orderId, String msisdn, String offerType) {
		if ("TR001".equalsIgnoreCase(offerType) || "HC001".equalsIgnoreCase(offerType)) {
			return "/HKTCare/" + orderId + "_" + msisdn + "_" + offerType + ".pdf";
		}
		return null;
	}
	
	public String getProjectEagleSignedFormsFileName(String orderId, String msisdn, String offerType) {
		return "/HKTCare/" + orderId + "_" + msisdn + "_" + offerType + ".pdf";
	}
	
	public String getMobileSafetyPhoneFormsFileName(String orderId) {
		return orderId + "_M013_mobile_safety.pdf";
	}
	
	public String getNFCSimFormsFileName(String orderId) {
		return orderId + "_M015_nfc.pdf";
	}
	
	public String getOctopusSimFormsFileName(String orderId) {
		return orderId + "_M016_octopus.pdf";
	}
	
	public String getIGuardCareCashFormsFileName(String orderId, String pdfLang) {
		if (pdfLang.equals("en")){
			return orderId + "_CareCash_EN.pdf";
		}else if (pdfLang.equals("zh")){
			return orderId + "_CareCash_CHI.pdf";			
		}
		return null;
	}
}
