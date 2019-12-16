package com.bomwebportal.ims.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.AttbDTO;
import com.bomwebportal.ims.dto.ui.ComponentUI;
import com.bomwebportal.ims.dto.ui.GiftUI;
import com.bomwebportal.ims.dto.ui.ImsDecodeTypeUI;
import com.bomwebportal.ims.dto.ui.NowTVOfferUI;
import com.bomwebportal.ims.dto.ui.NowTVUI;
import com.bomwebportal.ims.dto.ui.NowTVVasUI;
import com.bomwebportal.ims.dto.ui.NowTvTopUpUI;
import com.bomwebportal.ims.dto.ui.NowTvVasBundle;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.SubscribedChannelUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.dto.ui.VASDetailUI;
import com.bomwebportal.ims.service.ImsBasketDetailsService;
import com.bomwebportal.ims.service.ImsNowTVService;
import com.bomwebportal.ims.service.ImsNowTVService.NowTvList;
import com.google.gson.Gson;

/*kinman new nowtv*/
import com.bomwebportal.ims.dto.ui.NowTVAddUI;
import com.bomwebportal.ims.dto.ui.NowTvCampaignUI;
import com.bomwebportal.ims.dto.ui.NowTvChannelUI;
import com.bomwebportal.ims.dto.ui.NowTvPackUI;
import com.bomwebportal.bean.LookupTableBean;
/*kinman new nowtv*/

public class ImsNowTVController extends SimpleFormController{
	
    protected final Log logger = LogFactory.getLog(getClass());
    private Gson gson = new Gson();

    private ImsNowTVService service;

    private ImsBasketDetailsService basketService;
    
    public ImsNowTVService getService() {
		return service;
	}
	public void setService(ImsNowTVService service) {
		this.service = service;
	}
	public ImsBasketDetailsService getBasketService() {
		return basketService;
	}
	public void setBasketService(ImsBasketDetailsService basketService) {
		this.basketService = basketService;
	}
	private MessageSource message;
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderImsUI sessionOrder = (OrderImsUI) request.getSession()
				.getAttribute(ImsConstants.IMS_ORDER);
		if (sessionOrder.getNowTvFormType() == null
				|| sessionOrder.getNowTvFormType().equals("")) {
			return new ModelAndView(new RedirectView("imsbasketdetails.html"));
/*		} else if (!sessionOrder.getNowTvFormType().equals("PCD List")
				&& !sessionOrder.getNowTvFormType().equals("PON List")
				&& request.getParameter("TVList")!=null) {
			if(!request.getParameter("TVList").equals("")&&!request.getParameter("TVList").equals("null")){
				return new ModelAndView(new RedirectView("imsnowtv.html?TVList=" + sessionOrder.getNowTvFormType()));
			}else{
				return super.handleRequest(request, response);
			}*/
		}
		else{
			return super.handleRequest(request, response);
		}
	}
			 
	
