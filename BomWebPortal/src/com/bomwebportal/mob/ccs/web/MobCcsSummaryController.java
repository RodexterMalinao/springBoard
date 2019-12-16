package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.AllOrdDocDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.HsrmDTO;
import com.bomwebportal.dto.IGuardDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobBuoQuotaDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderHsrmLogDTO;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.QuotaPlanInfoUI;
import com.bomwebportal.dto.ResultDTO;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.dto.ui.DepositUI;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DuplicateOrderException;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtChinaDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtMnpDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocUI;
import com.bomwebportal.mob.ccs.dto.MobSponsorshipDTO;
import com.bomwebportal.mob.ccs.dto.SbOrderAmendDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.CancellationUI;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentMonthyUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;
import com.bomwebportal.mob.ccs.dto.ui.SummaryUI;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.service.GoldenMrtAdminService;
import com.bomwebportal.mob.ccs.service.MobCcsApprovalLogService;
import com.bomwebportal.mob.ccs.service.MobCcsCancelService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.mob.ccs.service.MobCcsPaymentUpfrontService;
import com.bomwebportal.mob.ccs.service.MobCcsReportService;
import com.bomwebportal.mob.ccs.service.MobCcsSbOrderAmendService;
import com.bomwebportal.mob.ccs.service.MobCcsSupportDocService;
import com.bomwebportal.mob.ccs.service.MobCcsUrgentOrderAlertService;
import com.bomwebportal.mob.ccs.service.StaffInfoService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mobquota.dto.MobQuotaUsageDTO;
import com.bomwebportal.mobquota.dto.QuotaConsumeRequest;
import com.bomwebportal.mobquota.service.MobQuotaService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.DepositService;
import com.bomwebportal.service.IGuardService;
import com.bomwebportal.service.MobPreActReqService;
import com.bomwebportal.service.MobileDetailService;
import com.bomwebportal.service.MobileSimInfoService;
import com.bomwebportal.service.OrdDocService;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.service.OrderHsrmService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.ReleaseLockService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.ConfigProperties;
import com.bomwebportal.util.SystemTime;
import com.bomwebportal.util.Utility;

