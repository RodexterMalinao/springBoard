package com.bomwebportal.ims.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustTagDTO;
import org.openuri.www.CustomerBasicInfoDTO;
import org.openuri.www.CustomerSearchResponse;
import org.openuri.www.SearchingKeyDTO;
import org.openuri.www.ServiceLineDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllDocDTO.Type;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO.CollectedInd;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.exception.ImsAfException;
import com.bomwebportal.exception.ImsEmptySignatureException;
import com.bomwebportal.exception.ImsMobilePinException;
import com.bomwebportal.exception.ImsAlreadySignOffException;
import com.bomwebportal.exception.ImsReportGerationException;
import com.bomwebportal.exception.WebServiceException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dao.ImsEmailParamHelperDAO;
import com.bomwebportal.ims.dto.Ims1AMSFSAInfoDTO;
import com.bomwebportal.ims.dto.ImsAllOrdDocWaiveDTO;
import com.bomwebportal.ims.dto.ImsRptBasketDtlDTO;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;
import com.bomwebportal.ims.dto.ImsRptChannelDTO;
import com.bomwebportal.ims.dto.ImsRptCoreServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptGiftDTO;
import com.bomwebportal.ims.dto.ImsRptNtvServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptOptServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptServicePlanDetailDTO;
import com.bomwebportal.ims.dto.ImsSignoffDTO;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.ims.dto.report.RptImsAppServDTO;
import com.bomwebportal.ims.dto.ui.ImsSummaryUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedChannelUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.service.BasketSummaryService;
import com.bomwebportal.ims.service.Ims1AMSEnquiryService;
import com.bomwebportal.ims.service.ImsAddressInfoService;
import com.bomwebportal.ims.service.ImsBasketDetailsService;
import com.bomwebportal.ims.service.ImsNowTVService;
import com.bomwebportal.ims.service.ImsOrderDetailService;
import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.ims.service.ImsReport2Service;
import com.bomwebportal.ims.service.ImsReportService;
import com.bomwebportal.ims.service.ImsSMSService;
import com.bomwebportal.ims.service.ReleaseLoginIDService;
import com.bomwebportal.ims.service.ValidateAccountNumService;
import com.bomwebportal.service.MobilePINService;
import com.bomwebportal.service.OrdDocService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.service.MobilePINServiceImpl.MobileOffer;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.service.SupportDocAdminService;
import com.bomwebportal.service.SupportDocService;
import com.bomwebportal.util.ConfigProperties;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.CustomerInformationController.CustomerPremierInfoDTO;
import com.bomwebportal.web.ext.BomWebPortalApplicationFlow;
import com.bomwebportal.wsclient.CustProfileClient;
import com.bomwebportal.wsclient.CustomerSearchClient;
import com.google.gson.Gson;
import com.bomwebportal.ims.dto.ui.NowTVAddUI;
import com.bomwebportal.ims.dto.ImsCollectDocDTO;

import org.openuri.www.CustomerSearchResponse;

public class ImsSummaryController extends SimpleFormController{

    protected final Log logger = LogFactory.getLog(getClass());
    private Gson gson = new Gson();

    private ReleaseLoginIDService releaseLoginIDService;
    private ImsOrderDetailService imsOrderDetailService;
    private ImsReport2Service imsReport2Service;
    private BasketSummaryService basketSummaryService;
    private ValidateAccountNumService validateAccountNumService;
    private ImsBasketDetailsService basketDetailService;
	
	private SupportDocService supportDocService;
	private SupportDocAdminService supportDocAdminService;
    private OrderEsignatureService  orderEsignatureService;
    private ImsSMSService imsSMSService;
    private ImsNowTVService imsNowTVService;
    private MobilePINService mobilePINService;
    private ImsEmailParamHelperDAO imsEmailParamHelperDAO;

    public MobilePINService getMobilePINService() {
		return mobilePINService;
	}

	public void setMobilePINService(MobilePINService mobilePINService) {
		this.mobilePINService = mobilePINService;
	}

    private String acqUrl;

	public String getAcqUrl() {
		return acqUrl;
	}

	public void setAcqUrl(String acqUrl) {
		logger.info("acqUrl:"+acqUrl);
		this.acqUrl = acqUrl;
	}

	private CustomerSearchClient custSearchClient;	
    
    public CustomerSearchClient getCustSearchClient() {
		return custSearchClient;
	}

	public void setCustSearchClient(CustomerSearchClient custSearchClient) {
		System.out.println("@@@@");
		System.out.println(custSearchClient);
		this.custSearchClient = custSearchClient;
	}
    
    private CustProfileClient custProfileClient;
	
    public CustProfileClient getCustProfileClient() {
		return custProfileClient;
	}
	public void setCustProfileClient(CustProfileClient custProfileClient) {
		this.custProfileClient = custProfileClient;
	}
    
    
    private ImsAddressInfoService imsAddressInfoService;
    public void setImsAddressInfoService(ImsAddressInfoService imsAddressInfoService) {
		this.imsAddressInfoService = imsAddressInfoService;
	}
	public ImsAddressInfoService getImsAddressInfoService() {
		return imsAddressInfoService;
	}
    
	private Ims1AMSEnquiryService ims1AMSEnquiryService;
	public void setIms1AMSEnquiryService(Ims1AMSEnquiryService ims1AMSEnquiryService) {
		this.ims1AMSEnquiryService = ims1AMSEnquiryService;
	}
	public Ims1AMSEnquiryService getIms1AMSEnquiryService() {
		return ims1AMSEnquiryService;
	}
    
    //ADD BY CELIA
	private ImsOrderService imsOrderService;
	public void setImsOrderService(ImsOrderService imsOrderService) {
		this.imsOrderService = imsOrderService;
	}

	public ImsOrderService getImsOrderService() {
		return imsOrderService;
	}
    public ImsNowTVService getImsNowTVService() {
		return imsNowTVService;
	}

	public void setImsNowTVService(ImsNowTVService imsNowTVService) {
		this.imsNowTVService = imsNowTVService;
	}

	// add by steven
	private OrdDocService ordDocService;

	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}

	public OrdDocService getOrdDocService() {
		return ordDocService;
	}
	private ImsReportService service;
	
	public ImsReportService getService() {
		return service;
	}

	public void setService(ImsReportService service) {
		this.service = service;
	}// add by steven end
  
	public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}

	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}

	public ValidateAccountNumService getValidateAccountNumService() {
		return validateAccountNumService;
	}

	public void setValidateAccountNumService(
			ValidateAccountNumService validateAccountNumService) {
		this.validateAccountNumService = validateAccountNumService;
	}
    
    public ReleaseLoginIDService getReleaseLoginIDService() {
		return releaseLoginIDService;
	}

	public void setReleaseLoginIDService(ReleaseLoginIDService releaseLoginIDService) {
		this.releaseLoginIDService = releaseLoginIDService;
	}

	public BasketSummaryService getBasketSummaryService() {
		return basketSummaryService;
	}

	public void setBasketSummaryService(BasketSummaryService basketSummaryService) {
		this.basketSummaryService = basketSummaryService;
	}

	public ImsReport2Service getImsReport2Service() {
		return imsReport2Service;
	}

	public void setImsReport2Service(ImsReport2Service imsReport2Service) {
		this.imsReport2Service = imsReport2Service;
	}

	public ImsOrderDetailService getImsOrderDetailService() {
		return imsOrderDetailService;
	}

	public void setImsOrderDetailService(ImsOrderDetailService imsOrderDetailService) {
		this.imsOrderDetailService = imsOrderDetailService;
	}

	private BomWebPortalApplicationFlow appFlow;
    
	public BomWebPortalApplicationFlow getAppFlow() {
		return appFlow;
	}

	public void setAppFlow(
			BomWebPortalApplicationFlow appFlow) {
		this.appFlow = appFlow;
	}
	

	public void setImsSMSService(ImsSMSService imsSMSService) {
		this.imsSMSService = imsSMSService;
	}

	public ImsSMSService getImsSMSService() {
		return imsSMSService;
	}
	
	public ImsBasketDetailsService getBasketDetailService() {
		return basketDetailService;
	}
	
	public void setBasketDetailService(ImsBasketDetailsService basketDetailService) {
		this.basketDetailService = basketDetailService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		logger.info("formBackingObject");
		String Locale = RequestContextUtils.getLocale(request).toString().contains("en")?"EN":"CHI";
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		//save order initial order image		
		if(sessionOrder.getOrderStatus()==null){
			imsOrderDetailService.suspendOrder(sessionOrder, "S000"); //await sign off					
			request.getSession().setAttribute("imsLoginIdUi", null);
			request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, null);
			request.getSession().setAttribute(ImsConstants.IMS_ORDER, sessionOrder);
			//request.getSession().setAttribute("imsMobileOfferUi", null);
		}		
		
		
		System.out.println(gson.toJson(sessionOrder.getCustomer()));
		ImsSummaryUI sessionSummary = new ImsSummaryUI();
		//ImsSummaryUI basketSummary = new ImsSummaryUI();
		BeanUtils.copyProperties(sessionOrder.getCustomer(), sessionSummary);
		
		sessionSummary.setInstallAddress(sessionOrder.getInstallAddress());
		if(sessionOrder.getBillingAddress() != null){
			sessionSummary.setBillingAddress(sessionOrder.getBillingAddress());
		}
		sessionSummary.setAppointment(sessionOrder.getAppointment());
		
		sessionSummary.setNoBooking(sessionOrder.isBookingNotAllowed());
		if(sessionSummary.getNoBooking().equals("Y")){
			sessionSummary.setAppntStartDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntStartDateDisplay(), "dd/MM/yyyy"));
		}else{
			sessionSummary.setAppntStartDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntStartDateDisplay(), "dd/MM/yyyy HH:mm"));
			sessionSummary.setAppntEndDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntEndDateDisplay(), "HH:mm"));
		}		
		
		//eSignature CR by Eric Ng
		if("Y".equals(sessionOrder.getIsCC())&&!"R".equals(sessionOrder.getMode())){
			sessionSummary.setDisMode(DisMode.I);
		}else{
			sessionSummary.setDisMode(sessionOrder.getDisMode());
		}
		sessionSummary.setCollectMethod((sessionOrder.getCollectMethod() == null) ? CollectMethod.D : sessionOrder.getCollectMethod());
		if(StringUtils.isNotBlank(sessionOrder.getEsigEmailAddr())){		
			sessionSummary.setEsigEmailAddr(sessionOrder.getEsigEmailAddr());
			sessionSummary.setEmailAddr(sessionOrder.getEsigEmailAddr());
		}
		else if(StringUtils.isNotBlank(sessionOrder.getCustomer().getContact().getEmailAddr())){
			sessionSummary.setEsigEmailAddr(sessionOrder.getCustomer().getContact().getEmailAddr());
			sessionSummary.setEmailAddr(sessionOrder.getCustomer().getContact().getEmailAddr());
		}
