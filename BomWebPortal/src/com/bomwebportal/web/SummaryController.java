package com.bomwebportal.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllDocDTO.Type;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.AllOrdDocWaiveDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ClubPointDetailDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.dto.HsrmDTO;
import com.bomwebportal.dto.IGuardDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobBillMediaDTO;
import com.bomwebportal.dto.MobBuoQuotaDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.QuotaPlanInfoUI;
import com.bomwebportal.dto.ResultDTO;
import com.bomwebportal.dto.ServicePlanReportDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.dto.SummaryDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.dto.VasOnetimeAmtDTO;
import com.bomwebportal.dto.ui.DepositUI;
import com.bomwebportal.dto.ui.SupportDocUI;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DuplicateOrderException;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobSponsorshipDTO;
import com.bomwebportal.mob.ccs.dto.SbOrderAmendDTO;
import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;
import com.bomwebportal.mob.ccs.service.MobCcsApprovalLogService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.mob.ccs.service.MobCcsSbOrderAmendService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mob.ccs.web.MobCcsAuthorizeController;
import com.bomwebportal.mob.ds.dto.MobDsPaymentTransDTO;
import com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO;
import com.bomwebportal.mobquota.dto.MobQuotaUsageDTO;
import com.bomwebportal.mobquota.dto.QuotaConsumeRequest;
import com.bomwebportal.mobquota.exception.AppServiceException;
import com.bomwebportal.mobquota.service.MobQuotaService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.IGuardService;
import com.bomwebportal.service.ItemDisplayService;
import com.bomwebportal.service.MobItemQuotaService;
import com.bomwebportal.service.MobPreActReqService;
import com.bomwebportal.service.MobileDetailService;
import com.bomwebportal.service.MobileSimInfoService;
import com.bomwebportal.service.OrderHsrmService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.QuotaPlanInfoService;
import com.bomwebportal.service.ReportService;
import com.bomwebportal.service.SupportDocService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.SystemTime;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.util.ReportSessionName;


public class SummaryController extends SimpleFormController{
	

    protected final Log logger = LogFactory.getLog(getClass());
    
	private static final String LOB = "MOB";
	private static final String ITEM_SELECTION_GROUP_ID_HELPER_CARE = "6666666664";
	private static final String ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE= "6666666665";
	private static final String PROJECT_EAGLE_ITEM_SELECTTION_GROUP_ID = "6666666663";
	
    private VasDetailService service;
    private MobileDetailService mobileDetailService;
    
    private CustomerProfileService customerProfileService;
    
    private OrderService orderService;
    
    private SupportDocService supportDocService;
    
    private IGuardService iGuardService;
    private MobCcsApprovalLogService mobCcsApprovalLogService;    
    private MobCcsSbOrderAmendService mobCcsSbOrderAmendService;
    private MobPreActReqService mobPreActReqService;
    private MobItemQuotaService mobItemQuotaService;
    private MobQuotaService mobQuotaService;
    private MobCcsOrderRemarkService remarkService;
    private OrderHsrmService orderHsrmService;
    private QuotaPlanInfoService quotaPlanInfoService;
    private ItemDisplayService itemDisplayService;
    private MobCcsMrtService mobCcsMrtService;
    private MobileSimInfoService mobileSimInfoService;
    private ReportService reportService;
    
