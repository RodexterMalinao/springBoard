package com.bomwebportal.lts.web.acq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerInformationDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.CsPortalIdRegrArqDTO;
import com.bomwebportal.lts.dto.ItemAttbBaseDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsQuotaDTO;
import com.bomwebportal.lts.dto.LtsTeamFunctionDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO.Status;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.LtsDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.PrepayLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.service.CsPortalService;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsSignatureService;
import com.bomwebportal.lts.service.acq.LtsAcqOrderService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.order.LtsDsOrderApprovalSubmitService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.service.order.SaveSbOrderLtsService;
import com.bomwebportal.lts.service.order.acq.LtsAcqOrderInitSignoffService;
import com.bomwebportal.lts.service.order.acq.LtsAcqOrderPostSubmitLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.service.WorkQueueService;
import com.bomwebportal.lts.service.bom.CustContactInfoService;
import com.bomwebportal.service.ServiceActionBase;

public class LtsAcqOrderController extends AbstractController{

	
	private final String paymentView = "ltsacqpaymentmethod.html";
	private final String appointmentView = "ltsacqappointment.html";
	private final String createOrderView = "ltsacqaddressrollout.html";
	private final String orderInfoView = "ltsorderinfo.html";
	private final String orderSummaryView = "ltsacqsummary.html";
	private final String amendOrderView = "orderamend.html";
	
	protected LtsAcqOrderService ltsAcqOrderService;
	private OrderStatusService orderStatusService;
	private CsPortalService csPortalService;
	private LtsCommonService ltsCommonService;
	private WorkQueueService workQueueService;
	private CustomerProfileLtsService customerProfileLtsService;
	private LtsAcqDnPoolService ltsAcqDnPoolService;
	private LtsSignatureService ltsSignatureService;
	private SaveSbOrderLtsService saveSbOrderLtsService;
	
	private LtsAcqOrderPostSubmitLtsService ltsAcqOrderPostSubmitLtsService;
	private LtsDsOrderApprovalSubmitService ltsDsOrderApprovalSubmitService;
	private LtsAcqOrderInitSignoffService ltsAcqOrderInitSignoffService;
	private CustContactInfoService custContactInfoService;
	private LtsOfferService ltsOfferService;

	private Locale locale;
	private MessageSource messageSource;

	public CustContactInfoService getCustContactInfoService() {
		return custContactInfoService;
	}

	public void setCustContactInfoService(
			CustContactInfoService custContactInfoService) {
		this.custContactInfoService = custContactInfoService;
	}

	public LtsSignatureService getLtsSignatureService() {
		return ltsSignatureService;
	}

	public void setLtsSignatureService(LtsSignatureService ltsSignatureService) {
		this.ltsSignatureService = ltsSignatureService;
	}

	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	public LtsAcqOrderService getLtsAcqOrderService() {
		return ltsAcqOrderService;
	}

	public void setLtsAcqOrderService(
			LtsAcqOrderService ltsAcqOrderService) {
		this.ltsAcqOrderService = ltsAcqOrderService;
	}

	public CsPortalService getCsPortalService() {
		return csPortalService;
	}

	public void setCsPortalService(CsPortalService csPortalService) {
		this.csPortalService = csPortalService;
	}

	public OrderStatusService getOrderStatusService() {
		return this.orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService pOrderStatusService) {
		this.orderStatusService = pOrderStatusService;
	}

	public WorkQueueService getWorkQueueService() {
		return workQueueService;
	}