//		sessionSummary.setEsigEmailAddr(sessionOrder.getEsigEmailAddr());
		sessionSummary.setEsigEmailLang((sessionOrder.getEsigEmailLang() == null) ? EsigEmailLang.ENG : sessionOrder.getEsigEmailLang());
		sessionSummary.setSMSno(sessionOrder.getCustomer().getContact().getContactPhone());
//		sessionSummary.setSMSno(sessionOrder.getSMSno()); //jacky 20141203 
		//sessionSummary.setEsigEmailLang(sessionOrder.getEsigEmailLang());
		
		// kinman 31102012
    	//System.out.println("DisMode0:"+sessionSummary.getDisMode());
    	//System.out.println("PrintFormClickInd:"+sessionSummary.getPrintFormClickInd());
		//sessionSummary.setPrintPreviewFormSignedInd("");
		//sessionSummary.setClickDigitalSignButtonInd("");
		
		sessionSummary.setPrintPreviewFormSignedInd("");
		sessionSummary.setClickDigitalSignButtonInd("");
		
		// kinman 05-11-2012
		sessionSummary.setClickCaptureIpadButtonInd("");
		System.out.println("PrintPreviewFormSignedInd:"+sessionSummary.getPrintPreviewFormSignedInd());
		System.out.println("ClickDigitalSignButtonInd:"+sessionSummary.getClickDigitalSignButtonInd());	
		
		
		sessionSummary.getAccount().setPayment(sessionOrder.getCustomer().getAccount().getPayment());
		sessionSummary.setFixedLineNo(sessionOrder.getFixedLineNo());
		sessionSummary.setLoginId(sessionOrder.getLoginId());
		sessionSummary.setAppDate(Utility.date2String(sessionOrder.getAppDate(), "dd/MM/yyyy"));
		sessionSummary.setTargetCommDate(Utility.date2String(sessionOrder.getTargetCommDate(), "dd/MM/yyyy"));
		sessionSummary.setCashFsPrepay(sessionOrder.getCashFsPrepay());
		sessionSummary.setPrepayAmt(sessionOrder.getPrepayAmt());
		//Gary
		sessionSummary.setHktLoginId(sessionOrder.getCustomer().getCsPortalLogin());
		sessionSummary.setHktMobileNum(sessionOrder.getCustomer().getCsPortalMobile());
		
		//nowid
		sessionSummary.setIsRegNowId(sessionOrder.getCustomer().getIsRegNowId());
		sessionSummary.setNowId(sessionOrder.getCustomer().getNowId());
		
		sessionSummary.setMode(sessionOrder.getMode());
		
		if(sessionOrder.getInstallmentChrg()!=null) 
		{
			if(Integer.parseInt(sessionOrder.getInstallmentChrg())>0){
			sessionSummary.setOtInstallChrg(sessionOrder.getInstallmentChrg()+" X "+sessionOrder.getInstallmentMonth()+" months");
			}else{
				sessionSummary.setOtInstallChrg(sessionOrder.getOtInstallChrg());         //TT
				sessionSummary.setInstallOtCode(sessionOrder.getInstallOtCode()); //Gary
				sessionSummary.setInstallOTDesc_en(sessionOrder.getInstallOTDesc_en()); //Gary
				sessionSummary.setInstallOTDesc_zh(sessionOrder.getInstallOTDesc_zh()); //Gary
			
			}
		}else{
			sessionSummary.setOtInstallChrg(sessionOrder.getOtInstallChrg());         //TT
			sessionSummary.setInstallOtCode(sessionOrder.getInstallOtCode()); //Gary
			sessionSummary.setInstallOTDesc_en(sessionOrder.getInstallOTDesc_en()); //Gary
			sessionSummary.setInstallOTDesc_zh(sessionOrder.getInstallOTDesc_zh()); //Gary
		}
		

		String locale = RequestContextUtils.getLocale(request).toString();
		
		String compForOtInstallChrg = "";
		if("en".equalsIgnoreCase(locale)){
			compForOtInstallChrg = sessionSummary.getInstallOTDesc_en();
		}else{
			compForOtInstallChrg = sessionSummary.getInstallOTDesc_zh();
		}
		if(compForOtInstallChrg==null||compForOtInstallChrg.isEmpty()){
			compForOtInstallChrg = "";
		}
		String OtInstallChrg = sessionSummary.getOtInstallChrg();
		if(OtInstallChrg==null||OtInstallChrg.isEmpty()){
			//mass project WAIVED case
			sessionSummary.setWaivedOtInstallChrg("Waived");
		}else if("0".equalsIgnoreCase(OtInstallChrg)){
			sessionSummary.setWaivedOtInstallChrg("Waived");
		}else{
			if(OtInstallChrg.startsWith("-")){
				sessionSummary.setWaivedOtInstallChrg("$"+OtInstallChrg.substring(1)+compForOtInstallChrg+"(Waived)");
				if(sessionOrder.getServiceWaiver()!=null){
					if("B".equalsIgnoreCase(sessionOrder.getServiceWaiver())){
						sessionSummary.setWaivedOtInstallChrg("$"+OtInstallChrg.substring(1)+compForOtInstallChrg+"(Waived)");
					}
					if("V".equalsIgnoreCase(sessionOrder.getServiceWaiver())){
						sessionSummary.setWaivedOtInstallChrg("$"+OtInstallChrg.substring(1)+compForOtInstallChrg+"(Waived)");
					}
					if("G".equalsIgnoreCase(sessionOrder.getServiceWaiver())){
						sessionSummary.setWaivedOtInstallChrg("$"+OtInstallChrg.substring(1)+compForOtInstallChrg+"(Waived)");
					}
				}
				if(sessionOrder.getServiceWaiverNowTVPage()!=null){
					if("G".equalsIgnoreCase(sessionOrder.getServiceWaiver())){
						sessionSummary.setWaivedOtInstallChrg("$"+OtInstallChrg.substring(1)+compForOtInstallChrg+"(Waived)");
					}
				}
			}else{
				sessionSummary.setWaivedOtInstallChrg("$"+OtInstallChrg+compForOtInstallChrg);
			}
		}
		
		
		sessionSummary.setDobStr(Utility.date2String(sessionSummary.getDob(), "dd/MM/yyyy"));
		sessionSummary.setLangPreference(sessionOrder.getLangPreference());
		sessionSummary.setPrintFormClickInd("");
		
		sessionSummary.setPaperModeConfirmInd("");
		sessionSummary.setWithoutEmailComfirmInd("");
		sessionSummary.setSelectedDisModeInd("");
		//System.out.println("PrintFormClickInd:"+sessionSummary.getPrintFormClickInd());
		//sessionSummary.setPrintPreviewFormSignedInd("");
		//sessionSummary.setClickDigitalSignButtonInd("");
		sessionSummary.setPrimaryAcctNo(sessionOrder.getPrimaryAcctNo());
		sessionSummary.setNowTvFormType(sessionOrder.getNowTvFormType());
		sessionSummary.setWaivedPrepay(sessionOrder.getWaivedPrepay());
		if(sessionOrder.getOrderId() == null){
			String newOrderId = imsOrderDetailService.getNewOrderId("Y".equals(sessionOrder.getIsCC())?user.getChannelCd():sessionOrder.getShopCd(),sessionOrder.getCreateBy());
			sessionOrder.setOrderId(newOrderId);
		}
