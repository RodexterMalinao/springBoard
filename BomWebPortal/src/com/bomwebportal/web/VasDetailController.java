package com.bomwebportal.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BasketMinVasLkupDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.ItemFuncAssgnMobDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.QuotaPlanInfoUI;
import com.bomwebportal.dto.SimDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.dto.VasAttbComponentDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobSponsorshipDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsApprovalLogService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.ItemFuncMobService;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.service.MobItemQuotaService;
import com.bomwebportal.service.MobileDetailService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.SystemTime;
import com.bomwebportal.util.Utility;



public class VasDetailController  extends SimpleFormController{
	
	private static final String ITEM_SELECTION_GROUP_ID_BR_86EASY_OPTOUT = "100000106";
	private static final String ITEM_SELECTION_GROUP_ID_PROJECT_EAGLE_INSURANCE= "6666666663";

    protected final Log logger = LogFactory.getLog(getClass());
    
    private VasDetailService service;
    private MobileDetailService mobileDetailService;
    private OrderService orderService;
    private ItemFuncMobService itemFuncMobService;
	private MnpService mnpService;
	private CodeLkupService codeLkupService;
	private MobItemQuotaService mobItemQuotaService;
    private MobCcsApprovalLogService mobCcsApprovalLogService;
	
    public OrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public VasDetailService getService() {
		return service;
	}
	public void setService(VasDetailService service) {
		this.service = service;
	}
	public void setMobileDetailService(MobileDetailService mobileDetailService) {
		this.mobileDetailService = mobileDetailService;
	}
	public MobileDetailService getMobileDetailService() {
		return mobileDetailService;
	}

