package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;
import com.bomwebportal.lts.dto.LtsTeamFunctionDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.CsPortalService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsQuotaService;
import com.bomwebportal.lts.service.LtsSignatureService;
import com.bomwebportal.lts.service.LtsUpgradeEyeOrderService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.pccw.wq.service.WorkQueueService;

public class LtsUpgradeEyeOrderController extends AbstractController{

	
	private final String paymentView = "ltspayment.html";
	private final String createOrderView = "ltscustomerinquiry.html";
	private final String orderInfoView = "ltsorderinfo.html";
	private final String orderSummaryView = "ltssummary.html";
	private final String amendOrderView = "orderamend.html";
	
	protected LtsUpgradeEyeOrderService ltsUpgradeEyeOrderService;
	private OrderStatusService orderStatusService;
	private CsPortalService csPortalService;
	private LtsCommonService ltsCommonService;
	private WorkQueueService workQueueService;
	private LtsSignatureService ltsSignatureService;
	private LtsOfferService ltsOfferService;
	private LtsQuotaService ltsQuotaService;
	
	private Locale locale;
	private MessageSource messageSource;

	
	
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

	public LtsUpgradeEyeOrderService getLtsUpgradeEyeOrderService() {
		return ltsUpgradeEyeOrderService;
	}

	public void setLtsUpgradeEyeOrderService(
			LtsUpgradeEyeOrderService ltsUpgradeEyeOrderService) {
		this.ltsUpgradeEyeOrderService = ltsUpgradeEyeOrderService;
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

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public LtsQuotaService getLtsQuotaService() {
		return ltsQuotaService;
	}

	public void setLtsQuotaService(LtsQuotaService ltsQuotaService) {
		this.ltsQuotaService = ltsQuotaService;
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		String action = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ACTION);
		setLocale(RequestContextUtils.getLocale(request));
		
		LtsTeamFunctionDTO ltsTeamFunction = ltsCommonService.getTeamFunction(bomSalesUser.getChannelCd(), bomSalesUser.getShopCd());
		LtsSessionHelper.setLtsTeamFunction(request, ltsTeamFunction);
		
//		if (StringUtils.equals(LtsConstant.ORDER_ACTION_CREATE, action) || StringUtils.isEmpty(action)) {
//			LtsSessionHelper.clearOrderCapture(request);
//			return new ModelAndView(new RedirectView(createOrderView));	
//		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_SUSPEND, action)) {
			return suspendOrder(request, orderCapture, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_ENQUIRY, action)) {
			return enquiryOrder(request, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_RECALL, action)) {
			return recallOrder(request, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_AMEND, action)){
			return amendOrder(request, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_ENQUIRY_N_CANCEL, action)) {
			return enquiryAndCancelOrder(request, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_CANCEL, action)) {
			return cancelOrder(request, orderCapture, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_SIGNOFF, action)) {
			return signOffOrder(request, orderCapture, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_APPROVE, action)) {
			return approveOrder(request, orderCapture, bomSalesUser);
		}
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_SUSPEND_W_PEND, action)) {
			return suspendOrderWithPending(request, orderCapture, bomSalesUser);
		}
		
		LtsSessionHelper.clearAllSessionAttb(request);
		return new ModelAndView(new RedirectView(createOrderView));
	}

	private ModelAndView enquiryAndCancelOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		OrderCaptureDTO dummyOrderCapture = ltsUpgradeEyeOrderService.recallOrder(orderId, true, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY_N_CANCEL);
		LtsSessionHelper.setOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
	
	private ModelAndView enquiryOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		OrderCaptureDTO dummyOrderCapture = ltsUpgradeEyeOrderService.recallOrder(orderId, true, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		LtsSessionHelper.setOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
	
	private ModelAndView recallOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		OrderCaptureDTO dummyOrderCapture = ltsUpgradeEyeOrderService.recallOrder(orderId, false, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_RECALL);
		LtsSessionHelper.setOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(paymentView));
	}
	
	private ModelAndView amendOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		OrderCaptureDTO dummyOrderCapture = ltsUpgradeEyeOrderService.recallOrder(orderId, true, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_AMEND);
		LtsSessionHelper.setOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(amendOrderView));
	}
	
	private ModelAndView suspendOrder(HttpServletRequest request, OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {

		String reasonCd = request.getParameter(LtsConstant.REQUEST_PARAM_REASON_CD);
		
		if (orderCapture.getSbOrder() != null) { 
			ServiceDetailDTO ltsService = LtsSbOrderHelper.getLtsService(orderCapture.getSbOrder());
			
			if (!(StringUtils.equals(LtsConstant.ORDER_STATUS_INITIAL, ltsService.getSbStatus())
					||StringUtils.equals(LtsConstant.ORDER_STATUS_SUSPENDED, ltsService.getSbStatus())
					|| StringUtils.equals(LtsConstant.ORDER_STATUS_APPROVAL_REJECTED, ltsService.getSbStatus()))){
				orderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
				return new ModelAndView(new RedirectView(orderSummaryView));
			}
		}

		//TODO
		boolean isNoSuspendReason = true;
		if (orderCapture.getSbOrder() != null) { 
			ServiceDetailLtsDTO ltsSrv = LtsSbOrderHelper.getLtsService(orderCapture.getSbOrder());
			isNoSuspendReason = orderStatusService.noSuspendReason(orderCapture.getSbOrder().getOrderId(), ltsSrv.getDtlId());
		}
		if(isNoSuspendReason){
			//Update quota only for the first time suspend
			String fromViewName = request.getParameter(LtsConstant.REQUEST_PARAM_FROM_VIEW);
			if(StringUtils.isNotBlank(reasonCd)){
				
				List<ItemDetailDTO> itemList = new ArrayList<ItemDetailDTO>();
				List<ItemSetDetailDTO> itemSetList = new ArrayList<ItemSetDetailDTO>();
				if(orderCapture.getLtsBasketServiceForm() != null){
					addAllIfNotNull(itemList, orderCapture.getLtsBasketServiceForm().getPlanItemList());
					addAllIfNotNull(itemList, orderCapture.getLtsBasketServiceForm().getPlanItemList());
					addAllIfNotNull(itemList, orderCapture.getLtsBasketServiceForm().getContentItemList());
					addAllIfNotNull(itemList, orderCapture.getLtsBasketServiceForm().getMoovItemList());
					addAllIfNotNull(itemSetList, orderCapture.getLtsBasketServiceForm().getContItemSetList());
				}
				
				if(orderCapture.getLtsBasketVasDetailForm() != null){
					addAllIfNotNull(itemList, orderCapture.getLtsBasketVasDetailForm().getContentItemList());
					addAllIfNotNull(itemList, orderCapture.getLtsBasketVasDetailForm().getE0060VasItemList());
					addAllIfNotNull(itemList, orderCapture.getLtsBasketVasDetailForm().getFfpHotItemList());
					addAllIfNotNull(itemList, orderCapture.getLtsBasketVasDetailForm().getFfpOtherItemList());
					addAllIfNotNull(itemList, orderCapture.getLtsBasketVasDetailForm().getFfpStaffItemList());
					addAllIfNotNull(itemList, orderCapture.getLtsBasketVasDetailForm().getHotVasItemList());
					addAllIfNotNull(itemList, orderCapture.getLtsBasketVasDetailForm().getIddVasItemList());
					addAllIfNotNull(itemList, orderCapture.getLtsBasketVasDetailForm().getMoovItemList());
					addAllIfNotNull(itemList, orderCapture.getLtsBasketVasDetailForm().getOtherVasItemList());
					addAllIfNotNull(itemSetList, orderCapture.getLtsBasketVasDetailForm().getBundleVasSetList());
				}

				if(orderCapture.getLtsPremiumSelectionForm() != null){
					addAllIfNotNull(itemSetList, orderCapture.getLtsPremiumSelectionForm().getGiftSetList());
					addAllIfNotNull(itemSetList, orderCapture.getLtsPremiumSelectionForm().getPremiumSetList());
				}
				
				/*Check and add quota*/
				String invalidProgramCdDesc = processQuota(orderCapture, itemList, itemSetList);
				if(StringUtils.isNotBlank(invalidProgramCdDesc)){
					StringBuilder sb = new StringBuilder("Selected item ");
					sb.append(invalidProgramCdDesc);
					sb.append(" is not available. Please select another one.");
					if(StringUtils.isBlank(fromViewName)){
						fromViewName = "ltssupportdoc";
						if(orderCapture.getSbOrder() != null){
							fromViewName = "ltssummary";
						}
					}
					ModelAndView mav = new ModelAndView(new RedirectView(fromViewName+".html?errorMsg="+sb.toString())) ;
					return mav ;
				}
				
			}
		}
		
		String sbOrderId = ltsUpgradeEyeOrderService.saveOrder(orderCapture, bomSalesUser, reasonCd);
		
		//log csportal validation result
		if(orderCapture.getLtsPaymentForm() != null && orderCapture.getLtsPaymentForm().getCspVaildateRsp() != null){
			orderCapture.getLtsPaymentForm().getCspVaildateRsp().setOrderId(sbOrderId);
			csPortalService.createCsPortalTxn(orderCapture.getLtsPaymentForm().getCspVaildateRsp(), orderCapture.getLtsSalesInfoForm().getStaffId());
		}
		
		if (StringUtils.isEmpty(reasonCd)) {
			OrderCaptureDTO dummyOrderCapture = ltsUpgradeEyeOrderService.recallOrder(sbOrderId, false, bomSalesUser);
			dummyOrderCapture.setOrderType(orderCapture.getOrderType());
			LtsSessionHelper.setOrderCapture(request, dummyOrderCapture);
//			SbOrderDTO sbOrder = ltsUpgradeEyeOrderService.retrieveOrder(sbOrderId);
//			orderCapture.setSbOrder(sbOrder);
			orderCapture.setOrderAction(LtsConstant.ORDER_ACTION_SUSPEND);
			return new ModelAndView(new RedirectView(orderSummaryView));
		}
		else {
			LtsSessionHelper.clearOrderCapture(request);
		}
		
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, messageSource.getMessage("lts.ltsUpgEyeOrder.ordSuspended", new Object[] {sbOrderId}, this.locale));
		return new ModelAndView(new RedirectView(orderInfoView));
	}
	
	private ModelAndView cancelOrder(HttpServletRequest request, OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		
		String reasonCd = request.getParameter(LtsConstant.REQUEST_PARAM_REASON_CD);
		
		if (orderCapture == null || orderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		String sbOrderId = orderCapture.getSbOrder().getOrderId();
		ltsUpgradeEyeOrderService.cancelOrder(orderCapture.getSbOrder(), bomSalesUser, reasonCd);
		if(LtsConstant.ORDER_ACTION_ENQUIRY_N_CANCEL.equals(orderCapture.getOrderAction())){
			try {
				workQueueService.sbOrderCancelled(sbOrderId, false, bomSalesUser.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, messageSource.getMessage("lts.ltsUpgEyeOrder.sbOrderCancelled", new Object[] {sbOrderId}, this.locale) );
		return new ModelAndView(new RedirectView(orderInfoView));
	}
	
	private ModelAndView signOffOrder(HttpServletRequest request, OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		
		//TODO
		String fromViewName = request.getParameter(LtsConstant.REQUEST_PARAM_FROM_VIEW);
		
		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		if (orderCapture == null || sbOrder == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		ServiceDetailLtsDTO ltsSrv = LtsSbOrderHelper.getLtsService(sbOrder);
		boolean isNoSuspendReason = orderStatusService.noSuspendReason(sbOrder.getOrderId(), ltsSrv.getDtlId());
		if(isNoSuspendReason){
			
			List<String> itemIdList = new ArrayList<String>();
			for( ItemDetailLtsDTO  item :ltsSrv.getItemDtls()){
				if(LtsConstant.IO_IND_INSTALL.equals(item.getIoInd())){
					itemIdList.add(item.getItemId());
				}
			}
			

			List<ItemDetailDTO> itemList = new ArrayList<ItemDetailDTO>();
			List<ItemSetDetailDTO> itemSetList = new ArrayList<ItemSetDetailDTO>();
			itemList = ltsOfferService.getItem(itemIdList.toArray(new String[0]), LtsConstant.DISPLAY_TYPE_ITEM_SELECT, LtsConstant.LOCALE_ENG, true);
			for(ItemDetailDTO item: itemList){
				item.setSelected(true);
			}
			
			String invalidProgramCdDesc = processQuota(orderCapture, itemList, itemSetList);
			if(StringUtils.isNotBlank(invalidProgramCdDesc)){
				StringBuilder sb = new StringBuilder("Selected item ");
				sb.append(invalidProgramCdDesc);
				sb.append(" is not available. Please select another one.");
				if(StringUtils.isBlank(fromViewName)){
					fromViewName = "ltssummary";
				}
				ModelAndView mav = new ModelAndView(new RedirectView(fromViewName+".html?errorMsg="+sb.toString())) ;
				return mav ;
			}
			
		}
		
		sbOrder.setSignatures(ltsSignatureService.getOrderSignatures(sbOrder.getOrderId()));

		String emailResult = ltsUpgradeEyeOrderService.signOffOrder(sbOrder, request.getParameter("rptType"), request.getParameter("exportType"), request.getParameter("editButton"), bomSalesUser);

		sbOrder = ltsUpgradeEyeOrderService.retrieveOrder(orderCapture.getSbOrder().getOrderId());
		orderCapture.setSbOrder(sbOrder);
		orderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		
		if (StringUtils.isNotEmpty(emailResult)) {
			request.getSession().setAttribute("emailResult", emailResult);	
		}
		
		request.setAttribute("signoffCompletedMsg", "Submit Completed." );
		return new ModelAndView(new RedirectView(orderSummaryView));
//		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, "Springboard Order ["+orderCapture.getSbOrder().getOrderId()+"] Completed." );
//		return new ModelAndView(new RedirectView(orderInfoView));
	}
	
	private ModelAndView approveOrder(HttpServletRequest request, OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		if (orderCapture == null || orderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		ltsUpgradeEyeOrderService.approvalOrder(orderCapture.getSbOrder(), bomSalesUser);
		
		SbOrderDTO sbOrder = ltsUpgradeEyeOrderService.retrieveOrder(orderCapture.getSbOrder().getOrderId());
		orderCapture.setSbOrder(sbOrder);
		orderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		return new ModelAndView(new RedirectView(orderSummaryView));
		
//		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, "Springboard Order ["+orderCapture.getSbOrder().getOrderId()+"] is waiting approval." );
//		return new ModelAndView(new RedirectView(orderInfoView));
	}
	
	private ModelAndView suspendOrderWithPending(HttpServletRequest request, OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		
		if (orderCapture == null || orderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		ltsUpgradeEyeOrderService.suspendOrderWithPending(orderCapture.getSbOrder(), bomSalesUser);
		orderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
	
	/*return invalid itemDescription for error message*/
	private String processQuota(OrderCaptureDTO orderCapture, List<ItemDetailDTO> itemList, List<ItemSetDetailDTO> itemSetList){
		
		List<String> programCdList = new ArrayList<String>();
		if(orderCapture.getSelectedBasket() != null){
			String programCd = orderCapture.getSelectedBasket().getProgramCd();
			if(StringUtils.isNotBlank(programCd)){
				String invalidProgramCdDesc = ltsOfferService.validateQuotaByProgramCdRtnStr(programCd, 1);
				if(StringUtils.isNotBlank(invalidProgramCdDesc)){
					return "(" + invalidProgramCdDesc + ")";
				}else{
					programCdList.add(programCd);
				}
			}
		}
		
		List<String> outQuotaItemDesc = new ArrayList<String>();
		if(itemList != null && itemList.size() > 0){
			outQuotaItemDesc.addAll(ltsOfferService.getOutQuotaItemDescList(itemList));
		}
		if(itemSetList != null && itemSetList.size() > 0){
			outQuotaItemDesc.addAll(ltsOfferService.getOutQuotaItemSetDescList(itemSetList));
		}
		if(outQuotaItemDesc.size() > 0){

			StringBuilder sb = new StringBuilder();
			for(String itemDesc : outQuotaItemDesc){
				sb.append("(" + itemDesc + ")");
			}
			return sb.toString();
		}else{

			if(itemList != null && itemList.size() > 0){
				programCdList.addAll(ltsOfferService.getQuotaProgramCdItemList(itemList));
			}
			if(itemSetList != null && itemSetList.size() > 0){
				programCdList.addAll(ltsOfferService.getQuotaProgramCdItemSetList(itemSetList));
			}
			if(programCdList.size() > 0){
				for(String code : programCdList){
					ltsQuotaService.increaseQuotaUsed(code);
				}
			}
		}
		
		return null;
	}

	private static <E> void addAllIfNotNull(List<E> list, Collection<? extends E> c) {
	    if (c != null) {
	        list.addAll(c);
	    }
	}
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