//************************* Antony Refernce *************************
		sessionSummary.setWarrPeriod(sessionOrder.getWarrPeriod());
		
		ImsRptServicePlanDetailDTO servPlanDto = new ImsRptServicePlanDetailDTO();
		
		ImsRptCoreServiceDetailDTO coreServiceDtl = new ImsRptCoreServiceDetailDTO();
		ImsRptBasketItemDTO progItem = new ImsRptBasketItemDTO();
		List<ImsRptBasketItemDTO> bvasMandItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> bvasNonMItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> preInstItemList = new ArrayList<ImsRptBasketItemDTO>();
		
		ImsRptOptServiceDetailDTO optServiceDtl = new ImsRptOptServiceDetailDTO();
		List<ImsRptBasketItemDTO> optPremItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> optServItemList = new ArrayList<ImsRptBasketItemDTO>();
		//List<ImsRptBasketItemDTO> wlBbItemList = new ArrayList<ImsRptBasketItemDTO>();
		//List<ImsRptBasketItemDTO> antiVirItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> mediaItemList = new ArrayList<ImsRptBasketItemDTO>();

		ImsRptNtvServiceDetailDTO ntvServiceDtl = new ImsRptNtvServiceDetailDTO();
		List<ImsRptBasketItemDTO> ntvFreeItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> ntvPayItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> ntvOtherItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptChannelDTO> ntvFreeSPChannelList = new ArrayList<ImsRptChannelDTO>();
		List<ImsRptChannelDTO> ntvFreeEPChannelList = new ArrayList<ImsRptChannelDTO>();
		List<ImsRptChannelDTO> ntvFreeHDChannelList = new ArrayList<ImsRptChannelDTO>();
		List<ImsRptChannelDTO> ntvPayChannelList = new ArrayList<ImsRptChannelDTO>();
		
		String basketId = "";
		String itemIdStr = "";
		
		
		//auto add item
		List<SubscribedItemUI> autoAddItemList = new ArrayList<SubscribedItemUI>();
		List<SubscribedItemUI> removeItemList = new ArrayList<SubscribedItemUI>();
		String subItemIdStr  = "";
		for(SubscribedItemUI subItem:sessionOrder.getSubscribedItems()){
			if(("BE_F_REC").equalsIgnoreCase(subItem.getType())||("VAS_DUMMY_GIFT").equalsIgnoreCase(subItem.getType())){
				removeItemList.add(subItem);
			}
			else if(StringUtils.isNotBlank(subItem.getId())){
				subItemIdStr += subItem.getId()+",";
			}
		}
		
		if(StringUtils.isNotBlank(subItemIdStr)){
			autoAddItemList.addAll(basketDetailService.addAutoTieItems(subItemIdStr.substring(0,subItemIdStr.length()-1)));
			autoAddItemList.addAll(basketDetailService.getVASAutoTieDummyGiftList(subItemIdStr.substring(0,subItemIdStr.length()-1)));
		}

		
		List<SubscribedItemUI> allSubscribedItems = new ArrayList<SubscribedItemUI>();
		
		if(autoAddItemList.size()>0||removeItemList.size()>0) {
			for(SubscribedItemUI item:sessionOrder.getSubscribedItems()){
				allSubscribedItems.add(item);
			}
			
			if(removeItemList.size()>0){ 
				for(SubscribedItemUI item:removeItemList){
					allSubscribedItems.remove(item);
				}
			}
			
			if(autoAddItemList.size()>0||removeItemList.size()>0){
				for(SubscribedItemUI item:autoAddItemList) {
					allSubscribedItems.add(item);
				}
			}
			sessionOrder.setSubscribedItems( allSubscribedItems.toArray(new SubscribedItemUI[allSubscribedItems.size()]));
		}
		//auto add item end
		
		
		SubscribedItemUI[] basket = sessionOrder.getSubscribedItems();
		sessionOrder.setHDDPurchased(false);
		for(int i=0; i < basket.length; i++){
			logger.debug(">>>basket " + i + " ID: " + basket[i].getBasketId());
			if(basket[i].getBasketId() != null && basket[i].getBasketId() != "" && basket[i].getType().equalsIgnoreCase("PROG")){
				basketId = basket[i].getBasketId();
			}
			if(i==0){
				itemIdStr = basket[i].getId();
			}else{
				itemIdStr = itemIdStr + "," + basket[i].getId();
			}
			if(basket[i].getType()!=null && basket[i].getType().equalsIgnoreCase("BE_F_REC"))
				sessionOrder.setHDDPurchased(true);				
		}

		SubscribedChannelUI[] channel = sessionOrder.getSubscribedChannels();
		String channelIdStr = "";
		if(channel != null){
			for(int i=0; i < channel.length; i++){
				if(i==0){
					channelIdStr = channel[i].getChannelId();
				}else{
					channelIdStr = channelIdStr + "," + channel[i].getChannelId();
				}
			}
		}
		
		ImsRptBasketDtlDTO basketDtl = new ImsRptBasketDtlDTO();
		List<ImsRptBasketItemDTO> basketItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> optServiceList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> ntvPgmItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptChannelDTO> channelFreeList = new ArrayList<ImsRptChannelDTO>();
		List<ImsRptChannelDTO> channelPayList = new ArrayList<ImsRptChannelDTO>();
		List<ImsRptGiftDTO> finalGiftList = new ArrayList<ImsRptGiftDTO>();
		List<ImsRptGiftDTO> giftList = new ArrayList<ImsRptGiftDTO>();
		
		basketDtl = imsReport2Service.getBasketDetail(basketId, locale);
		logger.debug("\nBasket detail=" + basketDtl);
		sessionSummary.setBasketDtl(basketDtl);
		
		if(itemIdStr != ""){
			basketItemList = imsReport2Service.getBasketItem(basketId, itemIdStr, locale);
			logger.debug("\nItem list =" + basketItemList);
			//sessionSummary.setBasketItemList(basketItemList);
			
			if(basketDtl.getCanSubcOptSrv().equalsIgnoreCase("Y")){
				optServiceList = imsReport2Service.getOptServiceY(basketId, itemIdStr, locale, sessionOrder.getOrderId(), false);
			}else{
				optServiceList = imsReport2Service.getOptServiceN(basketId, itemIdStr, locale, sessionOrder.getOrderId(), false);
			}
			logger.debug("\nOptional Service list =" + optServiceList);

			ntvPgmItemList = imsReport2Service.getNtvItem(itemIdStr, locale, false);
			logger.debug("\nItem list =" + ntvPgmItemList);
			
			String salesChannel = "";
			
			if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
				salesChannel = "DS";
			}else if(sessionOrder.getIsPT().equals("Y")){
				salesChannel = "PT";
			}else if(sessionOrder.getIsCC().equals("Y")){
				salesChannel = "CC";
			}else{
				salesChannel = "RS";
			}
			
			if("Y".equalsIgnoreCase(sessionOrder.getMobileOfferInd())){
				salesChannel = salesChannel + "_M";
			}
			
			giftList = imsReport2Service.getGiftList(
					itemIdStr,
					locale,
					salesChannel
					);
			logger.debug("giftList:"+giftList.size());
		}

		if(channelIdStr != ""){
			channelFreeList = imsReport2Service.getNtvChannelFree(channelIdStr, locale, sessionOrder.getWarrPeriod(), sessionOrder.getTvCredit());
			//logger.debug("\nItem list =" + channelFreeList);

			channelPayList = imsReport2Service.getNtvChannelPay(channelIdStr, locale, sessionOrder.getWarrPeriod(), sessionOrder.getTvCredit());
			//logger.debug("\nItem list =" + channelPayList);
		}
		
		double totalRecurrentAmt = 0;
		double totalMthToMthRate = 0;
		
		// Core Service
		coreServiceDtl.setBandwidth(basketDtl.getBandwidth());
		coreServiceDtl.setBandwidth_desc(basketDtl.getBandwidth_desc());
		coreServiceDtl.setCanSubcOptSrv(basketDtl.getCanSubcOptSrv());
		
		ImsRptBasketItemDTO item; 
		for (int i=0; i < basketItemList.size(); i++){
			if (basketItemList.get(i).getType().equalsIgnoreCase("PROG")) {
				progItem.setType(basketItemList.get(i).getType());
				progItem.setMdoInd(null);
				progItem.setItemTitle(basketDtl.getBasketTitle());
				progItem.setItemMthFix(basketItemList.get(i).getItemMthFix());
				progItem.setItemMth2Mth(basketItemList.get(i).getItemMth2Mth());
				progItem.setItemDetails(basketItemList.get(i).getItemDetails());
				progItem.setItemMthFixAmt(basketItemList.get(i).getItemMthFixAmt());
				progItem.setItemMth2MthAmt(basketItemList.get(i).getItemMth2MthAmt());
			}
			if (!basketItemList.get(i).getType().equalsIgnoreCase("PROG")&&!basketItemList.get(i).getType().equalsIgnoreCase("PRE_INST")
					&&!basketItemList.get(i).getType().equalsIgnoreCase("OTHER")&&basketDtl.getCanSubcOptSrv().equalsIgnoreCase("Y")) {
				if (basketItemList.get(i).getMdoInd().equalsIgnoreCase("M")) {
					item = new ImsRptBasketItemDTO(); 
					item.setType(basketItemList.get(i).getType());
					item.setMdoInd(basketItemList.get(i).getMdoInd());
					item.setItemTitle(basketItemList.get(i).getItemDetailName());
					item.setItemMthFix(null);
					item.setItemMth2Mth(null);
					item.setItemDetails(basketItemList.get(i).getItemDetailInfo());
					item.setItemMthFixAmt(null);
					item.setItemMth2MthAmt(null);
					bvasMandItemList.add(item);
				} else {
					item = new ImsRptBasketItemDTO(); 
					item.setType(basketItemList.get(i).getType());
					item.setMdoInd(basketItemList.get(i).getMdoInd());
					item.setItemTitle(basketItemList.get(i).getItemDetailName());
					item.setItemMthFix(basketItemList.get(i).getItemMthFix());
					item.setItemMth2Mth(basketItemList.get(i).getItemMth2Mth());
					item.setItemDetails(basketItemList.get(i).getItemDetailInfo());
					item.setItemMthFixAmt(basketItemList.get(i).getItemMthFixAmt());
					item.setItemMth2MthAmt(basketItemList.get(i).getItemMth2MthAmt());
					if(basketItemList.get(i).getItemMthFixAmt() != null && !("").equals(basketItemList.get(i).getItemMthFixAmt())){
						totalRecurrentAmt = totalRecurrentAmt + Double.parseDouble(basketItemList.get(i).getItemMthFixAmt());
					}
					if(basketItemList.get(i).getItemMth2MthAmt() != null && !("").equals(basketItemList.get(i).getItemMth2MthAmt())){
						totalMthToMthRate = totalMthToMthRate + Double.parseDouble(basketItemList.get(i).getItemMth2MthAmt());
					}
					bvasNonMItemList.add(item);
				}
			}
			if (basketItemList.get(i).getType().equalsIgnoreCase("PRE_INST")) {
				item = new ImsRptBasketItemDTO(); 
				item.setType(basketItemList.get(i).getType());
				item.setMdoInd(basketItemList.get(i).getMdoInd());
				item.setItemTitle(basketItemList.get(i).getItemDetailName());
				item.setItemMthFix(basketItemList.get(i).getItemMthFix());
				item.setItemMth2Mth(basketItemList.get(i).getItemMth2Mth());
				item.setItemDetails(basketItemList.get(i).getItemDetailInfo());
				item.setItemMthFixAmt(basketItemList.get(i).getItemMthFixAmt());
				item.setItemMth2MthAmt(basketItemList.get(i).getItemMth2MthAmt());
				preInstItemList.add(item);
			}
		}
		
		if(progItem.getItemMthFixAmt() != null && !("").equals(progItem.getItemMthFixAmt())){
			totalRecurrentAmt = totalRecurrentAmt + Double.parseDouble(progItem.getItemMthFixAmt());
		}
		if(progItem.getItemMth2MthAmt() != null && !("").equals(progItem.getItemMth2MthAmt())){
			totalMthToMthRate = totalMthToMthRate + Double.parseDouble(progItem.getItemMth2MthAmt());
		}
		
		coreServiceDtl.setProgItem(progItem);
		coreServiceDtl.getProgItem().setItemDetails(coreServiceDtl.getProgItem().getItemDetails().replaceAll(((char)10+""),"<br>"));
		coreServiceDtl.setBvasMandItemList(bvasMandItemList);
		coreServiceDtl.setBvasNonMItemList(bvasNonMItemList);
		servPlanDto.setCoreServiceDetail(coreServiceDtl);
		
		
		// Optional Service
		for (int i=0; i < optServiceList.size(); i++){			
			if (optServiceList.get(i).getType().equalsIgnoreCase("OPT_PREM")) {
				ImsRptBasketItemDTO optPrem = new ImsRptBasketItemDTO(); 
				optPrem.setType(optServiceList.get(i).getType());
				optPrem.setMdoInd(null);
				optPrem.setItemTitle(optServiceList.get(i).getItemDetailName());
				optPrem.setItemMthFix(optServiceList.get(i).getItemMthFix());
				optPrem.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
				optPrem.setItemDetails(optServiceList.get(i).getItemDetailInfo());
				optPrem.setItemMthFixAmt(optServiceList.get(i).getItemMthFixAmt());
				optPrem.setItemMth2MthAmt(optServiceList.get(i).getItemMth2MthAmt());
				optPremItemList.add(optPrem);
			}
/*			if (optServiceList.get(i).getType().equalsIgnoreCase("WL_BB")) {
				ImsRptBasketItemDTO wlBb = new ImsRptBasketItemDTO(); 
				wlBb.setType(optServiceList.get(i).getType());
				wlBb.setMdoInd(null);
				wlBb.setItemTitle(optServiceList.get(i).getItemDetailName());
				wlBb.setItemMthFix(optServiceList.get(i).getItemMthFix());
				wlBb.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
				wlBb.setItemDetails(optServiceList.get(i).getItemDetailInfo());
				wlBb.setItemMthFixAmt(optServiceList.get(i).getItemMthFixAmt());
				wlBb.setItemMth2MthAmt(optServiceList.get(i).getItemMth2MthAmt());
				wlBbItemList.add(wlBb);
			}
			if (optServiceList.get(i).getType().equalsIgnoreCase("ANTI_VIR")) {
				ImsRptBasketItemDTO antiVir = new ImsRptBasketItemDTO(); 
				antiVir.setType(optServiceList.get(i).getType());
				antiVir.setMdoInd(null);
				antiVir.setItemTitle(optServiceList.get(i).getItemDetailName());
				antiVir.setItemMthFix(optServiceList.get(i).getItemMthFix());
				antiVir.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
				antiVir.setItemDetails(optServiceList.get(i).getItemDetailInfo());
				antiVir.setItemMthFixAmt(optServiceList.get(i).getItemMthFixAmt());
				antiVir.setItemMth2MthAmt(optServiceList.get(i).getItemMth2MthAmt());
				antiVirItemList.add(antiVir);
			}
*/
			if (!optServiceList.get(i).getType().equalsIgnoreCase("OPT_PREM")
					&& !optServiceList.get(i).getType().equalsIgnoreCase("MEDIA")) {
				ImsRptBasketItemDTO optServ = new ImsRptBasketItemDTO(); 
				optServ.setType(optServiceList.get(i).getType());
				optServ.setMdoInd(null);
				optServ.setItemTitle(optServiceList.get(i).getItemDetailName());
				optServ.setItemMthFix(optServiceList.get(i).getItemMthFix());
				optServ.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
				optServ.setItemDetails(optServiceList.get(i).getItemDetailInfo());
				optServ.setItemMthFixAmt(optServiceList.get(i).getItemMthFixAmt());
				optServ.setItemMth2MthAmt(optServiceList.get(i).getItemMth2MthAmt());
				optServItemList.add(optServ);
			}
			if (optServiceList.get(i).getType().equalsIgnoreCase("MEDIA")) {
				ImsRptBasketItemDTO media = new ImsRptBasketItemDTO(); 
				media.setType(optServiceList.get(i).getType());
				media.setMdoInd(null);
				media.setItemTitle(optServiceList.get(i).getItemDetailName());
				media.setItemMthFix(optServiceList.get(i).getItemMthFix());
				media.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
				media.setItemDetails(optServiceList.get(i).getItemDetailInfo());
				media.setItemMthFixAmt(optServiceList.get(i).getItemMthFixAmt());
				media.setItemMth2MthAmt(optServiceList.get(i).getItemMth2MthAmt());
				mediaItemList.add(media);
			}
			if(optServiceList.get(i).getItemMthFixAmt() != null && !("").equals(optServiceList.get(i).getItemMthFixAmt())){
				totalRecurrentAmt = totalRecurrentAmt + Double.parseDouble(optServiceList.get(i).getItemMthFixAmt());
			}
			if(optServiceList.get(i).getItemMth2MthAmt() != null && !("").equals(optServiceList.get(i).getItemMth2MthAmt())){
				totalMthToMthRate = totalMthToMthRate + Double.parseDouble(optServiceList.get(i).getItemMth2MthAmt());
			}
		}
		
		optServiceDtl.setOptPremItemList(optPremItemList);