public Object formBackingObject(HttpServletRequest request) throws ServletException{	
	
		
		logger.debug(request.getSession().getAttribute(ImsConstants.IMS_ORDER)==null?"NULL":"ORDER FOUND");
/*		if (request.getSession().getAttribute(ImsConstants.IMS_ORDER) == null) {
			OrderImsUI tmpOrder = DevService.getNewOrder();
			request.getSession().setAttribute(ImsConstants.IMS_ORDER, tmpOrder);
		}*/
		
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		String inFormType= "";
		String locale = RequestContextUtils.getLocale(request).toString();
		String basketID = (String)request.getSession().getAttribute("IMS_BasketID");
		String NowTVFormType = order.getNowTvFormType();
		String IsCoupon = "";
		String TVTypeStr = "";
		String OtherTVList = "";
		String ContractPeriod = (String)request.getSession().getAttribute("IMS_ContractPeriod");
		boolean IsLamma = false;
		NowTVUI nowTVDetails = new NowTVUI();
		String District = order.getInstallAddress().getDistNo();
		String appDate ="sysdate";
		
		request.setAttribute("lang", locale.toUpperCase());
		
		if(order.getAppDate()!=null){
			SimpleDateFormat dateyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
			appDate = 
				"to_date('" + 
				dateyyyyMMdd.format(order.getAppDate())+
				"','yyyymmdd')";
		}
		logger.info("appDate: "+appDate);
		
		logger.info("NowTVFormType: "+NowTVFormType);
		logger.info("inFormType: " + inFormType);
		
		String[] ChannelItemList = (String[])request.getParameterValues("channelItem");
		String[] EntItemList = (String[])request.getParameterValues("EntItem");
		String[] NowTVVasItemList = (String[])request.getParameterValues("nowTVVASItem");
		String[] NowTVPTItemList = (String[])request.getParameterValues("nowTVPTItem");
		String[] NowTVVasHDItemList = (String[])request.getParameterValues("nowTVVASHDItem");
		String[] NowTVSTBItemList = (String[])request.getParameterValues("nowTVSTBItem");
		String[] NowTVOtherItemList = (String[])request.getParameterValues("nowTVOtherItem");
		String[] NowTVHDItemList = (String[])request.getParameterValues("nowTVHDItem");
		String[] HDChannelItemList = (String[])request.getParameterValues("HDchannelItem");
		String[] AVChannelItemList = (String[])request.getParameterValues("AVchannelItem");
		
		NowTVVasItemList = (String[]) ArrayUtils.addAll(NowTVVasItemList, NowTVVasHDItemList);
		NowTVVasItemList = (String[]) ArrayUtils.addAll(NowTVVasItemList, NowTVOtherItemList);
		NowTVVasItemList = (String[]) ArrayUtils.addAll(NowTVVasItemList, NowTVHDItemList);
		NowTVVasItemList = (String[]) ArrayUtils.addAll(NowTVVasItemList, NowTVSTBItemList);
		NowTVVasItemList = (String[]) ArrayUtils.addAll(NowTVVasItemList, NowTVPTItemList);
		
		ChannelItemList = (String[]) ArrayUtils.addAll(ChannelItemList, HDChannelItemList);
		ChannelItemList = (String[]) ArrayUtils.addAll(ChannelItemList, EntItemList);
		ChannelItemList = (String[]) ArrayUtils.addAll(ChannelItemList, AVChannelItemList);
		
		List<String> SelectedChannelList = new ArrayList<String>();
		List<ImsDecodeTypeUI> decodeTypeList = new ArrayList<ImsDecodeTypeUI>();
		
		if(ChannelItemList!=null){
			for(int j=0; j<ChannelItemList.length; j++){
				SelectedChannelList.add(ChannelItemList[j]);
			}
		}
		
	
		if(request.getParameter("NowTVFromType")!= null){
			inFormType = request.getParameter("NowTVFromType");
		}
		
		OtherTVList = request.getParameter("TVList");
		
		if( order.getSubscribedItems().length > 0  ){
		
			if(("deselect").equals(OtherTVList)){
				
				List<SubscribedItemUI> deleteElements = new ArrayList<SubscribedItemUI>();
				String[] SelectedItemList = (String[])request.getSession().getAttribute("selectedIMSNowVasList");
				
				for(SubscribedItemUI item : order.getSubscribedItems()){
					if(item.getType().indexOf("NTV_SP_MA") > -1 
							|| item.getType().indexOf("NTV_SP_PH") > -1 
							|| item.getType().indexOf("NTV_PAY") > -1 ){
						deleteElements.add(item);
					}
				}
				
				for(SubscribedItemUI element : deleteElements){
					logger.info("Remove element: " + element.getId() + ", " + element.getType());
					order.setSubscribedItems((SubscribedItemUI[]) ArrayUtils.removeElement(order.getSubscribedItems(), element));
					int index = ArrayUtils.indexOf(NowTVVasItemList, element.getId());
					int sessionIndex = ArrayUtils.indexOf(SelectedItemList, element.getId());
					if(index > -1 ){
						NowTVVasItemList = (String[]) ArrayUtils.remove(NowTVVasItemList, index);
					}
					if(sessionIndex > -1){
						SelectedItemList = (String[]) ArrayUtils.remove(SelectedItemList, sessionIndex);
					}
			
				}
				
				
				request.getSession().setAttribute("selectedIMSNowVasList", SelectedItemList);
				OtherTVList = null;
				
			}else{
				
				List<NowTvList> nowTvListMapping = service.getNowTvListCodeMapping();
				
				for(int i = 0; i < order.getSubscribedItems().length ; i++){
					
					logger.info("order.getSubscribedItems()[i]: " + order.getSubscribedItems()[i].getType());

					String description = NowTvListMappingLkup(nowTvListMapping, order.getSubscribedItems()[i].getType());
					if (description != null)
						if(OtherTVList == null || description.equals(OtherTVList))
						{
							OtherTVList = description;
							break;
						}
					
				}
			}
		}
		
		logger.info("OtherTVList:"+OtherTVList);
		logger.info("Subscribed item: " + gson.toJson(order.getSubscribedItems()));
		
		if(order.getNowTvFormType()!=null){
			if(!order.getNowTvFormType().equals("PCD List")
					&&!order.getNowTvFormType().equals("PON List")
					&&!order.getNowTvFormType().equals("PCD List - Lamma")){
				order.setNowTvFormType("PCD List");
				NowTVFormType = "PCD List";
			}else if(District!=null&&District.equals("271")){
				NowTVFormType = "PCD List - Lamma";
				order.setNowTvFormType("PCD List - Lamma");
			}
		}
		
		String housingType = "";
		String SB = "";
		String technology = "";
		String channel = "";
		String progOfferCd = "";

		if(order.getInstallAddress().getHousingTypeList()!=null){
			housingType = order.getInstallAddress().getHousingTypeList().get(0).getHousingType();
			SB = order.getInstallAddress().getSerbdyno();
		}
		technology = order.getInstallAddress().getAddrInventory().getTechnology();
		
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
		progOfferCd = (String) request.getSession().getAttribute("progOfferCd");
		
		String otChrgInd = "";
		
		if(order.getServiceWaiver()!=null&&"B".equalsIgnoreCase(order.getServiceWaiver())){
			otChrgInd = "W";
		}else{
			otChrgInd = order.getTmpOTChrgType();
		}
		
		String staffOfferInd = "";
		staffOfferInd = (String) request.getSession().getAttribute("staffOfferInd");
		
		List<GiftUI> pcdGiftList = new ArrayList<GiftUI>();
		HashMap<String, HashMap<String, Object>> giftSelectCnt = new HashMap<String, HashMap<String, Object>>(); 
		pcdGiftList = basketService.getGiftList(housingType, locale, appDate, SB,technology,channel,order.getPcdLike100Ind(),progOfferCd,otChrgInd,order.getChannelTeamCd(),order.getStaffGroup(),staffOfferInd,"Y");
		
		if(pcdGiftList!=null){
			HashMap<String, Object> normalGiftCntMap = new HashMap<String, Object>();
			for(GiftUI gift:pcdGiftList){ 
						boolean checkBandwidth = false;
						if(gift.getBandwidth()==null){
							checkBandwidth = true;
						}else{
							String[] bandwidthList = gift.getBandwidth().split(",");
							for(String s:bandwidthList){
								if(order.getBandwidth().equals(s)){
									checkBandwidth = true;
								}
							}
						}
						
						if(checkBandwidth){
								GiftUI gifttmp = gift.clone();
								if(order.getComponentsClone()!=null && gifttmp.getGiftAttbList()!=null && gifttmp.getGiftAttbList().size()>0){
									logger.info("set gift attribute for gift " + gifttmp.getId());
									
									for(int j=0; j<order.getComponentsClone().length; j++){
										for(AttbDTO attb:gifttmp.getGiftAttbList()){
											if(order.getComponentsClone()[j].getAttbId().equals(attb.getAttbId())){
												logger.info("attribute: "+order.getComponentsClone()[j].getAttbValue());
												attb.setInputValue(order.getComponentsClone()[j].getAttbValue());
											}
										}
									}
								}
								if(nowTVDetails.getPcdGiftList()==null){
									List<GiftUI> newGiftList = new ArrayList<GiftUI>();
									newGiftList.add(gifttmp);
									nowTVDetails.setPcdGiftList(newGiftList);
								}
								else nowTVDetails.getPcdGiftList().add(gifttmp);
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
			
			if(nowTVDetails.getPcdGiftList()!=null){
				for(GiftUI gift:nowTVDetails.getPcdGiftList()){ 
					gift.setNormalGiftCnt((Integer)normalGiftCntMap.get(gift.getType()));
				}
				giftSelectCnt = basketService.getGiftSelectCnt(channel,locale);

				
				if(giftSelectCnt!=null){
					nowTVDetails.setGiftSelectCnt(giftSelectCnt);
				}
			}
		}
		
		if(OtherTVList != null){
			if(!OtherTVList.equals("") && !OtherTVList.equals("null")){
				nowTVDetails.setOtherTVList(OtherTVList);
			}else {
				nowTVDetails.setOtherTVList(null);
			}
		}else{
			nowTVDetails.setOtherTVList(null);
		}
		
		if(order.getAdultViewAllow()!=null){
			nowTVDetails.setIsAdult(order.getAdultViewAllow());
		}
		else{
			nowTVDetails.setIsAdult("N");
		}
		
		if(District!=null&&District.equals("271")){
			IsLamma = true;
			NowTVFormType = "PCD List - Lamma";
			order.setDecodeType("SD");
		}
		
		if(inFormType!=null){
			if(inFormType.equals("PON2PCD")) {
				NowTVFormType = "PCD List";
			}else if(inFormType.equals("PCD2PON")){
				NowTVFormType = "PON List";
			}
		}

		
		logger.info("locale:" + locale);
		logger.info("ImsNowTVController.formBackingObject() Start");
		
    	BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	if (user==null){
    		user = new BomSalesUserDTO();
    	}
    	logger.info("userId:" + user.getUsername());
		
		//get select list from DB
    	
    	
    	String TVType = (String)request.getSession().getAttribute("IMSTVType");
    	if(TVType.equals("HD")&&TVType!=null){
    		TVTypeStr = "SD','HD";
    	}else{
    		TVTypeStr = "SD";
    	}
    	
    	String contractPeriod = "0','"+order.getWarrPeriod();
    	
    	String HOSTypeStr = order.getInstallAddress().getHousingTypeList().get(0).getHousingType();

    	nowTVDetails.setNowTVStarterList(service.getNowTVStarterList(locale,basketID, appDate));
    	nowTVDetails.setNowTVStarterDescList(service.getNowTVDescList(locale, NowTVFormType, "STARTERPACK", appDate));
       	nowTVDetails.setNowTVEntDescList(service.getNowTVDescList(locale, NowTVFormType, "ENTPACK", appDate));
    	nowTVDetails.setNowTVasList(service.getNowTVVasList(locale, basketID,NowTVFormType, IsCoupon, TVTypeStr, HOSTypeStr,ContractPeriod, appDate,HOSTypeStr));
    	List<NowTVVasUI> nowTVOtherList = service.getNowTVOtherList(locale,contractPeriod, TVTypeStr,appDate,HOSTypeStr,order.getPcdLike100Ind(),order.getPcdNowOneBoxInd(),order.getBandwidth());
    	List<NowTVVasUI> removeList = new ArrayList<NowTVVasUI>();
    	if(order.getPcdNowOneBoxInd().equalsIgnoreCase("Y")&&nowTVOtherList!=null&&nowTVOtherList.size()>0){
    		for(NowTVVasUI ui:nowTVOtherList){
    			if(ui.getItemType().equalsIgnoreCase("AIO_SUBOWN")||ui.getItemType().equalsIgnoreCase("AIO_RENTAL")
    					||ui.getItemType().equalsIgnoreCase("AIO_RENTAL_PREM")||ui.getItemType().equalsIgnoreCase("HDD_PREM")){
    				removeList.add(ui);
    			}
    		}
    	}
    	nowTVOtherList.removeAll(removeList);
    	nowTVDetails.setNowTVOtherList(nowTVOtherList);
    	nowTVDetails.setBandwidth(order.getBandwidth());
    	nowTVDetails.setPtTvList(service.getPTTVList());
    	

    	System.out.println("OtherTVList before form changing: " +OtherTVList);
    	if(OtherTVList != null){
    		if(!OtherTVList.equals("") && !OtherTVList.equals("null")){
    			System.out.println("OtherTVList in TV Channel: " +OtherTVList);
    			nowTVDetails.setNowTVChannelList(service.getNowTVChannelList(locale, OtherTVList, TVTypeStr, IsLamma, appDate));  
    		}else{
    			System.out.println("NowTVFormType in TV Channel: " + NowTVFormType);
    			nowTVDetails.setNowTVChannelList(service.getNowTVChannelList(locale, NowTVFormType, TVTypeStr, IsLamma, appDate));
    		}
    				
		}else{
			System.out.println("NowTVFormType in TV Channel: " + NowTVFormType);
			nowTVDetails.setNowTVChannelList(service.getNowTVChannelList(locale, NowTVFormType, TVTypeStr, IsLamma, appDate));
		}
    	
    	nowTVDetails.setSerbdyno(order.getInstallAddress().getSerbdyno());
    	logger.info("order.getInstallAddress().getSerbdyno(): "+order.getInstallAddress().getSerbdyno());
    	logger.info("nowTVDetails.getSerbdyno(): "+nowTVDetails.getSerbdyno());
    	
    	if(nowTVDetails.getNowTVChannelList()!=null){
    		for(int i=0;i<nowTVDetails.getNowTVChannelList().size();i++){
    			nowTVDetails.getNowTVChannelList().get(i).setChannelDesc(nowTVDetails.getNowTVChannelList().get(i).getChannelDesc().replaceAll(((char)10)+"", "<br>"));
    		}
    	}

    	nowTVDetails.setNowTVHDList(service.getNowTVHDList(locale, NowTVFormType, TVTypeStr, appDate));  
    	
    	if(nowTVDetails.getNowTVStarterDescList().size()>0 && nowTVDetails.getNowTVStarterDescList()!= null){
    		nowTVDetails.setNowTVStarterDesc(nowTVDetails.getNowTVStarterDescList().get(0).getChannelDesc());
    	}
    	
    	setVasTitle(nowTVDetails.getNowTVStarterList());
    	setVasTitle(nowTVDetails.getNowTVVasList());
    	setVasTitle(nowTVDetails.getNowTVOtherList());
    	
    	
    	nowTVDetails.setNowTVFormType(order.getNowTvFormType());
    	nowTVDetails.setIsCouponOffer((String)request.getSession().getAttribute("IMS_IsCouponBasket"));
    	nowTVDetails.setInFormType(inFormType);
    	nowTVDetails.setTechnology((String)request.getSession().getAttribute("IMSTechnology"));
    	
    	String[] selectedIMSNowVasList = new String[0];
    	String[] selectedIMSChannelList = new String[0];
		if(order.getOrderActionInd()!=null){
			if(order.getOrderActionInd().equals("W") && order.getSubscribedItems()!=null){
				if(order.getSubscribedItems().length>0){
					for(int i = 0; i<order.getSubscribedItems().length; i++)
					{		
						if(order.getSubscribedItems()[i].getType().contains("NTV_")){
							if(order.getSubscribedItems()[i].getType().contains("NTV_HD")){
								nowTVDetails.setHDAct("Y");
							}
						}
						logger.info("order.getSubscribedItems()["+i+"].getId(): " + order.getSubscribedItems()[i].getId());
						logger.info("order.getSubscribedItems()["+i+"].getType(): " + order.getSubscribedItems()[i].getType());	
						System.out.println(gson.toJson(order.getSubscribedItems()));
					}
					logger.info("order.getSubscribedItems().length: " + order.getSubscribedItems().length);
					logger.info("selectedIMSNowVasList.length: " + selectedIMSNowVasList.length);
					request.getSession().setAttribute("selectedIMSNowVasList", selectedIMSNowVasList);	
				}
			}
			if(order.getOrderActionInd().equals("W") && order.getSubscribedChannels()!=null){
				if(order.getSubscribedChannels().length>0){
					for(int i = 0; i<order.getSubscribedChannels().length; i++)
					{
						selectedIMSChannelList = (String[]) ArrayUtils.add(selectedIMSChannelList, order.getSubscribedChannels()[i].getChannelId());
					}
					logger.info("order.getSubscribedChannels().length: " + order.getSubscribedChannels().length);
					request.getSession().setAttribute("selectedIMSChannelList", selectedIMSChannelList);	
				}
			}
		}
		
    	if(SelectedChannelList!=null && SelectedChannelList.size()>0){
			request.getSession().setAttribute("selectedIMSChannelList", ChannelItemList);
			request.getSession().setAttribute("selectedIMSNowVasList", NowTVVasItemList);
    		nowTVDetails.setExclusiveList(service.getExclusiveList(locale, SelectedChannelList, appDate)); 
    		logger.info("SelectedChannelList.size(): " + SelectedChannelList.size());
    	}
    	 
		
		if(nowTVDetails.getNowTVStarterDescList()!=null)
		{
			for(int i =0;i<nowTVDetails.getNowTVStarterDescList().size();i++){
				nowTVDetails.getNowTVStarterDescList().get(i).setChannelDesc(nowTVDetails.getNowTVStarterDescList().get(i).getChannelDesc().replaceAll(((char)10)+"", " "));
			}
		}
		
		/*
		logger.info("order.getDecodeType(): "+order.getDecodeType());
		
		if(order.getDecodeType()==null){
			//if(order.getNowTvFormType().equals("PON List")){
			//	order.setDecodeType("HD");
			//}else {
				order.setDecodeType("SD");
			//}
		}
			
		nowTVDetails.setDecodeType(order.getDecodeType());
		
		decodeTypeList = service.getDecodeType();
		nowTVDetails.setDecodeTypeList(decodeTypeList);
		String code;
		for(int i=0; i<nowTVDetails.getDecodeTypeList().size(); i++){
			code = nowTVDetails.getDecodeTypeList().get(i).getCode();
			logger.info("getDecodeTypeList: "+code);
		}
    	
    		
    	
    	if(OtherTVList!=null){
    		if(!OtherTVList.equals("")&&!OtherTVList.equals("null")){
    			order.setNowTvFormType(OtherTVList);
//				request.getSession().setAttribute("selectedIMSChannelList", null);
//				request.getSession().setAttribute("selectedIMSNowVasList", null);
    		}
    	}
    	
    	request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);
    	*/
    	
		if(order.getTvPriceInd() == null ){
			if ("Y".equals(LookupTableBean.getInstance().getNewTvPriceCutOff()) ) {
				order.setTvPriceInd("Y");
			}else {
				order.setTvPriceInd("N");
			}
		}
		

		String[] GiftList = (String[])request.getParameterValues("PcdNtvGiftItemBox");
		List<String> selectedPcdNtvGiftList = new ArrayList<String>();

		
		if(GiftList!=null){
			for(int j=0; j<GiftList.length; j++){
				selectedPcdNtvGiftList.add(GiftList[j]);
			}
		}
    	
    	if(selectedPcdNtvGiftList!=null && selectedPcdNtvGiftList.size()>0){
    		request.getSession().setAttribute("selectedPcdNtvGiftList", GiftList);
    		logger.info("selectedPcdNtvGiftList.size(): " + selectedPcdNtvGiftList.size());
    	}
		
		logger.info("order.getTvPriceInd(): "+order.getTvPriceInd());

		if (order.getTvPriceInd() != null && "Y".equals(order.getTvPriceInd())) {
			List<SubscribedItemUI> AddUIsubItems = new ArrayList<SubscribedItemUI>();
			
			for (int i= 0; i<order.getSubscribedItems().length;i++) {
				if (order.getSubscribedItems()[i].getType().contains("NTV_") 
						|| order.getSubscribedItems()[i].getType().contains("AIO_SUBOWN")
						|| order.getSubscribedItems()[i].getType().contains("AIO_RENTAL")
						|| order.getSubscribedItems()[i].getType().contains("HDD_PREM")) {
					SubscribedItemUI AddUIitem = new SubscribedItemUI();
					AddUIitem.setId(order.getSubscribedItems()[i].getId());
					AddUIitem.setBasketId(order.getSubscribedItems()[i].getBasketId());
					AddUIsubItems.add(AddUIitem);
					if(order.getSubscribedItems()[i].getType().contains("NTV_HD")){
						nowTVDetails.setHDAct("Y");
					}
					if(order.getSubscribedItems()[i].getType().contains("NTV_OTHER")
							|| order.getSubscribedItems()[i].getType().contains("AIO_SUBOWN")
							|| order.getSubscribedItems()[i].getType().contains("AIO_RENTAL")
							|| order.getSubscribedItems()[i].getType().contains("HDD_PREM")
							|| order.getSubscribedItems()[i].getType().contains("NTV_SD")
							|| order.getSubscribedItems()[i].getType().contains("NTV_HD")){
						selectedIMSNowVasList = (String[]) ArrayUtils.add(selectedIMSNowVasList, order.getSubscribedItems()[i].getId());
					}
					nowTVDetails.setSelectedVas(selectedIMSNowVasList);
				}
			}
			logger.info("AddUIsubItems:       123155235"+gson.toJson(AddUIsubItems));
			
			nowTVDetails.setNtvPricingInd(order.getTvPriceInd());

			nowTVDetails.setNowTVAddUI(service.getNewTVPricingDtl(order,"zh_HK".equalsIgnoreCase(RequestContextUtils.getLocale(request).toString())?"zh_HK":"en",false,null));
			nowTVDetails.getNowTVAddUI().loadNowTVAddUISettings(AddUIsubItems);
			
			//add back removed duplicate hard bundle of premier combo
			if (nowTVDetails.getNowTVAddUI().getHardCampaign() != null) {
				if (nowTVDetails.getNowTVAddUI().getHardCampaign().getNumOfSelectedPack() < nowTVDetails.getNowTVAddUI().getHardCampaign().getMin_select_cnt()) {
					if (nowTVDetails.getNowTVAddUI().getSelectedPacks() != null && nowTVDetails.getNowTVAddUI().getSelectedPacks().size() != 0) {
						List<String> campaignList = service.getPremierComboCampaignCode();
						for(NowTvPackUI hardPack:nowTVDetails.getNowTVAddUI().getHardCampaign().getTvPacks()){
							for(NowTvCampaignUI payCampaign:nowTVDetails.getNowTVAddUI().getPayTvCampaign()){
								for(NowTvPackUI payPack:payCampaign.getPacks()){
									if(payPack.isSelected()&&!hardPack.isSelected()&&payPack.getPackCd_SOPHIE().equalsIgnoreCase(hardPack.getPackCd_SOPHIE())
											&&campaignList.contains(payPack.getCampaignCd_SOPHIE())){
										if (nowTVDetails.getNowTVAddUI().getHardCampaign().getNumOfSelectedPack()<nowTVDetails.getNowTVAddUI().getHardCampaign().getMin_select_cnt()){
											hardPack.setSelected(true);
										}
									}
								}
							}
						}
					}
				}
			}
			
			
				
			nowTVDetails.setContractPeriod(order.getWarrPeriod());
			nowTVDetails.setBandwidth(order.getBandwidth());
		} else {
			nowTVDetails.setNtvPricingInd("N");
			
			logger.info("order.getDecodeType(): "+order.getDecodeType());
			
			if(order.getDecodeType()==null){
				//if(order.getNowTvFormType().equals("PON List")){
				//	order.setDecodeType("HD");
				//}else {
					order.setDecodeType("SD");
				//}
			}
				
			nowTVDetails.setDecodeType(order.getDecodeType());
			
			decodeTypeList = service.getDecodeType();
			nowTVDetails.setDecodeTypeList(decodeTypeList);
			String code;
			for(int i=0; i<nowTVDetails.getDecodeTypeList().size(); i++){
				code = nowTVDetails.getDecodeTypeList().get(i).getCode();
				logger.info("getDecodeTypeList: "+code);
			}
			
			if(OtherTVList!=null){
	    		if(!OtherTVList.equals("")&&!OtherTVList.equals("null")){
	    			order.setNowTvFormType(OtherTVList);
//					request.getSession().setAttribute("selectedIMSChannelList", null);
//					request.getSession().setAttribute("selectedIMSNowVasList", null);
	    		}
	    	}
	    	
	    	request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);
		}
		logger.debug("order ntv list ():   "+gson.toJson(nowTVDetails));
		return nowTVDetails;
	}
	

	
	private String NowTvListMappingLkup(List<NowTvList> nowTvListMapping, String code){
		
		if(nowTvListMapping != null && !nowTvListMapping.isEmpty())
			for(NowTvList map : nowTvListMapping){
				if(code.equals(map.listCode))
					return map.listDescription;
			}
		
		return null;
	}

	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
	throws ServletException, AppRuntimeException {
		
		String nextView = "imsinstallation.html";

		logger.info("Next View: " + nextView);
		String locale = RequestContextUtils.getLocale(request).toString();
		

		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
	
		String[] ChannelItemList = (String[])request.getParameterValues("channelItem");
		String[] EntItemList = (String[])request.getParameterValues("EntItem");
		String[] NowTVStarterItemList = (String[])request.getParameterValues("nowTVStarterItem");
		String[] NowTVVasItemList = (String[])request.getParameterValues("nowTVVASItem");
		String[] NowTVPTItemList = (String[])request.getParameterValues("nowTVPTItem");
		String[] NowTVVasHDItemList = (String[])request.getParameterValues("nowTVVASHDItem");
		String[] NowTVOtherItemList = (String[])request.getParameterValues("nowTVOtherItem");
		String[] NowTVHDItemList = (String[])request.getParameterValues("nowTVHDItem");
		String[] NowTVSTBItemList = (String[])request.getParameterValues("nowTVSTBItem");
		String[] HDChannelItemList = (String[])request.getParameterValues("HDchannelItem");
		String[] AVChannelItemList = (String[])request.getParameterValues("AVchannelItem");
		String[] FreeHDChannelItemList = (String[])request.getParameterValues("FreeHDchannelItem");
		String[] AdultView = (String[])request.getParameterValues("AVSelect");
		List<SubscribedItemUI> subItems = new ArrayList<SubscribedItemUI>();
		List<SubscribedChannelUI> subChannels = new ArrayList<SubscribedChannelUI>();
		NowTVUI nowTVDetails = (NowTVUI)command;
		List<String> SelectedChannelList = new ArrayList<String>();

		String[] GiftList = (String[])request.getParameterValues("PcdNtvGiftItemBox");
		List<String> SelectedGiftList = new ArrayList<String>();
		List<ComponentUI> ComponentList = new ArrayList<ComponentUI>();
		ComponentUI Component = new ComponentUI();
		List<SubscribedItemUI> GiftTypeList = new ArrayList<SubscribedItemUI>();
		
		NowTVOtherItemList = (String[]) ArrayUtils.addAll(NowTVOtherItemList, NowTVHDItemList);
		NowTVOtherItemList = (String[]) ArrayUtils.addAll(NowTVOtherItemList, NowTVSTBItemList);
		String appDate ="sysdate";
		
		if(order.getAppDate()!=null){
			SimpleDateFormat dateyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
			appDate = 
				"to_date('" + 
				dateyyyyMMdd.format(order.getAppDate())+
				"','yyyymmdd')";
		}
		logger.info("appDate: "+appDate);
		
		NowTVVasItemList = (String[]) ArrayUtils.addAll(NowTVVasItemList, NowTVVasHDItemList);
		NowTVVasItemList = (String[]) ArrayUtils.addAll(NowTVVasItemList,NowTVPTItemList);
		ChannelItemList = (String[]) ArrayUtils.addAll(ChannelItemList, HDChannelItemList);
		ChannelItemList = (String[]) ArrayUtils.addAll(ChannelItemList, EntItemList);
		ChannelItemList = (String[]) ArrayUtils.addAll(ChannelItemList, AVChannelItemList);
		
		logger.info("FreeHD: " + nowTVDetails.getFreeHD());
		logger.info("DecodeType: " + nowTVDetails.getDecodeType());

		if(nowTVDetails.getFreeHD()!=null && nowTVDetails.getFreeHD().equals("Y")){
			ChannelItemList = (String[]) ArrayUtils.addAll(ChannelItemList, FreeHDChannelItemList);
		}
		
		if(AdultView!=null && AdultView.length>0){
			order.setAdultViewAllow("Y");
			nowTVDetails.setIsAdult("Y");
		}else{
			order.setAdultViewAllow("N");
			nowTVDetails.setIsAdult("N");
		}
		
		List<SubscribedChannelUI> ChannelList = new ArrayList<SubscribedChannelUI>();
		SubscribedChannelUI ChannelItem = new SubscribedChannelUI();
		List<SubscribedItemUI> VASList = new ArrayList<SubscribedItemUI>();
		SubscribedItemUI SubItem = new SubscribedItemUI();
	
		List<NowTVVasUI> StrartEntList = new ArrayList<NowTVVasUI>();
		if(nowTVDetails.getNowTVStarterDescList()!=null){
			StrartEntList.addAll(nowTVDetails.getNowTVStarterDescList());
		}
		if(nowTVDetails.getNowTVEntDescList()!=null){
			StrartEntList.addAll(nowTVDetails.getNowTVEntDescList());
			
		}
		
		if(ChannelItemList!=null){
			for(int j=0; j<ChannelItemList.length; j++){
				for(int i=0;i<nowTVDetails.getNowTVChannelList().size();i++){
					if(ChannelItemList[j].equals(nowTVDetails.getNowTVChannelList().get(i).getChannelID())){
						if(i!=0){
							if(nowTVDetails.getNowTVChannelList().get(i).getChannelID().equals(nowTVDetails.getNowTVChannelList().get(i-1).getChannelID())){}
							else{
								SelectedChannelList.add(ChannelItemList[j]);
								ChannelItem = new SubscribedChannelUI();
								ChannelItem.setActionInd("A");
								ChannelItem.setChannelId(nowTVDetails.getNowTVChannelList().get(i).getChannelID());
								ChannelItem.setChannelCd(nowTVDetails.getNowTVChannelList().get(i).getChannelCD());
								ChannelList.add(ChannelItem);
							}			
						}else{
							SelectedChannelList.add(ChannelItemList[j]);
							ChannelItem = new SubscribedChannelUI();
							ChannelItem.setActionInd("A");
							ChannelItem.setChannelId(nowTVDetails.getNowTVChannelList().get(i).getChannelID());
							ChannelItem.setChannelCd(nowTVDetails.getNowTVChannelList().get(i).getChannelCD());
							ChannelList.add(ChannelItem);
						}

						if(nowTVDetails.getNowTVChannelList().get(i).getChildChID()!=null){
							if(nowTVDetails.getNowTVChannelList().get(i).getChildChTVType().equals("HD")&&!nowTVDetails.getHDAct().equals("Y")){
								
							}
							else{
								ChannelItem = new SubscribedChannelUI();
								ChannelItem.setActionInd("A");
								ChannelItem.setChannelId(nowTVDetails.getNowTVChannelList().get(i).getChildChID());
								ChannelItem.setChannelCd(nowTVDetails.getNowTVChannelList().get(i).getChildChCD());
								ChannelList.add(ChannelItem);
							}
						}
						//break;
					}
				}
				for(int i=0;i<nowTVDetails.getNowTVHDList().size();i++){
					if(ChannelItemList[j].equals(nowTVDetails.getNowTVHDList().get(i).getChannelID())){
						SelectedChannelList.add(ChannelItemList[j]);
						ChannelItem = new SubscribedChannelUI();
						ChannelItem.setActionInd("A");
						ChannelItem.setChannelId(nowTVDetails.getNowTVHDList().get(i).getChannelID());
						ChannelItem.setChannelCd(nowTVDetails.getNowTVHDList().get(i).getChannelCD());
						ChannelList.add(ChannelItem);
					}
				}
			}
		}
		
		System.out.print("ChannelList: "+gson.toJson(ChannelList));
		System.out.print("ChannelItemList: "+gson.toJson(ChannelItemList));
		System.out.print("SelectedChannelList: "+gson.toJson(SelectedChannelList));
		
		if(ChannelItemList!=null){
			for(int j=0; j<ChannelItemList.length; j++){
				for(int i=0;i<StrartEntList.size();i++){
					if(ChannelItemList[j].equals(StrartEntList.get(i).getChannelID())){
						SelectedChannelList.add(ChannelItemList[j]);
						ChannelItem = new SubscribedChannelUI();
						ChannelItem.setActionInd("A");
						ChannelItem.setChannelId(StrartEntList.get(i).getChannelID());
						ChannelItem.setChannelCd("");
						ChannelList.add(ChannelItem);
						break;
					}
				}
			}
		}
		
		if(SelectedChannelList!=null && SelectedChannelList.size()>0){
			request.getSession().setAttribute("selectedIMSChannelList", ChannelItemList);
			request.getSession().setAttribute("selectedIMSNowVasList", NowTVVasItemList);
    		nowTVDetails.setExclusiveList(service.getExclusiveList(locale, SelectedChannelList, appDate)); 
    		logger.info("SelectedChannelList.size(): " + SelectedChannelList.size());
    	}
		
		if(ChannelList!=null){
			SubscribedChannelUI[] ChItem = new SubscribedChannelUI[ChannelList.size()];
			for(int i=0; i<ChannelList.size(); i++){
				ChItem[i] = ChannelList.get(i);
			}
			order.setSubscribedChannels(ChItem);
		}

		
		if(order.getSubscribedItems().length>0){
			for(int i=0;i<order.getSubscribedItems().length;i++){
				if(!order.getSubscribedItems()[i].getType().contains("NTV_")&&!order.getSubscribedItems()[i].getType().contains("AIO_SUBOWN")
						&&!order.getSubscribedItems()[i].getType().contains("AIO_RENTAL")&&!order.getSubscribedItems()[i].getType().contains("HDD_PREM")){
					VASList.add(order.getSubscribedItems()[i]);
				}
			}
		}
		
		if(NowTVStarterItemList!=null){	
			for(int k=0; k<NowTVStarterItemList.length; k++){
				SubItem = new SubscribedItemUI();
				SubItem.setActionInd("A");
				SubItem.setBasketId("");
				SubItem.setType("NTV_FREE");
				SubItem.setId(NowTVStarterItemList[k]);
				VASList.add(SubItem);
			}		
		}
		
		String ItemType = "";
		if(NowTVVasItemList!=null){	
			for(int k=0; k<NowTVVasItemList.length; k++){
				for(int i=0;i<nowTVDetails.getNowTVVasList().size();i++){
					if(NowTVVasItemList[k].equals(nowTVDetails.getNowTVVasList().get(i).getItemID())){
						if(nowTVDetails.getNowTVVasList().get(i).getCredit()!=null){
							order.setTvCredit(nowTVDetails.getNowTVVasList().get(i).getCredit());
							ItemType = nowTVDetails.getNowTVVasList().get(i).getItemType();
							break;
						}
					}
				}
				SubItem = new SubscribedItemUI();
				SubItem.setActionInd("A");
				SubItem.setBasketId("");
				SubItem.setType(ItemType);
				SubItem.setId(NowTVVasItemList[k]);
				VASList.add(SubItem);
			}		
		}
		
		if(NowTVOtherItemList!=null){	
			for(int k=0; k<NowTVOtherItemList.length; k++){
				for(int i=0;i<nowTVDetails.getNowTVOtherList().size();i++){
					if(NowTVOtherItemList[k].equals(nowTVDetails.getNowTVOtherList().get(i).getItemID())){
						ItemType = nowTVDetails.getNowTVOtherList().get(i).getItemType();
						break;
					}
				}
				SubItem = new SubscribedItemUI();
				SubItem.setActionInd("A");
				SubItem.setBasketId("");
				SubItem.setType(ItemType);
				SubItem.setId(NowTVOtherItemList[k]);
				VASList.add(SubItem);
			}
		}

		order.setIsCreditCardOfferNowTVPage("N");
		order.setServiceWaiverNowTVPage(null);
    	if (GiftList!=null&&nowTVDetails.getPcdGiftList()!=null){
			for(int j=0; j<GiftList.length; j++){
				SelectedGiftList.add(GiftList[j]);
				for(int i=0;i<nowTVDetails.getPcdGiftList().size();i++){
					if(GiftList[j].equals(nowTVDetails.getPcdGiftList().get(i).getId())){
						
						if(nowTVDetails.getPcdGiftList().get(i).getCampaignCdList()!=null&&nowTVDetails.getPcdGiftList().get(i).getCampaignCdList().size()>0){
							order.setGiftWithCampaignSubInd("Y");
							for(int k=0;k<nowTVDetails.getPcdGiftList().get(i).getCampaignCdList().size();k++){
							SubItem = new SubscribedItemUI();
							SubItem.setActionInd("A");
							SubItem.setBasketId("");
							SubItem.setType(nowTVDetails.getPcdGiftList().get(i).getType());
//							logger.info("gift date "+BasketDetail.getGiftList().get(i).getRequiredInstDate());
//							if(BasketDetail.getGiftList().get(i).getRequiredInstDate()!=null&&Integer.parseInt(BasketDetail.getGiftList().get(i).getRequiredInstDate())<req_inst_date){
//								req_inst_date = Integer.parseInt(BasketDetail.getGiftList().get(i).getRequiredInstDate());
//							}
							if(("Y".equalsIgnoreCase(nowTVDetails.getPcdGiftList().get(i).getWaiveISFInd())&&"I".equalsIgnoreCase(order.getTmpOTChrgType()))
									||("Y".equalsIgnoreCase(nowTVDetails.getPcdGiftList().get(i).getWaiveASFInd())&&"A".equalsIgnoreCase(order.getTmpOTChrgType()))){
								order.setServiceWaiverNowTVPage("G");
							}
							if(nowTVDetails.getPcdGiftList().get(i).getPaymentMethod()!=null && "Credit Card".equalsIgnoreCase(nowTVDetails.getPcdGiftList().get(i).getPaymentMethod())){
								order.setIsCreditCardOfferNowTVPage("Y");
							}
							SubItem.setCampaignCd(nowTVDetails.getPcdGiftList().get(i).getCampaignCdList().get(k).getCampaignCd());
							SubItem.setPackCd(nowTVDetails.getPcdGiftList().get(i).getCampaignCdList().get(k).getPackCd());
							SubItem.setTopUpCd(nowTVDetails.getPcdGiftList().get(i).getCampaignCdList().get(k).getTopUpCd());
							SubItem.setId(nowTVDetails.getPcdGiftList().get(i).getId());
							GiftTypeList.add(SubItem);
							}
							
						}else{
						
						SubItem = new SubscribedItemUI();
						SubItem.setActionInd("A");
						SubItem.setBasketId("");
						SubItem.setType(nowTVDetails.getPcdGiftList().get(i).getType());
//						logger.info("gift date "+BasketDetail.getGiftList().get(i).getRequiredInstDate());
//						if(BasketDetail.getGiftList().get(i).getRequiredInstDate()!=null&&Integer.parseInt(BasketDetail.getGiftList().get(i).getRequiredInstDate())<req_inst_date){
//							req_inst_date = Integer.parseInt(BasketDetail.getGiftList().get(i).getRequiredInstDate());
//						}
						if(("Y".equalsIgnoreCase(nowTVDetails.getPcdGiftList().get(i).getWaiveISFInd())&&"I".equalsIgnoreCase(order.getTmpOTChrgType()))
						||("Y".equalsIgnoreCase(nowTVDetails.getPcdGiftList().get(i).getWaiveASFInd())&&"A".equalsIgnoreCase(order.getTmpOTChrgType()))){
							order.setServiceWaiverNowTVPage("G");
						}
						if(nowTVDetails.getPcdGiftList().get(i).getPaymentMethod()!=null && "Credit Card".equalsIgnoreCase(nowTVDetails.getPcdGiftList().get(i).getPaymentMethod())){
							order.setIsCreditCardOfferNowTVPage("Y");
						}
						SubItem.setId(nowTVDetails.getPcdGiftList().get(i).getId());
						GiftTypeList.add(SubItem);
						}
						
						if(nowTVDetails.getPcdGiftList().get(i).getDediVASList()!=null&&nowTVDetails.getPcdGiftList().get(i).getDediVASList().size()>0){
							for(VASDetailUI vas:nowTVDetails.getPcdGiftList().get(i).getDediVASList()){
								SubItem = new SubscribedItemUI();
								SubItem.setActionInd("A");
								SubItem.setBasketId("");
								SubItem.setType(vas.getItemType());
								SubItem.setId(vas.getItemId());
								GiftTypeList.add(SubItem);
							}
						}
						logger.info( gson.toJson( gson.toJson(ComponentList)));
						if(nowTVDetails.getPcdGiftList().get(i).getGiftAttbList()!=null){
							for(AttbDTO attb:nowTVDetails.getPcdGiftList().get(i).getGiftAttbList()){	
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
		
		NowTVVasItemList = (String[]) ArrayUtils.addAll(NowTVVasItemList, NowTVOtherItemList);
		
		if(VASList!=null){			
			SubscribedItemUI[] nowVas = new SubscribedItemUI[VASList.size()];
			for(int j=0;j<VASList.size();j++){
				nowVas[j] = VASList.get(j);
			}
			order.setSubscribedItems(nowVas);
		}	
		
		logger.info("nowTVDetails.getInFormType():" + nowTVDetails.getInFormType());
		logger.info("nowTVDetails.getNowTVFormType():" + nowTVDetails.getNowTVFormType());
		
		logger.info("order.getDecodeType(): "+order.getDecodeType());
		logger.info("nowTVDetails.getDecodeType(): "+nowTVDetails.getDecodeType());
		
		if(nowTVDetails.getInFormType()!=null){
			if(nowTVDetails.getInFormType().equals("PON2PCD")) {
				order.setNowTvFormType("PCD List");
			}else if(nowTVDetails.getInFormType().equals("PCD2PON")){
				order.setNowTvFormType("PON List");
			}
		}
		
		
		

		
		boolean HasNTVPay = false;
		
		for(int i = 0;i<order.getSubscribedItems().length;i++){
			if(order.getSubscribedItems()[i].getType().contains("NTV_")){
				if(!order.getSubscribedItems()[i].getType().equals("NTV_FREE")&&
						!order.getSubscribedItems()[i].getType().equals("NTV_OTHER")&&
						!order.getSubscribedItems()[i].getType().equals("NTV_HD")){
					HasNTVPay = true;
					break;
				}
			}
		}
		
		if(!HasNTVPay){
			order.setTvCredit("0");
		}
		
		request.getSession().setAttribute("selectedIMSChannelList", ChannelItemList);
		request.getSession().setAttribute("selectedIMSNowVasList", NowTVVasItemList);
		request.getSession().setAttribute("type30F6", null);
		
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);

		System.out.println(gson.toJson(order));
		
		System.out.println("HD Activation: " + nowTVDetails.getHDAct());
		
		if(order.getTvPriceInd() != null &&"Y".equals(order.getTvPriceInd()))
		{	
			if(nowTVDetails.getSelectedVas()!=null&&nowTVDetails.getSelectedVas().length>0){
				for(String itemId:nowTVDetails.getSelectedVas()){
					NowTVVasUI vas = nowTVDetails.getvMap().get(itemId);
					if("Y".equals(vas.getTopUpChecked())) order.setTopUpType("Y");	
	//				if(vas.getItemType().equals("NTV_PAY") || vas.getItemType().contains("NTV_SP"))
	//					order.setTvCredit(vas.getCredit());  
					
					//if(vas.getItemType().equals("NTV_FREE") && "C".equals(model.getTypeOfService())) continue;  
					//else {
						SubscribedItemUI item = new SubscribedItemUI();
						item.setId(vas.getItemID());
						item.setProductId(vas.getProduct_id());
						item.setType(vas.getItemType());
						//item.setFromPage(ImsConstants.RET_NTV_PAGE);
						subItems.add(item);
					//} 
				}
			}
		logger.info("ntv other list : nowTVDetails.getSelectedVas()" + gson.toJson(nowTVDetails.getSelectedVas()));
		
			NowTVAddUI addUI = nowTVDetails.getNowTVAddUI();
			boolean ftaSelected = false;
			if (addUI != null) {
				if (addUI.getFtaCampaign() != null) {
					ftaSelected = updateSubAddUI(subItems, subChannels, addUI.getFtaCampaign());
					
				}
				
				if (ftaSelected) {
					if (addUI.getHardCampaign() != null) {
						updateSubAddUI(subItems, subChannels, addUI.getHardCampaign());
					}
					if (addUI.getPayTvCampaign() != null) {
						for (NowTvCampaignUI payBundle: addUI.getPayTvCampaign()) {
							updateSubAddUI(subItems, subChannels, payBundle);
						}
					}
				}
			}
		
			logger.info("ntv other list : subItems()" + gson.toJson(subItems));
		}		

		if(GiftTypeList!=null){
			for(int j=0; j<GiftTypeList.size(); j++){			
				subItems.add(GiftTypeList.get(j));
			}
		}
		
		List<SubscribedItemUI> allSubscribedItems = new ArrayList<SubscribedItemUI>();
		
		if(subItems.size()>0) {
			
			     for (SubscribedItemUI item: order.getSubscribedItems()) {
			    	allSubscribedItems.add(item);
			    }
			    
			    for (SubscribedItemUI item: subItems) {
			    	allSubscribedItems.add(item);
			    }
			   
			
			order.setSubscribedItems( allSubscribedItems.toArray(new SubscribedItemUI[allSubscribedItems.size()]));
		} 
		if(subItems.size()>0) {
			order.setSubscribedChannels(subChannels.toArray(new SubscribedChannelUI[subChannels.size()]));
		} 
		
		List<ComponentUI> allComponentItems = new ArrayList<ComponentUI>();
		if(ComponentList!=null&&ComponentList.size()>0){
			if(order.getComponents()!=null&&order.getComponents().length>0){
				for(ComponentUI com: order.getComponents()){
					allComponentItems.add(com);
				}
			}
			for(ComponentUI com: ComponentList){
				allComponentItems.add(com);
			}
			
			order.setComponents(allComponentItems.toArray(new ComponentUI[allComponentItems.size()]));
			order.setComponentsClone(allComponentItems.toArray(new ComponentUI[allComponentItems.size()]));
		}
		logger.info("getComponents(): " + gson.toJson(order.getComponents()));
		
		order.setProcessVim(null);
		if (order.getTvPriceInd() != null && "Y".equals(order.getTvPriceInd())){
			
			//order.setDecodeType(nowTVDetails.getDecodeType());
			List<NowTvPackUI> packs = nowTVDetails.getNowTVAddUI().getFtaCampaign().getPacks();
		
			if(packs != null){
				
			
				for(NowTvPackUI pack: packs) {
					if (pack.isSelected() == true) {

						order.setProcessVim("P");
						break;}
			}}
		}
		else {
			if(NowTVStarterItemList!= null && NowTVVasItemList!=null){
				order.setProcessVim("B");
			}else if(NowTVStarterItemList!= null && NowTVVasItemList==null){
				order.setProcessVim("P");
			}
		}
		if("P".equalsIgnoreCase(order.getProcessVim()))
				order.setDecodeType(nowTVDetails.getDecodeType());
		else order.setDecodeType(null);
		
		logger.info("order.getProcessVim():" + order.getProcessVim());

		request.getSession().setAttribute("selectedPcdNtvGiftList", request.getParameterValues("PcdNtvGiftItemBox"));
		request.getSession().setAttribute("nowtvPageGoneInd", "Y");
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);
		
		if ("Y".equals(order.isSignoffed()) && !StringUtils.equals(order.getIsPT(), "Y")) nextView = "imspayment.html";
		else if ("Y".equals(order.isSignoffed())) nextView = "imsamendprogoffer.html";
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	public boolean checkAddUIPackSelected(List<SubscribedItemUI> subItems, List<SubscribedChannelUI> subChannels, List<NowTvPackUI> packs) {
		boolean isPackSelected = false;
		for(NowTvPackUI pack: packs) {
			if (pack.isSelected() == true) {
				SubscribedItemUI item = new SubscribedItemUI();
				item.setId(pack.getPackId());
				item.setBasketId(pack.getParentCampaignId());
				if(pack.getType()!=null&&pack.getType().startsWith("NTV_")){
					item.setType(pack.getType());
				}else
					item.setType("NTV_" + pack.getType());
				item.setCampaignCd(pack.getCampaignCd_SOPHIE());

				item.setPackCd(pack.getPackCd_SOPHIE());


				subItems.add(item);
				

				
//				if (pack.getChannelGroupId() != null && !"".equals(pack.getChannelGroupId())) {
//					SubscribedChannelUI ch = new SubscribedChannelUI();
//					ch.setChannelId(pack.getChannelGroupId());
//					subChannels.add(ch);
//				}
				isPackSelected = true;
			}
		}
		return isPackSelected;
	}
	public boolean updateSubAddUI(List<SubscribedItemUI> subItems, List<SubscribedChannelUI> subChannels, NowTvCampaignUI campaign) { // martin
		boolean isSelected = checkAddUIPackSelected(subItems, subChannels, campaign.getPacks());
		if (isSelected) {
			for (NowTvVasBundle vas: campaign.getVasBundles()) {
				SubscribedItemUI item = new SubscribedItemUI();
				item.setId(vas.getItemId());
				item.setType(vas.getType());
				subItems.add(item);
				for (NowTVOfferUI offer: vas.getOfferCd()) {

                    SubscribedItemUI item2 = new SubscribedItemUI();

                    item2.setId(offer.getItemId());

                    item2.setType(offer.getType());

                    subItems.add(item2);

                }

			}
			for (NowTvTopUpUI topUp: campaign.getTopUps()) {
				if (topUp.isSelected()) {
					SubscribedItemUI item = new SubscribedItemUI();
					item.setId(topUp.getItemId());
					item.setType(topUp.getType());
					item.setBasketId(topUp.getParentCampaignId());
					item.setCampaignCd(topUp.getCampaignCd_SOPHIE());

					item.setTopUpCd(topUp.getTopUpCd());


					subItems.add(item);
				}
			}
		}
		return isSelected;
	}

	public void setVasTitle(List<NowTVVasUI> List){
    	if(List!=null)
		{
			int newlineInx = 0;
			for(int i =0;i<List.size();i++){
				if(List.get(i).getItemDesc()!=null){
						newlineInx = List.get(i).getItemDesc().indexOf("<br/>");
					if(newlineInx < 0)
						newlineInx = List.get(i).getItemDesc().indexOf((char)10+"");
					if(List.get(i).getItemDesc().indexOf((char)10+"")>0){
						List.get(i).setVASTitle(List.get(i).getItemDesc().substring(0, newlineInx));
						List.get(i).setVASDetail(List.get(i).getItemDesc().substring(newlineInx+1).replaceAll(((char)10)+"", "<br>"));
					}else if(List.get(i).getItemDesc().indexOf("<br/>") > 0)
					{
						List.get(i).setVASTitle(List.get(i).getItemDesc().substring(0, newlineInx));
						List.get(i).setVASDetail(List.get(i).getItemDesc().substring(newlineInx+5).replaceAll(((char)10)+"", "<br>"));
					}
					else{
						List.get(i).setVASTitle(List.get(i).getItemDesc());
						List.get(i).setVASDetail("");
					}
				}
			}
		}
	}
	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
		NowTVUI model = (NowTVUI)command;
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		Locale Locale = RequestContextUtils.getLocale(request);
		String locale = Locale.toString();
		String[] GiftList = (String[])request.getParameterValues("PcdNtvGiftItemBox");
		List<String> SelectedVASList = new ArrayList<String>();
		List<String> SelectedGiftList = new ArrayList<String>();
		List<VASDetailUI> vasExclusiveGiftList = null;
		List<VASDetailUI> vasExclusiveGiftTypeList = null;
		if (model.getNowTVAddUI() != null) {
			for (String err: validateAddUI(order, model, model.getNowTVAddUI(),Locale)) {
				errors.rejectValue("subscribedItems", "", err);
			}
		}
		for (String err: validateGiftSelect(model, GiftList,Locale, errors)) {
//			errors.rejectValue("subscribedGiftsError", "", err);
			errors.rejectValue("subscribedItems", "", err);
			logger.info("gift select error "+err);
		}
		

		
		if(GiftList!=null){
			for(int i=0; i<GiftList.length; i++){
				if(GiftList[i]!=null&&!GiftList[i].isEmpty()){
					SelectedGiftList.add(GiftList[i]);
					for(GiftUI gift:model.getPcdGiftList()){
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
			if(order.getSubscribedItems()!=null&&order.getSubscribedItems().length>0){
				for(SubscribedItemUI subui:order.getSubscribedItems()){
					if(subui.getId()!=null&&!subui.getId().isEmpty()){
						SelectedVASList.add(subui.getId());
					}
				}
			}
		}
		
		if(SelectedGiftList!=null&&SelectedGiftList.size()>0){
			
			vasExclusiveGiftList = basketService.getExclusiveItemList(locale,SelectedVASList, SelectedGiftList); 
			vasExclusiveGiftTypeList = basketService.getVasExclusiveGiftType(SelectedVASList,SelectedGiftList,locale);
			
			vasExclusiveGiftList.addAll(vasExclusiveGiftTypeList);
			
			if (vasExclusiveGiftList != null) {
				
				if (vasExclusiveGiftList.size() != 0) {
					errors.rejectValue("giftExclusiveError", "ims.pcd.basketdetails.msg.018");//print to the VASdetail header
					
					for (int i = 0; i < vasExclusiveGiftList.size(); i++) {
						errors.rejectValue("giftSelectError", "","");
						errors.rejectValue("giftSelectError", "ims.pcd.basketdetails.msg.019");
						errors.rejectValue("giftSelectError", "","<"+ vasExclusiveGiftList.get(i).getVASTitle()+ ">");
						errors.rejectValue("giftSelectError", "","<"+ vasExclusiveGiftList.get(i).getVASTitle_B()+ ">");
						
					}
				}
	
			}
		}
		
		if(SelectedGiftList!=null&&SelectedGiftList.size()>0){
			for (String err: validatePayBundlePrice(model, model.getNowTVAddUI(), Locale, SelectedGiftList)){
				errors.rejectValue("giftSelectError", "", err);
			}
		}
		
		if(!errors.hasErrors())  request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);
	}
	public List<String> validateAddUI(OrderImsUI order, NowTVUI model, NowTVAddUI addui,Locale locale) {
		List<String> rtnList = new ArrayList<String>();
		List<Integer> indexOfCampaign = new ArrayList<Integer>();
		List<NowTvPackUI> selectedPacks = addui.getSelectedPacks();
		List<String> packsSOPHIE = new ArrayList<String>();
	
		int numOfPackSelected = 0;
		int connType = 0;
		boolean hdPurchased = false;
		boolean nowBoxPurchased = false;
		
		if("Y".equalsIgnoreCase(order.getPcdNowOneBoxInd())){
			nowBoxPurchased = true;
		}

		if (!selectedPacks.isEmpty()){
		// check NTV_HD item
		if(model.getSelectedVas()!=null&&model.getSelectedVas().length>0){
	        for (String itemId: model.getSelectedVas()) {
	           NowTVVasUI vas = model.getvMap().get(itemId);
	           if (vas != null && "NTV_HD".equals(vas.getItemType())) {
		              hdPurchased = true;
		       }
	           if (vas != null && vas.getItemType().contains("AIO_")) {
	        	   nowBoxPurchased = true;
		       }
	        }
		}
        connType = addui.renewConnType(nowBoxPurchased, hdPurchased, model.getBandwidth());
        // check NTV_HD item end

			
		// check limitation
		if (addui.getHardCampaign() != null) {
			numOfPackSelected = addui.getHardCampaign().getNumOfSelectedPack();
			if (numOfPackSelected < addui.getHardCampaign().getMin_select_cnt()) {
//				rtnList.add("Can not reach minimum pack (" + addui.getHardCampaign().getTitle() + ") limitation: Can not reach minimum No. of selected pack(s) (Within Hard Bundle Campaign)");
				rtnList.add(message.getMessage("ims.pcd.nowtv.msg.016", null, locale));
			}
			if (numOfPackSelected > addui.getHardCampaign().getMax_select_cnt()) {
//				rtnList.add("Exceed maximum pack (" + addui.getHardCampaign().getTitle() + ") limitation: No. of selected pack(s) (Within Hard Bundle Campaign) exceed limitation");
				rtnList.add(message.getMessage("ims.pcd.nowtv.msg.017", null, locale));
			}
		}
		
		if (addui.getPayTvCampaign() != null) {
			for (int i = 0; i < addui.getPayTvCampaign().size(); i++) {
				numOfPackSelected = addui.getPayTvCampaign().get(i).getNumOfSelectedPack();
				if (numOfPackSelected > 0) {
					if (numOfPackSelected < addui.getPayTvCampaign().get(i).getMin_select_cnt()) {
						rtnList.add(message.getMessage("ims.pcd.nowtv.msg.018", null, locale) + addui.getPayTvCampaign().get(i).getTitle() + message.getMessage("ims.pcd.nowtv.msg.018a", null, locale));
					}
					if (numOfPackSelected > addui.getPayTvCampaign().get(i).getMax_select_cnt()) {
//						rtnList.add("Exceed maximum pack (" + addui.getPayTvCampaign().get(i).getTitle() + ") limitation: No. of selected pack(s) (Within Pay Bundle Campaign) exceed limitation");
						rtnList.add(message.getMessage("ims.pcd.nowtv.msg.019", null, locale));
					}
					indexOfCampaign.add(i);
				}
			}
			if (indexOfCampaign.size() > 1) {
				rtnList.add(message.getMessage("ims.pcd.nowtv.msg.020", null, locale));
			}
		}
		// check limitation end
		
		// Check duplicate pack
		List<String> campaignList = service.getPremierComboCampaignCode();
		
		//add gift campaign/pack for checking
		if(order.getSubscribedItems()!=null&&order.getSubscribedItems().length>0){
			for(SubscribedItemUI subui:order.getSubscribedItems()){
				if(subui.getType()!=null&&!subui.getType().isEmpty()&&subui.getType().contains("GIFT")
						&&subui.getCampaignCd()!=null&&!subui.getCampaignCd().isEmpty()
						&&subui.getPackCd()!=null&&!subui.getPackCd().isEmpty()){
					NowTvPackUI packui = new NowTvPackUI();
					packui.setParentType("GIFT");
					packui.setCampaignCd_SOPHIE(subui.getCampaignCd());
					packui.setPackCd_SOPHIE(subui.getPackCd());
					packui.setCampaign_title(" ");
					packui.setTitle(" ");
					selectedPacks.add(packui);
				}
			}
		}
		
		if (selectedPacks != null && selectedPacks.size() != 0) {
			int duplicatedIndex = 0;
			for (NowTvPackUI pack: selectedPacks) {
				duplicatedIndex = packsSOPHIE.indexOf(pack.getPackCd_SOPHIE());
				if (duplicatedIndex == -1) {
					packsSOPHIE.add(pack.getPackCd_SOPHIE());
				} else {
					packsSOPHIE.add(pack.getPackCd_SOPHIE());
					// skip checking of FTA
					if (!"FTA".equals(selectedPacks.get(duplicatedIndex).getParentType()) && !"FTA".equals(pack.getParentType()) &&
							pack.getPackCd_SOPHIE() != null && !"".equals(pack.getPackCd_SOPHIE().trim()) && 
							pack.getCampaignCd_SOPHIE()!=null && !campaignList.contains(pack.getCampaignCd_SOPHIE())) {
						rtnList.add(message.getMessage("ims.pcd.nowtv.msg.021", null, locale) +
								selectedPacks.get(duplicatedIndex).getCampaign_title() + padSpace(selectedPacks.get(duplicatedIndex).getCampaign_title(), pack.getCampaign_title()) + selectedPacks.get(duplicatedIndex).getTitle() + "<br>" +
								pack.getCampaign_title() + padSpace(pack.getCampaign_title(), selectedPacks.get(duplicatedIndex).getCampaign_title()) + pack.getTitle() + "</div>");
						break;
					}
				}

				if(pack.getParentType().equalsIgnoreCase("PB")){
					for (NowTvCampaignUI camp:addui.getPayTvCampaign()){
						for(NowTvPackUI spack:camp.getTvPacks()){
							if(spack.getPackCd_SOPHIE()!=null&&spack.getPackCd_SOPHIE().equalsIgnoreCase(pack.getPackCd_SOPHIE()) 
									&& (spack.getParentMinCnt().equalsIgnoreCase("1")&&pack.getParentMinCnt().equalsIgnoreCase("1"))
									&& !spack.getParentCampaignId().equalsIgnoreCase(pack.getParentCampaignId()) 
									&& (Integer.parseInt(spack.getParentCampaignPrice()) < Integer.parseInt(pack.getParentCampaignPrice()))){
								rtnList.add(message.getMessage("ims.pcd.nowtv.msg.022", null, locale) + pack.getTitle() + " ($" + pack.getParentCampaignPrice() + message.getMessage("ims.pcd.nowtv.msg.022a", null, locale) +
										spack.getTitle() + " ($" + spack.getParentCampaignPrice() + message.getMessage("ims.pcd.nowtv.msg.022b", null, locale) + "</div>");
								break;
							}
						}
					}
				}
			}
			logger.info("packSOPHIE: " + gson.toJson(packsSOPHIE));
		}
		// check duplicate pack end
		
		// check isAdult
		boolean isAdult = false;
		for (NowTvPackUI pack: selectedPacks) {
			for (NowTvChannelUI channel: pack.getTvChannels()) {
				if (connType >= channel.getReadRight()) {
					if (channel.isAdult()) {
						isAdult = true;
						break;
					}
				}
			}
		}
		if (isAdult) {
			if (!"Y".equals(model.getIsAdult())) {
				rtnList.add(message.getMessage("ims.pcd.nowtv.msg.023", null, locale));
			}
		}
		// check isAdult end
		
		//remove duplicate hardbundle for premier combo if no error found
		if(rtnList!=null&&rtnList.size()==0){
			if (selectedPacks != null && selectedPacks.size() != 0) {
				for(NowTvPackUI hardPack:addui.getHardCampaign().getTvPacks()){
					for(NowTvCampaignUI payCampaign:addui.getPayTvCampaign()){
						for(NowTvPackUI payPack:payCampaign.getPacks()){
							if(payPack.isSelected()&&hardPack.isSelected()&&payPack.getPackCd_SOPHIE().equalsIgnoreCase(hardPack.getPackCd_SOPHIE())
									&&campaignList.contains(payPack.getCampaignCd_SOPHIE())){
								hardPack.setSelected(false);
							}
						}
					}
				}
			}
		}
		//remove duplicate hardbundle for premier combo if no error found end 
		
		}
		
		return rtnList;
	}
	
	public List<String> validatePayBundlePrice(NowTVUI model, NowTVAddUI addui,Locale locale, List<String> SelectedGiftList) {
		
		List<String> rtnList = new ArrayList<String>();
		
		if(SelectedGiftList!=null&&SelectedGiftList.size()>0){
			int maxSelectedVasPrice =  0;
			for(String cur:SelectedGiftList)
				for(GiftUI gift:model.getPcdGiftList()){
					if(cur.equalsIgnoreCase(gift.getId())&& gift.getMinVasPrice()!=null){
						for(NowTvPackUI pack:addui.getSelectedPacks()){
							if(Integer.valueOf(pack.getParentCampaignPrice())>maxSelectedVasPrice){
								maxSelectedVasPrice = Integer.valueOf(pack.getParentCampaignPrice());
							}
						}
						if(maxSelectedVasPrice<Integer.valueOf(gift.getMinVasPrice())){
							rtnList.add(message.getMessage("ims.pcd.basketdetails.msg.022", null, locale)+" HK$"+ gift.getMinVasPrice());
							rtnList.add("<"+gift.getGiftTitlePlainText()+">");
						}
					}
				}
		}
		return rtnList;
	}
	
	public List<String> validateGiftSelect(NowTVUI model, String[] GiftList,Locale locale, BindException errors) {
		List<String> rtnList = new ArrayList<String>();
		
    	if (model.getPcdGiftList()!=null){
			for(int i=0;i<model.getPcdGiftList().size();i++){
				if(model.getGiftSelectCnt().containsKey(model.getPcdGiftList().get(i).getType())){
					HashMap<String, Object> tmpCntMap = model.getGiftSelectCnt().get(model.getPcdGiftList().get(i).getType());
					if("Y".equalsIgnoreCase(model.getPcdGiftList().get(i).getShowInd())){	//only count showed gift	
						tmpCntMap.put("GIFT_CNT", (Integer)tmpCntMap.get("GIFT_CNT")+1);
					}
					model.getGiftSelectCnt().put(model.getPcdGiftList().get(i).getType(), tmpCntMap);
//					logger.info("update gift select cnt map "+BasketDetail.getGiftList().get(i).getType()+" "+BasketDetail.getGiftSelectCnt().get(BasketDetail.getGiftList().get(i).getType()).get("GIFT_CNT"));
				}
				if (GiftList!=null){
					for(int j=0; j<GiftList.length; j++){
						if(GiftList[j].equals(model.getPcdGiftList().get(i).getId())){
							//update gift select cnt map
							if(model.getGiftSelectCnt().containsKey(model.getPcdGiftList().get(i).getType())){
								HashMap<String, Object> selectedCntMap = model.getGiftSelectCnt().get(model.getPcdGiftList().get(i).getType());
								selectedCntMap.put("SELECTED_CNT", (Integer)selectedCntMap.get("SELECTED_CNT")+1);
								model.getGiftSelectCnt().put(model.getPcdGiftList().get(i).getType(), selectedCntMap);
//								logger.info("update gift select cnt map "+BasketDetail.getGiftList().get(i).getType()+" "+BasketDetail.getGiftSelectCnt().get(BasketDetail.getGiftList().get(i).getType()).get("SELECTED_CNT"));
							}
							//check gift attb input length
							if(model.getPcdGiftList().get(i).getGiftAttbList()!=null){
								for(AttbDTO dto:model.getPcdGiftList().get(i).getGiftAttbList()){
									
									String valid_gift = "Y";

									if(dto.getInputFormat()!=null&&!dto.getInputFormat().isEmpty()){
										valid_gift = validateVasInput(dto.getInputValue(),dto.getInputFormat());
									}
									
									if((dto.getMinLength()!=null && Integer.parseInt(dto.getMinLength())>dto.getInputValue().length())
										|| (dto.getMaxLength()!=null && Integer.parseInt(dto.getMaxLength())<dto.getInputValue().length())
										|| valid_gift.equalsIgnoreCase("N")){
										
										String errStr = constructErrStr(dto.getMinLength(), dto.getMaxLength(), dto.getInputFormat(),locale);
										logger.info("tttttttt!"+errStr+i+model.getPcdGiftList().get(i).getGiftAttbList().indexOf(dto));
										
										errors.rejectValue("pcdGiftList["+i+"].giftAttbList["+model.getPcdGiftList().get(i).getGiftAttbList().indexOf(dto)+"].inputValue", "", errStr);
									}
								}
							}
						}
					}
				}
			}
    		
    	}
		
		if(model.getGiftSelectCnt()!=null){
			
			for (String giftType : model.getGiftSelectCnt().keySet()) {
				HashMap<String, Object> map = model.getGiftSelectCnt().get(giftType);
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
	
	public String padSpace(String s1, String s2) {
		int numOfSpace = 5;
		String paddedSpace = "";
		if (s1.length() < s2.length()) numOfSpace = numOfSpace + s2.length() - s1.length() + 1;
		for (int i = 0; i < numOfSpace; i++) {
			paddedSpace = paddedSpace + "&nbsp;";
		}
		return paddedSpace;
	}
	public void setMessage(MessageSource message) {
		this.message = message;
	}
	public MessageSource getMessage() {
		return message;
	}
}