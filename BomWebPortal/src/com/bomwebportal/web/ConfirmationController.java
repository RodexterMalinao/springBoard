package com.bomwebportal.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.dto.ui.SupportDocUI;
import com.bomwebportal.exception.DuplicateOrderException;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.IGuardService;
import com.bomwebportal.service.ItemDisplayService;
import com.bomwebportal.service.OrdDocService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.ReportService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.thread.BomOrderRequestPool;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.ConfigProperties;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.util.GenericReportHelper;
import com.bomwebportal.web.util.ProjectEagleReportHelper;
import com.bomwebportal.web.util.ReportHelper;
import com.bomwebportal.web.util.ReportHelper.PdfLang;
import com.bomwebportal.web.util.ReportSessionName;
import com.bomwebportal.wsclient.BulkNewActClient;


public class ConfirmationController implements Controller{

    protected final Log logger = LogFactory.getLog(getClass());
    
    private BulkNewActClient bulkNewActClient;
    private IGuardService iGuardService;
    
	public BulkNewActClient getBulkNewActClient() {
		return bulkNewActClient;
	}
	public IGuardService getiGuardService() {
		return iGuardService;
	}
	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}
	public void setBulkNewActClient(BulkNewActClient bulkNewActClient) {
		this.bulkNewActClient = bulkNewActClient;
	}

    private OrderService orderService;
   
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public OrderService getOrderService() {
		return orderService;
	}

	private BomOrderRequestPool bomOrderRequestPool;
		
    public BomOrderRequestPool getBomOrderRequestPool() {
		return bomOrderRequestPool;
	}
	public void setBomOrderRequestPool(BomOrderRequestPool bomOrderRequestPool) {
		this.bomOrderRequestPool = bomOrderRequestPool;
	}
	
	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}
	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	private ReportHelper reportHelper;
	private GenericReportHelper genericReportHelper;
	private ReportService service;
	private OrderEsignatureService  orderEsignatureService;
	private OrdDocService ordDocService;
	private VasDetailService vasDetailService;
	private MobCcsOrderRemarkService remarkService;
	private ItemDisplayService itemDisplayService;
	
	public ReportHelper getReportHelper() {
		return reportHelper;
	}
	public void setReportHelper(ReportHelper reportHelper) {
		this.reportHelper = reportHelper;
	}
	public GenericReportHelper getGenericReportHelper() {
		return genericReportHelper;
	}
	public void setGenericReportHelper(GenericReportHelper genericReportHelper) {
		this.genericReportHelper = genericReportHelper;
	}
	public ReportService getService() {
		return service;
	}
	public void setService(ReportService service) {
		this.service = service;
	}
	public MobCcsOrderRemarkService getRemarkService() {
		return remarkService;
	}

	public void setRemarkService(MobCcsOrderRemarkService remarkService) {
		this.remarkService = remarkService;
	}
	
	public ItemDisplayService getItemDisplayService() {
		return itemDisplayService;
	}
	public void setItemDisplayService(ItemDisplayService itemDisplayService) {
		this.itemDisplayService = itemDisplayService;
	}
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
    	  
    	logger.info("confirmation handleRequest called ");
    	
    	//get info from session
     	String orderId= (String)request.getSession().getAttribute("orderIdSession");
     	if (orderId == null) {
     		throw new DuplicateOrderException("Signoff more than twice!!!");
     	}
     	
    	SupportDocUI sd = (SupportDocUI)request.getSession().getAttribute("supportDoc");
    	BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	CustomerProfileDTO  customerInfoDto = (CustomerProfileDTO)request.getSession().getAttribute("customer");
    	String locale="";
    	
    	OrderDTO orderDto = (OrderDTO)request.getSession().getAttribute("orderDto");
    	
		if (sd != null && (OrderDTO.DisMode.E == sd.getDisMode())) {
			service.deleteTempDigitalModePdf(orderId);
			service.generateAndSaveDigitalModePdf(request, false);
		}
		
     	//  update STATUS to DB
		MobileSimInfoDTO mobileSimInfo = (MobileSimInfoDTO)request.getSession().getAttribute("MobileSimInfo");
		if (mobileSimInfo != null &&
				StringUtils.isNotEmpty( mobileSimInfo.getIccid())) {

			if (!"".equals(orderId) && orderId != null) {
				if (salesUserDto.getChannelId() == 10) {
					orderService.updateBomWebOrderStatus(orderId,
							BomWebPortalConstant.BWP_ORDER_REVIEWING, "N", "N"); // change status to Review
					remarkService.insertOrderRemark(salesUserDto.getUsername(), orderId, salesUserDto.getUsername() + " signoff order for review.");
				} else if (!"UATSIMSIGNOFF".equals(mobileSimInfo.getIccid())){
					orderService.updateBomWebOrderStatus(orderId,
						BomWebPortalConstant.BWP_ORDER_SIGNOFF); // change status to Signoff
				}
				
			}
		} 
		
     	// create email req		
		OrderMobDTO orderMobDTO=orderService.getOrderMobDTO(orderId);
		if ("Y".equals(orderMobDTO.getCareStatus())){
			String iLang="";
			if (OrderDTO.DisMode.E == sd.getDisMode()){
				if ("CHN".equals(orderDto.getEsigEmailLang())){
					iLang="zh";
				}else{
					iLang="en";
				}
			}
		}
		
		EmailReqResult emailReqResult=null;
     	EmailReqResult emailReqResultIGuardToCust=null;
     	EmailReqResult emailReqResultIGuardToComp=null;
     	EmailReqResult emailReqResultMobileSafetyPhone = null;
     	EmailReqResult emailReqResultNFCSim = null;
     	EmailReqResult emailReqResultOctopusSim = null;
     	EmailReqResult emailReqResultHelperCareToCust = null;
		EmailReqResult emailReqResultTravelInsuranceToCust = null;
		EmailReqResult emailReqResultProjectEagleInsuranceToCust = null;
     	
     	String email = ConfigProperties.getPropertyByEnvironment("iGuardEmailAddr");
     	String[] emailnames = email.split(",");
    	String careCashEmail = ConfigProperties.getPropertyByEnvironment("careCashIGuardEmailAddr");
     	String[] careCashEmailnames = careCashEmail.split(",");
     	if (sd != null && (OrderDTO.DisMode.E == sd.getDisMode())) {
			EsigEmailLang pdfLang = sd.getEsigEmailLang();
			String signedFormsFileName = this.reportHelper.getSignedFormsFileName(orderId, OrderDTO.EsigEmailLang.CHN.equals(pdfLang) ? PdfLang.ZH : PdfLang.EN);
			if (salesUserDto.getChannelId() == 10 || salesUserDto.getChannelId() == 11) {
				emailReqResult =orderEsignatureService.createEmailReq(orderId, "RT008", new Date(), signedFormsFileName, null, null, 
     				sd.getEsigEmailAddr(), salesUserDto.getUsername());
			} else {
				emailReqResult =orderEsignatureService.createEmailReq(orderId, "RT001", new Date(), signedFormsFileName, null, null, 
	     				sd.getEsigEmailAddr(), salesUserDto.getUsername());
			}
     		
			String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
			
			// i-Guard
			List<String> iGuardList = iGuardService.isIGuardOrder(orderDto.getBasketId(), selectedItemList, orderDto.getAppInDate());
			for(int i=0;i<iGuardList.size();i++){
				String iGuardType = iGuardList.get(i);
				if("LDS".equalsIgnoreCase(iGuardType)){
					if (StringUtils.isNotBlank(orderDto.getiGuardSerialNo())) {
		     			String iGuardSignedFormsFileName = this.reportHelper.getIGuardSignedFormsFileName(orderId, orderDto.getMsisdn(), orderDto.getiGuardSerialNo(),iGuardType);
		     			emailReqResultIGuardToCust = orderEsignatureService.createEmailReq(orderId, "RT002", new Date(), iGuardSignedFormsFileName, null, null, sd.getEsigEmailAddr(), salesUserDto.getUsername()); 
		     			for (String emailname : emailnames) {
		     				emailReqResultIGuardToComp =orderEsignatureService.createEmailReq(orderId, "RT003", new Date(), iGuardSignedFormsFileName, null, null, emailname, salesUserDto.getUsername()); 
		     			}
					}
				}else if("AD".equalsIgnoreCase(iGuardType)){
					String iGuardSignedFormsFileName = this.reportHelper.getIGuardSignedFormsFileName(orderId, orderDto.getMsisdn(), orderDto.getiGuardSerialNo(),iGuardType);
	     			emailReqResultIGuardToCust = orderEsignatureService.createEmailReq(orderId, "RT015", new Date(), iGuardSignedFormsFileName, null, null, sd.getEsigEmailAddr(), salesUserDto.getUsername()); 
	     			for (String emailname : emailnames) {
	     				emailReqResultIGuardToComp =orderEsignatureService.createEmailReq(orderId, "RT016", new Date(), iGuardSignedFormsFileName, null, null, emailname, salesUserDto.getUsername()); 
	     			}
				}else if("UAD".equalsIgnoreCase(iGuardType)){
					String iGuardSignedFormsFileName = this.reportHelper.getIGuardSignedFormsFileName(orderId, orderDto.getMsisdn(), orderDto.getiGuardSerialNo(),iGuardType);
	     			emailReqResultIGuardToCust = orderEsignatureService.createEmailReq(orderId, "RT020", new Date(), iGuardSignedFormsFileName, null, null, sd.getEsigEmailAddr(), salesUserDto.getUsername()); 
	     			for (String emailname : emailnames) {
	     				emailReqResultIGuardToComp =orderEsignatureService.createEmailReq(orderId, "RT021", new Date(), iGuardSignedFormsFileName, null, null, emailname, salesUserDto.getUsername()); 
	     			}
				}
				
			}
			
			// HKT Care
			boolean enableHKTCareEmail = Utility.isCodeExist("ENABLE_HKTCARE_EMAIL", "Y");
			Boolean isTravelInsurance = false;
			Boolean isHelperCareInsurance = false;
			if (enableHKTCareEmail) {
				String travelInsuranceItemId = vasDetailService.getItemIdExistInSelectionGrpList(GenericReportHelper.TRAVEL_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID, selectedItemList);
				isTravelInsurance = StringUtils.isNotBlank(travelInsuranceItemId);
				if (isTravelInsurance) {
					String travelInsuranceDays = itemDisplayService.getTravelInsuranceDays(travelInsuranceItemId);
					String travelInsuranceSignedFormsFileName = this.reportHelper.getHktCareSignedFormsFileName(orderId, orderDto.getMsisdn(), "TR001");
					emailReqResultTravelInsuranceToCust = orderEsignatureService.createEmailReq(orderId, "RT023_" + travelInsuranceDays, new Date(), travelInsuranceSignedFormsFileName, null, null, sd.getEsigEmailAddr(), salesUserDto.getUsername()); 
				} 
				isHelperCareInsurance = vasDetailService.existInSelectionGrpList(GenericReportHelper.HELPERCARE_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID, selectedItemList);
				if (isHelperCareInsurance) {
					String helperInsuranceSignedFormsFileName = this.reportHelper.getHktCareSignedFormsFileName(orderId, orderDto.getMsisdn(), "HC001");
					emailReqResultHelperCareToCust = orderEsignatureService.createEmailReq(orderId, "RT024", new Date(), helperInsuranceSignedFormsFileName, null, null, sd.getEsigEmailAddr(), salesUserDto.getUsername()); 
				}
			}
			
			//Project Eagle
			Boolean isProjectEagleInsurance = false;
			isProjectEagleInsurance = vasDetailService.existInSelectionGrpList(ProjectEagleReportHelper.ITEM_SELECTTION_GROUP_ID, selectedItemList);		
			if (isProjectEagleInsurance) {
				String projectEagleSignedFormsFileName = this.reportHelper.getProjectEagleSignedFormsFileName(orderId, orderDto.getMsisdn(), "PE001");
				emailReqResultProjectEagleInsuranceToCust = orderEsignatureService.createEmailReq(orderId, "RT025", new Date(), projectEagleSignedFormsFileName, null, null, sd.getEsigEmailAddr(), salesUserDto.getUsername()); 
			} 
			
     		// mobile safety phone
			String appDateStr=(String) request.getSession().getAttribute("appDate");
			Date appDate = Utility.string2Date(appDateStr);
			BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
			//String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
     		if (this.vasDetailService.isItemsInGroup(basketDTO.getBasketId(), selectedItemList, appDate, "100000103")) {
     			String mobileSafetyPhoneFormsFileName = this.reportHelper.getMobileSafetyPhoneFormsFileName(orderId);
     			emailReqResultMobileSafetyPhone = this.orderEsignatureService.createEmailReq(orderId, "RT005", new Date(), mobileSafetyPhoneFormsFileName, null, null, sd.getEsigEmailAddr(), salesUserDto.getUsername());
     		}
     		
     		// NFC Sim
			if ((!customerInfoDto.isStudentPlanSubInd() && this.vasDetailService.isExtraFunctionSimByNfcInd(orderDto.getNfcInd()))) {
     			String nfcSimFormsFileName = this.reportHelper.getNFCSimFormsFileName(orderId);
     			emailReqResultNFCSim = this.orderEsignatureService.createEmailReq(orderId, "RT006", new Date(), nfcSimFormsFileName, null, null, sd.getEsigEmailAddr(), salesUserDto.getUsername());
     		}
			

			//careCash
			if ("Y".equals(customerInfoDto.getCareStatus())&&"I".equals(customerInfoDto.getCareOptInd())){
				String iGuardCareCashFileName = this.reportHelper.getIGuardCareCashFormsFileName(orderId,locale);
     			emailReqResultIGuardToCust = orderEsignatureService.createEmailReq(orderId, "RT017", new Date(), iGuardCareCashFileName, null, null, sd.getEsigEmailAddr(), salesUserDto.getUsername()); 
     			for (String emailname : careCashEmailnames) {
     			emailReqResultIGuardToComp =orderEsignatureService.createEmailReq(orderId, "RT018", new Date(), iGuardCareCashFileName, null, null, emailname, salesUserDto.getUsername()); 
     			}
			}
			

			// Octopus Sim
			/*if (this.vasDetailService.hasOctopusSim(orderId)) {
     			String octopusSimFormsFileName = this.reportHelper.getOctopusSimFormsFileName(orderId);
     			emailReqResultOctopusSim = this.orderEsignatureService.createEmailReq(orderId, "RT007", new Date(), octopusSimFormsFileName, null, null, sd.getEsigEmailAddr(), salesUserDto.getUsername());
     		}*/
     	}
     	//update STATUS to DB
		/******************* Remove order information in session ***************************/
		/*
     	if(!"".equals(orderId) && orderId !=null ){
     		//logger.info("change Order status");
     		orderService.updateBomWebOrderStatus(orderId, "OC") ; //change status to OC
     		
         	//Delete Session
         	request.getSession().removeAttribute("customer");
         	request.getSession().removeAttribute("payment");
         	request.getSession().removeAttribute("MNP");
         	request.getSession().removeAttribute("MobileSimInfo");
         	request.getSession().removeAttribute("orderIdSession");
        	request.getSession().removeAttribute("customerTierSession");
        	request.getSession().removeAttribute("baskettypeSession");
        	request.getSession().removeAttribute("rptypeSession");        	
        	request.getSession().removeAttribute("selectedVasItemList");
    	}
    	*/
     	
     	request.getSession().removeAttribute("customer");
     	request.getSession().removeAttribute("payment");
     	request.getSession().removeAttribute("MNP");
     	request.getSession().removeAttribute("MobileSimInfo");
     	request.getSession().removeAttribute("orderIdSession");
    	request.getSession().removeAttribute("customerTierSession");
    	request.getSession().removeAttribute("baskettypeSession");
    	request.getSession().removeAttribute("rptypeSession");        	
    	request.getSession().removeAttribute("selectedVasItemList");
    	//add eliot 20110824
    	request.getSession().removeAttribute("signoffDtoSession");
    	request.getSession().removeAttribute("sbuid");
    	
    	request.getSession().removeAttribute("customerSignSession");
		request.getSession().removeAttribute("bankSignSession");
		request.getSession().removeAttribute("mnpSignSession");
		request.getSession().removeAttribute("conciergeSignSession");

		request.getSession().removeAttribute("iGuardSignSession");
		request.getSession().removeAttribute(ReportSessionName.SIGNOFF.getTravelInsuranceSignDtoName());
		request.getSession().removeAttribute(ReportSessionName.SIGNOFF.getHelperCareInsuranceSignDtoName());
		request.getSession().removeAttribute(ReportSessionName.SIGNOFF.getProjectEagleInsuranceSignDtoName());
		request.getSession().removeAttribute("tdoSignSession"); //20130709
    	
     	/******************* for testing create BOM order(END) ***************************/     	
     	//END of delete 
    	ModelAndView myView =new ModelAndView("confirmation");
    	myView.addObject( "orderId", orderId); 
    	/*myView.addObject( "selectedModelId", selectedModelId); 
    	myView.addObject( "mobileDetail", mobileDetail); // return mobileDetail for image
    	//myView.addObject( "selectedColorName", selectedColorName); //return selectedImageName //remove 20120730
    	myView.addObject( "vasHSRPList", vasHSRPList); 
    	myView.addObject( "customerInfoDto", customerInfoDto); // return customer profile DTO 
    	myView.addObject( "mnpDto", mnpDto); 
    	myView.addObject( "paymentDto", paymentDto);
    	myView.addObject( "applicationDate", applicationDate);*/
    	
    	//end
    	
    	myView.addObject( "emailReqResult", emailReqResult);
    	myView.addObject( "emailReqResultIGuardToCust", emailReqResultIGuardToCust);
    	myView.addObject( "emailReqResultIGuardToComp", emailReqResultIGuardToComp);
    	myView.addObject( "emailReqResultMobileSafetyPhone", emailReqResultMobileSafetyPhone);
    	myView.addObject( "emailReqResultNFCSim", emailReqResultNFCSim);
    	myView.addObject( "emailReqResultOctopusSim", emailReqResultOctopusSim);
    	
    	myView.addObject("emailReqResultHelperCareToCust", emailReqResultHelperCareToCust);
    	myView.addObject("emailReqResultTravelInsuranceToCust", emailReqResultTravelInsuranceToCust);
    	myView.addObject("emailReqResultProjectEagleInsuranceToCust", emailReqResultProjectEagleInsuranceToCust);
    	boolean enableHKTCareEmail = Utility.isCodeExist("ENABLE_HKTCARE_EMAIL", "Y");
    	myView.addObject("enableHKTCareEmail", enableHKTCareEmail);
    	
    	logger.info("ConfirmationController.handleRequest() End");
    	
    	if ("UATSIMSIGNOFF".equals(mobileSimInfo.getIccid())) {
    		myView.addObject( "UATSIMSIGNOFF", "UATSIMSIGNOFF will not change order status, only generate PDF and make a e-mail request");
    	}
    
    	return myView;
    
    }
	public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}
	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}
	public OrdDocService getOrdDocService() {
		return ordDocService;
	}
	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}
 
}