//		optServiceDtl.setWlBbItemList(wlBbItemList);
//		optServiceDtl.setAntiVirItemList(antiVirItemList);
		optServiceDtl.setOptServItemList(optServItemList);
		optServiceDtl.setMediaItemList(mediaItemList);
		servPlanDto.setOptServiceDetail(optServiceDtl);
		
		//Gift Tony 20140904
		
		if (giftList!=null&&giftList.size()>0){
			servPlanDto.setGiftList(giftList);
		}
		
		// Now TV
		if(ntvPgmItemList == null){
			sessionSummary.setNtvPgmItemList(null);
		}
		
		for (int i=0; i < ntvPgmItemList.size(); i++){
			if (ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_FREE")) {
				ImsRptBasketItemDTO ntvFree = new ImsRptBasketItemDTO(); 
				ntvFree.setType(ntvPgmItemList.get(i).getType());
				ntvFree.setMdoInd(null);
				ntvFree.setItemTitle(ntvPgmItemList.get(i).getItemDetailName());
				ntvFree.setItemMthFix(ntvPgmItemList.get(i).getItemMthFix());
				ntvFree.setItemMth2Mth(ntvPgmItemList.get(i).getItemMth2Mth());
				ntvFree.setItemDetails(ntvPgmItemList.get(i).getItemDetailInfo().replaceAll(((char)10+""),"<br>"));
				ntvFree.setItemMthFixAmt(ntvPgmItemList.get(i).getItemMthFixAmt());
				ntvFree.setItemMth2MthAmt(ntvPgmItemList.get(i).getItemMth2MthAmt());
				ntvFree.setItemTvType(ntvPgmItemList.get(i).getItemTvType());
				ntvFreeItemList.add(ntvFree);
			}
/*			if (ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_PAY")
					|| ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_P_30F6")) {*/
			if (!ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_FREE")
					&& !ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_OTHER")
					&& !ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_PAY2")
					&& !ntvPgmItemList.get(i).getType().contains ("AIO_SUBOWN")
					&& !ntvPgmItemList.get(i).getType().contains ("AIO_RENTAL")
					&& !ntvPgmItemList.get(i).getType().contains ("HDD_PREM")) {
				ImsRptBasketItemDTO ntvPay = new ImsRptBasketItemDTO(); 
				ntvPay.setType(ntvPgmItemList.get(i).getType());
				ntvPay.setMdoInd(null);
				ntvPay.setItemTitle(ntvPgmItemList.get(i).getItemDetailName());
				ntvPay.setItemMthFix(ntvPgmItemList.get(i).getItemMthFix());
				ntvPay.setItemMth2Mth(ntvPgmItemList.get(i).getItemMth2Mth());
				ntvPay.setItemDetails(ntvPgmItemList.get(i).getItemDetailInfo());
				ntvPay.setItemMthFixAmt(ntvPgmItemList.get(i).getItemMthFixAmt());
				ntvPay.setItemMth2MthAmt(ntvPgmItemList.get(i).getItemMth2MthAmt());
				ntvPay.setItemTvType(ntvPgmItemList.get(i).getItemTvType());
				ntvPayItemList.add(ntvPay);
			}
			if (ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_OTHER")
					||ntvPgmItemList.get(i).getType().contains ("AIO_SUBOWN")
					||ntvPgmItemList.get(i).getType().contains ("AIO_RENTAL")
					||ntvPgmItemList.get(i).getType().contains ("HDD_PREM")) {
				ImsRptBasketItemDTO ntvOther = new ImsRptBasketItemDTO(); 
				ntvOther.setType(ntvPgmItemList.get(i).getType());
				ntvOther.setMdoInd(null);
				ntvOther.setItemTitle(ntvPgmItemList.get(i).getItemDetailName());
				ntvOther.setItemMthFix(ntvPgmItemList.get(i).getItemMthFix());
				ntvOther.setItemMth2Mth(ntvPgmItemList.get(i).getItemMth2Mth());
				ntvOther.setItemDetails(ntvPgmItemList.get(i).getItemDetailInfo());
				ntvOther.setItemMthFixAmt(ntvPgmItemList.get(i).getItemMthFixAmt());
				ntvOther.setItemMth2MthAmt(ntvPgmItemList.get(i).getItemMth2MthAmt());
				ntvOther.setItemTvType(ntvPgmItemList.get(i).getItemTvType());
				ntvOtherItemList.add(ntvOther);
			}
			if(ntvPgmItemList.get(i).getItemMthFixAmt() != null && !("").equals(ntvPgmItemList.get(i).getItemMthFixAmt())){
				totalRecurrentAmt = totalRecurrentAmt + Double.parseDouble(ntvPgmItemList.get(i).getItemMthFixAmt());
			}
			if(ntvPgmItemList.get(i).getItemMth2MthAmt() != null && !("").equals(ntvPgmItemList.get(i).getItemMth2MthAmt())){
				totalMthToMthRate = totalMthToMthRate + Double.parseDouble(ntvPgmItemList.get(i).getItemMth2MthAmt());
			}
		}
		
		for (int i=0; i < channelFreeList.size(); i++){
			if (channelFreeList.get(i).getChannelGroupCd().equalsIgnoreCase("STARTERPACK")) {
				ImsRptChannelDTO starterPack = new ImsRptChannelDTO(); 
				starterPack.setChannelGroupCd(channelFreeList.get(i).getChannelGroupCd());
				starterPack.setChannelGroupDesc(channelFreeList.get(i).getChannelGroupDesc());
				starterPack.setChannelId(channelFreeList.get(i).getChannelId());
				starterPack.setChannelCd(channelFreeList.get(i).getChannelCd());
				starterPack.setTvType(channelFreeList.get(i).getTvType());
				starterPack.setCredit(channelFreeList.get(i).getCredit());
				starterPack.setMdoInd(channelFreeList.get(i).getMdoInd());
				starterPack.setChannelDesc(channelFreeList.get(i).getChannelDesc().replaceAll(((char)10+""),"<br>"));
				starterPack.setParentChannelId(channelFreeList.get(i).getParentChannelId());
				ntvFreeSPChannelList.add(starterPack);
			}
			if (channelFreeList.get(i).getChannelGroupCd().equalsIgnoreCase("ENTPACK")) {
				ImsRptChannelDTO enterPack = new ImsRptChannelDTO(); 
				enterPack.setChannelGroupCd(channelFreeList.get(i).getChannelGroupCd());
				enterPack.setChannelGroupDesc(channelFreeList.get(i).getChannelGroupDesc());
				enterPack.setChannelId(channelFreeList.get(i).getChannelId());
				enterPack.setChannelCd(channelFreeList.get(i).getChannelCd());
				enterPack.setTvType(channelFreeList.get(i).getTvType());
				enterPack.setCredit(channelFreeList.get(i).getCredit());
				enterPack.setMdoInd(channelFreeList.get(i).getMdoInd());
				enterPack.setChannelDesc(channelFreeList.get(i).getChannelDesc().replaceAll(((char)10+""),"<br>"));
				enterPack.setParentChannelId(channelFreeList.get(i).getParentChannelId());
				ntvFreeEPChannelList.add(enterPack);
			}
			if (channelFreeList.get(i).getChannelGroupCd().equalsIgnoreCase("FREE2HD")) {
				ImsRptChannelDTO free2Hd = new ImsRptChannelDTO(); 
				free2Hd.setChannelGroupCd(channelFreeList.get(i).getChannelGroupCd());
				free2Hd.setChannelGroupDesc(channelFreeList.get(i).getChannelGroupDesc());
				free2Hd.setChannelId(channelFreeList.get(i).getChannelId());
				free2Hd.setChannelCd(channelFreeList.get(i).getChannelCd());
				free2Hd.setTvType(channelFreeList.get(i).getTvType());
				free2Hd.setCredit(channelFreeList.get(i).getCredit());
				free2Hd.setMdoInd(channelFreeList.get(i).getMdoInd());
				free2Hd.setChannelDesc(channelFreeList.get(i).getChannelDesc().replaceAll(((char)10+""),"<br>"));
				free2Hd.setParentChannelId(channelFreeList.get(i).getParentChannelId());
				ntvFreeHDChannelList.add(free2Hd);
			}
		}
		
		for (int i=0; i < channelPayList.size(); i++){
			ImsRptChannelDTO channelPay = new ImsRptChannelDTO(); 
			channelPay.setChannelGroupCd(null);
			channelPay.setChannelGroupDesc(channelPayList.get(i).getChannelGroupDesc());
			channelPay.setChannelId(channelPayList.get(i).getChannelId());
			channelPay.setChannelCd(channelPayList.get(i).getChannelCd());
			channelPay.setTvType(channelPayList.get(i).getTvType());
			channelPay.setCredit(channelPayList.get(i).getCredit());
			channelPay.setMdoInd(channelPayList.get(i).getMdoInd());
			channelPay.setChannelDesc(channelPayList.get(i).getChannelDesc().replaceAll(((char)10+""),"<br>"));
			channelPay.setParentChannelId(channelPayList.get(i).getParentChannelId());
			logger.info(channelPay.getParentChannelId());
			ntvPayChannelList.add(channelPay);
		}

		ntvServiceDtl.setNtvFreeItemList(ntvFreeItemList);
		ntvServiceDtl.setNtvPayItemList(ntvPayItemList);
		ntvServiceDtl.setNtvOtherItemList(ntvOtherItemList);
		ntvServiceDtl.setNtvFreeSPChannelList(ntvFreeSPChannelList);
		ntvServiceDtl.setNtvFreeEPChannelList(ntvFreeEPChannelList);
		ntvServiceDtl.setNtvFreeHDChannelList(ntvFreeHDChannelList);
		ntvServiceDtl.setNtvPayChannelList(ntvPayChannelList);
		servPlanDto.setNtvServiceDetail(ntvServiceDtl);

		sessionSummary.setServPlanDto(servPlanDto);
		sessionSummary.setTotalRecurrentAmt(totalRecurrentAmt);
		sessionSummary.setTotalMthToMthRate(totalMthToMthRate);
		
		//logger.info(sessionOrder.getBillingAddress().getAreaDesc());
		logger.info("sessionSummary");
		String imsSubmitTag = "";
		if(request.getSession().getAttribute("imsSubmitTag") != null){
			imsSubmitTag = (String)request.getSession().getAttribute("imsSubmitTag");
		}
		if(imsSubmitTag.equals("C")){
			String imsOrderId = (String)request.getSession().getAttribute("imsOrderId");
			sessionSummary.setOrderId(imsOrderId);
			logger.info("Order ID: " + imsOrderId);
			request.getSession().setAttribute("imsOrderId", null);
		}
		if(sessionOrder.getReasonCd() != null && sessionOrder.getReasonCd().substring(0, 1).equals("C")){
			sessionSummary.setcReasonCd(sessionOrder.getReasonCd());
		}
		sessionSummary.setSubmitTag((String)request.getSession().getAttribute("imsSubmitTag"));
		Entry<String, String>[] clist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsCancelLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			clist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsDSCancelLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		}
		for(int i=0; i<clist.length; i++){
			System.out.println(clist[i].getKey()+":"+clist[i].getValue());
		}
		request.getSession().setAttribute("cancelList", clist);
		
		
		//add by steven
		if ( !"Y".equals(sessionOrder.getIsCC())|| ("Y".equals(sessionOrder.getIsPT())&& "R".equals(sessionSummary.getMode())))
		{
			request.getSession().setAttribute(ImsConstants.IMS_DS_MISMATCH_HKID,false);
			sessionSummary.setAllOrdDocAssgnDTOs(this.retrieveAllOrdDocAssgnDTOs(request, sessionSummary.getCashFsPrepay()));
		}
		//add by steven end
		
		//add by Tony
		if ( "Y".equalsIgnoreCase(sessionOrder.getIsCC()) || "R".equals(sessionSummary.getMode()))
		{
			if (sessionOrder.getAllOrdDocAssgnDTOs()==null){
				
				sessionSummary.setAllOrdDocAssgnDTOs(this.retrieveCCAllOrdDocAssgnDTOs(request));
				List<ImsCollectDocDTO> imsCollectDocList = new ArrayList<ImsCollectDocDTO>();
			
				//initialize imsCollectDocList
				for(AllOrdDocAssgnDTO ordDocAssgn: sessionSummary.getAllOrdDocAssgnDTOs()){
					ImsCollectDocDTO imsCollectDoc = new ImsCollectDocDTO();
					imsCollectDoc.setDocType(ordDocAssgn.getDocType().toString());
					imsCollectDoc.setDocTypeDisplay(ordDocAssgn.getDocName());
					imsCollectDoc.setWaiveReason(ordDocAssgn.getWaiveReason());
			//		imsCollectDoc.setMarkDelInd(ordDocAssgn.getMarkDelInd().toString());
					imsCollectDoc.setCollectedInd("N");
			//		collectDoc.setFaxSerial(faxSerialMap.get(ordDocAssgn.getDocType()));
					imsCollectDocList.add(imsCollectDoc);
				}
				sessionSummary.setImsCollectDocDTOs(imsCollectDocList);
				sessionOrder.setAllOrdDocAssgnDTOs(sessionSummary.getAllOrdDocAssgnDTOs());
				sessionOrder.setImsCollectDocDtoList(imsCollectDocList);
				}else{
					sessionSummary.setAllOrdDocAssgnDTOs(sessionOrder.getAllOrdDocAssgnDTOs());
					sessionSummary.setImsCollectDocDTOs(sessionOrder.getImsCollectDocDtoList());
				}
			sessionSummary.setEdfRef(sessionOrder.getEdfRef());
		}
		//add by Tony end
		
		String salesChannelAF = "";
		
		salesChannelAF = imsReport2Service.getSalesChannel(sessionOrder.getShopCd(), sessionOrder.getSalesCd());
		
		sessionOrder.setSalesChannel(salesChannelAF);
		
		// martin
		if ("Y".equals(sessionOrder.getTvPriceInd())) {
			sessionSummary.getServPlanDto().getNtvServiceDetail().setNtvFreeItemList(null);
			sessionSummary.getServPlanDto().getNtvServiceDetail().setNtvFreeHDChannelList(null);
			sessionSummary.getServPlanDto().getNtvServiceDetail().setNtvFreeEPChannelList(null);
			
			NowTVAddUI addui = imsNowTVService.getNewTVPricingDtl(sessionOrder, locale, false, null);
			sessionSummary.setNowTVAddUI(addui);
			sessionSummary.setNewtvpricingind("Y");
		} else {
			sessionSummary.setNewtvpricingind("N");
		}
		// martin end
		
		sessionSummary.setOtChrgType(sessionOrder.getOtChrgType());
		sessionSummary.setIsPT(sessionOrder.getIsPT());
		sessionSummary.setIsCC(sessionOrder.getIsCC());
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)) sessionSummary.setIsDS("Y");
		else sessionSummary.setIsDS("N");
		sessionSummary.setQcCallingTimePeriod(sessionOrder.getDsQcCallTime());
		sessionSummary.setWaiveQc(sessionOrder.getDsWaiveQC());
		
		if(sessionSummary.getOptoutTheClubReason()!=null&&sessionSummary.getOptoutTheClubReason().length()>0){
			sessionSummary.setClubOptOutReasonDesc(LookupTableBean.getInstance().getImsClubOptoutLookupMap().get(sessionSummary.getOptoutTheClubReason()));
		}
		
		return sessionSummary;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		
		logger.info("onSubmit is called");

		ImsSummaryUI sessionSummary = (ImsSummaryUI)command;
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);	
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};									

		String iGuardAdminEmail = ConfigProperties.getPropertyByEnvironment("iGuardAdminEmailAddr");
		
		//String shopCd = sessionOrder.getShopCd();
