package com.bomwebportal.ims.web;

import net.sf.json.JSONArray;

import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;



import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.VasParmDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.ImsMobilePinException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.AttbDTO;
import com.bomwebportal.ims.dto.BasketDetailsDTO;
import com.bomwebportal.ims.dto.ImsBasketDTO;
import com.bomwebportal.ims.dto.NowTVCheckDTO;
import com.bomwebportal.ims.dto.ServiceDetailDTO;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.ims.dto.ValidateOfferResultDTO;
import com.bomwebportal.ims.dto.ui.AddressInfoUI;
import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.ComponentUI;
import com.bomwebportal.ims.dto.ui.GiftUI;
import com.bomwebportal.ims.dto.ui.InstallAddrUI;
import com.bomwebportal.ims.dto.ui.InstallFeeUI;
import com.bomwebportal.ims.dto.ui.NowTVUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedChannelUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.dto.ui.VASDetailUI;
import com.bomwebportal.ims.dto.ui.BasketDetailUI;
import com.bomwebportal.ims.service.DevService;
import com.bomwebportal.ims.service.ImsBasketDetailsService;
import com.bomwebportal.ims.service.ImsBasketSelectService;
import com.bomwebportal.ims.service.ImsPaymentService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.ext.*;
import com.google.gson.Gson;
import com.bomwebportal.service.ImsBasketCommonService;
import com.bomwebportal.service.MobilePINService;
import com.bomwebportal.service.MobilePINServiceImpl.MobileOffer;

public class ImsBasketDetailsController extends SimpleFormController{

    protected final Log logger = LogFactory.getLog(getClass());
    private Gson gson = new Gson();

    private ImsBasketDetailsService service;
    private MobilePINService mobilePINService;
    private MessageSource message;
    
    private ImsPaymentService imsPaymentService;
    
    public MobilePINService getMobilePINService() {
		return mobilePINService;
	}
	public void setMobilePINService(MobilePINService mobilePINService) {
		this.mobilePINService = mobilePINService;
	}
	public ImsBasketDetailsService getService() {
		return service;
	}
	public void setService(ImsBasketDetailsService service) {
		this.service = service;
	}
	
	private ImsBasketCommonService imsBasketCommonService;
		
	
	public ImsBasketCommonService getImsBasketCommonService() {
		return imsBasketCommonService;
	}
	public void setImsBasketCommonService(
			ImsBasketCommonService imsBasketCommonService) {
		this.imsBasketCommonService = imsBasketCommonService;
	}
    
	public ImsPaymentService getImsPaymentService() {
		return imsPaymentService;
	}

	public void setImsPaymentService(ImsPaymentService imsPaymentService) {
		this.imsPaymentService = imsPaymentService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderImsUI sessionOrder = (OrderImsUI) request.getSession()
				.getAttribute(ImsConstants.IMS_ORDER);
		String basketID=request.getParameter("basketID");
		if (basketID == null){
			if(sessionOrder.getSubscribedItems()==null) {
				return new ModelAndView(new RedirectView("imsbasketselect.html"));
			}else{
				return super.handleRequest(request, response);
			}
		} else {
			return super.handleRequest(request, response);
		}
	}
			 
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		
		if("Y".equals(request.getSession().getAttribute("MrtReserveException"))){
			throw new ImsMobilePinException();
		}
		
		logger.debug(request.getSession().getAttribute(ImsConstants.IMS_ORDER)==null?"NULL":"ORDER FOUND");
/*		if (request.getSession().getAttribute(ImsConstants.IMS_ORDER) == null) {
			OrderImsUI tmpOrder = DevService.getNewOrder();
			request.getSession().setAttribute(ImsConstants.IMS_ORDER, tmpOrder);
		}*/
		
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		String locale = RequestContextUtils.getLocale(request).toString();
		String customerTier=request.getParameter("customertier");
		String basketID=request.getParameter("basketID");
		String preInstallInd = request.getParameter("isPreinstall");
		String preUseInd = request.getParameter("isPreuse");
		
		String appDate ="sysdate";
		
		String[] VASList = (String[])request.getParameterValues("VASItemBox");
		String[] GiftList = (String[])request.getParameterValues("GiftItemBox");
		List<String> SelectedVASList = new ArrayList<String>();
		List<String> SelectedGiftList = new ArrayList<String>();
		
		if(VASList!=null){
			for(int j=0; j<VASList.length; j++){
				SelectedVASList.add(VASList[j]);
			}
		}
		
		if(GiftList!=null){
			for(int j=0; j<GiftList.length; j++){
				SelectedGiftList.add(GiftList[j]);
			}
		}
		
		if(preInstallInd!=null){
			order.setPreInstallInd(preInstallInd);
		}
		if(preUseInd!=null){
			order.setPreUseInd(preUseInd);
		}
		
		if(order.getAppDate()!=null){
			SimpleDateFormat dateyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
			appDate = 
				"to_date('" + 
				dateyyyyMMdd.format(order.getAppDate())+
				"','yyyymmdd')";
		}
		logger.info("appDate: "+appDate);
		
		logger.info("locale:" + locale);
		logger.info("ImsBasketDetailsController.formBackingObject() Start");
		
    	BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	if (user==null){
    		user = new BomSalesUserDTO();
    	}
    	logger.info("userId:" + user.getUsername());
    	
    	//if (orderStatus == suspend){
    	// goneInd = "Y"}
    	logger.info("order.getOrderActionInd():" + order.getOrderActionInd());
    	if( "W".equals(order.getOrderActionInd())){
    		request.getSession().setAttribute("nowtvPageGoneInd", "Y");
    	}
    	order.setComponentsClone(order.getComponents());//for nowTV page use
		if(order.getSubscribedItems()!=null&&order.getSubscribedItems().length>0){
			if(order.getSubscribedItems()[0].getBasketId()!=null){
				if(basketID==null){
					if(!order.getSubscribedItems()[0].getBasketId().equals("")){
						basketID = order.getSubscribedItems()[0].getBasketId();
						order.setNowTvFormType(null);
					}
				}else if(!basketID.equals(order.getSubscribedItems()[0].getBasketId()) && (!"Y".equals(order.isSignoffed()))){
					order.setComponents(null);
					order.setSubscribedImsVasParm(null);
					order.setDecodeType(null);
					order.setIsCreditCardOffer(null);
					order.setNowTvFormType(null);
					order.setPrepayCC(null);
					order.setPrepayCash(null);
					order.setProcessVim(null);
					order.setProcessVms(null);
					order.setProcessWifi(null);
					order.setSubscribedChannels(null);
					order.setSubscribedItems(null);
					order.setTvCredit(null);
					order.setWarrPeriod(null);
					order.setAdultViewAllow(null);
					if(request.getSession().getAttribute("selectedIMSChannelList")!=null){
						request.getSession().setAttribute("selectedIMSChannelList", null);
					}
					if(request.getSession().getAttribute("selectedIMSNowVasList")!=null){
						request.getSession().setAttribute("selectedIMSNowVasList", null);
					}
					if(request.getSession().getAttribute("selectedIMSVasList")!=null){
						request.getSession().setAttribute("selectedIMSVasList", null);
					}
					if(request.getSession().getAttribute("selectedIMSGiftList")!=null){
						request.getSession().setAttribute("selectedIMSGiftList", null);
					}
					if(request.getSession().getAttribute("IMSTVType")!=null){
						request.getSession().setAttribute("IMSTVType", null);
					}
					if(request.getSession().getAttribute("type30F6")!=null){
						request.getSession().setAttribute("type30F6", null);
					}
					if(request.getSession().getAttribute("IMS_ApprovalRequested")!=null){
						request.getSession().setAttribute("IMS_ApprovalRequested", null);
					}	
					
					if(request.getSession().getAttribute("nowtvPageGoneInd")!=null){
						request.getSession().setAttribute("nowtvPageGoneInd", null);
					}

					
					List<MobileOffer> imsMobileOfferUiList = (List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList");
					if (imsMobileOfferUiList != null){
						MobileOffer imsMobileOfferUi = new MobileOffer();
						for(int i= 0; i<imsMobileOfferUiList.size(); i++){	
							if(order != null && !("").equals(order)){
								try{
									imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin, order.getCreateBy(), order.getOrderId(), "NONONLINE");
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
						}
						
						request.getSession().setAttribute("imsMobileOfferUiList", null);	
					}
					if(order.getSubscribedCslOffers() != null){
						
						logger.info("SessionOrder ~ SubscribedCslOffers() != null");
						
						SubscribedCslOfferDTO[] csldto = order.getSubscribedCslOffers(); 
						logger.info("order.getSubscribedCslOffers():" + gson.toJson(csldto));
						MobileOffer imsMobileOfferUi = new MobileOffer();
						if(order != null && !("").equals(order)){
							for(SubscribedCslOfferDTO i:csldto){
								if(i.getVas_parm_a()!= null && !("").equals(i.getVas_parm_a()) && i.getVas_parm_b()!= null && !("").equals(i.getVas_parm_b())){
									try{
										imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(i.getVas_parm_a(), i.getVas_parm_b(), order.getCreateBy(), order.getOrderId(), "NONONLINE");
									}catch(Exception e){
										logger.error("Exception caught in releaseMobilePIN()", e);
										throw new ImsMobilePinException();
									}
									logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);								
								}
							}
						}else{
							for(SubscribedCslOfferDTO i:csldto){
								if(i.getVas_parm_a()!= null && !("").equals(i.getVas_parm_a()) && i.getVas_parm_b()!= null && !("").equals(i.getVas_parm_b())){
									try{
										imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(i.getVas_parm_a(), i.getVas_parm_b());
									}catch(Exception e){
										logger.error("Exception caught in releaseMobilePIN()", e);
										throw new ImsMobilePinException();
									}
									logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);
								}
							}
						}
						
						order.setSubscribedCslOffers(null);
					}
				}
			}
		}else {
			order.setNowTvFormType(null);
		}
		
		if(basketID!=null&&order.getSubscribedItems()!=null&&order.getSubscribedItems().length>0&&basketID.equals(order.getSubscribedItems()[0].getBasketId())){
			request.getSession().setAttribute("isSameBasket", "Y");
		}else{
			request.getSession().setAttribute("isSameBasket", "N");
		}
		
		if(customerTier!=null&&!"".equals(customerTier)){
			request.getSession().setAttribute("customerTierSession", customerTier);
		}

		//order.setSubscribedItems(new SubscribedItemUI[0]);

		//get select list from DB

		List<BasketDetailsDTO> basketDetailsList = new ArrayList<BasketDetailsDTO>();

		basketDetailsList =service.getBasketDetailsList(locale, basketID);
		
		List<VASDetailUI> FULLBVASDetailList = new ArrayList<VASDetailUI>();
		List<VASDetailUI> BOVASDetailList = new ArrayList<VASDetailUI>();
		List<VASDetailUI> BVASDetailList = new ArrayList<VASDetailUI>();
		List<VASDetailUI> FilterItemList = new ArrayList<VASDetailUI>();
		List<GiftUI> giftList = new ArrayList<GiftUI>(); //Tony 20140902
		List<GiftUI> promoGiftList = new ArrayList<GiftUI>(); //Tony 20140902
		HashMap<String, HashMap<String, Object>> giftSelectCnt = new HashMap<String, HashMap<String, Object>>(); 
		
		FilterItemList = service.getFilterItemList("IMS_ACQ_FILTER_OUT");
		List<String> removeItemList = new ArrayList<String>();
		

		String housingType = "";
		
		String SB = "";
		
		String channel = "";
		
		if(order.getInstallAddress().getHousingTypeList()!=null){
			housingType = order.getInstallAddress().getHousingTypeList().get(0).getHousingType();
			SB = order.getInstallAddress().getSerbdyno();
			boolean inFilter = false;
			boolean allFilter = false;
			String removeItemID = "";
			String tempItemID = "";
			List<String> keepList = new ArrayList<String>();
			for(int j=0; j<FilterItemList.size();j++){
				inFilter = false;
				
				for(int i=0; i<order.getInstallAddress().getHousingTypeList().size();i++){
					//System.out.println("Housing Type + Filter Type: " 
							//+ order.getInstallAddress().getHousingTypeList().get(i).getHousingType() 
							//+ " + " + FilterItemList.get(j).getItemType());
					if(order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals(FilterItemList.get(j).getItemType())){
						inFilter = true;
						removeItemList.add(FilterItemList.get(j).getItemId());
						
					}
				}
				if(!inFilter){	
					allFilter = true;
				}
				if(tempItemID!=FilterItemList.get(j).getItemId() && !allFilter){
					if(keepList!=null){
						for (int k =0;k<keepList.size();k++){
							if(keepList.get(k).equals(tempItemID)){
								keepList.add(tempItemID);
							}
						}
					}else keepList.add(tempItemID);
				}
				tempItemID=FilterItemList.get(j).getItemId();
				
				
			}
			
			//System.out.println("removeItemList: " + removeItemList);

			//System.out.println("keepList: " + keepList);
		}
		
		FULLBVASDetailList = service.getBVASDetailList(locale, basketID, appDate);

		List<VASDetailUI> VASDetailList = new ArrayList<VASDetailUI>();
		List<VasParmDTO> imsVasParmList = new ArrayList<VasParmDTO>();
		
		if(FULLBVASDetailList!=null){
			for(int i =0;i<FULLBVASDetailList.size();i++){
				if ("Y".equalsIgnoreCase(order.getPreInstallInd())||"Y".equalsIgnoreCase(order.getPreUseInd())){
					FULLBVASDetailList.get(i).setItemMdoInd("M");
//					if(FULLBVASDetailList.get(i).getItemType().equals("BVAS")||
//							FULLBVASDetailList.get(i).getItemType().equals("PRE_INST") ||
//							FULLBVASDetailList.get(i).getItemType().equals("OTHER")){
//						BVASDetailList.add(FULLBVASDetailList.get(i));
//					}else{
//						FULLBVASDetailList.get(i).setVASContractPeriod("0");
//						FULLBVASDetailList.get(i).setRPVASContractMatch("0");
//						FULLBVASDetailList.get(i).setRPContractPeriod("0");
//						BOVASDetailList.add(FULLBVASDetailList.get(i));
//					}
				}
//				else{
					BVASDetailList.add(FULLBVASDetailList.get(i));
//				}
			}
		}
		
