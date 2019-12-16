package com.bomwebportal.mob.ccs.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;

@Transactional(readOnly=true)
public class MobCcsCancelServiceImpl implements MobCcsCancelService {
	protected final Log logger = LogFactory.getLog(getClass());

	enum OrderStatus {
		CANCELLING("03")
		, CANCELLED("04")
		, FALLOUT("99")
		;
		OrderStatus(String status) {
			this.status = status;
		}
		public String getStatus() {
			return status;
		}
		private String status;
	}
	
	enum CheckPoint {
		BOM_SUBMISSION_PENDING("C400")
		, BOM_SUBMISSION_SUCCESS("C500")
		;
		CheckPoint(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		private String value;
	}
	
	private OrderService orderService;
	private MobCcsOrderRemarkService mobCcsOrderRemarkService;
	
	
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public MobCcsOrderRemarkService getMobCcsOrderRemarkService() {
		return mobCcsOrderRemarkService;
	}

	public void setMobCcsOrderRemarkService(
			MobCcsOrderRemarkService mobCcsOrderRemarkService) {
		this.mobCcsOrderRemarkService = mobCcsOrderRemarkService;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void cancelMobCcsOrder(String orderId, String codeId, String remark, String username) {
		if (logger.isInfoEnabled()) {
			logger.info("orderId: " + orderId + ", codeId: " + codeId + ", remark: " + remark + ", username: " + username);
		}
		OrderDTO orderDTO = this.orderService.getOrder(orderId);
		
		
		if (!"MOB".equals(orderDTO.getOrderSumLob())) {
			return;
		}
		
		this.orderService.backupOrder(orderId);
		orderDTO = this.orderService.getOrder(orderId);
		String histSeq=orderService.getOrderHistSeqNo(orderId);
		
		if (logger.isDebugEnabled()) {
			logger.debug("orderId: " + orderId + ", orderStatus: " + orderDTO.getOrderStatus() + ", ocid: " + orderDTO.getOcid() + ", checkPoint: " + orderDTO.getCheckPoint() + ", reasonCd: " + orderDTO.getReasonCd() + ", creditCardTrxInd: " + orderDTO.getCreditCardTrxInd());
		}
		
		if (StringUtils.isBlank(orderDTO.getOcid())) {
			if ("Y".equals(orderDTO.getCreditCardTrxInd())) {
				// orderStatus = 03, checkPoint = C400, hist_seq_no
				this.orderService.updateCcsOrderStatusToCancel(orderDTO.getOrderId()
						, OrderStatus.CANCELLING.getStatus()
						, CheckPoint.BOM_SUBMISSION_PENDING.getValue()
						, codeId
						, username
						, histSeq);
				// [20140128]MultiSim case, set member status to Cancelled, SP update MRT/ SIM to free
				if (this.orderService.isMultiSimOrder(orderDTO.getOrderId())){
					this.orderService.updateMultiSimMemberStatus(orderDTO.getOrderId(), "ALL", BomWebPortalConstant.BWP_MULTISIM_ORDER_CANCELLED, null, null, "AP");
				}
				this.orderService.cancelOrderReleaseGoods(orderDTO.getOrderId());
				this.orderService.assignSimForCancel(orderDTO.getOrderId());
				
			} else {
				// release goods
				// orderStatus = 04
				this.orderService.updateCcsOrderStatus(orderDTO.getOrderId()
						, OrderStatus.CANCELLED.getStatus()
						, null
						, codeId
						, null
						, null
						, username);
				// [20140128]MultiSim case, set member status to Cancelled, SP update MRT/ SIM to free
				if (this.orderService.isMultiSimOrder(orderDTO.getOrderId())){
					this.orderService.updateMultiSimMemberStatus(orderDTO.getOrderId(), "ALL", BomWebPortalConstant.BWP_MULTISIM_ORDER_CANCELLED, null, null, "AP");
				}
				this.orderService.cancelOrderReleaseGoods(orderDTO.getOrderId());
				
				
			}
		} else {
			// orderStatus = 03, checkPoint = C500
			this.orderService.updateCcsOrderStatus(orderDTO.getOrderId()
					, OrderStatus.CANCELLING.getStatus()
					, CheckPoint.BOM_SUBMISSION_SUCCESS.getValue()
					, codeId
					, null
					, null
					, username);
			// [20140128]MultiSim case, set member status to Canceling 800, SP update MRT/ SIM to canceling
			if (this.orderService.isMultiSimOrder(orderDTO.getOrderId())){
				this.orderService.cancelMultiSimMember(orderDTO.getOrderId(),"AP");
			}
			this.orderService.cancelOrderReleaseGoods(orderDTO.getOrderId());
		}
		this.orderService.cancelCourierQuotaProcess(orderDTO.getOrderId(),username);
		this.mobCcsOrderRemarkService.insertOrderRemark(username, orderId, remark);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void cancelWithRecreateMobCcsOrder(String orderId, String codeId, String remark, String username, String newOrderId) {
		OrderDTO orderDTO = this.orderService.getOrder(orderId);
		
		
		if (!"MOB".equals(orderDTO.getOrderSumLob())) {
			return;
		}
		
		this.orderService.backupOrder(orderId);
		orderDTO = this.orderService.getOrder(orderId);
		String histSeq=orderService.getOrderHistSeqNo(orderId);
		
		if (StringUtils.isBlank(orderDTO.getOcid())) {
			if ("Y".equals(orderDTO.getCreditCardTrxInd())) {
				// orderStatus = 03, checkPoint = C400, hist_seq_no
				this.orderService.updateCcsOrderStatusToCancel(orderDTO.getOrderId()
						, OrderStatus.CANCELLING.getStatus()
						, CheckPoint.BOM_SUBMISSION_PENDING.getValue()
						, codeId
						, username
						, histSeq);
				
			} else {
				// release goods
				// orderStatus = 04
				this.orderService.updateCcsOrderStatus(orderDTO.getOrderId()
						, OrderStatus.CANCELLED.getStatus()
						, null
						, codeId
						, null
						, null
						, username);
				
				
				
			}
		} else {
			// orderStatus = 03, checkPoint = C500
			this.orderService.updateCcsOrderStatus(orderDTO.getOrderId()
					, OrderStatus.CANCELLING.getStatus()
					, CheckPoint.BOM_SUBMISSION_SUCCESS.getValue()
					, codeId
					, null
					, null
					, username);
			
		}
		
		this.orderService.cancelRecreateOrderStockMrtTransfer(orderDTO.getOrderId(), newOrderId);
		
		this.mobCcsOrderRemarkService.insertOrderRemark(username, orderId, remark);
	}

}