/*		if(sessionSummary.getPrimaryAcctNo() != null
				&& !("").equals(sessionSummary.getPrimaryAcctNo())){
			int acctnb = Integer.parseInt(sessionSummary.getPrimaryAcctNo());
			String srccode = null;
			String currentView = "imssummary.html";
		
			int result = -1;
			
			result = validateAccountNumService.validateAccountNum(acctnb, srccode);

			if(result != 0){
				return new ModelAndView(new RedirectView(currentView));
			}
		}
*/

		if(sessionOrder.getCustomer().getAccount().getBillMedia().equals("P")){
			sessionOrder.getCustomer().getAccount().setEmailAddr("");
		}
		sessionOrder.setLangPreference(sessionSummary.getLangPreference().equals("en") ?"ENG" : "CHI");
		sessionOrder.setPrimaryAcctNo(sessionSummary.getPrimaryAcctNo());
		
		DisMode disMode = sessionSummary.getDisMode();
    	if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
    		sessionOrder.getCustomer().getContact().setEmailAddr(sessionSummary.getEsigEmailAddr());
    	}
    	else if ( disMode == DisMode.E)
    	{
    		sessionOrder.getCustomer().getContact().setEmailAddr(sessionSummary.getEsigEmailAddr());
    	}else if ( disMode == DisMode.I )
    	{
    		sessionOrder.getCustomer().getContact().setEmailAddr(sessionSummary.getEmailAddr());
    	}
		sessionOrder.getCustomer().getContact().setContactPhone(sessionSummary.getSMSno());
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, sessionOrder);
		
		System.out.println(gson.toJson(sessionOrder));
		//sessionOrder.setShopCd("TP1");
		//String newOrderId = imsOrderService.getNewOrderId(sessionOrder.getShopCd());
		if(sessionSummary.getcReasonCd() != null && !("").equals(sessionSummary.getcReasonCd())
				&& sessionSummary.getSubmitTag().equals("C")){
			int releaseResult = -99;
			releaseResult = releaseLoginIDService.releaseLoginID(sessionOrder.getLoginId(), sessionOrder.getCustomer().getIdDocNum(), sessionOrder.getCustomer().getIdDocType());
			logger.info(releaseResult);
			
			
			MobileOffer imsMobileOfferUi = new MobileOffer();
			List<MobileOffer> imsMobileOfferUiList = (List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList");
			
			logger.info("Sunmmmary Release MobilePIN*** (List<MobileOffer>) request.getSession().getAttribute"+"(imsMobileOfferUiList):"+ gson.toJson((List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList")));
			
			if (imsMobileOfferUiList != null){
				
				for(int i= 0; i<imsMobileOfferUiList.size(); i++){
				
					if(sessionOrder != null && !("").equals(sessionOrder)){
						try{
							imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
						}catch(Exception e){
							logger.error("Exception caught in releaseMobilePIN()", e);
							throw new ImsMobilePinException();
						}
						logger.info("release old number: " + imsMobileOfferUiList.get(i).mrt + " , old pin: " + imsMobileOfferUiList.get(i).pin);
					}else{
						try{
							imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin);
						}catch(Exception e){
							logger.error("Exception caught in releaseMobilePIN()", e);
							throw new ImsMobilePinException();	
						}
						logger.info("release old number: " + imsMobileOfferUiList.get(i).mrt + " , old pin: " + imsMobileOfferUiList.get(i).pin);
					}
				
					logger.info(gson.toJson(imsMobileOfferUi));
				}
			}
			request.getSession().setAttribute("imsMobileOfferUiList", null);
			/*
			if(request.getSession().getAttribute("imsMobileOfferUi") != null){
				logger.info("request.getSession().getAttribute"+"(imsMobileOfferUi):"+ gson.toJson(request.getSession().getAttribute("imsMobileOfferUi")));
				
				imsMobileOfferUi = (MobileOffer) request.getSession().getAttribute("imsMobileOfferUi");
				if(sessionOrder != null && !("").equals(sessionOrder)){
					imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUi.mrt, imsMobileOfferUi.pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
					logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);
				}else if((imsMobileOfferUi.mrt != null && !("").equals(imsMobileOfferUi.mrt)) && (imsMobileOfferUi.pin != null && !("").equals(imsMobileOfferUi.pin))){
					imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUi.mrt, imsMobileOfferUi.pin);
					logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);
				}
				
				request.getSession().setAttribute("imsMobileOfferUi", null);
			}else if(sessionOrder.getSubscribedCslOffers() != null){
				SubscribedCslOfferDTO[] csldto = sessionOrder.getSubscribedCslOffers(); 
				if(csldto[0].getVas_parm_a() != null 
						&& !"".equals(csldto[0].getVas_parm_a())
						&& csldto[0].getVas_parm_b() != null 
						&& !"".equals(csldto[0].getVas_parm_b())){
					
					if(sessionOrder != null && !("").equals(sessionOrder)){
					mobilePINService.releaseMobilePIN(csldto[0].getVas_parm_a(), csldto[0].getVas_parm_b(), sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
					}else{
						mobilePINService.releaseMobilePIN(csldto[0].getVas_parm_a(), csldto[0].getVas_parm_b());
					}
					logger.info("release old number: " +csldto[0].getVas_parm_a()+ " , old pin: " +csldto[0].getVas_parm_b());
				}
				
			}*/
			
			imsOrderDetailService.cancelOrder(sessionOrder, sessionSummary.getcReasonCd());
			String currentView = "imssummary.html";
			
	
			
			request.getSession().setAttribute("imsSubmitTag", sessionSummary.getSubmitTag());
			request.getSession().setAttribute("imsOrderId", sessionOrder.getOrderId());
			request.getSession().setAttribute("imsLoginIdUi", null);
			request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, null);
			
			return new ModelAndView(new RedirectView(currentView));
		}else{
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	if(user == null){
    		user = new BomSalesUserDTO();
    	}

    	
		
		

    	// eSignature CR by Eric Ng. 
    	String orderId = sessionSummary.getOrderId();
    	String username = user.getUsername();
    	//request.getSession().setAttribute("sessionSummaryDisMode",disMode);
    	

    	
    	CollectMethod collectMethod = sessionSummary.getCollectMethod();
    	
    	String emailAddress = "";
    	String SMSno = "";
    	//celia 20141110
    	if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
    		emailAddress = sessionSummary.getEsigEmailAddr();
    		SMSno = sessionSummary.getSMSno();
    	}
    	else if ( disMode == DisMode.E)
    	{
    		emailAddress = sessionSummary.getEsigEmailAddr();
    		SMSno = sessionSummary.getSMSno();
    	}else if ( disMode == DisMode.I )
    	{
    		emailAddress = sessionSummary.getEmailAddr();
    		SMSno = sessionSummary.getSMSno();
    	}
    	else if ( disMode == DisMode.S) 
    	{
    		SMSno = sessionSummary.getSMSno();
    	}
    	
    	
    	String langPreference = sessionSummary.getLangPreference();
    	EsigEmailLang emailLang =  (langPreference.equals("en")) ? EsigEmailLang.ENG : EsigEmailLang.CHN ;
 
 		sessionOrder.setEsigEmailLang(emailLang);
		sessionOrder.setEsigEmailAddr(emailAddress);
		sessionOrder.setDisMode(disMode);
		
		generateRpt(request, sessionOrder, langPreference);
		File file = new File(acqUrl + sessionOrder.getOrderId(),sessionOrder.getOrderId()+"_AF.pdf");
//		File file = new File(acqUrl + sessionOrder.getOrderId(),sessionOrder.getOrderId()+"_AFs.pdf");
		logger.info(file.getPath());
		logger.info("pdf size:"+file.length());
		
			if(!file.exists()|| file.length()<=0){
//				throw new FileNotFoundException("no AF generated");
				throw new ImsReportGerationException();
			}

		sessionOrder.setSMSno(SMSno);
		sessionOrder.setEmailAddress(emailAddress);
		sessionOrder.setCollectMethod(collectMethod);
		sessionOrder.setFAXno(sessionSummary.getFAXno());
		
		sessionOrder.setDsQcCallTime(sessionSummary.getQcCallingTimePeriod());
		sessionOrder.setDsWaiveQC(sessionSummary.getWaiveQc());
		
		//ims direct sales altered
		sessionOrder.setDsWaiveQC(sessionSummary.getWaiveQCReason()); 
		sessionOrder.setDsQcCallTime(sessionSummary.getQcCallingTimePeriod());
		
		//add system finding checking for QC
		/*
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			if (imsAddressInfoService.checkifAddressExactMatchwService(sessionOrder.getInstallAddress().getSerbdyno(), 
					 sessionOrder.getInstallAddress().getFloorNo(), 
					 sessionOrder.getInstallAddress().getUnitNo()) ||
					 (sessionOrder.getCustomer().getCustNo().length()>0)){
			sessionOrder.setSysF("1D1I");
			}else sessionOrder.setSysF("");	
		}*/
		
		if (sessionOrder.getCustomer().getCustNo() != null){
		    logger.debug("IS new customer: " + sessionOrder.getCustomer().getCustNo());
		    if (sessionOrder.getCustomer().getCustNo().length()>0)
		    	sessionOrder.setIsNewCust("N"); 
		    else sessionOrder.setIsNewCust("Y");
		}else sessionOrder.setIsNewCust("Y");
		
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			if ("M".equals(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType()) &&
					"N".equals(sessionOrder.getCashFsPrepay()) &&
					!"Y".equals(sessionOrder.getWaivedPrepay())
			){
				imsOrderService.saveQRCodeFile(sessionOrder, "N", sessionOrder.getPrepayCash());
			}
		}
		
		OrderImsUI signOffOrder = imsOrderDetailService.signOffOrder(sessionOrder);
		generateRpt(request, sessionOrder, langPreference);
		
		//sessionOrder.setOrderId(newOrderId);
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, signOffOrder);
		String submitTag = "SO";
		request.getSession().setAttribute("imsSubmitTag", submitTag);
		request.getSession().setAttribute("imsLoginIdUi", null);
		request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, null);
		request.getSession().setAttribute("imsMobileOfferUi", null);
		
		// eSignature CR by Eric Ng. Making email request
		ModelAndView myView = new ModelAndView(new RedirectView(getSuccessView()));
		
		// "AF_Full_" + langPreference + ".pdf"

