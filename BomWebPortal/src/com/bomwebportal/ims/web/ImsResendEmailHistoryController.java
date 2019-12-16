package com.bomwebportal.ims.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import org.springframework.web.servlet.view.RedirectView;


import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.EmailTemplateDTO;
import com.bomwebportal.dto.OrdEmailReqDTO;

import com.bomwebportal.dto.OrderDTO.DisMode;

import com.bomwebportal.ims.constant.ImsConstants;


import com.bomwebportal.ims.dto.ui.ImsResendEmailHistoryUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;

import com.bomwebportal.ims.service.BasketSummaryService;
import com.bomwebportal.ims.service.ImsOLOrderSearchService;
import com.bomwebportal.ims.service.ImsReport2Service;

import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.service.SupportDocService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.ext.BomWebPortalApplicationFlow;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;

public class ImsResendEmailHistoryController extends SimpleFormController{

    protected final Log logger = LogFactory.getLog(getClass());

    private ImsReport2Service imsReport2Service;
    private BasketSummaryService basketSummaryService;
	private SupportDocService supportDocService;
	private OrdEmailReqService ordEmailReqService;
	private OrderEsignatureService orderEsignatureService;
	
	private ImsOLOrderSearchService imsOlOrderSearchService;
	
	public ImsOLOrderSearchService getImsOlOrderSearchService() {
		return imsOlOrderSearchService;
	}

	public void setImsOlOrderSearchService(
			ImsOLOrderSearchService imsOlOrderSearchService) {
		this.imsOlOrderSearchService = imsOlOrderSearchService;
	}
	
