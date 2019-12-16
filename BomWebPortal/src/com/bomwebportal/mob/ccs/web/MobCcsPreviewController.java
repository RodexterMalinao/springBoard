package com.bomwebportal.mob.ccs.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;//Paper bill Athena 20130925
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ClubPointDetailDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.IGuardDTO;
import com.bomwebportal.dto.ItemDisplayDTO;
import com.bomwebportal.dto.MobBillMediaDTO;
import com.bomwebportal.dto.MobBuoQuotaDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderMobDTO;
//import com.bomwebportal.dto.OrderMobDTO.NfcInd;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.QuotaPlanInfoUI;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.dto.ui.DepositUI;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocUI;
import com.bomwebportal.mob.ccs.dto.MobSponsorshipDTO;
import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;
import com.bomwebportal.mob.ccs.dto.PreviewDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentMonthyUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.service.MobCcsApprovalLogService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRescueService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderSearchService;
import com.bomwebportal.mob.ccs.service.MobCcsPaymentUpfrontService;
import com.bomwebportal.mob.ccs.service.MobCcsSupportDocService;
import com.bomwebportal.mob.ccs.service.StaffInfoService;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.DepositService;
import com.bomwebportal.service.IGuardService;
import com.bomwebportal.service.ItemDisplayService;
import com.bomwebportal.service.ItemFuncMobService;
import com.bomwebportal.service.MobileDetailService;
import com.bomwebportal.service.MultiSimInfoService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.QuotaPlanInfoService;
import com.bomwebportal.service.ReleaseLockService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsPreviewController extends SimpleFormController {

    protected final Log logger = LogFactory.getLog(getClass());
    private static String LOCK = "Y";

    private MobCcsMrtService mobCcsMrtService;
    private VasDetailService vasDetailService;
    private MobileDetailService mobileDetailService;
    private MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService;
    private CustomerProfileService customerProfileService;
    private OrderService orderService;
    private DeliveryService deliveryService;
    private MobCcsSupportDocService mobCcsSupportDocService;
    private StaffInfoService staffInfoService;
    private ReleaseLockService releaseLockService;
    private MobCcsOrderRemarkService mobCcsOrderRemarkService;
    //add by Eliot 20120515
    //private MobCcsApprovalLogService mobCcsApprovalLogService;
    private MobCcsOrderSearchService mobCcsOrderSearchService;
	private MobCcsOrderRescueService mobCcsOrderRescueService;
	private IGuardService iGuardService; //add by wilson 20121018
	private MultiSimInfoService multiSimInfoService;
	private QuotaPlanInfoService quotaPlanInfoService;
	private ItemDisplayService itemDisplayService;
	private ItemFuncMobService itemFuncMobService;

	private DepositService depositService;
	private MobCcsApprovalLogService mobCcsApprovalLogService;
	
	public IGuardService getiGuardService() {
		return iGuardService;
	}

	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}

    public MobCcsOrderRemarkService getMobCcsOrderRemarkService() {
		return mobCcsOrderRemarkService;
	}

	public void setMobCcsOrderRemarkService(
			MobCcsOrderRemarkService mobCcsOrderRemarkService) {
		this.mobCcsOrderRemarkService = mobCcsOrderRemarkService;
	}

	public ReleaseLockService getReleaseLockService() {
		return releaseLockService;
	}

	public void setReleaseLockService(ReleaseLockService releaseLockService) {
		this.releaseLockService = releaseLockService;
	}
    
    public MobCcsSupportDocService getMobCcsSupportDocService() {
    	return mobCcsSupportDocService;
    }

    public void setMobCcsSupportDocService(
	    MobCcsSupportDocService mobCcsSupportDocService) {
	this.mobCcsSupportDocService = mobCcsSupportDocService;
    }

    public StaffInfoService getStaffInfoService() {
	return staffInfoService;
    }

    public void setStaffInfoService(StaffInfoService staffInfoService) {
	this.staffInfoService = staffInfoService;
    }

    public DeliveryService getDeliveryService() {
	return deliveryService;
    }

    public void setDeliveryService(DeliveryService deliveryService) {
	this.deliveryService = deliveryService;
    }

    public MobCcsPaymentUpfrontService getMobCcsPaymentUpfrontService() {
	return mobCcsPaymentUpfrontService;
    }

    public void setMobCcsPaymentUpfrontService(
	    MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService) {
	this.mobCcsPaymentUpfrontService = mobCcsPaymentUpfrontService;
    }

    public CustomerProfileService getCustomerProfileService() {
	return customerProfileService;
    }

    public void setCustomerProfileService(
	    CustomerProfileService customerProfileService) {
	this.customerProfileService = customerProfileService;
    }

    public OrderService getOrderService() {
	return orderService;
    }

    public void setOrderService(OrderService orderService) {
	this.orderService = orderService;
    }

    public MobCcsMrtService getMobCcsMrtService() {
	return mobCcsMrtService;
    }

    public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
	this.mobCcsMrtService = mobCcsMrtService;
    }

    public VasDetailService getVasDetailService() {
	return vasDetailService;
    }

    public void setVasDetailService(VasDetailService vasDetailService) {
	this.vasDetailService = vasDetailService;
    }

    public MobileDetailService getMobileDetailService() {
	return mobileDetailService;
    }

    public void setMobileDetailService(MobileDetailService mobileDetailService) {
	this.mobileDetailService = mobileDetailService;
    }   
    

    public MobCcsOrderSearchService getMobCcsOrderSearchService() {
		return mobCcsOrderSearchService;
	}

	public void setMobCcsOrderSearchService(
			MobCcsOrderSearchService mobCcsOrderSearchService) {
		this.mobCcsOrderSearchService = mobCcsOrderSearchService;
	}
	
	public MobCcsOrderRescueService getMobCcsOrderRescueService() {
		return mobCcsOrderRescueService;
	}
	
	public void setMobCcsOrderRescueService(
			MobCcsOrderRescueService mobCcsOrderRescueService) {
		this.mobCcsOrderRescueService = mobCcsOrderRescueService;
	}
	public DepositService getDepositService() {
		return depositService;
	}

	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}
	public MobCcsPreviewController() {
    }

	public MultiSimInfoService getMultiSimInfoService() {
		return multiSimInfoService;
	}

	public void setMultiSimInfoService(MultiSimInfoService multiSimInfoService) {
		this.multiSimInfoService = multiSimInfoService;
	}
	
    public QuotaPlanInfoService getQuotaPlanInfoService() {
		return quotaPlanInfoService;
	}

	public void setQuotaPlanInfoService(QuotaPlanInfoService quotaPlanInfoService) {
		this.quotaPlanInfoService = quotaPlanInfoService;
	}
	
	public ItemDisplayService getItemDisplayService() {
		return itemDisplayService;
	}

	public void setItemDisplayService(ItemDisplayService itemDisplayService) {
		this.itemDisplayService = itemDisplayService;
	}

	public ItemFuncMobService getItemFuncMobService() {
		return itemFuncMobService;
	}

	public void setItemFuncMobService(ItemFuncMobService itemFuncMobService) {
		this.itemFuncMobService = itemFuncMobService;
	}

	
	
	public MobCcsApprovalLogService getMobCcsApprovalLogService() {
		return mobCcsApprovalLogService;
	}

	public void setMobCcsApprovalLogService(MobCcsApprovalLogService mobCcsApprovalLogService) {
		this.mobCcsApprovalLogService = mobCcsApprovalLogService;
	}

	public Object formBackingObject(HttpServletRequest request)
	    throws ServletException {
    	PreviewDTO dto = (PreviewDTO) MobCcsSessionUtil.getSession(request, "preview");
 
    	if (dto == null){
	    	dto = new PreviewDTO();
    	}
	    	
	    	String orderId = (String) request.getParameter("orderId");
	    	if (orderId == null) {
	    		orderId = (String) request.getParameter("orderIdSession");
	    	}
	    	
	    	CustomerProfileDTO customerProfileDTO = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
			BasketDTO basketDTO = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
			MRTUI mrtUI = (MRTUI) MobCcsSessionUtil.getSession(request, "mrt");
			DeliveryUI deliveryUI = (DeliveryUI) MobCcsSessionUtil.getSession(request, "delivery");
			PaymentUpFrontUI paymentUpFrontUI = (PaymentUpFrontUI) MobCcsSessionUtil.getSession(request, "paymentUpFront");
			PaymentMonthyUI paymentMonthyUI = (PaymentMonthyUI) MobCcsSessionUtil.getSession(request, "paymentMonthy");
			String previousOrderType = (String) request.getSession().getAttribute("orderType");
			OrderDTO orderDTO =(OrderDTO)MobCcsSessionUtil.getSession(request, "orderDTO");
			MobSponsorshipDTO mobSponsorshipDTO = (MobSponsorshipDTO)MobCcsSessionUtil.getSession(request, "mobSponsorshipDTO");
			DepositUI depositUI = (DepositUI)MobCcsSessionUtil.getSession(request, "depositInfo");
			List<MultiSimInfoDTO> multiSimInfoDTOs =(List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
			List<ClubPointDetailDTO> clubPointDetailDTO = (List<ClubPointDetailDTO>) request.getSession().getAttribute("clubPointDetailSession");
			dto.setValue("customer", customerProfileDTO);
			dto.setValue("basket", basketDTO);
			dto.setValue("mrt", mrtUI);
			dto.setValue("delivery", deliveryUI);
			dto.setOrderId(orderId);
			dto.setValue("paymentUpFrontUI", paymentUpFrontUI);
			dto.setValue("paymentMonthyUI", paymentMonthyUI);
			dto.setValue("previousOrderType", previousOrderType);
			dto.setValue("orderDTO", orderDTO);
			dto.setValue("mobSponsorshipDTO", mobSponsorshipDTO);
			dto.setValue("depositInfo", depositUI);
			dto.setValue("multiSimInfoList", multiSimInfoDTOs);
			dto.setValue("clubPointDetailSession", clubPointDetailDTO);
    	return dto;
    }

    public ModelAndView onSubmit(HttpServletRequest request,
	    HttpServletResponse response, Object command, BindException errors)
	    throws ServletException, AppRuntimeException {

		String nextView = (String) request.getAttribute("nextView");
		logger.info("Next View: " + nextView);
		
		//get orderType, add by wilson 20120305
		PreviewDTO previewDTO = (PreviewDTO) command;
		request.getSession().setAttribute("orderType", previewDTO.getOrderType());
		MobCcsSessionUtil.setSession(request, "orderType",	previewDTO.getOrderType());
		MobCcsSessionUtil.setSession(request, "preview", previewDTO);
		
		return new ModelAndView(new RedirectView(nextView));

    }

    protected Map referenceData(HttpServletRequest request) throws Exception {
	logger.info("MobCcsPreviewController ReferenceData called");
	Map referenceData = new HashMap<String, List<String>>();

	String locale = RequestContextUtils.getLocale(request).toString();

	String status = (String) request.getParameter("status");
	String action = (String) request.getParameter("action");

	CustomerProfileDTO customerProfileDTO = null;
	MRTUI mrtUI = null;
	PaymentUpFrontUI paymentUpFrontUI = null;
	PaymentMonthyUI paymentMonthyUI = new PaymentMonthyUI();
	String orderId = null;
	ModelDTO mobileDetail = null;
	DeliveryUI deliveryUI = null;
	List<VasDetailDTO> vasHSRPList = null;
	MobCcsSupportDocUI supportingDoc = null;
	StaffInfoUI staffInfo = null;
	BasketDTO basketDTO = null;
	OrderDTO lockOrderDTO = null;
	OrderDTO orderDTO =null;
	List<StockDTO> stockList = null;
	String[] authorityLock = null;
	VasDetailDTO vasDetail = null;
	BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
	IGuardDTO iGuardDto = null;
	IGuardDTO iGuardADDto =null;
	MobSponsorshipDTO mobSponsorshipDTO = null;
	List<MultiSimInfoDTO> multiSimInfoDTOs = null;
	List<ClubPointDetailDTO> clubPointDetailDTO = null;
	VasMrtSelectionDTO vasMrtSelectionDTO = null;
	VasMrtSelectionDTO ssMrtSelectionDTO = null;
	Double mupAdminCharge =0.00;
	
	//List<DepositDTO> depositDTOList = null;
	DepositUI depositUI = null;

	//recall order 
	if ((status != null && (status.equalsIgnoreCase(BomWebPortalConstant.BWP_MOBCCS_ORDER_PENDING) 
			|| status.equalsIgnoreCase(BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT))) 
			|| (action != null && action.equalsIgnoreCase("ENQUIRY"))) {
	    orderId = (String) request.getParameter("orderId");
	    String basketId = orderService.findBasketId(orderId);

	    //BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

	    OrderRemarkDTO orderRemarkDTO = new OrderRemarkDTO(); //add by wilson 20120402
	    Date now = new Date();
		orderRemarkDTO.setOrderId(orderId);
		if (action != null && action.equalsIgnoreCase("ENQUIRY")){
			orderRemarkDTO.setRemark(salesUserDto.getUsername()+" ENQUIRY ORDER");//add by wilson 210120410
		}else{
			orderRemarkDTO.setRemark(salesUserDto.getUsername()+" RECALL ORDER");
		}		
		orderRemarkDTO.setCreateBy(salesUserDto.getUsername());
		orderRemarkDTO.setCreateDate(now);
		orderRemarkDTO.setLastUpdBy(salesUserDto.getUsername());
		orderRemarkDTO.setLastUpdDate(now);
		this.mobCcsOrderRemarkService.insertOrderRemarkDTO(orderRemarkDTO);
		
	    orderDTO = orderService.getOrderWithPaidAmount(orderId); //call new method==> getOrder(orderId);
	    String appDate = Utility.date2String(orderDTO.getAppInDate(), BomWebPortalConstant.DATE_FORMAT);
	    MobCcsSessionUtil.setSession(request, "appDateTime", orderDTO.getAppInDate());
	    customerProfileDTO = customerProfileService.getCustomerProfile(orderId);
	    ArrayList<MobCcsMrtBaseDTO> mobCcsMrtDtoList = mobCcsMrtService.getMobCcsMrtDTO(orderId);
	   
	    //James 20120306
	    //add by Eliot 20120515
	    if (mobCcsMrtDtoList != null && !mobCcsMrtDtoList.isEmpty()) {
	    	mrtUI = mobCcsMrtService.mrtDtoChangeToUiDto(mobCcsMrtDtoList);
	    	if (mrtUI!=null && customerProfileDTO != null){
	    		mrtUI.setSimType(customerProfileDTO.getSimType());
	    	}
	    	if (mrtUI!=null){
	    		mrtUI.setMnpExtendAuthInd(mobCcsApprovalLogService.isApproval(orderId,"AU19")?"Y":null);
	    	}
	    }
	    paymentUpFrontUI = mobCcsPaymentUpfrontService.getPaymentUpfront(orderId);
	    PaymentDTO paymentDto = orderService.getPayment(orderId);
	    BeanUtils.copyProperties(paymentMonthyUI, paymentDto);
	    mobileDetail = mobileDetailService.getMobileDetail(locale, basketId);
	    deliveryUI = deliveryService.getMobCcsDelivery(orderId);
	    if("DRAF".equalsIgnoreCase(orderDTO.getBusTxnType())){
	    	supportingDoc = mobCcsSupportDocService.getMobCcsSupportDocUI(orderId, locale);
	    	supportingDoc.setMobCcsSupportDocDTOList(mobCcsSupportDocService.getMobCcsSupportDocDTOInitialList(locale));
	    }else{
	    	supportingDoc = mobCcsSupportDocService.getMobCcsSupportDocUI(orderId, locale);
	    }
	    
	    staffInfo = staffInfoService.getStaffInfoDTO(orderId);
	    List<String> resultList = orderService.getSelectedItemList(orderId);
	    List<ComponentDTO> componentList = orderService.getComponentList(orderId);
	    stockList = orderService.getStockAssignment(orderId);
	    vasDetail = orderService.getVasDetailDTO(orderId, orderDTO.getAppInDate());
		OrderMobDTO orderMobDTO = this.orderService.getOrderMobDTO(orderId);
		if (orderMobDTO != null) {
			orderDTO.setNfcInd(orderMobDTO.getNfcInteger());
			orderDTO.setStockAssgnDate(orderMobDTO.getStockAssgnDate());
			// MBU2019003 -- Add Campaign_ID
			orderDTO.setCampaignId(orderMobDTO.getCampaignId());
			
			vasDetail.setSimExtraFunction(orderMobDTO.getNfcInteger());
			if (StringUtils.isNotEmpty(orderMobDTO.getStaffId())) {
				mobSponsorshipDTO = new MobSponsorshipDTO();
				mobSponsorshipDTO.setBasketId(basketId);
				mobSponsorshipDTO.setOrderId(orderMobDTO.getOrderId());
				mobSponsorshipDTO.setStaffId(orderMobDTO.getStaffId());
				mobSponsorshipDTO.setCcc(orderMobDTO.getCcc());
				mobSponsorshipDTO.setSponsorLevel(orderMobDTO.getSponsorLevel());
			}
			customerProfileDTO.setCareStatus(orderMobDTO.getCareStatus());
			customerProfileDTO.setCareOptInd(orderMobDTO.getCareOptInd());
			customerProfileDTO.setCareDmSupInd(orderMobDTO.getCareDmSupInd());
			customerProfileDTO.setHkbnInd(orderMobDTO.getHkbnInd());
		}
		basketDTO = vasDetailService.getBasketAttribute(basketId, appDate);

		   
	    List<DepositDTO> depositDTOList = depositService.findDepositDTOByOrderId(orderId);
	    depositUI = new DepositUI();
	    depositUI.setDepositDTOList(depositDTOList);

	    request.getSession().setAttribute("orderType", orderDTO.getBusTxnType());
		MobCcsSessionUtil.setSession(request, "orderType",	orderDTO.getBusTxnType());
		
		MobCcsSessionUtil.setSession(request, "originalOrderType",	orderDTO.getBusTxnType());

	    String[] selectedItemList = null;

	    if (resultList != null) {
	    	selectedItemList = resultList.toArray(new String[resultList.size()]);
	    }
	    //201309
		/*admin Charge*/	
		Double adminCharge = vasDetailService.getOneTimeChargeAmount(orderId);
		mupAdminCharge = vasDetailService.getMupAdminChargeAmount(orderId);
		basketDTO.setAdminCharge(String.valueOf(adminCharge));	
		/*admin charge*/
	    
	    String oneCardTwoSimInd= vasDetailService.getOneCardTwoSimInd(selectedItemList);//add by wilson 20120807, for vas ic2N issue
	    basketDTO.setOneCardTwoNumberVasInd(oneCardTwoSimInd);//add by wilson 20120807, for vas ic2N issue
	    if (deliveryUI != null && deliveryUI.getDeliveryTimeSlot() != null) {
	    	String[] timeSlot = deliveryService.getTimeSlotDesc(deliveryUI.getDeliveryTimeSlot());
	    	if (timeSlot != null) {
	    		deliveryUI.setTimeSlotFrom(timeSlot[0]);
	    		deliveryUI.setTimeSlotTo(timeSlot[1]);
	    	} 
	    }
	    
	    multiSimInfoDTOs = multiSimInfoService.getMultiSimInfoDTO(orderId, locale, 
	    		Utility.date2String(orderDTO.getAppInDate(), "dd/MM/yyyy"), "2", orderDTO.getShopCode(), customerProfileDTO.getBrand(), customerProfileDTO.getSimType());
	    if (multiSimInfoDTOs!=null){
			for(MultiSimInfoDTO msi : multiSimInfoDTOs){
				for (VasDetailDTO vas: msi.getSimItemList()) {
					vas.setItemHtml2(vas.getItemHtml().replaceAll("\\<.*?>",""));
				}
			}
	    }
	    
	    clubPointDetailDTO = orderService.getClubPointDetailsByOrderId(orderId);
	    
	    vasMrtSelectionDTO = orderService.getVasMrtSelectionDTO(orderId);
	    ssMrtSelectionDTO = orderService.getSsMrtSelectionDTO(orderId);
	    if (selectedItemList != null) {
	    	for (String itemId : selectedItemList) {
				if (itemFuncMobService.getItemFuncAssgnMobDTO(itemId, "EX06") != null) {
					if (vasMrtSelectionDTO!=null){
						vasMrtSelectionDTO.setVasItemId(itemId);
						vasMrtSelectionDTO.setFuncId("EX06");
						request.getSession().setAttribute("vasMrtSelectionSession", vasMrtSelectionDTO);
					}
				}
				if (itemFuncMobService.getItemFuncAssgnMobDTO(itemId, "EX07") != null) {
					if (ssMrtSelectionDTO!=null){
						ssMrtSelectionDTO.setVasItemId(itemId);
						ssMrtSelectionDTO.setFuncId("EX07");
						request.getSession().setAttribute("ssMrtSelectionSession", ssMrtSelectionDTO);
					}
				}
			}
	    }
	    
	    deliveryUI.setPaymentMethod(paymentUpFrontUI.getPayMethodType()); 		
		deliveryUI.setValue("appDate", appDate);
	    
	    vasHSRPList = vasDetailService.getVasDetailSelectedList(locale, orderId);
	    
	    SubscriberDTO sub = orderService.getBomWebSub(orderId);
	    if (mrtUI!=null){
	    	mrtUI.setOrigActDateStr(Utility.date2String(sub.getOrigActDate(), "dd/MM/yyyy"));
	    }
	    Boolean originalPrvicyInd= customerProfileDTO.isPrivacyInd();
		request.getSession().setAttribute("originalPrvicyInd", originalPrvicyInd);//add 20130322
	    
	    request.getSession().setAttribute("orderIdSession", orderId);
	    request.getSession().setAttribute("appDate", appDate);
	    request.getSession().setAttribute("numType", customerProfileDTO.getNumType()); //DENNIS MIP3
		request.getSession().setAttribute("simType", customerProfileDTO.getSimType());

		request.getSession().setAttribute("brandType", customerProfileDTO.getBrand());
		request.getSession().setAttribute("pLang", sub.getSmsLang());

	    request.getSession().setAttribute("basketSession", basketId);
	    customerProfileDTO.setActiveMobileNum(customerProfileDTO.getSrvNum());
	   
	    SuperOrderDTO superOrderDto = new SuperOrderDTO();
		superOrderDto.setOrderSalesCd(orderDTO.getSalesCd());
		superOrderDto.setOrderId(orderDTO.getOrderId());
		superOrderDto.setOrderShopCd(orderDTO.getShopCode());
		request.getSession().setAttribute("superOrderDto", superOrderDto);
	    
	    MobCcsSessionUtil.setSession(request, "orderIdSession", orderId);
	    MobCcsSessionUtil.setSession(request, "orderDTO", orderDTO);
	    MobCcsSessionUtil.setSession(request, "originalOrderDTO", orderDTO);//add by wilson 20121018, iGuard
	    MobCcsSessionUtil.setSession(request, "orderId", orderId);
	    MobCcsSessionUtil.setSession(request, "appDate", appDate);
	    MobCcsSessionUtil.setSession(request, "customer", customerProfileDTO);
	    MobCcsSessionUtil.setSession(request, "mrt", mrtUI);
	    MobCcsSessionUtil.setSession(request, "paymentUpFront", paymentUpFrontUI);
	    MobCcsSessionUtil.setSession(request, "originalpaymentUpFront", paymentUpFrontUI);//add by wilson 20120530, for supportDoc original pay method
	    MobCcsSessionUtil.setSession(request, "paymentMonthy", paymentMonthyUI);
	    MobCcsSessionUtil.setSession(request, "basketSession", basketId);
	    MobCcsSessionUtil.setSession(request, "selectedVasItemList", selectedItemList);
	  
		request.getSession().setAttribute("selectedVasItemList", selectedItemList);
	    MobCcsSessionUtil.setSession(request, "supportingDoc", supportingDoc);
	    MobCcsSessionUtil.setSession(request, "staffInfo", staffInfo);
	    MobCcsSessionUtil.setSession(request, "delivery", deliveryUI);
	    MobCcsSessionUtil.setSession(request, "basket", basketDTO);
	    MobCcsSessionUtil.setSession(request, "originalBasket", basketDTO);//add by wilson 20120530, for supportDoc get basket amt
	    request.getSession().setAttribute("componentList", componentList);
	    //add by Eliot@20120202 for define working status is recall or new order
	    MobCcsSessionUtil.setSession(request, "workStatus", BomWebPortalCcsConstant.RECALL_ORDER);
	    MobCcsSessionUtil.setSession(request, "vasDetail", vasDetail);
	    MobCcsSessionUtil.setSession(request, "vasHSRPList", vasHSRPList);
	    MobCcsSessionUtil.setSession(request, "mobSponsorshipDTO", mobSponsorshipDTO);
	    MobCcsSessionUtil.setSession(request, "MultiSimInfoList", multiSimInfoDTOs);
	    request.getSession().setAttribute("MultiSimInfoList", multiSimInfoDTOs);
	    request.getSession().setAttribute("clubPointDetailSession", clubPointDetailDTO);
	    MobCcsSessionUtil.setSession(request, "depositInfo", depositUI);
	    request.getSession().setAttribute("vasMrtSelectionSession", vasMrtSelectionDTO);
	    request.getSession().setAttribute("ssMrtSelectionSession", ssMrtSelectionDTO);
	    
		
	    /***********QuotaPlanInfo**************************/
		List<MobBuoQuotaDTO> mobBuoQuotaDtoList =quotaPlanInfoService.getBomWebBuoQuota(orderId);
		Map<String,QuotaPlanInfoUI> quotaPlanInfoMap = new HashMap<String,QuotaPlanInfoUI>();
		for (MobBuoQuotaDTO mobBuoQuotaDto : mobBuoQuotaDtoList){
			QuotaPlanInfoUI quotaPlanInfoUI = new QuotaPlanInfoUI();
			if ("P".equals(mobBuoQuotaDto.getAutoTopUpInd())){
				quotaPlanInfoUI.setAutoTopUpInd("Y");
				quotaPlanInfoUI.setIotPlan("Y");
				if(customerProfileDTO.isSuppressLocalTopUpInd()){
					quotaPlanInfoUI.setSuppressLocal("Y");
				}
				if (customerProfileDTO.isSuppressRoamTopUpInd()){
					quotaPlanInfoUI.setSuppressRoam("Y");
				}
			}else{
				quotaPlanInfoUI.setAutoTopUpInd(mobBuoQuotaDto.getAutoTopUpInd());
			}
			
			ItemDisplayDTO  itemDisplayDTO = itemDisplayService.getItemDisplay(Integer.parseInt(mobBuoQuotaDto.getItemId()), "en", "ITEM_SELECT");
			
			quotaPlanInfoUI.setMaxTopUpTimes(mobBuoQuotaDto.getMaxCntAutoTopUp());
			quotaPlanInfoUI.setItemId(mobBuoQuotaDto.getItemId());
			quotaPlanInfoUI.setSelectedQuotaPlanOption(mobBuoQuotaDto.getBuoId());
			quotaPlanInfoUI.setEngDesc(mobBuoQuotaDto.getEngDesc());
			quotaPlanInfoUI.setItemDisplayDTO(itemDisplayDTO);
			quotaPlanInfoMap.put(mobBuoQuotaDto.getItemId(), quotaPlanInfoUI);
			
		}
		request.getSession().setAttribute("quotaPlanInfoMapSession", quotaPlanInfoMap);			
		
		/***********QuotaPlanInfoEnd***********************/

	    authorityLock = releaseLockService.checkAuthority(orderId, salesUserDto.getUsername());

	    if (authorityLock != null) {
	    	 //To lock order
		    if (action == null || action.isEmpty()) {
		    	//If 0 then user has the authority to access
		    	if ("0".equalsIgnoreCase(authorityLock[0])){
		    		lockOrderDTO  = releaseLockService.getOrderLockInfo(orderId);
			    	if (lockOrderDTO != null) {
			    		if ("Y".equalsIgnoreCase(lockOrderDTO.getLockInd()) && !salesUserDto.getUsername().equalsIgnoreCase(lockOrderDTO.getLastUpdateBy())) {
				    		action = "LOCK";
				    	} else {
				    		releaseLockService.updateOrderLockInd(orderId, LOCK, salesUserDto.getUsername());
				    	}
			    	}  
			    //if authorityLock[0] does not return 0 then display the error message
		    	} else {
		    		action = authorityLock[1];
		    	}
		    }
	    } else {
	    	action = "System error. Please return to search page and recall the order again";
	    }
	 
	    List<String> vasTempList = new ArrayList<String>();
		for (VasDetailDTO vas :vasDetailService.getVasDetailSelectedList(locale,orderId)) {
			vasTempList.add(vas.getItemId());
		}
		String [] vasGuardList = (String[]) vasTempList.toArray(new String[2]);
	    	 	    	
 		List<String> srvPlanList=iGuardService.isIGuardOrder(basketDTO.getBasketId(), selectedItemList, orderDTO.getAppInDate());
 		for(int i=0; i<srvPlanList.size();i++){
 		 	if (!StringUtils.isEmpty(srvPlanList.get(i))){
 		 	String srvPlanType=srvPlanList.get(i);
 		 	if("LDS".equalsIgnoreCase(srvPlanType)){
 		 		 if (!StringUtils.isEmpty(orderDTO.getiGuardSerialNo()) ){
 		 			 iGuardDto = iGuardService.getCcsPreviewIGuardDTO(orderId, orderDTO.getAppInDate(), "i-Guard Preview", customerProfileDTO, mrtUI, basketDTO, staffInfo, deliveryUI, salesUserDto, stockList, componentList,srvPlanType);
 		 		 }
	    	}else if("AD".equalsIgnoreCase(srvPlanType)){
	    		iGuardADDto = iGuardService.getCcsPreviewIGuardDTO(orderId, orderDTO.getAppInDate(), "i-Guard Preview", customerProfileDTO, mrtUI, basketDTO, staffInfo, deliveryUI, salesUserDto, stockList, componentList,srvPlanType);
	    		iGuardDto = iGuardService.getCcsPreviewIGuardDTO(orderId, orderDTO.getAppInDate(), "i-Guard Preview", customerProfileDTO, mrtUI, basketDTO, staffInfo, deliveryUI, salesUserDto, stockList, componentList,srvPlanType);
	    		
	    	}			    }
 		} 	
	} else {
		//new order, order amend
		//BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
	    orderId = (String) MobCcsSessionUtil.getSession(request, "orderId");
	    String appDate = (String) request.getSession().getAttribute("appDate");
	    
	    String orderType = (String) request.getSession().getAttribute("orderType");
	    
	    if (orderDTO == null) {
	    	orderDTO = new OrderDTO();
	    }
	    
	    orderDTO.setBusTxnType(orderType);
	    customerProfileDTO = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
	    mrtUI = (MRTUI) MobCcsSessionUtil.getSession(request, "mrt");
	    paymentUpFrontUI = (PaymentUpFrontUI) MobCcsSessionUtil.getSession(request, "paymentUpFront");
	    paymentMonthyUI = (PaymentMonthyUI) MobCcsSessionUtil.getSession(request, "paymentMonthy");

	    // keep same as MOB(common)
	    basketDTO =(BasketDTO)MobCcsSessionUtil.getSession(request, "basket");
	    String selectedBasketId = basketDTO.getBasketId();
	    String[] selectedItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
	    mobileDetail = mobileDetailService.getMobileDetail(locale, selectedBasketId);

	    supportingDoc = (MobCcsSupportDocUI) MobCcsSessionUtil.getSession(request, "supportingDoc");
	    staffInfo = (StaffInfoUI) MobCcsSessionUtil.getSession(request, "staffInfo");
	    deliveryUI = (DeliveryUI) MobCcsSessionUtil.getSession(request, "delivery");
	    vasDetail = (VasDetailDTO)MobCcsSessionUtil.getSession(request, "vasDetail");
	    List<ComponentDTO> componentList = (List<ComponentDTO>) MobCcsSessionUtil.getSession(request, "componentList");
	   

	    if (!"".equals(selectedBasketId) && selectedBasketId != null) {
	    	vasHSRPList = vasDetailService.getUserSelectedBasketItemList(
			locale, appDate, selectedBasketId, selectedItemList,mrtUI, paymentMonthyUI );

	    }
	    /*iGuard */
	    Date appDateTime = (Date) MobCcsSessionUtil.getSession(request,"appDateTime");
	    if (appDateTime== null){
	    	appDateTime = new Date();
	    }	    
	    List<String> srvPlanList=iGuardService.isIGuardOrder(basketDTO.getBasketId(), selectedItemList, appDateTime);
	    for(int i=0; i<srvPlanList.size();i++){
		    if (!StringUtils.isEmpty(srvPlanList.get(i))){
		    	String srvPlanType=srvPlanList.get(i);
		    	if("LDS".equalsIgnoreCase(srvPlanType)){
			    	iGuardDto = iGuardService.getCcsPreviewIGuardDTO(orderId, appDateTime, "i-Guard Preview", customerProfileDTO, mrtUI, basketDTO, staffInfo, deliveryUI, salesUserDto, stockList, componentList,srvPlanType);

		    	}else if("AD".equalsIgnoreCase(srvPlanType)){
		    		iGuardADDto = iGuardService.getCcsPreviewIGuardDTO(orderId, appDateTime, "i-Guard Preview", customerProfileDTO, mrtUI, basketDTO, staffInfo, deliveryUI, salesUserDto, stockList, componentList,srvPlanType);
			    	iGuardDto = iGuardService.getCcsPreviewIGuardDTO(orderId, appDateTime, "i-Guard Preview", customerProfileDTO, mrtUI, basketDTO, staffInfo, deliveryUI, salesUserDto, stockList, componentList,srvPlanType);
		    	}
		    	orderDTO.setiGuardSerialNo("i-Guard Preview");
		    	
			}
	    }
	    /*iGuard */
	    
	    mobSponsorshipDTO = (MobSponsorshipDTO)MobCcsSessionUtil.getSession(request, "mobSponsorshipDTO");
	    
	    depositUI = (DepositUI) MobCcsSessionUtil.getSession(request, "depositInfo");

	    multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
	    if(multiSimInfoDTOs != null) {
			List<MultiSimInfoDTO> selectedMultiSimInfo = new ArrayList<MultiSimInfoDTO> ();
			int memNum = 1;
			for(MultiSimInfoDTO msi : multiSimInfoDTOs){
				if(selectedItemList != null){
					for(String selectedItem : selectedItemList){
						if(selectedItem.equals(msi.getItemId())){
							selectedMultiSimInfo.add(msi);
						}
					}
				}
			}
			multiSimInfoDTOs.clear();
			multiSimInfoDTOs.addAll(selectedMultiSimInfo);
			for(MultiSimInfoDTO msi : multiSimInfoDTOs){
				for(MultiSimInfoMemberDTO msim : msi.getMembers()){
					msim.setParentOrderId(orderId);
					msim.setMemberOrderId(orderId + "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(memNum-1, memNum));
					msim.setMemberNum("ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(memNum-1, memNum));
					if (StringUtils.isNotEmpty(msim.getSameAsCustInd()) && !msim.isSameAsCust()){
						msim.getActualUserDTO().setOrderId(orderId);
						msim.getActualUserDTO().setOrderType("M");
						msim.getActualUserDTO().setMemberNum(msim.getMemberNum());
					}
					memNum++;
				}
				for (VasDetailDTO vas: msi.getSimItemList()) {
					vas.setItemHtml2(vas.getItemHtml().replaceAll("\\<.*?>",""));
				}
			}
		}
	}
	
	clubPointDetailDTO = orderService.getClubPointDetailsByOrderId(orderId);
	
	if (StringUtils.isNotBlank(orderId)) {
		List<OrderRemarkDTO> orderRemarkResultList = this.mobCcsOrderRemarkService.getOrderRemarkDTOALL(orderId);
		referenceData.put("orderRemarkResultList", orderRemarkResultList);
	}
	//Paper bill Athena 20130925 (Start)
	List<MobBillMediaDTO> billMediaList  = LookupTableBean.getInstance().getBillMediaOption();
	List<MobBillMediaDTO> selectedBillMediaList = new ArrayList(); 
	List<ComponentDTO> componentList = orderService.getComponentList(orderId); 
	if (billMediaList!= null && customerProfileDTO.getSelectedBillMedia()!= null){	
		for(MobBillMediaDTO mbm: billMediaList) {
			for (String billCd: customerProfileDTO.getSelectedBillMedia()) {
				if (billCd.equals(mbm.getCodeId())) {
					selectedBillMediaList.add(mbm);
				}
			}
		}
	}
	//Paper bill Athena 20130925 (End)	
	
	List<MobBuoQuotaDTO> mobBuoQuotaDtoList = new ArrayList<MobBuoQuotaDTO>();
	Map<String,QuotaPlanInfoUI> quotaPlanInfoMap = (Map<String,QuotaPlanInfoUI>)request.getSession().getAttribute("quotaPlanInfoMapSession");

	if (quotaPlanInfoMap !=null){
		for (Map.Entry<String, QuotaPlanInfoUI> entry : quotaPlanInfoMap.entrySet()) {
			MobBuoQuotaDTO mobBuoQuotaDto = new MobBuoQuotaDTO();
			if ("Y".equalsIgnoreCase(entry.getValue().getIotPlan())) {
				if ("Y".equalsIgnoreCase(entry.getValue().getSuppressLocal())) {
					customerProfileDTO.setSuppressLocalTopUpInd(true);
					//subscriberDto.setSuppressLocalTopUpInd("Y");
				}
				if ("Y".equalsIgnoreCase(entry.getValue().getSuppressRoam())) {
					customerProfileDTO.setSuppressRoamTopUpInd(true);
					//subscriberDto.setSuppressRoamTopUpInd("Y");
				}
				mobBuoQuotaDto.setAutoTopUpInd(entry.getValue().getAutoTopUpInd());
			} else {
				mobBuoQuotaDto.setAutoTopUpInd(entry.getValue().getAutoTopUpInd());
			}
		}
	}
	
	//referenceData 
	referenceData.put("customer", customerProfileDTO);
	if (StringUtils.isNotEmpty(customerProfileDTO.getBillPeriod())) {
		int billDate = Integer.parseInt(customerProfileDTO.getBillPeriod()) - 1;
		referenceData.put("billDate", billDate);
	}
	if (selectedBillMediaList!= null){	
		referenceData.put("selectedBillMediaList", selectedBillMediaList); //Paper bill Athena 20130925
	}
	referenceData.put("orderId", orderId);
	referenceData.put("mobileDetail", mobileDetail);
	referenceData.put("vasHSRPList", vasHSRPList);
	referenceData.put("mrt", mrtUI);
	referenceData.put("paymentMonthy", paymentMonthyUI);
	referenceData.put("paymentUpFront", paymentUpFrontUI);
	referenceData.put("delivery", deliveryUI);
	referenceData.put("supportingDoc", supportingDoc);
	referenceData.put("staffInfo", staffInfo);
	referenceData.put("action", action);
	referenceData.put("lockOrderDTO", lockOrderDTO);
	
	referenceData.put("orderDTO", orderDTO);
	referenceData.put("basket", basketDTO);
	referenceData.put("stockList", stockList);
	referenceData.put("vasDetail", vasDetail);
	referenceData.put("orderRescueList", this.mobCcsOrderRescueService.getMobCcsOrderRescueDTOByOrderId(orderId));
	
	referenceData.put("user", salesUserDto);//for control DOA handling button
	if (StringUtils.isNotBlank(orderDTO.getOcid())) {
		referenceData.put("ocidStatus", this.mobCcsOrderSearchService.getOcidStatus(orderDTO.getOcid()));
	}
	
	
	String orderType = (String) request.getSession().getAttribute("orderType");
	
	String showDraftOrderButtonInd;
	String showPreOrderButtonInd;
	String showPendOrderButtonInd;
	//update button control IND
	showDraftOrderButtonInd =showDraftOrderButtonYN( orderDTO, orderType);
	showPreOrderButtonInd=showPreOrderButtonYN( orderDTO,  orderType);
	showPendOrderButtonInd= showPendOrderButtonYN(  orderDTO,  orderType);

	referenceData.put("showDraftOrderButtonInd", showDraftOrderButtonInd);
	referenceData.put("showPreOrderButtonInd", showPreOrderButtonInd);
	referenceData.put("showPendOrderButtonInd", showPendOrderButtonInd);
	
	referenceData.put("iGuardDto", iGuardDto);
	referenceData.put("iGuardADDto", iGuardADDto);
	referenceData.put("mobSponsorshipDTO", mobSponsorshipDTO);
	referenceData.put("depositInfo", depositUI);
	
	referenceData.put("MultiSimInfoList", multiSimInfoDTOs);
	referenceData.put("clubPointDetailDTO", clubPointDetailDTO);
	referenceData.put("theClubLogin", customerProfileDTO.getTheClubLogin());
	referenceData.put("mupAdminCharge",mupAdminCharge);
	
	return referenceData;

    }
   
    
	String showDraftOrderButtonYN(OrderDTO orderDTO, String orderType) {
		String showDraftOrderButtonInd="N";
		if (orderDTO !=null && orderDTO.getBusTxnType()!=null){
			if("DRAF".equals(orderDTO.getBusTxnType())){
				showDraftOrderButtonInd= "Y";
			}
		}
		return showDraftOrderButtonInd;
	}

	String showPreOrderButtonYN(OrderDTO orderDTO, String orderType) {
		String showPreOrderButtonInd="N";
		if (orderDTO !=null && orderDTO.getBusTxnType()!=null){
			if("PRE".equals(orderDTO.getBusTxnType()) ||"PEND".equals(orderDTO.getBusTxnType())){
				showPreOrderButtonInd= "Y";
			}
		}

		return showPreOrderButtonInd;
	}

	String showPendOrderButtonYN(OrderDTO orderDTO, String orderType) {
		String showPendOrderButtonInd="N";
		if (orderDTO !=null && orderDTO.getBusTxnType()!=null){
			if("PRE".equals(orderDTO.getBusTxnType()) ||"PEND".equals(orderDTO.getBusTxnType())){
				showPendOrderButtonInd= "Y";
			}
		}
		return showPendOrderButtonInd;
	}

	BigDecimal computeDepositAmount(List<DepositDTO> depositList) {
		BigDecimal amount = BigDecimal.ZERO;
		if (depositList == null) return amount;
		
		for (DepositDTO deposit: depositList) {
			BigDecimal itemAmt = deposit.getDepositAmount();
			if (!"Y".equals(deposit.getWaiveInd())) {
				amount = amount.add(itemAmt);
			}
		}
		return amount;
	}

}