	public void setWorkQueueService(WorkQueueService workQueueService) {
		this.workQueueService = workQueueService;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}
	
	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}
	
	public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		String action = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ACTION);
		setLocale(RequestContextUtils.getLocale(request));
		
		LtsTeamFunctionDTO ltsTeamFunction = ltsCommonService.getTeamFunction(bomSalesUser.getChannelCd(), bomSalesUser.getShopCd());
		LtsSessionHelper.setLtsTeamFunction(request, ltsTeamFunction);
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_CREATE, action) || StringUtils.isEmpty(action)) {
			return createAcqOrder(request, bomSalesUser);	
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_SUSPEND, action)) {
			return suspendOrder(request, acqOrderCapture, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_ENQUIRY, action)) {
			return enquiryOrder(request, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_RECALL, action)) {
			return recallOrder(request, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_RECALL_N_UPDATE_PREPAYMENT, action)) {
			return recallAndUpdatePrepaymentOrder(request, bomSalesUser);
		}

		if (StringUtils.equals(LtsConstant.ORDER_ACTION_RECALL_N_UPDATE_APPOINTMENT, action)) {
			return recallAndUpdateAppointment(request, bomSalesUser);
		}

		if (StringUtils.equals(LtsConstant.ORDER_ACTION_UPDATE_APPOINTMENT_FOR_QC, action)) {
			return updateAppointment(request, acqOrderCapture, bomSalesUser, action);
		}

		if (StringUtils.equals(LtsConstant.ORDER_ACTION_UPDATE_APPOINTMENT_FOR_AWAIT_PREPAYMENT, action)) {
			return updateAppointment(request, acqOrderCapture, bomSalesUser, action);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_AMEND, action)){
			return amendOrder(request, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_ENQUIRY_N_CANCEL, action)) {
			return enquiryAndCancelOrder(request, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_CANCEL, action)) {
			return cancelOrder(request, acqOrderCapture, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_SIGNOFF, action)) {
			return signOffOrder(request, acqOrderCapture, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_APPROVE, action)) {
			return approveOrder(request, acqOrderCapture, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_SUSPEND_W_PEND, action)) {
			return suspendOrderWithPending(request, acqOrderCapture, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_SUBMIT, action)) {
			return signOffAcqOrder(request, acqOrderCapture, bomSalesUser);
//			SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
//			if(sbOrder != null && sbOrder.getLtsDsOrderInfo() != null && "Y".equals(sbOrder.getLtsDsOrderInfo().getMustQc())){	// Must QC case --> Pending for QC
//				return suspendOrderForQc(request, acqOrderCapture, bomSalesUser);
//			}
//			else if(sbOrder != null && sbOrder.getPrepay() != null){	//With prepayment --> Pending for prepayment
//				return suspendOrderForPrepayment(request, acqOrderCapture, bomSalesUser);
//			}
//			else if(sbOrder != null && sbOrder.getPrepay() == null){	//Without prepayment --> Sign-off
//				return signOffOrder(request, acqOrderCapture, bomSalesUser);
//			}
//			
//			return suspendOrderWithPending(request, acqOrderCapture, bomSalesUser);
		}		
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_DS_SUBMIT, action)) {
			return signOffAcqOrder(request, acqOrderCapture, bomSalesUser);
//			return submitDsOrder(request, acqOrderCapture, bomSalesUser);
		}
		
		LtsSessionHelper.clearAllSessionAttb(request);
		return new ModelAndView(new RedirectView(createOrderView));
	}
	
	private ModelAndView createAcqOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		LtsSessionHelper.clearAcqOrderCapture(request);
		LtsSessionHelper.clearAllSessionAttb(request);
		
		AcqOrderCaptureDTO acqOrderCapture = new AcqOrderCaptureDTO();
		acqOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_CREATE);
		
		String custNum = request.getParameter(LtsConstant.REQUEST_PARAM_LEGACY_CUST_NUM);
		if(StringUtils.isNotBlank(custNum) && StringUtils.isNumeric(custNum)){
			CustomerDetailProfileLtsDTO custDtlProfile = customerProfileLtsService.getCustByCustNum(custNum, LtsConstant.SYSTEM_ID_DRG);
			if(custDtlProfile != null){
				acqOrderCapture.setCustomerDetailProfileLtsDTO(custDtlProfile);
			}
		}
		
		CustomerInformationDTO custInfo = (CustomerInformationDTO)request.getSession().getAttribute("customerInformationDTOSession");		
		if(acqOrderCapture != null && custInfo != null && custInfo.getBomVerifiedCustomerResult() != null){
			acqOrderCapture.setBomVerifiedCustomerResult(custInfo.getBomVerifiedCustomerResult());
		}
		
		if(StringUtils.isNotBlank(bomSalesUser.getSalesType()) && StringUtils.isNotBlank(bomSalesUser.getLocation())){
			LtsDsOrderInfoDTO dsInfo = new LtsDsOrderInfoDTO();
			dsInfo.setDsType(bomSalesUser.getSalesType());
			dsInfo.setDsLocation(bomSalesUser.getLocation());			
			acqOrderCapture.setLtsDsOrderInfo(dsInfo);
		}						
		
		LtsSessionHelper.setAcqOrderCapture(request, acqOrderCapture);
		
		return new ModelAndView(new RedirectView(createOrderView));	
	}

	private ModelAndView enquiryAndCancelOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		AcqOrderCaptureDTO dummyOrderCapture = ltsAcqOrderService.recallOrder(orderId, true, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY_N_CANCEL);
		LtsSessionHelper.setAcqOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
	
	private ModelAndView enquiryOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		AcqOrderCaptureDTO dummyOrderCapture = ltsAcqOrderService.recallOrder(orderId, true, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		LtsSessionHelper.setAcqOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
	
	private ModelAndView recallOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		AcqOrderCaptureDTO dummyOrderCapture = ltsAcqOrderService.recallOrder(orderId, false, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_RECALL);
		LtsSessionHelper.setAcqOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(paymentView));
	}
	
    private ModelAndView recallAndUpdatePrepaymentOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		AcqOrderCaptureDTO dummyOrderCapture = ltsAcqOrderService.recallOrder(orderId, false, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_RECALL_N_UPDATE_PREPAYMENT);
		LtsSessionHelper.setAcqOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(appointmentView));
	}

    private ModelAndView recallAndUpdateAppointment(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		AcqOrderCaptureDTO dummyOrderCapture = ltsAcqOrderService.recallOrder(orderId, false, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_RECALL_N_UPDATE_APPOINTMENT);
		LtsSessionHelper.setAcqOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(appointmentView));
	}

	private ModelAndView amendOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		AcqOrderCaptureDTO dummyOrderCapture = ltsAcqOrderService.recallOrder(orderId, true, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_AMEND);
		LtsSessionHelper.setAcqOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(amendOrderView));
	}
	

	private ModelAndView suspendOrder(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {
		
		String reasonCd = request.getParameter(LtsConstant.REQUEST_PARAM_REASON_CD);
		String sbOrderId = ltsAcqOrderService.saveOrder(acqOrderCapture, bomSalesUser, reasonCd);
		
		if (acqOrderCapture.getSbOrder() != null)
		{
			if(!(StringUtils.equals(LtsConstant.ORDER_STATUS_SUSPENDED, LtsSbOrderHelper.getLtsService(acqOrderCapture.getSbOrder()).getSbStatus())
					|| StringUtils.equals(LtsConstant.ORDER_STATUS_APPROVAL_REJECTED, LtsSbOrderHelper.getLtsService(acqOrderCapture.getSbOrder()).getSbStatus())))
			{
				acqOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
				return new ModelAndView(new RedirectView(orderSummaryView));
			}
		}
		else
		{
			String fromViewName = request.getParameter(LtsConstant.REQUEST_PARAM_FROM_VIEW);
			if(!StringUtils.isBlank(reasonCd) && !StringUtils.isBlank(fromViewName))
			{
				List<String> sList = validateItemQuota(acqOrderCapture);
				if (sList.size()>0) {
					ModelAndView mav = new ModelAndView(new RedirectView(fromViewName+".html"));
					mav.addObject("outQuoErrMsgList", sList);
					return mav;
				}
			}
		}
		
		
		if (StringUtils.isEmpty(reasonCd)) {
			AcqOrderCaptureDTO dummyOrderCapture = ltsAcqOrderService.recallOrder(sbOrderId, false, bomSalesUser);
			LtsSessionHelper.setAcqOrderCapture(request, dummyOrderCapture);
//			SbOrderDTO sbOrder = ltsAcqOrderService.retrieveOrder(sbOrderId);
//			orderCapture.setSbOrder(sbOrder);
			acqOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_SUSPEND);
			return new ModelAndView(new RedirectView(orderSummaryView));
		}
		else {
			if(LtsBackendConstant.REASON_CODE_DRG_DOWNTIME.equals(reasonCd)){
				SbOrderDTO sbOrder = ltsAcqOrderService.retrieveOrder(sbOrderId);
				if(sbOrder != null && sbOrder.getSrvDtls() != null){
					this.ltsAcqOrderPostSubmitLtsService.submitDrgDowntimeWorkQueue(sbOrder, bomSalesUser.getUsername());
				}	
			}
			
			LtsSessionHelper.clearAcqOrderCapture(request);
		}
		
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, "Order Suspended. Springboard Order Id : " +sbOrderId);
		return new ModelAndView(new RedirectView(orderInfoView));
	}
	
	private List<String> validateItemQuota(AcqOrderCaptureDTO orderCapture){
		
		Map<String,LtsQuotaDTO> quotaMap = new HashMap<String,LtsQuotaDTO>();
		List<String> outQuotaItemList = new ArrayList<String>();
		List<String> errorMsgList = new ArrayList<String>();
		
		if(orderCapture!=null)
		{
			BasketDetailDTO basketDtl = orderCapture.getSelectedBasket();
			
			//calculate the CurrentQuotaUsed for each ProgramCd
			quotaMap = ltsOfferService.getQuotaMapByBasket(basketDtl,quotaMap);	
			
			if(orderCapture.getLtsPremiumSelectionForm()!=null)
			{
				if(orderCapture.getLtsPremiumSelectionForm().getGiftSetList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemSetList(orderCapture.getLtsPremiumSelectionForm().getGiftSetList(),quotaMap);
				if(orderCapture.getLtsPremiumSelectionForm().getPremiumSetList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemSetList(orderCapture.getLtsPremiumSelectionForm().getPremiumSetList(),quotaMap);
			}
			if(orderCapture.getLtsAcqBasketVasDetailForm()!=null)
			{
				if(orderCapture.getLtsAcqBasketVasDetailForm().getPrewireVasItemList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemList(orderCapture.getLtsAcqBasketVasDetailForm().getPrewireVasItemList(),quotaMap);
				if(orderCapture.getLtsAcqBasketVasDetailForm().getPreinstallVasItemList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemList(orderCapture.getLtsAcqBasketVasDetailForm().getPreinstallVasItemList(),quotaMap);
				if(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemList(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList(),quotaMap);
				if(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemList(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList(),quotaMap);
				if(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemList(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList(),quotaMap);				
			}
			if(orderCapture.getLtsAcqBasketServiceForm()!=null)
			{
				if(orderCapture.getLtsAcqBasketServiceForm().getContItemSetList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemSetList(orderCapture.getLtsAcqBasketServiceForm().getContItemSetList(),quotaMap);
				if(orderCapture.getLtsAcqBasketServiceForm().getMoovItemList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemList(orderCapture.getLtsAcqBasketServiceForm().getMoovItemList(),quotaMap);	
				if(orderCapture.getLtsAcqBasketServiceForm().getContentItemList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemList(orderCapture.getLtsAcqBasketServiceForm().getContentItemList(),quotaMap);
				if(orderCapture.getLtsAcqBasketServiceForm().getPlanItemList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemList(orderCapture.getLtsAcqBasketServiceForm().getPlanItemList(),quotaMap);
				if(orderCapture.getLtsAcqBasketServiceForm().getBvasItemList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemList(orderCapture.getLtsAcqBasketServiceForm().getBvasItemList(),quotaMap);
				if(orderCapture.getLtsAcqBasketServiceForm().getInstallItemList()!=null)
					quotaMap = ltsOfferService.getQuotaMapByItemList(orderCapture.getLtsAcqBasketServiceForm().getInstallItemList(),quotaMap);				
			}
			//
			
			// get the all OutQuota basket desc and item desc
			String tempOutQuotaBasket = ltsOfferService.getOutQuotaByBasket(basketDtl,quotaMap);
			if(StringUtils.isNotBlank(tempOutQuotaBasket)){
				outQuotaItemList.add(tempOutQuotaBasket);
			}
			if(orderCapture.getLtsPremiumSelectionForm()!=null)
			{
				if(orderCapture.getLtsPremiumSelectionForm().getGiftSetList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemSetListInMap(orderCapture.getLtsPremiumSelectionForm().getGiftSetList(),quotaMap));
				if(orderCapture.getLtsPremiumSelectionForm().getPremiumSetList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemSetListInMap(orderCapture.getLtsPremiumSelectionForm().getPremiumSetList(),quotaMap));
			}
			if(orderCapture.getLtsAcqBasketVasDetailForm()!=null)
			{
				if(orderCapture.getLtsAcqBasketVasDetailForm().getPrewireVasItemList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemListInMap(orderCapture.getLtsAcqBasketVasDetailForm().getPrewireVasItemList(),quotaMap));
				if(orderCapture.getLtsAcqBasketVasDetailForm().getPreinstallVasItemList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemListInMap(orderCapture.getLtsAcqBasketVasDetailForm().getPreinstallVasItemList(),quotaMap));
				if(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemListInMap(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList(),quotaMap));		
				if(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemListInMap(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList(),quotaMap));
				if(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemListInMap(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList(),quotaMap));
			}
			if(orderCapture.getLtsAcqBasketServiceForm()!=null)
			{
				if(orderCapture.getLtsAcqBasketServiceForm().getContItemSetList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemSetListInMap(orderCapture.getLtsAcqBasketServiceForm().getContItemSetList(),quotaMap));
				if(orderCapture.getLtsAcqBasketServiceForm().getMoovItemList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemListInMap(orderCapture.getLtsAcqBasketServiceForm().getMoovItemList(),quotaMap));
				if(orderCapture.getLtsAcqBasketServiceForm().getContentItemList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemListInMap(orderCapture.getLtsAcqBasketServiceForm().getContentItemList(),quotaMap));		
				if(orderCapture.getLtsAcqBasketServiceForm().getPlanItemList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemListInMap(orderCapture.getLtsAcqBasketServiceForm().getPlanItemList(),quotaMap));
				if(orderCapture.getLtsAcqBasketServiceForm().getBvasItemList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemListInMap(orderCapture.getLtsAcqBasketServiceForm().getBvasItemList(),quotaMap));		
				if(orderCapture.getLtsAcqBasketServiceForm().getInstallItemList()!=null)
					outQuotaItemList.addAll(ltsOfferService.getOutQuotaByItemListInMap(orderCapture.getLtsAcqBasketServiceForm().getInstallItemList(),quotaMap));
			}
			//
		
			if (outQuotaItemList.isEmpty()) {
				ltsOfferService.addBasketQuotaUsed(basketDtl);
				if(orderCapture.getLtsPremiumSelectionForm()!=null)
				{
					if(orderCapture.getLtsPremiumSelectionForm().getGiftSetList()!=null)
						ltsOfferService.addItemSetListQuotaUsed(orderCapture.getLtsPremiumSelectionForm().getGiftSetList());
					if(orderCapture.getLtsPremiumSelectionForm().getPremiumSetList()!=null)
						ltsOfferService.addItemSetListQuotaUsed(orderCapture.getLtsPremiumSelectionForm().getPremiumSetList());
				}
				if(orderCapture.getLtsAcqBasketVasDetailForm()!=null)
				{
					if(orderCapture.getLtsAcqBasketVasDetailForm().getPrewireVasItemList()!=null)
						ltsOfferService.addItemListQuotaUsed(orderCapture.getLtsAcqBasketVasDetailForm().getPrewireVasItemList());
					if(orderCapture.getLtsAcqBasketVasDetailForm().getPreinstallVasItemList()!=null)
						ltsOfferService.addItemListQuotaUsed(orderCapture.getLtsAcqBasketVasDetailForm().getPreinstallVasItemList());
					if(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList()!=null)
						ltsOfferService.addItemListQuotaUsed(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasHotItemList());		
					if(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList()!=null)
						ltsOfferService.addItemListQuotaUsed(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasOtherItemList());
					if(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList()!=null)
						ltsOfferService.addItemListQuotaUsed(orderCapture.getLtsAcqBasketVasDetailForm().getFfpVasStaffItemList());
				}
				if(orderCapture.getLtsAcqBasketServiceForm()!=null)
				{
					if(orderCapture.getLtsAcqBasketServiceForm().getContItemSetList()!=null)
						ltsOfferService.addItemSetListQuotaUsed(orderCapture.getLtsAcqBasketServiceForm().getContItemSetList());
					if(orderCapture.getLtsAcqBasketServiceForm().getMoovItemList()!=null)
						ltsOfferService.addItemListQuotaUsed(orderCapture.getLtsAcqBasketServiceForm().getMoovItemList());
					if(orderCapture.getLtsAcqBasketServiceForm().getContentItemList()!=null)
						ltsOfferService.addItemListQuotaUsed(orderCapture.getLtsAcqBasketServiceForm().getContentItemList());		
					if(orderCapture.getLtsAcqBasketServiceForm().getPlanItemList()!=null)
						ltsOfferService.addItemListQuotaUsed(orderCapture.getLtsAcqBasketServiceForm().getPlanItemList());
					if(orderCapture.getLtsAcqBasketServiceForm().getBvasItemList()!=null)
						ltsOfferService.addItemListQuotaUsed(orderCapture.getLtsAcqBasketServiceForm().getBvasItemList());		
					if(orderCapture.getLtsAcqBasketServiceForm().getInstallItemList()!=null)
						ltsOfferService.addItemListQuotaUsed(orderCapture.getLtsAcqBasketServiceForm().getInstallItemList());
				}
			}
			else
			{
				for(int i = 0; i < outQuotaItemList.size(); i++){
					errorMsgList.add(messageSource.getMessage("lts.offerDtl.outQuotaMsg", new Object[] {outQuotaItemList.get(i)}, locale));
				}
			}
		
		}
		
		return errorMsgList;
	}
	
	private ModelAndView cancelOrder(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {
		
		String reasonCd = request.getParameter(LtsConstant.REQUEST_PARAM_REASON_CD);
		

		if (acqOrderCapture == null || acqOrderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}

		String sbOrderId = acqOrderCapture.getSbOrder().getOrderId();
		ltsAcqOrderService.cancelOrder(acqOrderCapture.getSbOrder(), bomSalesUser, reasonCd);
		if(LtsConstant.ORDER_ACTION_ENQUIRY_N_CANCEL.equals(acqOrderCapture.getOrderAction())){
			try {
				workQueueService.sbOrderCancelled(sbOrderId, false, bomSalesUser.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// release DN pool status to Normal
		releaseDnFromDnPool(acqOrderCapture.getSbOrder());
		
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, "Springboard Order ["+sbOrderId+"] Cancelled." );
		return new ModelAndView(new RedirectView(orderInfoView));
	}
	
	private void releaseDnFromDnPool(SbOrderDTO pSbOrder) {
		// primary del
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(pSbOrder);		
		if (ltsServiceDetail != null
				&& LtsConstant.DN_SOURCE_DN_POOL.equals(ltsServiceDetail.getDnSource())) {
			ltsAcqDnPoolService.releaseDnStatusToNormalByConfirmedDn(ltsServiceDetail.getSrvNum());
		}
		// second del
		ServiceDetailLtsDTO lts2ndDelServiceDetail = LtsSbOrderHelper.get2ndDelService(pSbOrder);
		if (lts2ndDelServiceDetail != null
				&& LtsConstant.DN_SOURCE_DN_POOL.equals(lts2ndDelServiceDetail.getDnSource())) {	
			ltsAcqDnPoolService.releaseDnStatusToNormalByConfirmedDn(lts2ndDelServiceDetail.getSrvNum());
		}
	}
	
	private ModelAndView submitDsOrder(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {
		SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
		if (acqOrderCapture == null || sbOrder == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		if(ltsAcqOrderPostSubmitLtsService != null){
			boolean containsPrepaymentItem = false;
			if(sbOrder != null && sbOrder.getPrepay() != null){ 
				for(PrepayLtsDTO info : sbOrder.getPrepay()){
					if(!"C".equals(info.getPaymentStatus())){	//Exempt Credit Card case 
						containsPrepaymentItem = true;
					}
				}
			}
			
			boolean mustQc = false;
			if(sbOrder != null && sbOrder.getLtsDsOrderInfo() != null && "Y".equals(sbOrder.getLtsDsOrderInfo().getMustQc())){
				mustQc = true;
			}
			
			ServiceDetailLtsDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
			if(StringUtils.equals("Y", ltsService.getAccount().getCustomer().getMismatchInd())
					&& !Arrays.asList(LtsConstant.NAME_MISMATCH_AFTER_APPROVAL).contains(sbOrder.getLtsDsOrderInfo().getNameMismatchStatus())){
				boolean submitNameNotMatchWqSuccess = ltsDsOrderApprovalSubmitService.submitOrderForCustNameNotMatch(sbOrder, bomSalesUser.getUsername());
				if(submitNameNotMatchWqSuccess){
					acqOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
				}else{
					request.getSession().setAttribute("emailResult", "Failed to Submit Name Not Match Approval WQ.");
				}
				return new ModelAndView(new RedirectView(orderSummaryView));
			}
			else if(containsPrepaymentItem && mustQc){
				ServiceDetailDTO[] serviceDetails = acqOrderCapture.getSbOrder().getSrvDtls();
				for (int i=0; serviceDetails!=null && i<serviceDetails.length; ++i) {
					// update order status = Q
					orderStatusService.setPendingQcStatus(acqOrderCapture.getSbOrder().getOrderId(), serviceDetails[i].getDtlId(), bomSalesUser.getUsername(), null);
				}	
			}
			else if(containsPrepaymentItem && !mustQc){
				ltsAcqOrderService.signOffOrder(sbOrder, bomSalesUser);
			}
			else{
				return signOffOrder(request, acqOrderCapture, bomSalesUser);
			}
		}
		else{
			logger.warn("ltsAcqOrderPostSubmitLtsService is EMPTY");
		}
		
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
	
	private ModelAndView signOffAcqOrder(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {
		if (acqOrderCapture == null || acqOrderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		acqOrderCapture.getSbOrder().setSignatures(ltsSignatureService.getOrderSignatures(acqOrderCapture.getSbOrder().getOrderId()));
//		String emailResult = ltsAcqOrderInitSignoffService.initialSignoff(acqOrderCapture.getSbOrder(), bomSalesUser);

		ValidationResultDTO result = ltsAcqOrderInitSignoffService.initialSignoff(acqOrderCapture.getSbOrder(), bomSalesUser);
		
		SbOrderDTO sbOrder = ltsAcqOrderService.retrieveOrder(acqOrderCapture.getSbOrder().getOrderId());
		acqOrderCapture.setSbOrder(sbOrder);
		
		acqOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_AFTER_SUBMIT);
		if(result.getStatus() == Status.INVAILD){
			request.getSession().setAttribute("emailResult", result.getMessageList());
		}
		
		return new ModelAndView(new RedirectView(orderSummaryView));
	}

	private ModelAndView signOffOrder(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {
		return signOffOrder(request, acqOrderCapture, bomSalesUser, false);
	}
	
	private ModelAndView signOffOrder(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser, boolean pForDsQcService) {

		SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
		if (acqOrderCapture == null || sbOrder == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		List<String> messageList = new ArrayList<String>(); 
		Status sts = Status.VALID;
		
		// TBC LtsAcqDigitalSignatureForm still not create
/*		if (StringUtils.equals(LtsConstant.DISTRIBUTE_MODE_ESIGNATURE, sbOrder.getDisMode())
				&& acqOrderCapture.getLtsAcqDigitalSignatureForm() != null) {
			sbOrder.setSignatures(LtsSbOrderHelper.convertSignature(acqOrderCapture.getLtsAcqDigitalSignatureForm().getSignatureList()));
		}*/
		
		sbOrder.setSignatures(ltsSignatureService.getOrderSignatures(sbOrder.getOrderId()));

		String emailResult = isAwaitQcStatus(sbOrder)? ltsAcqOrderService.signOffOrderQC(sbOrder, bomSalesUser)
				: ltsAcqOrderService.signOffOrder(sbOrder, bomSalesUser);

		//CsPortal Registration
		ItemDetailDTO cspItem = acqOrderCapture.getLtsAcqBillMediaBillLangForm().getPrimaryBillMediaDtl().getCsPortalItem();
		if(cspItem != null && cspItem.isSelected() && cspItem.getItemAttbs() != null){
			String emailAddr = null;
			String targetAcct = null;
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_MYHKT_BILL, cspItem.getItemType())) {
				emailAddr = getAttbValueByAttbTypeAndKey(cspItem.getItemAttbs(), 
						LtsConstant.CHANNEL_ATTB_FORMAT_EMAIL, LtsConstant.ITEM_ATTB_INFO_KEY_HKT);
				targetAcct = LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY;				
			}
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_THE_CLUB_BILL, cspItem.getItemType())) {
				emailAddr = getAttbValueByAttbTypeAndKey(cspItem.getItemAttbs(), 
						LtsConstant.CHANNEL_ATTB_FORMAT_EMAIL, LtsConstant.ITEM_ATTB_INFO_KEY_CLUB);
				targetAcct = LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY;				
			}
			if (StringUtils.equals(LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL, cspItem.getItemType())) {
				emailAddr = getAttbValueByAttbType(cspItem.getItemAttbs(), 
						LtsConstant.CHANNEL_ATTB_FORMAT_EMAIL);
				targetAcct = LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER;
			}
			//Do not register myHKT if the email or mobile is dummy
			if(StringUtils.equals(targetAcct, LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER)
					&& ( StringUtils.contains(LtsSbHelper.getLtsService(sbOrder).getAccount().getCustomer().getCsPortalLogin(), LtsBackendConstant.CSP_DUMMY_EMAIL_DOMAIN)
						 || StringUtils.contains(LtsSbHelper.getLtsService(sbOrder).getAccount().getCustomer().getCsPortalMobile(),LtsBackendConstant.CSP_DUMMY_MOBILE_NUM))){
				targetAcct = LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY;
			}
			if(StringUtils.isNotBlank(emailAddr)){								
				CsPortalIdRegrArqDTO csPortalIdRegrArqDTO = (CsPortalIdRegrArqDTO)csPortalService.regCsIdForTheClubAndHkt(acqOrderCapture.getSbOrder(), 
						acqOrderCapture.getLtsAcqSalesInfoForm().getStaffId(), 
						LtsCsPortalBackendConstant.SOURCE_SYSTEM_SPRINGBOARD, 
						StringUtils.isNotBlank(acqOrderCapture.getAddressRollout().getHktPremier()), 
						targetAcct);
				CustomerDetailLtsDTO cust = LtsSbHelper.getLtsService(sbOrder).getAccount().getCustomer();				
				if (StringUtils.equals(LtsConstant.ITEM_TYPE_MYHKT_BILL, cspItem.getItemType())) {
					cust.setCsPortalInd(csPortalIdRegrArqDTO!=null && csPortalIdRegrArqDTO.isMyHktRegSucces()?
							LtsBackendConstant.CSP_REG:LtsBackendConstant.CSP_FAIL_REG);
				}
				if (StringUtils.equals(LtsConstant.ITEM_TYPE_THE_CLUB_BILL, cspItem.getItemType())) {
	                cust.setTheClubInd(csPortalIdRegrArqDTO!=null && csPortalIdRegrArqDTO.isTheClubRegSucces()?
							LtsBackendConstant.CSP_REG:LtsBackendConstant.CSP_FAIL_REG);
				} 
				if (StringUtils.equals(LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL, cspItem.getItemType())) {
					cust.setCsPortalInd(csPortalIdRegrArqDTO!=null && csPortalIdRegrArqDTO.isMyHktRegSucces()?
							LtsBackendConstant.CSP_REG:LtsBackendConstant.CSP_FAIL_REG);
					cust.setTheClubInd(csPortalIdRegrArqDTO!=null && csPortalIdRegrArqDTO.isTheClubRegSucces()?
							LtsBackendConstant.CSP_REG:LtsBackendConstant.CSP_FAIL_REG);	
				}
				cust.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
				saveSbOrderLtsService.saveCustomer(cust, sbOrder.getOrderId(), cust.getCustNo(), bomSalesUser.getUsername());
			}			
		}		
        
		ltsAcqOrderInitSignoffService.regIguardCarecashForSbOrder(acqOrderCapture.getSbOrder(), bomSalesUser.getUsername());
		
		String updatePdpoResult = ltsAcqOrderInitSignoffService.updateCustDataPrivayInfo(sbOrder, bomSalesUser.getUsername());
		if (StringUtils.isNotBlank(updatePdpoResult)) {
			messageList.add(updatePdpoResult);	
			sts = Status.INVAILD;
		}

		custContactInfoService.updateCustContactInfo(sbOrder.getOrderId(), sbOrder.getContact(), bomSalesUser.getUsername(), sbOrder.getSalesChannel());
		
		sbOrder = ltsAcqOrderService.retrieveOrder(acqOrderCapture.getSbOrder().getOrderId());
		acqOrderCapture.setSbOrder(sbOrder);
		
		acqOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_AFTER_SUBMIT);
		
		
		if(sts == Status.INVAILD){
			messageList.add(emailResult);
			request.getSession().setAttribute("emailResult", messageList);
		}else{
			if(StringUtils.isNotBlank(emailResult)){
				request.getSession().setAttribute("emailResult", emailResult);
			}
		}
		
		
		return new ModelAndView(new RedirectView(orderSummaryView));
//		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, "Springboard Order ["+orderCapture.getSbOrder().getOrderId()+"] Completed." );
//		return new ModelAndView(new RedirectView(orderInfoView));
	}
	
		
	private String getAttbValueByAttbType(ItemAttbBaseDTO[] attbArray, String attbType){
		for(ItemAttbBaseDTO attb: attbArray){
			if(attbType.equals(attb.getInputFormat())){
				return attb.getAttbValue();
			}
		}		
		return null;
	}
	
	private String getAttbValueByAttbTypeAndKey(ItemAttbBaseDTO[] attbArray, String attbType, String attbKey){
		for(ItemAttbBaseDTO attb: attbArray){
			if(attbType.equals(attb.getInputFormat()) && attbKey.equals(attb.getAttbInfoKey())){
				return attb.getAttbValue();
			}
		}		
		return null;
	}
	
	private ModelAndView approveOrder(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {

		
		if (acqOrderCapture == null || acqOrderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		ltsAcqOrderService.approvalOrder(acqOrderCapture.getSbOrder(), bomSalesUser);
		
		SbOrderDTO sbOrder = ltsAcqOrderService.retrieveOrder(acqOrderCapture.getSbOrder().getOrderId());
		acqOrderCapture.setSbOrder(sbOrder);
		acqOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		return new ModelAndView(new RedirectView(orderSummaryView));
		
//		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, "Springboard Order ["+orderCapture.getSbOrder().getOrderId()+"] is waiting approval." );
//		return new ModelAndView(new RedirectView(orderInfoView));
	}
	

	private ModelAndView suspendOrderWithPending(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {	

		
		if (acqOrderCapture == null || acqOrderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		ltsAcqOrderService.suspendOrderWithPending(acqOrderCapture.getSbOrder(), bomSalesUser);
		acqOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
	
	private boolean isAwaitQcStatus(SbOrderDTO sbOrder) {
    	OrderStatusDTO[] status = orderStatusService.getStatus(sbOrder.getOrderId());
    	for (int i=0; status!=null && i<status.length; ++i) {
 		   if (StringUtils.equals(LtsBackendConstant.ORDER_STATUS_AWAIT_QC, status[i].getSbStatus())) {
 			   return true;
 		   }
        }
 	    return false;
    }

	private ModelAndView suspendOrderForPrepayment(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {			
		if (acqOrderCapture == null || acqOrderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		ltsAcqOrderService.suspendOrderForPrepayment(acqOrderCapture.getSbOrder(), bomSalesUser);
		acqOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
	
	private ModelAndView suspendOrderForQc(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture, BomSalesUserDTO bomSalesUser) {			
		if (acqOrderCapture == null || acqOrderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		ltsAcqOrderService.suspendOrderForQc(acqOrderCapture.getSbOrder(), bomSalesUser);
		acqOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
	
	private ModelAndView updateAppointment(HttpServletRequest request, AcqOrderCaptureDTO acqOrderCapture, 
			BomSalesUserDTO bomSalesUser, String orderAction) {	
		if (acqOrderCapture == null || acqOrderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
		ltsAcqOrderService.updateAppointmentForQc(acqOrderCapture, sbOrder, bomSalesUser);
		
		for(ServiceDetailDTO srvDtl : sbOrder.getSrvDtls()){
			saveSbOrderLtsService.saveAppointmentDetail(srvDtl.getAppointmentDtl(), sbOrder.getOrderId(), srvDtl.getDtlId(), bomSalesUser.getUsername());
		}
		
		if(LtsConstant.ORDER_ACTION_UPDATE_APPOINTMENT_FOR_AWAIT_PREPAYMENT.equals(orderAction)){
			orderAction = LtsConstant.ORDER_ACTION_RECALL_N_UPDATE_PREPAYMENT;
		}
		
		acqOrderCapture.setOrderAction(orderAction);
		
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
	
	/**
	 * @return the ltsAcqDnPoolService
	 */
	public LtsAcqDnPoolService getLtsAcqDnPoolService() {
		return ltsAcqDnPoolService;
	}

	/**
	 * @param ltsAcqDnPoolService the ltsAcqDnPoolService to set
	 */
	public void setLtsAcqDnPoolService(LtsAcqDnPoolService ltsAcqDnPoolService) {
		this.ltsAcqDnPoolService = ltsAcqDnPoolService;
	}

	/**
	 * @return the saveSbOrderLtsService
	 */
	public SaveSbOrderLtsService getSaveSbOrderLtsService() {
		return saveSbOrderLtsService;
	}

	/**
	 * @param saveSbOrderLtsService the saveSbOrderLtsService to set
	 */
	public void setSaveSbOrderLtsService(SaveSbOrderLtsService saveSbOrderLtsService) {
		this.saveSbOrderLtsService = saveSbOrderLtsService;
	}

	public LtsAcqOrderPostSubmitLtsService getLtsAcqOrderPostSubmitLtsService() {
		return ltsAcqOrderPostSubmitLtsService;
	}

	public void setLtsAcqOrderPostSubmitLtsService(
			LtsAcqOrderPostSubmitLtsService ltsAcqOrderPostSubmitLtsService) {
		this.ltsAcqOrderPostSubmitLtsService = ltsAcqOrderPostSubmitLtsService;
	}

	public LtsDsOrderApprovalSubmitService getLtsDsOrderApprovalSubmitService() {
		return ltsDsOrderApprovalSubmitService;
	}

	public void setLtsDsOrderApprovalSubmitService(
			LtsDsOrderApprovalSubmitService ltsDsOrderApprovalSubmitService) {
		this.ltsDsOrderApprovalSubmitService = ltsDsOrderApprovalSubmitService;
	}

	public LtsAcqOrderInitSignoffService getLtsAcqOrderInitSignoffService() {
		return ltsAcqOrderInitSignoffService;
	}

	public void setLtsAcqOrderInitSignoffService(
			LtsAcqOrderInitSignoffService ltsAcqOrderInitSignoffService) {
		this.ltsAcqOrderInitSignoffService = ltsAcqOrderInitSignoffService;
	}



}