	public SupportDocService getSupportDocService() {
		return supportDocService;
	}

	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}
    
    public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}

	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
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
	

	public Object formBackingObject(HttpServletRequest request) throws ServletException{
	
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		
		ImsResendEmailHistoryUI form = new ImsResendEmailHistoryUI();
		
		BeanUtils.copyProperties(sessionOrder.getCustomer(), form);
//		form.setInstallAddress(sessionOrder.getInstallAddress());
//		if(sessionOrder.getBillingAddress() != null){
//			form.setBillingAddress(sessionOrder.getBillingAddress());
//		}
//		form.setAppointment(sessionOrder.getAppointment());		
//		//form.setAppntStartDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntStartDateDisplay(), "dd/MM/yyyy HH:mm"));
//		//form.setAppntEndDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntEndDateDisplay(), "HH:mm"));
//		form.getAccount().setPayment(sessionOrder.getCustomer().getAccount().getPayment());
//		form.setFixedLineNo(sessionOrder.getFixedLineNo());
//		form.setLoginId(sessionOrder.getLoginId());
//		form.setAppDate(Utility.date2String(sessionOrder.getAppDate(), "dd/MM/yyyy"));
//		form.setTargetCommDate(Utility.date2String(sessionOrder.getTargetCommDate(), "dd/MM/yyyy"));
//		form.setCashFsPrepay(sessionOrder.getCashFsPrepay());
//		form.setPrepayAmt(sessionOrder.getPrepayAmt());
//		
//		if(sessionOrder.getInstallmentChrg()!=null) 
//		{
//			if(Integer.parseInt(sessionOrder.getInstallmentChrg())>0){
//				form.setOtInstallChrg(sessionOrder.getInstallmentChrg()+" X "+sessionOrder.getInstallmentMonth()+" months");
//			}else{
//				form.setOtInstallChrg(sessionOrder.getOtInstallChrg());         //TT
//			
//			}
//		}else{
//			form.setOtInstallChrg(sessionOrder.getOtInstallChrg());         //TT
//		}
//		
//		String OtInstallChrg = form.getOtInstallChrg();
//		if(OtInstallChrg.startsWith("-")){
//			form.setWaivedOtInstallChrg(OtInstallChrg.substring(1)+"(Waived)");
//		}else{
//			form.setWaivedOtInstallChrg(OtInstallChrg);
//		}
//		form.setDobStr(Utility.date2String(form.getDob(), "dd/MM/yyyy"));
		form.setOrderId(sessionOrder.getOrderId());
		form.setSignOffDate(Utility.date2String(sessionOrder.getSignOffDate(), "dd/MM/yyyy HH:mm:ss"));
		
		//kinman
		form.setDisMode(sessionOrder.getDisMode());
		form.setLangPreference(sessionOrder.getLangPreference());
		form.setEsigEmailLang(sessionOrder.getEsigEmailLang());
		form.setEsigEmailAddr(sessionOrder.getEsigEmailAddr());
		
		logger.info("Eric Ng EsigEmailAddr: " + sessionOrder.getEsigEmailAddr());
		
		//raymond added
		form.setPendingOrder(sessionOrder.isPending());
		form.setSignoffedOrder(sessionOrder.isSignoffed());
		form.setNoBooking(sessionOrder.isBookingNotAllowed());
		
//		if(form.getNoBooking().equals("Y")){
//			form.setAppntStartDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntStartDateDisplay(), "dd/MM/yyyy"));
//			//form.setAppntEndDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntEndDateDisplay(), "HH:mm"));
//		}else{
//			form.setAppntStartDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntStartDateDisplay(), "dd/MM/yyyy HH:mm"));
//			form.setAppntEndDateDisplay(Utility.date2String(sessionOrder.getAppointment().getAppntEndDateDisplay(), "HH:mm"));
//		}
//		
////************************* Antony Refernce *************************
//		form.setWarrPeriod(sessionOrder.getWarrPeriod());
//		
//		ImsRptServicePlanDetailDTO servPlanDto = new ImsRptServicePlanDetailDTO();
//		
//		ImsRptCoreServiceDetailDTO coreServiceDtl = new ImsRptCoreServiceDetailDTO();
//		ImsRptBasketItemDTO progItem = new ImsRptBasketItemDTO();
//		List<ImsRptBasketItemDTO> bvasMandItemList = new ArrayList<ImsRptBasketItemDTO>();
//		List<ImsRptBasketItemDTO> bvasNonMItemList = new ArrayList<ImsRptBasketItemDTO>();
//		List<ImsRptBasketItemDTO> preInstItemList = new ArrayList<ImsRptBasketItemDTO>();
//		
//		ImsRptOptServiceDetailDTO optServiceDtl = new ImsRptOptServiceDetailDTO();
//		List<ImsRptBasketItemDTO> optPremItemList = new ArrayList<ImsRptBasketItemDTO>();
//		List<ImsRptBasketItemDTO> optServItemList = new ArrayList<ImsRptBasketItemDTO>();
//		//List<ImsRptBasketItemDTO> wlBbItemList = new ArrayList<ImsRptBasketItemDTO>();
//		//List<ImsRptBasketItemDTO> antiVirItemList = new ArrayList<ImsRptBasketItemDTO>();
//		List<ImsRptBasketItemDTO> mediaItemList = new ArrayList<ImsRptBasketItemDTO>();
//
//		ImsRptNtvServiceDetailDTO ntvServiceDtl = new ImsRptNtvServiceDetailDTO();
//		List<ImsRptBasketItemDTO> ntvFreeItemList = new ArrayList<ImsRptBasketItemDTO>();
//		List<ImsRptBasketItemDTO> ntvPayItemList = new ArrayList<ImsRptBasketItemDTO>();
//		List<ImsRptBasketItemDTO> ntvOtherItemList = new ArrayList<ImsRptBasketItemDTO>();
//		List<ImsRptChannelDTO> ntvFreeSPChannelList = new ArrayList<ImsRptChannelDTO>();
//		List<ImsRptChannelDTO> ntvFreeEPChannelList = new ArrayList<ImsRptChannelDTO>();
//		List<ImsRptChannelDTO> ntvFreeHDChannelList = new ArrayList<ImsRptChannelDTO>();
//		List<ImsRptChannelDTO> ntvPayChannelList = new ArrayList<ImsRptChannelDTO>();
//		
//		String basketId = "";
//		String itemIdStr = "";
//		String locale = RequestContextUtils.getLocale(request).toString();
//		SubscribedItemUI[] basket = sessionOrder.getSubscribedItems();
//		for(int i=0; i < basket.length; i++){
//			logger.debug(">>>basket " + i + " ID: " + basket[i].getBasketId());
//			if(basket[i].getBasketId() != null && basket[i].getBasketId() != ""){
//				basketId = basket[i].getBasketId();
//			}
//			if(i==0){
//				itemIdStr = basket[i].getId();
//			}else{
//				itemIdStr = itemIdStr + "," + basket[i].getId();
//			}
//		}
//
//		SubscribedChannelUI[] channel = sessionOrder.getSubscribedChannels();
//		String channelIdStr = "";
//		if(channel != null){
//			for(int i=0; i < channel.length; i++){
//				if(i==0){
//					channelIdStr = channel[i].getChannelId();
//				}else{
//					channelIdStr = channelIdStr + "," + channel[i].getChannelId();
//				}
//			}
//		}
//		
//		ImsRptBasketDtlDTO basketDtl = new ImsRptBasketDtlDTO();
//		List<ImsRptBasketItemDTO> basketItemList = new ArrayList<ImsRptBasketItemDTO>();
//		List<ImsRptBasketItemDTO> optServiceList = new ArrayList<ImsRptBasketItemDTO>();
//		List<ImsRptBasketItemDTO> ntvPgmItemList = new ArrayList<ImsRptBasketItemDTO>();
//		List<ImsRptChannelDTO> channelFreeList = new ArrayList<ImsRptChannelDTO>();
//		List<ImsRptChannelDTO> channelPayList = new ArrayList<ImsRptChannelDTO>();
//		
//		basketDtl = imsReport2Service.getBasketDetail(basketId, locale);
//		logger.debug("\nBasket detail=" + basketDtl);
//		/*
//		 * Added on 20130218 by Eric Ng. This is for IMS OnlineSales order display.
//		 */
//		basketDtl.setBasketTitle(basketDtl.getBandwidth() + "M " + basketDtl.getBasketTitle());
//		form.setBasketDtl(basketDtl);
//		
//		if(itemIdStr != ""){
//			basketItemList = imsReport2Service.getBasketItem(basketId, itemIdStr, locale);
//			logger.debug("\nItem list =" + basketItemList);
//			//sessionSummary.setBasketItemList(basketItemList);
//			
//			if(basketDtl.getCanSubcOptSrv().equalsIgnoreCase("Y")){
//				optServiceList = imsReport2Service.getOptServiceY(itemIdStr, locale);
//			}else{
//				optServiceList = imsReport2Service.getOptServiceN(basketId, itemIdStr, locale);
//			}
//			logger.debug("\nOptional Service list =" + optServiceList);
//
//			ntvPgmItemList = imsReport2Service.getNtvItem(itemIdStr, locale);
//			logger.debug("\nItem list =" + ntvPgmItemList);
//		}
//
//		if(channelIdStr != ""){
//			channelFreeList = imsReport2Service.getNtvChannelFree(channelIdStr, locale);
//			//logger.debug("\nItem list =" + channelFreeList);
//
//			channelPayList = imsReport2Service.getNtvChannelPay(channelIdStr, locale);
//			//logger.debug("\nItem list =" + channelPayList);
//		}
//		
//		double totalRecurrentAmt = 0;
//		double totalMthToMthRate = 0;
//		
//		// Core Service
//		coreServiceDtl.setBandwidth(basketDtl.getBandwidth());
//		coreServiceDtl.setCanSubcOptSrv(basketDtl.getCanSubcOptSrv());
//		
//		ImsRptBasketItemDTO item; 
//		for (int i=0; i < basketItemList.size(); i++){
//			if (basketItemList.get(i).getType().equalsIgnoreCase("PROG")) {
//				progItem.setType(basketItemList.get(i).getType());
//				progItem.setMdoInd(null);
//				progItem.setItemTitle(basketDtl.getBasketTitle());
//				progItem.setItemMthFix(basketItemList.get(i).getItemMthFix());
//				progItem.setItemMth2Mth(basketItemList.get(i).getItemMth2Mth());
//				progItem.setItemDetails(basketItemList.get(i).getItemDetails());
//				progItem.setItemMthFixAmt(basketItemList.get(i).getItemMthFixAmt());
//				progItem.setItemMth2MthAmt(basketItemList.get(i).getItemMth2MthAmt());
//			}
//			if (basketItemList.get(i).getType().equalsIgnoreCase("BVAS")) {
//				if (basketItemList.get(i).getMdoInd().equalsIgnoreCase("M")) {
//					item = new ImsRptBasketItemDTO(); 
//					item.setType(basketItemList.get(i).getType());
//					item.setMdoInd(basketItemList.get(i).getMdoInd());
//					item.setItemTitle(basketItemList.get(i).getItemDetailName());
//					item.setItemMthFix(null);
//					item.setItemMth2Mth(null);
//					item.setItemDetails(basketItemList.get(i).getItemDetailInfo());
//					item.setItemMthFixAmt(null);
//					item.setItemMth2MthAmt(null);
//					bvasMandItemList.add(item);
//				} else {
//					item = new ImsRptBasketItemDTO(); 
//					item.setType(basketItemList.get(i).getType());
//					item.setMdoInd(basketItemList.get(i).getMdoInd());
//					item.setItemTitle(basketItemList.get(i).getItemDetailName());
//					item.setItemMthFix(basketItemList.get(i).getItemMthFix());
//					item.setItemMth2Mth(basketItemList.get(i).getItemMth2Mth());
//					item.setItemDetails(basketItemList.get(i).getItemDetailInfo());
//					item.setItemMthFixAmt(basketItemList.get(i).getItemMthFixAmt());
//					item.setItemMth2MthAmt(basketItemList.get(i).getItemMth2MthAmt());
//					if(basketItemList.get(i).getItemMthFixAmt() != null && !("").equals(basketItemList.get(i).getItemMthFixAmt())){
//						totalRecurrentAmt = totalRecurrentAmt + Double.parseDouble(basketItemList.get(i).getItemMthFixAmt());
//					}
//					if(basketItemList.get(i).getItemMth2MthAmt() != null && !("").equals(basketItemList.get(i).getItemMth2MthAmt())){
//						totalMthToMthRate = totalMthToMthRate + Double.parseDouble(basketItemList.get(i).getItemMth2MthAmt());
//					}
//					bvasNonMItemList.add(item);
//				}
//			}
//			if (basketItemList.get(i).getType().equalsIgnoreCase("PRE_INST")) {
//				item = new ImsRptBasketItemDTO(); 
//				item.setType(basketItemList.get(i).getType());
//				item.setMdoInd(basketItemList.get(i).getMdoInd());
//				item.setItemTitle(basketItemList.get(i).getItemDetailName());
//				item.setItemMthFix(basketItemList.get(i).getItemMthFix());
//				item.setItemMth2Mth(basketItemList.get(i).getItemMth2Mth());
//				item.setItemDetails(basketItemList.get(i).getItemDetailInfo());
//				item.setItemMthFixAmt(basketItemList.get(i).getItemMthFixAmt());
//				item.setItemMth2MthAmt(basketItemList.get(i).getItemMth2MthAmt());
//				preInstItemList.add(item);
//			}
//		}
//		
//		if(progItem.getItemMthFixAmt() != null && !("").equals(progItem.getItemMthFixAmt())){
//			totalRecurrentAmt = totalRecurrentAmt + Double.parseDouble(progItem.getItemMthFixAmt());
//		}
//		if(progItem.getItemMth2MthAmt() != null && !("").equals(progItem.getItemMth2MthAmt())){
//			totalMthToMthRate = totalMthToMthRate + Double.parseDouble(progItem.getItemMth2MthAmt());
//		}
//		
//		coreServiceDtl.setProgItem(progItem);
//		coreServiceDtl.getProgItem().setItemDetails(coreServiceDtl.getProgItem().getItemDetails().replaceAll(((char)10+""),"<br>"));
//		coreServiceDtl.setBvasMandItemList(bvasMandItemList);
//		coreServiceDtl.setBvasNonMItemList(bvasNonMItemList);
//		servPlanDto.setCoreServiceDetail(coreServiceDtl);
//		
//		// Optional Service
//		for (int i=0; i < optServiceList.size(); i++){
//			if (optServiceList.get(i).getType().equalsIgnoreCase("OPT_PREM")) {
//				ImsRptBasketItemDTO optPrem = new ImsRptBasketItemDTO(); 
//				optPrem.setType(optServiceList.get(i).getType());
//				optPrem.setMdoInd(null);
//				optPrem.setItemTitle(optServiceList.get(i).getItemDetailName());
//				optPrem.setItemMthFix(optServiceList.get(i).getItemMthFix());
//				optPrem.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
//				optPrem.setItemDetails(optServiceList.get(i).getItemDetailInfo());
//				optPrem.setItemMthFixAmt(optServiceList.get(i).getItemMthFixAmt());
//				optPrem.setItemMth2MthAmt(optServiceList.get(i).getItemMth2MthAmt());
//				optPremItemList.add(optPrem);
//			}
///*			if (optServiceList.get(i).getType().equalsIgnoreCase("WL_BB")) {
//				ImsRptBasketItemDTO wlBb = new ImsRptBasketItemDTO(); 
//				wlBb.setType(optServiceList.get(i).getType());
//				wlBb.setMdoInd(null);
//				wlBb.setItemTitle(optServiceList.get(i).getItemDetailName());
//				wlBb.setItemMthFix(optServiceList.get(i).getItemMthFix());
//				wlBb.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
//				wlBb.setItemDetails(optServiceList.get(i).getItemDetailInfo());
//				wlBb.setItemMthFixAmt(optServiceList.get(i).getItemMthFixAmt());
//				wlBb.setItemMth2MthAmt(optServiceList.get(i).getItemMth2MthAmt());
//				wlBbItemList.add(wlBb);
//			}
//			if (optServiceList.get(i).getType().equalsIgnoreCase("ANTI_VIR")) {
//				ImsRptBasketItemDTO antiVir = new ImsRptBasketItemDTO(); 
//				antiVir.setType(optServiceList.get(i).getType());
//				antiVir.setMdoInd(null);
//				antiVir.setItemTitle(optServiceList.get(i).getItemDetailName());
//				antiVir.setItemMthFix(optServiceList.get(i).getItemMthFix());
//				antiVir.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
//				antiVir.setItemDetails(optServiceList.get(i).getItemDetailInfo());
//				antiVir.setItemMthFixAmt(optServiceList.get(i).getItemMthFixAmt());
//				antiVir.setItemMth2MthAmt(optServiceList.get(i).getItemMth2MthAmt());
//				antiVirItemList.add(antiVir);
//			}
//*/
//			if (!optServiceList.get(i).getType().equalsIgnoreCase("OPT_PREM")
//					&& !optServiceList.get(i).getType().equalsIgnoreCase("MEDIA")) {
//				ImsRptBasketItemDTO optServ = new ImsRptBasketItemDTO(); 
//				optServ.setType(optServiceList.get(i).getType());
//				optServ.setMdoInd(null);
//				optServ.setItemTitle(optServiceList.get(i).getItemDetailName());
//				optServ.setItemMthFix(optServiceList.get(i).getItemMthFix());
//				optServ.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
//				optServ.setItemDetails(optServiceList.get(i).getItemDetailInfo());
//				optServ.setItemMthFixAmt(optServiceList.get(i).getItemMthFixAmt());
//				optServ.setItemMth2MthAmt(optServiceList.get(i).getItemMth2MthAmt());
//				optServItemList.add(optServ);
//			}
//			if (optServiceList.get(i).getType().equalsIgnoreCase("MEDIA")) {
//				ImsRptBasketItemDTO media = new ImsRptBasketItemDTO(); 
//				media.setType(optServiceList.get(i).getType());
//				media.setMdoInd(null);
//				media.setItemTitle(optServiceList.get(i).getItemDetailName());
//				media.setItemMthFix(optServiceList.get(i).getItemMthFix());
//				media.setItemMth2Mth(optServiceList.get(i).getItemMth2Mth());
//				media.setItemDetails(optServiceList.get(i).getItemDetailInfo());
//				media.setItemMthFixAmt(optServiceList.get(i).getItemMthFixAmt());
//				media.setItemMth2MthAmt(optServiceList.get(i).getItemMth2MthAmt());
//				mediaItemList.add(media);
//			}
//			if(optServiceList.get(i).getItemMthFixAmt() != null && !("").equals(optServiceList.get(i).getItemMthFixAmt())){
//				totalRecurrentAmt = totalRecurrentAmt + Double.parseDouble(optServiceList.get(i).getItemMthFixAmt());
//			}
//			if(optServiceList.get(i).getItemMth2MthAmt() != null && !("").equals(optServiceList.get(i).getItemMth2MthAmt())){
//				totalMthToMthRate = totalMthToMthRate + Double.parseDouble(optServiceList.get(i).getItemMth2MthAmt());
//			}
//		}
//		
//		optServiceDtl.setOptPremItemList(optPremItemList);
//		//optServiceDtl.setWlBbItemList(wlBbItemList);
//		//optServiceDtl.setAntiVirItemList(antiVirItemList);
//		optServiceDtl.setOptServItemList(optServItemList);
//		optServiceDtl.setMediaItemList(mediaItemList);
//		servPlanDto.setOptServiceDetail(optServiceDtl);
//		
//		// Now TV
//		if(ntvPgmItemList == null){
//			form.setNtvPgmItemList(null);
//		}
//		
//		for (int i=0; i < ntvPgmItemList.size(); i++){
//			if (ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_FREE")) {
//				ImsRptBasketItemDTO ntvFree = new ImsRptBasketItemDTO(); 
//				ntvFree.setType(ntvPgmItemList.get(i).getType());
//				ntvFree.setMdoInd(null);
//				ntvFree.setItemTitle(ntvPgmItemList.get(i).getItemDetailName());
//				ntvFree.setItemMthFix(ntvPgmItemList.get(i).getItemMthFix());
//				ntvFree.setItemMth2Mth(ntvPgmItemList.get(i).getItemMth2Mth());
//				ntvFree.setItemDetails(ntvPgmItemList.get(i).getItemDetailInfo().replaceAll(((char)10+""),"<br>"));
//				ntvFree.setItemMthFixAmt(ntvPgmItemList.get(i).getItemMthFixAmt());
//				ntvFree.setItemMth2MthAmt(ntvPgmItemList.get(i).getItemMth2MthAmt());
//				ntvFree.setItemTvType(ntvPgmItemList.get(i).getItemTvType());
//				ntvFreeItemList.add(ntvFree);
//			}
///*			if (ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_PAY")
//					|| ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_P_30F6")) {*/
//			if (!ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_FREE")
//					&& !ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_OTHER")) {
//				ImsRptBasketItemDTO ntvPay = new ImsRptBasketItemDTO(); 
//				ntvPay.setType(ntvPgmItemList.get(i).getType());
//				ntvPay.setMdoInd(null);
//				ntvPay.setItemTitle(ntvPgmItemList.get(i).getItemDetailName());
//				ntvPay.setItemMthFix(ntvPgmItemList.get(i).getItemMthFix());
//				ntvPay.setItemMth2Mth(ntvPgmItemList.get(i).getItemMth2Mth());
//				ntvPay.setItemDetails(ntvPgmItemList.get(i).getItemDetailInfo());
//				ntvPay.setItemMthFixAmt(ntvPgmItemList.get(i).getItemMthFixAmt());
//				ntvPay.setItemMth2MthAmt(ntvPgmItemList.get(i).getItemMth2MthAmt());
//				ntvPay.setItemTvType(ntvPgmItemList.get(i).getItemTvType());
//				ntvPayItemList.add(ntvPay);
//			}
//			if (ntvPgmItemList.get(i).getType().equalsIgnoreCase("NTV_OTHER")) {
//				ImsRptBasketItemDTO ntvOther = new ImsRptBasketItemDTO(); 
//				ntvOther.setType(ntvPgmItemList.get(i).getType());
//				ntvOther.setMdoInd(null);
//				ntvOther.setItemTitle(ntvPgmItemList.get(i).getItemDetailName());
//				ntvOther.setItemMthFix(ntvPgmItemList.get(i).getItemMthFix());
//				ntvOther.setItemMth2Mth(ntvPgmItemList.get(i).getItemMth2Mth());
//				ntvOther.setItemDetails(ntvPgmItemList.get(i).getItemDetailInfo());
//				ntvOther.setItemMthFixAmt(ntvPgmItemList.get(i).getItemMthFixAmt());
//				ntvOther.setItemMth2MthAmt(ntvPgmItemList.get(i).getItemMth2MthAmt());
//				ntvOther.setItemTvType(ntvPgmItemList.get(i).getItemTvType());
//				ntvOtherItemList.add(ntvOther);
//			}
//			if(ntvPgmItemList.get(i).getItemMthFixAmt() != null && !("").equals(ntvPgmItemList.get(i).getItemMthFixAmt())){
//				totalRecurrentAmt = totalRecurrentAmt + Double.parseDouble(ntvPgmItemList.get(i).getItemMthFixAmt());
//			}
//			if(ntvPgmItemList.get(i).getItemMth2MthAmt() != null && !("").equals(ntvPgmItemList.get(i).getItemMth2MthAmt())){
//				totalMthToMthRate = totalMthToMthRate + Double.parseDouble(ntvPgmItemList.get(i).getItemMth2MthAmt());
//			}
//		}
//		
//		for (int i=0; i < channelFreeList.size(); i++){
//			if (channelFreeList.get(i).getChannelGroupCd().equalsIgnoreCase("STARTERPACK")) {
//				ImsRptChannelDTO starterPack = new ImsRptChannelDTO(); 
//				starterPack.setChannelGroupCd(channelFreeList.get(i).getChannelGroupCd());
//				starterPack.setChannelGroupDesc(channelFreeList.get(i).getChannelGroupDesc());
//				starterPack.setChannelId(channelFreeList.get(i).getChannelId());
//				starterPack.setChannelCd(channelFreeList.get(i).getChannelCd());
//				starterPack.setTvType(channelFreeList.get(i).getTvType());
//				starterPack.setCredit(channelFreeList.get(i).getCredit());
//				starterPack.setMdoInd(channelFreeList.get(i).getMdoInd());
//				starterPack.setChannelDesc(channelFreeList.get(i).getChannelDesc().replaceAll(((char)10+""),"<br>"));
//				starterPack.setParentChannelId(channelFreeList.get(i).getParentChannelId());
//				ntvFreeSPChannelList.add(starterPack);
//			}
//			if (channelFreeList.get(i).getChannelGroupCd().equalsIgnoreCase("ENTPACK")) {
//				ImsRptChannelDTO enterPack = new ImsRptChannelDTO(); 
//				enterPack.setChannelGroupCd(channelFreeList.get(i).getChannelGroupCd());
//				enterPack.setChannelGroupDesc(channelFreeList.get(i).getChannelGroupDesc());
//				enterPack.setChannelId(channelFreeList.get(i).getChannelId());
//				enterPack.setChannelCd(channelFreeList.get(i).getChannelCd());
//				enterPack.setTvType(channelFreeList.get(i).getTvType());
//				enterPack.setCredit(channelFreeList.get(i).getCredit());
//				enterPack.setMdoInd(channelFreeList.get(i).getMdoInd());
//				enterPack.setChannelDesc(channelFreeList.get(i).getChannelDesc().replaceAll(((char)10+""),"<br>"));
//				enterPack.setParentChannelId(channelFreeList.get(i).getParentChannelId());
//				ntvFreeEPChannelList.add(enterPack);
//			}
//			if (channelFreeList.get(i).getChannelGroupCd().equalsIgnoreCase("FREE2HD")) {
//				ImsRptChannelDTO free2Hd = new ImsRptChannelDTO(); 
//				free2Hd.setChannelGroupCd(channelFreeList.get(i).getChannelGroupCd());
//				free2Hd.setChannelGroupDesc(channelFreeList.get(i).getChannelGroupDesc());
//				free2Hd.setChannelId(channelFreeList.get(i).getChannelId());
//				free2Hd.setChannelCd(channelFreeList.get(i).getChannelCd());
//				free2Hd.setTvType(channelFreeList.get(i).getTvType());
//				free2Hd.setCredit(channelFreeList.get(i).getCredit());
//				free2Hd.setMdoInd(channelFreeList.get(i).getMdoInd());
//				free2Hd.setChannelDesc(channelFreeList.get(i).getChannelDesc().replaceAll(((char)10+""),"<br>"));
//				free2Hd.setParentChannelId(channelFreeList.get(i).getParentChannelId());
//				ntvFreeHDChannelList.add(free2Hd);
//			}
//		}
//		
//		for (int i=0; i < channelPayList.size(); i++){
//			ImsRptChannelDTO channelPay = new ImsRptChannelDTO(); 
//			channelPay.setChannelGroupCd(null);
//			channelPay.setChannelGroupDesc(channelPayList.get(i).getChannelGroupDesc());
//			channelPay.setChannelId(channelPayList.get(i).getChannelId());
//			channelPay.setChannelCd(channelPayList.get(i).getChannelCd());
//			channelPay.setTvType(channelPayList.get(i).getTvType());
//			channelPay.setCredit(channelPayList.get(i).getCredit());
//			channelPay.setMdoInd(channelPayList.get(i).getMdoInd());
//			channelPay.setChannelDesc(channelPayList.get(i).getChannelDesc().replaceAll(((char)10+""),"<br>"));
//			channelPay.setParentChannelId(channelPayList.get(i).getParentChannelId());
//			ntvPayChannelList.add(channelPay);
//		}
//
//		ntvServiceDtl.setNtvFreeItemList(ntvFreeItemList);
//		ntvServiceDtl.setNtvPayItemList(ntvPayItemList);
//		ntvServiceDtl.setNtvOtherItemList(ntvOtherItemList);
//		ntvServiceDtl.setNtvFreeSPChannelList(ntvFreeSPChannelList);
//		ntvServiceDtl.setNtvFreeEPChannelList(ntvFreeEPChannelList);
//		ntvServiceDtl.setNtvFreeHDChannelList(ntvFreeHDChannelList);
//		ntvServiceDtl.setNtvPayChannelList(ntvPayChannelList);
//		servPlanDto.setNtvServiceDetail(ntvServiceDtl);
//
//		form.setServPlanDto(servPlanDto);
//		form.setTotalRecurrentAmt(totalRecurrentAmt);
//		form.setTotalMthToMthRate(totalMthToMthRate);
		
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		form.setSaleResendEmailAllowed(this.imsOlOrderSearchService.IsSaleResendEmailAllowed(user.getCategory()));
		logger.info("User Category: " + user.getCategory() + ", IsSaleResendEmailAllowed: " + form.isSaleResendEmailAllowed());
		
	    return form;
	}	
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {
		
		logger.info("Order submitted");
		
		ImsResendEmailHistoryUI form = (ImsResendEmailHistoryUI) command;
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		logger.info("email address: " + form.getEsigEmailAddr() );
		logger.info("isEmailSendConfirm: " + form.isEmailSendConfirm());
		ModelAndView modelAndView = new ModelAndView(new RedirectView("imsorderdetail.html?orderId=" + form.getOrderId()+ "&imsOrderEnquiry=Y"));
		
		if (form.isEmailSendConfirm()){
			List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOnlyOrderId(form.getOrderId());
			
			if (!isEmpty(list)) {
				OrdEmailReqDTO ordEmailReqDto = list.get(0);
				EmailReqResult result = this.orderEsignatureService.resendEmailReq(ordEmailReqDto.getOrderId(), ResendEmailTemplateMapping(ordEmailReqDto.getTemplateId())
						, ordEmailReqDto.getFilePathName1(), ordEmailReqDto.getFilePathName2(), ordEmailReqDto.getFilePathName3()
						, form.getEsigEmailAddr(), user.getUsername());
				
				
				logger.info("user.getUsername(): " + user.getUsername());
				
				modelAndView.addObject("emailReqResult", result.toString());
				modelAndView.addObject("emailReqResultMsg", result.getMessage());
				
				if (EmailReqResult.SUCCESS.equals(result)) {
					List<OrdEmailReqDTO> latestList = this.ordEmailReqService.getOrdEmailReqDTOALLByOnlyOrderId(form.getOrderId());
					if (!isEmpty(latestList)) {
						modelAndView.addObject("sentDate", Utility.date2String(latestList.get(latestList.size() - 1).getSentDate(), BomWebPortalConstant.DATE_FORMAT + " HH:mm:ss"));
					}
				}
			}
			
		}
		
		return modelAndView;
	}
	
	
	private String ResendEmailTemplateMapping(String templateId){
		
		String mapToTemplateId = templateId.replace("RT0", "RT1");
		
//		if (("RT004").equals(templateId)){
//			mapToTemplateId = "RT104";
//		}else if (("RT005").equals(templateId)){
//			mapToTemplateId = "RT105";
//		}else if (("RT006").equals(templateId)){
//			mapToTemplateId = "RT106";
//		}
		
		
		
		logger.info("Map template id from "+templateId + " to " + mapToTemplateId );
		
		return mapToTemplateId;
	}
		
	
	public Map<String, Object> referenceData(HttpServletRequest request) {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		//BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		
		logger.info("orderid: " + sessionOrder.getOrderId());
		logger.info("DisMode: " + sessionOrder.getDisMode());
		
		if (sessionOrder.getDisMode() == DisMode.E){
//			String emailReqResult = request.getParameter("emailReqResult");
//			referenceData.put("emailReqResult", emailReqResult);
//			
//			String emailReqResultMsg = request.getParameter("emailReqResultMsg");
//			referenceData.put("emailReqResultMsg", emailReqResultMsg);
			
			/*
		     * Prepare email history
		     */
		    
	    	logger.info("Prepare email history.");
	    	List<OrdEmailReqDTO> list = this.ordEmailReqService.getOrdEmailReqDTOALLByOnlyOrderId(sessionOrder.getOrderId());
	    	List<OrdEmailReqDTO> listWithoutSRDEmail = new ArrayList<OrdEmailReqDTO>();
	    	int seq_display = 1;
	    	for (OrdEmailReqDTO dto : list){
	    		if (!dto.getTemplateId().equals("RT007") && !dto.getTemplateId().equals("RT011")){
	    			dto.setSeqNo(seq_display);
	    			seq_display++;
	    			listWithoutSRDEmail.add(dto);
	    		}
	    	}
	    	logger.info("Sending email request count: " + listWithoutSRDEmail.size());
	    	
	    	referenceData.put("emailHistoryList", listWithoutSRDEmail);
	    	
			if (!isEmpty(listWithoutSRDEmail)) {
				OrdEmailReqDTO ordEmailReqDto = listWithoutSRDEmail.get(0);
				EmailTemplateDTO emailTemplateDto = this.orderEsignatureService.getEmailTemplateDTO(
						this.ResendEmailTemplateMapping(ordEmailReqDto.getTemplateId()), 
						ordEmailReqDto.getLob(), 
						ordEmailReqDto.getEsigEmailLang());
				
				referenceData.put("emailTemplateDto", emailTemplateDto);
				
				try {
					String emailSubject = this.orderEsignatureService.getEmailSubject(emailTemplateDto, ordEmailReqDto,null);
					String emailContent = this.orderEsignatureService.getEmailContent(emailTemplateDto, ordEmailReqDto,null);
					referenceData.put("emailSubject", emailSubject);
					referenceData.put("emailContent", emailContent);
					
					
				} catch (Exception e) {
					if (logger.isDebugEnabled()) {
						logger.debug("Exception during prepare email preview", e);
					}
					referenceData.put("emailException", e);
				}
			}
	    	

		}
		

		return referenceData;
	}

	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	
}
