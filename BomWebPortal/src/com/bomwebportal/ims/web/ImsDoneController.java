package com.bomwebportal.ims.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.AllOrdDocWaiveDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.exception.ImsMobilePinException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.HousingTypeDTO;
import com.bomwebportal.ims.dto.ImsRptBasketDtlDTO;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;
import com.bomwebportal.ims.dto.ImsRptChannelDTO;
import com.bomwebportal.ims.dto.ImsRptCoreServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptGiftDTO;
import com.bomwebportal.ims.dto.ImsRptNtvServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptOptServiceDetailDTO;
import com.bomwebportal.ims.dto.ImsRptServicePlanDetailDTO;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.ImsDoneUI;
import com.bomwebportal.ims.dto.ui.ImsSummaryUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedChannelUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.service.BasketSummaryService;
import com.bomwebportal.ims.service.ImsNowTVService;
import com.bomwebportal.ims.service.ImsOrderDetailService;
import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.ims.service.ImsReport2Service;
import com.bomwebportal.ims.service.ImsSMSService;
import com.bomwebportal.ims.service.ReleaseLoginIDService;
import com.bomwebportal.service.MobilePINService;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.service.SupportDocService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.ext.BomWebPortalApplicationFlow;
import com.bomwebportal.service.MobilePINServiceImpl.MobileOffer;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.google.gson.Gson;
import com.bomwebportal.ims.dto.ui.NowTVAddUI;


public class ImsDoneController extends SimpleFormController{

    protected final Log logger = LogFactory.getLog(getClass());
    private Gson gson = new Gson();
    private MobilePINService mobilePINService;
    private ImsReport2Service imsReport2Service;
    private BasketSummaryService basketSummaryService;
	private SupportDocService supportDocService;
	private ImsOrderDetailService imsOrderDetailService;
	private ImsAppointmentController appntController;
	private ImsOrderService orderService;
	private OrdEmailReqService ordEmailReqService;
	private OrderEsignatureService orderEsignatureService;
    private ImsSMSService imsSMSService;
    private ImsNowTVService imsNowTVService;
    private ReleaseLoginIDService releaseLoginIDService;
    
    
    
    public MobilePINService getMobilePINService() {
		return mobilePINService;
	}

	public void setMobilePINService(MobilePINService mobilePINService) {
		this.mobilePINService = mobilePINService;
	}

	public ReleaseLoginIDService getReleaseLoginIDService() {
		return releaseLoginIDService;
	}

	public void setReleaseLoginIDService(ReleaseLoginIDService releaseLoginIDService) {
		this.releaseLoginIDService = releaseLoginIDService;
	}
	public ImsNowTVService getImsNowTVService() {
		return imsNowTVService;
	}