//		generateRpt(request, sessionOrder, orderId, langPreference);
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			EmailReqResult emailReqResult = null;
			EmailReqResult careCashEmailReqResult;
			EmailReqResult iGuardEmailReqResult;
			logger.debug("Make email request.");
			String sendQRCode="";
			if ("M".equals(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType()) &&
					"N".equals(sessionOrder.getCashFsPrepay()) &&
					!"Y".equals(sessionOrder.getWaivedPrepay())
			)sendQRCode="Y";
			else sendQRCode="N";
			String templateIdE= imsEmailParamHelperDAO.getDSTemplateId(user.getChannelId()+"", sessionOrder.getOrderType(), "E", sendQRCode);
			String templateIdS= imsEmailParamHelperDAO.getDSTemplateId(user.getChannelId()+"", sessionOrder.getOrderType(), "S", sendQRCode);
			
			emailReqResult = orderEsignatureService.createEmailReqIMS(orderId, templateIdE,
					new Date(), orderId + "_AF.pdf" , null, null, emailAddress, username);
			imsSMSService.createSMSReq(orderId, new Date(), SMSno, username, templateIdS, 1);
			
			if("I".equalsIgnoreCase(sessionSummary.getCareCashInd())){
				careCashEmailReqResult = orderEsignatureService.createEmailReqIMS(orderId, "RT034",
						new Date(), orderId + "_CARECash.pdf" , null, null, sessionSummary.getCareCashEmail(), username);
				iGuardEmailReqResult = orderEsignatureService.createEmailReqIMS(orderId, "RT035",
						new Date(), orderId + "_CARECash.pdf" , null, null, iGuardAdminEmail, username);
			}

			myView.addObject( "emailReqResult", emailReqResult.toString());
			myView.addObject( "emailReqResultMsg", emailReqResult.getMessage());
			
			
		}else if (disMode == DisMode.E && !"Y".equals(sessionOrder.getIsPT())){
			EmailReqResult emailReqResult;
			EmailReqResult careCashEmailReqResult;
			EmailReqResult iGuardEmailReqResult;
			logger.debug("Make email request.");
//			
			emailReqResult = orderEsignatureService.createEmailReq(orderId, "RT001",
					new Date(), orderId + "_AF.pdf" , null, null, emailAddress, username);
			
			System.out.println("Eric Ng emailReqResult:" + emailReqResult.toString());
			
			if("I".equalsIgnoreCase(sessionSummary.getCareCashInd())){
				careCashEmailReqResult = orderEsignatureService.createEmailReq(orderId, "RT034",
						new Date(), orderId + "_CARECash.pdf" , null, null, sessionSummary.getCareCashEmail(), username);
				iGuardEmailReqResult = orderEsignatureService.createEmailReq(orderId, "RT035",
						new Date(), orderId + "_CARECash.pdf" , null, null, iGuardAdminEmail, username);
			}
			
			imsSMSService.createSMSReq(orderId, new Date(), SMSno, username, "RT046", 1);
			
			myView.addObject( "emailReqResult", emailReqResult.toString());
			myView.addObject( "emailReqResultMsg", emailReqResult.getMessage());
			
		}
		else if ( disMode == DisMode.I || (disMode == DisMode.E && "Y".equals(sessionOrder.getIsPT()) && "R".equals(sessionOrder.getMode())))
		{	
			EmailReqResult emailReqResult;
			EmailReqResult careCashEmailReqResult;
			EmailReqResult iGuardEmailReqResult;
			logger.debug("Make email request.");
			if ("Y".equals(sessionOrder.getIsPT()))
			{
				emailReqResult = orderEsignatureService.createEmailReqIMS(orderId, "RT013",
					new Date(), orderId + "_AF.pdf" , null, null, emailAddress, username);
			}
			else
			{
				emailReqResult = orderEsignatureService.createEmailReqIMS(orderId, "RT012",
						new Date(), orderId + "_AF.pdf" , null, null, emailAddress, username);
			}
			
			imsSMSService.createSMSReq(orderId, new Date(), SMSno, username, "Y".equals(sessionOrder.getIsPT())?"RT015":"RT014", 1);
			
			if("I".equalsIgnoreCase(sessionSummary.getCareCashInd())){
				careCashEmailReqResult = orderEsignatureService.createEmailReqIMS(orderId, "RT034",
						new Date(), orderId + "_CARECash.pdf" , null, null, sessionSummary.getCareCashEmail(), username);
				iGuardEmailReqResult = orderEsignatureService.createEmailReqIMS(orderId, "RT035",
						new Date(), orderId + "_CARECash.pdf" , null, null, iGuardAdminEmail, username);
			}
			
			System.out.println("Anthony emailReqResult:" + emailReqResult.toString());
			
			myView.addObject( "emailReqResult", emailReqResult.toString());
			myView.addObject( "emailReqResultMsg", emailReqResult.getMessage());
			
		}
		else if ( disMode == DisMode.S )
		{		
			EmailReqResult careCashEmailReqResult;
			EmailReqResult iGuardEmailReqResult;
			
			imsSMSService.createSMSReq(orderId, new Date(), SMSno, username, "Y".equals(sessionOrder.getIsPT())?"RT015":"RT014");
			
			if("I".equalsIgnoreCase(sessionSummary.getCareCashInd())){
				careCashEmailReqResult = orderEsignatureService.createEmailReqIMS(orderId, "RT034",
						new Date(), orderId + "_CARECash.pdf" , null, null, sessionSummary.getCareCashEmail(), username);
				iGuardEmailReqResult = orderEsignatureService.createEmailReqIMS(orderId, "RT035",
						new Date(), orderId + "_CARECash.pdf" , null, null, iGuardAdminEmail, username);
			}
			
		}
		else if ( disMode == DisMode.P )
		{		
			EmailReqResult careCashEmailReqResult;
			EmailReqResult iGuardEmailReqResult;
			
			if("I".equalsIgnoreCase(sessionSummary.getCareCashInd())){
				careCashEmailReqResult = orderEsignatureService.createEmailReq(orderId, "RT034",
						new Date(), orderId + "_CARECash.pdf" , null, null, sessionSummary.getCareCashEmail(), username);
				iGuardEmailReqResult = orderEsignatureService.createEmailReq(orderId, "RT035",
						new Date(), orderId + "_CARECash.pdf" , null, null, iGuardAdminEmail, username);
			}
			
		}

		//add by steven
		if (disMode == DisMode.E) {//E - e-sign, P - paper
			ordDocService.updateAllOrderDocDTOOutdatedInd(orderId, "I004");//I004 - Signed Application forms- mark outdated
		}
		
		
		if (disMode == DisMode.P) {//E - e-sign, P - paper
			request.getSession().setAttribute(ImsConstants.IMS_CUST_SIGN, null);// make all sign null
			request.getSession().setAttribute(ImsConstants.IMS_CREDIT_CARD_SIGN, null);// make all sign null
			request.getSession().setAttribute(ImsConstants.IMS_3_PARTY_SIGN, null);// make all sign null
			request.getSession().setAttribute(ImsConstants.IMS_TDO_MOOV_SIGN, null);// make all sign null
			request.getSession().setAttribute(ImsConstants.IMS_TDO_TV_SIGN, null);// make all sign null
			request.getSession().setAttribute(ImsConstants.IMS_TDO_PCD_SIGN, null);// make all sign null
		}
		if (sessionOrder != null) {
			if ("M".equals(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType())) {// M - Cash
				ordDocService.updateAllOrderDocDTOOutdatedInd(orderId, "I002");//I002 - Credit card copy- mark outdated
				request.getSession().setAttribute(ImsConstants.IMS_CREDIT_CARD_SIGN, null);// make credit sign null
				request.getSession().setAttribute(ImsConstants.IMS_3_PARTY_SIGN, null);// make credit sign null
			}
		}
		if (sessionOrder != null) {
			if ("C".equals(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType())) {// C - Credit Card
				ordDocService.updateAllOrderDocDTOOutdatedInd(orderId, "I003");//I003 - Cash Memo- mark outdated
			}
		}

		BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		//non cc update collected indicator
		if (!"Y".equals(sessionOrder.getIsCC())){
			if (!this.isEmpty(sessionSummary.getAllOrdDocAssgnDTOs())) {
				//System.out.println("!this.isEmpty(sessionSummary.getAllOrdDocAssgnDTOs(), on submit in summary controller");
				for (Iterator<AllOrdDocAssgnDTO> it = sessionSummary.getAllOrdDocAssgnDTOs().iterator(); it.hasNext(); ) {
					AllOrdDocAssgnDTO dto = it.next();
					if (StringUtils.isBlank(dto.getWaiveReason())) {
						dto.setCollectedInd(CollectedInd.Y.equals(dto.getCollectedInd()) ? CollectedInd.Y : CollectedInd.N);
					} else {
						dto.setCollectedInd(CollectedInd.N);
					}
					dto.setWaivedBy(StringUtils.isBlank(dto.getWaiveReason()) ? null : salesUserDto.getUsername());
					int number_of_collected_doc = ordDocService.getImsNumberOfCollectedDoc(sessionOrder.getOrderId(), dto.getDocType().toString());
					if(number_of_collected_doc>0){
						dto.setCollectedInd(CollectedInd.Y);
					}else{
						dto.setCollectedInd(CollectedInd.N);
					}				
					if (DocType.I004.equals(dto.getDocType())) {// I004 - Signed Application forms
						if (DisMode.E.equals(sessionSummary.getDisMode())) {// E - esign, P - Paper
							it.remove();
						}
					}
					System.out.println("In summary controller, on Submit, :required doc:\t"+dto.getDocName()+"\t"+dto.getDocType()+"\t"+dto.getCollectedInd());
				}
			}else{
				System.out.println("this.isEmpty(sessionSummary.getAllOrdDocAssgnDTOs()");
			}
		}
		
		if ("Y".equals(sessionOrder.getIsCC())){
			
			//sessionOrder.setImsCollectDocDtoList(sessionSummary.getImsCollectDocDTOs());
			sessionOrder.setAllOrdDocAssgnDTOs(sessionSummary.getAllOrdDocAssgnDTOs());
			request.getSession().setAttribute(ImsConstants.IMS_ORDER, sessionOrder);
			
		}
		
		sessionOrder.setNowTVAddUI(sessionSummary.getNowTVAddUI());
		
		this.supportDocService.ims_mark_all_delete(orderId);

		this.supportDocService.insertImsAllOrdDocAssgnDTOALL(sessionSummary.getAllOrdDocAssgnDTOs());
		
		myView.addObject("awaitCash", "Y");
		//add by steven end
		return myView;
		}
	}

	private void generateRpt(HttpServletRequest request,
			OrderImsUI sessionOrder, String langPreference) throws ImsAfException, ImsEmptySignatureException {
		logger.info("saveReportPDF @ ImsReport2ServiceImpl is called");
		ArrayList<Object> arrayList_en = new ArrayList<Object>();
		ArrayList<Object> arrayList_zh = new ArrayList<Object>();

		arrayList_en.add(sessionOrder);
		arrayList_zh.add(sessionOrder);
		Boolean isCC = "Y".equalsIgnoreCase(sessionOrder.getIsCC());
		Boolean isPT = "Y".equalsIgnoreCase(sessionOrder.getIsPT());
		//Boolean isDS = "Y".equalsIgnoreCase(sessionOrder.getIsDS());
		Boolean isDS = ((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)?true:false);
		
		ImsRptServicePlanDetailDTO servPlanDto = new ImsRptServicePlanDetailDTO();
		ImsRptServicePlanDetailDTO servPlanDto2 = new ImsRptServicePlanDetailDTO();
		servPlanDto = imsReport2Service.getServPlanDesc(sessionOrder, "en", isPT,isDS);
		servPlanDto2 = imsReport2Service.getServPlanDesc(sessionOrder, "zh_HK", isPT,isDS);
		arrayList_en.add(servPlanDto);
		arrayList_zh.add(servPlanDto2);
		
		
		
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
				
		if (langPreference.equals("en")) {
			service.generatePdfReports(arrayList_en,null,"en", sessionOrder.getOrderId(), null, sessionOrder.getDisMode().toString(), isCC, isPT, isDS,false, false,true);// save not C order PDF without Showing
		} else {
			service.generatePdfReports(arrayList_zh,null,"zh", sessionOrder.getOrderId(), null, sessionOrder.getDisMode().toString(), isCC, isPT, isDS,false, false,true);// save not C order PDF without Showing
		}
		    service.generatePdfReports(arrayList_en,null,"en", sessionOrder.getOrderId(), null, sessionOrder.getDisMode().toString(), isCC, isPT, isDS,true,false);// save C order PDF without Showing
		logger.info("saveReportPDF <end>");
	}
	
	
