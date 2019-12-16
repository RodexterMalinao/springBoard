package com.bomwebportal.mob.ccs.web;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.AllOrdDocDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.dto.IGuardDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.ServicePlanReportDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasOnetimeAmtDTO;
import com.bomwebportal.dto.report.RptMobileSafetyPhoneDTO;
import com.bomwebportal.dto.report.RptNFCConsentDTO;
import com.bomwebportal.dto.report.RptOctopusConsentDTO;
import com.bomwebportal.dto.report.RptOctopusTnCDTO;
import com.bomwebportal.dto.report.RptTnGServiceFormDTO;
import com.bomwebportal.dto.report.RptTradeInHSDTO;
import com.bomwebportal.mob.ccs.dto.ContactDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocUI;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsPaymentUpfrontService;
import com.bomwebportal.mob.ccs.service.MobCcsReportService;
import com.bomwebportal.mob.ccs.service.MobCcsSupportDocService;
import com.bomwebportal.mob.ccs.service.StaffInfoService;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.DepositService;
import com.bomwebportal.service.IGuardService;
import com.bomwebportal.service.MobileDetailService;
import com.bomwebportal.service.MobileSimInfoService;
import com.bomwebportal.service.MultiSimInfoService;
import com.bomwebportal.service.OrdDocService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.ReportService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.util.GenericReportHelper.GenericForm;
import com.bomwebportal.web.util.ProjectEagleReportHelper;