	public void setImsNowTVService(ImsNowTVService imsNowTVService) {
		this.imsNowTVService = imsNowTVService;
	}

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
	}
	public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}
	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}
	public void setImsSMSService(ImsSMSService imsSMSService) {
		this.imsSMSService = imsSMSService;
	}
	public ImsSMSService getImsSMSService() {
		return imsSMSService;
	}
    public ImsOrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(ImsOrderService orderService) {
		this.orderService = orderService;
	}

	public ImsReport2Service getImsReport2Service() {
		return imsReport2Service;
	}

	public void setImsReport2Service(ImsReport2Service imsReport2Service) {
		this.imsReport2Service = imsReport2Service;
	}

	public BasketSummaryService getBasketSummaryService() {
		return basketSummaryService;
	}

	public void setBasketSummaryService(BasketSummaryService basketSummaryService) {
		this.basketSummaryService = basketSummaryService;
	}
    
    private BomWebPortalApplicationFlow appFlow;
    
	public BomWebPortalApplicationFlow getAppFlow() {
		return appFlow;
	}

	public void setAppFlow(
			BomWebPortalApplicationFlow appFlow) {
		this.appFlow = appFlow;
	}
	
	public ImsOrderDetailService getImsOrderDetailService() {
		return imsOrderDetailService;
	}

	public void setImsOrderDetailService(ImsOrderDetailService imsOrderDetailService) {
		this.imsOrderDetailService = imsOrderDetailService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		logger.info("formBackingObject");
		String Locale = RequestContextUtils.getLocale(request).toString().contains("en")?"EN":"CHI";
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};

		logger.info("ReasonCd:"+sessionOrder.getReasonCd());	
		logger.info("sessionOrder.getImsOrderType():"+sessionOrder.getImsOrderType());	
		if(("R004".equals(sessionOrder.getReasonCd())||"R006".equals(sessionOrder.getReasonCd()))
				&& "DS".equals(sessionOrder.getImsOrderType())){
			request.getSession().setAttribute(ImsConstants.IMS_DS_MISMATCH_HKID,true);
		}else{
			request.getSession().setAttribute(ImsConstants.IMS_DS_MISMATCH_HKID,false);
		}
		
		ImsDoneUI sessionDone = new ImsDoneUI();
		
		

		
		
		ImsSummaryUI basketSummary = new ImsSummaryUI();
		BeanUtils.copyProperties(sessionOrder.getCustomer(), sessionDone);
		sessionDone.setInstallAddress(sessionOrder.getInstallAddress());
		if(sessionOrder.getBillingAddress() != null){
			sessionDone.setBillingAddress(sessionOrder.getBillingAddress());
		}
		sessionDone.setAppointment(sessionOrder.getAppointment());		
		//sessionDone.setAppntStartDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntStartDateDisplay(), "dd/MM/yyyy HH:mm"));
		//sessionDone.setAppntEndDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntEndDateDisplay(), "HH:mm"));
		sessionDone.getAccount().setPayment(sessionOrder.getCustomer().getAccount().getPayment());
		sessionDone.setFixedLineNo(sessionOrder.getFixedLineNo());
		sessionDone.setLoginId(sessionOrder.getLoginId());
		sessionDone.setAppDate(Utility.date2String(sessionOrder.getAppDate(), "dd/MM/yyyy"));
		sessionDone.setTargetCommDate(Utility.date2String(sessionOrder.getTargetCommDate(), "dd/MM/yyyy"));
		sessionDone.setCashFsPrepay(sessionOrder.getCashFsPrepay());
		sessionDone.setPrepayAmt(sessionOrder.getPrepayAmt());
		sessionDone.setReasonCd(sessionOrder.getReasonCd());
		//Gary
		sessionDone.setHktLoginId(sessionOrder.getCustomer().getCsPortalLogin());
		sessionDone.setHktMobileNum(sessionOrder.getCustomer().getCsPortalMobile());
		sessionDone.setOtInstallChrg(sessionOrder.getOtInstallChrg());         //TT
		sessionDone.setInstallOtCode(sessionOrder.getInstallOtCode()); //Gary
		sessionDone.setInstallOTDesc_en(sessionOrder.getInstallOTDesc_en()); //Gary
		sessionDone.setInstallOTDesc_zh(sessionOrder.getInstallOTDesc_zh()); //Gary
		
		
		//nowid
		sessionDone.setIsRegNowId(sessionOrder.getCustomer().getIsRegNowId());
		sessionDone.setNowId(sessionOrder.getCustomer().getNowId());
		
		
		if(sessionOrder.getInstallmentChrg()!=null) 
		{
			if(Integer.parseInt(sessionOrder.getInstallmentChrg())>0){
				sessionDone.setOtInstallChrg(sessionOrder.getInstallmentChrg()+" X "+sessionOrder.getInstallmentMonth()+" months");
			}else{
				sessionDone.setOtInstallChrg(sessionOrder.getOtInstallChrg());         //TT
			
			}
		}else{
			sessionDone.setOtInstallChrg(sessionOrder.getOtInstallChrg());         //TT
		}
		
		String locale = RequestContextUtils.getLocale(request).toString();
		
		String OtInstallChrg = sessionDone.getOtInstallChrg();
		String compForOtInstallChrg = "";
		if("en".equalsIgnoreCase(locale)){
			compForOtInstallChrg = sessionDone.getInstallOTDesc_en();
		}else{
			compForOtInstallChrg = sessionDone.getInstallOTDesc_zh();
		}
		if(compForOtInstallChrg==null||compForOtInstallChrg.isEmpty()){
			compForOtInstallChrg = "";
		}
		if(OtInstallChrg==null||OtInstallChrg.isEmpty()){
			//mass project WAIVED case
			sessionDone.setWaivedOtInstallChrg("Waived");
		}else{
		if(OtInstallChrg!=null && OtInstallChrg.startsWith("-")){
			sessionDone.setWaivedOtInstallChrg("$"+OtInstallChrg.substring(1)+compForOtInstallChrg+"(Waived)");
				if(sessionOrder.getServiceWaiver()!=null){
					if("B".equalsIgnoreCase(sessionOrder.getServiceWaiver())){
						sessionDone.setWaivedOtInstallChrg("$"+OtInstallChrg.substring(1)+compForOtInstallChrg+"(Waived)");
					}
					if("V".equalsIgnoreCase(sessionOrder.getServiceWaiver())){
						sessionDone.setWaivedOtInstallChrg("$"+OtInstallChrg.substring(1)+compForOtInstallChrg+"(Waived)");
					}
					if("G".equalsIgnoreCase(sessionOrder.getServiceWaiver())){
						sessionDone.setWaivedOtInstallChrg("$"+OtInstallChrg.substring(1)+compForOtInstallChrg+"(Waived)");
					}
				}
				if(sessionOrder.getServiceWaiverNowTVPage()!=null){
					if("G".equalsIgnoreCase(sessionOrder.getServiceWaiver())){
						sessionDone.setWaivedOtInstallChrg("$"+OtInstallChrg.substring(1)+compForOtInstallChrg+"(Waived)");
					}
				}
			}else{
				sessionDone.setWaivedOtInstallChrg("$"+OtInstallChrg+compForOtInstallChrg);
			}
		}
		sessionDone.setDobStr(Utility.date2String(sessionDone.getDob(), "dd/MM/yyyy"));
		sessionDone.setOrderId(sessionOrder.getOrderId());
		sessionDone.setSignOffDate(Utility.date2String(sessionOrder.getSignOffDate(), "dd/MM/yyyy HH:mm:ss"));
		sessionDone.setSubmitDate(Utility.date2String(sessionOrder.getSubmitDate(), "dd/MM/yyyy HH:mm:ss"));
		
		//kinman
		sessionDone.setDisMode(sessionOrder.getDisMode());
		sessionDone.setCollectMethod(sessionOrder.getCollectMethod());
		sessionDone.setLangPreference(sessionOrder.getLangPreference());
		sessionDone.setEsigEmailLang(sessionOrder.getEsigEmailLang());
		sessionDone.setEsigEmailAddr(sessionOrder.getEsigEmailAddr());
		sessionDone.setEmailAddr(sessionOrder.getEmailAddress());
		sessionDone.setSMSno(sessionOrder.getSMSno());
		
		sessionDone.setIsCC(user.getChannelId()==2||user.getChannelId()==3?"Y":"N");
		sessionDone.setIsPT(user.getChannelId()==3?"Y":"N");
		sessionDone.setCallDate(Utility.date2String(sessionOrder.getCallDate(), "dd/MM/yyyy"));
		sessionDone.setCallTime(Utility.date2String(sessionOrder.getCallDate(), "HH:mm"));
		sessionDone.setPositionNo(sessionOrder.getPositionNo());
		sessionDone.setOrderStatus(sessionOrder.getOrderStatus());
		
		System.out.println("sessionOrder.getDisMode() is: "+sessionOrder.getDisMode());	
		System.out.println("sessionOrder.getCollectMethod() is: "+sessionOrder.getCollectMethod());	
		System.out.println("sessionOrder.getLangPreference() is: "+sessionOrder.getLangPreference());	
		System.out.println("sessionOrder.getEsigEmailLang() is: "+sessionOrder.getEsigEmailLang());	
		System.out.println("sessionOrder.getEsigEmailAddr() is: "+sessionOrder.getEsigEmailAddr());	
		System.out.println("sessionOrder.getOrderStatus() is: "+sessionOrder.getOrderStatus());	
		
		
		//raymond added
		sessionDone.setPendingOrder(sessionOrder.isPending());
		sessionDone.setSignoffedOrder(sessionOrder.isSignoffed());
		sessionDone.setNoBooking(sessionOrder.isBookingNotAllowed());
		
		if(sessionDone.getNoBooking().equals("Y")){
			sessionDone.setAppntStartDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntStartDateDisplay(), "dd/MM/yyyy"));
			//sessionDone.setAppntEndDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntEndDateDisplay(), "HH:mm"));
		}else{
			sessionDone.setAppntStartDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntStartDateDisplay(), "dd/MM/yyyy HH:mm"));
			sessionDone.setAppntEndDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntEndDateDisplay(), "HH:mm"));
		}
		
