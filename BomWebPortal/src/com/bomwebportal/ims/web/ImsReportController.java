package com.bomwebportal.ims.web;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.dto.report.RptHSTradeDescDTO;
import com.bomwebportal.exception.ImsAlreadySignOffException;
import com.bomwebportal.ims.dto.ImsSignoffDTO;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ImsRptCoreServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptNtvServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptOptServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptBasketDtlDTO;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;
import com.bomwebportal.ims.dto.ImsRptChannelDTO;
import com.bomwebportal.ims.dto.ImsRptServicePlanDetailDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedChannelUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;

import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.ims.service.ImsReportService;
import com.bomwebportal.ims.service.ImsReport2Service;
import com.google.gson.Gson;

public class ImsReportController implements Controller {
	protected final Log logger = LogFactory.getLog(getClass());

    private Gson gson = new Gson();
	private ImsReportService service;

	private ImsOrderService orderservice;

	public ImsReportService getService() {
		return service;
	}

	public void setService(ImsReportService service) {
		this.service = service;
	}

	private ImsReport2Service service2;

	public ImsReport2Service getService2() {
		return service2;
	}

	public void setService2(ImsReport2Service service2) {
		this.service2 = service2;
	}

	public ImsReportController() {
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		Boolean isPT= user.getChannelId()==3;
		Boolean isCC = false;
		if(user.getChannelId()==2||user.getChannelId()==3||user.getChannelId()==99){
			isCC=true;
		}
		//Boolean isDS= user.getChannelCd().equalsIgnoreCase("DS");
		Boolean isDS = ((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)?true:false);
		
		
		//isDS = false;

		logger.info("ImsReportController onSubmit is called");
		try {			
			
			Boolean cOrder = false;//Gary
			String pdfName = "";
			String OrderIdForViewPDF;
			if(request.getParameter("OrderIdForViewPDF")!=null){
				OrderIdForViewPDF = request.getParameter("OrderIdForViewPDF");
				pdfName = OrderIdForViewPDF + ".pdf";
				OutputStream screenOutputStream = response.getOutputStream();
				response.setContentType("application/pdf");
				response.addHeader("Content-disposition","attachment; filename=" + pdfName);
				response.setHeader("Cache-Control", "private");
				logger.info("OrderIdForViewPDF:"+OrderIdForViewPDF);
				service.viewPdfReports(screenOutputStream, OrderIdForViewPDF);
				screenOutputStream.flush();
				screenOutputStream.close();
				return new ModelAndView();
			}
			
			String orderId = "";
			OrderImsUI orderIms = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);

			if(request.getParameter("TestOrderID")!=null){
				orderIms = orderservice.getBomWebImsOrder(request.getParameter("TestOrderID")); 
				logger.debug("orderIms.getOrderId():" + orderIms.getOrderId());
				if("P".equals(orderIms.getOrderId().substring(0, 1))){
					isPT=true;
					logger.debug("is pt:orderIms.getOrderId():" + orderIms.getOrderId());
				}
				logger.debug("TestOrderID orderIms:"+gson.toJson(orderIms));
			}
			if(orderIms!=null){
				orderId = orderIms.getOrderId();
			}
			
			
			String pdfLang = request.getParameter("pdfLang");
			pdfLang = (pdfLang==null)?"en":pdfLang;
			logger.debug("orderId:"+orderId+" pdfLang:" + pdfLang);


			String forTestingTheClub = request.getParameter("test");
			String disMode = "null";
			if(request.getParameter("disMode")!=null){
				 disMode = request.getParameter("disMode");
				 request.getSession().setAttribute("disMode", disMode);
			}else if (request.getSession().getAttribute("disMode")!=null){
				disMode = (String) request.getSession().getAttribute("disMode");
			}
			logger.info("orderId:"+orderId+" disMode:"+disMode);
			System.out.println("orderId:"+orderId+" disMode:"+disMode);
			
			Boolean Signed = false;
			if(request.getParameter("SignedOrNot")!=null){
				logger.info("orderId:"+orderId+" SignedOrNot:"+request.getParameter("SignedOrNot"));
				System.out.println("orderId:"+orderId+" SignedOrNot:"+request.getParameter("SignedOrNot"));
				if(request.getParameter("SignedOrNot").equals("true")){
					Signed = true;
					logger.info("orderId:"+orderId+" Preview form with Sign is clicked");
					System.out.println("orderId:"+orderId+" Preview form with Sign is clicked");
				}else{
					logger.info("orderId:"+orderId+" Preview form without Sign is clicked");
					System.out.println("orderId:"+orderId+" Preview form without Sign is clicked");
				}
			}
			
			//Gary add for draft watermark
			Boolean isPreview = false;
			if(request.getParameter("justPreview")!=null){
				if(request.getParameter("justPreview").equals("true")){
					isPreview = true;
				}
			}


			pdfName = orderIms.getOrderId();
			if ("en".equals(pdfLang)){
				pdfName = pdfName + "_EN.pdf";
			}else{
				pdfName = pdfName + "_CHI.pdf";
			}

			ArrayList<Object> arrayList_en = new ArrayList<Object>();
			ArrayList<Object> arrayList_zh = new ArrayList<Object>();

				
				
			/*****************************/
//			if (true||orderIms.isPending().equalsIgnoreCase("Y")) {
			if (orderIms.isPending().equalsIgnoreCase("Y")||"Y".equals(forTestingTheClub)) {
				Date d = new Date();
				System.out.println("IMS AF Print date:"+d+"  orderIms:"+gson.toJson(orderIms));
				
				arrayList_en.add(orderIms);
				arrayList_zh.add(orderIms);
						
				ImsRptServicePlanDetailDTO servPlanDto = new ImsRptServicePlanDetailDTO();
				servPlanDto = service2.getServPlanDesc(orderIms, "en", isPT,isDS);			
				arrayList_en.add(servPlanDto);
				
				ImsRptServicePlanDetailDTO servPlanDto2 = new ImsRptServicePlanDetailDTO();
				servPlanDto2 = service2.getServPlanDesc(orderIms, "zh_HK", isPT,isDS);			
				arrayList_zh.add(servPlanDto2);
				
				//Gary
				String cOrderString = "null";
				if (request.getParameter("cOrder") != null) {
					cOrderString = request.getParameter("cOrder");
					if (cOrderString.equals("True")) {
						cOrder = true;
					}
				}
				
				logger.debug("cOrderString:" + cOrderString);
				if (cOrder) {
					logger.debug("This is C order, generate C order pdf");
				} else {
					logger.debug("not C order");
				}
				//gary end
				
				
				ImsSignoffDTO pcdTDOSign  = (ImsSignoffDTO)request.getSession().getAttribute(ImsConstants.IMS_TDO_PCD_SIGN);
				if(pcdTDOSign!=null && ImsSignoffDTO.SignatureTypeEnum.PCD_TDO_SIGN == pcdTDOSign.getSignatureType()){
					arrayList_zh.add(pcdTDOSign);
					arrayList_en.add(pcdTDOSign);
				}		
				
				ImsSignoffDTO tvTDOSign  = (ImsSignoffDTO)request.getSession().getAttribute(ImsConstants.IMS_TDO_TV_SIGN);
				if(tvTDOSign!=null && ImsSignoffDTO.SignatureTypeEnum.TV_TDO_SIGN == tvTDOSign.getSignatureType()){
					arrayList_zh.add(tvTDOSign);
					arrayList_en.add(tvTDOSign);
				}		
				
				ImsSignoffDTO moovTDOSign  = (ImsSignoffDTO)request.getSession().getAttribute(ImsConstants.IMS_TDO_MOOV_SIGN);
				if(moovTDOSign!=null&&ImsSignoffDTO.SignatureTypeEnum.MOOV_TDO_SIGN == moovTDOSign.getSignatureType()){
					arrayList_zh.add(moovTDOSign);
					arrayList_en.add(moovTDOSign);
				}		
				
				
				ImsSignoffDTO customerSign  = (ImsSignoffDTO)request.getSession().getAttribute(ImsConstants.IMS_CUST_SIGN);
				if(customerSign!=null&&ImsSignoffDTO.SignatureTypeEnum.CUSTOMER_SIGN == customerSign.getSignatureType()){
					arrayList_zh.add(customerSign);
					arrayList_en.add(customerSign);
				}		
				
				ImsSignoffDTO creditCardSignDto  = (ImsSignoffDTO)request.getSession().getAttribute(ImsConstants.IMS_CREDIT_CARD_SIGN);
				if(creditCardSignDto!=null&&ImsSignoffDTO.SignatureTypeEnum.CREDIT_CARD_SIGN == creditCardSignDto.getSignatureType()){
					arrayList_zh.add(creditCardSignDto);
					arrayList_en.add(creditCardSignDto);
				}			
				
				ImsSignoffDTO thirdPartySign  = (ImsSignoffDTO)request.getSession().getAttribute(ImsConstants.IMS_3_PARTY_SIGN);
				if(thirdPartySign!=null&&ImsSignoffDTO.SignatureTypeEnum.ThirdParty_SIGN == thirdPartySign.getSignatureType()){
					arrayList_zh.add(thirdPartySign);
					arrayList_en.add(thirdPartySign);
				}		
				
				ImsSignoffDTO careCashSign  = (ImsSignoffDTO)request.getSession().getAttribute(ImsConstants.IMS_CARE_CASH_SIGN);
				if(careCashSign!=null&&ImsSignoffDTO.SignatureTypeEnum.CareCash_SIGN == careCashSign.getSignatureType()){
					arrayList_zh.add(careCashSign);
					arrayList_en.add(careCashSign);
				}		
				
				if(customerSign==null && creditCardSignDto==null && thirdPartySign==null && 
						pcdTDOSign==null && tvTDOSign ==null && moovTDOSign==null && careCashSign==null){
					Signed = false; // all signs are empty, did not sign.
					logger.info("orderId:"+orderId+" all signs are null");
				}
				
				
				logger.info("forTestingTheClub:"+forTestingTheClub);				
				if(!"Y".equals(forTestingTheClub)){
					if(service.isDBSignOffEd(orderId)){
						throw new ImsAlreadySignOffException();
					}
				}
				response.setContentType("application/pdf");
				response.addHeader("Content-disposition","attachment; filename=" + pdfName);
				response.setHeader("Cache-Control", "private");
				OutputStream screenOutputStream = response.getOutputStream();
				if ("en".equalsIgnoreCase(pdfLang)) {
					service.generatePdfReports(arrayList_en, screenOutputStream, "en", orderId, Signed, disMode, isCC, isPT,isDS, cOrder, isPreview);
				} else {
					service.generatePdfReports(arrayList_zh, screenOutputStream, "zh", orderId, Signed, disMode, isCC, isPT,isDS, cOrder, isPreview);
				}				
				service.generatePdfReports(arrayList_en, null, "en", orderId, null, disMode, isCC, isPT,isDS, true, false);// save C order PDF without Showing
				screenOutputStream.flush();
				screenOutputStream.close();
//				for(int i=1;i<15;i++){
//					if(i==1){
//						orderIms.setHasBBDailup("N");
//						orderIms.getCustomer().setTheClubInd("Y"); 
//						orderIms.getCustomer().setCsPortalInd("Y");		
//						
//					}else if(i==2){
//						orderIms.setHasBBDailup("N");
//						orderIms.getCustomer().setTheClubInd("Y"); 
//						orderIms.getCustomer().setCsPortalInd("A");		
//						
//					}else if(i==3){
//						orderIms.setHasBBDailup("N");
//						orderIms.getCustomer().setTheClubInd("A"); 
//						orderIms.getCustomer().setCsPortalInd("Y");		
//						
//					}else if(i==4){
//						orderIms.setHasBBDailup("N");
//						orderIms.getCustomer().setTheClubInd("A"); 
//						orderIms.getCustomer().setCsPortalInd("N");		
//						
//					}else if(i==5){
//						orderIms.setHasBBDailup("N");
//						orderIms.getCustomer().setTheClubInd("A"); 
//						orderIms.getCustomer().setCsPortalInd("A");		
//						
//					}else if(i==6){
//						orderIms.setHasBBDailup("N");
//						orderIms.getCustomer().setTheClubInd("X"); 
//						orderIms.getCustomer().setCsPortalInd("X");		
//						
//					}else if(i==7){
//						orderIms.setHasBBDailup("N");
//						orderIms.getCustomer().setTheClubInd("Y"); 
//						orderIms.getCustomer().setCsPortalInd("N");		
//						
//					}else if(i==8){
//						orderIms.setHasBBDailup("Y");
//						orderIms.getCustomer().setTheClubInd("Y"); 
//						orderIms.getCustomer().setCsPortalInd("Y");		
//						
//					}else if(i==9){
//						orderIms.setHasBBDailup("Y");
//						orderIms.getCustomer().setTheClubInd("Y"); 
//						orderIms.getCustomer().setCsPortalInd("A");		
//						
//					}else if(i==10){
//						orderIms.setHasBBDailup("Y");
//						orderIms.getCustomer().setTheClubInd("A"); 
//						orderIms.getCustomer().setCsPortalInd("Y");		
//						
//					}else if(i==11){
//						orderIms.setHasBBDailup("Y");
//						orderIms.getCustomer().setTheClubInd("A"); 
//						orderIms.getCustomer().setCsPortalInd("N");		
//						
//					}else if(i==12){
//						orderIms.setHasBBDailup("Y");
//						orderIms.getCustomer().setTheClubInd("A"); 
//						orderIms.getCustomer().setCsPortalInd("A");		
//						
//					}else if(i==13){
//						orderIms.setHasBBDailup("Y");
//						orderIms.getCustomer().setTheClubInd("X"); 
//						orderIms.getCustomer().setCsPortalInd("X");		
//						
//					}else if(i==14){
//						orderIms.setHasBBDailup("Y");
//						orderIms.getCustomer().setTheClubInd("Y"); 
//						orderIms.getCustomer().setCsPortalInd("N");		
//						
//					}
//						
//					service.generatePdfReports(arrayList_en, null, "en", orderId, null, disMode, isCC, isPT,isDS,false, false);      
//				}
			} else {
				response.setContentType("application/pdf");
				response.addHeader("Content-disposition","attachment; filename=" + pdfName);
				response.setHeader("Cache-Control", "private");
				OutputStream screenOutputStream = response.getOutputStream();
				logger.info("orderId:"+orderId+" viewPdfReports, signoff-ed");
				service.viewPdfReports(screenOutputStream, orderId);
				screenOutputStream.flush();
				screenOutputStream.close();
			}



			
			logger.info("orderId:"+orderId+" ImsReportController onSubmit <end>");
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			logger.error("Exception in ReportController", ioEx);
			response.resetBuffer();
			response.setContentType("text/html");
			response.setHeader("Content-Disposition", null);
			response.getWriter().println("Exception in ReportController " + ioEx.toString());
		}

		return new ModelAndView();
	}
	
	

	public void setOrderservice(ImsOrderService orderservice) {
		this.orderservice = orderservice;
	}

	public ImsOrderService getOrderservice() {
		return orderservice;
	}
	
}