/*	
	private String getFirstLine(String input) {
		String output;
		
		if (input != null && input.length() > 0) {
			//logger.debug(">> getFirstLine:input ==" + input);
			if (input.indexOf(((char)10)+"") == -1) {
				output = input;
			} else {
				output = input.substring(0, input.indexOf(((char)10)+""));
			}
			//logger.debug(">> getFirstLine:output==" + output);
		} else {
			output = "";
		}
		
		return output;	
	}
*/
/*  
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	System.out.println("ImsSummaryController .handleRequest");
    	BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	if (user==null){
    		user = new BomSalesUserDTO();
    	}
    	logger.info("userId:" + user.getUsername());
    	
        return new ModelAndView("imssummary", "userId", user.getUsername());
    }
*/
	
// add by steven
	private List<AllOrdDocAssgnDTO> retrieveAllOrdDocAssgnDTOs(HttpServletRequest request, String CashFsPrepay) {
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		//List<AllDocDTO> allDocDTOs = supportDocService.getAllDocDTOByType("IMS",Type.I);
		List<AllDocDTO> allDocDTOs = supportDocService.getAllDocDTOByTypeAndAppDate("IMS",Type.I,sessionOrder.getAppDate());
		
		if (this.isEmpty(allDocDTOs)) {
			return Collections.emptyList();
		}
		List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs = new ArrayList<AllOrdDocAssgnDTO>();
		for (AllDocDTO dto : allDocDTOs) {
			boolean required = false;
			switch (dto.getDocType()) {
				case I001:
					// NETVIGATOR Application Form
					break;
				case I002:
				{	// Credit Card Copy
					if (sessionOrder != null) {// M - Cash, C - Credit Card
						if ("C".equals(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType())) {
							required = true;
						}
					}
			    	if("DS".equals(sessionOrder.getImsOrderType())){required = false;}
					break;
				}
				case I003:
				{	// Cash Memo
					if (sessionOrder != null) {// M - Cash, C - Credit Card
						if ("M".equals(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType())&&!"Y".equals(sessionOrder.getWaivedPrepay())) {
							required = true;
						}
					}
					if("Y".equals(CashFsPrepay)){
						required = false;
					}
			    	if("DS".equals(sessionOrder.getImsOrderType())){required = false;}
					break;
				}
				case I004:
				{	//Signed Application Form
					//if distribution mode = paper
						required = true;
				    	if("DS".equals(sessionOrder.getImsOrderType())){required = false;}
					break;
				}
				case I007:
				{	//HK ID, for Direct Sales, MisMatch WQ
					//if MisMatch = true
					
					String CustProfileMissMatch = "F";
					CustProfileMissMatch = sessionOrder.getCustomer().getExistingCustomerConflictWithName();		
					if("Y".equals(CustProfileMissMatch)&& "DS".equals(sessionOrder.getImsOrderType())){
						request.getSession().setAttribute(ImsConstants.IMS_DS_MISMATCH_HKID,true);
						required = true;
					}
					break;
				}
				case I008:
				{	// IMS - NTV Bill/ Statement
					required = false;
					break;
				}
				case I009: // IMS - Staff Verification (NTV-A)
				case I010: // IMS - Corporate Staff Verification (NTV-A)
				{
					required = false;
					break;
				}
			}
			if (required) {
	    		Date now = new Date();
	    		BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
				AllOrdDocAssgnDTO allOrdDocAssgnDTO = new AllOrdDocAssgnDTO();
				allOrdDocAssgnDTO.setOrderId(sessionOrder.getOrderId());
				int number_of_collected_doc = ordDocService.getImsNumberOfCollectedDoc(sessionOrder.getOrderId(), dto.getDocType().toString());
				if(number_of_collected_doc>0){
					allOrdDocAssgnDTO.setCollectedInd(CollectedInd.Y);
				}else{
					allOrdDocAssgnDTO.setCollectedInd(CollectedInd.N);
				}				
				allOrdDocAssgnDTO.setCreateBy(salesUserDto.getUsername());
				allOrdDocAssgnDTO.setCreateDate(now);
				allOrdDocAssgnDTO.setLastUpdBy(salesUserDto.getUsername());
				allOrdDocAssgnDTO.setLastUpdDate(now);
				allOrdDocAssgnDTO.setDocType(dto.getDocType());
				allOrdDocAssgnDTO.setDocName(dto.getDocName());
				allOrdDocAssgnDTOs.add(allOrdDocAssgnDTO);
			}
		}
		return allOrdDocAssgnDTOs;
	}
	
	private List<AllOrdDocAssgnDTO> retrieveCCAllOrdDocAssgnDTOs(HttpServletRequest request) {
		logger.info("In retrieveCCAllOrdDocAssgnDTOs @ ImsSummrayController");
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		
		List<AllDocDTO> allDocDTOs = supportDocService.getAllDocDTOByTypeAndAppDate("IMS",Type.I,sessionOrder.getAppDate());
		logger.info("Third party credit card: "+sessionOrder.getCustomer().getAccount().getPayment().getThirdPartyInd());
		logger.info("Business registration: "+sessionOrder.getCustomer().getIdDocType());
		if (this.isEmpty(allDocDTOs)) {
			return Collections.emptyList();
		}
		logger.info("Support Doc size: "+allDocDTOs.size());
		List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs = new ArrayList<AllOrdDocAssgnDTO>();
		for (AllDocDTO dto : allDocDTOs) {
			boolean required = false;
			switch (dto.getDocType()) {
				case I005:
				{	// Third Party Credit Card
					if ("Y".equalsIgnoreCase(sessionOrder.getCustomer().getAccount().getPayment().getThirdPartyInd())){
						required = true;
					}
					break;
				}
				case I006:
				{	// Business Registration
					if ("BS".equalsIgnoreCase(sessionOrder.getCustomer().getIdDocType())){
						required = true;
					}
					break;
				}
				case I008:
				{	// IMS - NTV Bill/ Statement
					required = false;
					break;
				}
				case I009: // IMS - Staff Verification (NTV-A)
				case I010: // IMS - Corporate Staff Verification (NTV-A)
				{
					required = false;
					break;
				}
			}
			
			if (required) {
	    		Date now = new Date();
	    		BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
				AllOrdDocAssgnDTO allOrdDocAssgnDTO = new AllOrdDocAssgnDTO();
				allOrdDocAssgnDTO.setOrderId(sessionOrder.getOrderId());
				int number_of_collected_doc = ordDocService.getImsNumberOfCollectedDoc(sessionOrder.getOrderId(), dto.getDocType().toString());
				if(number_of_collected_doc>0){
					allOrdDocAssgnDTO.setCollectedInd(CollectedInd.Y);
				}else{
					allOrdDocAssgnDTO.setCollectedInd(CollectedInd.N);
				}
				allOrdDocAssgnDTO.setCreateBy(salesUserDto.getUsername());
				allOrdDocAssgnDTO.setCreateDate(now);
				allOrdDocAssgnDTO.setLastUpdBy(salesUserDto.getUsername());
				allOrdDocAssgnDTO.setLastUpdDate(now);
				allOrdDocAssgnDTO.setDocType(dto.getDocType());
				allOrdDocAssgnDTO.setDocName(dto.getDocName());
				allOrdDocAssgnDTOs.add(allOrdDocAssgnDTO);
				logger.info("docname"+dto.getDocName());
			}
		}
		return allOrdDocAssgnDTOs;
	}

	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	public SupportDocService getSupportDocService() {
		return supportDocService;
	}
	
	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}//add by steven end
	
	public SupportDocAdminService getSupportDocAdminService() {
		return supportDocAdminService;
	}
	
	public void setSupportDocAdminService(
			SupportDocAdminService supportDocAdminService) {
		this.supportDocAdminService = supportDocAdminService;
	}
	
	public Map<String, Object> referenceData(HttpServletRequest request) {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		//eSignature CR by Eric Ng, for iPad Proof Capture
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		logger.info("sessionId:"+sessionId);
		referenceData.put( "currentSessionId", sessionId);//for app
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		referenceData.put( "salesUserDto", salesUserDto);//for app
		List<String> imsDocTypeList = this.imsOrderDetailService.getImsDocTypeList();
		
		Map<DocType, List<ImsAllOrdDocWaiveDTO>> waiveReasonsPaper = new HashMap<DocType, List<ImsAllOrdDocWaiveDTO>>();
		for (DocType docType : DocType.values()) {
			Boolean docNeeded = false;
			for(int i =0;i<imsDocTypeList.size();i++){
				if(imsDocTypeList.get(i).equalsIgnoreCase(docType.toString())){
					docNeeded=true;
				}
			}
			if(docNeeded){
				List<ImsAllOrdDocWaiveDTO> reasons = this.imsOrderDetailService.getWaiveReasonList(docType.toString(), "P");
				if (!this.isEmpty(reasons)) {
					waiveReasonsPaper.put(docType, reasons);
				}
				for (ImsAllOrdDocWaiveDTO dto : reasons) {
					System.out.println("dto.getDEFAULT_WAIVE_IND():"+dto.getDEFAULT_WAIVE_IND()+"\tdto.getDocType():"+dto.getDocType()+"\tdto.getWaiveReason():"+dto.getWaiveReason());
				}
			}
		}
		referenceData.put("waiveReasonsPaper", waiveReasonsPaper);
		
		Map<DocType, List<ImsAllOrdDocWaiveDTO>> waiveReasonsDigital = new HashMap<DocType, List<ImsAllOrdDocWaiveDTO>>();
		for (DocType docType : DocType.values()) {
			Boolean docNeeded = false;
			for(int i =0;i<imsDocTypeList.size();i++){
				if(imsDocTypeList.get(i).equalsIgnoreCase(docType.toString())){
					docNeeded=true;
				}
			}
			if(docNeeded){
				List<ImsAllOrdDocWaiveDTO> reasons = this.imsOrderDetailService.getWaiveReasonList(docType.toString(), "E");
				if (!this.isEmpty(reasons)) {
					waiveReasonsDigital.put(docType, reasons);
				}
				for (ImsAllOrdDocWaiveDTO dto : reasons) {
					System.out.println("dto.getDEFAULT_WAIVE_IND():"+dto.getDEFAULT_WAIVE_IND()+"\tdto.getDocType():"+dto.getDocType()+"\tdto.getWaiveReason():"+dto.getWaiveReason());
				}
			}
		}
		referenceData.put("waiveReasonsDigital", waiveReasonsDigital);
		referenceData.put("waiveQCReason", LookupTableBean.getInstance().getImsWaiveQCLookupMap());
		
		return referenceData;
	}
	
    @Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
    	
    	ImsSummaryUI summary = (ImsSummaryUI)command;
    	