//************************* Antony Refernce *************************
		sessionDone.setWarrPeriod(sessionOrder.getWarrPeriod());
		
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
		List<ImsRptGiftDTO> giftList = new ArrayList<ImsRptGiftDTO>();
		
		String basketId = "";
		String itemIdStr = "";
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
		
		logger.debug("channelIdStr: " + channelIdStr);
		
		
		ImsRptBasketDtlDTO basketDtl = new ImsRptBasketDtlDTO();
		List<ImsRptBasketItemDTO> basketItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> optServiceList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptBasketItemDTO> ntvPgmItemList = new ArrayList<ImsRptBasketItemDTO>();
		List<ImsRptChannelDTO> channelFreeList = new ArrayList<ImsRptChannelDTO>();
		List<ImsRptChannelDTO> channelPayList = new ArrayList<ImsRptChannelDTO>();
		
		basketDtl = imsReport2Service.getBasketDetail(basketId, locale);
		logger.debug("\nBasket detail=" + basketDtl);
		sessionDone.setBasketDtl(basketDtl);
		
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
			
			sessionOrder.setIsCC(user.getChannelId()==2||user.getChannelId()==3?"Y":"N"); //tmp jacky 20141113
			sessionOrder.setIsPT(user.getChannelId()==3?"Y":"N");
			
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
					&&!basketItemList.get(i).getType().equalsIgnoreCase("OTHER")&&basketDtl.getCanSubcOptSrv().equalsIgnoreCase("Y"))  {
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
		//optServiceDtl.setWlBbItemList(wlBbItemList);
		//optServiceDtl.setAntiVirItemList(antiVirItemList);
		optServiceDtl.setOptServItemList(optServItemList);
		optServiceDtl.setMediaItemList(mediaItemList);
		servPlanDto.setOptServiceDetail(optServiceDtl);
		
		//Gift Tony 20140904
		
		if (giftList!=null&&giftList.size()>0){
			servPlanDto.setGiftList(giftList);
		}
		
		// Now TV
		if(ntvPgmItemList == null){
			sessionDone.setNtvPgmItemList(null);
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

		sessionDone.setServPlanDto(servPlanDto);
		sessionDone.setTotalRecurrentAmt(totalRecurrentAmt);
		sessionDone.setTotalMthToMthRate(totalMthToMthRate);
		sessionDone.setOtChrgType(sessionOrder.getOtChrgType());
		sessionDone.setFAXno(sessionOrder.getFAXno());
		
		//Tony
		String imsSubmitTag = "";
		if(request.getSession().getAttribute("imsSubmitTag") != null){
			imsSubmitTag = (String)request.getSession().getAttribute("imsSubmitTag");
		}
		if(imsSubmitTag.equals("C")){
			String imsOrderId = (String)request.getSession().getAttribute("imsOrderId");
			sessionDone.setOrderId(imsOrderId);
			logger.info("Order ID: " + imsOrderId);
			request.getSession().setAttribute("imsOrderId", null);
		}
		if(sessionOrder.getReasonCd() != null && sessionOrder.getReasonCd().substring(0, 1).equals("C")){
			sessionDone.setcReasonCd(sessionOrder.getReasonCd());
			sessionDone.setCancelReason(sessionOrder.getReasonCd());
		}
		sessionDone.setSubmitTag((String)request.getSession().getAttribute("imsSubmitTag"));
		Entry<String, String>[] clist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsCancelLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			clist = imsOrderDetailService.getLookUpMapByLocale(LookupTableBean.getInstance().getImsDSCancelLookupMap(),Locale).entrySet().toArray(new Entry[0]);
		}
		for(int i=0; i<clist.length; i++){
			System.out.println(clist[i].getKey()+":"+clist[i].getValue());
		}
		request.getSession().setAttribute("cancelList", clist);
		
		if ("Y".equals(sessionOrder.getTvPriceInd())) {
			sessionDone.getServPlanDto().getNtvServiceDetail().setNtvFreeHDChannelList(null);
			sessionDone.getServPlanDto().getNtvServiceDetail().setNtvFreeEPChannelList(null);
			sessionDone.getServPlanDto().getNtvServiceDetail().setNtvFreeItemList(null);
					
			NowTVAddUI addui = imsNowTVService.getNewTVPricingDtl(sessionOrder, locale, false, sessionOrder.getOrderId());
			sessionDone.setNowTVAddUI(addui);
			sessionDone.setNewtvpricingind("Y");
		} else {
			sessionDone.setNewtvpricingind("N");
		}
		
		/// ims direct sales only
		if("DS".equals(sessionOrder.getImsOrderType())){
		logger.debug("orderObj : " + new Gson().toJson(sessionOrder));
		if(sessionDone.getInstallAddress().getHousingTypeList()==null){
			logger.debug("sessionDone.getInstallAddress().getHousingTypeList() is null"); 
			HousingTypeDTO h = new HousingTypeDTO(); 
			h.setHousingType(sessionOrder.getInstallAddress().getAddrInventory().getTechnology());
			List<HousingTypeDTO> l = new ArrayList<HousingTypeDTO>();
			l.add(h);
			sessionDone.getInstallAddress().setHousingTypeList(l);
		}
		sessionDone.setAppntUI((AppointmentUI) appntController.formBackingObject(request));
		try {
			//Date d1 = new SimpleDateFormat("yyyy/MM/dd").parse(sessionDone.getAppntUI().getTargetInstallDate());
			Date d1 = new SimpleDateFormat("yyyy/MM/dd").parse(sessionOrder.getAppointment().getTargetInstallDate());
			Date d2 = new SimpleDateFormat("yyyy/MM/dd").parse(sessionDone.getAppntUI().getEstimatedSrd());
			logger.debug("d1" + d1);
			logger.debug("d2" + d2);
			logger.debug("d1.before(d2)" + d1.before(d2));
			if (d1.before(d2)) {
				sessionDone.setSrdAvailble("N");
				sessionDone.setSerialNum("");
			} else {
				sessionDone.setSrdAvailble("Y");
			}
		} catch (Exception e) {
			sessionDone.setSrdAvailble("Y");
		}
		
		//for qc process detail re-select srd
		request.getSession().setAttribute("srdAvailble", sessionDone.getSrdAvailble());
		
		logger.debug(new Gson().toJson(sessionDone));
		
		if(!StringUtils.isEmpty(sessionOrder.getOcId())) sessionDone.setL1ordnum(orderService.getL1OrderNum(sessionOrder.getOrderId()));
		}
		/// ims direct sales only(end)
		
		logger.info("sessionDone");
		List<AllOrdDocAssgnDTO> recordsFromDB = this.supportDocService.getInUseAllOrdDocAssgnDTOALL(sessionOrder.getOrderId()); 
	    if (!this.isEmpty(recordsFromDB)) {
	    	sessionDone.setAllOrdDocAssgnDTOs(recordsFromDB);
			logger.info("In done controller, required doc list exists in db ");
		}else{
			logger.info("In done controller, required doc list NOT exists in db ");
		}
	    sessionDone.setImsCollectDocDTOs(sessionOrder.getImsCollectDocDtoList());
	    sessionDone.setEdfRef(sessionOrder.getEdfRef());

		if(sessionDone.getOptoutTheClubReason()!=null&&sessionDone.getOptoutTheClubReason().length()>0){
			sessionDone.setClubOptOutReasonDesc(LookupTableBean.getInstance().getImsClubOptoutLookupMap().get(sessionDone.getOptoutTheClubReason()));
		}
	    
		return sessionDone;
	}	
	
	public Map<String, Object> referenceData(HttpServletRequest request) {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		//eSignature CR by Eric Ng, for iPad Proof Capture
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		logger.info("sessionId:"+sessionId);
		referenceData.put( "currentSessionId", sessionId);//for app
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		referenceData.put( "salesUserDto", salesUserDto);//for app

		//BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		Map<DocType, List<AllOrdDocWaiveDTO>> waiveReasons = new HashMap<DocType, List<AllOrdDocWaiveDTO>>();
		for (DocType docType : DocType.values()) {
			List<AllOrdDocWaiveDTO> reasons = this.supportDocService.getAllOrdDocWaiveDTOALL("IMS", docType);
			if (!this.isEmpty(reasons)) {
				waiveReasons.put(docType, reasons);
			}
		}
		referenceData.put("waiveReasons", waiveReasons);
		
		String emailReqResult = request.getParameter("emailReqResult");
		referenceData.put("emailReqResult", emailReqResult);
		
		String emailReqResultMsg = request.getParameter("emailReqResultMsg");
		referenceData.put("emailReqResultMsg", emailReqResultMsg);
		
		referenceData.put("awaitCash", request.getParameter("awaitCash"));
		referenceData.put("awaitSignOff", request.getParameter("awaitSignOff"));
		return referenceData;
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
    	System.out.println("ImsDoneController .handleRequest");
    	BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	if (user==null){
    		user = new BomSalesUserDTO();
    	}
    	logger.info("userId:" + user.getUsername());
    	
        return new ModelAndView("imsdone", "userId", user.getUsername());
    }