public class MobCcsReportController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());

	private VasDetailService vasDetailService;
	private OrderService orderService;
	private MobileDetailService mobileDetailService;
	private MobCcsMrtService mobCcsMrtService;
	private MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService;
	private StaffInfoService staffInfoService;
	private CustomerProfileService customerProfileService;
	private DeliveryService deliveryService;
	private MobileSimInfoService mobileSimInfoService;
	private MobCcsReportService mobCcsReportService;
	private MobCcsSupportDocService mobCcsSupportDocService;
	private IGuardService iGuardService;
	private OrdDocService ordDocService;
	private DepositService depositService;
	private MultiSimInfoService multiSimInfoService;
	private ReportService reportService;

	public MultiSimInfoService getMultiSimInfoService() {
		return multiSimInfoService;
	}

	public void setMultiSimInfoService(MultiSimInfoService multiSimInfoService) {
		this.multiSimInfoService = multiSimInfoService;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public MobileDetailService getMobileDetailService() {
		return mobileDetailService;
	}

	public void setMobileDetailService(MobileDetailService mobileDetailService) {
		this.mobileDetailService = mobileDetailService;
	}

	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
	}

	public MobCcsPaymentUpfrontService getMobCcsPaymentUpfrontService() {
		return mobCcsPaymentUpfrontService;
	}

	public void setMobCcsPaymentUpfrontService(
			MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService) {
		this.mobCcsPaymentUpfrontService = mobCcsPaymentUpfrontService;
	}

	public StaffInfoService getStaffInfoService() {
		return staffInfoService;
	}

	public void setStaffInfoService(StaffInfoService staffInfoService) {
		this.staffInfoService = staffInfoService;
	}

	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}

	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}

	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	public MobileSimInfoService getMobileSimInfoService() {
		return mobileSimInfoService;
	}

	public void setMobileSimInfoService(
			MobileSimInfoService mobileSimInfoService) {
		this.mobileSimInfoService = mobileSimInfoService;
	}

	public void setMobCcsReportService(MobCcsReportService mobCcsReportService) {
		this.mobCcsReportService = mobCcsReportService;
	}

	public MobCcsReportService getMobCcsReportService() {
		return mobCcsReportService;
	}

	public MobCcsSupportDocService getMobCcsSupportDocService() {
		return mobCcsSupportDocService;
	}

	public void setMobCcsSupportDocService(
			MobCcsSupportDocService mobCcsSupportDocService) {
		this.mobCcsSupportDocService = mobCcsSupportDocService;
	}
	
	public IGuardService getiGuardService() {
		return iGuardService;
	}

	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}

	public OrdDocService getOrdDocService() {
		return ordDocService;
	}

	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}

	public DepositService getDepositService() {
		return depositService;
	}

	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Date startTime = new Date();
		logger.debug("Start generate pdf: " + startTime);
		logger.info("Start generate pdf: " + startTime);

		try {
			 /* Athena 20131111 online sales report (start) */
			// online sales HsTradeDesc report by item code
			String[] hstdItemList = request.getParameterValues("hstdItemList");
			String osOrderId = request.getParameter("osOrderId");
			String reportType = request.getParameter("reportType");
			String upDateStatus = request.getParameter("upDateStatus");
			if (osOrderId!= null) {
				String watermark = request.getParameter("watermark");
				String Copy = request.getParameter("custCopy");
				Boolean custCopy = true;
				if (Copy!= null && "N".equalsIgnoreCase(Copy)) {
					custCopy = false;
				}
				DeliveryUI deliveryUI = new DeliveryUI();
				PaymentUpFrontUI paymentUpFrontUI = new PaymentUpFrontUI();
				OrderDTO orderDto = new OrderDTO();
				List<StockDTO> stockList = null;
				ContactDTO contactDto = new ContactDTO();
				StaffInfoUI staffInfo = new StaffInfoUI();
				
				orderDto = orderService.getOrderWithPaidAmount(osOrderId);
				OrderMobDTO orderMobDTO = this.orderService.getOrderMobDTO(osOrderId);
				if (orderMobDTO != null) {
					orderDto.setNfcInd(orderMobDTO.getNfcInteger());
				}
				String brand = "1"; //hard-coded csl for online sales order
				
				List<ContactDTO> contactDTOList= deliveryService.findContactDTOList(osOrderId);
				if (contactDTOList != null && !contactDTOList.isEmpty()) {
					for (ContactDTO dto : contactDTOList) {
						if (dto.getContactType() != null && dto.getContactType().equalsIgnoreCase("CC")) {
							contactDto=dto;
						}
					}
				}
				deliveryUI = deliveryService.getMobCcsDelivery(osOrderId);		
				paymentUpFrontUI = mobCcsPaymentUpfrontService.getPaymentUpfront(osOrderId);
				staffInfo = staffInfoService.getStaffInfoDTO(osOrderId);
				
				Double osBalance = 0.0;
				Double totalPayment= 0.0;
				Double paid= 0.0;
				
				paid= (double) mobCcsPaymentUpfrontService.getPaidAmtStsDelivery(osOrderId);
				totalPayment= (double) mobCcsPaymentUpfrontService.getTotalPaidAmtStsDelivery(osOrderId);
				
				
				if("O".equals(reportType)){
					stockList = orderService.getDOAStockAssignment(osOrderId);
				}else{
					stockList = orderService.getStockAssignment(osOrderId);
				}
				osBalance = totalPayment - paid;
				List<String> SmNoList = new ArrayList<String>();
				SmNoList = deliveryService.getSalesMemoNo(osOrderId);
				ArrayList<Object> StsDeliveryDTOArrayList = new ArrayList<Object>();
				StsDeliveryDTOArrayList.add(orderDto); 
				StsDeliveryDTOArrayList.add(deliveryUI); 
				StsDeliveryDTOArrayList.add(contactDto); 
				StsDeliveryDTOArrayList.add(paymentUpFrontUI);
				StsDeliveryDTOArrayList.add(stockList);
				StsDeliveryDTOArrayList.add(osBalance);
				StsDeliveryDTOArrayList.add(staffInfo);
				String pdfName = osOrderId;
				String pdfLang = request.getParameter("pdfLang");
				if (pdfLang!= null){
					if (BomWebPortalCcsConstant.REPORT_LOCALE_ZH_HK.equals(pdfLang)) {
						pdfName = pdfName + BomWebPortalCcsConstant.REPORT_CHI;
						pdfLang = "zh";
					} else {
						pdfName = pdfName + BomWebPortalCcsConstant.REPORT_EN;
						
					}
				}else{
					pdfName = pdfName + BomWebPortalCcsConstant.REPORT_EN;
					pdfLang="en";
				}
				// update check point from 500 to 599
				if ("Y".equalsIgnoreCase(upDateStatus)  && osOrderId.contains("CSBS") ) {
					orderService.updatePendingOrderCheckPoint(osOrderId, "500", "599");
				}
				String save = request.getParameter("save");
				if (save != null && "Y".equals(save)) {
					mobCcsReportService.saveStssDeliveryPdfReports(StsDeliveryDTOArrayList,pdfLang,false,paid,totalPayment,SmNoList,osOrderId, brand);
					AllOrdDocDTO allOrdDocDTO = new AllOrdDocDTO();
					allOrdDocDTO.setOrderId(osOrderId);
					allOrdDocDTO.setDocType(AllDocDTO.DocType.M001);
					allOrdDocDTO.setFilePathName(osOrderId + "_" + (pdfLang.equals("zh")? "CHI.pdf" : "EN.pdf"));
					allOrdDocDTO.setProcessDate(null);
					allOrdDocDTO.setCreateBy(orderDto.getSalesCd());
					allOrdDocDTO.setLastUpdBy(orderDto.getSalesCd());
					ordDocService.insertAllOrderDocDTO(allOrdDocDTO);
					response.getWriter().println("true");
					
					Date endTime = new Date();
					logger.debug("End save pdf: " + endTime);
					logger.info("End save pdf: " + endTime);
					
					return null;
				}

				response.setContentType("application/pdf");
				response.addHeader("Content-disposition", "attachment; filename="+ pdfName);
				response.setHeader("Cache-Control", "private");
				
				OutputStream outputStream = response.getOutputStream();
				if("O".equals(reportType)){
					mobCcsReportService.generateStsDOADeliveryNote(StsDeliveryDTOArrayList,outputStream,pdfLang,SmNoList,custCopy, osOrderId, brand);
				}else{
					if (watermark != null && "Y".equals(watermark)) {
						mobCcsReportService.generateStsDeliveryNote(StsDeliveryDTOArrayList,outputStream,pdfLang,true,paid,totalPayment,SmNoList,osOrderId, custCopy, brand);
					} else {
						mobCcsReportService.generateStsDeliveryNote(StsDeliveryDTOArrayList,outputStream,pdfLang,false,paid,totalPayment,SmNoList,osOrderId, custCopy, brand);
					}
				}

				Date endTime = new Date();
				logger.debug("End save pdf: " + endTime);
				logger.info("End save pdf: " + endTime);
				return null;
			}

			if (hstdItemList != null && hstdItemList.length != 0) {
					List<HSTradeDescDTO> hstdDTOs = new ArrayList<HSTradeDescDTO>();
					int i=0;
					String pdfName = "PIS_form";
					String pdfLang = request.getParameter("pdfLang");
					if (pdfLang!= null){
						if (BomWebPortalCcsConstant.REPORT_LOCALE_ZH_HK.equals(pdfLang)) {
							pdfName = pdfName + BomWebPortalCcsConstant.REPORT_CHI;
							pdfLang = "zh";
						} else {
							pdfName = pdfName + BomWebPortalCcsConstant.REPORT_EN;
						}
					}else{
						pdfName = pdfName + BomWebPortalCcsConstant.REPORT_EN;
						pdfLang = "en";
					}
					for(i=0;i<hstdItemList.length;i++){
						HSTradeDescDTO hsDesc =mobCcsReportService.getHSTradeDescByItemCode(hstdItemList[i]);
						if(hsDesc!= null){
							hstdDTOs.add(hsDesc);
						}
					}

					response.setContentType("application/pdf");
					response.addHeader("Content-disposition", "attachment; filename="+ pdfName);
					response.setHeader("Cache-Control", "private");
					
					OutputStream outputStream = response.getOutputStream();
					mobCcsReportService.generatePISform(hstdDTOs,outputStream,pdfLang,osOrderId, "1"); //hard-coded csl for online sales order
					
					Date endTime = new Date();
					logger.debug("End save pdf: " + endTime);
					logger.info("End save pdf: " + endTime);
					return null;
			/* Athena 20131111 online sales report (end) */
		} else {
			String orderId = request.getParameter("orderId");
			String printSeq = request.getParameter("printSeq");
			String updatePendingOrdercheckPoint = request.getParameter("upDateStatus");

			String basketId = orderService.findBasketId(orderId);
			String locale = BomWebPortalCcsConstant.REPORT_LOCALE_EN;
			
			CustomerProfileDTO customerInfoDto = customerProfileService.getCustomerProfile(orderId);
			String brand = customerInfoDto.getBrand();
			
			DeliveryUI deliveryUI = new DeliveryUI();
			BomSalesUserDTO salesUserDto = new BomSalesUserDTO();
			ArrayList<MobCcsMrtBaseDTO> mobCcsMrtDtoList = null;
			MnpDTO mnpDto = new MnpDTO();
			PaymentUpFrontUI paymentUpFrontUI = new PaymentUpFrontUI();
			PaymentDTO paymentDto = new PaymentDTO();
			MobileSimInfoDTO mobileSimInfo = new MobileSimInfoDTO();
			ModelDTO mobileDetail = new ModelDTO();
			OrderDTO orderDto = new OrderDTO();
			AccountDTO accountDto = new AccountDTO();
			List<StockDTO> stockList = null;
			BasketDTO basketDto = new BasketDTO();
			StaffInfoUI staffInfo = new StaffInfoUI();
			MobCcsSupportDocUI supportDocUI = new MobCcsSupportDocUI();
			MRTUI mrtUI = new MRTUI();
			IGuardDTO iGuardDto = new IGuardDTO();
			RptMobileSafetyPhoneDTO rptMobileSafetyPhoneDTO = new RptMobileSafetyPhoneDTO();
			RptTradeInHSDTO rptTradeInHSDTO = new RptTradeInHSDTO();
			RptNFCConsentDTO rptNFCConsentDTO = new RptNFCConsentDTO();
			RptOctopusTnCDTO rptOctopusTnCDTO = new RptOctopusTnCDTO();
			RptOctopusConsentDTO rptOctopusConsentDTO = new RptOctopusConsentDTO();
			List<ComponentDTO> componentList = this.orderService.getComponentList(orderId);
			
			orderDto = orderService.getOrderWithPaidAmount(orderId);
			OrderMobDTO orderMobDTO = this.orderService.getOrderMobDTO(orderId);
			if (orderMobDTO != null) {
				orderDto.setNfcInd(orderMobDTO.getNfcInteger());
			}
			
			List<MultiSimInfoDTO> multiSimInfoDTOs = null;
			multiSimInfoDTOs = multiSimInfoService.getMultiSimInfoDTO(orderId, locale, 
			    		Utility.date2String(orderDto.getAppInDate(), "dd/MM/yyyy"), "2", orderDto.getShopCode(), customerInfoDto.getBrand(), customerInfoDto.getSimType());  //MultiSim Athena 20140128
			
			//D = delivery Form, G = Courier Guid, O = DOA delivery
			if (printSeq.contains("D") || printSeq.contains("O") || printSeq.contains("G")){
				deliveryUI = deliveryService.getMobCcsDelivery(orderId);
			}
			
			////D = delivery Form, O = DOA delivery
			if (printSeq.contains("D") || printSeq.contains("O") || printSeq.contains("C") || printSeq.contains("M")){
				staffInfo = staffInfoService.getStaffInfoDTO(orderId);
			}
			
			//D = delivery Form, G = Courier Guid
			if (printSeq.contains("D") || printSeq.contains("G")){
				paymentUpFrontUI = mobCcsPaymentUpfrontService.getPaymentUpfront(orderId);
			}
			
			//S = SSForm, G = Courier Guid, C = Change Service Form
			
			if (!printSeq.contains("M")) {
				salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
			}
			
			mobCcsMrtDtoList = mobCcsMrtService.getMobCcsMrtDTO(orderId);

			mnpDto = mobCcsMrtService.convertToMnpDto(mobCcsMrtDtoList);
			
			if (mobCcsMrtDtoList != null) {
				mrtUI = mobCcsMrtService.mrtDtoChangeToUiDto(mobCcsMrtDtoList);
			}
						
			//S = SSForm, G = Courier Guid
			if(printSeq.contains("S") || printSeq.contains("G") || printSeq.contains("M")){
				paymentDto = orderService.getPayment(orderId);
			}
			
			mobileDetail = mobileDetailService.getMobileDetail(locale, basketId);
			mobileSimInfo = orderService.getSim(orderId);
			accountDto = orderService.getAccount(orderId);
			
			//add by Eliot 20120509
			if("O".equals(printSeq)){
				stockList = orderService.getDOAStockAssignment(orderId);
			}else{
				stockList = orderService.getStockAssignment(orderId);
				if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {	//MultiSim Athena 20140128
					if (stockList.size() > 0 && stockList.get(0) instanceof StockDTO) {
						for (StockDTO stksItem:stockList){
							if (stksItem.getItemSerial().equalsIgnoreCase(mobileSimInfo.getIccid())){
								stksItem.setItemDesc(stksItem.getItemDesc()+" (Primary MRT: "+orderDto.getMsisdn()+")");
								//stksItem.setItemSerial(stksItem.getItemSerial()+" (Primary MRT: "+orderDto.getMsisdn()+")");
							}
							for(MultiSimInfoDTO multiSimInfoDTO: multiSimInfoDTOs){
								List<MultiSimInfoMemberDTO> multiSimInfoMemberDTOs=multiSimInfoDTO.getMembers();
								if (multiSimInfoMemberDTOs != null && multiSimInfoMemberDTOs.size() > 0) {
									for(MultiSimInfoMemberDTO multiSimInfoMemberDTO: multiSimInfoMemberDTOs){
										if (stksItem.getType().equalsIgnoreCase("SIM") && multiSimInfoMemberDTO.getSim().getIccid()!=null){	
											if (multiSimInfoMemberDTO.getSim().getIccid().equalsIgnoreCase(stksItem.getItemSerial())){
												stksItem.setItemDesc(stksItem.getItemDesc()+" (MRT: "+multiSimInfoMemberDTO.getMsisdn()+")");
												//stksItem.setItemSerial(stksItem.getItemSerial()+" (MRT: "+multiSimInfoMemberDTO.getMsisdn()+")");
											}
										}
									}
								}
							}
						}
					}
				}
			}
			//G = Courier Guid, C = Change Service Form
			if(printSeq.contains("G") || printSeq.contains("C")){
				supportDocUI.setMobCcsSupportDocDTOList(mobCcsSupportDocService
						.getMobCcsSupportDocDTOForCourierGuid(orderId));
			}

			// add by eliot 20120305
			// update check point from 500 to 599
			if ("Y".equalsIgnoreCase(updatePendingOrdercheckPoint) && orderId.contains("CSBS")) {
				orderService.updatePendingOrderCheckPoint(orderId, "500", "599");
			}

			ArrayList<Object> ccsFormDTOArrayList = new ArrayList<Object>();
			ArrayList<Object> mobccsFormDTOArrayList = new ArrayList<Object>();
			String pdfLang = request.getParameter("pdfLang");

			String pdfName = "";

			pdfName = orderDto.getOrderId();

			if (BomWebPortalCcsConstant.REPORT_LOCALE_ZH_HK.equals(pdfLang)) {
				pdfName = pdfName + BomWebPortalCcsConstant.REPORT_CHI;
				locale = BomWebPortalCcsConstant.REPORT_LOCALE_ZH_HK;
				pdfLang = "zh";
			} else {
				pdfName = pdfName + BomWebPortalCcsConstant.REPORT_EN;
				locale = BomWebPortalCcsConstant.REPORT_LOCALE_EN;
			}

			List<VasDetailDTO> vasReportUseDetailList = new ArrayList<VasDetailDTO>();
			List<VasDetailDTO> vasHSRPReportUseList = new ArrayList<VasDetailDTO>();
			List<VasDetailDTO> vasGifsReportUseDetailList = new ArrayList<VasDetailDTO>();
			List<VasDetailDTO> vasOptionalReportUseList = new ArrayList<VasDetailDTO>();
			List<VasOnetimeAmtDTO> vasOnetimeAmtList = new ArrayList<VasOnetimeAmtDTO>();
			int vasOnetimeAmtFee = 0;
			List<VasDetailDTO> rebateReportUseList = new ArrayList<VasDetailDTO>();

			if (StringUtils.isNotEmpty(basketId)) {

				vasReportUseDetailList = vasDetailService
						.getReportUseRPHSRPList(locale, basketId, "SS_FORM_RP",	orderId);

				vasHSRPReportUseList = vasDetailService
						.getReportUseVasDetailtList(locale, orderId, basketId);
				// REPORT use free Gifs VAS
				vasGifsReportUseDetailList = vasDetailService
						.getReportUseFreeGifsDetailtList(locale, orderId, basketId);
				// REPORT use optional VAS
				vasOptionalReportUseList = vasDetailService
						.getReportUseVasOptionalDetailtList(locale, orderId, basketId);

				vasOnetimeAmtList
					= orderService.getVasOnetimeAmtList(locale, orderId);
				if (vasOnetimeAmtList != null
						&& vasOnetimeAmtList.size() >= 0) {
					for (VasOnetimeAmtDTO dto: vasOnetimeAmtList) {
						vasOnetimeAmtFee += Integer.parseInt(dto.getVasOnetimeAmt());
					}
				}
				
				if (mobileSimInfo.getSimCharge() > 0) {
					VasOnetimeAmtDTO simOnetimeCharge = new VasOnetimeAmtDTO();
					if ("zh".equalsIgnoreCase(pdfLang)) {
						simOnetimeCharge.setItemDesc("SIM卡費用");
					} else {
						simOnetimeCharge.setItemDesc("SIM Charge");
					}
					if (mobileSimInfo.getSimWaiveReason() != null && !"".equals(mobileSimInfo.getSimWaiveReason())) {
						simOnetimeCharge.setVasOnetimeAmt("0");
						//simOnetimeAmtCharge = mobileSimInfo.getSimCharge(); included in prepayment waive
					} else {
						simOnetimeCharge.setVasOnetimeAmt("" + mobileSimInfo.getSimCharge());
						vasOnetimeAmtFee += mobileSimInfo.getSimCharge();
					}
					vasOnetimeAmtList.add(simOnetimeCharge);
				}
				
				List<VasOnetimeAmtDTO> adminChargeMemberToo1List =orderService.getRetToo1AdmChargeList(locale, orderId);
				if (adminChargeMemberToo1List != null
						&& adminChargeMemberToo1List.size() >= 0) {
					for (VasOnetimeAmtDTO adminChargeMemberToo1: adminChargeMemberToo1List) {
						vasOnetimeAmtFee += Integer.parseInt(adminChargeMemberToo1.getVasOnetimeAmt());
						vasOnetimeAmtList.add(adminChargeMemberToo1);
					}
				}
				
				
				rebateReportUseList = orderService.getRebateList(locale, orderId);
				
				basketDto = vasDetailService.getBasketAttribute(basketId,
						Utility.date2String(orderDto.getAppInDate(),
								BomWebPortalConstant.DATE_FORMAT));

				logger.debug("MobCcsReport - Handset payment: " + basketDto.getUpfrontAmt());
				logger.debug("MobCcsReport - Pre-payment: " + basketDto.getPrePaymentAmt());
			}

			ServicePlanReportDTO servicePlanReport = new ServicePlanReportDTO();
			servicePlanReport.setMainServDtls(vasReportUseDetailList);
			servicePlanReport.setVasFreeGifsDtls(vasGifsReportUseDetailList);
			servicePlanReport.setVasOptionalDtls(vasOptionalReportUseList);
			servicePlanReport.setVasDtls(vasHSRPReportUseList);
			servicePlanReport.setHandsetDeviceAmount(Long.toString(orderService.getHandsetDeviceAmount(orderId)));
			servicePlanReport.setFirstMonthFee(Long.toString(orderService.getFirstMonthFee(orderId)));
			servicePlanReport.setFirstMonthServiceLicenceFee(Long.toString(orderService.getFirstMonthServiceLicenceFee(orderId)));
			servicePlanReport.setVasOnetimeAmtList(vasOnetimeAmtList);
			servicePlanReport.setVasOnetimeAmtFee("" + vasOnetimeAmtFee);
			servicePlanReport.setRebateList(rebateReportUseList);

			int billPeriod = Integer.parseInt(accountDto.getBillPeriod());
			servicePlanReport.setBillPeriod("" + (billPeriod - 1));

			servicePlanReport.setPenaltyinfo(orderService.getPenaltyInfoList(orderId));

			if (mobileDetail != null) {
				if (StringUtils.isNotEmpty(mobileDetail.getModelId())) {
					servicePlanReport.setHandsetDeviceInfo(orderService.getHandsetDeviceDescription(
							  							   BomWebPortalCcsConstant.REPORT_LOCALE_EN,
							  							   mobileDetail.getBrandId(),
							  							   mobileDetail.getModelId(),
							  							   mobileDetail.getColorDto().get(0).getColorId()));
							
				}
			}

			servicePlanReport.setSecSrvContractPeriod(orderService.getSecSrvContractPeriod(orderId));
			servicePlanReport.setConciergeInd(orderService.getConciergeInd(orderId));
			logger.info("ConciergeInd:" + servicePlanReport.getConciergeInd());
			servicePlanReport.setBasketType(orderService.getBasketType(orderId));
			logger.info("BasketType:" + servicePlanReport.getBasketType());
			servicePlanReport.setLocale(locale);
			servicePlanReport.setHandsetDeviceDescription(mobileDetailService.getSSFormDesc(orderId));

			ccsFormDTOArrayList.add(customerInfoDto);
			ccsFormDTOArrayList.add(servicePlanReport);

			if ("C".equals(paymentDto.getPayMethodType()) || "A".equals(paymentDto.getPayMethodType())) {
				ccsFormDTOArrayList.add(paymentDto);
			}

			ccsFormDTOArrayList.add(mnpDto);
			ccsFormDTOArrayList.add(salesUserDto);
			ccsFormDTOArrayList.add(mobileSimInfo);
			ccsFormDTOArrayList.add(orderDto);
			ccsFormDTOArrayList.add(mrtUI);
			ccsFormDTOArrayList.add(staffInfo);
			
			List<String> vasTempList = new ArrayList<String>();
			for (VasDetailDTO vas :vasOptionalReportUseList) {
				vasTempList.add(vas.getItemId());
			}

			String [] vasGuardList = (String[]) vasTempList.toArray(new String[2]);

			List<String> srvPlanList=iGuardService.isIGuardOrder(orderDto.getBasketId(), vasGuardList, orderDto.getAppInDate());
			if (printSeq.contains("I") && orderDto != null
					&& srvPlanList !=null) {
				 for(int i=0; i<srvPlanList.size();i++){
					  String srvPlanType=srvPlanList.get(i);
						iGuardDto = iGuardService.getCcsIGuardDTO(customerInfoDto, orderDto, mrtUI, basketDto, stockList, componentList,srvPlanType);
						ccsFormDTOArrayList.add(iGuardDto); 
						mobccsFormDTOArrayList.add(iGuardDto);
						mobccsFormDTOArrayList.add("<b><u>Remark: "+ srvPlanType+" i-Guard PhoneProtector Form (1 set 2 copy) attached</u></b>");
				}
			}
			
			String tradeInItem = mobCcsReportService.hasTradeInHS(orderId);
			if (printSeq.contains("T") && (tradeInItem != null)) {
				BeanUtils.copyProperties(rptTradeInHSDTO, customerInfoDto);
				//BeanUtils.copyProperties(rptTradeInHSDTO, mnpDto);
				BeanUtils.copyProperties(rptTradeInHSDTO, orderDto);
				rptTradeInHSDTO.setDeliveryDate(deliveryUI.getDeliveryDate());
				String tradeInDescription = mobCcsReportService.getTradeInRBSchedule(tradeInItem, locale);
				rptTradeInHSDTO.setRebateSchedule(tradeInDescription);
				String imeiAttribID = mobCcsReportService.getTradeInIMEIAttributeID();
				rptTradeInHSDTO.setImei("");
				for (ComponentDTO dto : componentList) {
					if (dto.getCompAttbId().equals(imeiAttribID)) {
						rptTradeInHSDTO.setImei(dto.getCompAttbVal());
					}
				}
				ccsFormDTOArrayList.add(rptTradeInHSDTO); 
				mobccsFormDTOArrayList.add(rptTradeInHSDTO);
			}
			
			if (printSeq.contains("P") && mobCcsReportService.hasMobileSafetyPhoneOffer(orderId)) {
				rptMobileSafetyPhoneDTO = new RptMobileSafetyPhoneDTO();
				BeanUtils.copyProperties(rptMobileSafetyPhoneDTO, customerInfoDto);
				rptMobileSafetyPhoneDTO.setCustFirstName(customerInfoDto.getCustFirstName());
				BeanUtils.copyProperties(rptMobileSafetyPhoneDTO, mnpDto);
				BeanUtils.copyProperties(rptMobileSafetyPhoneDTO, orderDto);
				if ("N".equalsIgnoreCase(mnpDto.getMnp()) || "A".equalsIgnoreCase(mnpDto.getMnp())) {
					logger.info("mnpDto.getServiceReqDate() = " + mnpDto.getServiceReqDate());
					rptMobileSafetyPhoneDTO.setTargetCommencementDate(mnpDto.getServiceReqDate());
				} else {
					logger.info("mnpDto.getCutoverDate() = " + mnpDto.getCutoverDate());
					rptMobileSafetyPhoneDTO.setTargetCommencementDate(mnpDto.getCutoverDate());
				}
				
				for (ComponentDTO dto : componentList) {
					if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.CONTACT_PERSON.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceContactPerson(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.CONTACT_PHONE.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceContactPhone(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.USER_NAME.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceUserName(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.USER_TITLE.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceUserTitle(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.INSTALL_ADDR1.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceInstallAddr1(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.INSTALL_ADDR2.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceInstallAddr2(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.INSTALL_ADDR3.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceInstallAddr3(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.INSTALL_ADDR4.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceInstallAddr4(dto.getCompAttbVal());
					} else if (dto.getCompAttbId().equals(RptMobileSafetyPhoneDTO.ServiceAttb.INSTALL_ADDR5.getAttbId())) {
						rptMobileSafetyPhoneDTO.setServiceInstallAddr5(dto.getCompAttbVal());
					} 
				}
				ccsFormDTOArrayList.add(rptMobileSafetyPhoneDTO); 
				mobccsFormDTOArrayList.add(rptMobileSafetyPhoneDTO);
			}
			

			if (printSeq.contains("N") && StringUtils.isNotBlank(orderDto.getNfcInd()) && !"0".equals(orderDto.getNfcInd()) && !customerInfoDto.isStudentPlanSubInd()) {

				rptNFCConsentDTO = new RptNFCConsentDTO();
				BeanUtils.copyProperties(rptNFCConsentDTO, customerInfoDto);
				BeanUtils.copyProperties(rptNFCConsentDTO, orderDto);
				rptNFCConsentDTO.setAppInDate(null);
				ccsFormDTOArrayList.add(rptNFCConsentDTO); 
				mobccsFormDTOArrayList.add(rptNFCConsentDTO);
				
				rptOctopusTnCDTO = new RptOctopusTnCDTO();
				ccsFormDTOArrayList.add(rptOctopusTnCDTO); 
				mobccsFormDTOArrayList.add(rptOctopusTnCDTO);
			}
			/*if (printSeq.contains("N") && mobCcsReportService.hasNFCSim(orderId)) {
				rptNFCConsentDTO = new RptNFCConsentDTO();
				BeanUtils.copyProperties(rptNFCConsentDTO, customerInfoDto);				
				BeanUtils.copyProperties(rptNFCConsentDTO, orderDto);
				rptNFCConsentDTO.setAppInDate(null);
				ccsFormDTOArrayList.add(rptNFCConsentDTO); 
				mobccsFormDTOArrayList.add(rptNFCConsentDTO);
				
				rptOctopusTnCDTO = new RptOctopusTnCDTO();
				ccsFormDTOArrayList.add(rptOctopusTnCDTO); 
				mobccsFormDTOArrayList.add(rptOctopusTnCDTO);
			}
			
			if (printSeq.contains("N") && mobCcsReportService.hasOctopusSim(orderId)) {
				rptOctopusConsentDTO = new RptOctopusConsentDTO();
				BeanUtils.copyProperties(rptOctopusConsentDTO, customerInfoDto);				
				BeanUtils.copyProperties(rptOctopusConsentDTO, orderDto);
				rptOctopusConsentDTO.setAppInDate(null);
				ccsFormDTOArrayList.add(rptOctopusConsentDTO); 
				mobccsFormDTOArrayList.add(rptOctopusConsentDTO);
			}*/

			
			if (printSeq.contains("F") && orderService.hasTNGServiceDummyVas(orderId)) {
				RptTnGServiceFormDTO rptTnGServiceFormDTO = new RptTnGServiceFormDTO();
				rptTnGServiceFormDTO.setAppInDate(orderDto.getAppInDate());
				rptTnGServiceFormDTO.setAppInDateStr(Utility.date2String(((OrderDTO) orderDto).getAppInDate(),
						BomWebPortalConstant.DATE_FORMAT));
				if ("HKID".equals(((CustomerProfileDTO) customerInfoDto).getIdDocType())) {
					rptTnGServiceFormDTO.setIdDocNum(((CustomerProfileDTO) customerInfoDto).getIdDocNum());
					rptTnGServiceFormDTO.setCustName(((CustomerProfileDTO) customerInfoDto).getTitle()
							+ " " + ((CustomerProfileDTO) customerInfoDto).getCustLastName()
							+ " " + ((CustomerProfileDTO) customerInfoDto).getCustFirstName());
				}
				
			
				mobccsFormDTOArrayList.add(rptTnGServiceFormDTO);
				ccsFormDTOArrayList.add(rptTnGServiceFormDTO); 
			}

			
			
			// ******************************************
			// SAVE TO SERVERS, FOR MOBILE ONLINE SALES
			// ******************************************
			String msisdn = orderDto == null ? "" : orderDto.getMsisdn();

			String [] vasGuardList1 = (String[]) vasTempList.toArray(new String[2]);
			List<String> iGuardItemSelectionGroupIdList = iGuardService.isIGuardOrder(basketDto.getBasketId(), vasGuardList1,orderDto.getAppInDate());
			if ("Y".equals(customerInfoDto.getCareStatus())) {
				iGuardItemSelectionGroupIdList.add("CARECASH");
			}
			
			String save = request.getParameter("save");
			if (save != null && "Y".equals(save)) {
				mobCcsReportService.savePdfReports(ccsFormDTOArrayList, pdfLang, orderId, iGuardItemSelectionGroupIdList, brand);
				AllOrdDocDTO allOrdDocDTO = new AllOrdDocDTO();
				allOrdDocDTO.setOrderId(orderId);
				allOrdDocDTO.setDocType(AllDocDTO.DocType.M001);
				allOrdDocDTO.setFilePathName(orderId + "_" + (BomWebPortalCcsConstant.REPORT_LOCALE_ZH_HK.equals(locale)? "CHI.pdf" : "EN.pdf"));
				allOrdDocDTO.setProcessDate(null);
				allOrdDocDTO.setCreateBy(salesUserDto.getUsername());
				allOrdDocDTO.setLastUpdBy(salesUserDto.getUsername());
				ordDocService.insertAllOrderDocDTO(allOrdDocDTO);
				response.getWriter().println("true");
				
				Date endTime = new Date();
				logger.debug("End save pdf: " + endTime);
				logger.info("End save pdf: " + endTime);
				
				return null;
			}

			mobccsFormDTOArrayList.add(orderDto); // DN,CG,CS
			mobccsFormDTOArrayList.add(mnpDto); // DN,CG,CS
			mobccsFormDTOArrayList.add(deliveryUI); // DN,CG
			mobccsFormDTOArrayList.add(staffInfo); // DN,CS
			//mobccsFormDTOArrayList.add(basketDto); // DN,CG
			mobccsFormDTOArrayList.add(paymentUpFrontUI); // DN
			mobccsFormDTOArrayList.add(stockList); // DN,DOADN
			mobccsFormDTOArrayList.add(supportDocUI); // CG
			mobccsFormDTOArrayList.add(mobileSimInfo); // CG,CS
			mobccsFormDTOArrayList.add(servicePlanReport); // CG
			mobccsFormDTOArrayList.add(paymentDto); // CG
			mobccsFormDTOArrayList.add(customerInfoDto); // CS
			mobccsFormDTOArrayList.add(mrtUI); // CS, CG
			mobccsFormDTOArrayList.add(multiSimInfoDTOs);//MultiSim Athena 20140128
			ccsFormDTOArrayList.add(multiSimInfoDTOs);//MultiSim Athena 20140128

			Double osBalance = 0.0;	
			Double hsPayment = 0.0;
			Double prePayment = 0.0;
			Double paid = 0.0;
			Double too1AdminCharge = vasDetailService.getMupAdminChargeAmount(orderId);
			if (basketDto != null) {
				if (StringUtils.isNotEmpty(basketDto.getUpfrontAmt())) {
					hsPayment = Double.parseDouble(basketDto.getUpfrontAmt());
				}
			}
			Long prepay = orderService.getPrepaymentWithoutHandset(orderId);
			if (prepay != null) {
				prePayment = prepay.doubleValue() ;
				basketDto.setPrePaymentAmt("" + prePayment);
			}
			
			mobccsFormDTOArrayList.add(basketDto); // DN,CG
			ccsFormDTOArrayList.add(basketDto);
			
			ccsFormDTOArrayList.add(componentList);
			
			if (orderDto != null) {
				if (StringUtils.isNotEmpty(orderDto.getPaidAmt())) {
					paid = Double.parseDouble(orderDto.getPaidAmt());
				}
			}
			
			osBalance = hsPayment + prePayment + too1AdminCharge - paid;
			if(orderDto!=null){//20140220 deposit report Athena
				
				BigDecimal depositTotal= depositService.getDepositAmountForOrder(orderDto.getAgreementNum());
				if (depositTotal!=null){
					osBalance = hsPayment + prePayment + (Double)depositTotal.doubleValue() - paid;
				}
			}
			mobccsFormDTOArrayList.add(osBalance); // DN
			logger.debug("****************************" + osBalance);
			
			response.setContentType("application/pdf");
			response.addHeader("Content-disposition", "attachment; filename=" + pdfName);
			response.setHeader("Cache-Control", "private");
			
			OutputStream outputStream = response.getOutputStream();
			
			printSeq = removeUnnecessaryFormFromPrintSeq(
					printSeq,
					vasReportUseDetailList,
					vasGifsReportUseDetailList,
					vasOptionalReportUseList);
			
			String watermark = request.getParameter("watermark");
			mobCcsReportService.generateCCSPdfReports(ccsFormDTOArrayList,
					  mobccsFormDTOArrayList, 
					  outputStream, 
					  pdfLang, 
					  orderId,
					  printSeq,
					  (watermark != null && "Y".equals(watermark)),
					  iGuardItemSelectionGroupIdList,
					  brand);
				
		}
			Date endTime = new Date();
			logger.debug("End generate pdf: " + endTime);
		} catch (Exception e) {
			logger.error("Exception in MobCcsReportController: "
					+ ExceptionUtils.getFullStackTrace(e));

			response.resetBuffer();
			response.setContentType("text/html");
			response.setHeader("Content-Disposition", null);
			response.getWriter().println(
					"Exception in CCSReportController: "
							+ ExceptionUtils.getFullStackTrace(e));
		}
		return new ModelAndView();
	}

	private String removeUnnecessaryFormFromPrintSeq(String printSeq, List<VasDetailDTO> vasReportUseDetailList,
			List<VasDetailDTO> vasGifsReportUseDetailList, List<VasDetailDTO> vasOptionalReportUseList) {
		
		if (StringUtils.isBlank(printSeq)) {
			return "";
		}
		
		String finalPrintSeq = printSeq;
		
		List<VasDetailDTO> allSubscribedVasList = new ArrayList<VasDetailDTO>();
		allSubscribedVasList.addAll(vasReportUseDetailList);
		allSubscribedVasList.addAll(vasGifsReportUseDetailList);
		allSubscribedVasList.addAll(vasOptionalReportUseList);
		
		List<String> allSubscribedVasItemIdList = new ArrayList<String>();
		for (VasDetailDTO allSubscribedVas : allSubscribedVasList) {
			allSubscribedVasItemIdList.add(allSubscribedVas.getItemId());
		}
		
		boolean isRestartService = vasDetailService.existInSelectionGrpList(ProjectEagleReportHelper.ITEM_SELECTTION_GROUP_ID, allSubscribedVasItemIdList);
		if (!isRestartService) {
			finalPrintSeq = StringUtils.replaceChars(finalPrintSeq, ProjectEagleReportHelper.RESTART_SERVICE_FORM_CCS_FORM_ABBREVIATION, "");
		}
		
		for (GenericForm genericForm : GenericForm.values()) {
			boolean isNeccessary = vasDetailService.existInSelectionGrpList(genericForm.getItemSelectionGroupId(), allSubscribedVasItemIdList);
			if (!isNeccessary) {
				finalPrintSeq = StringUtils.replaceChars(finalPrintSeq, genericForm.getCcsformAbbreviation(), "");
			}
		}
		
		return finalPrintSeq;
	}
}