//    	OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
//		
//    	if(sessionOrder.getDisMode()!=null&&"E".equalsIgnoreCase(sessionOrder.getDisMode().toString())){
//    		ImsSignoffDTO customerSign  = (ImsSignoffDTO)request.getSession().getAttribute(ImsConstants.IMS_CUST_SIGN);
//    		if(customerSign==null||customerSign.getSignatureInputStream().available()<=0){
//    			errors.rejectValue("emptySignatureError", "","Customer Signature is empty.");
//    		}
//    	}
    	// ims direct sales only
    	if(!(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)) return;
    	if(summary.getcReasonCd() != null && summary.getSubmitTag().equals("C")) return;
    	
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "esigEmailAddr", "ims.pcd.summary.msg.022");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "SMSno", "ims.pcd.summary.msg.021");
    	
	}
    public void setImsEmailParamHelperDAO(ImsEmailParamHelperDAO imsEmailParamHelperDAO) {
		this.imsEmailParamHelperDAO = imsEmailParamHelperDAO;
	}

	public ImsEmailParamHelperDAO getImsEmailParamHelperDAO() {
		return imsEmailParamHelperDAO;
	}
	/*
    private List<CustomerBasicInfoDTO> getCustomerBasicInfoDTOList(CustomerBasicInfoDTO[] customerBasicInfoDTOs) {
		if (customerBasicInfoDTOs == null || customerBasicInfoDTOs.length == 0) {
			return Collections.emptyList();
		}
		
		List<CustomerBasicInfoDTO> customerBasicInfoDTOList = new ArrayList<CustomerBasicInfoDTO>();
		for (CustomerBasicInfoDTO customerBasicInfoDTO : customerBasicInfoDTOs) {
			CustomerPremierInfoDTO customerPremierInfoDTO = new CustomerPremierInfoDTO();
			BeanUtils.copyProperties(customerBasicInfoDTO, customerPremierInfoDTO);
			try {
				// call remote serivce
				CustTagDTO custTagDTO = null; 
				if (StringUtils.isNotBlank(customerBasicInfoDTO.getIdDocNum())) {
					CustProfileClient.SystemId systemId = CustProfileClient.SystemId.valueOf(customerBasicInfoDTO.getSystemId());
					CustProfileClient.IdDocType idDocType = CustProfileClient.IdDocType.valueOf(customerBasicInfoDTO.getIdDocType());
					custTagDTO = this.custProfileClient.getCustomerTagByIdDoc(systemId, idDocType, customerBasicInfoDTO.getIdDocNum());
				}
				customerPremierInfoDTO.setCustTagDTO(custTagDTO);
			} catch (Exception e) {
				logger.warn("Exception in call remote service", e);
			}
			customerBasicInfoDTOList.add(customerPremierInfoDTO);
		}
		return customerBasicInfoDTOList;
	}
    */
    public class CustomerPremierInfoDTO extends CustomerBasicInfoDTO {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private CustTagDTO custTagDTO;

		public CustTagDTO getCustTagDTO() {
			return custTagDTO;
		}

		public void setCustTagDTO(CustTagDTO custTagDTO) {
			this.custTagDTO = custTagDTO;
		}
	}
	
}