*/

	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	public SupportDocService getSupportDocService() {
		return supportDocService;
	}

	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}
	
    @Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
    	ImsDoneUI sessionDone = (ImsDoneUI)command;
    	if(!(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)) throw new Exception();
    	else {
    		if(sessionDone.getcReasonCd() != null && !("").equals(sessionDone.getcReasonCd())
    				&& sessionDone.getSubmitTag().equals("C")){
        		OrderImsUI order = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
    			int releaseResult = -99;
    			releaseResult = releaseLoginIDService.releaseLoginID(order.getLoginId(), order.getCustomer().getIdDocNum(), order.getCustomer().getIdDocType());
    			logger.info(releaseResult);
    			imsOrderDetailService.cancelOrder(order, sessionDone.getcReasonCd());
    			/*
    			MobileOffer imsMobileOfferUi = new MobileOffer();
    			OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);	
    			if(request.getSession().getAttribute("imsMobileOfferUi") != null){
    				logger.info("request.getSession().getAttribute"+"(imsMobileOfferUi):"+  gson.toJson(request.getSession().getAttribute("imsMobileOfferUi")));
    				
    				imsMobileOfferUi = (MobileOffer) request.getSession().getAttribute("imsMobileOfferUi");
    				if(sessionOrder != null && !("").equals(sessionOrder)){
    					imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUi.mrt, imsMobileOfferUi.pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
    					logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);
    				}else if((imsMobileOfferUi.mrt != null && !("").equals(imsMobileOfferUi.mrt)) && (imsMobileOfferUi.pin != null && !("").equals(imsMobileOfferUi.pin))){
    					imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUi.mrt, imsMobileOfferUi.pin);
    					logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);
    				}
    				
    				request.getSession().setAttribute("imsMobileOfferUiList", null);
    			}
    			else*/
    			//OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
    			
    			
    			
    			if(order.getSubscribedCslOffers() != null){
    				SubscribedCslOfferDTO[] csldto = order.getSubscribedCslOffers(); 
    				if(order != null && !("").equals(order)){
    					for(SubscribedCslOfferDTO i:csldto){
    						if(i.getVas_parm_a()!= null && !("").equals(i.getVas_parm_a()) && i.getVas_parm_b()!= null && !("").equals(i.getVas_parm_b())){
    							try{
    								MobileOffer imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(i.getVas_parm_a(), i.getVas_parm_b(), order.getCreateBy(), order.getOrderId(), "NONONLINE");
    								logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);
    							}catch(Exception e){
    								logger.error("Exception caught in releaseMobilePIN()", e);
    								throw new ImsMobilePinException();
    							}
    															
    						}
    					}
    				}else{
    					for(SubscribedCslOfferDTO i:csldto){
    						if(i.getVas_parm_a()!= null && !("").equals(i.getVas_parm_a()) && i.getVas_parm_b()!= null && !("").equals(i.getVas_parm_b())){
    							try{
    								MobileOffer imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(i.getVas_parm_a(), i.getVas_parm_b());
    								logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);
    							}catch(Exception e){
    								logger.error("Exception caught in releaseMobilePIN()", e);
    								throw new ImsMobilePinException();
    							}
    								
    						}
    					}
    				}
    				
    				
    				
    			}
    			
    			request.getSession().setAttribute("imsSubmitTag", sessionDone.getSubmitTag());
    			request.getSession().setAttribute("imsOrderId", sessionDone.getOrderId());
    			request.getSession().setAttribute("imsLoginIdUi", null);
    			request.getSession().setAttribute(ImsConstants.IMS_APPOINTMENT_SERIAL, null);
    			request.getSession().setAttribute("imsMobileOfferUiList", null);
    			return new ModelAndView(new RedirectView("imsdone.html"));
    		}else{
	    		OrderImsUI order = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
	    		logger.info("onsubmit direct sales : ");
	    		if("N".equals(((ImsDoneUI)command).getSrdAvailble())) {
	    			logger.info("onsubmit direct sales : getSrdAvailble N");
	    			this.appntController.onSubmit(request, response, ((ImsDoneUI)command).getAppntUI(), errors);   	
		    		if("Y".equalsIgnoreCase(order.getPreInstallInd())||"Y".equalsIgnoreCase(order.getPreUseInd())){
		    			DateFormat dft = new SimpleDateFormat("yyyy/MM/dd");
		    			Date targetCommDate = dft.parse(sessionDone.getTargetCommDate());
		    			order.setTargetCommDate(targetCommDate);
		    		}
	    			//celia
	        		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
	    			logger.info("return ims order list : " + new Gson().toJson((ImsDoneUI)command)); 
	    		    if (bomSalesUserDTO.getChannelId()==12){
	    		    	orderEsignatureService.createEmailReq(((ImsDoneUI)command).getOrderId(), "AMNT001",new Date(), null , null, null , ((ImsDoneUI)command).getEmailAddr(), bomSalesUserDTO.getUsername() );
	    				List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(((ImsDoneUI)command).getOrderId(),"AMNT001");
	    		    if (list.size()!=0)	    
	    		    	imsSMSService.createSMSReq(((ImsDoneUI)command).getOrderId(), new Date(), ((ImsDoneUI)command).getSMSno(),  bomSalesUserDTO.getUsername() , "AMNT002", list.get(list.size()-1).getSeqNo());    	   
	    		    else 
	    				imsSMSService.createSMSReq(((ImsDoneUI)command).getOrderId(), new Date(), ((ImsDoneUI)command).getSMSno(),  bomSalesUserDTO.getUsername() , "AMNT002", 1);
	    		    }else if (bomSalesUserDTO.getChannelId()==13){
	    				orderEsignatureService.createEmailReq(((ImsDoneUI)command).getOrderId(), "AMNT003",new Date(), null , null, null , ((ImsDoneUI)command).getEmailAddr(), bomSalesUserDTO.getUsername() );
	    				List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOrderId(((ImsDoneUI)command).getOrderId(),"AMNT003");
	    			    if (list.size()!=0)	    
	    			    	imsSMSService.createSMSReq(((ImsDoneUI)command).getOrderId(), new Date(), ((ImsDoneUI)command).getSMSno(),  bomSalesUserDTO.getUsername() , "AMNT004", list.get(list.size()-1).getSeqNo());    	   
	    			    else 
	    					imsSMSService.createSMSReq(((ImsDoneUI)command).getOrderId(), new Date(), ((ImsDoneUI)command).getSMSno(),  bomSalesUserDTO.getUsername() , "AMNT004", 1);
	    		    	}
	    		} 
	    		order.setSubmitDate(orderService.getApplicationDate());
	    		order.setShopCode(((ImsDoneUI)command).getShopCode());
	    		order.setTranCode(((ImsDoneUI)command).getTransactionCd());
	    		if (order.getCustomer().getCustNo()!=null){
	    			if (order.getCustomer().getCustNo().length()>0)
	    				order.setIsNewCust("N");
	    			else order.setIsNewCust("Y");
	    		}else order.setIsNewCust("Y");
	    		
	    		imsOrderDetailService.signOffOrder(order);
	    	
	    		return new ModelAndView(new RedirectView("imsdone.html"));
    		}
    	}
		
	}

	public void setAppntController(ImsAppointmentController appntController) {
		this.appntController = appntController;
	}

	public ImsAppointmentController getAppntController() {
		return appntController;
	}
}