		String coreOfferProductIds = "";
		if(basketDetailsList!=null)
		{
			String coreOfferComma = "";
			for(int i =0;i<basketDetailsList.size();i++){
				basketDetailsList.get(i).setSummary(basketDetailsList.get(i).getSummary().replaceAll(((char)10)+"", "<br>"));
				basketDetailsList.get(i).setItemDetail(basketDetailsList.get(i).getItemDetail().replaceAll(((char)10)+"", "<br>"));
				if( basketDetailsList.get(i).getProductId() !=null && !"".equals(basketDetailsList.get(i).getProductId())){
					coreOfferProductIds += coreOfferComma + basketDetailsList.get(i).getProductId(); 
					coreOfferComma = ",";
				}
			}
		}
		logger.info("coreOfferProductIds: "+ coreOfferProductIds);

		order.setPcdLike100Ind(imsBasketCommonService.getLikeOfferByProdId(coreOfferProductIds));		
    	
    	String tmpOtChrgType = "";
    	if("PON".equalsIgnoreCase(basketDetailsList.get(0).getTechnology())){
    		tmpOtChrgType = order.getPonOTChrgType();
    	}else if("VDSL".equalsIgnoreCase(basketDetailsList.get(0).getTechnology())){
    		tmpOtChrgType = order.getVdslOTChrgType();
    	}else if("ADSL".equalsIgnoreCase(basketDetailsList.get(0).getTechnology())){
    		tmpOtChrgType = order.getAdslOTChrgType();
    	}else if("Vectoring".equalsIgnoreCase(basketDetailsList.get(0).getTechnology())){
    		tmpOtChrgType = order.getVectorOTChrgType();
    	}
    	
    	order.setTmpOTChrgType(tmpOtChrgType);
		
    	order.setServiceWaiver(null);
    	if(("Y".equalsIgnoreCase(basketDetailsList.get(0).getWaiveISFInd())&&"I".equalsIgnoreCase(order.getTmpOTChrgType()))
    			||("Y".equalsIgnoreCase(basketDetailsList.get(0).getWaiveASFInd())&&"A".equalsIgnoreCase(order.getTmpOTChrgType()))){
    		order.setServiceWaiver("B");
    	}
		
		String otChrgInd = "";
		
		if(order.getServiceWaiver()!=null&&"B".equalsIgnoreCase(order.getServiceWaiver())){
			otChrgInd = "W";
		}else{
			otChrgInd = order.getTmpOTChrgType();
		}
		
		if(basketDetailsList.size()>0 && basketDetailsList!=null){
			if(basketDetailsList.get(0).getCanSubcOptSrv().equals("Y")){
				String technology = basketDetailsList.get(0).getTechnology();
				String bandwidth = basketDetailsList.get(0).getBandwidth();
				String progOfferCd = basketDetailsList.get(0).getOfferCode();
				VASDetailList = service.getVASDetailList(locale, basketID, appDate, bandwidth, technology, housingType, order.getInstallAddress().getSbFilterVas(), 
						order.getIsCC(),order.getPreInstallInd(),order.getPcdLike100Ind(),order.getMobileOfferInd(),order.getPreUseInd(),order.getInstallAddress().getSerbdyno(),
						order.getSupremeFsInd(),order.getChannelTeamCd(),order.getStaffGroup(),order.getServiceCodeStr(),progOfferCd,otChrgInd);
				imsVasParmList = service.getimsVasParmList(locale);
			}
//			else if (basketDetailsList.get(0).getIsPreintalltion().equals("Y")){
//				if(BOVASDetailList!=null){
//					for(int i =0;i<BVASDetailList.size();i++){
//						VASDetailList = BOVASDetailList;
//					}
//				}
//			}
		}
		
		BasketDetailJoinCslOfferbyProdIds(order, basketDetailsList, coreOfferProductIds);
		
		String productIds = "";
		if(BVASDetailList!=null)
		{
			int newlineInx = 0;
			String comma = "";
			
			for(int i =0;i<BVASDetailList.size();i++){
								
				if( BVASDetailList.get(i).getProductId() !=null && !"".equals(BVASDetailList.get(i).getProductId())){
					productIds += comma + BVASDetailList.get(i).getProductId(); 
					comma = ",";
				}
				
				if(BVASDetailList.get(i).getVASHtml()!=null){
					newlineInx = BVASDetailList.get(i).getVASHtml().indexOf("<br/>");
					if(newlineInx < 0)
						newlineInx = BVASDetailList.get(i).getVASHtml().indexOf((char)10+"");
					if(BVASDetailList.get(i).getVASHtml().indexOf((char)10+"")>0){
						BVASDetailList.get(i).setVASTitle(BVASDetailList.get(i).getVASHtml().substring(0, newlineInx));
						BVASDetailList.get(i).setVASDetail(BVASDetailList.get(i).getVASHtml().substring(newlineInx+1).replaceAll(((char)10)+"", "<br>"));
					}else if(BVASDetailList.get(i).getVASHtml().indexOf("<br/>") > 0)
					{
						BVASDetailList.get(i).setVASTitle(BVASDetailList.get(i).getVASHtml().substring(0, newlineInx));
						BVASDetailList.get(i).setVASDetail(BVASDetailList.get(i).getVASHtml().substring(newlineInx+5).replaceAll(((char)10)+"", "<br>"));
					}
					else{
						BVASDetailList.get(i).setVASTitle(BVASDetailList.get(i).getVASHtml());
						BVASDetailList.get(i).setVASDetail("");
					}
					if(order.getComponents()!=null && BVASDetailList.get(i).getAttbId()!=null){
						for(int j=0; j<order.getComponents().length; j++){
							if(order.getComponents()[j].getAttbId().equals(BVASDetailList.get(i).getAttbId())){
								BVASDetailList.get(i).setInputValue(order.getComponents()[j].getAttbValue());
								break;
							}
						}
					}
				}
			}
		}
		
		
		
		//kinman
		logger.info("productIds: "+ productIds);
		VASDetailJoinCslOfferbyProdIds(order,BVASDetailList, imsVasParmList, productIds);
		
		boolean IsElligibleForNowTV = false;
		
		IsElligibleForNowTV = service.checkEligibleForNowTV(basketID);
		