public class MobCcsSummaryController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	private MobCcsMrtService mobCcsMrtService;
	private GoldenMrtAdminService goldenMrtAdminService;
	private DeliveryService deliveryService;
	private MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService;
	private StaffInfoService staffInfoService;
	private ReleaseLockService releaseLockService;
	private VasDetailService vasDetailService;
	private MobileDetailService mobileDetailService;
	private CustomerProfileService customerProfileService;
	private OrderService orderService;
	private MobCcsSupportDocService mobCcsSupportDocService;
	private MobCcsApprovalLogService mobCcsApprovalLogService;
	private MobCcsOrderRemarkService mobCcsOrderRemarkService;
	private MobCcsCancelService mobCcsCancelService;
	private MobCcsSbOrderAmendService mobCcsSbOrderAmendService;
	private MobCcsUrgentOrderAlertService mobCcsUrgentOrderAlertService;
	private IGuardService iGuardService; // add by wilson 20121018
	private DepositService depositService;
	private MobQuotaService mobQuotaService;
	private MobPreActReqService mobPreActReqService;
	private OrderHsrmService orderHsrmService;
	private MobCcsReportService mobCcsReportService;
	private OrdDocService ordDocService;
	private OrderEsignatureService  orderEsignatureService;
	private OrdEmailReqService ordEmailReqService;

	public IGuardService getiGuardService() {
		return iGuardService;
	}

	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}

	/**
	 * @return the mobCcsSbOrderAmendService
	 */
	public MobCcsSbOrderAmendService getMobCcsSbOrderAmendService() {
		return mobCcsSbOrderAmendService;
	}

	/**
	 * @param mobCcsSbOrderAmendService the mobCcsSbOrderAmendService to set
	 */
	public void setMobCcsSbOrderAmendService(
			MobCcsSbOrderAmendService mobCcsSbOrderAmendService) {
		this.mobCcsSbOrderAmendService = mobCcsSbOrderAmendService;
	}
	
	public MobCcsUrgentOrderAlertService getMobCcsUrgentOrderAlertService() {
		return mobCcsUrgentOrderAlertService;
	}

	public void setMobCcsUrgentOrderAlertService(
			MobCcsUrgentOrderAlertService mobCcsUrgentOrderAlertService) {
		this.mobCcsUrgentOrderAlertService = mobCcsUrgentOrderAlertService;
	}

	public MobCcsApprovalLogService getMobCcsApprovalLogService() {
		return mobCcsApprovalLogService;
	}

	public void setMobCcsApprovalLogService(
			MobCcsApprovalLogService mobCcsApprovalLogService) {
		this.mobCcsApprovalLogService = mobCcsApprovalLogService;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public ReleaseLockService getReleaseLockService() {
		return releaseLockService;
	}

	public void setReleaseLockService(ReleaseLockService releaseLockService) {
		this.releaseLockService = releaseLockService;
	}

	public MobileDetailService getMobileDetailService() {
		return mobileDetailService;
	}

	public void setMobileDetailService(MobileDetailService mobileDetailService) {
		this.mobileDetailService = mobileDetailService;
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

	public GoldenMrtAdminService getGoldenMrtAdminService() {
		return goldenMrtAdminService;
	}

	public void setGoldenMrtAdminService(
			GoldenMrtAdminService goldenMrtAdminService) {
		this.goldenMrtAdminService = goldenMrtAdminService;
	}

	public MobCcsSupportDocService getMobCcsSupportDocService() {
		return mobCcsSupportDocService;
	}

	public void setMobCcsSupportDocService(
			MobCcsSupportDocService mobCcsSupportDocService) {
		this.mobCcsSupportDocService = mobCcsSupportDocService;
	}

	public MobCcsOrderRemarkService getMobCcsOrderRemarkService() {
		return mobCcsOrderRemarkService;
	}

	public void setMobCcsOrderRemarkService(
			MobCcsOrderRemarkService mobCcsOrderRemarkService) {
		this.mobCcsOrderRemarkService = mobCcsOrderRemarkService;
	}

	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	public void setMobCcsPaymentUpfrontService(
			MobCcsPaymentUpfrontService mobCcsPaymentUpfrontService) {
		this.mobCcsPaymentUpfrontService = mobCcsPaymentUpfrontService;
	}

	public MobCcsPaymentUpfrontService getMobCcsPaymentUpfrontService() {
		return mobCcsPaymentUpfrontService;
	}

	public void setStaffInfoService(StaffInfoService staffInfoService) {
		this.staffInfoService = staffInfoService;
	}

	public StaffInfoService getStaffInfoService() {
		return staffInfoService;
	}

	private MobileSimInfoService mobileSimInfoService;

	public MobileSimInfoService getMobileSimInfoService() {
		return mobileSimInfoService;
	}

	public void setMobileSimInfoService(
			MobileSimInfoService mobileSimInfoService) {
		this.mobileSimInfoService = mobileSimInfoService;
	}

	public DepositService getDepositService() {
		return depositService;
	}

	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}

	public MobQuotaService getMobQuotaService() {
		return mobQuotaService;
	}

	public void setMobQuotaService(MobQuotaService mobQuotaService) {
		this.mobQuotaService = mobQuotaService;
	}

	public MobPreActReqService getMobPreActReqService() {
		return mobPreActReqService;
	}

	public void setMobPreActReqService(MobPreActReqService mobPreActReqService) {
		this.mobPreActReqService = mobPreActReqService;
	}

	public OrderHsrmService getOrderHsrmService() {
		return orderHsrmService;
	}

	public void setOrderHsrmService(OrderHsrmService orderHsrmService) {
		this.orderHsrmService = orderHsrmService;
	}

	public MobCcsReportService getMobCcsReportService() {
		return mobCcsReportService;
	}

	public void setMobCcsReportService(MobCcsReportService mobCcsReportService) {
		this.mobCcsReportService = mobCcsReportService;
	}
	public OrdDocService getOrdDocService() {
		return ordDocService;
	}

	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}

	public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}

	public void setOrderEsignatureService(OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
	}
	
	public MobCcsSummaryController() {
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		return new SummaryUI();
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		String nextView = (String) request.getAttribute("nextView");
		logger.info("Next View: " + nextView);

		return new ModelAndView(new RedirectView(nextView));

	}

	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map referenceData = new HashMap<String, List<String>>();

		logger.info("MobCcsSummaryController ReferenceData is called");
		//String locale = RequestContextUtils.getLocale(request).toString();
		
		// get info from session
		//ModelDTO mobileDetail = new ModelDTO();
		//BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		BomSalesUserDTO loginUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String channelId = String.valueOf(loginUserDto.getChannelId());
		
		MobCcsSupportDocUI supportingDoc = (MobCcsSupportDocUI) MobCcsSessionUtil.getSession(request, "supportingDoc");
		if (supportingDoc == null) {
			supportingDoc = new MobCcsSupportDocUI();
		}
		StaffInfoUI sessionStaffInfo = (StaffInfoUI) MobCcsSessionUtil	.getSession(request, "staffInfo");
		if (sessionStaffInfo == null) {
			sessionStaffInfo = new StaffInfoUI();
		}
		String orderId = (String) MobCcsSessionUtil.getSession(request,	"orderId");
		CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		DeliveryUI deliveryUI = (DeliveryUI) MobCcsSessionUtil.getSession(request, "delivery");
		
		MRTUI mrtUI = (MRTUI) MobCcsSessionUtil.getSession(request, "mrt");
		VasMrtSelectionDTO vasMrtSelectionDTO = (VasMrtSelectionDTO)request.getSession().getAttribute("vasMrtSelectionSession");
		VasMrtSelectionDTO ssMrtSelectionDTO = (VasMrtSelectionDTO)request.getSession().getAttribute("ssMrtSelectionSession");
		
		ArrayList<MobCcsMrtBaseDTO> mrtDtoList = null;
		MnpDTO mnpDto = new MnpDTO();

		if (mrtUI != null) {
			mrtDtoList = mobCcsMrtService.uiDtoChangeToMrtDtoList(mrtUI, vasMrtSelectionDTO, sessionStaffInfo.getSalesId());
			mnpDto = mobCcsMrtService.mrtDtoChangeToMnpDto(mrtDtoList, loginUserDto.getChannelCd());
		}
		PaymentMonthyUI paymentMonthyUI = (PaymentMonthyUI) MobCcsSessionUtil.getSession(request, "paymentMonthy");
		PaymentUpFrontUI paymentUpFrontUI = (PaymentUpFrontUI) MobCcsSessionUtil.getSession(request, "paymentUpFront");
		PaymentDTO paymentDto = new PaymentDTO();
		if (paymentUpFrontUI != null) {
			BeanUtils.copyProperties(paymentDto, paymentMonthyUI);
		}
		BasketDTO basketDto = (BasketDTO) MobCcsSessionUtil.getSession(request,	"basket");
		String appDateStr = (String) request.getSession().getAttribute("appDate");
		Date appDateTime = (Date) MobCcsSessionUtil.getSession(request,"appDateTime"); //new app date with date time
		
		String orderType = (String) request.getSession().getAttribute("orderType");
		//String workStatus = (String) MobCcsSessionUtil.getSession(request,"workStatus");
		MobileSimInfoDTO mobileSimInfo = new MobileSimInfoDTO();
		String selectedBasketId = basketDto.getBasketId();
		String[] selectedItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		List<VasDetailDTO> systemAssignVasDetailList = (List<VasDetailDTO>) MobCcsSessionUtil.getSession(request, "systemVasItemList");
		List<ComponentDTO> componentList = (List<ComponentDTO>) request.getSession().getAttribute("componentList");
		VasDetailDTO vasDetail = (VasDetailDTO) MobCcsSessionUtil.getSession(request, "vasDetail");
		OrderDTO originalOrderDTO = (OrderDTO)MobCcsSessionUtil.getSession(request,"originalOrderDTO");//for iGauard
		Boolean originalPrvicyInd = (Boolean)request.getSession().getAttribute("originalPrvicyInd");//add 20130325
		List<MultiSimInfoDTO> multiSimInfos = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		EmailReqResult 	emailReqResultIGuardToCust=null;
     	EmailReqResult 	emailReqResultIGuardToComp=null;
		// Cancel recreate order logic, add by wilson 20120417
		CancellationUI cancellationUI = (CancellationUI) MobCcsSessionUtil.getSession(request, "cancellation");
		if (cancellationUI != null && "Y".equals(cancellationUI.getOrderRecreateInd())) {
			orderId = "";//if recreate order, need to set orderId="", to get new seq
		}
		//get order id
		if (("".equals(orderId) || orderId == null)) {
			
			orderId = orderService.getShopSeqStoredProcedure(loginUserDto.getChannelCd(),	channelId);//modify function from getShopSeq ==>getShopSeqStoredProcedure
			if (orderId ==null || "".equals(orderId)){
				throw new DuplicateOrderException(
						SystemTime.asDate()	+ " Failed to get orderID due to system error, Please screen dump this page for futher investigate!!! <br>");
			}
			
			request.getSession().setAttribute("orderIdSession", orderId);
			MobCcsSessionUtil.setSession(request, "orderId", orderId);

			if (appDateTime==null){
				appDateTime= SystemTime.asDate();
				logger.info("newOrderId/appDateTime :"+orderId+"/"+appDateTime);
			}
			if (cancellationUI != null	&& "Y".equals(cancellationUI.getOrderRecreateInd())) {
				// copy order remarks
				this.mobCcsOrderRemarkService.insertOrderRemark(loginUserDto.getUsername(), orderId, "COPY ORDER FORM orderId:"+ cancellationUI.getOrderId());
			}
			this.mobCcsOrderRemarkService.insertOrderRemark(loginUserDto.getUsername(),	orderId, "CREATE ORDER");

		} else {
			//--TO prevent missing appDateTime data 20130625---------------------------
			logger.info("[SB_CHECK]orderId:"+orderId+",appDate:"+appDateStr);
			logger.info("[SB_CHECK]orderId:"+orderId+",appDateTime:"+appDateTime);
			if (appDateTime==null){
				logger.info("[SB_CHECK]" +SystemTime.asDate()
				+ "<br> Order ID : "
				+ orderId
				+ " can not be saved due to system error, Please recall and modify the order again!!! [APP_DATE]<br>"
				);
				throw new DuplicateOrderException(
						SystemTime.asDate()
								+ "<br> Order ID : "
								+ orderId
								+ " can not be saved due to system error, Please recall and modify the order again!!! [APP_DATE]<br>"
								);
			}
			//--END TO prevent missing appDateTime data 20130625---------------------------
			
			String orderStatus = orderService.getOrderStatus(orderId);
			if ((orderStatus != null)
					&& (!"01".equals(orderStatus.trim()) && !"99"
							.equals(orderStatus.trim()))) {
			}

			orderService.backupDeleteBomWebAll(orderId);
			this.mobCcsOrderRemarkService.insertOrderRemark(loginUserDto.getUsername(),	orderId, "MODIFLY ORDER");

		}

		/************************ Update OrderID for MnpDTO ***********************/
		mnpDto.setOrderId(orderId);

		/************************ Update OrderID for MnpDTO (End) *******************/

		/************************ Update OrderID for MultiSimInfoDTO ***********************/
		if(multiSimInfos != null) {
			List<MultiSimInfoDTO> selectedMultiSimInfo = new ArrayList<MultiSimInfoDTO> ();
			int memNum = 1;
			for(MultiSimInfoDTO msi : multiSimInfos){
				for(String selectedItem : selectedItemList){
					if(selectedItem.equals(msi.getItemId())){
						selectedMultiSimInfo.add(msi);
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
		}
		/************************ Update OrderID for MultiSimInfoDTO (End) *******************/
		
		// /create orderDTO
		/************************ Create Order Object *****************************/
		OrderDTO orderDto = (OrderDTO) MobCcsSessionUtil.getSession(request,
				"orderDTO");
		if (orderDto == null
				|| (cancellationUI != null && "Y".equals(cancellationUI
						.getOrderRecreateInd()))) {
			orderDto = new OrderDTO();
			orderDto.setOrderId(orderId);
			orderDto.setOrderSumLob("MOB");// add 20111026
			orderDto.setSource("WP");
			orderDto.setBusTxnType(orderType);// add by wilson 20120305 //"ACT"
			orderDto.setShopCode(loginUserDto.getChannelCd());// salesUserDto.getShopCd()
			orderDto.setOrderStatus(BomWebPortalConstant.BWP_MOBCCS_ORDER_PENDING);
			orderDto.setCheckPoint(BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_INITIAL);// add
																						// by
																						// wilson
																						// 20110208
			orderDto.setBasketId(selectedBasketId);// add 20110815

			logger.info("Summart Controller MNP setting");
			orderDto.setAgreementNum(orderId);
			//orderDto.setAppInDate(Utility.string2Date(appDate)); remove by wilson 20120524
			if (orderDto.getCreateDate() == null) {
				orderDto.setCreateDate(SystemTime.asDate());
			}
			orderDto.setSalesType("S");
			orderDto.setSalesCd(sessionStaffInfo.getSalesId());// mobileSimInfo.getSalesCd()
			orderDto.setSalesName(sessionStaffInfo.getSalesName());// mobileSimInfo.getSalesName()

	    	/*
	    	if("0".equals(customerInfoDto.getAddrProofInd())){
	    		orderDto.setOnHoldInd("Y");
	    		orderDto.setOnHoldReaCd("10");
	    	}else{
	    		orderDto.setOnHoldInd("N");
	    	}
	    	*/
	    	orderDto.setOnHoldInd("N");


			orderDto.setImei("");// auto assign

			if (mobileSimInfo.getAoInd() != null) {
				orderDto.setAoInd("Y");
			} else {
				orderDto.setAoInd("N");
			}
		}
		orderDto.setOrderStatus(BomWebPortalConstant.BWP_MOBCCS_ORDER_PENDING);
		orderDto.setCheckPoint(BomWebPortalConstant.BWP_MOBCCS_CHECK_POINT_INITIAL);
		orderDto.setBusTxnType(orderType);
		orderDto.setAppInDate(appDateTime);//edit by wilson 20120524, remove Utility.string2Date(appDate)
		orderDto.setLastUpdateBy(loginUserDto.getUsername());
		if (multiSimInfos != null && multiSimInfos.size() > 0) {
			orderDto.setMultiSim(true);
		} else {
			orderDto.setMultiSim(false);
		}
			
		
		//recreate order
		if (cancellationUI != null && "Y".equals(cancellationUI.getOrderRecreateInd())) {
			orderDto.setCloneOrderId(cancellationUI.getOrderId());// copy cancellation(old) order_id to colne_order_id
		}
		orderDto.setAcctNum(customerInfoDto.getAcctNum());//OLIVER MIP4
		
		orderDto.setBomCustNum(customerInfoDto.getBomCustNum());
		
		orderDto.setMobCustNum(customerInfoDto.getMobCustNum());
		
		
		
		/************************ Create Order Object (End) ************************/

		/************************ Update OrderID for CustomerProfile ****************/
		customerInfoDto.setOrderId(orderId); // set order id
		if (StringUtils.isNotEmpty(customerInfoDto.getSameAsCustInd()) && !customerInfoDto.isSameAsCust()){
			customerInfoDto.getActualUserDTO().setOrderId(orderId);
		}
		
		/************************ Update OrderID for CustomerProfile (End) **********/

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
			
			subscriberDto.setTelemkSuppressValue("N");
			subscriberDto.setEmailSuppressValue("N");
			subscriberDto.setSmsSuppressValue("N");
			subscriberDto.setDmSuppressValue("N");
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
		if (customerInfoDto.isMob0060OptOutInd()){
			subscriberDto.setMob0060OptOutInd("Y");
		}else{
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
		if ("2".equals(customerInfoDto.getAddrProofInd())) {
			subscriberDto.setAddrProofReferrer(customerInfoDto.getServiceNum());
		} else if ("5".equals(customerInfoDto.getAddrProofInd())) {
			if (StringUtils.isBlank(customerInfoDto.getActivationCd())) {
				subscriberDto.setActivationCd(mobPreActReqService.generatePreActivationCode());
			} else {
				subscriberDto.setActivationCd(customerInfoDto.getActivationCd());
			}
        }
		
		subscriberDto.setPwd(Utility.getDefaultPassword(
				customerInfoDto.getIdDocType(), customerInfoDto.getIdDocNum()));
		subscriberDto.setSmsLang(customerInfoDto.getSmsLang());
		if (mrtUI != null){
			if(StringUtils.isNotEmpty(mrtUI.getOrigActDateStr())){
				subscriberDto.setOrigActDate(Utility.string2Date(mrtUI.getOrigActDateStr()));
			}
		}
		
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
		/************************ Create SubscriberDTO (End) ************************/

		/************************ Create AccountDTO *****************************/

		AccountDTO accountDto = new AccountDTO();
		accountDto.setOrderId(orderId);
		if ("BS".equals(customerInfoDto.getIdDocType())) {
			accountDto.setAcctName(customerInfoDto.getCompanyName()
					.toUpperCase());
		} else {
			accountDto.setAcctName(customerInfoDto.getTitle().toUpperCase()
					+ " " + customerInfoDto.getContactName());
		}
		accountDto.setBillFreq("MTHLY");
		accountDto.setBillLang(customerInfoDto.getBillLang());
		Calendar appDate = Calendar.getInstance();
		appDate.setTime(appDateTime);
    	if ("0".equalsIgnoreCase(customerInfoDto.getBrand())){
    		accountDto.setBillPeriod(String.valueOf(Utility.get1010BillPeriod(appDate.get(Calendar.DATE))));
    	} else if ("1".equalsIgnoreCase(customerInfoDto.getBrand())) {
    		accountDto.setBillPeriod(String.valueOf(Utility.getCslBillPeriod(appDate.get(Calendar.DATE))));
    	}

		accountDto.setSmsNum(customerInfoDto.getContactPhone());
		accountDto.setEmailAddr(customerInfoDto.getEmailAddr());
		accountDto.setBillLang(customerInfoDto.getBillLang());
		accountDto.setBrand(customerInfoDto.getBrand());
		accountDto.setSameAsCustInd(customerInfoDto.getSameAsCustInd());
		if("current".equalsIgnoreCase(customerInfoDto.getAcctType()))//OLIVER MIP4
    		accountDto.setIsNew("N");
    	else 
    		accountDto.setIsNew("Y");
	
		
    	accountDto.setAcctNum(customerInfoDto.getAcctNum());   	
    	accountDto.setMobCustNum(customerInfoDto.getMobCustNum());    	
    	accountDto.setAcctType(customerInfoDto.getAcctType());
    	accountDto.setSrvNum(customerInfoDto.getSrvNum());
    	
    
		/************************ Create AccountDTO (End) ************************/


		/************************ Update OrderID for SimDTO ************************/
		mobileSimInfo.setOrderId(orderId);// set order id
		mobileSimInfo.setSimBrandType(customerInfoDto.getSimType());
		/************************ Update OrderID for SimDTO (End) *******************/

		/************************ Update OrderID for PaymentDTO ********************/
		if ("C".equals(paymentDto.getPayMethodType())) {// add 20110613
			paymentDto.setCreditExpiryDate(paymentDto.getCreditExpiryMonth()
					+ "/" + paymentDto.getCreditExpiryYear());// edit
		}

		paymentDto.setOrderId(orderId);// set order id
		
		//add by herbert 20120425
		if ("N".equalsIgnoreCase(paymentDto.getCreditCardVerifiedInd())){
			paymentDto.setCreditCardVerifiedInd("");
		}
		
		/************************ Update OrderID for PaymentDTO (End) ***************/
		
		/************************ Set value from MrtDto to OrderDTO *******************/
		// add by eliot 20120103
		if (mrtDtoList != null) {
			//To get the first row of data and therefore only loop once
			for (MobCcsMrtBaseDTO dto : mrtDtoList) {
				orderDto.setMnpInd(dto.getMnpInd());
				orderDto.setMsisdn(dto.getMsisdn());
				orderDto.setMsisdnLvl(dto.getMsisdnLvl());
				
				if (dto instanceof MobCcsMrtMnpDTO) {
					logger.info("CutoverDate():" + ((MobCcsMrtMnpDTO) dto).getCutOverDate());
					orderDto.setSrvReqDate(((MobCcsMrtMnpDTO) dto).getCutOverDate());
				} else if (dto instanceof MobCcsMrtChinaDTO) {
					logger.info("ServiceReqDate():"	+ ((MobCcsMrtChinaDTO) dto).getServiceReqDate());
					orderDto.setSrvReqDate(((MobCcsMrtChinaDTO) dto).getServiceReqDate());
				} else if (dto instanceof MobCcsMrtDTO) {
					logger.info("ServiceReqDate():"	+ ((MobCcsMrtDTO) dto).getServiceReqDate());
					orderDto.setSrvReqDate(((MobCcsMrtDTO) dto).getServiceReqDate());
				}
				break;
			}
		}
		/************************ Update OrderID for MrtDTO (End) *******************/

		/************************ Staff Sponsorship *************************/ 
		
		MobSponsorshipDTO mobSponsorshipDTO = null;
		mobSponsorshipDTO = (MobSponsorshipDTO)MobCcsSessionUtil.getSession(request, "mobSponsorshipDTO");
		if (mobSponsorshipDTO != null && StringUtils.isNotEmpty(mobSponsorshipDTO.getStaffId())) {
			orderDto.setMobSponsorshipDTO(mobSponsorshipDTO);
		}
		/************************ Staff Sponsorship *************************/
		
		/************************ NFC Octopus AIO ***************************/
		// check for checked box in VasDetail page
		if (vasDetail != null) {
			orderDto.setNfcInd(vasDetail.getSimExtraFunction());
		}
		/************************ NFC Octopus AIO ***************************/
		
		/************************ Deposit Handling **************************/
		DepositDTO depositDTOs[] = null;
		DepositUI depositUI = (DepositUI)MobCcsSessionUtil.getSession(request,"depositInfo");
		if (depositUI != null && CollectionUtils.isNotEmpty(depositUI.getDepositDTOList())) {
			depositDTOs = depositUI.getDepositDTOList().toArray(new DepositDTO[]{});
		}
		orderDto.setDepositDTOs(depositDTOs);
		/************************ Deposit Handling **************************/
		
			
		String messageMrtPkg = "";
		String messageOrderPkg = "";
		String messageAll = "";
		List<StockDTO> stockList = null;
		BasketDTO basketDTO = null;
		
		

		basketDTO = vasDetailService.getBasketAttribute(selectedBasketId,	appDateStr);
		try {
			
			IGuardDTO iGuardDto = new IGuardDTO();
			List<String> srvPlanList = iGuardService.isIGuardOrder(
					basketDTO.getBasketId(), selectedItemList, appDateTime);
			for(int i=0;i<srvPlanList.size();i++){
				if (!StringUtils.isEmpty(srvPlanList.get(i))) {
	
					String iGuardSn = "";
					if (originalOrderDTO != null
							&& StringUtils.isNotBlank(originalOrderDTO
									.getiGuardSerialNo())) {
						iGuardSn = originalOrderDTO.getiGuardSerialNo();
	
					}
					if (StringUtils.isBlank(iGuardSn)) {
	
						iGuardSn = getIGuardSerialNoFunction(selectedBasketId,
								selectedItemList, originalOrderDTO, appDateTime);
					}
					orderDto.setiGuardSerialNo(iGuardSn);
					String srvPlanType = srvPlanList.get(i);
					iGuardDto = iGuardService.getCcsIGuardDTO(customerInfoDto,
							orderDto, mrtUI, basketDTO, stockList, componentList,srvPlanType);
	
					// iGuardService.getCcsPreviewIGuardDTO(orderId, appDate,
					// iGuardSerialNo, customerInfo, mrtUI, basketDto, staffInfoUI,
					// deliveryUI, salesUserDto, stockList)
				} else {
					if (!StringUtils.isEmpty(orderDto.getiGuardSerialNo())) {
						logger.info("delete SerialNo:"	+ iGuardDto.getSerialNo());
						orderDto.setiGuardSerialNo("");
					}
	
				}
			}
			
			/***************************QuotaPlan**************************************/
			
			List<MobBuoQuotaDTO> mobBuoQuotaDtoList = new ArrayList<MobBuoQuotaDTO>();
			Map<String,QuotaPlanInfoUI> quotaPlanInfoMap = (Map<String,QuotaPlanInfoUI>)request.getSession().getAttribute("quotaPlanInfoMapSession");
			if (quotaPlanInfoMap!=null){
			
				for (Map.Entry<String, QuotaPlanInfoUI> entry : quotaPlanInfoMap.entrySet()) {
					MobBuoQuotaDTO mobBuoQuotaDto = new MobBuoQuotaDTO();
					if ("Y".equalsIgnoreCase(entry.getValue().getIotPlan())) {
						if ("Y".equalsIgnoreCase(entry.getValue().getSuppressLocal())) {
							customerInfoDto.setSuppressLocalTopUpInd(true);
							subscriberDto.setSuppressLocalTopUpInd("Y");
						}
						if ("Y".equalsIgnoreCase(entry.getValue().getSuppressRoam())) {
							customerInfoDto.setSuppressRoamTopUpInd(true);
							subscriberDto.setSuppressRoamTopUpInd("Y");
						}
						mobBuoQuotaDto.setAutoTopUpInd("P");
					} else {
						mobBuoQuotaDto.setAutoTopUpInd(entry.getValue().getAutoTopUpInd());
					}
					mobBuoQuotaDto.setBuoId(entry.getValue().getSelectedQuotaPlanOption());
					mobBuoQuotaDto.setMaxCntAutoTopUp(entry.getValue().getMaxTopUpTimes());
					mobBuoQuotaDto.setItemId(entry.getValue().getItemId());
					mobBuoQuotaDto.setCreateBy(loginUserDto.getUsername());
					mobBuoQuotaDto.setLastUpdBy(loginUserDto.getUsername());
					mobBuoQuotaDtoList.add(mobBuoQuotaDto);
				}
			}
			
			/***************************QuotaPlan End *********************************/
			
			List<String> authorizeLog = new ArrayList<String>();
			if (StringUtils.isNotEmpty((String)MobCcsSessionUtil.getSession(request, "AU18"))) {
				authorizeLog.add((String)MobCcsSessionUtil.getSession(request, "AU18"));
			}
			
			if (StringUtils.isNotEmpty((String)MobCcsSessionUtil.getSession(request, "AU19"))) {
				authorizeLog.add((String)MobCcsSessionUtil.getSession(request, "AU19"));
			}
			//authorizeLog.add((String)MobCcsSessionUtil.getSession(request, "AU18"));
			
			orderService.insertBomWebAll(orderId, selectedBasketId, orderDto,
					subscriberDto, mnpDto, mobileSimInfo, paymentDto,
					accountDto, customerInfoDto, selectedItemList,
					componentList, channelId, iGuardDto, vasDetail, basketDto, multiSimInfos,
					systemAssignVasDetailList,mrtDtoList,mobBuoQuotaDtoList, authorizeLog);
			// add mrt add database method
			customerProfileService.insertBomCustomerProfile(customerInfoDto);
			
			if (deliveryUI == null) {
				deliveryUI = new DeliveryUI();
			}
			deliveryUI.setOrderId(orderId);
			deliveryService.insertBomwebDelivery(deliveryUI);
								
			if (paymentUpFrontUI == null) {
				paymentUpFrontUI = new PaymentUpFrontUI();
			}
			
			/********** Update upfrontPayment DTO *******************/
			paymentUpFrontUI.setOrderId(orderId);
			paymentUpFrontUI.setUsername(sessionStaffInfo.getSalesId());
			
			//add by herbert 20120425, for re-check credit card 
			if ("N".equalsIgnoreCase(paymentUpFrontUI.getCreditCardVerifiedInd())){
				paymentUpFrontUI.setCreditCardVerifiedInd("");
			}
			/********** Update upfrontPayment DTO (End) *******************/

			supportingDoc.setOrderId(orderId);
			mobCcsSupportDocService.insertMobCcsSupportDocUI(supportingDoc);

			mobCcsPaymentUpfrontService.insertBomPaymentUpfront(paymentUpFrontUI);

			sessionStaffInfo.setOrderId(orderId);
			staffInfoService.insertBomwebStaffInfo(sessionStaffInfo);

			// edit by wilson 20120423
			if (vasDetail != null && vasDetail.getSelectedSimItemId() != null) {
				if (basketDto != null && basketDto.getBasketTypeId() != null &&("2".equals(basketDto.getBasketTypeId()) ||"3".equals(basketDto.getBasketTypeId()))) {
					orderService.deleteBomWebNoneSuitableSim(orderId,	vasDetail.getSelectedSimItemId());
				}
			}
			// add by wilson 20120430, for order recreate, copy payment trx and stock
			if (cancellationUI != null
					&& "Y".equals(cancellationUI.getOrderRecreateInd())) {
				// if recreate order, copy cancellation(old) order_id to colne_order_id
				orderDto.setCloneOrderId(cancellationUI.getOrderId());
				
				// orderService.copyBomwebStockAssign(cancellationUI.getOrderId(), orderId, loginUserDto.getUsername()) ;
				orderService.cancelRecreateOrderStockInsStockAssgnByCloneOrder(cancellationUI.getOrderId(), orderId, loginUserDto.getUsername());
				
				mobCcsCancelService.cancelWithRecreateMobCcsOrder(cancellationUI.getOrderId(), cancellationUI.getCodeId(), loginUserDto.getUsername() + " CANCEL RECREATE " + cancellationUI.getRemark(), loginUserDto.getUsername(), orderId);
				if("Y".equals(cancellationUI.getPaymentTransferInd())){
					orderService.paymentTransProcess(cancellationUI.getOrderId(), orderId, loginUserDto.getUsername()) ;
					this.mobCcsOrderRemarkService.insertOrderRemark(loginUserDto.getUsername(), orderId, "Payment TRANSFERRED FORM orderId:"+ cancellationUI.getOrderId());
				}
				
				
				//Project Seven
				if (orderHsrmService.isPrj7AttbExists(componentList)){
					orderService.copyBomwebMobOrderHsrmLog(cancellationUI.getOrderId(), orderId, loginUserDto.getUsername()) ;
				}
			}
			
			/************************ Consume Quota ************************/
			// Update Quota Usage ... if any ...
			SuperOrderDTO superOrderDto = (SuperOrderDTO) request.getSession().getAttribute("superOrderDto");
			superOrderDto.setOrderId(orderId);
			if ("HKID".equalsIgnoreCase(customerInfoDto.getIdDocType()) || "PASS".equalsIgnoreCase(customerInfoDto.getIdDocType())) {
				QuotaConsumeRequest qcr = superOrderDto.getQuotaConsumeRequest();
				if (qcr != null) {
					try {
						qcr.setOrderId(superOrderDto.getOrderId());
						MobQuotaUsageDTO[] quotaUsage = mobQuotaService.consumeQuota(qcr);
					} catch (Exception e) {
						logger.error("Error when consuming quota in ConfirmationController", e);
						mobCcsOrderRemarkService.insertOrderRemark(loginUserDto.getUsername(), superOrderDto.getOrderId(), "Error when consuming quota");
					}
				}
			}
			
			/***************************************************************/
			request.getSession().setAttribute("superOrderDto", superOrderDto);
			
			// call pl sql pkg update mrt
			try {
				messageMrtPkg = orderService.orderMrtUpdate(orderId);
				// call pl/sql to update order status
				try {
					messageOrderPkg = orderService.orderStatusProcess(orderId);
				} catch (Exception e) {
					messageOrderPkg="orderService.orderStatusProcess Exception:"+e.toString();
					logger.error("orderStatusProcess PKG Error:" + e.toString());
				}
			} catch (Exception e) {
				messageMrtPkg="orderService.orderMrtUpdate Exception:"+e.toString();
				logger.error("orderMrtUpdate PKG Error:" + e.toString());
			}

			// check if urgent order, send alert email
			OrderDTO latestOrderDto = this.orderService.getOrder(orderId);
			// order_status = 99, reason_cd = A003 / M001
			if (BomWebPortalConstant.BWP_MOBCCS_ORDER_FALLOUT.equals(latestOrderDto.getOrderStatus())) {
				String[] falloutCodes = { "A003", "M001" };
				for (String falloutCode : falloutCodes) {
					if (falloutCode.equals(latestOrderDto.getReasonCd())) {
						logger.info("Send urgent order alert email - orderId: " + orderId +
										", orderStatus: " + latestOrderDto.getOrderStatus() +
										", reasonCd: " + latestOrderDto.getReasonCd());
						try {
							this.mobCcsUrgentOrderAlertService.sendAlert(latestOrderDto);
						} catch (Exception e) {
							logger.warn("Exception during sending urgent alert email", e);
						}
						break;
					}
				}
			}
			

			orderService.insertBomwebCustQuotaInUse(
					customerInfoDto.getIdDocType(),
					customerInfoDto.getIdDocNum(), orderId,
					sessionStaffInfo.getSalesId(), selectedBasketId);

			messageAll = orderService.getMobCcsOrderStatusDesc(orderId);

		
			stockList = orderService.getParentOrderStockAssignment(orderId);
			/*
			 * 
			 * Add by herbert 20120220 Get SIM information from stockList
			 */

			for (StockDTO stock : stockList) {
				if (stock.getType().equalsIgnoreCase("SIM")) {
					MobileSimInfoDTO inMobileSimInfoDto = new MobileSimInfoDTO();
					inMobileSimInfoDto.setIccid(stock.getItemSerial());
					logger.info("validateSim before, iccid:"
							+ stock.getItemSerial() + ", model:"
							+ stock.getItemDesc());
					MobileSimInfoDTO resultMobileSimInfoDto = mobileSimInfoService
							.validateSim(inMobileSimInfoDto);
					logger.info("validateSim after, iccid:"
							+ resultMobileSimInfoDto.getIccid() + " , model:"
							+ resultMobileSimInfoDto.getItemCd());
					
					String simBrandType = mobileSimInfo.getSimBrandType();
					mobileSimInfo = resultMobileSimInfoDto;
					mobileSimInfo.setItemCd(stock.getItemCode());
					mobileSimInfo.setOrderId(orderId);
					mobileSimInfo.setSimBrandType(simBrandType);
				}
			}
			orderService.updateBomWebSim(mobileSimInfo);

			/************************ Start HSRM ***************************/
			//Project Seven: submit key infromaton for pre-registration marking off process
			String frontEndDisplayWarningMessage="";
			OrderHsrmLogDTO existOrderConfirmedLogForOrder = orderHsrmService.getOrderConfirmedHsrmLog(orderId);
			
			if (existOrderConfirmedLogForOrder==null){
				HsrmDTO validateResult = orderHsrmService.validateHSRM(componentList,orderId,"2", basketDto.getHsPosItemCd(), loginUserDto.getUsername(), customerInfoDto.getIdDocNum(), appDateTime,basketDto.getMipBrand());
				ResultDTO updateResult= orderHsrmService.updateHSRMOrder(componentList, orderId, basketDto.getHsPosItemCd(), loginUserDto.getUsername(), false,validateResult);
				if (!updateResult.getReturnBool()){
					if (StringUtils.isNotBlank(updateResult.getReturnMessage())){
						frontEndDisplayWarningMessage =  "HSRM pre-reg number status \"update\" failed: "+updateResult.getReturnMessage()+" Pre-reg number status has not been updated, please note.";;
					}
				}else{
					if (StringUtils.isNotBlank(updateResult.getReturnMessage())){
						frontEndDisplayWarningMessage = updateResult.getReturnMessage()+" Pre-reg number status has not been updated, please note.";;
					}
				}
				if (StringUtils.isNotBlank(frontEndDisplayWarningMessage)){
					referenceData.put("frontEndDisplayWarningMessage", frontEndDisplayWarningMessage);
				}
			}
			//fail, jsut insert log
			/************************** End HSRM ***************************/
			
			// add by wilson 20120319, update CcsApprovalLog
			String deliveryAuthId = (String) MobCcsSessionUtil.getSession(request, "AU01");
			String quotaAuthId = (String) MobCcsSessionUtil.getSession(request,"AU02");
			String paperBillAuthId = (String) MobCcsSessionUtil.getSession(request, "AU06");//Paper bill Athena 20130925
			String supportIdDocAuthId = (String) MobCcsSessionUtil.getSession(request, "AU03");
			//add by Eliot 20120514
			String specialNumAuthId = (String) MobCcsSessionUtil.getSession(request, "AU05");
			String rpWaiveAuthId = (String) MobCcsSessionUtil.getSession(request, "AU16");
			if (deliveryAuthId != null) {
				mobCcsApprovalLogService.updateApprovalLog(orderId,deliveryAuthId, "SMAU01");
			}
			if (quotaAuthId != null) {
				mobCcsApprovalLogService.updateApprovalLog(orderId,quotaAuthId, "SMAU02");
			}
			if (supportIdDocAuthId != null) {
				mobCcsApprovalLogService.updateApprovalLog(orderId,supportIdDocAuthId, "SMAU03");
			}
			//add by Eliot 20120514
			if (specialNumAuthId != null) {
				mobCcsApprovalLogService.updateApprovalLog(orderId,specialNumAuthId, "SMAU05");
			}
			if (paperBillAuthId != null) {//Paper bill Athena 20130925
				mobCcsApprovalLogService.updateApprovalLog(orderId,paperBillAuthId, "SMAU06");
			}
			if (rpWaiveAuthId != null) {//Paper bill Athena 20130925
				mobCcsApprovalLogService.updateApprovalLog(orderId,rpWaiveAuthId, "SMAU16");
			}
		} catch (Exception e) {

			logger.error("MobCcsSummaryController: insertBomWebAll insertBomCustomerProfile failed"
					+ e.toString());
			throw new DuplicateOrderException(
					SystemTime.asDate()
							+ "<br> Order ID : "
							+ orderId
							+ " can not be saved due to system error, Please screen dump this page for futher investigate!!! <br>"
							+ e.toString());

		}

		/*mobileDetail = mobileDetailService.getMobileDetail(locale,
				selectedBasketId);
		logger.debug("******* BasketId = " + selectedBasketId + " *******");*/

		/*List<VasDetailDTO> vasHSRPList = new ArrayList<VasDetailDTO>();

		if (!"".equals(selectedBasketId) && selectedBasketId != null) {

			vasHSRPList = vasDetailService.getVasDetailtSelectedList(locale,
					orderId);// edit by wilson 20110127 use new sql

		}*/

		/************************ To Release Order *********************/

		OrderDTO lockOrderDTO = releaseLockService.getOrderLockInfo(orderId);
		if (lockOrderDTO != null) {

			if ("Y".equalsIgnoreCase(lockOrderDTO.getLockInd())) {
				if (loginUserDto.getUsername().equalsIgnoreCase(
						lockOrderDTO.getLastUpdateBy())) {
					releaseLockService.updateOrderLockInd(orderId, "",
							loginUserDto.getUsername());
				}
			}
		}

		/***************************************************************/
		
		/********************To insert into amend table*****************/
		
		if (customerInfoDto.isAmend()) {
			SbOrderAmendDTO dto = new SbOrderAmendDTO();
			dto.setOrderId(customerInfoDto.getOrderId());
			dto.setOrderAmendType("OA01");
			dto.setCreateBy(loginUserDto.getUsername());
			dto.setLastUpdBy(loginUserDto.getUsername());
			mobCcsSbOrderAmendService.insertSbOrderAmendDTO(dto);
		}
		
		if (mrtUI != null && mrtUI.isMrtAmend()) {
			SbOrderAmendDTO dto = new SbOrderAmendDTO();
			dto.setOrderId(customerInfoDto.getOrderId());
			dto.setOrderAmendType("OA02");
			dto.setCreateBy(loginUserDto.getUsername());
			dto.setLastUpdBy(loginUserDto.getUsername());
			mobCcsSbOrderAmendService.insertSbOrderAmendDTO(dto);
		}
		
		if (mrtUI != null && mrtUI.isMnpAmend()) {
			SbOrderAmendDTO dto = new SbOrderAmendDTO();
			dto.setOrderId(customerInfoDto.getOrderId());
			dto.setOrderAmendType("OA03");
			dto.setCreateBy(loginUserDto.getUsername());
			dto.setLastUpdBy(loginUserDto.getUsername());
			mobCcsSbOrderAmendService.insertSbOrderAmendDTO(dto);
		}
		
		if (multiSimInfos != null && multiSimInfos.size() > 0) {
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
					SbOrderAmendDTO dto = new SbOrderAmendDTO();
					dto.setOrderId(customerInfoDto.getOrderId());
					dto.setOrderAmendType("OA04");
					dto.setCreateBy(loginUserDto.getUsername());
					dto.setLastUpdBy(loginUserDto.getUsername());
					mobCcsSbOrderAmendService.insertSbOrderAmendDTO(dto);
					
				}
				if (isMSAmendMnp) {
					SbOrderAmendDTO dto = new SbOrderAmendDTO();
					dto.setOrderId(customerInfoDto.getOrderId());
					dto.setOrderAmendType("OA03");
					dto.setCreateBy(loginUserDto.getUsername());
					dto.setLastUpdBy(loginUserDto.getUsername());
					mobCcsSbOrderAmendService.insertSbOrderAmendDTO(dto);
				}
			}
		}
		
		/***************************************************************/

		
		/*****************IGUARD CareCash Email Send ********************/
		String workStatus = (String) MobCcsSessionUtil.getSession(request,"workStatus");
		String careCashEmail = ConfigProperties.getPropertyByEnvironment("careCashIGuardEmailAddr");
     	String[] careCashEmailnames = careCashEmail.split(",");
		if (!"recall".equals(workStatus)){
			if ("Y".equals(customerInfoDto.getCareStatus())&& ("I".equals(customerInfoDto.getCareOptInd()))){
			int noOfEmailSent=ordEmailReqService.isCareCashFormSent(orderId);
				if (noOfEmailSent==0){
					ArrayList<Object> pReportArrList = new ArrayList<Object>();
					String pdfLang ="en";
					pReportArrList.add(orderDto);
					pReportArrList.add(customerInfoDto);
					pReportArrList.add(loginUserDto);
					ArrayList<String>iGuardType = new ArrayList<String>();
					iGuardType.add("CARECASH");
					String fileName = "";
					OrderMobDTO orderMobDto;
					orderMobDto=orderService.getOrderMobDTO(orderId);
					if ("00".equals(customerInfoDto.getSmsLang())){
						pdfLang = "zh";
						fileName = "CHI";
					}else{
						fileName = "EN";
					}
					if ("I".equals(customerInfoDto.getCareOptInd())){
						mobCcsReportService.saveCareCashPdfReports(pReportArrList, pdfLang, orderId, iGuardType, customerInfoDto.getBrand());
						String signedFormsFileName = orderId + "_CareCash_" + fileName + ".pdf";
						AllOrdDocDTO allOrdDocDTO = new AllOrdDocDTO();
						allOrdDocDTO.setOrderId(orderId);
						allOrdDocDTO.setFilePathName(signedFormsFileName);
						allOrdDocDTO.setProcessDate(null);
						allOrdDocDTO.setCreateBy(loginUserDto.getUsername());
						allOrdDocDTO.setLastUpdBy(loginUserDto.getUsername());		
						allOrdDocDTO.setDocTypeMob("M045");
						ordDocService.insertAllOrderDocDTO(allOrdDocDTO);
				     
				     	String email = ConfigProperties.getPropertyByEnvironment("iGuardEmailAddr");
				     	String[] emailnames = email.split(",");
							
						//careCash
						emailReqResultIGuardToCust = orderEsignatureService.createEmailReq(orderId, "RT017", new Date(), signedFormsFileName, null, null, customerInfoDto.getEmailAddr(), loginUserDto.getUsername()); 
						for (String emailname : careCashEmailnames) {
						emailReqResultIGuardToComp =orderEsignatureService.createEmailReq(orderId, "RT018", new Date(), signedFormsFileName, null, null, emailname, loginUserDto.getUsername()); 
						}
					}	
				}
			}
		}
		

		referenceData.put("orderId", orderId);
		referenceData.put("cancellationUI", cancellationUI);
		MobCcsSessionUtil.setSession(request, "cancellation", null);//clear session
		referenceData.put("messageMrtPkg", messageMrtPkg);
		referenceData.put("messageOrderPkg", messageOrderPkg);
		referenceData.put("messageAll", messageAll);

		referenceData.put("emailReqResultIGuardToCust",emailReqResultIGuardToCust);
		referenceData.put("emailReqResultIGuardToComp", emailReqResultIGuardToComp);


		return referenceData;

	}

	public MobCcsCancelService getMobCcsCancelService() {
		return mobCcsCancelService;
	}

	public void setMobCcsCancelService(MobCcsCancelService mobCcsCancelService) {
		this.mobCcsCancelService = mobCcsCancelService;
	}
	
	private String getIGuardSerialNoFunction(String selectedBasketId, String[] selectedItemList, OrderDTO originalOrderDto, Date appDate) {
		String iGuardSerialNo="";
		
		List<String> iGuardType=iGuardService.isIGuardOrder(selectedBasketId, selectedItemList, appDate);
		//System.out.println("iGuardInd:"+iGuardInd);
		for(int i=0;i<iGuardType.size();i++){
	    	if (originalOrderDto != null){//recall order, reuse SN
	    		iGuardSerialNo = originalOrderDto.getiGuardSerialNo();//reuse iGuard SN
	    		if (StringUtils.isEmpty(iGuardSerialNo)){//b4 no iGuard, change with have iGuard
		    		if (!StringUtils.isEmpty(iGuardType.get(i))){
		    			iGuardSerialNo = iGuardService.getIGuardSN();
		    		}
	    		}
	    	}else{
	    		//new order
	    		if (!StringUtils.isEmpty(iGuardType.get(i))){
	    			iGuardSerialNo = iGuardService.getIGuardSN();
	    		}
	    		
	    	}
		}
		return iGuardSerialNo;
	}

	

}