	public ItemFuncMobService getItemFuncMobService() {
		return itemFuncMobService;
	}
	public void setItemFuncMobService(ItemFuncMobService itemFuncMobService) {
		this.itemFuncMobService = itemFuncMobService;
	}
	public MnpService getMnpService() {
		return mnpService;
	}
	public void setMnpService(MnpService mnpService) {
		this.mnpService = mnpService;
	}
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	public MobItemQuotaService getMobItemQuotaService() {
		return mobItemQuotaService;
	}
	public void setMobItemQuotaService(MobItemQuotaService mobItemQuotaService) {
		this.mobItemQuotaService = mobItemQuotaService;
	}
	public MobCcsApprovalLogService getMobCcsApprovalLogService() {
		return mobCcsApprovalLogService;
	}
	public void setMobCcsApprovalLogService(
			MobCcsApprovalLogService mobCcsApprovalLogService) {
		this.mobCcsApprovalLogService = mobCcsApprovalLogService;
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		SuperOrderDTO superOrderDto = (SuperOrderDTO) request.getSession().getAttribute("superOrderDto");
		
		int channelId=(user.getChannelId() == 11 ? 10 : user.getChannelId());//get channelId , convert CFM(66), CPA(68) => IBS, OBS(2)
		if (LookupTableBean.getInstance().getAllowFalloutChannelList()!=null && LookupTableBean.getInstance().getAllowFalloutChannelList().length>0 && Utility.isContainString(LookupTableBean.getInstance().getAllowFalloutChannelList(), user.getChannelCd())){
			channelId =2;//convert channel ID
		}
		
		BasketDTO	originalBasket =(BasketDTO)MobCcsSessionUtil.getSession(request, "originalBasket");//add basket info to front end
				
		String appDate = (String) request.getSession().getAttribute("appDate");
		String orderId = (String) request.getSession().getAttribute("orderIdSession");
		
		String[] vasItem = (String[])request.getParameterValues("vasitem");
		if (vasItem != null){
			
			request.getSession().setAttribute("selectedVasItemList", vasItem);
		}
		
		Map<String, Object> referenceData = new HashMap<String, Object>();

		ModelDTO mobileDetail= new  ModelDTO();
		String locale = RequestContextUtils.getLocale(request).toString();

    	String selectedBasketId=request.getParameter("basket");
    	String selectedColorName="";
    	String pageMessage="";
    	
    	String selectedBrandId="";
    	String selectedModelId="";
    	String selectedColorId ="";
    	if (!"".equals(selectedBasketId) ){
        	
    		mobileDetail =mobileDetailService.getMobileDetail(locale,selectedBasketId );
    		if (mobileDetail !=null){
	    		selectedColorName =mobileDetail.getColorDto().get(0).getColorName();
				selectedBrandId=mobileDetail.getBrandId();
				selectedModelId=mobileDetail.getModelId();
				selectedColorId =mobileDetail.getColorDto().get(0).getColorId();
    		}

    	}else{
    		pageMessage ="please select basket first!!";
    	}
    	
    	BasketDTO basketDto =service.getBasketAttribute(selectedBasketId, appDate);
    	
    	// MIP.P4 modification
    	String nature = basketDto.getNature();
    	
    	basketDto.setBasketMobItemQuotaInfoList(mobItemQuotaService.getBasketMobItemQuotaDTOALL(selectedBasketId, appDate));
    	if ((channelId == 10 || channelId == 11) && ("2".equals(basketDto.getBasketTypeId()) || "3".equals(basketDto.getBasketTypeId()) ) ) {
    		basketDto.setHandsetExtraFunction(service.getSimOnlyBasketSimType(selectedBasketId, appDate));
    	}
    	
    	String[] callListArray = (String[]) request.getSession().getAttribute("callListSession");
    	
    	if (callListArray != null && callListArray.length > 0) {
    		basketDto.setCallListCd(callListArray[0]); // get data from serviceseelction page
            basketDto.setCallListDesc(callListArray[1]);//get data from serviceseelction page
    	}
    	
    	MobCcsSessionUtil.setSession(request, "basket", basketDto);//save basket session
    	request.getSession().setAttribute("baskettypeSession", basketDto.getBasketTypeId());//wilson 20120417PRD
    	
    	String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
    	
    	//obsoleted list
    	List<VasDetailDTO> selectedVasList =new ArrayList<VasDetailDTO>();
    	if (orderId !=null){
    		selectedVasList=orderService.getSelectedVasList(locale, orderId, channelId+"", selectedBasketId, appDate, superOrderDto.getOrderShopCd(), nature);
    	}
    	/************************ Start SYstem Assign Vas ************************/
		List<VasDetailDTO> systemAssignVasDetailList = new ArrayList<VasDetailDTO>();
		/************* Start Pre-activation system assign barring items ********************/
		CustomerProfileDTO sessionCustomer = null;
		if (user.getChannelId() == 2) {
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
			referenceData.put("customerProfileDTO", (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer"));
		} else {
			sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
			referenceData.put("customerProfileDTO", (CustomerProfileDTO) request.getSession().getAttribute("customer"));
		}
		
		List<VasDetailDTO> preActivationVasItemList = service.getSystemAssignVas("PRE_ACTIVATION_BARRING_VAS_ITEM", appDate, "en", sessionCustomer.getBrand(), sessionCustomer.getSimType());
		if (CollectionUtils.isNotEmpty(preActivationVasItemList)) {
			selectedItemList = this.removeItemFromSelectedItemList(selectedItemList, preActivationVasItemList);
			selectedVasList = this.removeObsoleteItemFromSelectedItemList(selectedVasList, preActivationVasItemList);
			if ("5".equals(sessionCustomer.getAddrProofInd())) {
				systemAssignVasDetailList.addAll(preActivationVasItemList);
			}
		}
		/************* End Pre-activation system assign barring items ************************/
		MobCcsSessionUtil.setSession(request, "systemVasItemList", systemAssignVasDetailList);//save basket session
    	request.getSession().setAttribute("systemVasItemList", systemAssignVasDetailList);
		/************************ End SYstem Assign Vas ***********************/
    	
    	List<VasDetailDTO> vasHSRPList=new ArrayList<VasDetailDTO>(); //from menu
    	List<VasDetailDTO> vasBFEEList=new ArrayList<VasDetailDTO>(); 
    	
    	List<VasDetailDTO> tempVasDetailList=new ArrayList<VasDetailDTO>(); //from menu
    	List<VasDetailDTO> tempHardBundleVasDetailList=new ArrayList<VasDetailDTO>();
    	
    	List<VasDetailDTO> allVasList=new ArrayList<VasDetailDTO>();
    	List<VasDetailDTO> specialVasList=new ArrayList<VasDetailDTO>();
    	List<VasDetailDTO> vasList=new ArrayList<VasDetailDTO>();
    	List<VasDetailDTO> premiumList=new ArrayList<VasDetailDTO>();
    	
    	HashMap<String,List<ItemFuncAssgnMobDTO>> itemFuncInfos = new HashMap<String,List<ItemFuncAssgnMobDTO>>();
    	
    	String basketDisplayTitle="";
    	
    	List<String> eagleItemList = service.getItemSelectionGrpList(ITEM_SELECTION_GROUP_ID_PROJECT_EAGLE_INSURANCE);
    	
		List<String> iguardItem = service.getItemSelectionGrpList("6666666668");
		List<String> iguardUADItem = service.getItemSelectionGrpList("6666666669");
		List<CodeLkupDTO> iguardIdLkup = codeLkupService.getCodeLkupDTOALL("UPP_BASKET_TYPE");
		List<String> iguardId = new ArrayList<String>();
		for (CodeLkupDTO codeLkupDTO : iguardIdLkup) {
			iguardId.add(codeLkupDTO.getCodeId());
		}
    	
    	if (!"".equals(selectedBasketId) && selectedBasketId !=null ){
			// MIP.P4 modification
			// String nature = basketDto.getNature();
    		vasHSRPList=service.getRPHSRPList(locale, selectedBasketId, appDate, superOrderDto.getOrderShopCd(), sessionCustomer.getBrand(), sessionCustomer.getSimType(), nature);
    		findItemFunctions(itemFuncInfos, vasHSRPList, appDate);
    		
    		// MIP.P4 modification
    		// String nature = service.getBasketAttribute(selectedBasketId, appDate).getNature();
    		tempVasDetailList=service.getVasDetailList(channelId+"" ,locale, selectedBasketId ,  selectedItemList,"ITEM_SELECT" , appDate, superOrderDto.getOrderShopCd(), sessionCustomer.getBrand(), sessionCustomer.getSimType(), nature);
    		basketDisplayTitle =service.getBasketDisplayTitle(locale, selectedBasketId);
    		
    		// MIP.P4 modification
    		// String nature = service.getBasketAttribute(selectedBasketId, appDate).getNature();
    		tempHardBundleVasDetailList=service.getHardBundleVasDetailList(channelId+"" ,locale, selectedBasketId ,  selectedItemList,"ITEM_SELECT" , appDate, superOrderDto.getOrderShopCd(), sessionCustomer.getBrand(), sessionCustomer.getSimType(), nature);
    		
    		allVasList.addAll(tempVasDetailList);
    		allVasList.addAll(tempHardBundleVasDetailList);
    	}
    	
    	MobCcsSessionUtil.setSession(request, "vasHSRPList", vasHSRPList);
    	findItemFunctions(itemFuncInfos, allVasList, appDate);
		MobCcsSessionUtil.setSession(request, "itemFuncInfos", itemFuncInfos);
		
		redistributeVAS(allVasList, specialVasList, vasList, premiumList);

		if (CollectionUtils.isNotEmpty(vasList)) {
			selectedVasList = this.removeObsoleteItemFromSelectedItemList(selectedVasList, vasList);
		}
		
    	//return new ModelAndView
		referenceData.put("orderDto", (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO"));
		referenceData.put("eagleItemList", eagleItemList);
		referenceData.put("iguardUADItemList", iguardUADItem);
		referenceData.put("iguardItemList", iguardItem );
		referenceData.put("iguardIdList", iguardId );
    	referenceData.put( "selectedBrandId", selectedBrandId); 
    	referenceData.put( "selectedModelId", selectedModelId); 
    	referenceData.put( "selectedColorId", selectedColorId); 
    	referenceData.put( "selectedBasketId", selectedBasketId); 
    	referenceData.put( "selectedColorName", selectedColorName);
    	referenceData.put( "mobileDetail", mobileDetail);
    	referenceData.put( "pageMessage", pageMessage);
    	referenceData.put( "vasHSRPList", vasHSRPList); 
    	referenceData.put( "basketDisplayTitle", basketDisplayTitle);
    	referenceData.put( "vasBFEEList", vasBFEEList);
    	referenceData.put( "basket", basketDto);
    	String orderType =(String)request.getSession().getAttribute("orderType");// for disable save as draft button
    	referenceData.put( "orderType", orderType);// for disable save as draft button
    	referenceData.put( "selectedVasList", selectedVasList);// for dummy vas display
    	
    	referenceData.put( "originalBasket", originalBasket);
    	
    	referenceData.put("channelId", channelId);
    	
    	// MIP.P4 modification
    	List<SimDTO> simSelectedDetailList = service.getSimSelection(locale, appDate, selectedBasketId, "", superOrderDto.getOrderShopCd(), sessionCustomer.getSimType(), sessionCustomer.getBrand(), nature); 
    	
    	//sim selection 20
		referenceData.put("simSelectedDetailList", simSelectedDetailList);
		//add IDD item list
		List<String> iddItemList = service.getItemSelectionGrpList("8888888888");//get IDD item List
		referenceData.put("iddItemList", iddItemList);//for front end alert 
		String originalOrderType =(String)MobCcsSessionUtil.getSession(request, "originalOrderType");
		referenceData.put("originalOrderType", originalOrderType);

		// for staff sponsorships
		referenceData.put("itemFuncInfos", itemFuncInfos);
		
		List<CodeLkupDTO> waiveReasons= codeLkupService.findActiveReasonCodeLkupByType("WAIVE_OT", SystemTime.asDate());
		referenceData.put("waiveReasons", waiveReasons);
		
		List<CodeLkupDTO> rpWaiveReasons= codeLkupService.findActiveReasonCodeLkupByType("WAIVE_OT", SystemTime.asDate());
		referenceData.put("rpWaiveReasons", rpWaiveReasons);

		referenceData.put("systemAssignVasDetailList", systemAssignVasDetailList);
		referenceData.put("specialVasList", specialVasList);
		referenceData.put("vasList", vasList);
		referenceData.put("premiumList", premiumList);
		
		MobCcsSessionUtil.setSession(request, "vasList", vasList);//save basket session
    	request.getSession().setAttribute("vasList", vasList);
		
		//Dummy Waive RP pre-payment
		String dummyWaiveRpPrepayItemExist = "N";
		List<String> dummyWaiveRpPrepayItemList = service.getDummyWaiveRpPrepayItemExist(selectedBasketId, appDate);
		if (CollectionUtils.isNotEmpty(dummyWaiveRpPrepayItemList)){
			dummyWaiveRpPrepayItemExist = "Y";
		}
			
		referenceData.put("dummyWaiveRpPrepayItemExist", dummyWaiveRpPrepayItemExist);
		
		handleBR86EasyOptout(sessionCustomer, referenceData);
		
		return referenceData;
	}

    /////START ////////////////////////////////////////////////////
	public Object formBackingObject(HttpServletRequest request) throws ServletException{	
		
	
		String[] vasItem = (String[])request.getParameterValues("vasitem");//edit by wilson 20110221
		String appDate = (String) request.getSession().getAttribute("appDate");//add by wilson 20120301
		String basketId=request.getParameter("basket");
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		OrderDTO orderDto = null;
		VasDetailDTO vasDtl =  (VasDetailDTO) MobCcsSessionUtil.getSession(request, "vasDetail");
		
		if (user.getChannelId() == 2) {
			orderDto = (OrderDTO)MobCcsSessionUtil.getSession(request, "orderDTO");
		} else {
			orderDto = (OrderDTO) request.getSession().getAttribute("OrderDto");
		}
		//(String[])request.getSession().getAttribute("selectedVasItemList");
		
		if (vasDtl == null) {
			vasDtl = new VasDetailDTO();
			if (service.isUppReport(Utility.string2Date(appDate))){
				vasDtl.setChkIguard(true);
			}
		} else {
			vasDtl.setChkIguard(false);
			vasDtl.setPcRelationshipList(null);
		}
		
		if (service.isUppReport(Utility.string2Date(appDate))){
			vasDtl.setChkIguard(true);
		}
		if (service.enableUADPlan(Utility.string2Date(appDate))){
			vasDtl.setChkUADPlan(true);
		}
		
	
		vasDtl.setVasitem(vasItem);
		vasDtl.setValue("user", user);
		vasDtl.setValue("orderDto", orderDto);
		vasDtl.setValue("request", request);
		//*****************Public house validation *******************
		CustomerProfileDTO sessionCustomer = null;
		BasketDTO sessionBasket = null;
		sessionBasket = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
		if (user.getChannelId() == 2) {
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		} else {
			sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		}
		
		if (sessionBasket!=null && sessionBasket.getBasketId()!=null){
			if (!sessionBasket.getBasketId().equals(basketId)){
				vasDtl.setRpWaiveReason(null);
				vasDtl.setMinVasAuthInd(null);
			}
		}
		if ("HKID".equalsIgnoreCase(sessionCustomer.getIdDocType())){
			vasDtl.setHkidInd(true);
		}
		
		
		
		MnpDTO sessionMnp = (MnpDTO) request.getSession().getAttribute("MNP");
		
		vasDtl.setValue("customer", sessionCustomer);
		vasDtl.setValue("basket", sessionBasket);
		vasDtl.setValue("MNP", sessionMnp);//retail only
		vasDtl.setValue("appMode", request.getSession().getAttribute("appMode"));
		vasDtl.setValue("itemFuncInfos", MobCcsSessionUtil.getSession(request, "itemFuncInfos"));
		MobSponsorshipDTO mobSponsorshipDTO = (MobSponsorshipDTO) MobCcsSessionUtil.getSession(request, "mobSponsorshipDTO");
		if (mobSponsorshipDTO != null && !basketId.equalsIgnoreCase(mobSponsorshipDTO.getBasketId())) {
			mobSponsorshipDTO = null;
			MobCcsSessionUtil.setSession(request, "mobSponsorshipDTO", null);
		}
		vasDtl.setValue("mobSponsorshipDTO", mobSponsorshipDTO);
		
		//add by nancy 20140120
		vasDtl.setValue("MultiSimInfoList", request.getSession().getAttribute("MultiSimInfoList"));
		
		String simItemId="";
		
		//vasDtl.setSimitem(new String []{"104000004"    });
		VasDetailDTO vasDetailSession = (VasDetailDTO)MobCcsSessionUtil.getSession(request, "vasDetail");
		
		// MIP.P4 modification
    	BasketDTO basketDto =service.getBasketAttribute(basketId, appDate);
    	String nature = basketDto.getNature();
		
		if (vasDetailSession!=null){
			
			simItemId=vasDetailSession.getSelectedSimItemId();
			//check original selected sim item is exist in basket item assign , add by wilson 20120308 
			String flag="N";
			
			OrderDTO orderDTO = (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO");
	    	String channelCd = (orderDTO == null ? user.getChannelCd() : orderDTO.getShopCode());
	    	// MIP.P4 modification
			List<SimDTO> simSelectedDetailList = service.getSimSelection("en", appDate, basketId, "", channelCd, sessionCustomer.getSimType(), sessionCustomer.getBrand(),  nature);
			for( int i=0; simSelectedDetailList!=null && i<simSelectedDetailList.size(); i++){
				if (simItemId.equals(simSelectedDetailList.get(i).getItemId())){
					flag ="Y";//exist
					break;
				}
			}
			if ("N".equals(flag)){
				if (user.getChannelId() == 2) {
					for (SimDTO sim:simSelectedDetailList) {
						if (sim.getStockCount() > 0) {
							vasDtl.setSelectedSimItemId(sim.getItemId());
							break;
						}
					}
				} else if (simSelectedDetailList != null && simSelectedDetailList.size() > 0) {
					vasDtl.setSelectedSimItemId(simSelectedDetailList.get(0).getItemId());
				}
			}else{
				vasDtl.setSelectedSimItemId(vasDetailSession.getSelectedSimItemId());//set original selected sim
			}

			if (sessionBasket !=null){
				vasDetailSession.setBasketDto(sessionBasket);
			}
		}else{
			//set default sim item, new basket selection
			OrderDTO orderDTO = (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO");
			String channelCd = (orderDTO == null ? user.getChannelCd() : orderDTO.getShopCode());
			// MIP.P4 modification
			List<SimDTO> simSelectedDetailList = service.getSimSelection("en", appDate, basketId, "", channelCd, sessionCustomer.getSimType(), sessionCustomer.getBrand(), nature);
			for (SimDTO sim:simSelectedDetailList) {
				if (sim.getCharge() == 0 && (sim.getStockCount() > 0 || user.getChannelId() != 2)) {
					vasDtl.setSelectedSimItemId(sim.getItemId());
					break;
				}
			}
		}
		
		//*************************************************************/
		
		/************************ Hard bundle VAS ***********************/
		vasDtl.setHardBundledGrpId(service.getBasketHardBundleGrpId(basketId, sessionCustomer.getBrand(), appDate));
		BasketMinVasLkupDTO minVasDTO = service.getBasketMinVasLkup(basketId, appDate);
		if (minVasDTO != null) {
			vasDtl.setMinVas(minVasDTO.getMinVas());
			
			String orderId = (String) request.getSession().getAttribute("orderIdSession");
			if (StringUtils.isNotBlank(orderId)) {
				if (mobCcsApprovalLogService.isApprovedBasket(orderId, basketId, "AU18")) {
					vasDtl.setMinVasAuthInd("Y");
				}
			}
		} else {
			vasDtl.setMinVas(0.0);
		}
		/*************************************************************/
		
		return vasDtl;
	}
	
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
		throws ServletException, AppRuntimeException {
			long startTimeTotal = System.currentTimeMillis();
		
			String nextView = (String)request.getAttribute("nextView");
			
			BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
			CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
			if (user.getChannelId() == 2) {
				sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
			}
			String channelId = String.valueOf(user.getChannelId());
			if (user !=null){
				channelId= ""+user.getChannelId();
			}
			
			if ("saveDraft".equalsIgnoreCase(request.getParameter("nextAction"))) {
				nextView = "mobccsstaffinfo.html";
			} else { //bypass customer info validation 
				CustomerProfileDTO customerDto = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
				if (customerDto!=null && customerDto.isByPassValidation()) {
					errors.rejectValue("itemHtml", "itemHtml.byPassNotAllow", "By-pass Validation on Customer's Information has to be unchecked in order to proceed" );
					Map model = errors.getModel();
					try {
						model.putAll(referenceData(request));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new ModelAndView("vasdetail", model);
				}
			}
			
			
			
			logger.info("Next View: " + nextView);
			VasDetailDTO vasDetail = (VasDetailDTO)command;

			request.getSession().setAttribute("brandSession", request.getParameter("brand"));
			request.getSession().setAttribute("modelSession", request.getParameter("model"));
			request.getSession().setAttribute("colorSession", request.getParameter("color"));
			request.getSession().setAttribute("basketSession", request.getParameter("basket"));
			String appDate = (String) request.getSession().getAttribute("appDate");
			
			String[] vasItem = request.getParameterValues("vasitem");
			BasketDTO sessionBasket = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
			
			if ("undefined".equals(vasDetail.getSimWaiveReason())) {
				vasDetail.setSimWaiveReason("");
			}
			if ("undefined".equals(vasDetail.getRpWaiveReason())) {
				vasDetail.setRpWaiveReason("");
			}
			
			request.getSession().setAttribute("selectedVasItemList", vasItem);
			
			List<String> selectedItemList = null;
			if (vasItem!= null && vasItem.length > 0) {
				selectedItemList = Arrays.asList(vasItem);
			}
			vasDetail.setFinalOuotaList(mobItemQuotaService.getFinalQuota(sessionBasket.getBasketId(), appDate, selectedItemList));
			MobCcsSessionUtil.setSession(request, "vasDetail", vasDetail);//save whole page DTO, 20120220
			
			/*******************************************************************/
			long startTime = System.currentTimeMillis();
			List<String> vasList = service.getSubscribedVASList(request.getParameter("basket"), vasItem, sessionCustomer.getBrand(), sessionCustomer.getSimType());	
			long endTime   = System.currentTimeMillis();
			logger.info("totalTime call VasDetailController in vasDetailservice.getSubscribedVASList(): "+((endTime - startTime))+"ms");
			
			//delete multisim reserve num
			List<MultiSimInfoDTO> multiSimInfos = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
			MnpDTO mnpDTO = (MnpDTO) request.getSession().getAttribute("MNP");
			
			sessionCustomer.setPcrfMupAlert("0");
			if(multiSimInfos != null) {
				boolean exist = false;
				
				for(MultiSimInfoDTO msi : multiSimInfos){
					if(vasList != null){
						for(String selectedItem : vasList){
							if(selectedItem.equals(msi.getItemId())){
								exist = true;
								if ("2".equalsIgnoreCase(sessionCustomer.getPcrfMupAlert())){
									msi.setPcrfMupAlert(true);
								}
								
								if(msi.isPcrfMupAlert()){
									sessionCustomer.setPcrfMupAlert("2");
								} else {
									sessionCustomer.setPcrfMupAlert("1");
								}
								break;
							}
						}
						
						if (!exist){
							for (MultiSimInfoMemberDTO msim : msi.getMembers()) {
								String checkMsisdn = msim.getOriginalNewMsisdn();
								if (StringUtils.isNotEmpty(checkMsisdn) && mnpDTO!=null){
									mnpService.reserveMsisdn(checkMsisdn, "D", mnpDTO.getShopCd());	
								}
							}							
							sessionCustomer.setPcrfMupAlert("0");
						}
					}
				}
				
			}
			
			request.getSession().setAttribute("customer", sessionCustomer);
			MobCcsSessionUtil.setSession(request, "customer", sessionCustomer);
			
			vasList.addAll(service.getHsItemIdByBasket(sessionBasket.getBasketId(), Utility.string2Date(appDate)));
			
			startTime = System.currentTimeMillis();
			List<VasAttbComponentDTO> vasAttbList = service.getVasAttbComponentList(vasList, channelId);
			endTime   = System.currentTimeMillis();
			logger.info("totalTime call VasDetailController in vasDetailservice.getVasAttbComponentList(): "+((endTime - startTime))+"ms");
			
			sessionBasket.setOneCardTwoNumberVasInd(service.getOneCardTwoSimInd(vasItem));
			MobCcsSessionUtil.setSession(request, "basket", sessionBasket);//save again
			
			/*******************************************************************/
			String originalOrderType =(String)MobCcsSessionUtil.getSession(request, "originalOrderType");
			if(originalOrderType!=null || "DRAF".equals(originalOrderType) || "PRE".equals(originalOrderType)||"PEND".equals(originalOrderType)){
				if ("saveDraft".equalsIgnoreCase(request.getParameter("nextAction"))){
					request.getSession().setAttribute("orderType", "DRAF");
				}
			}else{
				if ("saveDraft".equalsIgnoreCase(request.getParameter("nextAction"))){
					request.getSession().setAttribute("orderType", "DRAF");
				}else{
					request.getSession().setAttribute("orderType", "PRE");
				}
				
			}
			logger.info("JMETER CHECKPOINT: vasAttbList == null?: "+(vasAttbList == null));
	
			/***************************QuotaPlanInfo**************************/
			Map<String,QuotaPlanInfoUI> quotaPlanInfoUi= (HashMap <String,QuotaPlanInfoUI>)request.getSession().getAttribute("quotaPlanInfoMapSession");
			List<VasDetailDTO> selectedVasHSRPList = (List<VasDetailDTO>) MobCcsSessionUtil.getSession(request,"vasHSRPList");
			if (MapUtils.isNotEmpty(quotaPlanInfoUi)){
				Iterator<Map.Entry<String, QuotaPlanInfoUI>> it = quotaPlanInfoUi.entrySet().iterator();
				
				while (it.hasNext()) {
					boolean hasBasketItem = false;
					Map.Entry<String, QuotaPlanInfoUI> entry = (Map.Entry<String, QuotaPlanInfoUI>) it.next();
	
					for (int i = 0; i < selectedVasHSRPList.size(); i++) {
						if (selectedVasHSRPList.get(i).getItemId().equals(entry.getKey())) {
							hasBasketItem = true;
						}
					}
	
					// delete basket info in the hash map
					if (!hasBasketItem && vasItem == null) { 
						it.remove();
					}
					// delete vas info in the hash map
					else if(!hasBasketItem && vasItem!=null){
						List<String> selectedVasItemList = Arrays.asList(vasItem);
						if (!selectedVasItemList.contains(entry.getKey())){
							System.out.println("delete vas");
							it.remove();
						}
					}
	
				}
				request.getSession().setAttribute("quotaPlanInfoMapSession",quotaPlanInfoUi);
			}
			
			
			/****************************QuotaPlanInfoEND********************************/
			
			/****************************Check1C2NSession********************************/
			VasMrtSelectionDTO vasMrtSelectionSession = (VasMrtSelectionDTO)request.getSession().getAttribute("vasMrtSelectionSession");
			if (vasMrtSelectionSession != null && !vasMrtSelectionSession.isChinaNumberSubscribed()){
				request.getSession().removeAttribute("vasMrtSelectionSession");
			}
			
			/****************************Check1C2NSessionEND********************************/
			
			long endTimeTotal   = System.currentTimeMillis();
			logger.info("totalTime call VasDetailController: "+((endTimeTotal - startTimeTotal))+"ms");
			
			if (vasAttbList!=null && vasAttbList.size()>0 && !"saveDraft".equalsIgnoreCase(request.getParameter("nextAction"))){
				request.getSession().setAttribute("vasAttbList", vasAttbList);
				String attrUid=(String)request.getParameter("sbuid");
				ModelAndView modelAndView =  new ModelAndView(new RedirectView("vasattb.html"));
				modelAndView.addObject("sbuid", attrUid);
				return modelAndView;
				
			}else{
			
				request.getSession().removeAttribute("vasAttbList");
				request.getSession().removeAttribute("componentList");
				String attrUid=(String)request.getParameter("sbuid");
				ModelAndView modelAndView =  new ModelAndView(new RedirectView(nextView));
				modelAndView.addObject("sbuid", attrUid);
				return modelAndView;
			}
			
	
	}
	
	boolean  isExistInString(String stringList[], String a){
		boolean result =false;
		
		for (int i=0; stringList!=null && i<stringList.length; i++ ){
			if (a.equals(stringList[i])){
				result= true;
				break;
			}
		}

		return result;
	}
	
	
	private Map<String,List<ItemFuncAssgnMobDTO>> findItemFunctions(Map<String,List<ItemFuncAssgnMobDTO>> itemFuncs, List<VasDetailDTO> vasList, String appDate) {
		if (vasList == null) return null;
	
		Map<String, List<ItemFuncAssgnMobDTO>> ItemFuncAssgnMobMap  = LookupTableBean.getInstance().getItemFuncAssgnMobMap();
		

		/*if (ItemFuncAssgnMobMap  == null) return null;*/
		
		if (ItemFuncAssgnMobMap  == null){
			
			//old method
			for (VasDetailDTO vas : vasList) {
				List<ItemFuncAssgnMobDTO> funcList = itemFuncMobService.findItemFuncAssgnMobDTO(vas.getItemId(), appDate);
				if (CollectionUtils.isNotEmpty(funcList)) {
					itemFuncs.put(vas.getItemId(), funcList);
				}
			}
			return itemFuncs;
		}else{
			//new method
			
			for (VasDetailDTO vas : vasList) {
				List<ItemFuncAssgnMobDTO> list = ItemFuncAssgnMobMap.get(vas.getItemId());
				if (CollectionUtils.isNotEmpty(list)) {
					List<ItemFuncAssgnMobDTO> newList = new ArrayList<ItemFuncAssgnMobDTO>();
					
					for (ItemFuncAssgnMobDTO dto : list){
						//check end date
						if (Utility.dateWithin(Utility.string2Date(appDate), dto.getEffStartDate(), dto.getEffEndDate())){
							newList.add(dto);
						}
					}
					
					if (CollectionUtils.isNotEmpty(newList)) {
						itemFuncs.put(vas.getItemId(), newList);
					}
				}
				
			}
			
			return itemFuncs;
		}
		
		
	}
	
	
	
	
	private String[] removeItemFromSelectedItemList(String[] selectedItemList, List<VasDetailDTO> removeItemList) {
		if (selectedItemList == null || selectedItemList.length == 0 ||
				CollectionUtils.isEmpty(removeItemList)) {
			return selectedItemList;
		}
		
		List<String> selectedItemArrayList = new ArrayList<String>(Arrays.asList(selectedItemList));
		Iterator<String> iterator = selectedItemArrayList.iterator();
		
		while (iterator.hasNext()) {
			String selectedItem = iterator.next();
			for (VasDetailDTO removeItem : removeItemList) {
				if (selectedItem.equalsIgnoreCase((removeItem.getItemId()))) {
					iterator.remove();
				}
			}
		}
		
		return selectedItemArrayList.toArray(new String[selectedItemArrayList.size()]);
	}
	
	private List<VasDetailDTO> removeObsoleteItemFromSelectedItemList(List<VasDetailDTO> selectedItemList, List<VasDetailDTO> removeItemList) {
		if (CollectionUtils.isEmpty(selectedItemList) ||CollectionUtils.isEmpty(removeItemList)) {
			return selectedItemList;
		}
		
		List<VasDetailDTO> selectedItemArrayList = new ArrayList<VasDetailDTO>(selectedItemList);
		Iterator<VasDetailDTO> iterator = selectedItemArrayList.iterator();
		
		while (iterator.hasNext()) {
			VasDetailDTO selectedItem = iterator.next();
			for (VasDetailDTO removeItem : removeItemList) {
				if (selectedItem.getItemId().equalsIgnoreCase((removeItem.getItemId()))) {
					iterator.remove();
				}
			}
		}
		
		return selectedItemArrayList;
	}
	
	private void redistributeVAS(List<VasDetailDTO> allVasList, List<VasDetailDTO> specialVasList,
			List<VasDetailDTO> vasList, List<VasDetailDTO> premiumList) {
		if (CollectionUtils.isNotEmpty(allVasList)) {
			allVasList = removeDuplicateItems(allVasList);
            for (VasDetailDTO vas : allVasList) {
            	String categoryId = vas.getCategoryId();
            	if (Utility.isCodeExist("VAS_SPECIAL_CATEGORY", categoryId)) {
            		specialVasList.add(vas);
            	} else if (Utility.isCodeExist("VAS_PREMIUM_CATEGORY", categoryId)) {
            		premiumList.add(vas);
            	} else {
            		vasList.add(vas);
            	}
            }
		}
	}
	
	private List<VasDetailDTO> removeDuplicateItems(List<VasDetailDTO> allVasList) {
		Set<VasDetailDTO> distinctAllVasSet = new HashSet<VasDetailDTO>(allVasList);
		List<VasDetailDTO> distinctAllVasList = new ArrayList<VasDetailDTO>(distinctAllVasSet);
		return distinctAllVasList;
	}
	
	private void handleBR86EasyOptout(CustomerProfileDTO sessionCustomer, Map<String, Object> referenceData) {
		List<String> br86EasyOptoutList = new ArrayList<String>();
		if (sessionCustomer != null && "BS".equalsIgnoreCase(sessionCustomer.getIdDocType())) {
			br86EasyOptoutList = service.getItemSelectionGrpList(ITEM_SELECTION_GROUP_ID_BR_86EASY_OPTOUT);
		}
		referenceData.put("br86EasyOptoutList", br86EasyOptoutList);
	}
	
}