	public IGuardService getiGuardService() {
		return iGuardService;
	}

	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}
    
    public MobCcsSbOrderAmendService getMobCcsSbOrderAmendService() {
		return mobCcsSbOrderAmendService;
	}

	public void setMobCcsSbOrderAmendService(
			MobCcsSbOrderAmendService mobCcsSbOrderAmendService) {
		this.mobCcsSbOrderAmendService = mobCcsSbOrderAmendService;
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
	
	public void setCustomerProfileService(CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}
	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public OrderService getOrderService() {
		return orderService;
	}

	public SupportDocService getSupportDocService() {
		return supportDocService;
	}
	
	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}
	
	public MobCcsApprovalLogService getMobCcsApprovalLogService() {
		return mobCcsApprovalLogService;
	}

	public void setMobCcsApprovalLogService(
			MobCcsApprovalLogService mobCcsApprovalLogService) {
		this.mobCcsApprovalLogService = mobCcsApprovalLogService;
	}

	public MobPreActReqService getMobPreActReqService() {
		return mobPreActReqService;
	}

	public void setMobPreActReqService(MobPreActReqService mobPreActReqService) {
		this.mobPreActReqService = mobPreActReqService;
	}

	public MobItemQuotaService getMobItemQuotaService() {
		return mobItemQuotaService;
	}

	public void setMobItemQuotaService(MobItemQuotaService mobItemQuotaService) {
		this.mobItemQuotaService = mobItemQuotaService;
	}

	public MobQuotaService getMobQuotaService() {
		return mobQuotaService;
	}

	public void setMobQuotaService(MobQuotaService mobQuotaService) {
		this.mobQuotaService = mobQuotaService;
	}

	public MobCcsOrderRemarkService getRemarkService() {
		return remarkService;
	}

	public void setRemarkService(MobCcsOrderRemarkService remarkService) {
		this.remarkService = remarkService;
	}

	public OrderHsrmService getOrderHsrmService() {
		return orderHsrmService;
	}

	public void setOrderHsrmService(OrderHsrmService orderHsrmService) {
		this.orderHsrmService = orderHsrmService;
	}	

	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
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

	public MobileSimInfoService getMobileSimInfoService() {
		return mobileSimInfoService;
	}

	public void setMobileSimInfoService(MobileSimInfoService mobileSimInfoService) {
		this.mobileSimInfoService = mobileSimInfoService;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException{				
		
		System.out.println("*** formBackingObject ***");
		
		MobileSimInfoDTO mobileSimInfo = (MobileSimInfoDTO)request.getSession().getAttribute("MobileSimInfo");
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		CustomerProfileDTO  customerInfoDto = (CustomerProfileDTO)request.getSession().getAttribute("customer");
		String orderId= (String)request.getSession().getAttribute("orderIdSession");
		MnpDTO mnpDto = (MnpDTO)request.getSession().getAttribute("MNP");
		BasketDTO basketDTO = (BasketDTO) MobCcsSessionUtil.getSession(request,	"basket");
		List<MultiSimInfoDTO> multiSimInfoDTOs = new ArrayList<MultiSimInfoDTO>();
		OrderDTO orderDto = (OrderDTO) request.getSession().getAttribute("OrderDto");
		List<ClubPointDetailDTO> clubPointDetailDTO = (List<ClubPointDetailDTO>) request.getSession().getAttribute("clubPointDetailSession");
		List<MultiSimInfoDTO> tempMultiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		String[] vasItemArray = (String[]) request.getSession().getAttribute("selectedVasItemList");
		List<String> vasList = service.getSubscribedVASList(basketDTO.getBasketId(), vasItemArray, customerInfoDto.getBrand(), customerInfoDto.getSimType());
		if (vasList!= null && tempMultiSimInfoDTOs != null) {
			for (MultiSimInfoDTO msi: tempMultiSimInfoDTOs) {
				if (vasList.contains(msi.getItemId())) {
					multiSimInfoDTOs.add(msi);
				}
			}
		}
		
		SummaryDTO summary = new SummaryDTO();
		summary.setOrderId(orderId);
		if (salesUserDto.getChannelId() != 10 && salesUserDto.getChannelId() != 11) {
			mobileSimInfo.setChannelId(salesUserDto.getChannelId());
			mobileSimInfo.setChannelCd(salesUserDto.getChannelCd());
		}
		summary.setMobileSimInfoDTO(mobileSimInfo);
		if (mnpDto != null) {
			summary.setMsisdn(mnpDto.getMsisdn());
		}
		//20110915 Eliot Yuen
		summary.setMnpDTO(mnpDto);
		summary.setMultiSimInfoList(multiSimInfoDTOs);
		summary.setClubPointDetailDTO(clubPointDetailDTO);
		
		return summary;
	}

    protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		
    	System.out.println("*** referenceData ***");
    	
    	logger.info("ReferenceData called");
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
	
				
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		logger.info("SummaryController is called");
    	String locale = RequestContextUtils.getLocale(request).toString();
    	ModelDTO mobileDetail= new  ModelDTO();
  
        //get info from session
     	String orderId= (String)request.getSession().getAttribute("orderIdSession");
     	System.out.println("orderID + = + = " + orderId);
     	CustomerProfileDTO  customerInfoDto = (CustomerProfileDTO)request.getSession().getAttribute("customer");
     	//boolean originalPrvicyInd ;
     	Boolean originalPrvicyInd = (Boolean)request.getSession().getAttribute("originalPrvicyInd");
     	MnpDTO mnpDto = (MnpDTO)request.getSession().getAttribute("MNP");
     	MobDsPaymentUpfrontDTO paymentUpfrontDTO = (MobDsPaymentUpfrontDTO) request.getSession().getAttribute("paymentUpfront");
     	BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	String channelId = String.valueOf(salesUserDto.getChannelId());
     	PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");
     	MobileSimInfoDTO mobileSimInfo = (MobileSimInfoDTO)request.getSession().getAttribute("MobileSimInfo");
    	String selectedBrandId=(String)request.getSession().getAttribute("brandSession");
    	String selectedModelId=(String)request.getSession().getAttribute("modelSession");
    	String selectedColorId=(String)request.getSession().getAttribute("colorSession");
    	String selectedBasketId=(String)request.getSession().getAttribute("basketSession");
    	String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");//request.getParameterValues("vasitem");
    	List<VasDetailDTO> systemAssignVasDetailList = (List<VasDetailDTO>) request.getSession().getAttribute("systemVasItemList");
    	List<ComponentDTO> componentList = (List<ComponentDTO>) request.getSession().getAttribute("componentList");
    	SupportDocUI supportDocUI = (SupportDocUI) request.getSession().getAttribute(SupportDocController.SESSION_SUPPORTDOC);
    	
    	String iGuardSerialNo = "";
    	OrderDTO originalOrderDto = (OrderDTO) request.getSession().getAttribute("orderDtoOriginal");
    	String cloneOrderId = (String)request.getSession().getAttribute("cloneOrderId");
    	BasketDTO basketDTO = (BasketDTO) MobCcsSessionUtil.getSession(request,	"basket");
    	List<MultiSimInfoDTO> multiSimInfos = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
    	//List<DepositDTO> depositDTOList = (List<DepositDTO>)request.getSession().getAttribute("depositDTOList");
    	DepositUI depositUI = (DepositUI)MobCcsSessionUtil.getSession(request, "depositInfo");
    	String oldMsisdn = (String)request.getSession().getAttribute("msisdn");
     	//if orderId not exist then get order from db sequence, and then set it to session 
    	VasMrtSelectionDTO vasMrtSelectionDTO = (VasMrtSelectionDTO)request.getSession().getAttribute("vasMrtSelectionSession");
    	VasMrtSelectionDTO ssMrtSelectionDTO = (VasMrtSelectionDTO)request.getSession().getAttribute("ssMrtSelectionSession");
    	if("".equals(orderId) || orderId == null ){
    		if(channelId.equals("3")){
    			orderId = orderService.getShopSeqStoredProcedure(salesUserDto.getChannelCd(), channelId); //modify function from getShopSeq ==>getShopSeqStoredProcedure
    		} else {
    			orderId = orderService.getShopSeqStoredProcedure(salesUserDto.getShopCd(), channelId); //modify function from getShopSeq ==>getShopSeqStoredProcedure
    		}
    		//For direct sales clone order
    		if (orderId == null || "".equals(orderId)){
	    		if (channelId.equals("10") || channelId.equals("11")) {
	    			if (cloneOrderId != null) {
	    				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    				//Fix order by updating sales info
	    				BomSalesUserDTO originalSalesUserDto = orderService.getSalesUserInfo(originalOrderDto.getSalesCd(), df.format(originalOrderDto.getAppInDate()));
	    				if (originalSalesUserDto != null) {
	    					orderId = orderService.getShopSeqStoredProcedure(originalSalesUserDto.getShopCd(), originalSalesUserDto.getChannelId() + ""); //modify function from getShopSeq ==>getShopSeqStoredProcedure
	    				}
	    			}
	    		}
    		}
    		
    		if (orderId ==null || "".equals(orderId)){
				throw new DuplicateOrderException(
						SystemTime.asDate()	+ " Failed to get orderID due to system error, Please screen dump this page for futher investigate!!! <br>");
			}
    		logger.info("sessionId:"+sessionId);
    		logger.info("get new orderId:"+orderId);
    		
    		request.getSession().setAttribute("orderIdSession", orderId);//set it to session    		
    	}else{    
    		
			String orderStatus = orderService.getOrderStatus(orderId);
			logger.info("orderStatus@controller:"+orderStatus);

			if (salesUserDto.getChannelId() != 10 && salesUserDto.getChannelId() != 11) {
			if ((orderStatus != null) && !(BomWebPortalConstant.BWP_ORDER_INITIAL.equalsIgnoreCase(orderStatus.trim()))) {
				throw new DuplicateOrderException("Order ID : " + orderId
						+ " has been submitted!");
			}
			}

			orderService.backupOrder(orderId);//add backup
			logger.info("sessionId:"+sessionId);
			logger.info("delete orderId:"+orderId);
			
			//update msisdn_status to cancel when delete
			//String oldMsisdn = (String)request.getSession().getAttribute("msisdn");
			//deleteTableData(orderId, channelId, oldMsisdn); // 20110613 mark test
			
    	}
    	
    	iGuardSerialNo = getIGuardSerialNoFunction(selectedBasketId, selectedItemList, originalOrderDto);
     	
     	/************************ Create Order Object *****************************/
    	OrderDTO orderDto = new OrderDTO();

    	if(request.getParameter("action") != null 
    			&& ("AMEND".equalsIgnoreCase(request.getParameter("action"))) 
    			|| "AMEND".equalsIgnoreCase((String)request.getSession().getAttribute("action"))){
    		orderDto = (OrderDTO) request.getSession().getAttribute("OrderDto");
    		orderDto.setLastUpdateBy(salesUserDto.getUsername());
    	} else {
    		//OrderDTO orderDto = new OrderDTO();
        	orderDto.setOrderId(orderId);
        	orderDto.setOrderSumLob("MOB");// add 20111026
        	orderDto.setSource("WP");
        	orderDto.setBusTxnType("ACT");
        	orderDto.setMsisdn(mnpDto.getMsisdn());
        	orderDto.setMsisdnLvl(mnpDto.getMsisdnLvl());
        	orderDto.setMnpInd(mnpDto.getMnp());
        	orderDto.setShopCode(salesUserDto.getBomShopCd());
        	if (originalOrderDto!=null && cloneOrderId == null) {
        		orderDto.setShopCode(originalOrderDto.getShopCode());
        	}
        	orderDto.setOrderStatus(BomWebPortalConstant.BWP_ORDER_INITIAL);
        	
        	orderDto.setBasketId(selectedBasketId);// add 20110815
        	//add eliot 20110829
        	orderDto.setLastUpdateBy(salesUserDto.getUsername());
        	logger.info("Summart Controller MNP setting");
    		if("Y".equals(mnpDto.getMnp())){
        		logger.info("CutoverDate():"+mnpDto.getCutoverDate());
    			orderDto.setSrvReqDate(mnpDto.getCutoverDate());
        	}else{
        		logger.info("ServiceReqDate():"+mnpDto.getServiceReqDate());
    			orderDto.setSrvReqDate(mnpDto.getServiceReqDate());
        	}
        	orderDto.setAgreementNum(orderId);
        	orderDto.setAppInDate(SystemTime.asDate());
        	if (originalOrderDto!=null && cloneOrderId == null) {
        		orderDto.setAppInDate(originalOrderDto.getAppInDate());
        	}
        	if(orderDto.getCreateDate()==null){ //set cteate date
    			orderDto.setCreateDate(SystemTime.asDate());
    		}
        	if (originalOrderDto!=null && cloneOrderId == null) {
        		orderDto.setCreateDate(originalOrderDto.getCreateDate());
        	}
        	orderDto.setSalesType(mobileSimInfo.getSalesType());
        	orderDto.setSalesCd(mobileSimInfo.getSalesCd());
        	orderDto.setSalesContactNum(mobileSimInfo.getSalesContactNum());
        	//add by eliot 20110627
        	orderDto.setSalesName(mobileSimInfo.getSalesName());
        	//orderDto.setDepositWaive("Y");
        	/*
        	if("0".equals(customerInfoDto.getAddrProofInd())){
        		orderDto.setOnHoldInd("Y");
        		orderDto.setOnHoldReaCd("10");
        	}else{
        		orderDto.setOnHoldInd("N");
        	}
        	*/
        	orderDto.setOnHoldInd("N");
        	orderDto.setImei(mobileSimInfo.getImei());
			
        	//// warrantyStartDate //////
        	//// warrantPeriod ////
        	
        	//add herbert 20110707, for Advance order use. **start
        	if (mobileSimInfo.getAoInd()!=null) {
        		orderDto.setAoInd(mobileSimInfo.getAoInd()); 
        	} else {
        		orderDto.setAoInd("N"); 
        	}    	
        	
        	//add herbert 20110707, for Advance order use. **end
        	// support doc
        	orderDto.setCollectMethod(supportDocUI.getCollectMethod());
        	orderDto.setDisMode(supportDocUI.getDisMode());
        	if (supportDocUI.getDisMode() != null) {
    	    	switch (supportDocUI.getDisMode()) {
    	    	case E:
    	        	orderDto.setEsigEmailAddr(supportDocUI.getEsigEmailAddr());
    	        	orderDto.setEsigEmailLang(supportDocUI.getEsigEmailLang());
    	        	break;
    	    	case P:
    	        	orderDto.setEsigEmailAddr(null);
    	        	orderDto.setEsigEmailLang(null);
    	    		break;
    	    	}
        	}
        	
        	orderDto.setManualAfNo(supportDocUI.getManualAfNo());
        	
        	orderDto.setiGuardSerialNo(iGuardSerialNo);
    		orderDto.setMobileSafetyPhone(this.service.isItemsInGroup(selectedBasketId, selectedItemList, orderDto.getAppInDate(), "100000103"));
    		
    		orderDto.setAcctNum(customerInfoDto.getAcctNum());//OLIVER MIP4
    		orderDto.setBomCustNum(customerInfoDto.getBomCustNum());
    		orderDto.setMobCustNum(customerInfoDto.getMobCustNum());
    		orderDto.setSrvNum(customerInfoDto.getSrvNum());
    		/************************ NFC Octopus AIO****************/
    		String nfcInteger = this.service.getSimType(mobileSimInfo.getItemCd());
    		orderDto.setNfcInd(nfcInteger);
    		/************************ NFC Octopus AIO****************/
    		
    		if (channelId.equals("10") || channelId.equals("11")) {
    			if (cloneOrderId != null) {
    				orderDto.setOrderStatus(originalOrderDto.getOrderStatus());
    				/*orderDto.setOcid(originalOrderDto.getOcid());
    				orderDto.setErrorMessage(originalOrderDto.getErrorMessage());
    				orderDto.setErrorCode(originalOrderDto.getErrorCode());
    				orderDto.setCheckPoint(originalOrderDto.getCheckPoint());
    				orderDto.setBomOrderStatus(originalOrderDto.getBomOrderStatus());
    				orderDto.setBomCreationDate(originalOrderDto.getBomCreationDate());
    				orderDto.setBomCustNum(originalOrderDto.getBomCustNum());*/
    				orderDto.setShopCode(originalOrderDto.getShopCode());
    				orderDto.setSalesType(originalOrderDto.getSalesType());
    		    	orderDto.setSalesCd(originalOrderDto.getSalesCd());
    		    	orderDto.setSalesName(originalOrderDto.getSalesName());
    		    	orderDto.setAppInDate(originalOrderDto.getAppInDate());
    		    	//orderDto.setReasonCd(originalOrderDto.getReasonCd());
    		    	orderDto.setMobCustNum(originalOrderDto.getMobCustNum());
    		    	//orderDto.setSuperAppInd(originalOrderDto.getSuperAppInd());
    		    	//orderDto.setOrderAppInd(originalOrderDto.getOrderAppInd());
    		    	orderDto.setCloneOrderId(cloneOrderId);
    		    	
   		    	
    		    	referenceData.put("redirectFromClone", cloneOrderId);
    		    	request.getSession().removeAttribute("cloneOrderId");
    		    	//System.out.println("Remove session cloneOrderId");
    			} else {
    				orderDto.setOrderStatus(BomWebPortalConstant.BWP_ORDER_INITIAL);
    				//orderDto.setShopCode(salesUserDto.getAreaCd());
    				//System.out.println("No session cloneOrderID");
    				//System.out.println("originalOrderDto != null = " + (originalOrderDto != null));
    				if (originalOrderDto != null) {
    					orderDto.setCloneOrderId(originalOrderDto.getCloneOrderId());
    					//System.out.println("originalOrderDto.getOrderId() = " + originalOrderDto.getOrderId());
    				}
    			}
    		}
    	}
    	
    	/************************ Create Order Object (End)************************/
    	
    	
    	
    	/************************ Update OrderID for CustomerProfile****************/
    	customerInfoDto.setOrderId(orderId); //set order id
    	if (StringUtils.isNotEmpty(customerInfoDto.getSameAsCustInd()) && !customerInfoDto.isSameAsCust()){
    		customerInfoDto.getActualUserDTO().setOrderId(orderId);
    	}
    	/************************ Update OrderID for CustomerProfile (End)**********/
    	/************************ Bill media option(Start) Paper bill Athena 20130925**********/    	
		List<MobBillMediaDTO> billMediaList  = LookupTableBean.getInstance().getBillMediaOption();//Athena 20130925
		List<MobBillMediaDTO> selectedBillMediaList = new ArrayList(); 
		if(customerInfoDto.getSelectedBillMedia()!=null){
			for(MobBillMediaDTO mbm: billMediaList) {
				for (String billCd: customerInfoDto.getSelectedBillMedia()) {
					if (billCd.equals(mbm.getCodeId())) {
						selectedBillMediaList.add(mbm);
					}
				}
			}
		}
		String paperBillAuthId = (String) MobCcsSessionUtil.getSession(request, "AU06");//Paper bill Athena 20130925
		if (paperBillAuthId != null) {//Paper bill Athena 20130925
			//System.out.println(paperBillAuthId);
			mobCcsApprovalLogService.updateApprovalLog(orderId,paperBillAuthId, "SMAU06");
		}
		/************************ Bill media option(End)**********/  
		
		/************************ First Month Pre=payment(Start)**********/  
		String rpWaiveAuthId = (String) MobCcsSessionUtil.getSession(request, "AU16");
		if (rpWaiveAuthId != null) {
			mobCcsApprovalLogService.updateApprovalLog(orderId,rpWaiveAuthId, "SMAU16");
		}
		/************************ First Month Pre=payment(End)**********/  
		
		
		/************************ Project Seven ********************/
		if (channelId.equals("10") || channelId.equals("11")) {
			if (cloneOrderId != null) {
				//Project Seven
				if (orderHsrmService.isPrj7AttbExists(componentList)){
					orderService.copyBomwebMobOrderHsrmLog(cloneOrderId, orderId, salesUserDto.getUsername());
				}
			}
		}
		/***********************************************************/
		
    	/************************ Create SubscriberDTO *****************************/
    	SubscriberDTO subscriberDto = new SubscriberDTO();
        subscriberDto.setOrderId(orderId);
		/* New Privacy Stamp */
		if (customerInfoDto.getPrivacyStampDate() == null) {// new case

			customerInfoDto.setPrivacyStampDate(SystemTime.asDate());
			subscriberDto.setPrivacyStampDate(customerInfoDto.getPrivacyStampDate()); // update time stamp
		}

		if (originalPrvicyInd != null) { // recall

			if (originalPrvicyInd ^ customerInfoDto.isPrivacyInd()) {
				// diff
				customerInfoDto.setPrivacyStampDate(SystemTime.asDate());
				subscriberDto.setPrivacyStampDate(customerInfoDto.getPrivacyStampDate()); // update time stamp

				// remove session
				request.getSession().removeAttribute("originalPrvicyInd");
				Boolean newOriginalPrvicyInd = customerInfoDto.isPrivacyInd();
				request.getSession().setAttribute("originalPrvicyInd",	newOriginalPrvicyInd);// add 20130322
			} else {
				subscriberDto.setPrivacyStampDate(customerInfoDto.getPrivacyStampDate());
				

			}
		} else {
			subscriberDto.setPrivacyStampDate(customerInfoDto.getPrivacyStampDate());
			
		}

		if (customerInfoDto.isPrivacyInd()) {// add 20130321

			subscriberDto.setTelemkSuppressValue("Y");
			subscriberDto.setEmailSuppressValue("Y");
			subscriberDto.setSmsSuppressValue("Y");
			subscriberDto.setDmSuppressValue("Y");

			subscriberDto.setPrivacyInd("Y");
			

		} else {
			subscriberDto.setPrivacyInd("N");
			
		}
		/* End Privacy Stamp */
		/*Suppress Top-up*/ //20130827	
		if (customerInfoDto.isSuppressLocalTopUpInd()) {
			subscriberDto.setSuppressLocalTopUpInd("Y");			
		} else {
			subscriberDto.setSuppressLocalTopUpInd("N");		
		}
		
		if (customerInfoDto.isSuppressRoamTopUpInd()) {
			subscriberDto.setSuppressRoamTopUpInd("Y");		
		} else {
			subscriberDto.setSuppressRoamTopUpInd("N");		
		}
		/*Suppress Top-up*/ 
		
		/*IDD0060 opt out*/ //20140130
		if (customerInfoDto.isMob0060OptOutInd()) {
			subscriberDto.setMob0060OptOutInd("Y");		
		} else {
			subscriberDto.setMob0060OptOutInd("N");		
		}
		/*IDD0060 opt out*/
		

        subscriberDto.setAddrProofInd(customerInfoDto.getAddrProofInd());
        //If await address proof, set the address proof indicator to 0 [Address proof collected]
        /*
         *  no longer needed ... 0 => no addr proof
        if("0".equals(customerInfoDto.getAddrProofInd())){
        	  subscriberDto.setAddrProofInd("1");
        }
        */
        
        if("2".equals(customerInfoDto.getAddrProofInd())){
        	subscriberDto.setAddrProofReferrer(customerInfoDto.getServiceNum());
        } else if ("5".equals(customerInfoDto.getAddrProofInd())) {
        	if (StringUtils.isBlank(customerInfoDto.getActivationCd())) {
        		subscriberDto.setActivationCd(mobPreActReqService.generatePreActivationCode());
        	} else {
        		subscriberDto.setActivationCd(customerInfoDto.getActivationCd());
        	}
        }
        
        subscriberDto.setPwd(Utility.getDefaultPassword(customerInfoDto.getIdDocType(), customerInfoDto.getIdDocNum()));
        subscriberDto.setSmsLang(customerInfoDto.getSmsLang());
        subscriberDto.setBrand(customerInfoDto.getBrand());
        
		subscriberDto.setPcrfAlertEmail(customerInfoDto.getPcrfAlertEmail());
		subscriberDto.setSameAsEbillAddrInd(customerInfoDto.getSameAsEbillAddrInd());
		subscriberDto.setPcrfAlertType(customerInfoDto.getPcrfAlertType());
		subscriberDto.setPcrfSmsNum(customerInfoDto.getPcrfSmsNum());
		subscriberDto.setPcrfSmsRecipient(customerInfoDto.getPcrfSmsRecipient());
		subscriberDto.setPcrfMupAlert(customerInfoDto.getPcrfMupAlert());
		subscriberDto.setSmsOptOutFirstRoam(customerInfoDto.getSmsOptOutFirstRoam());
		subscriberDto.setSmsOptOutRoamHu(customerInfoDto.getSmsOptOutRoamHu());
		
		if (ssMrtSelectionDTO!=null){

			if (ssMrtSelectionDTO.isSsSubscribed()){
					if (StringUtils.equals(ssMrtSelectionDTO.getSecSrvNum(), ssMrtSelectionDTO.getDbSecSrvNum())){
						subscriberDto.setSecSrvNum(ssMrtSelectionDTO.getSecSrvNum());
						ssMrtSelectionDTO.setDbSecSrvNum(ssMrtSelectionDTO.getSecSrvNum());
						subscriberDto.setOldSecSrvNum(ssMrtSelectionDTO.getOldSecSrvNum());
					}else{
						subscriberDto.setSecSrvNum(ssMrtSelectionDTO.getSecSrvNum());
						ssMrtSelectionDTO.setDbSecSrvNum(ssMrtSelectionDTO.getSecSrvNum());
						subscriberDto.setOldSecSrvNum(ssMrtSelectionDTO.getDbSecSrvNum());
					}
			
			}else{
				subscriberDto.setSecSrvNum(null);
				ssMrtSelectionDTO.setDbSecSrvNum(null);
				subscriberDto.setOldSecSrvNum(ssMrtSelectionDTO.getDbSecSrvNum());
			}
			
		}
    	/************************ Create SubscriberDTO (End)************************/
        
        //add by wilson 20100119
        /************************ Create AccountDTO *****************************/
        
    	AccountDTO accountDto = new AccountDTO();
    	accountDto.setOrderId(orderId);
    	if ("BS".equals(customerInfoDto.getIdDocType())){ //add 20110621 wilson 
    		accountDto.setAcctName(customerInfoDto.getCompanyName().toUpperCase());
    	}else{
    		accountDto.setAcctName(customerInfoDto.getTitle().toUpperCase()+" "+ customerInfoDto.getContactName());//add title 20110607 wilson
    	}
    	accountDto.setBillFreq("MTHLY");
    	accountDto.setBillLang(customerInfoDto.getBillLang());
    	Calendar appDate = Calendar.getInstance();
    	appDate.setTime(orderDto.getAppInDate());
    	
    	if(("current").equalsIgnoreCase(customerInfoDto.getAcctType()))//OLIVER MIP4
    		accountDto.setBillPeriod(customerInfoDto.getBillPeriod());
    	else if ("0".equalsIgnoreCase(customerInfoDto.getBrand())){
    		accountDto.setBillPeriod(String.valueOf(Utility.get1010BillPeriod(appDate.get(Calendar.DATE))));
    	} else if ("1".equalsIgnoreCase(customerInfoDto.getBrand())) {
    		accountDto.setBillPeriod(String.valueOf(Utility.getCslBillPeriod(appDate.get(Calendar.DATE))));
    	}
    	
    	accountDto.setSmsNum(customerInfoDto.getContactPhone());
    	accountDto.setEmailAddr(customerInfoDto.getEmailAddr());//edit by wilson 20100120
    	accountDto.setBillLang(customerInfoDto.getBillLang());
    	accountDto.setBrand(customerInfoDto.getBrand());
    	accountDto.setSameAsCustInd(customerInfoDto.getSameAsCustInd());
    	
    	if("current".equalsIgnoreCase(customerInfoDto.getAcctType()))//OLIVER MIP4
    		accountDto.setIsNew("N");
    	else 
    		accountDto.setIsNew("Y");
    	accountDto.setAcctNum(customerInfoDto.getAcctNum());
    	accountDto.setMobCustNum(customerInfoDto.getMobCustNum());
    	accountDto.setSrvNum(customerInfoDto.getSrvNum());
    	
    	
       /************************ Create AccountDTO (End)************************/
        
       	/************************ Update OrderID for  MnpDTO ***********************/
        mnpDto.setOrderId(orderId);  
        List<MobCcsMrtBaseDTO> mrtDtoList = mobCcsMrtService.mnpDtoChangeToMrtDtoList(mnpDto, vasMrtSelectionDTO, salesUserDto.getUsername());
        String applicationDate =Utility.date2String(SystemTime.asDate(), "dd/MM/yyyy");//add by wilson 20100119
        /************************ Update OrderID for  MnpDTO (End)*******************/        
     	
        /************************ Update OrderID for  SimDTO ************************/
        mobileSimInfo.setOrderId(orderId);//set order id
     	/************************ Update OrderID for  SimDTO (End)*******************/
     	
      	/************************ Update OrderID for  PaymentDTO ********************/     
        if("C".equals(paymentDto.getPayMethodType())){//add 20110613
        	paymentDto.setCreditExpiryDate(paymentDto.getCreditExpiryMonth()+"/"+ paymentDto.getCreditExpiryYear());//edit
        }

        paymentDto.setOrderId(orderId);//set order id
     	/************************ Update OrderID for  PaymentDTO (End)***************/
    	/************************ Update AllOrdDocAssgnDTO ************************/
        if (!this.isEmpty(supportDocUI.getAllOrdDocAssgnDTOs())) {
        	for (AllOrdDocAssgnDTO dto : supportDocUI.getAllOrdDocAssgnDTOs()) {
        		dto.setOrderId(orderId);
        	}
        }
    	List<AllDocDTO> allDocDtos = this.supportDocService.getAllDocDTOKnownByTypeAndAppDate(LOB, Type.I, orderDto.getAppInDate());
		Map<DocType, List<AllOrdDocWaiveDTO>> waiveReasons = new HashMap<DocType, List<AllOrdDocWaiveDTO>>();
		for (DocType docType : DocType.values()) {
			List<AllOrdDocWaiveDTO> reasons = this.supportDocService.getAllOrdDocWaiveDTOALL(LOB, docType);
			if (!this.isEmpty(reasons)) {
				waiveReasons.put(docType, reasons);
			}
		}
    	/************************ Update AllOrdDocAssgnDTO (End)************************/
    	
    	
		// edit by wilson 20100127
		/************************ IGUARD DTO (Start)************************/
		SignoffDTO iGuardSignDto = (SignoffDTO) request.getSession().getAttribute("iGuardSignSession");
		IGuardDTO iGuardDto = new IGuardDTO();
		IGuardDTO iGuardADDto = new IGuardDTO();
		IGuardDTO iGuardUadDto = null;
		List<String> srvPlanList = iGuardService.isIGuardOrder(selectedBasketId, selectedItemList, orderDto.getAppInDate());
		for(int i=0; i<srvPlanList.size();i++){
			if (srvPlanList.get(i)!=null) {
				String srvPlanType = srvPlanList.get(i);
				if("LDS".equalsIgnoreCase(srvPlanType)){
			    	iGuardDto = iGuardService.getRsIGuardDTO(customerInfoDto,
							basketDTO, mobileSimInfo, iGuardSignDto, mnpDto, orderDto, componentList,srvPlanType);
		    	}else if("AD".equalsIgnoreCase(srvPlanType)){
		    		iGuardADDto = iGuardService.getRsIGuardDTO(customerInfoDto,
							basketDTO, mobileSimInfo, iGuardSignDto, mnpDto, orderDto, componentList,srvPlanType);
		    	}else if ("UAD".equalsIgnoreCase(srvPlanType)){
		    		iGuardUadDto = iGuardService.getRsIGuardDTO(customerInfoDto,
							basketDTO, mobileSimInfo, iGuardSignDto, mnpDto, orderDto, componentList,srvPlanType);
		    	}
				orderDto.setiGuardSerialNo(iGuardDto.getSerialNo());
			}else {
				if (!StringUtils.isEmpty(orderDto.getiGuardSerialNo())) {
						orderDto.setiGuardSerialNo("");
				}
			}
		}
		/************************ IGUARD DTO (End)************************/
		
		/************************ Direct Sales Upfront Payment DTO (Start)************************/
		List<MobDsPaymentTransDTO> paymentTransList = null;
		if ("10".equals(channelId) || "11".equals(channelId)) {
			if ((paymentUpfrontDTO != null) && (paymentUpfrontDTO.getPaymentTransList() != null)) {
				for (MobDsPaymentTransDTO paymentTransDTO: paymentUpfrontDTO.getPaymentTransList()) {
					paymentTransDTO.setOrderId(orderId);
					paymentTransDTO.setMsisdn(mnpDto.getMsisdn());
				}
				paymentTransList = paymentUpfrontDTO.getPaymentTransList();
			}
		} 
		/************************ Direct Sales Upfront Payment DTO (End)************************/
		
		/************************ Deposit DTO ****************************/
		DepositDTO depositDTOs[] = null;
		
		if (depositUI != null && CollectionUtils.isNotEmpty(depositUI.getDepositDTOList())) {
			for (DepositDTO depositDTO : depositUI.getDepositDTOList()) {
				depositDTO.setOrderId(orderId);
			}
			depositDTOs = depositUI.getDepositDTOList().toArray(new DepositDTO[]{});
		}
		orderDto.setDepositDTOs(depositDTOs);
		/************************ Deposit DTO ****************************/

		orderDto.setMultiSim(false);
		List<String> vasList = service.getSubscribedVASList(basketDTO.getBasketId(), selectedItemList, customerInfoDto.getBrand(), customerInfoDto.getSimType());
		if(multiSimInfos != null) {
			List<MultiSimInfoDTO> selectedMultiSimInfo = new ArrayList<MultiSimInfoDTO> ();
			int memNum = 1;
			for(MultiSimInfoDTO msi : multiSimInfos){
				if(vasList != null){
					for(String selectedItem : vasList){
						if(selectedItem.equals(msi.getItemId())){
							selectedMultiSimInfo.add(msi);
						}
					}
				}
			}
			multiSimInfos.clear();
			multiSimInfos.addAll(selectedMultiSimInfo);
			for(MultiSimInfoDTO msi : multiSimInfos){
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
			}
			if (multiSimInfos.size() > 0) {
				orderDto.setMultiSim(true);
			} 
		}
		
		String action = request.getParameter("action");
		if (orderId.startsWith("D") && !BomWebPortalConstant.BWP_ORDER_INITIAL.equals(orderDto.getOrderStatus()) 
     			&& !BomWebPortalConstant.BWP_ORDER_REJECTED.equals(orderDto.getOrderStatus())
     			&& ("AMEND".equalsIgnoreCase(request.getParameter("action"))) 
    			|| "AMEND".equalsIgnoreCase((String)request.getSession().getAttribute("action"))) {
			SbOrderAmendDTO dto = new SbOrderAmendDTO();
			dto.setOrderId(customerInfoDto.getOrderId());
			dto.setCreateBy(salesUserDto.getUsername());
			dto.setLastUpdBy(salesUserDto.getUsername());
			
			if (customerInfoDto.isAmend()) {
				if("0".equals(customerInfoDto.getAddrProofInd())){
	        		orderDto.setOnHoldInd("Y");
	        		orderDto.setOnHoldReaCd("10");
	        	}else{
	        		orderDto.setOnHoldInd("N");
	        	}
				//Insert amend order only when it has OCID
				if(orderDto.getOcid() != null){
					dto.setOrderAmendType("OA01");
					mobCcsSbOrderAmendService.insertSbOrderAmendDTO(dto);
				}	
			}
			if (mnpDto.isAmend()) {
				orderDto.setMsisdn(mnpDto.getMsisdn());
	        	orderDto.setMsisdnLvl(mnpDto.getMsisdnLvl());
	        	orderDto.setMnpInd(mnpDto.getMnp());

	    		if("Y".equals(mnpDto.getMnp())){
	        		logger.info("CutoverDate():"+mnpDto.getCutoverDate());
	    			orderDto.setSrvReqDate(mnpDto.getCutoverDate());
	        	}else{
	        		logger.info("ServiceReqDate():"+mnpDto.getServiceReqDate());
	    			orderDto.setSrvReqDate(mnpDto.getServiceReqDate());
	        	}
	    		//Insert amend order only when it has OCID
				if(orderDto.getOcid() != null){
					if ("N".equals(mnpDto.getMnp())) {
						dto.setOrderAmendType("OA02"); //New Number SRD is amend.
						mobCcsSbOrderAmendService.insertSbOrderAmendDTO(dto);
					} else {
						dto.setOrderAmendType("OA03"); //MNP info is amend.
						mobCcsSbOrderAmendService.insertSbOrderAmendDTO(dto);
					}
				}
			}
			if (mobileSimInfo.isAmend()) {
				orderDto.setSalesType(mobileSimInfo.getSalesType());
	        	orderDto.setSalesCd(mobileSimInfo.getSalesCd());
	        	orderDto.setSalesContactNum(mobileSimInfo.getSalesContactNum());
	        	orderDto.setSalesName(mobileSimInfo.getSalesName());
	        	orderDto.setImei(mobileSimInfo.getImei());

	        	if (mobileSimInfo.getAoInd()!=null) {
	        		orderDto.setAoInd(mobileSimInfo.getAoInd()); 
	        	} else {
	        		orderDto.setAoInd("N"); 
	        	}
	        	//Insert amend order only when it has OCID
				if(orderDto.getOcid() != null){
					dto.setOrderAmendType("OA04");
					mobCcsSbOrderAmendService.insertSbOrderAmendDTO(dto);
				}
			}
			boolean isMSAmendSim = false;
			boolean isMSAmendMnp = false;
			for (MultiSimInfoDTO msi: multiSimInfos) {
				if (msi.isAmend()) {
					isMSAmendMnp = true;
				}
				if (msi.isAmendSim()) {
					isMSAmendMnp = true;
				}
			}
			if(orderDto.getOcid() != null){
				if (isMSAmendSim) {
					dto.setOrderAmendType("OA04");
					mobCcsSbOrderAmendService.insertSbOrderAmendDTO(dto);
				}
				if (isMSAmendMnp) {
					dto.setOrderAmendType("OA03"); //MNP info is amend.
					mobCcsSbOrderAmendService.insertSbOrderAmendDTO(dto);
				}
			}
     	}
		
		if (orderId != null){
			OrderMobDTO dto = orderService.getOrderMobDTO(orderId);
			if (dto != null){
				String csubInd = dto.getCsubInd();
				if ("Y".equals(csubInd)){
			    	if (customerInfoDto != null){
			    		customerInfoDto.setCustomerTier("62");
			    		request.getSession().setAttribute("customer", customerInfoDto);
			    	} 
				}
			}
		}
		
		deleteTableData(orderId, channelId, oldMsisdn, orderDto.getSalesCd(), orderDto.getAppInDate());
		//VasDetailDTO vasDetailDto = null;
		VasDetailDTO vasDetailDto =  (VasDetailDTO) MobCcsSessionUtil.getSession(request, "vasDetail");
		
		/************Quota Plan ************/
		List<MobBuoQuotaDTO> mobBuoQuotaDtoList = new ArrayList<MobBuoQuotaDTO>();
		Map<String,QuotaPlanInfoUI> quotaPlanInfoMap = (Map<String,QuotaPlanInfoUI>)request.getSession().getAttribute("quotaPlanInfoMapSession");
		if (quotaPlanInfoMap!=null){	
			for (Map.Entry<String, QuotaPlanInfoUI> entry : quotaPlanInfoMap.entrySet()) {
				MobBuoQuotaDTO mobBuoQuotaDto = new MobBuoQuotaDTO();
				if ("Y".equals(entry.getValue().getIotPlan())) {
					mobBuoQuotaDto.setAutoTopUpInd("P");
					if ("Y".equals(entry.getValue().getSuppressLocal())) {
						subscriberDto.setSuppressLocalTopUpInd("Y");
						customerInfoDto.setSuppressLocalTopUpInd(true);
					}
					if ("Y".equals(entry.getValue().getSuppressRoam())) {
						subscriberDto.setSuppressRoamTopUpInd("Y");
						customerInfoDto.setSuppressRoamTopUpInd(true);
					}
				} else {
					mobBuoQuotaDto.setAutoTopUpInd(entry.getValue().getAutoTopUpInd());
				}
				mobBuoQuotaDto.setBuoId(entry.getValue().getSelectedQuotaPlanOption());
				mobBuoQuotaDto.setMaxCntAutoTopUp(entry.getValue().getMaxTopUpTimes());
				mobBuoQuotaDto.setItemId(entry.getValue().getItemId());
				mobBuoQuotaDto.setCreateBy(salesUserDto.getUsername());
				mobBuoQuotaDto.setLastUpdBy(salesUserDto.getUsername());
				mobBuoQuotaDtoList.add(mobBuoQuotaDto);
			}
		}
		/*************Quota Plan End***********/
		
		List<String> authorizeLog = new ArrayList<String>();
		if (StringUtils.isNotEmpty((String)MobCcsSessionUtil.getSession(request, "AU18"))) {
			authorizeLog.add((String)MobCcsSessionUtil.getSession(request, "AU18"));
		}
		
		if (StringUtils.isNotEmpty((String)MobCcsSessionUtil.getSession(request, "AU19"))) {
			authorizeLog.add((String)MobCcsSessionUtil.getSession(request, "AU19"));
		}
		
		/************************ Staff Sponsorship *************************/ 
		MobSponsorshipDTO mobSponsorshipDTO = null;
		mobSponsorshipDTO = (MobSponsorshipDTO)MobCcsSessionUtil.getSession(request, "mobSponsorshipDTO");
		if (mobSponsorshipDTO != null && StringUtils.isNotEmpty(mobSponsorshipDTO.getStaffId())) {
			orderDto.setMobSponsorshipDTO(mobSponsorshipDTO);
		}
		/************************ Staff Sponsorship *************************/
		
		orderService.insertBomWebAll(orderId, selectedBasketId
				, orderDto, subscriberDto, mnpDto
				, mobileSimInfo, paymentDto
				, accountDto, customerInfoDto, selectedItemList, componentList, channelId
				, supportDocUI.getAllOrdDocAssgnDTOs(), iGuardDto
				, vasDetailDto, basketDTO, paymentTransList, multiSimInfos
				, systemAssignVasDetailList, mrtDtoList, mobBuoQuotaDtoList, authorizeLog);
     	customerProfileService.insertBomCustomerProfile(customerInfoDto);
     	
     	SuperOrderDTO superOrderDto = (SuperOrderDTO) request.getSession().getAttribute("superOrderDto");
     	superOrderDto.setOrderId(orderId);
    	List<ClubPointDetailDTO> clubPointDetailDTO = orderService.getClubPointDetailsByOrderId(orderId);
    	/************************ HKTcare validation *************************/
    	boolean isTravelInsurance=false;
    	boolean isHelperCareInsurance=false;
    	if(selectedItemList!=null){
    		isTravelInsurance =service.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE,selectedItemList);
    		isHelperCareInsurance=service.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_HELPER_CARE,selectedItemList);
    	}
    	/************************ Project Eagle validation *************************/
    	boolean isProjectEagle=false;
    	if(selectedItemList!=null){
    		isProjectEagle =service.existInSelectionGrpList(PROJECT_EAGLE_ITEM_SELECTTION_GROUP_ID,selectedItemList);
    	}
     	// Update Quota Usage ... if any ..
		/****************************** Start Quota Validation ***********************************/
		if ("HKID".equalsIgnoreCase(customerInfoDto.getIdDocType()) || "PASS".equalsIgnoreCase(customerInfoDto.getIdDocType())) {
			BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
			VasDetailDTO vasDetail =  (VasDetailDTO) MobCcsSessionUtil.getSession(request, "vasDetail");
			BasketDTO sessionBasket =  (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
			
			// get the list of finalQuotaList again, in case not come from prev. page...
			List<String> selectedVasItemList = null;
			if (vasDetail.getVasitem() != null && vasDetail.getVasitem().length > 0) {
				selectedVasItemList = Arrays.asList(vasDetail.getVasitem());
			}
			vasDetail.setFinalOuotaList(mobItemQuotaService.getFinalQuota(sessionBasket.getBasketId(), Utility.date2String(orderDto.getAppInDate(), "dd/MM/yyyy"), selectedVasItemList));
			
			//createMobQuotaConsumeRequest
			String authBy = MobCcsAuthorizeController.getSessionAuthorizedBy(request, "AU15");  // tmp use AU15 ...
			QuotaConsumeRequest qcr = mobItemQuotaService.createMobQuotaConsumeRequest(
					customerInfoDto.getIdDocType(), customerInfoDto.getIdDocNum(), orderId, 
					user.getUsername(), vasDetail, authBy, orderDto.getAppInDate());
			
			//Validate Quota
			superOrderDto.setQuotaConsumeRequest(null);
			
			boolean validQuota = true;
			if (CollectionUtils.isNotEmpty(qcr.getItems())) {
				validQuota = this.checkQuota(qcr, referenceData);
			} else {
				qcr = null;
			}
			
			if (qcr != null) {
				superOrderDto.setQuotaConsumeRequest(qcr);
				if (validQuota) {
					try {
 	 					MobQuotaUsageDTO[] quotaUsage = mobQuotaService.consumeQuota(qcr);
 	 				} catch (Exception e) {
 	 					logger.error("Error when consuming quota in ConfirmationController", e);
 	 					remarkService.insertOrderRemark(salesUserDto.getSalesCd(), orderId, "Error when consuming quota");
 	 				}
				}
			}
		}
		/****************************** End Quota Validation ***********************************/
 
    	String selectedColorName="";
    	String pageMessage="";
    	
    	//get Model detail
    	if (!"".equals(selectedBrandId) && ! "".equals(selectedModelId) && !"".equals(selectedColorId)){
    		if (selectedBrandId!=null && selectedModelId!=null && selectedColorId !=null ){
    			
    			//get selectedmodelDetail
    			mobileDetail =mobileDetailService.getMobileDetail(locale,selectedBrandId, selectedModelId ,applicationDate);
	    		//get color name
	    		for (int i=0; i<mobileDetail.getColorDto().size(); i++){
	    			if (selectedColorId.equals(mobileDetail.getColorDto().get(i).getColorId())){
	    				selectedColorName =mobileDetail.getColorDto().get(i).getColorName();
	    			}
	    		}
    		}
    	}
    	
    	//get  vasDetailList
    	
    	//List<VasDetailDTO> vasDetailList=new ArrayList<VasDetailDTO>(); 
    	List<VasDetailDTO> vasHSRPList=new ArrayList<VasDetailDTO>(); 
    	
    	List<VasDetailDTO> vasReportUseDetailList=new ArrayList<VasDetailDTO>(); 
    	List<VasDetailDTO> vasHSRPReportUseList=new ArrayList<VasDetailDTO>(); 
    	List<VasDetailDTO> vasGifsReportUseDetailList=new ArrayList<VasDetailDTO>(); 
    	List<VasDetailDTO> vasOptionalReportUseList=new ArrayList<VasDetailDTO>(); 
    	
    	
    	if (!"".equals(selectedBasketId) && selectedBasketId !=null ){	    	
	    	/*
	    	//insert item info insert to db
        	for ( int i=0; i<selectedItemList.length; i++){
        		orderService.insertBomWebSubscribedItem(orderId, selectedItemList[i], "VAS"); //edit by wilson 20100125
        	}*/
        		vasHSRPList=service.getVasDetailSelectedList(locale, orderId);//edit by wilson 20110127 use new sql
	    		
	    		vasReportUseDetailList=service.getReportUseRPHSRPList(locale, selectedBasketId, "SS_FORM_RP",orderId); //for rate plan
	    			    		
	    		vasHSRPReportUseList=service.getReportUseVasDetailtList(locale, orderId, selectedBasketId);
	    		// REPORT use free Gifs VAS
				vasGifsReportUseDetailList =service.getReportUseFreeGifsDetailtList(
						locale, orderId, selectedBasketId);//add by wilson 20110726
				// REPORT use optional VAS
				vasOptionalReportUseList= service.getReportUseVasOptionalDetailtList(locale, orderId, selectedBasketId);//add by wilson 20110726
	    		
    	} 
    	
    	/*********** Save Rate Plan and VAS to session **************/
    	ServicePlanReportDTO servicePlanReport = new ServicePlanReportDTO();
    	servicePlanReport.setMainServDtls(vasReportUseDetailList);
    	servicePlanReport.setVasFreeGifsDtls(vasGifsReportUseDetailList); //add by wilson 20110726
    	servicePlanReport.setVasOptionalDtls(vasOptionalReportUseList); //add by wilson 20110726
    	servicePlanReport.setVasDtls(vasHSRPReportUseList);
    	
    	//servicePlanReport.setMainServDtls(vasHSRPReportUseList);
    	//servicePlanReport.setVasDtls(vasReportUseDetailList);
    	
    	//addby wilson 20110121
    	servicePlanReport.setHandsetDeviceAmount( Long.toString( orderService.getHandsetDeviceAmount(orderId)) );
    	servicePlanReport.setFirstMonthFee(Long.toString(orderService.getFirstMonthFee(orderId) ));
    	servicePlanReport.setFirstMonthServiceLicenceFee(Long.toString(orderService.getFirstMonthServiceLicenceFee(orderId))); //add by wilson20110722
    	
    	List<VasOnetimeAmtDTO> vasOnetimeAmtList
			= orderService.getVasOnetimeAmtList(locale, orderId);
		int vasOnetimeAmtFee = 0;
		servicePlanReport.setVasOnetimeAmtList(vasOnetimeAmtList);
		if (vasOnetimeAmtList != null
				&& vasOnetimeAmtList.size() >= 0) {
			for (VasOnetimeAmtDTO dto: vasOnetimeAmtList) {
				vasOnetimeAmtFee += Integer.parseInt(dto.getVasOnetimeAmt());
			}
		}
		servicePlanReport.setVasOnetimeAmtFee("" + vasOnetimeAmtFee);
		
    	servicePlanReport.setRebateList(orderService.getRebateList(locale, orderId));
    	
    	int billPeriod = Integer.parseInt(accountDto.getBillPeriod());
		servicePlanReport.setBillPeriod(""+(billPeriod-1));//edit 20110621, past to report value = accountDto.getBillPeriod()-1
    	
		servicePlanReport.setPenaltyinfo(orderService.getPenaltyInfoList(orderId));//[0]contract_period, [1]penalty_type [2]penalty_amt
    	if (!"".equals(selectedModelId) && selectedModelId!=null){
    		servicePlanReport.setHandsetDeviceInfo(orderService.getHandsetDeviceDescription(locale, selectedBrandId, selectedModelId, selectedColorId));
    	}    	
    	servicePlanReport.setSecSrvContractPeriod(orderService.getSecSrvContractPeriod(orderId));
    	//add 20110615, set setConciergeInd
    	/*if (("".equals(mnpDto.getCutoverDateStr()) || mnpDto.getCutoverDateStr()==null) &&
        		("".equals(mnpDto.getServiceReqDateStr()) || mnpDto.getServiceReqDateStr()==null)){
    		logger.info("ConciergeInd=Y");
     		servicePlanReport.setConciergeInd("Y");
    		
    	}else{
    		logger.info("ConciergeInd=N");
    		servicePlanReport.setConciergeInd("N");
    	}*/
    	
    	servicePlanReport.setConciergeInd(orderService.getConciergeInd(orderId));//edit 20110620
		logger.info("ConciergeInd:"+servicePlanReport.getConciergeInd());
		
		//add by eliot 20110824
		servicePlanReport.setBasketType(orderService.getBasketType(orderId));//edit 20110620
		logger.info("BasketType:"+servicePlanReport.getBasketType());    	
    	
		String salesLocation = "";
		Map<String,String> dsLocationList = mobileSimInfoService.getDSLocationList();
		if (mobileSimInfo.getSalesLocation()!=null) {
			salesLocation = dsLocationList.get(mobileSimInfo.getSalesLocation());
		}
		
    	/*********** Save session **************/
		request.getSession().setAttribute("orderId", orderId);
		request.getSession().setAttribute("msisdn", orderDto.getMsisdn());
		request.getSession().setAttribute("iccid", mobileSimInfo.getIccid());
    	request.getSession().setAttribute("orderDto", orderDto);
    	request.getSession().setAttribute("accountDto", accountDto);
    	request.getSession().setAttribute("servicePlanReport", servicePlanReport);
    	request.getSession().setAttribute("MobileSimInfo", mobileSimInfo);
    	request.getSession().setAttribute("MNP", mnpDto);    	
    	request.getSession().setAttribute("paymentUpfront", paymentUpfrontDTO);
    	request.getSession().setAttribute("subscriberDto", subscriberDto);  
    	request.getSession().setAttribute("vasHSRPList", vasHSRPList);  //add by wilson 20110131 
    	request.getSession().setAttribute("applicationDate", applicationDate);  //add by wilson 20110131 
    	request.getSession().setAttribute("mobileDetail", mobileDetail);  //add by wilson 20110131 
    	request.getSession().setAttribute("selectedColorName", selectedColorName);  //add by wilson 20110131
    	request.getSession().setAttribute("salesLocation", salesLocation);
    	request.getSession().setAttribute(SupportDocController.SESSION_SUPPORTDOC, supportDocUI);
    	MobCcsSessionUtil.setSession(request, "depositInfo", depositUI);

    	request.getSession().setAttribute("MultiSimInfoList", multiSimInfos);	//add by nancy 20140121
    	request.getSession().setAttribute("ssMrtSelectionSession", ssMrtSelectionDTO);
    	request.getSession().setAttribute("superOrderDto", superOrderDto);
    	request.getSession().setAttribute("clubPointDetailSession", clubPointDetailDTO);
    	/************************************************************/
    	if(orderId.startsWith("D") && request.getParameter("action") != null){
    		request.getSession().setAttribute("action", action);
    	}
    	/*************Creat HS_TRADE_DESC DTO*******************************/ //add by eliot 20110527
    	HSTradeDescDTO hsTradeDesc = new HSTradeDescDTO();
    	
    	if(!"".equals(orderId) || orderId != null ){
    		hsTradeDesc = mobileDetailService.getHSTradeDesc(orderId);
    	}
    									   
    	request.getSession().setAttribute("hsTradeDescSummary", hsTradeDesc);
    	
    	
		//orderDto.setNfcSim(this.service.hasNFCSim(orderId));
		//orderDto.setOctopusSim(this.service.hasOctopusSim(orderId));
		 	
//    	referenceData.put( "orderId", orderId); 
//    	referenceData.put( "msisdn", mnpDto.getMsisdn()); 
//    	referenceData.put( "iccid", mobileSimInfo.getIccid());

		referenceData.put("billDate", billPeriod - 1);
    	
    	referenceData.put( "selectedBrandId", selectedBrandId); 
    	referenceData.put( "selectedModelId", selectedModelId); 
    	referenceData.put( "selectedColorId", selectedColorId); 

    	referenceData.put( "mobileDetail", mobileDetail); // return mobileDetail for image
    	referenceData.put( "selectedColorName", selectedColorName); //return selectedImageName 
    	
    	referenceData.put( "pageMessage", pageMessage);
    	//referenceData.put( "vasDetailList", vasDetailList); 
    	referenceData.put( "vasHSRPList", vasHSRPList); 
    	
    	referenceData.put("customerInfoDto", customerInfoDto); // return customer profile DTO
    	referenceData.put("selectedBillMediaList", selectedBillMediaList); //Paper bill Athena 20130925
    	referenceData.put( "mnpDto", mnpDto);
    	referenceData.put( "paymentUpfrontDto", paymentUpfrontDTO);
    	referenceData.put( "paymentDto", paymentDto);
    	referenceData.put( "applicationDate", applicationDate);//add by wilson 20100119
    			
    	referenceData.put( "sessionCutoverDateStr",  mnpDto.getCutoverDateStr());
    	referenceData.put( "sessionServiceReqDateStr", mnpDto.getServiceReqDateStr());
    	
    	referenceData.put( "salesUserDto", salesUserDto);//for app
    
    	referenceData.put( "currentSessionId", sessionId);//for app
    	
    	referenceData.put("allDocDtos", allDocDtos);//all doc, id , desc
		referenceData.put("waiveReasons", waiveReasons);//list of waive id , desc
		
		referenceData.put("mobileSimInfo", mobileSimInfo);

		List<String> vasTempList = new ArrayList<String>();
		for (VasDetailDTO vas :vasOptionalReportUseList) {
			vasTempList.add(vas.getItemId());
		}
		String [] vasGuardList = (String[]) vasTempList.toArray(new String[2]);
    	List<String> iGuardList = iGuardService.isIGuardOrder(orderDto.getBasketId(), vasGuardList, orderDto.getAppInDate());
    	request.getSession().setAttribute("iGuardList", iGuardList);
    	referenceData.put("iGuardList", iGuardList);
		referenceData.put("iGuardDto", iGuardDto);// add by wilson 20121010
		referenceData.put("iGuardADDto", iGuardADDto);
		referenceData.put("iGuardUadDto", iGuardUadDto);
		referenceData.put("MultiSimInfoList", multiSimInfos);	//add by nancy 20140121
		referenceData.put("clubPointDetailDTO", clubPointDetailDTO);
		referenceData.put("theClubLogin", customerInfoDto.getTheClubLogin());
		
		/*Signture Dto check*/
		SignoffDTO customerSignDto  = (SignoffDTO)request.getSession().getAttribute("customerSignSession");
		referenceData.put("customerSignDto", customerSignDto);
		
		SignoffDTO mnpSignDto  = (SignoffDTO)request.getSession().getAttribute("mnpSignSession");
		referenceData.put("mnpSignDto", mnpSignDto);
		
		SignoffDTO bankSignDto  = (SignoffDTO)request.getSession().getAttribute("bankSignSession");
		referenceData.put("bankSignDto", bankSignDto);
		
		SignoffDTO conciergeSignDto  = (SignoffDTO)request.getSession().getAttribute("conciergeSignSession");
		referenceData.put("conciergeSignDto", conciergeSignDto);
		
		referenceData.put("iGuardSignDto", iGuardSignDto);
		
		referenceData.put("isTravelInsurance", isTravelInsurance);
		referenceData.put("isHelperCareInsurance", isHelperCareInsurance);
		referenceData.put("isProjectEagle", isProjectEagle);
		/*SignoffDTO tdoSignDto  = (SignoffDTO)request.getSession().getAttribute("tdoSignSession");
		referenceData.put("tdoSignSession", tdoSignDto);*/
		
		referenceData.put("depositInfo", depositUI);
		referenceData.put("basket", basketDTO);
		
		referenceData.put("cloneOrderDto", orderService.getOrder(orderDto.getCloneOrderId()));
    	
		//check 1C2N or JOC	
		if (selectedItemList!=null){
			if (service.validJOC(orderDto.getBasketId(), Arrays.asList(selectedItemList), orderDto.getAppInDate())) {
				referenceData.put("jocInd", true);
			} else {
				referenceData.put("jocInd", false);
			}
		}
		
		if (DisMode.P == supportDocUI.getDisMode()) {
			referenceData.put("signCaptureAllOrdDocDTOList", null);
		} else {
			reportService.generateAndSaveDigitalModePdf(request, true);
			referenceData.put("signCaptureAllOrdDocDTOList", supportDocService.retrieveAllOrdDocDTOList(orderId, orderDto.getAppInDate(), true));
		}
		
		return referenceData;
	}
    
    
    //call service to delete customer  and order data
    private void deleteTableData(String orderId, String channelId, String oldMsisdn, String staffId, Date appDate){
    	customerProfileService.deleteBomCustomerProfile(orderId);
    	orderService.deleteBomWebAllOrder(orderId, channelId, oldMsisdn, staffId, appDate);
    }
    
    public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
	throws ServletException, AppRuntimeException {
    	
    	System.out.println("*** onSubmit ***");
    	
    	logger.info("summarycontroler.onSubmit is call");
    	
    	SummaryDTO summaryDTO = (SummaryDTO)command;
    	String orderId = (String)request.getSession().getAttribute("orderId");
    	
    	summaryDTO.setOrderId(orderId);
    	//summaryDTO.setMsisdn((String)request.getSession().getAttribute("msisdn"));
    	summaryDTO.setMobileSimInfoDTO((MobileSimInfoDTO)request.getSession().getAttribute("MobileSimInfo"));
    	
    	summaryDTO.setMnpDTO((MnpDTO)request.getSession().getAttribute("MNP"));
    	summaryDTO.setMsisdn(summaryDTO.getMnpDTO().getMsisdn());//replace get from session request.getSession().getAttribute("msisdn")); 
    	logger.info("SummaryController.handleRequest() End");
    	String nextView = "confirmation.html?orderId=" + orderId;
    	SupportDocUI supportDocUI = (SupportDocUI) request.getSession().getAttribute(SupportDocController.SESSION_SUPPORTDOC);
      	PaymentDTO paymentDto = (PaymentDTO)request.getSession().getAttribute("payment");
      	MnpDTO mnpDto = (MnpDTO)request.getSession().getAttribute("MNP");
      	OrderDTO orderDto = (OrderDTO) request.getSession().getAttribute("orderDto");
      	List<ComponentDTO> componentList = (List<ComponentDTO>) request.getSession().getAttribute("componentList");
    	BasketDTO basketDTO = (BasketDTO) MobCcsSessionUtil.getSession(request,	"basket");
    	BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	MobileSimInfoDTO mobileSimInfo = (MobileSimInfoDTO)request.getSession().getAttribute("MobileSimInfo");
    	CustomerProfileDTO sessionCustomer=(CustomerProfileDTO) request.getSession().getAttribute("customer");
    	//IGuardDTO iGuardDTO = (IGuardDTO) request.getSession().getAttribute("iGuardDto");

      	//check all signature is collected
    	ModelAndView summaryModelAndView = new ModelAndView(new RedirectView("summary.html"));
    	String attrUid=(String)request.getParameter("sbuid");
		summaryModelAndView.addObject("sbuid", attrUid);
		
		
    	boolean signatureAllCollectedInd=true;
    	if(supportDocUI.getDisMode()==DisMode.E){//check only is e-sign flow
    		
    		//0.tdo signature must collect
    		/*if (!(request.getSession().getAttribute("tdoSignSession") instanceof SignoffDTO)) {
				summaryModelAndView.addObject("tdoSignPresent", false);
				signatureAllCollectedInd=false;
			}*/
    		
    		//1.customer signature must collect
    		if (!(request.getSession().getAttribute("customerSignSession") instanceof SignoffDTO)) {
				summaryModelAndView.addObject("customerSignPresent", false);
				signatureAllCollectedInd=false;
			}
    		
    		//2.bank signature must collect when not equals to cash payment
    		if(!"M".equalsIgnoreCase(paymentDto.getPayMethodType())){
	    		if (!(request.getSession().getAttribute("bankSignSession") instanceof SignoffDTO)) {
					summaryModelAndView.addObject("bankSignPresent", false);
					signatureAllCollectedInd=false;
				}
    		}
    		//3.MNP signature must collect when MNP
    		if("Y".equalsIgnoreCase(mnpDto.getMnp())){
	    		if (!(request.getSession().getAttribute("mnpSignSession") instanceof SignoffDTO)) {
					summaryModelAndView.addObject("mnpSignPresent", false);
					signatureAllCollectedInd=false;
				}
    		}
    		//4.Concierge signature must collect when basket type =concierge
			if (this.isConcierge(request.getSession())) {
				if (!(request.getSession().getAttribute("conciergeSignSession") instanceof SignoffDTO)) {
					summaryModelAndView.addObject("conciergeSignPresent", false);
					signatureAllCollectedInd=false;
				}
			}
			// 5.iGuard signature
			String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		
			List<String> iGuardList=iGuardService.isIGuardOrder(basketDTO.getBasketId(), selectedVasItemList, orderDto.getAppInDate());
			for(int i=0; i<iGuardList.size();i++){
				if("LDS".equalsIgnoreCase(iGuardList.get(i))){
					if (!StringUtils.isEmpty(orderDto.getiGuardSerialNo())) {
						if (!(request.getSession().getAttribute("iGuardSignSession") instanceof SignoffDTO)) {
							summaryModelAndView.addObject("iGuardSignPresent", false);
							signatureAllCollectedInd = false;
						}
					}
				}
				else if("AD".equalsIgnoreCase(iGuardList.get(i))){
					if (!(request.getSession().getAttribute("iGuardSignSession") instanceof SignoffDTO)) {
						summaryModelAndView.addObject("iGuardSignPresent", false);
						signatureAllCollectedInd = false;
					}
				}
				else if("UAD".equalsIgnoreCase(iGuardList.get(i))){
					if (!(request.getSession().getAttribute("iGuardSignSession") instanceof SignoffDTO)) {
						summaryModelAndView.addObject("iGuardSignPresent", false);
						signatureAllCollectedInd = false;
					}
				}
			}
			
			if ("I".equalsIgnoreCase(sessionCustomer.getCareOptInd())){
				if (!(request.getSession().getAttribute("iGuardSignSession") instanceof SignoffDTO)) {
					summaryModelAndView.addObject("iGuardSignPresent", false);
					signatureAllCollectedInd = false;
				}
			}
			// 6.Travel Insurance signature
			if (service.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE,selectedVasItemList)){
				if (!(request.getSession().getAttribute(ReportSessionName.SIGNOFF.getTravelInsuranceSignDtoName()) instanceof SignoffDTO)) {
					summaryModelAndView.addObject("travelInsuranceSignPresent", false);
					signatureAllCollectedInd = false;
				}
			}
			// 7.Helper Insurance signature
			if (service.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_HELPER_CARE,selectedVasItemList)){
				if (!(request.getSession().getAttribute(ReportSessionName.SIGNOFF.getHelperCareInsuranceSignDtoName()) instanceof SignoffDTO)) {
					summaryModelAndView.addObject("helperCareInsurancePresent", false);
					signatureAllCollectedInd = false;
				}
			}

			// 8.Project Eagle Insurance signature
			if (service.existInSelectionGrpList(PROJECT_EAGLE_ITEM_SELECTTION_GROUP_ID,selectedVasItemList)){
				if (!(request.getSession().getAttribute(ReportSessionName.SIGNOFF.getProjectEagleInsuranceSignDtoName()) instanceof SignoffDTO)) {
					summaryModelAndView.addObject("projectEaglePresent", false);
					signatureAllCollectedInd = false;
				}
			}
			
    	}
    	
    	if (!signatureAllCollectedInd){
    		return summaryModelAndView;
    	}
    	
    	/************************ Start HSRM ***************************/
    	HsrmDTO validateHSRMResult = new HsrmDTO();
    	//Queue Ref HSRM validation
    	if (!summaryDTO.isIgnoreHSRMCheck()){
	    	validateHSRMResult = orderHsrmService.validateHSRM(componentList,orderId,""+salesUserDto.getChannelId(), basketDTO.getHsPosItemCd(), salesUserDto.getUsername(), sessionCustomer.getIdDocNum(), orderDto.getAppInDate(),basketDTO.getMipBrand());
	    	if (!validateHSRMResult.getReturnBool()){
	    		summaryModelAndView.addObject("passHsrmComplete","Validation failure on HSRM Pre-reg number: "+validateHSRMResult.getReturnMessage());
	    		return summaryModelAndView;
	    	}else{
				if (StringUtils.isNotBlank(validateHSRMResult.getReturnMessage())){
					errors.rejectValue("ignoreHSRMCheck", "dummy", validateHSRMResult.getReturnMessage());
					Map model = errors.getModel();
					try {
						model.putAll(referenceData(request));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return new ModelAndView("summary", model);
				}
			}
	    	
	    	/*if(!validateHSRMResult.getReturnBool()){
	    		summaryModelAndView.addObject("passHsrmComplete", "Validate HSRM Pre-registration number failed: "+validateHSRMResult.getReturnMessage());
	    		return summaryModelAndView;
	    	}*/
    	}
    	
    	
    	
    	
    	boolean passHsrmComplete = true;
    	//Project Seven: submit key infromaton for pre-registration marking off process 	
    	boolean hsrmCompleted = orderHsrmService.hsrmCompleted(orderId);
    	/*if (salesUserDto.getChannelId() == 10 || salesUserDto.getChannelId() == 11) {
    		if (CollectionUtils.isNotEmpty(componentList)) {
    			for (ComponentDTO component : componentList) {
    				List<OrderHsrmLogDTO> hsrmLogList = orderHsrmService.getOrderHsrmLog(orderId, component.getCompAttbVal());

            		if (CollectionUtils.isNotEmpty(hsrmLogList)) {
            			for (OrderHsrmLogDTO log : hsrmLogList) {
            				if ("Completed".equalsIgnoreCase(log.getActionInd()) && "Succeed".equalsIgnoreCase(log.getStatus())) {
            					needHsrmComplete = false;
            				}
            			}
            		}
    			}
    		}
    	}*/
    	
    	if (!hsrmCompleted) {
	    	boolean aoInd = false;
	    	if (salesUserDto.getChannelId() == 10 || salesUserDto.getChannelId() == 11) {
	    		List<StockAssgnDTO> stockAssgnList = mobileSimInfo.getStockAssgnList();
	    		if (CollectionUtils.isNotEmpty(stockAssgnList)) {
	    			for (StockAssgnDTO stock : stockAssgnList) {
	    				if (stock.getItemCode().equalsIgnoreCase(basketDTO.getHsPosItemCd())) {
	    					aoInd = "Y".equals(mobileSimInfo.getAoInd());
	    				}
	    			}
	    		}
	    	} else {
	    		aoInd = "Y".equals(mobileSimInfo.getAoInd());
	    	}
    	
	    	if (!summaryDTO.isIgnoreHSRMComplete()){
		    	ResultDTO hsrmResult = orderHsrmService.completeHSRMOrder(componentList, orderId, basketDTO.getHsPosItemCd(), salesUserDto.getUsername(), aoInd,validateHSRMResult);
		    	if (!hsrmResult.getReturnBool() /*&& (salesUserDto.getChannelId() == 1 || salesUserDto.getChannelId() == 10 || salesUserDto.getChannelId() == 11)*/) {
		    		passHsrmComplete = false;
		    		summaryModelAndView.addObject("passHsrmComplete", "HSRM Pre-registration number \"complete\" failed: "+hsrmResult.getReturnMessage());
		    	}else{
					if (StringUtils.isNotBlank(hsrmResult.getReturnMessage())){
						errors.rejectValue("ignoreHSRMComplete", "dummy", hsrmResult.getReturnMessage());
						Map model = errors.getModel();
						try {
							model.putAll(referenceData(request));
						} catch (Exception e) {
							e.printStackTrace();
						}
						return new ModelAndView("summary", model);
					}
				}
	    	}
    	}
	    	
    	if (!passHsrmComplete){ 		
    		return summaryModelAndView;
    	}
    	
    	/************************** End HSRM ***************************/
    	
   		
    
    	ModelAndView modelAndView =  new ModelAndView(new RedirectView(nextView));
    	modelAndView.addObject("sbuid", attrUid);
    	return modelAndView;
  
 	
		

    }
    
    private boolean isEmpty(List<?> list) {
    	return list == null || list.isEmpty();
    }
    
    private boolean isConcierge(HttpSession session) {
		return "6".equals(session.getAttribute("baskettypeSession"));
	}
    
    private String getIGuardSerialNoFunction(String selectedBasketId,
			String[] selectedItemList, OrderDTO originalOrderDto) {
		String iGuardSerialNo = "";

		List<String> iGuardType = iGuardService.isIGuardOrder(selectedBasketId,
				selectedItemList, SystemTime.asDate());
		for(int i=0;i<iGuardType.size();i++){
			
			if (originalOrderDto != null) {// recall order, reuse SN
				iGuardSerialNo = originalOrderDto.getiGuardSerialNo();// reuse
																		// iGuard SN
				if (StringUtils.isEmpty(iGuardSerialNo)) {// b4 no iGuard, change
															// with have iGuard
					if (!StringUtils.isEmpty(iGuardType.get(i))) {
						iGuardSerialNo = iGuardService.getIGuardSN();
					}
				}
			} else {
				// new order
				if (!StringUtils.isEmpty(iGuardType.get(i))) {
					iGuardSerialNo = iGuardService.getIGuardSN();
				}
	
			}
		}
		return iGuardSerialNo;
	}
    
    private boolean checkQuota(QuotaConsumeRequest qcr, Map<String, Object> referenceData) {
		MobQuotaUsageDTO[] quotaUsages = null;
		boolean validQuota = true;
		try {
			quotaUsages = mobQuotaService.mockConsumeQuota(qcr);
		} catch (AppServiceException e) {
			logger.error("Error while checking quota", e);
			referenceData.put("pageMessage", "Quota Check is not available. Please note Quota Check is ignored.");
			return false;
		}
		
		logger.info(Utility.toPrettyJson(quotaUsages));
		if (quotaUsages != null) {
			if (StringUtils.isEmpty(qcr.getAuthBy())) {
				for (MobQuotaUsageDTO usage : quotaUsages) {
					// check also the returned orderid , over quota error if the quota id is a newly added one ...
					if (usage.isOverQuota() && StringUtils.isEmpty(usage.getOrderId())) {
						validQuota = false;
					}
				}
			}
		}
		
		if (!validQuota) {
			referenceData.put("quotaExceed", "Exceeded Quota, but missing Auth info. Order is not allowed to signoff. Please go back and modify.");
		}
		
		return validQuota;
	}
    
}