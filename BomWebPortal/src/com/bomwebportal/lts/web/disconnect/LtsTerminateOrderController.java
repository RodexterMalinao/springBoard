package com.bomwebportal.lts.web.disconnect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.CsPortalService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.disconnect.LtsTerminateOrderService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsTerminateOrderController extends AbstractController {
	
	private final String createOrderView="lts/disconnect/ltsterminatecustomerinquiry.html";
	private final String orderInfoView="ltsorderinfo.html";
	private final String orderSummaryView="ltsterminatesummary.html";
	private final String orderRecallView="ltsterminateappointment.html";
	private final String orderRecallSalesView="ltsterminatesalesinfo.html";
	private final String amendOrderView="orderamend.html";
	
	protected LtsTerminateOrderService ltsTerminateOrderService;
	private OrderStatusService orderStatusService;
	private CsPortalService csPortalService;
	private LtsCommonService ltsCommonService;
	
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}
	
	public LtsTerminateOrderService getLtsTerminateOrderService() {
		return ltsTerminateOrderService;
	}

	public void setLtsTerminateOrderService(
			LtsTerminateOrderService ltsTerminateOrderService) {
		this.ltsTerminateOrderService = ltsTerminateOrderService;
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
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BomSalesUserDTO bomSalesUser = LtsSessionHelper.getBomSalesUser(request);
		TerminateOrderCaptureDTO orderCapture = LtsSessionHelper.getTerminateOrderCapture(request);
		String action = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ACTION);
		
		if (StringUtils.equals(LtsConstant.ORDER_ACTION_CREATE, action) || StringUtils.isEmpty(action)) {
			LtsSessionHelper.clearTerminateOrderCapture(request);
			return new ModelAndView(new RedirectView(createOrderView));	
		}
		
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

		if (StringUtils.equals(LtsConstant.ORDER_ACTION_ENQUIRY_N_CANCEL, action)) {
			return enquiryAndCancelOrder(request, bomSalesUser);
		}
		
		
		LtsSessionHelper.clearTerminateOrderCapture(request);
		return new ModelAndView(new RedirectView(createOrderView));
	}
	
	private ModelAndView enquiryAndCancelOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		TerminateOrderCaptureDTO dummyOrderCapture = ltsTerminateOrderService.recallOrder(orderId, true, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY_N_CANCEL);
		LtsSessionHelper.setTerminateOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
	
	private ModelAndView enquiryOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		TerminateOrderCaptureDTO dummyOrderCapture = ltsTerminateOrderService.recallOrder(orderId, true, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		LtsSessionHelper.setTerminateOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
	
	private ModelAndView recallOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		TerminateOrderCaptureDTO dummyOrderCapture = ltsTerminateOrderService.recallOrder(orderId, false, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_RECALL);
		LtsSessionHelper.setTerminateOrderCapture(request, dummyOrderCapture);
		
		if(LtsConstant.SALES_ROLE_SUPPORT.equals(bomSalesUser.getCategory())){
			return new ModelAndView(new RedirectView(orderRecallSalesView));
		}else{
			return new ModelAndView(new RedirectView(orderRecallView));
		}
	}
	
	private ModelAndView amendOrder(HttpServletRequest request, BomSalesUserDTO bomSalesUser) {
		
		String orderId = request.getParameter(LtsConstant.REQUEST_PARAM_ORDER_ID);
		TerminateOrderCaptureDTO dummyOrderCapture = ltsTerminateOrderService.recallOrder(orderId, false, bomSalesUser);

		if (dummyOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_AMEND);
		LtsSessionHelper.setTerminateOrderCapture(request, dummyOrderCapture);
		return new ModelAndView(new RedirectView(amendOrderView));
	}
	
	private ModelAndView suspendOrder(HttpServletRequest request, TerminateOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		
		if (orderCapture.getSbOrder() != null) {
			
			ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getDisconnectEyeService(orderCapture.getSbOrder().getSrvDtls());
			
			if(ltsService == null){
				ltsService = LtsSbOrderHelper.getDisconnectDelService(orderCapture.getSbOrder().getSrvDtls());
			}
								
//			ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(orderCapture.getSbOrder());
			if (!(StringUtils.equals(LtsConstant.ORDER_STATUS_SUSPENDED, ltsService.getSbStatus())
				|| StringUtils.equals(LtsConstant.ORDER_STATUS_APPROVAL_REJECTED, ltsService.getSbStatus()))){
				orderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
				return new ModelAndView(new RedirectView(orderSummaryView));		
			}
			
		}
		
		String reasonCd = request.getParameter(LtsConstant.REQUEST_PARAM_REASON_CD);
		String sbOrderId = ltsTerminateOrderService.saveOrder(orderCapture, bomSalesUser, reasonCd);
		
		if (StringUtils.isEmpty(reasonCd)) {
			TerminateOrderCaptureDTO dummyOrderCapture = ltsTerminateOrderService.recallOrder(sbOrderId, false, bomSalesUser);
			LtsSessionHelper.setTerminateOrderCapture(request, dummyOrderCapture);
			dummyOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_SUSPEND);
//			LtsSessionHelper.setTerminateOrderCapture(request, dummyOrderCapture);
			return new ModelAndView(new RedirectView(orderSummaryView));
		}
		else {
			LtsSessionHelper.clearOrderCapture(request);
		}
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, "Order Suspended. Springboard Order Id : " +sbOrderId);
		return new ModelAndView(new RedirectView(orderInfoView));
	}
	
	private ModelAndView cancelOrder(HttpServletRequest request, TerminateOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		
		String reasonCd = request.getParameter(LtsConstant.REQUEST_PARAM_REASON_CD);
		
		if (orderCapture == null || orderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		String sbOrderId = orderCapture.getSbOrder().getOrderId();
		ltsTerminateOrderService.cancelOrder(orderCapture.getSbOrder(), bomSalesUser, reasonCd);
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, "Springboard Order ["+sbOrderId+"] Cancelled." );
		return new ModelAndView(new RedirectView(orderInfoView));
	}
	
	private ModelAndView signOffOrder(HttpServletRequest request, TerminateOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		
		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		if (orderCapture == null || sbOrder == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}		

		String signOffError = ltsTerminateOrderService.signOffOrder(sbOrder, request.getParameter("rptType"), request.getParameter("exportType"), request.getParameter("editButton"), bomSalesUser);
		request.getSession().setAttribute("signOffError", signOffError);
		
		sbOrder = ltsTerminateOrderService.retrieveOrder(orderCapture.getSbOrder().getOrderId());
		orderCapture.setSbOrder(sbOrder);
		orderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		
		return new ModelAndView(new RedirectView(orderSummaryView));
//		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, "Springboard Order ["+orderCapture.getSbOrder().getOrderId()+"] Completed." );
//		return new ModelAndView(new RedirectView(orderInfoView));
	}
	
	private ModelAndView approveOrder(HttpServletRequest request, TerminateOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		if (orderCapture == null || orderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		ltsTerminateOrderService.approvalOrder(orderCapture.getSbOrder(), bomSalesUser);
		
		SbOrderDTO sbOrder = ltsTerminateOrderService.retrieveOrder(orderCapture.getSbOrder().getOrderId());
		orderCapture.setSbOrder(sbOrder);
		orderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		return new ModelAndView(new RedirectView(orderSummaryView));
		
//		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, "Springboard Order ["+orderCapture.getSbOrder().getOrderId()+"] is waiting approval." );
//		return new ModelAndView(new RedirectView(orderInfoView));
	}
	
	private ModelAndView suspendOrderWithPending(HttpServletRequest request, TerminateOrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {
		
		if (orderCapture == null || orderCapture.getSbOrder() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		ltsTerminateOrderService.suspendOrderWithPending(orderCapture.getSbOrder(), bomSalesUser);
		orderCapture.setOrderAction(LtsConstant.ORDER_ACTION_ENQUIRY);
		return new ModelAndView(new RedirectView(orderSummaryView));
	}
}
