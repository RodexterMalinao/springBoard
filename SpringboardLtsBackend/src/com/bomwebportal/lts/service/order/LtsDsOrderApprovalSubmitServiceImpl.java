package com.bomwebportal.lts.service.order;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.DsServiceLtsService;
import com.bomwebportal.lts.service.activity.PipbActivityLtsSubmissionService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;

public class LtsDsOrderApprovalSubmitServiceImpl implements LtsDsOrderApprovalSubmitService {

protected final Log logger = LogFactory.getLog(getClass());
	
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private OrderStatusService orderStatusService;
	private PipbActivityLtsSubmissionService pipbActivityLtsSubmissionService;
	private PipbOrderResumeSubmitService pipbOrderResumeSubmitService;
	private DsServiceLtsService dsServiceLtsService;

	@Override
	public boolean resumeSignOffDsOrder(String orderId, String username) {
		if(StringUtils.isBlank(orderId)){
			return false;
		}
		
		SbOrderDTO sbOrder = orderRetrieveLtsService.retrieveSbOrder(orderId, true);
		return resumeSignOffDsOrder(sbOrder, username);
	}
	
	@Override
	public boolean resumeSignOffDsOrder(SbOrderDTO sbOrder, String username) {
		if(sbOrder == null){
			return false;
		}
		
		if(checkAndSubmitMustQcOrder(sbOrder, username)){
			/*Change status to Q and stop*/
			return true;
		}

		try{
			return pipbOrderResumeSubmitService.resumeSignOffPipbOrder(sbOrder, username);
		}catch(Exception e){
			logger.error("Exception caught in signoffDsOrder " + sbOrder.getOrderId() + " - " + e.getMessage());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return false;
		}
	}

	public boolean checkCustNameNotMatchCase(SbOrderDTO sbOrder){
		ServiceDetailLtsDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
		if(StringUtils.equals("Y", ltsService.getAccount().getCustomer().getMismatchInd())){
			return true;
		}
		return false;
	}
	
	public boolean checkAndSubmitMustQcOrder(SbOrderDTO pSbOrder, String userId){
		if(pSbOrder.getLtsDsOrderInfo() == null){
			return false;
		}
		
		if("Y".equals(pSbOrder.getLtsDsOrderInfo().getMustQc())){
			ServiceDetailDTO[] srvDtls = pSbOrder.getSrvDtls();
			for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
				// update order status = Q
				orderStatusService.setPendingQcStatus(pSbOrder.getOrderId(), srvDtls[i].getDtlId(), userId, null);
			}	
			return true;
		}
		return false;
	}
	
	public boolean submitOrderForCustNameNotMatch(SbOrderDTO sbOrder, String username){
		try{
			String remarks = null;
			pipbActivityLtsSubmissionService.createCustNameNotMatchApproval(sbOrder.getOrderId(), username, remarks);
			
			dsServiceLtsService.updateDsNameNotMatchApprovalStatus(sbOrder.getOrderId(), username, LtsBackendConstant.NAME_MISMATCH_APR_CD_PENDING_APPROVAL);
			ServiceDetailDTO[] serviceDetails = sbOrder.getSrvDtls();
			for (int i=0; serviceDetails!=null && i<serviceDetails.length; ++i) {
				// update order status = W + reason = APPROVAL
				orderStatusService.setPendingNameNotMatchApprovalStatus(sbOrder.getOrderId(), serviceDetails[i].getDtlId(), username, null);
			}
			
			return true;
		}catch(Exception e){
			logger.error("Exception caught in submitOrderForCustNameNotMatch " + sbOrder.getOrderId() + " - " + e.getMessage());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return false;
		}
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}

	public PipbActivityLtsSubmissionService getPipbActivityLtsSubmissionService() {
		return pipbActivityLtsSubmissionService;
	}

	public void setPipbActivityLtsSubmissionService(
			PipbActivityLtsSubmissionService pipbActivityLtsSubmissionService) {
		this.pipbActivityLtsSubmissionService = pipbActivityLtsSubmissionService;
	}

	public PipbOrderResumeSubmitService getPipbOrderResumeSubmitService() {
		return pipbOrderResumeSubmitService;
	}

	public void setPipbOrderResumeSubmitService(
			PipbOrderResumeSubmitService pipbOrderResumeSubmitService) {
		this.pipbOrderResumeSubmitService = pipbOrderResumeSubmitService;
	}

	public DsServiceLtsService getDsServiceLtsService() {
		return dsServiceLtsService;
	}

	public void setDsServiceLtsService(DsServiceLtsService dsServiceLtsService) {
		this.dsServiceLtsService = dsServiceLtsService;
	}
}