		String vasProductIds = "";
		if(VASDetailList!=null)
		{
			int newlineInx = 0;
			String vasComma = "";
			for(int i =0;i<VASDetailList.size();i++){
				
				if( VASDetailList.get(i).getProductId() !=null && !"".equals(VASDetailList.get(i).getProductId())){
					vasProductIds += vasComma + VASDetailList.get(i).getProductId(); 
					vasComma = ",";
				}
				if(VASDetailList.get(i).getVASHtml()!=null){
					newlineInx = VASDetailList.get(i).getVASHtml().indexOf("<br/>");
					if(newlineInx < 0)
					newlineInx = VASDetailList.get(i).getVASHtml().indexOf((char)10+"");
					if(VASDetailList.get(i).getVASHtml().indexOf((char)10+"")>0){
						VASDetailList.get(i).setVASTitle(VASDetailList.get(i).getVASHtml().substring(0, newlineInx));
						VASDetailList.get(i).setVASDetail(VASDetailList.get(i).getVASHtml().substring(newlineInx+1).replaceAll(((char)10)+"", "<br>"));
					}else if(VASDetailList.get(i).getVASHtml().indexOf("<br/>") > 0)
					{
						VASDetailList.get(i).setVASTitle(VASDetailList.get(i).getVASHtml().substring(0, newlineInx));
						VASDetailList.get(i).setVASDetail(VASDetailList.get(i).getVASHtml().substring(newlineInx+5).replaceAll(((char)10)+"", "<br>"));
					}
					else{
						VASDetailList.get(i).setVASTitle(VASDetailList.get(i).getVASHtml());
						VASDetailList.get(i).setVASDetail("");
					}
				}
				if(order.getComponents()!=null && VASDetailList.get(i).getAttbId()!=null){
					for(int j=0; j<order.getComponents().length; j++){
						if(order.getComponents()[j].getAttbId().equals(VASDetailList.get(i).getAttbId())){
							VASDetailList.get(i).setInputValue(order.getComponents()[j].getAttbValue());
							break;
						}
					}
				}
				//remove Now One Box and HDD VAS items if the basket is not eligible for NowTV
				if(!IsElligibleForNowTV&&("AIO_PUR_FS".equalsIgnoreCase(VASDetailList.get(i).getItemType())||"HDD".equalsIgnoreCase(VASDetailList.get(i).getItemType()))){
					removeItemList.add(VASDetailList.get(i).getItemId());
				}
			}
			if(removeItemList!=null){
				for(int i =0;i<VASDetailList.size();i++){
					for(int j =0;j<removeItemList.size();j++){
						if(VASDetailList.get(i).getItemId().equals(removeItemList.get(j))){
							VASDetailList.remove(i);
						}
					}
				}
			}
		}
		
		
		//kinman
		logger.info("vasProductIds: "+ vasProductIds);
		VASDetailJoinCslOfferbyProdIds(order,VASDetailList, imsVasParmList, vasProductIds);
		
		
		String allProductIds=coreOfferProductIds;
		if(StringUtils.isNotBlank(productIds))
			allProductIds+= ","+productIds;
		if(StringUtils.isNotBlank(vasProductIds))
			allProductIds+= ","+vasProductIds;
		logger.info("allProductIds: "+ allProductIds);
		logger.info("setPcdLike100Ind: "+ order.getPcdLike100Ind());
		
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			channel = "DS";
		}else if(order.getIsPT().equals("Y")){
			channel = "PT";
		}else if(order.getIsCC().equals("Y")){
			channel = "CC";
		}else{
			channel = "RS";
		}
		
		if("Y".equalsIgnoreCase(order.getMobileOfferInd())){
			channel = channel + "_M";
		}
		
		BasketDetailUI BasketDetail = new BasketDetailUI();

		BasketDetail.setBasketDetailList(basketDetailsList);
		BasketDetail.setBVasDetailList(BVASDetailList);
		BasketDetail.setVasDetailList(VASDetailList);
		

		
		InstallFeeUI InstallFee;
		String basketSpecialOTCStr = "";
		
		InstallFee = imsPaymentService.getSpecialOTC(basketID,null);
		
		if(InstallFee!=null&&InstallFee.getImsInstallationInstallmentPlanList().size()>0){
			String otcDesc = "";
			if("en".equalsIgnoreCase(locale)){
				otcDesc = InstallFee.getImsInstallationInstallmentPlanList().get(0).getInstallOTDesc_en()==null?"":InstallFee.getImsInstallationInstallmentPlanList().get(0).getInstallOTDesc_en();
			}else{
				otcDesc = InstallFee.getImsInstallationInstallmentPlanList().get(0).getInstallOTDesc_zh()==null?"":InstallFee.getImsInstallationInstallmentPlanList().get(0).getInstallOTDesc_zh();
			}	
			basketSpecialOTCStr = "$" + InstallFee.getImsInstallationInstallmentPlanList().get(0).getOneTimeFee() + " " + otcDesc;
		}
		
		BasketDetail.setBasketSpecialOTCDesc(basketSpecialOTCStr);
		
		String staffOfferInd = "N";
		if(("11").equals(basketDetailsList.get(0).getBasketTypeId())){
			staffOfferInd = "Y";
		}
		
		if(basketDetailsList.size()>0 && basketDetailsList!=null){
			String technology = basketDetailsList.get(0).getTechnology();
			String bandwidth = basketDetailsList.get(0).getBandwidth();
			String progOfferCd = basketDetailsList.get(0).getOfferCode();
			request.getSession().setAttribute("basketTechnology", technology);
			request.getSession().setAttribute("basketBandwidth", bandwidth);
			request.getSession().setAttribute("progOfferCd", progOfferCd);
			request.getSession().setAttribute("staffOfferInd", staffOfferInd);
			if(basketDetailsList.get(0).getCanSubcOptSrv().equals("Y")){
					giftList = service.getGiftList(housingType, locale, appDate, SB,technology,channel,order.getPcdLike100Ind(),progOfferCd,otChrgInd,order.getChannelTeamCd(),order.getStaffGroup(),staffOfferInd,"N");
					if(!("11").equals(basketDetailsList.get(0).getBasketTypeId())){
						try {
							promoGiftList = service.getPromoGiftList(locale, housingType, SB, channel, technology,order.getPcdLike100Ind(),progOfferCd);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				giftSelectCnt = service.getGiftSelectCnt(channel,locale);
			}
		}
		
		if(giftList!=null){
			HashMap<String, Object> normalGiftCntMap = new HashMap<String, Object>();
			for(GiftUI gift:giftList){ 
					if(!basketDetailsList.get(0).getContractPeriod().equals("0")||("11").equals(basketDetailsList.get(0).getBasketTypeId())){
						boolean checkBandwidth = false;
						boolean checkGiftCampaign = true;
						if(gift.getBandwidth()==null){
							checkBandwidth = true;
						}else{
							String[] bandwidthList = gift.getBandwidth().split(",");
							for(String s:bandwidthList){
								if(basketDetailsList.get(0).getBandwidth().equals(s)){
									checkBandwidth = true;
								}
							}
						}
						//remove gift tied with campaign when the basket is not eligible for nowTV
						if(gift.getCampaignCdList()!=null&&gift.getCampaignCdList().size()>0&&!IsElligibleForNowTV){
							checkGiftCampaign = false;
						}
						
						if(checkBandwidth&&checkGiftCampaign){
								GiftUI gifttmp = gift.clone();
								if(order.getComponents()!=null && gifttmp.getGiftAttbList()!=null && gifttmp.getGiftAttbList().size()>0){
									logger.info("set gift attribute for gift " + gifttmp.getId());
									
									for(int j=0; j<order.getComponents().length; j++){
										for(AttbDTO attb:gifttmp.getGiftAttbList()){
											if(order.getComponents()[j].getAttbId().equals(attb.getAttbId())){
												logger.info("attribute: "+order.getComponents()[j].getAttbValue());
												attb.setInputValue(order.getComponents()[j].getAttbValue());
											}
										}
									}
								}
								if(BasketDetail.getGiftList()==null){
									List<GiftUI> newGiftList = new ArrayList<GiftUI>();
									newGiftList.add(gifttmp);
									BasketDetail.setGiftList(newGiftList);
								}
								else BasketDetail.getGiftList().add(gifttmp);
								if("Y".equalsIgnoreCase(gifttmp.getShowInd())){
									if(normalGiftCntMap.containsKey(gifttmp.getType())){
										normalGiftCntMap.put(gifttmp.getType(), (Integer)normalGiftCntMap.get(gifttmp.getType())+1);
									}else{
										normalGiftCntMap.put(gifttmp.getType(), 1);
									}
								}else{
									if(!normalGiftCntMap.containsKey(gifttmp.getType())){
										normalGiftCntMap.put(gifttmp.getType(), 0);
									}
								}
						}
					}
			}
			
			if(BasketDetail.getGiftList()!=null){
				for(GiftUI gift:BasketDetail.getGiftList()){ 
					gift.setNormalGiftCnt((Integer)normalGiftCntMap.get(gift.getType()));
				}
			}
		}
		
		if(promoGiftList!=null){
			for(GiftUI promoGift:promoGiftList){ 
					if(!basketDetailsList.get(0).getContractPeriod().equals("0")){
						boolean checkBandwidth = false;
						if(promoGift.getBandwidth()==null){
							checkBandwidth = true;
						}else{
							String[] bandwidthList = promoGift.getBandwidth().split(",");
							for(String s:bandwidthList){
								if(basketDetailsList.get(0).getBandwidth().equals(s)){
									checkBandwidth = true;
								}
							}
						}
						
						if(checkBandwidth){
								GiftUI gifttmp = promoGift.clone();
//								IF(ORDER.GETCOMPONENTS()!=NULL && GIFTTMP.GETGIFTATTBLIST()!=NULL && GIFTTMP.GETGIFTATTBLIST().SIZE()>0){
//									LOGGER.INFO("SET GIFT ATTRIBUTE FOR GIFT " + GIFTTMP.GETID());
//									FOR(INT J=0; J<ORDER.GETCOMPONENTS().LENGTH; J++){
//										FOR(ATTBDTO ATTB:GIFTTMP.GETGIFTATTBLIST()){
//											IF(ATTB.GETITEMID().EQUALS(ORDER.GETCOMPONENTS()[J].GETITEMID()) && ORDER.GETCOMPONENTS()[J].GETATTBID().EQUALS(ATTB.GETATTBID())){
//												LOGGER.INFO("ATTRIBUTE: "+ORDER.GETCOMPONENTS()[J].GETATTBVALUE());
//												ATTB.SETINPUTVALUE(ORDER.GETCOMPONENTS()[J].GETATTBVALUE());
//											}
//										}
//									}
//								}
								if(BasketDetail.getPromoGiftList()==null){
									List<GiftUI> newPromoGiftList = new ArrayList<GiftUI>();
									newPromoGiftList.add(gifttmp);
									BasketDetail.setPromoGiftList(newPromoGiftList);
								}
								else BasketDetail.getPromoGiftList().add(gifttmp);
						}
					}
			}
		}
		
		if(giftSelectCnt!=null){
			BasketDetail.setGiftSelectCnt(giftSelectCnt);
		}
		
		if(order.getSubscribedItems()!=null&&order.getSubscribedItems().length>0&&BasketDetail.getPromoGiftList()!=null&&BasketDetail.getPromoGiftList().size()>0){
			for(int i = 0; i<order.getSubscribedItems().length; i++){
				if(order.getSubscribedItems()[i].getType().equalsIgnoreCase("PROM_GIFT")){
					for(int j=0; j<BasketDetail.getPromoGiftList().size();j++){
						if(order.getSubscribedItems()[i].getId().equals(BasketDetail.getPromoGiftList().get(j).getId())){
							BasketDetail.setPromoGiftCode(BasketDetail.getPromoGiftList().get(j).getPromoCode());
							logger.info("promo code: "+BasketDetail.getPromoGiftCode());
						}
					}
				}
			}
		}
		
    	if(SelectedVASList!=null && SelectedVASList.size()>0){
    		request.getSession().setAttribute("selectedIMSVasList", request.getParameterValues("VASItemBox"));
//    		BasketDetail.setExclusiveItemList(service.getExclusiveItemList(locale, SelectedVASList)); 
//    		BasketDetail.setNowExclusiveItemList(service.getNowExclusiveItemList(locale, SelectedVASList)); 
    		logger.info("SelectedVASList.size(): " + SelectedVASList.size());
    		logger.info("SelectedVASList: " + new Gson().toJson(SelectedVASList)); 
    	}

		//default tick dedicated offer gifts
    	//no need pre-tick as requested by marketing
//		if(BasketDetail.getGiftList()!=null&&BasketDetail.getGiftList().size()>0&&!"Y".equalsIgnoreCase(order.getBasketPageVisitInd())){
//			for(GiftUI gift: BasketDetail.getGiftList()){
//				if("Y".equalsIgnoreCase(gift.getDediOfferInd())){
//					SelectedGiftList.add(gift.getId());
//					GiftList = (String[]) ArrayUtils.add(GiftList, gift.getId());
//				}
//			}
//		}
    	
    	if(SelectedGiftList!=null && SelectedGiftList.size()>0){
    		request.getSession().setAttribute("selectedIMSGiftList", GiftList);
    		logger.info("SelectedGiftList.size(): " + SelectedGiftList.size());
    	}
		
		request.getSession().setAttribute("IMS_BasketID", basketID);
		
		String[] selectedIMSVasList = new String[0];
		if(order.getOrderActionInd()!=null && order.getSubscribedItems()!=null){
			if(order.getOrderActionInd().equals("W") && order.getSubscribedItems().length>0){
				for(int i = 0; i<order.getSubscribedItems().length; i++){
					if(order.getSubscribedItems()[i].getActionInd()!=null
							&& order.getSubscribedItems()[i].getActionInd().equals("D")){
					
					}else{
						selectedIMSVasList = (String[]) ArrayUtils.add(selectedIMSVasList, order.getSubscribedItems()[i].getId());
					}
				}
				logger.info("order.getSubscribedItems().length: " + order.getSubscribedItems().length);
				request.getSession().setAttribute("selectedIMSVasList", selectedIMSVasList);	
				request.getSession().setAttribute("selectedIMSGiftList", selectedIMSVasList);	
			}
		}
				
		System.out.println(gson.toJson(order));
		
		return BasketDetail;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
	throws ServletException, AppRuntimeException {
	
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		List<BasketDetailsDTO> basketDetailsList = new ArrayList<BasketDetailsDTO>();
		List<BasketDetailsDTO> basketPaymentList = new ArrayList<BasketDetailsDTO>();
		List<NowTVCheckDTO> NowTVCheckList = new ArrayList<NowTVCheckDTO>();
	
		String locale = RequestContextUtils.getLocale(request).toString();
		
		String Serbdyno = order.getInstallAddress().getSerbdyno();

		String basketID=request.getParameter("basketID");
		
		boolean IsPreInstall = false;
		boolean IsCreditCard = false;
		boolean IsCash = false;
		boolean IsElligibleForNowTV = false;
		String IsCoupon = "";
		String TVType = "";
		
		String nextView = "imsnowtv.html";
		
		if(order.getNowTvFormType()!=null){
			if(!order.getNowTvFormType().equals("PON List")&&!order.getNowTvFormType().equals("PCD List")){
				nextView = nextView + "?TVList=" + order.getNowTvFormType();
			}
		}else
		{
			nextView = nextView + "?TVList=null";
		}
		//System.out.println("order.getNowTvFormType(): " + order.getNowTvFormType());
		//System.out.println("Next View: " + nextView);
		
		BasketDetailUI BasketDetail = (BasketDetailUI)command;
		
		List<ServiceDetailDTO> ServiceDetailList = order.getInstallAddress().getServiceDetailList();
		
		if(order.getSubscribedItems()!=null&&order.getSubscribedItems().length>0&&basketID==null){
			logger.info("order.getSubscribedItems()[0].getBasketId()"+order.getSubscribedItems()[0].getBasketId());
			if(order.getSubscribedItems()[0].getBasketId()!=null){
				if(!order.getSubscribedItems()[0].getBasketId().equals(""))
					basketID = order.getSubscribedItems()[0].getBasketId();
			}
		}
		
		basketDetailsList = service.getBasketDetailsList(locale, basketID);
		basketPaymentList = service.getBasketPaymentList(basketID);
		String[] VASList = (String[])request.getParameterValues("VASItemBox");
		String[] GiftList = (String[])request.getParameterValues("GiftItemBox");
		List<SubscribedItemUI> VASTypeList = new ArrayList<SubscribedItemUI>();
		List<SubscribedItemUI> GiftTypeList = new ArrayList<SubscribedItemUI>();
		List<SubscribedItemUI> SubItemList = new ArrayList<SubscribedItemUI>();
		SubscribedItemUI SubItem = new SubscribedItemUI();
		List<ComponentUI> ComponentList = new ArrayList<ComponentUI>();
		ComponentUI Component = new ComponentUI();
		String Technology = "";
		List<String> SelectedVASList = new ArrayList<String>();
		List<String> SelectedGiftList = new ArrayList<String>();
		
		
		
		List<SubscribedCslOfferDTO> SubCslOfferList = new ArrayList<SubscribedCslOfferDTO>();
		List<SubscribedCslOfferDTO> SubImsVasParmList = new ArrayList<SubscribedCslOfferDTO>();

		SubscribedCslOfferDTO SubscribedImsVasParm = new SubscribedCslOfferDTO();
		SubscribedCslOfferDTO SubscribedCslOffer = new SubscribedCslOfferDTO();
		
		List<VasParmDTO> vasParmDTOList =  new ArrayList<VasParmDTO>();
		
		//kinman
		if(BasketDetail.getBasketDetailList() !=null){
			for(int i=0;i<BasketDetail.getBasketDetailList().size();i++){
				if(BasketDetail.getBasketDetailList().get(i).getVasParmDTO()!=null){
					SubscribedCslOffer = new SubscribedCslOfferDTO();
					try {
						BeanUtils.copyProperties(SubscribedCslOffer, BasketDetail.getBasketDetailList().get(i).getVasParmDTO());
					} catch (Exception e) {
						logger.error("Exception caught in copy base VasParmDTO to extended SubscribedCslOfferDTO", e);
					}
					SubCslOfferList.add(SubscribedCslOffer);
				}
		}
		}	
		
		
		
		logger.debug("###########VASList : " + new Gson().toJson(VASList));
		if(!"A".equalsIgnoreCase(order.getSupremeFsInd())){
			order.setSupremeFsInd("N");
		}
		order.setTngRebateInd("N");
		NowTVCheckList = service.getNowTVCheckList(Serbdyno, basketDetailsList.get(0).getTechnology()); 
		if(VASList!=null){
			order.setProcessVms(null);
			order.setProcessWifi(null);
			for(int j=0; j<VASList.length; j++){
				SelectedVASList.add(VASList[j]);
				for(int i=0;i<BasketDetail.getBVasDetailList().size();i++){
					if(VASList[j].equals(BasketDetail.getBVasDetailList().get(i).getItemId())){
						SubItem = new SubscribedItemUI();
						SubItem.setActionInd("A");
						SubItem.setBasketId(basketID);
						SubItem.setType(BasketDetail.getBVasDetailList().get(i).getItemType());
						SubItem.setId(BasketDetail.getBVasDetailList().get(i).getItemId());
						VASTypeList.add(SubItem);
						if(BasketDetail.getBVasDetailList().get(i).getInputValue()!=null){
							Component = new ComponentUI();
							Component.setActionInd("A");
							Component.setAttbId(BasketDetail.getBVasDetailList().get(i).getAttbId());
							Component.setAttbValue(BasketDetail.getBVasDetailList().get(i).getInputValue());
							ComponentList.add(Component);
							order.setProcessWifi("P");
						}
						//kinman
						if(BasketDetail.getBVasDetailList().get(i).getVasParmDTO()!=null){
							SubscribedCslOffer = new SubscribedCslOfferDTO();
							try {
								BeanUtils.copyProperties(SubscribedCslOffer, BasketDetail.getBVasDetailList().get(i).getVasParmDTO());
							} catch (Exception e) {
								logger.error("Exception caught in copy base VasParmDTO to extended SubscribedCslOfferDTO", e);
							}
							SubCslOfferList.add(SubscribedCslOffer);
						}
						//break;
					}
				}
				for(int i=0;i<BasketDetail.getVasDetailList().size();i++){
					if(VASList[j].equals(BasketDetail.getVasDetailList().get(i).getItemId())){
						SubItem = new SubscribedItemUI();
						SubItem.setActionInd("A");
						SubItem.setBasketId("");
						SubItem.setType(BasketDetail.getVasDetailList().get(i).getItemType());
						if(BasketDetail.getVasDetailList().get(i).getItemType().equals("MEDIA")){
							order.setProcessVms("P");
						}
						if(BasketDetail.getVasDetailList().get(i).getItemType().equals("SUP_FIELD")){
							order.setSupremeFsInd("V");
						}
						if(BasketDetail.getVasDetailList().get(i).getItemType().equals("TNG_REBATE")){
							order.setTngRebateInd("Y");
						}
						SubItem.setId(BasketDetail.getVasDetailList().get(i).getItemId());
						VASTypeList.add(SubItem);
						if(BasketDetail.getVasDetailList().get(i).getInputValue()!=null){
							Component = new ComponentUI();
							Component.setActionInd("A");
							Component.setAttbId(BasketDetail.getVasDetailList().get(i).getAttbId());
							Component.setAttbValue(BasketDetail.getVasDetailList().get(i).getInputValue());
							ComponentList.add(Component);
							order.setProcessWifi("P");
						}
						//break;
						//kinman
						if(BasketDetail.getVasDetailList().get(i).getVasParmDTO()!=null){
							SubscribedCslOffer = new SubscribedCslOfferDTO();
							try {
								BeanUtils.copyProperties(SubscribedCslOffer, BasketDetail.getVasDetailList().get(i).getVasParmDTO());
							} catch (Exception e) {
								logger.error("Exception caught in copy base VasParmDTO to extended SubscribedCslOfferDTO", e);
							}
							SubCslOfferList.add(SubscribedCslOffer);
						}
						//Tony imsVasParm
						if(BasketDetail.getVasDetailList().get(i).getImsVasParmDTO()!=null){
							SubscribedImsVasParm = new SubscribedCslOfferDTO();
							try {
								BeanUtils.copyProperties(SubscribedImsVasParm, BasketDetail.getVasDetailList().get(i).getImsVasParmDTO());
							} catch (Exception e) {
								logger.error("Exception caught in copy base VasParmDTO to extended SubscribedCslOfferDTO", e);
							}
							SubImsVasParmList.add(SubscribedImsVasParm);
						}
						//waive ISF/ASF by VAS
						if(("Y".equalsIgnoreCase(BasketDetail.getVasDetailList().get(i).getWaiveISFInd())&&"I".equalsIgnoreCase(order.getTmpOTChrgType()))
								||("Y".equalsIgnoreCase(BasketDetail.getVasDetailList().get(i).getWaiveASFInd())&&"A".equalsIgnoreCase(order.getTmpOTChrgType()))){
							order.setServiceWaiver("V");
						}
						if(BasketDetail.getVasDetailList().get(i).getPaymentMtd()!=null && "Credit Card".equalsIgnoreCase(BasketDetail.getVasDetailList().get(i).getPaymentMtd())){
							IsCreditCard = true;
						}
					}
				}
			}
			
			
		}else{
			order.setProcessVms(null);
			order.setProcessWifi(null);
		}
		
		//if supremeFsInd changed, clear the appointment
		if(order.getOldSupremeFsInd()!=null&&
				!(("A".equalsIgnoreCase(order.getOldSupremeFsInd())||"V".equalsIgnoreCase(order.getOldSupremeFsInd()))?"Y":"N").equals(("A".equalsIgnoreCase(order.getSupremeFsInd())||"V".equalsIgnoreCase(order.getSupremeFsInd()))?"Y":"N")){
			AppointmentUI appointment = new AppointmentUI();
			appointment.setActionInd("A");
			order.setAppointment(appointment);
		}
		order.setOldSupremeFsInd(order.getSupremeFsInd());
		order.setBasketPageVisitInd("Y");
			
		
		String nowOneBoxInd = "N";
    	if(SelectedVASList!=null && SelectedVASList.size()>0){
    		request.getSession().setAttribute("selectedIMSVasList", request.getParameterValues("VASItemBox"));	
//    		BasketDetail.setExclusiveItemList(service.getExclusiveItemList(locale, SelectedVASList)); 
    		BasketDetail.setNowExclusiveItemList(service.getNowExclusiveItemList(locale, SelectedVASList)); 
    		if(BasketDetail.getNowExclusiveItemList()!=null&&BasketDetail.getNowExclusiveItemList().size()>0) nowOneBoxInd = "Y";
    		logger.info("SelectedVASList.size(): " + SelectedVASList.size());
    	}
    	order.setPcdNowOneBoxInd(nowOneBoxInd);
    	
    	int req_inst_date = 999;
    	
    	order.setOptInInd("N");
    	order.setGiftWithCampaignSubInd("N");
    	//add gift Tony
    	if (GiftList!=null&&BasketDetail.getGiftList()!=null){
			for(int j=0; j<GiftList.length; j++){
				SelectedGiftList.add(GiftList[j]);
				for(int i=0;i<BasketDetail.getGiftList().size();i++){
					if(GiftList[j].equals(BasketDetail.getGiftList().get(i).getId())){
						
						if(BasketDetail.getGiftList().get(i).getCampaignCdList()!=null&&BasketDetail.getGiftList().get(i).getCampaignCdList().size()>0){
							order.setGiftWithCampaignSubInd("Y");
							for(int k=0;k<BasketDetail.getGiftList().get(i).getCampaignCdList().size();k++){
							SubItem = new SubscribedItemUI();
							SubItem.setActionInd("A");
							SubItem.setBasketId("");
							SubItem.setType(BasketDetail.getGiftList().get(i).getType());
							if(BasketDetail.getGiftList().get(i).getType().equals("OPT_GIFT")){
								order.setOptInInd("Y");
								logger.info("OPT_GIFT selected.");
							}
							logger.info("gift date "+BasketDetail.getGiftList().get(i).getRequiredInstDate());
							if(BasketDetail.getGiftList().get(i).getRequiredInstDate()!=null&&Integer.parseInt(BasketDetail.getGiftList().get(i).getRequiredInstDate())<req_inst_date){
								req_inst_date = Integer.parseInt(BasketDetail.getGiftList().get(i).getRequiredInstDate());
							}
							if(("Y".equalsIgnoreCase(BasketDetail.getGiftList().get(i).getWaiveISFInd())&&"I".equalsIgnoreCase(order.getTmpOTChrgType()))
									||("Y".equalsIgnoreCase(BasketDetail.getGiftList().get(i).getWaiveASFInd())&&"A".equalsIgnoreCase(order.getTmpOTChrgType()))){
								order.setServiceWaiver("G");
							}
							if(BasketDetail.getGiftList().get(i).getPaymentMethod()!=null && "Credit Card".equalsIgnoreCase(BasketDetail.getGiftList().get(i).getPaymentMethod())){
								IsCreditCard = true;
							}
							SubItem.setCampaignCd(BasketDetail.getGiftList().get(i).getCampaignCdList().get(k).getCampaignCd());
							SubItem.setPackCd(BasketDetail.getGiftList().get(i).getCampaignCdList().get(k).getPackCd());
							SubItem.setTopUpCd(BasketDetail.getGiftList().get(i).getCampaignCdList().get(k).getTopUpCd());
							SubItem.setId(BasketDetail.getGiftList().get(i).getId());
							GiftTypeList.add(SubItem);
							}
							
						}else{
						
						SubItem = new SubscribedItemUI();
						SubItem.setActionInd("A");
						SubItem.setBasketId("");
						SubItem.setType(BasketDetail.getGiftList().get(i).getType());
						if(BasketDetail.getGiftList().get(i).getType().equals("OPT_GIFT")){
							order.setOptInInd("Y");
							logger.info("OPT_GIFT selected.");
						}
						logger.info("gift date "+BasketDetail.getGiftList().get(i).getRequiredInstDate());
						if(BasketDetail.getGiftList().get(i).getRequiredInstDate()!=null&&Integer.parseInt(BasketDetail.getGiftList().get(i).getRequiredInstDate())<req_inst_date){
							req_inst_date = Integer.parseInt(BasketDetail.getGiftList().get(i).getRequiredInstDate());
						}
						if(("Y".equalsIgnoreCase(BasketDetail.getGiftList().get(i).getWaiveISFInd())&&"I".equalsIgnoreCase(order.getTmpOTChrgType()))
								||("Y".equalsIgnoreCase(BasketDetail.getGiftList().get(i).getWaiveASFInd())&&"A".equalsIgnoreCase(order.getTmpOTChrgType()))){
							order.setServiceWaiver("G");
						}
						if(BasketDetail.getGiftList().get(i).getPaymentMethod()!=null && "Credit Card".equalsIgnoreCase(BasketDetail.getGiftList().get(i).getPaymentMethod())){
							IsCreditCard = true;
						}
						SubItem.setId(BasketDetail.getGiftList().get(i).getId());
						GiftTypeList.add(SubItem);
						}
						
						if(BasketDetail.getGiftList().get(i).getDediVASList()!=null&&BasketDetail.getGiftList().get(i).getDediVASList().size()>0){
							for(VASDetailUI vas:BasketDetail.getGiftList().get(i).getDediVASList()){
								SubItem = new SubscribedItemUI();
								SubItem.setActionInd("A");
								SubItem.setBasketId("");
								SubItem.setType(vas.getItemType());
								SubItem.setId(vas.getItemId());
								GiftTypeList.add(SubItem);
							}
						}
						
						if(BasketDetail.getGiftList().get(i).getGiftAttbList()!=null){
							for(AttbDTO attb:BasketDetail.getGiftList().get(i).getGiftAttbList()){	
								Component = new ComponentUI();
								Component.setActionInd("A");
								Component.setAttbId(attb.getAttbId());
								Component.setAttbValue(attb.getInputValue());
								ComponentList.add(Component);
							}
						}
					}
				}
			}
    		
    	}
    	
    	if(BasketDetail.getPromoGiftCode()!=null){
    		logger.info("@@@promotion gift: "+BasketDetail.getPromoGiftCode());
			
			if(BasketDetail.getPromoGiftList()!=null&&BasketDetail.getPromoGiftList().size()>0){
				for(int i=0;i<BasketDetail.getPromoGiftList().size();i++){
					if(BasketDetail.getPromoGiftCode().equals(BasketDetail.getPromoGiftList().get(i).getPromoCode())){
						logger.info("promo gift date "+BasketDetail.getPromoGiftList().get(i).getRequiredInstDate());
						if(BasketDetail.getPromoGiftList().get(i).getRequiredInstDate()!=null&&Integer.parseInt(BasketDetail.getPromoGiftList().get(i).getRequiredInstDate())<req_inst_date){
							req_inst_date = Integer.parseInt(BasketDetail.getPromoGiftList().get(i).getRequiredInstDate());
						}
						SubItem = new SubscribedItemUI();
						SubItem.setActionInd("A");
						SubItem.setBasketId("");
						SubItem.setType(BasketDetail.getPromoGiftList().get(i).getType());
						SubItem.setId(BasketDetail.getPromoGiftList().get(i).getId());
						GiftTypeList.add(SubItem);
					}
				}
			}
    	}
    	
    	order.setGiftInstDate(Integer.toString(req_inst_date));

		logger.info("giftcommdate "+order.getGiftInstDate());
		
    	if(SelectedGiftList!=null && SelectedGiftList.size()>0){
    		request.getSession().setAttribute("SelectedGiftList", request.getParameterValues("GiftItemBox")); 
    		logger.info("SelectedGiftList.size(): " + SelectedGiftList.size());
    	}
		
		if(basketDetailsList!=null){
			for(int i=0; i<basketDetailsList.size(); i++){
				SubItem = new SubscribedItemUI();
				SubItem.setActionInd("A");
				SubItem.setBasketId(basketDetailsList.get(i).getBasketId());
				SubItem.setType(basketDetailsList.get(i).getItemType());
				SubItem.setId(basketDetailsList.get(i).getItemID());			
				SubItemList.add(SubItem);
			}
			if(VASTypeList!=null){
				for(int j=0; j<VASTypeList.size(); j++){			
					SubItemList.add(VASTypeList.get(j));
				}
			}
			if(GiftTypeList!=null){
				for(int j=0; j<GiftTypeList.size(); j++){			
					SubItemList.add(GiftTypeList.get(j));
				}
			}
		}
		// Added back NowTV item from order object
	//	if(order.getOrderActionInd()!=null){
				if(order.getSubscribedItems()!=null){
					for(int i = 0; i<order.getSubscribedItems().length; i++){
						if(order.getSubscribedItems()[i].getType().contains("NTV_")
								|| order.getSubscribedItems()[i].getType().contains("AIO_SUBOWN")
								|| order.getSubscribedItems()[i].getType().contains("AIO_RENTAL")
								|| order.getSubscribedItems()[i].getType().contains("HDD_PREM")){
							//order.getSubscribedItems()[i].setActionInd("U");
							SubItemList.add(order.getSubscribedItems()[i]);
							logger.info("order.getSubscribedItems()["+i+"].getId(): " + order.getSubscribedItems()[i].getId()+
									"  getType(): " + order.getSubscribedItems()[i].getType());			
//						}else {
//							for(int j=0;j<SubItemList.size();j++){
//								if(order.getSubscribedItems()[i].getId().equals(SubItemList.get(j).getId())){
//									SubItemList.get(j).setActionInd("U");
//									order.getSubscribedItems()[i].setActionInd("U");
//								}else if(order.getSubscribedItems()[i].getActionInd()==null){
//									order.getSubscribedItems()[i].setActionInd("D");
//								}else if(!order.getSubscribedItems()[i].getActionInd().equals("U")){
//										order.getSubscribedItems()[i].setActionInd("D");
//								}		
//							}
						}
					}
//					for(int i = 0; i<order.getSubscribedItems().length; i++){
//						if(order.getSubscribedItems()[i].getActionInd().equals("D")){
//							SubItemList.add(order.getSubscribedItems()[i]);
//						}							
//					}
				}
	//		}
		//}
		
		if(SubItemList!=null){
			SubscribedItemUI[] item = new SubscribedItemUI[SubItemList.size()];
			for(int i=0; i<SubItemList.size(); i++){
				item[i] = new SubscribedItemUI();
				item[i].setActionInd(SubItemList.get(i).getActionInd());
				item[i].setBasketId(SubItemList.get(i).getBasketId());
				item[i].setType(SubItemList.get(i).getType());
				item[i].setCampaignCd(SubItemList.get(i).getCampaignCd());
				item[i].setPackCd(SubItemList.get(i).getPackCd());
				item[i].setTopUpCd(SubItemList.get(i).getTopUpCd());
				item[i].setId(SubItemList.get(i).getId());
			}
			order.setSubscribedItems(item);
		}
		
		order.setComponents(null);
		logger.debug("BasketDetail(): " + gson.toJson(BasketDetail));
		if(ComponentList!=null){
			ComponentUI[] comItem = new ComponentUI[ComponentList.size()];
			for(int i=0; i<ComponentList.size(); i++){
				comItem[i] = new ComponentUI();
				comItem[i].setActionInd(ComponentList.get(i).getActionInd());
				comItem[i].setAttbId(ComponentList.get(i).getAttbId());
				comItem[i].setAttbValue(ComponentList.get(i).getAttbValue());


				for (VASDetailUI bvas:BasketDetail.getBVasDetailList()){
					if(ComponentList.get(i).getAttbId().equals(bvas.getAttbId())){
						comItem[i].setItemId(bvas.getItemId());
					}
				}
				for (VASDetailUI vas:BasketDetail.getVasDetailList()){
					if(ComponentList.get(i).getAttbId().equals(vas.getAttbId())){
						comItem[i].setItemId(vas.getItemId());
					}
				}
				if (BasketDetail.getGiftList()!=null){
					for (GiftUI gift:BasketDetail.getGiftList()){
						for(AttbDTO attb:gift.getGiftAttbList()){	
							if(ComponentList.get(i).getAttbId().equals(attb.getAttbId())){
								comItem[i].setItemId(gift.getId());
							}
						}
					}
				}

				order.setComponents(comItem);
			}
		}
		logger.info("getComponents(): " + gson.toJson(order.getComponents()));
		
		//kinman
		order.setSubscribedCslOffers(null);
		if(SubCslOfferList!=null){
			logger.info("SubCslOfferList: " + gson.toJson(SubCslOfferList));
			SubscribedCslOfferDTO[] cslOffer = new SubscribedCslOfferDTO[SubCslOfferList.size()];
			for(int i=0; i<SubCslOfferList.size(); i++){
				if(SubCslOfferList.get(i).getVas_type().equalsIgnoreCase("MOBILEPIN")){
				cslOffer[i] = new SubscribedCslOfferDTO();
				cslOffer[i] = SubCslOfferList.get(i);				
				order.setSubscribedCslOffers(cslOffer);
				}
			}
		}
		order.setSubscribedImsVasParm(null);
		if(SubImsVasParmList!=null){
			logger.info("SubImsVasParmList: " + gson.toJson(SubImsVasParmList));
			SubscribedCslOfferDTO[] imsVasParm = new SubscribedCslOfferDTO[SubImsVasParmList.size()];
			for(int i=0; i<SubImsVasParmList.size(); i++){
				imsVasParm[i] = new SubscribedCslOfferDTO();
				imsVasParm[i] = SubImsVasParmList.get(i);				
				order.setSubscribedImsVasParm(imsVasParm);
			}
		}
		logger.info("order.getSubscribedCslOffers(): " + gson.toJson(order.getSubscribedCslOffers()));
		logger.info("order.getSubscribedImsVasParm(): " + gson.toJson(order.getSubscribedImsVasParm()));
		
		
	
		System.out.println(gson.toJson(order));
		
		if(basketDetailsList!=null&&basketDetailsList.size()>0){
			order.setBandwidth(String.valueOf(basketDetailsList.get(0).getBandwidth()));
			Technology = basketDetailsList.get(0).getTechnology();
			order.setTermExt(basketDetailsList.get(0).getTermExt());
			//order.setOtInstallChrg(basketDetailsList.get(0).getOnetimeAmt());

			if(Float.valueOf(basketDetailsList.get(0).getBandwidth())<18 && !Technology.equals("PON")){
				TVType = "SD";
			}else{
				TVType = "HD";
			}
		}
		
		if (ServiceDetailList!=null){
			for(int l=0; l<ServiceDetailList.size();l++)
			{	
				if(basketDetailsList!=null&&basketDetailsList.size()>0){
				if(ServiceDetailList.get(l).getTechnology().equals(basketDetailsList.get(0).getTechnology())
						&&ServiceDetailList.get(l).getTechnologySupported().equals("Y")){
					order.getInstallAddress().getAddrInventory().setResourceShortage(ServiceDetailList.get(l).getIsResrcShort());
				}
				}
			}
		}
		
		if(basketDetailsList!=null&&basketDetailsList.size()>0){
			if(order.getNowTvFormType()==null){
				order.setNowTvFormType(basketDetailsList.get(0).getNowTvFormType());
			}
			if(basketDetailsList.get(0).getTechnology()!=null){
				if (order.getInstallAddress().getAddrInventory().getTechnology()!=null){
					if(!order.getInstallAddress().getAddrInventory().getTechnology().equals(basketDetailsList.get(0).getTechnology())){
						AppointmentUI appointment = new AppointmentUI();
						appointment.setActionInd("A");
						order.setAppointment(appointment);
					}
				}
				order.getInstallAddress().getAddrInventory().setTechnology(basketDetailsList.get(0).getTechnology());
				
			}
		
			if(basketDetailsList.get(0).getTechnology().equals("PON"))
			{
				order.setMovingChrg("680");
			}else{
				order.setMovingChrg("300");
			}
		}
		
		if(basketPaymentList!=null&&basketPaymentList.size()>0){
			if(basketPaymentList.size()==1){
				if(basketPaymentList.get(0).getItemType().equals("Credit Card")){
					IsCreditCard = true;
				}else if (!IsCreditCard) IsCash = true;
			}			
		}
		
		
		if(BasketDetail.getBVasDetailList()!=null&&BasketDetail.getBVasDetailList().size()>0){
			for(int i=0;i<BasketDetail.getBVasDetailList().size();i++){
				if(BasketDetail.getBVasDetailList().get(i).getItemType().equals("PRE_INST")){
					IsPreInstall = true;
					break;
				}
			}
		}
		
		
		if(IsCreditCard){
			order.setIsCreditCardOffer("Y");
		}else{
			order.setIsCreditCardOffer("N");
		}
		
		
		logger.info("Tech: "+ order.getInstallAddress().getAddrInventory().getTechnology());
		
		order.setWarrPeriod(basketDetailsList.get(0).getContractPeriod());
		
		if(basketDetailsList!=null&&basketDetailsList.size()>0){

			if(IsPreInstall){
				order.setPrepayCC("800");
				order.setPrepayCash("800");	

				if(!order.getInstallAddress().getAddrInventory().getTechnology().equals("PON")){
					if(order.getInstallAddress().getHousingTypeList()!=null){
						for(int i=0;i<order.getInstallAddress().getHousingTypeList().size();i++){
							if(order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals("PH")
									|| order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals("HOS")
									|| order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals("PH_AO")
									|| order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals("HOS_AO")){
								order.setPrepayCC("500");
								order.setPrepayCash("500");
								break;
							}
						}
					}
				}
			}else if(!order.getWarrPeriod().equals("0")){
				order.setPrepayCC(basketDetailsList.get(0).getRecurrentAmt());
				order.setPrepayCash("800");

				if(!order.getInstallAddress().getAddrInventory().getTechnology().equals("PON")
						&& order.getInstallAddress().getHousingTypeList()!=null){
					for(int i=0;i<order.getInstallAddress().getHousingTypeList().size();i++){
						if(order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals("PH")
								|| order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals("HOS")
								|| order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals("PH_AO")
								|| order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals("HOS_AO")){
							order.setPrepayCash("500");
							break;
						}
					}
				}
			}else if(order.getWarrPeriod().equals("0")){
				order.setPrepayCC(basketDetailsList.get(0).getMthToMthRate());
				order.setPrepayCash("800");	
				
				if(!order.getInstallAddress().getAddrInventory().getTechnology().equals("PON")
						&& order.getInstallAddress().getHousingTypeList()!=null){
					for(int i=0;i<order.getInstallAddress().getHousingTypeList().size();i++){
						if(order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals("PH")
								|| order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals("HOS")
								|| order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals("PH_AO")
								|| order.getInstallAddress().getHousingTypeList().get(i).getHousingType().equals("HOS_AO")){
							order.setPrepayCash("500");
							break;
						}
					}
				}
			}else{
				order.setPrepayCC("800");
				order.setPrepayCash("800");	
			}
			
			IsElligibleForNowTV = service.checkEligibleForNowTV(basketID);
			
			logger.info("Serbdyno:" +Serbdyno);
		//	logger.info("getNTV_srv_cd_found(): "+ NowTVCheckList.get(0).getNTV_srv_cd_found());
		//	logger.info("getMax_BB(): "+ NowTVCheckList.get(0).getMax_BB());
			
			if(//Float.valueOf(basketDetailsList.get(0).getBandwidth())<3
					NowTVCheckList.size() == 0
					|| IsPreInstall 
					|| !IsElligibleForNowTV 
					|| basketDetailsList.get(0).getContractPeriod().equals("0")
					|| ("Y".equalsIgnoreCase(order.getPcdLike100Ind())&&("Y".equalsIgnoreCase(order.getPreInstallInd())||"Y".equalsIgnoreCase(order.getPreUseInd())))
					|| ("3".equalsIgnoreCase(basketDetailsList.get(0).getBandwidth())&&"N".equalsIgnoreCase(order.getInstallAddress().getAddrInventory().getH264Ind()))
				//	|| (basketDetailsList.get(0).getBandwidth().equals("3") 
				//			&& NowTVCheckList.get(0).getNTV_srv_cd_found().equals("N") 
							//&& NowTVCheckList.get(0).getMax_BB().equals("3")
							//) 
					){
				if ("Y".equals(order.isSignoffed()) && !StringUtils.equals(order.getIsPT(), "Y")){ 
					
					//Remove all the NTV_ items in amendment flow
					if(order.getSubscribedItems()!=null){
						List<SubscribedItemUI> AmdSubItemList = new ArrayList<SubscribedItemUI>();
						for(int i = 0; i<order.getSubscribedItems().length; i++){
							if(!order.getSubscribedItems()[i].getType().contains("NTV_")
									&& !order.getSubscribedItems()[i].getType().contains("AIO_")){
								AmdSubItemList.add(order.getSubscribedItems()[i]);
								logger.info("order.getSubscribedItems()["+i+"].getId(): " + order.getSubscribedItems()[i].getId());
								logger.info("order.getSubscribedItems()["+i+"].getType(): " + order.getSubscribedItems()[i].getType());			
							}
						}
						if(AmdSubItemList!=null&&AmdSubItemList.size()>0){
							SubscribedItemUI[] sitem = new SubscribedItemUI[AmdSubItemList.size()];
							for(int i=0; i<AmdSubItemList.size(); i++){
								sitem[i] = new SubscribedItemUI();
								sitem[i].setActionInd(AmdSubItemList.get(i).getActionInd());
								sitem[i].setBasketId(AmdSubItemList.get(i).getBasketId());
								sitem[i].setType(AmdSubItemList.get(i).getType());
								sitem[i].setId(AmdSubItemList.get(i).getId());
							}
							order.setSubscribedItems(sitem);
						}else{
							order.setSubscribedItems(null);
						}
					}
					
					nextView = "imspayment.html";
				}
				else if ("Y".equals(order.isSignoffed())) nextView = "imsamendprogoffer.html";
				else nextView = "imsinstallation.html";
				order.setNowTvFormType(null);
				order.setDecodeType(null);
				order.setProcessVim(null);
				order.setIsCreditCardOfferNowTVPage(null);
				order.setServiceWaiverNowTVPage(null);
			}
			request.getSession().setAttribute("IMS_ContractPeriod", basketDetailsList.get(0).getContractPeriod());	

		
			for(int i =0;i<basketDetailsList.size();i++){
				if(basketDetailsList.get(i).getIsCouponPlan().equals('Y')){
					IsCoupon = basketDetailsList.get(i).getIsCouponPlan();
					break;
				}
				else{
					IsCoupon = basketDetailsList.get(i).getIsCouponPlan();
				}
			}
		}
		
		
		logger.info("IsCoupon: " + IsCoupon);
		logger.info("IsPreInstall: " + IsPreInstall);
		logger.info("IsCreditCard: " + IsCreditCard);
		logger.info("IsCash: " + IsCash);

		logger.info("Next View: " + nextView);
		
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);
		request.getSession().setAttribute("selectedIMSVasList", request.getParameterValues("VASItemBox"));
		request.getSession().setAttribute("selectedIMSGiftList", request.getParameterValues("GiftItemBox"));
		request.getSession().setAttribute("IMS_IsCouponBasket", IsCoupon);
		request.getSession().setAttribute("IMSTVType", TVType);
		request.getSession().setAttribute("IMSTechnology", Technology);
		
		request.getSession().setAttribute("cslNumPinMissing",BasketDetail.getCslNumPinMissing());

		logger.info("session order: "+ gson.toJson(order));
		return new ModelAndView(new RedirectView(nextView));
		
		
	}	
	
	
	public void BasketDetailJoinCslOfferbyProdIds(OrderImsUI order, List<BasketDetailsDTO> basketDetailsList, String productIds){
		
		logger.info("productIds: "+productIds);
		
		List<VasParmDTO> coreOfferVasParmDTOs = new ArrayList<VasParmDTO>(); 	
		if(!"".equals(productIds)){
			coreOfferVasParmDTOs = imsBasketCommonService.getVasParmsByProdId(productIds, "NONONLINE", "en");
		}
			
		logger.info("imsBasketCommonService.getVasParmsByProdId(): "+  gson.toJson(coreOfferVasParmDTOs));
						
		if(basketDetailsList!=null && coreOfferVasParmDTOs != null){
			for(VasParmDTO i:coreOfferVasParmDTOs){
				for(BasketDetailsDTO j:basketDetailsList){		
					if(StringUtils.isNotEmpty(j.getProductId()) 
						&& StringUtils.isNotEmpty(i.getProd_id())
						&& j.getProductId().equals(i.getProd_id())){
							j.setVasParmDTO(i);
							j.getVasParmDTO().setItem_id(j.getItemID());
					}				
				}
			}
		
			logger.info("add coreOfferVasParmDTOs to basketDetailsList: " +  gson.toJson(basketDetailsList));
				
			if(order.getSubscribedCslOffers() != null){
					
				logger.info("session ~ order.getSubscribedCslOffers(): " + gson.toJson(order.getSubscribedCslOffers() ));		
				for(SubscribedCslOfferDTO i:order.getSubscribedCslOffers()){
					for(BasketDetailsDTO j:basketDetailsList){	
						if(j.getVasParmDTO() != null  &&  i.getProd_id().equals(j.getVasParmDTO().getProd_id())){
							logger.info("add to core offer ");
							if(StringUtils.isNotEmpty(i.getVas_parm_a())){
								j.getVasParmDTO().setVas_parm_a(i.getVas_parm_a());
							}
							if(StringUtils.isNotEmpty(i.getVas_parm_b())){
								j.getVasParmDTO().setVas_parm_b(i.getVas_parm_b());
							}
							if(StringUtils.isNotEmpty(i.getVas_parm_c())){
								j.getVasParmDTO().setVas_parm_c(i.getVas_parm_c());
							}
						}		
					}					
				}
				
				logger.info("after add order.getSubscribedCslOffers() to basketDetailsList: " + gson.toJson(basketDetailsList));
			}
		
		}	
	}




	public void VASDetailJoinCslOfferbyProdIds(OrderImsUI order, List<VASDetailUI> VASDetailList, List<VasParmDTO> imsVasParmList, String productIds){
		
		logger.info("productIds: "+productIds);
		
		List<VasParmDTO> VASDetailListVasParmDTOs = new ArrayList<VasParmDTO>(); 	
		if(!"".equals(productIds)){
			VASDetailListVasParmDTOs = imsBasketCommonService.getVasParmsByProdId(productIds, "NONONLINE", "en");
		}
		
		logger.info("imsBasketCommonService.getVasParmsByProdId(): "+  gson.toJson(VASDetailListVasParmDTOs));
		
		if(VASDetailList!=null && VASDetailListVasParmDTOs != null){
			for(VasParmDTO i:VASDetailListVasParmDTOs){
				for(VASDetailUI j:VASDetailList){		
					if(StringUtils.isNotEmpty(j.getProductId()) 
					   && StringUtils.isNotEmpty(i.getProd_id())
					   && j.getProductId().equals(i.getProd_id())){
							j.setVasParmDTO(i);
							j.getVasParmDTO().setItem_id(j.getItemId());
							
							if(imsVasParmList!=null){
								for(int k=0;k<imsVasParmList.size();k++){
									if(i.getVas_type()!=null&&i.getVas_type().equalsIgnoreCase(imsVasParmList.get(k).getVas_type())
											&&j.getItemType()!=null&&j.getItemType().equalsIgnoreCase(imsVasParmList.get(k).getItem_type())){
										VasParmDTO imsVasParmDto = new VasParmDTO();
										try {
											BeanUtils.copyProperties(imsVasParmDto, imsVasParmList.get(k));
										}  catch (Exception e) {
											logger.error("Exception caught in copy base VasParmDTO to extended SubscribedCslOfferDTO", e);
										}
										j.setImsVasParmDTO(imsVasParmDto);
										j.getImsVasParmDTO().setItem_id(j.getItemId());
										j.getImsVasParmDTO().setProd_id(j.getProductId());
										logger.info("set item_id for: "+j.getItemId()+" "+j.getImsVasParmDTO().getItem_id());
										logger.info("set item_id for: "+j.getProductId()+" "+j.getImsVasParmDTO().getProd_id());
										break;
									}
								}
							}
					}
				}
			}
			
			logger.debug("add VASDetailListVasParmDTOs to VASDetailList: " +  gson.toJson(VASDetailList));
			
			if(order.getSubscribedCslOffers() != null){
				
				logger.info("session ~ order.getSubscribedCslOffers(): " + gson.toJson(order.getSubscribedCslOffers() ));
				
				for(SubscribedCslOfferDTO i:order.getSubscribedCslOffers()){
					for(VASDetailUI j:VASDetailList){	
						if(j.getVasParmDTO() != null  &&  i.getProd_id().equals(j.getVasParmDTO().getProd_id())){
							logger.info("add to vas");
							if(StringUtils.isNotEmpty(i.getVas_parm_a())){
								j.getVasParmDTO().setVas_parm_a(i.getVas_parm_a());
							}
							if(StringUtils.isNotEmpty(i.getVas_parm_b())){
								j.getVasParmDTO().setVas_parm_b(i.getVas_parm_b());
							}
							if(StringUtils.isNotEmpty(i.getVas_parm_c())){
								j.getVasParmDTO().setVas_parm_c(i.getVas_parm_c());
							}							
						}		
					}					
				}
			}
			logger.debug("after add order.getSubscribedCslOffers to VASDetailList: " + gson.toJson(VASDetailList));
			

			
			if(order.getSubscribedImsVasParm() != null){
				
				logger.info("session ~ order.getSubscribedImsVasParm(): " + gson.toJson(order.getSubscribedImsVasParm()));
				
				for(VasParmDTO i:order.getSubscribedImsVasParm()){
					for(VASDetailUI j:VASDetailList){	
						if(j.getImsVasParmDTO() != null  &&  i.getProd_id().equals(j.getImsVasParmDTO().getProd_id())){
							logger.info("add to vas");
							if(StringUtils.isNotEmpty(i.getVas_parm_a())){
								j.getImsVasParmDTO().setVas_parm_a(i.getVas_parm_a());
							}
							if(StringUtils.isNotEmpty(i.getVas_parm_b())){
								j.getImsVasParmDTO().setVas_parm_b(i.getVas_parm_b());
							}
							if(StringUtils.isNotEmpty(i.getVas_parm_c())){
								j.getImsVasParmDTO().setVas_parm_c(i.getVas_parm_c());
							}							
						}		
					}					
				}
			}
			
			

		}			
	}


	
	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
		BasketDetailUI BasketDetail = (BasketDetailUI)command;
		String[] GiftList = (String[])request.getParameterValues("GiftItemBox");
		String[] VASList = (String[])request.getParameterValues("VASItemBox");
		Locale Locale = RequestContextUtils.getLocale(request);
		String locale = Locale.toString();
		List<String> SelectedVASList = new ArrayList<String>();
		List<String> SelectedGiftList = new ArrayList<String>();
		
		OrderImsUI sessionOrder = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		String sb = sessionOrder.getInstallAddress().getSerbdyno();
		
		for (String err: validateGiftSelect(BasketDetail, GiftList,Locale, errors)) {
			errors.rejectValue("subscribedGiftsError", "", err);
			logger.info("gift select error "+err);
		}
		
		if(VASList!=null){
			for(int i=0; i<VASList.length; i++){
				SelectedVASList.add(VASList[i]);
			}
		}
		
		if(GiftList!=null){
			for(int i=0; i<GiftList.length; i++){
				if(GiftList[i]!=null&&!GiftList[i].isEmpty()){
					SelectedGiftList.add(GiftList[i]);
					for(GiftUI gift:BasketDetail.getGiftList()){
						if(GiftList[i].equals(gift.getId())){
							if(gift.getDediVASList()!=null&&gift.getDediVASList().size()>0){
								for(VASDetailUI vas:gift.getDediVASList()){
									SelectedVASList.add(vas.getItemId());
								}
							}
						}
					}
				}
			}
		}

		List<VASDetailUI> exclusiveList = null;
		List<VASDetailUI> giftExclusiveList = null;
		List<VASDetailUI> nowExclusiveList = null;
		List<VASDetailUI> giftExProgList = null;
		List<VASDetailUI> vasExclusiveGiftList = null;
		List<VASDetailUI> vasExclusiveGiftTypeList = null;
		
		if(SelectedVASList!=null&&SelectedVASList.size()>0){
			
			logger.info("SelectedVASList: "+gson.toJson(SelectedVASList));	
			
			Boolean isHDDPurchased=false;
			Boolean pcdNowOneBoxInd=false;
			for(String cur:SelectedVASList){
				for(VASDetailUI vas:BasketDetail.getVasDetailList()){
					if(cur.equalsIgnoreCase(vas.getItemId())){
						if(vas.getItemType().equalsIgnoreCase("HDD"))
							isHDDPurchased=true;
						if(vas.getItemType().equalsIgnoreCase("AIO_PUR_FS"))
							pcdNowOneBoxInd=true;
						
						//checking vas attb input length
						if(vas.getImsVasParmDTO()!=null){
							
							String valid_a = "Y";
							String valid_b = "Y";
							String valid_c = "Y";

							if(vas.getImsVasParmDTO().getVas_parm_a_input_format()!=null&&!vas.getImsVasParmDTO().getVas_parm_a_input_format().isEmpty()){
								valid_a = validateVasInput(vas.getImsVasParmDTO().getVas_parm_a(),vas.getImsVasParmDTO().getVas_parm_a_input_format());
							}
							if(vas.getImsVasParmDTO().getVas_parm_b_input_format()!=null&&!vas.getImsVasParmDTO().getVas_parm_b_input_format().isEmpty()){
								valid_b = validateVasInput(vas.getImsVasParmDTO().getVas_parm_b(),vas.getImsVasParmDTO().getVas_parm_b_input_format());
							}
							if(vas.getImsVasParmDTO().getVas_parm_c_input_format()!=null&&!vas.getImsVasParmDTO().getVas_parm_c_input_format().isEmpty()){
								valid_c = validateVasInput(vas.getImsVasParmDTO().getVas_parm_c(),vas.getImsVasParmDTO().getVas_parm_c_input_format());
							}
							
							if((vas.getImsVasParmDTO().getVas_parm_a_min_length()!=null
									&&Integer.parseInt(vas.getImsVasParmDTO().getVas_parm_a_min_length())>vas.getImsVasParmDTO().getVas_parm_a().length())
								||
								(vas.getImsVasParmDTO().getVas_parm_a_max_length()!=null
										&&Integer.parseInt(vas.getImsVasParmDTO().getVas_parm_a_max_length())<vas.getImsVasParmDTO().getVas_parm_a().length())
								||valid_a.equalsIgnoreCase("N")){

								String errStrA = constructErrStr(vas.getImsVasParmDTO().getVas_parm_a_min_length(), vas.getImsVasParmDTO().getVas_parm_a_max_length(), 
										vas.getImsVasParmDTO().getVas_parm_a_input_format(),Locale);
								errors.rejectValue("vasDetailList["+BasketDetail.getVasDetailList().indexOf(vas)+"].imsVasParmDTO.vas_parm_a", "", errStrA);
							}
							if((vas.getImsVasParmDTO().getVas_parm_b_min_length()!=null
									&&Integer.parseInt(vas.getImsVasParmDTO().getVas_parm_b_min_length())>vas.getImsVasParmDTO().getVas_parm_b().length())
								||
								(vas.getImsVasParmDTO().getVas_parm_b_max_length()!=null
										&&Integer.parseInt(vas.getImsVasParmDTO().getVas_parm_b_max_length())<vas.getImsVasParmDTO().getVas_parm_b().length())
								||valid_b.equalsIgnoreCase("N")){
								
								String errStrB = constructErrStr(vas.getImsVasParmDTO().getVas_parm_b_min_length(), vas.getImsVasParmDTO().getVas_parm_b_max_length(), 
										vas.getImsVasParmDTO().getVas_parm_b_input_format(),Locale);
								errors.rejectValue("vasDetailList["+BasketDetail.getVasDetailList().indexOf(vas)+"].imsVasParmDTO.vas_parm_b", "", errStrB);
							}
							if((vas.getImsVasParmDTO().getVas_parm_c_min_length()!=null
									&&Integer.parseInt(vas.getImsVasParmDTO().getVas_parm_c_min_length())>vas.getImsVasParmDTO().getVas_parm_c().length())
								||
								(vas.getImsVasParmDTO().getVas_parm_c_max_length()!=null
										&&Integer.parseInt(vas.getImsVasParmDTO().getVas_parm_c_max_length())<vas.getImsVasParmDTO().getVas_parm_c().length())
								||valid_c.equalsIgnoreCase("N")){

								String errStrC = constructErrStr(vas.getImsVasParmDTO().getVas_parm_c_min_length(), vas.getImsVasParmDTO().getVas_parm_c_max_length(), 
										vas.getImsVasParmDTO().getVas_parm_c_input_format(),Locale);
								errors.rejectValue("vasDetailList["+BasketDetail.getVasDetailList().indexOf(vas)+"].imsVasParmDTO.vas_parm_c", "", errStrC);
							}
						}
					}
				}
				
				for(VASDetailUI bvas:BasketDetail.getBVasDetailList())
					if(cur.equalsIgnoreCase(bvas.getItemId()))
						if(bvas.getItemType().equalsIgnoreCase("AIO_PUR_FS"))
							pcdNowOneBoxInd=true;
			}
			if(isHDDPurchased&&!pcdNowOneBoxInd){	
				errors.rejectValue("VASError", "","");
				errors.rejectValue("VASError","ims.pcd.basketdetails.msg.015");
			}
			
			nowExclusiveList = service.getNowExclusiveItemList(locale, SelectedVASList); 
			
			if (nowExclusiveList != null) {
	
				if (nowExclusiveList.size() > 1) {
					if(exclusiveList!=null&&exclusiveList.size() == 0){
						errors.rejectValue("exclusiveError", "ims.pcd.basketdetails.msg.016");//print to the VASdetail header
					}
					errors.rejectValue("VASError", "","");
					errors.rejectValue("VASError", "ims.pcd.basketdetails.msg.017");
					
					for (int i = 0; i < 2; i++) {
						errors.rejectValue("VASError", "","<"+ nowExclusiveList.get(i).getVASTitle()+ ">");
					}
				}
	
			} else {
				//System.out.println("exclusiveItemList==null");
				logger.info("nowExclusiveList==null");
	
			}
		}
		
		if(SelectedGiftList!=null&&SelectedGiftList.size()>0){
			
			int maxSelectedVasPrice =  0;
			for(String cur:SelectedGiftList)
				for(GiftUI gift:BasketDetail.getGiftList()){
					if(cur.equalsIgnoreCase(gift.getId())&& gift.getMinVasPrice()!=null){	
						for(String curr:SelectedVASList){
							for(VASDetailUI vas:BasketDetail.getVasDetailList()){
								if(curr.equalsIgnoreCase(vas.getItemId())&& 
										(Integer.valueOf(vas.getRecurrentAmt())>maxSelectedVasPrice)){
									maxSelectedVasPrice = Integer.valueOf(vas.getRecurrentAmt());
								}
							}
						}
						if(maxSelectedVasPrice<Integer.valueOf(gift.getMinVasPrice())){
							errors.rejectValue("VASError", "","");
							errors.rejectValue("VASError", "",message.getMessage("ims.pcd.basketdetails.msg.022", null, Locale)+" HK$"+ gift.getMinVasPrice());
							errors.rejectValue("VASError", "","<"+gift.getGiftTitlePlainText()+">");
						}
					}
				}
			
			giftExProgList = service.getGiftExclusiveProgItemList(locale, SelectedGiftList, BasketDetail.getBasketDetailList().get(0).getOfferCode());
			
			if (giftExProgList != null) {
	
				if (giftExProgList.size() != 0) {
					errors.rejectValue("exclusiveError", "ims.pcd.basketdetails.msg.018");//print to the VASdetail header
					
					for (int i = 0; i < giftExProgList.size(); i++) {
						errors.rejectValue("VASError", "","");
						errors.rejectValue("VASError", "ims.pcd.basketdetails.msg.021");
						errors.rejectValue("VASError", "","<"+ giftExProgList.get(i).getVASTitle()+ ">");
						
					}
				}
	
			} else {
				//System.out.println("exclusiveItemList==null");
				logger.info("giftExclusiveList==null");
	
			}
		}
		
		if((SelectedVASList!=null&&SelectedVASList.size()>0)||(SelectedGiftList!=null&&SelectedGiftList.size()>0)){
			
			vasExclusiveGiftList = service.getExclusiveItemList(locale,SelectedVASList, SelectedGiftList); 
			
			vasExclusiveGiftTypeList = service.getVasExclusiveGiftType(SelectedVASList,SelectedGiftList,locale);
			
			vasExclusiveGiftList.addAll(vasExclusiveGiftTypeList);
			
			if (vasExclusiveGiftList != null) {
				
				if (vasExclusiveGiftList.size() != 0) {
					errors.rejectValue("exclusiveError", "ims.pcd.basketdetails.msg.018");//print to the VASdetail header
					
					for (int i = 0; i < vasExclusiveGiftList.size(); i++) {
						errors.rejectValue("VASError", "","");
						errors.rejectValue("VASError", "ims.pcd.basketdetails.msg.019");
						errors.rejectValue("VASError", "","<"+ vasExclusiveGiftList.get(i).getVASTitle()+ ">");
						errors.rejectValue("VASError", "","<"+ vasExclusiveGiftList.get(i).getVASTitle_B()+ ">");
						
					}
				}
	
			} else {
				//System.out.println("exclusiveItemList==null");
				logger.info("vasExclusiveGiftList==null");
	
			}
		}
		
		List<String> subProdIdList = getSubPordIdList(BasketDetail, VASList, GiftList);
		
		String subProdIdStr = "";
		String comma = "";
		for (String prodId: subProdIdList){
			subProdIdStr += comma + prodId;
			comma = ",";
		}
		
		if(!StringUtils.isEmpty(LookupTableBean.getInstance().getEnableIOExclusiveCheck())&&"Y".equalsIgnoreCase(LookupTableBean.getInstance().getEnableIOExclusiveCheck())){
		
			for (String err: validateExclusiveRouter(subProdIdList, sb)){
				if(exclusiveList!=null&&exclusiveList.size()==0&&nowExclusiveList!=null&&nowExclusiveList.size()==0){
					errors.rejectValue("exclusiveError","ims.pcd.basketdetails.msg.016");//print to the VASdetail header
				}
						
				errors.rejectValue("VASError", "","");
				errors.rejectValue("VASError", "","<"+ err+ ">");
			}
			
			String errMsg = validateMeshRouter(subProdIdStr);
			
			if(!StringUtils.isEmpty(errMsg)){
				errors.rejectValue("VASError", "","");
				errors.rejectValue("VASError", "","<"+ errMsg+ ">");
			}
		
		}
		
		ValidateOfferResultDTO validateOfferResult = service.validateOfferInBom(subProdIdList.toArray(new String[subProdIdList.size()]));
		
		if(validateOfferResult.getO_result()!=0){
			errors.rejectValue("VASError", "","");
			errors.rejectValue("VASError", "", validateOfferResult.getO_result_msg());
		}
		
		
		/**********************Validate CSL Offer**********************/
		releaseCslOfferNumPin(request);
		CheckCslOfferNumPin(request, command, errors);
		/**********************End Validate CSL Offer**********************/
		
	}
	public List<String> validateGiftSelect(BasketDetailUI BasketDetail, String[] GiftList,Locale locale, BindException errors) {
		List<String> rtnList = new ArrayList<String>();
		
    	if (BasketDetail.getGiftList()!=null){
			for(int i=0;i<BasketDetail.getGiftList().size();i++){
				if(BasketDetail.getGiftSelectCnt().containsKey(BasketDetail.getGiftList().get(i).getType())){
					HashMap<String, Object> tmpCntMap = BasketDetail.getGiftSelectCnt().get(BasketDetail.getGiftList().get(i).getType());
					if("Y".equalsIgnoreCase(BasketDetail.getGiftList().get(i).getShowInd())){	//only count showed gift	
						tmpCntMap.put("GIFT_CNT", (Integer)tmpCntMap.get("GIFT_CNT")+1);
					}
					BasketDetail.getGiftSelectCnt().put(BasketDetail.getGiftList().get(i).getType(), tmpCntMap);
//					logger.info("update gift select cnt map "+BasketDetail.getGiftList().get(i).getType()+" "+BasketDetail.getGiftSelectCnt().get(BasketDetail.getGiftList().get(i).getType()).get("GIFT_CNT"));
				}
				if (GiftList!=null){
					for(int j=0; j<GiftList.length; j++){
						if(GiftList[j].equals(BasketDetail.getGiftList().get(i).getId())){
							//update gift select cnt map
							if(BasketDetail.getGiftSelectCnt().containsKey(BasketDetail.getGiftList().get(i).getType())){
								HashMap<String, Object> selectedCntMap = BasketDetail.getGiftSelectCnt().get(BasketDetail.getGiftList().get(i).getType());
								selectedCntMap.put("SELECTED_CNT", (Integer)selectedCntMap.get("SELECTED_CNT")+1);
								BasketDetail.getGiftSelectCnt().put(BasketDetail.getGiftList().get(i).getType(), selectedCntMap);
//								logger.info("update gift select cnt map "+BasketDetail.getGiftList().get(i).getType()+" "+BasketDetail.getGiftSelectCnt().get(BasketDetail.getGiftList().get(i).getType()).get("SELECTED_CNT"));
							}
							//check gift attb input length
							if(BasketDetail.getGiftList().get(i).getGiftAttbList()!=null){
								for(AttbDTO dto:BasketDetail.getGiftList().get(i).getGiftAttbList()){
									
									String valid_gift = "Y";

									if(dto.getInputFormat()!=null&&!dto.getInputFormat().isEmpty()){
										valid_gift = validateVasInput(dto.getInputValue(),dto.getInputFormat());
									}
									
									if((dto.getMinLength()!=null && Integer.parseInt(dto.getMinLength())>dto.getInputValue().length())
										|| (dto.getMaxLength()!=null && Integer.parseInt(dto.getMaxLength())<dto.getInputValue().length())
										|| valid_gift.equalsIgnoreCase("N")){
										
										String errStr = constructErrStr(dto.getMinLength(), dto.getMaxLength(), dto.getInputFormat(),locale);
										
										errors.rejectValue("giftList["+i+"].giftAttbList["+BasketDetail.getGiftList().get(i).getGiftAttbList().indexOf(dto)+"].inputValue", "", errStr);
									}
								}
							}
						}
					}
				}
			}
    		
    	}

		if(BasketDetail.getPromoGiftList()!=null&&BasketDetail.getPromoGiftList().size()>0){
				for(int i=0;i<BasketDetail.getPromoGiftList().size();i++){
					if(BasketDetail.getGiftSelectCnt().containsKey(BasketDetail.getPromoGiftList().get(i).getType())){
						HashMap<String, Object> tmpCntMap = BasketDetail.getGiftSelectCnt().get(BasketDetail.getPromoGiftList().get(i).getType());
						tmpCntMap.put("GIFT_CNT", (Integer)tmpCntMap.get("GIFT_CNT")+1);
						BasketDetail.getGiftSelectCnt().put(BasketDetail.getPromoGiftList().get(i).getType(), tmpCntMap);
						logger.info("update gift select cnt map "+BasketDetail.getPromoGiftList().get(i).getType()+" "+BasketDetail.getGiftSelectCnt().get(BasketDetail.getPromoGiftList().get(i).getType()).get("GIFT_CNT"));
					}
					if(BasketDetail.getPromoGiftCode()!=null){
						if(BasketDetail.getPromoGiftCode().equals(BasketDetail.getPromoGiftList().get(i).getPromoCode())){
							//update promo gift select cnt map
							if(BasketDetail.getGiftSelectCnt().containsKey(BasketDetail.getPromoGiftList().get(i).getType())){
								HashMap<String, Object> selectedCntMap = BasketDetail.getGiftSelectCnt().get(BasketDetail.getPromoGiftList().get(i).getType());
								selectedCntMap.put("SELECTED_CNT", (Integer)selectedCntMap.get("SELECTED_CNT")+1);
								BasketDetail.getGiftSelectCnt().put(BasketDetail.getPromoGiftList().get(i).getType(), selectedCntMap);
								logger.info("update gift select cnt map "+BasketDetail.getPromoGiftList().get(i).getType()+" "+BasketDetail.getGiftSelectCnt().get(BasketDetail.getPromoGiftList().get(i).getType()).get("SELECTED_CNT"));
							}
						}
				}
			}
    	}
		
		if(BasketDetail.getGiftSelectCnt()!=null){
			
			for (String giftType : BasketDetail.getGiftSelectCnt().keySet()) {
				HashMap<String, Object> map = BasketDetail.getGiftSelectCnt().get(giftType);
				if((Integer)map.get("GIFT_CNT")!=0){
					if((Integer)map.get("SELECTED_CNT")<(Integer)map.get("MIN_CNT")&&(Integer)map.get("GIFT_CNT")>=(Integer)map.get("MIN_CNT")){
						rtnList.add(map.get("MIN_CNT")+" "+map.get("TYPE_DESC").toString().toLowerCase()+" " +message.getMessage("ims.pcd.basketdetails.msg.020", null, locale));
					}
					if((Integer)map.get("SELECTED_CNT")>(Integer)map.get("MAX_CNT")&&(Integer)map.get("MAX_CNT")!=0){
						rtnList.add(map.get("MAX_CNT")+" "+map.get("TYPE_DESC").toString().toLowerCase()+" " +message.getMessage("ims.pcd.basketdetails.msg.020", null, locale));
					}
					if((Integer)map.get("SELECTED_CNT")<(Integer)map.get("MIN_CNT")&&(Integer)map.get("GIFT_CNT")<(Integer)map.get("MIN_CNT")&&(Integer)map.get("SELECTED_CNT")<(Integer)map.get("GIFT_CNT")){
						rtnList.add(map.get("GIFT_CNT")+" "+map.get("TYPE_DESC").toString().toLowerCase()+" " +message.getMessage("ims.pcd.basketdetails.msg.020", null, locale));
					}
				}
//				if((Integer)map.get("MAX_CNT")!=0
//						&&(Integer)map.get("SELECTED_CNT")>(Integer)map.get("MAX_CNT")){
//					rtnList.add("Please select at most "+map.get("MAX_CNT")+" "+map.get("TYPE_DESC").toString().toLowerCase()+".");
//				}
				
			}
			
			
		}

	
		
		return rtnList;
	}
	
	public List<String> getSubPordIdList(BasketDetailUI BasketDetail, String[] VASList, String[] GiftList) {
		
		List<String> subProductIdList = new ArrayList<String>();
		
		if(BasketDetail.getBasketDetailList()!=null){
			for(int i=0; i<BasketDetail.getBasketDetailList().size(); i++){
				if(BasketDetail.getBasketDetailList().get(i).getProductId()!=null&&!"".equalsIgnoreCase(BasketDetail.getBasketDetailList().get(i).getProductId())){
					subProductIdList.add(BasketDetail.getBasketDetailList().get(i).getProductId());
				}
			}
		}
		
		if(VASList!=null){
			for(int j=0;j<VASList.length;j++){
				for(int k=0;k<BasketDetail.getBVasDetailList().size();k++){
					if(VASList[j].equals(BasketDetail.getBVasDetailList().get(k).getItemId())){
						if(BasketDetail.getBVasDetailList().get(k).getProductId()!=null&&!"".equalsIgnoreCase(BasketDetail.getBVasDetailList().get(k).getProductId())){
								subProductIdList.add(BasketDetail.getBVasDetailList().get(k).getProductId());
						}
					}
				}
				
				for(int l=0;l<BasketDetail.getVasDetailList().size();l++){
					if(VASList[j].equals(BasketDetail.getVasDetailList().get(l).getItemId())){
						if(BasketDetail.getVasDetailList().get(l).getProductId()!=null&&!"".equalsIgnoreCase(BasketDetail.getVasDetailList().get(l).getProductId())){
							subProductIdList.add(BasketDetail.getVasDetailList().get(l).getProductId());
						}
					}
				}
			}
		}
		
		if(GiftList!=null){
			for(int m=0;m<GiftList.length;m++){
				for(int n=0;n<BasketDetail.getGiftList().size();n++){
					if(GiftList[m].equals(BasketDetail.getGiftList().get(n).getId())){
						if(BasketDetail.getGiftList().get(n).getDediVASList()!=null&&BasketDetail.getGiftList().get(n).getDediVASList().size()>0){
							for(VASDetailUI vas:BasketDetail.getGiftList().get(n).getDediVASList()){
								if(vas.getProductId()!=null&&!"".equalsIgnoreCase(vas.getProductId())){
									subProductIdList.add(vas.getProductId());
								}
							}
						}
					}
				}
			}
		}

		return subProductIdList;
	}
	
	public List<String> validateExclusiveRouter(List<String> subProductIdList, String sb) {
		List<String> rtnList = new ArrayList<String>();
		

		logger.info("subProductIdList "+new Gson().toJson(subProductIdList));
		logger.info("sb "+sb);
		
		rtnList = imsBasketCommonService.validateExclusiveRouter(subProductIdList.toArray(new String[subProductIdList.size()]), null, null, null, sb, "I", "N", "N");

		logger.info("setBomExclusiveChekingErrMsg "+new Gson().toJson(rtnList));

		return rtnList;
	}
	
	public String validateMeshRouter(String subProductIdStr) {
		String result = "";

		logger.info("subProductIdStr "+subProductIdStr);
		
		result = imsBasketCommonService.validateMeshRouter(subProductIdStr, null, null);

		logger.info("validateMeshRouterErrMsg "+result);

		return result;
	}

	public void releaseCslOfferNumPin(HttpServletRequest request){
		request.getSession().setAttribute("MrtReserveException", null); 
		
		logger.info("ReleaseMobilePIN");
		
		MobileOffer imsMobileOfferUi = new MobileOffer();
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);	
		
		List<MobileOffer> imsMobileOfferUiList = (List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList");
		
		logger.info("ReleaseMobilePIN*** (List<MobileOffer>) request.getSession().getAttribute"+"(imsMobileOfferUiList):"+ gson.toJson((List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList")));
			
		if (imsMobileOfferUiList != null){
			
			for(int i= 0; i<imsMobileOfferUiList.size(); i++){
			
				if(sessionOrder != null && !("").equals(sessionOrder)){
					try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
					}catch(Exception e){
						request.getSession().setAttribute("MrtReserveException", "Y");
						logger.error("Exception caught in releaseMobilePIN()", e);
					}
					logger.info("release old number: " + imsMobileOfferUiList.get(i).mrt + " , old pin: " + imsMobileOfferUiList.get(i).pin);
					
				}else{
					try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(imsMobileOfferUiList.get(i).mrt, imsMobileOfferUiList.get(i).pin);
					}catch(Exception e){
						request.getSession().setAttribute("MrtReserveException", "Y");
						logger.error("Exception caught in releaseMobilePIN()", e);
					}
					logger.info("release old number: " + imsMobileOfferUiList.get(i).mrt + " , old pin: " + imsMobileOfferUiList.get(i).pin);
					
				}
			
				logger.info(gson.toJson(imsMobileOfferUi));
			}
			request.getSession().setAttribute("imsMobileOfferUiList", null);
		}else if(sessionOrder.getSubscribedCslOffers() != null){
			
			SubscribedCslOfferDTO[] csldto = sessionOrder.getSubscribedCslOffers(); 
			
			if(sessionOrder != null && !("").equals(sessionOrder)){
				for(SubscribedCslOfferDTO i:csldto){
					if(i.getVas_parm_a()!= null && !("").equals(i.getVas_parm_a()) && i.getVas_parm_b()!= null && !("").equals(i.getVas_parm_b())){
						try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(i.getVas_parm_a(), i.getVas_parm_b(), sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
						}catch(Exception e){
							request.getSession().setAttribute("MrtReserveException", "Y");
							logger.error("Exception caught in releaseMobilePIN()", e);
						}
						logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);								
					}
				}	
			}else{
				for(SubscribedCslOfferDTO i:csldto){
					if(i.getVas_parm_a()!= null && !("").equals(i.getVas_parm_a()) && i.getVas_parm_b()!= null && !("").equals(i.getVas_parm_b())){
						try{
							imsMobileOfferUi = (MobileOffer) mobilePINService.releaseMobilePIN(i.getVas_parm_a(), i.getVas_parm_b());
						}catch(Exception e){
							request.getSession().setAttribute("MrtReserveException", "Y");
							logger.error("Exception caught in releaseMobilePIN()", e);
						}
						logger.info("release old number: " + imsMobileOfferUi.mrt + " , old pin: " + imsMobileOfferUi.pin);
						}
					}
			}
			
		}
	}
	
	
	public void CheckCslOfferNumPin(HttpServletRequest request, Object command, BindException errors){
		/**********************Check Csl Offer Num Pin**********************/
		BasketDetailUI BasketDetail = (BasketDetailUI)command;
		logger.debug("BasketDetail: "+  gson.toJson(BasketDetail));
		String returntxt = "";
		
		if (BasketDetail != null) {
			 
			List<BasketDetailsDTO> basketDetailList = new ArrayList<BasketDetailsDTO>();
			 basketDetailList  = BasketDetail.getBasketDetailList();
			 if(basketDetailList !=null){
				 for(int i=0; i < basketDetailList.size(); i++){
					 if(basketDetailList.get(i).getVasParmDTO() != null){
						 if(StringUtils.isNotEmpty(basketDetailList.get(i).getVasParmDTO().getVas_parm_a()) || StringUtils.isNotEmpty(basketDetailList.get(i).getVasParmDTO().getVas_parm_b()))
							returntxt = validateCslOfferNumPin(request, basketDetailList.get(i).getVasParmDTO().getVas_parm_a(), basketDetailList.get(i).getVasParmDTO().getVas_parm_b());
							if(StringUtils.isNotEmpty(returntxt)){
						 	errors.rejectValue("basketDetailList["+i+"].vasParmDTO.vas_parm_a", "",returntxt);
							}
					 }
				 }				 
			 }
			 
			 List<VASDetailUI> BVASDetailList = new ArrayList<VASDetailUI>();
			 BVASDetailList = BasketDetail.getBVasDetailList();
			 if(BVASDetailList !=null){
				 for(int i=0; i < BVASDetailList.size(); i++){
					 if(BVASDetailList.get(i).getVasParmDTO() != null){
						 if(StringUtils.isNotEmpty(BVASDetailList.get(i).getVasParmDTO().getVas_parm_a()) || StringUtils.isNotEmpty(BVASDetailList.get(i).getVasParmDTO().getVas_parm_b()))
							 returntxt = validateCslOfferNumPin(request, BVASDetailList.get(i).getVasParmDTO().getVas_parm_a(), BVASDetailList.get(i).getVasParmDTO().getVas_parm_b());
						 if(StringUtils.isNotEmpty(returntxt)){
							 errors.rejectValue("BVasDetailList["+i+"].vasParmDTO.vas_parm_a", "",returntxt);
						 }
					 }
				 }
			 }
			 
			 List<VASDetailUI> vasDetailList = new ArrayList<VASDetailUI>();
			 vasDetailList = BasketDetail.getVasDetailList();
			 if(vasDetailList !=null){
				 for(int i=0; i < vasDetailList.size(); i++){
					 if(vasDetailList.get(i).getVasParmDTO() != null){
						 if(StringUtils.isNotEmpty(vasDetailList.get(i).getVasParmDTO().getVas_parm_a()) || StringUtils.isNotEmpty(vasDetailList.get(i).getVasParmDTO().getVas_parm_b()))
							 returntxt = validateCslOfferNumPin(request, vasDetailList.get(i).getVasParmDTO().getVas_parm_a(), vasDetailList.get(i).getVasParmDTO().getVas_parm_b());
						     if(StringUtils.isNotEmpty(returntxt)){
						    	 errors.rejectValue("vasDetailList["+i+"].vasParmDTO.vas_parm_a", "",returntxt);
						     }	 
					 }
				 }
			 }
		}
	
		
		/**********************End Check Csl Offer Num Pin**********************/
	}
	
	public String validateCslOfferNumPin(HttpServletRequest request, String cslmrt, String cslmrtpin){
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);	
		
		MobileOffer imsMobileOfferUi = new MobileOffer();
		List<MobileOffer> imsMobileOfferUiListTmp = new ArrayList<MobileOffer>();
		String number = cslmrt;
		String pin = cslmrtpin;
		
		logger.info("CheckMobilePIN request of number: "+"["+number+"]"+" and pin:"+"["+pin+"]");
				
		List<MobileOffer> imsMobileOfferUiList = (List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList");
		
		logger.info("BEGIN***** (List<MobileOffer>) request.getSession().getAttribute"+"(imsMobileOfferUiList):"+ gson.toJson((List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList")));
		
		String result = "fail"; 
		
		if (imsMobileOfferUiList != null){
			
			Boolean recordRepeated = false;
			
			for(int i= 0; i<imsMobileOfferUiList.size(); i++){
				
				imsMobileOfferUiListTmp.add(imsMobileOfferUiList.get(i));
				if(number.equals(imsMobileOfferUiList.get(i).mrt) && pin.equals(imsMobileOfferUiList.get(i).pin)){
					recordRepeated = true;
				}	
			}
			
			
			if(recordRepeated){
				result = "success";
			}else{
				if(sessionOrder != null && !("").equals(sessionOrder)){
					try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.getMobilePIN(number, pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
					}catch(Exception e){
						request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception 
						result = "exception"; 
						logger.error("Exception caught in getMobilePIN()", e);
					}
				}else{
					try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.getMobilePIN(number, pin, "NONONLINE");
					}catch(Exception e){
						request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception 
						result = "exception"; 
						logger.error("Exception caught in getMobilePIN()", e);
					}
				}
				
				
				//if status is active
				if(imsMobileOfferUi.status.equals("A") ){
					if(sessionOrder != null && !("").equals(sessionOrder)){
						try{
							imsMobileOfferUi = (MobileOffer) mobilePINService.reserveMobilePIN(number, pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
						}catch(Exception e){
							request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception 
							result = "exception"; 
							logger.error("Exception caught in reserveMobilePIN()", e);
						}
					}else{
						try{
							imsMobileOfferUi = (MobileOffer) mobilePINService.reserveMobilePIN(number, pin);
						}catch(Exception e){
							request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception 
							result = "exception"; 
							logger.error("Exception caught in reserveMobilePIN()", e);
						}	
					}
						
					if(imsMobileOfferUi.status.equals("R") ){				
						result = "success"; 
						
						//add back new record
						imsMobileOfferUiListTmp.add(imsMobileOfferUi);
						
						request.getSession().setAttribute("imsMobileOfferUiList",imsMobileOfferUiListTmp);
						
					}
				}
			}
			logger.info(gson.toJson(imsMobileOfferUi));
		}else{
			if(sessionOrder != null && !("").equals(sessionOrder)){
				try{
					imsMobileOfferUi = (MobileOffer) mobilePINService.getMobilePIN(number, pin, sessionOrder.getCreateBy(), sessionOrder.getOrderId(), "NONONLINE");
				}catch(Exception e){
					request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception 
					result = "exception"; 
					logger.error("Exception caught in getMobilePIN()", e);
				}	
			}else{
				try{
				imsMobileOfferUi = (MobileOffer) mobilePINService.getMobilePIN(number, pin, "NONONLINE");
				}catch(Exception e){
					request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception 
					result = "exception"; 
					logger.error("Exception caught in getMobilePIN()", e);
				}	
			}
			logger.info(gson.toJson(imsMobileOfferUi));
			//if status is active
			
			if(imsMobileOfferUi.status.equals("A") ){
				if(sessionOrder != null && !("").equals(sessionOrder)){
					try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.reserveMobilePIN(number, pin);
						
					}catch(Exception e){
						request.getSession().setAttribute("MrtReserveException", "Y");//unknown error found, throw Exception
						result = "exception"; 
						logger.error("Exception caught in reserveMobilePIN()", e);
					}
				}else{
					try{
						imsMobileOfferUi = (MobileOffer) mobilePINService.reserveMobilePIN(number, pin);
					}catch(Exception e){
						request.getSession().setAttribute("MrtReserveException", "Y"); //unknown error found, throw Exception 
						result = "exception"; 
						logger.error("Exception caught in reserveMobilePIN()", e);
					}
				}
			if(imsMobileOfferUi.status.equals("R") ){				
				result = "success"; 
				
				//add new record
				imsMobileOfferUiListTmp.add(imsMobileOfferUi);
				
				request.getSession().setAttribute("imsMobileOfferUiList",imsMobileOfferUiListTmp);
				
			}
			}
			logger.info(gson.toJson(imsMobileOfferUi));
		}
		logger.info("END***** (List<MobileOffer>) request.getSession().getAttribute"+"(imsMobileOfferUiList):"+ gson.toJson((List<MobileOffer>) request.getSession().getAttribute("imsMobileOfferUiList")));
		
			
		JSONArray jsonArray = new JSONArray();
		
		String returntxt = "";
		
		if(imsMobileOfferUi.rtnErrMessage != null){
			 returntxt = imsMobileOfferUi.rtnErrMessage.toString();
		}
	 
		jsonArray.add("{\"message\":\"" + returntxt	
					 	+ "\", \"result\":\"" + result
					 	+ "\"}");

		
		logger.info("CSL validate API result:"+jsonArray);
		
		return returntxt;		
	}
	
	public String validateVasInput(String input, String type){
		
		String valid = "Y";

		logger.info("checking attribute input: "+input);
		logger.info("checking attribute type: "+type);
		
		if (type.equalsIgnoreCase("NUMBER")){
			
			char[] array = input.toCharArray();
			
			if(array.length==0){
				valid="N";
			}else{
				if(!Pattern.matches("[0-9]+", input)){
					valid="N";
				}
			}
		}else if(type.equalsIgnoreCase("LETTER")){
			if(!Pattern.matches("[a-zA-Z ]+", input)){
				valid="N";
			}
		}
		else if(type.equalsIgnoreCase("MOBILE")){
			
			char[] array = input.toCharArray();
			if(array.length==8){
				if(!(Pattern.matches("[0-9]+", input)&&
						(String.valueOf(array[0]).equalsIgnoreCase("4")||String.valueOf(array[0]).equalsIgnoreCase("5")||String.valueOf(array[0]).equalsIgnoreCase("6")||
								String.valueOf(array[0]).equalsIgnoreCase("7")||String.valueOf(array[0]).equalsIgnoreCase("8")||String.valueOf(array[0]).equalsIgnoreCase("9")))){
					valid="N";
				}
			}else{
				valid="N";
			}
		}
		
		return valid;
		
	}
	
	public String constructErrStr(String min, String max, String inputFormat,Locale locale){
		
		String errStr = "";
		String rangeStr = "";

		if(min==null){
			min = "1";
		}
		
		if(max==null){
			max = "500";
		}
		
		if(min.equalsIgnoreCase(max)){
			rangeStr = min;
		}else{
			rangeStr = min + " to " + max;
		}
		
		if(inputFormat!=null&&!inputFormat.isEmpty()){
			if(inputFormat.equalsIgnoreCase("NUMBER")){
				errStr = "Number only, please input " + rangeStr + " numbers.";
			}
			if(inputFormat.equalsIgnoreCase("LETTER")){
				errStr = "Letter only, please input " + rangeStr + " letters.";
			}
			if(inputFormat.equalsIgnoreCase("TEXT")){
				errStr = "Text only, please input " + rangeStr + " text.";
			}
			if(inputFormat.equalsIgnoreCase("MOBILE")){
				errStr = message.getMessage("ims.pcd.basketdetails.msg.014", null, locale);
			}
		}
		
		return errStr;
	}
	public void setMessage(MessageSource message) {
		this.message = message;
	}
	public MessageSource getMessage() {
		return message;
	}
}
