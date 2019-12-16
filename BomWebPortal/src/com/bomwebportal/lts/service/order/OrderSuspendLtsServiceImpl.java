package com.bomwebportal.lts.service.order;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.order.OrderIdDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;

public class OrderSuspendLtsServiceImpl implements OrderSuspendLtsService {

	private SaveSbOrderLtsService saveSbOrderLtsService;
	private OrderStatusService orderStatusService;
	private OrderDetailService orderDetailService;
	
	@Transactional
	public String suspendOrder(SbOrderDTO pSbOrder, BomSalesUserDTO pBomSalesUser, String pReasonCd) {
		
		if(StringUtils.isBlank(pSbOrder.getShopCd())){
			pSbOrder.setShopCd(pBomSalesUser.getShopCd());
		}
		preSubmit(pSbOrder, pBomSalesUser);
		saveSbOrderLtsService.saveSbOrder(pSbOrder, pBomSalesUser.getUsername());		
		postSubmit(pSbOrder, pReasonCd, pBomSalesUser);
		return pSbOrder.getOrderId();
	}
	
//	private void getServiceActions() {
//		
//		ServiceDetailDTO[] srvDtls = this.sbOrder.getSrvDtls();
//		ServiceActionTypeDTO[] srvActions = null;
//		
//		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
//			srvActions = this.serviceActionTypeLookupService.getServiceActionType(srvDtls[i]);
//			srvDtls[i].setSrvActions(srvActions);
//			
//			for (int j=0; j<srvActions.length; ++j) {
//				if (StringUtils.isNotEmpty(srvActions[j].getSuspendReaCdBom())) {
//					srvDtls[i].setSuspendBomInd(LtsConstant.SUSPEND_IND_BY_DTL);
//					srvDtls[i].setSuspendBomReasonCd(srvActions[j].getSuspendReaCdBom());
//				}
//			}
//		}
//		ServiceDetailLtsDTO ltsEyeSrv = LtsSbHelper.getLtsEyeService(srvDtls);
//		ServiceDetailOtherLtsDTO imsEyeSrv = LtsSbHelper.getImsEyeService(srvDtls);
//		ServiceActionTypeDTO[] imsEyeActions = this.serviceActionTypeLookupService.getServiceActionType(imsEyeSrv, ltsEyeSrv.getFromProd(), ltsEyeSrv.getToProd());
//		
//		for (int i=0; imsEyeActions!=null && i<imsEyeActions.length; ++i) {
//			imsEyeSrv.addSrvAction(imsEyeActions[i]);
//		}
//	}
	
	private void preSubmit(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		this.initializeOrderDetail(sbOrder, bomSalesUser);
		this.initializeServices(sbOrder);
//		this.getServiceActions();
		this.initializeStatus(sbOrder, bomSalesUser);
		this.initalizeDetailContents(sbOrder);
	}
	
	private void postSubmit(SbOrderDTO sbOrder, String suspendReasonCd, BomSalesUserDTO bomSalesUser) {
		this.updateStatusAfterSuspend(sbOrder, suspendReasonCd, bomSalesUser);
	}
	
	private void initializeOrderDetail(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		
		if (StringUtils.isNotEmpty(sbOrder.getOrderId())) {
			return;
		}
		
		String srvType = LtsSbOrderHelper.getLtsEyeService(sbOrder) != null ? "E" : "D";
		
		OrderIdDTO orderid = this.orderDetailService.generateOrderId(
				bomSalesUser.getChannelId() == 1 ? bomSalesUser.getShopCd()
						: bomSalesUser.getChannelCd(), bomSalesUser
						.getUsername(), LtsConstant.ORDER_MODE_STAFF, srvType);
		sbOrder.setOrderId(orderid.getOrderId());
		sbOrder.setBoc(orderid.getBoc());
	}
	
	private void initializeServices(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		
		for (int i=0; i<srvDtls.length; ++i) {
			if (StringUtils.isEmpty(srvDtls[i].getDtlId())) {
				srvDtls[i].setDtlId(this.getMaxDtlId(sbOrder));
			}
		}
	}
	
	private void initalizeDetailContents(SbOrderDTO sbOrder) {
		
		ServiceDetailLtsDTO eyeLtsSrv = LtsSbOrderHelper.getLtsEyeService(sbOrder);
		ServiceDetailOtherLtsDTO eyeImsSrv = LtsSbOrderHelper.getImsEyeService(sbOrder.getSrvDtls());
		
		if (eyeImsSrv == null || eyeLtsSrv == null || eyeImsSrv.getNowtvDetail() == null) {
			return;
		}
		eyeLtsSrv.getAccount().getCustomer().setDob(eyeImsSrv.getNowtvDetail().getDob());
	}
	
	private String getMaxDtlId(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		int maxDtlId = 0;
		int curDtlId = 0;
		
		for (int i=0; i<srvDtls.length; ++i) {
			if (StringUtils.isEmpty(srvDtls[i].getDtlId())) {
				continue;
			}
			curDtlId = Integer.parseInt(srvDtls[i].getDtlId());
			if (maxDtlId < curDtlId) {
				maxDtlId = curDtlId;
			}
		}
		return String.valueOf(maxDtlId+1);
	}
	
	private void initializeStatus(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		
		ServiceDetailDTO[] srvs = sbOrder.getSrvDtls();
		
		for (int i=0; srvs!=null && i<srvs.length; ++i) {
			if (StringUtils.isEmpty(srvs[i].getSbStatus())) {
				this.orderStatusService.initializeStatus(sbOrder.getOrderId(), srvs[i].getDtlId(), bomSalesUser.getUsername());
			}
		}
	}

	private void updateStatusAfterSuspend(SbOrderDTO sbOrder, String pSuspendReasonCd, BomSalesUserDTO bomSalesUser) {
		
		ServiceDetailDTO[] srvs = sbOrder.getSrvDtls();
		
		
		for (int i=0; srvs!=null && i<srvs.length; ++i) {
			String suspendReasonCd = pSuspendReasonCd;
			if(StringUtils.isEmpty(suspendReasonCd)){
				suspendReasonCd = srvs[i].getSbReasonCd();
			}
			if (!StringUtils.equals(LtsConstant.ORDER_STATUS_APPROVAL_REJECTED, srvs[i].getSbStatus())) {
				this.orderStatusService.setSuspendStatus(sbOrder.getOrderId(), srvs[i].getDtlId(), suspendReasonCd, bomSalesUser.getUsername(), srvs[i].getWorkQueueType());
			}
		}
	}

	public SaveSbOrderLtsService getSaveSbOrderLtsService() {
		return saveSbOrderLtsService;
	}

	public void setSaveSbOrderLtsService(SaveSbOrderLtsService saveSbOrderLtsService) {
		this.saveSbOrderLtsService = saveSbOrderLtsService;
	}

	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}

	public OrderDetailService getOrderDetailService() {
		return orderDetailService;
	}

	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}
}
